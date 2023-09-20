package com.landray.kmss.third.ding.oms;

import com.dingtalk.api.request.OapiSmartworkHrmEmployeeListRequest;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeListResponse;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeListResponse.EmpFieldInfoVO;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeListResponse.EmpFieldVO;
import com.dingtalk.api.response.OapiUserListbypageResponse.Userlist;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.component.locker.interfaces.ConcurrencyException;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
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
import com.landray.kmss.third.ding.model.ThirdDingRoleCateMapp;
import com.landray.kmss.third.ding.model.ThirdDingRolelineMapp;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingRoleCateMappService;
import com.landray.kmss.third.ding.service.IThirdDingRolelineMappService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.EmojiFilter;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.HibernateException;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 钉钉到EKP组织架构同步（触发全量更新、启动增量同步）
 *
 * @TODO 性能优化, 按部门作事务提交
 */
public class SynchroOrgDing2EkpImp_bak implements SynchroOrgDing2Ekp, SynchroOrgDingRole2Ekp, DingConstant {

    private static boolean locked = false;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SynchroOrgDing2EkpImp_bak.class);

    private SysQuartzJobContext jobContext = null;

    /*
     * 是否有钉钉人事接口的权限
     */
    private boolean hrmPermission = true;

    private static final int errorCount2Interrupt = 30;

    private Map<String, Integer> errorsCount = new ConcurrentHashMap<String, Integer>();

    private boolean interruptForErrors = false;

    private Map<String, String> relationMap = new HashMap<String, String>();
    private Map<String, SysOrgPerson> loginNameMap = new HashMap<String, SysOrgPerson>();
    private Map<String, SysOrgPerson> mobileMap = new HashMap<String, SysOrgPerson>();
    private List<String> allDingIds = new ArrayList<String>();
    private DingApiService dingApiService = DingUtils.getDingApiService();
    private boolean associatedExternalEnable = true; // 是否同步外部组织，默认同步
    private String initPassword;
    private Map<String, JSONObject> createDeptMap = new HashMap<String, JSONObject>(); // 存放新建部门的信息，以便为“仅新增时同步”功能更新部门相关信息

    private String dingOrgId2ekp = ""; // 钉钉同步根部门id
    // private Set<String> dingOrgIdsSet = new HashSet<String>();

    private IOmsRelationService omsRelationService;

    private IKmssPasswordEncoder passwordEncoder;

    public IKmssPasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

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

    @Override
    public void generateMapping() throws Exception {
        String temp = "存在运行中的钉钉到EKP组织架构同步任务，当前任务中断...";
        if (locked) {
            logger.error(temp);
            return;
        }
        if (!"true".equals(DingConfig.newInstance().getDingEnabled())) {
            temp = "钉钉集成已经关闭，故不同步数据";
            logger.debug(temp);
            return;
        }
        if (StringUtil.isNotNull(DingConfig.newInstance().getSyncSelection())) {
            if (!"2".equals(DingConfig.newInstance().getSyncSelection())) {
                temp = "钉钉集成-通讯录配置-同步选择-从钉钉同步到本系统未开启，故不同步数据";
                logger.debug(temp);
                return;
            }
        } else {
            if (!"true"
                    .equals(DingConfig.newInstance().getDingOmsInEnabled())) {
                temp = "钉钉集成-通讯录配置-同步选择-从钉钉同步到本系统未开启，故不同步数据";
                logger.debug(temp);
                return;
            }
        }

        locked = true;
        log("开始建立钉钉与EKP组织架构映射关系....");
        try {
            long time = System.currentTimeMillis();

            // 初始化数据
            long caltime = System.currentTimeMillis();
            init();
            temp = "初始化数据耗时(秒)："
                    + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);


            // 处理钉钉到EKP组织架构
            caltime = System.currentTimeMillis();
            updateSyncOrgElements();
            temp = "处理钉钉到EKP组织架构耗时(秒)："
                    + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);

            // 同步钉钉管理员
            String synDingAdmin = DingConfig.newInstance()
                    .getDingAdminSynEnabled();
            if (StringUtil.isNotNull(synDingAdmin)
                    && "true".equalsIgnoreCase(synDingAdmin)) {
                saveOrUpdateSynDingAdmin();
            }

            // 钉钉角色同步
            caltime = System.currentTimeMillis();
            updateDingRoleSyn();
            temp = "角色初始化同步耗时(秒)："
                    + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);

            temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - time) / 1000;
            logger.debug(temp);
        } catch (Exception e) {
            throw e;
        } finally {
            locked = false;
            relationMap.clear();
            postMap.clear();
            personMap.clear();
            mobileMap.clear();
            loginNameMap.clear();
            allDingIds.clear();
            createDeptMap.clear();
            roleMap.clear();
            role2postMap.clear();
            role2groupMap.clear();
            role2staffingMap.clear();

            hrmPermission = true;
            errorsCount.clear();
            interruptForErrors = false;
        }
    }

    @Override
    public void synDingRoles(SysQuartzJobContext context){
        if(locked){
            logger.warn("同步定时任务在执行，禁止执行角色同步任务");
            if(context!=null){
                context.logMessage("组织机构同步到EKO的定时任务正在执行，禁止执行角色同步任务");
            }
        }else {
            this.jobContext=context;
            updateDingRoleSyn();
        }
    }

    @Override
    public void synchro(SysQuartzJobContext context) throws Exception {

    }


    private void updateDingRoleSyn() {
        // 岗位处理
        initPost();
        // 群组处理
        initGrop();
        // 职务
        initStaffing();
        // 角色线
        synchroRoleline();
    }

    // 初始化职务
    private void initStaffing() {
        TransactionStatus status = null;
        try {
            DingConfig config = DingConfig.newInstance();
            String ding2ekpRoleStaffingSynWay = config
                    .getDing2ekpRoleStaffingSynWay();
            if (StringUtil.isNotNull(ding2ekpRoleStaffingSynWay)
                    && "syn".equalsIgnoreCase(ding2ekpRoleStaffingSynWay)) {
                status = TransactionUtils.beginNewTransaction();
                String groupId = config.getDing2ekpRoleStaffing();
                logger.debug("职务groupId:" + groupId);
                String roleResult = DingUtils.dingApiService
                        .getRoleByGroupId(Long.valueOf(groupId));
                if (StringUtil.isNotNull(roleResult)) {
                    JSONObject roles = JSONObject.fromObject(roleResult);
                    if (roles.getInt("errcode") == 0) {
                        // 获取ekp的职务信息
                        HQLInfo hqlInfo = new HQLInfo();
                        hqlInfo.setWhereBlock(
                                "fdImportInfo like :info");
                        hqlInfo.setParameter("info",
                                importInfoPre + "_role_%");
                        List<SysOrganizationStaffingLevel> staffingList = getSysOrganizationStaffingLevelService()
                                .findList(hqlInfo);
                        Set<String> ekpAllRoleImport = new HashSet<String>();
                        for (SysOrganizationStaffingLevel staffing : staffingList) {
                            ekpAllRoleImport.add(staffing.getFdImportInfo());
                            role2staffingMap.put(staffing.getFdImportInfo(),
                                    staffing);
                        }
                        JSONArray roleList = roles.getJSONObject("role_group")
                                .getJSONArray("roles");
                        roleMap.put("staffing",
                                roles.getJSONObject("role_group")
                                        .getString("group_name"));
                        for (int i = 0; i < roleList.size(); i++) {
                            // com.landray.kmss.third.ding.oms.SynchroOrgDing2EkpImp_staffing_xxxx
                            JSONObject role = roleList.getJSONObject(i);
                            logger.debug("role:" + role);
                            List<SysOrgPerson> persons = new ArrayList<SysOrgPerson>();
                            setRolePersons(persons, role.getLong("role_id"));

                            // 角色
                            if (role2staffingMap.containsKey(importInfoPre
                                    + "_role_" + role.getInt("role_id"))) {
                                // 修改岗位角色
                                ekpAllRoleImport.remove(importInfoPre
                                        + "_role_" + role.getInt("role_id"));
                                logger.debug("--修改角色(群组)名称---");
                                SysOrganizationStaffingLevel _staffing = role2staffingMap
                                        .get(importInfoPre
                                                + "_role_"
                                                + role.getInt("role_id"));
                                SysOrganizationStaffingLevel staffing = (SysOrganizationStaffingLevel) getSysOrganizationStaffingLevelService()
                                        .findByPrimaryKey(_staffing.getFdId());
                                staffing.setFdName(role.getString("role_name"));
                                staffing.setDocAlterTime(new Date());
                                staffing.setFdPersons(persons);
                                // staffing.setFdDescription("来自钉钉角色："
                                // + roles.getJSONObject("role_group")
                                // .getString("group_name"));
                                getSysOrganizationStaffingLevelService()
                                        .update(staffing);
                            } else {
                                logger.debug("----新增角色（群组）----");
                                SysOrganizationStaffingLevel staffing = new SysOrganizationStaffingLevel();
                                staffing.setFdName(role.getString("role_name"));
                                staffing.setFdImportInfo(
                                        importInfoPre + "_role_"
                                                + role.getInt("role_id"));
                                staffing.setDocCreateTime(new Date());
                                staffing.setFdLevel(1);
                                staffing.setFdIsDefault(false);
                                staffing.setFdDescription("来自钉钉角色同步");
                                staffing.setFdDescription("来自钉钉角色："
                                        + roles.getJSONObject("role_group")
                                        .getString("group_name"));
                                staffing.setFdPersons(persons);
                                getSysOrganizationStaffingLevelService()
                                        .add(staffing);
                            }
                        }
                        // 处理冗余的数据
                        for (String key : ekpAllRoleImport) {
                            SysOrganizationStaffingLevel staffing = role2staffingMap
                                    .get(key);
                            log("删除多余的职务数据:" + staffing.getFdName());
                            logger.warn("删除多余的职务数据:" + staffing.getFdName());
                            staffing.setFdPersons(null);
                            getSysOrganizationStaffingLevelService()
                                    .update(staffing);
                            getSysOrganizationStaffingLevelService()
                                    .delete(staffing);
                        }

                        TransactionUtils.commit(status);
                    } else {
                        logger.warn("获取角色失败");
                    }
                } else {
                    logger.warn("获取角色失败");
                }
            }
        } catch (Exception e) {
            logger.error("初始化职务失败：" + e.getMessage(), e);
            if (status != null) {
                try {
                    TransactionUtils.rollback(status);
                } catch (Exception ex) {
                    logger.error("---事务回滚出错---", ex);
                }
            }
            addError(e);
        }
    }

    // 初始化群组
    private void initGrop() {
        TransactionStatus status = null;
        try {
            DingConfig config = DingConfig.newInstance();
            String ding2ekpRoleGroupSynWay = config
                    .getDing2ekpRoleGroupSynWay();
            if (StringUtil.isNotNull(ding2ekpRoleGroupSynWay)
                    && "syn".equalsIgnoreCase(ding2ekpRoleGroupSynWay)) {
                status = TransactionUtils.beginNewTransaction();
                String groupId = config.getDing2ekpRoleGroup();
                logger.info("群组groupId:" + groupId);
                if(StringUtil.isNull(groupId)) {
                    return;
                }

                // 获取ekp的岗位信息
                HQLInfo hqlInfo = new HQLInfo();
                hqlInfo.setWhereBlock(
                        "fdOrgType=16 and fdImportInfo like :info");
                hqlInfo.setParameter("info", importInfoPre + "_role_%");
                hqlInfo.setOrderBy("fdIsAvailable");
                List<SysOrgGroup> groupList = getSysOrgGroupService()
                        .findList(hqlInfo);
                Set<String> hadSynRoleImport = new HashSet<String>();
                for (SysOrgGroup group : groupList) {
                    role2groupMap.put(group.getFdImportInfo(), group);
                }
                String[] groupIds = groupId.split(";");
                for (int i=0;i<groupIds.length;i++) {
                    updateSigleRoleByGroupId(Long.valueOf(groupIds[i]),role2groupMap,hadSynRoleImport);
                }
                // 处理冗余数据
                for (String key : role2groupMap.keySet()) {
                    if(hadSynRoleImport.contains(key)) {
                        continue;
                    }
                    SysOrgGroup group = role2groupMap.get(key);
                    if (!group.getFdIsAvailable()) {
                        continue;
                    }
                    log("删除多余的数据：" + group.getFdName());
                    logger.warn("删除多余的数据：" + group.getFdName());
                    group.setFdIsAvailable(false);
                    getSysOrgPostService().update(group);
                }
                TransactionUtils.commit(status);
            }
        } catch (Exception e) {
            logger.error("初始化群组失败：" + e.getMessage(), e);
            if (status != null) {
                try {
                    TransactionUtils.rollback(status);
                } catch (Exception ex) {
                    logger.error("---事务回滚出错---", ex);
                }
            }
            addError(e);
        }
    }

    private void updateSigleRoleByGroupId(Long groupId, Map<String, SysOrgGroup> role2groupMap, Set<String> hadSynRoleImport) throws Exception {

        String roleResult = DingUtils.dingApiService.getRoleByGroupId(groupId);
        if (StringUtil.isNotNull(roleResult)) {
            JSONObject roles = JSONObject.fromObject(roleResult);
            if (roles.getInt("errcode") == 0) {
                JSONArray roleList = roles.getJSONObject("role_group")
                        .getJSONArray("roles");
                roleMap.put("group", roles.getJSONObject("role_group")
                        .getString("group_name"));
                for (int i = 0; i < roleList.size(); i++) {
                    // com.landray.kmss.third.ding.oms.SynchroOrgDing2EkpImp_role_xxxx
                    JSONObject role = roleList.getJSONObject(i);
                    logger.debug("role:" + role);
                    List<SysOrgPerson> persons = new ArrayList<SysOrgPerson>();
                    setRolePersons(persons, role.getLong("role_id"));
                    hadSynRoleImport.add(importInfoPre + "_role_"
                            + role.getInt("role_id"));
                    // 角色
                    if (role2groupMap.containsKey(importInfoPre+ "_role_" + role.getInt("role_id"))) {
                        // 修改群组角色
                        logger.debug("--修改角色(群组)名称---");
                        SysOrgGroup group = role2groupMap.get(importInfoPre
                                + "_role_" + role.getInt("role_id"));
                        group.setFdName(role.getString("role_name"));
                        group.setFdIsAvailable(true);
                        group.setFdAlterTime(new Date());
                        group.setFdMembers(persons);
                        getSysOrgGroupService().update(group);
                    } else {
                        logger.debug("----新增角色（群组）----");
                        SysOrgGroup group = new SysOrgGroup();
                        group.setFdName(role.getString("role_name"));
                        group.setFdImportInfo(importInfoPre + "_role_"
                                + role.getInt("role_id"));
                        group.setFdOrgType(4);
                        group.setFdCreateTime(new Date());
                        group.setFdIsAvailable(true);
                        group.setFdIsBusiness(true);
                        group.setFdIsAbandon(false);
                        group.setFdMembers(persons);
                        group.setFdMemo("来自钉钉角色："
                                + roles.getJSONObject("role_group")
                                .getString("group_name"));
                        getSysOrgGroupService().add(group);
                    }
                }


            } else {
                logger.warn("获取角色失败");
            }
        } else {
            logger.warn("获取角色失败");
        }

    }

    // 初始化岗位
    private void initPost() {
        // 判断是否需要同步角色到岗位里
        TransactionStatus status = null;
        try {
            DingConfig config = DingConfig.newInstance();
            String ding2ekpRolePostSynWay = config.getDing2ekpRolePostSynWay();
            if (StringUtil.isNotNull(ding2ekpRolePostSynWay)
                    && "syn".equalsIgnoreCase(ding2ekpRolePostSynWay)) {
                status = TransactionUtils.beginNewTransaction();
                String groupId = config.getDing2ekpRolePost();
                logger.debug("岗位groupId:" + groupId);
                String roleResult = DingUtils.dingApiService
                        .getRoleByGroupId(Long.valueOf(groupId));
                if (StringUtil.isNotNull(roleResult)) {
                    JSONObject roles = JSONObject.fromObject(roleResult);
                    if (roles.getInt("errcode") == 0) {
                        // 获取ekp的岗位信息
                        HQLInfo hqlInfo = new HQLInfo();
                        hqlInfo.setWhereBlock(
                                "fdOrgType=4 and fdImportInfo like :info");
                        hqlInfo.setParameter("info", importInfoPre + "_role_%");
                        hqlInfo.setOrderBy("fdIsAvailable");
                        List<SysOrgPost> postList = getSysOrgPostService()
                                .findList(hqlInfo);
                        // 存放ekp的role信息，以便后面移除多余的垃圾数据（切换角色导致）
                        Set<String> ekpAllRoleImport = new HashSet<String>();
                        for (SysOrgPost post : postList) {
                            ekpAllRoleImport.add(post.getFdImportInfo());
                            role2postMap.put(post.getFdImportInfo(), post);
                        }
                        JSONArray roleList = roles.getJSONObject("role_group")
                                .getJSONArray("roles");
                        roleMap.put("post", roles.getJSONObject("role_group")
                                .getString("group_name"));
                        for (int i = 0; i < roleList.size(); i++) {
                            // com.landray.kmss.third.ding.oms.SynchroOrgDing2EkpImp_role_xxxx
                            JSONObject role = roleList.getJSONObject(i);
                            logger.debug("role:" + role);
                            // 获取角色下的人员列表
                            List<SysOrgPerson> persons = new ArrayList<SysOrgPerson>();
                            setRolePersons(persons, role.getLong("role_id"));

                            // 角色
                            if (role2postMap.containsKey(importInfoPre
                                    + "_role_" + role.getInt("role_id"))) {
                                ekpAllRoleImport.remove(importInfoPre
                                        + "_role_" + role.getInt("role_id"));
                                // 修改岗位角色
                                logger.debug("--修改角色名称---");
                                SysOrgPost post = role2postMap.get(importInfoPre
                                        + "_role_" + role.getInt("role_id"));
                                post.setFdName(role.getString("role_name"));
                                post.setFdIsAvailable(true);
                                post.setFdAlterTime(new Date());
                                post.setFdPersons(persons);
                                getSysOrgPostService().update(post);
                            } else {
                                logger.debug("----新增岗位角色----");
                                SysOrgPost post = new SysOrgPost();
                                post.setFdName(role.getString("role_name"));
                                post.setFdImportInfo(importInfoPre + "_role_"
                                        + role.getInt("role_id"));
                                post.setFdOrgType(4);
                                post.setFdCreateTime(new Date());
                                post.setFdIsAvailable(true);
                                post.setFdIsBusiness(true);
                                post.setFdIsAbandon(false);
                                post.setFdPersons(persons);
                                post.setFdMemo("来自钉钉角色："
                                        + roles.getJSONObject("role_group")
                                        .getString("group_name"));
                                getSysOrgPostService().add(post);
                            }
                        }
                        // 处理冗余数据
                        for (String key : ekpAllRoleImport) {
                            SysOrgPost post = role2postMap.get(key);
                            if (!post.getFdIsAvailable()) {
                                continue;
                            }
                            log("删除多余的数据：" + post.getFdName());
                            logger.warn("删除多余的数据：" + post.getFdName());
                            post.setFdIsAvailable(false);
                            getSysOrgPostService().update(post);
                        }
                        TransactionUtils.commit(status);
                    } else {
                        logger.warn("获取角色失败");
                    }
                } else {
                    logger.warn("获取角色失败");
                }
            }
        } catch (Exception e) {
            logger.error("初始化岗位失败：" + e.getMessage(), e);
            if (status != null) {
                try {
                    TransactionUtils.rollback(status);
                } catch (Exception ex) {
                    logger.error("---事务回滚出错---", ex);
                }
            }
            addError(e);
        }
    }

    private void setRolePersons(List<SysOrgPerson> persons, Long role_id) {
        boolean hasMore = false;
        Long count = 0L;
        do {
            try {
                String simpleList = DingUtils.dingApiService
                        .getSimplelistByRoleId(role_id, count);
                if (StringUtil.isNotNull(simpleList)) {
                    JSONObject personObject = JSONObject
                            .fromObject(simpleList);
                    if (personObject.getInt("errcode") == 0) {
                        hasMore = personObject
                                .getJSONObject("result")
                                .getBoolean("hasMore");
                        JSONArray resultList = personObject
                                .getJSONObject("result")
                                .getJSONArray("list");
                        if (resultList != null
                                && !resultList.isEmpty()) {
                            for (int j = 0; j < resultList
                                    .size(); j++) {
                                String userid = resultList
                                        .getJSONObject(j)
                                        .getString("userid");
                                logger.debug(
                                        "userid:" + userid);
                                OmsRelationModel model = getOmsRelationModel(
                                        userid);
                                if (model == null) {
                                    logger.warn(
                                            "对照表找不到  userid:"
                                                    + userid
                                                    + "对应的记录");
                                    continue;
                                }
                                logger.debug("");
                                SysOrgPerson sysOrgPerson = (SysOrgPerson) sysOrgPersonService
                                        .findByPrimaryKey(
                                                model.getFdEkpId());
                                if (sysOrgPerson != null) {
                                    persons.add(sysOrgPerson);
                                }
                            }

                        }

                    }
                }
                count++;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        } while (hasMore);
    }

    private Map<String, String> getEkpRoleMap() {
        Map<String, String> map = new HashMap<String, String>();
        DingConfig config = DingConfig.newInstance();
        String ding2ekpRoleStaffingSynWay = config
                .getDing2ekpRoleStaffingSynWay();
        // 职务
        if (StringUtil.isNotNull(ding2ekpRoleStaffingSynWay)
                && "syn".equalsIgnoreCase(ding2ekpRoleStaffingSynWay)) {
            String staffingId = config.getDing2ekpRoleStaffing();
            map.put("staffing", staffingId);
        }

        // 岗位
        String ding2ekpRolePostSynWay = config.getDing2ekpRolePostSynWay();
        if (StringUtil.isNotNull(ding2ekpRolePostSynWay)
                && "syn".equalsIgnoreCase(ding2ekpRolePostSynWay)) {
            String postId = config.getDing2ekpRolePost();
            map.put("post", postId);
        }

        // 群组
        String ding2ekpRoleGroupSynWay = config
                .getDing2ekpRoleGroupSynWay();
        if (StringUtil.isNotNull(ding2ekpRoleGroupSynWay)
                && "syn".equalsIgnoreCase(ding2ekpRoleGroupSynWay)) {
            String groupId = config.getDing2ekpRoleGroup();
            logger.debug("群组groupId:" + groupId);
            map.put("group", groupId);
        }

        logger.debug(map.toString());
        return map;
    }

    /*
     * 同步钉钉管理员
     */
    private void saveOrUpdateSynDingAdmin() {
        logger.debug("同步钉钉管理员到ekp");
        log("同步钉钉管理员到ekp:");
        TransactionStatus status = null;
        try {
            JSONObject adminJSON = dingApiService.getAdmin();
            logger.debug("钉钉管理员：" + adminJSON);
            if (adminJSON == null || adminJSON.isEmpty()) {
                return;
            }
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
                        .findByPrimaryKey(groupId, SysOrgElement.class, true);
            }
            if (group == null) {
                log("根据名称  ‘系统级_子管理员’找对应的群组...");
                logger.debug("根据名称  ‘系统级_子管理员’找到对应的群组");
                HQLInfo hql = new HQLInfo();
                hql.setWhereBlock("fdName=:fdName");
                hql.setParameter("fdName", "系统级_子管理员");
                group = (SysOrgGroup) sysOrgElementService.findFirstOne(hql);
            }

            if (group == null) {
                log("找不到  '系统级_子管理员'  群组，无法将管理员同步到该群组");
                logger.warn("找不到  '系统级_子管理员'  群组，无法将管理员同步到该群组");
                return;
            }
            group.setFdMembers(sysOrgElementList);
            sysOrgElementService.update(group);

            TransactionUtils.commit(status);
        } catch (Exception e) {
            logger.error("同步钉钉管理员失败：" + e.getMessage(), e);
            if (status != null) {
                try {
                    TransactionUtils.rollback(status);
                } catch (Exception ex) {
                    logger.error("---事务回滚出错---", ex);
                }
            }
            addError(e);
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
            logger.debug("根据名称  ‘系统级_子管理员’找到对应的群组");
            HQLInfo hql = new HQLInfo();
            hql.setWhereBlock("fdName=:fdName");
            hql.setParameter("fdName", "系统级_子管理员");
            group = (SysOrgGroup) sysOrgElementService.findFirstOne(hql);
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
                logger.debug("更新管理员信息：" + admin_userid);
                log("更新管理员信息：" + admin_userid);
                String fdId = relationMap.get(admin_userid + "|8");
                logger.warn("管理员的fdId:" + fdId);
            } else {
                // 当前ekp无该管理员的信息
                logger.debug("新增管理员信息：" + admin_userid);
                log("新增管理员信息：" + admin_userid);
                updateUser(dingApiService.userGet(admin_userid, null), true,
                        null,
                        getDingMainDeptId(dingApiService.getAccessToken(),
                                admin_userid));

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
            logger.debug("ekpUserId:" + ekpUserId);
            if (StringUtil.isNull(ekpUserId) && isAdd) {
                // 用户没有则新增
                JSONObject dingUser = dingApiService.userGet(admin_userid,
                        null);
                saveOrUpdateCallbackUser(dingUser, true);
                if (relationMap.containsKey(admin_userid + "|8")) {
                    logger.debug("-=======新增了管理员======" + admin_userid);
                    ekpUserId = relationMap.get(admin_userid + "|8");
                }
            }
            logger.debug("final ekpUserId:" + ekpUserId);
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

    private void updatePersons() throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                TransactionStatus status = null;
                try {
                    status = TransactionUtils.beginNewTransaction();
                    List<SysOrgPerson> persons = sysOrgPersonService.findList(
                            "fdImportInfo is not null and fdIsAvailable = 1",
                            null);
                    for (SysOrgPerson person : persons) {
                        logger.debug("更新用户：" + person.getFdLoginName());
                        sysOrgPersonService.update(person);
                    }
                    TransactionUtils.commit(status);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    if (status != null) {
                        TransactionUtils.rollback(status);
                    }
                }
            }

        });
        thread.start();
        TimeUnit.HOURS.timedJoin(thread, 2);

        if (thread.isAlive()) {
            thread.interrupt();
            throw new TimeoutException(
                    "更新用户信息超时");
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
        String temp = "存在运行中的钉钉到EKP组织架构同步任务，当前任务中断...";
        this.jobContext = context;
        if (locked) {
            logger.error(temp);
            jobContext.logError(temp);
            return;
        }
        if (!"true".equals(DingConfig.newInstance().getDingEnabled())) {
            temp = "钉钉集成已经关闭，故不同步数据";
            logger.debug(temp);
            context.logMessage(temp);
            return;
        }
        if (StringUtil.isNotNull(DingConfig.newInstance().getSyncSelection())) {
            if (!"2".equals(DingConfig.newInstance().getSyncSelection())) {
                temp = "钉钉组织架构接入已经关闭，故不同步数据";
                logger.debug(temp);
                return;
            }
        } else {
            if (!"true"
                    .equals(DingConfig.newInstance().getDingOmsInEnabled())) {
                temp = "钉钉组织架构接入已经关闭，故不同步数据";
                logger.debug(temp);
                return;
            }
        }
        locked = true;
        SynchroInModel model = new SynchroInModel();
        log("开始钉钉到EKP组织架构同步....");
        try {
            long time = System.currentTimeMillis();
            getComponentLockService().tryLock(model, "omsIn");

            // 初始化数据
            long caltime = System.currentTimeMillis();
            init();
            temp = "初始化数据耗时(秒)："
                    + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);
            context.logMessage(temp);


            // 处理钉钉到EKP组织架构
            caltime = System.currentTimeMillis();
            updateSyncOrgElements();
            temp = "处理钉钉到EKP组织架构耗时(秒)："
                    + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);
            context.logMessage(temp);

            caltime = System.currentTimeMillis();
            updatePersons();
            temp = "更新人员详细信息耗时(秒)："
                    + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);
            context.logMessage(temp);

            // 同步钉钉管理员
            String synDingAdmin = DingConfig.newInstance()
                    .getDingAdminSynEnabled();
            if (StringUtil.isNotNull(synDingAdmin)
                    && "true".equalsIgnoreCase(synDingAdmin)) {
                saveOrUpdateSynDingAdmin();
            } else {
                logger.warn("同步钉钉管理员开关未开启，不同步钉钉管理员：" + synDingAdmin);
            }

            // 钉钉角色初始化
            caltime = System.currentTimeMillis();
            updateDingRoleSyn();
            temp = "钉钉角色同步耗时(秒)："
                    + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);
            context.logMessage(temp);

            temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - time) / 1000;
            logger.debug(temp);
            context.logMessage(temp);
            getComponentLockService().unLock(model);
        } catch(ConcurrencyException e){
            temp = "存在运行中的钉钉到EKP组织架构同步任务，当前任务中断...";
            logger.error(temp);
            jobContext.logError(temp);
        } catch (Exception e) {
            logger.error("钉钉到EKP组织架构同步失败：", e);
            getComponentLockService().unLock(model);
        } finally {
            locked = false;
            relationMap.clear();
            postMap.clear();
            personMap.clear();
            mobileMap.clear();
            loginNameMap.clear();
            allDingIds.clear();
            createDeptMap.clear();
            roleMap.clear();
            role2postMap.clear();
            role2groupMap.clear();
            role2staffingMap.clear();
        }
    }

    // 提供后台调用（F4专用）
    @Override
    public void triggerSynchro() throws Exception {
        String temp = "存在运行中的钉钉到EKP组织架构同步任务，当前任务中断...";
        if (locked) {
            logger.error(temp);
            return;
        }
        if (!"true".equals(DingConfig.newInstance().getDingEnabled())) {
            temp = "钉钉集成已经关闭，故不同步数据";
            logger.debug(temp);
            return;
        }
        if (StringUtil.isNotNull(DingConfig.newInstance().getSyncSelection())) {
            if (!"2".equals(DingConfig.newInstance().getSyncSelection())) {
                temp = "钉钉组织架构接入已经关闭，故不同步数据";
                logger.debug(temp);
                return;
            }
        } else {
            if (!"true"
                    .equals(DingConfig.newInstance().getDingOmsInEnabled())) {
                temp = "钉钉组织架构接入已经关闭，故不同步数据";
                logger.debug(temp);
                return;
            }
        }

        locked = true;
        log("开始钉钉到EKP组织架构同步....");
        try {
            long time = System.currentTimeMillis();

            // 初始化数据
            long caltime = System.currentTimeMillis();
            init();
            temp = "初始化数据耗时(秒)："
                    + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);

            // 处理钉钉到EKP组织架构
            caltime = System.currentTimeMillis();
            updateSyncOrgElements();
            temp = "处理钉钉到EKP组织架构耗时(秒)："
                    + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);

            // 同步钉钉管理员
            logger.warn("--------------------");
            String synDingAdmin = DingConfig.newInstance()
                    .getDingAdminSynEnabled();
            if (StringUtil.isNotNull(synDingAdmin)
                    && "true".equalsIgnoreCase(synDingAdmin)) {
                saveOrUpdateSynDingAdmin();
            }

            temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - time) / 1000;
            logger.debug(temp);
        } catch (Exception e) {
            logger.error("钉钉到EKP组织架构同步失败：", e);
        } finally {
            locked = false;
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

        hrmPermission = true;
        errorsCount.clear();
        interruptForErrors = false;
        if ("false".equals(DingConfig.newInstance()
                .getAssociatedExternalEnabled())) {
            associatedExternalEnable = false;
            log("不接入钉钉端外部组织");
        } else {
            associatedExternalEnable = true;
            log("接入钉钉端外部组织！！！");
        }

        // 查询钉钉同步根机构信息
        dingOrgId2ekp = DingConfig.newInstance().getDingOrgId2ekp();
        if (StringUtil.isNotNull(dingOrgId2ekp)) {
            log("钉钉同步根机构id为：" + dingOrgId2ekp);
        } else {
            log("钉钉同步根机构id为空，默认同步钉钉通讯录所有组织!");
        }
        // 清洗重复的数据
        updateHandlerRepeatData();
        updateRelationType();
        List list = omsRelationService
                .findList("fdAppKey='" + getAppKey() + "'", null);
        for (int i = 0; i < list.size(); i++) {
            OmsRelationModel model = (OmsRelationModel) list.get(i);
            if (StringUtil.isNull(model.getFdType())
                    && StringUtil.isNotNull(model.getFdEkpId())) {
                SysOrgElement ele = (SysOrgElement) sysOrgElementService
                        .findByPrimaryKey(model.getFdEkpId(), null,
                                true);
                if (ele != null) {
                    if (8 == ele.getFdOrgType()) {
                        model.setFdType("8");
                    } else if (2 == ele.getFdOrgType()
                            || 1 == ele.getFdOrgType()) {
                        model.setFdType("2");
                    }
                    omsRelationService.update(model);
                } else {
                    omsRelationService.delete(model);
                }
            }
            relationMap.put(model.getFdAppPkId() + "|" + model.getFdType(),
                    model.getFdEkpId());
        }
        log("系统中完成映射的数量(用户+机构/部门):" + list.size());

        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fdIsAvailable = :fdIsAvailable and fdOrgType=8");
        hqlInfo.setParameter("fdIsAvailable", true);
        List<SysOrgPerson> personList = sysOrgPersonService.findList(hqlInfo);
        for (SysOrgPerson ele : personList) {
            if (StringUtil.isNotNull(ele.getFdLoginName())) {
                loginNameMap.put(ele.getFdLoginName(), ele);
            }
        }
        log("系统中已填写登录名的用户数:" + loginNameMap.size());

        hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock(
                "sysOrgPerson.fdIsAvailable = :fdIsAvailable and sysOrgPerson.fdMobileNo is not null");
        hqlInfo.setParameter("fdIsAvailable", true);
        personList = sysOrgPersonService.findList(hqlInfo);
        for (SysOrgPerson person : personList) {
            if (StringUtil.isNotNull(person.getFdMobileNo())) {
                mobileMap.put(person.getFdMobileNo(), person);
            }
        }
        log("系统中已填写手机号的用户数:" + mobileMap.size());

        hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fdOrgType=8 and fdImportInfo like :info");
        hqlInfo.setParameter("info", importInfoPre + "_person_%");
        hqlInfo.setOrderBy("fdIsAvailable");
        personList = sysOrgPersonService.findList(hqlInfo);
        String imkey = null;
        for (SysOrgPerson person : personList) {
            imkey = person.getFdImportInfo().replace(importInfoPre + "_person_",
                    "");
            if (StringUtil.isNotNull(imkey)) {
                personMap.put(imkey, person);
            }
        }
        log("系统中已映射的人员数:" + personMap.size());

        hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fdOrgType=4 and fdImportInfo like :info");
        hqlInfo.setParameter("info", importInfoPre + "_post_%");
        hqlInfo.setOrderBy("fdIsAvailable");
        List<SysOrgPost> postList = getSysOrgPostService().findList(hqlInfo);
        for (SysOrgPost post : postList) {
            postMap.put(post.getFdImportInfo(), post);
        }
        log("系统中已映射的岗位(主管/成员)数:" + postMap.size());

        // 查询初始密码
        setInitPassword();

    }

    private void setInitPassword() {
        // 查询初始密码

        try {
            ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil
                    .getBean("sysAppConfigService");
            Map orgMap = sysAppConfigService.findByKey(
                    "com.landray.kmss.sys.organization.model.SysOrgDefaultConfig");
            if (orgMap != null && orgMap.containsKey("orgDefaultPassword")) {
                Object pwd = orgMap.get("orgDefaultPassword");
                if (pwd != null && StringUtil.isNotNull(pwd.toString())) {
                    initPassword = pwd.toString().trim();
                    logger.debug("人员初始密码为：" + initPassword);
                } else {
                    initPassword = "";
                }
            }
        } catch (Exception e) {
            logger.error("设置初始密码发生异常！");
            logger.error("", e);
        }

    }

    /**
     * 处理钉钉到EKP组织架构
     */
    private void updateSyncOrgElements() throws Exception {
        JSONObject ret = null;
        if (StringUtil.isNotNull(dingOrgId2ekp)) {
            String[] dingOrgIds = dingOrgId2ekp.split(";");
            for (int i = 0; i < dingOrgIds.length; i++) {
                JSONObject ret1 = dingApiService
                        .departsGet(dingOrgIds[i]);
                logger.debug("获取钉钉部门：" + dingOrgIds[i] + "  return -> " + ret1);
                if (ret1.getInt("errcode") != 0) {
                    log("获取部门信息失败，钉钉部门id：" + dingOrgIds[i]);
                    throw new Exception("获取部门信息失败，同步中断。" + "钉钉部门id："
                            + dingOrgIds[i] + "," + ret1);
                }
                if (ret == null) {
                    ret = ret1;
                } else {
                    JSONArray depts = ret.getJSONArray("department");
                    JSONArray depts1 = ret1.getJSONArray("department");
                    depts.addAll(depts1);
                    ret.put("department", depts);
                }
                JSONObject dept_this = dingApiService
                        .departGet(Long.parseLong(dingOrgIds[i]));
                ret.getJSONArray("department").add(dept_this);
            }
        } else {
            ret = dingApiService.departGet();
        }

        logger.debug("钉钉同步的部门：" + ret);
        if (ret == null) {
            log("获取钉钉部门列表发生不可预知的错误");
        } else {
            if (ret.getInt("errcode") == 0) {
                JSONArray depts = ret.getJSONArray("department");
                Map<String, String> hierMaps = new HashMap<String, String>(
                        depts.size());
                Map<String, JSONObject> deptMaps = new HashMap<String, JSONObject>(
                        depts.size());
                String id = null;
                JSONObject jdept = null;
                StringBuffer parents = new StringBuffer();
                Map<String, String> map = new HashMap<String, String>();
                for (int k = 0; k < depts.size(); k++) {
                    jdept = depts.getJSONObject(k);

                    if (jdept.toString().contains("isFromUnionOrg")) {
                        if (!associatedExternalEnable) {
                            continue;
                        }
                    }
                    if (!jdept.containsKey("parentid")
                            || "1".equals(jdept.getString("id"))) {
                        map.put(jdept.getString("id"), "");
                    } else if (jdept.containsKey("parentid")) {
                        if (StringUtil.isNotNull(dingOrgId2ekp) && dingOrgId2ekp
                                .contains(jdept.getString("id"))) {
                            map.put(jdept.getString("id"), "");

                        }
                        map.put(jdept.getString("id"),
                                jdept.getString("parentid"));
                    }
                }
                for (int k = 0; k < depts.size(); k++) {
                    if (depts.getJSONObject(k).toString()
                            .contains("isFromUnionOrg")) {
                        if (!associatedExternalEnable) {
                            continue;
                        }
                    }
                    parents.setLength(0);
                    id = depts.getJSONObject(k).getString("id");
                    allDingIds.add(id + "|2");
                    getParent(id, parents, map, 0);
                    hierMaps.put(id, parents.toString());
                    deptMaps.put(id, depts.getJSONObject(k));
                }
                List<String> hiers = new ArrayList<String>(hierMaps.values());
                Collections.sort(hiers, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        if (o1.length() > o2.length()) {
                            return 1;
                        } else if (o1.length() < o2.length()) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                });
                log("\t从钉钉获取部门数:" + depts.size() + "(包括关联的外部组织在内)");
                for (String deptId : hiers) {
                    jdept = deptMaps.get(deptId.split("\\|")[0]);
                    updateDept(jdept, false, false);
                    if (interruptForErrors) {
                        break;
                    }
                }
                for (Object obj : depts) {
                    JSONObject dept = (JSONObject) obj;
                    if (StringUtil.isNotNull(dingOrgId2ekp)) {
                    }
                    updateUsers(dept);
                }

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
                SetView<String> diff_ekpIds = Sets.difference(ekpIds, dingIds);
                log("清理钉钉端不存在的映射关系处理:" + diff_ekpIds);
                for (String dingId : diff_ekpIds) {
                    if (dingId.contains("|")) {
                        dingId = dingId.substring(0, dingId.indexOf("|"));
                    }
                    cleanData(dingId);
                }

                // ekp中存在而钉钉中不存在的信息处理
                String orgHandle = DingConfig.newInstance()
                        .getDing2ekpOrgHandle();
                if ("autoDisable".equals(orgHandle)) {
                    logger.debug(
                            "==========设置了钉钉到ekp全量同步时删除ekp中的多余的组织架构数据(该数据钉钉断不存在)========");
                    omsRelationService.deleteEkpOrg();
                } else {
                    logger.debug(
                            "==========设置了钉钉到ekp全量同步时对ekp中的多余的组织架构数据(该数据钉钉断不存在)不处理=========");
                }
            } else {
                log(" 失败,出错信息：" + ret.getString("errmsg"));
            }
        }
    }

    private void getParent(String id, StringBuffer ids,
                           Map<String, String> map, int count) {
        if (map.containsKey(id)) {
            ids.append(id + "|");
            if (count > 15) {
                logger.error("构建上级部门层级出现死循环，请检查数据，本部门id:" + id + "，构建后的层级id:"
                        + ids.toString());
                return;
            }
            count++;
            getParent(map.get(id), ids, map, count);
        }
    }

    /**
     * 处理单个钉钉部门信息
     *
     * @throws Exception
     */
    private void updateDept(JSONObject element, boolean flag, boolean callback)
            throws Exception {
        TransactionStatus status = null;
        try {
            if (element.toString().contains("isFromUnionOrg")) {
                if (!associatedExternalEnable) {
                    return;
                }
            }
            status = TransactionUtils.beginNewTransaction();
            String deptId = element.getString("id");
            String name = element.getString("name");
            String msg = "\t开始同步部门：";
            SysOrgElement orgOrDept = null;
            logger.debug("部门信息：" + element.toString());
            synchronized (globalLock) {
                boolean isCreate = false;
                if (createDeptMap != null && createDeptMap
                        .containsKey(element.getString("name"))) {
                    logger.debug("该部门是新增的范畴：" + element.getString("name"));
                    isCreate = true;
                }
                String addOrUpdate = "add";
                if (orgOrDept == null) {
                    orgOrDept = getSysOrgDept(deptId, true);
                    if (orgOrDept != null) {
                        addOrUpdate = "update";
                        updateDept(orgOrDept, element, callback, "update");
                        log(msg + "更新部门(名称=" + name + ",dingId=" + deptId
                                + ",EKPId=" + orgOrDept.getFdId() + ")");
                    }
                }
                if (orgOrDept == null) {
                    orgOrDept = new SysOrgDept();
                    addOrUpdate = "add";
                    updateDept(orgOrDept, element, callback, "add");
                    log(msg + "新增部门(名称=" + name + ",dingId=" + deptId
                            + ",EKPId=" + orgOrDept.getFdId()
                            + ")并建立映射关系和对应的岗位");
                }
                if (orgOrDept != null) {
                    updateRelation(element.getString("id"), orgOrDept.getFdId(),
                            "2", element);
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
                                && deptId.equals(outerPermitDepts)) {
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
                        range.setFdIsOpenLimit(true);
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
                    logger.debug("deptLeaderSynWay:" + deptLeaderSynWay);
                    if (StringUtil.isNotNull(deptLeaderSynWay)) {
                        logger.debug("新配置方式处理部门主管");
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
                        logger.debug("旧方式处理部门主管");
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
                        logger.debug("新配置方式处理一人多部门");
                        if (("add".equals(addOrUpdate)
                                && !"noSyn".equals(deptSynWay))
                                || ("update".equals(addOrUpdate)
                                && "syn".equals(deptSynWay)
                                || (isCreate && !"noSyn"
                                .equals(deptSynWay)))) {
                            String deptSyn = DingConfig.newInstance()
                                    .getDing2ekpDepartment();
                            logger.debug("deptSyn:" + deptSyn);
                            if ("multDept".equals(deptSyn)) {
                                SysOrgPost post = addPost(name, deptId,
                                        "person", orgOrDept);
//                                getSysOrgPostService().getBaseDao()
//                                        .getHibernateSession().merge(post);
                            }
                        }

                    } else {
                        logger.debug("旧方式处理一人多部门");
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
            TransactionUtils.commit(status);
        } catch (Exception e) {
            logger.error("新增/更新部门失败:", e);
            if (status != null) {
                try {
                    TransactionUtils.rollback(status);
                } catch (Exception ex) {
                    logger.error("---事务回滚出错---", ex);
                }
            }
            addError(e);
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
        logger.debug("deptNameSynWay:" + deptNameSynWay);
        if (StringUtil.isNotNull(deptNameSynWay)) {
            logger.debug("新配置方式处理部门名称");
            if ("add".equals(addOrUpdate) || ("update".equals(addOrUpdate)
                    && "syn".equals(deptNameSynWay))) {
                elem.setFdName(element.getString("name"));
            }

        } else {
            logger.debug("旧方式处理部门名称");
            elem.setFdName(element.getString("name"));
        }
        elem.setFdIsAvailable(true);
        elem.setFdIsBusiness(true);
        elem.setFdImportInfo(
                importInfoPre + "_dept_" + element.getString("id"));
        String dingId = element.getString("id");
        if (StringUtil.isNotNull(dingId)) {
            JSONObject jo = dingApiService.departGet(Long.parseLong(dingId));
            String deptOrderSynWay = DingConfig.newInstance()
                    .getDing2ekpDeptOrderSynWay();
            logger.debug("deptOrderSynWay：" + deptOrderSynWay);
            if (StringUtil.isNotNull(deptOrderSynWay)) {
                logger.debug("新配置方式处理部门排序");
                if (("add".equals(addOrUpdate)
                        && !"noSyn".equals(deptOrderSynWay))
                        || ("update".equals(addOrUpdate)
                        && "syn".equals(deptOrderSynWay))) {
                    if (jo.containsKey("order")) {
                        elem.setFdOrder(jo.getInt("order"));
                    } else {
                        logger.debug(elem.getFdName() + "部门返回不含排序信息");
                    }
                }
            } else {
                logger.debug("旧方式处理部门排序");
                if (jo.containsKey("order")) {
                    elem.setFdOrder(jo.getInt("order"));
                } else {
                    logger.debug(elem.getFdName() + "部门返回不含排序信息");
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
        logger.debug("deptParentDeptSynWay:" + deptParentDeptSynWay);
        if (StringUtil.isNotNull(deptParentDeptSynWay)) {
            logger.debug("新配置方式处理部门的上级部门");
            if ("add".equals(addOrUpdate) || ("update".equals(addOrUpdate)
                    && "syn".equals(deptParentDeptSynWay))) {
                elem.setFdParent(parentDept);
            }
        } else {
            logger.debug("旧方式处理部门的上级部门");
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
        List<Userlist> userlists = new ArrayList<Userlist>();
        dingApiService.userList(userlists, deptId, 0L);
        if (userlists != null && !userlists.isEmpty()) {
            JSONArray users = JSONArray.fromObject(userlists);
            log(msg + "(员工数:" + users.size() + ")");
            TransactionStatus status = null;
            try {
                status = TransactionUtils.beginNewTransaction();
                for (Object obj : users) {
                    JSONObject dept_user = (JSONObject) obj;
                    allDingIds.add(dept_user.getString("userid") + "|8");
                    // 部门下的用户信息不足，所以需要根据userid去获取用户的详情
                    JSONObject user = dingApiService
                            .userGet(dept_user.getString("userid"), null);
                    allDingIds.add(user.getString("userid") + "|8");
                    String mainDeptId = String.valueOf(deptId);
                    try {
                        mainDeptId = getDingMainDeptId(
                                dingApiService.getAccessToken(),
                                user.getString("userid"));
                        if (StringUtil.isNotNull(mainDeptId)
                                && !allDingIds.contains(mainDeptId + "|2")) {
                            logger.debug("人员" + user.getString("name")
                                    + "的主部门不在同步范围内，设置当前部门为主部门       钉钉主部门id:"
                                    + mainDeptId + " 当前钉钉部门id:" + deptId);
                            jobContext.logMessage("人员" + user.getString("name")
                                    + "的主部门不在同步范围内，设置当前部门为主部门     钉钉原主部门id:"
                                    + mainDeptId + " 当前钉钉部门id:" + deptId
                                    + " 当前部门名称：" + name);
                            mainDeptId = String.valueOf(deptId);
                        }
                    } catch (Exception e2) {
                        mainDeptId = String.valueOf(deptId);
                        logger.error("获取人员主部门失败！" + user);
                        logger.error(e2.getMessage(), e2);
                    }
                    updateUser(user, false, deptId, mainDeptId);

                    if (interruptForErrors) {
                        logger.warn("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"
                                + interruptForErrors);
                        break;
                    }
                }
                logger.warn("------------提交事务  start--------------");
                TransactionUtils.getTransactionManager().commit(status);
                logger.warn("------------提交事务  end--------------");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                TransactionUtils.getTransactionManager().rollback(status);
            } finally {

            }
        }
        return;
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

    private void addError(Exception e) {
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
                            String mainDeptId)
            throws Exception {
        TransactionStatus status = null;
        try {
            logger.debug("处理单个钉钉用户信息:element：" + element);
            logger.debug("flag:" + flag + "  deptId:" + deptId + "  mainDeptId:"
                    + mainDeptId);


            if (!element.containsKey("mobile")) {
                element.put("mobile", "");
            }
            if (!element.containsKey("jobnumber")) {
                element.put("jobnumber", "");
            }
            String userid = element.getString("userid");
            String mobileNo = getUserMobile(element);
            String name = EmojiFilter.filterEmoji(element.getString("name"));
            String info = importInfoPre + "_person_" + userid;
            String msg = "\t开始同步用户${name}(userid=${userid},mobile=${mobileNo})..."
                    .replace("${name}", name)
                    .replace("${userid}", userid)
                    .replace("${mobileNo}", mobileNo);
            SysOrgPerson person_tmp = getSysOrgPerson(userid, name, mobileNo);
            status = TransactionUtils.beginNewTransaction();
            SysOrgPerson person = null;
            if (person_tmp != null) {
                person = (SysOrgPerson) sysOrgPersonService
                        .findByPrimaryKey(person_tmp.getFdId(), null, true);
            }
            String addOrUpdate = "add";
            if (person != null) {
                // 生态组织人员不接受修改回调
                if (SysOrgEcoUtil.IS_ENABLED_ECO && BooleanUtils.isTrue(person.getFdIsExternal())) {
                    if (status != null) {
                        TransactionUtils.getTransactionManager().rollback(status);
                    }
                    logger.debug("生态组织人员不接受修改回调：" + element.getString("name") + " " + element);
                    return;
                }
                addOrUpdate = "update";
                logger.debug("============updateUser============"
                        + person.getFdName() + " " + element);
                updateUser(person, element, mainDeptId);

            }

            if (person == null) {
                addOrUpdate = "add";
                msg += "根据匹配原则(钉钉映射信息/EKP人员同步信息/手机号)无法匹配对应人员，直接新增人员${name}"
                        .replace("${name}", name);
                if (deptId == null) {
                    // 保存EKP人员时修改标识覆盖新建标识
                    person = handleIsvPerson(element, mainDeptId);
                } else if (deptId != null) {
                    SysOrgElement sysOrgElement = sysOrgCoreService
                            .format(sysOrgCoreService.findByImportInfo(info));
                    if (sysOrgElement == null) {
                        person = new SysOrgPerson();
                        // 强制使用手机号MD5为ID，确保人员不重复
                        person.setFdId(getPersonId(mobileNo));
                        addUser(person, element, mainDeptId);
                    }
                }
            }
            log(msg);
            if (person != null) {
                // 建立人员映射关系
                updateRelation(userid, person.getFdId(), "8", element);
                // 岗位关系关联
                addPost(element, person, deptId, addOrUpdate);
                sysOrgPersonService.update(person);
//				sysOrgPersonService.getBaseDao().getHibernateSession()
//						.merge(person);
                // 处理人员角色信息
                // logger.warn("--------------处理人员角色信息--------------");
                // updateUserRoles(element);
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
                        SysOrgElement ele = (SysOrgElement) sysOrgElementService
                                .findByPrimaryKey(person.getFdId());
                        ele.setFdIsExternal(true);
                        sysOrgElementService.update(ele);
                    } else {
                        // 非生态组织，则回滚
                        log(msg + "ekp同步到钉钉,仅支持生态组织实时同步!同步人员"
                                + element.getString("name") + "(id=" + userid
                                + ")");
                        if (status != null) {
                            try {
                                TransactionUtils.rollback(status);
                            } catch (Exception ex) {
                                logger.error(ex.getMessage(), ex);
                            }
                        }
                        return;
                    }
                }

                // 邀请外部人员成功，推送通知
                if (isExternal) {
                    pushAddPersonMessage(element);
                }

            } else {
                log(msg + "同步人员" + element.getString("name") + "(id=" + userid
                        + ")到EKP失败");
            }
            TransactionUtils.commit(status);
        } catch (Exception e) {
            logger.error("更新人员失败", e);
            addError(e);
            if (status != null) {
                try {
                    TransactionUtils.getTransactionManager().rollback(status);
                } catch (Exception ex) {
                    logger.error("---事务回滚出错---", ex);
                }
            }
        }
    }

    // 更新单个用户的角色管理
    private void updateUserRoles(JSONObject element) {
        if (element == null) {
            return;
        }
        //判断是否需要角色同步
        if (roleMap == null || roleMap.isEmpty()) {
            logger.debug("角色不需要同步");
            return;
        }
        logger.warn("--------------------------roleMap:" + roleMap + "  size:"
                + roleMap.size());

        JSONArray roles = null;
        if (element.containsKey("roles")) {
            roles = element.getJSONArray("roles");
        } else if (!element.containsKey("orderInDepts")) {
            // 获取部门下的人员时，没有角色信息
            try {
                JSONObject userInfo = dingApiService.userGet(
                        element.getString("userid"),
                        null);
                if (userInfo != null && userInfo.containsKey("roles")) {
                    roles = userInfo.getJSONArray("roles");
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (roles == null || roles.isEmpty()) {
            logger.warn("角色为空！");
            return;
        }
        logger.debug("roles:" + roles);
        for (int i = 0; i < roles.size(); i++) {
            JSONObject role = roles.getJSONObject(i);
            String groupName = role.getString("groupName");
            if (roleMap.containsValue(groupName)) {

                for (String key : roleMap.keySet()) {
                    if (!groupName.equals(roleMap.get(key))) {
                        continue;
                    }
                    if ("post".equals(key)) {
                        SysOrgPost post = role2postMap.get(
                                importInfoPre + "_role_" + role.getInt("id"));
                        updatePostRole(element.getString("userid"), post);
                    }
                    if ("group".equals(key)) {
                        SysOrgGroup group = role2groupMap.get(
                                importInfoPre + "_role_" + role.getInt("id"));
                        updateGroupRole(element.getString("userid"), group);
                    }
                    if ("staffing".equals(key)) {
                        SysOrganizationStaffingLevel staffing = role2staffingMap
                                .get(importInfoPre + "_role_"
                                        + role.getInt("id"));
                        pushStaffingRole(element.getString("userid"), staffing);

                    }
                }

            } else {
                logger.warn("该角色不在同步范围内：" + role);
            }

        }
        logger.warn("-------------更新个人角色结束-------------"
                + element.getString("name"));

    }

    // 添加人员到职务里
    private void pushStaffingRole(String dingUserId,
                                  SysOrganizationStaffingLevel staffing) {

        logger.debug("===---同步职务--====" + dingUserId);
        if (dingUserId == null || staffing == null) {
            return;
        }
        logger.debug("---同步职务名称---" + staffing.getFdName());
        try {
            OmsRelationModel omsRelationModel = getOmsRelationModel(dingUserId);
            String ekpUserId = omsRelationModel.getFdEkpId();
            SysOrgPerson user = (SysOrgPerson) sysOrgPersonService
                    .findByPrimaryKey(ekpUserId);
            List<SysOrgPerson> persons = staffing.getFdPersons();
            if (persons != null && !persons.isEmpty()) {
                for (SysOrgPerson per : persons) {
                    if (per.getFdId().equals(ekpUserId)) {
                        logger.debug("该职务已经包括这个用户了");
                        return;
                    }
                }
            }
            logger.debug("===---添加职务--====" + staffing.getFdName());
            if (persons == null) {
                persons = new ArrayList<SysOrgPerson>();
            }
            persons.add(user);
            SysOrganizationStaffingLevel _staffing = (SysOrganizationStaffingLevel) getSysOrganizationStaffingLevelService()
                    .findByPrimaryKey(staffing.getFdId());
            _staffing.setFdPersons(persons);
            // getSysOrganizationStaffingLevelService().getBaseDao()
            // .getHibernateSession().update(_staffing);
            user.setFdStaffingLevel(_staffing);
            sysOrgPersonService.getBaseDao()
                    .getHibernateSession().update(user);

            logger.debug("===---添加个人到职务中结束--====" + user.getFdName());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 将人员移出职务
    private void removeStaffingRole(String dingUserId,
                                    SysOrganizationStaffingLevel staffing) {
        if (dingUserId == null || staffing == null) {
            return;
        }
        try {
            OmsRelationModel omsRelationModel = getOmsRelationModel(dingUserId);
            String ekpUserId = omsRelationModel.getFdEkpId();

            List<SysOrgPerson> persons = staffing.getFdPersons();
            if (persons != null && !persons.isEmpty()) {
                for (SysOrgPerson per : persons) {
                    if (per.getFdId().equals(ekpUserId)) {
                        logger.debug("该职务移除这个用户");
                        persons.remove(per);
                        break;
                    }
                }
            }
            SysOrganizationStaffingLevel _staffing = (SysOrganizationStaffingLevel) getSysOrganizationStaffingLevelService()
                    .findByPrimaryKey(staffing.getFdId());
            _staffing.setFdPersons(persons);
            getSysOrganizationStaffingLevelService().getBaseDao()
                    .getHibernateSession().update(_staffing);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 添加人员到群组里
    private void updateGroupRole(String dingUserId, SysOrgGroup group) {
        if (dingUserId == null || group == null) {
            return;
        }
        try {
            OmsRelationModel omsRelationModel = getOmsRelationModel(dingUserId);
            String ekpUserId = omsRelationModel.getFdEkpId();
            SysOrgPerson user = (SysOrgPerson) sysOrgPersonService
                    .findByPrimaryKey(ekpUserId);
//			addUser2Gruop(user, (SysOrgGroup) getSysOrgGroupService()
//					.findByPrimaryKey(group.getFdId()));
            SysOrgGroup _group = (SysOrgGroup) getSysOrgGroupService()
                    .findByPrimaryKey(group.getFdId());
            List<SysOrgElement> sysOrgElements = group.getFdMembers();
            if (sysOrgElements == null || sysOrgElements.isEmpty()) {
                List<SysOrgElement> newElement = new ArrayList<SysOrgElement>();
                newElement.add(user);
                _group.setFdMembers(newElement);
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
                _group.setFdMembers(sysOrgElements);
            }
            logger.debug("------群组添加------" + dingUserId);
            getSysOrgGroupService().getBaseDao().getHibernateSession().update(_group);
            logger.debug("------群组添加结束------" + dingUserId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 将人员移出群组
    private void removeGroupRole(String dingUserId, SysOrgGroup group) {
        if (dingUserId == null || group == null) {
            return;
        }
        try {
            OmsRelationModel omsRelationModel = getOmsRelationModel(dingUserId);
            String ekpUserId = omsRelationModel.getFdEkpId();
            SysOrgPerson user = (SysOrgPerson) sysOrgPersonService
                    .findByPrimaryKey(ekpUserId);
            removeUserFromGruop(user, (SysOrgGroup) getSysOrgGroupService()
                    .findByPrimaryKey(group.getFdId()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 添加人员到岗位里
    private void updatePostRole(String dingUserId, SysOrgPost post) {
        if (dingUserId == null || post == null) {
            return;
        }
        try {
            OmsRelationModel omsRelationModel = getOmsRelationModel(dingUserId);
            String ekpUserId = omsRelationModel.getFdEkpId();
            SysOrgPerson user = (SysOrgPerson) sysOrgPersonService
                    .findByPrimaryKey(ekpUserId);
            List<SysOrgPerson> persons = post.getFdPersons();
            if (persons != null && !persons.isEmpty()) {
                for (SysOrgPerson per : persons) {
                    if (per.getFdId().equals(ekpUserId)) {
                        logger.debug("该岗位已经包括这个用户了");
                        return;
                    }
                }
            }
            logger.debug(
                    "===添加岗位角色===" + dingUserId + " ->" + post.getFdName());
            persons.add(user);

            SysOrgPost _post = (SysOrgPost) getSysOrgPostService()
                    .findByPrimaryKey(post.getFdId());
            _post.setFdPersons(persons);
            getSysOrgPostService().getBaseDao().getHibernateSession()
                    .update(_post);
            logger.debug("---=添加岗位角色结束-----" + dingUserId + " ->"
                    + post.getFdName());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    private void removePostRole(String dingUserId, SysOrgPost post) {
        if (dingUserId == null || post == null) {
            return;
        }
        try {
            OmsRelationModel omsRelationModel = getOmsRelationModel(dingUserId);
            String ekpUserId = omsRelationModel.getFdEkpId();
            SysOrgPerson user = (SysOrgPerson) sysOrgPersonService
                    .findByPrimaryKey(ekpUserId);
            List<SysOrgPerson> persons = post.getFdPersons();
            if (persons != null && !persons.isEmpty()) {
                for (SysOrgPerson per : persons) {
                    if (per.getFdId().equals(ekpUserId)) {
                        persons.remove(per);
                        break;
                    }
                }
            }
            SysOrgPost _post = (SysOrgPost) getSysOrgPostService()
                    .findByPrimaryKey(post.getFdId());
            _post.setFdPersons(persons);
            getSysOrgPostService().getBaseDao().getHibernateSession()
                    .update(_post);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * 根据手机号生成ID，防止高并发时增加重复人员
     *
     * @return
     */
    private String getPersonId(String mobile) {
        if (StringUtil.isNull(mobile)) {
            return IDGenerator.generateID();
        } else {
            return MD5Util.getMD5String(mobile);
        }
    }

    private void pushAddPersonMessage(JSONObject element) throws Exception {
        String agentid = DingConfig.newInstance().getDingAgentid();
        if (StringUtil.isNotNull(agentid)) {
            // 钉钉用户id
            String userId = element.getString("userid");

            // 组织名称
            String ddDeptId = element.getJSONArray("department").getString(0);
            OmsRelationModel orm = getOmsRelationModel(ddDeptId);
            String deptName = "";
            if (orm != null) {
                SysOrgElement dept = (SysOrgElement) sysOrgElementService.findByPrimaryKey(orm.getFdEkpId());
                String hierarchyId = dept.getFdHierarchyId();
                String topId = hierarchyId.split("x")[1];
                SysOrgElement org = (SysOrgElement) sysOrgElementService.findByPrimaryKey(topId);
                deptName = org.getFdName();
            }

            // 企业名称
            JSONObject corp = dingApiService.departGet(1L);
            String corpName = EmojiFilter.filterEmoji(corp.getString("name"));

            String content = "欢迎加入 " + corpName + " " + deptName + "。点击工作台，可切换到" + corpName + "工作台进行协作。";
            dingApiService.messageSend(content, userId, "", false,
                    Long.parseLong(agentid), null);
        } else {
            logger.debug("没有获取到agentid，无法推送消息，须后台开启【待办待阅推送配置】并填写钉钉消息微应用ID.");
        }
    }

    private void addPost(JSONObject element, SysOrgPerson person, Long deptId,
                         String addOrUpdate)
            throws Exception {
        logger.debug("addOrUpdate:" + addOrUpdate + " " + element);
        if (element.containsKey("department")) {
            String deptstr = element.getString("department");
            JSONArray jas = JSONArray.fromObject(deptstr);
            String ddid = null;
            SysOrgPost post = null;
            String logmsg = "\t";
            boolean flag = false;
            boolean deptManagerEnabled = false;
            boolean moreDeptEnabled = false;
            boolean isCreate = false;
            if (createDeptMap != null && createDeptMap
                    .containsKey(element.getString("name"))) {
                logger.debug("该部门是新增的范畴：" + element.getString("name"));
                isCreate = true;
            }

            String deptLeaderSynWay = DingConfig.newInstance()
                    .getDing2ekpDeptLeaderSynWay();
            logger.debug("deptLeaderSynWay:" + deptLeaderSynWay);
            if (StringUtil.isNotNull(deptLeaderSynWay)) {
                if ((("add".equals(addOrUpdate) || isCreate)
                        && !"noSyn".equals(deptLeaderSynWay))
                        || ("update".equals(addOrUpdate)
                        && "syn".equals(deptLeaderSynWay))) {
                    deptManagerEnabled = true;
                }
            } else {
                if ("true".equals(DingConfig.newInstance()
                        .getDingOmsInDeptManagerEnabled())) {
                    deptManagerEnabled = true;
                }
            }
            // 一人多部门
            String deptSynWay = DingConfig.newInstance()
                    .getDing2ekpDepartmentSynWay();
            if (StringUtil.isNotNull(deptSynWay)) {
                logger.debug("新配置方式处理一人多部门  addOrUpdate:" + addOrUpdate
                        + " deptSynWay:" + deptSynWay);
                if ((("add".equals(addOrUpdate) || isCreate)
                        && !"noSyn".equals(deptSynWay))
                        || ("update".equals(addOrUpdate)
                        && "syn".equals(deptSynWay))) {
                    String deptSyn = DingConfig.newInstance()
                            .getDing2ekpDepartment();
                    logger.debug("deptSyn:" + deptSyn);
                    if ("multDept".equals(deptSyn)) {
                        moreDeptEnabled = true;
                    }
                }

            } else {
                logger.debug("旧方式处理一人多部门");
                if ("true".equals(DingConfig.newInstance()
                        .getDingOmsInMoreDeptEnabled())) {
                    moreDeptEnabled = true;
                }
            }
            logger.debug("moreDeptEnabled:" + moreDeptEnabled);
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
                    logger.debug("停止：deptId：" + deptId + " ddid: " + ddid);
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
                                logger.debug("添加" + person.getFdName() + "的岗位："
                                        + post.getFdName());
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
                            logger.debug("添加" + person.getFdName() + "的岗位："
                                    + post.getFdName());
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
                    logger.debug("element 22:" + element);
                    List<SysOrgPost> postList = person.getFdPosts();
                    if (postList.size() > 0) {
                        for (int i = 0; i < postList.size(); i++) {
                            String postName = postList.get(i).getFdName();
                            logger.debug(
                                    "========检查postName===========" + postName);
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
        logger.debug("element:" + element);
        if (!element.containsKey("isLeaderInDepts")) {
            // 获取部门的用户是 isLeader
            tempJson = dingApiService.userGet(element.getString("userid"),
                    null);
        }
        JSONObject json = JSONObject
                .fromObject(tempJson.getString("isLeaderInDepts"));
        logger.debug("=========json========" + json);
        // 判断是否开启同步部门主管
        boolean isManagerEnabled = false;
        String deptLeaderSynWay = DingConfig.newInstance()
                .getDing2ekpDeptLeaderSynWay();
        logger.debug("deptLeaderSynWay:" + deptLeaderSynWay);
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
        logger.debug("isManagerEnabled:" + isManagerEnabled);
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


        logger.debug("=========set========" + deptNames.toString());
        return deptNames;
    }

    /**
     * 添加EKP用户
     */
    private void addUser(SysOrgPerson person, JSONObject element,
                         String mainDeptId) throws Exception {
        // F4密码默认为空
        // person.setFdNewPassword("1");
        logger.debug("新增EKP用户：" + element);
        createDeptMap.put(element.getString("name"), element); // 记录新增部门
        setUserInfo(person, element, "add", mainDeptId);
        // 添加日志信息
        if (UserOperHelper.allowLogOper("addUser", "*")) {
            UserOperContentHelper.putAdd(person, "fdLoginName", "fdNewPassword",
                    "fdIsAvailable", "FdIsBusiness",
                    "fdMobileNo", "fdName", "fdParent", "fdEmail", "fdNo",
                    "fdWorkPhone");
        }
    }

    /**
     * 更新EKP用户
     */
    private void updateUser(SysOrgPerson person, JSONObject element,
                            String mainDeptId) throws Exception {
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
        logger.debug("addOrUpdate:" + addOrUpdate + " element:" + element);
        person.setFdIsAvailable(true);
        if (person.getFdIsBusiness() == null) {
            person.setFdIsBusiness(true);
        }
        // 业务相关设置 默认相关
        String isBusinessSynWay = DingConfig.newInstance()
                .getDing2ekpFdIsBusinessSynWay();
        logger.debug("isBusinessSynWay:" + isBusinessSynWay);
        if (StringUtil.isNotNull(isBusinessSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(isBusinessSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(isBusinessSynWay))) {
                String isBusiness = DingConfig.newInstance()
                        .getDing2ekpFdIsBusiness();
                logger.debug("isBusiness:" + isBusiness);
                String isBusinessVal = getDingValue(isBusiness, element);
                logger.debug("isBusinessVal:" + isBusinessVal);
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
        String defLangSynWay = DingConfig.newInstance()
                .getDing2ekpDefLangSynWay();
        logger.debug("defLangSynWay:" + defLangSynWay);
        if (StringUtil.isNotNull(defLangSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(defLangSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(defLangSynWay))) {
                String defLang = DingConfig.newInstance().getDing2ekpDefLang();
                logger.debug("defLang:" + defLang);
                String defLangVal = getDingValue(defLang, element);
                logger.debug("defLangVal:" + defLangVal);
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
        String mobileSynWay = DingConfig.newInstance()
                .getDing2ekpMobileSynWay();
        logger.debug("mobileSynWay:" + mobileSynWay);
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
        String loginNameSynWay = DingConfig.newInstance()
                .getDing2ekpLoginNameSynWay();
        logger.debug("loginNameSynWay:" + loginNameSynWay);
        if (StringUtil.isNotNull(loginNameSynWay)) {
            if ("add".equals(addOrUpdate)
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(loginNameSynWay))) {
                String loginNameSyn = DingConfig.newInstance()
                        .getDing2ekpLoginName();
                logger.debug("loginNameSyn:" + loginNameSyn);
                if (StringUtil.isNotNull(loginNameSyn)) {
                    String loginNameVal = getDingValue(loginNameSyn, element);
                    logger.debug("loginNameVal:" + loginNameVal);
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
        String nameSynWay = DingConfig.newInstance().getDing2ekpNameSynWay();
        logger.debug("nameSynWay:" + nameSynWay);
        if (StringUtil.isNotNull(nameSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(nameSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(nameSynWay))) {
                String name = DingConfig.newInstance().getDing2ekpName();
                logger.debug("name:" + name);
                if (StringUtil.isNotNull(name)) {
                    String dingName = getDingValue(name, element);
                    logger.debug("dingName:" + dingName);
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
                        OmsRelationModel model = getOmsRelationModel(ddid);
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
                String deptSynWay = DingConfig.newInstance()
                        .getDing2ekpDepartmentSynWay();
                logger.debug("deptSynWay:" + deptSynWay + " addOrUpdate:"
                        + addOrUpdate);
                boolean flag = false; // 是否处理人员主部门
                if (StringUtil.isNotNull(deptSynWay)) {
                    logger.debug("判断是否需要处理人员主部门：" + mainDeptId);
                    if (("add".equals(addOrUpdate)
                            && !"noSyn".equals(deptSynWay))
                            || ("update".equals(addOrUpdate)
                            && "syn".equals(deptSynWay))) {
                        flag = true;
                    }
                } else {
                    logger.debug("旧方式处理人员主部门");
                    flag = true;
                }
                if (flag) {
                    logger.debug("开始处理主部门");
                    SysOrgElement dept = getSysOrgDept(mainDeptId, false);
                    if (dept != null) {
                        logger.debug(
                                EmojiFilter
                                        .filterEmoji(element.getString("name"))
                                        + " 主部门设置:上级部门：" + dept.getFdName());
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
                logger.debug(EmojiFilter.filterEmoji(element.getString("name"))
                        + " 无主部门");
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
                        logger.debug(EmojiFilter
                                .filterEmoji(element.getString("name"))
                                + " 上级部门：" + dept.getFdName());
                    }
                }
            }
        }

        // 邮箱设置
        String emailSynWay = DingConfig.newInstance().getDing2ekpEmailSynWay();
        logger.debug("emailSynWay:" + emailSynWay);
        if (StringUtil.isNotNull(emailSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(emailSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(emailSynWay))) {
                String emailSyn = DingConfig.newInstance().getDing2ekpEmail();
                logger.debug("emailSyn:" + emailSyn);
                if (StringUtil.isNotNull(emailSyn)) {
                    String emailVal = getDingValue(emailSyn, element);
                    logger.debug("emailVal:" + emailVal);
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
        String fdNoSynWay = DingConfig.newInstance().getDing2ekpFdNoSynWay();
        logger.debug("fdNoSynWay:" + fdNoSynWay);
        if (StringUtil.isNotNull(fdNoSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(fdNoSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(fdNoSynWay))) {
                String fdNoSyn = DingConfig.newInstance().getDing2ekpFdNo();
                logger.debug("fdNoSyn:" + fdNoSyn);
                if (StringUtil.isNotNull(fdNoSyn)) {
                    String fdNoVal = getDingValue(fdNoSyn, element);
                    logger.debug("fdNoVal:" + fdNoVal);
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
        String telSynWay = DingConfig.newInstance().getDing2ekpTelSynWay();
        logger.debug("telSynWay:" + telSynWay);
        if (StringUtil.isNotNull(telSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(telSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(telSynWay))) {
                String telSyn = DingConfig.newInstance().getDing2ekpTel();
                logger.debug("telSyn:" + telSyn);
                if (StringUtil.isNotNull(telSyn)) {
                    String telVal = getDingValue(telSyn, element);
                    logger.debug("telVal:" + telVal);
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
                importInfoPre + "_person_" + element.getString("userid"));

        // 昵称
        String fdNickNameSynWay = DingConfig.newInstance()
                .getDing2ekpFdNickNameSynWay();
        logger.debug("fdNickNameSynWay:" + fdNickNameSynWay);
        if (StringUtil.isNotNull(fdNickNameSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(fdNickNameSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(fdNickNameSynWay))) {
                String fdNickNameSyn = DingConfig.newInstance()
                        .getDing2ekpFdNickName();
                logger.debug("fdNickNameSyn:" + fdNickNameSyn);
                if (StringUtil.isNotNull(fdNickNameSyn)) {
                    String fdNickNameVal = getDingValue(fdNickNameSyn, element);
                    logger.debug("fdNickNameVal:" + fdNickNameVal);
                    if (StringUtil.isNotNull(fdNickNameVal)) {
                        person.setFdNickName(fdNickNameVal);
                    } else {
                        logger.error("用户的昵称为空：" + element);
                    }
                }
            }
        }

        // 关键字
        String keywordSynWay = DingConfig.newInstance()
                .getDing2ekpKeywordSynWay();
        logger.debug("keywordSynWay:" + keywordSynWay);
        if (StringUtil.isNotNull(keywordSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(keywordSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(keywordSynWay))) {
                String keywordSyn = DingConfig.newInstance()
                        .getDing2ekpKeyword();
                logger.debug("keywordSyn:" + keywordSyn);
                if (StringUtil.isNotNull(keywordSyn)) {
                    String keywordVal = getDingValue(keywordSyn, element);
                    logger.debug("keywordVal:" + keywordVal);
                    if (StringUtil.isNotNull(keywordVal)) {
                        person.setFdKeyword(keywordVal);
                    } else {
                        logger.error("用户的关键字为空：" + element);
                    }
                }
            }
        }

        // 性别
        String sexSynWay = DingConfig.newInstance().getDing2ekpSexSynWay();
        logger.debug("sexSynWay:" + sexSynWay);
        if (StringUtil.isNotNull(sexSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(sexSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(sexSynWay))) {
                String sexSyn = DingConfig.newInstance().getDing2ekpSex();
                logger.debug("sexSyn:" + sexSyn);
                if (StringUtil.isNotNull(sexSyn)) {
                    String sexVal = getDingValue(sexSyn, element);
                    logger.debug("sexVal:" + sexVal);
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
        String shortNoSynWay = DingConfig.newInstance()
                .getDing2ekpFdShortNoSynWay();
        logger.debug("shortNoSynWay:" + shortNoSynWay);
        if (StringUtil.isNotNull(shortNoSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(shortNoSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(shortNoSynWay))) {
                String shortNoSyn = DingConfig.newInstance()
                        .getDing2ekpFdShortNo();
                logger.debug("shortNoSyn:" + shortNoSyn);
                if (StringUtil.isNotNull(shortNoSyn)) {
                    String shortNoVal = getDingValue(shortNoSyn, element);
                    logger.debug("shortNoVal:" + shortNoVal);
                    if (StringUtil.isNotNull(shortNoVal)) {
                        person.setFdShortNo(shortNoVal);
                    } else {
                        logger.error("用户的短号为空：" + element);
                    }
                }
            }
        }

        // 备注
        String fdMemoSynWay = DingConfig.newInstance()
                .getDing2ekpFdMemoSynWay();
        logger.debug("fdMemoSynWay:" + fdMemoSynWay);
        if (StringUtil.isNotNull(fdMemoSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(fdMemoSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(fdMemoSynWay))) {
                String fdMemoSyn = DingConfig.newInstance().getDing2ekpFdMemo();
                logger.debug("fdMemoSyn:" + fdMemoSyn);
                if (StringUtil.isNotNull(fdMemoSyn)) {
                    String fdMemoVal = getDingValue(fdMemoSyn, element);
                    logger.debug("fdMemoVal:" + fdMemoVal);
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
                setInitPassword();
            }
            if (StringUtil.isNotNull(initPassword)) {
                String password = passwordEncoder.encodePassword(initPassword);
                logger.debug("password:" + password);
                person.setFdPassword(password);
                person.setFdInitPassword(
                        PasswordUtil.desEncrypt(SecureUtil
                                .BASE64Decoder(initPassword)));
            }
        }

        // 排序号
        String orderSynWay = DingConfig.newInstance()
                .getDing2ekpOrderSynWay();
        logger.debug("orderSynWay:" + orderSynWay);
        if (StringUtil.isNotNull(orderSynWay)) {
            if (("add".equals(addOrUpdate)
                    && !"noSyn".equals(orderSynWay))
                    || ("update".equals(addOrUpdate)
                    && "syn".equals(orderSynWay))) {
                String orderSyn = DingConfig.newInstance().getDing2ekpOrder();
                logger.debug("orderSyn:" + orderSyn);
                if (StringUtil.isNotNull(orderSyn)) {
                    String orderVal = getDingValue(orderSyn, element);
                    logger.debug("orderVal:" + orderVal);
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
            logger.debug("key:" + key);
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
                    //logger.debug("钉钉入职时间字段："+hiredDate+" 类型："+ hiredDate.getClass().getName());
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
                    // JSONObject ext = element.getJSONObject("extattr");
                    // {拓展语言=en-US, 测试扩展字段=的方式发到}
                    logger.debug("extattr:" + element.getString("extattr"));
                    String extattr = element.getString("extattr");
                    Map<String, String> map = new HashMap<String, String>();
                    if (StringUtil.isNotNull(extattr)
                            && !"{}".equals(extattr)) {
                        extattr = extattr.trim().substring(1,
                                extattr.trim().length() - 1);
                        logger.debug("extattr 22:" + extattr);
                        if (StringUtil.isNotNull(extattr)) {
                            try {
                                JSONObject extJson = element
                                        .getJSONObject("extattr"); // 回调获取的extattr格式不一样（用户详情和部门用户列表接口）
                                String val_temp = extJson.getString(key);
                                logger.debug("val_temp:" + val_temp);
                                return val_temp;
                            } catch (Exception e) {
                                logger.debug("", e);
                            }

                            if (extattr.indexOf(",") != -1) {
                                String[] extArr = extattr.split(",");
                                for (int i = 0; i < extArr.length; i++) {
                                    if (extArr[i].contains("=")) {
                                        map.put(extArr[i].split("=")[0].trim(),
                                                extArr[i].split("=")[1].trim());
                                    }
                                }
                            } else {
                                if (extattr.contains("=")) {
                                    map.put(extattr.split("=")[0].trim(),
                                            extattr.split("=")[1].trim());
                                }
                            }

                        }

                    }
                    if (map != null && map.containsKey(key)) {
                        return map.get(key);
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
        logger.info("***updateRelation***" + fdAppId+ "  ekpId:" + ekpId + "  type:" + type);
        if (!relationMap.containsKey(fdAppId + "|" + type)) {
            OmsRelationModel model = (OmsRelationModel) omsRelationService
                    .findFirstOne("fdEkpId='" + ekpId + "' and fdAppKey='" + getAppKey() + "'", null);
            if (model != null) {
                model.setFdAppPkId(fdAppId);
                if ("8".equals(type)) {
                    model.setFdAvatar(element.getString("avatar"));
                    model.setFdUnionId(getUnionId(element));
                    //账号类型
                    setAccountType(element,model);
                }
                omsRelationService.update(model);
            } else {
                log("\tdingId=" + fdAppId + "的数据关联已建立");
                model = new OmsRelationModel();
                model.setFdEkpId(ekpId);
                model.setFdAppPkId(fdAppId);
                model.setFdAppKey(getAppKey());
                model.setFdType(type);
                if ("8".equals(type)) {
                    model.setFdAvatar(element.getString("avatar"));
                    model.setFdUnionId(getUnionId(element));
                    //账号类型
                    setAccountType(element,model);
                }
                omsRelationService.add(model);
            }
            relationMap.put(model.getFdAppPkId() + "|" + model.getFdType(),
                    model.getFdEkpId());
        } else if (relationMap.containsKey(fdAppId + "|" + type)
                && !relationMap.get(fdAppId + "|" + type).equals(ekpId)) {
            List<OmsRelationModel> list = omsRelationService
                    .findList("fdAppPkId='" + fdAppId + "' and fdAppKey='"
                            + getAppKey() + "'", null);
            if (list != null && !list.isEmpty()) {
                for (OmsRelationModel model : list) {
                    model.setFdEkpId(ekpId);
                    if ("8".equals(type)) {
                        model.setFdAvatar(element.getString("avatar"));
                        model.setFdUnionId(getUnionId(element));
                        //账号类型
                        setAccountType(element,model);
                    }
                    omsRelationService.update(model);
                }
            }
        } else if ("8".equals(type)) {
            // 人员已经存在，添加头像信息
            OmsRelationModel per = (OmsRelationModel) omsRelationService
                    .findFirstOne("fdEkpId='" + ekpId + "' and fdAppKey='"
                            + getAppKey() + "'", null);
            if (per != null) {
                per.setFdAvatar(element.getString("avatar"));
                per.setFdUnionId(getUnionId(element));
                omsRelationService.update(per);
            }

        }
    }

    /**
     * 设置钉钉账号类型
     * @param element
     * @param model
     */
    private void setAccountType(JSONObject element, OmsRelationModel model) {
        if(element.containsKey("exclusiveAccount")||element.containsKey("exclusive_account")){
            String dingAccountType="common";
            if(element.containsKey("exclusiveAccountType")){
                dingAccountType = element.getString("exclusiveAccountType");
            }else if(element.containsKey("exclusive_account_type")){
                dingAccountType = element.getString("exclusive_account_type");
            }
            model.setFdAccountType(dingAccountType);
        }
    }

    private String getAppKey() {
        return StringUtil.isNull(DING_OMS_APP_KEY) ? "default"
                : DING_OMS_APP_KEY;
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
                logger.debug("回调的部门在同步范围内【钉钉部门Id="
                        + element.getString("id") + ",部门名称="
                        + element.getString("name") + "】");
            }

            long time = System.currentTimeMillis();
            String type = "更新";
            String addOrUpdate = "update";
            if (flag) {
                type = "新增";
                addOrUpdate = "add";
            }
            logger.debug("钉钉回调执行EKP的" + type + "部门操作:钉钉部门Id="
                    + element.getString("id") + ",部门名称="
                    + element.getString("name"));
            updateDept(element, flag, true);

            // 回调部门主管配置，兼容旧数据
            boolean deptManagerEnabled = false;
            String deptLeaderSynWay = DingConfig.newInstance()
                    .getDing2ekpDeptLeaderSynWay();
            logger.debug("deptLeaderSynWay:" + deptLeaderSynWay);
            if (StringUtil.isNotNull(deptLeaderSynWay)) {
                logger.debug("处理部门主管关系：" + element);
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
                                person = getSysOrgPerson(did, "", "");
                                if (person != null) {
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
            logger.debug("cost time:"
                    + ((System.currentTimeMillis() - time) / 1000) + " s");
        }
    }

    @Override
    public void saveOrUpdateCallbackUser(JSONObject element, boolean flag)
            throws Exception {
        // 回调的初始密码设置
        setInitPassword();

        relationMap = new HashMap<String, String>();
        // 关系映射表初始化
        List list = omsRelationService.findList("fdAppKey='" + getAppKey() + "'", null);
        for (int i = 0; i < list.size(); i++) {
            OmsRelationModel model = (OmsRelationModel) list.get(i);
            relationMap.put(model.getFdEkpId(), model.getFdAppPkId());
        }

        DingConfig dc = DingConfig.newInstance();
        if ((StringUtil.isNotNull(dc.getSyncSelection())
                && "1".equals(dc.getSyncSelection()))
                || ("true".equals(dc.getDingOmsOutEnabled())
                && "true".equals(dc.getDingMobileEnabled()))) {
            logger.debug("组织架构从ekp到钉钉同步,用户信息修改!");
            logger.debug("用户信息：" + element);
            // 判断用户是否禁用（如果禁用，则删除钉钉用户）以及对照表的关系（用户状态正常，则建立映射关系）
            OmsRelationModel model = getOmsRelationModel(element.getString("userid"));
            if (model == null) {
                String userId = element.getString("userid");
                logger.warn("不存在该用户的映射关系，将根据配置信息尝试建立对照关系   ："
                        + userId);
                String o2d_userId = DingConfig.newInstance()
                        .getOrg2dingUserid();
                if (StringUtil.isNotNull(o2d_userId)) {
                    logger.debug("o2d_userId:" + o2d_userId);
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
                            logger.debug("建立映射关系  ：" + sysOrgPerson.getFdId()
                                    + "   dingId:" + userId);
                            model = new OmsRelationModel();
                            model.setFdEkpId(sysOrgPerson.getFdId());
                            model.setFdAppPkId(userId);
                            model.setFdAppKey(getAppKey());
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
            logger.debug("钉钉回调执行EKP的" + type + "人员操作:钉钉用户Id="
                    + element.getString("userid") + ",用户名称="
                    + element.getString("name"));
            String mainDeptId = getDingMainDeptId(
                    dingApiService.getAccessToken(),
                    element.getString("userid"));
            updateUser(element, flag, null, mainDeptId);
            logger.debug("钉钉回调" + type + "人员共耗时:"
                    + ((System.currentTimeMillis() - time) / 1000) + " s");
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
                logger.debug("钉钉回调删除部门的Id为null，直接退出无法执行删除操作");
                return;
            }
            SysOrgElement dept = getSysOrgDept(deptId.toString(), true);
            if (dept != null) {
                if (UserOperHelper.allowLogOper("deleteDept", "*")) {
                    UserOperContentHelper.putUpdate(dept)
                            .putSimple("fdIsAvailable", true, false);
                }
                // 如果当前部门下有存在多余的子部门/人员和岗位则移到根目录
                String leaderPost = importInfoPre + "_post_2_" + deptId;
                String personPost = importInfoPre + "_post_8_" + deptId;
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
                logger.debug(
                        "钉钉删除部门回调EKP时在中间表和通过关键标识找不到对应的部门，无法置为无效，id=" + deptId);
            }
            logger.debug("钉钉回调删除部门共耗时:"
                    + ((System.currentTimeMillis() - time) / 1000) + " s");
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
        logger.debug("isEkp2Ding:{}  isDing2Ekp:{}" ,isEkp2Ding,isDing2Ekp);
        if (isDing2Ekp || isEkp2Ding) {
            long time = System.currentTimeMillis();
            if (StringUtil.isNull(userid)) {
                logger.debug("钉钉回调删除人员的Id为null，直接退出无法执行删除操作");
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
                hqlInfo.setParameter("info", importInfoPre + "_person_" + userid);
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
                                logger.debug(
                                        "ekp同步到钉钉，仅支持生态组织实时同步!id=" + userid);
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
                        logger.debug(
                                "钉钉删除人员回调EKP时找不到对应的人员，无法置为无效，id=" + userid);
                    }
                }
            }else{
                logger.warn("------------没有找到需要删除的人员 userid:{}-------------",userid);
            }
            logger.debug("钉钉回调删除人员共耗时:"
                    + ((System.currentTimeMillis() - time) / 1000) + " s");
        }
    }

    private void log(String msg) {
        logger.debug("【钉钉接入组织架构到EKP】" + msg);
        if (this.jobContext != null) {
            jobContext.logMessage(msg);
        }

    }

    private OmsRelationModel getOmsRelationModel(String dingId)
            throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fdAppKey=:fdAppKey and fdAppPkId=:fdAppPkId");
        hqlInfo.setParameter("fdAppKey", getAppKey());
        hqlInfo.setParameter("fdAppPkId", dingId);
        return (OmsRelationModel) omsRelationService.findFirstOne(hqlInfo);
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
                logger.debug(
                        "根据钉钉userid=" + userid + "无法获取映射的EKP的用户id，无法更新手机号");
            }
        } else {
            logger.debug("手机号码无法更新，因为客户是使用的ISV模式，这种模式无法获取钉钉的手机号");
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
            OmsRelationModel model = getOmsRelationModel(dingDeptId);
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
            hqlInfo.setParameter("info", importInfoPre + "_dept_" + dingDeptId);
            return (SysOrgElement) sysOrgElementService.findFirstOne(hqlInfo);
        }
        return ele;
    }

    // 获取人员数据
    private SysOrgPerson getSysOrgPerson(String userid, String name,
                                         String mobileNo) throws Exception {
        SysOrgPerson person = null;
        String msg = "\t";
        if (person == null && relationMap.containsKey(userid + "|8")) {
            String ekpid = relationMap.get(userid + "|8");
            person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(ekpid,
                    null, true);
            if (person != null) {
                msg += "根据中间表映射信息${userid}完成用户${name}匹配，直接更新该用户信息"
                        .replace("${userid}", userid).replace("${name}",
                                name);
            }
            return person;
        }
        if (person == null && StringUtil.isNotNull(userid)
                && loginNameMap.containsKey(userid)) {
            person = loginNameMap.get(userid);
            if (person != null) {
                msg += "根据钉钉userId信息${no}完成用户${name}匹配，直接更新该用户信息"
                        .replace("${no}", userid).replace("${name}", name);
            }
        }
        if (person == null && StringUtil.isNotNull(mobileNo)
                && mobileMap.containsKey(mobileNo)) {
            person = mobileMap.get(mobileNo);
            if (person != null) {
                msg += "根据钉钉电话号码信息${mobileNo}完成用户${name}匹配，直接更新该用户信息"
                        .replace("${mobileNo}", mobileNo)
                        .replace("${name}", name);
            }
        }
        if (person == null && StringUtil.isNotNull(userid)
                && personMap.containsKey(userid)) {
            person = personMap.get(userid);
            if (person != null) {
                msg += "根据组织架构人员信息${importInfo}完成用户${name}匹配，直接更新该用户信息"
                        .replace("${importInfo}",
                                importInfoPre + "_person_" + userid)
                        .replace("${name}", name);
            }
        }
        if (StringUtil.isNotNull(msg)) {
            log(msg);
        }
        // 获取映射表数据
        if (person == null) {
            OmsRelationModel model = getOmsRelationModel(userid);
            if (model != null) {
                person = (SysOrgPerson) sysOrgPersonService
                        .findByPrimaryKey(model.getFdEkpId(), null, true);
                if (person != null) {
                    relationMap.put(userid + "|" + model.getFdType(),
                            person.getFdId());
                }
            }
        }
        // 获取数据库数据
        if (person == null) {
            // 根据关系ID查询用户
            SysOrgElement sysOrgElement = sysOrgCoreService
                    .format(sysOrgCoreService.findByImportInfo(importInfoPre + "_person_" + userid));
            if (sysOrgElement != null) {
                person = (SysOrgPerson) sysOrgElement;
            }
            if (person == null && StringUtil.isNotNull(mobileNo)) {
                // 根据手机号查询用户（查询有效用户）
                person = getPersonByMobile(mobileNo, true);
                if (person == null) {
                    // 根据手机号查询用户（查询无效用户）
                    person = getPersonByMobile(mobileNo, false);
                }
            }
        }
        return person;
    }

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @param isAvailable
     * @return
     * @throws Exception
     */
    private SysOrgPerson getPersonByMobile(String mobile, boolean isAvailable) throws Exception {
        if (StringUtil.isNull(mobile)) {
            logger.warn("手机号码为空，不进行手机号码匹配");
            return null;
        }
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("sysOrgPerson.fdMobileNo = :mobile and sysOrgPerson.fdIsAvailable = :isAvailable");
        hqlInfo.setParameter("mobile", mobile);
        hqlInfo.setParameter("isAvailable", isAvailable);
        return (SysOrgPerson) sysOrgPersonService.findFirstOne(hqlInfo);
    }

    // 钉钉端不存在的映射关系处理
    private void cleanData(String dingId) throws Exception {
        SysOrgElement ele = null;
        TransactionStatus status = null;
        logger.debug("------钉钉端不存在的映射关系处理 dingId--------:" + dingId);
        try {
            // 判断该用户在钉钉中是否存在
            JSONObject o = dingApiService.userGet(dingId, null);
            if (o.getInt("errcode") == 0) {
                return;
            }
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
                    String leaderPost = importInfoPre + "_post_2_"
                            + dingId.split("[|]")[0];
                    String personPost = importInfoPre + "_post_8_"
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
            TransactionUtils.commit(status);
        } catch (Exception e) {
            logger.error("清理钉钉端不存在的映射关系失败", e);
            if (status != null) {
                try {
                    TransactionUtils.rollback(status);
                } catch (Exception ex) {
                    logger.error("---事务回滚出错---", ex);
                }
            }
        }
    }

    // =================================================F4新增功能部分========================================================
    // 部门主管实例：信息部_主管=com.landray.kmss.third.ding.oms.SynchroOrgDing2EkpImp_post_1_钉钉部门Id
    // 多部门实例：信息部_成员=com.landray.kmss.third.ding.oms.SynchroOrgDing2EkpImp_post_8_钉钉部门Id
    // 部门：com.landray.kmss.third.ding.oms.SynchroOrgDing2EkpImp_dept_钉钉部门Id
    // 人员：com.landray.kmss.third.ding.oms.SynchroOrgDing2EkpImp_person_钉钉人员Id
    private String importInfoPre = "com.landray.kmss.third.ding.oms.SynchroOrgDing2EkpImp";

    private Map<String, SysOrgPost> postMap = new HashMap<String, SysOrgPost>();

    // 角色同步相关的集合
    private Map<String, String> roleMap = new HashMap<String, String>(); // value:角色组名称key:post/group/Staffing
    private Map<String, SysOrgPost> role2postMap = new HashMap<String, SysOrgPost>();
    private Map<String, SysOrgGroup> role2groupMap = new HashMap<String, SysOrgGroup>();
    private Map<String, SysOrganizationStaffingLevel> role2staffingMap = new HashMap<String, SysOrganizationStaffingLevel>();

    private Map<String, SysOrgPerson> personMap = new HashMap<String, SysOrgPerson>();

    private ISysOrgPostService sysOrgPostService = null;

    public ISysOrgPostService getSysOrgPostService() {
        if (sysOrgPostService == null) {
            sysOrgPostService = (ISysOrgPostService) SpringBeanUtil
                    .getBean("sysOrgPostService");
        }
        return sysOrgPostService;
    }

    private ISysOrgGroupService sysOrgGroupService = null;

    public ISysOrgGroupService getSysOrgGroupService() {
        if (sysOrgGroupService == null) {
            sysOrgGroupService = (ISysOrgGroupService) SpringBeanUtil
                    .getBean("sysOrgGroupService");
        }
        return sysOrgGroupService;
    }

    private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService = null;

    public ISysOrganizationStaffingLevelService
    getSysOrganizationStaffingLevelService() {
        if (sysOrganizationStaffingLevelService == null) {
            sysOrganizationStaffingLevelService = (ISysOrganizationStaffingLevelService) SpringBeanUtil
                    .getBean("sysOrganizationStaffingLevelService");
        }
        return sysOrganizationStaffingLevelService;
    }

    private String getDingMainDeptId(String token, String userid)
            throws Exception {
        if (!hrmPermission) {
            return null;
        }
        if (StringUtil.isNull(token) || StringUtil.isNull(userid)) {
            logger.debug("钉钉的token(" + token + ")或者用户id(" + userid
                    + ")为空，无法获取当前用户的主部门");
        }
        String dingDeptId = null;
        String dingUrl = DingConstant.DING_PREFIX
                + "/topapi/smartwork/hrm/employee/list"
                + DingUtil.getDingAppKeyByEKPUserId("?", null);
        logger.debug("钉钉接口：" + dingUrl);
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
            logger.debug(
                    "获取钉钉人员id=" + userid + "时无法获取智能人事的主部门：" + rsp.getBody());
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
        logger.debug("getPost方法：deptId->" + deptId + " type:" + type
                + " callback:" + callback);
        SysOrgPost post = null;
        String iminfo = importInfoPre + "_post";
        String name = "";
        if ("leader".equals(type)) {
            name = "_主管";
            iminfo += "_2_" + deptId;
        } else {
            name = "_成员";
            iminfo += "_8_" + deptId;
        }
        if (postMap.containsKey(iminfo) && !callback) {
            post = postMap.get(iminfo);
        } else {
            Object obj = sysOrgCoreService.findByImportInfo(iminfo);
            if (obj != null) {
                post = (SysOrgPost) sysOrgCoreService
                        .format((SysOrgElement) obj);
                if (!post.getFdIsAvailable()) {
                    post.setFdIsAvailable(true);
                    getSysOrgPostService().update(post);
                }
                postMap.put(iminfo, post);
            }
        }
        if (post == null && deptId != null) {
            JSONObject dingDept = dingApiService.departGet(deptId);
            name = dingDept.getString("name") + name;
            String where = "fdName='" + name + "'";
            OmsRelationModel model = getOmsRelationModel(
                    dingDept.getString("id"));
            if (model != null) {
                where += " and hbmParent.fdId='" + model.getFdEkpId() + "'";
                return (SysOrgPost) getSysOrgPostService().findFirstOne(where,
                        "fdIsAvailable desc");
            }
        }
        return post;
    }

    private SysOrgPost addPost(String name, String did, String type,
                               SysOrgElement parent) throws Exception {
        logger.debug(
                "addPost: name->" + name + " did:" + did + " type:" + type);
        boolean isCreate = false;
        SysOrgPost post = null;
        boolean flag = false;
        if (StringUtil.isNull(name) || StringUtil.isNull(did)) {
            logger.debug(
                    "部门名称(" + name + ")为空或部门钉钉Id(" + did + ")为空则直接跳过不创建岗位信息");
            return null;
        }
        String iminfo = importInfoPre + "_post";
        if ("leader".equals(type)) {
            name += "_主管";
            iminfo += "_2_" + did;
        } else {
            name += "_成员";
            iminfo += "_8_" + did;
        }
        logger.debug("name:" + name);
//        if (postMap.containsKey(iminfo)) {
//            post = postMap.get(iminfo);
//        } else {
            Object obj = sysOrgCoreService.findByImportInfo(iminfo);
            if (obj != null) {
                post = (SysOrgPost) sysOrgCoreService
                        .format((SysOrgElement) obj);
            }
            if (post == null) {
                String where = "fdName='" + name + "'";
                OmsRelationModel model = getOmsRelationModel(did);
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
        if (!post.getFdIsAvailable()) {
            flag = true;
        }
        if (!name.equals(post.getFdName())) {
            post.setFdName(name);
            flag = true;
        }
        // 设置岗位主管为当前部门的部门领导
        if ("leader".equals(type)) {
            if (parent.getHbmThisLeader() == null
                    || (parent.getHbmThisLeader() != null
                    && !parent.getHbmThisLeader().getFdId()
                    .equals(post.getFdId()))) {
                flag = true;
            }
            parent.setHbmThisLeader(post);
        }
        if (!iminfo.equals(post.getFdImportInfo())) {
            post.setFdImportInfo(iminfo);
            flag = true;
        }
        if (post.getFdParent() == null && parent != null) {
            flag = true;
        }
        if (flag) {
            post.setFdIsAvailable(true);
            post.setFdParent(parent);
        }
        if (isCreate == true) {
            getSysOrgPostService().add(post);
        } else {
            getSysOrgPostService().update(post);
        }
        postMap.put(iminfo, post);
        return post;
    }

    protected final Object globalLock = new Object();

    // isv模式的回调，有可能会覆盖人员新增的场景，需要在修改的时候判断人员，如果不存在则新建
    private synchronized SysOrgPerson handleIsvPerson(JSONObject element,
                                                      String mainDeptId) {
        SysOrgPerson person = null;
        try {
            String userid = element.getString("userid");
            String info = importInfoPre + "_person_" + userid;
            person = (SysOrgPerson) sysOrgPersonService.findFirstOne(
                    "fdOrgType=8 and fdImportInfo='" + info + "'", null);
            if (person == null) {
                person = new SysOrgPerson();
                if (StringUtil.isNotNull(element.getString("mobile"))) {
                    person.setFdLoginName(element.getString("mobile"));
                    // 强制使用手机号MD5为ID，确保人员不重复
                    person.setFdId(getPersonId(element.getString("mobile")));
                }
                if (StringUtil.isNull(person.getFdLoginName())) {
                    person.setFdLoginName(userid);
                }
                person.setFdIsAvailable(true);
                person.setFdIsBusiness(true);
                setUserInfo(person, element, "add", mainDeptId);
                // 集群环境处理
                sysOrgPersonService.add(person);
                logger.debug("处理钉钉回调事件修改标识覆盖新建标识导致无法新建，EKP的处理是当查不到人员时则新建");
            } else {
                logger.debug("当前用户已经存在不再新建，用户名称：" + person.getFdName()
                        + ",用户Id:" + person.getFdId() + ",钉钉Id:" + userid);
            }

            OmsRelationModel model = getOmsRelationModel(userid);
            if (model == null && person != null) {
                model = new OmsRelationModel();
                model.setFdEkpId(person.getFdId());
                model.setFdAppPkId(userid);
                model.setFdAppKey(getAppKey());
                model.setFdType("8");
                model.setFdAvatar(element.getString("avatar"));
                model.setFdUnionId(getUnionId(element));
                //账号类型
                setAccountType(element,model);
                // 集群环境处理
                if (getOmsRelationModel(userid) == null) {
                    omsRelationService.add(model);
                    relationMap.put(
                            model.getFdAppPkId() + "|" + model.getFdType(),
                            model.getFdEkpId());
                }
            }
            if (model != null) {
                // 头像信息为空
                logger.debug("-------------更新头像信息-------------------");
                model.setFdAvatar(element.getString("avatar"));
                omsRelationService.update(model);
            }
        } catch (Exception e) {
            logger.error("---保存EKP人员失败---", e);
            person = null;
        }
        return person;
    }

    private void updateHandlerRepeatData() throws Exception {
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            String sql = "select fd_import_info from sys_org_element GROUP BY fd_import_info having count(fd_id)>1";
            List<Object[]> repeats = sysOrgElementService.getBaseDao().getHibernateSession().createNativeQuery(sql).list();
            if (repeats != null && repeats.size() > 0) {
                List<SysOrgElement> eles = null;
                List<OmsRelationModel> models = null;
                for (Object obj : repeats) {
                    if (obj == null || StringUtil.isNull(obj.toString())) {
                        continue;
                    }
                    eles = sysOrgElementService.findList(
                            "fdImportInfo='" + obj.toString() + "'", null);
                    for (SysOrgElement ele : eles) {
                        if (!ele.getFdIsAvailable()) {
                            ele.setFdImportInfo(null);
                            sysOrgElementService.update(ele);
                            models = omsRelationService.findList(
                                    "fdEkpId='" + ele.getFdId() + "'", null);
                            for (OmsRelationModel model : models) {
                                omsRelationService.delete(model);
                            }
                        }
                    }
                }
            }
            TransactionUtils.commit(status);
        } catch (Exception e) {
            logger.error("处理人员/部门失败:", e);
            if (status != null) {
                try {
                    TransactionUtils.rollback(status);
                } catch (Exception ex) {
                    logger.error("---事务回滚出错---", ex);
                }
            }
        }
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
                logger.debug("--------组织架构从钉钉到ekp---------");

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

    /**
     * 初始化映射表关系的类型
     */
    private void updateRelationType() {
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            List<OmsRelationModel> list = omsRelationService
                    .findList(
                            "fdAppKey='" + getAppKey() + "' and fdType is null",
                            null);
            for (OmsRelationModel model : list) {
                SysOrgElement ele = (SysOrgElement) sysOrgElementService
                        .findByPrimaryKey(model.getFdEkpId());
                if (ele != null) {
                    if (8 == ele.getFdOrgType()) {
                        model.setFdType("8");
                    } else if (2 == ele.getFdOrgType()
                            || 1 == ele.getFdOrgType()) {
                        model.setFdType("2");
                    }
                    omsRelationService.update(model);
                }
            }
            TransactionUtils.commit(status);
        } catch (Exception e) {
            logger.error("更新映射类型失败:", e);
            if (status != null) {
                try {
                    TransactionUtils.rollback(status);
                } catch (Exception ex) {
                    logger.error("---事务回滚出错---", ex);
                }
            }
        }
    }
    // =================================================F4新增功能部分========================================================
    @Override
    public void saveOrUpdateRolesCallback(JSONObject roleCallbackData)
            throws Exception {
        saveOrUpdateRolesGroupCallback(roleCallbackData);
        // 角色线回调
        saveOrUpdateRolesRolelineCallback(roleCallbackData);
    }

    public void saveOrUpdateRolesGroupCallback(JSONObject roleCallbackData)
            throws Exception {
        logger.debug("roleCallbackData:" + roleCallbackData);
        if (roleCallbackData == null) {
            return;
        }
        String eventType = roleCallbackData.getString("EventType");
        Map<String, String> ekpRoleMap = getEkpRoleMap(); // "post":"123456"
        if (ekpRoleMap == null || ekpRoleMap.isEmpty()) {
            logger.warn("----ekp中未开启 角色->岗位、角色->职务、角色->群组 的同步----");
            return;
        }
        if ("label_conf_add".equals(eventType) || "label_conf_modify".equals(eventType)) {
            Map<String, JSONObject> dingRoleMap = new HashMap<String, JSONObject>(); // "post":{"groupId":123...}
            String groupList = DingUtils.dingApiService.getRoleList();
            logger.debug("groupList:" + groupList);
            if (StringUtil.isNotNull(groupList)) {
                JSONObject groupObject = JSONObject.fromObject(groupList);
                if (groupObject.getInt("errcode") == 0) {
                    JSONArray list = groupObject.getJSONObject("result")
                            .getJSONArray("list");
                    // 过滤掉非ekp配置的角色组
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject group = list.getJSONObject(i);
                        String groupId = group.getString("groupId");
                        if (ekpRoleMap.containsValue(groupId)) {
                            logger.debug("group在同步范围！");
                            for (String key : ekpRoleMap.keySet()) {
                                if (groupId.equals(ekpRoleMap.get(key))) {
                                    dingRoleMap.put(key, group);
                                }
                            }
                        }
                    }

                } else {
                    logger.warn("获取钉钉角色列表失败：" + groupObject);
                    return;
                }
                // 判断在同步范围内
                JSONArray LabelIdList = roleCallbackData
                        .getJSONArray("LabelIdList");
                for (int k = 0; k < LabelIdList.size(); k++) { // 一般只有一个分组回调
                    int data_groupId = LabelIdList.getInt(k);
                    logger.debug("data_groupId:" + data_groupId);
                    for (String roleType : dingRoleMap.keySet()) {
                        boolean isFlag = false;
                        String type = null;
                        JSONObject role = null;
                        JSONArray roles = dingRoleMap.get(roleType)
                                .getJSONArray("roles");
                        for (int j = 0; j < roles.size(); j++) {
                            if (data_groupId == roles.getJSONObject(j)
                                    .getInt("id")) {
                                isFlag = true;
                                role = roles.getJSONObject(j);
                                type = roleType;
                                break;
                            }
                        }
                        if (isFlag) {
                            logger.debug("role:" + role + "  type:" + type);
                            if ("post".equals(type)) {
                                updateOrAddRole2org(4, role, dingRoleMap);
                            }
                            if ("group".equals(type)) {
                                updateOrAddRole2org(16, role, dingRoleMap);
                            }
                            if ("staffing".equals(type)) {
                                HQLInfo hqlInfo = new HQLInfo();
                                hqlInfo.setWhereBlock(
                                        "fdImportInfo like :info");
                                hqlInfo.setParameter("info",
                                        importInfoPre + "_role_"
                                                + role.getInt("id"));
                                SysOrganizationStaffingLevel staffing = (SysOrganizationStaffingLevel) getSysOrganizationStaffingLevelService()
                                        .findFirstOne(hqlInfo);
                                if (staffing != null) {
                                    // 修改岗位角色
                                    logger.debug("--修改角色(群组)名称---");
                                    staffing.setFdName(role.getString("name"));
                                    staffing.setDocAlterTime(new Date());
                                    getSysOrganizationStaffingLevelService()
                                            .update(staffing);
                                } else {
                                    // 新增
                                    logger.debug("----新增角色（群组）----");
                                    staffing = new SysOrganizationStaffingLevel();
                                    staffing.setFdName(
                                            role.getString("name"));
                                    staffing.setFdImportInfo(
                                            importInfoPre + "_role_"
                                                    + role.getInt("id"));
                                    staffing.setDocCreateTime(new Date());
                                    staffing.setFdLevel(1);
                                    staffing.setFdIsDefault(false);
                                    staffing.setFdDescription("来自钉钉角色同步");
                                    staffing.setFdDescription(
                                            "来自钉钉角色：" + dingRoleMap
                                                    .get(roleType)
                                                    .getString("name"));
                                    getSysOrganizationStaffingLevelService()
                                            .add(staffing);
                                }
                            }

                            break; // 跳出循环
                        }

                    }

                }

            }

        } else if ("label_conf_del".equals(eventType)) {
            // 角色删除
            logger.warn("角色删除:" + roleCallbackData);
            JSONArray LabelIdList = roleCallbackData
                    .getJSONArray("LabelIdList");
            for (String key : ekpRoleMap.keySet()) {
                logger.debug("key:" + key);
                if ("post".equals(key) || "group".equals(key)) {
                    HQLInfo hqlInfo = new HQLInfo();
                    hqlInfo.setWhereBlock(
                            "(fdOrgType=4 or fdOrgType=16) and fdImportInfo like :info");
                    hqlInfo.setParameter("info", importInfoPre
                            + "_role_" + LabelIdList.getInt(0));
                    List<SysOrgElement> listRoles = sysOrgElementService
                            .findList(hqlInfo);
                    if (listRoles != null && listRoles.size() > 0) {
                        for (SysOrgElement org : listRoles) {
                            org.setFdIsAvailable(false);
                            sysOrgElementService.update(org);
                        }
                    }
                } else {
                    HQLInfo hqlInfo = new HQLInfo();
                    hqlInfo.setWhereBlock(
                            "fdImportInfo like :info");
                    hqlInfo.setParameter("info",
                            importInfoPre + "_role_" + LabelIdList.getInt(0));
                    List<SysOrganizationStaffingLevel> staffingList = getSysOrganizationStaffingLevelService()
                            .findList(hqlInfo);
                    if (staffingList != null && staffingList.size() > 0) {
                        for (SysOrganizationStaffingLevel staff : staffingList) {
                            getSysOrganizationStaffingLevelService()
                                    .delete(staff);
                        }
                    }
                }

            }
        }
    }

    private void updateOrAddRole2org(int orgType, JSONObject role, Map<String, JSONObject> dingRoleMap) {
        try {
            logger.debug("orgType:" + orgType + "   role:" + role
                    + "  dingRoleMap:" + dingRoleMap);
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setWhereBlock(
                    "fdOrgType=:orgType and fdImportInfo like :info");
            hqlInfo.setParameter("orgType", orgType);
            hqlInfo.setParameter("info", importInfoPre
                    + "_role_" + role.getInt("id"));
            // com.landray.kmss.third.ding.oms.SynchroOrgDing2EkpImp_role_422996043
            hqlInfo.setOrderBy("fdIsAvailable");
            List<SysOrgElement> orgList = sysOrgElementService
                    .findList(hqlInfo);
            if (orgList != null && orgList.size() > 0) {
                // 修改
                for (SysOrgElement org : orgList) {
                    org.setFdName(role.getString("name"));
                    org.setFdIsAvailable(true);
                    org.setFdAlterTime(new Date());
                    getSysOrgPostService().update(org);
                }
            } else {
                // 新增
                if (orgType == 4) {
                    logger.debug("----新增岗位角色----");
                    SysOrgPost org = new SysOrgPost();
                    org.setFdName(role.getString("name"));
                    org.setFdImportInfo(
                            importInfoPre + "_role_"
                                    + role.getInt("id"));
                    org.setFdOrgType(orgType);
                    org.setFdCreateTime(new Date());
                    org.setFdIsAvailable(true);
                    org.setFdIsBusiness(true);
                    org.setFdIsAbandon(false);
                    String roleType = "";
                    if (orgType == 4) {
                        roleType = "post";
                    } else if (orgType == 16) {
                        roleType = "group";
                    }
                    org.setFdMemo("来自钉钉角色：" + dingRoleMap
                            .get("post").getString("name"));
                    getSysOrgPostService().add(org);
                } else if (orgType == 16) {
                    logger.debug("----新增群组角色----");
                    SysOrgGroup org = new SysOrgGroup();
                    org.setFdName(role.getString("name"));
                    org.setFdImportInfo(
                            importInfoPre + "_role_"
                                    + role.getInt("id"));
                    org.setFdOrgType(orgType);
                    org.setFdCreateTime(new Date());
                    org.setFdIsAvailable(true);
                    org.setFdIsBusiness(true);
                    org.setFdIsAbandon(false);
                    org.setFdMemo("来自钉钉角色：" + dingRoleMap
                            .get("group").getString("name"));
                    getSysOrgGroupService().add(org);
                }

            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveOrUpdateUserRolesCallback(JSONObject plainTextJson)
            throws Exception {
        logger.debug("角色线回调：" + plainTextJson.toString());
        String eventType = plainTextJson.getString("EventType");
        if (!"label_user_scope_change".equals(eventType)) {
            saveOrUpdateUserRolesGroupCallback(plainTextJson);
        }
        // 更新角色线成员
        saveOrUpdateUserRolelinesCallback(plainTextJson);
    }

    public void saveOrUpdateUserRolesGroupCallback(JSONObject plainTextJson)
            throws Exception {
        logger.debug("人员角色变更：" + plainTextJson);
        JSONArray LabelIdList = plainTextJson.getJSONArray("LabelIdList");
        // 获取人员数据
        JSONArray UserIdList = plainTextJson.getJSONArray("UserIdList");
        List<SysOrgPerson> userList = new ArrayList<SysOrgPerson>();
        for (int i = 0; i < UserIdList.size(); i++) {
            OmsRelationModel model = getOmsRelationModel(
                    UserIdList.getString(i));
            if (model == null) {
                logger.warn(
                        "对照表找不到  userid:"
                                + UserIdList.getString(i)
                                + "对应的记录");
                continue;
            }
            SysOrgPerson sysOrgPerson = (SysOrgPerson) sysOrgPersonService
                    .findByPrimaryKey(
                            model.getFdEkpId());
            if (sysOrgPerson != null) {
                userList.add(sysOrgPerson);
            }
        }
        if (userList == null || userList.isEmpty()) {
            logger.warn("同步的钉钉人员在EKP中没有找到对于的记录，请先维护对照表信息：" + LabelIdList);
            return;
        }

        // 同步操作
        String op = plainTextJson.getString("action");

        for (int i = 0; i < LabelIdList.size(); i++) {
            int labelId = LabelIdList.getInt(i);

            Map<String, String> ekpRoleMap = getEkpRoleMap(); // "post":"123456"
            if (ekpRoleMap == null || ekpRoleMap.isEmpty()) {
                logger.warn("----ekp中未开启 角色->岗位、角色->职务、角色->群组 的同步----");
                return;
            }
            for (String key : ekpRoleMap.keySet()) {
                logger.debug("key:" + key);
                HQLInfo hqlInfo = new HQLInfo();
                if ("group".equals(key)) {
                    hqlInfo.setWhereBlock(
                            "fdOrgType=16 and fdImportInfo like :info and fdIsAvailable=:fdIsAvailable");
                    hqlInfo.setParameter("info",
                            importInfoPre + "_role_" + labelId);
                    hqlInfo.setParameter("fdIsAvailable", true);
                    List<SysOrgGroup> list = getSysOrgGroupService()
                            .findList(hqlInfo);
                    if (list == null || list.isEmpty()) {
                        logger.warn("找不到对于的群组角色");
                        continue;
                    }
                    for (SysOrgGroup group : list) {
                        List<SysOrgPerson> fdPersons = group.getFdMembers();
                        if (fdPersons == null) {
                            fdPersons = new ArrayList<SysOrgPerson>();
                        }
                        if ("add".equals(op)) {
                            logger.debug("----新增----");
                            fdPersons.addAll(userList);
                        } else if ("remove".equals(op)) {
                            logger.debug("----删除----");
                            fdPersons.removeAll(userList);
                        }
                        group.setFdMembers(fdPersons);
                        getSysOrgGroupService().update(group);
                    }
                } else if ("post".equals(key)) {
                    hqlInfo.setWhereBlock(
                            "fdOrgType=4 and fdImportInfo like :info and fdIsAvailable=:fdIsAvailable");
                    hqlInfo.setParameter("info",
                            importInfoPre + "_role_" + labelId);
                    hqlInfo.setParameter("fdIsAvailable", true);
                    List<SysOrgPost> list = getSysOrgPostService()
                            .findList(hqlInfo);
                    if (list == null || list.isEmpty()) {
                        logger.warn("找不到对于的岗位角色");
                        continue;
                    }
                    for (SysOrgPost post : list) {
                        List<SysOrgPerson> fdPersons = post.getFdPersons();
                        if (fdPersons == null) {
                            fdPersons = new ArrayList<SysOrgPerson>();
                        }
                        if ("add".equals(op)) {
                            logger.debug("----新增----");
                            fdPersons.addAll(userList);
                        } else if ("remove".equals(op)) {
                            logger.debug("----删除----");
                            fdPersons.removeAll(userList);
                        }
                        post.setFdPersons(fdPersons);
                        getSysOrgPostService().update(post);
                    }
                } else if ("staffing".equals(key)) {
                    hqlInfo.setWhereBlock(
                            "fdImportInfo like :info");
                    hqlInfo.setParameter("info",
                            importInfoPre + "_role_" + labelId);
                    List<SysOrganizationStaffingLevel> staffingList = getSysOrganizationStaffingLevelService()
                            .findList(hqlInfo);
                    if (staffingList == null || staffingList.isEmpty()) {
                        logger.warn("找不到对于的职务角色");
                        continue;
                    }
                    for (SysOrganizationStaffingLevel staffing : staffingList) {
                        List<SysOrgPerson> fdPersons = staffing.getFdPersons();
                        if (fdPersons == null) {
                            fdPersons = new ArrayList<SysOrgPerson>();
                        }
                        if ("add".equals(op)) {
                            logger.debug("----新增----");
                            fdPersons.addAll(userList);
                        } else if ("remove".equals(op)) {
                            logger.debug("----删除----");
                            fdPersons.removeAll(userList);
                        }
                        staffing.setFdPersons(fdPersons);
                        getSysOrganizationStaffingLevelService()
                                .update(staffing);
                    }
                }

            }
        }
    }

    private ISysOrgRoleConfCateService sysOrgRoleConfCateService = null;

    public ISysOrgRoleConfCateService getSysOrgRoleConfCateService() {
        if (sysOrgRoleConfCateService == null) {
            sysOrgRoleConfCateService = (ISysOrgRoleConfCateService) SpringBeanUtil
                    .getBean("sysOrgRoleConfCateService");
        }
        return sysOrgRoleConfCateService;
    }

    private ISysOrgRoleConfService sysOrgRoleConfService = null;

    public ISysOrgRoleConfService getSysOrgRoleConfService() {
        if (sysOrgRoleConfService == null) {
            sysOrgRoleConfService = (ISysOrgRoleConfService) SpringBeanUtil
                    .getBean("sysOrgRoleConfService");
        }
        return sysOrgRoleConfService;
    }

    private ISysOrgRoleLineService sysOrgRoleLineService = null;

    public ISysOrgRoleLineService getSysOrgRoleLineService() {
        if (sysOrgRoleLineService == null) {
            sysOrgRoleLineService = (ISysOrgRoleLineService) SpringBeanUtil
                    .getBean("sysOrgRoleLineService");
        }
        return sysOrgRoleLineService;
    }

    private IThirdDingRolelineMappService thirdDingRolelineMappService = null;

    public IThirdDingRolelineMappService getThirdDingRolelineMappService() {
        if (thirdDingRolelineMappService == null) {
            thirdDingRolelineMappService = (IThirdDingRolelineMappService) SpringBeanUtil
                    .getBean("thirdDingRolelineMappService");
        }
        return thirdDingRolelineMappService;
    }

    private IThirdDingRoleCateMappService thirdDingRoleCateMappService = null;

    public IThirdDingRoleCateMappService getThirdDingRoleCateMappService() {
        if (thirdDingRoleCateMappService == null) {
            thirdDingRoleCateMappService = (IThirdDingRoleCateMappService) SpringBeanUtil
                    .getBean("thirdDingRoleCateMappService");
        }
        return thirdDingRoleCateMappService;
    }

    private void synchroRoleLineConfCates() throws Exception {
        logger.debug("同步角色线分类");
        String roleGroup = DingUtils.getDingApiService().getRoleList();
        Map<String, String> roleGroupMap = new HashMap<String, String>();
        JSONObject rs = JSONObject.fromObject(roleGroup);
        if (rs.getInt("errcode") == 0 && rs.containsKey("result")) {
            JSONObject result = rs.getJSONObject("result");
            JSONArray list = result.getJSONArray("list");
            for (int i = 0; i < list.size(); i++) {
                JSONObject role = list.getJSONObject(i);
                roleGroupMap.put(role.getString("groupId"),
                        role.getString("name"));
            }
        } else {
            throw new Exception("获取角色组失败," + roleGroup);
        }
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            List<ThirdDingRoleCateMapp> cateMappList = getThirdDingRoleCateMappService()
                    .findList(new HQLInfo());
            Map<String, String> cate_mapp = new HashMap<String, String>();
            for (ThirdDingRoleCateMapp mapp : cateMappList) {
                String groupId = mapp.getFdGroupId();
                String cateId = mapp.getFdEkpCateId();
                cate_mapp.put(cateId, groupId);
            }
            String[] cateIdArray = new String[cate_mapp.size()];
            cateIdArray = cate_mapp.keySet().toArray(cateIdArray);
            List<SysOrgRoleConfCate> cateList = getSysOrgRoleConfCateService()
                    .findByPrimaryKeys(cateIdArray);
            Map<String, SysOrgRoleConfCate> cate_ekp = new HashMap<String, SysOrgRoleConfCate>();
            for (SysOrgRoleConfCate cate : cateList) {
                String groupId = cate_mapp.get(cate.getFdId());
                cate_ekp.put(groupId, cate);
            }
            DingConfig config = new DingConfig();
            String roleGroupId = config.getDing2ekpRoleRoleline();
            String[] roleGroupIds = roleGroupId.split(";");

            for (int i = 0; i < roleGroupIds.length; i++) {
                String groupId = roleGroupIds[i];
                String groupName = roleGroupMap.get(groupId);
                if (cate_ekp.containsKey(groupId)) {
                    SysOrgRoleConfCate cate = cate_ekp.get(groupId);
                    cate.setFdName(groupName);
                    getSysOrgRoleConfCateService().update(cate);
                    logger.debug("更新角色分类，ID：{},名称：{}", cate.getFdId(),
                            cate.getFdName());
                } else {
                    SysOrgRoleConfCate cate = new SysOrgRoleConfCate();
                    String cateId = IDGenerator.generateID();
                    cate.setFdId(cateId);
                    cate.setFdCreateTime(new Date());
                    cate.setFdName(groupName);
                    getSysOrgRoleConfCateService().add(cate);
                    logger.debug("新增角色分类，ID：{},名称：{}", cate.getFdId(),
                            cate.getFdName());
                    ThirdDingRoleCateMapp mapp = new ThirdDingRoleCateMapp();
                    mapp.setFdEkpCateId(cateId);
                    mapp.setFdGroupId(groupId);
                    getThirdDingRoleCateMappService().add(mapp);
                    cate_ekp.put(groupId, cate);
                }
            }
            // 删除分类
            try {
                Set<String> ekpGroupIds = cate_ekp.keySet();
                ekpGroupIds.removeAll(Arrays.asList(roleGroupIds));
                for (String groupId : ekpGroupIds) {
                    String fdId = cate_ekp.get(groupId).getFdId();
                    List<SysOrgRoleConf> list = getSysOrgRoleConfService()
                            .findList("fdRoleConfCate.fdId = '" + fdId + "'", null);
                    for (SysOrgRoleConf conf : list) {
                        conf.setFdIsAvailable(false);
                        conf.setFdRoleConfCate(null);
                        getSysOrgRoleConfService().update(conf);
                    }
                    getSysOrgRoleConfCateService().delete(fdId);
                    getThirdDingRoleCateMappService().deleteByCateId(fdId);
                    logger.debug("删除角色分类，ID：{}", fdId);
                }
            } catch (Exception e) {
                logger.warn("角色分类删除失败，可能原因是分类下存在角色线配置！请手动处理："+e.getMessage());
                log("角色分类删除失败，可能原因是分类下存在角色线配置！请手动处理："+e.getMessage());
            }
            TransactionUtils.getTransactionManager().commit(status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.getTransactionManager().rollback(status);
            throw e;
        }
    }

    private SysOrgRoleConfCate findSysOrgRoleConfCate(String groupId)
            throws Exception {
        ThirdDingRoleCateMapp mapp = getThirdDingRoleCateMappService()
                .findByGroupId(groupId);
        if (mapp == null) {
            return null;
        }
        SysOrgRoleConfCate cate = (SysOrgRoleConfCate) getSysOrgRoleConfCateService()
                .findByPrimaryKey(mapp.getFdEkpCateId());
        return cate;
    }

    private void synchroRoleLineConfs() throws Exception {
        logger.debug("同步角色线配置");
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            DingConfig config = new DingConfig();
            String roleGroupId = config.getDing2ekpRoleRoleline();
            String[] roleGroupIds = roleGroupId.split(";");
            Set<String> dingRoleIds = new HashSet<String>();
            for (String groupId : roleGroupIds) {
                String result = DingUtils.getDingApiService()
                        .getRoleByGroupId(Long.parseLong(groupId));
                JSONObject rs = JSONObject.fromObject(result);
                if (rs.getInt("errcode") == 0 && rs.containsKey("role_group")) {
                    JSONObject role_group = rs.getJSONObject("role_group");
                    JSONArray roles = role_group.getJSONArray("roles");
                    synchroRoleLineConf(roles, groupId, dingRoleIds);
                } else {
                    throw new Exception("获取角色组失败," + result);
                }
            }
            disableRoleConf(dingRoleIds);
            TransactionUtils.getTransactionManager().commit(status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.getTransactionManager().rollback(status);
            throw e;
        }
    }

    private void synchroRoleLineConf(JSONArray roleList, String groupId,
                                     Set<String> dingRoleIds)
            throws Exception {
        logger.debug("同步角色线配置，分组ID：{}，数据：{}", groupId, roleList.toString());
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            SysOrgRoleConfCate cate = findSysOrgRoleConfCate(groupId);
            HQLInfo hqlInfo = new HQLInfo();
            for (int i = 0; i < roleList.size(); i++) {
                JSONObject o = roleList.getJSONObject(i);
                String role_id = o.getString("role_id");
                String role_name = o.getString("role_name");
                dingRoleIds.add(role_id);
                SysOrgRoleConf conf = null;
                ThirdDingRolelineMapp mapp = getThirdDingRolelineMappService()
                        .findByDingRole(role_id);
                if (mapp == null) {
                    String roleConfId = IDGenerator.generateID();
                    conf = addSysOrgRoleConf(roleConfId, role_name, cate);
                    addRolelineMapp(role_id + "", roleConfId);
                    logger.debug("找不到映射关系，新增角色线配置，ID：{}，名称：{}", roleConfId,
                            role_name);
                } else {
                    String ekpRoleId = mapp.getFdEkpRoleId();
                    conf = (SysOrgRoleConf) getSysOrgRoleConfService()
                            .findByPrimaryKey(ekpRoleId, null, true);
                    if (conf == null) {
                        conf = addSysOrgRoleConf(ekpRoleId, role_name, cate);
                        logger.debug("找不到角色线配置，执行新增，ID：{}，名称：{}", ekpRoleId,
                                role_name);
                    } else {
                        conf.setFdIsAvailable(true);
                        conf.setFdName(role_name);
                        conf.setFdRoleConfCate(cate);
                        getSysOrgRoleConfService().update(conf);
                        logger.debug("更新角色线配置，ID：{}，名称：{}", conf.getFdId(),
                                role_name);
                    }
                }
            }
            TransactionUtils.getTransactionManager().commit(status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.getTransactionManager().rollback(status);
            throw e;
        }
    }

    private void disableRoleConf(Set<String> dingRoleIds) throws Exception {
        logger.debug("范围之外的角色线配置置为无效");
        // 角色线配置置为无效
        List<ThirdDingRolelineMapp> mappList = getThirdDingRolelineMappService()
                .findList(new HQLInfo());
        for (ThirdDingRolelineMapp mapp : mappList) {
            String dingRoleId = mapp.getFdDingRoleId();
            if (!dingRoleIds.contains(dingRoleId)) {
                String ekpRoleId = mapp.getFdEkpRoleId();
                SysOrgRoleConf conf = (SysOrgRoleConf) getSysOrgRoleConfService()
                        .findByPrimaryKey(ekpRoleId, null, true);
                if (conf != null) {
                    conf.setFdIsAvailable(false);
                    getSysOrgRoleConfService().update(conf);
                    logger.debug("角色线配置置为无效，ID：{}，名称：{}，钉钉ID：{}",
                            conf.getFdId(), conf.getFdName(), dingRoleId);
                }
            }
        }
    }

    private void synchroRoleLines() throws Exception {
        logger.debug("同步角色线成员");
        try {
            DingConfig config = new DingConfig();
            String roleGroupId = config.getDing2ekpRoleRoleline();
            String[] roleGroupIds = roleGroupId.split(";");
            for (String groupId : roleGroupIds) {
                String result = DingUtils.getDingApiService()
                        .getRoleByGroupId(Long.parseLong(groupId));
                logger.debug("获取数据，groupId：{}，数据：{}", groupId, result);
                JSONObject rs = JSONObject.fromObject(result);
                if (rs.getInt("errcode") == 0 && rs.containsKey("role_group")) {
                    JSONObject role_group = rs.getJSONObject("role_group");
                    JSONArray roles = role_group.getJSONArray("roles");
                    for (int i = 0; i < roles.size(); i++) {
                        JSONObject role = roles.getJSONObject(i);
                        Long role_id = role.getLong("role_id");
                        TransactionStatus status = null;
                        try {
                            status = TransactionUtils.beginNewTransaction();
                            synchroRoleLines(role_id);
                            TransactionUtils.getTransactionManager()
                                    .commit(status);
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                            TransactionUtils.getTransactionManager()
                                    .rollback(status);
                            throw e;
                        }
                    }
                } else {
                    throw new Exception("获取角色组失败," + result);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    private void synchroRoleLines(Long role_id) throws Exception {
        logger.debug("同步角色线成员，钉钉角色ID：{}", role_id);
        boolean hasMore = false;
        Long count = 0L;
        List<SysOrgElement> rootDepts = getRootDepts();
        ThirdDingRolelineMapp mapp = (ThirdDingRolelineMapp) getThirdDingRolelineMappService()
                .findByDingRole(role_id + "");
        if (mapp == null) {
            throw new Exception("找不到角色线配置，role_id:" + role_id);
        }
        SysOrgRoleConf conf = (SysOrgRoleConf) getSysOrgRoleConfService()
                .findByPrimaryKey(mapp.getFdEkpRoleId(), null, true);
        if (conf == null) {
            throw new Exception("找不到角色线配置，ekp_id:" + role_id);
        }
        delRoleLinesBatch(conf.getFdId(), null);
        do {
            hasMore = false;
            try {
                String simpleList = DingUtils.dingApiService
                        .getSimplelistByRoleId(role_id, count);
                logger.debug("获取角色线详情信息，role_id：{}，数据：{}", role_id, simpleList);
                if (StringUtil.isNotNull(simpleList)) {
                    JSONObject personObject = JSONObject.fromObject(simpleList);
                    if (personObject.getInt("errcode") == 0) {
                        hasMore = personObject.getJSONObject("result")
                                .getBoolean("hasMore");
                        JSONArray resultList = personObject
                                .getJSONObject("result").getJSONArray("list");
                        if (resultList != null && !resultList.isEmpty()) {
                            for (int j = 0; j < resultList.size(); j++) {
                                synchroRoleLine(role_id,
                                        resultList.getJSONObject(j), conf,
                                        rootDepts);
                            }
                        }
                    }
                }
                count++;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw e;
            }
        } while (hasMore);
    }

    private void synchroRoleLine(Long role_id, JSONObject o,
                                 SysOrgRoleConf conf, List<SysOrgElement> rootDepts)
            throws Exception {
        logger.debug("同步角色线详情，role_id：{}，数据：{}", role_id, o.toString());
        String userid = o.getString("userid");
        String name = o.getString("name");
        logger.debug("userid:" + userid);
        OmsRelationModel model = getOmsRelationModel(
                userid);
        if (model == null) {
            logger.warn("对照表找不到  userid:" + userid
                    + "对应的记录");
            return;
        }
        SysOrgPerson sysOrgPerson = (SysOrgPerson) sysOrgPersonService
                .findByPrimaryKey(model.getFdEkpId());
        if (sysOrgPerson != null) {
            SysOrgRoleLine parent = addSysOrgRoleLine(sysOrgPerson, name, null,
                    conf);
            JSONArray manageScopes = null;
            if (o.containsKey("manageScopes")) {
                manageScopes = o.getJSONArray("manageScopes");
            }
            if (manageScopes == null || manageScopes.isEmpty()) {
                for (SysOrgElement root : rootDepts) {
                    addSysOrgRoleLine(root, root.getFdName(), parent, conf);
                }
            } else {
                for (int i = 0; i < manageScopes.size(); i++) {
                    JSONObject scope = manageScopes.getJSONObject(i);
                    name = scope.getString("name");
                    Long dept_id = scope.getLong("dept_id");
                    model = getOmsRelationModel(
                            dept_id + "");
                    if (model == null) {
                        logger.warn("对照表找不到  dept_id:" + dept_id
                                + "对应的记录");
                        continue;
                    }
                    SysOrgElement ele = (SysOrgElement) sysOrgElementService
                            .findByPrimaryKey(model.getFdEkpId());
                    addSysOrgRoleLine(ele, name, parent, conf);
                }
            }
        }
    }

    private List<SysOrgElement> getRootDepts() throws Exception {
        HQLInfo info = new HQLInfo();
        info.setWhereBlock(
                "(fdOrgType=1 or fdOrgType=2) and hbmParent=null and fdIsAvailable=:avail");
        info.setParameter("avail", true);
        return sysOrgElementService.findList(info);
    }

    private SysOrgRoleLine addSysOrgRoleLine(SysOrgElement ele,
                                             String roleLineName,
                                             SysOrgRoleLine parent, SysOrgRoleConf conf) throws Exception {
        if (ele == null) {
            return null;
        }
        logger.debug("新增角色线成员，ID：{}，名称：{}", ele.getFdId(), ele.getFdName());
        SysOrgRoleLine line = new SysOrgRoleLine();
        line.setFdAlterTime(new Date());
        line.setFdCreateTime(new Date());
        line.setFdHasChild(parent == null ? true : false);
        line.setFdName(roleLineName);
        line.setFdParent(parent);
        line.setSysOrgRoleConf(conf);
        line.setSysOrgRoleMember(ele);
        getSysOrgRoleLineService().add(line);
        return line;
    }

    private SysOrgRoleConf addSysOrgRoleConf(String fdId, String name,
                                             SysOrgRoleConfCate cate) throws Exception {
        SysOrgRoleConf conf = new SysOrgRoleConf();
        conf.setFdId(fdId);
        conf.setFdName(name);
        conf.setFdAlterTime(new Date());
        conf.setFdCreateTime(new Date());
        conf.setFdIsAvailable(true);
        conf.setFdRoleConfCate(cate);
        getSysOrgRoleConfService().add(conf);
        return conf;
    }

    private void addRolelineMapp(String role_id,
                                 String roleConfId) throws Exception {
        ThirdDingRolelineMapp mapp = new ThirdDingRolelineMapp();
        mapp.setFdDingRoleId(role_id + "");
        mapp.setFdEkpRoleId(roleConfId);
        getThirdDingRolelineMappService().add(mapp);
    }

    // 同步角色线
    private void synchroRoleline() {
        try {
            DingConfig config = DingConfig.newInstance();
            String ding2ekpRoleRolelineSynWay = config
                    .getDing2ekpRoleRolelineSynWay();
            if (StringUtil.isNotNull(ding2ekpRoleRolelineSynWay)
                    && "syn".equalsIgnoreCase(ding2ekpRoleRolelineSynWay)) {
                String groupId = config.getDing2ekpRoleRoleline();
                logger.debug("角色线groupId:" + groupId);
                synchroRoleLineConfCates();
                synchroRoleLineConfs();
                synchroRoleLines();
            }
        } catch (Exception e) {
            logger.error("同步角色线失败：" + e.getMessage(), e);
            jobContext.logError("同步角色线失败：" + e.getMessage());
        }
    }

    private void delRoleLinesBatch(String roleConfId, List<String> memberIds)
            throws HibernateException, Exception {
        logger.debug("清空角色线成员数据，角色线配置ID：{}", roleConfId);
        if (memberIds == null || memberIds.isEmpty()) {
            NativeQuery query_update = getSysOrgRoleLineService().getBaseDao()
                    .getHibernateSession().createNativeQuery(
                            "update sys_org_role_line set fd_parent_id = null where fd_role_line_conf_id='"
                                    + roleConfId + "'");
            NativeQuery query_delete = getSysOrgRoleLineService().getBaseDao()
                    .getHibernateSession().createNativeQuery(
                            "delete from sys_org_role_line where fd_role_line_conf_id='"
                                    + roleConfId + "'");
            try {
                query_update.executeUpdate();
                query_delete.executeUpdate();
            } catch (Exception e) {
                logger.error(
                        "delete from sys_org_role_line where fd_role_line_conf_id='"
                                + roleConfId + "'");
                logger.error(e.getMessage(), e);
            }
            return;
        }
        String arrStr = "";
        String tmp = "";
        for (String roleLineId : memberIds) {
            tmp = "'" + roleLineId + "'";
            arrStr += "," + tmp;
        }
        if (arrStr.length() > 0) {
            arrStr = arrStr.substring(1);
        }

        logger.debug(
                "update sys_org_role_line set fd_parent_id = null where fd_role_line_conf_id='"
                        + roleConfId + "' and fd_member_id not in (" + arrStr
                        + ")");
        NativeQuery query_update = getSysOrgRoleLineService().getBaseDao()
                .getHibernateSession().createNativeQuery(
                        "update sys_org_role_line set fd_parent_id = null where fd_role_line_conf_id='"
                                + roleConfId + "' and fd_member_id not in ("
                                + arrStr
                                + ")");
        query_update.executeUpdate();

        logger.debug(
                "delete from sys_org_role_line where fd_role_line_conf_id='"
                        + roleConfId + "' and fd_member_id not in (" + arrStr
                        + ")");
        NativeQuery query_delete = getSysOrgRoleLineService().getBaseDao()
                .getHibernateSession().createNativeQuery(
                        "delete from sys_org_role_line where fd_role_line_conf_id='"
                                + roleConfId + "' and fd_member_id not in ("
                                + arrStr
                                + ")");
        try {
            query_delete.executeUpdate();
        } catch (Exception e) {
            logger.error(
                    "delete from sys_org_role_line where fd_role_line_conf_id='"
                            + roleConfId + "' and fd_member_id not in ("
                            + arrStr
                            + ")");
            logger.error(e.getMessage(), e);
        }
    }

    private Map<String, String> getRoleGroupsMap() throws Exception {
        String roleGroup = DingUtils.getDingApiService().getRoleList();
        Map<String, String> roleGroupMap = new HashMap<String, String>();
        JSONObject rs = JSONObject.fromObject(roleGroup);
        if (rs.getInt("errcode") == 0 && rs.containsKey("result")) {
            JSONObject result = rs.getJSONObject("result");
            JSONArray list = result.getJSONArray("list");
            for (int i = 0; i < list.size(); i++) {
                JSONObject role = list.getJSONObject(i);
                roleGroupMap.put(role.getString("groupId"),
                        role.getString("name"));
            }
            return roleGroupMap;
        } else {
            throw new Exception("获取角色组失败," + roleGroup);
        }
    }

    private void addRoleConfCate(String cateId, String groupName)
            throws Exception {
        SysOrgRoleConfCate cate = new SysOrgRoleConfCate();
        cate.setFdId(cateId);
        cate.setFdCreateTime(new Date());
        cate.setFdName(groupName);
        getSysOrgRoleConfCateService().add(cate);
    }

    private void updateRoleConfCate(String groupId, String groupName)
            throws Exception {
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            ThirdDingRoleCateMapp mapp = getThirdDingRoleCateMappService()
                    .findByGroupId(groupId);
            if (mapp != null) {
                String cateId = mapp.getFdEkpCateId();
                SysOrgRoleConfCate cate = (SysOrgRoleConfCate) getSysOrgRoleConfCateService()
                        .findByPrimaryKey(cateId, null, true);
                if (cate == null) {
                    addRoleConfCate(cateId, groupName);
                } else {
                    cate.setFdName(groupName);
                    getSysOrgRoleConfCateService().update(cate);
                }
            } else {
                String cateId = IDGenerator.generateID();
                addRoleConfCate(cateId, groupName);
                logger.debug("新增角色分类，ID：{},名称：{}", cateId,
                        groupName);
                mapp = new ThirdDingRoleCateMapp();
                mapp.setFdEkpCateId(cateId);
                mapp.setFdGroupId(groupId);
                getThirdDingRoleCateMappService().add(mapp);
            }
            TransactionUtils.getTransactionManager().commit(status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.getTransactionManager().rollback(status);
            throw e;
        }
    }

    private void updateRoleConf(String groupId, String roleId, String roleName)
            throws Exception {
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            ThirdDingRoleCateMapp cateMapp = getThirdDingRoleCateMappService()
                    .findByGroupId(groupId);
            SysOrgRoleConfCate cate = null;
            if (cateMapp != null) {
                String cateId = cateMapp.getFdEkpCateId();
                cate = (SysOrgRoleConfCate) getSysOrgRoleConfCateService()
                        .findByPrimaryKey(cateId, null, true);
            }
            ThirdDingRolelineMapp mapp = (ThirdDingRolelineMapp) getThirdDingRolelineMappService()
                    .findByDingRole(roleId);
            if (mapp != null) {
                String ekpRoleId = mapp.getFdEkpRoleId();
                SysOrgRoleConf conf = (SysOrgRoleConf) getSysOrgRoleConfService()
                        .findByPrimaryKey(ekpRoleId, null, true);
                if (conf == null) {
                    addSysOrgRoleConf(ekpRoleId, roleName, cate);
                } else {
                    conf.setFdName(roleName);
                    conf.setFdIsAvailable(true);
                    getSysOrgRoleConfService().update(conf);
                }
            } else {
                String roleConfId = IDGenerator.generateID();
                addSysOrgRoleConf(roleConfId, roleName, cate);
                addRolelineMapp(roleId + "", roleConfId);
                logger.debug("找不到映射关系，新增角色线配置，ID：{}，名称：{}", roleConfId,
                        roleName);
            }
            TransactionUtils.getTransactionManager().commit(status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.getTransactionManager().rollback(status);
            throw e;
        }
    }

    private void addOrUpdateRole(JSONArray LabelIdList) throws Exception {
        Map<String, String> roleGroupsMap = getRoleGroupsMap();
        String groupId = LabelIdList.getString(0);
        String rolelines = DingConfig.newInstance().getDing2ekpRoleRoleline();
        String[] rolelineArray = rolelines.split(";");
        List<String> rolelineList = Arrays.asList(rolelineArray);
        if (roleGroupsMap.containsKey(groupId)) {
            logger.debug("角色组回调，" + LabelIdList);
            for (int i = 0; i < LabelIdList.size(); i++) {
                groupId = LabelIdList.getString(i);
                if (rolelineList.contains(groupId)) {
                    // 更新角色组
                    updateRoleConfCate(groupId, roleGroupsMap.get(groupId));
                }
            }
        } else {
            logger.debug("角色回调，" + LabelIdList);
            for (int i = 0; i < LabelIdList.size(); i++) {
                Long roleId = LabelIdList.getLong(i);
                // 更新角色
                String role = DingUtils.getDingApiService().getRole(roleId);
                JSONObject rs = JSONObject.fromObject(role);
                if (rs.getInt("errcode") == 0 && rs.containsKey("role")) {
                    JSONObject roleObj = rs.getJSONObject("role");
                    String groupId_this = roleObj.getString("groupId");
                    if (rolelineList.contains(groupId_this)) {
                        // 更新角色线配置
                        String name = roleObj.getString("name");
                        updateRoleConf(groupId_this, roleId + "", name);
                    }
                } else {
                    throw new Exception("获取角色失败," + roleId);
                }
            }
        }
    }

    private void deleteRoleConf(JSONArray LabelIdList) throws Exception {
        for (int i = 0; i < LabelIdList.size(); i++) {
            Long roleId = LabelIdList.getLong(i);
            ThirdDingRolelineMapp mapp = (ThirdDingRolelineMapp) getThirdDingRolelineMappService()
                    .findByDingRole(roleId + "");
            if (mapp != null) {
                String ekpRoleId = mapp.getFdEkpRoleId();
                SysOrgRoleConf conf = (SysOrgRoleConf) getSysOrgRoleConfService()
                        .findByPrimaryKey(ekpRoleId, null, true);
                if (conf != null) {
                    conf.setFdIsAvailable(false);
                    getSysOrgRoleConfService().update(conf);
                }
            }
        }
    }

    public void saveOrUpdateRolesRolelineCallback(JSONObject roleCallbackData)
            throws Exception {
        String eventType = roleCallbackData.getString("EventType");
        DingConfig config = DingConfig.newInstance();
        String synWay = config.getDing2ekpRoleRolelineSynWay();
        if (StringUtil.isNull(synWay) || !"syn".equals(synWay)) {
            logger.warn("----ekp中未开启角色线同步----");
            return;
        }
        JSONArray LabelIdList = roleCallbackData
                .getJSONArray("LabelIdList");
        if ("label_conf_add".equals(eventType)
                || "label_conf_modify".equals(eventType)) {
            addOrUpdateRole(LabelIdList);
        } else if ("label_conf_del".equals(eventType)) {
            deleteRoleConf(LabelIdList);
        }
    }

    public void saveOrUpdateUserRolelinesCallback(JSONObject roleCallbackData)
            throws Exception {
        DingConfig config = DingConfig.newInstance();
        String synWay = config.getDing2ekpRoleRolelineSynWay();
        if (StringUtil.isNull(synWay) || !"syn".equals(synWay)) {
            logger.warn("----ekp中未开启角色线同步----");
            return;
        }
        JSONArray LabelIdList = null;
        String eventType = roleCallbackData.getString("EventType");
        if ("label_user_scope_change".equals(eventType)) {
            long roleId = roleCallbackData.getLong("roleId");
            LabelIdList = new JSONArray();
            LabelIdList.add(roleId);
        } else {
            LabelIdList = roleCallbackData
                    .getJSONArray("LabelIdList");
        }
        logger.debug("角色回调，" + LabelIdList);
        String rolelines = DingConfig.newInstance().getDing2ekpRoleRoleline();
        String[] rolelineArray = rolelines.split(";");
        List<String> rolelineList = Arrays.asList(rolelineArray);
        for (int i = 0; i < LabelIdList.size(); i++) {
            Long roleId = LabelIdList.getLong(i);
            // 更新角色
            String role = DingUtils.getDingApiService().getRole(roleId);
            JSONObject rs = JSONObject.fromObject(role);
            if (rs.getInt("errcode") == 0 && rs.containsKey("role")) {
                JSONObject roleObj = rs.getJSONObject("role");
                String groupId_this = roleObj.getString("groupId");
                if (rolelineList.contains(groupId_this)) {
                    // 更新角色线成员
                    synchroRoleLines(roleId);
                }
            } else {
                throw new Exception("获取角色失败," + roleId);
            }
        }
    }

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
