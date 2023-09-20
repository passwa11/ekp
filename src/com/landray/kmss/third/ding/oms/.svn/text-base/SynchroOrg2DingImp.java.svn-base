package com.landray.kmss.third.ding.oms;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.framework.spring.event.interfaces.IEventCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.organization.event.SysOrgElementEcoAddEvent;
import com.landray.kmss.sys.organization.event.SysOrgElementEcoUpdateEvent;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.*;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.webservice.SysOrgWebserviceConstant;
import com.landray.kmss.sys.organization.webservice.out.ISysSynchroGetOrgWebService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.third.ding.model.ThirdDingOmsError;
import com.landray.kmss.third.ding.model.ThirdDingOmsPost;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingOmsErrorService;
import com.landray.kmss.third.ding.service.IThirdDingOmsPostService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.TransactionStatus;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SynchroOrg2DingImp implements SynchroOrg2Ding, DingConstant,
        SysOrgConstant, SysOrgWebserviceConstant, ApplicationListener,
        IEventMulticasterAware {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SynchroOrg2DingImp.class);

    private SysQuartzJobContext jobContext = null;

    private DingOmsConfig dingOmsConfig = null;
    private String lastUpdateTime = null;

    // 需要同步的所有组织架构元素Id集合
    private Set<Object> rootOrgChildren = null;

    // EKP钉钉映射表:key=ekpId;value=钉钉userId
    private volatile Map<String, String> relationMap = null;
    // 岗位缓存表
    private Map<String, ThirdDingOmsPost> omsPostMap = null;

    private Map<String, JSONObject> createDeptMap = null; // 存放新建部门的信息，以便为“仅新增时同步”功能更新部门相关信息

    private DingApiService dingApiService = null;

    private ISysSynchroGetOrgWebService sysSynchroGetOrgWebService;

    public ISysSynchroGetOrgWebService getSysSynchroGetOrgWebService() {
        if (sysSynchroGetOrgWebService == null) {
            sysSynchroGetOrgWebService = (ISysSynchroGetOrgWebService) SpringBeanUtil
                    .getBean("sysSynchroGetOrgWebService");
        }
        return sysSynchroGetOrgWebService;
    }

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

    private ISysOrgCoreService sysOrgCoreService;

    public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
        this.sysOrgCoreService = sysOrgCoreService;
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

    private IEventMulticaster multicaster;

    @Override
    public void setEventMulticaster(IEventMulticaster multicaster) {
        this.multicaster = multicaster;

    }

    private String getDingRootDeptId() {
        return StringUtil.isNull(DingConfig.newInstance().getDingDeptid()) ? "1"
                : DingConfig.newInstance().getDingDeptid();
    }

    private ISysAppConfigService sysAppConfigService;

    public ISysAppConfigService getSysAppConfigService() {
        if (sysAppConfigService == null) {
            sysAppConfigService = (ISysAppConfigService) SpringBeanUtil
                    .getBean("sysAppConfigService");
        }
        return sysAppConfigService;
    }

    private CountDownLatch countDownLatch;
    private long syncTime = 0L;
    private Set<SysOrgElement> syncDepts = null;
    private Set<ThirdDingOmsPost> omsPost = null;
    private List<SysOrgElement> syncPosts = null;
    private Set<SysOrgPerson> syncPersons = null;
    private volatile Set<ThirdDingOmsError> errors = null;
    private Session session = null;
    private Map<String, Map<String, String>> ppMap = null;
    private Map<String, String> ppersonMap = null;
    private Map<String, List<SysOrgElement>> deptLeaderMap = null;
    private long allcount = 0L;
    private boolean pesonFlag = true;
    private static boolean locked = false;
    // 处理组织成员查看范围权限
    private Map<String, SysOrgElementRange> rangeMap = null;
    // 处理部门隐藏后的可见性
    private Map<String, SysOrgElementHideRange> hideRangeMap = null;
    // 同步的顶级机构或部门
    private Map<String, SysOrgElement> synRootDepts = null;

    @Override
    public void triggerSynchro(SysQuartzJobContext context) {
        String temp = "";
        this.jobContext = context;
        if (locked) {
            temp = "存在运行中的钉钉组织架构同步任务，当前任务中断...";
            logger.info(temp);
            context.logMessage(temp);
            return;
        }
        if (!"true".equals(DingConfig.newInstance().getDingEnabled())) {
            temp = "钉钉集成已经关闭，故不同步数据";
            logger.info(temp);
            context.logMessage(temp);
            return;
        }
        if (StringUtil.isNotNull(DingConfig.newInstance().getSyncSelection())) {
            if (!"1".equals(DingConfig.newInstance().getSyncSelection())) {
                temp = "钉钉集成-通讯录配置-同步选择-从本系统同步到钉钉未开启，故不同步数据";
                logger.info(temp);
                context.logMessage(temp);
                return;
            }
        } else {
            if (!"true".equals(DingConfig.newInstance().getDingOmsOutEnabled())) {
                temp = "钉钉组织架构接出已经关闭，故不同步数据";
                logger.debug(temp);
                context.logMessage(temp);
                return;
            }
        }
        try {
            dingOmsConfig = new DingOmsConfig();
            lastUpdateTime = dingOmsConfig.getLastUpdateTime();
            temp = "==========开始同步(" + lastUpdateTime + ")钉钉组织数据===============";
            logger.debug(temp);
            context.logMessage(temp);

            locked = true;
            pesonFlag = true;
            long alltime = System.currentTimeMillis();

            // 初始化数据
            long caltime = System.currentTimeMillis();
            init();
            temp = "初始化数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);
            context.logMessage(temp);

            // 获取EKP组织数据
            caltime = System.currentTimeMillis();
            getSyncData();
            temp = "获取EKP组织数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);
            context.logMessage(temp);

            // 添加钉钉的部门数据
            caltime = System.currentTimeMillis();
            handleDept(new ArrayList(syncDepts));
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
            handleUpdateDept(new ArrayList(syncDepts));
            temp = "更新钉钉部门层级和部门主管数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);
            context.logMessage(temp);

            // 先迁移人员，后删除部门，部门中有人员，不能删除
            caltime = System.currentTimeMillis();
            handleDelDept(new ArrayList(syncDepts));
            temp = "先迁移人员，后删除部门，部门中有人员，不能删除数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
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
            locked = false;
            relationMap = null;
            omsPostMap = null;
            syncDepts = null;
            syncPersons = null;
            syncPosts = null;
            rootOrgChildren = null;
            ppMap = null;
            ppersonMap = null;
            deptLeaderMap = null;
        }
    }

    /**
     * 所有根组织
     *
     * @throws Exception
     */
    private List<SysOrgElement> findRootDepts() throws Exception {
        // 查询所有跟机构
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock(" fd_parentid = null and fd_is_available = 1 and fd_is_business = 1 and (fd_org_type = 1 or fd_org_type = 2)");
        List<SysOrgElement> depts = sysOrgElementService.findList(hqlInfo);
        return depts;
    }


    private void handlePersonSync(Set<SysOrgPerson> persons) throws Exception {
        String size = DingConfig.newInstance().getDingSize();
        if (StringUtil.isNull(size)) {
            size = "2000";
        }
        int rowsize = Integer.parseInt(size);
        int count = persons.size() % rowsize == 0 ? persons.size() / rowsize : persons.size() / rowsize + 1;
        List<SysOrgPerson> allpersons = new ArrayList<SysOrgPerson>(persons);
        logger.debug("人员总数据:" + allpersons.size() + "条,将执行" + count + "次人员分批同步,每次" + size + "条");
        countDownLatch = new CountDownLatch(count);
        List<SysOrgPerson> temppersons = null;
        for (int i = 0; i < count; i++) {
            logger.debug("执行第" + (i + 1) + "批");
            if (persons.size() > rowsize * (i + 1)) {
                temppersons = allpersons.subList(rowsize * i, rowsize * (i + 1));
            } else {
                temppersons = allpersons.subList(rowsize * i, persons.size());
            }
            taskExecutor.execute(new PersonRunner(temppersons));
        }
        try {
            countDownLatch.await(3, TimeUnit.HOURS);
        } catch (InterruptedException exc) {
            exc.printStackTrace();
            logger.error("{}", exc);
        }
        logger.debug("本次共同步总人员数据:" + allcount + "条");
        log("本次共同步总人员数据:" + allcount + "条");
    }

    private void init() throws Exception {
        allcount = 0L;
        syncDepts = new HashSet<SysOrgElement>(500);
        syncPosts = new ArrayList<SysOrgElement>(1000);
        omsPost = new HashSet<ThirdDingOmsPost>(500);
        syncPersons = new HashSet<SysOrgPerson>(2000);
        errors = new HashSet<ThirdDingOmsError>(100);
        omsPostMap = new HashMap<String, ThirdDingOmsPost>(500);
        relationMap = new HashMap<String, String>(5000);
        createDeptMap = new HashMap<String, JSONObject>();
        dingApiService = DingUtils.getDingApiService();
        ppMap = new HashMap<String, Map<String, String>>();
        ppersonMap = new HashMap<String, String>();
        deptLeaderMap = new HashMap<String, List<SysOrgElement>>();
        session = omsRelationService.getBaseDao().getHibernateSession();
        rangeMap = new HashMap<String, SysOrgElementRange>(1000);
        hideRangeMap = new HashMap<>(1000);
        synRootDepts = new HashMap<String, SysOrgElement>(500);
        // 需要同步的组织架构列表初始化（直接读取数据库）
        List<SysOrgElement> allOrgChildrn = getAllOrgByRootOrg();
        rootOrgChildren = new HashSet<Object>(5000);
        if (StringUtil.isNotNull(DingConfig.newInstance().getDingOrgId())) {
            String[] orgIds = DingConfig.newInstance().getDingOrgId().split(";");
            for (String orgId : orgIds) {
                SysOrgElement rootOrg = sysOrgCoreService.findByPrimaryKey(orgId);
                if (!allOrgChildrn.contains(rootOrg)) {
                    allOrgChildrn.add(rootOrg);
                }
                for (SysOrgElement org : allOrgChildrn) {
                    rootOrgChildren.addAll(sysOrgCoreService.findAllChildrenItem(org,
                            SysOrgElement.ORG_TYPE_ORGORDEPT | SysOrgElement.ORG_TYPE_PERSON, "fdId"));
                }
            }
            logger.debug("组织机构所有数据（有效）：" + rootOrgChildren.size() + "条");
        } else {
            logger.warn("钉钉配置没有配同步的根机构部门，默认同步所有组织信息");
            // jobContext.logMessage("钉钉配置没有配同步的根机构部门，默认同步所有组织信息");
        }
        // 获取所有同步的顶级机构或部门
        if (StringUtil.isNotNull(DingConfig.newInstance().getDingOrgId())) {
            String[] orgIds = DingConfig.newInstance().getDingOrgId()
                    .split(";");
            Set<SysOrgElement> tmpSet = new HashSet<SysOrgElement>();
            List<SysOrgElement> rootList = sysOrgCoreService
                    .findByPrimaryKeys(orgIds);
            if (!"true".equals(DingConfig.newInstance().getDingOmsRootFlag())) {
                for (SysOrgElement ele : rootList) {
                    List<SysOrgElement> newRootList = sysOrgCoreService
                            .findDirectChildren(ele.getFdId(),
                                    ORG_TYPE_ORGORDEPT);
                    tmpSet.addAll(newRootList);
                }
            } else {
                tmpSet.addAll(rootList);
            }
            for (SysOrgElement ele : tmpSet) {
                synRootDepts.put(ele.getFdId(), ele);
            }

        } else {
            List<SysOrgElement> rootList = sysOrgCoreService
                    .findDirectChildren(null, ORG_TYPE_ORGORDEPT);
            for (SysOrgElement ele : rootList) {
                synRootDepts.put(ele.getFdId(), ele);
            }
        }

        // 关系映射表初始化
        List list = omsRelationService.findList("fdAppKey='" + getAppKey() + "'", null);
        for (int i = 0; i < list.size(); i++) {
            OmsRelationModel model = (OmsRelationModel) list.get(i);
            relationMap.put(model.getFdEkpId(), model.getFdAppPkId());
        }
        // 异常数据处理
        updateErrorData();
        // 岗位缓存的初始化
        List<ThirdDingOmsPost> omsPosts = thirdDingOmsPostService.findList(null,
                null);
        for (ThirdDingOmsPost op : omsPosts) {
            if (StringUtil.isNotNull(op.getFdContent())) {
                op.setDocContent(op.getFdContent());
                op.setFdContent("");
                thirdDingOmsPostService.update(op);
            }
            omsPostMap.put(op.getFdId(), op);
        }

        String getPostSql = "select pp.fd_personid,pp.fd_postid,ele.fd_name,ele.fd_parentid from sys_org_post_person pp,sys_org_element ele where pp.fd_postid=ele.fd_id";
        String postOrder = DingConfig.newInstance().getOrg2dingPositionOrder();
        if (StringUtil.isNotNull(postOrder) && "postOrder".equals(postOrder)) {
            getPostSql = "select pp.fd_personid,pp.fd_postid,ele.fd_name,ele.fd_parentid from sys_org_post_person pp,sys_org_element ele where pp.fd_postid=ele.fd_id order by case when ele.fd_order is null then 1 else 0 end asc,ele.fd_order ASC";
        }
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
                    if (pps[3] == null) {
                        tempMap.put("parentids", tempMap.get("parentids"));
                    } else {
                        tempMap.put("parentids", tempMap.get("parentids") + ";" + pps[3].toString());
                    }
                } else {
                    tempMap = new HashMap<String, String>();
                    tempMap.put("postids", pps[1].toString());
                    tempMap.put("names", pps[2].toString());
                    if (pps[3] == null) {
                        tempMap.put("parentids", "");
                    } else {
                        tempMap.put("parentids", pps[3].toString());
                    }
                }
                ppMap.put(pps[0].toString(), tempMap);
            }
            if (pps[0] != null && pps[1] != null) {
                // 已岗位为角度构建的岗位与人的关系
                if (ppersonMap.containsKey(pps[1].toString())) {
                    ppersonMap.put(pps[1].toString(), ppersonMap.get(pps[1].toString()) + ";" + pps[0].toString());
                } else {
                    ppersonMap.put(pps[1].toString(), pps[0].toString());
                }
            }
        }
        // 部门主管的数据缓存
        List<SysOrgElement> leaders = sysOrgElementService
                .findList("fdOrgType in (1,2) and hbmThisLeader.fdId is not null", null);
        String tempid = null;
        List<SysOrgElement> templeaders = null;
        for (SysOrgElement leader : leaders) {
            tempid = leader.getHbmThisLeader().getFdId();
            if (deptLeaderMap.containsKey(tempid)) {
                templeaders = deptLeaderMap.get(tempid);
            } else {
                templeaders = new ArrayList<SysOrgElement>();
            }
            templeaders.add(leader);
            deptLeaderMap.put(tempid, templeaders);
        }
    }

    private boolean exist(String id) {
        if (rootOrgChildren == null) {
            return true;
        }
        // 当配置的同步部门为 空时，默认同步
        if (StringUtil.isNull(DingConfig.newInstance().getDingOrgId())) {
            return true;
        }
        return rootOrgChildren.contains(id);
    }

    private String updateDept(SysOrgElement ekpdept) throws Exception {

        JSONObject dept = new JSONObject();
        boolean isCreate = false; // 部门是否本次同步新建的
        String did = relationMap.get(ekpdept.getFdId());
        dept.accumulate("id", did);
        logger.debug("更新部门：" + ekpdept.getFdName() + " did:" + did);
        if (createDeptMap != null && createDeptMap.containsKey(did)) {
            logger.debug("更新本次同步新建的部门信息：" + createDeptMap.get(did));
            isCreate = true;
        }

        DingConfig dingConfig = DingConfig.newInstance();

        String deptNameSynWay = dingConfig.getOrg2dingDeptNameSynWay();
        if (StringUtil.isNotNull(deptNameSynWay)) {
            logger.debug("deptNameSynWay:" + deptNameSynWay);
            if ("syn".equals(deptNameSynWay) || isCreate) {
                dept.accumulate("name", DingUtil.getString(ekpdept.getFdName(), 64));
            }
        } else {
            logger.debug("旧方式更新部门名称！");
            dept.accumulate("name", DingUtil.getString(ekpdept.getFdName(), 64));
        }

        // 上级部门
        String parentDeptSynWay = dingConfig.getOrg2dingDeptParentDeptSynWay();
        logger.debug("parentDeptSynWay:" + parentDeptSynWay);
        if (StringUtil.isNull(parentDeptSynWay)
                || (StringUtil.isNotNull(parentDeptSynWay)
                && "syn".equals(parentDeptSynWay))
                || isCreate) {

            String parentId = getDingRootDeptId();
            if (ekpdept.getFdParent() != null) {
                parentId = relationMap.get(ekpdept.getFdParent().getFdId());
            }
            if (StringUtil.isNull(parentId)) {
                parentId = getDingRootDeptId();
            }
            dept.accumulate("parentid", parentId);
        }


        // 部门群
        String oldCreateDeptGroup = dingConfig.getDingOmsCreateDeptGroup();
        if (StringUtil.isNotNull(oldCreateDeptGroup)) {
            logger.debug("旧方式创建部门群");
            if (!"true".equals(oldCreateDeptGroup)) {
                dept.accumulate("createDeptGroup", "false");
            } else {
                dept.accumulate("createDeptGroup", "true");
            }
        } else {
            logger.debug("新配置方式创建部门群");
            String o2d_groupSynWay = dingConfig.getOrg2dingDeptGroupSynWay();
            logger.debug("o2d_groupSynWay:" + o2d_groupSynWay);
            if ("syn".equalsIgnoreCase(o2d_groupSynWay) || (isCreate
                    && "addSyn".equalsIgnoreCase(o2d_groupSynWay))) {
                String o2d_groupAll = dingConfig.getOrg2dingDeptGroupAll();
                String o2d_group = dingConfig.getOrg2dingDeptGroup();
                logger.debug("o2d_groupAll:" + o2d_groupAll
                        + " o2d_group:" + o2d_group);
                if (StringUtil.isNotNull(o2d_groupAll)
                        && "true".equals(o2d_groupAll)) {
                    dept.accumulate("createDeptGroup", true);
                } else {
                    if (StringUtil.isNotNull(o2d_group)) {
                        String groupValue = getDeptProperty(
                                o2d_group, ekpdept);
                        logger.debug("groupValue:" + groupValue);
                        if ("true".equals(groupValue) || "1".equals(groupValue)
                                || "是".equals(groupValue)) {
                            dept.accumulate("createDeptGroup",
                                    true);
                        } else if ("false".equals(groupValue)
                                || "0".equals(groupValue)
                                || "否".equals(groupValue)) {
                            dept.accumulate("createDeptGroup",
                                    false);
                        } else {
                            logger.warn("创建部门群配置的字段值非true/false!");
                        }
                    }
                }
                if (dept.containsKey("createDeptGroup")) {
                    // 部门群是否包含子部门成员
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
                        logger.debug("groupContainSubDeptAll:" + o2d_groupAll
                                + " groupContainSubDeptKey:"
                                + groupContainSubDeptKey);
                        if (StringUtil.isNotNull(groupContainSubDeptAll)
                                && "true".equals(groupContainSubDeptAll)) {
                            dept.accumulate("groupContainSubDept", true);
                        } else {
                            if (StringUtil.isNotNull(groupContainSubDeptKey)) {
                                String groupContainSubDeptValue = getDeptProperty(
                                        groupContainSubDeptKey, ekpdept);
                                logger.debug("groupContainSubDeptValue:"
                                        + groupContainSubDeptValue);
                                if ("true".equalsIgnoreCase(
                                        groupContainSubDeptValue)
                                        || "1".equals(groupContainSubDeptValue)
                                        || "是".equals(
                                        groupContainSubDeptValue)) {
                                    dept.accumulate("groupContainSubDept",
                                            true);
                                } else if ("false".equalsIgnoreCase(
                                        groupContainSubDeptValue)
                                        || "0".equals(groupContainSubDeptValue)
                                        || "否".equals(
                                        groupContainSubDeptValue)) {
                                    dept.accumulate("groupContainSubDept",
                                            false);
                                } else {
                                    logger.warn(
                                            "部门群包含子部门成员配置的字段值非true/false或/0 或者  是/否!："
                                                    + groupContainSubDeptValue);
                                }
                            }

                        }
                    }
                }

            }

        }

        // 是否同步部门主管
        String dingDeptLeaderEnabled = dingConfig.getDingDeptLeaderEnabled();
        if (StringUtil.isNotNull(dingDeptLeaderEnabled)) {// 旧开关方式
            logger.debug("旧方式同步部门主管");
            if ("true".equals(dingDeptLeaderEnabled)) {
                String userIdList = getDeptManagerUseridList(ekpdept);
                dept.accumulate("deptManagerUseridList", userIdList);
            }
        } else {
            logger.debug("新配置方式同步部门主管");
            String o2d_deptManagerWay = dingConfig
                    .getOrg2dingDeptDeptManagerSynWay();
            logger.debug("o2d_deptManagerWay:" + o2d_deptManagerWay);
            if (StringUtil.isNotNull(o2d_deptManagerWay)) {
                if ("noSyn".equals(o2d_deptManagerWay)) {
                    logger.debug("部门主管同步功能未开启!");
                }
                // else if ("syn".equals(o2d_deptManagerWay)) {
                else if ("syn".equals(o2d_deptManagerWay) || (isCreate
                        && "addSyn".equalsIgnoreCase(o2d_deptManagerWay))) {
                    String userIdList = getDeptManagerUseridList(ekpdept);
                    logger.debug("ManagerUseridList:" + userIdList);
                    dept.accumulate("deptManagerUseridList", userIdList);
                }

            }
        }

        // 排序号
        String o2d_orderWay = dingConfig.getOrg2dingDeptOrderSynWay();
        if (StringUtil.isNull(o2d_orderWay)) {
            logger.debug("旧方式同步部门排序");
            if (ekpdept.getFdOrder() != null) {
                dept.accumulate("order", ekpdept.getFdOrder());
            }
        } else {
            logger.debug("新配置方式同步部门排序！");

            if ("syn".equalsIgnoreCase(o2d_orderWay) || (isCreate
                    && "addSyn".equalsIgnoreCase(o2d_orderWay))) {
                String o2d_order = dingConfig.getOrg2dingDeptOrder();
                logger.debug("o2d_deptOrder:" + o2d_order);
                if (StringUtil.isNotNull(o2d_order)) {

                    if ("fdOrder".equals(o2d_order)) {
                        dept.accumulate("order",
                                ekpdept.getFdOrder());
                    } else {
                        logger.debug("部门排序配置类fdOrder之外的字段，尝试获取改字段："
                                + o2d_order);
                        String orderValue = getDeptProperty(
                                o2d_order, ekpdept);
                        logger.debug("orderValue:" + orderValue);
                        if (StringUtil.isNotNull(orderValue)) {
                            dept.accumulate("order", orderValue);
                        } else {
                            logger.debug(
                                    "根据" + o2d_order + "字段获取的数据为空");
                            dept.accumulate("order", "");
                        }

                    }

                } else {
                    logger.debug("部门排序配置字段为空！");
                }

            }
        }

        boolean flag = true;
        if ("null".equals(dept.getString("id"))) {
            logger.error("dept id is null::" + dept);
            flag = false;
        }
        if (flag) {
            // 设置人员查看范围
            setRange(ekpdept, dept);
            logger.debug("更新部门信息:" + dept.toString());
            return dingApiService.departUpdate(dept);
        } else {
            return " error has null ::" + dept.toString();
        }
    }

    private String getDeptManagerUseridList(SysOrgElement element) throws Exception {
        StringBuilder userIdList = new StringBuilder();
        List<String> leaderIds = new ArrayList<String>();
        if (element.getHbmThisLeader() != null) {
            String leaderid = element.getHbmThisLeader().getFdId();
            if (element.getHbmThisLeader().getFdOrgType() == 8) {
                leaderIds.add(leaderid);
            } else {
                if (ppersonMap.containsKey(leaderid)) {
                    String[] lids = ppersonMap.get(leaderid).split("[,;]");
                    for (String lid : lids) {
                        if (StringUtil.isNull(lid)) {
                            continue;
                        }
                        leaderIds.add(lid);
                    }
                }
            }
            String deptDid = relationMap.get(element.getFdId());
            logger.debug("deptDid:" + deptDid);
            for (String personId : leaderIds) {
                if (relationMap.containsKey(personId)) {
                    try {
                        logger.debug("添加" + element.getFdName() + "(" + deptDid
                                + ")的部门主管信息：" + relationMap.get(personId));
                        // 先判断该用户是否在该部门下 userUpdate
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

                        userIdList.append(relationMap.get(personId) + "|");
                    } catch (Exception e) {
                        logger.error("添加部门主管过程中发生异常：" + element.getFdName());
                        logger.error(e.getMessage(), e);
                    }

                }
            }
        }
        return userIdList.toString().length() > 0 ? userIdList.toString().substring(0, userIdList.length() - 1)
                : userIdList.toString();
    }

    /**
     * 设置组织管理员可见
     *
     * @param dept
     * @param jodept
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/10/12 10:48 上午
     */
    private void setOrgAuthAdminRange(SysOrgElement dept, JSONObject jodept) throws Exception {
        List<SysOrgElement> list = dept.getAuthElementAdmins();
        if (CollectionUtils.isNotEmpty(list)) {
            StringBuffer deptPermits = new StringBuffer();
            StringBuffer userPermits = new StringBuffer();
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
     * 设置部门或机构的人员查看范围
     *
     * @param dept
     * @param jodept
     */
    private void setRange(SysOrgElement dept, JSONObject jodept) throws Exception {
        // 如果是外部组织的根组织，需要设置为隐藏，同时需要把组织管理员设置为可见
        if (SysOrgEcoUtil.IS_ENABLED_ECO && BooleanUtils.isTrue(dept.getFdIsExternal())
                && synRootDepts.containsKey(dept.getFdId())) {
            jodept.put("deptHiding", "true");
            setOrgAuthAdminRange(dept, jodept);
        }
        //设置可见性范围
        SysOrgElementRange range = rangeMap.get(dept.getFdId());
        if (range != null) {
            setRange(range, jodept);
        }
        //设置隐藏可见性
        setHideRange(dept, jodept);
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
        if (dept.getFdIsAvailable()) {
            SysOrgElementHideRange hideRange = new SysOrgElementHideRange();
            if ((!"true".equals(DingConfig.newInstance().getDingHideRangeEnabled()) && !BooleanUtils.isTrue(dept.getFdIsExternal())) || dept.getFdHideRange() == null) {
                hideRange.setFdIsOpenLimit(false);
                hideRange.setFdViewType(1);
            } else {
                hideRange = dept.getFdHideRange();
            }
            return hideRange;
        }
        return null;
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
        if ((fdHideRange == null || !BooleanUtils.isTrue(fdHideRange.getFdIsOpenLimit()))) {
            jsonObject.put("deptHiding", "false");
            return;
        }
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
     * 设置通讯录可见范围
     *
     * @param range
     * @param jodept
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/10/11 8:49 上午
     */
    private void setRange(SysOrgElementRange range, JSONObject jodept) throws Exception {
        if (range != null) {
            jodept.put("outerDept", String.valueOf(range.getFdIsOpenLimit()));
            Integer viewType = range.getFdViewType();
            if (viewType == null) {
                viewType = 1;
            }
            if (viewType == 2) {
                // 指定组织/人员
                String subType = range.getFdViewSubType();
                StringBuffer did = new StringBuffer();
                StringBuffer pid = new StringBuffer();
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
    }

    /**
     * 初始化部门或机构的查看范围权限，需要获取“部门人员查看通讯录范围”开关
     * <ul>
     * <li>有同步生态组织</li>
     * <li>开启：同步部门设置的查看范围权限，如果未设置，默认设置根组织为查看本组织</li>
     * <li>关闭：设置根组织为查看本组织（保证内外隔离）</li>
     *
     * <li>没有同步生态组织</li>
     * <li>开启：同步查看范围权限</li>
     * <li>关闭：不同步查看范围权限</li>
     * </ul>
     *
     * @param depts
     * @throws Exception
     */
    private void initRange(List<SysOrgElement> depts) throws Exception {
        SysOrgElement dept = null;
        List<SysOrgElement> newDepts = new ArrayList<SysOrgElement>();
        for (int n = 0; n < depts.size(); n++) {
            dept = depts.get(n);
            if (!exist(dept.getFdId())) {
                continue;
            }
            if (!isOmsRootOrg(dept.getFdId())) {
                continue;
            }
            if (dept.getFdIsAvailable()) {
                // 如果同步选项设置为不同步查看范围且是内部组织，或查看范围为空，设置为关闭
                SysOrgElementRange range = new SysOrgElementRange();
                if ((!"true".equals(DingConfig.newInstance().getDingRangeEnabled())
                        && !BooleanUtils.isTrue(dept.getFdIsExternal())) || dept.getFdRange() == null) {
                    range.setFdIsOpenLimit(false);
                    range.setFdViewType(1);
                } else {
                    SysOrgElementRange r = dept.getFdRange();
                    range.setFdIsOpenLimit(r.getFdIsOpenLimit());
                    range.setFdViewType(r.getFdViewType());
                    range.setFdOthers(r.getFdOthers());
                    range.setFdViewSubType(r.getFdViewSubType());
                    range.setFdElement(r.getFdElement());
                }
                rangeMap.put(dept.getFdId(), range);
                //是否需要同步部门隐藏性，需要根据配置值进行同步
                SysOrgElementHideRange hideRange = new SysOrgElementHideRange();
                if (!"true".equals(DingConfig.newInstance().getDingHideRangeEnabled()) || dept.getFdHideRange() == null) {
                    hideRange.setFdIsOpenLimit(false);
                    hideRange.setFdViewType(1);
                } else {
                    hideRange = dept.getFdHideRange();
                }
                hideRangeMap.put(dept.getFdId(), hideRange);
            }
        }
        // 同步顶级部门权限
        if (!newDepts.isEmpty()) {
            depts.addAll(newDepts);
        }
    }

    private void handleDept(List<SysOrgElement> depts) throws Exception {
        logger.debug("处理新增部门");
        // 先增加所有部门，且先挂到根机构下
        String logInfo = null;
        SysOrgElement dept = null;
        long count = 0L;
        if (CollectionUtils.isEmpty(depts)) {
            return;
        }
        for (int n = 0; n < depts.size(); n++) {
            dept = depts.get(n);
            if (!exist(dept.getFdId())) {
                continue;
            }
            if (!isOmsRootOrg(dept.getFdId())) {
                continue;
            }
            if (dept.getFdIsAvailable()) {
                // 新增
                if (!relationMap.keySet().contains(dept.getFdId())) {
                    JSONObject jodept = new JSONObject();
                    String name = DingUtil.getString(dept.getFdName(), (64 - (String.valueOf(n).length() + 1))) + "_" + n;
                    jodept.accumulate("name", name);// 增加部门挂在根下时，会出现重复名，在此加ID区分，会在更新操作时修改正确
                    jodept.accumulate("parentid", Integer.valueOf(getDingRootDeptId()));
                    String oldCreateDeptGroup = DingConfig.newInstance()
                            .getDingOmsCreateDeptGroup();
                    if (StringUtil.isNotNull(oldCreateDeptGroup)) {
                        logger.debug("旧方式创建部门群");
                        if (!"true".equals(DingConfig.newInstance()
                                .getDingOmsCreateDeptGroup())) {
                            jodept.accumulate("createDeptGroup", false);
                        } else {
                            jodept.accumulate("createDeptGroup", true);
                        }
                    } else {
                        logger.debug("新配置方式创建部门群");
                        String o2d_groupSynWay = DingConfig.newInstance()
                                .getOrg2dingDeptGroupSynWay();
                        logger.debug("o2d_groupSynWay:" + o2d_groupSynWay);
                        if ("syn".equalsIgnoreCase(o2d_groupSynWay)
                                || ("addSyn"
                                .equalsIgnoreCase(o2d_groupSynWay))) {
                            String o2d_groupAll = DingConfig.newInstance()
                                    .getOrg2dingDeptGroupAll();
                            String o2d_group = DingConfig.newInstance()
                                    .getOrg2dingDeptGroup();
                            logger.debug("o2d_groupAll:" + o2d_groupAll
                                    + " o2d_group:" + o2d_group);
                            if (StringUtil.isNotNull(o2d_groupAll)
                                    && "true".equals(o2d_groupAll)) {
                                jodept.accumulate("createDeptGroup", true);
                            } else {
                                if (StringUtil.isNotNull(o2d_group)) {
                                    String groupValue = getDeptProperty(
                                            o2d_group, dept);
                                    System.out.println(
                                            "groupValue:" + groupValue);
                                    if ("true".equals(groupValue)
                                            || "1".equals(groupValue)
                                            || "是".equals(groupValue)) {
                                        jodept.accumulate("createDeptGroup",
                                                "true");
                                    } else if ("false".equals(groupValue)
                                            || "0".equals(groupValue)
                                            || "否".equals(groupValue)) {
                                        jodept.accumulate("createDeptGroup",
                                                "false");
                                    } else {
                                        logger.warn(
                                                "创建部门群配置的字段值非true/false 1/0 是/否!");
                                    }
                                }
                            }
                        }
                    }

                    // 部门排序
                    String o2d_orderWay = DingConfig.newInstance()
                            .getOrg2dingDeptOrderSynWay();
                    if (StringUtil.isNull(o2d_orderWay)) {
                        logger.debug("旧方式同步部门排序");
                        if (dept.getFdOrder() != null) {
                            jodept.accumulate("order", dept.getFdOrder());
                        }
                    } else {
                        logger.debug("新配置方式同步部门排序！");

                        if ("syn".equalsIgnoreCase(o2d_orderWay)
                                || ("addSyn"
                                .equalsIgnoreCase(o2d_orderWay))) {
                            String o2d_order = DingConfig.newInstance()
                                    .getOrg2dingDeptOrder();
                            logger.debug("o2d_deptOrder:" + o2d_order);
                            if (StringUtil.isNotNull(o2d_order)) {

                                if ("fdOrder".equals(o2d_order)) {
                                    jodept.accumulate("order",
                                            dept.getFdOrder());
                                } else {
                                    logger.debug("部门排序配置类fdOrder之外的字段，尝试获取改字段："
                                            + o2d_order);
                                    String orderValue = getDeptProperty(
                                            o2d_order, dept);
                                    logger.debug("orderValue:" + orderValue);
                                    if (StringUtil.isNotNull(orderValue)) {
                                        jodept.accumulate("order", orderValue);
                                    } else {
                                        logger.debug(
                                                "根据" + o2d_order + "字段获取的数据为空");
                                        jodept.accumulate("order", "");
                                    }

                                }

                            } else {
                                logger.debug("部门排序配置字段为空！");
                            }


                        }
                    }

                    logInfo = "增加部门到钉钉" + dept.getFdName() + ", "
                            + dept.getFdId();
                    JSONObject ret = dingApiService.departCreate(jodept);
                    if (ret == null) {
                        logInfo += ",不可预知的错误(可能是网络或者钉钉接口异常，无法返回成功或者失败信息)!";
                        addOmsErrors(dept, logInfo, "add");
                    } else {
                        if (ret.getInt("errcode") == 0 || ret.getInt("errcode") == 60016) {
                            logger.debug("新建部门成功或者部门已经存在：" + jodept);
                            logger.debug("钉钉返回信息：" + ret);
                            String id = ret.getString("id");
                            createDeptMap.put(id, ret);
                            logInfo += " created,钉钉对应ID:" + id;
                            addRelation(dept, id, "2", null);
                        } else {
                            logInfo = " 失败,出错信息：" + ret.getString("errmsg");
                        }
                    }
                    logger.error(logInfo);
                    log(logInfo);
                }
            }
            count++;
        }
        logInfo = "处理部门（新增、删除）同步到钉钉的个数为:" + count + "条";
        log(logInfo);
    }

    private void handleUpdateDept(List<SysOrgElement> depts) throws Exception {
        // 更新所有部门，主要是更新关系
        String logInfo = null;
        String rtn = null;
        if (CollectionUtils.isEmpty(depts)) {
            return;
        }
        for (SysOrgElement dept : depts) {
            if (!exist(dept.getFdId())) {
                continue;
            }
            if (!isOmsRootOrg(dept.getFdId())) {
                continue;
            }
            if (dept.getFdIsAvailable()) {
                if (dept.getFdParent() == null) {
                    logInfo = "更新部门: " + dept.getFdName() + " " + dept.getFdId() + ",对应钉钉ID:"
                            + relationMap.get(dept.getFdId());
                    rtn = updateDept(dept);
                    logInfo += " ,retmsg:" + rtn;
                    log(logInfo);
                } else {
                    if (!relationMap.keySet().contains(dept.getFdParent().getFdId())) {
                        logInfo = "警告：从关系中找不到钉钉所对应的父ID，则移到根部门下，当前部门: " + dept.getFdName() + " " + dept.getFdId()
                                + ",父ID：" + dept.getFdParent().getFdId();
                        rtn = updateDept(dept);
                        logInfo += " ,retmsg:" + rtn;
                        if (!"ok".equals(rtn)) {
                            addOmsErrors(dept, logInfo, "update");
                        }
                        logger.warn(logInfo);
                        log(logInfo);
                    } else {
                        logInfo = "新增后或更新钉钉的父部门ID ," + dept.getFdName() + "," + dept.getFdId() + ",对应父钉钉ID:"
                                + relationMap.get(dept.getFdParent().getFdId()) + ",对应钉钉ID:"
                                + relationMap.get(dept.getFdId());
                        rtn = updateDept(dept);
                        logInfo += " ,retmsg:" + rtn;
                        if (!"ok".equals(rtn)) {
                            addOmsErrors(dept, logInfo, "update");
                        }
                        log(logInfo);
                    }
                }
            }
        }
    }

    private void handleDelDept(List<SysOrgElement> depts) throws Exception {
        String logInfo = null;
        String rtn = null;
        if (CollectionUtils.isEmpty(depts)) {
            return;
        }
        for (SysOrgElement dept : depts) {
            if (!dept.getFdIsAvailable()) {
                // 删除
                if (!relationMap.keySet().contains(dept.getFdId())) {
                    logInfo = "警告：从关系中找不到钉钉对应的ID，当前部门 ：" + dept.getFdName() + "," + dept.getFdId() + ",删除忽略";
                    logger.warn(logInfo);
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
                            if (rtn.indexOf("60003") == -1) {
                                addOmsErrors(dept, logInfo, "del");
                            }
                        }
                    }
                }
                log(logInfo);
            }
        }
    }

    private JSONObject getUser(SysOrgPerson element, String addOrUpdate)
            throws Exception {

        logger.debug("addOrUpdate:" + addOrUpdate);
        if (StringUtil.isNull(addOrUpdate)) {
            logger.warn("addOrUpdate标识为空，人员默认新增及更新时信息均同步");
            addOrUpdate = "add";
        }
        JSONObject person = new JSONObject();
        String userid = element.getFdLoginName();
        // 根据配置来确定是选择那种作为企业号的userid，默认是登录名
        String o2d_userId = DingConfig.newInstance().getOrg2dingUserid();
        if (StringUtil.isNotNull(o2d_userId)) {
            logger.debug("o2d_userId:" + o2d_userId);
            if ("fdId".equals(o2d_userId)) {
                userid = element.getFdId();
            }
        } else {
            String wxln = DingConfig.newInstance().getWxLoginName();
            if (StringUtil.isNull(wxln)) {
                // 配置字段方式
                String o2d_userid = DingConfig.newInstance()
                        .getOrg2dingUserid();
                logger.debug("o2d_userid:" + o2d_userid);
                if (StringUtil.isNotNull(o2d_userid)) {
                    if ("fdId".equalsIgnoreCase(o2d_userid)) {
                        userid = element.getFdId();
                    }
                }

            } else {
                if ("id".equalsIgnoreCase(wxln)) {
                    userid = element.getFdId();
                }
            }
        }
        // 如果数据已经存在则不管是否是登录名还是ID
        if (relationMap.get(element.getFdId()) != null) {
            userid = relationMap.get(element.getFdId());
        }
        person.accumulate("userid", userid);

        // 名称
        String o2d_name = DingConfig.newInstance().getOrg2dingNameSynWay();
        if (StringUtil.isNull(o2d_name)) {
            logger.debug("旧方式同步人员名称");
            person.accumulate("name", element.getFdName());
        } else {
            if ("syn".equalsIgnoreCase(o2d_name)
                    || ("addSyn".equalsIgnoreCase(o2d_name)
                    && "add".equalsIgnoreCase(addOrUpdate))) {
                logger.debug("添加name信息：" + element.getFdName());
                person.accumulate("name", element.getFdName());
            }
        }

        // 设置部门
        JSONArray depts = getUserDepartment(element, addOrUpdate);
        logger.debug("depts:" + depts);
        String mulDeptEnabled = DingConfig.newInstance()
                .getDingPostMulDeptEnabled();
        if (StringUtil.isNull(mulDeptEnabled)) {
            //新配置方式
            String o2d_DeptWay = DingConfig.newInstance()
                    .getOrg2dingDepartmentSynWay();
            logger.debug("o2d_DeptWay:" + o2d_DeptWay);
            if ("syn".equalsIgnoreCase(o2d_DeptWay)
                    || ("addSyn".equalsIgnoreCase(o2d_DeptWay)
                    && "add".equalsIgnoreCase(addOrUpdate))) {
                person.accumulate("department", depts);
            }
        } else {
            logger.debug("旧方式同步一人多部门");
            person.accumulate("department", depts);
        }

        // 设置手机号码
        if (StringUtil.isNotNull(element.getFdMobileNo())) {
            String mobileWay = DingConfig.newInstance()
                    .getOrg2dingMobileSynWay();
            logger.debug("mobileWay:" + mobileWay);
            if (StringUtil.isNotNull(mobileWay)) {
                if ("syn".equalsIgnoreCase(mobileWay)
                        || ("addSyn".equalsIgnoreCase(mobileWay)
                        && "add".equalsIgnoreCase(addOrUpdate))) {
                    person.accumulate("mobile", element.getFdMobileNo());
                }
            } else {
                logger.debug("旧方式同步手机号码！");
                person.accumulate("mobile", element.getFdMobileNo());
            }
        }

        // 设置邮箱
        String o2d_emailWay = DingConfig.newInstance().getOrg2dingEmailSynWay();
        if (StringUtil.isNotNull(o2d_emailWay)) {
            logger.debug("o2d_emailWay：" + o2d_emailWay);
            if ("syn".equalsIgnoreCase(o2d_emailWay)
                    || ("addSyn".equalsIgnoreCase(o2d_emailWay)
                    && "add".equalsIgnoreCase(addOrUpdate))) {
                // 同步邮箱
                String o2d_email = DingConfig.newInstance().getOrg2dingEmail();
                logger.debug("o2d_email:" + o2d_email);
                if (StringUtil.isNotNull(o2d_email)) {
                    if ("fdEmail".equals(o2d_email)) {
                        person.accumulate("email", element.getFdEmail());
                    } else {
                        logger.warn("邮箱字段选择了其他字段，尝试获取该字段的值:" + o2d_email);
                        String emailValue = getPersonProperty(o2d_email,
                                element);
                        logger.debug("emailValue:" + emailValue);
                        if (StringUtil.isNotNull(emailValue)) {
                            person.accumulate("email", emailValue);
                        } else {
                            logger.debug(element.getFdName() + "邮箱为空！");
                            person.accumulate("email", "");
                        }
                    }
                }

            }
        } else {
            logger.debug("旧方式同步邮箱");
            person.accumulate("email", element.getFdEmail());
        }

        // 钉钉分机号存在唯一性,若办公电话一样,同步过去会出错
        String wp = DingConfig.newInstance().getDingWorkPhoneEnabled();
        if (StringUtil.isNotNull(wp)) {
            logger.debug("旧方式同步办公电话！");
            if ("true".equals(wp)) {
                String workPhone = element.getFdWorkPhone();
                person.accumulate("tel",
                        StringUtil.isNull(workPhone) ? "" : workPhone);
            }
        } else {
            String o2d_telWay = DingConfig.newInstance().getOrg2dingTelSynWay();
            if (StringUtil.isNotNull(o2d_telWay)) {
                logger.debug("新方式同步办公电话！o2d_telWay：" + o2d_telWay);
                if ("syn".equalsIgnoreCase(o2d_telWay)
                        || ("addSyn".equalsIgnoreCase(o2d_telWay)
                        && "add".equalsIgnoreCase(addOrUpdate))) {
                    String o2d_tel = DingConfig.newInstance().getOrg2dingTel();
                    logger.debug("o2d_tel:" + o2d_tel);
                    if (StringUtil.isNotNull(o2d_tel)) {
                        if ("fdWorkPhone".equals(o2d_tel)) {
                            String workPhone = element.getFdWorkPhone();
                            person.accumulate("tel",
                                    StringUtil.isNull(workPhone) ? ""
                                            : workPhone);
                        } else {
                            logger.warn("办公电话字段选择了其他字段，尝试获取该字段的值:" + o2d_tel);
                            String Value = getPersonProperty(o2d_tel,
                                    element);
                            logger.debug("telValue:" + Value);

                            if (StringUtil.isNotNull(Value)) {
                                person.accumulate("tel", Value);
                            } else {
                                logger.debug(element.getFdName() + "办公电话为空！");
                                person.accumulate("tel", "");
                            }
                        }
                    }

                }
            }
        }

        // 设置员工工号
        String no = DingConfig.newInstance().getDingNoEnabled();
        if (StringUtil.isNotNull(no)) {
            logger.debug("旧方式同步设置员工工号！");
            if ("true".equals(no) && StringUtil.isNotNull(element.getFdNo())) {
                person.accumulate("jobnumber", element.getFdNo());
            } else {
                person.accumulate("jobnumber", "");
            }
        } else {
            String o2d_jobnumberWay = DingConfig.newInstance()
                    .getOrg2dingJobnumberSynWay();
            if (StringUtil.isNotNull(o2d_jobnumberWay)) {
                logger.debug(
                        "新方式同步设置员工工号！o2d_jobnumberWay：" + o2d_jobnumberWay);
                if ("syn".equalsIgnoreCase(o2d_jobnumberWay)
                        || ("addSyn".equalsIgnoreCase(o2d_jobnumberWay)
                        && "add".equalsIgnoreCase(addOrUpdate))) {
                    String o2d_jobnumber = DingConfig.newInstance()
                            .getOrg2dingJobnumber();
                    logger.debug("o2d_jobnumber:" + o2d_jobnumber);
                    if (StringUtil.isNotNull(o2d_jobnumber)) {
                        if ("fdNo".equals(o2d_jobnumber)) {
                            person.accumulate("jobnumber", element.getFdNo());
                        } else {
                            logger.warn(
                                    "工号选择了编号字段其他字段，尝试获取该字段的值:" + o2d_jobnumber);
                            String jobnumbeValue = getPersonProperty(
                                    o2d_jobnumber,
                                    element);
                            logger.debug("jobnumbeValue:" + jobnumbeValue);
                            if (StringUtil.isNotNull(jobnumbeValue)) {
                                person.accumulate("jobnumber", jobnumbeValue);
                            } else {
                                logger.debug(element.getFdName() + "工号为空！");
                                person.accumulate("jobnumber", "");
                            }
                        }
                    } else {
                        person.accumulate("jobnumber", "");
                    }

                }
            }
        }

        // 是否同步职位
        String dingPostEnabled = DingConfig.newInstance().getDingPostEnabled();
        if (StringUtil.isNotNull(dingPostEnabled)) {
            logger.debug("旧方式同步职位！");
            if ("true".equals(dingPostEnabled)) {
                if (ppMap.containsKey(element.getFdId())) {
                    String position = ppMap.get(element.getFdId()).get("names");
                    if (StringUtil.isNotNull(position)) {
                        if (position.length() > 64) {
                            position = position.substring(0, 64);
                        }
                        person.accumulate("position", position);
                    } else {
                        person.accumulate("position", "");
                    }
                } else {
                    person.accumulate("position", "");
                }
            } else {
                person.accumulate("position", "");
            }
        } else {
            String o2d_dingPositionWay = DingConfig.newInstance()
                    .getOrg2dingPositionSynWay();
            if (StringUtil.isNotNull(o2d_dingPositionWay)) {
                logger.debug(
                        "新方式同步设置职位！o2d_dingPositionWay：" + o2d_dingPositionWay);
                if ("syn".equalsIgnoreCase(o2d_dingPositionWay)
                        || ("addSyn".equalsIgnoreCase(o2d_dingPositionWay)
                        && "add".equalsIgnoreCase(addOrUpdate))) {
                    String o2d_dingPosition = DingConfig.newInstance()
                            .getOrg2dingPosition();
                    logger.debug("o2d_dingPosition:" + o2d_dingPosition);
                    if (StringUtil.isNotNull(o2d_dingPosition)) {
                        if ("hbmPosts".equals(o2d_dingPosition)) {
                            if (ppMap.containsKey(element.getFdId())) {
                                String position = ppMap.get(element.getFdId())
                                        .get("names");
                                logger.debug("岗位信息：" + position);
                                if (StringUtil.isNotNull(position)) {
                                    if (position.length() > 64) {
                                        position = position.substring(0, 64);
                                    }
                                    person.accumulate("position", position);
                                } else {
                                    person.accumulate("position", "");
                                }
                            } else {
                                person.accumulate("position", "");
                            }
                        } else {
                            logger.warn(
                                    "岗位选择了所属岗位字段的其他字段，尝试获取该字段的值:"
                                            + o2d_dingPosition);
                            String dingPositionValue = getPersonProperty(
                                    o2d_dingPosition,
                                    element);
                            logger.debug(
                                    "dingPositionValue:" + dingPositionValue);
                            if (StringUtil.isNotNull(dingPositionValue)) {
                                person.accumulate("position",
                                        dingPositionValue);
                            } else {
                                logger.debug(element.getFdName() + "岗位为空！");
                                person.accumulate("position", "");
                            }
                        }
                    }

                }
            }
        }
        // 人员排序
        Integer order = element.getFdOrder();
        if (order != null) {
            JSONObject orderJson = new JSONObject();
            String parentId = getDingRootDeptId();
            if (element.getFdParent() != null) {
                parentId = relationMap.get(element.getFdParent().getFdId());
            }
            if (StringUtil.isNull(parentId)) {
                parentId = getDingRootDeptId();
            }

            String personOrder = DingConfig.newInstance().getDingPersonOrder();
            if (StringUtil.isNotNull(personOrder)) {
                logger.debug("旧方式同步人员排序！");
                // 判断是否降序排列
                if ("1".equals(DingConfig.newInstance().getDingPersonOrder())) {
                    order = Integer.MAX_VALUE - order;
                }
                orderJson.accumulate(parentId, order);
                person.accumulate("orderInDepts", orderJson);
            } else {
                String o2d_orderWay = DingConfig.newInstance()
                        .getOrg2dingOrderInDeptsSynWay();
                logger.debug("o2d_order:" + o2d_orderWay);
                if ("syn".equalsIgnoreCase(o2d_orderWay)
                        || ("addSyn".equalsIgnoreCase(o2d_orderWay)
                        && "add".equalsIgnoreCase(addOrUpdate))) {
                    String o2d_order = DingConfig.newInstance()
                            .getOrg2dingOrderInDepts();
                    if (StringUtil.isNotNull(o2d_order)
                            && "desc".equals(o2d_order)) {
                        order = Integer.MAX_VALUE - order;
                    }
                    orderJson.accumulate(parentId, order);
                    person.accumulate("orderInDepts", orderJson);
                }
            }

        }

        // 备注
        String o2d_remarkWay = DingConfig.newInstance()
                .getOrg2dingRemarkSynWay();
        if (StringUtil.isNotNull(o2d_remarkWay)) {
            logger.debug(
                    "新方式同步设置备注！o2d_remarkWay：" + o2d_remarkWay);
            if ("syn".equalsIgnoreCase(o2d_remarkWay)
                    || ("addSyn".equalsIgnoreCase(o2d_remarkWay)
                    && "add".equalsIgnoreCase(addOrUpdate))) {
                String o2d_remark = DingConfig.newInstance()
                        .getOrg2dingRemark();
                logger.debug("o2d_remark:" + o2d_remark);
                if (StringUtil.isNotNull(o2d_remark)) {
                    if ("fdMemo".equals(o2d_remark)) {
                        person.accumulate("remark", element.getFdMemo());
                    } else {
                        logger.warn(
                                "备注选择了fdMemo字段其他字段，尝试获取该字段的值:" + o2d_remark);
                        String remarkValue = getPersonProperty(
                                o2d_remark,
                                element);
                        logger.debug("remarkValue:" + remarkValue);
                        if (StringUtil.isNotNull(remarkValue)) {
                            person.accumulate("remark", remarkValue);
                        } else {
                            logger.debug(element.getFdName() + "备注为空！");
                            person.accumulate("remark", "");
                        }
                    }
                }

            }
        }

        // 入职时间
        String o2d_hiredDateWay = DingConfig.newInstance()
                .getOrg2dingHiredDateSynWay();
        if (StringUtil.isNotNull(o2d_hiredDateWay)) {
            logger.debug(
                    "新方式同步入职时间！o2d_hiredDateWay：" + o2d_hiredDateWay);
            if ("syn".equalsIgnoreCase(o2d_hiredDateWay)
                    || ("addSyn".equalsIgnoreCase(o2d_hiredDateWay)
                    && "add".equalsIgnoreCase(addOrUpdate))) {
                String o2d_hiredDate = DingConfig.newInstance()
                        .getOrg2dingHiredDate();
                logger.debug("o2d_hiredDate:" + o2d_hiredDate);
                if (StringUtil.isNotNull(o2d_hiredDate)) {
                    if ("fdHiredate".equals(o2d_hiredDate)) {
                        Date hd = element.getFdHiredate();
                        logger.debug("hiredDate before:" + hd);
                        if (hd != null) {
                            person.accumulate("hiredDate", hd.getTime());
                        } else {
                            person.accumulate("hiredDate", "");
                        }
                    } else {
                        logger.warn(
                                "入职时间选择了fdHiredate其他字段，尝试获取该字段的值:"
                                        + o2d_hiredDate);
                        String hireDateValue = getPersonProperty(
                                o2d_hiredDate,
                                element);
                        logger.debug("hireDateValue:" + hireDateValue);
                        if (StringUtil.isNotNull(hireDateValue)) {
                            try {
                                Long time = DateUtil
                                        .convertStringToDate(hireDateValue)
                                        .getTime();
                                person.accumulate("hiredDate", time);
                            } catch (Exception e) {
                                logger.error("转换入职时间出错，不同步入职时间字段："
                                        + element.getFdName());

                            }

                        } else {
                            logger.debug(element.getFdName() + "入职时间为空！");
                            person.accumulate("hiredDate", "");
                        }
                    }
                }

            }
        }

        // 企业邮箱
        String o2d_orgEmailWay = DingConfig.newInstance()
                .getOrg2dingOrgEmailSynWay();
        if (StringUtil.isNotNull(o2d_orgEmailWay)) {
            logger.debug(
                    "新方式同步设置企业邮箱！o2d_orgEmailWay：" + o2d_orgEmailWay);
            if ("syn".equalsIgnoreCase(o2d_orgEmailWay)
                    || ("addSyn".equalsIgnoreCase(o2d_orgEmailWay)
                    && "add".equalsIgnoreCase(addOrUpdate))) {
                String o2d_orgEmail = DingConfig.newInstance()
                        .getOrg2dingOrgEmail();
                logger.debug("o2d_orgEmail:" + o2d_orgEmail);
                if (StringUtil.isNotNull(o2d_orgEmail)) {
                    String orgEmailValue = getPersonProperty(
                            o2d_orgEmail,
                            element);
                    logger.debug("orgEmailValue:" + orgEmailValue);
                    if (StringUtil.isNotNull(orgEmailValue)) {
                        person.accumulate("orgEmail", orgEmailValue);
                    } else {
                        logger.debug(element.getFdName() + "企业邮箱为空！");
                        person.accumulate("orgEmail", "");
                    }

                }

            }
        }

        // 号码隐藏 isHide
        String o2d_isHideWay = DingConfig.newInstance()
                .getOrg2dingIsHideSynWay();
        if (StringUtil.isNotNull(o2d_isHideWay)) {
            logger.debug(
                    "新方式同步设置号码隐藏！o2d_isHideWay：" + o2d_isHideWay);
            if ("syn".equalsIgnoreCase(o2d_isHideWay)
                    || ("addSyn".equalsIgnoreCase(o2d_isHideWay)
                    && "add".equalsIgnoreCase(addOrUpdate))) {

                String o2d_isHide = DingConfig.newInstance()
                        .getOrg2dingIsHide();
                logger.debug("o2d_isHide:" + o2d_isHide);
                String o2d_isHideAll = DingConfig.newInstance()
                        .getOrg2dingIsHideAll();
                if (StringUtil.isNotNull(o2d_isHideAll)
                        && "true".equals(o2d_isHideAll)) {
                    logger.debug("隐藏全部手机号码");
                    person.accumulate("isHide", true);
                } else {
                    try {
                        // 从字段同步
                        if (StringUtil.isNotNull(o2d_isHide)) {
                            if ("isContactPrivate".equals(o2d_isHide)) {
                                // 员工黄页的隐私设置
                                logger.debug(
                                        "员工黄页的隐私设置：id-> " + element.getFdId());

                                Object object = getSysZonePersonInfoService()
                                        .findByPrimaryKey(element.getFdId(),
                                                null, true);
                                Object o = null;
                                if (object != null) {
                                    Class clazz = object.getClass();
                                    Method m = clazz
                                            .getMethod("getIsContactPrivate");
                                    o = m.invoke(object);
                                }
                                logger.debug("员工黄页获取的的值：" + o);
                                if (o != null) {
                                    String hideObject = o.toString().trim();
                                    logger.debug(
                                            "hideObject:" + hideObject);
                                    if ("true".equals(hideObject)
                                            || "1".equals(hideObject)
                                            || "是".equals(hideObject)) {
                                        person.accumulate("isHide", true);
                                    } else {
                                        person.accumulate("isHide", false);
                                    }
                                } else {
                                    logger.debug(
                                            "个人员工黄页隐私不设置的情况下，则取全局员工黄页的隐私配置");

                                    Map orgMap = getSysAppConfigService()
                                            .findByKey(
                                                    "com.landray.kmss.sys.zone.model.SysZonePrivateConfig");
                                    if (orgMap != null
                                            && orgMap.containsKey(
                                            "isContactPrivate")) {
                                        Object isContactPrivateObject = orgMap
                                                .get("isContactPrivate");
                                        logger.debug(
                                                "isContactPrivateObject:"
                                                        + isContactPrivateObject);
                                        if (isContactPrivateObject != null
                                                && "1".equals(
                                                isContactPrivateObject
                                                        .toString())) {
                                            person.accumulate("isHide",
                                                    true);
                                        } else {
                                            person.accumulate("isHide",
                                                    false);
                                        }
                                    } else {
                                        person.accumulate("isHide", false);
                                    }
                                }

                            } else {
                                // 从字段同步
                                String o2d_isHideValue = getPersonProperty(
                                        o2d_isHide,
                                        element);
                                logger.debug("从字段同步o2d_isHideValue："
                                        + o2d_isHideValue);

                                if (StringUtil.isNotNull(o2d_isHideValue)) {

                                    if ("true".equals(o2d_isHideValue)
                                            || "1".equals(o2d_isHideValue)
                                            || "是".equals(o2d_isHideValue)) {
                                        person.accumulate("isHide", true);
                                    } else {
                                        person.accumulate("isHide", false);
                                    }
                                } else {
                                    person.accumulate("isHide", false);
                                }

                            }

                        }


                    } catch (Exception e) {
                        logger.error("获取人员的隐藏号码信息异常:" + element.getFdName());
                        logger.error(e.getMessage(), e);
                    }


                }

            }
        }

        // 是否高管
        String o2d_isSeniorWay = DingConfig.newInstance()
                .getOrg2dingIsSeniorSynWay();
        if (StringUtil.isNotNull(o2d_isSeniorWay)) {
            logger.debug(
                    "新方式同步是否高管！o2d_isSeniorWay：" + o2d_isSeniorWay);
            if ("syn".equalsIgnoreCase(o2d_isSeniorWay)
                    || ("addSyn".equalsIgnoreCase(o2d_isSeniorWay)
                    && "add".equalsIgnoreCase(addOrUpdate))) {

                String o2d_isSenior = DingConfig.newInstance()
                        .getOrg2dingIsSenior();
                logger.debug("o2d_isSenior:" + o2d_isSenior);
                if (StringUtil.isNotNull(o2d_isSenior)) {
                    String seniorValue = getPersonProperty(
                            o2d_isSenior,
                            element);
                    logger.debug("seniorValue:" + seniorValue);
                    if (StringUtil.isNotNull(seniorValue)) {
                        if ("true".equalsIgnoreCase(seniorValue)
                                || "1".equals(seniorValue)
                                || "是".equals(seniorValue)) {
                            person.accumulate("isSenior", true);
                        } else if ("false".equalsIgnoreCase(seniorValue)
                                || "0".equals(seniorValue)
                                || "否".equals(seniorValue)) {
                            person.accumulate("isSenior", false);
                        } else {
                            logger.warn("同步人员是否高管字段是获取的字段不是true/false 1/0  是/否!"
                                    + element.getFdName());
                            //person.accumulate("isSenior", false);
                        }

                    } else {
                        logger.warn(element.getFdName() + "是否高管为空！");
                        person.accumulate("isSenior", false);
                    }
                }

            }
        }

        // 办公地点
        String workPlaceSynWay = DingConfig.newInstance()
                .getOrg2dingWorkPlaceSynWay();
        if (StringUtil.isNotNull(workPlaceSynWay)) {
            logger.debug(
                    "办公地点设置！workPlaceSynWay：" + workPlaceSynWay);
            if ("syn".equalsIgnoreCase(workPlaceSynWay)
                    || ("addSyn".equalsIgnoreCase(workPlaceSynWay)
                    && "add".equalsIgnoreCase(addOrUpdate))) {

                String workPlace = DingConfig.newInstance()
                        .getOrg2dingWorkPlace();
                logger.debug("workPlace:" + workPlace);
                if (StringUtil.isNotNull(workPlace)) {
                    String workPlaceValue = getPersonProperty(
                            workPlace,
                            element);
                    logger.debug("workPlaceValue:" + workPlaceValue);
                    if (StringUtil.isNotNull(workPlaceValue)) {
                        person.accumulate("workPlace", workPlaceValue);
                    } else {
                        logger.warn(element.getFdName() + "获取办公地点为空！");
                        person.accumulate("workPlace", "");
                    }
                }

            }
        }

        return person;
    }

    // 根据字段获取人员的值，该值范围：SysOrgPerson和自定义
    private String getPersonProperty(String key, SysOrgPerson element) {

        try {
            logger.debug("key:" + key + " ,name:" + element.getFdName());
            if (StringUtil.isNull(key)) {
                return null;
            }
            // SysDictModel model = SysDataDict.getInstance()
            // .getModel(
            // "com.landray.kmss.sys.organization.model.SysOrgPerson");
            // Map<String, SysDictCommonProperty> map = model.getPropertyMap();
            // System.out.println("map:" + map);

            Map<String, Object> customMap = element.getCustomPropMap();
            logger.debug("customMap:" + customMap);
            if (customMap != null && customMap.containsKey(key)) {
                if (customMap.get(key) == null) {
                    return null;
                }
                String v = customMap.get(key).toString();
                logger.debug("value:" + v);
                return v;
            } else {
                logger.debug("非自定义字段");
                String _key = "get" + key.substring(0, 1).toUpperCase()
                        + key.substring(1);
                logger.debug("_key:" + _key);
                Class clazz = element.getClass();
                Method method = clazz.getMethod(_key.trim());
                Object obj = method.invoke(element);
                if ("fdStaffingLevel".equals(key) || "hbmParent".equals(key)) { // 对职务特殊处理
                    // fdStaffingLevel
                    if (obj != null) {
                        clazz = obj.getClass();
                        method = clazz.getMethod("getFdName");
                        obj = method.invoke(obj);
                    }
                }
                logger.debug("obj:" + obj);
                return obj == null ? null : obj.toString();
            }

            //
            // Set<String> proSet = map.keySet();
            // if(proSet.contains(key)){
            // logger.debug("非自定义字段");
            // String _key = "get" + key.substring(0, 1).toUpperCase()
            // + key.substring(1);
            // logger.debug("_key:" + _key);
            // Class clazz = element.getClass();
            // Method method = clazz.getMethod(_key.trim());
            // Object obj = method.invoke(element);
            // logger.debug("obj:" + obj);
            // return obj == null ? null : obj.toString();
            // } else {
            // logger.debug("自定义字段" + key);
            // Map<String, Object> customMap = element.getCustomPropMap();
            // if (customMap != null) {
            // String v = (String) customMap.get(key);
            // logger.debug("value:" + v);
            // return v;
            // }
            // }
        } catch (Exception e) {
            logger.error("根据字段获取人员的值过程中发生了异常");
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    // 根据字段获取部门的值，该值范围：dept和自定义
    private String getDeptProperty(String key, SysOrgElement element) {

        try {
            logger.debug("key:" + key + " ,name:" + element.getFdName());
            if (StringUtil.isNull(key)) {
                return null;
            }
            // SysDictModel model = SysDataDict.getInstance()
            // .getModel(
            // "com.landray.kmss.sys.organization.model.SysOrgDept");
            // Map<String, SysDictCommonProperty> map = model.getPropertyMap();
            // Set<String> proSet = map.keySet();
            Map<String, Object> customMap = element.getCustomPropMap();
            if (customMap != null && customMap.containsKey(key)) {
                if (customMap.get(key) == null) {
                    return null;
                }
                String v = customMap.get(key).toString();
                logger.debug("value:" + v);
                return v;
            } else {
                logger.debug("非自定义字段");
                String _key = "get" + key.substring(0, 1).toUpperCase()
                        + key.substring(1);
                logger.debug("_key:" + _key);
                Class clazz = element.getClass();
                Method method = clazz.getMethod(_key.trim());
                Object obj = method.invoke(element);
                logger.debug("obj:" + obj);
                return obj == null ? null : obj.toString();
            }

        } catch (Exception e) {
            logger.error("根据字段获取部门的值过程中发生了异常");
            logger.error("", e);
        }
        return null;
    }

    private JSONArray getUserDepartment(SysOrgPerson element,
                                        String addOrUpdate) throws Exception {
        logger.debug("addOrUpdate: " + addOrUpdate);
        JSONArray depts = new JSONArray();
        // 把当前人所属部门同步过去
        String parentId = getDingRootDeptId();
        if (element.getFdParent() != null) {
            parentId = relationMap.get(element.getFdParent().getFdId());
        }
        if (StringUtil.isNull(parentId)) {
            parentId = getDingRootDeptId();
        }
        depts.add(parentId);

        // 是否同步部门主管 在部门表更时处理
//		String dingDeptLeaderEnabled = DingConfig.newInstance().getDingDeptLeaderEnabled();
//		if (StringUtil.isNotNull(dingDeptLeaderEnabled)) {// 旧开关方式
//			if ("true".equals(dingDeptLeaderEnabled)) {
//				List<String> list = addUserLeader(depts, element);
//				depts.addAll(list);
//				logger.debug("list:"+list);
//			}
//		} else {
//			String o2d_deptManagerWay = DingConfig.newInstance()
//					.getOrg2dingDeptDeptManagerSynWay();
//			logger.debug("o2d_deptManagerWay:" + o2d_deptManagerWay);
//			if (StringUtil.isNotNull(o2d_deptManagerWay)) {
//				if ("noSyn".equals(o2d_deptManagerWay)) {
//					logger.debug("部门主管同步功能未开启!");
//				} else if ("syn".equals(o2d_deptManagerWay)
//						|| ("addSyn".equals(o2d_deptManagerWay)
//								&& "add".equals(addOrUpdate))) {
//					String deptManager = DingConfig.newInstance()
//							.getOrg2dingDeptDeptManager();
//					logger.debug("部门主管开启同步方式，deptManager：" + deptManager);
//					List<String> list = addUserLeader(depts, element);
//					logger.debug("list:"+list);
//					depts.addAll(list);
//				}
//
//			}
//		}


        // 是否开启一人多部门同步
        String dingPostEnabled = DingConfig.newInstance().getDingPostMulDeptEnabled();
        if (StringUtil.isNull(dingPostEnabled)) {
            String o2d_DeptWay = DingConfig.newInstance().getOrg2dingDepartmentSynWay();
            logger.debug("o2d_DeptWay:" + o2d_DeptWay);
            if (StringUtil.isNotNull(o2d_DeptWay)) {
                if (("syn".equals(o2d_DeptWay)
                        || ("addSyn".equals(o2d_DeptWay)
                        && "add".equals(addOrUpdate)))
                        && "fdMuilDept".equals(DingConfig.newInstance()
                        .getOrg2dingDepartment())) {
                    logger.debug("一人多部门功能开启");
                    if (ppMap.containsKey(element.getFdId())) {
                        String[] parentids = ppMap.get(element.getFdId())
                                .get("parentids").split("[,;]");
                        for (String pid : parentids) {
                            if (StringUtil.isNull(pid)) {
                                continue;
                            }
                            if (relationMap.get(pid) != null
                                    && !depts.contains(relationMap.get(pid))) {
                                depts.add(relationMap.get(pid));
                            }
                        }
                    }
                }
            }


        } else {
            logger.debug("一人多部门功能（旧数据）");
            if ("true".equals(dingPostEnabled)) {
                if (ppMap.containsKey(element.getFdId())) {
                    String[] parentids = ppMap.get(element.getFdId()).get("parentids").split("[,;]");
                    for (String pid : parentids) {
                        if (StringUtil.isNull(pid)) {
                            continue;
                        }
                        if (relationMap.get(pid) != null && !depts.contains(relationMap.get(pid))) {
                            depts.add(relationMap.get(pid));
                        }
                    }
                }
            }
        }

        return depts;
    }

    private void handlePerson(List<SysOrgPerson> persons) throws Exception {
        String logInfo = null;
        long count = 0L;
        if (CollectionUtils.isEmpty(persons)) {
            return;
        }
        for (SysOrgPerson person : persons) {
            if (StringUtil.isNull(person.getFdLoginName())) {
                logInfo = "警告：当前个人 " + person.getFdName() + ",的登录名为空，直接跳过";
                logger.warn(logInfo);
                log(logInfo);
                continue;
            }
            if (person.getFdIsAvailable()) {
                if (!exist(person.getFdId())) {
                    continue;
                }
                if (!person.getFdIsBusiness()) {
                    logger.debug("业务无关的人员不同步：" + person.getFdName());
                    continue;
                }
                if (!relationMap.keySet().contains(person.getFdId())) {
                    addPerson(person);
                } else {
                    updatePerson(person);
                }
            } else {
                if (!relationMap.keySet().contains(person.getFdId())) {
                    logInfo = "警告：从关系中找不到钉钉对应的ID，当前个人 " + person.getFdName() + ", " + ",loginName:"
                            + (person.getFdLoginName() == null ? "" : person.getFdLoginName()) + ",id:"
                            + person.getFdId() + ",删除忽略";
                    logger.warn(logInfo);
                } else {
                    deletePerson(person);
                }
            }
            count++;
            allcount++;
        }
        logInfo = "本次批量同步个人到钉钉的个数为:" + count + "条";
        log(logInfo);
    }

    private void addPerson(SysOrgPerson person) throws Exception {
        String logInfo = "增加个人到钉钉 " + person.getFdName() + ", " + person.getFdId();
        JSONObject ret = dingApiService.userCreate(getUser(person, "add"));
        if (ret == null) {
            logInfo += ",不可预知的错误(可能是网络或者钉钉接口异常，无法返回成功或者失败信息)!";
            addOmsErrors(person, logInfo, "add");
        } else {
            if (ret.getInt("errcode") == 0 || ret.getInt("errcode") == 60102
                    || ret.getInt("errcode") == 60104) {
                String unionId = getUnionId(ret);
                // #111497
                if (ret.getInt("errcode") == 60104
                        && ret.containsKey("userid")) {
                    // 先判断是否有用户 userid是否已存在
                    logger.warn(
                            person.getFdName() + "的号码(" + person.getFdMobileNo()
                                    + ")在钉钉已经存在 : " + ret);
                    jobContext.logMessage(
                            person.getFdName() + "的号码(" + person.getFdMobileNo()
                                    + ")在钉钉已经存在 : " + ret);
                    if (relationMap.containsValue(ret.getString("userid"))) {
                        logInfo += "  该userId已有映射关系，不再更新映射关系!userid:"
                                + ret.getString("userid");
                        logger.warn("该userId已有映射关系，不再更新映射关系!userid:"
                                + ret.getString("userid"));

                    } else {
                        logInfo += " ,created";
                        logger.warn("建立映射关系并且更新用户" + person.getFdName()
                                + "数据   userid:" + ret.getString("userid")
                                + "    ekpId:" + person.getFdId());
                        addRelation(person, ret.getString("userid"), "8", unionId);
                        jobContext
                                .logMessage(
                                        "建立映射关系并且更新用户    " + person.getFdName()
                                                + "  的数据   userid:"
                                                + ret.getString("userid")
                                                + "    ekpId:"
                                                + person.getFdId());
                        String update_ret = dingApiService
                                .userUpdate(getUser(person, "add"));
                        logger.debug("update_ret:  " + update_ret);
                        jobContext.logMessage(person.getFdName()
                                + " update_ret:" + update_ret);
                    }

                } else {
                    logInfo += " ,created";
                    String o2d_userId = DingConfig.newInstance()
                            .getOrg2dingUserid();
                    if (StringUtil.isNotNull(o2d_userId)) {
                        logger.debug("o2d_userId:" + o2d_userId);
                        if ("fdId".equals(o2d_userId)) {
                            addRelation(person, person.getFdId(), "8", unionId);
                        } else {
                            addRelation(person, person.getFdLoginName(), "8", unionId);
                        }
                    } else {
                        String wxln = DingConfig.newInstance().getWxLoginName();
                        if ("id".equalsIgnoreCase(wxln)) {
                            addRelation(person, person.getFdId(), "8", unionId);
                        } else {
                            addRelation(person, person.getFdLoginName(), "8", unionId);
                        }
                    }
                }


            } else {
                logInfo += " 失败,出错信息：" + ret.getString("errmsg");
                addOmsErrors(person, logInfo, "add");
            }
        }
        logger.error(logInfo);
        log(logInfo);
    }

    private void updatePerson(SysOrgPerson person) throws Exception {
        String logInfo = "更新个人到钉钉 " + person.getFdName() + ", " + person.getFdId();
        logInfo += " ,retmsg:";
        String retMsg = dingApiService.userUpdate(getUser(person, "update"));
        logInfo += retMsg;
        if (StringUtil.isNull(retMsg)) {
            logInfo += ",不可预知的错误(可能是网络或者钉钉接口异常，无法返回成功或者失败信息)!";
            addOmsErrors(person, logInfo, "update");
        }
        logger.error(logInfo);
        log(logInfo);

        if (StringUtil.isNull(retMsg)) {
            return;
        }
        if (!"ok".equals(retMsg)) {
            JSONObject jo = JSONObject.fromObject(retMsg);
            // {"errcode":60121,"errmsg":"找不到该用户"}
            if (jo.getInt("errcode") == 60121) {
                logInfo = "补增个人到钉钉 " + person.getFdName() + ", " + person.getFdId();
                logInfo += " ,retmsg:";
                JSONObject ret = dingApiService
                        .userCreate(getUser(person, "add"));
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
                addNewAnddelOld(person);
            } else if (jo.getInt("errcode") == 40022) {
                // 手机号码调整后不删除后重新添加(新需求#53650)
                JSONObject pm = getUser(person, "update");
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

    private void addNewAnddelOld(SysOrgPerson person) throws Exception {
        String logInfo = "删除钉钉中未激活的手机号用户 " + person.getFdName() + ","
                + person.getFdId() + ",loginName:" + person.getFdLoginName();
        String userid = relationMap.get(person.getFdId());
        if (StringUtil.isNull(userid)) {
            logInfo = "删除钉钉中未激活的手机号用户：因为中间映射表无相关数据无法执行 ";
            logger.warn(logInfo);
            log(logInfo);
            return;
        }
        JSONObject ujo = dingApiService.userGet(userid, person.getFdId());
        String unionId = getUnionId(ujo);
        if (ujo.containsKey("active") && !ujo.getBoolean("active")) {
            logInfo += " ," + dingApiService.userDelete(userid);
            logInfo += "\n";
            omsRelationService.deleteByKey(person.getFdId(), getAppKey());
            relationMap.remove(person.getFdId());
            // 增加新的钉钉用户
            logInfo += "增加新的钉钉用户（激活） " + person.getFdName() + ","
                    + person.getFdId() + ",loginName:" + person.getFdLoginName();
            JSONObject ret = dingApiService.userCreate(getUser(person, "add"));
            if (ret == null) {
                logInfo += ",不可预知的错误!";
            } else {
                if (ret.getInt("errcode") == 0) {
                    logInfo += " ,created";
                    String wxln = DingConfig.newInstance().getWxLoginName();
                    if ("id".equalsIgnoreCase(wxln)) {
                        addRelation(person, person.getFdId(), "8", unionId);
                    } else {
                        addRelation(person, person.getFdLoginName(), "8", unionId);
                    }
                } else {
                    logInfo += " 失败,出错信息：" + ret.getString("errmsg");
                }
            }
            logger.error(logInfo);
            log(logInfo);
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

    private void deletePerson(SysOrgPerson person) throws Exception {
        String logInfo = "删除钉钉中的个人ID " + person.getFdName() + " " + person.getFdId() + ","
                + relationMap.get(person.getFdId());
        if (relationMap.containsKey(person.getFdId()) && StringUtil.isNotNull(relationMap.get(person.getFdId()))) {
            String temp = dingApiService.userDelete(relationMap.get(person.getFdId()));
            logInfo += " ,retMsg:" + temp;
            if ("ok".equalsIgnoreCase(temp)) {
                omsRelationService.deleteByKey(person.getFdId(), getAppKey());
                if (relationMap != null) {
                    relationMap.remove(person.getFdId());
                }
            } else {
                if (!(logInfo.indexOf("60111") != -1 || logInfo.indexOf("60121") != -1)) {
                    addOmsErrors(person, logInfo, "del");
                }
            }
        } else {
            logInfo += " ,钉钉Id找不到";
        }
        log(logInfo);
    }

    @SuppressWarnings("unchecked")
    private void handleUpdatePost() throws Exception {
        ThirdDingOmsPost opost = null;
        JSONArray personIds = null;
        JSONObject postEle = null;
        SysOrgPerson temp = null;
        SysOrgElement ele = null;
        StringBuffer ids = new StringBuffer();

        DataSource dataSource = (DataSource) SpringBeanUtil
                .getBean("dataSource");
        Connection conn = null;
        PreparedStatement psupdate_oms_post = null;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            psupdate_oms_post = conn
                    .prepareStatement(
                            "update third_ding_oms_post set fd_name = ?,doc_content = ? where fd_id = ?");
            int loop = 0;
            for (SysOrgElement post : syncPosts) {
                opost = omsPostMap.get(post.getFdId());
                if (opost != null
                        && StringUtil.isNotNull(opost.getDocContent())) {
                    postEle = JSONObject.fromObject(opost.getDocContent());
                    if (postEle.has("persons")) {
                        personIds = postEle.getJSONArray("persons");
                        for (int i = 0; i < personIds.size(); i++) {
                            if (StringUtil.isNull(personIds.getString(i))) {
                                continue;
                            }
                            temp = (SysOrgPerson) sysOrgPersonService
                                    .findByPrimaryKey(personIds.getString(i),
                                            null, true);
                            if (temp != null) {
                                syncPersons.add(temp);
                            }
                        }
                    }
                    if (postEle.has("parentid") && StringUtil
                            .isNotNull(postEle.getString("parentid"))) {
                        ele = (SysOrgElement) sysOrgElementService
                                .findByPrimaryKey(postEle.getString("parentid"),
                                        null,
                                        true);
                        if (ele != null) {
                            syncDepts.add(ele);
                        }
                    }
                    if (postEle.has("leaderparentid") && StringUtil
                            .isNotNull(postEle.getString("leaderparentid"))) {
                        String[] lpids = postEle.getString("leaderparentid")
                                .split("[,;]");
                        for (String lpid : lpids) {
                            if (StringUtil.isNotNull(lpid)) {
                                ele = (SysOrgElement) sysOrgElementService
                                        .findByPrimaryKey(lpid, null, true);
                                if (ele != null) {
                                    syncDepts.add(ele);
                                }
                            }
                        }
                    }
                }
                // 保存岗位信息
                if (post.getFdIsAvailable()) {
                    postEle = new JSONObject();
                    personIds = new JSONArray();
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
                        syncDepts.add(post.getFdParent());
                    }
                    postEle.put("leaderparentid", "");
                    if (deptLeaderMap.containsKey(post.getFdId())) {
                        ids.setLength(0);
                        List<SysOrgElement> ldps = deptLeaderMap
                                .get(post.getFdId());
                        for (int i = 0; i < ldps.size(); i++) {
                            if (i == 0) {
                                ids.append(ldps.get(i).getFdId());
                            } else {
                                ids.append(";" + ldps.get(i).getFdId());
                            }
                        }
                        postEle.put("leaderparentid", ids.toString());
                    }
                    if (omsPostMap.containsKey(post.getFdId())) {
                        opost = omsPostMap.get(post.getFdId());
                        opost.setFdName(post.getFdName());
                        opost.setDocContent(postEle.toString());
                        logger.debug("更新岗位信息：" + post.getFdName());
                        // thirdDingOmsPostService.update(opost);

                        if (loop > 0 && (loop % 200 == 0)) {
                            psupdate_oms_post.executeBatch();
                            conn.commit();
                        }

                        psupdate_oms_post.setString(1, post.getFdName());
                        psupdate_oms_post.setString(2, postEle.toString());
                        psupdate_oms_post.setString(3, post.getFdId());
                        psupdate_oms_post.addBatch();
                        loop++;

                    } else {
                        opost = new ThirdDingOmsPost();
                        opost.setFdId(post.getFdId());
                        opost.setFdName(post.getFdName());
                        opost.setDocContent(postEle.toString());
                        logger.debug("新增岗位信息：" + post.getFdName());
                        thirdDingOmsPostService.add(opost);
                    }
                } else if (opost != null) {
                    omsPost.add(opost);
                }
            }

            psupdate_oms_post.executeBatch();
            conn.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            JdbcUtils.closeStatement(psupdate_oms_post);
            JdbcUtils.closeConnection(conn);
        }
    }

    private void addRelation(SysOrgElement element, String appPkId, String type, String unionId) throws Exception {
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

    private boolean isOmsRootOrg(String rootId) {
        if ("true".equals(DingConfig.newInstance().getDingOmsRootFlag())) {
            return true;
        } else if (DingConfig.newInstance().getDingOrgId().indexOf(rootId) != -1) {
            return false;
        }
        return true;
    }

    private List<SysOrgElement> getAllOrgByRootOrg() throws Exception {
        List<SysOrgElement> allOrgInRootOrg = new ArrayList<SysOrgElement>();
        List allOrgChildren = sysOrgElementService.findList("(fdOrgType=1) and fdIsAvailable=1", null);
        for (int i = 0; i < allOrgChildren.size(); i++) {
            SysOrgElement org = (SysOrgElement) allOrgChildren.get(i);
            if (StringUtil.isNotNull(DingConfig.newInstance().getDingOrgId())) {
                SysOrgElement parent = org.getFdParent();
                while (parent != null) {
                    if (DingConfig.newInstance().getDingOrgId().indexOf(parent.getFdId()) != -1) {
                        allOrgInRootOrg.add(org);
                        break;
                    }
                    parent = parent.getFdParent();
                }
            } else {
                allOrgInRootOrg.add(org);
            }
        }
        return allOrgInRootOrg;
    }

    private void terminate() throws Exception {
        logger.debug("同步的部门机构数据：" + syncDepts.size() + "条");
        logger.debug("同步的岗位数据：" + syncPosts.size() + "条");
        logger.debug("同步的人员数据：" + syncPersons.size() + "条");
        int delomserror = session.createQuery("delete from ThirdDingOmsError where fdOms='ekp'").executeUpdate();
        logger.debug("删除上次异常同步数据：" + delomserror + "条");
        if (errors != null && errors.size() > 0) {
            for (ThirdDingOmsError error : errors) {
                thirdDingOmsErrorService.add(error);
            }
            logger.debug("添加本次异常同步数据：" + errors.size() + "条");
        }
        if (omsPost != null && omsPost.size() > 0) {
            for (ThirdDingOmsPost post : omsPost) {
                thirdDingOmsPostService.getBaseDao().delete(post);
            }
        }
        if (StringUtil.isNotNull(lastUpdateTime) && pesonFlag) {
            dingOmsConfig.setLastUpdateTime(lastUpdateTime);
            dingOmsConfig.save();
        }
    }

    private void adminHandle() throws Exception {
        ISysOrgPersonService personService = null;
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
                    personService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
                    for (int i = 0; i < ja.size(); i++) {
                        jo = ja.getJSONObject(i);
                        rjo = dingApiService.userGet(jo.getString("userid"),
                                null);
                        if (rjo == null || !rjo.containsKey("mobile") || StringUtil.isNull(rjo.getString("mobile"))) {
                            continue;
                        }
                        SysOrgPerson pls = (SysOrgPerson) personService
                                .findFirstOne("fdMobileNo='" + rjo.getString("mobile") + "' and fdIsAvailable = 1", null);
                        if (pls != null) {
                            OmsRelationModel model = (OmsRelationModel) omsRelationService.findFirstOne("fdEkpId='" + pls.getFdId() + "'", null);
                            if (model == null) {
                                model = new OmsRelationModel();
                                model.setFdEkpId(pls.getFdId());
                                model.setFdAppPkId(jo.getString("userid"));
                                model.setFdAppKey(getAppKey());
                                omsRelationService.add(model);
                                logInfo += " ,create (" + pls.getFdName() + ")";
                            } else {
                                model.setFdEkpId(pls.getFdId());
                                model.setFdAppPkId(jo.getString("userid"));
                                model.setFdAppKey(getAppKey());
                                omsRelationService.update(model);
                                logInfo += " ,update (" + pls.getFdName() + ")";
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
        private final List<SysOrgPerson> persons;

        public PersonRunner(List<SysOrgPerson> persons) {
            this.persons = persons;
        }

        @Override
        public void run() {
            try {
                handlePerson(persons);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("", e);
                pesonFlag = false;
            } finally {
                countDownLatch.countDown();
            }
        }
    }

    /**
     * @throws Exception 获取数据库中的需要同步的机构、部门和人员
     */
    private void getSyncData() throws Exception {
        syncTime = 0L;
        SysOrgElement temp = null;
        if (StringUtil.isNotNull(lastUpdateTime)) {
            syncTime = DateUtil.convertStringToDate(lastUpdateTime, "yyyy-MM-dd HH:mm:ss.SSS").getTime();
        }
        // 获取人员
        List<SysOrgPerson> persons = getData(ORG_TYPE_PERSON);
        if (persons != null && !persons.isEmpty()) {
            syncPersons.addAll(persons);
            if (persons.get(0) != null && persons.get(0).getFdAlterTime() != null) {
                if (syncTime < persons.get(0).getFdAlterTime().getTime()) {
                    syncTime = persons.get(0).getFdAlterTime().getTime();
                }
            }
        }
        // 获取部门
        List<SysOrgElement> depts = getData(ORG_TYPE_DEPT);
        // 根部门每次都需要更新，因为当增加了部门时，要更新可查看范围
        List<SysOrgElement> rootDepts = findRootDepts();
        if (CollectionUtils.isNotEmpty(depts)) {
            for (SysOrgElement ele : rootDepts) {
                if (!depts.contains(ele)) {
                    depts.add(ele);
                }
            }
        } else {
            depts = rootDepts;
        }

        if (depts != null && !depts.isEmpty()) {
            // 初始化部门查看范围权限
            initRange(depts);
            syncDepts.addAll(depts);
            if (depts.get(0) != null && depts.get(0).getFdAlterTime() != null) {
                if (syncTime < depts.get(0).getFdAlterTime().getTime()) {
                    syncTime = depts.get(0).getFdAlterTime().getTime();
                }
            }
            for (SysOrgElement dept : depts) {
                if (dept.getHbmThisLeader() == null) {
                    continue;
                }
                temp = sysOrgCoreService.format(dept.getHbmThisLeader());
                if (temp.getFdOrgType() == 8) {
                    syncPersons.add((SysOrgPerson) temp);
                } else if (temp.getFdOrgType() == 4) {
                    List<SysOrgPerson> list = temp.getFdPersons();
                    if (list != null && list.size() > 0) {
                        for (SysOrgPerson person : list) {
                            syncPersons.add(person);
                        }
                    }
                }
            }
        }

        // 获取岗位
        List<SysOrgElement> posts = getData(ORG_TYPE_POST);
        if (posts != null && !posts.isEmpty()) {
            syncPosts.addAll(posts);
            if (posts.get(0) != null && posts.get(0).getFdAlterTime() != null) {
                if (syncTime < posts.get(0).getFdAlterTime().getTime()) {
                    syncTime = posts.get(0).getFdAlterTime().getTime();
                }
            }
            for (SysOrgElement post : posts) {
                List<SysOrgPerson> list = post.getFdPersons();
                if (list != null && list.size() > 0) {
                    for (SysOrgPerson person : list) {
                        syncPersons.add(person);
                    }
                }
                if (post.getFdParent() != null) {
                    syncDepts.add(post.getFdParent());
                }
            }
        }
        // 获取历史岗位信息
        handleUpdatePost();
        if (syncTime != 0) {
            lastUpdateTime = DateUtil.convertDateToString(new Date(syncTime), "yyyy-MM-dd HH:mm:ss.SSS");
        }
        // 异常数据表的获取，优先处理
        String where = "fdOms='ekp' and fdEkpType in ('1','2')";
        List<Object> ids = thirdDingOmsErrorService.findValue("fdEkpId", where, null);
        if (ids != null && ids.size() > 0) {
            HQLInfo hqlInfo = new HQLInfo();
            where = HQLUtil.buildLogicIN("fdId", ids);
            hqlInfo.setWhereBlock(where);
            List<SysOrgElement> errordepts = sysOrgElementService.findList(hqlInfo);
            for (SysOrgElement dept : errordepts) {
                if (dept != null) {
                    syncDepts.add(dept);
                }
            }
        }
        where = "fdOms='ekp' and fdEkpType='8'";
        ids = thirdDingOmsErrorService.findValue("fdEkpId", where, null);
        if (ids != null && ids.size() > 0) {
            HQLInfo hqlInfo = new HQLInfo();
            where = HQLUtil.buildLogicIN("fdId", ids);
            hqlInfo.setWhereBlock(where);
            List<SysOrgPerson> errorpersons = sysOrgPersonService.findList(hqlInfo);
            for (SysOrgPerson person : errorpersons) {
                if (person != null) {
                    syncPersons.add(person);
                }
            }
        }
    }

    /**
     * @param type
     * @return
     * @throws Exception 根据传入的类型获取数据
     */
    private List getData(int type) throws Exception {
        List rtnList = new ArrayList();
        HQLInfo info = new HQLInfo();
        String sql = "1=1";
        // 部门全量同步，因为新增部门后需要把新增的部门更新到其他需要展示的地方去
        if (StringUtil.isNotNull(lastUpdateTime)) {
            Date date = DateUtil.convertStringToDate(lastUpdateTime, "yyyy-MM-dd HH:mm:ss.SSS");
            sql += " and fdAlterTime>:beginTime";
            info.setParameter("beginTime", date);
        }
        info.setOrderBy("fdAlterTime desc");
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
        errors.add(error);
    }

    private void testData() throws Exception {
        SysOrgOrg org = new SysOrgOrg();
        org.setFdOrgType(1);
        org.setFdName("测试机构");
        org.setFdIsAbandon(false);
        org.setFdIsAvailable(true);
        org.setFdIsBusiness(true);
        sysOrgElementService.add(org);
        Thread.sleep(10000);
        List<SysOrgOrg> orgs = sysOrgElementService.findList("fdName='测试机构'", null);
        if (orgs != null && orgs.size() > 0) {
            org = orgs.get(0);
        }
        for (int i = 1; i <= 10; i++) {
            SysOrgDept dept = new SysOrgDept();
            dept.setFdName("测试部门" + i);
            dept.setFdOrgType(2);
            dept.setFdParent(org);
            dept.setFdIsAbandon(false);
            dept.setFdIsAvailable(true);
            dept.setFdIsBusiness(true);
            sysOrgElementService.add(dept);
            Thread.sleep(3000);
            List<SysOrgDept> depts = sysOrgElementService.findList("fdName='测试部门" + i + "'", null);
            if (depts != null && depts.size() > 0) {
                dept = depts.get(0);
            }
            for (int j = 1; j <= 50; j++) {
                SysOrgPerson person = new SysOrgPerson();
                person.setFdName("部门" + i + "人员" + j);
                person.setFdOrgType(8);
                person.setFdParent(dept);
                person.setFdLoginName("person" + i + "" + j);
                person.setFdMobileNo((18600000000L + i * 1000 + j) + "");
                person.setFdPassword("1");
                person.setFdIsAbandon(false);
                person.setFdIsAvailable(true);
                person.setFdIsBusiness(true);
                sysOrgElementService.add(person);
                Thread.sleep(1000);
            }
        }
    }

    private void updateErrorData() throws Exception {
        // 人员处理
        String einfo = "手机号码在公司中已存在 userid:";
        String where = "fdOms='ekp' and fdEkpType='8'";
        String duserid = null;
        JSONObject jo = null;
        SysOrgElement dept = null;
        List<ThirdDingOmsError> errors = thirdDingOmsErrorService.findList(where, null);
        for (ThirdDingOmsError error : errors) {
            duserid = relationMap.get(error.getFdEkpId());
            if (StringUtil.isNull(duserid)) {
                continue;
            }
            jo = dingApiService.userGet(duserid, error.getFdEkpId());
            if (jo != null && jo.getInt("errcode") == 60121) {
                int index = error.getFdDesc().indexOf(einfo);
                if (index != -1) {
                    duserid = error.getFdDesc().substring(index + einfo.length(), error.getFdDesc().length());
                    logger.debug("获取正确的钉钉用户Userid=" + duserid);
                    OmsRelationModel model = (OmsRelationModel) omsRelationService.findFirstOne("fdEkpId='" + error.getFdEkpId() + "'", null);
                    if (model != null) {
                        model.setFdAppPkId(duserid);
                        omsRelationService.update(model);
                        relationMap.put(model.getFdEkpId(), model.getFdAppPkId());
                    }
                }
            }
        }
        // 部门处理
        where = "fdOms='ekp' and fdEkpType in ('1','2')";
        errors = thirdDingOmsErrorService.findList(where, null);
        String ddeptid = null;
        JSONArray ja = null;
        for (ThirdDingOmsError error : errors) {
            ddeptid = relationMap.get(error.getFdEkpId());
            if (StringUtil.isNull(ddeptid)) {
                continue;
            }
            jo = dingApiService.departGet(Long.valueOf(ddeptid));
            //处理部门不存在和父部门不存在的问题
            if (jo != null && jo.getInt("errcode") == 60003) {
                dept = (SysOrgElement) sysOrgElementService.findByPrimaryKey(error.getFdEkpId(), null, true);
                if (dept != null && dept.getFdParent() != null) {
                    ddeptid = relationMap.get(dept.getFdParent().getFdId());
                    if (StringUtil.isNotNull(ddeptid)) {
                        jo = dingApiService.departsSubGet(ddeptid);
                        if (jo != null && jo.getInt("errcode") == 0) {
                            ja = jo.getJSONArray("department");
                            for (int i = 0; i < ja.size(); i++) {
                                if (dept.getFdName().equals(ja.getJSONObject(i).getString("name"))) {
                                    OmsRelationModel model = (OmsRelationModel) omsRelationService.findFirstOne("fdEkpId='" + error.getFdEkpId() + "'",
                                            null);
                                    if (model != null) {
                                        model.setFdAppPkId(ja.getJSONObject(i).getString("id"));
                                        omsRelationService.update(model);
                                        relationMap.put(model.getFdEkpId(), model.getFdAppPkId());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (error.getFdDesc().indexOf("60008") != -1) {
                //父部门下该部门名称已存在，原因是中间表数据丢失导致重新新建，处理方式：删除父部门下重复的部门（如果无部门成员和子部门）
                dept = (SysOrgElement) sysOrgElementService.findByPrimaryKey(error.getFdEkpId(), null, true);
                if (dept != null && dept.getFdParent() != null) {
                    ddeptid = relationMap.get(dept.getFdParent().getFdId());
                    if (StringUtil.isNotNull(ddeptid)) {
                        jo = dingApiService.departsSubGet(ddeptid);
                        if (jo != null && jo.getInt("errcode") == 0) {
                            ja = jo.getJSONArray("department");
                            for (int i = 0; i < ja.size(); i++) {
                                if (dept.getFdName().equals(ja.getJSONObject(i).getString("name"))) {
                                    dingApiService.departDelete(ja.getJSONObject(i).getString("id"));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void log(String msg) {
        logger.debug("【EKP组织架构同步到钉钉】" + msg);
        if (this.jobContext != null) {
            jobContext.logMessage(msg);
        }
    }

    /**
     * 实时同步生态组织
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event == null) {
            return;
        }
        if (!(event instanceof SysOrgElementEcoAddEvent
                || event instanceof SysOrgElementEcoUpdateEvent)) {
            return;
        }
        SysOrgElement ele = null;
        if (event instanceof SysOrgElementEcoAddEvent) {
            SysOrgElementEcoAddEvent eleEvent = (SysOrgElementEcoAddEvent) event;
            ele = eleEvent.getSysOrgElement();
        }
        if (event instanceof SysOrgElementEcoUpdateEvent) {
            SysOrgElementEcoUpdateEvent eleEvent = (SysOrgElementEcoUpdateEvent) event;
            ele = eleEvent.getSysOrgElement();
        }
        if (ele == null) {
            return;
        }
        final String orgId = ele.getFdId();
        try {
            if (!SysOrgEcoUtil.IS_ENABLED_ECO || !"true".equals(DingConfig.newInstance().getDingOmsExternal())) {
                logger.debug("钉钉集成生态组织实时同步已经关闭，故不同步数据");
                return;
            }
            if (ele.getFdOrgType() == ORG_TYPE_ORG || ele.getFdOrgType() == ORG_TYPE_DEPT
                    || ele.getFdOrgType() == ORG_TYPE_PERSON || ele.getFdOrgType() == ORG_TYPE_POST) {
                multicaster.attatchEvent(
                        new EventOfTransactionCommit(StringUtils.EMPTY),
                        new IEventCallBack() {
                            @Override
                            public void execute(ApplicationEvent arg0)
                                    throws Throwable {
                                taskExecutor.execute(new DeptRunner(orgId));
                            }
                        });
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    class DeptRunner implements Runnable {
        private final String orgId;

        public DeptRunner(String orgId) {
            this.orgId = orgId;
        }

        @Override
        public void run() {
            new SersyncOrg2Ding(orgId).triggerSynchro();
        }
    }
}
