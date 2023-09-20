package com.landray.kmss.third.ding.oms;

import com.dingtalk.api.request.OapiSmartworkHrmEmployeeListRequest;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeListResponse;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeListResponse.EmpFieldInfoVO;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeListResponse.EmpFieldVO;
import com.dingtalk.api.response.OapiUserListbypageResponse.Userlist;
import com.dingtalk.api.response.OapiV2UserListResponse;
import com.google.common.collect.Sets;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.component.locker.interfaces.ConcurrencyException;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.*;
import com.landray.kmss.sys.organization.service.*;
import com.landray.kmss.sys.organization.util.PasswordUtil;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.util.*;
import com.landray.kmss.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionStatus;

import java.sql.BatchUpdateException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 钉钉到EKP组织架构同步（触发全量更新、启动增量同步）
 *
 * @TODO 性能优化, 按部门作事务提交
 */
public class SynchroOrgDing2EkpImp implements SynchroOrgDing2Ekp, DingConstant {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SynchroOrgDing2EkpImp.class);

    private SysQuartzJobContext jobContext = null;

    /*
     * 是否有钉钉人事接口的权限
     */
    private boolean hrmPermission = true;

    private static final int errorCount2Interrupt = 30;

    private Map<String, Integer> errorsCount = new ConcurrentHashMap<String, Integer>();

    private boolean interruptForErrors = false;

    private Map<String, String> relationMap = new HashMap<String, String>();
    private Map<String, String> loginNameMap = new HashMap<String, String>();
    private Map<String, String> mobileMap = new HashMap<String, String>();
    private List<String> allDingIds = new ArrayList<String>();
    private DingApiService dingApiService = DingUtils.getDingApiService();
    private boolean associatedExternalEnable = true; // 是否同步外部组织，默认同步
    private String initPassword;
    //private Map<String, JSONObject> createDeptMap = new HashMap<String, JSONObject>(); // 存放新建部门的信息，以便为“仅新增时同步”功能更新部门相关信息

    private String dingOrgId2ekp = ""; // 钉钉同步根部门id

    private IOmsRelationService omsRelationService;

    private IKmssPasswordEncoder passwordEncoder;

    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);

    public void setPasswordEncoder(IKmssPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setOmsRelationService(IOmsRelationService omsRelationService) {
        this.omsRelationService = omsRelationService;
    }

    private ISysOrgCoreService sysOrgCoreService;

    public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
        this.sysOrgCoreService = sysOrgCoreService;
    }

    private ISysOrgPersonService sysOrgPersonService;

    public void
        setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
        this.sysOrgPersonService = sysOrgPersonService;
    }

    private ISysOrgDeptService sysOrgDeptService;

    public void setSysOrgDeptService(ISysOrgDeptService sysOrgDeptService) {
        this.sysOrgDeptService = sysOrgDeptService;
    }

    private ISysOrgOrgService sysOrgOrgService;

    public void setSysOrgOrgService(ISysOrgOrgService sysOrgOrgService) {
        this.sysOrgOrgService = sysOrgOrgService;
    }

    private ISysOrgElementService sysOrgElementService;

    public void setSysOrgElementService(
            ISysOrgElementService sysOrgElementService) {
        this.sysOrgElementService = sysOrgElementService;
    }

    private ISysOrgElementExternalService sysOrgElementExternalService;
    private ISysOrgElementExternalService sysOrgElementExternalDeptService;

    public void setSysOrgElementExternalService(
            ISysOrgElementExternalService sysOrgElementExternalService) {
        this.sysOrgElementExternalService = sysOrgElementExternalService;
    }

    public void setSysOrgElementExternalDeptService(
            ISysOrgElementExternalService sysOrgElementExternalDeptService) {
        this.sysOrgElementExternalDeptService = sysOrgElementExternalDeptService;
    }

    private ISysOrgGroupService sysOrgGroupService = null;
    public ISysOrgGroupService getSysOrgGroupService() {
        if (sysOrgGroupService == null) {
            sysOrgGroupService = (ISysOrgGroupService) SpringBeanUtil
                    .getBean("sysOrgGroupService");
        }
        return sysOrgGroupService;
    }

    private SynchroOrgDingRole2Ekp synchroOrgDingRole2Ekp;
    public void setSynchroOrgDingRole2Ekp(SynchroOrgDingRole2Ekp synchroOrgDingRole2Ekp){
        this.synchroOrgDingRole2Ekp = synchroOrgDingRole2Ekp;
    }

    @Override
    public void generateMapping() throws Exception {
        String temp = "存在运行中的钉钉到EKP组织架构同步任务，当前任务中断...";
        SynchroInModel model = new SynchroInModel();
        try {
            getComponentLockService().tryLock(model, "omsIn");
            this.jobContext = new EmptyQuartzJobContext();
            if(!checkNeedSynchro()){
                return;
            }
            long time = System.currentTimeMillis();
            log("开始建立钉钉与EKP组织架构映射关系....");

            // 初始化数据
            long caltime = System.currentTimeMillis();
            init();
            temp = "初始化数据耗时(秒)："
                    + (System.currentTimeMillis() - caltime) / 1000;
            if(logger.isDebugEnabled()){
                logger.debug(temp);
            }

            // 处理钉钉到EKP组织架构
            caltime = System.currentTimeMillis();
            updateSyncOrgElements();
            temp = "处理钉钉到EKP组织架构耗时(秒)："
                    + (System.currentTimeMillis() - caltime) / 1000;
            if(logger.isDebugEnabled()){
                logger.debug(temp);
            }

            // 同步钉钉管理员
            String synDingAdmin = DingConfig.newInstance()
                    .getDingAdminSynEnabled();
            if (StringUtil.isNotNull(synDingAdmin)
                    && "true".equalsIgnoreCase(synDingAdmin)) {
                saveOrUpdateSynDingAdmin();
            }

            // 钉钉角色同步
            synchroOrgDingRole2Ekp.synchro(jobContext);

            temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - time) / 1000;
            if(logger.isDebugEnabled()){
                logger.debug(temp);
            }
            getComponentLockService().unLock(model);
        }catch(ConcurrencyException e){
            logger.error(temp);
            jobContext.logError(temp);
            throw e;
        }catch (Exception e) {
            getComponentLockService().unLock(model);
            throw e;
        } finally {
            relationMap.clear();
            mobileMap.clear();
            loginNameMap.clear();
            allDingIds.clear();
            hrmPermission = true;
            errorsCount.clear();
            interruptForErrors = false;
        }
    }

    /*
     * 同步钉钉管理员
     */
    private void saveOrUpdateSynDingAdmin() {
        if(logger.isDebugEnabled()){
            logger.debug("同步钉钉管理员到ekp");
        }
        log("同步钉钉管理员到ekp:");
        TransactionStatus status = null;
        try {
            JSONObject adminJSON = dingApiService.getAdmin();
            if(logger.isDebugEnabled()){
                logger.debug("钉钉管理员：" + adminJSON);
            }
            if (adminJSON == null || adminJSON.isEmpty()) {
                return;
            }
            logger.debug("开启事务");
            status = TransactionUtils.beginNewTransaction();
            List<String> idsList = new ArrayList();
            if (adminJSON.getInt("errcode") == 0) {
                JSONArray admins = adminJSON.getJSONArray("adminList");
                for (int i = 0; i < admins.size(); i++) {
                    JSONObject admin = admins.getJSONObject(i);
                    String admin_userid = admin.getString("userid");
                    addOrUdateDingAdmin(admin_userid, idsList);
                }
            }
            logger.warn("relationMap:" + relationMap);
            logger.warn("idsList.size:" + idsList.size());
            log("钉钉管理员的fdId:" + idsList.toString());
            //加入群组中
            List<SysOrgElement> sysOrgElementList = sysOrgElementService
                    .findByPrimaryKeys(idsList.toArray(new String[0]));

            SysOrgGroup group = null;
            String groupId = DingConfig.newInstance().getDingAdminGroupId();
            if (StringUtil.isNotNull(groupId)) {
                log("优先根据groupId(" + groupId + ")找对应的群组...");
                group = (SysOrgGroup) sysOrgElementService
                        .findByPrimaryKey(groupId, SysOrgGroup.class, true);
            }
            if (group == null) {
                log("根据名称  ‘系统级_子管理员’找对应的群组...");
                if(logger.isDebugEnabled()){
                    logger.debug("根据名称  ‘系统级_子管理员’找到对应的群组");
                }
                HQLInfo hql = new HQLInfo();
                hql.setWhereBlock("fdName=:fdName");
                hql.setParameter("fdName", "系统级_子管理员");
                SysOrgGroup result = (SysOrgGroup) sysOrgElementService.findFirstOne(hql);
                if (result != null) {
                    group = result;
                }
            }

            if (group == null) {
                log("找不到  '系统级_子管理员'  群组，无法将管理员同步到该群组");
                logger.warn("找不到  '系统级_子管理员'  群组，无法将管理员同步到该群组");
                return;
            }
            group.setFdMembers(sysOrgElementList);
            sysOrgElementService.update(group);
            logger.debug("提交事务");
            TransactionUtils.commit(status);
        } catch (Exception e) {
            logger.error("同步钉钉管理员失败：" + e.getMessage(), e);
            if (status != null) {
                try {
                    logger.debug("回滚事务");
                    TransactionUtils.rollback(status);
                } catch (Exception ex) {
                    logger.error("---事务回滚出错---", ex);
                }
            }
        }
    }

    // 添加用户到 群组中
    private void addUser2Gruop(SysOrgElement user, SysOrgGroup group)
            throws Exception {
        if (group == null || user == null) {
            return;
        }
        List<SysOrgElement> newElement = new ArrayList<SysOrgElement>();
        List<SysOrgElement> sysOrgElements = group.getFdMembers();
        if (sysOrgElements == null || sysOrgElements.isEmpty()) {
            newElement.add(user);
            group.setFdMembers(newElement);
        } else {
            // 根据fdId判断是否群组含有该用户
            boolean hasInGroup = false;
            for (SysOrgElement element : sysOrgElements) {
                if (user.getFdId().equals(element.getFdId())) {
                    hasInGroup = true;
                    logger.warn("群组含有该用户了，不用添加了");
                    return;
                }
            }
            sysOrgElements.add(user);
            group.setFdMembers(sysOrgElements);
        }
        sysOrgElementService.getBaseDao().getHibernateSession().update(group);
    }

    // 获取 '系统级_子管理员'群组
    private SysOrgGroup getAdminGruop()
            throws Exception {
        SysOrgGroup group = null;
        String groupId = DingConfig.newInstance().getDingAdminGroupId();
        if (StringUtil.isNotNull(groupId)) {
            group = (SysOrgGroup) sysOrgElementService
                    .findByPrimaryKey(groupId, SysOrgElement.class, true);
        }
        if (group == null) {
            if(logger.isDebugEnabled()){
                logger.debug("根据名称  ‘系统级_子管理员’找到对应的群组");
            }
            HQLInfo hql = new HQLInfo();
            hql.setWhereBlock("fdName=:fdName");
            hql.setParameter("fdName", "系统级_子管理员");
            SysOrgGroup result = (SysOrgGroup) sysOrgElementService.findFirstOne(hql);
            if (result != null) {
                group = result;
            }
        }
        return group;
    }

    // 移除管理员到 '系统级_子管理员'群组
    private void removeUserFromGruop(SysOrgElement user, SysOrgGroup group)
            throws Exception {
        if (group == null || user == null) {
            return;
        }
        List<SysOrgElement> sysOrgElements = group.getFdMembers();
        if (sysOrgElements != null && !sysOrgElements.isEmpty()) {
            for (SysOrgElement ele : sysOrgElements) {
                if (user.getFdId().equals(ele.getFdId())) {
                    sysOrgElements.remove(ele);
                    group.setFdMembers(sysOrgElements);
                    break;
                }
            }
        }
        sysOrgElementService.getBaseDao().getHibernateSession().update(group);
    }

    // 处理单个钉钉管理员
    private void addOrUdateDingAdmin(String admin_userid, List idsList) {
        logger.warn("relationMap:" + relationMap);
        try {
            if (relationMap.containsKey("admin_userid" + "|8")) {
                if(logger.isDebugEnabled()){
                    logger.debug("更新管理员信息：" + admin_userid);
                }
                log("更新管理员信息：" + admin_userid);
                String fdId = relationMap.get(admin_userid + "|8");
                logger.warn("管理员的fdId:" + fdId);
            } else {
                // 当前ekp无该管理员的信息
                if(logger.isDebugEnabled()){
                    logger.debug("新增管理员信息：" + admin_userid);
                }
                log("新增管理员信息：" + admin_userid);
                updateUser(dingApiService.userGet(admin_userid, null), true,
                        null,
                        getDingMainDeptId(dingApiService.getAccessToken(),
                                admin_userid),false);
            }
            if (relationMap.containsKey(admin_userid + "|8")) {
                idsList.add(relationMap.get(admin_userid + "|8"));
            } else {
                logger.error("处理单个钉钉管理员  fail...");
                log("处理单个钉钉管理员(userid:" + admin_userid + ")  fail...");
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * 处理钉钉管理员的回调 org_admin_add org_admin_remove
     *
     * @param admin_userid 钉钉userId
     * @param isAdd        true:新增 ；false:删除
     */
    @Override
    public void saveOrUpdateCallbackDingAdmin(String admin_userid,
                                              boolean isAdd) {
        try {
            String ekpUserId = omsRelationService
                    .getEkpUserIdByDingUserId(admin_userid);
            if(logger.isDebugEnabled()){
                logger.debug("ekpUserId:" + ekpUserId);
            }
            if (StringUtil.isNull(ekpUserId) && isAdd) {
                // 用户没有则新增
                JSONObject dingUser = dingApiService.userGet(admin_userid,
                        null);
                saveOrUpdateCallbackUser(dingUser, true);
                if (relationMap.containsKey(admin_userid + "|8")) {
                    if(logger.isDebugEnabled()){
                        logger.debug("-=======新增了管理员======" + admin_userid);
                    }
                    ekpUserId = relationMap.get(admin_userid + "|8");
                }
            }
            if(logger.isDebugEnabled()){
                logger.debug("final ekpUserId:" + ekpUserId);
            }
            if (StringUtil.isNull(ekpUserId)) {
                return;
            }
            SysOrgElement user = (SysOrgElement) sysOrgElementService
                    .findByPrimaryKey(ekpUserId);
            if (isAdd) {
                // 新增
                addUser2Gruop(user, getAdminGruop());
            } else {
                // 删除   (移出'系统级_子管理员'列表)
                removeUserFromGruop(user, getAdminGruop());
            }
        } catch (Exception e) {
            logger.error("处理钉钉管理员的回调过程中发生异常：  " + e.getMessage(), e);
        }
    }

    IComponentLockService componentLockService = null;

    private IComponentLockService getComponentLockService() {
        if (componentLockService == null) {
            componentLockService = (IComponentLockService) SpringBeanUtil
                    .getBean("componentLockService");
        }
        return componentLockService;
    }

    @Override
    public void triggerSynchro(SysQuartzJobContext context) {
        String temp = null;
        SynchroInModel model = new SynchroInModel();
        try {
            this.jobContext = context;
            long time = System.currentTimeMillis();
            getComponentLockService().tryLock(model, "omsIn");
            if(!checkNeedSynchro()){
                return;
            }
            log("开始钉钉到EKP组织架构同步....");

            DingConfig dingConfig = DingConfig.newInstance();
            // 初始化数据
            long caltime = System.currentTimeMillis();
            init();
            temp = "初始化数据耗时(秒)："
                    + (System.currentTimeMillis() - caltime) / 1000;
            if(logger.isDebugEnabled()){
                logger.debug(temp);
            }
            context.logMessage(temp);

            // 处理钉钉到EKP组织架构
            caltime = System.currentTimeMillis();
            updateSyncOrgElements();
            temp = "处理钉钉到EKP组织架构耗时(秒)："
                    + (System.currentTimeMillis() - caltime) / 1000;
            if(logger.isDebugEnabled()){
                logger.debug(temp);
            }
            context.logMessage(temp);

            // 同步钉钉管理员
            String synDingAdmin = dingConfig.getDingAdminSynEnabled();
            if (StringUtil.isNotNull(synDingAdmin)
                    && "true".equalsIgnoreCase(synDingAdmin)) {
                saveOrUpdateSynDingAdmin();
            } else {
                logger.warn("同步钉钉管理员开关未开启，不同步钉钉管理员：" + synDingAdmin);
            }

            synchroOrgDingRole2Ekp.synchro(jobContext);

            temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - time) / 1000;
            if(logger.isDebugEnabled()){
                logger.debug(temp);
            }
            context.logMessage(temp);
            getComponentLockService().unLock(model);
        } catch(ConcurrencyException e){
            temp = "存在运行中的钉钉到EKP组织架构同步任务，当前任务中断...";
            logger.error(temp);
            jobContext.logError(temp);
        } catch (Exception e) {
            logger.error("钉钉到EKP组织架构同步失败：", e);
            jobContext.logError(e.getMessage());
            getComponentLockService().unLock(model);
        } finally {
            relationMap.clear();
            mobileMap.clear();
            loginNameMap.clear();
            allDingIds.clear();
        }
    }

    /**
     * 检验ekp根机构id是否有效
     */
    private void checkEkpRootIdConfig() throws Exception {
        DingConfig dingConfig = DingConfig.newInstance();
        //ekp根机构id
        String ekpRootOrgId = dingConfig.getDingInOrgId();
        boolean isAvailabed = StringUtils.isBlank(ekpRootOrgId);
        if(!isAvailabed){
            isAvailabed = (ekpRootOrgId.indexOf(";") == -1);
            if(isAvailabed){
                try{
                    HQLInfo hqlInfo = new HQLInfo();
                    hqlInfo.setSelectBlock("fdId");
                    hqlInfo.setWhereBlock("fdId=:id");
                    hqlInfo.setParameter("id", ekpRootOrgId);
                    String fdId = (String) sysOrgElementService.findFirstOne(hqlInfo);
                    isAvailabed = ekpRootOrgId.equals(fdId);
                }
                catch (Exception e){
                    isAvailabed = false;
                    logger.error(e.getMessage(), e);
                }
            }
            if(!isAvailabed){
                throw new Exception("ekp根机构Id无效");
            }
        }
    }

    /**
     * 判断是否启用了钉钉到EKP的组织同步
     * @return
     */
    private boolean checkNeedSynchro(){
        String temp = null;
        if (!"true".equals(DingConfig.newInstance().getDingEnabled())) {
            temp = "钉钉集成已经关闭，故不同步数据";
            if(logger.isDebugEnabled()){
                logger.debug(temp);
            }
            jobContext.logMessage(temp);
            return false;
        }
        if (StringUtil.isNotNull(DingConfig.newInstance().getSyncSelection())) {
            if (!"2".equals(DingConfig.newInstance().getSyncSelection())) {
                temp = "钉钉组织架构接入已经关闭，故不同步数据";
                if(logger.isDebugEnabled()){
                    logger.debug(temp);
                }
                jobContext.logMessage(temp);
                return false;
            }
        } else {
            if (!"true"
                    .equals(DingConfig.newInstance().getDingOmsInEnabled())) {
                temp = "钉钉组织架构接入已经关闭，故不同步数据";
                if(logger.isDebugEnabled()){
                    logger.debug(temp);
                }
                jobContext.logMessage(temp);
                return false;
            }
        }
        return true;
    }

    @Override
    public void triggerSynchro() throws Exception {
        String temp = null;
        SynchroInModel model = new SynchroInModel();
        try {
            getComponentLockService().tryLock(model, "omsIn");
            this.jobContext = new EmptyQuartzJobContext();
            if(!checkNeedSynchro()){
                return;
            }
            long time = System.currentTimeMillis();
            log("开始钉钉到EKP组织架构同步....");

            // 初始化数据
            long caltime = System.currentTimeMillis();
            init();
            temp = "初始化数据耗时(秒)："
                    + (System.currentTimeMillis() - caltime) / 1000;
            if(logger.isDebugEnabled()){
                logger.debug(temp);
            }

            // 处理钉钉到EKP组织架构
            caltime = System.currentTimeMillis();
            updateSyncOrgElements();
            temp = "处理钉钉到EKP组织架构耗时(秒)："
                    + (System.currentTimeMillis() - caltime) / 1000;
            if(logger.isDebugEnabled()){
                logger.debug(temp);
            }

            // 同步钉钉管理员
            logger.warn("--------------------");
            String synDingAdmin = DingConfig.newInstance()
                    .getDingAdminSynEnabled();
            if (StringUtil.isNotNull(synDingAdmin)
                    && "true".equalsIgnoreCase(synDingAdmin)) {
                saveOrUpdateSynDingAdmin();
            }

            temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - time) / 1000;
            if(logger.isDebugEnabled()){
                logger.debug(temp);
            }
            getComponentLockService().unLock(model);
        } catch(ConcurrencyException e){
            temp = "存在运行中的钉钉到EKP组织架构同步任务，当前任务中断...";
            logger.error(temp,e);
            jobContext.logError(temp, e);
        } catch (Exception e) {
            logger.error("钉钉到EKP组织架构同步失败：", e);
            getComponentLockService().unLock(model);
        } finally {
            relationMap.clear();
            postMap.clear();
            personMap.clear();
            mobileMap.clear();
            loginNameMap.clear();
            allDingIds.clear();
            hrmPermission = true;
            errorsCount.clear();
            interruptForErrors = false;
        }
    }

    private void init() throws Exception {
        checkEkpRootIdConfig();
        log("------开始初始化任务------");
        hrmPermission = true;
        errorsCount.clear();
        interruptForErrors = false;
        DingConfig dingConfig = DingConfig.newInstance();
        if ("false".equals(dingConfig.getAssociatedExternalEnabled())) {
            associatedExternalEnable = false;
            log("不接入钉钉端外部组织");
        } else {
            associatedExternalEnable = true;
            log("接入钉钉端外部组织！！！");
        }

        // 查询钉钉同步根机构信息
        dingOrgId2ekp = dingConfig.getDingOrgId2ekp();
        if (StringUtil.isNotNull(dingOrgId2ekp)) {
            log("钉钉同步根机构id为：" + dingOrgId2ekp);
        } else {
            log("钉钉同步根机构id为空，默认同步钉钉通讯录所有组织!");
        }
        // 清洗重复的数据
        SynchroOrgDing2EkpUtil.updateHandlerRepeatData(this.jobContext);
        SynchroOrgDing2EkpUtil.updateRelationType(this.jobContext);
        SynchroOrgDing2EkpUtil.deleteInvalidRelationRecords(this.jobContext);

        //预加载数据
        SynchroOrgDing2EkpUtil.initRelationsMap(this.jobContext, this.relationMap);
        SynchroOrgDing2EkpUtil.initLoginNameMap(this.jobContext, this.loginNameMap);
        SynchroOrgDing2EkpUtil.initMobileMap(this.jobContext, this.mobileMap);
        SynchroOrgDing2EkpUtil.initPersonMap(this.jobContext, this.personMap);
        SynchroOrgDing2EkpUtil.initPostMap(this.jobContext, this.postMap);

        // 查询初始密码
        this.initPassword = SynchroOrgDing2EkpUtil.getInitPassword();
        log("------结束初始化任务------");
    }

    /**
     * 处理钉钉到EKP组织架构
     */
    private void updateSyncOrgElements() throws Exception {
        JSONObject ret = SynchroOrgDing2EkpUtil.getDingDeptData(this.jobContext, this.dingOrgId2ekp);
        if (ret == null) {
            log("获取钉钉部门列表发生不可预知的错误");
        } else {
            log("开始处理钉钉到EKP组织架构");
            if(logger.isDebugEnabled()){
                logger.debug("钉钉同步的部门：" + ret);
            }
            if (ret.getInt("errcode") == 0) {
                JSONArray depts = ret.getJSONArray("department");
                syncDepts(depts);
                syncUsers(depts);
                cleanEkpData();
                cleanExtraEkpData();
            } else {
                log(" 失败,出错信息：" + ret.getString("errmsg"));
            }
        }
    }

    /**
     * 同步部门
     * @param depts
     * @throws Exception
     */
    private void syncDepts(JSONArray depts) throws Exception {
        Map<String, JSONObject> deptMaps = new HashMap<String, JSONObject>(depts.size());
        List<String> hiers = new ArrayList<String>();
        //分析钉钉端数据,并将结果存入map变量中
        SynchroOrgDing2EkpUtil.analysisDingDeptData(depts, deptMaps, hiers, this.allDingIds,
                this.associatedExternalEnable, this.dingOrgId2ekp);
        JSONObject jdept;
        log("从钉钉获取部门数:" + depts.size() + "(包括关联的外部组织在内)");
        log("开始处理部门数据......");
        for (String deptId : hiers) {
            jdept = deptMaps.get(deptId.split("\\|")[0]);
            updateDept(jdept, false, false);
            if (interruptForErrors) {
                throw new Exception("同个错误出现次数过多，同步中断");
            }
        }
        log("结束处理部门数据");
    }

    /**
     * 同步人员
     * @param depts
     * @throws Exception
     */
    private void syncUsers(JSONArray depts) throws Exception {
        log("开始处理钉钉用户数据......");
        for (Object obj : depts) {
            JSONObject dept = (JSONObject) obj;
            updateUsers(dept);
        }
    }

    /**
     * 清理钉钉中已删除的数据（跟映射表对比）
     * @throws Exception
     */
    private void cleanEkpData() throws Exception {
        log("结束处理钉钉用户数据");
        // 钉钉端不存在的映射关系处理
        Set<String> ekpIds = Sets.newHashSet(relationMap.keySet());
        Set<String> dingIds = Sets.newHashSet(allDingIds);
        if (StringUtil.isNull(dingOrgId2ekp)) {
            // 判断总数是否正确
            JSONObject o = dingApiService.getOrgUserCount(false);
            if (o.getInt("errcode") == 0) {
                int count = o.getInt("count");
                if (count != dingIds.size()) {
                    logger.error("两边的用户数不一致，钉钉中当前用户数：" + count
                            + "，ekp同步过程中获取到的用户数：" + dingIds.size());
                }
            }
        }
        Set<String> diff_ekpIds = Sets.difference(ekpIds, dingIds);
        log("清理钉钉端不存在的映射关系处理:" + diff_ekpIds);
        for (String dingId : diff_ekpIds) {
            cleanData(dingId);
        }
    }


    /**
     * 清理ekp中比钉钉多的数据（非从钉钉同步过来的）
     */
    private void cleanExtraEkpData() throws Exception {
        // ekp中存在而钉钉中不存在的信息处理
        String orgHandle = DingConfig.newInstance()
                .getDing2ekpOrgHandle();
        if ("autoDisable".equals(orgHandle)) {
            if(logger.isDebugEnabled()){
                logger.debug(
                        "==========设置了钉钉到ekp全量同步时删除ekp中的多余的组织架构数据(该数据钉钉端不存在)========");
            }
            omsRelationService.deleteEkpOrg();
        } else {
            if(logger.isDebugEnabled()){
                logger.debug(
                        "==========设置了钉钉到ekp全量同步时对ekp中的多余的组织架构数据(该数据钉钉端不存在)不处理=========");
            }
        }
        log("成功处理钉钉到EKP组织架构");
    }

    /**
     * 设置部门可见范围
     * @param element
     * @param orgOrDept
     */
    private void updateDeptRange(JSONObject element, SysOrgElement orgOrDept) throws Exception {
        // 限制本部门成员查看通讯录
        Boolean outerDept = (Boolean) element.get("outerDept");
        // 可见人员
        String outerPermitUsers = (String) element
                .get("outerPermitUsers");
        // 可见部门
        String outerPermitDepts = (String) element
                .get("outerPermitDepts");
        SysOrgElementRange range = orgOrDept.getFdRange();
        if (range == null) {
            range = new SysOrgElementRange();
            range.setFdId(orgOrDept.getFdId());
            range.setFdElement(orgOrDept);
            range.setFdIsOpenLimit(false);
            range.setFdViewType(1);
            orgOrDept.setFdRange(range);
        }
        if (Boolean.TRUE.equals(outerDept)) {
            // 开启可见性配置
            if (StringUtil.isNull(outerPermitUsers)
                    && StringUtil.isNull(outerPermitDepts)) {
                // 仅自己可见
                range.setFdViewType(0);
            } else if (StringUtil.isNull(outerPermitUsers)
                    && element.getString("id").equals(outerPermitDepts)) {
                // 只能看到所在部门及下级部门通讯录
                range.setFdViewType(1);
            } else if (StringUtil.isNotNull(outerPermitUsers)
                    || StringUtil.isNotNull(outerPermitDepts)) {
                // 指定部门或人员可见
                range.setFdViewType(2);
            }
        }

        //部门隐藏可见属性 ......start ........
        Boolean deptHiding = (Boolean) element.get("deptHiding");
        // 隐藏后可见部门
        String deptPermits = (String) element.get("deptPermits");
        // 隐藏后可见人员
        String userPermits = (String) element.get("userPermits");
        SysOrgElementHideRange hideRange = orgOrDept.getFdHideRange();
        //默认对所有人可见
        if (hideRange == null) {
            hideRange = new SysOrgElementHideRange();
            hideRange.setFdId(orgOrDept.getFdId());
            hideRange.setFdElement(orgOrDept);
            hideRange.setFdIsOpenLimit(false);
            hideRange.setFdViewType(1);
            orgOrDept.setFdHideRange(hideRange);
        }
        if (Boolean.TRUE.equals(deptHiding)) {
            // 开启可见性配置
            if (StringUtil.isNotNull(deptPermits) || StringUtil.isNotNull(userPermits)) {
                hideRange.setFdIsOpenLimit(true);
                List<String> sysOrgElementIdList = new ArrayList<>();
                // 对部分部门处理
                if (StringUtil.isNotNull(deptPermits)) {
                    String[] deptSplit = deptPermits.split("\\|");
                    for(String dept :deptSplit){
                        String ekpId = relationMap.get(dept + "|2");
                        if(StringUtil.isNotNull(ekpId)){
                            sysOrgElementIdList.add(ekpId);
                        }
                    }
                }
                // 对部分人员进行处理
                if(StringUtil.isNotNull(userPermits)){
                    String[] userSplit = userPermits.split("\\|");
                    for(String user :userSplit){
                        String ekpId = relationMap.get(user + "|8");
                        if(StringUtil.isNotNull(ekpId)){
                            sysOrgElementIdList.add(ekpId);
                        }
                    }
                }
                if(CollectionUtils.isNotEmpty(sysOrgElementIdList)) {
                    HQLInfo hqlInfo = new HQLInfo();
                    String where = "sysOrgElement.fdId in (:fdIds)";
                    hqlInfo.setWhereBlock(where);
                    hqlInfo.setParameter("fdIds", sysOrgElementIdList);
                    List<SysOrgElement> list = sysOrgElementService.findList(hqlInfo);
                    hideRange.setFdOthers(list);
                }
            }
        }
        //部门隐藏可见属性 ......end......
    }

    /**
     * 处理单个钉钉部门信息
     *
     * @throws Exception
     */
    private void updateDept(JSONObject element, boolean flag, boolean callback)
            throws Exception {
        log("开始处理单个钉钉部门【"+element.getString("name")+"】信息......");
        TransactionStatus status = null;
        Throwable t = null;
        try {
            if (element.toString().contains("isFromUnionOrg")) {
                if (!associatedExternalEnable) {
                    return;
                }
            }
            logger.debug("开启事务");
            status = TransactionUtils.beginNewTransaction(300);
            String deptId = element.getString("id");
            String name = element.getString("name");
            String msg = "\t开始同步部门：";
            SysOrgElement orgOrDept = null;
            logger.debug("部门信息：" + element.toString());
            synchronized (globalLock) {
                String addOrUpdate = "add";

                orgOrDept = getSysOrgDept(deptId, true);
                if (orgOrDept != null) {
                    addOrUpdate = "update";
                    updateDept(orgOrDept, element, callback, "update");
                    log(msg + "更新部门(名称=" + name + ",dingId=" + deptId
                            + ",EKPId=" + orgOrDept.getFdId() + ")");
                }else {
                    orgOrDept = new SysOrgDept();
                    addOrUpdate = "add";
                    updateDept(orgOrDept, element, callback, "add");
                    log(msg + "新增部门(名称=" + name + ",dingId=" + deptId
                            + ",EKPId=" + orgOrDept.getFdId()
                            + ")并建立映射关系和对应的岗位");
                    sysOrgDeptService.add(orgOrDept);
                }
                if (orgOrDept != null) {
                    updateRelation(element.getString("id"), orgOrDept.getFdId(),
                            "2", element);
                    updateDeptRange(element,orgOrDept);

                    // 判断是否生态组织
                    boolean fdIsExternal = orgOrDept.getFdIsExternal()
                            .booleanValue();
                    SysOrgElement fdParent = orgOrDept.getFdParent();
                    if (fdParent != null) {
                        // 根据上级部门判断是否为生态组织
                        fdIsExternal = fdParent.getFdIsExternal();
                    }
                    if (fdIsExternal) {
                        // 生态组织时，获取所属生态组织类型
                        // 标识为生态组织
                        orgOrDept.setFdIsExternal(true);
                        // 允许查看组织范围映射
                        orgOrDept.getFdRange().setFdIsOpenLimit(true);
                    }

                    // 构建F4的岗位信息(主管和成员)
                    if (orgOrDept.getFdOrgType() == 2) {
                        sysOrgDeptService.update(orgOrDept);
                    } else if (orgOrDept.getFdOrgType() == 1) {
                        sysOrgOrgService.update(orgOrDept);
                    }

                    // 同步部门主管
                    String deptLeaderSynWay = DingConfig.newInstance()
                            .getDing2ekpDeptLeaderSynWay();
                    if(logger.isDebugEnabled()){
                        logger.debug("deptLeaderSynWay:" + deptLeaderSynWay);
                    }
                    if (StringUtil.isNotNull(deptLeaderSynWay)) {
                        if(logger.isDebugEnabled()){
                            logger.debug("新配置方式处理部门主管");
                        }
                        if (("add".equals(addOrUpdate)
                                && !"noSyn".equals(deptLeaderSynWay))
                                || ("update".equals(addOrUpdate)
                                && "syn".equals(deptLeaderSynWay))) {
                            SysOrgPost post = addPost(name, deptId, "leader",
                                    orgOrDept);
//                            getSysOrgPostService().getBaseDao()
//                                    .getHibernateSession().merge(post);

                        }

                    } else {
                        if(logger.isDebugEnabled()){
                            logger.debug("旧方式处理部门主管");
                        }
                        if ("true".equals(DingConfig.newInstance()
                                .getDingOmsInDeptManagerEnabled())) {
                            SysOrgPost post = addPost(name, deptId, "leader",
                                    orgOrDept);
//                            getSysOrgPostService().getBaseDao()
//                                    .getHibernateSession().merge(post);
                        }
                    }

                    // 一人多部门
                    String deptSynWay = DingConfig.newInstance()
                            .getDing2ekpDepartmentSynWay();
                    if (StringUtil.isNotNull(deptSynWay)) {
                        if(logger.isDebugEnabled()){
                            logger.debug("新配置方式处理一人多部门");
                        }
                        if (("add".equals(addOrUpdate)
                                && !"noSyn".equals(deptSynWay))
                                || ("update".equals(addOrUpdate)
                                && "syn".equals(deptSynWay)
                                )) {
                            String deptSyn = DingConfig.newInstance()
                                    .getDing2ekpDepartment();
                            if(logger.isDebugEnabled()){
                                logger.debug("deptSyn:" + deptSyn);
                            }
                            if ("multDept".equals(deptSyn)) {
                                SysOrgPost post = addPost(name, deptId,
                                        "person", orgOrDept);
//                                getSysOrgPostService().getBaseDao()
//                                        .getHibernateSession().merge(post);
                            }
                        }
                    } else {
                        if(logger.isDebugEnabled()){
                            logger.debug("旧方式处理一人多部门");
                        }
                        if ("true".equals(DingConfig.newInstance()
                                .getDingOmsInMoreDeptEnabled())) {
                            SysOrgPost post = addPost(name, deptId, "person",
                                    orgOrDept);
//                            getSysOrgPostService().getBaseDao()
//                                    .getHibernateSession().merge(post);
                        }
                    }
                } else {
                    log(msg + "新增/更新部门(名称=" + name + ",dingId=" + deptId
                            + ")失败");
                }
            }
            if (orgOrDept.getFdOrgType() == 2) {
                sysOrgDeptService.update(orgOrDept);
            } else if (orgOrDept.getFdOrgType() == 1) {
                sysOrgOrgService.update(orgOrDept);
            }
            if(logger.isDebugEnabled()){
                logger.debug("\t提交同步当前钉钉部门信息事务......");
            }
            TransactionUtils.commit(status);
            if(logger.isDebugEnabled()){
                logger.debug("\t成功提交同步当前钉钉部门信息事务......");
            }
        } catch (Exception e) {
            t = e;
            logger.error("新增/更新部门失败:", e);
            addError(e);
        }
        finally {
            if(t != null && status != null){
                log("处理单个钉钉部门信息失败");
                if(status.isRollbackOnly()){
                    logger.debug("回滚事务");
                    TransactionUtils.rollback(status);
                }
                log("已回滚处理单个钉钉部门信息事务");
            }
        }
    }

    /**
     * 新增/更新EKP部门
     */
    private void updateDept(SysOrgElement elem, JSONObject element,
                            boolean callback, String addOrUpdate) throws Exception {
        String oldName = elem.getFdName();
        String deptNameSynWay = DingConfig.newInstance()
                .getDing2ekpDeptNameSynWay();
        if(logger.isDebugEnabled()){
            logger.debug("deptNameSynWay:" + deptNameSynWay);
        }
        if (StringUtil.isNotNull(deptNameSynWay)) {
            if(logger.isDebugEnabled()){
                logger.debug("新配置方式处理部门名称");
            }
            if ("add".equals(addOrUpdate) || ("update".equals(addOrUpdate)
                    && "syn".equals(deptNameSynWay))) {
                elem.setFdName(element.getString("name"));
            }

        } else {
            if(logger.isDebugEnabled()){
                logger.debug("旧方式处理部门名称");
            }
            elem.setFdName(element.getString("name"));
        }
        elem.setFdIsAvailable(true);
        elem.setFdIsBusiness(true);
        elem.setFdImportInfo(
                SynchroOrgDing2EkpUtil.importInfoPre + "_dept_" + element.getString("id"));
        String dingId = element.getString("id");
        if (StringUtil.isNotNull(dingId)) {
            JSONObject jo = dingApiService.departGet(Long.parseLong(dingId));
            String deptOrderSynWay = DingConfig.newInstance()
                    .getDing2ekpDeptOrderSynWay();
            if(logger.isDebugEnabled()){
                logger.debug("deptOrderSynWay：" + deptOrderSynWay);
            }
            if (StringUtil.isNotNull(deptOrderSynWay)) {
                if(logger.isDebugEnabled()){
                    logger.debug("新配置方式处理部门排序");
                }
                if (("add".equals(addOrUpdate)
                        && !"noSyn".equals(deptOrderSynWay))
                        || ("update".equals(addOrUpdate)
                        && "syn".equals(deptOrderSynWay))) {
                    if (jo.containsKey("order")) {
                        elem.setFdOrder(jo.getInt("order"));
                    } else {
                        if(logger.isDebugEnabled()){
                            logger.debug(elem.getFdName() + "部门返回不含排序信息");
                        }
                    }
                }
            } else {
                if(logger.isDebugEnabled()){
                    logger.debug("旧方式处理部门排序");
                }
                if (jo.containsKey("order")) {
                    elem.setFdOrder(jo.getInt("order"));
                } else {
                    if(logger.isDebugEnabled()){
                        logger.debug(elem.getFdName() + "部门返回不含排序信息");
                    }
                }
            }
        }
        SysOrgElement parentDept = null;
        if (("1".equals(element.getString("id"))
                || !element.containsKey("parentid")
                || (StringUtil.isNotNull(dingOrgId2ekp)
                && dingOrgId2ekp.contains(element.getString("id"))))
                && StringUtil
                .isNotNull(DingConfig.newInstance().getDingInOrgId())) {
            parentDept = (SysOrgElement) sysOrgElementService
                    .findByPrimaryKey(DingConfig.newInstance().getDingInOrgId(),
                            null, true);
        } else if (element.containsKey("parentid")) {
            if (StringUtil.isNull(dingOrgId2ekp)
                    || (StringUtil.isNotNull(dingOrgId2ekp)
                    && !dingOrgId2ekp
                    .contains(element.getString("id")))) {
                parentDept = getSysOrgDept(element.getString("parentid"),
                        false);
            }
        }

        String deptParentDeptSynWay = DingConfig.newInstance()
                .getDing2ekpDeptParentDeptSynWay();
        if(logger.isDebugEnabled()){
            logger.debug("deptParentDeptSynWay:" + deptParentDeptSynWay);
        }
        if (StringUtil.isNotNull(deptParentDeptSynWay)) {
            if(logger.isDebugEnabled()){
                logger.debug("新配置方式处理部门的上级部门");
            }
            if ("add".equals(addOrUpdate) || ("update".equals(addOrUpdate)
                    && "syn".equals(deptParentDeptSynWay))) {
                elem.setFdParent(parentDept);
            }
        } else {
            if(logger.isDebugEnabled()){
                logger.debug("旧方式处理部门的上级部门");
            }
            elem.setFdParent(parentDept);
        }

        // 添加日志信息
        if (UserOperHelper.allowLogOper("updateDept", "*")) {
            UserOperContentHelper.putUpdate(elem).putSimple("fdName", oldName,
                    elem.getFdName());
        }
    }

    /**
     * 处理指定部门下钉钉员工信息
     */
    private void updateUsers(JSONObject dept) throws Exception {
        if (dept.toString().contains("isFromUnionOrg")) {
            if (!associatedExternalEnable) {
                return;
            }
        }
        Long deptId = dept.getLong("id");
        String name = dept.getString("name");
        String msg = "\t开始同步钉钉部门(名称：${name},dingId=${deptId})下的员工信息"
                .replace("${name}", name).replace("${deptId}",
                        deptId.toString());
        List<OapiV2UserListResponse.ListUserResponse> dingUserList = new ArrayList<OapiV2UserListResponse.ListUserResponse>();
        SynchroOrgDing2EkpUtil.getDingUsersByDeptId(this.jobContext, deptId, 0L, dingUserList);
        if (dingUserList != null && !dingUserList.isEmpty()) {
            List<String> dingUserIds = dingUserList.stream().map(res->{
                return res.getUserid();
            }).collect(Collectors.toList());
            Map<String, String> dingMainDeptIds = SynchroOrgDing2EkpUtil.getDingMainDeptIds(dingUserIds);
            JSONArray users = SynchroOrgDing2EkpUtil.convert2JSonArray(dingUserList);
            log(msg + "(员工数:" + dingUserList.size() + ")");
            String mainDeptId = deptId.toString(), userId, userName;
            for (Object obj : users) {
                JSONObject dept_user = (JSONObject) obj;
                userId = dept_user.getString("userid");
                userName = dept_user.getString("name");
                allDingIds.add(userId + "|8");
                try {
                    if(dingMainDeptIds.containsKey(userId)){
                        mainDeptId = dingMainDeptIds.get(userId);
                    }
                    else{
                        mainDeptId = getDingMainDeptId(dingApiService.getAccessToken(), userId);
                    }
                    if (StringUtil.isNotNull(mainDeptId)
                            && !allDingIds.contains(mainDeptId + "|2")) {
                        if(logger.isDebugEnabled()){
                            logger.debug("人员" + userName
                                    + "的主部门不在同步范围内，设置当前部门为主部门       钉钉主部门id:"
                                    + mainDeptId + " 当前钉钉部门id:" + deptId);
                        }
                        jobContext.logMessage("人员" + userName
                                + "的主部门不在同步范围内，设置当前部门为主部门     钉钉原主部门id:"
                                + mainDeptId + " 当前钉钉部门id:" + deptId
                                + " 当前部门名称：" + name);
                        mainDeptId = String.valueOf(deptId);
                    }
                } catch (Exception e2) {
                    mainDeptId = String.valueOf(deptId);
                    logger.error("获取人员主部门失败！" + dept_user);
                    logger.error(e2.getMessage(), e2);
                }
                updateUser(dept_user, false, deptId, mainDeptId,false);

                if (interruptForErrors) {
                    logger.warn("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"
                            + interruptForErrors);
                    break;
                }
            }
        }
    }

    /*
     * 获取钉钉手机号码：外国的手机号码需要加上区号
     */
    private String getUserMobile(JSONObject element) {
        if (element == null || element.isEmpty()
                || !element.containsKey("mobile")
                || StringUtil.isNull(element.getString("mobile"))) {
            return "";
        }
        String mobile_pre = "";// 手机区号
        if (element.containsKey("stateCode")
                && !"86".equals(element.getString("stateCode"))) {
            mobile_pre = "+" + element.getString("stateCode")
                    + "-";
            logger.warn("不是中国标准区号，手机号码需要加上区号:" + mobile_pre);
        } else {
            logger.warn("------element不含有stateCode参数-------" + element);
        }
        return mobile_pre + element.getString("mobile");
    }

    public void addError(Exception e) {
        String msg = e.getMessage();
        if (StringUtil.isNull(msg)) {
            return;
        }
        Integer count = errorsCount.get(msg);
        if (count == null) {
            errorsCount.put(msg, 1);
        } else {
            count++;
            errorsCount.put(msg, count);
            if (count >= errorCount2Interrupt) {
                interruptForErrors = true;
                logger.error("同个错误的次数超过了" + errorCount2Interrupt
                        + ",停止执行同步，错误信息：" + msg);
            }
        }
    }

    /**
     * 处理单个钉钉用户信息
     *
     * @param element
     * @param flag
     * @throws Exception
     */
    private void updateUser(JSONObject element, boolean flag, Long deptId,
                            String mainDeptId,boolean isCallBack)
            throws Exception {
        TransactionStatus status = null;
        Throwable t = null;
        boolean isDuplicatedPkException = false;
        try {
            if(logger.isDebugEnabled()){
                logger.debug("处理单个钉钉用户信息:element：" + element);
                logger.debug("flag:" + flag + "  deptId:" + deptId + "  mainDeptId:"
                        + mainDeptId);
            }

            if (!element.containsKey("mobile")) {
                element.put("mobile", "");
            }
            if (!element.containsKey("jobnumber")) {
                element.put("jobnumber", "");
            }
            String userid = element.getString("userid");
            String mobileNo = getUserMobile(element);
            String name = EmojiFilter.filterEmoji(element.getString("name"));
            String msg = "\t\t\t开始同步用户${name}(userid=${userid},mobile=${mobileNo})..."
                    .replace("${name}", name)
                    .replace("${userid}", userid)
                    .replace("${mobileNo}", mobileNo);
            String sysOrgPersonId = null;
            sysOrgPersonId=SynchroOrgDing2EkpUtil.getSysOrgPerson(this.jobContext, userid, name, mobileNo,
                    this.relationMap, this.loginNameMap, this.mobileMap, this.personMap,isCallBack);
            logger.debug("开启事务");
            status = TransactionUtils.beginNewTransaction(600);
            SysOrgPerson person = null;
            if (sysOrgPersonId != null) {
                person = (SysOrgPerson) sysOrgPersonService
                        .findByPrimaryKey(sysOrgPersonId, null, true);
            }
            String addOrUpdate = "add";
            if (person != null) {
                // 生态组织人员不接受修改回调
                if (SysOrgEcoUtil.IS_ENABLED_ECO && BooleanUtils.isTrue(person.getFdIsExternal())) {
                    TransactionUtils.commit(status);
                    if(logger.isDebugEnabled()){
                        logger.debug("生态组织人员不接受修改回调：" + element.getString("name") + " " + element);
                    }
                    return;
                }
                addOrUpdate = "update";
                if(logger.isDebugEnabled()){
                    logger.debug("============updateUser============"
                            + person.getFdName() + " " + element);
                }
                updateUser(person, element, mainDeptId);
            } else {
                addOrUpdate = "add";
                msg += "根据匹配原则(钉钉映射信息/EKP人员同步信息/手机号)无法匹配对应人员，直接新增人员${name}"
                        .replace("${name}", name);
                if (deptId == null) {
                    // 保存EKP人员时修改标识覆盖新建标识
                    person = handleIsvPerson(element, mainDeptId);
                } else if (deptId != null) {
                    person = new SysOrgPerson();
                    // 强制使用手机号MD5为ID，确保人员不重复
                    person.setFdId(SynchroOrgDing2EkpUtil.getPersonId(mobileNo));
                    addUser(person, element, mainDeptId);
                }
            }
            log(msg);
            if (person != null) {
                // 建立人员映射关系
                updateRelation(userid, person.getFdId(), "8", element);
                // 岗位关系关联
                addPost(element, person, deptId, addOrUpdate);
                // 判断用户是否生态组织人员
                boolean isExternal = SysOrgEcoUtil.isExternal(person);
                // ekp同步到钉钉,且开启生态组织实时同步
                DingConfig dc = DingConfig.newInstance();
                boolean isEkp2Ding = (StringUtil
                        .isNotNull(dc.getSyncSelection())
                        && "1".equals(dc.getSyncSelection())
                        && "true".equals(dc.getDingOmsExternal())
                        && SysOrgEcoUtil.IS_ENABLED_ECO)
                        || ("true".equals(dc.getDingOmsOutEnabled())
                        && "true".equals(dc.getDingOmsExternal())
                        && SysOrgEcoUtil.IS_ENABLED_ECO);
                if (isEkp2Ding) {
                    if (isExternal) {
                        person.setFdIsExternal(true);
                        sysOrgPersonService.update(person);
                    } else {
                        // 非生态组织，则回滚
                        log(msg + "ekp同步到钉钉,仅支持生态组织实时同步!同步人员"
                                + element.getString("name") + "(id=" + userid
                                + ")");
                        TransactionUtils.rollback(status);
                        return;
                    }
                }
                // 邀请外部人员成功，推送通知
                if (isExternal) {
                    SynchroOrgDing2EkpUtil.pushAddPersonMessage(element.getString("userid"),
                            element.getJSONArray("department").getString(0));
                }
            } else {
                log(msg + "同步人员" + element.getString("name") + "(id=" + userid
                        + ")到EKP失败");
            }
            sysOrgPersonService.update(person);
            logger.debug("提交事务");
            TransactionUtils.commit(status);
        }
        catch (BatchUpdateException | DataIntegrityViolationException | ConstraintViolationException e){
            t = e;
            if(t instanceof DataIntegrityViolationException){
                t = ((DataIntegrityViolationException) e).getRootCause();
            }
            else if(t instanceof ConstraintViolationException) {
                t = ((ConstraintViolationException) t).getSQLException();
            }
            if(t instanceof BatchUpdateException){
                if(StringUtil.isNotNull(t.getMessage())
                        && t.getMessage().toUpperCase().indexOf("PRIMARY") > -1){
                    isDuplicatedPkException = true;
                }
            }
            if(!isDuplicatedPkException){
                throw e;
            }
        }
        catch (Exception e) {
            t = e;
            logger.error("更新人员失败", e);
            jobContext.logError("更新人员失败", e);
            addError(e);
        }
        finally {
            if(t != null && status != null){
                log("\t\t同步当前人员信息失败");
                if(status.isRollbackOnly()){
                    logger.debug("回滚事务");
                    TransactionUtils.rollback(status);
                }
                log("\t\t已回滚同步当前人员信息事务");
                if(isDuplicatedPkException){
                    log("\t\t重新执行同步当前人员信息");
                    duplicatedPkExceptionHandle(element, flag, deptId, mainDeptId);
                }
            }
            else{
                if(logger.isDebugEnabled()){
                    logger.debug("\t\t\t成功同步当前人员信息");
                }
            }
        }
    }

    /**
     * 处理一种情况：相同手机号人员从钉钉删除后，再次加入钉钉
     * @param element
     * @param flag
     * @param deptId
     * @param mainDeptId
     * @throws Exception
     */
    private void duplicatedPkExceptionHandle(JSONObject element, boolean flag, Long deptId, String mainDeptId) throws Exception {
        String mobileNo = getUserMobile(element);
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock("sysOrgPerson.fdId");
        hqlInfo.setWhereBlock("sysOrgPerson.fdMobileNo=:mobileNo");
        hqlInfo.setParameter("mobileNo", mobileNo);
        String personId = (String) sysOrgPersonService.findFirstOne(hqlInfo);
        if(StringUtils.isNotBlank(personId)){
            mobileMap.put(mobileNo, personId);
            updateUser(element, flag, deptId, mainDeptId,false);
        }
    }

    private void addPost(JSONObject element, SysOrgPerson person, Long deptId,
                         String addOrUpdate)
            throws Exception {
        if(logger.isDebugEnabled()){
            logger.debug("addOrUpdate:" + addOrUpdate + " " + element);
        }
        if (element.containsKey("department")) {
            String deptstr = element.getString("department");
            JSONArray jas = JSONArray.fromObject(deptstr);
            String ddid = null;
            SysOrgPost post = null;
            String logmsg = "\t";
            boolean flag = false;
            boolean deptManagerEnabled = false;
            boolean moreDeptEnabled = false;
            DingConfig dingConfig = DingConfig.newInstance();
            String deptLeaderSynWay = dingConfig.getDing2ekpDeptLeaderSynWay();
            if(logger.isDebugEnabled()){
                logger.debug("deptLeaderSynWay:" + deptLeaderSynWay);
            }
            if (StringUtil.isNotNull(deptLeaderSynWay)) {
                if (("add".equals(addOrUpdate)
                        && !"noSyn".equals(deptLeaderSynWay))
                        || ("update".equals(addOrUpdate)
                        && "syn".equals(deptLeaderSynWay))) {
                    deptManagerEnabled = true;
                }
            } else {
                if ("true".equals(dingConfig.getDingOmsInDeptManagerEnabled())) {
                    deptManagerEnabled = true;
                }
            }
            // 一人多部门
            String deptSynWay = dingConfig.getDing2ekpDepartmentSynWay();
            if (StringUtil.isNotNull(deptSynWay)) {
                if(logger.isDebugEnabled()){
                    logger.debug("新配置方式处理一人多部门  addOrUpdate:" + addOrUpdate
                            + " deptSynWay:" + deptSynWay);
                }
                if (("add".equals(addOrUpdate)
                        && !"noSyn".equals(deptSynWay))
                        || ("update".equals(addOrUpdate)
                        && "syn".equals(deptSynWay))) {
                    String deptSyn = dingConfig.getDing2ekpDepartment();
                    if(logger.isDebugEnabled()){
                        logger.debug("deptSyn:" + deptSyn);
                    }
                    if ("multDept".equals(deptSyn)) {
                        moreDeptEnabled = true;
                    }
                }

            } else {
                if(logger.isDebugEnabled()){
                    logger.debug("旧方式处理一人多部门");
                }
                if ("true".equals(dingConfig.getDingOmsInMoreDeptEnabled())) {
                    moreDeptEnabled = true;
                }
            }
            if(logger.isDebugEnabled()){
                logger.debug("moreDeptEnabled:" + moreDeptEnabled);
            }
            if(moreDeptEnabled || deptManagerEnabled){
                if(!element.containsKey("isLeaderInDepts")){
                    //由于获取部门用户基础信息不包含"在对应的部门中是否为主管"数据，故需要另外查询当前用户详情接口
                    SynchroOrgDing2EkpUtil.appendIsLeaderInDeptsData(element);
                }
            }
            if (deptId == null) {
                flag = true;
            }
            for (int i = 0; i < jas.size(); i++) {
                ddid = jas.getString(i);
                // F4部门主管的处理
                if (StringUtil.isNull(ddid)) {
                    continue;
                }
                if (deptId != null && !ddid.equals(deptId.toString())) {
                    if(logger.isDebugEnabled()){
                        logger.debug("停止：deptId：" + deptId + " ddid: " + ddid);
                    }
                    continue;
                }
                ddid = ddid.trim();
                // 84405 钉钉通讯录同步到EKP支持多部门多主管增加开关
                if (deptManagerEnabled) {
                    if (element.containsKey("isLeaderInDepts")) {
                        JSONObject json = JSONObject.fromObject(
                                element.getString("isLeaderInDepts"));
                        if (json.containsKey(ddid)
                                && json.get(ddid) instanceof Boolean
                                && json.getBoolean(ddid)) {
                            post = getPost(Long.parseLong(ddid), "leader",
                                    flag);
                            if (post != null && person.getHbmPosts() == null) {
                                person.setHbmPosts(new ArrayList());
                            }
                            if (post != null && person != null
                                    && !person.getHbmPosts().contains(post)) {
                                if(logger.isDebugEnabled()){
                                    logger.debug("添加" + person.getFdName() + "的岗位："
                                            + post.getFdName());
                                }
                                person.getHbmPosts().add(post);
                                logmsg += "设置部门主管岗位信息：岗位=" + post.getFdName()
                                        + ",人员=" + person.getFdName();
                            } else {
                                logmsg += "根据钉钉部门id(" + ddid
                                        + ")无法获取EKP对应的主管岗位或者主管岗位中已经存在此人无需在添加一次";
                                if (post != null && person != null) {
                                    logmsg += ",其中部门主管岗位信息：岗位="
                                            + post.getFdName() + ",人员="
                                            + person.getFdName();
                                }
                            }
                            log(logmsg);
                        }
                    }
                    if (element.containsKey("isLeader")
                            && element.getBoolean("isLeader")) {
                        post = getPost(Long.parseLong(ddid), "leader", flag);
                        if (post != null && person.getHbmPosts() == null) {
                            person.setHbmPosts(new ArrayList());
                        }
                        if (post != null && person != null
                                && !person.getHbmPosts().contains(post)) {
                            if (person.getHbmPosts() == null) {
                                person.setHbmPosts(new ArrayList());
                            }
                            if(logger.isDebugEnabled()){
                                logger.debug("添加" + person.getFdName() + "的岗位："
                                        + post.getFdName());
                            }
                            person.getHbmPosts().add(post);
                            logmsg += "设置部门主管岗位信息：岗位=" + post.getFdName()
                                    + ",人员=" + person.getFdName();
                        } else {
                            logmsg += "根据钉钉部门id(" + ddid
                                    + ")无法获取EKP对应的主管岗位或者主管岗位中已经存在此人无需在添加一次";
                            if (post != null && person != null) {
                                logmsg += ",其中部门主管岗位信息：岗位=" + post.getFdName()
                                        + ",人员=" + person.getFdName();
                            }
                        }
                        log(logmsg);
                    }
                }

                // 84405 钉钉通讯录同步到EKP支持多部门多主管增加开关
                if (moreDeptEnabled) {
                    // F4部门成员的处理
                    post = getPost(Long.parseLong(ddid), "person", flag);
                    if (post != null && person.getHbmPosts() == null) {
                        person.setHbmPosts(new ArrayList());
                    }
                    if (post != null && person != null
                            && !person.getHbmPosts().contains(post)) {
                        person.getHbmPosts().add(post);
                        logmsg += "设置部门成员岗位信息：岗位=" + post.getFdName() + ",人员="
                                + person.getFdName();
                    } else {
                        logmsg += "根据钉钉部门id(" + ddid
                                + ")无法获取EKP对应的成员岗位或者成员岗位中已经存在此人无需在添加一次";
                        if (post != null && person != null) {
                            logmsg += ",其中部门成员岗位信息：岗位=" + post.getFdName()
                                    + ",人员=" + person.getFdName();
                        }
                    }
                    log(logmsg);
                }
            }

            // 处理多余岗位的问题 #102301
            if (moreDeptEnabled && jas.size() > 0) {
                try {
                    Set deptNames = getDingDeptName(jas, element);
                    if(logger.isDebugEnabled()){
                        logger.debug("element 22:" + element);
                    }
                    List<SysOrgPost> postList = person.getFdPosts();
                    if (postList.size() > 0) {
                        for (int i = 0; i < postList.size(); i++) {
                            String postName = postList.get(i).getFdName();
                            if(logger.isDebugEnabled()){
                                logger.debug(
                                        "========检查postName===========" + postName);
                            }
                            if (StringUtil
                                    .isNull(postList.get(i).getFdImportInfo())
                                    || postList.get(i).getFdImportInfo()
                                    .indexOf(
                                            "com.landray.kmss.third.ding.oms.SynchroOrgDing2EkpImp_post") == -1) {
                                continue;
                            }

                            if (!deptNames.contains(postName)) {
                                // 处理删除的岗位
                                logger.warn(
                                        "========删除人员的岗位==========" + postName
                                                + " (人员：" + person.getFdName()
                                                + ")");
                                person.getHbmPosts().remove(postList.get(i));
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }
    }

    private Set getDingDeptName(JSONArray jas, JSONObject element)
            throws Exception {

        JSONObject tempJson = new JSONObject();
        tempJson = element;
        Set deptNames = new HashSet<>();
        String ddid;
        JSONObject dingDept;
        String name = "成员";
        if(logger.isDebugEnabled()){
            logger.debug("element:" + element);
        }
        if (!element.containsKey("isLeaderInDepts")) {
            // 获取部门的用户是 isLeader
            tempJson = dingApiService.userGet(element.getString("userid"),
                    null);
        }
        JSONObject json = JSONObject
                .fromObject(tempJson.getString("isLeaderInDepts"));
        if(logger.isDebugEnabled()){
            logger.debug("=========json========" + json);
        }
        // 判断是否开启同步部门主管
        boolean isManagerEnabled = false;
        String deptLeaderSynWay = DingConfig.newInstance()
                .getDing2ekpDeptLeaderSynWay();
        if(logger.isDebugEnabled()){
            logger.debug("deptLeaderSynWay:" + deptLeaderSynWay);
        }
        if (StringUtil.isNotNull(deptLeaderSynWay)) {
            if (!"noSyn".equals(deptLeaderSynWay)) {
                isManagerEnabled = true;
            }
        } else {
            if ("true".equals(DingConfig.newInstance()
                    .getDingOmsInDeptManagerEnabled())) {
                isManagerEnabled = true;
            }
        }
        if(logger.isDebugEnabled()){
            logger.debug("isManagerEnabled:" + isManagerEnabled);
        }
        for (int i = 0; i < jas.size(); i++) {
            ddid = jas.getString(i).trim();
            dingDept = dingApiService.departGet(Long.parseLong(ddid));
            if (null != dingDept && !dingDept.isEmpty()) {
                if (json.containsKey(ddid)
                        && json.get(ddid) instanceof Boolean
                        && json.getBoolean(ddid)
                        && isManagerEnabled) {
                    name = "主管";
                    deptNames.add(dingDept.getString("name") + "_成员");
                } else {
                    name = "成员";
                }
                deptNames.add(dingDept.getString("name") + "_" + name);
            }
        }
        if(logger.isDebugEnabled()){
            logger.debug("=========set========" + deptNames.toString());
        }
        return deptNames;
    }

    /**
     * 添加EKP用户
     */
    private void addUser(SysOrgPerson person, JSONObject element,
                         String mainDeptId) throws Exception {
        // F4密码默认为空
        // person.setFdNewPassword("1");
        if(logger.isDebugEnabled()){
            logger.debug("新增EKP用户：" + element);
        }
        //createDeptMap.put(element.getString("name"), element); // 记录新增部门
        setUserInfo(person, element, "add", mainDeptId);
        // 添加日志信息
        if (UserOperHelper.allowLogOper("addUser", "*")) {
            UserOperContentHelper.putAdd(person, "fdLoginName", "fdNewPassword",
                    "fdIsAvailable", "FdIsBusiness",
                    "fdMobileNo", "fdName", "fdParent", "fdEmail", "fdNo",
                    "fdWorkPhone");
        }
        sysOrgPersonService.add(person);
    }

    /**
     * 更新EKP用户
     */
    private void updateUser(SysOrgPerson person, JSONObject element,
                            String mainDeptId) throws Exception {
        if(!person.getFdIsAvailable()){
            person.setFdIsAvailable(Boolean.TRUE);
        }
        String oldMobileNo = person.getFdMobileNo();
        String oldName = person.getFdName();
        SysOrgElement oldParent = person.getFdParent();
        String oldEmail = person.getFdEmail();
        String oldNo = person.getFdNo();
        String oldWorkPhone = person.getFdWorkPhone();
        setUserInfo(person, element, "update", mainDeptId);
        // 添加日志信息
        if (UserOperHelper.allowLogOper("updateUser", "*")) {
            UserOperContentHelper.putUpdate(person).putSimple("fdMobileNo",
                    oldMobileNo, person.getFdMobileNo())
                    .putSimple("fdName", oldName, person.getFdName())
                    .putSimple("fdParent", oldParent, person.getFdParent())
                    .putSimple("fdEmail", oldEmail, person.getFdEmail())
                    .putSimple("fdNo", oldNo, person.getFdNo())
                    .putSimple("fdWorkPhone", oldWorkPhone,
                            person.getFdWorkPhone());
        }
    }

    private void setUserInfo(SysOrgPerson person, JSONObject element,
                             String addOrUpdate, String mainDeptId) throws Exception {
        if(logger.isDebugEnabled()){
            logger.debug("addOrUpdate:" + addOrUpdate + " element:" + element);
        }
        person.setFdIsAvailable(true);
        if (person.getFdIsBusiness() == null) {
            person.setFdIsBusiness(true);
        }
        DingConfig dingConfig= DingConfig.newInstance();
        // 业务相关设置 默认相关
        String isBusinessSynWay = dingConfig.getDing2ekpFdIsBusinessSynWay();
        if(logger.isDebugEnabled()){
            logger.debug("isBusinessSynWay:" + isBusinessSynWay);
        }
        if (StringUtil.isNotNull(isBusinessSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(isBusinessSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(isBusinessSynWay))) {
                String isBusiness = dingConfig.getDing2ekpFdIsBusiness();
                if(logger.isDebugEnabled()){
                    logger.debug("isBusiness:" + isBusiness);
                }
                String isBusinessVal = getDingValue(isBusiness, element);
                if(logger.isDebugEnabled()){
                    logger.debug("isBusinessVal:" + isBusinessVal);
                }
                if (StringUtil.isNotNull(isBusinessVal)) {
                    // 0/1 true/false 是/否
                    if ("0".equals(isBusinessVal) || "否".equals(isBusinessVal)
                            || "false".equalsIgnoreCase(isBusinessVal)) {
                        person.setFdIsBusiness(false);
                    } else {
                        person.setFdIsBusiness(true);
                    }
                }
            }
        }
        person.setFdIsAbandon(false);
        // 默认语言
        String defLangSynWay = dingConfig.getDing2ekpDefLangSynWay();
        if(logger.isDebugEnabled()){
            logger.debug("defLangSynWay:" + defLangSynWay);
        }
        if (StringUtil.isNotNull(defLangSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(defLangSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(defLangSynWay))) {
                String defLang = dingConfig.getDing2ekpDefLang();
                if(logger.isDebugEnabled()){
                    logger.debug("defLang:" + defLang);
                }
                String defLangVal = getDingValue(defLang, element);
                if(logger.isDebugEnabled()){
                    logger.debug("defLangVal:" + defLangVal);
                }
                if (StringUtil.isNotNull(defLangVal)) {
                    // 0/1 true/false 是/否
                    if (defLangVal.contains("-")) {
                        person.setFdDefaultLang(defLangVal);
                    } else if ("中文".equals(defLangVal)) {
                        person.setFdDefaultLang("zh-CN");
                    } else if ("英文".equals(defLangVal)) {
                        person.setFdDefaultLang("en-US");
                    }
                }
            }
        }
        // 手机号码
        String mobileSynWay = dingConfig.getDing2ekpMobileSynWay();
        if(logger.isDebugEnabled()){
            logger.debug("mobileSynWay:" + mobileSynWay);
        }
        if (StringUtil.isNotNull(mobileSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(mobileSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(mobileSynWay))) {
                if (StringUtil.isNotNull(element.getString("mobile"))) {
                    person.setFdMobileNo(getUserMobile(element));
                }
            }
        } else {
            if (StringUtil.isNotNull(element.getString("mobile"))) {
                person.setFdMobileNo(getUserMobile(element));
            }
        }

        // 登录名
        String loginNameSynWay = dingConfig.getDing2ekpLoginNameSynWay();
        if(logger.isDebugEnabled()){
            logger.debug("loginNameSynWay:" + loginNameSynWay);
        }
        if (StringUtil.isNotNull(loginNameSynWay)) {
            if ("add".equals(addOrUpdate)
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(loginNameSynWay))) {
                String loginNameSyn = dingConfig.getDing2ekpLoginName();
                if(logger.isDebugEnabled()){
                    logger.debug("loginNameSyn:" + loginNameSyn);
                }
                if (StringUtil.isNotNull(loginNameSyn)) {
                    String loginNameVal = getDingValue(loginNameSyn, element);
                    if(logger.isDebugEnabled()){
                        logger.debug("loginNameVal:" + loginNameVal);
                    }
                    if (StringUtil.isNotNull(loginNameVal)) {
                        person.setFdLoginName(loginNameVal);
                    } else {
                        logger.error("用户的登录名为空，默认以手机号、userId取手机号码：" + element);
                        if (StringUtil.isNotNull(element.getString("mobile"))) {
                            person.setFdLoginName(element.getString("mobile"));
                        }
                        if (StringUtil.isNull(person.getFdLoginName())) {
                            person.setFdLoginName(element.getString("userid"));
                        }
                    }
                }
            }
        }

        // 用户名
        String nameSynWay = dingConfig.getDing2ekpNameSynWay();
        if(logger.isDebugEnabled()){
            logger.debug("nameSynWay:" + nameSynWay);
        }
        if (StringUtil.isNotNull(nameSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(nameSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(nameSynWay))) {
                String name = dingConfig.getDing2ekpName();
                if(logger.isDebugEnabled()){
                    logger.debug("name:" + name);
                }
                if (StringUtil.isNotNull(name)) {
                    String dingName = getDingValue(name, element);
                    if(logger.isDebugEnabled()){
                        logger.debug("dingName:" + dingName);
                    }
                    if (StringUtil.isNotNull(dingName)) {
                        person.setFdName(EmojiFilter.filterEmoji(dingName));
                    } else {
                        logger.error("用户的用户名为空：" + element);
                    }
                }
            }
        } else {
            person.setFdName(
                    EmojiFilter.filterEmoji(element.getString("name")));
        }
        // 人员的部门设置
        if (element.containsKey("department")) {
            String deptstr = element.getString("department");
            JSONArray jas = JSONArray.fromObject(deptstr);
            Map<String, String> dingDeptMap = new HashMap<String, String>();
            String ddid = null;
            String firstDingId = null;
            for (int i = 0; i < jas.size(); i++) {
                ddid = jas.getString(i);
                if (StringUtil.isNotNull(ddid)) {
                    if (!relationMap.containsKey(ddid + "|2")) {
                        OmsRelationModel model = SynchroOrgDing2EkpUtil.getOmsRelationModel(ddid);
                        if (model != null) {
                            dingDeptMap.put(model.getFdEkpId(),
                                    model.getFdEkpId());
                            relationMap.put(
                                    model.getFdAppPkId() + "|"
                                            + model.getFdType(),
                                    model.getFdEkpId());
                        }
                    } else {
                        dingDeptMap.put(relationMap.get(ddid + "|2"),
                                relationMap.get(ddid + "|2"));
                    }
                }
                if (i == 0) {
                    firstDingId = relationMap.get(ddid + "|2");
                }
            }
            String ekppid = null;

            if (StringUtil.isNotNull(mainDeptId)) {
                // 主部门设置
                String deptSynWay = dingConfig.getDing2ekpDepartmentSynWay();
                if(logger.isDebugEnabled()){
                    logger.debug("deptSynWay:" + deptSynWay + " addOrUpdate:"
                            + addOrUpdate);
                }
                boolean flag = false; // 是否处理人员主部门
                if (StringUtil.isNotNull(deptSynWay)) {
                    if(logger.isDebugEnabled()){
                        logger.debug("判断是否需要处理人员主部门：" + mainDeptId);
                    }
                    if (("add".equals(addOrUpdate)
                            && !"noSyn".equals(deptSynWay))
                            || ("update".equals(addOrUpdate)
                            && "syn".equals(deptSynWay))) {
                        flag = true;
                    }
                } else {
                    if(logger.isDebugEnabled()){
                        logger.debug("旧方式处理人员主部门");
                    }
                    flag = true;
                }
                if (flag) {
                    if(logger.isDebugEnabled()){
                        logger.debug("开始处理主部门");
                    }
                    SysOrgElement dept = getSysOrgDept(mainDeptId, false);
                    if (dept != null) {
                        if(logger.isDebugEnabled()){
                            logger.debug(
                                    EmojiFilter
                                            .filterEmoji(element.getString("name"))
                                            + " 主部门设置:上级部门：" + dept.getFdName());
                        }
                        person.setFdParent(dept);
                    } else {
                        // 主部门不在同步范围内，取其中一个部门作为上级部门
                        logger.warn("------主部门不在同步范围内-----" + mainDeptId);
                        if (element.containsKey("department")) {
                            JSONArray depts = element
                                    .getJSONArray("department");
                            for (int k = 0; k < depts.size(); k++) {
                                if (relationMap
                                        .containsKey(depts.get(k) + "|2")) {
                                    String ekpid = relationMap
                                            .get(depts.get(k) + "|2");
                                    dept = (SysOrgElement) sysOrgElementService
                                            .findByPrimaryKey(ekpid,
                                                    null, false);
                                    if (dept != null) {
                                        logger.warn(
                                                EmojiFilter
                                                        .filterEmoji(
                                                                element.getString(
                                                                        "name"))
                                                        + " 主部门设置:(取同步范围内的部门作为上级部门)"
                                                        + dept.getFdName());
                                        person.setFdParent(dept);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // 非主部门设置（无主部门）
                if(logger.isDebugEnabled()){
                    logger.debug(EmojiFilter.filterEmoji(element.getString("name"))
                            + " 无主部门");
                }
                if (person.getFdParent() != null) {
                    ekppid = person.getFdParent().getFdId();
                }
                if ((StringUtil.isNull(ekppid) || (StringUtil.isNotNull(ekppid)
                        && !dingDeptMap.containsKey(ekppid)))
                        && dingDeptMap.size() > 0
                        && StringUtil.isNotNull(firstDingId)) {
                    // 已经存在EKP部门不在钉钉的部门中则变化
                    SysOrgElement dept = (SysOrgElement) sysOrgElementService
                            .findByPrimaryKey(firstDingId, null, true);
                    if (dept != null) {
                        person.setFdParent(dept);
                        if(logger.isDebugEnabled()){
                            logger.debug(EmojiFilter
                                    .filterEmoji(element.getString("name"))
                                    + " 上级部门：" + dept.getFdName());
                        }
                    }
                }
            }
        }

        // 邮箱设置
        String emailSynWay = dingConfig.getDing2ekpEmailSynWay();
        if(logger.isDebugEnabled()){
            logger.debug("emailSynWay:" + emailSynWay);
        }
        if (StringUtil.isNotNull(emailSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(emailSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(emailSynWay))) {
                String emailSyn = dingConfig.getDing2ekpEmail();
                if(logger.isDebugEnabled()){
                    logger.debug("emailSyn:" + emailSyn);
                }
                if (StringUtil.isNotNull(emailSyn)) {
                    String emailVal = getDingValue(emailSyn, element);
                    if(logger.isDebugEnabled()){
                        logger.debug("emailVal:" + emailVal);
                    }
                    if (StringUtil.isNotNull(emailVal)) {
                        person.setFdEmail(emailVal);
                    } else {
                        logger.error("用户的邮箱为空：" + element);
                    }
                }
            }
        } else {
            if (element.containsKey("email")
                    && StringUtil.isNotNull(element.getString("email"))) {
                person.setFdEmail(element.getString("email"));
            }
        }
        // 编号
        String fdNoSynWay = dingConfig.getDing2ekpFdNoSynWay();
        if(logger.isDebugEnabled()){
            logger.debug("fdNoSynWay:" + fdNoSynWay);
        }
        if (StringUtil.isNotNull(fdNoSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(fdNoSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(fdNoSynWay))) {
                String fdNoSyn = dingConfig.getDing2ekpFdNo();
                if(logger.isDebugEnabled()){
                    logger.debug("fdNoSyn:" + fdNoSyn);
                }
                if (StringUtil.isNotNull(fdNoSyn)) {
                    String fdNoVal = getDingValue(fdNoSyn, element);
                    if(logger.isDebugEnabled()){
                        logger.debug("fdNoVal:" + fdNoVal);
                    }
                    if (StringUtil.isNotNull(fdNoVal)) {
                        person.setFdNo(fdNoVal);
                    } else {
                        logger.error("用户的编号为空：" + element);
                    }
                }
            }
        } else {
            if (element.containsKey("jobnumber")
                    && StringUtil.isNotNull(element.getString("jobnumber"))) {
                person.setFdNo(element.getString("jobnumber"));
            }
        }
        // 办公电话
        String telSynWay = dingConfig.getDing2ekpTelSynWay();
        if(logger.isDebugEnabled()){
            logger.debug("telSynWay:" + telSynWay);
        }
        if (StringUtil.isNotNull(telSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(telSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(telSynWay))) {
                String telSyn = dingConfig.getDing2ekpTel();
                if(logger.isDebugEnabled()){
                    logger.debug("telSyn:" + telSyn);
                }
                if (StringUtil.isNotNull(telSyn)) {
                    String telVal = getDingValue(telSyn, element);
                    if(logger.isDebugEnabled()){
                        logger.debug("telVal:" + telVal);
                    }
                    if (StringUtil.isNotNull(telVal)) {
                        person.setFdWorkPhone(telVal);
                    } else {
                        logger.error("用户的办公电话为空：" + element);
                    }
                }
            }
        } else {
            if (element.containsKey("tel")
                    && StringUtil.isNotNull(element.getString("tel"))) {
                person.setFdWorkPhone(element.getString("tel"));
            }
        }
        person.setFdImportInfo(
                SynchroOrgDing2EkpUtil.importInfoPre + "_person_" + element.getString("userid"));

        // 昵称
        String fdNickNameSynWay = dingConfig.getDing2ekpFdNickNameSynWay();
        if(logger.isDebugEnabled()){
            logger.debug("fdNickNameSynWay:" + fdNickNameSynWay);
        }
        if (StringUtil.isNotNull(fdNickNameSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(fdNickNameSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(fdNickNameSynWay))) {
                String fdNickNameSyn = dingConfig.getDing2ekpFdNickName();
                if(logger.isDebugEnabled()){
                    logger.debug("fdNickNameSyn:" + fdNickNameSyn);
                }
                if (StringUtil.isNotNull(fdNickNameSyn)) {
                    String fdNickNameVal = getDingValue(fdNickNameSyn, element);
                    if(logger.isDebugEnabled()){
                        logger.debug("fdNickNameVal:" + fdNickNameVal);
                    }
                    if (StringUtil.isNotNull(fdNickNameVal)) {
                        person.setFdNickName(fdNickNameVal);
                    } else {
                        logger.error("用户的昵称为空：" + element);
                    }
                }
            }
        }

        // 关键字
        String keywordSynWay = dingConfig.getDing2ekpKeywordSynWay();
        if(logger.isDebugEnabled()){
            logger.debug("keywordSynWay:" + keywordSynWay);
        }
        if (StringUtil.isNotNull(keywordSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(keywordSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(keywordSynWay))) {
                String keywordSyn = dingConfig.getDing2ekpKeyword();
                if(logger.isDebugEnabled()){
                    logger.debug("keywordSyn:" + keywordSyn);
                }
                if (StringUtil.isNotNull(keywordSyn)) {
                    String keywordVal = getDingValue(keywordSyn, element);
                    if(logger.isDebugEnabled()){
                        logger.debug("keywordVal:" + keywordVal);
                    }
                    if (StringUtil.isNotNull(keywordVal)) {
                        person.setFdKeyword(keywordVal);
                    } else {
                        logger.error("用户的关键字为空：" + element);
                    }
                }
            }
        }

        // 性别
        String sexSynWay = dingConfig.getDing2ekpSexSynWay();
        if(logger.isDebugEnabled()){
            logger.debug("sexSynWay:" + sexSynWay);
        }
        if (StringUtil.isNotNull(sexSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(sexSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(sexSynWay))) {
                String sexSyn = dingConfig.getDing2ekpSex();
                if(logger.isDebugEnabled()){
                    logger.debug("sexSyn:" + sexSyn);
                }
                if (StringUtil.isNotNull(sexSyn)) {
                    String sexVal = getDingValue(sexSyn, element);
                    if(logger.isDebugEnabled()){
                        logger.debug("sexVal:" + sexVal);
                    }
                    if (StringUtil.isNotNull(sexVal)) {
                        if ("男".equals(sexVal)
                                || "M".equalsIgnoreCase(sexVal)) {
                            person.setFdSex("M");
                        } else if ("女".equals(sexVal)
                                || "F".equalsIgnoreCase(sexVal)) {
                            person.setFdSex("F");
                        }
                    } else {
                        logger.error("用户的性别为空：" + element);
                    }
                }
            }
        }

        // 短号
        String shortNoSynWay = dingConfig.getDing2ekpFdShortNoSynWay();
        if(logger.isDebugEnabled()){
            logger.debug("shortNoSynWay:" + shortNoSynWay);
        }
        if (StringUtil.isNotNull(shortNoSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(shortNoSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(shortNoSynWay))) {
                String shortNoSyn = dingConfig.getDing2ekpFdShortNo();
                if(logger.isDebugEnabled()){
                    logger.debug("shortNoSyn:" + shortNoSyn);
                }
                if (StringUtil.isNotNull(shortNoSyn)) {
                    String shortNoVal = getDingValue(shortNoSyn, element);
                    if(logger.isDebugEnabled()){
                        logger.debug("shortNoVal:" + shortNoVal);
                    }
                    if (StringUtil.isNotNull(shortNoVal)) {
                        person.setFdShortNo(shortNoVal);
                    } else {
                        logger.error("用户的短号为空：" + element);
                    }
                }
            }
        }

        // 备注
        String fdMemoSynWay = dingConfig.getDing2ekpFdMemoSynWay();
        if(logger.isDebugEnabled()){
            logger.debug("fdMemoSynWay:" + fdMemoSynWay);
        }
        if (StringUtil.isNotNull(fdMemoSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(fdMemoSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(fdMemoSynWay))) {
                String fdMemoSyn = dingConfig.getDing2ekpFdMemo();
                if(logger.isDebugEnabled()){
                    logger.debug("fdMemoSyn:" + fdMemoSyn);
                }
                if (StringUtil.isNotNull(fdMemoSyn)) {
                    String fdMemoVal = getDingValue(fdMemoSyn, element);
                    if(logger.isDebugEnabled()){
                        logger.debug("fdMemoVal:" + fdMemoVal);
                    }
                    if (StringUtil.isNotNull(fdMemoVal)) {
                        person.setFdMemo(fdMemoVal);
                    } else {
                        logger.error("用户的备注为空：" + element);
                    }
                }
            }
        }

        // 人员密码处理 只有新增才修改
        if ("add".equals(addOrUpdate)) {
            if (StringUtil.isNull(initPassword)) {
                initPassword = SynchroOrgDing2EkpUtil.getInitPassword();
            }
            if (StringUtil.isNotNull(initPassword)) {
                String password = passwordEncoder.encodePassword(initPassword);
                if(logger.isDebugEnabled()){
                    logger.debug("password:" + password);
                }
                person.setFdPassword(password);
                person.setFdInitPassword(
                        PasswordUtil.desEncrypt(SecureUtil
                                .BASE64Decoder(initPassword)));
            }
        }

        // 排序号
        String orderSynWay = dingConfig.getDing2ekpOrderSynWay();
        if(logger.isDebugEnabled()){
            logger.debug("orderSynWay:" + orderSynWay);
        }
        if (StringUtil.isNotNull(orderSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(orderSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(orderSynWay))) {
                String orderSyn = dingConfig.getDing2ekpOrder();
                if(logger.isDebugEnabled()){
                    logger.debug("orderSyn:" + orderSyn);
                }
                if (StringUtil.isNotNull(orderSyn)) {
                    String orderVal = getDingValue(orderSyn, element);
                    if(logger.isDebugEnabled()){
                        logger.debug("orderVal:" + orderVal);
                    }
                    if (StringUtil.isNotNull(orderVal)) {
                        try {
                            Integer order_int = Integer.parseInt(orderVal);
                            person.setFdOrder(order_int);
                        } catch (Exception e) {
                            logger.error("设置人员的排序号出错：" + orderVal);
                            logger.error(e.getMessage());
                        }

                    } else {
                        logger.error("用户的排序号为空：" + element);
                    }
                }
            }
        }
    }

    // 根据key获取钉钉字段
    private String getDingValue(String key, JSONObject element) {
        // 优先取基本信息，基本信息找不到则到拓展字段里找，都找不到则返回null
        try {
            if(logger.isDebugEnabled()){
                logger.debug("key:" + key);
            }
            if (StringUtil.isNull(key)) {
                return null;
            }
            if (element == null || element.isEmpty()) {
                return null;
            }
            if (element.containsKey(key)) {
                //入职时间特殊处理
                if("hiredDate".equals(key)){
                    Object hiredDate = element.get("hiredDate");
                    if(hiredDate instanceof Long){
                        return DateUtil.convertDateToString(new Date((Long)hiredDate),"yyyy-MM-dd");
                    }else if( hiredDate instanceof JSONObject){
                        //{"date":1,"hours":13,"seconds":5,"month":10,"timezoneOffset":-480,"year":121,"minutes":17,"time":1635743825211,"day":1}
                        JSONObject tempData = (JSONObject) hiredDate;
                        return DateUtil.convertDateToString(new Date(tempData.getLong("time")),"yyyy-MM-dd");
                    }
                }
                return element.getString(key);
            } else {
                if (element.containsKey("extattr")
                        && StringUtil.isNotNull(element.getString("extattr"))) {
                    if(logger.isDebugEnabled()){
                        logger.debug("extattr:" + element.getString("extattr"));
                    }
                    try {
                        // 回调获取的extattr格式不一样（用户详情和部门用户列表接口）
                        JSONObject extJson = JSONObject.fromObject(element.get("extattr"));
                        String val_temp = extJson.getString(key);
                        if(logger.isDebugEnabled()){
                            logger.debug("val_temp:" + val_temp);
                        }
                        return val_temp;
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("根据钉钉用户信息获取字段发生异常，key" + key + " element:" + element);
            logger.error("", e);
        }

        return null;
    }

    private void updateRelation(String fdAppId, String ekpId, String type,
                                JSONObject element)
            throws Exception {
        logger.debug("***updateRelation***" + fdAppId+ "  ekpId:" + ekpId + "  type:" + type);
        if (!relationMap.containsKey(fdAppId + "|" + type)) {
            boolean isRelationExist = relationMap.values().contains(ekpId);
            OmsRelationModel model = new OmsRelationModel();
            if(isRelationExist){
                model = (OmsRelationModel) omsRelationService.findFirstOne("fdEkpId='"+ekpId+"'", null);
                relationMap.remove(model.getFdAppPkId()+ "|" + type);
            }
            model.setFdEkpId(ekpId);
            model.setFdAppPkId(fdAppId);
            model.setFdAppKey(SynchroOrgDing2EkpUtil.getAppKey());
            model.setFdType(type);
            if ("8".equals(type)) {
                model.setFdAvatar(element.getString("avatar"));
                model.setFdUnionId(getUnionId(element));
                //账号类型
                setAccountType(element,model);
            }
            if(isRelationExist){
                omsRelationService.update(model);
            }
            else{
                omsRelationService.add(model);
            }
            relationMap.put(fdAppId + "|" + type, ekpId);
            log("\tdingId=" + fdAppId + "的数据关联已建立");
        }
        else if ("8".equals(type)) {
            // 人员已经存在，添加头像信息

//            StringBuilder sql = new StringBuilder();
//            sql.append("update oms_relation_model set fd_avatar=:fdAvatar, fd_union_id=:fdUnionId where fd_ekp_id=:fdEkpId and fd_app_key=:fdAppKey");
//            Session session = omsRelationService.getBaseDao().getHibernateSession();
//            Query query = session.createNativeQuery(sql.toString());
//            query.setParameter("fdAvatar", element.getString("avatar"));
//            query.setParameter("fdUnionId", getUnionId(element));
//            query.setParameter("fdEkpId", ekpId);
//            query.setParameter("fdAppKey", SynchroOrgDing2EkpUtil.getAppKey());
//            query.executeUpdate();
            OmsRelationModel omsRelationModel = omsRelationService.findByEkpId(relationMap.get(fdAppId + "|" + type));
            if(omsRelationModel!=null){
                String unionIdOld = omsRelationModel.getFdUnionId();
                String avatarOld = omsRelationModel.getFdAvatar();
                String unionIdNew = getUnionId(element);
                String avatarNew = element.getString("avatar");
                String accountType = SynchroOrgDing2EkpUtil.getAccontType(element);
                if(unionIdOld==null || avatarOld==null || !unionIdOld.equals(unionIdNew) || !avatarOld.equals(avatarNew)
                    || !accountType.equals(omsRelationModel.getFdAccountType())){
                    omsRelationModel.setFdUnionId(unionIdNew);
                    omsRelationModel.setFdAvatar(avatarNew);
                    omsRelationModel.setFdAccountType(accountType);
                    omsRelationService.update(omsRelationModel);
                }
            }
        }
    }

    /**
     * 设置钉钉账号类型
     * @param element
     * @param model
     */
    private void setAccountType(JSONObject element, OmsRelationModel model) {
        String dingAccountType = SynchroOrgDing2EkpUtil.getAccontType(element);
        if(StringUtil.isNotNull(dingAccountType)){
            model.setFdAccountType(dingAccountType);
        }
    }

    @Override
    public void saveOrUpdateCallbackDept(JSONObject element, boolean flag)
            throws Exception {
        if ((StringUtil.isNotNull(DingConfig.newInstance().getSyncSelection())
                && "2".equals(DingConfig.newInstance().getSyncSelection())) ||
                "true".equals(DingConfig.newInstance().getDingOmsInEnabled())) {

            if (!isDeptOrUserAccess(true, element.getString("id"))) {
                logger.warn("回调的部门不在同步范围内【钉钉部门Id="
                        + element.getString("id") + ",部门名称="
                        + element.getString("name") + "】");
                return;
            } else {
                if(logger.isDebugEnabled()){
                    logger.debug("回调的部门在同步范围内【钉钉部门Id="
                            + element.getString("id") + ",部门名称="
                            + element.getString("name") + "】");
                }
            }

            long time = System.currentTimeMillis();
            String type = "更新";
            String addOrUpdate = "update";
            if (flag) {
                type = "新增";
                addOrUpdate = "add";
            }
            if(logger.isDebugEnabled()){
                logger.debug("钉钉回调执行EKP的" + type + "部门操作:钉钉部门Id="
                        + element.getString("id") + ",部门名称="
                        + element.getString("name"));
            }
            updateDept(element, flag, true);

            // 回调部门主管配置，兼容旧数据
            boolean deptManagerEnabled = false;
            String deptLeaderSynWay = DingConfig.newInstance()
                    .getDing2ekpDeptLeaderSynWay();
            if(logger.isDebugEnabled()){
                logger.debug("deptLeaderSynWay:" + deptLeaderSynWay);
            }
            if (StringUtil.isNotNull(deptLeaderSynWay)) {
                if(logger.isDebugEnabled()){
                    logger.debug("处理部门主管关系：" + element);
                }
                if (("add".equals(addOrUpdate)
                        && !"noSyn".equals(deptLeaderSynWay))
                        || ("update".equals(addOrUpdate)
                        && "syn".equals(deptLeaderSynWay))) {
                    deptManagerEnabled = true;
                }
            } else {
                String oldManagerEnabled = DingConfig.newInstance()
                        .getDingOmsInDeptManagerEnabled();
                if (StringUtil.isNotNull(oldManagerEnabled)
                        && "true".equals(oldManagerEnabled)) {
                    deptManagerEnabled = true;
                }
            }

            if (deptManagerEnabled) {
                // 部门主管
                List<Userlist> userlists = new ArrayList<Userlist>();
                List<SysOrgPerson> personlists = new ArrayList<SysOrgPerson>();
                String deptId = element.getString("id");
                dingApiService.userList(userlists, Long.parseLong(deptId), 0L);
                if (userlists != null && !userlists.isEmpty()) {
                    JSONArray users = JSONArray.fromObject(userlists);
                    List<String> userids = new ArrayList<String>();
                    SysOrgPerson person = null;
                    for (Object obj : users) {
                        JSONObject user = (JSONObject) obj;
                        if (user.containsKey("isLeader")
                                && user.getBoolean("isLeader")
                                && user.containsKey("userid")) {
                            userids.add(user.getString("userid"));
                        }
                    }
                    synchronized (globalLock) {
                        SysOrgPost post = getPost(Long.parseLong(deptId),
                                "leader", true);
                        if (userids == null || userids.isEmpty()) {
                            if (post != null && post.getHbmPersons() != null) {
                                post.getHbmPersons().clear();
                                sysOrgPostService.update(post);
                            }
                        } else if (post != null) {
                            for (String did : userids) {
                                String sysOrgPersonId = SynchroOrgDing2EkpUtil.getSysOrgPerson(this.jobContext, did, "", "",
                                        this.relationMap, this.loginNameMap, this.mobileMap, this.personMap,true);
                                if (sysOrgPersonId != null) {
                                    person = (SysOrgPerson) sysOrgPersonService
                                            .findByPrimaryKey(sysOrgPersonId, null, true);
                                    personlists.add(person);
                                }
                            }
                            if (!personlists.isEmpty()) {
                                if (post.getHbmPersons() != null) {
                                    post.getHbmPersons().clear();
                                }
                                post.setHbmPersons(personlists);
                                sysOrgPostService.update(post);
                            }
                        }
                    }
                }
            }
            if(logger.isDebugEnabled()){
                logger.debug("cost time:"
                        + ((System.currentTimeMillis() - time) / 1000) + " s");
            }
        }
    }

    @Override
    public void saveOrUpdateCallbackUser(JSONObject element, boolean flag)
            throws Exception {
        // 回调的初始密码设置
        this.initPassword = SynchroOrgDing2EkpUtil.getInitPassword();
        DingConfig dc = DingConfig.newInstance();
        if ((StringUtil.isNotNull(dc.getSyncSelection())
                && "1".equals(dc.getSyncSelection()))
                || ("true".equals(dc.getDingOmsOutEnabled())
                && "true".equals(dc.getDingMobileEnabled()))) {
            if(logger.isDebugEnabled()){
                logger.debug("组织架构从ekp到钉钉同步,用户信息修改!");
                logger.debug("用户信息：" + element);
            }
            // 判断用户是否禁用（如果禁用，则删除钉钉用户）以及对照表的关系（用户状态正常，则建立映射关系）
            OmsRelationModel model = SynchroOrgDing2EkpUtil.getOmsRelationModel(element.getString("userid"));
            if (model == null) {
                String userId = element.getString("userid");
                logger.warn("不存在该用户的映射关系，将根据配置信息尝试建立对照关系   ："
                        + userId);
                String o2d_userId = DingConfig.newInstance()
                        .getOrg2dingUserid();
                if (StringUtil.isNotNull(o2d_userId)) {
                    if(logger.isDebugEnabled()){
                        logger.debug("o2d_userId:" + o2d_userId);
                    }
                    SysOrgPerson sysOrgPerson = null;
                    if ("fdId".equals(o2d_userId)) {
                        sysOrgPerson = (SysOrgPerson) sysOrgPersonService
                                .findByPrimaryKey(userId);
                    } else if ("fdLoginName".equals(o2d_userId)) {
                        HQLInfo hqlInfo = new HQLInfo();
                        hqlInfo.setWhereBlock("fdLoginName=:fdLoginName And fdIsAvailable=:fdIsAvailable");
                        hqlInfo.setParameter("fdLoginName", userId);
                        hqlInfo.setParameter("fdIsAvailable", true); //优先匹配有效人员
                        sysOrgPerson = (SysOrgPerson) sysOrgPersonService.findFirstOne(hqlInfo);
                        if (sysOrgPerson == null) {
                            //无效人员
                            hqlInfo = new HQLInfo();
                            hqlInfo.setWhereBlock("fdLoginName=:fdLoginName And fdIsAvailable=:fdIsAvailable");
                            hqlInfo.setParameter("fdLoginName", userId);
                            hqlInfo.setParameter("fdIsAvailable", false); //有效人员
                            sysOrgPerson = (SysOrgPerson) sysOrgPersonService.findFirstOne(hqlInfo);
                        }
                    }
                    if (sysOrgPerson == null) {
                        logger.warn("根据userId:" + userId
                                + "  无法匹配人员的fdId,不建立映射关系");
                    } else {
                        // 先判断该用户是否是禁用状态
                        if (!sysOrgPerson.getFdIsAvailable()) {
                            logger.warn("该用户已经禁用，将删除钉钉人员：" + userId);
                            String rs = dingApiService.userDelete(userId);
                            logger.warn("删除状态：" + rs);

                        } else {
                            if(logger.isDebugEnabled()){
                                logger.debug("建立映射关系  ：" + sysOrgPerson.getFdId()
                                        + "   dingId:" + userId);
                            }
                            model = new OmsRelationModel();
                            model.setFdEkpId(sysOrgPerson.getFdId());
                            model.setFdAppPkId(userId);
                            model.setFdAppKey(SynchroOrgDing2EkpUtil.getAppKey());
                            model.setFdType("8");
                            model.setFdAvatar(element.getString("avatar"));
                            model.setFdUnionId(getUnionId(element));
                            //账号类型
                            setAccountType(element,model);
                            omsRelationService.add(model);
                        }
                    }
                }
            }
            if ("true".equals(dc.getDingMobileEnabled())) {
                saveOrUpdateCallBackMobile(element);
            }
        }
        // 钉钉同步到ekp
        boolean isDing2Ekp = (StringUtil.isNotNull(dc.getSyncSelection())
                && "2".equals(dc.getSyncSelection()))
                || "true".equals(dc.getDingOmsInEnabled());
        // ekp同步到钉钉,且开启生态组织实时同步
        boolean isEkp2Ding = (StringUtil.isNotNull(dc.getSyncSelection())
                && "1".equals(dc.getSyncSelection())
                && "true".equals(dc.getDingOmsExternal())
                && SysOrgEcoUtil.IS_ENABLED_ECO);
        if (isEkp2Ding || isDing2Ekp
                && isDeptOrUserAccess(false, element.getString("userid"))) {
            long time = System.currentTimeMillis();
            String type = "更新";
            if (flag) {
                type = "新增";
            }
            if(logger.isDebugEnabled()){
                logger.debug("钉钉回调执行EKP的" + type + "人员操作:钉钉用户Id="
                        + element.getString("userid") + ",用户名称="
                        + element.getString("name"));
            }
            String mainDeptId = getDingMainDeptId(
                    dingApiService.getAccessToken(),
                    element.getString("userid"));
            updateUser(element, flag, null, mainDeptId,true);
            if(logger.isDebugEnabled()){
                logger.debug("钉钉回调" + type + "人员共耗时:"
                        + ((System.currentTimeMillis() - time) / 1000) + " s");
            }
        }
    }

    @Override
    public void deleteCallbackDept(Long deptId) throws Exception {
        if ((StringUtil.isNotNull(DingConfig.newInstance().getSyncSelection())
                && "2".equals(DingConfig.newInstance().getSyncSelection()))
                || "true".equals(
                DingConfig.newInstance().getDingOmsInEnabled())) {
            long time = System.currentTimeMillis();
            if (deptId == null) {
                if(logger.isDebugEnabled()){
                    logger.debug("钉钉回调删除部门的Id为null，直接退出无法执行删除操作");
                }
                return;
            }
            SysOrgElement dept = getSysOrgDept(deptId.toString(), true);
            if (dept != null) {
                if (UserOperHelper.allowLogOper("deleteDept", "*")) {
                    UserOperContentHelper.putUpdate(dept)
                            .putSimple("fdIsAvailable", true, false);
                }
                // 如果当前部门下有存在多余的子部门/人员和岗位则移到根目录
                String leaderPost = SynchroOrgDing2EkpUtil.importInfoPre + "_post_2_" + deptId;
                String personPost = SynchroOrgDing2EkpUtil.importInfoPre + "_post_8_" + deptId;
                List<SysOrgElement> subEles = sysOrgElementService.findList(
                        "hbmParent.fdId='" + dept.getFdId() + "'",
                        null);
                for (SysOrgElement ele : subEles) {
                    if (ele.getFdOrgType() == 4 && (leaderPost
                            .equalsIgnoreCase(ele.getFdImportInfo())
                            || personPost
                            .equalsIgnoreCase(ele.getFdImportInfo()))) {
                        ele.setFdIsAvailable(false);
                        if (ele.getHbmPersons() != null) {
                            ele.getHbmPersons().clear();
                        }
                        sysOrgElementService.update(ele);
                    } else {
                        ele.setFdParent(null);
                        sysOrgElementService.update(ele);
                    }
                }
                dept.setFdIsAvailable(false);
                sysOrgElementService.update(dept);
                omsRelationService.deleteByKey(dept.getFdId());
            } else {
                if(logger.isDebugEnabled()){
                    logger.debug(
                            "钉钉删除部门回调EKP时在中间表和通过关键标识找不到对应的部门，无法置为无效，id=" + deptId);
                }
            }
            if(logger.isDebugEnabled()){
                logger.debug("钉钉回调删除部门共耗时:"
                        + ((System.currentTimeMillis() - time) / 1000) + " s");
            }
        }
    }

    @Override
    public void deleteCallbackUser(String userid) throws Exception {
        DingConfig dc = DingConfig.newInstance();
        boolean isDing2Ekp = (StringUtil.isNotNull(dc.getSyncSelection())
                && "2".equals(dc.getSyncSelection()))
                || "true".equals(dc.getDingOmsInEnabled());
        // ekp同步到钉钉,且开启生态组织实时同步或者开启了人员退出企业监听
        boolean isEkp2Ding = (StringUtil.isNotNull(dc.getSyncSelection())
                && "1".equals(dc.getSyncSelection())
                && (("true".equals(dc.getDingOmsExternal())
                && SysOrgEcoUtil.IS_ENABLED_ECO)||"true".equalsIgnoreCase(dc.getUserLeaveListenerEnable())));
        if(logger.isDebugEnabled()){
            logger.debug("isEkp2Ding:{}  isDing2Ekp:{}" ,isEkp2Ding,isDing2Ekp);
        }
        if (isDing2Ekp || isEkp2Ding) {
            long time = System.currentTimeMillis();
            if (StringUtil.isNull(userid)) {
                if(logger.isDebugEnabled()){
                    logger.debug("钉钉回调删除人员的Id为null，直接退出无法执行删除操作");
                }
                return;
            }
            //先根据映射表去找
            List<SysOrgPerson> persons = new ArrayList<SysOrgPerson>();
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo = new HQLInfo();
            hqlInfo.setWhereBlock("fdType=:fdType and fdAppPkId=:fdAppPkId");
            hqlInfo.setParameter("fdType","8");
            hqlInfo.setParameter("fdAppPkId",userid);
            List<OmsRelationModel> models = omsRelationService.findList(hqlInfo);
            if(models!=null && models.size()>0){
                for(OmsRelationModel model:models){
                    SysOrgPerson person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(model.getFdEkpId(),null,true);
                    persons.add(person);
                }
            }else{
                hqlInfo = new HQLInfo();
                hqlInfo.setWhereBlock("fdOrgType=8 and fdImportInfo=:info and fdIsAvailable=:fdIsAvailable");
                hqlInfo.setParameter("info", SynchroOrgDing2EkpUtil.importInfoPre + "_person_" + userid);
                hqlInfo.setParameter("fdIsAvailable", true);
                persons = sysOrgPersonService.findList(hqlInfo);
            }
            if (persons != null && persons.size() > 0) {
                for (int i = 0; i < persons.size(); i++) {
                    SysOrgPerson person = persons.get(i);
                    if (person != null) {
                        // 生态组织
                        if (isEkp2Ding) {
                            if (!person.getFdIsExternal().booleanValue() && !"true".equalsIgnoreCase(dc.getUserLeaveListenerEnable())) {
                                if(logger.isDebugEnabled()){
                                    logger.debug(
                                            "ekp同步到钉钉，仅支持生态组织实时同步!id=" + userid);
                                }
                                return;
                            }
                        }
                        person.setFdIsAvailable(new Boolean(false));
                        if (UserOperHelper.allowLogOper("deleteUser", "*")) {
                            UserOperContentHelper.putUpdate(person)
                                    .putSimple("fdIsAvailable", true, false);
                        }
                        if (i > 0) {
                            person.setFdImportInfo("");
                        }
                        sysOrgPersonService.update(person);
                        omsRelationService.deleteByKey(person.getFdId());
                    } else {
                        if(logger.isDebugEnabled()){
                            logger.debug(
                                    "钉钉删除人员回调EKP时找不到对应的人员，无法置为无效，id=" + userid);
                        }
                    }
                }
            }else{
                logger.warn("------------没有找到需要删除的人员 userid:{}-------------",userid);
            }
            if(logger.isDebugEnabled()){
                logger.debug("钉钉回调删除人员共耗时:"
                        + ((System.currentTimeMillis() - time) / 1000) + " s");
            }
        }
    }

    private void log(String msg) {
        if(logger.isDebugEnabled()){
            logger.debug("【钉钉接入组织架构到EKP】" + msg);
        }
        if (this.jobContext != null) {
            String time = sdf.format(Calendar.getInstance().getTime());
            jobContext.logMessage(time + "  " + msg);
        }
    }

    /**
     * <p>
     * 更新ekp的用户手机号码
     * </p>
     *
     * @throws Exception
     * @author 孙佳
     */
    @Override
    public void saveOrUpdateCallBackMobile(JSONObject element)
            throws Exception {
        long time = System.currentTimeMillis();
        DingConfig config = new DingConfig();
        if (!"3".equals(config.getDevModel())) {
            updateUserMobile(element);
        } else {
            log("ISV的开发方式（授权给服务商开发）不支持手机号码的回调");
        }
        log("cost time:" + ((System.currentTimeMillis() - time) / 1000) + " s");
    }

    private void updateUserMobile(JSONObject element) throws Exception {
        if (element.containsKey("mobile")) {
            String userid = element.getString("userid");
            String mobileNo = getUserMobile(element);
            String ekpid = omsRelationService.getEkpUserIdByDingUserId(userid);
            if (StringUtil.isNotNull(ekpid)) {
                SysOrgPerson person = (SysOrgPerson) sysOrgPersonService
                        .findByPrimaryKey(ekpid, null, true);
                if (person != null
                        && !person.getFdMobileNo().equals(mobileNo)) {
                    String oleMobileNo = person.getFdMobileNo();
                    person.setFdMobileNo(mobileNo);
                    // 记录日志信息
                    if (UserOperHelper.allowLogOper("updateUserMobile", "*")) {
                        UserOperContentHelper.putUpdate(person).putSimple(
                                "fdMobileNo", oleMobileNo,
                                person.getFdMobileNo());
                    }
                    sysOrgPersonService.update(person);
                }
            } else {
                if(logger.isDebugEnabled()){
                    logger.debug(
                            "根据钉钉userid=" + userid + "无法获取映射的EKP的用户id，无法更新手机号");
                }
            }
        } else {
            if(logger.isDebugEnabled()){
                logger.debug("手机号码无法更新，因为客户是使用的ISV模式，这种模式无法获取钉钉的手机号");
            }
        }
    }

    // 获取部门数据
    private SysOrgElement getSysOrgDept(String dingDeptId, boolean nolazy)
            throws Exception {
        SysOrgElement ele = null;
        if (relationMap.containsKey(dingDeptId + "|2")) {
            String ekpid = relationMap.get(dingDeptId + "|2");
            ele = (SysOrgElement) sysOrgElementService.findByPrimaryKey(ekpid,
                    null, nolazy);
        } else {
            OmsRelationModel model = SynchroOrgDing2EkpUtil.getOmsRelationModel(dingDeptId);
            if (model != null) {
                ele = (SysOrgElement) sysOrgElementService
                        .findByPrimaryKey(model.getFdEkpId(), null, true);
                if (ele != null) {
                    relationMap.put(dingDeptId + "|" + model.getFdType(),
                            ele.getFdId());
                }
            }
        }

        // 获取数据库数据
        if (ele == null) {
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setWhereBlock(
                    "fdImportInfo = :info and fdOrgType in (1,2)");
            hqlInfo.setParameter("info", SynchroOrgDing2EkpUtil.importInfoPre + "_dept_" + dingDeptId);
            ele = (SysOrgElement) sysOrgElementService.findFirstOne(hqlInfo);
        }
        return ele;
    }

    // 钉钉端不存在的映射关系处理
    private void cleanData(String dingId) throws Exception {
        SysOrgElement ele = null;
        TransactionStatus status = null;
        if(logger.isDebugEnabled()){
            logger.debug("------钉钉端不存在的映射关系处理 dingId--------:" + dingId);
        }
        try {
            // 判断该部门或者用户在钉钉中是否存在
            if(dingId.contains("|")){
                String _dingId = dingId.substring(0,dingId.indexOf("|"));
                if(dingId.endsWith("|8")){
                    logger.info("查询人员:"+_dingId);
                    JSONObject o = dingApiService.userGet(_dingId, null);
                    if (o.getInt("errcode") == 0) {
                        return;
                    }
                }else{
                    logger.info("查询部门:"+_dingId);
                    JSONObject o = dingApiService.departGet(Long.parseLong(_dingId));
                    if (o.getInt("errcode") == 0) {
                        return;
                    }
                }
            }
            logger.info("处理关系 dingId:" + dingId);
            status = TransactionUtils.beginNewTransaction();
            ele = (SysOrgElement) sysOrgElementService
                    .findByPrimaryKey(relationMap.get(dingId), null, true);
            if (ele != null) {
                if (ele.getFdOrgType() == 8) {
                    if (ele != null) {
                        ele.setFdIsAvailable(new Boolean(false));
                        sysOrgElementService.update(ele);
                    }
                } else {
                    // 如果当前部门下有存在多余的子部门/人员和岗位则移到根目录
                    String leaderPost = SynchroOrgDing2EkpUtil.importInfoPre + "_post_2_"
                            + dingId.split("[|]")[0];
                    String personPost = SynchroOrgDing2EkpUtil.importInfoPre + "_post_8_"
                            + dingId.split("[|]")[0];
                    List<SysOrgElement> subEles = sysOrgElementService
                            .findList("hbmParent.fdId='" + ele.getFdId() + "'",
                                    null);
                    for (SysOrgElement subele : subEles) {
                        if (subele.getFdOrgType() == 4 && (leaderPost
                                .equalsIgnoreCase(subele.getFdImportInfo())
                                || personPost.equalsIgnoreCase(
                                subele.getFdImportInfo()))) {
                            subele.setFdIsAvailable(false);
                            if (subele.getHbmPersons() != null) {
                                subele.getHbmPersons().clear();
                            }
                            sysOrgElementService.update(subele);
                        } else {
                            subele.setFdParent(null);
                            sysOrgElementService.update(subele);
                        }
                    }
                    ele.setFdIsAvailable(false);
                    sysOrgElementService.update(ele);
                }
                omsRelationService.deleteByKey(ele.getFdId());
            } else {
                omsRelationService.deleteByKey(relationMap.get(dingId));
            }
            logger.debug("提交事务");
            TransactionUtils.commit(status);
        } catch (Exception e) {
            logger.error("清理钉钉端不存在的映射关系失败", e);
            if (status != null) {
                try {
                    logger.debug("回滚事务");
                    TransactionUtils.rollback(status);
                } catch (Exception ex) {
                    logger.error("---事务回滚出错---", ex);
                }
            }
        }
    }

    // =================================================F4新增功能部分========================================================
    private Map<String, String> postMap = new HashMap<String, String>();

    private Map<String, String> personMap = new HashMap<String, String>();

    private ISysOrgPostService sysOrgPostService = null;

    public ISysOrgPostService getSysOrgPostService() {
        if (sysOrgPostService == null) {
            sysOrgPostService = (ISysOrgPostService) SpringBeanUtil
                    .getBean("sysOrgPostService");
        }
        return sysOrgPostService;
    }


    private String getDingMainDeptId(String token, String userid)
            throws Exception {
        if (!hrmPermission) {
            return null;
        }
        if (StringUtil.isNull(token) || StringUtil.isNull(userid)){
            if(logger.isDebugEnabled()){
                logger.debug("钉钉的token(" + token + ")或者用户id(" + userid
                        + ")为空，无法获取当前用户的主部门");
            }
        }
        String dingDeptId = null;
        String dingUrl = DingConstant.DING_PREFIX
                + "/topapi/smartwork/hrm/employee/list"
                + DingUtil.getDingAppKeyByEKPUserId("?", null);
        if(logger.isDebugEnabled()){
            logger.debug("钉钉接口：" + dingUrl);
        }
        ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
        OapiSmartworkHrmEmployeeListRequest req = new OapiSmartworkHrmEmployeeListRequest();
        req.setUseridList(userid);
        req.setFieldFilterList("sys00-mainDept");
        OapiSmartworkHrmEmployeeListResponse rsp = client.execute(req, token);
        if (rsp.getErrcode() == 0) {
            List<EmpFieldInfoVO> vos = rsp.getResult();
            if (vos != null && vos.size() > 0) {
                for (EmpFieldInfoVO vo : vos) {
                    List<EmpFieldVO> fvos = vo.getFieldList();
                    if (fvos != null && fvos.size() > 0) {
                        for (EmpFieldVO fvo : fvos) {
                            if ("sys00-mainDeptId".equals(fvo.getFieldCode())
                                    && StringUtil.isNotNull(fvo.getValue())) {
                                dingDeptId = fvo.getValue();
                            }
                        }
                    }
                }
            }
        } else {
            if(logger.isDebugEnabled()){
                logger.debug(
                        "获取钉钉人员id=" + userid + "时无法获取智能人事的主部门：" + rsp.getBody());
            }
            if (rsp.getErrcode() == 88) {
                logger.error("智能人事的接口没有权限，本次同步不再通过智能人事的接口获取人员主部门");
                hrmPermission = false;
            }
        }
        // 异常数据处理（钉钉）
        if ("-1".equals(dingDeptId)) {
            dingDeptId = "1";
        }
        return dingDeptId;
    }

    private SysOrgPost getPost(Long deptId, String type, boolean callback)
            throws Exception {
        if(logger.isDebugEnabled()){
            logger.debug("getPost方法：deptId->" + deptId + " type:" + type
                    + " callback:" + callback);
        }
        SysOrgPost post = null;
        String postId = null;
        String iminfo = SynchroOrgDing2EkpUtil.importInfoPre + "_post";
        String name = "";
        if ("leader".equals(type)) {
            name = "_主管";
            iminfo += "_2_" + deptId;
        } else {
            name = "_成员";
            iminfo += "_8_" + deptId;
        }
        if (postMap.containsKey(iminfo) && !callback) {
            postId = postMap.get(iminfo);
            if(StringUtil.isNotNull(postId)){
                post = (SysOrgPost) this.getSysOrgPostService().findByPrimaryKey(postId,null,true);
            }
        } else {
            Object obj = sysOrgCoreService.findByImportInfo(iminfo);
            if (obj != null) {
                post = (SysOrgPost) sysOrgCoreService
                        .format((SysOrgElement) obj);
                if (!post.getFdIsAvailable()) {
                    post.setFdIsAvailable(true);
                    getSysOrgPostService().update(post);
                }
                postMap.put(iminfo, post.getFdId());
            }
        }
        if (post == null && deptId != null) {
            JSONObject dingDept = dingApiService.departGet(deptId);
            name = dingDept.getString("name") + name;
            String where = "fdName='" + name + "'";
            String ekpDeptId = relationMap.get(dingDept.getString("id")+"|"+2);
//            OmsRelationModel model = SynchroOrgDing2EkpUtil.getOmsRelationModel(
//                    dingDept.getString("id"));
            if (ekpDeptId != null) {
                try {
                    where += " and hbmParent.fdId='" + ekpDeptId + "'";
                    post = (SysOrgPost) getSysOrgPostService().findFirstOne(where,
                            "fdIsAvailable desc");
                }catch (Exception e){
                    logger.error("ekpDeptId:"+ekpDeptId+", where:"+where );
                    throw e;
                }
            }
        }
        return post;
    }

    private SysOrgPost addPost(String name, String did, String type,
                               SysOrgElement parent) throws Exception {
        if(logger.isDebugEnabled()){
            logger.debug(
                    "addPost: name->" + name + " did:" + did + " type:" + type);
        }
        boolean isCreate = false;
        SysOrgPost post = null;
        boolean flag = false;
        if (StringUtil.isNull(name) || StringUtil.isNull(did)) {
            if(logger.isDebugEnabled()){
                logger.debug(
                        "部门名称(" + name + ")为空或部门钉钉Id(" + did + ")为空则直接跳过不创建岗位信息");
            }
            return null;
        }
        String iminfo = SynchroOrgDing2EkpUtil.importInfoPre + "_post";
        if ("leader".equals(type)) {
            name += "_主管";
            iminfo += "_2_" + did;
        } else {
            name += "_成员";
            iminfo += "_8_" + did;
        }
        if(logger.isDebugEnabled()){
            logger.debug("name:" + name);
        }
        Object obj = sysOrgCoreService.findByImportInfo(iminfo);
        if (obj != null) {
            post = (SysOrgPost) sysOrgCoreService
                    .format((SysOrgElement) obj);
        }
        if (post == null) {
            String where = "fdName='" + name + "'";
            OmsRelationModel model = SynchroOrgDing2EkpUtil.getOmsRelationModel(did);
            if (model != null) {
                where += " and hbmParent.fdId='" + model.getFdEkpId() + "'";
                post = (SysOrgPost) getSysOrgPostService()
                        .findFirstOne(where, "fdIsAvailable desc");
            }
            // }
            // 查不到对应的岗位则新建
            if (post == null) {
                post = new SysOrgPost();
                post.setFdName(name);
                post.setFdImportInfo(iminfo);
                post.setFdParent(parent);
                post.setFdIsAvailable(true);
                post.setFdIsBusiness(true);
                post.setFdIsAbandon(false);
                isCreate = true;
            }
        }

        // 设置岗位主管为当前部门的部门领导
        if ("leader".equals(type)) {
            parent.setHbmThisLeader(post);
        }
        post.setFdImportInfo(iminfo);
        post.setFdName(name);
        post.setFdIsAvailable(true);
        post.setFdParent(parent);

        if (isCreate == true) {
            getSysOrgPostService().add(post);
        } else {
            getSysOrgPostService().update(post);
        }
        postMap.put(iminfo, post.getFdId());
        return post;
    }

    protected final Object globalLock = new Object();

    // isv模式的回调，有可能会覆盖人员新增的场景，需要在修改的时候判断人员，如果不存在则新建
    private synchronized SysOrgPerson handleIsvPerson(JSONObject element,
                                                      String mainDeptId) {
        SysOrgPerson person = null;
        try {
            String userid = element.getString("userid");
            String info = SynchroOrgDing2EkpUtil.importInfoPre + "_person_" + userid;
            person = (SysOrgPerson) sysOrgPersonService.findFirstOne(
                    "fdOrgType=8 and fdImportInfo='" + info + "'", null);
            if (person == null) {
                person = new SysOrgPerson();
                if (StringUtil.isNotNull(element.getString("mobile"))) {
                    person.setFdLoginName(element.getString("mobile"));
                    // 强制使用手机号MD5为ID，确保人员不重复
                    person.setFdId(SynchroOrgDing2EkpUtil.getPersonId(element.getString("mobile")));
                }
                if (StringUtil.isNull(person.getFdLoginName())) {
                    person.setFdLoginName(userid);
                }
                person.setFdIsAvailable(true);
                person.setFdIsBusiness(true);
                setUserInfo(person, element, "add", mainDeptId);
                sysOrgPersonService.add(person);
                if(logger.isDebugEnabled()){
                    logger.debug("处理钉钉回调事件修改标识覆盖新建标识导致无法新建，EKP的处理是当查不到人员时则新建");
                }
            } else {
                if(logger.isDebugEnabled()){
                    logger.debug("当前用户已经存在不再新建，用户名称：" + person.getFdName()
                            + ",用户Id:" + person.getFdId() + ",钉钉Id:" + userid);
                }
            }

            OmsRelationModel model = SynchroOrgDing2EkpUtil.getOmsRelationModel(userid);
            if (model == null && person != null) {
                model = new OmsRelationModel();
                model.setFdEkpId(person.getFdId());
                model.setFdAppPkId(userid);
                model.setFdAppKey(SynchroOrgDing2EkpUtil.getAppKey());
                model.setFdType("8");
                model.setFdAvatar(element.getString("avatar"));
                model.setFdUnionId(getUnionId(element));
                //账号类型
                setAccountType(element,model);
                // 集群环境处理
                if (SynchroOrgDing2EkpUtil.getOmsRelationModel(userid) == null) {
                    omsRelationService.add(model);
                    relationMap.put(
                            model.getFdAppPkId() + "|" + model.getFdType(),
                            model.getFdEkpId());
                }
            }
            if (model != null) {
                // 头像信息为空
                if(logger.isDebugEnabled()){
                    logger.debug("-------------更新头像信息-------------------");
                }
                model.setFdAvatar(element.getString("avatar"));
                omsRelationService.update(model);
            }
        } catch (Exception e) {
            logger.error("---保存EKP人员失败---", e);
            person = null;
        }
        return person;
    }

    /**
     * 判断部门和人员是否在同步范围内（钉钉同步到ekp，主要用于回调判断）
     *
     * @param isDept
     * @param dingId
     * @return
     */
    private boolean isDeptOrUserAccess(boolean isDept, String dingId) {

        DingConfig dc = DingConfig.newInstance();
        try {
            if ((StringUtil.isNotNull(dc.getSyncSelection())
                    && "2".equals(dc.getSyncSelection()))
                    || "true".equals(dc.getDingOmsInEnabled())) {
                if(logger.isDebugEnabled()){
                    logger.debug("--------组织架构从钉钉到ekp---------");
                }

                dingOrgId2ekp = dc.getDingOrgId2ekp();
                if (StringUtil.isNull(dingOrgId2ekp)
                        || "1".equals(dingOrgId2ekp)) {
                    return true;
                }
                String[] dingRoots = dingOrgId2ekp.split(";");
                JSONObject parents = new JSONObject();
                if (isDept) {
                    parents = dingApiService.getDeptParents(dingId);
                    if (parents != null && parents.getLong("errcode") == 0) {
                        JSONArray parentIds = parents.getJSONArray("parentIds");
                        if (parentIds == null || parentIds.isEmpty()) {
                            return false;
                        }
                        for (int i = 0; i < parentIds.size(); i++) {
                            String parentId = String
                                    .valueOf(parentIds.getLong(i));
                            for (int j = 0; j < dingRoots.length; j++) {
                                if (dingRoots[j].equals(parentId)) {
                                    return true;
                                }
                            }
                        }
                    } else {
                        logger.warn("部门的上级部门查询异常：" + parents);
                    }
                } else {
                    parents = dingApiService.getPersonParents(dingId);
                    if (parents != null && parents.getLong("errcode") == 0) {
                        JSONArray parentIds = parents
                                .getJSONArray("department");
                        if (parentIds == null || parentIds.isEmpty()) {
                            return false;
                        }
                        for (int i = 0; i < parentIds.size(); i++) {
                            JSONArray departments = parentIds.getJSONArray(i);
                            for (int j = 0; j < departments.size(); j++) {

                                String parentId = String
                                        .valueOf(departments.getLong(j));
                                for (int k = 0; k < dingRoots.length; k++) {
                                    if (dingRoots[k].equals(parentId)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    } else {
                        logger.warn("人员上级部门查询异常：" + parents);
                    }
                }
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return true;
        }
    }

    // =================================================F4新增功能部分========================================================

    private String getUnionId(JSONObject ujo) {
        if (ujo.containsKey("unionId")) {
            return ujo.getString("unionId");
        }
        if (ujo.containsKey("unionid")) {
            return ujo.getString("unionid");
        }
        return null;
    }

}