package com.landray.kmss.third.weixin.work.oms;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.organization.model.*;
import com.landray.kmss.sys.organization.service.*;
import com.landray.kmss.sys.organization.util.PasswordUtil;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.weixin.work.api.CorpGroupAppShareInfo;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinCgDeptMapp;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinCgUserMapp;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.model.api.WxDepart;
import com.landray.kmss.third.weixin.work.model.api.WxUser;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinCgDeptMappService;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinCgUserMappService;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 下游组织同步器，增对单个组织进行同步
 */
public class CorpgroupOrg2EkpSynchronizer {

    private static final Log logger = LogFactory
            .getLog(CorpgroupOrg2EkpSynchronizer.class);

    private IThirdWeixinCgDeptMappService thirdWeixinCgDeptMappService;

    private IThirdWeixinCgUserMappService thirdWeixinCgUserMappService;

    public IThirdWeixinCgDeptMappService getThirdWeixinCgDeptMappService() {
        if(thirdWeixinCgDeptMappService==null){
            thirdWeixinCgDeptMappService = (IThirdWeixinCgDeptMappService) SpringBeanUtil.getBean("thirdWeixinCgDeptMappService");
        }
        return thirdWeixinCgDeptMappService;
    }

    public IThirdWeixinCgUserMappService getThirdWeixinCgUserMappService() {
        if(thirdWeixinCgUserMappService==null){
            thirdWeixinCgUserMappService = (IThirdWeixinCgUserMappService) SpringBeanUtil.getBean("thirdWeixinCgUserMappService");
        }
        return thirdWeixinCgUserMappService;
    }

    private ISysOrgElementService sysOrgElementService;
    public ISysOrgElementService getSysOrgElementService(){
        if(sysOrgElementService==null){
            sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
        }
        return sysOrgElementService;
    }

    private ISysOrgDeptService sysOrgDeptService;
    public ISysOrgDeptService getSysOrgDeptService(){
        if(sysOrgDeptService==null){
            sysOrgDeptService = (ISysOrgDeptService) SpringBeanUtil.getBean("sysOrgDeptService");
        }
        return sysOrgDeptService;
    }

    private ISysOrgPersonService sysOrgPersonService;
    public ISysOrgPersonService getSysOrgPersonService(){
        if(sysOrgPersonService==null){
            sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
        }
        return sysOrgPersonService;
    }

    private ISysOrgPostService sysOrgPostService;
    public ISysOrgPostService getSysOrgPostService(){
        if(sysOrgPostService==null){
            sysOrgPostService = (ISysOrgPostService) SpringBeanUtil.getBean("sysOrgPostService");
        }
        return sysOrgPostService;
    }

    private IKmssPasswordEncoder passwordEncoder;
    public IKmssPasswordEncoder getPasswordEncoder() {
        if(passwordEncoder==null){
            passwordEncoder = (IKmssPasswordEncoder) SpringBeanUtil.getBean("passwordEncoder");
        }
        return passwordEncoder;
    }
    //下游组织相关信息
    private CorpGroupAppShareInfo shareInfo = null;
    private SysQuartzJobContext jobContext = null;
    //人员部门同步类型（主部门、一人多部门）
    private String personDeptType = null;
    //从企业微信获取到的部门列表
    private List<WxDepart> departsList = null;
    //部门映射
    private Map<String,DeptMapperVo> deptMap = null;

    public CorpgroupOrg2EkpSynchronizer(CorpGroupAppShareInfo shareInfo, SysQuartzJobContext jobContext){
        this.shareInfo = shareInfo;
        this.jobContext = jobContext;
        WeixinWorkConfig config = WeixinWorkConfig.newInstance();
        personDeptType = config.getDataMap().get("corpGroup2ekp.department");
    }

    /**
     * 同步单个下游组织
     * @param
     * @throws Exception
     */
    public void doSynchro() throws Exception {
        Long start = System.currentTimeMillis();
        jobContext.logMessage("");
        jobContext.logMessage("开始同步 【"+shareInfo.getCorpName()+"】 的组织数据");
        WxworkApiService wxworkApiService = WxworkUtils.getWxworkApiService();
        //获取部门列表
        departsList = wxworkApiService
                .departGet(null,shareInfo.getCorpId()+"#"+shareInfo.getAgentId(),1);
//        if (departsList == null || departsList.size() == 0) {
//            throw new Exception("获取部门列表异常 id：" + 1);
//        }
        jobContext.logMessage("同步部门数："+departsList.size());
        //同步部门
        syncDepts();
        //同步用户（包括删除）
        syncPersons();
        //更新部门负责人信息
        syncDeptLeader();
        //处理删除的部门
        deleteDepts();
        jobContext.logMessage("【"+shareInfo.getCorpName() + "】 同步完成，耗时："+(System.currentTimeMillis()-start)+"毫秒");
        jobContext.logMessage("");
    }


    /**
     * 同步所有人员
     * @throws Exception
     */
    private void syncPersons() throws Exception {
        logger.debug("开始同步人员");
        Map<String, UserMapperVo> usersMap = buildUsersMap();
        List<Object[]> deptMappingList = getThirdWeixinCgDeptMappService().findValue("fdWxDeptId,fdEkpId,fdEkpPostId","fdCorpId='" + shareInfo.getCorpId() + "'", null);
        deptMap = new HashMap<>();
        for(Object[] params:deptMappingList){
            deptMap.put((String)params[0],new DeptMapperVo((String)params[1],(String)params[2],(String)params[0]));
        }
        //同步部门下用户
        Set<String> wxUserIdSet = syncPersons(usersMap);
        //同步顶级用户
        syncRootPersons(wxUserIdSet,usersMap);
        jobContext.logMessage("同步用户数："+wxUserIdSet.size());
        //处理删除的人员
        deleteUsers(wxUserIdSet);
    }

    /**
     * 新增部门映射
     * @param wxDept
     * @param ekpId
     * @param postId
     * @return
     * @throws Exception
     */
    private ThirdWeixinCgDeptMapp addDeptMapp(WxDepart wxDept, String ekpId, String postId) throws Exception {
        logger.debug("新增部门映射，微信部门ID："+wxDept.getId()+"，ekp部门ID："+ekpId+"，部门名称："+wxDept.getName());
        ThirdWeixinCgDeptMapp mapp = new ThirdWeixinCgDeptMapp();
        mapp.setDocAlterTime(new Date());
        mapp.setDocAlterTime(new Date());
        mapp.setFdDeptName(wxDept.getName());
        mapp.setFdEkpId(ekpId);
        mapp.setFdEkpPostId(postId);
        mapp.setFdIsAvailable(true);
        mapp.setFdCorpId(shareInfo.getCorpId());
        mapp.setFdWxDeptId(wxDept.getId()+"");
        getThirdWeixinCgDeptMappService().add(mapp);
        return mapp;
    }

    /**
     * 新增人员映射
     * @param wxUser
     * @param ekpId
     * @return
     * @throws Exception
     */
    private String addUserMapp(WxUser wxUser, String ekpId) throws Exception {
        logger.debug("新增用户映射，微信用户ID："+wxUser.getUserId()+"，EKP用户ID："+ekpId+"，名称："+wxUser.getName());
        ThirdWeixinCgUserMapp mapp = new ThirdWeixinCgUserMapp();
        mapp.setDocCreateTime(new Date());
        mapp.setDocAlterTime(new Date());
        mapp.setFdUserId(wxUser.getUserId());
        mapp.setFdEkpId(ekpId);
        //mapp.setFdIsAvailable(true);
        mapp.setFdCorpId(shareInfo.getCorpId());
        mapp.setFdUserName(wxUser.getName());
        mapp.setFdOpenUserId(wxUser.getOpenUserid());
        Integer status = wxUser.getStatus();
        if(status==2 || status==5){
            mapp.setFdIsAvailable(false);
        }else{
            mapp.setFdIsAvailable(true);
        }
        return getThirdWeixinCgUserMappService().add(mapp);
    }

    /**
     * 新增部门
     * @param wxDept
     * @param deptMappingMap
     * @throws Exception
     */
    private void handleAddDept(WxDepart wxDept, Map<String,ThirdWeixinCgDeptMapp> deptMappingMap) throws Exception {
        Long deptId = wxDept.getId();
        ThirdWeixinCgDeptMapp mapp = deptMappingMap.get(deptId+"");
        if(mapp==null){
            //创建部门
            SysOrgDept dept = addExternalDept(wxDept);
            SysOrgPost post = null;
            // 启用了一人多部门时，需在部门下建一个岗位
            if("muilDept".equals(personDeptType)){
                post = addExternalPost(dept);
            }
            //添加部门映射
            mapp = addDeptMapp(wxDept,dept.getFdId(),post==null?null:post.getFdId());
            deptMappingMap.put(wxDept.getId()+"",mapp);
        }
    }

    /**
     * 更新部门
     * @param wxDept
     * @param deptMappingMap
     * @throws Exception
     */
    private void handleUpdateDept(WxDepart wxDept, Map<String,ThirdWeixinCgDeptMapp> deptMappingMap) throws Exception {
        Long deptId = wxDept.getId();
        ThirdWeixinCgDeptMapp mapp = deptMappingMap.get(deptId+"");
        if(mapp!=null){
            //更新部门详情
            SysOrgDept dept = updateExternalDept(mapp,wxDept,deptMappingMap);
            //更新岗位
            if("muilDept".equals(personDeptType)){
                updateExternalPost(mapp,dept,deptMappingMap);
            }
            //更新部门映射
            updateDeptMapp(mapp,wxDept);
        }
    }

    /**
     * 更新部门映射
     * @param mapp
     * @param wxDept
     * @throws Exception
     */
    private void updateDeptMapp(ThirdWeixinCgDeptMapp mapp,WxDepart wxDept) throws Exception {
        if(mapp.getFdIsAvailable()!=true || !wxDept.getName().equals(mapp.getFdDeptName())){
            mapp.setFdIsAvailable(true);
            mapp.setFdDeptName(wxDept.getName());
            getThirdWeixinCgDeptMappService().update(mapp);
        }
    }

    /**
     * 更新人员映射
     * @param vo
     * @param wxUser
     * @throws Exception
     */
    private void updateUserMapp(UserMapperVo vo,WxUser wxUser) throws Exception {
        if(vo.getAvailable()!=true || !wxUser.getName().equals(vo.getUserName())){
            ThirdWeixinCgUserMapp mapp = (ThirdWeixinCgUserMapp)getThirdWeixinCgUserMappService().findByPrimaryKey(vo.getMappId(),null,true);
            Integer status = wxUser.getStatus();
            if(status==2 || status==5){
                mapp.setFdIsAvailable(false);
            }else {
                mapp.setFdIsAvailable(true);
            }
            mapp.setFdUserName(wxUser.getName());
            getThirdWeixinCgUserMappService().update(mapp);
        }
    }

    /**
     * 获取生态组织类型
     * @return
     * @throws Exception
     */
    private SysOrgElement getOrgType() throws Exception {
        WeixinWorkConfig config = WeixinWorkConfig.newInstance();
        String orgTypeId = config.getSyncCorpGroupOrgType();
        return (SysOrgElement)getSysOrgElementService().findByPrimaryKey(orgTypeId,null,true);
    }

    /**
     * 更新部门信息
     * @param mapp
     * @param wxDept
     * @param deptMappingMap
     * @return
     * @throws Exception
     */
    private SysOrgDept updateExternalDept(ThirdWeixinCgDeptMapp mapp, WxDepart wxDept, Map<String,ThirdWeixinCgDeptMapp> deptMappingMap) throws Exception {
        //更新部门
        SysOrgDept dept = (SysOrgDept)getSysOrgDeptService().findByPrimaryKey(mapp.getFdEkpId());
        if(dept==null){
            throw new Exception("找不到部门，部门ID："+mapp.getFdEkpId()+"，部门名称："+wxDept.getName());
        }
        logger.debug("更新部门详情，微信部门ID："+wxDept.getId()+"，ekp部门ID："+mapp.getFdEkpId()+"部门名称："+wxDept.getName());
        dept.setFdName(wxDept.getName());
        dept.setFdOrder(wxDept.getOrder()==null?null:wxDept.getOrder().intValue());
        Long wxParentId = wxDept.getParentid();
        if(wxParentId==null){
            dept.setFdParent(getOrgType());
        }else{
            ThirdWeixinCgDeptMapp parentMapp = deptMappingMap.get(wxParentId+"");
            if(parentMapp==null){
                dept.setFdParent(getOrgType());
            }else{
                dept.setFdParent((SysOrgDept)getSysOrgDeptService().findByPrimaryKey(parentMapp.getFdEkpId()));
            }
        }
        getSysOrgDeptService().update(dept);
        return dept;
    }

    /**
     * 更新岗位
     * @param mapp
     * @param dept
     * @param deptMappingMap
     * @throws Exception
     */
    private void updateExternalPost(ThirdWeixinCgDeptMapp mapp, SysOrgDept dept, Map<String,ThirdWeixinCgDeptMapp> deptMappingMap) throws Exception {
        if(StringUtil.isNull(mapp.getFdEkpPostId())){
            SysOrgPost post = addExternalPost(dept);
            mapp.setFdEkpPostId(post.getFdId());
            getThirdWeixinCgDeptMappService().update(mapp);
            deptMappingMap.put(mapp.getFdWxDeptId(),mapp);
            return;
        }
        //更新部门
        SysOrgPost post = (SysOrgPost)getSysOrgPostService().findByPrimaryKey(mapp.getFdEkpPostId());
        if(post==null){
            throw new Exception("找不到岗位，岗位ID："+mapp.getFdEkpPostId()+"，岗位名称："+dept.getFdName()+"_岗位");
        }
        logger.debug("更新岗位详情，岗位ID："+mapp.getFdEkpPostId()+"岗位名称："+dept.getFdName()+"_岗位");
        post.setFdName(dept.getFdName()+"_岗位");
        post.setFdIsAvailable(dept.getFdIsAvailable());
        post.setFdParent(dept);
        getSysOrgPostService().update(post);
    }

    /**
     * 构建人员映射
     * @return
     * @throws Exception
     */
    private Map<String, UserMapperVo> buildUsersMap() throws Exception {
        List list = getThirdWeixinCgUserMappService().findValue("fdEkpId,fdUserId,fdUserName,fdIsAvailable,fdId","fdCorpId='"+shareInfo.getCorpId()+"'",null);
        Map<String, UserMapperVo> usersMap = new HashMap<>();
        for(Object o:list){
            Object[] fields = (Object[]) o;
            UserMapperVo vo = new UserMapperVo((String)fields[0],(String)fields[1],(String)fields[2],(Boolean)fields[3],(String)fields[4]);
            usersMap.put((String)fields[1],vo);
        }
        return usersMap;
    }

    /**
     * 同步根组织下的人员
     * @param wxUserIdSet
     * @param usersMap
     * @throws Exception
     */
    private void syncRootPersons(Set<String> wxUserIdSet, Map<String, UserMapperVo> usersMap) throws Exception {
        logger.debug("同步顶层用户");
        WxworkApiService wxworkApiService = WxworkUtils.getWxworkApiService();
        JSONArray array = wxworkApiService.getAgentAllowUsers(shareInfo.getCorpId(), shareInfo.getAgentId());
        if(array==null || array.isEmpty()){
            return;
        }
        logger.debug("顶层用户列表："+array.toString());
        for(int i=0;i<array.size();i++){
            JSONObject object = array.getJSONObject(i);
            String userid = object.getString("userid");
            if(wxUserIdSet.contains(userid)){
                continue;
            }
            String result = wxworkApiService.userGet(userid,shareInfo.getCorpId()+"#"+shareInfo.getAgentId(),1);
            JSONObject resultObj = JSONObject.parseObject(result);
            Integer errcode = resultObj.getInteger("errcode");
            if(errcode!=0){
                logger.error("获取用户信息失败，userid:"+userid+"，返回信息："+result);
                jobContext.logError("获取用户信息失败，userid:"+userid+"，返回信息："+result);
                continue;
            }
            WxUser user = JSONObject.parseObject(result, WxUser.class);
            wxUserIdSet.add(user.getUserId());
            syncPerson(user,usersMap);
        }
    }

    /**
     * 接口没有返回的用户，需要置为无效
     * @param wxUserIdList
     * @throws Exception
     */
    private void deleteUsers(Set<String> wxUserIdList) throws Exception {
        if(wxUserIdList==null || wxUserIdList.isEmpty()){
            logger.info("用户列表为空，不处理删除操作");
            return;
        }
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo
                .setWhereBlock("fdCorpId = :corpId and "
                        + buildLogicNotIN(
                        "fdUserId", new ArrayList<>(wxUserIdList)
                ));
        hqlInfo.setParameter("corpId",shareInfo.getCorpId());
        List<ThirdWeixinCgUserMapp> deletingList = getThirdWeixinCgUserMappService()
                .findList(hqlInfo);
        if(deletingList==null || deletingList.isEmpty()){
            return;
        }
        for(ThirdWeixinCgUserMapp mapp:deletingList){
            SysOrgElement element = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(mapp.getFdEkpId());
            element.setFdIsAvailable(false);
            getSysOrgElementService().update(element);
            mapp.setFdIsAvailable(false);
            getThirdWeixinCgUserMappService().update(mapp);
        }
    }

    /**
     * 接口没有返回的部门，需要置为无效
     * @throws Exception
     */
    private void deleteDepts() throws Exception {
        if(departsList==null || departsList.isEmpty()) {
            logger.info("部门列表为空，不处理删除操作");
            return;
        }
        List<String> wxDeptIds = new ArrayList<>();
        for(WxDepart depart:departsList){
            wxDeptIds.add(depart.getId()+"");
        }
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo
                .setWhereBlock("fdCorpId = :corpId and "
                        + buildLogicNotIN(
                        "fdWxDeptId",
                        wxDeptIds));
        hqlInfo.setParameter("corpId",shareInfo.getCorpId());
        List<ThirdWeixinCgDeptMapp> deletingList = getThirdWeixinCgDeptMappService()
                .findList(hqlInfo);
        if(deletingList==null || deletingList.isEmpty()){
            return;
        }
        for(ThirdWeixinCgDeptMapp mapp:deletingList){
            SysOrgElement element = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(mapp.getFdEkpId());
            element.setFdIsAvailable(false);
            getSysOrgElementService().update(element);
            if(StringUtil.isNotNull(mapp.getFdEkpPostId())){
                SysOrgElement post = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(mapp.getFdEkpPostId());
                post.setFdIsAvailable(false);
                getSysOrgElementService().update(post);
            }
            mapp.setFdIsAvailable(false);
            getThirdWeixinCgDeptMappService().update(mapp);
        }
    }

    public static <V> String buildLogicNotIN(String item, List<V> valueList) {
        List<V> valueListCopy = new ArrayList<>();
        if(valueList.isEmpty()) {
            valueListCopy.add(null);
        } else {
            valueListCopy.addAll(valueList);
        }
        int n = (valueListCopy.size() - 1) / 1000;
        StringBuffer rtnStr = new StringBuffer();
        Object obj = valueListCopy.get(0);
        boolean isString = false;
        if (obj instanceof Character || obj instanceof String) {
            isString = true;
        }
        String tmpStr;
        for (int i = 0; i <= n; i++) {
            int size = i == n ? valueListCopy.size() : (i + 1) * 1000;
            if (i > 0) {
                rtnStr.append(" and ");
            }
            rtnStr.append(item + " not in (");
            if (isString) {
                StringBuffer tmpBuf = new StringBuffer();
                for (int j = i * 1000; j < size; j++) {
                    tmpStr = valueListCopy.get(j).toString().replaceAll("'", "''");
                    tmpBuf.append(",'").append(tmpStr).append("'");
                }
                tmpStr = tmpBuf.substring(1);
            } else {
                tmpStr = valueListCopy.subList(i * 1000, size).toString();
                tmpStr = tmpStr.substring(1, tmpStr.length() - 1);
            }
            rtnStr.append(tmpStr);
            rtnStr.append(")");
        }
        if (n > 0) {
            return "(" + rtnStr.toString() + ")";
        } else {
            return rtnStr.toString();
        }
    }

    /**
     * 同步部门数据
     * @throws Exception
     */
    private void syncDepts() throws Exception{
        logger.debug("开始同步部门");
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            List<ThirdWeixinCgDeptMapp> deptMappings = getThirdWeixinCgDeptMappService().findList("fdCorpId='" + shareInfo.getCorpId() + "'", null);
            Map<String, ThirdWeixinCgDeptMapp> deptMappingMap = new HashMap<>();
            for (ThirdWeixinCgDeptMapp mapp : deptMappings) {
                deptMappingMap.put(mapp.getFdWxDeptId(), mapp);
            }
            logger.debug("开始处理新增部门");
            //先处理新增的部门
            for (WxDepart wxDept : departsList) {
                handleAddDept(wxDept, deptMappingMap);
            }
            logger.debug("开始更新部门详情");
            //更新部门详情
            for (WxDepart wxDept : departsList) {
                handleUpdateDept(wxDept, deptMappingMap);
            }
            TransactionUtils.getTransactionManager().commit(status);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            jobContext.logError(e.getMessage(),e);
            if(status!=null){
                TransactionUtils.getTransactionManager().rollback(status);
            }
            throw e;
        }
    }

    /**
     * 同步部门下人员
     * @throws Exception
     */
    private Set<String> syncPersons(Map<String, UserMapperVo> usersMap) throws Exception {
        Set<String> wxUserIdSet = new HashSet<>();
        // 同步当前部门的人员
        for (WxDepart wxDept : departsList) {
            logger.debug("同步部门 "+wxDept.getName()+" 下的人员");
            syncPersons(wxDept.getId(), usersMap, wxUserIdSet);
        }
        return wxUserIdSet;
    }

    /**
     * 同步单个部门下的人员
     * @param wxDeptId 当前部门ID
     * @param usersMap 用户映射
     * @throws Exception
     */
    private void syncPersons(Long wxDeptId, Map<String, UserMapperVo> usersMap, Set<String> wxUserIdSet) throws Exception {
        WxworkApiService wxworkApiService = WxworkUtils.getWxworkApiService();
        List<WxUser> wxUsers = wxworkApiService.userList(wxDeptId,false,null,shareInfo.getCorpId()+"#"+shareInfo.getAgentId(), 1);
        if(wxUsers==null){
            return;
        }
        for(WxUser wxUser:wxUsers){
            syncPerson(wxUser,usersMap);
            wxUserIdSet.add(wxUser.getUserId());
        }
    }

    /**
     * 同步用户
     * @param wxUser
     * @param usersMap
     * @throws Exception
     */
    private void syncPerson(WxUser wxUser, Map<String, UserMapperVo> usersMap) throws Exception {
        logger.debug("同步用户，用户ID："+wxUser.getUserId()+"，用户名称："+wxUser.getUserId());
        UserMapperVo vo = usersMap.get(wxUser.getUserId());
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            if (vo == null) {
                //新建用户
                String ekpId = addExternalPerson(wxUser);
                //新建映射
                addUserMapp(wxUser, ekpId);
                usersMap.put(wxUser.getUserId(), new UserMapperVo(ekpId, wxUser.getUserId(), null, null, null));
            } else {
                //更新用户
                updateExternalPerson(wxUser, vo);
                //更新映射
                updateUserMapp(vo, wxUser);
            }
            TransactionUtils.getTransactionManager().commit(status);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            if(vo==null){
                jobContext.logError("新建用户失败，用户ID："+wxUser.getUserId()+"，用户名称："+wxUser.getUserId()+"，错误信息："+e.getMessage());
            }else{
                jobContext.logError("更新用户失败，用户ID："+wxUser.getUserId()+"，用户名称："+wxUser.getUserId()+"，错误信息："+e.getMessage());
            }
            if(status!=null){
                TransactionUtils.getTransactionManager().rollback(status);
            }
        }
    }

    /**
     * 新增部门
     * @param wxDepart
     * @return
     * @throws Exception
     */
    private SysOrgDept addExternalDept(WxDepart wxDepart) throws Exception {
        logger.debug("新增部门，name:"+wxDepart.getName()+"，id:"+wxDepart.getId());
        SysOrgDept dept = new SysOrgDept();
        dept.setFdName(wxDepart.getName());
        dept.setFdIsExternal(true);
        //dept.setFdParent((SysOrgElement)sysOrgElementService.findByPrimaryKey(parentId));
        setRange(dept);
        dept.setFdOrder(wxDepart.getOrder()==null?null:wxDepart.getOrder().intValue());
        getSysOrgDeptService().add(dept);
        return dept;
    }

    /**
     * 新增岗位
     * @param dept
     * @return
     * @throws Exception
     */
    private SysOrgPost addExternalPost(SysOrgDept dept) throws Exception {
        logger.debug("新增岗位，name:"+dept.getFdName()+"，id:"+dept.getFdId());
        SysOrgPost post = new SysOrgPost();
        post.setFdName(dept.getFdName()+"_岗位");
        post.setFdIsExternal(true);
        post.setFdParent(dept);
        post.setFdIsExternal(dept.getFdIsAbandon());
        getSysOrgPostService().add(post);
        return post;
    }

    /**
     * 新增用户
     * @param wxUser
     * @return
     * @throws Exception
     */
    private String addExternalPerson(WxUser wxUser) throws Exception {
        logger.debug("新增人员，name:"+wxUser.getName()+"，id:"+wxUser.getUserId());
        SysOrgPerson person = new SysOrgPerson();
        person.setFdIsExternal(true);
        person.setFdIsAvailable(true);
        setPersonProps(person, wxUser, true);
        return getSysOrgPersonService().add(person);
    }

    /**
     * 更新用户
     * @param wxUser
     * @param vo
     * @throws Exception
     */
    private void updateExternalPerson(WxUser wxUser, UserMapperVo vo) throws Exception {
        logger.debug("更新人员，name:"+wxUser.getName()+"，id:"+wxUser.getUserId());
        String ekpId = vo.getEkpId();
        SysOrgPerson person = (SysOrgPerson)getSysOrgPersonService().findByPrimaryKey(ekpId,null,true);
        if(person==null){
            jobContext.logError("找不到人员，人员ID："+vo.getEkpId()+"，人员名称："+wxUser.getName());
            logger.error("找不到人员，人员ID："+vo.getEkpId()+"，人员名称："+wxUser.getName());
            return ;
        }
        person.setFdIsExternal(true);
        person.setFdIsAvailable(true);
        setPersonProps(person, wxUser, false);
        getSysOrgPersonService().update(person);
    }

    private void setRange(SysOrgElement sysOrgElement) throws Exception {
        SysOrgElementRange range = new SysOrgElementRange();
        // 默认值
        range.setFdIsOpenLimit(false);
        range.setFdViewType(1);
        range.setFdElement(sysOrgElement);
        sysOrgElement.setFdRange(range);
    }

    /**
     * 获取属性值
     * @param wxUser 用户对象
     * @param isAdd 是否新增
     * @param synWayProp 同步方式
     * @param synValueProp 同步值的字段
     * @return
     */
    private String getPropValue(WxUser wxUser, boolean isAdd, String synWayProp, String synValueProp) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        WeixinWorkConfig config = WeixinWorkConfig.newInstance();
        String synWay = config.getDataMap().get(synWayProp);
        if(StringUtil.isNotNull(synWay)){
            if ("syn".equalsIgnoreCase(synWay)
                    || (isAdd && "addSyn".equalsIgnoreCase(synWay))) {
                String synValuePropValue = config.getDataMap().get(synValueProp);
                String value = null;
                if(StringUtil.isNotNull(synValuePropValue)) {
                    Object valueObj = PropertyUtils.getProperty(wxUser, synValuePropValue);
                    value = valueObj==null?null:valueObj.toString();
                    //value = (String) PropertyUtils.getProperty(wxUser, synValuePropValue);
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

    /**
     * {
     *             "userid": "woRUHiEQAAXyY5MWtKFcbFQPhDClBFYg",
     *             "name": "cztest6002",
     *             "department": [
     *                 17
     *             ],
     *             "gender": "0",
     *             "avatar": "https://rescdn.qqmail.com/node/wwmng/wwmng/style/images/independent/DefaultAvatar$73ba92b5.png",
     *             "status": 4,
     *             "order": [
     *                 0
     *             ],
     *             "main_department": 17,
     *             "is_leader_in_dept": [
     *                 0
     *             ],
     *             "thumb_avatar": "https://rescdn.qqmail.com/node/wwmng/wwmng/style/images/independent/DefaultAvatar$73ba92b5.png",
     *             "open_userid": "woRUHiEQAAXyY5MWtKFcbFQPhDClBFYg",
     *             "direct_leader": []
     *         }
     * @param person
     * @param wxUser
     * @param isAdd
     */
    private void setPersonProps(SysOrgPerson person,WxUser wxUser, boolean isAdd) throws Exception {
        String name = getPropValue(wxUser,isAdd,"corpGroup2ekp.name.synWay","corpGroup2ekp.name");
        if(name!=null){
            person.setFdName(name);
        }
        String gender = getPropValue(wxUser,isAdd,"corpGroup2ekp.gender.synWay","corpGroup2ekp.gender");
        if(gender!=null){
            person.setFdSex(getSex(gender));
        }
        Integer status = wxUser.getStatus();
        if(status==2 || status==5){
            person.setFdIsAvailable(false);
        }else{
            person.setFdIsAvailable(true);
        }
        if(isAdd){
            person.setFdLoginName(wxUser.getUserId());
            SysOrgDefaultConfig sysOrgDefaultConfig = new SysOrgDefaultConfig();
            String psw = sysOrgDefaultConfig.getOrgDefaultPassword();
            if(StringUtil.isNotNull(psw)){
                person.setFdPassword(getPasswordEncoder().encodePassword(psw));
                person.setFdInitPassword(PasswordUtil.desEncrypt(psw));
            }
        }
        setPersonDepts(isAdd,wxUser,person);
        setPersonOrder(isAdd,wxUser,person);
    }

    /**
     * 设置人员部门，包括一人多部门的处理
     * @param isAdd
     * @param wxUser
     * @param person
     * @throws Exception
     */
    private void setPersonDepts(boolean isAdd, WxUser wxUser, SysOrgPerson person) throws Exception {
        WeixinWorkConfig config = WeixinWorkConfig.newInstance();
        String synWay = config.getDataMap().get("corpGroup2ekp.department.synWay");
        if(StringUtil.isNull(synWay)) {
            return;
        }
        if ("syn".equalsIgnoreCase(synWay)
                || (isAdd && "addSyn".equalsIgnoreCase(synWay))) {
            Long mainDeptId = wxUser.getMainDepartment();
            if(mainDeptId!=null){
                DeptMapperVo deptVo = deptMap.get(mainDeptId+"");
                if(deptVo!=null) {
                    String deptId = deptVo.getEkpDeptId();
                    SysOrgElement dept = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(deptId);
                    if (dept != null) {
                        person.setFdParent(dept);
                    }
                }
            }else{
                person.setFdParent(getOrgType());
            }
            if("muilDept".equals(personDeptType)){
                Long[] deptIds = wxUser.getDepartIds();
                List<SysOrgPost> posts = new ArrayList<>();
                for(Long deptId:deptIds){
                    if(deptId.equals(mainDeptId)){
                        continue;
                    }
                    DeptMapperVo deptVo = deptMap.get(deptId+"");
                    if(deptVo==null){
                        continue;
                    }
                    String ekpPostId = deptVo.getEkpPostId();
                    if(StringUtil.isNull(ekpPostId)){
                        continue;
                    }
                    SysOrgPost post = (SysOrgPost)getSysOrgPostService().findByPrimaryKey(ekpPostId);
                    if(post!=null){
                        posts.add(post);
                    }
                }
                person.setFdPosts(posts);
            }
        }
    }

    private void setPersonOrder(boolean isAdd, WxUser wxUser, SysOrgPerson person) throws Exception {
        WeixinWorkConfig config = WeixinWorkConfig.newInstance();
        String synWay = config.getDataMap().get("corpGroup2ekp.order.synWay");
        if(StringUtil.isNull(synWay)) {
            return;
        }
        if ("syn".equalsIgnoreCase(synWay)
                || (isAdd && "addSyn".equalsIgnoreCase(synWay))) {
            Long mainDept = wxUser.getMainDepartment();
            if(mainDept==null){
                return;
            }
            Long order = 0L;
            Long[] departIds = wxUser.getDepartIds();
            Long[] orderInDepts = wxUser.getOrder();
            for (int i = 0; i < departIds.length; i++) {
                if (mainDept.equals(departIds[i])) {
                    order = orderInDepts[i];
                    break;
                }
            }
            String orderSynKey = config.getDataMap().get("corpGroup2ekp.orderInDepts");
            if ("desc".equals(orderSynKey)) { // 逆序
                order = 2147483647L - order; // 避免排序号有负数，出现异常
            }
            person.setFdOrder(order.intValue());
        }
    }

    private class DeptMapperVo {
        private String ekpDeptId;

        public String getEkpDeptId() {
            return ekpDeptId;
        }

        public void setEkpDeptId(String ekpDeptId) {
            this.ekpDeptId = ekpDeptId;
        }

        public String getEkpPostId() {
            return ekpPostId;
        }

        public void setEkpPostId(String ekpPostId) {
            this.ekpPostId = ekpPostId;
        }

        public String getWxDeptId() {
            return wxDeptId;
        }

        public void setWxDeptId(String wxDeptId) {
            this.wxDeptId = wxDeptId;
        }

        private String ekpPostId;

        public DeptMapperVo(String ekpDeptId, String ekpPostId, String wxDeptId) {
            this.ekpDeptId = ekpDeptId;
            this.ekpPostId = ekpPostId;
            this.wxDeptId = wxDeptId;
        }

        private String wxDeptId;
    }

    private class UserMapperVo {
        public UserMapperVo(String ekpId, String userId, String userName, Boolean isAvailable,String mappId) {
            this.ekpId = ekpId;
            this.userId = userId;
            this.userName = userName;
            this.isAvailable = isAvailable;
            this.mappId = mappId;
        }

        private String ekpId;

        public String getEkpId() {
            return ekpId;
        }

        public void setEkpId(String ekpId) {
            this.ekpId = ekpId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        private String userId;
        private String userName;

        public Boolean getAvailable() {
            return isAvailable;
        }

        public void setAvailable(Boolean available) {
            isAvailable = available;
        }

        private Boolean isAvailable;

        public String getMappId() {
            return mappId;
        }

        public void setMappId(String mappId) {
            this.mappId = mappId;
        }

        private String mappId;
    }

    /**
     * 同步部门领导
     * @throws Exception
     */
    private void syncDeptLeader() throws Exception{
        logger.debug("开始同步部门领导");
        try {
            List<Object[]> deptMappings = getThirdWeixinCgDeptMappService().findValue("fdWxDeptId,fdEkpId", "fdCorpId='" + shareInfo.getCorpId() + "'", null);
            Map<String, String> deptMappingMap = new HashMap<>();
            for (Object[] mapp : deptMappings) {
                deptMappingMap.put((String)mapp[0], (String)mapp[1]);
            }
            Map<String, UserMapperVo> usersMap = buildUsersMap();
            for (WxDepart wxDept : departsList) {
                Long deptId = wxDept.getId();
                String ekpId = deptMappingMap.get(deptId+"");
                if(StringUtil.isNull(ekpId)){
                    continue;
                }
                String[] leaders = wxDept.getDeptLeaders();
                if(leaders==null){
                    continue;
                }
                SysOrgDept dept = (SysOrgDept)getSysOrgDeptService().findByPrimaryKey(ekpId,null,true);
                if(dept==null){
                    continue;
                }
                List<SysOrgElement> admins = new ArrayList<>();
                for(String userid:leaders){
                    UserMapperVo userMapperVo = usersMap.get(userid);
                    if(userMapperVo==null){
                        continue;
                    }
                    String userEkpId = userMapperVo.getEkpId();
                    SysOrgPerson person = (SysOrgPerson)getSysOrgPersonService().findByPrimaryKey(userEkpId,null,true);
                    if(person==null){
                        continue;
                    }
                    admins.add(person);
                }
                dept.setAuthElementAdmins(admins);
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            jobContext.logError(e.getMessage(),e);
            throw e;
        }
    }
}
