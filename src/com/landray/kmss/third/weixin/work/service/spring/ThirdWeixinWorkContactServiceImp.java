package com.landray.kmss.third.weixin.work.service.spring;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.organization.model.*;
import com.landray.kmss.sys.organization.service.IKmssPasswordEncoder;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.util.PasswordUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinContact;
import com.landray.kmss.third.weixin.model.ThirdWeixinContactMapp;
import com.landray.kmss.third.weixin.model.ThirdWeixinContactTag;
import com.landray.kmss.third.weixin.service.IThirdWeixinContactMappService;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkCallback;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinWorkContactService;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ThirdWeixinWorkContactServiceImp implements IThirdWeixinWorkContactService {

    private static final Logger logger = LoggerFactory.getLogger(ThirdWeixinWorkContactServiceImp.class);

    private ISysOrgElementService sysOrgElementService;
    public void setSysOrgElementService(ISysOrgElementService sysOrgElementService){
        this.sysOrgElementService = sysOrgElementService;
    }

    private ISysOrgPersonService sysOrgPersonService;
    public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService){
        this.sysOrgPersonService = sysOrgPersonService;
    }

    private IWxworkOmsRelationService wxworkOmsRelationService;

    public void setWxworkOmsRelationService(
            IWxworkOmsRelationService wxworkOmsRelationService) {
        this.wxworkOmsRelationService = wxworkOmsRelationService;
    }

    private IThirdWeixinContactMappService thirdWeixinContactMappService;
    public void setThirdWeixinContactMappService(IThirdWeixinContactMappService thirdWeixinContactMappService){
        this.thirdWeixinContactMappService = thirdWeixinContactMappService;
    }

    private IKmssPasswordEncoder passwordEncoder;
    public void setPasswordEncoder(IKmssPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private List<String> getWxUserList() throws Exception {
        HQLInfo info = new HQLInfo();
        info.setSelectBlock("fdAppPkId");
        info.setWhereBlock("fdAppPkId is not null and fdEkpId in (select fdId from SysOrgPerson sysOrgPerson)");
        return wxworkOmsRelationService.findValue(info);
    }

    @Override
    public void synchro2ekp() throws Exception {
        WeixinWorkConfig config = WeixinWorkConfig.newInstance();
        String orgTypeSetting = config.getSyncContactOrgTypeSetting();
        if(StringUtil.isNull(orgTypeSetting)){
            throw new Exception("必须先配置好“组织类型同步设置”");
        }
        List<String> wxUsers = getWxUserList();
        if(wxUsers==null || wxUsers.isEmpty()){
            throw new Exception("映射表中没有人员映射数据，请先完成“组织同步”或“人员初始化映射”");
        }
        logger.debug("orgTypeSetting:"+orgTypeSetting);
        Map<String,String> tagOrgTypeMapping = getTagOrgTypeMapping();
        WxworkApiService wxworkApiService = WxworkUtils.getWxworkApiService();
        Map<String,ThirdWeixinContact> contactMap = wxworkApiService.listContactBatch(wxUsers);
        Set<String> contactIds_current = contactMap.keySet();
        for(ThirdWeixinContact contact:contactMap.values()){
            logger.debug("同步客户："+contact.toString());
            handleContact(contact,tagOrgTypeMapping);
        }
        deleteContacts(contactIds_current);
    }

    private ThirdWeixinContactMapp findContactMappByOrgTypeId(List<ThirdWeixinContactMapp> list, String orgTypeId){
        if(list==null || list.isEmpty()){
            return null;
        }
        for(ThirdWeixinContactMapp mapp:list){
            if(orgTypeId.equals(mapp.getFdOrgTypeId())){
                return mapp;
            }
        }
        return null;
    }

    private SysOrgElement findDeptByName(String parentId, String name) throws Exception {
        HQLInfo info = new HQLInfo();
        info.setWhereBlock("fdIsExternal = :external and fdOrgType = 2  and hbmParent.fdId = :parentId and fdName = :name");
        info.setParameter("external",true);
        info.setParameter("parentId",parentId);
        info.setParameter("name",name);
        return (SysOrgElement) sysOrgElementService.findFirstOne(info);
    }

    private SysOrgDept addExternalDept(String parentId, String name) throws Exception {
        logger.debug("新增部门，name:"+name+"，parentId:"+parentId);
        SysOrgDept dept = new SysOrgDept();
        dept.setFdName(name);
        dept.setFdIsExternal(true);
        dept.setFdParent((SysOrgElement)sysOrgElementService.findByPrimaryKey(parentId));
        setRange(dept);
        if("微信客户".equals(name)){
            dept.setFdOrder(Integer.MAX_VALUE);
        }
        sysOrgElementService.add(dept);
        return dept;
    }

    private void setRange(SysOrgElement sysOrgElement) throws Exception {
        SysOrgElementRange range = new SysOrgElementRange();
        // 默认值
        range.setFdIsOpenLimit(false);
        range.setFdViewType(1);
        range.setFdElement(sysOrgElement);
        sysOrgElement.setFdRange(range);
    }

    private String getPropValue(ThirdWeixinContact contact, boolean isAdd, String synWayProp, String synValueProp) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        WeixinWorkConfig config = WeixinWorkConfig.newInstance();
        String synWay = config.getDataMap().get(synWayProp);
        if(StringUtil.isNotNull(synWay)){
            if ("syn".equalsIgnoreCase(synWay)
                    || (isAdd && "addSyn".equalsIgnoreCase(synWay))) {
                String synValuePropValue = config.getDataMap().get(synValueProp);
                String value = null;
                if(StringUtil.isNotNull(synValuePropValue)) {
                    value = (String) PropertyUtils.getProperty(contact, synValuePropValue);
                }
                if(value == null){
                    value = "";
                }
                return value;
            }
        }
        return null;
    }

    private String getSex(String gender){
        if("1".equals(gender)){
            return "M";
        }else if("0".equals(gender)){
            return "F";
        }
        return null;
    }

    private void setPersonProps(SysOrgPerson person,ThirdWeixinContact contact, boolean isAdd) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String name = getPropValue(contact,isAdd,"contact2ekp.name.synWay","contact2ekp.name");
        if(name!=null){
            person.setFdName(name);
        }
        String alias = getPropValue(contact,isAdd,"contact2ekp.alias.synWay","contact2ekp.alias");
        if(alias!=null){
            person.setFdNickName(alias);
        }
        String gender = getPropValue(contact,isAdd,"contact2ekp.gender.synWay","contact2ekp.gender");
        if(gender!=null){
            person.setFdSex(getSex(gender));
        }
        //String position = getPropValue(contact,isAdd,"contact2ekp.position.synWay","contact2ekp.position");

    }

    private void addThirdWeixinContactMapp(ThirdWeixinContact contact, String personId, String tagId, String tagName, String orgTypeId) throws Exception {
        logger.debug("新增客户映射，客户名称："+contact.getName()+"，ekp用户ID："+personId+"，标签名称："+tagName);
        ThirdWeixinContactMapp mapp = new ThirdWeixinContactMapp();
        mapp.setFdContactName(contact.getName());
        mapp.setFdContactUserId(contact.getExternal_userid());
        mapp.setFdCorpId(WeixinWorkConfig.newInstance().getWxOrgId());
        mapp.setFdExternalId(personId);
        mapp.setFdIsDelete(false);
        mapp.setFdTagId(tagId+";");
        mapp.setFdTagName(tagName+";");
        mapp.setFdOrgTypeId(orgTypeId);
        thirdWeixinContactMappService.add(mapp);
    }

    private void addContact(ThirdWeixinContact contact, String tagId, String tagName, Map<String,String> tagOrgTypeMapping) throws Exception {
        TransactionStatus status = null;
        try {
            logger.debug("新增客户，客户名称："+contact.getName()+"，客户ID："+contact.getExternal_userid()+"，标签ID："+tagId+"，标签名称："+tagName);
            status = TransactionUtils.beginNewTransaction();
            String orgTypeId = tagOrgTypeMapping.get(tagId);
            if(StringUtil.isNull(orgTypeId)){
                logger.warn("找不到改标签的映射，标签名称："+tagName+"，标签ID："+tagId);
                return;
            }
            String deptName = getDeptName(contact);
            SysOrgElement dept = findDeptByName(orgTypeId, deptName);
            if (dept == null) {
                dept = addExternalDept(orgTypeId, deptName);
            }
            SysOrgPerson person = new SysOrgPerson();
            person.setFdLoginName(contact.getExternal_userid().replaceAll("-","") + "_" + orgTypeId);
            person.setFdParent(dept);
            person.setFdIsExternal(true);
            SysOrgDefaultConfig sysOrgDefaultConfig = new SysOrgDefaultConfig();
            String psw = sysOrgDefaultConfig.getOrgDefaultPassword();
            if(StringUtil.isNotNull(psw)){
                person.setFdPassword(passwordEncoder.encodePassword(psw));
                person.setFdInitPassword(PasswordUtil.desEncrypt(psw));
            }
            setPersonProps(person, contact, true);
            String personId = sysOrgPersonService.add(person);

            addThirdWeixinContactMapp(contact, personId, tagId, tagName,orgTypeId);
            TransactionUtils.getTransactionManager().commit(status);
        }catch (Exception e){
            if(status!=null){
                TransactionUtils.getTransactionManager().rollback(status);
            }
            throw e;
        }
    }

    private void updateContact(ThirdWeixinContact contact, String tagId, Map<String,String> tagOrgTypeMapping, String personId) throws Exception {
        TransactionStatus status = null;
        try {
            logger.debug("更新客户，客户名称："+contact.getName()+"，客户ID："+contact.getExternal_userid()+"，标签ID："+tagId+"，EKP客户ID："+personId);
            status = TransactionUtils.beginNewTransaction();
            String deptName = getDeptName(contact);
            if (StringUtil.isNull(deptName)) {
                logger.warn("客户企业名称为空，不同步。客户名称："+contact.getName()+"，客户ID："+contact.getExternal_userid()+"，标签ID："+tagId+"，标签ID："+tagId);
                return;
            }
            String orgTypeId = tagOrgTypeMapping.get(tagId);
            SysOrgElement dept = findDeptByName(orgTypeId, deptName);
            if (dept == null) {
                dept = addExternalDept(orgTypeId, deptName);
            }

            SysOrgPerson person = (SysOrgPerson)sysOrgPersonService.findByPrimaryKey(personId,null,true);
            boolean isAdd = false;
            if(person==null) {
                person = new SysOrgPerson();
                person.setFdIsAvailable(true);
                person.setFdId(personId);
                person.setFdLoginName(contact.getExternal_userid().replaceAll("-","") + "_" + orgTypeId);
            }
            person.setFdParent(dept);
            person.setFdIsExternal(true);
            person.setFdIsAvailable(true);
            setPersonProps(person, contact, false);
            if(isAdd){
                sysOrgPersonService.add(person);
            }else {
                sysOrgPersonService.update(person);
            }
            TransactionUtils.getTransactionManager().commit(status);
        }catch (Exception e){
            if(status!=null){
                TransactionUtils.getTransactionManager().rollback(status);
            }
            throw e;
        }
    }

    private void deleteContact(ThirdWeixinContactMapp mapp) throws Exception {
        TransactionStatus status = null;
        try {
            logger.debug("删除客户，客户名称："+mapp.getFdContactName()+"，客户ID："+mapp.getFdContactUserId()+"，ekp用户ID："+mapp.getFdExternalId());
            status = TransactionUtils.beginNewTransaction();
            String ekpId = mapp.getFdExternalId();
            SysOrgElement element = (SysOrgElement)sysOrgElementService.findByPrimaryKey(ekpId);
            if(element!=null){
                element.setFdIsAvailable(false);
                sysOrgElementService.update(element);
            }
            mapp.setFdIsDelete(true);
            thirdWeixinContactMappService.update(mapp);
            TransactionUtils.getTransactionManager().commit(status);
        }catch (Exception e){
            if(status!=null){
                TransactionUtils.getTransactionManager().rollback(status);
            }
            throw e;
        }
    }

    private void deleteContact(List<ThirdWeixinContactMapp> list,Set<String> tagIdSetNew, Map<String,String> tagOrgTypeMapping) throws Exception {
        Set<String> orgTypeIdSetNew = new HashSet<>();
        for(String tagId:tagIdSetNew){
            String orgTypeId = tagOrgTypeMapping.get(tagId);
            if(StringUtil.isNotNull(orgTypeId)){
                orgTypeIdSetNew.add(orgTypeId);
            }
        }
        List<ThirdWeixinContactMapp> toDelete = new ArrayList<>();
        for(ThirdWeixinContactMapp mapp: list){
            if(!orgTypeIdSetNew.contains(mapp.getFdOrgTypeId())){
                toDelete.add(mapp);
            }
        }
        for(ThirdWeixinContactMapp mapp: toDelete){
            deleteContact(mapp);
        }
    }

    private void handleContact(ThirdWeixinContact contact, Map<String,String> tagOrgTypeMapping) throws Exception {
        logger.debug("处理客户："+contact.getName()+","+contact.getExternal_userid());
        String deptName = getDeptName(contact);
        if(StringUtil.isNull(deptName)){
            logger.warn("客户企业名称为空，不同步。客户名称："+contact.getName()+"，客户ID："+contact.getExternal_userid());
            return;
        }
        String external_userid = contact.getExternal_userid();
        List<ThirdWeixinContactMapp> list = thirdWeixinContactMappService.findByContactId(external_userid);
        Map<String, ThirdWeixinContactTag> tagsMap = contact.getTagsMap();
        Set<String> tagIdSetNew = tagsMap.keySet();
        for(String tagId:tagIdSetNew){
            ThirdWeixinContactTag contactTag = tagsMap.get(tagId);
            if(!tagOrgTypeMapping.containsKey(tagId)){
                logger.info("标签："+tagId+"，"+contactTag.getTag_name()+" 没做映射，不处理");
                continue;
            }
            ThirdWeixinContactMapp mapp = thirdWeixinContactMappService.findByContactAndOrgType(external_userid,tagOrgTypeMapping.get(tagId));
            if(mapp==null){
                addContact(contact,tagId,contactTag.getTag_name(),tagOrgTypeMapping);
            }else{
                updateContact(contact,tagId,tagOrgTypeMapping,mapp.getFdExternalId());
                updateContactMapp(mapp,tagId,contactTag.getTag_name());
            }
        }
        deleteContact(list, tagIdSetNew, tagOrgTypeMapping);
    }

    private void updateContactMapp(ThirdWeixinContactMapp mapp, String tagId,String tagName) throws Exception {
        mapp.setFdIsDelete(false);
        String tagIdStr = mapp.getFdTagId();
        String tagNameStr = mapp.getFdTagName();
        String[] tagIds = tagIdStr.split(";");
        boolean contains = false;
        for(String _tagId:tagIds){
            if(_tagId.equals(tagId)){
                contains = true;
                break;
            }
        }
        if(!contains){
            tagIdStr += tagId+";";
            tagNameStr += tagName+";";
            mapp.setFdTagId(tagIdStr);
            mapp.setFdTagName(tagNameStr);
        }
        thirdWeixinContactMappService.update(mapp);
    }

    private String buildInBlock(List<String> contactIds){
        String temp = "";
        List<String> temps = new ArrayList<String>();
        for (int i = 0; i < contactIds.size(); i++) {
            if (i > 0 && i % 1000 == 0) {
                temps.add(temp);
                temp = "";
            }
            temp += ",'" + contactIds.get(i) + "'";
        }
        if (StringUtil.isNotNull(temp)) {
            temps.add(temp);
        }
        String inBlock = "";
        for (String in : temps) {
            in = in.substring(1);
            inBlock += " and fdContactUserId not in (" + in + ")";
        }
        return inBlock;
    }

    private void deleteContacts(Set<String> contactIds) throws Exception {
        HQLInfo info = new HQLInfo();
        info.setWhereBlock("1=1 "+buildInBlock(new ArrayList(contactIds)));
        List<ThirdWeixinContactMapp> list = thirdWeixinContactMappService.findList(info);
        for(ThirdWeixinContactMapp mapp:list){
            deleteContact(mapp);
        }
    }

    private Map<String,String> getTagOrgTypeMapping() throws Exception {
        String orgTypeSetting = WeixinWorkConfig.newInstance().getSyncContactOrgTypeSetting();
        return getTagOrgTypeMapping(orgTypeSetting);
    }

    private Map<String,String> getTagOrgTypeMapping(String orgTypeSetting) throws Exception {
        if(StringUtil.isNull(orgTypeSetting)){
            throw new Exception("必须先配置好“组织类型同步设置”");
        }
        logger.debug("orgTypeSetting:"+orgTypeSetting);
        JSONArray orgTypeSettingArray = JSONArray.parseArray(orgTypeSetting);
        Map<String,String> tagOrgTypeMapping = new HashMap<>();
        for(int i=0;i<orgTypeSettingArray.size();i++){
            JSONObject settingObj = orgTypeSettingArray.getJSONObject(i);
            String tagIdStr = settingObj.getString("tagId");
            String[] tagIds = tagIdStr.split(";");
            String orgTypeId = settingObj.getString("tagOrgType");
            for(String tagId:tagIds){
                tagOrgTypeMapping.put(tagId,orgTypeId);
            }
        }
        return tagOrgTypeMapping;
    }

    private ThirdWeixinContact buildThirdWeixinContact(JSONObject result) throws Exception {
        JSONObject external_contact = result.getJSONObject("external_contact");
        ThirdWeixinContact contact = new ThirdWeixinContact(external_contact.getString("external_userid"),
                external_contact.getString("name"),external_contact.getString("position"),
                external_contact.getString("corp_full_name"), external_contact.getString("corp_name"),
                external_contact.getInteger("gender"));
        JSONArray follow_users = result.getJSONArray("follow_user");
        if(follow_users==null){
            return contact;
        }
        //Map<String,String> tagOrgTypeMapping = getTagOrgTypeMapping();
        for(int i=0;i<follow_users.size();i++){
            JSONObject follow_user = follow_users.getJSONObject(i);
            JSONArray tags = follow_user.getJSONArray("tags");
            if(tags==null || tags.isEmpty()){
                continue;
            }
            for(int j=0;j<tags.size();j++){
                JSONObject tag = tags.getJSONObject(j);
                String tagId = tag.getString("tag_id");
                String tagName = tag.getString("tag_name");
                String group_name = tag.getString("group_name");
                Integer type = tag.getInteger("type");
                contact.addTag(new ThirdWeixinContactTag(group_name,tagId,tagName,type));
            }
        }
        return contact;
    }

    private void handleContact(String eventType, String contactId) throws Exception {
        WxworkApiService wxworkApiService = WxworkUtils.getWxworkApiService();
        JSONObject result = wxworkApiService.getContact(contactId);
        if(result.getInteger("errcode")==0){
            ThirdWeixinContact contact = buildThirdWeixinContact(result);
            handleContact(contact,getTagOrgTypeMapping());
        }else{
            if(result.getInteger("errcode")==2 && "del_external_contact".equals(eventType)){

            }
        }
    }

    private void handleTag(String eventType, String tagId) throws Exception {
        Map<String,String> tagOrgTypeMapping = getTagOrgTypeMapping();
        if(!tagOrgTypeMapping.containsKey(tagId)){
            logger.info("没有配置标签 "+ tagId +" 的映射，无需处理");
            return;
        }
        WxworkApiService wxworkApiService = WxworkUtils.getWxworkApiService();
        JSONObject result = wxworkApiService.listCorpTag(null,tagId);
        if(result.getInteger("errcode")==0){
            JSONArray tagGroups = result.getJSONArray("tag_group");
            if(tagGroups==null || tagGroups.isEmpty()){
                throw new Exception("获取标签详情失败，返回信息："+result);
            }
            JSONObject tagGroup = tagGroups.getJSONObject(0);
            JSONArray tags = tagGroup.getJSONArray("tag");
            if(tags==null || tags.isEmpty()){
                throw new Exception("获取标签详情失败，返回信息："+result);
            }
            JSONObject tag = tags.getJSONObject(0);

        }else{
            if(result.getInteger("errcode")==2 && "del_external_contact".equals(eventType)){

            }
        }
    }

    @Override
    public void contactCallback(net.sf.json.JSONObject plainTextJson, ThirdWeixinWorkCallback info)
            throws Exception {
        String eventType = plainTextJson.getString("ChangeType");
        String fdEventTypeTip = null;
        try {
            if ("add_external_contact".equals(eventType)) {
                fdEventTypeTip = "添加企业客户事件";
                String ExternalUserID = plainTextJson.getString("ExternalUserID");
                handleContact(eventType,ExternalUserID);
            } else if ("edit_external_contact".equals(eventType)) {
                fdEventTypeTip = "编辑企业客户事件";
                String ExternalUserID = plainTextJson.getString("ExternalUserID");
                handleContact(eventType,ExternalUserID);
            } else if ("del_external_contact".equals(eventType)) {
                fdEventTypeTip = "删除企业客户事件";
                String ExternalUserID = plainTextJson.getString("ExternalUserID");
                handleContact(eventType,ExternalUserID);
            } else if ("create".equals(eventType)) {
                //无需处理
            } else if ("update".equals(eventType)) {
                fdEventTypeTip = "企业客户标签变更事件";
//                String tagType = plainTextJson.getString("TagType");
//                String tagId = plainTextJson.getString("Id");
//                if("tag".equals(tagType)) {
//                    handleTag(eventType,tagId);
//                }
            } else if ("delete".equals(eventType)) {
                fdEventTypeTip = "企业客户标签删除事件";
                String tagType = plainTextJson.getString("TagType");
                String tagId = plainTextJson.getString("Id");
                if("tag".equals(tagType)){
                    //handleTag(eventType,tagId);
                }
            }
            info.setFdIsSuccess(true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            info.setFdIsSuccess(false);
            info.setFdErrorInfo(e.getMessage());
        }
        info.setFdEventTypeTip(fdEventTypeTip);

    }

    private String getTagName(String tagId) throws Exception {
        WxworkApiService wxworkApiService = WxworkUtils.getWxworkApiService();
        JSONObject result = wxworkApiService.listCorpTag(null, tagId);
        if (result.getInteger("errcode") == 0) {
            JSONArray tagGroups = result.getJSONArray("tag_group");
            if (tagGroups == null || tagGroups.isEmpty()) {
                throw new Exception("获取标签详情失败，返回信息：" + result);
            }
            JSONObject tagGroup = tagGroups.getJSONObject(0);
            JSONArray tags = tagGroup.getJSONArray("tag");
            if (tags == null || tags.isEmpty()) {
                throw new Exception("获取标签详情失败，返回信息：" + result);
            }
            for(int i=0;i<tags.size();i++){
                JSONObject tag = tags.getJSONObject(i);
                if(tagId.equals(tag.getString("id"))) {
                    return tag.getString("name");
                }
            }
        }
        return null;
    }

    @Override
    public String updateOrgTypeSetting() {
        WeixinWorkConfig config = WeixinWorkConfig.newInstance();
        String orgTypeSetting = config.getSyncContactOrgTypeSetting();
        if(StringUtil.isNull(orgTypeSetting)){
            return null;
        }
        try {
            JSONArray array = JSONArray.parseArray(orgTypeSetting);
            for (int i = 0; i < array.size(); i++) {
                JSONObject o = array.getJSONObject(i);
                String tagIdStr = o.getString("tagId");
                String[] tagIds = tagIdStr.split(";");
                String tagNameStr = "";
                for(String tagId:tagIds){
                    String tagName = getTagName(tagId);
                    if(StringUtil.isNotNull(tagName)){
                        tagNameStr += tagName+";";
                    }else{
                        throw new Exception("无法获取到标签名称："+tagId);
                    }
                }
                if(StringUtil.isNotNull(tagNameStr)){
                    o.put("tagName",tagNameStr);
                }
            }
            config.setSyncContactOrgTypeSetting(array.toString());
            config.save();
            return array.toString();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return null;
        }
    }

    private boolean isHasMappingRecord() throws Exception {
        Long count = thirdWeixinContactMappService.getMappRecordCount();
        if(count==0){
            return false;
        }else{
            return true;
        }
    }

    private void updateOldMappTagId(ThirdWeixinContactMapp mapp, String tagId){
        logger.debug("旧的映射去除标签，标签ID："+tagId);
        String[] tagIds = mapp.getFdTagId().split(";");
        String[] tagNames = mapp.getFdTagName().split(";");
        String tagIdStrNew = "";
        String tagNameStrNew = "";
        for(int i=0;i<tagIds.length;i++){
            if(tagIds[i].equals(tagId)){
                continue;
            }
            tagIdStrNew += tagIds[i]+";";
            tagNameStrNew += tagNames[i]+";";
        }
        mapp.setFdTagId(tagIdStrNew);
        mapp.setFdTagName(tagNameStrNew);
    }

    private void updateOldMapp(ThirdWeixinContactMapp mapp, String tagId) throws Exception {
        String tagIdStr = mapp.getFdTagId();
        //只有一个标签，直接删除映射
        if((tagId+";").equals(tagIdStr)){
            logger.debug("映射设置为删除状态，ID："+mapp.getFdId()+"，客户名称："+mapp.getFdContactName()+"，标签ID："+tagId);
            mapp.setFdIsDelete(true);
        }else{
            logger.debug("映射去除旧的ID，ID："+mapp.getFdId()+"，客户名称："+mapp.getFdContactName()+"，标签ID："+tagId);
            updateOldMappTagId(mapp,tagId);
        }
        thirdWeixinContactMappService.update(mapp);
    }

    private void updateNewMapp(ThirdWeixinContactMapp mapp, String tagId, String tagName) throws Exception {
        logger.debug("新映射加上标签，ID："+mapp.getFdId()+"，客户名称："+mapp.getFdContactName()+"，标签ID："+tagId);
        mapp.setFdIsDelete(false);
        String[] tagIds = mapp.getFdTagId().split(";");
        boolean toAdd = true;
        for(int i=0;i<tagIds.length;i++){
            if(tagIds[i].equals(tagId)){
                toAdd = false;
                break;
            }
        }
        if(toAdd==true){
            mapp.setFdTagId(mapp.getFdTagId()+tagId+";");
            mapp.setFdTagName(mapp.getFdTagName()+tagName+";");
        }
        thirdWeixinContactMappService.update(mapp);
    }

    private void updateContactOrgType(String contactId, String newOrgTypeId) throws Exception {
        SysOrgPerson person = (SysOrgPerson)sysOrgPersonService.findByPrimaryKey(contactId);
        String deptName = person.getFdParent().getFdNameOri();
        SysOrgElement dept = findDeptByName(newOrgTypeId,deptName);
        if (dept == null) {
            dept = addExternalDept(newOrgTypeId, deptName);
        }
        person.setFdParent(dept);
        person.setFdIsAvailable(true);
        sysOrgPersonService.update(person);
    }

    private String addContact2OrgType(ThirdWeixinContactMapp mapp, String newOrgTypeId, String tagId) throws Exception {
        logger.debug("新增客户，客户ID："+mapp.getFdContactUserId()+"，客户名称："+mapp.getFdContactName()+"，组织类型："+newOrgTypeId);
        SysOrgPerson personOld = (SysOrgPerson)sysOrgPersonService.findByPrimaryKey(mapp.getFdExternalId());
        String deptName = personOld.getFdParent().getFdNameOri();
        SysOrgElement dept = findDeptByName(newOrgTypeId,deptName);
        if (dept == null) {
            dept = addExternalDept(newOrgTypeId, deptName);
        }
        SysOrgPerson person = new SysOrgPerson();
        person.setFdName(personOld.getFdName());
        person.setFdLoginName(mapp.getFdContactUserId().replaceAll("-","") + "_" + newOrgTypeId);
        person.setFdNickName(personOld.getFdNickName());
        person.setFdIsExternal(true);
        person.setFdSex(personOld.getFdSex());
        person.setFdParent(dept);
        return sysOrgPersonService.add(person);
    }

    private void addThirdWeixinContactMapp(ThirdWeixinContactMapp oldMapp, String newOrgTypeId, String tagId,String tagName, String personId) throws Exception {
        logger.debug("新增映射，客户ID："+oldMapp.getFdContactUserId()+"，客户名称："+oldMapp.getFdContactName()+"，组织类型："+newOrgTypeId+"，标签ID："+tagId+"，标签名称："+tagName+"，EKP客户ID："+personId);
        //新增一个mapp
        ThirdWeixinContactMapp mappNew = new ThirdWeixinContactMapp();
        mappNew.setFdIsDelete(false);
        mappNew.setFdOrgTypeId(newOrgTypeId);
        mappNew.setFdTagId(tagId+";");
        mappNew.setFdContactUserId(oldMapp.getFdContactUserId());
        mappNew.setFdExternalId(personId);
        mappNew.setFdTagName(tagName+";");
        mappNew.setFdContactName(oldMapp.getFdContactName());
        mappNew.setFdCorpId(oldMapp.getFdCorpId());
        thirdWeixinContactMappService.add(mappNew);
    }

    private void updateContact2NewOrgType(ThirdWeixinContactMapp mapp, String newOrgTypeId, String tagId,String tagName) throws Exception {
        logger.debug("客户迁移到新组织类型下，或者在新组织类型下新增一个客户，客户名称："+mapp.getFdContactName()+"，标签ID："+tagId+"，新增组织类型："+newOrgTypeId);
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            String tagIdStr = mapp.getFdTagId();
            //只有一个标签，直接修改映射标签
            if ((tagId + ";").equals(tagIdStr)) {
                logger.debug("迁移客户到新组织类型下");
                mapp.setFdIsDelete(false);
                mapp.setFdOrgTypeId(newOrgTypeId);
                thirdWeixinContactMappService.update(mapp);
                //修改ekp客户的组织类型
                updateContactOrgType(mapp.getFdExternalId(), newOrgTypeId);
            } else {
                //旧的映射移除tagId
                updateOldMappTagId(mapp, tagId);
                thirdWeixinContactMappService.update(mapp);
                //新增客户
                String personId = addContact2OrgType(mapp, newOrgTypeId, tagId);
                addThirdWeixinContactMapp(mapp, newOrgTypeId, tagId, tagName, personId);
            }
            TransactionUtils.getTransactionManager().commit(status);
        }catch (Exception e){
            if(status!=null){
                TransactionUtils.getTransactionManager().rollback(status);
            }
            logger.error(e.getMessage(),e);
            throw e;
        }

    }

    private String getTagName2(String tagIdStr, String tagNameStr, String tagId){
        String[] tagIds = tagIdStr.split(";");
        String[] tagNames = tagNameStr.split(";");
        for(int i=0;i<tagIds.length;i++) {
            if(tagId.equals(tagIds[i])){
                return tagNames[i];
            }
        }
        return null;
    }

    /**
     * 当新的组织类型下已经存在该客户时，进行映射合并
     * @param oldMapp 旧组织类型对应的映射
     * @param newMapp 新组织类型对应的映射
     * @param tagId   标签ID
     */
    private void mergerMapp(ThirdWeixinContactMapp oldMapp, ThirdWeixinContactMapp newMapp, String tagId, String tagName) throws Exception {
        logger.debug("新的组织类型下已经存在该客户，进行映射合并，客户ID："+oldMapp.getFdExternalId()+"，新组织ID："+newMapp.getFdOrgTypeId());
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            updateOldMapp(oldMapp, tagId);
            updateNewMapp(newMapp,tagId,tagName);
            logger.debug("旧组织下的客户置为无效，客户名称："+oldMapp.getFdContactName()+"，标签ID："+tagId+"，EKP客户ID："+oldMapp.getFdExternalId());
            SysOrgPerson person = (SysOrgPerson)sysOrgPersonService.findByPrimaryKey(oldMapp.getFdExternalId());
            person.setFdIsAvailable(false);
            sysOrgPersonService.update(person);

            logger.debug("新组织下的客户置为有效，客户名称："+newMapp.getFdContactName()+"，标签ID："+tagId+"，EKP客户ID："+newMapp.getFdExternalId());
            person = (SysOrgPerson)sysOrgPersonService.findByPrimaryKey(newMapp.getFdExternalId());
            person.setFdIsAvailable(true);
            sysOrgPersonService.update(person);
            TransactionUtils.getTransactionManager().commit(status);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            if(status!=null){
                TransactionUtils.getTransactionManager().rollback(status);
            }
            throw e;
        }
    }


    private void changeContactOrgType(String oldOrgTypeId,String newOrgTypeId, String tagId,String tagName) throws Exception {
        List<ThirdWeixinContactMapp> list = (List<ThirdWeixinContactMapp>)thirdWeixinContactMappService.findContactMapp(oldOrgTypeId,tagId);
        if(list==null || list.isEmpty()){
            return;
        }
        logger.debug("标签ID："+tagId+"，标签名称："+tagName+"，该标签在组织类型 "+oldOrgTypeId+" 下的客户数据需要迁移到 "+newOrgTypeId);
        for(ThirdWeixinContactMapp mapp:list){
            String contactId = mapp.getFdContactUserId();
            ThirdWeixinContactMapp mappInNewOrgtype = thirdWeixinContactMappService.findByContactAndOrgType(contactId,newOrgTypeId);
            if(mappInNewOrgtype!=null){
                //如果在新组织类型下已经有这个客户，则合并；
                mergerMapp(mapp,mappInNewOrgtype,tagId,tagName);
            }else{
                //如果在新组织类型下已经没有这个客户，则把原来的客户迁移到新的组织类型下，或者新增一个客户；
                updateContact2NewOrgType(mapp,newOrgTypeId,tagId,tagName);
            }
        }
    }

    private Map<String,String> getTagNameMap(String orgTypeSetting){
        Map<String,String> tagNameMapping = new HashMap<>();
        if(StringUtil.isNull(orgTypeSetting)){
            return tagNameMapping;
        }
        JSONArray orgTypeSettingArray = JSONArray.parseArray(orgTypeSetting);
        for(int i=0;i<orgTypeSettingArray.size();i++){
            JSONObject settingObj = orgTypeSettingArray.getJSONObject(i);
            String tagIdStr = settingObj.getString("tagId");
            String[] tagIds = tagIdStr.split(";");
            String tagNameStr = settingObj.getString("tagName");
            String[] tagNames = tagNameStr.split(";");
            for(int j=0;j<tagIds.length;j++){
                tagNameMapping.put(tagIds[j],tagNames[j]);
            }
        }
        return tagNameMapping;
    }

    private Map<String,String> getTagNameMap(String oldOrgTypeSetting, String newOrgTypeSetting){
        Map<String,String> oldTagNameMap = getTagNameMap(oldOrgTypeSetting);
        Map<String,String> newTagNameMap = getTagNameMap(newOrgTypeSetting);
        oldTagNameMap.putAll(newTagNameMap);
        return oldTagNameMap;
    }

    @Override
    public void changeOrgTypeMapping(String newOrgTypeSetting) throws Exception {
        WeixinWorkConfig config = WeixinWorkConfig.newInstance();
        String oldOrgTypeSetting = config.getSyncContactOrgTypeSetting();
        if(StringUtil.isNull(oldOrgTypeSetting) || StringUtil.isNull(newOrgTypeSetting)){
            return;
        }
        if(!isHasMappingRecord()){
            return;
        }
        Map<String,String> oldMappingMap = getTagOrgTypeMapping(oldOrgTypeSetting);
        Map<String,String> newMappingMap = getTagOrgTypeMapping(newOrgTypeSetting);
        Map<String,String> tagNameMap = getTagNameMap(oldOrgTypeSetting,newOrgTypeSetting);
        for(String tagId:oldMappingMap.keySet()){
            String oldOrgType = oldMappingMap.get(tagId);
            if(!newMappingMap.containsKey(tagId)){
                logger.info("新的映射配置中没有 "+tagId);
                continue;
            }
            String newOrgType = newMappingMap.get(tagId);
            //组织映射类型变了
            if(!oldOrgType.equals(newOrgType)){
                changeContactOrgType(oldOrgType,newOrgType,tagId,tagNameMap.get(tagId));
            }
        }
    }

    private String getDeptName(ThirdWeixinContact contact){
        String deptName = contact.getCorp_full_name();
        if(StringUtil.isNull(deptName)){
            deptName = contact.getCorp_name();
        }
        if(StringUtil.isNull(deptName)){
            deptName = "微信客户";
        }
        return deptName;
    }
}
