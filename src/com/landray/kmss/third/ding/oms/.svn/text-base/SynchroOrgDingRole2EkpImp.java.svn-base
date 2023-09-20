package com.landray.kmss.third.ding.oms;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.component.locker.interfaces.ConcurrencyException;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.sys.organization.model.*;
import com.landray.kmss.sys.organization.service.*;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.third.ding.model.ThirdDingRoleCateMapp;
import com.landray.kmss.third.ding.model.ThirdDingRolelineMapp;
import com.landray.kmss.third.ding.service.IThirdDingRoleCateMappService;
import com.landray.kmss.third.ding.service.IThirdDingRolelineMappService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.SynchroOrgDing2EkpUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.HibernateException;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.text.SimpleDateFormat;
import java.util.*;

public class SynchroOrgDingRole2EkpImp implements SynchroOrgDingRole2Ekp {

    private SysQuartzJobContext jobContext = null;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SynchroOrgDingRole2EkpImp.class);

    private DingApiService dingApiService = DingUtils.getDingApiService();

    // 角色同步相关的集合
    private Map<String, String> roleMap = new HashMap<String, String>(); // value:角色组名称key:post/group/Staffing
    private Map<String, SysOrgPost> role2postMap = new HashMap<String, SysOrgPost>();
    private Map<String, SysOrgGroup> role2groupMap = new HashMap<String, SysOrgGroup>();
    private Map<String, SysOrganizationStaffingLevel> role2staffingMap = new HashMap<String, SysOrganizationStaffingLevel>();

    IComponentLockService componentLockService = null;

    private IComponentLockService getComponentLockService() {
        if (componentLockService == null) {
            componentLockService = (IComponentLockService) SpringBeanUtil
                    .getBean("componentLockService");
        }
        return componentLockService;
    }

    private ISysOrgElementService sysOrgElementService = null;

    private ISysOrgPersonService sysOrgPersonService = null;

    public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
        this.sysOrgElementService = sysOrgElementService;
    }

    public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
        this.sysOrgPersonService = sysOrgPersonService;
    }

    public void setSysOrgPostService(ISysOrgPostService sysOrgPostService) {
        this.sysOrgPostService = sysOrgPostService;
    }

    public void setSysOrgGroupService(ISysOrgGroupService sysOrgGroupService) {
        this.sysOrgGroupService = sysOrgGroupService;
    }

    public void setSysOrganizationStaffingLevelService(ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
        this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
    }

    public void setSysOrgRoleConfCateService(ISysOrgRoleConfCateService sysOrgRoleConfCateService) {
        this.sysOrgRoleConfCateService = sysOrgRoleConfCateService;
    }

    public void setSysOrgRoleConfService(ISysOrgRoleConfService sysOrgRoleConfService) {
        this.sysOrgRoleConfService = sysOrgRoleConfService;
    }

    public void setSysOrgRoleLineService(ISysOrgRoleLineService sysOrgRoleLineService) {
        this.sysOrgRoleLineService = sysOrgRoleLineService;
    }

    public void setThirdDingRolelineMappService(IThirdDingRolelineMappService thirdDingRolelineMappService) {
        this.thirdDingRolelineMappService = thirdDingRolelineMappService;
    }

    public void setThirdDingRoleCateMappService(IThirdDingRoleCateMappService thirdDingRoleCateMappService) {
        this.thirdDingRoleCateMappService = thirdDingRoleCateMappService;
    }

    private ISysOrgPostService sysOrgPostService = null;

    private ISysOrgGroupService sysOrgGroupService = null;

    private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService = null;

    private ISysOrgRoleConfCateService sysOrgRoleConfCateService = null;

    private ISysOrgRoleConfService sysOrgRoleConfService = null;

    private ISysOrgRoleLineService sysOrgRoleLineService = null;

    private IThirdDingRolelineMappService thirdDingRolelineMappService = null;

    private IThirdDingRoleCateMappService thirdDingRoleCateMappService = null;

    @Override
    public void synchro(SysQuartzJobContext jobContext) throws Exception {
        this.jobContext = jobContext;
        // 钉钉角色初始化
        long caltime = System.currentTimeMillis();
        updateDingRoleSyn();
        String temp = "钉钉角色同步耗时(秒)："
                + (System.currentTimeMillis() - caltime) / 1000;
        logger.debug(temp);
        jobContext.logMessage(temp);
    }

    private void updateDingRoleSyn() {
        init();
        // 岗位处理
        initPost();
        // 群组处理
        initGrop();
        // 职务
        initStaffing();
        // 角色线
        synchroRoleline();

        clear();
    }

    private void init() {
        roleMap.clear();
        role2postMap.clear();
        role2groupMap.clear();
        role2staffingMap.clear();
    }

    private void clear() {
        roleMap.clear();
        role2postMap.clear();
        role2groupMap.clear();
        role2staffingMap.clear();
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
                                SynchroOrgDing2EkpUtil.importInfoPre + "_role_%");
                        List<SysOrganizationStaffingLevel> staffingList = sysOrganizationStaffingLevelService
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
                            if (role2staffingMap.containsKey(SynchroOrgDing2EkpUtil.importInfoPre
                                    + "_role_" + role.getInt("role_id"))) {
                                // 修改岗位角色
                                ekpAllRoleImport.remove(SynchroOrgDing2EkpUtil.importInfoPre
                                        + "_role_" + role.getInt("role_id"));
                                logger.debug("--修改角色(群组)名称---");
                                SysOrganizationStaffingLevel _staffing = role2staffingMap
                                        .get(SynchroOrgDing2EkpUtil.importInfoPre
                                                + "_role_"
                                                + role.getInt("role_id"));
                                SysOrganizationStaffingLevel staffing = (SysOrganizationStaffingLevel) sysOrganizationStaffingLevelService
                                        .findByPrimaryKey(_staffing.getFdId());
                                staffing.setFdName(role.getString("role_name"));
                                staffing.setDocAlterTime(new Date());
                                staffing.setFdPersons(persons);
                                // staffing.setFdDescription("来自钉钉角色："
                                // + roles.getJSONObject("role_group")
                                // .getString("group_name"));
                                sysOrganizationStaffingLevelService
                                        .update(staffing);
                            } else {
                                logger.debug("----新增角色（群组）----");
                                SysOrganizationStaffingLevel staffing = new SysOrganizationStaffingLevel();
                                staffing.setFdName(role.getString("role_name"));
                                staffing.setFdImportInfo(
                                        SynchroOrgDing2EkpUtil.importInfoPre + "_role_"
                                                + role.getInt("role_id"));
                                staffing.setDocCreateTime(new Date());
                                staffing.setFdLevel(1);
                                staffing.setFdIsDefault(false);
                                staffing.setFdDescription("来自钉钉角色同步");
                                staffing.setFdDescription("来自钉钉角色："
                                        + roles.getJSONObject("role_group")
                                        .getString("group_name"));
                                staffing.setFdPersons(persons);
                                sysOrganizationStaffingLevelService
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
                            sysOrganizationStaffingLevelService
                                    .update(staffing);
                            sysOrganizationStaffingLevelService
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
                if (StringUtil.isNull(groupId)) {
                    return;
                }

                // 获取ekp的岗位信息
                HQLInfo hqlInfo = new HQLInfo();
                hqlInfo.setWhereBlock(
                        "fdOrgType=16 and fdImportInfo like :info");
                hqlInfo.setParameter("info", SynchroOrgDing2EkpUtil.importInfoPre + "_role_%");
                hqlInfo.setOrderBy("fdIsAvailable");
                List<SysOrgGroup> groupList = sysOrgGroupService
                        .findList(hqlInfo);
                Set<String> hadSynRoleImport = new HashSet<String>();
                for (SysOrgGroup group : groupList) {
                    role2groupMap.put(group.getFdImportInfo(), group);
                }
                String[] groupIds = groupId.split(";");
                for (int i = 0; i < groupIds.length; i++) {
                    updateSigleRoleByGroupId(Long.valueOf(groupIds[i]), role2groupMap, hadSynRoleImport);
                }
                // 处理冗余数据
                for (String key : role2groupMap.keySet()) {
                    if (hadSynRoleImport.contains(key)) {
                        continue;
                    }
                    SysOrgGroup group = role2groupMap.get(key);
                    if (!group.getFdIsAvailable()) {
                        continue;
                    }
                    log("删除多余的数据：" + group.getFdName());
                    logger.warn("删除多余的数据：" + group.getFdName());
                    group.setFdIsAvailable(false);
                    sysOrgPostService.update(group);
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
                    hadSynRoleImport.add(SynchroOrgDing2EkpUtil.importInfoPre + "_role_"
                            + role.getInt("role_id"));
                    // 角色
                    if (role2groupMap.containsKey(SynchroOrgDing2EkpUtil.importInfoPre + "_role_" + role.getInt("role_id"))) {
                        // 修改群组角色
                        logger.debug("--修改角色(群组)名称---");
                        SysOrgGroup group = role2groupMap.get(SynchroOrgDing2EkpUtil.importInfoPre
                                + "_role_" + role.getInt("role_id"));
                        group.setFdName(role.getString("role_name"));
                        group.setFdIsAvailable(true);
                        group.setFdAlterTime(new Date());
                        group.setFdMembers(persons);
                        sysOrgGroupService.update(group);
                    } else {
                        logger.debug("----新增角色（群组）----");
                        SysOrgGroup group = new SysOrgGroup();
                        group.setFdName(role.getString("role_name"));
                        group.setFdImportInfo(SynchroOrgDing2EkpUtil.importInfoPre + "_role_"
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
                        sysOrgGroupService.add(group);
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
                        hqlInfo.setParameter("info", SynchroOrgDing2EkpUtil.importInfoPre + "_role_%");
                        hqlInfo.setOrderBy("fdIsAvailable");
                        List<SysOrgPost> postList = sysOrgPostService
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
                            if (role2postMap.containsKey(SynchroOrgDing2EkpUtil.importInfoPre
                                    + "_role_" + role.getInt("role_id"))) {
                                ekpAllRoleImport.remove(SynchroOrgDing2EkpUtil.importInfoPre
                                        + "_role_" + role.getInt("role_id"));
                                // 修改岗位角色
                                logger.debug("--修改角色名称---");
                                SysOrgPost post = role2postMap.get(SynchroOrgDing2EkpUtil.importInfoPre
                                        + "_role_" + role.getInt("role_id"));
                                post.setFdName(role.getString("role_name"));
                                post.setFdIsAvailable(true);
                                post.setFdAlterTime(new Date());
                                post.setFdPersons(persons);
                                sysOrgPostService.update(post);
                            } else {
                                logger.debug("----新增岗位角色----");
                                SysOrgPost post = new SysOrgPost();
                                post.setFdName(role.getString("role_name"));
                                post.setFdImportInfo(SynchroOrgDing2EkpUtil.importInfoPre + "_role_"
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
                                sysOrgPostService.add(post);
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
                            sysOrgPostService.update(post);
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
        }
    }

    private void setRolePersons(List<SysOrgPerson> persons, Long role_id) {
        boolean hasMore = false;
        Long count = 0L;
        do {
            try {
                hasMore = false;
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
                                OmsRelationModel model = SynchroOrgDing2EkpUtil.getOmsRelationModel(
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
                hasMore = false;
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
            List<ThirdDingRoleCateMapp> cateMappList = thirdDingRoleCateMappService
                    .findList(new HQLInfo());
            Map<String, String> cate_mapp = new HashMap<String, String>();
            for (ThirdDingRoleCateMapp mapp : cateMappList) {
                String groupId = mapp.getFdGroupId();
                String cateId = mapp.getFdEkpCateId();
                cate_mapp.put(cateId, groupId);
            }
            String[] cateIdArray = new String[cate_mapp.size()];
            cateIdArray = cate_mapp.keySet().toArray(cateIdArray);
            List<SysOrgRoleConfCate> cateList = sysOrgRoleConfCateService
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
                    sysOrgRoleConfCateService.update(cate);
                    logger.debug("更新角色分类，ID：{},名称：{}", cate.getFdId(),
                            cate.getFdName());
                } else {
                    SysOrgRoleConfCate cate = new SysOrgRoleConfCate();
                    String cateId = IDGenerator.generateID();
                    cate.setFdId(cateId);
                    cate.setFdCreateTime(new Date());
                    cate.setFdName(groupName);
                    sysOrgRoleConfCateService.add(cate);
                    logger.debug("新增角色分类，ID：{},名称：{}", cate.getFdId(),
                            cate.getFdName());
                    ThirdDingRoleCateMapp mapp = new ThirdDingRoleCateMapp();
                    mapp.setFdEkpCateId(cateId);
                    mapp.setFdGroupId(groupId);
                    thirdDingRoleCateMappService.add(mapp);
                    cate_ekp.put(groupId, cate);
                }
            }
            // 删除分类
            try {
                Set<String> ekpGroupIds = cate_ekp.keySet();
                ekpGroupIds.removeAll(Arrays.asList(roleGroupIds));
                for (String groupId : ekpGroupIds) {
                    String fdId = cate_ekp.get(groupId).getFdId();
                    List<SysOrgRoleConf> list = sysOrgRoleConfService
                            .findList("fdRoleConfCate.fdId = '" + fdId + "'", null);
                    for (SysOrgRoleConf conf : list) {
                        conf.setFdIsAvailable(false);
                        conf.setFdRoleConfCate(null);
                        sysOrgRoleConfService.update(conf);
                    }
                    sysOrgRoleConfCateService.delete(fdId);
                    thirdDingRoleCateMappService.deleteByCateId(fdId);
                    logger.debug("删除角色分类，ID：{}", fdId);
                }
            } catch (Exception e) {
                logger.warn("角色分类删除失败，可能原因是分类下存在角色线配置！请手动处理：" + e.getMessage());
                log("角色分类删除失败，可能原因是分类下存在角色线配置！请手动处理：" + e.getMessage());
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
        ThirdDingRoleCateMapp mapp = thirdDingRoleCateMappService
                .findByGroupId(groupId);
        if (mapp == null) {
            return null;
        }
        SysOrgRoleConfCate cate = (SysOrgRoleConfCate) sysOrgRoleConfCateService
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
                ThirdDingRolelineMapp mapp = thirdDingRolelineMappService
                        .findByDingRole(role_id);
                if (mapp == null) {
                    String roleConfId = IDGenerator.generateID();
                    conf = addSysOrgRoleConf(roleConfId, role_name, cate);
                    addRolelineMapp(role_id + "", roleConfId);
                    logger.debug("找不到映射关系，新增角色线配置，ID：{}，名称：{}", roleConfId,
                            role_name);
                } else {
                    String ekpRoleId = mapp.getFdEkpRoleId();
                    conf = (SysOrgRoleConf) sysOrgRoleConfService
                            .findByPrimaryKey(ekpRoleId, null, true);
                    if (conf == null) {
                        conf = addSysOrgRoleConf(ekpRoleId, role_name, cate);
                        logger.debug("找不到角色线配置，执行新增，ID：{}，名称：{}", ekpRoleId,
                                role_name);
                    } else {
                        conf.setFdIsAvailable(true);
                        conf.setFdName(role_name);
                        conf.setFdRoleConfCate(cate);
                        sysOrgRoleConfService.update(conf);
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
        List<ThirdDingRolelineMapp> mappList = thirdDingRolelineMappService
                .findList(new HQLInfo());
        for (ThirdDingRolelineMapp mapp : mappList) {
            String dingRoleId = mapp.getFdDingRoleId();
            if (!dingRoleIds.contains(dingRoleId)) {
                String ekpRoleId = mapp.getFdEkpRoleId();
                SysOrgRoleConf conf = (SysOrgRoleConf) sysOrgRoleConfService
                        .findByPrimaryKey(ekpRoleId, null, true);
                if (conf != null) {
                    conf.setFdIsAvailable(false);
                    sysOrgRoleConfService.update(conf);
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

    private List<SysOrgElement> getRootDepts() throws Exception {
        HQLInfo info = new HQLInfo();
        info.setWhereBlock(
                "(fdOrgType=1 or fdOrgType=2) and hbmParent=null and fdIsAvailable=:avail");
        info.setParameter("avail", true);
        return sysOrgElementService.findList(info);
    }

    private void synchroRoleLines(Long role_id) throws Exception {
        logger.debug("同步角色线成员，钉钉角色ID：{}", role_id);
        boolean hasMore = false;
        Long count = 0L;
        List<SysOrgElement> rootDepts = getRootDepts();
        ThirdDingRolelineMapp mapp = (ThirdDingRolelineMapp) thirdDingRolelineMappService
                .findByDingRole(role_id + "");
        if (mapp == null) {
            throw new Exception("找不到角色线配置，role_id:" + role_id);
        }
        SysOrgRoleConf conf = (SysOrgRoleConf) sysOrgRoleConfService
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
        OmsRelationModel model = SynchroOrgDing2EkpUtil.getOmsRelationModel(
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
                    model = SynchroOrgDing2EkpUtil.getOmsRelationModel(
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
        sysOrgRoleLineService.add(line);
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
        sysOrgRoleConfService.add(conf);
        return conf;
    }

    private void addRolelineMapp(String role_id,
                                 String roleConfId) throws Exception {
        ThirdDingRolelineMapp mapp = new ThirdDingRolelineMapp();
        mapp.setFdDingRoleId(role_id + "");
        mapp.setFdEkpRoleId(roleConfId);
        thirdDingRolelineMappService.add(mapp);
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
            NativeQuery query_update = sysOrgRoleLineService.getBaseDao()
                    .getHibernateSession().createNativeQuery(
                            "update sys_org_role_line set fd_parent_id = null where fd_role_line_conf_id='"
                                    + roleConfId + "'");
            NativeQuery query_delete = sysOrgRoleLineService.getBaseDao()
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
        NativeQuery query_update = sysOrgRoleLineService.getBaseDao()
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
        NativeQuery query_delete = sysOrgRoleLineService.getBaseDao()
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
        sysOrgRoleConfCateService.add(cate);
    }

    private void updateRoleConfCate(String groupId, String groupName)
            throws Exception {
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            ThirdDingRoleCateMapp mapp = thirdDingRoleCateMappService
                    .findByGroupId(groupId);
            if (mapp != null) {
                String cateId = mapp.getFdEkpCateId();
                SysOrgRoleConfCate cate = (SysOrgRoleConfCate) sysOrgRoleConfCateService
                        .findByPrimaryKey(cateId, null, true);
                if (cate == null) {
                    addRoleConfCate(cateId, groupName);
                } else {
                    cate.setFdName(groupName);
                    sysOrgRoleConfCateService.update(cate);
                }
            } else {
                String cateId = IDGenerator.generateID();
                addRoleConfCate(cateId, groupName);
                logger.debug("新增角色分类，ID：{},名称：{}", cateId,
                        groupName);
                mapp = new ThirdDingRoleCateMapp();
                mapp.setFdEkpCateId(cateId);
                mapp.setFdGroupId(groupId);
                thirdDingRoleCateMappService.add(mapp);
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
            ThirdDingRoleCateMapp cateMapp = thirdDingRoleCateMappService
                    .findByGroupId(groupId);
            SysOrgRoleConfCate cate = null;
            if (cateMapp != null) {
                String cateId = cateMapp.getFdEkpCateId();
                cate = (SysOrgRoleConfCate) sysOrgRoleConfCateService
                        .findByPrimaryKey(cateId, null, true);
            }
            ThirdDingRolelineMapp mapp = (ThirdDingRolelineMapp) thirdDingRolelineMappService
                    .findByDingRole(roleId);
            if (mapp != null) {
                String ekpRoleId = mapp.getFdEkpRoleId();
                SysOrgRoleConf conf = (SysOrgRoleConf) sysOrgRoleConfService
                        .findByPrimaryKey(ekpRoleId, null, true);
                if (conf == null) {
                    addSysOrgRoleConf(ekpRoleId, roleName, cate);
                } else {
                    conf.setFdName(roleName);
                    conf.setFdIsAvailable(true);
                    sysOrgRoleConfService.update(conf);
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
            ThirdDingRolelineMapp mapp = (ThirdDingRolelineMapp) thirdDingRolelineMappService
                    .findByDingRole(roleId + "");
            if (mapp != null) {
                String ekpRoleId = mapp.getFdEkpRoleId();
                SysOrgRoleConf conf = (SysOrgRoleConf) sysOrgRoleConfService
                        .findByPrimaryKey(ekpRoleId, null, true);
                if (conf != null) {
                    conf.setFdIsAvailable(false);
                    sysOrgRoleConfService.update(conf);
                }
            }
        }
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);

    private void log(String msg) {
        logger.debug("【钉钉接入组织架构到EKP】" + msg);
        if (this.jobContext != null) {
            String time = sdf.format(Calendar.getInstance().getTime());
            jobContext.logMessage(time + "  " + msg);
        }
    }


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
                                        SynchroOrgDing2EkpUtil.importInfoPre + "_role_"
                                                + role.getInt("id"));
                                SysOrganizationStaffingLevel staffing = (SysOrganizationStaffingLevel) sysOrganizationStaffingLevelService
                                        .findFirstOne(hqlInfo);
                                if (staffing != null) {
                                    // 修改岗位角色
                                    logger.debug("--修改角色(群组)名称---");
                                    staffing.setFdName(role.getString("name"));
                                    staffing.setDocAlterTime(new Date());
                                    sysOrganizationStaffingLevelService
                                            .update(staffing);
                                } else {
                                    // 新增
                                    logger.debug("----新增角色（群组）----");
                                    staffing = new SysOrganizationStaffingLevel();
                                    staffing.setFdName(
                                            role.getString("name"));
                                    staffing.setFdImportInfo(
                                            SynchroOrgDing2EkpUtil.importInfoPre + "_role_"
                                                    + role.getInt("id"));
                                    staffing.setDocCreateTime(new Date());
                                    staffing.setFdLevel(1);
                                    staffing.setFdIsDefault(false);
                                    staffing.setFdDescription("来自钉钉角色同步");
                                    staffing.setFdDescription(
                                            "来自钉钉角色：" + dingRoleMap
                                                    .get(roleType)
                                                    .getString("name"));
                                    sysOrganizationStaffingLevelService
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
                    hqlInfo.setParameter("info", SynchroOrgDing2EkpUtil.importInfoPre
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
                            SynchroOrgDing2EkpUtil.importInfoPre + "_role_" + LabelIdList.getInt(0));
                    List<SysOrganizationStaffingLevel> staffingList = sysOrganizationStaffingLevelService
                            .findList(hqlInfo);
                    if (staffingList != null && staffingList.size() > 0) {
                        for (SysOrganizationStaffingLevel staff : staffingList) {
                            sysOrganizationStaffingLevelService
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
            hqlInfo.setParameter("info", SynchroOrgDing2EkpUtil.importInfoPre
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
                    sysOrgPostService.update(org);
                }
            } else {
                // 新增
                if (orgType == 4) {
                    logger.debug("----新增岗位角色----");
                    SysOrgPost org = new SysOrgPost();
                    org.setFdName(role.getString("name"));
                    org.setFdImportInfo(
                            SynchroOrgDing2EkpUtil.importInfoPre + "_role_"
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
                    sysOrgPostService.add(org);
                } else if (orgType == 16) {
                    logger.debug("----新增群组角色----");
                    SysOrgGroup org = new SysOrgGroup();
                    org.setFdName(role.getString("name"));
                    org.setFdImportInfo(
                            SynchroOrgDing2EkpUtil.importInfoPre + "_role_"
                                    + role.getInt("id"));
                    org.setFdOrgType(orgType);
                    org.setFdCreateTime(new Date());
                    org.setFdIsAvailable(true);
                    org.setFdIsBusiness(true);
                    org.setFdIsAbandon(false);
                    org.setFdMemo("来自钉钉角色：" + dingRoleMap
                            .get("group").getString("name"));
                    sysOrgGroupService.add(org);
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
            OmsRelationModel model = SynchroOrgDing2EkpUtil.getOmsRelationModel(
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
                            SynchroOrgDing2EkpUtil.importInfoPre + "_role_" + labelId);
                    hqlInfo.setParameter("fdIsAvailable", true);
                    List<SysOrgGroup> list = sysOrgGroupService
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
                        sysOrgGroupService.update(group);
                    }
                } else if ("post".equals(key)) {
                    hqlInfo.setWhereBlock(
                            "fdOrgType=4 and fdImportInfo like :info and fdIsAvailable=:fdIsAvailable");
                    hqlInfo.setParameter("info",
                            SynchroOrgDing2EkpUtil.importInfoPre + "_role_" + labelId);
                    hqlInfo.setParameter("fdIsAvailable", true);
                    List<SysOrgPost> list = sysOrgPostService
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
                        sysOrgPostService.update(post);
                    }
                } else if ("staffing".equals(key)) {
                    hqlInfo.setWhereBlock(
                            "fdImportInfo like :info");
                    hqlInfo.setParameter("info",
                            SynchroOrgDing2EkpUtil.importInfoPre + "_role_" + labelId);
                    List<SysOrganizationStaffingLevel> staffingList = sysOrganizationStaffingLevelService
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
                        sysOrganizationStaffingLevelService
                                .update(staffing);
                    }
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

    @Override
    public void synDingRoles(SysQuartzJobContext context) {
        SynchroInModel model = new SynchroInModel();
        try {
            getComponentLockService().tryLock(model, "omsIn");
            this.jobContext = context;
            updateDingRoleSyn();
            getComponentLockService().unLock(model);
        } catch (ConcurrencyException e) {
            logger.error("同步定时任务在执行，禁止执行角色同步任务");
            if (context != null) {
                context.logMessage("组织机构同步到EKO的定时任务正在执行，禁止执行角色同步任务");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (context != null) {
                context.logError(e.getMessage(), e);
            }
            getComponentLockService().unLock(model);
        } finally {

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
                                SynchroOrgDing2EkpUtil.importInfoPre + "_role_" + role.getInt("id"));
                        updatePostRole(element.getString("userid"), post);
                    }
                    if ("group".equals(key)) {
                        SysOrgGroup group = role2groupMap.get(
                                SynchroOrgDing2EkpUtil.importInfoPre + "_role_" + role.getInt("id"));
                        updateGroupRole(element.getString("userid"), group);
                    }
                    if ("staffing".equals(key)) {
                        SysOrganizationStaffingLevel staffing = role2staffingMap
                                .get(SynchroOrgDing2EkpUtil.importInfoPre + "_role_"
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
            OmsRelationModel omsRelationModel = SynchroOrgDing2EkpUtil.getOmsRelationModel(dingUserId);
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
            SysOrganizationStaffingLevel _staffing = (SysOrganizationStaffingLevel) sysOrganizationStaffingLevelService
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
            OmsRelationModel omsRelationModel = SynchroOrgDing2EkpUtil.getOmsRelationModel(dingUserId);
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
            SysOrganizationStaffingLevel _staffing = (SysOrganizationStaffingLevel) sysOrganizationStaffingLevelService
                    .findByPrimaryKey(staffing.getFdId());
            _staffing.setFdPersons(persons);
            sysOrganizationStaffingLevelService.getBaseDao()
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
            OmsRelationModel omsRelationModel = SynchroOrgDing2EkpUtil.getOmsRelationModel(dingUserId);
            String ekpUserId = omsRelationModel.getFdEkpId();
            SysOrgPerson user = (SysOrgPerson) sysOrgPersonService
                    .findByPrimaryKey(ekpUserId);
//			addUser2Gruop(user, (SysOrgGroup) getSysOrgGroupService()
//					.findByPrimaryKey(group.getFdId()));
            SysOrgGroup _group = (SysOrgGroup) sysOrgGroupService
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
            sysOrgGroupService.getBaseDao().getHibernateSession().update(_group);
            logger.debug("------群组添加结束------" + dingUserId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


    private void removePostRole(String dingUserId, SysOrgPost post) {
        if (dingUserId == null || post == null) {
            return;
        }
        try {
            OmsRelationModel omsRelationModel = SynchroOrgDing2EkpUtil.getOmsRelationModel(dingUserId);
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
            SysOrgPost _post = (SysOrgPost) sysOrgPostService
                    .findByPrimaryKey(post.getFdId());
            _post.setFdPersons(persons);
            sysOrgPostService.getBaseDao().getHibernateSession()
                    .update(_post);

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
            OmsRelationModel omsRelationModel = SynchroOrgDing2EkpUtil.getOmsRelationModel(dingUserId);
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

            SysOrgPost _post = (SysOrgPost) sysOrgPostService
                    .findByPrimaryKey(post.getFdId());
            _post.setFdPersons(persons);
            sysOrgPostService.getBaseDao().getHibernateSession()
                    .update(_post);
            logger.debug("---=添加岗位角色结束-----" + dingUserId + " ->"
                    + post.getFdName());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
