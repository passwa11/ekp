package com.landray.kmss.third.ding.oms;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.component.locker.interfaces.ConcurrencyException;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgElementHideRange;
import com.landray.kmss.sys.organization.model.SysOrgElementRange;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.*;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingOmsErrorService;
import com.landray.kmss.third.ding.service.IThirdDingOmsPostService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.TransactionStatus;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SynchroOrg2DingImpV2 implements SynchroOrg2Ding, DingConstant,
        SysOrgConstant {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SynchroOrg2DingImpV2.class);

    private SysQuartzJobContext jobContext = null;

    private DingApiService dingApiService = null;

    private long syncTime = 0L;

    /**
     * 组织映射关系，key为ekp组织id，value为钉钉组织id
     */
    private Map<String, String> relationMap = null;

    /**
     * 钉钉账号类型关系，key为钉钉组织id，value为类型
     */
    private Map<String, String> accountTypeMap = null;

    /**
     * 部门领导关系，key为领导的id，value为部门id列表
     */
    private Map<String, List<String>> deptLeaderMap = null;

    /**
     * 需要同步的顶级组织，key为组织fdId，value为组织层级id
     */
    private Map<String, String> syncRootIdsMap = new HashMap<>();

    //人员同步是否全部成功
    private boolean personSyncSuccess = true;

    //保存同步失败的记录
    private List<ThirdDingOmsError> syncErrorRecords;

    private IBaseService sysZonePersonInfoService;

    public IBaseService getSysZonePersonInfoService() {
        if (sysZonePersonInfoService == null) {
            sysZonePersonInfoService = (IBaseService) SpringBeanUtil
                    .getBean("sysZonePersonInfoService");
        }
        return sysZonePersonInfoService;
    }

    private IOmsRelationService omsRelationService;

    public void setOmsRelationService(IOmsRelationService omsRelationService) {
        this.omsRelationService = omsRelationService;
    }

    private ISysOrgElementService sysOrgElementService;

    public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
        this.sysOrgElementService = sysOrgElementService;
    }

    private ISysOrgPersonService sysOrgPersonService;

    public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
        this.sysOrgPersonService = sysOrgPersonService;
    }

    private IThirdDingOmsPostService thirdDingOmsPostService;

    public void setThirdDingOmsPostService(IThirdDingOmsPostService thirdDingOmsPostService) {
        this.thirdDingOmsPostService = thirdDingOmsPostService;
    }

    private ThreadPoolTaskExecutor taskExecutor;

    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    private IThirdDingOmsErrorService thirdDingOmsErrorService;

    public void setThirdDingOmsErrorService(IThirdDingOmsErrorService thirdDingOmsErrorService) {
        this.thirdDingOmsErrorService = thirdDingOmsErrorService;
    }

    private ISysAppConfigService sysAppConfigService;

    public void setSysAppConfigService(ISysAppConfigService sysAppConfigService) {
        this.sysAppConfigService = sysAppConfigService;
    }

    private IComponentLockService componentLockService = null;

    public void setComponentLockService(IComponentLockService componentLockService) {
        this.componentLockService = componentLockService;
    }

    private String getDingRootDeptId() {
        return StringUtil.isNull(DingConfig.newInstance().getDingDeptid()) ? "1"
                : DingConfig.newInstance().getDingDeptid().trim();
    }

    private boolean checkNeedSynchro2Ding() {
        String temp = "";
        if (!"true".equals(DingConfig.newInstance().getDingEnabled())) {
            temp = "钉钉集成已经关闭，故不同步数据";
            logger.info(temp);
            jobContext.logMessage(temp);
            return false;
        }
        if (StringUtil.isNotNull(DingConfig.newInstance().getSyncSelection())) {
            if (!"1".equals(DingConfig.newInstance().getSyncSelection())) {
                temp = "钉钉集成-通讯录配置-同步选择-从本系统同步到钉钉未开启，故不同步数据";
                logger.info(temp);
                jobContext.logMessage(temp);
                return false;
            }
        } else {
            if (!"true".equals(DingConfig.newInstance().getDingOmsOutEnabled())) {
                temp = "钉钉组织架构接出已经关闭，故不同步数据";
                logger.debug(temp);
                jobContext.logMessage(temp);
                return false;
            }
        }
        return true;
    }

    @Override
    public void triggerSynchro(SysQuartzJobContext context) {
        this.jobContext = context;
        // 判断是否启用了ekp到钉钉的组织同步
        if (!checkNeedSynchro2Ding()) {
            return;
        }
        Synchro2DingLock lock = new Synchro2DingLock();
        try {
            componentLockService.tryLock(lock, "synchro2Ding");
            doSynchro(context);
            componentLockService.unLock(lock);
        } catch (ConcurrencyException e) {
            context.logError(
                    "已经有同步任务正在执行，如果是由于同步过程中重启等原因导致定时任务被锁，需到“后台配置”->“应用中心”->“机制”->“锁机制”中释放锁",
                    e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            componentLockService.unLock(lock);
        }
    }

    private void doSynchro(SysQuartzJobContext context) {
        try {
            DingOmsConfig dingOmsConfig = new DingOmsConfig();
            String lastUpdateTime = dingOmsConfig.getLastUpdateTime();
            String temp = "==========开始同步(同步时间戳：" + lastUpdateTime + ")钉钉组织数据===============";
            logger.info(temp);
            context.logMessage(temp);
            long alltime = System.currentTimeMillis();

            // 初始化数据
            long caltime = System.currentTimeMillis();
            init();
            temp = "初始化数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
            logger.info(temp);
            context.logMessage(temp);

            // 获取EKP组织数据
            caltime = System.currentTimeMillis();
            Set<String> syncDepts = new HashSet<>();
            Set<String> syncPersons = new HashSet<>();
            getSyncData(syncDepts, syncPersons);
            temp = "获取EKP组织数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);
            context.logMessage(temp);

            // 添加钉钉的部门数据
            // 报错本次新增的部门ID
            Set<String> addDeptIds = new HashSet<>();
            Set<String> delDeptIds = new HashSet<>();
            caltime = System.currentTimeMillis();
            handleAddDept(syncDepts, addDeptIds, delDeptIds);
            temp = "添加钉钉的部门数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);
            context.logMessage(temp);

            // 添加钉钉的人员数据
            caltime = System.currentTimeMillis();
            handlePersonSync(syncPersons);
            temp = "添加钉钉的人员数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);
            context.logMessage(temp);

            // 更新钉钉部门层级数据
            caltime = System.currentTimeMillis();
            handleUpdateDept(syncDepts, addDeptIds);
            temp = "更新钉钉部门层级和部门主管数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);
            context.logMessage(temp);

            // 先迁移人员，后删除部门，部门中有人员，不能删除
            caltime = System.currentTimeMillis();
            handleDelDept(delDeptIds);
            temp = "先迁移人员，后删除部门，如果部门中有人员，不能删除数据。耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);
            context.logMessage(temp);

            // 更新钉钉钉钉管理员数据
            caltime = System.currentTimeMillis();
            adminHandle();
            temp = "更新钉钉钉钉管理员数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);
            context.logMessage(temp);

            terminate();
            temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000;
            logger.debug(temp);
            context.logMessage(temp);
        } catch (Exception ex) {
            logger.error("钉钉组织架构同步任务失败：", ex);
            if (context != null) {
                context.logError(ex);
            }
        } finally {
            relationMap = null;
            deptLeaderMap = null;
        }
    }

    /**
     * 获取所有根组织
     *
     * @throws Exception
     */
    private Set<String> findRootDepts() throws Exception {
        if (syncRootIdsMap != null && !syncRootIdsMap.isEmpty()) {
            return syncRootIdsMap.keySet();
        }
        // 查询所有根机构
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock("fdId");
        hqlInfo.setWhereBlock(" fd_parentid = null and fd_is_available = 1 and fd_is_business = 1 and (fd_org_type = 1 or fd_org_type = 2)");
        List<String> depts = sysOrgElementService.findList(hqlInfo);
        return new HashSet<>(depts);
    }

    private void handlePersonSync(Set<String> personIds) throws Exception {
        if (personIds == null || personIds.isEmpty()) {
            logger.debug("本次共同步总人员数据: 0 条");
            return;
        }
        Map<String, Map<String, String>> ppMap = null;
        if (isMulDeptEnabled() || isPositionSyncEnabled()) {
            ppMap = buildOmsPostMap();
        }
        int threadCount = Integer.parseInt(new DingOmsConfig().getSyncThreadSize());
        int remainder = personIds.size() % threadCount;
        int rowsize = personIds.size() / threadCount;
        if (remainder != 0) {
            threadCount++;
        }
        List<String> allpersons = new ArrayList<>(personIds);
        logger.debug("人员总数据:" + allpersons.size() + "条,将分成" + threadCount + "次人员分批同步,每次" + rowsize + "条");
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        List<String> temppersons = null;
        for (int i = 0; i < threadCount; i++) {
            //logger.debug("执行第" + (i + 1) + "批");
            if (i == (threadCount - 1)) {
                temppersons = allpersons.subList(rowsize * i, personIds.size());
            } else {
                temppersons = allpersons.subList(rowsize * i, rowsize * (i + 1));
            }
            taskExecutor.execute(new PersonRunner(temppersons, countDownLatch, ppMap));
        }
        try {
            countDownLatch.await(3, TimeUnit.HOURS);
        } catch (InterruptedException exc) {
            logger.error(exc.getMessage(), exc);
        }
        logger.debug("本次共同步总人员数据:" + personIds.size() + "条");
        log("本次共同步总人员数据:" + personIds.size() + "条");
    }

    private void init() throws Exception {
        dingApiService = DingUtils.getDingApiService();
        syncErrorRecords = new ArrayList<>();
        relationMap = new HashMap<>();
        accountTypeMap = new HashMap<>();
        // 关系映射表初始化
        List list = omsRelationService.findValue("fdEkpId, fdAppPkId, fdAccountType", "fdAppKey='" + getAppKey() + "'", null);
        for (int i = 0; i < list.size(); i++) {
            Object[] values = (Object[]) list.get(i);
            relationMap.put((String) values[0], (String) values[1]);
            if (StringUtil.isNotNull((String) values[1])) {
                accountTypeMap.put((String) values[1], (String) values[2]);
            }
        }
        initAllSyncRootIdsMap();
        // 异常数据处理
        updateErrorData();
        // 岗位关系初始化
        buildOmsPostMap();
        // 部门主管的数据缓存
        initDeptLeaderMap();
    }

    private void initDeptLeaderMap() throws Exception {
        deptLeaderMap = new HashMap<>();
        List<SysOrgElement> leaders = sysOrgElementService
                .findList("fdOrgType in (1,2) and hbmThisLeader.fdId is not null", null);
        List<String> templeaders = null;
        for (SysOrgElement leader : leaders) {
            String tempid = leader.getHbmThisLeader().getFdId();
            if (deptLeaderMap.containsKey(tempid)) {
                templeaders = deptLeaderMap.get(tempid);
            } else {
                templeaders = new ArrayList<>();
            }
            templeaders.add(leader.getFdId());
            deptLeaderMap.put(tempid, templeaders);
        }
    }

    /**
     * 构建一人多部门的关系
     *
     * @return
     * @throws Exception
     */
    private Map<String, Map<String, String>> buildOmsPostMap() throws Exception {
        Session session = thirdDingOmsPostService.getBaseDao().getHibernateSession();
        String getPostSql = "select pp.fd_personid,pp.fd_postid,ele.fd_name,ele.fd_parentid from sys_org_post_person pp,sys_org_element ele where pp.fd_postid=ele.fd_id";
        String postOrder = DingConfig.newInstance().getOrg2dingPositionOrder();
        if (StringUtil.isNotNull(postOrder) && "postOrder".equals(postOrder)) {
            getPostSql = "select pp.fd_personid,pp.fd_postid,ele.fd_name,ele.fd_parentid from sys_org_post_person pp,sys_org_element ele where pp.fd_postid=ele.fd_id order by case when ele.fd_order is null then 1 else 0 end asc,ele.fd_order ASC";
        }
        Map<String, Map<String, String>> ppMap = new HashMap<>();
        // 岗位数据的缓存
        List<Object[]> personPosts = session
                .createSQLQuery(getPostSql)
                .list();
        Map<String, String> tempMap = null;
        for (Object[] pps : personPosts) {
            if (pps[0] != null && pps[1] != null && pps[2] != null) {
                // 以人为角度构建的人与岗位关系
                if (ppMap.containsKey(pps[0].toString())) {
                    tempMap = ppMap.get(pps[0].toString());
                    tempMap.put("postids", tempMap.get("postids") + ";" + pps[1].toString());
                    tempMap.put("names", tempMap.get("names") + ";" + pps[2].toString());
                    if (pps[3] != null) {
                        tempMap.put("parentids", tempMap.get("parentids") + ";" + pps[3].toString());
                    }
                } else {
                    tempMap = new HashMap<>();
                    tempMap.put("postids", pps[1].toString());
                    tempMap.put("names", pps[2].toString());
                    if (pps[3] == null) {
                        tempMap.put("parentids", "");
                    } else {
                        tempMap.put("parentids", pps[3].toString());
                    }
                    ppMap.put(pps[0].toString(), tempMap);
                }
            }
        }
        return ppMap;
    }

    private String getDeptParentId(SysOrgElement dept, boolean isAdd) {
        String parentDeptSynWay = DingConfig.newInstance().getOrg2dingDeptParentDeptSynWay();
        logger.debug("parentDeptSynWay:" + parentDeptSynWay);
        if (StringUtil.isNull(parentDeptSynWay) || "syn".equals(parentDeptSynWay)
                || (isAdd && "addSyn".equals(parentDeptSynWay))) {
            String parentId = getDingRootDeptId();
            if (dept.getFdParent() != null) {
                parentId = relationMap.get(dept.getFdParent().getFdId());
            }
            return parentId;
        }
        return null;
    }

    private String getDeptManagerUseridList(SysOrgElement dept, boolean isAdd) throws Exception {
        String dingDeptLeaderEnabled = DingConfig.newInstance().getDingDeptLeaderEnabled();
        if (StringUtil.isNotNull(dingDeptLeaderEnabled)) {// 旧开关方式
            logger.debug("旧方式同步部门主管");
            if ("true".equals(dingDeptLeaderEnabled)) {
                String userIdList = getDeptManagerUseridList(dept);
                logger.debug("ManagerUseridList:" + userIdList);
                return userIdList;
            }
        } else {
            logger.debug("新配置方式同步部门主管");
            String o2d_deptManagerWay = DingConfig.newInstance()
                    .getOrg2dingDeptDeptManagerSynWay();
            logger.debug("o2d_deptManagerWay:" + o2d_deptManagerWay);
            if ("syn".equals(o2d_deptManagerWay) || (
                    isAdd && "addSyn".equalsIgnoreCase(o2d_deptManagerWay))) {
                String userIdList = getDeptManagerUseridList(dept);
                logger.debug("ManagerUseridList:" + userIdList);
                return userIdList;
            }
        }
        return null;
    }

    private String updateDept(SysOrgElement dept, Set<String> addDeptIds) throws Exception {
        JSONObject deptObj = new JSONObject();
        boolean isAdd = false; // 部门是否本次同步新建的
        String did = relationMap.get(dept.getFdId());
        deptObj.accumulate("id", did);
        logger.debug("更新部门：" + dept.getFdName() + " did:" + did);
        if (addDeptIds.contains(dept.getFdId())) {
            isAdd = true;
        }
        String name = getName(dept, isAdd);
        if (name != null) {
            deptObj.accumulate("name", DingUtil.getString(name, 64));
        }
        // 上级部门
        String parentId = getDeptParentId(dept, isAdd);
        if (StringUtil.isNotNull(parentId)) {
            deptObj.accumulate("parentid", parentId);
        }
        Boolean createDeptGroup = isCreateDeptGroup(dept, isAdd);
        if (createDeptGroup != null && createDeptGroup == true) {
            deptObj.accumulate("createDeptGroup", true);
            Boolean groupContainSubDept = getGroupContainSubDept(dept, true);
            if (groupContainSubDept != null && groupContainSubDept == true) {
                deptObj.accumulate("groupContainSubDept", true);
            } else {
                deptObj.accumulate("groupContainSubDept", false);
            }
        } else {
            deptObj.accumulate("createDeptGroup", false);
        }
        // 是否同步部门主管
        String deptManagerUserIdList = getDeptManagerUseridList(dept, isAdd);
        if (StringUtil.isNotNull(parentId)) {
            deptObj.accumulate("deptManagerUseridList", deptManagerUserIdList);
        }
        Integer order = getDeptOrderValue(dept, isAdd);
        if (order != null) {
            deptObj.accumulate("order", order);
        }
        // 设置人员查看范围
        setRange(dept, deptObj);
        // 设置隐藏可见范围
        setHideRange(dept, deptObj);
        logger.debug("更新部门信息:" + deptObj.toString());
        return dingApiService.departUpdate(deptObj);
    }

    /**
     * 设置组织管理员可见
     *
     * @param dept
     * @param jodept
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/10/27 5:46 下午
     */
    private void setOrgAuthAdminRange(SysOrgElement dept, JSONObject jodept) {
        List<SysOrgElement> list = dept.getAuthElementAdmins();
        if (CollectionUtils.isNotEmpty(list)) {
            StringBuilder deptPermits = new StringBuilder();
            StringBuilder userPermits = new StringBuilder();
            for (SysOrgElement elem : list) {
                String dingId = relationMap.get(elem.getFdId());
                if (elem.getFdOrgType() == ORG_TYPE_ORG || elem.getFdOrgType() == ORG_TYPE_DEPT) {
                    if (deptPermits.length() > 0) {
                        deptPermits.append("\\|");
                    }
                    deptPermits.append(dingId);
                } else if (elem.getFdOrgType() == ORG_TYPE_POST) {
                    // 岗位需要解析成个人
                    List<SysOrgElement> persons = elem.getFdPersons();
                    if (CollectionUtils.isNotEmpty(persons)) {
                        for (SysOrgElement person : persons) {
                            if (userPermits.length() > 0) {
                                userPermits.append("\\|");
                            }
                            userPermits.append(relationMap.get(person.getFdId()));
                        }
                    }
                } else if (elem.getFdOrgType() == ORG_TYPE_PERSON) {
                    if (userPermits.length() > 0) {
                        userPermits.append("\\|");
                    }
                    userPermits.append(dingId);
                }
            }
            jodept.put("deptPermits", deptPermits.toString());
            jodept.put("userPermits", userPermits.toString());
        }
    }

    /**
     * 设置隐藏可见范围
     *
     * @param dept
     * @param jsonObject
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/10/11 8:50 上午
     */
    private void setHideRange(SysOrgElement dept, JSONObject jsonObject) throws Exception {

        //从初始化缓存中读取
        SysOrgElementHideRange fdHideRange = getDeptHideRange(dept);
        // 如果隐藏范围没有或者未打开隐藏设置并且为非生态组织则设置全部可见
        if (fdHideRange == null) {
            return;
        }
        jsonObject.put("deptHiding", String.valueOf(fdHideRange.getFdIsOpenLimit()));
        if (BooleanUtils.isTrue(fdHideRange.getFdIsOpenLimit())) {
            //设置组织管理员可见
            setOrgAuthAdminRange(dept, jsonObject);
            jsonObject.put("deptHiding", "true");
            StringBuilder deptPermits = new StringBuilder();
            StringBuilder userPermits = new StringBuilder();
            //部门隐藏后对部分人员可见
            if (1 == fdHideRange.getFdViewType()) {
                for (SysOrgElement elem : fdHideRange.getFdOthers()) {
                    String dingId = relationMap.get(elem.getFdId());
                    if (elem.getFdOrgType() == ORG_TYPE_ORG || elem.getFdOrgType() == ORG_TYPE_DEPT) {
                        if (deptPermits.length() > 0) {
                            deptPermits.append("\\|");
                        }
                        deptPermits.append(dingId);
                    } else if (elem.getFdOrgType() == ORG_TYPE_POST) {
                        // 岗位需要解析成个人
                        List<SysOrgElement> persons = elem.getFdPersons();
                        if (CollectionUtils.isNotEmpty(persons)) {
                            for (SysOrgElement person : persons) {
                                if (userPermits.length() > 0) {
                                    userPermits.append("\\|");
                                }
                                userPermits.append(relationMap.get(person.getFdId()));
                            }
                        }
                    } else if (elem.getFdOrgType() == ORG_TYPE_PERSON) {
                        if (userPermits.length() > 0) {
                            userPermits.append("\\|");
                        }
                        userPermits.append(dingId);
                    }
                }
            }
            Object deptPermitExist = jsonObject.get("deptPermits");
            if (deptPermitExist != null && !"".equals(deptPermitExist)) {
                if (deptPermits.length() > 0) {
                    deptPermits.append("\\|").append(deptPermitExist);
                } else {
                    deptPermits.append(deptPermitExist);
                }
            }
            Object userPermitExist = jsonObject.get("userPermits");
            if (userPermitExist != null && !"".equals(userPermitExist)) {
                if (userPermits.length() > 0) {
                    userPermits.append("\\|").append(userPermitExist);
                } else {
                    userPermits.append(userPermitExist);
                }
            }
            jsonObject.put("deptPermits", deptPermits.toString());
            jsonObject.put("userPermits", userPermits.toString());
        }

    }

    /**
     * 给钉钉用户添加部门，因为钉钉那边，部门领导必须在本部门下面
     *
     * @param personId
     * @param deptDid
     * @throws Exception
     */
    private void addDept2Person(String personId, String deptDid) throws Exception {
        JSONObject person = dingApiService
                .userGet(relationMap.get(personId), personId);
        if (person != null
                && person.containsKey("department")) {
            String dept = person.getString("department");
            JSONArray jas = JSONArray.fromObject(dept);
            logger.debug("jas:" + jas);
            if (!jas.contains(deptDid)) {
                logger.debug("添加人员到该部门下");
                jas.add(deptDid);
                JSONObject user = new JSONObject();
                user.accumulate("userid",
                        relationMap.get(personId));
                user.accumulate("department", jas);
                String retMsg = dingApiService.userUpdate(user);
                logger.debug(retMsg);
            }
        }
    }

    private String getDeptManagerUseridList(SysOrgElement element) throws Exception {
        StringBuilder userIdList = new StringBuilder();
        Set<String> leaderIds = new HashSet<>();
        if (element.getHbmThisLeader() != null) {
            SysOrgElement leader = element.getHbmThisLeader();
            if (leader.getFdOrgType() == 8) {
                leaderIds.add(leader.getFdId());
            } else {
                List<SysOrgPerson> persons = leader.getFdPersons();
                for (SysOrgPerson person : persons) {
                    leaderIds.add(person.getFdId());
                }
            }
            String deptDid = relationMap.get(element.getFdId());
            logger.debug("deptDid:" + deptDid);
            for (String personId : leaderIds) {
                if (!relationMap.containsKey(personId)) {
                    continue;
                }
                try {
                    logger.debug("添加" + element.getFdName() + "(" + deptDid
                            + ")的部门主管信息：" + relationMap.get(personId));
                    // 先判断该用户是否在该部门下 userUpdate
                    addDept2Person(personId, deptDid);
                    userIdList.append(relationMap.get(personId) + "|");
                } catch (Exception e) {
                    logger.error("添加部门主管过程中发生异常：" + element.getFdName());
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return userIdList.toString().length() > 0 ? userIdList.substring(0, userIdList.length() - 1)
                : userIdList.toString();
    }

    /**
     * 设置部门或机构的人员查看范围
     *
     * @param dept
     * @param jodept
     */
    private void setRange(SysOrgElement dept, JSONObject jodept) {
        // 如果是外部组织的根组织，需要设置为隐藏，同时需要把组织管理员设置为可见
        if (SysOrgEcoUtil.IS_ENABLED_ECO && BooleanUtils.isTrue(dept.getFdIsExternal())
                && syncRootIdsMap.containsKey(dept.getFdId())) {
            jodept.put("deptHiding", "true");
            //设置管理员查看
            setOrgAuthAdminRange(dept, jodept);
        }
        setRangeDetail(dept, jodept);
    }

    /**
     * 获取部门或机构隐藏范围
     *
     * @param dept
     * @description:
     * @return: com.landray.kmss.sys.organization.model.SysOrgElementHideRange
     * @author: wangjf
     * @time: 2021/10/27 5:42 下午
     */
    private SysOrgElementHideRange getDeptHideRange(SysOrgElement dept) {
        SysOrgElementHideRange hideRange = null;
        if (dept.getFdIsAvailable()) {
            if (!"true".equals(DingConfig.newInstance().getDingHideRangeEnabled()) && !BooleanUtils.isTrue(dept.getFdIsExternal())) {
                //隐藏属性，未内部组织并且隐藏性同步开关未开启
            } else {
                //开启同步开关或者是外部组织
                hideRange = dept.getFdHideRange();
            }
        }
        return hideRange;
    }

    private SysOrgElementRange getDeptRange(SysOrgElement dept) {
        // 如果同步选项设置为不同步查看范围且是内部组织，或查看范围为空，设置为关闭
        SysOrgElementRange range = null;
        if (dept.getFdIsAvailable()) {
            if (!"true".equals(DingConfig.newInstance().getDingRangeEnabled()) && !BooleanUtils.isTrue(dept.getFdIsExternal())) {
                //判断为可见性同步开关并且为内部组织，直接返回null，不进行钉钉数据的更改
            } else {
                //外部组织或者开启了同步开关则直接获取部门的可见范围
                range = dept.getFdRange();
            }
        }
        return range;
    }

    private void setRangeDetail(SysOrgElement dept, JSONObject jodept) {
        SysOrgElementRange range = getDeptRange(dept);
        if (range == null) {
            //说明可见性信息不需要同步
            return;
        }
        jodept.put("outerDept", String.valueOf(range.getFdIsOpenLimit()));
        Integer viewType = range.getFdViewType();
        if (viewType == null) {
            viewType = 1;
        }
        if (viewType == 2) {
            // 指定组织/人员
            String subType = range.getFdViewSubType();
            StringBuilder did = new StringBuilder();
            StringBuilder pid = new StringBuilder();
            Set<SysOrgElement> depts = new HashSet<>();
            if (subType.contains("1")) {
                jodept.put("outerDeptOnlySelf", "false");
                if (range.getFdElement() != null) {
                    depts.add(range.getFdElement());
                }
            }
            if (subType.contains("2")) {
                List<SysOrgElement> list = range.getFdOthers();
                if (CollectionUtils.isNotEmpty(list)) {
                    for (SysOrgElement elem : list) {
                        if (elem.getFdOrgType() == ORG_TYPE_ORG || elem.getFdOrgType() == ORG_TYPE_DEPT) {
                            depts.add(elem);
                        } else if (elem.getFdOrgType() == ORG_TYPE_PERSON) {
                            String dingId = relationMap.get(elem.getFdId());
                            if (StringUtil.isNotNull(dingId)
                                    && pid.indexOf(dingId) == -1) {
                                if (pid.length() > 0) {
                                    pid.append("\\|");
                                }
                                pid.append(dingId);
                            }
                        }
                    }
                }
            }

            // 过滤子组织
            Set<SysOrgElement> flatDepts = DingUtil.filterSubOrg(depts);
            if (CollectionUtils.isNotEmpty(flatDepts)) {
                for (SysOrgElement ele : flatDepts) {
                    String dingId = relationMap.get(ele.getFdId());
                    if (StringUtil.isNotNull(dingId)) {
                        did.append(dingId).append("\\|");
                    }
                }
            }
            jodept.put("outerPermitDepts", did.toString());
            jodept.put("outerPermitUsers", pid.toString());
        } else if (viewType == 1) {
            // 仅所在组织及下级组织/人员
            jodept.put("outerDeptOnlySelf", "true");
        } else {
            // 仅自己
            jodept.put("outerPermitUsers", "");
            jodept.put("outerPermitDepts", "");
        }
    }

    private Boolean toBoolean(String value) {
        if ("true".equals(value)
                || "1".equals(value)
                || "是".equals(value)) {
            return true;
        } else if ("false".equals(value)
                || "0".equals(value)
                || "否".equals(value)) {
            return false;
        }
        return false;
    }

    /**
     * 是否创建部门群
     *
     * @param dept
     * @param isAdd
     * @return
     */
    private Boolean isCreateDeptGroup(SysOrgElement dept, boolean isAdd) {
        String oldCreateDeptGroup = DingConfig.newInstance()
                .getDingOmsCreateDeptGroup();
        if (StringUtil.isNotNull(oldCreateDeptGroup)) {
            logger.debug("旧方式创建部门群");
            if (!"true".equals(DingConfig.newInstance()
                    .getDingOmsCreateDeptGroup())) {
                return false;
            } else {
                return true;
            }
        } else {
            String o2d_groupSynWay = DingConfig.newInstance()
                    .getOrg2dingDeptGroupSynWay();
            logger.debug("o2d_groupSynWay:" + o2d_groupSynWay);
            if ("syn".equalsIgnoreCase(o2d_groupSynWay)
                    || (isAdd && "addSyn"
                    .equalsIgnoreCase(o2d_groupSynWay))) {
                String o2d_groupAll = DingConfig.newInstance()
                        .getOrg2dingDeptGroupAll();
                String o2d_group = DingConfig.newInstance()
                        .getOrg2dingDeptGroup();
                logger.debug("o2d_groupAll:" + o2d_groupAll
                        + " o2d_group:" + o2d_group);
                if (StringUtil.isNotNull(o2d_groupAll)
                        && "true".equals(o2d_groupAll)) {
                    return true;
                } else {
                    if (StringUtil.isNotNull(o2d_group)) {
                        String groupValue = getPropertyValue(
                                o2d_group, dept);
                        return toBoolean(groupValue);
                    }
                }
            }
            return null;
        }
    }

    private Integer getDeptOrderValue(SysOrgElement dept, boolean isAdd) {
        try {
            String orderStr = getPropValue(dept, isAdd, "fdOrder", null, "org2ding.dept.order.synWay", "org2ding.dept.order");
            if (StringUtil.isNull(orderStr)) {
                return null;
            }
            return Integer.parseInt(orderStr);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private Boolean getGroupContainSubDept(SysOrgElement ekpDept, boolean isCreate) {
        DingConfig dingConfig = DingConfig.newInstance();
        String groupContainSubDeptSynWay = dingConfig
                .getOrg2dingDeptGroupContainSubDeptSynWay();
        logger.debug("groupContainSubDeptSynWay:"
                + groupContainSubDeptSynWay);
        if ("syn".equalsIgnoreCase(groupContainSubDeptSynWay)
                || (isCreate
                && "addSyn".equalsIgnoreCase(
                groupContainSubDeptSynWay))) {

            String groupContainSubDeptAll = dingConfig
                    .getOrg2dingDeptGroupContainSubDeptAll();
            String groupContainSubDeptKey = dingConfig
                    .getOrg2dingDeptGroupContainSubDept();
            logger.debug("groupContainSubDeptAll:" + groupContainSubDeptAll
                    + " groupContainSubDeptKey:"
                    + groupContainSubDeptKey);
            if (StringUtil.isNotNull(groupContainSubDeptAll)
                    && "true".equals(groupContainSubDeptAll)) {
                return true;
            } else {
                if (StringUtil.isNull(groupContainSubDeptKey)) {
                    return null;
                }
                String groupContainSubDeptValue = getPropertyValue(
                        groupContainSubDeptKey, ekpDept);
                Boolean groupContainSub = toBoolean(groupContainSubDeptValue);
                if (groupContainSub == null) {
                    return null;
                }
                if (groupContainSub == true) {
                    return true;
                } else if (groupContainSub == false) {
                    return false;
                }
            }
        }
        return null;
    }

    /**
     * 同步部门，主要处理新增和删除
     *
     * @param deptIds    本次同步的部门id列表
     * @param addDeptIds 本次同步新增的部门id列表
     * @throws Exception
     */
    private void handleAddDept(Set<String> deptIds, Set<String> addDeptIds, Set<String> delDeptIds) throws Exception {
        logger.debug("处理新增部门");
        // 先增加所有部门，且先挂到根机构下
        long count = 0L;
        if (CollectionUtils.isEmpty(deptIds)) {
            return;
        }
        for (String deptId : deptIds) {
            if (!checkRootNeedSync(deptId)) {
                continue;
            }
            SysOrgElement dept = (SysOrgElement) sysOrgElementService.findByPrimaryKey(deptId,null,true);
            updateSyncTime(dept);
            if (dept.getFdIsAvailable()) {
                // 新增
                if (!relationMap.keySet().contains(deptId)) {
                    JSONObject jodept = new JSONObject();
                    String name = DingUtil.getString(dept.getFdName(), (64 - (deptId.length() + 1))) + "_" + deptId;
                    jodept.accumulate("name", name);// 增加部门挂在根下时，会出现重复名，在此加ID区分，会在更新操作时修改正确
                    jodept.accumulate("parentid", Integer.valueOf(getDingRootDeptId()));
                    Boolean createDeptGroup = isCreateDeptGroup(dept, true);
                    if (createDeptGroup != null && createDeptGroup) {
                        jodept.accumulate("createDeptGroup", true);
                        Boolean groupContainSubDept = getGroupContainSubDept(dept, true);
                        if (groupContainSubDept != null && groupContainSubDept == true) {
                            jodept.accumulate("groupContainSubDept", true);
                        } else {
                            jodept.accumulate("groupContainSubDept", false);
                        }
                    } else {
                        jodept.accumulate("createDeptGroup", false);
                    }
                    Integer orderValue = getDeptOrderValue(dept, true);
                    if (orderValue != null) {
                        jodept.accumulate("order", orderValue.intValue());
                    }
                    String logInfo = "增加部门到钉钉" + dept.getFdName() + ", "
                            + dept.getFdId();
                    addDeptIds.add(deptId);
                    JSONObject ret = dingApiService.departCreate(jodept);
                    if (ret == null) {
                        logInfo += ",不可预知的错误(可能是网络或者钉钉接口异常，无法返回成功或者失败信息)!";
                        addOmsErrors(dept, logInfo, "add");
                    } else {
                        if (ret.getInt("errcode") == 0 || ret.getInt("errcode") == 60016) {
                            logger.info("新建部门成功或者部门已经存在，请求报文:" + jodept + "，响应报文:" + ret);
                            String id = ret.getString("id");
                            logInfo += " created,钉钉对应ID:" + id;
                            addRelation(dept, id, "2", null, null);
                        } else {
                            logInfo = " 失败,出错信息：" + ret.getString("errmsg");
                        }
                    }
                    logger.error(logInfo);
                    log(logInfo);
                }
            } else {
                delDeptIds.add(dept.getFdId());
            }
            count++;
        }
        log("处理部门（新增、删除）同步到钉钉的个数为:" + count + "条");
    }

    /**
     * 更新部门详情
     *
     * @param depts      部门id列表
     * @param addDeptIds 本次同步新增的部门id列表
     * @throws Exception
     */
    private void handleUpdateDept(Set<String> depts, Set<String> addDeptIds) throws Exception {
        // 更新所有部门，主要是更新关系
        String logInfo = "";
        for (String deptId : depts) {
            if (!checkRootNeedSync(deptId)) {
                continue;
            }
            SysOrgElement dept = (SysOrgElement) sysOrgElementService.findByPrimaryKey(deptId);
            if (dept.getFdIsAvailable()) {
                if (dept.getFdParent() == null) {
                    logInfo = "更新部门: " + dept.getFdName() + " " + dept.getFdId() + ",对应钉钉ID:"
                            + relationMap.get(dept.getFdId());
                } else {
                    logInfo = "新增后或更新钉钉的父部门ID ," + dept.getFdName() + "," + dept.getFdId() + ",对应父钉钉ID:"
                            + relationMap.get(dept.getFdParent().getFdId()) + ",对应钉钉ID:"
                            + relationMap.get(dept.getFdId());
                }
                String rtn = updateDept(dept, addDeptIds);
                logInfo += " ,retmsg:" + rtn;
                if (!"ok".equals(rtn)) {
                    addOmsErrors(dept, logInfo, "update");
                }
                log(logInfo);
            }
        }
    }

    /**
     * 删除钉钉部门
     *
     * @param delDeptIds
     * @throws Exception
     */
    private void handleDelDept(Set<String> delDeptIds) throws Exception {
        String logInfo = null;
        String rtn = null;
        if (CollectionUtils.isEmpty(delDeptIds)) {
            return;
        }
        for (String deptId : delDeptIds) {
            SysOrgElement dept = (SysOrgElement) sysOrgElementService.findByPrimaryKey(deptId);
            // 删除
            if (!relationMap.keySet().contains(dept.getFdId())) {
                logInfo = "从关系中找不到钉钉对应的ID，当前部门 ：" + dept.getFdName() + "," + dept.getFdId() + ",不做删除处理";
                logger.info(logInfo);
            } else {
                logInfo = "删除钉钉中的部门 " + dept.getFdName() + ", " + dept.getFdId() + ",钉钉中ID："
                        + relationMap.get(dept.getFdId());
                rtn = dingApiService.departDelete(relationMap.get(dept.getFdId()));
                logInfo += " ,retmsg:" + rtn;
                if ("ok".equalsIgnoreCase(rtn)) {
                    omsRelationService.deleteByKey(dept.getFdId(), getAppKey());
                    relationMap.remove(dept.getFdId());
                } else {
                    if (StringUtil.isNotNull(rtn)) {
                        if (!rtn.contains("60003")) {
                            addOmsErrors(dept, logInfo, "del");
                        }
                    }
                }
                log(logInfo);
            }
        }
    }

    private String getUserId(SysOrgPerson person) {
        String userid = person.getFdLoginName();
        // 如果数据已经存在则不管是否是登录名还是ID
        if (relationMap.get(person.getFdId()) != null) {
            userid = relationMap.get(person.getFdId());
            return userid;
        }
        // 根据配置来确定是选择那种作为企业号的userid，默认是登录名
        String o2d_userId = DingConfig.newInstance().getOrg2dingUserid();
        if (StringUtil.isNotNull(o2d_userId)) {
            //logger.debug("o2d_userId:" + o2d_userId);
            if ("fdId".equals(o2d_userId)) {
                userid = person.getFdId();
            }
        } else {
            String wxln = DingConfig.newInstance().getWxLoginName();
            if (StringUtil.isNotNull(wxln) && "id".equalsIgnoreCase(wxln)) {
                userid = person.getFdId();
            }
        }
        return userid;
    }

    private String getName(SysOrgElement element, boolean isAdd) {
        String o2d_name = DingConfig.newInstance().getOrg2dingDeptNameSynWay();
        if (element instanceof SysOrgPerson) {
            o2d_name = DingConfig.newInstance().getOrg2dingNameSynWay();
        }
        if (StringUtil.isNull(o2d_name)) {
            logger.debug("旧方式同步名称");
            return element.getFdName();
        } else {
            if ("syn".equalsIgnoreCase(o2d_name)
                    || ("addSyn".equalsIgnoreCase(o2d_name)
                    && isAdd)) {
                return element.getFdName();
            }
        }
        return null;
    }

    private String getMobile(SysOrgPerson person, boolean isAdd) {
        if (StringUtil.isNull(person.getFdMobileNo())) {
            return null;
        }
        String mobileWay = DingConfig.newInstance()
                .getOrg2dingMobileSynWay();
        logger.debug("mobileWay:" + mobileWay);
        if (StringUtil.isNotNull(mobileWay)) {
            if (isAdd) {
                return person.getFdMobileNo();
            }
        } else {
            logger.debug("旧方式同步手机号码！");
            return person.getFdMobileNo();
        }
        return null;
    }

    /**
     * 获取同步属性的值，兼容旧版本的配置（防止升级项目没有重新配置时出现异常）
     *
     * @param element
     * @param isAdd         是否新增操作
     * @param oldSyncProp   旧版本的同步字段
     * @param oldConfigProp 旧版本的配置项
     * @param synWayProp    新版本的同步设置
     * @param synValueProp  新版本的同步字段设置
     * @return
     */
    private String getPropValue(SysOrgElement element, boolean isAdd, String oldSyncProp, String oldConfigProp, String synWayProp, String synValueProp) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        DingConfig dingConfig = DingConfig.newInstance();
        String synWay = dingConfig.getDataMap().get(synWayProp);
        if (StringUtil.isNotNull(synWay)) {
            if ("syn".equalsIgnoreCase(synWay)
                    || (isAdd && "addSyn".equalsIgnoreCase(synWay))) {
                String synValuePropValue = dingConfig.getDataMap().get(synValueProp);
                if (element == null) {
                    return synValuePropValue;
                }
                String value = getPropertyValue(synValuePropValue, element);
                if (value == null) {
                    value = "";
                }
                return value;
            }
        } else {
            if (oldSyncProp == null) {
                return null;
            }
            if (oldConfigProp != null) {
                String oldConfigValue = dingConfig.getDataMap().get(oldConfigProp);
                if (!"true".equals(oldConfigValue)) {
                    return null;
                }
            }
            Object value = PropertyUtils.getProperty(element, oldSyncProp);
            if (value == null) {
                return "";
            }
            if (value instanceof Date) {
                return ((Date) value).getTime() + "";
            }
            return value.toString();
        }
        return null;
    }

    /**
     * 获取用户的职位信息
     *
     * @param person
     * @param ppMap  用户职位映射关系
     * @return
     */
    private String getPositionFromPpMap(SysOrgPerson person, Map<String, Map<String, String>> ppMap) {
        if (!ppMap.containsKey(person.getFdId())) {
            return "";
        }
        String position = ppMap.get(person.getFdId()).get("names");
        if (StringUtil.isNotNull(position)) {
            if (position.length() > 64) {
                position = position.substring(0, 64);
            }
            return position;
        } else {
            return "";
        }
    }

    /**
     * 获取职位的值
     *
     * @param person
     * @param isAdd
     * @param ppMap
     * @return
     */
    private String getPosition(SysOrgPerson person, boolean isAdd, Map<String, Map<String, String>> ppMap) {
        String dingPostEnabled = DingConfig.newInstance().getDingPostEnabled();
        if (StringUtil.isNotNull(dingPostEnabled)) {
            logger.debug("旧方式同步职位！");
            if (!"true".equals(dingPostEnabled)) {
                return null;
            }
            return getPositionFromPpMap(person, ppMap);
        } else {
            String o2d_dingPositionWay = DingConfig.newInstance()
                    .getOrg2dingPositionSynWay();
            //logger.debug(
            //		"新方式同步设置职位！o2d_dingPositionWay：" + o2d_dingPositionWay);
            if (StringUtil.isNull(o2d_dingPositionWay)) {
                return null;
            }
            if (!"syn".equalsIgnoreCase(o2d_dingPositionWay)
                    && !(isAdd && "addSyn".equalsIgnoreCase(o2d_dingPositionWay))) {
                return null;
            }
            String o2d_dingPosition = DingConfig.newInstance()
                    .getOrg2dingPosition();
            logger.debug("o2d_dingPosition:" + o2d_dingPosition);
            if (StringUtil.isNull(o2d_dingPosition)) {
                return null;
            }
            if ("hbmPosts".equals(o2d_dingPosition)) {
                return getPositionFromPpMap(person, ppMap);
            } else {
                String dingPositionValue = getPropertyValue(
                        o2d_dingPosition,
                        person);
                if (StringUtil.isNotNull(dingPositionValue)) {
                    return dingPositionValue;
                } else {
                    return "";
                }
            }
        }
    }

    /**
     * 是否隐藏手机号
     *
     * @param person
     * @param isAdd
     * @return
     */
    private Boolean isHideMobile(SysOrgPerson person, boolean isAdd) {
        String o2d_isHideWay = DingConfig.newInstance()
                .getOrg2dingIsHideSynWay();
        //logger.debug(
        //		"新方式同步设置号码隐藏！o2d_isHideWay：" + o2d_isHideWay);
        if (StringUtil.isNull(o2d_isHideWay)) {
            return null;
        }
        if (!"syn".equalsIgnoreCase(o2d_isHideWay)
                && !(isAdd && "addSyn".equalsIgnoreCase(o2d_isHideWay))) {
            return null;
        }
        String o2d_isHide = DingConfig.newInstance()
                .getOrg2dingIsHide();
        //logger.debug("o2d_isHide:" + o2d_isHide);
        String o2d_isHideAll = DingConfig.newInstance()
                .getOrg2dingIsHideAll();
        if ("true".equals(o2d_isHideAll)) {
            logger.debug("隐藏全部手机号码");
            return true;
        } else {
            try {
                if ("isContactPrivate".equals(o2d_isHide)) {
                    Object zonePerson = getSysZonePersonInfoService()
                            .findByPrimaryKey(person.getFdId(),
                                    null, true);
                    Object o = PropertyUtils.getProperty(zonePerson, "isContactPrivate");
                    logger.debug("员工黄页获取的的值：" + o);
                    if (o != null) {
                        String hideObject = o.toString().trim();
                        return toBoolean(hideObject);
                    } else {
                        logger.debug(
                                "个人员工黄页隐私不设置的情况下，则取全局员工黄页的隐私配置");
                        Map orgMap = sysAppConfigService
                                .findByKey(
                                        "com.landray.kmss.sys.zone.model.SysZonePrivateConfig");
                        if (orgMap != null && orgMap.containsKey("isContactPrivate")) {
                            Object isContactPrivateObject = orgMap
                                    .get("isContactPrivate");
                            logger.debug(
                                    "isContactPrivateObject:"
                                            + isContactPrivateObject);
                            return toBoolean(isContactPrivateObject.toString());
                        } else {
                            return false;
                        }
                    }
                } else {
                    // 从字段同步
                    String o2d_isHideValue = getPropertyValue(o2d_isHide, person);
                    logger.debug("从字段同步o2d_isHideValue："
                            + o2d_isHideValue);
                    return toBoolean(o2d_isHideValue);
                }
            } catch (Exception e) {
                logger.error("获取人员的隐藏号码信息异常:" + person.getFdName());
                logger.error(e.getMessage(), e);
            }
        }
        return false;
    }

    private JSONObject getUser(SysOrgPerson person, boolean isAdd, Map<String, Map<String, String>> ppMap)
            throws Exception {
        logger.debug("isAdd:" + isAdd);
        JSONObject personObj = new JSONObject();
        personObj.accumulate("userid", getUserId(person));

        String name = getName(person, isAdd);
        if (StringUtil.isNotNull(name)) {
            personObj.accumulate("name", person.getFdName());
        }
        JSONArray userDepts = getUserDepartment(person, isAdd, ppMap);
        if (userDepts != null) {
            personObj.accumulate("dept_id_list", userDepts.join(","));
        }
        String mobile = getMobile(person, isAdd);
        if (StringUtil.isNotNull(mobile)) {
            personObj.accumulate("mobile", person.getFdMobileNo());
        }
        String email = getPropValue(person, isAdd, "fdEmail", null, "org2ding.email.synWay", "org2ding.email");
        if (email != null) {
            personObj.accumulate("email", email);
        }
        // 钉钉分机号存在唯一性,若办公电话一样,同步过去会出错
        String tel = getPropValue(person, isAdd, "fdWorkPhone", "dingWorkPhoneEnabled", "org2ding.tel.synWay", "org2ding.tel");
        if (tel != null) {
            personObj.accumulate("telephone", tel);
        }
        String jobnumber = getPropValue(person, isAdd, "fdNo", "dingNoEnabled", "org2ding.jobnumber.synWay", "org2ding.jobnumber");
        if (jobnumber != null) {
            personObj.accumulate("job_number", jobnumber);
        }
        // 是否同步职位
        String position = getPosition(person, isAdd, ppMap);
        if (position != null) {
            personObj.accumulate("title", position);
        }

        //String orderStr = getPropValue(person,isAdd,"fdOrder",null,"org2ding.orderInDepts.synWay","fdOrder");
        String synWay = DingConfig.newInstance().getDataMap().get("org2ding.orderInDepts.synWay");
        if (StringUtil.isNotNull(synWay)) {
            if ("syn".equalsIgnoreCase(synWay)
                    || (isAdd && "addSyn".equalsIgnoreCase(synWay))) {
                Integer order = person.getFdOrder();
                if (order != null) {
                    String o2d_order = DingConfig.newInstance().getOrg2dingOrderInDepts();
                    String personOrder = DingConfig.newInstance().getDingPersonOrder();
                    if ("desc".equals(o2d_order) || "1".equals(personOrder)) {
                        order = Integer.MAX_VALUE - order;
                    }
                    String parentId = getDingRootDeptId();
                    if (person.getFdParent() != null) {
                        parentId = relationMap.get(person.getFdParent().getFdId());
                    }
                    JSONArray dept_order_list = new JSONArray();
                    JSONObject orderJson = new JSONObject();
                    orderJson.put("dept_id", Integer.parseInt(parentId));
                    orderJson.put("order", order);
                    dept_order_list.add(orderJson);
                    personObj.accumulate("dept_order_list", dept_order_list);
                }
            }
        }

        String remark = getPropValue(person, isAdd, "fdMemo", null, "org2ding.remark.synWay", "org2ding.remark");
        if (remark != null) {
            personObj.accumulate("remark", remark);
        }
        String hiredDate = getPropValue(person, isAdd, "fdHiredDate", null, "org2ding.hiredDate.synWay", "org2ding.hiredDate");
        if (StringUtil.isNotNull(hiredDate)) {
            try {
                Long time = Long.parseLong(hiredDate);
                personObj.accumulate("hired_date", time);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        String orgEmail = getPropValue(person, isAdd, "fdOrgEmail", null, "org2ding.orgEmail.synWay", "org2ding.orgEmail");
        if (orgEmail != null) {
            personObj.accumulate("org_email", orgEmail);
        }
        Boolean isHideMobile = isHideMobile(person, isAdd);
        if (isHideMobile != null) {
            personObj.accumulate("hide_mobile", isHideMobile);
        }
        // 是否高管
        String seniorValue = getPropValue(person, isAdd, null, null, "org2ding.isSenior.synWay", "org2ding.isSenior");
        if (seniorValue != null) {
            Boolean isSenior = toBoolean(seniorValue);
            personObj.accumulate("senior_mode", isSenior);
        }
        String workPlace = getPropValue(person, isAdd, null, null, "org2ding.workPlace.synWay", "org2ding.workPlace");
        if (workPlace != null) {
            personObj.accumulate("work_place", workPlace);
        }
        //专属账号同步
        String dingAccountType = getDingAccountType(person);
        Boolean isExclusive_account = isExclusiveAccount(person);
        if (isExclusive_account) {
            //专属账号类型
            DingConfig dingConfig = DingConfig.newInstance();
            String accountType = dingConfig.getOrg2dingIsExclusiveAccountType();
            if (isAdd) {
                personObj.accumulate("exclusive_account", true);
                if ("sso".equals(accountType)) {
                    dingAccountType = "sso";
                    personObj.accumulate("exclusive_account_type", "sso");
                } else {
                    dingAccountType = "dingtalk";
                    personObj.accumulate("exclusive_account_type", "dingtalk");
                    String password = dingConfig.getOrg2dingIsExclusiveAccountPassword();
                    //logger.debug("*初*始*密*码*："+password);
                    personObj.accumulate("init_password", password);
                }
            }
            if ("dingtalk".equals(accountType)) {
                //登录名同步
                String login_idSynWay = dingConfig.getOrg2dingIsExclusiveAccountLoginNameSynWay();
                if ("syn".equalsIgnoreCase(login_idSynWay)
                        || (isAdd && "addSyn".equalsIgnoreCase(login_idSynWay))) {
                    String login_id = dingConfig.getOrg2dingIsExclusiveAccountLoginName();
                    String val = getPropertyValue(login_id, person);
                    logger.warn("专属账号登录名：{}", val);
                    if (StringUtil.isNotNull(val)) {
                        personObj.accumulate("login_id", val);
                        personObj.accumulate("loginId", val); //更新接口用的是loginId，新增接口是login_id。暂时都传，避免钉钉接口改动引起功能异常
                    }
                }
            }
        }
        personObj.accumulate("dingAccountType", dingAccountType); //同步的账号类型

        //拓展字段同步
        JSONObject extension = getExtension(person,isAdd);
        logger.warn("自定义字段："+extension);
        if (extension != null && extension.size() > 0) {
            personObj.accumulate("extension", extension);
        }

        return personObj;
    }

    /*
     * 获取拓展字段
     */
    private JSONObject getExtension(SysOrgPerson person, boolean isAdd) {
        List<DingConfigCustom> customData = DingConfig.getCustomData();
        if (customData==null||customData.isEmpty()) {
            return null;
        }
        JSONObject rs = new JSONObject();
        customData.forEach(custom->{
            if("syn".equals(custom.synWay) || (isAdd && "addSyn".equals(custom.synWay))){
                String value = getPropertyValue(custom.target, person);
                logger.warn("key:{},value:{}",custom.target,value);
                rs.put(custom.title,value);
            }
        });
        return rs;
    }

    private String getDingAccountType(SysOrgPerson person) {
        String type = "common";
        if (this.relationMap.containsKey(person.getFdId())) {
            //已有映射关系，消息类型不能修改
            if (accountTypeMap.containsKey(relationMap.get(person.getFdId()))) {
                return accountTypeMap.get(relationMap.get(person.getFdId()));
            }
        }
        return type;
    }

    /**
     * 是否专属账号
     *
     * @param person
     * @return
     */
    private Boolean isExclusiveAccount(SysOrgPerson person) {
        DingConfig dingConfig = DingConfig.newInstance();
        String exclusiveAccountEnable = dingConfig.getExclusiveAccountEnable();
        if (StringUtil.isNotNull(exclusiveAccountEnable) && "true".equalsIgnoreCase(exclusiveAccountEnable)) {
            //relation若有账号，按照映射表中的钉钉类型同步
            if (this.relationMap.containsKey(person.getFdId())) {
                //已有映射关系，消息类型不能修改
                if (accountTypeMap.containsKey(relationMap.get(person.getFdId()))) {
                    return !"common".equalsIgnoreCase(accountTypeMap.get(relationMap.get(person.getFdId())));
                }
            } else {
                //没有映射关系，根据配置判断是否需要同步为专属账号
                String exclusiveAccountFiled = dingConfig.getOrg2dingIsExclusiveAccountAll();
                if ("true".equalsIgnoreCase(exclusiveAccountFiled)) {
                    return true;
                } else {
                    String value = getPropertyValue(dingConfig.getOrg2dingIsExclusiveAccount(), person);
                    logger.warn("---是否同步专有账号---{}", value);
                    return toBoolean(value);
                }

            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * 获取组织属性值
     *
     * @param key     属性名称
     * @param element 组织对象
     * @return
     */
    private String getPropertyValue(String key, SysOrgElement element) {
        try {
            //logger.debug("key:" + key + " ,name:" + element.getFdName());
            if (StringUtil.isNull(key)) {
                return null;
            }
            Map<String, Object> customMap = element.getCustomPropMap();
            Object obj = "";
            if (customMap != null && customMap.containsKey(key)) {
                if (customMap.get(key) == null) {
                    return null;
                }
                obj = customMap.get(key);
            } else {
                obj = PropertyUtils.getProperty(element, key);
            }
            if (obj != null && ("fdStaffingLevel".equals(key) || "hbmParent".equals(key) || "fdParent".equals(key))) {
                obj = PropertyUtils.getProperty(obj, "fdName");
            }
            if (obj == null) {
                return null;
            }
            String v = obj.toString();
            if (obj instanceof Date) {
                v = ((Date) obj).getTime() + "";
            }
            //logger.debug("element("+element.getFdId()+","+element.getFdName()+"), key:"+key+"，value:" + v);
            return v;
        } catch (Exception e) {
            logger.error("根据字段获取部门的值过程中发生了异常", e);
        }
        return null;
    }

    /**
     * 是否启用职位同步
     *
     * @return
     */
    private boolean isPositionSyncEnabled() {
        String dingPostEnabled = DingConfig.newInstance().getDingPostEnabled();
        if (StringUtil.isNotNull(dingPostEnabled)) {
            if ("true".equals(dingPostEnabled)) {
                return true;
            }
        } else {
            String o2d_dingPositionWay = DingConfig.newInstance()
                    .getOrg2dingPositionSynWay();
            if ("syn".equalsIgnoreCase(o2d_dingPositionWay) || "addSyn".equalsIgnoreCase(o2d_dingPositionWay)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否启用一人多部门同步
     *
     * @return
     */
    private boolean isMulDeptEnabled() {
        String dingPostEnabled = DingConfig.newInstance().getDingPostMulDeptEnabled();
        if (StringUtil.isNull(dingPostEnabled)) {
            String o2d_DeptWay = DingConfig.newInstance().getOrg2dingDepartmentSynWay();
            logger.debug("o2d_DeptWay:" + o2d_DeptWay);
            if (!"syn".equals(o2d_DeptWay) && !"addSyn".equals(o2d_DeptWay)) {
                return false;
            }
            return "fdMuilDept".equals(DingConfig.newInstance()
                    .getOrg2dingDepartment());
        } else {
            return "true".equals(dingPostEnabled);
        }
    }

    private JSONArray getUserDepartment(SysOrgPerson element,
                                        boolean isAdd, Map<String, Map<String, String>> ppMap) {
        JSONArray depts = new JSONArray();
        // 是否开启一人多部门同步
        String dingPostEnabled = DingConfig.newInstance().getDingPostMulDeptEnabled();
        boolean multiDept = false;
        if (StringUtil.isNull(dingPostEnabled)) {
            String o2d_DeptWay = DingConfig.newInstance().getOrg2dingDepartmentSynWay();
            //logger.debug("o2d_DeptWay:" + o2d_DeptWay+ "，isAdd: " + isAdd);
            if (!"syn".equals(o2d_DeptWay) && !(isAdd && "addSyn".equals(o2d_DeptWay))) {
                return null;
            }
            multiDept = "fdMuilDept".equals(DingConfig.newInstance()
                    .getOrg2dingDepartment());
            logger.debug("一人多部门功能是否开启：" + multiDept);
        } else {
            multiDept = "fdMuilDept".equals(DingConfig.newInstance()
                    .getOrg2dingDepartment());
            logger.debug("一人多部门功能是否开启（旧配置）：" + multiDept);
        }
        JSONArray parentIds = getPersonParentIds(element, ppMap, multiDept);
        if (parentIds != null) {
            depts.addAll(parentIds);
        }
        return depts;
    }

    private JSONArray getPersonParentIds(SysOrgPerson element,
                                         Map<String, Map<String, String>> ppMap, boolean multiDept) {

        logger.debug("获取人员："+element.getFdName()+" 需要同步的部门信息");

        JSONArray array = new JSONArray();
        // 把当前人所属部门同步过去
        String parentId = getDingRootDeptId();
        if (element.getFdParent() != null) {
            if(relationMap.containsKey(element.getFdParent().getFdId())){
                String pid = relationMap.get(element.getFdParent().getFdId());
                if(StringUtil.isNotNull(pid)){
                    parentId=pid;
                }else{
                    logger.warn("人员："+element.getFdName()+"的所属部门的映射表异常！ fdParentID:"+element.getFdParent().getFdId());
                }
            }else {
                logger.warn("人员："+element.getFdName()+" 的所属部门没有同步到钉钉，请检查数据！ fdParentID:"+element.getFdParent().getFdId());
            }
        }
        array.add(Integer.parseInt(parentId));
        if (!multiDept) {
            return array;
        }
        if (ppMap.containsKey(element.getFdId())) {
            String[] parentids = ppMap.get(element.getFdId()).get("parentids").split("[,;]");
            for (String pid : parentids) {
                String dingPid = relationMap.get(pid);
                if (dingPid != null && !dingPid.equals(parentId)) {
                    array.add(Integer.parseInt(dingPid));
                }
            }
        }
        return array;
    }

    private void handlePerson(List<String> personIds, Map<String, Map<String, String>> ppMap) throws Exception {
        String logInfo = "";
        long count = 0L;
        for (String id : personIds) {
            SysOrgPerson person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(id,null,true);
            updateSyncTime(person);
            if (StringUtil.isNull(person.getFdLoginName())) {
                logInfo = "警告：当前个人 " + person.getFdName() + ",的登录名为空，直接跳过";
                logger.warn(logInfo);
                log(logInfo);
                continue;
            }
            if (person.getFdIsAvailable()) {
                if (!person.getFdIsBusiness()) {
                    logger.debug("业务无关的人员不同步：" + person.getFdName());
                    continue;
                }
                if (!relationMap.containsKey(person.getFdId())) {
                    addPerson(person, ppMap);
                } else {
                    updatePerson(person, ppMap);
                }
            } else {
                if (!relationMap.containsKey(person.getFdId())) {
                    logInfo = "警告：从关系中找不到钉钉对应的ID，且该用户是无效状态，当前用户信息（" + person.getFdName() + ", " + "loginName:"
                            + (person.getFdLoginName() == null ? "" : person.getFdLoginName()) + ",id:"
                            + person.getFdId() + "）,不处理";
                    logger.warn(logInfo);
                } else {
                    deletePerson(person);
                }
            }
            count++;
        }
        logInfo = "本次批量同步个人到钉钉的个数为:" + count + "条";
        logger.debug(logInfo);
        log(logInfo);
    }

    private void addPerson(SysOrgPerson person, Map<String, Map<String, String>> ppMap) throws Exception {
        //手机号码为空，而且不是专属账号的
        if (StringUtil.isNull(person.getFdMobileNo()) && !isExclusiveAccount(person)) {
            log("人员（" + person.getFdName() + ", " + person.getFdId() + "）的手机号为空，不进行新增操作");
            return;
        }
        String logInfo = "增加个人到钉钉（" + person.getFdName() + ", " + person.getFdId() + "）";
        JSONObject personObj = getUser(person, true, ppMap);
        String dingAccountType = "";
        if (personObj.containsKey("dingAccountType")) {
            dingAccountType = personObj.getString("dingAccountType");
        }
        JSONObject ret = dingApiService.userCreate(personObj);
        if (ret == null) {
            logInfo += ",不可预知的错误(可能是网络或者钉钉接口异常，无法返回成功或者失败信息)!";
            addOmsErrors(person, logInfo, "add");
        } else {
            // 60102:UserID在公司中已存在;  60104:手机号码在公司中已存在 (由于需要记录钉钉的专属账号类型，所以需要拿到用户详情)
            if (ret.getInt("errcode") == 0 || ret.getInt("errcode") == 60102 || ret.getInt("errcode") == 60104) {
                String unionId = getUnionId(ret);
                String userid = getUserId(ret);
                logger.warn("unionId:{} ,userId:", unionId, userid);
                JSONObject userInfo = null;
                // #111497
                if (ret.getInt("errcode") == 60104) {
                    logger.warn(ret + " ");
                    //手机号码存在
                    if (ret.containsKey("userid")) {
                        userid = ret.getString("userid");
                    }
                    if (StringUtil.isNotNull(userid)) {
                        userInfo = dingApiService.userGet_v2(userid, null);
                    } else {
                        JSONObject userIdByMobile = dingApiService.getUserIdByMobile(person.getFdMobileNo());
                        if (userIdByMobile.getInt("errcode") == 0) {
                            userInfo = dingApiService.userGet_v2(userIdByMobile.getJSONObject("result").getString("userid"), null);
                        }
                    }
                    logInfo += " 号码(" + person.getFdMobileNo() + ")在钉钉已经存在 : " + ret;

                } else if (ret.getInt("errcode") == 60102) {
                    logger.warn(ret + " ");
                    //UserId已存在
                    logInfo += " UserId(" + personObj.getString("userid") + ")在钉钉已经存在;";
                    userInfo = dingApiService.userGet_v2(personObj.getString("userid"), null);
                }
                if (userInfo != null && userInfo.getInt("errcode") == 0) {
                    logger.debug(" 钉钉人员信息：" + userInfo);
                    JSONObject info = userInfo.getJSONObject("result");
                    unionId = info.getString("unionid");
                    userid = getUserId(info);
                    if (info.getBoolean("exclusive_account")) {
                        dingAccountType = info.getString("exclusive_account_type");
                    } else {
                        dingAccountType = "common";
                    }
                } else {
                    logInfo += " 尝试获取钉钉已存在的人员失败!";
                }

                if (StringUtil.isNotNull(userid) && !relationMap.containsValue(userid)) {
                    logInfo += " ,created";
                    logger.warn("建立映射关系并且更新用户 " + person.getFdName() + " 的数据。userid:" + userid + "    ekpId:" + person.getFdId());
                    addRelation(person, userid, "8", unionId, dingAccountType);
                    if (ret.getInt("errcode") == 0) {
                        logInfo += " ok";
                    } else {
                        logInfo += " 匹配钉钉已存在的人员成功，将建立映射关系并更新钉钉人员信息";
                        String update_ret = dingApiService.userUpdate(getUser(person, false, ppMap));
                        logger.debug("更新结果：update_ret:  " + update_ret);
                        logInfo += " \n " + person.getFdName() + " update_ret:" + update_ret;
                    }

                } else {
                    logInfo += " 映射表已存在钉钉userid(" + userid + ")的关系，不再新增或更新该记录!";
                }
            } else {
                logInfo += " 失败,出错信息：" + ret.getString("errmsg");
                addOmsErrors(person, logInfo, "add");
            }
        }
        logger.info(logInfo);
        log(logInfo);
    }


    private void updatePerson(SysOrgPerson person, Map<String, Map<String, String>> ppMap) throws Exception {
        String logInfo = "更新个人到钉钉 " + person.getFdName() + ", " + person.getFdId();
        logInfo += " ,retmsg:";
        String retMsg = dingApiService.userUpdate(getUser(person, false, ppMap));
        logInfo += retMsg;
        if (StringUtil.isNull(retMsg)) {
            logInfo += ",不可预知的错误(可能是网络或者钉钉接口异常，无法返回成功或者失败信息)!";
            addOmsErrors(person, logInfo, "update");
            return;
        }
        logger.info(logInfo);
        log(logInfo);

        if (!"ok".equals(retMsg)) {
            JSONObject jo = JSONObject.fromObject(retMsg);
            // {"errcode":60121,"errmsg":"找不到该用户"}
            if (jo.getInt("errcode") == 60121) {
                logInfo = "补增个人到钉钉 " + person.getFdName() + ", " + person.getFdId();
                logInfo += " ,retmsg:";
                JSONObject ret = dingApiService
                        .userCreate(getUser(person, true, ppMap));
                if (ret == null) {
                    logInfo += ",不可预知的错误(可能是网络或者钉钉接口异常，无法返回成功或者失败信息)!";
                    addOmsErrors(person, logInfo, "add");
                } else {
                    if (ret.getInt("errcode") == 0) {
                        logInfo += " ,created";
                    } else {
                        logInfo += " 失败,出错信息：" + ret.getString("errmsg");
                        addOmsErrors(person, logInfo, "add");
                    }
                }
                logger.error(logInfo);
                log(logInfo);
            }
            // {"errcode":40022,"errmsg":"企业中的手机号码和登陆钉钉的手机号码不一致,暂时不支持修改用户信息,可以删除后重新添加"}
            // {"errcode":40021,"errmsg":"更换的号已注册过钉钉,可以删除后重新添加"}
            if (jo.getInt("errcode") == 40021) {
                addNewAnddelOld(person, ppMap);
            } else if (jo.getInt("errcode") == 40022) {
                // 手机号码调整后不删除后重新添加(新需求#53650)
                JSONObject pm = getUser(person, false, ppMap);
                if (pm.containsKey("mobile")) {
                    pm.remove("mobile");
                }
                retMsg = dingApiService.userUpdate(pm);
                if (!"ok".equals(retMsg)) {
                    addOmsErrors(person, logInfo, "update");
                }
                String tn = "name=" + person.getFdName() + ",loginName=" + person.getFdLoginName()
                        + ",个人手机号变更(40022/40021)请在手机客户端自行修改 ,retmsg:" + retMsg;
                logger.info(tn);
                log(tn);
            }
        }
    }

    /**
     * 更换的号已注册过钉钉, 删除钉钉中未激活的用户，再重新添加用户
     *
     * @param person
     * @param ppMap
     * @throws Exception
     */
    private void addNewAnddelOld(SysOrgPerson person, Map<String, Map<String, String>> ppMap) throws Exception {
        String logInfo = "删除钉钉中未激活的手机号用户 " + person.getFdName() + ","
                + person.getFdId() + ",loginName:" + person.getFdLoginName();
        String userid = relationMap.get(person.getFdId());
        if (StringUtil.isNull(userid)) {
            logInfo = "删除钉钉中未激活的手机号用户：因为中间映射表无相关数据无法执行 ";
            logger.warn(logInfo);
            log(logInfo);
            return;
        }
        JSONObject ujo = dingApiService.userGet_v2(userid, person.getFdId());
        if (ujo != null && ujo.containsKey("errcode") && ujo.getInt("errcode") == 0) {
            ujo = ujo.getJSONObject("result");
        }
        String dingAccountType = "common";
        String unionId = getUnionId(ujo);
        if (ujo.containsKey("active") && !ujo.getBoolean("active")) {
            logInfo += " ," + dingApiService.userDelete(userid);
            logInfo += "\n";
            omsRelationService.deleteByKey(person.getFdId(), getAppKey());
            relationMap.remove(person.getFdId());
            // 增加新的钉钉用户
            logInfo += "增加新的钉钉用户（激活） " + person.getFdName() + ","
                    + person.getFdId() + ",loginName:" + person.getFdLoginName();
            JSONObject personObj = getUser(person, true, ppMap);
            JSONObject ret = dingApiService.userCreate(personObj);
            if (ret == null) {
                logInfo += ",不可预知的错误!";
            } else {
                if (ret.getInt("errcode") == 0) {
                    logInfo += " ,created";
                    if (ret.containsKey("exclusive_account")) {
                        if (ret.getBoolean("exclusive_account")) {
                            dingAccountType = ret.getString("exclusive_account_type");
                        }
                    }
                    addRelation(person, personObj.getString("userid"), "8", unionId, dingAccountType);
                } else {
                    logInfo += " 失败,出错信息：" + ret.getString("errmsg");
                }
            }
            logger.error(logInfo);
            log(logInfo);
        }
    }

    private String getUnionId(JSONObject ujo) {
        String unionId = null;
        try {
            if (ujo.containsKey("unionId")) {
                unionId = ujo.getString("unionId");
            }
            if (StringUtil.isNull(unionId) && ujo.containsKey("unionid")) {
                unionId = ujo.getString("unionid");
            }
            if (StringUtil.isNull(unionId) && ujo.containsKey("errcode") && ujo.getInt("errcode") == 0
                    && ujo.containsKey("result")) {
                unionId = ujo.getJSONObject("result").getString("unionId");
            }
        } catch (Exception e) {
            logger.warn("获取钉钉unionId异常：" + e.getMessage(), e);
        }
        return unionId;
    }

    private String getUserId(JSONObject ujo) {
        String userid = null;
        try {
            if (ujo.containsKey("userId")) {
                userid = ujo.getString("userId");
            }
            if (StringUtil.isNull(userid) && ujo.containsKey("userid")) {
                userid = ujo.getString("userid");
            }
            if (StringUtil.isNull(userid) && ujo.containsKey("errcode") && ujo.getInt("errcode") == 0
                    && ujo.containsKey("result")) {
                userid = ujo.getJSONObject("result").getString("userid");
            }
        } catch (Exception e) {
            logger.warn("获取钉钉userid异常：" + e.getMessage(), e);
        }
        return userid;
    }

    private void deletePerson(SysOrgPerson person) throws Exception {
        String logInfo = "删除钉钉中的个人ID " + person.getFdName() + " " + person.getFdId() + ","
                + relationMap.get(person.getFdId());
        if (relationMap.containsKey(person.getFdId()) && StringUtil.isNotNull(relationMap.get(person.getFdId()))) {
            String temp = dingApiService.userDelete(relationMap.get(person.getFdId()));
            logInfo += " ,retMsg:" + temp;
            if ("ok".equalsIgnoreCase(temp)) {
                omsRelationService.deleteByKey(person.getFdId(), getAppKey());
                relationMap.remove(person.getFdId());
            } else {
                if (logInfo.contains("60111") || logInfo.contains("60121")) {
                    addOmsErrors(person, logInfo, "del");
                }
            }
        } else {
            logInfo += " ,钉钉Id找不到";
        }
        log(logInfo);
    }

    private void updateSyncTime(SysOrgElement element) {
        if (element != null && element.getFdAlterTime() != null) {
            long alterTime = element.getFdAlterTime().getTime();
            if (syncTime >= alterTime) {
                return;
            }
            syncTime = alterTime;
        }
    }

    private JSONObject buildOmsPostContent(SysOrgElement post) {
        JSONObject postEle = new JSONObject();
        JSONArray personIds = new JSONArray();
        List<SysOrgPerson> list = post.getFdPersons();
        if (list != null && list.size() > 0) {
            for (SysOrgPerson person : list) {
                personIds.add(person.getFdId());
            }
        }
        postEle.put("persons", personIds);
        postEle.put("isAvailable", post.getFdIsAvailable());
        postEle.put("id", post.getFdId());
        postEle.put("name", post.getFdName());
        postEle.put("type", "post");
        postEle.put("parentid", "");
        if (post.getFdParent() != null) {
            postEle.put("parentid", post.getFdParent().getFdId());
        }
        postEle.put("leaderparentid", "");
        if (deptLeaderMap.containsKey(post.getFdId())) {
            StringBuffer ids = new StringBuffer();
            List<String> ldps = deptLeaderMap
                    .get(post.getFdId());
            for (int i = 0; i < ldps.size(); i++) {
                if (i == 0) {
                    ids.append(ldps.get(i));
                } else {
                    ids.append(";" + ldps.get(i));
                }
            }
            postEle.put("leaderparentid", ids.toString());
        }
        return postEle;
    }

    private List<String> getElementIds(List<SysOrgElement> list) {
        if (list == null) {
            return null;
        }
        List<String> ids = new ArrayList<>();
        for (SysOrgElement e : list) {
            ids.add(e.getFdId());
        }
        return ids;
    }

    private boolean checkEqual(String oldStr, String newStr) {
        if (oldStr == null) {
            if (newStr == null) {
                return true;
            } else {
                return false;
            }
        } else {
            return oldStr.equals(newStr);
        }
    }

    /**
     * 取差集
     *
     * @param personIds_new
     * @param personIds_old
     * @return
     */
    private Set<String> getSubtraction(Set<String> personIds_new, Set<String> personIds_old) {
        Set<String> result = new HashSet<>();
        if (personIds_new == null) {
            return personIds_old;
        }
        if (personIds_old == null) {
            return personIds_new;
        }
        result.addAll(personIds_new);
        result.removeAll(personIds_old);
        Set<String> tmp = new HashSet<>();
        tmp.addAll(personIds_old);
        tmp.removeAll(personIds_new);
        result.addAll(tmp);
        return result;
    }

    private JSONObject getContentObj(ThirdDingOmsPost omsPost) {
        String oldContent = omsPost.getDocContent();
        if (StringUtil.isNull(oldContent)) {
            oldContent = omsPost.getFdContent();
        }
        if (StringUtil.isNull(oldContent)) {
            return null;
        }
        return JSONObject.fromObject(oldContent);
    }

    /**
     * 岗位变动后，计算出需要更新的部门和人员
     *
     * @param post
     * @param syncDeptIds
     * @param syncPersonIds
     */
    private void calcNeedSyncElems(SysOrgElement post, ThirdDingOmsPost omsPost, Set<String> syncDeptIds, Set<String> syncPersonIds) {
        JSONObject oldPostEle = getContentObj(omsPost);
        if (oldPostEle == null) {
            syncPersonIds.addAll(getElementIds(post.getFdPersons()));
            if (post.getFdParent() != null) {
                syncDeptIds.add(post.getFdParent().getFdId());
            }
            if (deptLeaderMap.containsKey(post.getFdId())) {
                List<String> ldps = deptLeaderMap
                        .get(post.getFdId());
                syncDeptIds.addAll(ldps);
            }
        } else {
            String oldParentId = oldPostEle.getString("parentid");
            JSONArray oldPersons = oldPostEle.getJSONArray("persons");
            String newParentId = post.getFdParent() == null ? null : post.getFdParent().getFdId();
            List<String> newPersons = getElementIds(post.getFdPersons());
            Set<String> personIds_new = new HashSet<>(newPersons);
            //如果上级部门变了
            if (!checkEqual(oldParentId, newParentId)) {
                if (deptLeaderMap.containsKey(post.getFdId())) {
                    Set<String> subtraction = getSubtraction(personIds_new, new HashSet<>(oldPersons));
                    //如果岗位成员变了
                    if (subtraction != null && !subtraction.isEmpty()) {
                        List<String> ldps = deptLeaderMap
                                .get(post.getFdId());
                        syncDeptIds.addAll(ldps);
                    }
                }
                personIds_new.addAll(oldPersons);
                syncPersonIds.addAll(personIds_new);
            } else {
                Set<String> subtraction = getSubtraction(personIds_new, new HashSet<>(oldPersons));
                //如果岗位成员变了
                if (subtraction != null && !subtraction.isEmpty()) {
                    if (deptLeaderMap.containsKey(post.getFdId())) {
                        List<String> ldps = deptLeaderMap
                                .get(post.getFdId());
                        syncDeptIds.addAll(ldps);
                    }
                    syncPersonIds.addAll(subtraction);
                }
            }
        }
    }

    /**
     * 1、如果是岗位所属部门变了，那么当前所有的岗位成员、以及之前的岗位成员都需要更新。
     * 2、如果是岗位成员变了，那么变动的成员需要更新；部门领导是该岗位的部门需要更新
     * 3、如果是新增的岗位，那么岗位成员、部门领导是该岗位的部门、以及岗位的所属部门，都需要更新
     *
     * @throws Exception
     */
    private void handleUpdatePost(Set<String> syncDeptIds, Set<String> syncPersonIds) throws Exception {
        // 获取岗位
        List<SysOrgElement> posts = getData(ORG_TYPE_POST, null);
        if (posts == null || posts.size() == 0) {
            return;
        }

        DataSource dataSource = (DataSource) SpringBeanUtil
                .getBean("dataSource");
        Connection conn = null;
        PreparedStatement psupdate_oms_post = null;
        PreparedStatement psinsert_oms_post = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            psupdate_oms_post = conn
                    .prepareStatement(
                            "update third_ding_oms_post set fd_name = ?,doc_content = ? where fd_id = ?");
            psinsert_oms_post = conn
                    .prepareStatement(
                            "insert into third_ding_oms_post(fd_id,fd_name,doc_content) values(?,?,?)");
            int loop = 0;
            for (SysOrgElement post : posts) {
                updateSyncTime(post);
                ThirdDingOmsPost omsPost = (ThirdDingOmsPost) thirdDingOmsPostService.findByPrimaryKey(post.getFdId(), null, true);
                if (omsPost == null) {
                    if (!post.getFdIsAvailable()) {
                        //无效岗位，且之前没有同步过
                        continue;
                    }
                    syncPersonIds.addAll(getElementIds(post.getFdPersons()));
                } else {
                    if (!post.getFdIsAvailable()) {
                        JSONObject oldPostEle = getContentObj(omsPost);
                        //岗位变成无效，旧的成员以及部门领导对应的部门需要更新
                        syncPersonIds.addAll(oldPostEle.getJSONArray("persons"));
                        if (deptLeaderMap.containsKey(post.getFdId())) {
                            List<String> ldps = deptLeaderMap
                                    .get(post.getFdId());
                            syncDeptIds.addAll(ldps);
                        }
                        thirdDingOmsPostService.delete(omsPost);
                    } else {
                        calcNeedSyncElems(post, omsPost, syncDeptIds, syncPersonIds);
                    }
                }
                JSONObject postEleNew = buildOmsPostContent(post);
                if (loop > 0 && (loop % 200 == 0)) {
                    psupdate_oms_post.executeBatch();
                    psinsert_oms_post.executeBatch();
                    conn.commit();
                }
                if (omsPost == null) {
                    psinsert_oms_post.setString(1, post.getFdId());
                    psinsert_oms_post.setString(2, post.getFdName());
                    psinsert_oms_post.setString(3, postEleNew.toString());
                    psinsert_oms_post.addBatch();
                } else {
                    psupdate_oms_post.setString(1, post.getFdName());
                    psupdate_oms_post.setString(2, postEleNew.toString());
                    psupdate_oms_post.setString(3, post.getFdId());
                    psupdate_oms_post.addBatch();
                }
                loop++;
            }
            psupdate_oms_post.executeBatch();
            psinsert_oms_post.executeBatch();
            conn.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            JdbcUtils.closeStatement(psinsert_oms_post);
            JdbcUtils.closeStatement(psupdate_oms_post);
            JdbcUtils.closeConnection(conn);
        }
    }

    private void addRelation(SysOrgElement element, String appPkId, String type, String unionId, String accountType) throws Exception {
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            OmsRelationModel model = new OmsRelationModel();
            model.setFdEkpId(element.getFdId());
            model.setFdAppPkId(appPkId);
            model.setFdAppKey(getAppKey());
            model.setFdType(type);
            if ("8".equals(type)) {
                model.setFdUnionId(unionId);
                model.setFdAccountType(accountType);
            }
            omsRelationService.add(model);
            relationMap.put(model.getFdEkpId(), model.getFdAppPkId());
            TransactionUtils.getTransactionManager().commit(status);
        } catch (Exception e) {
            logger.error("---保存中间映射表失败---", e);
            if (status != null) {
                try {
                    TransactionUtils.getTransactionManager().rollback(status);
                } catch (Exception ex) {
                    logger.error("---事务回滚出错---", ex);
                }
            }
        }
    }

    private String getAppKey() {
        return StringUtil.isNull(DING_OMS_APP_KEY) ? "default" : DING_OMS_APP_KEY;
    }

    /**
     * 判断是否是顶级部门，以及是否启用了顶级部门同步
     *
     * @param rootId
     * @return
     */
    private boolean checkRootNeedSync(String rootId) {
        if (syncRootIdsMap == null || syncRootIdsMap.isEmpty()) {
            return true;
        }
        if ("true".equals(DingConfig.newInstance().getDingOmsRootFlag())) {
            return true;
        } else if (DingConfig.newInstance().getDingOrgId().contains(rootId)) {
            return false;
        }
        return true;
    }

    /**
     * 获取需要同步的顶级组织，包括机构的下级机构
     * 如果没有设置同步范围，则返回null
     *
     * @return
     * @throws Exception
     */
    private Map<String, String> initAllSyncRootIdsMap() throws Exception {
        String dingOrgId = DingConfig.newInstance().getDingOrgId();
        if (StringUtil.isNull(dingOrgId)) {
            return null;
        }
        syncRootIdsMap = new HashMap<>();
        Set<String> dingOrgIdSet = new HashSet<>(Arrays.asList(dingOrgId.split(";")));
        for (String fdId : dingOrgIdSet) {
            SysOrgElement element = (SysOrgElement) sysOrgElementService.findByPrimaryKey(fdId);
            if (element != null) {
                syncRootIdsMap.put(element.getFdId(), element.getFdHierarchyId());
            }
        }
        List allOrgChildren = sysOrgElementService.findList("fdOrgType=1 and fdIsAvailable=1", null);
        for (int i = 0; i < allOrgChildren.size(); i++) {
            SysOrgElement org = (SysOrgElement) allOrgChildren.get(i);
            SysOrgElement parent = org.getFdParent();
            while (parent != null) {
                if (dingOrgIdSet.contains(parent.getFdId())) {
                    syncRootIdsMap.put(org.getFdId(), org.getFdHierarchyId());
                    break;
                }
                parent = parent.getFdParent();
            }
        }
        return syncRootIdsMap;
    }

    private void terminate() throws Exception {
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            int delomserror = thirdDingOmsErrorService.deleteEkpRecord();
            logger.info("删除上次异常同步数据：" + delomserror + "条");
            for (ThirdDingOmsError error : syncErrorRecords) {
                thirdDingOmsErrorService.add(error);
            }
            logger.info("添加本次异常同步数据：" + syncErrorRecords.size() + "条");

            if (personSyncSuccess && syncTime > 0) {
                String updateTime = DateUtil.convertDateToString(new Date(syncTime), "yyyy-MM-dd HH:mm:ss.SSS");
                DingOmsConfig dingOmsConfig = new DingOmsConfig();
                dingOmsConfig.setLastUpdateTime(updateTime);
                dingOmsConfig.save();
            }
            TransactionUtils.getTransactionManager().commit(status);
        } catch (Exception e) {
            if (status != null) {
                TransactionUtils.getTransactionManager().rollback(status);
            }
            throw e;
        }
    }

    /**
     * 更新钉钉管理员的映射关系
     */
    private void adminHandle() throws Exception {
        String logInfo = "钉钉管理员";
        JSONObject ret = dingApiService.getAdmin();
        if (ret == null) {
            logInfo += ",不可预知的错误(可能是网络或者钉钉接口异常，无法返回成功或者失败信息)!";
        } else {
            if (ret.getInt("errcode") == 0) {
                JSONArray ja = ret.getJSONArray("adminList");
                if (ja == null) {
                    logInfo += " ,管理员列表为空";
                } else {
                    JSONObject jo = null;
                    JSONObject rjo = null;
                    for (int i = 0; i < ja.size(); i++) {
                        jo = ja.getJSONObject(i);
                        //如果在映射表中则忽略
                        if (relationMap.containsKey(jo.getString("userid"))) {
                            logInfo += " ,存在映射(" + jo.getString("userid") + ")";
                            continue;
                        }

                        rjo = dingApiService.userGet_v2(jo.getString("userid"), null);
                        String type = "common";
                        String unionId = "";
                        try {
                            rjo = rjo.getJSONObject("result");
                            if (rjo.getBoolean("exclusive_account")) {
                                type = rjo.getString("exclusive_account_type");
                            }
                            unionId = getUnionId(rjo);
                        } catch (Exception e) {
                            logger.warn(e.getMessage(), e);
                        }
                        if (rjo == null || !rjo.containsKey("mobile") || StringUtil.isNull(rjo.getString("mobile"))) {
                            continue;
                        }
                        SysOrgPerson pls = (SysOrgPerson) sysOrgPersonService
                                .findFirstOne("fdMobileNo='" + rjo.getString("mobile") + "' and fdIsAvailable = 1", null);
                        if (pls != null) {
                            OmsRelationModel model = (OmsRelationModel) omsRelationService.findFirstOne("fdEkpId='" + pls.getFdId() + "'", null);
                            if (model == null) {
                                model = new OmsRelationModel();
                                model.setFdEkpId(pls.getFdId());
                                model.setFdAppPkId(jo.getString("userid"));
                                model.setFdAppKey(getAppKey());
                                model.setFdUnionId(unionId);
                                model.setFdAccountType(type);
                                omsRelationService.add(model);
                                logInfo += " ,create (" + pls.getFdName() + ")";
                            } else {
                                if (!jo.getString("userid").equals(model.getFdAppPkId())) {
                                    model.setFdAppPkId(jo.getString("userid"));
                                    model.setFdAppKey(getAppKey());
                                    model.setFdUnionId(unionId);
                                    model.setFdAccountType(type);
                                    omsRelationService.update(model);
                                }
                                logInfo += " ,更新(" + pls.getFdName() + ")";
                            }
                        } else {
                            logInfo += " ,通过钉钉的手机号无法在EKP中找到用户:" + rjo.getString("name");
                        }
                    }
                }
            } else {
                logInfo += " 失败,出错信息：" + ret.getString("errmsg");
            }
        }
        logger.error(logInfo);
        log(logInfo);
    }

    class PersonRunner implements Runnable {
        private final List<String> personIds;
        private CountDownLatch countDownLatch;
        private Map<String, Map<String, String>> ppMap;

        public PersonRunner(List<String> personIds, CountDownLatch countDownLatch, Map<String, Map<String, String>> ppMap) {
            this.personIds = personIds;
            this.countDownLatch = countDownLatch;
            this.ppMap = ppMap;
        }

        @Override
        public void run() {
            try {
                handlePerson(personIds, ppMap);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                personSyncSuccess = false;
            } finally {
                countDownLatch.countDown();
            }
        }
    }

    /**
     * @throws Exception 获取需要同步的机构、部门和人员
     */
    private void getSyncData(Set<String> syncDepts, Set<String> syncPersons) throws Exception {
        // 异常数据表的获取，优先处理
        String where = "fdOms='ekp' and fdEkpType in ('1','2')";
        List<String> ids = thirdDingOmsErrorService.findValue("fdEkpId", where, null);
        syncDepts.addAll(ids);

        where = "fdOms='ekp' and fdEkpType='8'";
        ids = thirdDingOmsErrorService.findValue("fdEkpId", where, null);
        syncPersons.addAll(ids);

        // 获取人员
        List<String> persons = getData(ORG_TYPE_PERSON, "fdId");
        if (persons != null && !persons.isEmpty()) {
            syncPersons.addAll(persons);
        }
        // 获取部门
        List<String> depts = getData(ORG_TYPE_DEPT, "fdId");
        if (depts != null && !depts.isEmpty()) {
            syncDepts.addAll(depts);
        }
        // 根部门每次都需要更新，因为当增加了部门时，要更新可查看范围
        Set<String> rootDepts = findRootDepts();
        syncDepts.addAll(rootDepts);

        // 获取历史岗位信息
        handleUpdatePost(syncDepts, syncPersons);

    }

    /**
     * 构建同步范围where条件，范围外的无效记录也需要更新
     *
     * @return
     * @throws Exception
     */
    private String buildSynchroScopeBlock(HQLInfo info) throws Exception {
        if (syncRootIdsMap == null || syncRootIdsMap.isEmpty()) {
            return "";
        }
        String likeBlock = "";
        for (String hierId : syncRootIdsMap.values()) {
            likeBlock += " or fdHierarchyId like '" + hierId + "%'";
        }
        likeBlock = "(" + likeBlock.substring(4) + " or fdIsAvailable = :avail)";
        info.setParameter("avail", false);
        return likeBlock;
    }

    /**
     * @param type
     * @return
     * @throws Exception 根据传入的类型获取数据
     */
    private List getData(int type, String selectBlcok) throws Exception {
        List rtnList = new ArrayList();
        HQLInfo info = new HQLInfo();
        String sql = "1=1";
        String scopeLikeBlock = buildSynchroScopeBlock(info);
        if (StringUtil.isNotNull(scopeLikeBlock)) {
            sql = scopeLikeBlock;
        }
        DingOmsConfig dingOmsConfig = new DingOmsConfig();
        String lastUpdateTime = dingOmsConfig.getLastUpdateTime();
        if (StringUtil.isNotNull(lastUpdateTime)) {
            Date date = DateUtil.convertStringToDate(lastUpdateTime, "yyyy-MM-dd HH:mm:ss.SSS");
            sql += " and fdAlterTime>:beginTime";
            info.setParameter("beginTime", date);
        }
        info.setOrderBy("fdAlterTime desc");
        if (StringUtil.isNotNull(selectBlcok)) {
            info.setSelectBlock(selectBlcok);
        }
        if (type == ORG_TYPE_PERSON) {
            info.setWhereBlock(sql + " and fdOrgType=" + ORG_TYPE_PERSON);
            rtnList = sysOrgPersonService.findList(info);
        } else if (type == ORG_TYPE_ORG || type == ORG_TYPE_DEPT) {
            info.setWhereBlock(sql + " and fdOrgType in (" + ORG_TYPE_ORG + "," + ORG_TYPE_DEPT + ")");
            rtnList = sysOrgElementService.findList(info);
        } else if (type == ORG_TYPE_POST) {
            info.setWhereBlock(sql + " and fdOrgType=" + ORG_TYPE_POST);
            rtnList = sysOrgElementService.findList(info);
        }
        return rtnList;
    }

    private void addOmsErrors(SysOrgElement ele, String desc, String oper) throws Exception {
        if (ele == null) {
            return;
        }
        ThirdDingOmsError error = new ThirdDingOmsError();
        error.setFdOms("ekp");
        error.setFdEkpId(ele.getFdId());
        error.setFdEkpName(ele.getFdName());
        error.setFdEkpType(ele.getFdOrgType().toString());
        error.setFdDesc(desc);
        error.setFdOper(oper);
        syncErrorRecords.add(error);
    }

    private void updateErrorData() throws Exception {
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            updateErrorDataInner();
            TransactionUtils.getTransactionManager().commit(status);
        } catch (Exception e) {
            if (status != null) {
                TransactionUtils.getTransactionManager().rollback(status);
            }
            throw e;
        }
    }

    /**
     * 获取钉钉中上级部门下的同名子部门
     *
     * @param array
     * @param deptName
     * @return
     */
    private JSONObject getSameNameDept(JSONArray array, String deptName) {
        if (array == null || array.isEmpty()) {
            return null;
        }
        for (int i = 0; i < array.size(); i++) {
            JSONObject deptObj = array.getJSONObject(i);
            if (deptName.equals(deptObj.getString("name"))) {
                return deptObj;
            }
        }
        return null;
    }

    private void updateErrorDataPerson() throws Exception {
        // 人员处理
        String einfo = "手机号码在公司中已存在 userid:";
        List<ThirdDingOmsError> errors = thirdDingOmsErrorService.findList("fdOms='ekp' and fdEkpType='8'", null);
        for (ThirdDingOmsError error : errors) {
            String duserid = relationMap.get(error.getFdEkpId());
            if (StringUtil.isNull(duserid)) {
                continue;
            }
            int index = error.getFdDesc().indexOf(einfo);
            if (index == -1) {
                continue;
            }
            JSONObject jo = dingApiService.userGet(duserid, error.getFdEkpId());
            //60121：找不到该用户。原来的duserid在钉钉中已经找不到了，则把映射表中的钉钉id更新成新的id
            //比如，原来映射表中的钉钉id是a，然后同步这条记录的时候，钉钉返回对应的手机号已经给b用了；如果钉钉上找不到a了，则把映射表中的id改成b
            if (jo != null && jo.getInt("errcode") == 60121) {
                duserid = error.getFdDesc().substring(index + einfo.length());
                OmsRelationModel model = omsRelationService.findByEkpId(error.getFdEkpId());
                logger.warn("更新人员的映射关系，ekpid:" + error.getFdEkpId() + "，旧钉钉ID：" + model.getFdAppPkId() + "，新钉钉ID:" + duserid);
                model.setFdAppPkId(duserid);
                omsRelationService.update(model);
                relationMap.put(model.getFdEkpId(), model.getFdAppPkId());
            }
        }
    }

    private void updateErrorDataDept() throws Exception {
        // 部门处理
        List<ThirdDingOmsError> errors = thirdDingOmsErrorService.findList("fdOms='ekp' and fdEkpType in ('1','2')", null);
        String ddeptid = null;
        for (ThirdDingOmsError error : errors) {
            ddeptid = relationMap.get(error.getFdEkpId());
            if (StringUtil.isNull(ddeptid)) {
                continue;
            }
            // 60008:父部门下该部门名称已存在
            if (error.getFdDesc().indexOf("60008") != -1) {
                //父部门下该部门名称已存在，原因是中间表数据丢失导致重新新建，处理方式：删除父部门下重复的部门（如果无部门成员和子部门）
                SysOrgElement dept = (SysOrgElement) sysOrgElementService.findByPrimaryKey(error.getFdEkpId(), null, true);
                if (dept == null || dept.getFdParent() == null) {
                    continue;
                }
                ddeptid = relationMap.get(dept.getFdParent().getFdId());
                if (StringUtil.isNull(ddeptid)) {
                    continue;
                }
                JSONObject jo = dingApiService.departsSubGet(ddeptid);
                if (jo != null && jo.getInt("errcode") == 0) {
                    JSONObject deptObj = getSameNameDept(jo.getJSONArray("department"), dept.getFdName());
                    if (deptObj != null) {
                        logger.warn("删除钉钉中的重名部门，id:" + deptObj.getString("id") + ",名称:" + dept.getFdName());
                        dingApiService.departDelete(deptObj.getString("id"));
                    }
                }
                continue;
            }

            JSONObject jo = dingApiService.departGet(Long.parseLong(ddeptid));
            //处理部门不存在和父部门不存在的问题。60003：部门不存在
            if (jo == null || jo.getInt("errcode") != 60003) {
                continue;
            }
            SysOrgElement dept = (SysOrgElement) sysOrgElementService.findByPrimaryKey(error.getFdEkpId(), null, true);
            if (dept == null || dept.getFdParent() == null) {
                continue;
            }
            ddeptid = relationMap.get(dept.getFdParent().getFdId());
            if (StringUtil.isNull(ddeptid)) {
                continue;
            }
            //判断父部门在钉钉中是否存在
            jo = dingApiService.departsSubGet(ddeptid);
            if (jo == null || jo.getInt("errcode") != 0) {
                continue;
            }
            JSONObject deptObj = getSameNameDept(jo.getJSONArray("department"), dept.getFdName());
            if (deptObj != null) {
                OmsRelationModel model = omsRelationService.findByEkpId(error.getFdEkpId());
                logger.warn("更新同名部门的映射关系，ekpid:" + error.getFdEkpId() + ",名称:" + dept.getFdName() + "，旧钉钉ID：" + model.getFdAppPkId() + "，新钉钉ID:" + deptObj.getString("id"));
                model.setFdAppPkId(deptObj.getString("id"));
                omsRelationService.update(model);
                relationMap.put(model.getFdEkpId(), model.getFdAppPkId());
            }
        }
    }

    /**
     * 处理上次同步失败的记录，主要是对手机号已存在和部门名称已存在的场景做处理
     *
     * @throws Exception
     */
    private void updateErrorDataInner() throws Exception {
        updateErrorDataPerson();
        updateErrorDataDept();
    }

    private void log(String msg) {
        //logger.debug("【EKP组织架构同步到钉钉】" + msg);
        if (this.jobContext != null) {
            jobContext.logMessage(msg);
        }
    }

}
