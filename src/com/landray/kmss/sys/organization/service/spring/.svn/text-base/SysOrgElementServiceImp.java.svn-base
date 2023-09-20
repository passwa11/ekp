package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.ICheckUniqueBean;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.log.forms.SysLogChangePwdForm;
import com.landray.kmss.sys.log.forms.SysLogLoginForm;
import com.landray.kmss.sys.log.model.SysLogChangePwd;
import com.landray.kmss.sys.log.model.SysLogLogin;
import com.landray.kmss.sys.log.model.SysLogOrganization;
import com.landray.kmss.sys.log.service.ISysLogOrganizationService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.oms.OMSPlugin;
import com.landray.kmss.sys.organization.dao.ISysOrgElementDao;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.event.SysOrgElementChangeEvent;
import com.landray.kmss.sys.organization.event.SysOrgElementEcoAddEvent;
import com.landray.kmss.sys.organization.event.SysOrgElementEcoUpdateEvent;
import com.landray.kmss.sys.organization.event.SysOrgElementInvalidatedEvent;
import com.landray.kmss.sys.organization.exception.ExistChildrenException;
import com.landray.kmss.sys.organization.extend.service.spring.PluginExtendBaseServiceImp;
import com.landray.kmss.sys.organization.forms.SysOrgDeptForm;
import com.landray.kmss.sys.organization.forms.SysOrgElementForm;
import com.landray.kmss.sys.organization.forms.SysOrgOrgForm;
import com.landray.kmss.sys.organization.forms.SysOrgPersonForm;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOMSCache;
import com.landray.kmss.sys.organization.model.SysOrgDept;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgElementHideRange;
import com.landray.kmss.sys.organization.model.SysOrgElementRange;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgOrg;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrgRole;
import com.landray.kmss.sys.organization.model.SysOrganizationConfig;
import com.landray.kmss.sys.organization.service.ISysOMSCacheService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.sys.organization.service.ISysOrganizationRecentContactService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.service.ISysOrganizationVisibleService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.transport.model.SysTransportImportConfig;
import com.landray.kmss.sys.transport.model.SysTransportPrimaryKeyProperty;
import com.landray.kmss.sys.transport.service.ISysTransportImport;
import com.landray.kmss.sys.transport.service.ISysTransportProvider;
import com.landray.kmss.sys.transport.service.spring.ImportContext;
import com.landray.kmss.sys.transport.service.spring.ImportProperty;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.sys.xform.util.SysFormDingUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.CacheMode;
import org.hibernate.query.Query;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.TransactionStatus;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SysOrgElementServiceImp extends PluginExtendBaseServiceImp
        implements ISysOrgElementService, SysOrgConstant, ICheckUniqueBean,
        ISysTransportImport, ISysTransportProvider, IXMLDataBean,
        ApplicationContextAware, BaseTreeConstant {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgCoreServiceImp.class);

    /**
     * 是否需要发布生态组织事件
     */
    private ThreadLocal<Boolean> eventEco = new ThreadLocal<Boolean>();

    private boolean keepGroupUnique;
    private ISysLogOrganizationService sysLogOrganizationService;

    public ISysLogOrganizationService getSysLogOrganizationService() {
        if (sysLogOrganizationService == null) {
            sysLogOrganizationService = (ISysLogOrganizationService) SpringBeanUtil
                    .getBean("sysLogOrganizationService");
        }
        return sysLogOrganizationService;
    }

    private ISysOrganizationRecentContactService sysOrganizationRecentContactService;

    public ISysOrganizationRecentContactService getSysOrganizationRecentContactService() {
        if (sysOrganizationRecentContactService == null) {
            sysOrganizationRecentContactService = (ISysOrganizationRecentContactService) SpringBeanUtil
                    .getBean("sysOrganizationRecentContactService");
        }
        return sysOrganizationRecentContactService;
    }

    private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

    public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
        if (sysOrganizationStaffingLevelService == null) {
            sysOrganizationStaffingLevelService = (ISysOrganizationStaffingLevelService) SpringBeanUtil
                    .getBean("sysOrganizationStaffingLevelService");
        }
        return sysOrganizationStaffingLevelService;
    }


    public void setSysLogOrganizationService(
            ISysLogOrganizationService sysLogOrganizationService) {
        this.sysLogOrganizationService = sysLogOrganizationService;
    }

    private ISysOrganizationVisibleService sysOrganizationVisibleService;

    public ISysOrganizationVisibleService getSysOrganizationVisibleService() {
        if (sysOrganizationVisibleService == null) {
            sysOrganizationVisibleService = (ISysOrganizationVisibleService) SpringBeanUtil
                    .getBean("sysOrganizationVisibleService");
        }
        return sysOrganizationVisibleService;
    }

    private IOrgRangeService orgRangeService;

    public IOrgRangeService getOrgRangeService() {
        if (orgRangeService == null) {
            orgRangeService = (IOrgRangeService) SpringBeanUtil.getBean("orgRangeService");
        }
        return orgRangeService;
    }

    private ISysOrgPostService sysOrgPostService;

    public ISysOrgPostService getSysOrgPostService() {
        if (sysOrgPostService == null) {
            sysOrgPostService = (ISysOrgPostService) SpringBeanUtil.getBean("sysOrgPostService");
        }
        return sysOrgPostService;
    }

    @Override
    public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model,
                                         RequestContext requestContext) throws Exception {
        Object modelInfo = SysOrgElement.class;
        if (form instanceof SysOrgPersonForm) {
            modelInfo = SysOrgPerson.class;
        }
        SysOrgElement elem = (SysOrgElement) findByPrimaryKey(form.getFdId(),
                modelInfo, true);
        SysOrgElement elemOld = null;
        SysOrgElementForm formOld = null;
        // 只有机构和部门才会查看范围和隐藏设置
        if (!(form instanceof SysOrgOrgForm || form instanceof SysOrgDeptForm)) {
            if (elem != null) {
                elem.setFdRange(null);
                elem.setFdHideRange(null);
            }
            ((SysOrgElementForm) form).setFdRange(null);
            ((SysOrgElementForm) form).setFdHideRange(null);
        }
        if (elem != null) {
            elemOld = cloneSysOrgElement(elem);

            SysOrgElementForm elementForm = null;
            formOld = (SysOrgElementForm) super.convertModelToForm(elementForm,
                    elem, requestContext);
        }

        SysOrgElement element = (SysOrgElement) super.convertFormToModel(form,
                model, requestContext);

        SysOrgElementForm formNew = (SysOrgElementForm) form;

        // 将名称转换成拼音储存
        String fdName = element.getFdNameOri();
        if (StringUtil.isNotNull(fdName)) {
            String fdNamePinYin = SysOrgUtil.getFullPinyin(fdName);
            if (StringUtil.isNotNull(fdNamePinYin)) {
                element.setFdNamePinYin(fdNamePinYin);
                element.setFdNameSimplePinyin(SysOrgUtil.getSimplePinyin(fdName));
            }
        }
        if (!element.getFdIsAvailable().booleanValue()) {
            if (!element.getFdChildren().isEmpty()) {
                throw new KmssRuntimeException(new KmssMessage(
                        "sys-organization:sysOrgDept.error.existChildren"));
            }
        }

        checkGroupUnique(element);
        //变更日志需要记录下日志状态，避免重复记录
        addOrgModifyLog(element, elemOld, requestContext, formOld, formNew);
        //element.getTransientInfoMap().put("logFlag",true);
        // 更新人员时把变更的岗位的更新时间更新    #135969
        if (form instanceof SysOrgPersonForm) {
            updatePostAlterTime(elemOld, formNew);
        }
        return element;

    }

    private void updatePostAlterTime(SysOrgElement elemOld,
                                     SysOrgElementForm formNew) {
        try {
            if (elemOld == null) {
                return;
            }
            SysOrgPerson perOld = (SysOrgPerson) elemOld;
            List<SysOrgPost> fdPostsOld = perOld.getFdPosts();
            SysOrgPersonForm perNew = (SysOrgPersonForm) formNew;
            String newPostIds = perNew.getFdPostIds();
            // logger.warn("现有的岗位Ids:" + perNew.getFdPostIds());
            Set<String> set = new HashSet<>();
            String updatePostIds = null; // 需要更新的岗位id
            if (fdPostsOld != null && fdPostsOld.size() > 0) {
                if (StringUtil.isNull(newPostIds)) {
                    for (SysOrgPost post : fdPostsOld) {
                        if (StringUtil.isNull(updatePostIds)) {
                            updatePostIds = post.getFdId();
                        } else {
                            updatePostIds += ";" + post.getFdId();
                        }
                    }
                } else {
                    for (SysOrgPost post : fdPostsOld) {
                        if (newPostIds.contains(post.getFdId())) {
                            set.add(post.getFdId()); // 相同的岗位id
                        } else {
                            if (StringUtil.isNull(updatePostIds)) {
                                updatePostIds = post.getFdId();
                            } else {
                                updatePostIds += ";" + post.getFdId();
                            }
                        }
                    }
                    if (set.size() == 0) {
                        // 没有相同
                        if (StringUtil.isNull(updatePostIds)) {
                            updatePostIds = newPostIds;
                        } else {
                            updatePostIds += ";" + newPostIds;
                        }
                    } else {
                        String[] newpostIdsArr = newPostIds.split(";");
                        for (String id : newpostIdsArr) {
                            if (!set.contains(id)) {
                                if (StringUtil.isNull(updatePostIds)) {
                                    updatePostIds = id;
                                } else {
                                    updatePostIds += ";" + id;
                                }
                            }
                        }
                    }
                }
            } else {
                updatePostIds = newPostIds;
            }

            if (StringUtil.isNotNull(updatePostIds)) {
                // logger.warn("需要更新的ids:" + updatePostIds);
                String[] idsArr = updatePostIds.split(";");
                for (String pid : idsArr) {
                    SysOrgPost pos = (SysOrgPost) findByPrimaryKey(pid,
                            SysOrgPost.class, true);
                    pos.setFdAlterTime(new Date());
                    getSysOrgPostService().update(pos);
                }

            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private SysOrgElement cloneSysOrgElement(SysOrgElement elem) {
        SysOrgElement elemOld = null;
        // 这里不能使用浅克隆，因为对象属性会根据新model而发生变化，也不能使用深度克隆，因为会清除hibernate
        // session面报错，只好使用最原始的方法
        if (elem instanceof SysOrgPerson || SysOrgConstant.ORG_TYPE_PERSON == elem.getFdOrgType()) {
            elemOld = new SysOrgPerson();
            SysOrgPerson tempOld = (SysOrgPerson) elemOld;
            SysOrgPerson tempNew = (SysOrgPerson) elem;
            tempOld.setFdSex(tempNew.getFdSex());
            tempOld.setFdMobileNo(tempNew.getFdMobileNo());
            tempOld.setFdEmail(tempNew.getFdEmail());
            tempOld.setFdLoginName(tempNew.getFdLoginName());
            tempOld.setFdPassword(tempNew.getFdPassword());
            tempOld.setFdInitPassword(tempNew.getFdInitPassword());
            tempOld.setFdRtxNo(tempNew.getFdRtxNo());
            tempOld.setFdWechatNo(tempNew.getFdWechatNo());
            tempOld.setFdCardNo(tempNew.getFdCardNo());
            tempOld.setFdAttendanceCardNumber(tempNew
                    .getFdAttendanceCardNumber());
            tempOld.setFdWorkPhone(tempNew.getFdWorkPhone());
            tempOld.setFdDefaultLang(tempNew.getFdDefaultLang());
            tempOld.setFdLastChangePwd(tempNew.getFdLastChangePwd());
            tempOld.setFdLockTime(tempNew.getFdLockTime());
            tempOld.setFdCanLogin(tempNew.getFdCanLogin());
            tempOld.setFdStaffingLevel(tempNew.getFdStaffingLevel());
        } else if(SysOrgConstant.ORG_TYPE_ORG == elem.getFdOrgType()){
            elemOld = new SysOrgOrg();
        } else if(SysOrgConstant.ORG_TYPE_DEPT == elem.getFdOrgType()){
            elemOld = new SysOrgDept();
        } else if(SysOrgConstant.ORG_TYPE_POST == elem.getFdOrgType()){
            elemOld = new SysOrgPost();
        } else if(SysOrgConstant.ORG_TYPE_ROLE == elem.getFdOrgType()){
            elemOld = new SysOrgRole();
        } else if(SysOrgConstant.ORG_TYPE_GROUP == elem.getFdOrgType()){
            elemOld = new SysOrgGroup();
            SysOrgGroup tempOld = (SysOrgGroup) elemOld;
            SysOrgGroup tempNew = (SysOrgGroup) elem;
            tempOld.setFdGroupCate(tempNew.getFdGroupCate());
            if(!CollectionUtils.isEmpty(tempNew.getFdMembers())){
                tempOld.setFdMembers(tempNew.getFdMembers());
            }
        } else {
            elemOld = new SysOrgElement();
        }
        elemOld.setFdIsExternal(elem.getFdIsExternal());
        if(!CollectionUtils.isEmpty(elem.getAuthElementAdmins())){
            elemOld.setAuthElementAdmins(new ArrayList(elem.getAuthElementAdmins()));
        }
        elemOld.setFdAlterTime(elem.getFdAlterTime());
        elemOld.setFdCreateTime(elem.getFdCreateTime());
        elemOld.setFdFlagDeleted(elem.getFdFlagDeleted());
        if(!CollectionUtils.isEmpty(elem.getFdGroups())){
            elemOld.setFdGroups(new ArrayList(elem.getFdGroups()));
        }
        elemOld.setFdHierarchyId(elem.getFdHierarchyId());
        elemOld.setFdId(elem.getFdId());
        elemOld.setFdImportInfo(elem.getFdImportInfo());
        elemOld.setFdIsAbandon(elem.getFdIsAbandon());
        elemOld.setFdIsAvailable(elem.getFdIsAvailable());
        elemOld.setFdIsBusiness(elem.getFdIsBusiness());
        elemOld.setFdKeyword(elem.getFdKeyword());
        elemOld.setFdLdapDN(elem.getFdLdapDN());
        elemOld.setFdMemo(elem.getFdMemo());
        elemOld.setFdName(elem.getFdName());
        elemOld.setFdNamePinYin(elem.getFdNamePinYin());
        elemOld.setFdNo(elem.getFdNo());
        elemOld.setFdOrder(elem.getFdOrder());
        elemOld.setFdOrgType(elem.getFdOrgType());
        elemOld.setFdParent(elem.getFdParent());
        if(!CollectionUtils.isEmpty(elem.getFdPersons())){
            elemOld.setFdPersons(new ArrayList(elem.getFdPersons()));
        }
        if(!CollectionUtils.isEmpty(elem.getFdPosts())){
            elemOld.setFdPosts(new ArrayList(elem.getFdPosts()));
        }
        if(!CollectionUtils.isEmpty(elem.getHbmChildren())){
            elemOld.setHbmChildren(new ArrayList(elem.getHbmChildren()));
        }
        if(!CollectionUtils.isEmpty(elem.getHbmGroups())){
            elemOld.setHbmGroups(new ArrayList(elem.getHbmGroups()));
        }
        elemOld.setHbmParent(elem.getHbmParent());
        elemOld.setHbmParentOrg(elem.getHbmParentOrg());
        if(!CollectionUtils.isEmpty(elem.getHbmPersons())){
            elemOld.setHbmPersons(new ArrayList(elem.getHbmPersons()));
        }
        if(!CollectionUtils.isEmpty(elem.getHbmPosts())){
            elemOld.setHbmPosts(new ArrayList(elem.getHbmPosts()));
        }
        elemOld.setHbmSuperLeader(elem.getHbmSuperLeader());
        if(!CollectionUtils.isEmpty(elem.getHbmSuperLeaderChildren())){
            elemOld.setHbmSuperLeaderChildren(new ArrayList(elem.getHbmSuperLeaderChildren()));
        }
        elemOld.setHbmThisLeader(elem.getHbmThisLeader());
        if(!CollectionUtils.isEmpty(elem.getHbmThisLeaderChildren())){
            elemOld.setHbmThisLeaderChildren(new ArrayList(elem.getHbmThisLeaderChildren()));
        }
        elemOld.setSysDictModel(elem.getSysDictModel());
        elemOld.setFdOrgEmail(elem.getFdOrgEmail());
        if (SysOrgEcoUtil.IS_ENABLED_ECO) {
            elemOld.setFdExternal(elem.getFdExternal());
        }
        elemOld.setFdRange(elem.getFdRange());
        elemOld.setFdHideRange(elem.getFdHideRange());

        return elemOld;
    }

    /**
     * 添加组织架构变动日志
     *
     * @param element
     * @param requestContext
     * @throws Exception
     */
    public void addOrgModifyLog(SysOrgElement element,
                                SysOrgElement oldElement, RequestContext requestContext,
                                SysOrgElementForm formOld, SysOrgElementForm formNew)
            throws Exception {
        SysLogOrganization log = SysOrgUtil.buildSysLog(requestContext);
        String details = log.getFdOperator()
                + SysOrgUtil.getDetails(oldElement, element, formOld, formNew);
        // 如果是“置为无效”操作，需要记录原部门和原岗位
        String method = log.getFdParaMethod();
        if ("invalidated".equals(method) || "invalidatedAll".equals(method)) {
            List<String> postNames = new ArrayList<String>();
            for (Object obj : oldElement.getFdPosts()) {
                if (obj instanceof SysOrgPost) {
                    SysOrgPost post = (SysOrgPost) obj;
                    postNames.add(post.getFdName());
                }
            }
            details += ResourceUtil.getString(requestContext.getRequest(),
                    "sysOrgElement.originalOrg", "sys-organization",
                    new Object[]{oldElement.getFdParentsName(), postNames});
        }

        log.setFdDetails(details);// 设置详细信息
        log.setFdTargetId(element.getFdId());
        getSysLogOrganizationService().add(log);
    }

    /**
     * 添加组织架构变动日志
     *
     * @param element
     * @param addFlag
     * @throws Exception
     */
    @Override
    public void addOrgModifyLog(SysOrgElement element, boolean addFlag) throws Exception {
        if(element.getTransientInfoMap().get("logFlag") != null && Boolean.TRUE.equals(element.getTransientInfoMap().get("logFlag"))){
            return;
        }
        RequestContext requestContext = new RequestContext(Plugin.currentRequest());
        SysOrgElementForm oldForm = null;
        SysOrgElement oldElement = null;
        SysOrgElementForm formNew = null;
        if(!addFlag){
            //更新,需要查询原有对象
            oldElement = this.queryOriElement(element);
            if(oldElement != null){
                oldForm = (SysOrgElementForm) super.convertModelToForm(oldForm, oldElement, requestContext);
                SysOrgElement newElement = this.cloneSysOrgElement(element);
                formNew = (SysOrgElementForm) super.convertModelToForm(formNew, newElement, requestContext);
            }else{
                return;
            }
        }
        String logDetail = SysOrgUtil.getDetails(oldElement, element, oldForm, formNew);
        if(StringUtil.isNotNull(logDetail)){
            SysLogOrganization log = SysOrgUtil.buildAdminSysLog(requestContext);
            String details = log.getFdOperator() + logDetail;
            // 如果是“置为无效”操作，需要记录原部门和原岗位
            String method = log.getFdParaMethod();
            if ("invalidated".equals(method) || "invalidatedAll".equals(method)) {
                List<String> postNames = new ArrayList<String>();
                for (Object obj : oldElement.getFdPosts()) {
                    if (obj instanceof SysOrgPost) {
                        SysOrgPost post = (SysOrgPost) obj;
                        postNames.add(post.getFdName());
                    }
                }
                details += ResourceUtil.getString(requestContext.getRequest(),
                        "sysOrgElement.originalOrg", "sys-organization",
                        new Object[]{oldElement.getFdParentsName(), postNames});
            }

            log.setFdDetails(details);// 设置详细信息
            log.setFdTargetId(element.getFdId());
            getSysLogOrganizationService().add(log);
        }
    }

    private SysOrgElement queryOriElement(SysOrgElement element) throws Exception {
        TransactionStatus status = null;
        SysOrgElement oriElement = null;
        String hql = null;
        try{
            status = TransactionUtils.beginNewReadTransaction();
            if(SysOrgConstant.ORG_TYPE_PERSON == element.getFdOrgType()){
                hql = "select sysOrgPerson from com.landray.kmss.sys.organization.model.SysOrgPerson sysOrgPerson where sysOrgPerson.fdId = :fdId";
            }else{
                hql = "select sysOrgElement from com.landray.kmss.sys.organization.model.SysOrgElement sysOrgElement where sysOrgElement.fdId = :fdId";
            }
            Query query = getBaseDao().getHibernateSession().createQuery(hql);
            query.setCacheable(true);
            query.setCacheMode(CacheMode.NORMAL);
            query.setCacheRegion("sys-organization");
            query.setString("fdId",element.getFdId());
            List list = query.list();
            if(!CollectionUtils.isEmpty(list)){
                switch (element.getFdOrgType()){
                    case SysOrgConstant.ORG_TYPE_ORG:
                        oriElement = (SysOrgOrg) list.get(0);
                        break;
                    case SysOrgConstant.ORG_TYPE_DEPT:
                        oriElement = (SysOrgDept) list.get(0);
                        break;
                    case SysOrgConstant.ORG_TYPE_GROUP:
                        oriElement = (SysOrgGroup) list.get(0);
                        break;
                    case SysOrgConstant.ORG_TYPE_POST:
                        oriElement = (SysOrgPost) list.get(0);
                        break;
                    case SysOrgConstant.ORG_TYPE_PERSON:
                        oriElement = (SysOrgPerson) list.get(0);
                        break;
                    case SysOrgConstant.ORG_TYPE_ROLE:
                        oriElement = (SysOrgRole) list.get(0);
                        break;
                }
            }
            TransactionUtils.commit(status);
        }catch (Exception e){
            TransactionUtils.rollback(status);
            logger.error("查询原始组织架构数据失败：",e);
        }
        return oriElement;
    }

    public void setKeepGroupUnique(boolean keepGroupUnique) {
        this.keepGroupUnique = keepGroupUnique;
    }

    @Override
    public String checkUnique(RequestContext requestInfo) throws Exception {
        String fdOrgType = requestInfo.getParameter("fdOrgType");
        int type = new Integer(fdOrgType).intValue();
        boolean keepGroupUnique = false;
        try {
            keepGroupUnique = new SysOrganizationConfig().isKeepGroupUnique();
        } catch (Exception e) {
            // TODO 自动生成 catch 块
            logger.error(e.toString());
        }
        String fdId = requestInfo.getParameter("fdId");
        if (keepGroupUnique && (type & ~(ORG_TYPE_PERSON | ORG_TYPE_ROLE)) > 0) {
            SysOrgElement element = (SysOrgElement) findByPrimaryKey(fdId,
                    SysOrgElement.class, true);
            String fdName = requestInfo.getParameter("fdName");
            fdName = URLDecoder.decode(fdName, "UTF-8");
            String checkType = requestInfo.getParameter("checkType");
            HQLInfo hqlInfo = new HQLInfo();
            String hql = " sysOrgElement.fdName=:fdName ";
            hqlInfo.setParameter("fdName", fdName);
            if (StringUtil.isNotNull(fdId)) {
                hql += " and sysOrgElement.fdId!=:fdId ";
                hqlInfo.setParameter("fdId", fdId);
            }
            if ((type == 1) || (type == 2) || (type == 4) || (type == 16)) {// 这里是对岗位或群组名称的重复检测
                hql += " and sysOrgElement.fdOrgType!=8 and sysOrgElement.fdOrgType!=32 ";
            }
            if ("unique".equals(checkType)) {
                hql = hql + " and sysOrgElement.fdIsAvailable=" + SysOrgHQLUtil.toBooleanValueString(true) + " "; // 1 表示有效的登录名
                // 检测有效部分是否重名
            } else {
                if ((element != null) && (fdName.equals(element.getFdName()))) {
                    // 编辑名称并且名称没有改动过则 无需校验无效部分是否重名
                    // 编辑模式检查编号是否合法
                    String fdNo = requestInfo.getParameter("fdNo");
                    if (StringUtil.isNotNull(fdNo)) {
                        try {
                            checkFdNo(fdId, Integer.valueOf(fdOrgType), fdNo);
                        } catch (Exception e) {
                            return "0";
                        }
                    }
                    return "";
                }
                hql = hql + " and sysOrgElement.fdIsAvailable=" + SysOrgHQLUtil.toBooleanValueString(false) + " ";// 0 表示无效的登录名
                // 检测无效部分是否重名
            }
            hqlInfo.setWhereBlock(hql);
            List list = findList(hqlInfo);
            if ((list != null) && (list.size() > 0)) {
                return "0";
            }
        }
        // 检查编号是否合法
        String fdNo = requestInfo.getParameter("fdNo");
        if (StringUtil.isNotNull(fdNo)) {
            try {
                checkFdNo(fdId, Integer.valueOf(fdOrgType), fdNo);
            } catch (Exception e) {
                return "0";
            }
        }

        return "";
    }

    /**
     * 检查编号是否合法
     *
     * @param fdId
     * @param fdOrgType
     * @param fdNo
     * @throws Exception
     */
    @Override
    public void checkFdNo(String fdId, Integer fdOrgType, String fdNo) throws Exception {
        // 检测编号是否重复
        if (SysOrgConstant.ORG_TYPE_ROLE == fdOrgType) {
            return;
        }

        boolean noRequired = new SysOrganizationConfig().isNoRequired();
        if (noRequired) {
            if (StringUtil.isNull(fdNo)) {
                throw new Exception(ResourceUtil.getString("errors.required", null, null, ResourceUtil.getString("sys-organization:sysOrgOrg.fdNo")));
            }
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setWhereBlock("fdId != :fdId and fdOrgType = :fdOrgType and fdNo = :fdNo");
            hqlInfo.setParameter("fdId", fdId);
            hqlInfo.setParameter("fdOrgType", fdOrgType);
            hqlInfo.setParameter("fdNo", fdNo);
            List list = findList(hqlInfo);
            if ((list != null) && (list.size() > 0)) {
                throw new Exception(ResourceUtil.getString("organization.error.fdNo.mustUnique.forImport", "sys-organization", null, fdNo));
            }
        }
    }

    private void checkGroupUnique(SysOrgElement element) {
        boolean keepGroupUnique = false;
        try {
            keepGroupUnique = new SysOrganizationConfig().isKeepGroupUnique();
        } catch (Exception e) {
            logger.error(e.toString());
        }
        if (keepGroupUnique
                && (element.getFdOrgType().intValue() & ~(ORG_TYPE_PERSON | ORG_TYPE_ROLE)) > 0) {
            String hql = "select sysOrgElement from com.landray.kmss.sys.organization.model.SysOrgElement sysOrgElement"
                    + " where sysOrgElement.fdName=:name and sysOrgElement.fdOrgType!=8 and sysOrgElement.fdOrgType!=32";
            if (element.getFdId() != null) {
                hql += " and sysOrgElement.fdId!='" + element.getFdId() + "'";
            }
            Query query = getBaseDao().getHibernateSession().createQuery(hql);
            query.setCacheable(true);
            query.setCacheMode(CacheMode.NORMAL);
            query.setCacheRegion("sys-organization");
            query.setString("name", element.getFdName());
            List list = query.list();
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    SysOrgElement sysOrgElement = (SysOrgElement) list.get(i);
                    if (sysOrgElement.getFdIsAvailable().booleanValue()
                            && element.getFdIsAvailable().booleanValue()) {
                        throw new KmssRuntimeException(
                                new KmssMessage(
                                        "sys-organization:sysOrgElement.error.uniqueName",
                                        element.getFdName()));
                    }
                }
            }
        }
    }

    /*
     * 增加在组织机构做CRUD操作时，将操作的记录保存到OMSCACHE中，供OMS同步 add by wubing date:2006-12-15
     */
    private ISysOMSCacheService sysOMSCacheService;

    public void setSysOMSCacheService(ISysOMSCacheService sysOMSCacheService) {
        this.sysOMSCacheService = sysOMSCacheService;
    }

    public ISysOMSCacheService getSysOMSCacheService() {
        if (sysOMSCacheService == null) {
            sysOMSCacheService = (ISysOMSCacheService) SpringBeanUtil
                    .getBean("sysOMSCacheService");
        }
        return sysOMSCacheService;
    }

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        // 处理名称拼音
        SysOrgElement orgElement = (SysOrgElement) modelObj;
        String fdNamePinYin = SysOrgUtil
                .getFullPinyin(orgElement.getFdNameOri());
        if (StringUtil.isNotNull(fdNamePinYin)) {
            orgElement.setFdNamePinYin(fdNamePinYin);
            orgElement.setFdNameSimplePinyin(
                    SysOrgUtil.getSimplePinyin(orgElement.getFdNameOri()));
        }
        // 检查编号是否合法
        checkFdNo(orgElement.getFdId(), orgElement.getFdOrgType(), orgElement.getFdNo());
        try {
            SysOrgPerson creator = UserUtil.getUser();
            if (creator != null) {
                orgElement.setDocCreator(creator);
            }
        } catch (Exception e) {
            //这里为了解决系统初始化数据库时创建人员获取不到当前人，catch跳过
        }
        // 设置查看范围和隐藏属性
        setRanges(orgElement);

        String fdElementId = super.add(orgElement);
        //添加组织架构变动日志
        orgElement.setFdId(fdElementId);
        //addOrgModifyLog(orgElement,true);
        // 将操作的记录保存到OMSCACHE中
        for (String type : OMSPlugin.getOutTypes()) {
            SysOMSCache sysOMSCache = new SysOMSCache();
            sysOMSCache.setFdOrgElementId(fdElementId);
            sysOMSCache.setFdAppName(type);
            sysOMSCache.setFdOpType(new Integer(OMS_OP_FLAG_ADD));
            getSysOMSCacheService().add(sysOMSCache);
        }
        if (SysOrgEcoUtil.IS_ENABLED_ECO && BooleanUtils.isTrue(eventEco.get())) {
            // 发布生态组织新增事件
            if (BooleanUtils.isTrue(orgElement.getFdIsExternal())) {
                applicationContext.publishEvent(new SysOrgElementEcoAddEvent(orgElement));
            }
        }
        // 获取更新后的层级ID
        String afterHierarchyId = orgElement.getFdHierarchyId();
        // 如果有变更层级，需要发布事件，通知其它模块处理相关业务
        if (StringUtil.isNotNull(afterHierarchyId)) {
            applicationContext.publishEvent(new SysOrgElementChangeEvent(orgElement, "", afterHierarchyId));
        }
        return fdElementId;
    }

    @Override
    public void update(IBaseModel modelObj) throws Exception {
        SysOrgElement orgElement = (SysOrgElement) modelObj;

        // 检查“与业务相关”逻辑
        if (((orgElement.getFdOrgType() | ORG_TYPE_ORGORDEPT) == ORG_TYPE_ORGORDEPT) && !orgElement.getFdIsBusiness()) {
            checkBusines(orgElement);
        }

        // 处理名称拼音
        String fdNamePinYin = SysOrgUtil
                .getFullPinyin(orgElement.getFdNameOri());
        if (StringUtil.isNotNull(fdNamePinYin)) {
            orgElement.setFdNamePinYin(fdNamePinYin);
            orgElement.setFdNameSimplePinyin(
                    SysOrgUtil.getSimplePinyin(orgElement.getFdNameOri()));
        }

        if (orgElement.getFdOrgType() == ORG_TYPE_PERSON && !orgElement.getFdIsAvailable()) {
            if (orgElement.getFdParent() != null) {
                orgElement.setFdPreDeptId(orgElement.getFdParent().getFdId());
            }

            if (orgElement.getFdPosts() != null && orgElement.getFdPosts().size() > 0) {
                List<SysOrgPost> posts = orgElement.getFdPosts();
                String[] postsArr = new String[posts.size()];
                for (int i = 0; i < posts.size(); i++) {
                    postsArr[i] = posts.get(i).getFdId();
                }
                orgElement.setFdPrePostIds(postsArr);
            }
        }
        // 检查编号是否合法
        checkFdNo(orgElement.getFdId(), orgElement.getFdOrgType(), orgElement.getFdNo());
        // 设置查看范围和隐藏属性
        setRanges(orgElement);
        // 获取更新前的层级ID
        String beforeHierarchyId = orgElement.getFdHierarchyId();
        //添加组织架构变动日志
        //addOrgModifyLog(orgElement,false);
        super.update(orgElement);
        // 获取更新后的层级ID
        String afterHierarchyId = orgElement.getFdHierarchyId();
        // 如果有变更层级，需要发布事件，通知其它模块处理相关业务
        if (!beforeHierarchyId.equals(afterHierarchyId)) {
            applicationContext.publishEvent(new SysOrgElementChangeEvent(orgElement, beforeHierarchyId, afterHierarchyId));
        }
        Set<String> omsOutTypes = OMSPlugin.getOutTypes();
        if (!omsOutTypes.isEmpty()) {
            Map caches = getCacheMap(getSysOMSCacheService().findList(
                    " sysOMSCache.fdOrgElementId='" + modelObj.getFdId() + "'",
                    null));
            for (String type : omsOutTypes) {
                if (caches.keySet().contains(type)) {
                    continue;
                }
                SysOMSCache sysOMSCache = new SysOMSCache();
                sysOMSCache.setFdOrgElementId(modelObj.getFdId());
                sysOMSCache.setFdAppName(type);
                sysOMSCache.setFdOpType(new Integer(OMS_OP_FLAG_UPDATE));
                getSysOMSCacheService().add(sysOMSCache);
            }
        }
        // 如果是外转内，也需要发布事件，目的是需要同步钉钉数据
        boolean publishEcoEvent = false;
        HttpServletRequest request = Plugin.currentRequest();
        if (request != null) {
            String outToIn = request.getParameter("outToIn");
            if ("true".equals(outToIn)) {
                publishEcoEvent = true;
            }
        }
        if (SysOrgEcoUtil.IS_ENABLED_ECO && (publishEcoEvent || BooleanUtils.isTrue(eventEco.get()))) {
            // 发布生态组织更新事件
            if (publishEcoEvent || BooleanUtils.isTrue(orgElement.getFdIsExternal())) {
                applicationContext.publishEvent(new SysOrgElementEcoUpdateEvent(orgElement));
            }
        }
    }

    /**
     * 设置查看范围和隐藏属性
     *
     * @param orgElement
     * @throws Exception
     */
    private void setRanges(SysOrgElement orgElement) throws Exception {
        if (SysOrgEcoUtil.IS_ENABLED_ECO) {
            // 处理外部组织(如果有上级，并且上级是外部组织，那么强制把该组织设置为外部)
            SysOrgElement parent = orgElement.getFdParent();
            if (parent != null && BooleanUtils.isTrue(parent.getFdIsExternal())) {
                orgElement.setFdIsExternal(true);
            }
        }
        // 处理成员查看组织范围
        if (orgElement.getFdOrgType() == ORG_TYPE_ORG || orgElement.getFdOrgType() == ORG_TYPE_DEPT) {
            // 查看范围
            SysOrgElementRange range = getRange(orgElement);
            // 隐藏设置
            SysOrgElementHideRange hideRange = getHideRange(orgElement);
            if (SysOrgEcoUtil.IS_ENABLED_ECO && BooleanUtils.isTrue(orgElement.getFdIsExternal())) {
                // 生态组织，默认开启
                if (range == null) {
                    range = new SysOrgElementRange();
                    range.setFdIsOpenLimit(true);
                    range.setFdViewType(1);
                    orgElement.setFdRange(range);
                }
                if (hideRange == null) {
                    hideRange = new SysOrgElementHideRange();
                    hideRange.setFdIsOpenLimit(true);
                    hideRange.setFdViewType(0);
                    orgElement.setFdHideRange(hideRange);
                }
            } else {
                // 内部组织，默认关闭
                if (range == null) {
                    range = new SysOrgElementRange();
                    range.setFdIsOpenLimit(false);
                    range.setFdViewType(1);
                    orgElement.setFdRange(range);
                }
                if (hideRange == null) {
                    hideRange = new SysOrgElementHideRange();
                    hideRange.setFdIsOpenLimit(false);
                    hideRange.setFdViewType(0);
                    orgElement.setFdHideRange(hideRange);
                }
            }
            range.setFdElement(orgElement);
            hideRange.setFdElement(orgElement);
        }
    }

    private SysOrgElementRange getRange(SysOrgElement elem) throws Exception {
        SysOrgElementRange range = elem.getFdRange();
        if (range == null) {
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setModelName(SysOrgElementRange.class.getName());
            hqlInfo.setWhereBlock("fdElement.fdId = :elemId");
            hqlInfo.setParameter("elemId", elem.getFdId());
            List<SysOrgElementRange> list = getBaseDao().findList(hqlInfo);
            if (CollectionUtils.isNotEmpty(list)) {
                range = list.get(0);
            }
        }
        return range;
    }

    private SysOrgElementHideRange getHideRange(SysOrgElement elem) throws Exception {
        SysOrgElementHideRange hideRange = elem.getFdHideRange();
        if (hideRange == null) {
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setModelName(SysOrgElementHideRange.class.getName());
            hqlInfo.setWhereBlock("fdElement.fdId = :elemId");
            hqlInfo.setParameter("elemId", elem.getFdId());
            List<SysOrgElementHideRange> list = getBaseDao().findList(hqlInfo);
            if (CollectionUtils.isNotEmpty(list)) {
                hideRange = list.get(0);
            }
        }
        return hideRange;
    }

    private Map getCacheMap(List list) {
        Map caches = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            SysOMSCache cache = (SysOMSCache) list.get(i);
            caches.put(cache.getFdAppName(), cache);
        }
        return caches;
    }

    @Override
    public void delete(IBaseModel modelObj) throws Exception {
        Set<String> omsOutTypes = OMSPlugin.getOutTypes();
        if (!omsOutTypes.isEmpty()) {
            Map caches = getCacheMap(getSysOMSCacheService().findList(
                    " sysOMSCache.fdOrgElementId='" + modelObj.getFdId() + "'",
                    null));
            for (String type : omsOutTypes) {
                if (caches.keySet().contains(type)) {
                    SysOMSCache sysOMSCache = (SysOMSCache) caches.get(type);
                    if (sysOMSCache.getFdOpType().intValue() == OMS_OP_FLAG_ADD) {
                        getSysOMSCacheService().delete(sysOMSCache);
                    }
                    if (sysOMSCache.getFdOpType().intValue() == OMS_OP_FLAG_UPDATE) {
                        sysOMSCache
                                .setFdOpType(new Integer(OMS_OP_FLAG_DELETE));
                        getSysOMSCacheService().update(sysOMSCache);
                    }
                } else {
                    SysOMSCache sysOMSCache = new SysOMSCache();
                    sysOMSCache.setFdOrgElementId(getDelKewWords(modelObj));
                    sysOMSCache.setFdAppName(type);
                    sysOMSCache.setFdOpType(new Integer(OMS_OP_FLAG_DELETE));
                    getSysOMSCacheService().add(sysOMSCache);

                }
            }
        }
        super.delete(modelObj);
    }

    /**
     * 返回删除的记录的某些关键字组成的字符串
     *
     * @param modelObj
     * @return
     */
    private String getDelKewWords(IBaseModel modelObj) {
        // 字符串分隔符
        String splitStr = ";";
        StringBuffer delKeywords = new StringBuffer();
        if (modelObj instanceof SysOrgOrg) {
            SysOrgOrg element = (SysOrgOrg) modelObj;
            delKeywords.append("fdNo:").append(element.getFdNo()).append(
                    splitStr).append("fdId:").append(element.getFdId()).append(
                    splitStr).append("fdOrgType:SysOrgOrg");
        } else if (modelObj instanceof SysOrgDept) {
            SysOrgDept element = (SysOrgDept) modelObj;
            delKeywords.append("fdNo:").append(element.getFdNo()).append(
                    splitStr).append("fdId:").append(element.getFdId()).append(
                    splitStr).append("fdOrgType:SysOrgDept");

        } else if (modelObj instanceof SysOrgPerson) {
            SysOrgPerson element = (SysOrgPerson) modelObj;
            delKeywords.append("fdNo:").append(element.getFdNo()).append(
                            splitStr).append("fdLoginName:").append(
                            element.getFdLoginName()).append(splitStr).append("fdId:")
                    .append(element.getFdId()).append(splitStr).append(
                            "fdOrgType:SysOrgPerson");
        }
        return delKeywords.toString();

    }

    @Override
    public SysOrgElement format(SysOrgElement element) throws Exception {
        return ((ISysOrgElementDao) getBaseDao()).format(element);
    }

    @Override
    public void flushElement(SysOrgElement element) throws Exception {
        getBaseDao().getHibernateSession().flush();
        getBaseDao().getHibernateSession().refresh(element);
    }

    @Override
    public void setNotToUpdateRelation(Boolean notToUpdateRelation) {
        ((ISysOrgElementDao) getBaseDao())
                .setNotToUpdateRelation(notToUpdateRelation);
    }

    @Override
    public void updateRelation() throws Exception {
        ((ISysOrgElementDao) getBaseDao()).updateRelation();
    }

    @Override
    public List findAllChildElement(SysOrgElement element, int orgType)
            throws Exception {
        List rtnList = new ArrayList();
        HQLInfo hql = new HQLInfo();
        if (element != null) {
            String whereBlock = "fdHierarchyId like '"
                    + element.getFdHierarchyId() + "%' and fdOrgType = "
                    + orgType;
            hql.setWhereBlock(whereBlock);
            rtnList = this.findList(hql);
        }
        return rtnList;
    }

    @Override
    public void setNotToUpdateHierarchy(Boolean notToUpdateHierarchy) {
        ((ISysOrgElementDao) getBaseDao())
                .setNotToUpdateHierarchy(notToUpdateHierarchy);
    }

    @Override
    public void updateInvalidated(String id, RequestContext requestContext)
            throws Exception {
        SysOrgElement sysOrgElement = (SysOrgElement) this.findByPrimaryKey(id);
        if (!sysOrgElement.getFdChildren().isEmpty()) {
            if (BooleanUtils.isTrue(sysOrgElement.getFdIsExternal())) {
                // 生态组织置为无效时，不能清除上下级关系，因此需要检查所有下级是否有效
                for (Object obj : sysOrgElement.getFdChildren()) {
                    SysOrgElement child = (SysOrgElement) obj;
                    if (BooleanUtils.isTrue(child.getFdIsAvailable())) {
                        throw new ExistChildrenException();
                    }
                }
            } else {
                throw new ExistChildrenException();
            }
        }

        SysOrgElement elemOld = null;

        SysOrgElementForm elementForm = null;
        SysOrgElementForm formNew = null;
        SysOrgElementForm formOld = null;

        // 记录日志
        if (UserOperHelper.allowLogOper("invalidated", "com.landray.kmss.sys.organization.model.SysOrgOrg")
                || UserOperHelper.allowLogOper("invalidated", "com.landray.kmss.sys.organization.model.SysOrgDept")
                || UserOperHelper.allowLogOper("invalidated", "com.landray.kmss.sys.organization.model.SysOrgPost")
                || UserOperHelper.allowLogOper("invalidated", "com.landray.kmss.sys.organization.model.SysOrgGroup")
                || UserOperHelper.allowLogOper("invalidated", "com.landray.kmss.sys.organization.model.SysOrgPerson")
                || UserOperHelper.allowLogOper("invalidated", "com.landray.kmss.sys.organization.model.SysOrgRole")) {
            UserOperContentHelper.putUpdate(sysOrgElement);
        }

        if (sysOrgElement != null) {
            elemOld = cloneSysOrgElement(sysOrgElement);
            formOld = (SysOrgElementForm) super.convertModelToForm(elementForm,
                    sysOrgElement, requestContext);

            sysOrgElement.setFdIsAvailable(new Boolean(false));

            formNew = (SysOrgElementForm) super.convertModelToForm(elementForm,
                    sysOrgElement, requestContext);

        }

        // 增加组织架构操作日志
        if (requestContext.getRequest() != null) {
            addOrgModifyLog(sysOrgElement, elemOld, requestContext, formOld,
                    formNew);
            //避免重复添加日志
            //sysOrgElement.getTransientInfoMap().put("logFlag",true);
        }
        if (sysOrgElement instanceof SysOrgPerson) {
            if (sysOrgElement.getFdParent() != null) {
                sysOrgElement.setFdPreDeptId(sysOrgElement.getFdParent().getFdId());
            }

            if (sysOrgElement.getFdPosts() != null && sysOrgElement.getFdPosts().size() > 0) {
                List<SysOrgPost> posts = sysOrgElement.getFdPosts();
                String[] postsArr = new String[posts.size()];
                for (int i = 0; i < posts.size(); i++) {
                    postsArr[i] = posts.get(i).getFdId();
                }
                sysOrgElement.setFdPrePostIds(postsArr);
            }

        }
        update(sysOrgElement);
        flushHibernateSession();

        // 发布置为无效事件
        applicationContext.publishEvent(new SysOrgElementInvalidatedEvent(
                sysOrgElement, requestContext));
    }

    private void updateValidated(String id, RequestContext requestContext) throws Exception {
        SysOrgElement sysOrgElement = (SysOrgElement) this.findByPrimaryKey(id);
        sysOrgElement.setFdIsAvailable(new Boolean(true));
        super.update(sysOrgElement);

        if (SysOrgEcoUtil.IS_ENABLED_ECO && BooleanUtils.isTrue(eventEco.get())) {
            // 发布生态组织更新事件
            if (BooleanUtils.isTrue(sysOrgElement.getFdIsExternal())) {
                applicationContext.publishEvent(new SysOrgElementEcoUpdateEvent(sysOrgElement));
            }
        }
    }

    @Override
    public void updateValidated(String[] ids, RequestContext requestContext) throws Exception {
        for (int i = 0; i < ids.length; i++) {
            this.updateValidated(ids[i], requestContext);
        }
    }

    @Override
    public void updateInvalidated(String[] ids, RequestContext requestContext)
            throws Exception {
        for (int i = 0; i < ids.length; i++) {
            this.updateInvalidated(ids[i], requestContext);
        }
    }

    /**
     * 升级element表数据，将fdName的值转化为拼音给fd_name_pinyin赋值
     *
     * @author limh
     */
    @Override
    public void updatePinYinField() throws Exception {
        TransactionStatus status = null;
        String sql = "";
        int count = 0;

        try {
            // 每次查询1000条element表数据
            sql = "select fdId,fdName from  com.landray.kmss.sys.organization.model.SysOrgElement sysOrgElement ";
            Query query = getBaseDao().getHibernateSession().createQuery(sql);
            query.setCacheable(true);
            query.setCacheMode(CacheMode.NORMAL);
            query.setCacheRegion("sys-organization");
            int pageSize = 1000;
            query.setMaxResults(pageSize);
            List sysOrgElementList = new ArrayList();
            long beginTime = System.currentTimeMillis();

            do {
                // 执行数据升级
                status = TransactionUtils.beginNewTransaction();
                long bachBeginTime = System.currentTimeMillis();
                count++;
                sysOrgElementList.clear();
                sysOrgElementList = query.list();
                query.setFirstResult(count * pageSize);
                if (sysOrgElementList.size() <= 0) {
                    continue;
                }
                for (int i = 0; i < sysOrgElementList.size(); i++) {
                    Object[] sysOrgElement = (Object[]) sysOrgElementList
                            .get(i);
                    String fdId = (String) sysOrgElement[0];
                    String fdName = (String) sysOrgElement[1];
                    String fdNamePinYin = SysOrgUtil.getFullPinyin(fdName);
                    String fdNameSimplePinyin = SysOrgUtil.getSimplePinyin(fdName);
                    String sqlTemp = " update  com.landray.kmss.sys.organization.model.SysOrgElement sysOrgElement set"
                            + " sysOrgElement.fdNamePinYin = :fdNamePinYin"
                            + " , sysOrgElement.fdNameSimplePinyin = :fdNameSimplePinyin"
                            + " where sysOrgElement.fdId = :fdId";
                    Query queryTemp = getBaseDao().getHibernateSession()
                            .createQuery(sqlTemp).setString("fdNamePinYin",
                                    fdNamePinYin).setString(
                                    "fdNameSimplePinyin", fdNameSimplePinyin)
                            .setString("fdId", fdId);
                    queryTemp.executeUpdate();
                }
                long bachEndTime = System.currentTimeMillis();
                logger.debug("更新第" + count + "批次,耗时"
                        + (bachEndTime - bachBeginTime) + "毫秒");
                TransactionUtils.getTransactionManager().commit(status);
            } while (sysOrgElementList.size() > 0);
            long endTime = System.currentTimeMillis();
            logger.debug("数据更新共用时" + (endTime - beginTime) + "毫秒");
        } catch (Exception e) {
            TransactionUtils.getTransactionManager().rollback(status);
            logger.warn("升级组织架构数据出现异常" + e);
        } finally {

        }
    }

    /**
     * 获取当前operatorId登录日志信息:这里的登录日志1分钟内相同登录日志需去重。
     */
    @Override
    public List<SysLogLoginForm> getLoginLogByOperatorList(String operatorId,
                                                           int count, String orderBy) throws Exception {
        // 由于各种数据库支持的函数不一样，这里做一个通用的处理，逻辑如下：
        // 默认获取100条数据，按创建时间倒序排序，在程序中格式化分钟后做去重处理，如果去重后的数量还不够，再获取一些数据来处理
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setModelName("com.landray.kmss.sys.log.model.SysLogLogin");
        hqlInfo.setWhereBlock("sysLogLogin.fdOperatorId = :fdOperatorId and sysLogLogin.fdType = :fdType");
        hqlInfo.setParameter("fdOperatorId", operatorId);
        hqlInfo.setParameter("fdType", SysLogLogin.TYPE_SUCCESS);
        hqlInfo.setOrderBy(orderBy);
        hqlInfo.setRowSize(100);
        int page = 1;
        List<SysLogLoginForm> formList = new ArrayList<SysLogLoginForm>();
        Set<String> dteStrList = new HashSet<String>();
        while (true) {
            hqlInfo.setPageNo(page);
            List<SysLogLogin> list = this.findPage(hqlInfo).getList();
            if (CollectionUtils.isEmpty(list)) {
                // 没有更多数据
                break;
            }
            boolean hasAdd = false;
            for (SysLogLogin sysLogLogin : list) {
                String dteStr = DateUtil.convertDateToString(sysLogLogin.getFdCreateTime(), null,
                        UserUtil.getKMSSUser().getLocale());
                if (!dteStrList.contains(dteStr)) { // 去掉重复的记录
                    SysLogLoginForm sysLogLoginForm = new SysLogLoginForm();
                    sysLogLoginForm.setFdCreateTime(dteStr);
                    sysLogLoginForm.setFdId(sysLogLogin.getFdId());
                    sysLogLoginForm.setFdOperator(sysLogLogin.getFdOperator());
                    sysLogLoginForm.setFdOperatorId(sysLogLogin.getFdOperatorId());
                    sysLogLoginForm.setFdIp(sysLogLogin.getFdIp());
                    sysLogLoginForm.setFdBrowser(sysLogLogin.getFdBrowser());
                    sysLogLoginForm.setFdLocation(sysLogLogin.getFdLocation());
                    sysLogLoginForm.setFdVerification(sysLogLogin.getFdVerification());
                    sysLogLoginForm.setFdEquipment(sysLogLogin.getFdEquipment());
                    formList.add(sysLogLoginForm);
                    dteStrList.add(dteStr);
                    hasAdd = true;
                }
                // 数据取够了，返回
                if (formList.size() >= count) {
                    return formList;
                }
            }
            if (!hasAdd) {
                // 循环了100次，也拿不到1条数据，直接返回
                break;
            }
            // 如果返回的数量不够100条，可能已经没有数据了，可以返回
            if (list.size() < 100) {
                break;
            }
            page++;
        }
        return formList;
    }

    // 获取当前operatorId操作日志的敏感信息
    @Override
    public List<SysLogChangePwdForm> getSysLogChangePwdList(String operatorId,
                                                            int count, String orderBy) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        String hql = " sysLogChangePwd.fdOperatorId=:fdOperatorId ";
        hqlInfo.setModelName("com.landray.kmss.sys.log.model.SysLogChangePwd");
        hqlInfo.setWhereBlock(hql);
        hqlInfo.setParameter("fdOperatorId", operatorId);
        hqlInfo.setOrderBy(orderBy);
        hqlInfo.setPageNo(0);
        hqlInfo.setRowSize(count);
        List<SysLogChangePwd> sysLogChangePwdList = this.findPage(hqlInfo)
                .getList();
        List<SysLogChangePwdForm> sysLogChangePwdFormList = new ArrayList<SysLogChangePwdForm>();
        for (SysLogChangePwd sysLogChangePwd : sysLogChangePwdList) {
            SysLogChangePwdForm sysLogChangePwdForm = new SysLogChangePwdForm();
            sysLogChangePwdForm.setFdId(sysLogChangePwd.getFdId());
            sysLogChangePwdForm.setFdCreateTime(DateUtil.convertDateToString(
                    sysLogChangePwd.getFdCreateTime(), null, UserUtil.getKMSSUser().getLocale()));
            sysLogChangePwdForm.setFdIp(sysLogChangePwd.getFdIp());
            sysLogChangePwdForm.setFdOperatorId(sysLogChangePwd
                    .getFdOperatorId());
            sysLogChangePwdForm.setFdOperator(sysLogChangePwd.getFdOperator());
            sysLogChangePwdForm.setFdLocation(sysLogChangePwd.getFdLocation());
            sysLogChangePwdForm.setFdBrowser(sysLogChangePwd.getFdBrowser());
            sysLogChangePwdForm
                    .setFdEquipment(sysLogChangePwd.getFdEquipment());
            sysLogChangePwdForm.setFdDetails(sysLogChangePwd.getFdDetails());
            sysLogChangePwdFormList.add(sysLogChangePwdForm);
        }
        return sysLogChangePwdFormList;
    }

    @Override
    public void addImport(IBaseModel modelObj) throws Exception {
        // 检查是否重名
        if (!checkUniqueName(modelObj)) {
            throw builException(modelObj);
        }
        this.add(modelObj);
    }

    @Override
    public void updateImport(IBaseModel modelObj) throws Exception {
        // 检查是否重名
        if (!checkUniqueName(modelObj)) {
            throw builException(modelObj);
        }
        this.update(modelObj);
    }

    private Exception builException(IBaseModel modelObj) {
        SysOrgElement org = (SysOrgElement) modelObj;
        Exception e = new Exception(ResourceUtil.getString("sys.organization.mustUnique.error", "sys-organization", null, org.getFdName()));
        return e;
    }

    private boolean checkUniqueName(IBaseModel modelObj) throws Exception {
        SysOrgElement org = (SysOrgElement) modelObj;
        int type = org.getFdOrgType();
        String modelName = ModelUtil.getModelTableName(modelObj);
        boolean keepGroupUnique = false;
        try {
            keepGroupUnique = new SysOrganizationConfig().isKeepGroupUnique();
        } catch (Exception e) {
            logger.error(e.toString());
        }
        if (keepGroupUnique && (type & ~(ORG_TYPE_PERSON | ORG_TYPE_ROLE)) > 0) {
            String fdId = org.getFdId();
            String fdName = org.getFdName();
            HQLInfo hqlInfo = new HQLInfo();
            String hql = " " + modelName + ".fdName=:fdName ";
            hqlInfo.setParameter("fdName", fdName);
            if (StringUtil.isNotNull(fdId)) {
                hql += " and " + modelName + ".fdId!=:fdId ";
                hqlInfo.setParameter("fdId", fdId);
            }
            if ((type == 4) || (type == 16)) {// 这里是对岗位或群组名称的重复检测
                hql += " and " + modelName + ".fdOrgType!=8 and " + modelName + ".fdOrgType!=32 ";
            }
            hql = hql + " and " + modelName + ".fdIsAvailable = :fdIsAvailable "; // 1 表示有效的登录名 检测有效部分是否重名
            hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
            hqlInfo.setWhereBlock(hql);
            List list = findList(hqlInfo);
            if ((list != null) && (list.size() > 0)) {
                return false;
            }
        }
        return true;
    }

    /*********************************** 以下方法用于导入机制 ******************************************/
    // #36966 修改者：潘永辉 ，原因：导入数据包含“主关键字：Q全路径”时导入失败
    // 在调试中发现拼出来的HQL语句是这样的：sysOrgDept.fdParentsName=:prarm_0，param值：{"prarm_0":"机构名称_部门名称"}
    // 由于fdParentsName只是在程序中使用，在数据库并未有对应的字段，因此在导入过程出现异常
    // 导入机制中只是提供通用的操作，对于某些特殊的字段并没有提供单独的处理，所以这里提供特殊的字段处理

    /**
     * 主数据关键字属性查询条件，如果是查询“全路径”，这里需要过滤掉
     */
    @Override
    public String handlePrimaryKeyPropertyName(SysDictCommonProperty commonProperty) {
        String name = commonProperty.getName();
        if ("fdParentsName".equals(name)) {
            return null;
        }
        return name;
    }

    /**
     * 主数据关键字属性查询参数，如果是查询“全路径”，这里需要过滤掉
     */
    @Override
    public Object handlePrimaryKeyPropertyValue(
            SysDictCommonProperty commonProperty,
            Object value) {
        String name = commonProperty.getName();
        if ("fdParentsName".equals(name)) {
            return null;
        }
        return value;
    }

    /**
     * 导入数据时的具体数据，这里的做法是当导入数据有“全路径”作为主数据关键字属性时，需要获取导入时的“上级部门”来校验。
     * <p>
     * 一般说来，上级部门会以名称作为唯一主键查询，当名称有重复时，会造成导入的数据不准确，所以需要以“全路径”属性来作唯一校验
     */
    @Override
    public IBaseModel getModelByImportProperty(
            ImportContext context, ImportProperty importProperty,
            Row row, HQLInfo hqlInfo) throws Exception {

        // 获取本次处理的属性，只会对“上级部门”属性作处理
        if ("hbmParent".equals(importProperty.getProperty().getName())) {
            // 如果是有导入上级部门，则需要判断主键是否有“全路径”
            ImportProperty _importProperty = context.getKeyProperty();
            Object value = null;
            // 这段逻辑是获取导入行的“全路径”数据
            for (int i = 0; i < _importProperty
                    .getKeyColumnIndexes().length; i++) {
                if (_importProperty.getKeyColumnIndexes()[i] > -1
                        && "fdParentsName"
                        .equals(_importProperty.getKeyProperties()[i]
                                .getName())) {
                    value = ImportUtil.getCellValue(row
                                    .getCell((short) _importProperty
                                            .getKeyColumnIndexes()[i]),
                            _importProperty.getKeyProperties()[i], context
                                    .getLocale());
                    break;
                }
            }
            if (value != null) {
                // 路径上级部门的条件查询，可能会查询到多条数据
                List list = importProperty.getService().findList(hqlInfo);
                if (list != null && !list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        SysOrgElement parent = (SysOrgElement) list.get(i);
                        // 获取上级部门的全路径，与导入的“全路径”进行匹配
                        String parentsName = parent.getFdParentsName("/") + "/"
                                + parent.getFdName();
                        // 注意：导入时的“全路径”是针对上级部门，并不是针对导入的数据
                        if (value.equals(parentsName)) {
                            return parent;
                        }
                    }
                }

                // 如果导入时有“全路径”，而代码又执行到此行时，说明导入的“全路径”与“上级部门”不匹配，所以需要抛出异常
                throw new KmssException(new KmssMessage(
                        "sys-organization:sysOrgElement.transport.error.fdParentsName"));
            }
        }
        return null;
    }

    /**
     * 获取view页面的相关提示
     */
    @Override
    public String getViewPageNotice(SysTransportImportConfig config) {
        List<SysTransportPrimaryKeyProperty> list = config
                .getPrimaryKeyPropertyList();
        String notice = null;
        for (SysTransportPrimaryKeyProperty keyProperty : list) {
            if ("fdParentsName".equals(keyProperty.getFdName())) {
                notice = ResourceUtil.getString(
                        "sys-organization:sysOrgElement.transport.view.notice");
            }
        }
        return notice;
    }

    /**
     * 获取编辑页面的扩展的操作文件
     */
    @Override
    public String getEditExtendPath() {
        return "/sys/organization/transport_import_edit_operation.jsp";
    }

    /*********************************** 以上方法用于导入机制 ******************************************/

    @Override
    public List getDataList(RequestContext requestInfo) throws Exception {
        List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
        String type = requestInfo.getParameter("type");
        if ("1".equals(type)) { // 检查与业务相关的数据
            if (logger.isDebugEnabled()) {
                logger.debug("检查与业务相关的数据");
            }
            String fdId = requestInfo.getParameter("fdId");
            try {
                checkBusines((SysOrgElement) findByPrimaryKey(fdId));
            } catch (Exception e) {
                Map<String, String> nodeMap = new HashMap<String, String>();
                nodeMap.put("msg", ResourceUtil.getMessage("{sys-organization:sysOrgElement.isBusiness.error.existChildren}"));
                rtnList.add(nodeMap);
            }
        } else {
            String[] deptIds = requestInfo.getParameter("deptIds").split(";");
            for (String id : deptIds) {
                SysOrgElement elem = (SysOrgElement) findByPrimaryKey(id);
                Map<String, String> nodeMap = new HashMap<String, String>();
                nodeMap.put("id", id);
                nodeMap.put("name", elem.getDeptLevelNames());
                rtnList.add(nodeMap);
            }
        }

        return rtnList;
    }

    /**
     * 机构/部门设置与业务相关为“否”时，需要校验所有子级
     *
     * @param orgElement
     * @throws Exception
     */
    private void checkBusines(SysOrgElement orgElement) throws Exception {
        // 取数据库中的状态，如果原来的状态为“与业务相关”，则需要取所有的子级
        String sql = "select fd_is_business from sys_org_element where fd_id = :id and fd_is_available = :fdIsAvailable";
        List<?> list = getBaseDao().getHibernateSession().createNativeQuery(sql).setString("id", orgElement.getFdId()).setParameter("fdIsAvailable", Boolean.TRUE).list();
        if (logger.isDebugEnabled()) {
            logger.debug("部门/机构原状态(sql)[" + sql + "]，params[" + orgElement.getFdId() + "]");
            String _status = "";
            if (list != null && !list.isEmpty()) {
                _status = list.get(0).toString();
            }
            logger.debug("部门/机构原状态：" + _status);
        }
        // 当部门（机构）设置业务相关为“否”时，需要校验所有子级部门及其下的人员、岗位 不能含有业务相关为“是”的
        if (list != null && !list.isEmpty()) {
            String _status = list.get(0).toString();
            if ("true".equals(_status) || "1".equals(_status)) {
                sql = "select count(*) from sys_org_element where fd_org_type in (1,2,4,8) and fd_is_business = :isBusiness and fd_hierarchy_id like :hierarchyId";
                List<?> count = getBaseDao().getHibernateSession().createNativeQuery(sql).setParameter("isBusiness", Boolean.TRUE).setString("hierarchyId", orgElement.getFdHierarchyId() + "%").list();
                if (logger.isDebugEnabled()) {
                    logger.debug("部门/机构包含有业务相关的子级(sql)[" + sql + "]，params[1," + orgElement.getFdHierarchyId() + "%]");
                    logger.debug("部门/机构包含有业务相关的子级：" + count);
                }
                if (count != null && !count.isEmpty()) {
                    if (Integer.valueOf(count.get(0).toString()) > 1) {
                        throw new KmssException(new KmssMessage("sys-organization:sysOrgElement.isBusiness.error.existChildren"));
                    }
                }
            }
        }
    }

    public ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public JSONArray getParentOrgs(boolean isEcoPath, String fdId) throws Exception {
        JSONArray result = new JSONArray();
        if (StringUtil.isNotNull(fdId)) {
            SysOrgElement org = (SysOrgElement) findByPrimaryKey(fdId);
            if (org != null) {
                List<SysOrgElement> ls = new ArrayList<SysOrgElement>();
                getOrgOrgParents(ls, org);
                for (int i = ls.size() - 1; i >= 0; i--) {
                    SysOrgElement parent = ls.get(i);
                    JSONObject obj = new JSONObject();
                    obj.put("fdId", parent.getFdId());
                    obj.put("label", parent.getFdName());
                    obj.put("fdNo", parent.getFdNo());
                    obj.put("fdOrgType", parent.getFdOrgType());

                    if (SysOrgEcoUtil.IS_ENABLED_ECO) {
                        SysOrgElementRange range = parent.getFdRange();
                        if (range != null) {
                            obj.put("fdInviteUrl", range.getFdInviteUrl());
                        }
                    }

                    String adminStr = "";
                    List<SysOrgElement> list = parent.getAuthElementAdmins();
                    for (SysOrgElement sysOrgPerson : list) {
                        String fdName = sysOrgPerson.getFdName();
                        if (StringUtil.isNotNull(adminStr)) {
                            adminStr += ";";
                        }
                        adminStr += fdName;
                    }
                    obj.put("admin", adminStr);
                    result.add(obj);
                }

                JSONObject selfObj = new JSONObject();
                selfObj.put("fdId", org.getFdId());
                selfObj.put("label", org.getFdName());
                result.add(selfObj);
            }
        }
        return result;
    }

    private void getOrgOrgParents(List<SysOrgElement> ls, SysOrgElement sysOrgElement) {
        HttpServletRequest request = Plugin.currentRequest();
        String pid = null;
        if (request != null) {
            String deptLimit = request.getParameter("deptLimit");
            if (StringUtil.isNotNull(deptLimit)) {
                SysOrgPerson user = UserUtil.getUser();
                SysOrgElement parent = null;
                if ("myDept".equals(deptLimit)) {
                    parent = user.getFdParent();
                } else if ("myOrg".equals(deptLimit)) {
                    parent = user.getFdParentOrg();
                }
                if (parent != null) {
                    pid = parent.getFdId();
                }
            }
        }
        if (sysOrgElement.getFdParent() != null) {
            if (pid != null && pid.equals(sysOrgElement.getFdId())) {
                return;
            }
            ls.add(sysOrgElement.getFdParent());
            if (sysOrgElement.getFdParent().getFdParent() != null) {
                getOrgOrgParents(ls, sysOrgElement.getFdParent());
            }
        }
    }

    @Override
    public JSONArray getRecentContactList(String orgTypePara, String selectCount) throws Exception {
        return getRecentContactList(orgTypePara, selectCount, "");
    }

    @Override
    public JSONArray getRecentContactList(String orgTypePara, String selectCount, String exceptValue) throws Exception {
        return getRecentContactList(orgTypePara, selectCount, exceptValue, null, null);
    }

    @Override
    public JSONArray getDeptName(RequestContext requestContext) throws Exception {
        JSONArray result = new JSONArray();
        String deptId = requestContext.getParameter("deptIds");
        if (StringUtil.isNotNull(deptId)) {
            String[] deptIds = deptId.split(";");
            List<SysOrgElement> elems = findByPrimaryKeys(deptIds);
            for (SysOrgElement elem : elems) {
                JSONObject obj = new JSONObject();
                obj.put("id", elem.getFdId());
                obj.put("name", elem.getDeptLevelNames());
                result.add(obj);
            }
        }
        return result;
    }

    @Override
    public JSONArray getRecentContactList(String orgTypePara, String selectCount, String exceptValue, String cateId, String isExternal)
            throws Exception {
        JSONArray jsonArray = new JSONArray();
        SysOrgPerson person = UserUtil.getUser();
        if (person == null) {
            return jsonArray;
        }

        Boolean isExt = null;
        if (StringUtil.isNotNull(isExternal)) {
            isExt = Boolean.parseBoolean(isExternal);
        }

        int orgType = ORG_TYPE_DEFAULT;

        if (orgTypePara != null && !"".equals(orgTypePara)) {
            try {
                orgType = Integer.parseInt(orgTypePara);
            } catch (NumberFormatException e) {
            }
        }
        List elemList = null;

        if (StringUtil.isNotNull(selectCount) && selectCount.matches("\\d+")) {
            elemList = getSysOrganizationRecentContactService().findRecentContactsByCate(person.getFdId(), orgType,
                    StringUtil.getIntFromString(selectCount, 10), cateId, isExt);
        } else {
            elemList = getSysOrganizationRecentContactService().findRecentContactsByCate(person.getFdId(), orgType, cateId, isExt);
        }

        elemList = getSysOrganizationStaffingLevelService()
                .getStaffingLevelFilterResult(elemList);

        for (int i = 0; i < elemList.size(); i++) {
            JSONObject obj = new JSONObject();
            SysOrgElement org = (SysOrgElement) elemList.get(i);

            if (StringUtil.isNotNull(exceptValue) && exceptValue.indexOf(org.getFdId()) > -1) {
                continue;
            }

            obj.put("fdId", org.getFdId());
            obj.put("label", OrgDialogUtil.getDeptLevelNames(org));
            obj.put("labelLevel", org.getDeptLevelNames());
            obj.put("type", org.getFdOrgType());
            obj.put("isAvailable", org.getFdIsAvailable());
            obj.put("pinyin", org.getFdNamePinYin());
            obj.put("order", org.getFdOrder());
            obj.put("isExternal", org.getFdIsExternal().toString());
//            if (getOrgRangeService().checkAuth(org)) {
//                obj.put("isAllDept", "true");
//            } else {
//                obj.put("isAllDept", "false");
//            }

            String parentName = org.getFdParent() == null ? "" : org.getFdParentsName("_");
            obj.put("parentNames", parentName);

            // 针对无效的组织，避免出现重名无法区分，需要返回一些额外的信息（如编号），由于地址本原因，这里的额外信息使用parentName属性（原无效组织不存在parent信息）
            if (org.getFdIsAvailable() == null || !org.getFdIsAvailable()) {
                obj.put("parentNames", org.getFdNo());
            }

            if (SysOrgConstant.ORG_TYPE_PERSON == org.getFdOrgType()) {
                String img = "";
                if ("true".equals(SysFormDingUtil.getEnableDing())) {
                    img = PersonInfoServiceGetter.getPersonDingHeadimage(org
                            .getFdId(), null);
                } else {
                    img = PersonInfoServiceGetter.getPersonHeadimageUrl(org
                            .getFdId());
                }
				/*
				if (!PersonInfoServiceGetter.isFullPath(img)) {
					img = contextPath + img;
				}
				*/
                obj.put("icon", img);

                ISysOrgPersonService service = (ISysOrgPersonService) SpringBeanUtil
                        .getBean("sysOrgPersonService");

                SysOrgPerson p = (SysOrgPerson) service
                        .findByPrimaryKey(org.getFdId());

                String showStaffingLevel = new SysOrganizationConfig()
                        .getShowStaffingLevel();

                if (p.getFdStaffingLevel() != null
                        && "true".equals(showStaffingLevel)) {
                    obj.put("staffingLevel",
                            p.getFdStaffingLevel().getFdName());
                }

            }

            jsonArray.add(obj);
        }
        return jsonArray;
    }

    @Override
    public void setEventEco(Boolean flag) {
        eventEco.set(flag);
    }

    @Override
    public void removeEventEco() {
        eventEco.remove();
    }

    @Override
    public JSONObject getIconInfo(String orgId) throws Exception {
        SysOrgElement elem = (SysOrgElement) findByPrimaryKey(orgId);
        JSONObject rs = new JSONObject();
        rs.put("orgType", elem.getFdOrgType());
        if (elem.getFdOrgType() == 8) {
            String icon = "";
            if ("true".equals(SysFormDingUtil.getEnableDing())) {
                icon = PersonInfoServiceGetter.getPersonDingHeadimage(orgId,
                        null);
            } else {
                icon = PersonInfoServiceGetter.getPersonHeadimageUrl(orgId);
            }
            rs.put("icon", icon);
        }
        return rs;
    }

    /**
     * 根据类型查询组织架构主键列表
     *
     * @param tableName sys_org_element，hr_org_element
     * @param orderBy   例如：fd_alter_time desc
     * @param orgTypes  in的字符串格式 例如：'1','2','3' 如果为空则无查询条件
     * @return
     */
    @Override
    public List<String> getFdIdByOrgType(String tableName, String orderBy, List<Integer> orgTypes) {
        Connection conn = com.landray.kmss.sys.hibernate.spi.ConnectionWrapper.getInstance().getConnection(getBaseDao().openSession());
        List<String> fdIds = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            StringBuilder sql = new StringBuilder("select fd_id from ");
            sql.append(tableName).append(" ");
            if (orgTypes != null) {
                sql.append(" where fd_org_type in( ");
                for (int i = 0; i < orgTypes.size(); i++) {
                    if (i == orgTypes.size() - 1) {
                        sql.append("?");
                    } else {
                        sql.append("?,");
                    }
                }
                sql.append(")");
            }
            if (StringUtil.isNotNull(orderBy)) {
                sql.append("  order by  ").append(orderBy);
            }
            ps = conn.prepareStatement(sql.toString());
            if (orgTypes != null) {
                for (int i = 1; i <= orgTypes.size(); i++) {
                    ps.setInt(i, orgTypes.get(i - 1));
                }
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                fdIds.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            logger.error("查询组织架构的ID串失败：" + ex);
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
            JdbcUtils.closeConnection(conn);
        }
        return fdIds;
    }

    /**
     * 按批更新层级ID
     *
     * @param hqlInfo
     * @return
     * @throws Exception
     */
    private Integer updateRelationBatch(HQLInfo hqlInfo) throws Exception {
        TransactionStatus status = null;
        Exception t = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            List<SysOrgElement> elemList = findPage(hqlInfo).getList();
            if (elemList == null || elemList.isEmpty()) {
                return 0;
            }
            for (SysOrgElement elem : elemList) {
                elem.setHbmParentOrg(null);
                elem.setFdHierarchyId(HIERARCHY_ID_SPLIT + elem.getFdId() + HIERARCHY_ID_SPLIT);
                update(elem);
            }
            TransactionUtils.commit(status);
            return elemList.size();
        } catch (Exception e) {
            logger.error("批量更新层级关系失败：", e);
            t = e;
            throw e;
        } finally {
            if (t != null && status != null) {
                TransactionUtils.rollback(status);
            }
        }
    }

    @Override
    public void updateRelationBatch(Boolean isExternal) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        String hierarchyId = "concat(concat('" + HIERARCHY_ID_SPLIT + "', fdId), '" + HIERARCHY_ID_SPLIT + "')";
        // 查找层级ID不正确的机构和顶层部门
        String whereBlock = " fdIsExternal =:isExternal and fdIsAvailable=" + SysOrgHQLUtil.toBooleanValueString(true) + " and "
                + "(fdOrgType=1 or fdOrgType>1 and fdOrgType<16 and hbmParent is null) and "
                + "(hbmParentOrg is not null or fdHierarchyId!=" + hierarchyId + " or fdHierarchyId is null)";
        hqlInfo.setParameter("isExternal", isExternal);
        hqlInfo.setOrderBy("length(fdHierarchyId) asc");
        hqlInfo.setWhereBlock(whereBlock);
        hqlInfo.setRowSize(100);
        hqlInfo.setGetCount(false);
        hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
        int i = 0;
        // 循环处理，每批100条
        for (; i < 10000; i++) {
            Integer updateSize = updateRelationBatch(hqlInfo);
            if (updateSize == 0) {
                break;
            }
        }
        if (i == 10000) {
            throw new Exception("更新顶层数据层级ID的时候可能存在死循环");
        }

        hqlInfo = new HQLInfo();
        hqlInfo.setFromBlock("SysOrgElement sysOrgElement");
        hqlInfo.setJoinBlock("inner join sysOrgElement.hbmParent p");
        hqlInfo.setWhereBlock(
                "sysOrgElement.fdOrgType>1 and sysOrgElement.fdOrgType<16 and sysOrgElement.fdIsExternal =:isExternal and sysOrgElement.fdIsAvailable=" + SysOrgHQLUtil.toBooleanValueString(true) + " and "
                        + "(p.fdOrgType=1 and (sysOrgElement.hbmParentOrg is null or sysOrgElement.hbmParentOrg!=p) or "
                        + "p.fdOrgType>1 and (sysOrgElement.hbmParentOrg is null and p.hbmParentOrg is not null or "
                        + "sysOrgElement.hbmParentOrg is not null and p.hbmParentOrg is null or sysOrgElement.hbmParentOrg!=p.hbmParentOrg) or "
                        + "sysOrgElement.fdHierarchyId!=concat(concat(p.fdHierarchyId,sysOrgElement.fdId),'"
                        + HIERARCHY_ID_SPLIT + "'))");
        hqlInfo.setParameter("isExternal", isExternal);
        hqlInfo.setOrderBy("length(sysOrgElement.fdHierarchyId) asc");
        hqlInfo.setRowSize(100);
        hqlInfo.setGetCount(false);
        hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
        i = 0;
        for (; i < 100000; i++) {
            Integer updateSize = updateRelationBatch(hqlInfo);
            if (updateSize == 0) {
                break;
            }
        }
        if (i == 100000) {
            throw new Exception("更新层级ID的时候可能存在死循环");
        }
    }
}
