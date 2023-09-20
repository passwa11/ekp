package com.landray.kmss.third.ding.oms;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.*;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
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
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.TransactionStatus;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

/**
 * 实时同步生态组织到钉钉
 *
 * @author panyh
 * <p>
 * 2020年9月5日 下午4:29:41
 */
public class SersyncOrg2Ding implements DingConstant, SysOrgConstant {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SersyncOrg2Ding.class);

    /**
     * 传入的组织ID
     */
    private String orgId;
    /**
     * 需要同步的组织ID
     */
    private String syncId;

    private DingApiService dingApiService;
    private ISysOrgElementService sysOrgElementService;
    private ISysOrgPersonService sysOrgPersonService;
    private IOmsRelationService omsRelationService;
    private ISysOrgCoreService sysOrgCoreService;
    private ISysAppConfigService sysAppConfigService;
    private IBaseService sysZonePersonInfoService;
    private IThirdDingOmsPostService thirdDingOmsPostService;
    private IThirdDingOmsErrorService thirdDingOmsErrorService;

    private Set<SysOrgElement> syncDepts = new HashSet<SysOrgElement>(500);
    private Set<SysOrgPost> syncPosts = new HashSet<SysOrgPost>(500);
    private Set<SysOrgPerson> syncPersons = new HashSet<SysOrgPerson>(2000);
    private volatile Set<ThirdDingOmsError> errors = new HashSet<ThirdDingOmsError>(100);
    private Map<String, ThirdDingOmsPost> omsPostMap = new HashMap<String, ThirdDingOmsPost>(500);
    private Map<String, JSONObject> createDeptMap = new HashMap<String, JSONObject>();
    private Map<String, String> relationMap = new HashMap<String, String>(5000);
    private Map<String, Map<String, String>> ppMap = new HashMap<String, Map<String, String>>();
    private Map<String, String> ppersonMap = new HashMap<String, String>();
    private Map<String, SysOrgElementRange> rangeMap = new HashMap<String, SysOrgElementRange>(1000);
    private Map<String, SysOrgElement> synRootDepts = new HashMap<String, SysOrgElement>(500);
    private Set<Object> rootOrgChildren = new HashSet<Object>(5000);
    private Set<ThirdDingOmsPost> omsPost = new HashSet<ThirdDingOmsPost>(500);
    private Map<String, List<SysOrgElement>> deptLeaderMap = new HashMap<String, List<SysOrgElement>>();

    public SersyncOrg2Ding(String orgId) {
        this.orgId = orgId;
    }

    private void init() throws Exception {
        dingApiService = DingUtils.getDingApiService();
        sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
        sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
        omsRelationService = (IOmsRelationService) SpringBeanUtil.getBean("omsRelationService");
        sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
        sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
        sysZonePersonInfoService = (IBaseService) SpringBeanUtil.getBean("sysZonePersonInfoService");
        thirdDingOmsPostService = (IThirdDingOmsPostService) SpringBeanUtil.getBean("thirdDingOmsPostService");
        thirdDingOmsErrorService = (IThirdDingOmsErrorService) SpringBeanUtil.getBean("thirdDingOmsErrorService");
    }

    private void initData() throws Exception {
        // 需要同步的组织架构列表初始化（直接读取数据库）
        SysOrgElement elem = (SysOrgElement) sysOrgElementService.findByPrimaryKey(syncId);
        if (elem == null) {
            return;
        }
        String[] orgIds = null;
        if (StringUtil.isNotNull(DingConfig.newInstance().getDingOrgId())) {
            orgIds = DingConfig.newInstance().getDingOrgId().split(";");
        } else {
            List<String> tempIds = new ArrayList<String>();
            List<SysOrgElement> rootList = sysOrgCoreService.findDirectChildren(null, ORG_TYPE_ORGORDEPT);
            for (SysOrgElement ele : rootList) {
                tempIds.add(ele.getFdId());
            }
            orgIds = tempIds.toArray(new String[tempIds.size()]);
        }
        for (String orgId : orgIds) {
            if (elem.getFdId().equals(orgId)) {
                rootOrgChildren.add(elem.getFdId());
                synRootDepts.put(elem.getFdId(), elem);
            }
        }
        logger.debug("实时同步机构所有数据（有效）：" + rootOrgChildren.size() + "条");

        // 关系映射表初始化
        List list = omsRelationService.findList("fdAppKey='" + getAppKey() + "'", null);
        for (int i = 0; i < list.size(); i++) {
            OmsRelationModel model = (OmsRelationModel) list.get(i);
            relationMap.put(model.getFdEkpId(), model.getFdAppPkId());
        }

        updateErrorData();
        if (elem.getFdOrgType() == ORG_TYPE_POST || elem.getFdOrgType() == ORG_TYPE_PERSON) {
            // 岗位缓存的初始化
            List<ThirdDingOmsPost> omsPosts = thirdDingOmsPostService.findList(null, null);
            for (ThirdDingOmsPost op : omsPosts) {
                if (StringUtil.isNotNull(op.getFdContent())) {
                    op.setDocContent(op.getFdContent());
                    op.setFdContent("");
                    thirdDingOmsPostService.update(op);
                }
                omsPostMap.put(op.getFdId(), op);
            }

            // 岗位数据的缓存
            List<Object[]> personPosts;
            personPosts = sysOrgElementService.getBaseDao().getHibernateSession().createNativeQuery(
                            "select pp.fd_personid,pp.fd_postid,ele.fd_name,ele.fd_parentid from sys_org_post_person pp,sys_org_element ele where pp.fd_postid=ele.fd_id and ele.fd_is_external = :external")
                    .setParameter("external", true).list();
            Map<String, String> tempMap = null;
            for (Object[] pps : personPosts) {
                if (pps[0] != null && pps[1] != null && pps[2] != null) {
                    // 已人为角度构建的人与岗位关系
                    if (ppMap.containsKey(pps[0].toString())) {
                        tempMap = ppMap.get(pps[0].toString());
                        tempMap.put("postids", tempMap.get("postids") + ";" + pps[1].toString());
                        if (tempMap.get("names").indexOf(pps[2].toString()) == -1) {
                            tempMap.put("names", tempMap.get("names") + ";" + pps[2].toString());
                        }
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
        }
    }

    private String getAppKey() {
        return StringUtil.isNull(DING_OMS_APP_KEY) ? "default" : DING_OMS_APP_KEY;
    }

    private String getDingRootDeptId() {
        return StringUtil.isNull(DingConfig.newInstance().getDingDeptid()) ? "1"
                : DingConfig.newInstance().getDingDeptid();
    }

    private void log(String msg) {
        logger.debug("【EKP组织架构同步到钉钉】" + msg);
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

    private void addRelation(SysOrgElement element, String appPkId, String type) throws Exception {
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            OmsRelationModel model = new OmsRelationModel();
            model.setFdEkpId(element.getFdId());
            model.setFdAppPkId(appPkId);
            model.setFdAppKey(getAppKey());
            model.setFdType(type);
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

    private void check() {
        try {
            // 初始化
            init();

            // 判断同步的组织是否在可同步的组织内
            String dingOrgId = DingConfig.newInstance().getDingOrgId();
            if (StringUtil.isNotNull(dingOrgId)) {
                String[] orgIds = dingOrgId.split(";");
                // 是否同步根组织
                String dingOmsRootFlag = DingConfig.newInstance().getDingOmsRootFlag();
                List<String> rootIds = null;
                if (!"true".equals(dingOmsRootFlag)) { // 不同步根组织，取根组织下面的子组织
                    HQLInfo hqlInfo = new HQLInfo();
                    hqlInfo.setSelectBlock("sysOrgElement.fdId");
                    hqlInfo.setWhereBlock("sysOrgElement.hbmParent.fdId in ("
                            + SysOrgUtil.buildInBlock(new HashSet<String>(Arrays.asList(orgIds)))
                            + ") and sysOrgElement.fdIsAvailable = true and sysOrgElement.fdIsBusiness = true");
                    rootIds = sysOrgElementService.findList(hqlInfo);
                } else {
                    rootIds = Arrays.asList(orgIds);
                }
                HQLInfo hqlInfo = new HQLInfo();
                hqlInfo.setSelectBlock("sysOrgElement.fdId, sysOrgElement.fdHierarchyId");
                hqlInfo.setWhereBlock("sysOrgElement.fdId = :orgId");
                hqlInfo.setParameter("orgId", orgId);
                List<Object[]> deptLists = sysOrgElementService.findList(hqlInfo);
                for (Object[] dept : deptLists) {
                    for (String rootId : rootIds) {
                        if ("0".equals(dept[1].toString()) || dept[1].toString().contains(
                                BaseTreeConstant.HIERARCHY_ID_SPLIT + rootId + BaseTreeConstant.HIERARCHY_ID_SPLIT)) {
                            syncId = dept[0].toString();
                        }
                    }
                }
            } else {
                syncId = orgId;
            }
        } catch (Exception e) {
            logger.error("处理同步的根组织失败：", e);
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
            // 处理部门不存在和父部门不存在的问题
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
                // 父部门下该部门名称已存在，原因是中间表数据丢失导致重新新建，处理方式：删除父部门下重复的部门（如果无部门成员和子部门）
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

    /**
     * 执行同步操作
     */
    public void triggerSynchro() {
        check();
        if (StringUtil.isNull(syncId)) {
            logger.info("未找到需要实时同步的组织，无需同步");
            return;
        }
        TransactionStatus status = null;
        try {
            logger.warn("开始实时同步生态组织到钉钉,syncId:" + syncId);
            status = TransactionUtils.beginTransaction();
            sysOrgElementService.getBaseDao().flushHibernateSession();
            sysOrgElementService.getBaseDao().clearHibernateSession();
            synchro();
            TransactionUtils.getTransactionManager().commit(status);
            logger.warn("实时同步生态组织到钉钉任务完成,syncId:" + syncId);
        } catch (Exception e) {
            logger.error("实时同步生态组织失败：syncId:" + syncId, e);
            if (status != null) {
                TransactionUtils.getTransactionManager().rollback(status);
            }
        }
    }

    /**
     * 生态组织实时同步
     */
    public void synchro() {
        String temp = "";
        if (!"true".equals(DingConfig.newInstance().getDingEnabled())) {
            temp = "钉钉集成已经关闭，故不同步数据";
            logger.info(temp);
            return;
        }
        if (StringUtil.isNotNull(DingConfig.newInstance().getSyncSelection())) {
            if (!"1".equals(DingConfig.newInstance().getSyncSelection())) {
                temp = "钉钉集成-通讯录配置-同步选择-从本系统同步到钉钉未开启，故不同步数据";
                logger.info(temp);
                return;
            }
        } else {
            if (!"true".equals(DingConfig.newInstance().getDingOmsOutEnabled())) {
                temp = "钉钉组织架构接出已经关闭，故不同步数据";
                logger.debug(temp);
                return;
            }
        }
        if (!SysOrgEcoUtil.IS_ENABLED_ECO || !"true".equals(DingConfig.newInstance().getDingOmsExternal())) {
            temp = "钉钉集成生态组织实时同步已经关闭，故不同步数据";
            logger.debug(temp);
            return;
        }

        try {
            long alltime = System.currentTimeMillis();

            // 初始化数据
            long caltime = System.currentTimeMillis();
            initData();
            temp = "初始化数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
            logger.debug(temp);

            // 获取EKP组织数据
            getSyncData();

            // 已经禁用且同步到钉钉之后，不会再执行同步，防止重复处理
            if (CollectionUtils.isNotEmpty(syncDepts)) {
                Iterator<SysOrgElement> iter = syncDepts.iterator();
                while (iter.hasNext()) {
                    SysOrgElement ele = iter.next();
                    if (!ele.getFdIsAvailable()) {
                        if (relationMap == null || !relationMap.containsKey(ele.getFdId())) {
                            logger.debug("该组织已经禁用且同步到钉钉，故不同步数据");
                            iter.remove();
                        }
                    }
                }
            }
            if (CollectionUtils.isEmpty(syncDepts) && CollectionUtils.isEmpty(syncPersons)) {
                logger.debug("无组织同步");
                return;
            }
            // 添加钉钉的部门数据
            if (CollectionUtils.isNotEmpty(syncDepts)) {
                caltime = System.currentTimeMillis();
                handleDept(new ArrayList(syncDepts));
                temp = "添加钉钉的部门数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
                logger.debug(temp);

                // 更新钉钉部门层级数据
                caltime = System.currentTimeMillis();
                handleUpdateDept(new ArrayList(syncDepts));
                temp = "更新钉钉部门层级和部门主管数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
                logger.debug(temp);
            }

            // 更新人员
            if (CollectionUtils.isNotEmpty(syncPersons)) {
                caltime = System.currentTimeMillis();
                handlePerson(new ArrayList(syncPersons));
                temp = "更新人员数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
                logger.debug(temp);
            }

            // 先迁移人员，后删除部门，部门中有人员，不能删除
            if (CollectionUtils.isNotEmpty(syncDepts)) {
                caltime = System.currentTimeMillis();
                handleDelDept(new ArrayList(syncDepts));
                temp = "先迁移人员，后删除部门，部门中有人员，不能删除数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
                logger.debug(temp);
            }

            temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000;
            logger.debug(temp);
        } catch (Exception ex) {
            logger.error("生态组织实时同步到钉钉任务失败:", ex);
        } finally {
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
     * @throws Exception 获取数据库中的需要同步的机构、部门
     */
    private void getSyncData() throws Exception {
        // 获取数据
        SysOrgElement elem = (SysOrgElement) sysOrgElementService.findByPrimaryKey(syncId);
        if (elem == null) {
            return;
        }
        if (elem.getFdOrgType() == ORG_TYPE_ORG || elem.getFdOrgType() == ORG_TYPE_DEPT) {
            rangeMap.put(elem.getFdId(), elem.getFdRange());
            syncDepts.add(elem);
        } else if (elem.getFdOrgType() == ORG_TYPE_PERSON) {
            SysOrgPerson person = (SysOrgPerson) sysOrgCoreService.format(elem);
            syncPersons.add(person);
            List<SysOrgPost> posts = person.getFdPosts();
            if (CollectionUtils.isNotEmpty(posts)) {
                syncPosts.addAll(posts);
            }
        } else if (elem.getFdOrgType() == ORG_TYPE_POST) {
            SysOrgPost post = (SysOrgPost) sysOrgCoreService.format(elem);
            syncPosts.add(post);
            List<SysOrgPerson> persons = post.getFdPersons();
            if (CollectionUtils.isNotEmpty(persons)) {
                syncPersons.addAll(persons);
            }
            if (post.getFdParent() != null) {
                syncDepts.add(post.getFdParent());
            }
        }

        // 获取历史岗位信息
        handleUpdatePost();
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
            if (dept.getFdIsAvailable()) {
                // 新增
                if (!relationMap.keySet().contains(dept.getFdId())) {
                    JSONObject jodept = new JSONObject();
                    //name不允许超过64位，在保留后部分不变的情况下，缩减部门名称长度
                    String name = DingUtil.getString(dept.getFdName(), (64 - (String.valueOf(n).length() + 1))) + "_" + n;
                    jodept.accumulate("name", name);// 增加部门挂在根下时，会出现重复名，在此加ID区分，会在更新操作时修改正确
                    jodept.accumulate("parentid", Integer.valueOf(getDingRootDeptId()));
                    String oldCreateDeptGroup = DingConfig.newInstance().getDingOmsCreateDeptGroup();
                    if (StringUtil.isNotNull(oldCreateDeptGroup)) {
                        logger.debug("旧方式创建部门群");
                        if (!"true".equals(DingConfig.newInstance().getDingOmsCreateDeptGroup())) {
                            jodept.accumulate("createDeptGroup", false);
                        } else {
                            jodept.accumulate("createDeptGroup", true);
                        }
                    } else {
                        logger.debug("新配置方式创建部门群");
                        String o2d_groupSynWay = DingConfig.newInstance().getOrg2dingDeptGroupSynWay();
                        logger.debug("o2d_groupSynWay:" + o2d_groupSynWay);
                        if ("syn".equalsIgnoreCase(o2d_groupSynWay) || ("addSyn".equalsIgnoreCase(o2d_groupSynWay))) {
                            String o2d_groupAll = DingConfig.newInstance().getOrg2dingDeptGroupAll();
                            String o2d_group = DingConfig.newInstance().getOrg2dingDeptGroup();
                            logger.debug("o2d_groupAll:" + o2d_groupAll + " o2d_group:" + o2d_group);
                            if (StringUtil.isNotNull(o2d_groupAll) && "true".equals(o2d_groupAll)) {
                                jodept.accumulate("createDeptGroup", true);
                            } else {
                                if (StringUtil.isNotNull(o2d_group)) {
                                    String groupValue = getDeptProperty(o2d_group, dept);
                                    System.out.println("groupValue:" + groupValue);
                                    if ("true".equals(groupValue) || "1".equals(groupValue) || "是".equals(groupValue)) {
                                        jodept.accumulate("createDeptGroup", "true");
                                    } else if ("false".equals(groupValue) || "0".equals(groupValue)
                                            || "否".equals(groupValue)) {
                                        jodept.accumulate("createDeptGroup", "false");
                                    } else {
                                        logger.warn("创建部门群配置的字段值非true/false 1/0 是/否!");
                                    }
                                }
                            }

                        }

                    }

                    // 部门排序
                    String o2d_orderWay = DingConfig.newInstance().getOrg2dingDeptOrderSynWay();
                    if (StringUtil.isNull(o2d_orderWay)) {
                        logger.debug("旧方式同步部门排序");
                        if (dept.getFdOrder() != null) {
                            jodept.accumulate("order", dept.getFdOrder());
                        }
                    } else {
                        logger.debug("新配置方式同步部门排序！");

                        if ("syn".equalsIgnoreCase(o2d_orderWay) || ("addSyn".equalsIgnoreCase(o2d_orderWay))) {
                            String o2d_order = DingConfig.newInstance().getOrg2dingDeptOrder();
                            logger.debug("o2d_deptOrder:" + o2d_order);
                            if (StringUtil.isNotNull(o2d_order)) {

                                if ("fdOrder".equals(o2d_order)) {
                                    jodept.accumulate("order", dept.getFdOrder());
                                } else {
                                    logger.debug("部门排序配置类fdOrder之外的字段，尝试获取改字段：" + o2d_order);
                                    String orderValue = getDeptProperty(o2d_order, dept);
                                    logger.debug("orderValue:" + orderValue);
                                    if (StringUtil.isNotNull(orderValue)) {
                                        jodept.accumulate("order", orderValue);
                                    } else {
                                        logger.debug("根据" + o2d_order + "字段获取的数据为空");
                                        jodept.accumulate("order", "");
                                    }

                                }

                            } else {
                                logger.debug("部门排序配置字段为空！");
                            }

                        }
                    }

                    logInfo = "增加部门到钉钉" + dept.getFdName() + ", " + dept.getFdId();
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
                            addRelation(dept, id, "2");
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

    // 根据字段获取部门的值，该值范围：dept和自定义
    private String getDeptProperty(String key, SysOrgElement element) {
        try {
            logger.debug("key:" + key + " ,name:" + element.getFdName());
            if (StringUtil.isNull(key)) {
                return null;
            }
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
                String _key = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
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

    private void handleUpdateDept(List<SysOrgElement> depts) throws Exception {
        // 更新所有部门，主要是更新关系
        String logInfo = null;
        String rtn = null;
        if (CollectionUtils.isEmpty(depts)) {
            return;
        }
        for (SysOrgElement dept : depts) {
            if (dept.getFdIsAvailable()) {
                if (dept.getFdParent() == null) {
                    logInfo = "更新部: " + dept.getFdName() + " " + dept.getFdId() + ",对应钉钉ID:"
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

        String deptNameSynWay = DingConfig.newInstance().getOrg2dingDeptNameSynWay();
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
        String parentDeptSynWay = DingConfig.newInstance().getOrg2dingDeptParentDeptSynWay();
        logger.debug("parentDeptSynWay:" + parentDeptSynWay);
        if (StringUtil.isNull(parentDeptSynWay)
                || (StringUtil.isNotNull(parentDeptSynWay) && "syn".equals(parentDeptSynWay)) || isCreate) {

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
        String oldCreateDeptGroup = DingConfig.newInstance().getDingOmsCreateDeptGroup();
        if (StringUtil.isNotNull(oldCreateDeptGroup)) {
            logger.debug("旧方式创建部门群");
            if (!"true".equals(oldCreateDeptGroup)) {
                dept.accumulate("createDeptGroup", "false");
            } else {
                dept.accumulate("createDeptGroup", "true");
            }
        } else {
            logger.debug("新配置方式创建部门群");
            String o2d_groupSynWay = DingConfig.newInstance().getOrg2dingDeptGroupSynWay();
            logger.debug("o2d_groupSynWay:" + o2d_groupSynWay);
            if ("syn".equalsIgnoreCase(o2d_groupSynWay) || (isCreate && "addSyn".equalsIgnoreCase(o2d_groupSynWay))) {
                String o2d_groupAll = DingConfig.newInstance().getOrg2dingDeptGroupAll();
                String o2d_group = DingConfig.newInstance().getOrg2dingDeptGroup();
                logger.debug("o2d_groupAll:" + o2d_groupAll + " o2d_group:" + o2d_group);
                if (StringUtil.isNotNull(o2d_groupAll) && "true".equals(o2d_groupAll)) {
                    dept.accumulate("createDeptGroup", true);
                } else {
                    if (StringUtil.isNotNull(o2d_group)) {
                        String groupValue = getDeptProperty(o2d_group, ekpdept);
                        logger.debug("groupValue:" + groupValue);
                        if ("true".equals(groupValue) || "1".equals(groupValue) || "是".equals(groupValue)) {
                            dept.accumulate("createDeptGroup", true);
                        } else if ("false".equals(groupValue) || "0".equals(groupValue) || "否".equals(groupValue)) {
                            dept.accumulate("createDeptGroup", false);
                        } else {
                            logger.warn("创建部门群配置的字段值非true/false!");
                        }
                    }
                }

            }

        }

        // 是否同步部门主管
        String dingDeptLeaderEnabled = DingConfig.newInstance().getDingDeptLeaderEnabled();
        if (StringUtil.isNotNull(dingDeptLeaderEnabled)) {// 旧开关方式
            logger.debug("旧方式同步部门主管");
            if ("true".equals(dingDeptLeaderEnabled)) {
                String userIdList = getDeptManagerUseridList(ekpdept);
                dept.accumulate("deptManagerUseridList", userIdList);
            }
        } else {
            logger.debug("新配置方式同步部门主管");
            String o2d_deptManagerWay = DingConfig.newInstance().getOrg2dingDeptDeptManagerSynWay();
            logger.debug("o2d_deptManagerWay:" + o2d_deptManagerWay);
            if (StringUtil.isNotNull(o2d_deptManagerWay)) {
                if ("noSyn".equals(o2d_deptManagerWay)) {
                    logger.debug("部门主管同步功能未开启!");
                }
                // else if ("syn".equals(o2d_deptManagerWay)) {
                else if ("syn".equals(o2d_deptManagerWay)
                        || (isCreate && "addSyn".equalsIgnoreCase(o2d_deptManagerWay))) {
                    String userIdList = getDeptManagerUseridList(ekpdept);
                    logger.debug("ManagerUseridList:" + userIdList);
                    dept.accumulate("deptManagerUseridList", userIdList);
                }

            }
        }

        // 排序号
        String o2d_orderWay = DingConfig.newInstance().getOrg2dingDeptOrderSynWay();
        if (StringUtil.isNull(o2d_orderWay)) {
            logger.debug("旧方式同步部门排序");
            if (ekpdept.getFdOrder() != null) {
                dept.accumulate("order", ekpdept.getFdOrder());
            }
        } else {
            logger.debug("新配置方式同步部门排序！");

            if ("syn".equalsIgnoreCase(o2d_orderWay) || (isCreate && "addSyn".equalsIgnoreCase(o2d_orderWay))) {
                String o2d_order = DingConfig.newInstance().getOrg2dingDeptOrder();
                logger.debug("o2d_deptOrder:" + o2d_order);
                if (StringUtil.isNotNull(o2d_order)) {

                    if ("fdOrder".equals(o2d_order)) {
                        dept.accumulate("order", ekpdept.getFdOrder());
                    } else {
                        logger.debug("部门排序配置类fdOrder之外的字段，尝试获取改字段：" + o2d_order);
                        String orderValue = getDeptProperty(o2d_order, ekpdept);
                        logger.debug("orderValue:" + orderValue);
                        if (StringUtil.isNotNull(orderValue)) {
                            dept.accumulate("order", orderValue);
                        } else {
                            logger.debug("根据" + o2d_order + "字段获取的数据为空");
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
                        logger.debug(
                                "添加" + element.getFdName() + "(" + deptDid + ")的部门主管信息：" + relationMap.get(personId));
                        // 先判断该用户是否在该部门下 userUpdate
                        JSONObject person = dingApiService.userGet(
                                relationMap.get(personId), element.getFdId());
                        if (person != null && person.containsKey("department")) {
                            String dept = person.getString("department");
                            JSONArray jas = JSONArray.fromObject(dept);
                            logger.debug("jas:" + jas);
                            if (!jas.contains(deptDid)) {
                                logger.debug("添加人员到该部门下");
                                jas.add(deptDid);
                                JSONObject user = new JSONObject();
                                user.accumulate("userid", relationMap.get(personId));
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
        SysOrgElementRange range = rangeMap.get(dept.getFdId());
        if (range != null) {
            setRange(range, jodept);
        }
        //设置隐藏可见性
        setHideRange(dept, jodept);
    }

    /**
     * 设置组织管理员可见
     *
     * @param dept
     * @param jsonObject
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/10/12 10:48 上午
     */
    private void setOrgAuthAdminRange(SysOrgElement dept, JSONObject jsonObject){
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
            jsonObject.put("deptPermits", deptPermits.toString());
            jsonObject.put("userPermits", userPermits.toString());
        }
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

        //从部门中根据同步状态读取数据
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
                                if (StringUtil.isNotNull(dingId) && pid.indexOf(dingId) == -1) {
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
            if (ret.getInt("errcode") == 0 || ret.getInt("errcode") == 60102 || ret.getInt("errcode") == 60104) {

                // #111497
                if (ret.getInt("errcode") == 60104 && ret.containsKey("userid")) {
                    // 先判断是否有用户 userid是否已存在
                    logger.warn(person.getFdName() + "的号码(" + person.getFdMobileNo() + ")在钉钉已经存在 : " + ret);
                    if (relationMap.containsValue(ret.getString("userid"))) {
                        logInfo += "  该userId已有映射关系，不再更新映射关系!userid:" + ret.getString("userid");
                        logger.warn("该userId已有映射关系，不再更新映射关系!userid:" + ret.getString("userid"));

                    } else {
                        logInfo += " ,created";
                        logger.warn("建立映射关系并且更新用户" + person.getFdName() + "数据   userid:" + ret.getString("userid")
                                + "    ekpId:" + person.getFdId());
                        addRelation(person, ret.getString("userid"), "8");
                        String update_ret = dingApiService.userUpdate(getUser(person, "add"));
                        logger.debug("update_ret:  " + update_ret);
                    }

                } else {
                    logInfo += " ,created";
                    String o2d_userId = DingConfig.newInstance().getOrg2dingUserid();
                    if (StringUtil.isNotNull(o2d_userId)) {
                        logger.debug("o2d_userId:" + o2d_userId);
                        if ("fdId".equals(o2d_userId)) {
                            addRelation(person, person.getFdId(), "8");
                        } else {
                            addRelation(person, person.getFdLoginName(), "8");
                        }
                    } else {
                        String wxln = DingConfig.newInstance().getWxLoginName();
                        if ("id".equalsIgnoreCase(wxln)) {
                            addRelation(person, person.getFdId(), "8");
                        } else {
                            addRelation(person, person.getFdLoginName(), "8");
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
                JSONObject ret = dingApiService.userCreate(getUser(person, "add"));
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
        String logInfo = "删除钉钉中未激活的手机号用户 " + person.getFdName() + "," + person.getFdId() + ",loginName:"
                + person.getFdLoginName();
        String userid = relationMap.get(person.getFdId());
        if (StringUtil.isNull(userid)) {
            logInfo = "删除钉钉中未激活的手机号用户：因为中间映射表无相关数据无法执行 ";
            logger.warn(logInfo);
            log(logInfo);
            return;
        }
        JSONObject ujo = dingApiService.userGet(userid, person.getFdId());
        if (ujo.containsKey("active") && !ujo.getBoolean("active")) {
            logInfo += " ," + dingApiService.userDelete(userid);
            logInfo += "\n";
            omsRelationService.deleteByKey(person.getFdId(), getAppKey());
            relationMap.remove(person.getFdId());
            // 增加新的钉钉用户
            logInfo += "增加新的钉钉用户（激活） " + person.getFdName() + "," + person.getFdId() + ",loginName:"
                    + person.getFdLoginName();
            JSONObject ret = dingApiService.userCreate(getUser(person, "add"));
            if (ret == null) {
                logInfo += ",不可预知的错误!";
            } else {
                if (ret.getInt("errcode") == 0) {
                    logInfo += " ,created";
                    String wxln = DingConfig.newInstance().getWxLoginName();
                    if ("id".equalsIgnoreCase(wxln)) {
                        addRelation(person, person.getFdId(), "8");
                    } else {
                        addRelation(person, person.getFdLoginName(), "8");
                    }
                } else {
                    logInfo += " 失败,出错信息：" + ret.getString("errmsg");
                }
            }
            logger.error(logInfo);
            log(logInfo);
        }
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

    private JSONObject getUser(SysOrgPerson element, String addOrUpdate) throws Exception {

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
                String o2d_userid = DingConfig.newInstance().getOrg2dingUserid();
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
                    || ("addSyn".equalsIgnoreCase(o2d_name) && "add".equalsIgnoreCase(addOrUpdate))) {
                logger.debug("添加name信息：" + element.getFdName());
                person.accumulate("name", element.getFdName());
            }
        }

        // 设置部门
        JSONArray depts = getUserDepartment(element, addOrUpdate);
        logger.debug("depts:" + depts);
        String mulDeptEnabled = DingConfig.newInstance().getDingPostMulDeptEnabled();
        if (StringUtil.isNull(mulDeptEnabled)) {
            // 新配置方式
            String o2d_DeptWay = DingConfig.newInstance().getOrg2dingDepartmentSynWay();
            logger.debug("o2d_DeptWay:" + o2d_DeptWay);
            if ("syn".equalsIgnoreCase(o2d_DeptWay)
                    || ("addSyn".equalsIgnoreCase(o2d_DeptWay) && "add".equalsIgnoreCase(addOrUpdate))) {
                person.accumulate("department", depts);
            }
        } else {
            logger.debug("旧方式同步一人多部门");
            person.accumulate("department", depts);
        }

        // 设置手机号码
        if (StringUtil.isNotNull(element.getFdMobileNo())) {
            String mobileWay = DingConfig.newInstance().getOrg2dingMobileSynWay();
            logger.debug("mobileWay:" + mobileWay);
            if (StringUtil.isNotNull(mobileWay)) {
                if ("syn".equalsIgnoreCase(mobileWay)
                        || ("addSyn".equalsIgnoreCase(mobileWay) && "add".equalsIgnoreCase(addOrUpdate))) {
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
                    || ("addSyn".equalsIgnoreCase(o2d_emailWay) && "add".equalsIgnoreCase(addOrUpdate))) {
                // 同步邮箱
                String o2d_email = DingConfig.newInstance().getOrg2dingEmail();
                logger.debug("o2d_email:" + o2d_email);
                if (StringUtil.isNotNull(o2d_email)) {
                    if ("fdEmail".equals(o2d_email)) {
                        person.accumulate("email", element.getFdEmail());
                    } else {
                        logger.warn("邮箱字段选择了其他字段，尝试获取该字段的值:" + o2d_email);
                        String emailValue = getPersonProperty(o2d_email, element);
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
                person.accumulate("tel", element.getFdWorkPhone());
            }
        } else {
            String o2d_telWay = DingConfig.newInstance().getOrg2dingTelSynWay();
            if (StringUtil.isNotNull(o2d_telWay)) {
                logger.debug("新方式同步办公电话！o2d_telWay：" + o2d_telWay);
                if ("syn".equalsIgnoreCase(o2d_telWay)
                        || ("addSyn".equalsIgnoreCase(o2d_telWay) && "add".equalsIgnoreCase(addOrUpdate))) {
                    String o2d_tel = DingConfig.newInstance().getOrg2dingTel();
                    logger.debug("o2d_tel:" + o2d_tel);
                    if (StringUtil.isNotNull(o2d_tel)) {
                        if ("fdWorkPhone".equals(o2d_tel)) {
                            person.accumulate("tel", element.getFdWorkPhone());
                        } else {
                            logger.warn("办公电话字段选择了其他字段，尝试获取该字段的值:" + o2d_tel);
                            String Value = getPersonProperty(o2d_tel, element);
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
            String o2d_jobnumberWay = DingConfig.newInstance().getOrg2dingJobnumberSynWay();
            if (StringUtil.isNotNull(o2d_jobnumberWay)) {
                logger.debug("新方式同步设置员工工号！o2d_jobnumberWay：" + o2d_jobnumberWay);
                if ("syn".equalsIgnoreCase(o2d_jobnumberWay)
                        || ("addSyn".equalsIgnoreCase(o2d_jobnumberWay) && "add".equalsIgnoreCase(addOrUpdate))) {
                    String o2d_jobnumber = DingConfig.newInstance().getOrg2dingJobnumber();
                    logger.debug("o2d_jobnumber:" + o2d_jobnumber);
                    if (StringUtil.isNotNull(o2d_jobnumber)) {
                        if ("fdNo".equals(o2d_jobnumber)) {
                            person.accumulate("jobnumber", element.getFdNo());
                        } else {
                            logger.warn("工号选择了编号字段其他字段，尝试获取该字段的值:" + o2d_jobnumber);
                            String jobnumbeValue = getPersonProperty(o2d_jobnumber, element);
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
            String o2d_dingPositionWay = DingConfig.newInstance().getOrg2dingPositionSynWay();
            if (StringUtil.isNotNull(o2d_dingPositionWay)) {
                logger.debug("新方式同步设置职位！o2d_dingPositionWay：" + o2d_dingPositionWay);
                if ("syn".equalsIgnoreCase(o2d_dingPositionWay)
                        || ("addSyn".equalsIgnoreCase(o2d_dingPositionWay) && "add".equalsIgnoreCase(addOrUpdate))) {
                    String o2d_dingPosition = DingConfig.newInstance().getOrg2dingPosition();
                    logger.debug("o2d_dingPosition:" + o2d_dingPosition);
                    if (StringUtil.isNotNull(o2d_dingPosition)) {
                        if ("hbmPosts".equals(o2d_dingPosition)) {
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
                            logger.warn("岗位选择了所属岗位字段的其他字段，尝试获取该字段的值:" + o2d_dingPosition);
                            String dingPositionValue = getPersonProperty(o2d_dingPosition, element);
                            logger.debug("dingPositionValue:" + dingPositionValue);
                            if (StringUtil.isNotNull(dingPositionValue)) {
                                person.accumulate("position", dingPositionValue);
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
                String o2d_orderWay = DingConfig.newInstance().getOrg2dingOrderInDeptsSynWay();
                logger.debug("o2d_order:" + o2d_orderWay);
                if ("syn".equalsIgnoreCase(o2d_orderWay)
                        || ("addSyn".equalsIgnoreCase(o2d_orderWay) && "add".equalsIgnoreCase(addOrUpdate))) {
                    String o2d_order = DingConfig.newInstance().getOrg2dingOrderInDepts();
                    if (StringUtil.isNotNull(o2d_order) && "desc".equals(o2d_order)) {
                        order = Integer.MAX_VALUE - order;
                    }
                    orderJson.accumulate(parentId, order);
                    person.accumulate("orderInDepts", orderJson);
                }
            }

        }

        // 备注
        String o2d_remarkWay = DingConfig.newInstance().getOrg2dingRemarkSynWay();
        if (StringUtil.isNotNull(o2d_remarkWay)) {
            logger.debug("新方式同步设置备注！o2d_remarkWay：" + o2d_remarkWay);
            if ("syn".equalsIgnoreCase(o2d_remarkWay)
                    || ("addSyn".equalsIgnoreCase(o2d_remarkWay) && "add".equalsIgnoreCase(addOrUpdate))) {
                String o2d_remark = DingConfig.newInstance().getOrg2dingRemark();
                logger.debug("o2d_remark:" + o2d_remark);
                if (StringUtil.isNotNull(o2d_remark)) {
                    if ("fdMemo".equals(o2d_remark)) {
                        person.accumulate("remark", element.getFdMemo());
                    } else {
                        logger.warn("备注选择了fdMemo字段其他字段，尝试获取该字段的值:" + o2d_remark);
                        String remarkValue = getPersonProperty(o2d_remark, element);
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
        String o2d_hiredDateWay = DingConfig.newInstance().getOrg2dingHiredDateSynWay();
        if (StringUtil.isNotNull(o2d_hiredDateWay)) {
            logger.debug("新方式同步入职时间！o2d_hiredDateWay：" + o2d_hiredDateWay);
            if ("syn".equalsIgnoreCase(o2d_hiredDateWay)
                    || ("addSyn".equalsIgnoreCase(o2d_hiredDateWay) && "add".equalsIgnoreCase(addOrUpdate))) {
                String o2d_hiredDate = DingConfig.newInstance().getOrg2dingHiredDate();
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
                        logger.warn("入职时间选择了fdHiredate其他字段，尝试获取该字段的值:" + o2d_hiredDate);
                        String hireDateValue = getPersonProperty(o2d_hiredDate, element);
                        logger.debug("hireDateValue:" + hireDateValue);
                        if (StringUtil.isNotNull(hireDateValue)) {
                            try {
                                Long time = DateUtil.convertStringToDate(hireDateValue).getTime();
                                person.accumulate("hiredDate", time);
                            } catch (Exception e) {
                                logger.error("转换入职时间出错，不同步入职时间字段：" + element.getFdName());

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
        String o2d_orgEmailWay = DingConfig.newInstance().getOrg2dingOrgEmailSynWay();
        if (StringUtil.isNotNull(o2d_orgEmailWay)) {
            logger.debug("新方式同步设置企业邮箱！o2d_orgEmailWay：" + o2d_orgEmailWay);
            if ("syn".equalsIgnoreCase(o2d_orgEmailWay)
                    || ("addSyn".equalsIgnoreCase(o2d_orgEmailWay) && "add".equalsIgnoreCase(addOrUpdate))) {
                String o2d_orgEmail = DingConfig.newInstance().getOrg2dingOrgEmail();
                logger.debug("o2d_orgEmail:" + o2d_orgEmail);
                if (StringUtil.isNotNull(o2d_orgEmail)) {
                    String orgEmailValue = getPersonProperty(o2d_orgEmail, element);
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
        String o2d_isHideWay = DingConfig.newInstance().getOrg2dingIsHideSynWay();
        if (StringUtil.isNotNull(o2d_isHideWay)) {
            logger.debug("新方式同步设置号码隐藏！o2d_isHideWay：" + o2d_isHideWay);
            if ("syn".equalsIgnoreCase(o2d_isHideWay)
                    || ("addSyn".equalsIgnoreCase(o2d_isHideWay) && "add".equalsIgnoreCase(addOrUpdate))) {

                String o2d_isHide = DingConfig.newInstance().getOrg2dingIsHide();
                logger.debug("o2d_isHide:" + o2d_isHide);
                String o2d_isHideAll = DingConfig.newInstance().getOrg2dingIsHideAll();
                if (StringUtil.isNotNull(o2d_isHideAll) && "true".equals(o2d_isHideAll)) {
                    logger.debug("隐藏全部手机号码");
                    person.accumulate("isHide", true);
                } else {
                    try {
                        // 从字段同步
                        if (StringUtil.isNotNull(o2d_isHide)) {
                            if ("isContactPrivate".equals(o2d_isHide)) {
                                // 员工黄页的隐私设置
                                logger.debug("员工黄页的隐私设置：id-> " + element.getFdId());

                                Object object = sysZonePersonInfoService.findByPrimaryKey(element.getFdId(), null,
                                        true);
                                Object o = null;
                                if (object != null) {
                                    Class clazz = object.getClass();
                                    Method m = clazz.getMethod("getIsContactPrivate");
                                    o = m.invoke(object);
                                }
                                logger.debug("员工黄页获取的的值：" + o);
                                if (o != null) {
                                    String hideObject = o.toString().trim();
                                    logger.debug("hideObject:" + hideObject);
                                    if ("true".equals(hideObject) || "1".equals(hideObject) || "是".equals(hideObject)) {
                                        person.accumulate("isHide", true);
                                    } else {
                                        person.accumulate("isHide", false);
                                    }
                                } else {
                                    logger.debug("个人员工黄页隐私不设置的情况下，则取全局员工黄页的隐私配置");

                                    Map orgMap = sysAppConfigService
                                            .findByKey("com.landray.kmss.sys.zone.model.SysZonePrivateConfig");
                                    if (orgMap != null && orgMap.containsKey("isContactPrivate")) {
                                        Object isContactPrivateObject = orgMap.get("isContactPrivate");
                                        logger.debug("isContactPrivateObject:" + isContactPrivateObject);
                                        if (isContactPrivateObject != null
                                                && "1".equals(isContactPrivateObject.toString())) {
                                            person.accumulate("isHide", true);
                                        } else {
                                            person.accumulate("isHide", false);
                                        }
                                    } else {
                                        person.accumulate("isHide", false);
                                    }
                                }

                            } else {
                                // 从字段同步
                                String o2d_isHideValue = getPersonProperty(o2d_isHide, element);
                                logger.debug("从字段同步o2d_isHideValue：" + o2d_isHideValue);

                                if (StringUtil.isNotNull(o2d_isHideValue)) {

                                    if ("true".equals(o2d_isHideValue) || "1".equals(o2d_isHideValue)
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
        String o2d_isSeniorWay = DingConfig.newInstance().getOrg2dingIsSeniorSynWay();
        if (StringUtil.isNotNull(o2d_isSeniorWay)) {
            logger.debug("新方式同步是否高管！o2d_isSeniorWay：" + o2d_isSeniorWay);
            if ("syn".equalsIgnoreCase(o2d_isSeniorWay)
                    || ("addSyn".equalsIgnoreCase(o2d_isSeniorWay) && "add".equalsIgnoreCase(addOrUpdate))) {

                String o2d_isSenior = DingConfig.newInstance().getOrg2dingIsSenior();
                logger.debug("o2d_isSenior:" + o2d_isSenior);
                if (StringUtil.isNotNull(o2d_isSenior)) {
                    String seniorValue = getPersonProperty(o2d_isSenior, element);
                    logger.debug("seniorValue:" + seniorValue);
                    if (StringUtil.isNotNull(seniorValue)) {
                        if ("true".equalsIgnoreCase(seniorValue) || "1".equals(seniorValue)
                                || "是".equals(seniorValue)) {
                            person.accumulate("isSenior", true);
                        } else if ("false".equalsIgnoreCase(seniorValue) || "0".equals(seniorValue)
                                || "否".equals(seniorValue)) {
                            person.accumulate("isSenior", false);
                        } else {
                            logger.warn("同步人员是否高管字段是获取的字段不是true/false 1/0  是/否!" + element.getFdName());
                            // person.accumulate("isSenior", false);
                        }

                    } else {
                        logger.warn(element.getFdName() + "是否高管为空！");
                        person.accumulate("isSenior", false);
                    }
                }

            }
        }

        // 办公地点
        String workPlaceSynWay = DingConfig.newInstance().getOrg2dingWorkPlaceSynWay();
        if (StringUtil.isNotNull(workPlaceSynWay)) {
            logger.debug("办公地点设置！workPlaceSynWay：" + workPlaceSynWay);
            if ("syn".equalsIgnoreCase(workPlaceSynWay)
                    || ("addSyn".equalsIgnoreCase(workPlaceSynWay) && "add".equalsIgnoreCase(addOrUpdate))) {

                String workPlace = DingConfig.newInstance().getOrg2dingWorkPlace();
                logger.debug("workPlace:" + workPlace);
                if (StringUtil.isNotNull(workPlace)) {
                    String workPlaceValue = getPersonProperty(workPlace, element);
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
                String _key = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
                logger.debug("_key:" + _key);
                Class clazz = element.getClass();
                Method method = clazz.getMethod(_key.trim());
                Object obj = method.invoke(element);
                if ("fdStaffingLevel".equals(key)) { // 对职务特殊处理 fdStaffingLevel
                    logger.debug("人员的职务的设置");
                    if (obj != null) {
                        clazz = obj.getClass();
                        method = clazz.getMethod("getFdName");
                        obj = method.invoke(obj);
                    }
                }
                logger.debug("obj:" + obj);
                return obj == null ? null : obj.toString();
            }
        } catch (Exception e) {
            logger.error("根据字段获取人员的值过程中发生了异常");
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private JSONArray getUserDepartment(SysOrgPerson element, String addOrUpdate) throws Exception {
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

        // 是否开启一人多部门同步
        String dingPostEnabled = DingConfig.newInstance().getDingPostMulDeptEnabled();
        if (StringUtil.isNull(dingPostEnabled)) {
            String o2d_DeptWay = DingConfig.newInstance().getOrg2dingDepartmentSynWay();
            logger.debug("o2d_DeptWay:" + o2d_DeptWay);
            if (StringUtil.isNotNull(o2d_DeptWay)) {
                if (("syn".equals(o2d_DeptWay) || ("addSyn".equals(o2d_DeptWay) && "add".equals(addOrUpdate)))
                        && "fdMuilDept".equals(DingConfig.newInstance().getOrg2dingDepartment())) {
                    logger.debug("一人多部门功能开启");
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
                        if (rtn.indexOf("60003") == -1) {
                            addOmsErrors(dept, logInfo, "del");
                        }
                    }
                }
                log(logInfo);
            }
        }
    }

    private void handleUpdatePost() throws Exception {
        ThirdDingOmsPost opost = null;
        JSONArray personIds = null;
        JSONObject postEle = null;
        SysOrgPerson temp = null;
        SysOrgElement ele = null;
        StringBuffer ids = new StringBuffer();

        DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
        Connection conn = null;
        PreparedStatement psupdate_oms_post = null;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            psupdate_oms_post = conn
                    .prepareStatement("update third_ding_oms_post set fd_name = ?,doc_content = ? where fd_id = ?");
            int loop = 0;
            for (SysOrgElement post : syncPosts) {
                opost = omsPostMap.get(post.getFdId());
                if (opost != null && StringUtil.isNotNull(opost.getDocContent())) {
                    postEle = JSONObject.fromObject(opost.getDocContent());
                    if (postEle.has("persons")) {
                        personIds = postEle.getJSONArray("persons");
                        for (int i = 0; i < personIds.size(); i++) {
                            if (StringUtil.isNull(personIds.getString(i))) {
                                continue;
                            }
                            temp = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(personIds.getString(i), null,
                                    true);
                            if (temp != null) {
                                syncPersons.add(temp);
                            }
                        }
                    }
                    if (postEle.has("parentid") && StringUtil.isNotNull(postEle.getString("parentid"))) {
                        ele = (SysOrgElement) sysOrgElementService.findByPrimaryKey(postEle.getString("parentid"), null,
                                true);
                        if (ele != null) {
                            syncDepts.add(ele);
                        }
                    }
                    if (postEle.has("leaderparentid") && StringUtil.isNotNull(postEle.getString("leaderparentid"))) {
                        String[] lpids = postEle.getString("leaderparentid").split("[,;]");
                        for (String lpid : lpids) {
                            if (StringUtil.isNotNull(lpid)) {
                                ele = (SysOrgElement) sysOrgElementService.findByPrimaryKey(lpid, null, true);
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
                        List<SysOrgElement> ldps = deptLeaderMap.get(post.getFdId());
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

}
