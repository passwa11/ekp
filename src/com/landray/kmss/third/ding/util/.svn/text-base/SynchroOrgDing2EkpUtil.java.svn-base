package com.landray.kmss.third.ding.util;

import com.dingtalk.api.response.OapiSmartworkHrmEmployeeV2ListResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserListResponse;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.oms.DingApiV2Service;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class SynchroOrgDing2EkpUtil {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SynchroOrgDing2EkpUtil.class);

    // =================================================F4新增功能部分========================================================
    // 部门主管实例：信息部_主管=com.landray.kmss.third.ding.oms.SynchroOrgDing2EkpImp_post_1_钉钉部门Id
    // 多部门实例：信息部_成员=com.landray.kmss.third.ding.oms.SynchroOrgDing2EkpImp_post_8_钉钉部门Id
    // 部门：com.landray.kmss.third.ding.oms.SynchroOrgDing2EkpImp_dept_钉钉部门Id
    // 人员：com.landray.kmss.third.ding.oms.SynchroOrgDing2EkpImp_person_钉钉人员Id
    public static final String importInfoPre = "com.landray.kmss.third.ding.oms.SynchroOrgDing2EkpImp";

    private static ISysOrgPersonService sysOrgPersonService = null;

    public static ISysOrgPersonService getSysOrgPersonService(){
        if(sysOrgPersonService == null){
            sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
        }
        return sysOrgPersonService;
    }

    /**
     * 获取用户当前主部门信息
     * @param dingUserIds
     * @return
     */
    public static Map<String, String> getDingMainDeptIds(List<String> dingUserIds) throws Exception {
        Map<String, String> result = new HashMap<String, String>();
        if(dingUserIds == null || dingUserIds.isEmpty()){
            return result;
        }
        List<List<String>> dingUserIdLists = new ArrayList<List<String>>();
        int subSize = 100, fromIndex = 0, toIndex = subSize;
        while (fromIndex < dingUserIds.size()) {
            toIndex = fromIndex + subSize;
            if(toIndex > dingUserIds.size()){
                toIndex = dingUserIds.size();
            }
            dingUserIdLists.add(dingUserIds.subList(fromIndex, toIndex));
            fromIndex += subSize;
        }
        DingApiV2Service dingApiV2Service = DingUtils.getDingApiV2Service();
        dingUserIdLists.stream().forEach(subList->{
            String useridList = StringUtils.join(subList.toArray(), ",");
            try {
                OapiSmartworkHrmEmployeeV2ListResponse response = dingApiV2Service.v2_employeeList(useridList, null);
                if(response.getErrcode() == 0) {
                    response.getResult()
                            .stream()
                            .filter(vo -> (vo.getFieldDataList() != null
                                    && !vo.getFieldDataList().isEmpty()))
                            .forEach(vo -> {
                                List<OapiSmartworkHrmEmployeeV2ListResponse.EmpFieldDataVo> fvo = vo.getFieldDataList().stream().filter(fdVo -> {
                                    if ("sys00-mainDeptId".equals(fdVo.getFieldCode())
                                            && fdVo.getFieldValueList() != null
                                            && !fdVo.getFieldValueList().isEmpty()) {
                                        return true;
                                    }
                                    return false;
                                }).collect(Collectors.toList());
                                if (fvo != null && !fvo.isEmpty()) {
                                    String dingDeptId = fvo.get(0).getFieldValueList().get(0).getValue();
                                    // 异常数据处理（钉钉）
                                    if ("-1".equals(dingDeptId)) {
                                        dingDeptId = "1";
                                    }
                                    result.put(vo.getUserid(), dingDeptId);
                                }
                    });
                }
                else{
                    logger.error("获取员工["+useridList+"]花名册字段信息出错", response.getErrmsg());
                }
            } catch (Exception e) {
                logger.error("获取员工["+useridList+"]花名册字段信息出错", e);
            }
        });
        return result;
    }

    /**
     * 转化为json数组，兼容老接口返回的数据字段
     * @param dingUserList
     * @return
     */
    public static JSONArray convert2JSonArray(List<OapiV2UserListResponse.ListUserResponse> dingUserList){
        JSONArray users = new JSONArray();
        dingUserList.stream().forEach(user->{
            JSONObject userJson = JSONObject.fromObject(user);

            userJson.put("jobnumber", user.getJobNumber());
            userJson.put("extattr", user.getExtension());
            userJson.put("department", user.getDeptIdList());
            userJson.put("isLeader", user.getLeader());
            userJson.put("tel", user.getTelephone());
            users.add(userJson);
        });
        return users;
    }

    /**
     * 通过查询用户详情api获取员工在对应的部门中是否领导数据追加到当前部门用户
     * @param deptUserElement
     */
    public static void appendIsLeaderInDeptsData(JSONObject deptUserElement) throws Exception {
        DingApiV2Service dingApiV2Service = DingUtils.getDingApiV2Service();
        String userId = deptUserElement.getString("userid");
        String userName = deptUserElement.getString("name");
        OapiV2UserGetResponse response = dingApiV2Service.v2_findUser(userId);
        if(response.getErrcode() == 0){
            JSONObject isLeaderInDepts = new JSONObject();
            response.getResult().getLeaderInDept().stream().forEach(deptLeader -> {
                isLeaderInDepts.put(deptLeader.getDeptId(), deptLeader.getLeader());
            });
            deptUserElement.put("isLeaderInDepts", isLeaderInDepts.toString());
            if(logger.isDebugEnabled()){
                String debugMsg = "获取【"+userId+":"+userName+"】用户详情：" + deptUserElement.getString("isLeaderInDepts");
                logger.debug(debugMsg);
            }
        }
        else{
            String errorMsg = "获取【"+userId+":"+userName+"】用户详情失败："+response.getMessage();
            logger.error(errorMsg);
        }
    }

    /**
     * 分析钉钉数据，存入map中
     * @param depts
     * @param deptMaps
     * @param hiers
     */
    public static void analysisDingDeptData(JSONArray depts,
                                            Map<String, JSONObject> deptMaps,
                                            List<String> hiers,
                                            List<String> allDingIds,
                                            boolean associatedExternalEnable,
                                            String dingOrgId2ekp) {
        String id = null;
        JSONObject jdept = null;
        StringBuffer parents = new StringBuffer();
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> hierMaps = new HashMap<String, String>(depts.size());
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
        hiers.addAll(hierMaps.values());
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
    }

    private static void getParent(String id, StringBuffer ids,
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
     * 递归加载钉钉部门下的所有员工信息(v2接口)
     * @param deptId
     *  钉钉部门id
     * @param cursor
     *  分页游标
     * @param dingUserList
     *  存储用户信息list
     * @throws Exception
     */
    public static void getDingUsersByDeptId(SysQuartzJobContext jobContext,
                                            Long deptId, Long cursor,
                                            List<OapiV2UserListResponse.ListUserResponse> dingUserList) throws Exception {
        DingApiV2Service dingApiV2Service = DingUtils.getDingApiV2Service();
        if(cursor == null){
            cursor = 0L;
        }
        OapiV2UserListResponse response = dingApiV2Service.v2_userList(deptId, cursor);
        if("0".equals(response.getErrorCode())){
            OapiV2UserListResponse.PageResult result= response.getResult();
            dingUserList.addAll(result.getList());
            if(result.getHasMore()){
                getDingUsersByDeptId(jobContext, deptId, result.getNextCursor(), dingUserList);
            }
        }
        else{
            log(jobContext,"\t\t同步当前部门下的员工信息失败,因为调用钉钉接口异常!");
            if(StringUtil.isNotNull(response.getMessage())){
                log(jobContext,"\t\t"+response.getMessage());
            }
        }
    }

    /**
     * 获取部门钉钉数据
     * @return
     * @throws Exception
     */
    public static JSONObject getDingDeptData(SysQuartzJobContext jobContext, String dingOrgId2ekp) throws Exception {
        JSONObject ret = null;
        DingApiService dingApiService = DingUtils.getDingApiService();
        if (StringUtil.isNotNull(dingOrgId2ekp)) {
            String[] dingOrgIds = dingOrgId2ekp.split(";");
            for (int i = 0; i < dingOrgIds.length; i++) {
                JSONObject ret1 = dingApiService
                        .departsGet(dingOrgIds[i]);
                logger.debug("获取钉钉部门：" + dingOrgIds[i] + "  return -> " + ret1);
                if (ret1.getInt("errcode") != 0) {
                    log(jobContext,"获取部门信息失败，钉钉部门id：" + dingOrgIds[i]);
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
        return ret;
    }

    /**
     * 获取初始化密码
     * @return
     */
    public static String getInitPassword() {
        // 查询初始密码
        String initPassword = null;
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
            else{
                if(logger.isDebugEnabled()){
                    logger.debug("人员初始密码为null!");
                }
            }
        } catch (Exception e) {
            logger.error("设置初始密码发生异常！");
            logger.error("", e);
        }
        return initPassword;
    }

    /**
     * 统计已映射的岗位数据，存入map中
     * @throws Exception
     */
    public static void initPostMap(SysQuartzJobContext jobContext, Map<String, String> postMap) throws Exception {
        log(jobContext,"开始加载已映射的岗位数据存入map变量......");
        ISysOrgPostService sysOrgPostService = (ISysOrgPostService) SpringBeanUtil
                .getBean("sysOrgPostService");
        HQLInfo hqlInfo;
        hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock("fdImportInfo,fdId");
        hqlInfo.setWhereBlock("fdImportInfo like :info");
        hqlInfo.setParameter("info", SynchroOrgDing2EkpUtil.importInfoPre + "_post_%");
        hqlInfo.setOrderBy("fdIsAvailable");
        List<Object[]> postList = sysOrgPostService.findList(hqlInfo);
        for (Object[] params : postList) {
            postMap.put((String)params[0], (String)params[1]);
        }
        log(jobContext,"完成加载已映射的岗位数据存入map变量，系统中已映射的岗位(主管/成员)数:" + postMap.size());
    }

    /**
     * 统计已映射的用户数据，存入Map中
     * @throws Exception
     */
    public static void initPersonMap(SysQuartzJobContext jobContext, Map<String, String> personMap) throws Exception {
        log(jobContext,"开始加载已映射的用户数据存入map变量......");
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock("sysOrgPerson.fdImportInfo, sysOrgPerson.fdId");
        hqlInfo.setWhereBlock("sysOrgPerson.fdOrgType=8 and sysOrgPerson.fdImportInfo like :info");
        hqlInfo.setParameter("info", importInfoPre + "_person_%");
        List<Object[]> personList = getSysOrgPersonService().findList(hqlInfo);
        String imkey = null;
        for (Object[] ele : personList) {
            imkey = ele[0].toString().replace(importInfoPre + "_person_", "");
            if (StringUtil.isNotNull(imkey)) {
                personMap.put(imkey, ele[1].toString());
            }
        }
        log(jobContext,"完成加载已映射的用户数据存入map变量，系统中已映射的人员数:" + personMap.size());
    }

    /**
     * 统计有手机号码的用户数据，存入map中
     * @throws Exception
     */
    public static void initMobileMap(SysQuartzJobContext jobContext, Map<String, String> mobileMap) throws Exception {
        log(jobContext,"开始加载已填写手机号数据存入map变量......");
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock("sysOrgPerson.fdMobileNo, sysOrgPerson.fdId");
        hqlInfo.setWhereBlock("sysOrgPerson.fdIsAvailable = :fdIsAvailable and sysOrgPerson.fdMobileNo is not null and sysOrgPerson.fdMobileNo != ''");
        hqlInfo.setParameter("fdIsAvailable", true);
        List<Object[]> personList = getSysOrgPersonService().findList(hqlInfo);
        for (Object[] ele : personList) {
            String mobileNo = ele[0].toString();
            String fdId = ele[1].toString();
            if(mobileMap.containsKey(mobileNo)){
                log(jobContext, "发现拥有相同手机号["+mobileNo+"]的多个EKP账户["+fdId + "|" + mobileMap.get(mobileNo) +"]");
            }
            else{
                mobileMap.put(mobileNo, fdId);
            }
        }
        log(jobContext,"完成加载已填写手机号数据存入map变量，系统中已填写手机号的用户数:" + mobileMap.size());
    }

    /**
     * 统计有登录名数据，存入map中
     * @throws Exception
     */
    public static void initLoginNameMap(SysQuartzJobContext jobContext, Map<String, String> loginNameMap) throws Exception {
        log(jobContext,"开始加载有登录名数据存入map变量......");
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock("sysOrgPerson.fdLoginName, sysOrgPerson.fdId");
        hqlInfo.setWhereBlock("sysOrgPerson.fdIsAvailable = :fdIsAvailable and sysOrgPerson.fdLoginName is not null and sysOrgPerson.fdLoginName != ''");
        hqlInfo.setParameter("fdIsAvailable", true);
        List<Object[]> personList = getSysOrgPersonService().findList(hqlInfo);
        for (Object[] ele : personList) {
            loginNameMap.put(ele[0].toString(), ele[1].toString());
        }
        log(jobContext,"完成加载有登录名数据存入map变量，系统中已填写登录名的用户数:" + loginNameMap.size());
    }


    /**
     * 加载关联关系数据，存入map中
     * @throws Exception
     */
    public static void initRelationsMap(SysQuartzJobContext jobContext, Map<String, String> relationMap) throws Exception {
        log(jobContext, "开始加载关联关系数据存入map变量......");
        IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil.getBean("omsRelationService");
        String sql = "SELECT fd_app_pk_id, fd_type, fd_ekp_id " +
                " FROM oms_relation_model " +
                " WHERE fd_app_key = '" + SynchroOrgDing2EkpUtil.getAppKey() + "'" +
                " AND fd_app_pk_id is not null " +
                " AND fd_type is not null " +
                " AND fd_ekp_id is not null";
        List<Object[]> result = omsRelationService.getBaseDao().getHibernateSession().createNativeQuery(sql).list();
        if(result != null && !result.isEmpty()){
            result.stream().forEach(o->{
                Object[] r = (Object[]) o;
                relationMap.put(r[0] + "|" + r[1], r[2].toString());
            });
        }
        log(jobContext, "完成加载关联关系数据存入map变量【"+(result != null ? result.size() : 0) + "】");
    }


    /**
     * 删除oms_relation_model中无效数据
     * @throws Exception
     */
    public static void deleteInvalidRelationRecords(SysQuartzJobContext jobContext) throws Exception {
        Throwable t = null;
        TransactionStatus status = null;
        try{
            status = TransactionUtils.beginNewTransaction(1200);
            IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil.getBean("omsRelationService");
            String sql = "SELECT FD_ID FROM oms_relation_model  " +
                    " WHERE fd_app_key = '" + SynchroOrgDing2EkpUtil.getAppKey() + "'" +
                    "   AND fd_type is null" +
                    "   AND fd_ekp_id is not null " +
                    "   AND (SELECT COUNT(1) FROM sys_org_element ele where ele.fd_id = fd_ekp_id) = 0";
            List list = omsRelationService.getBaseDao().getHibernateSession().createNativeQuery(sql).list();
            if(list!=null && list.size()>0){
                sql = "DELETE FROM oms_relation_model  " +
                        " WHERE fd_app_key = '" + SynchroOrgDing2EkpUtil.getAppKey() + "'" +
                        "   AND fd_type is null" +
                        "   AND fd_ekp_id is not null " +
                        "   AND (SELECT COUNT(1) FROM sys_org_element ele where ele.fd_id = fd_ekp_id) = 0";
                int updatedRowsize = omsRelationService.getBaseDao().getHibernateSession().createNativeQuery(sql).executeUpdate();
                log(jobContext, "删除无效映射数据总量" + updatedRowsize);
            }
            TransactionUtils.commit(status);

        } catch (Exception e) {
            t = e;
            logger.error("删除无效映射数据失败:", e);
        }
        finally {
            if (t != null && status != null) {
                log(jobContext,"删除无效映射数据失败");
                if(status.isRollbackOnly()){
                    TransactionUtils.rollback(status);
                }
                log(jobContext,"已回滚删除无效映射数据处理事务!");
            }
        }
    }

    /**
     * 清洗重复的数据
     * @param jobContext
     * @throws Exception
     */
    public static void updateHandlerRepeatData(SysQuartzJobContext jobContext) throws Exception {
        log(jobContext,"开始处理重复数据......");
        Throwable t = null;
        TransactionStatus status = null;
        ISysOrgElementService sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
        try {
            status = TransactionUtils.beginNewTransaction(30);
            String sql = "select fd_import_info from sys_org_element where fd_import_info is not null GROUP BY fd_import_info having count(fd_id)>1";
            List<Object[]> repeats = sysOrgElementService.getBaseDao().getHibernateSession().createNativeQuery(sql).list();
            if (repeats != null && repeats.size() > 0) {
                List<String> repeatStrs = repeats.stream()
                                                    .filter(obj->obj != null && obj.length > 0)
                                                    .map(obj-> obj.toString())
                                                    .collect(Collectors.toList());
                //查询组织架构范围
                sql = "SELECT fd_id FROM sys_org_element " +
                        " WHERE " + HQLUtil.buildLogicIN("fd_import_info", repeatStrs) +
                        "       AND fd_is_available is not null AND fd_is_available = :status";
                repeatStrs = sysOrgElementService.getBaseDao()
                        .getHibernateSession().createNativeQuery(sql)
                        .setParameter("status", Boolean.FALSE).list();
                if(!repeatStrs.isEmpty()){
                    log(jobContext,"需要处理重复数据【"+repeatStrs.size()+"】条");
                    //更新组织架构
                    sql = "UPDATE sys_org_element SET fd_import_info = null WHERE " + HQLUtil.buildLogicIN("fd_id", repeatStrs);
                    int updatedRows = sysOrgElementService.getBaseDao().getHibernateSession().createNativeQuery(sql).executeUpdate();
                    logger.debug("清空fd_import_info字段为空的数据总量：", updatedRows);
                    //删除关联关系
                    sql = "DELETE FROM oms_relation_model WHERE " + HQLUtil.buildLogicIN("fd_ekp_id", repeatStrs);
                    updatedRows = sysOrgElementService.getBaseDao().getHibernateSession().createNativeQuery(sql).executeUpdate();
                    logger.debug("删除关联关系总量为：", updatedRows);
                }
            }
            TransactionUtils.commit(status);
        } catch (Exception e) {
            t = e;
            logger.error("处理重复数据失败:", e);
        }
        finally {
            if (t != null && status != null) {
                log(jobContext,"处理重复数据失败");
                if(status.isRollbackOnly()){
                    TransactionUtils.rollback(status);
                }
                log(jobContext,"已回滚重复数据处理事务!");
            }
            else{
                log(jobContext,"成功处理重复数据");
            }
        }
    }

    /**
     * 初始化映射表关系的类型
     */
    public static void updateRelationType(SysQuartzJobContext jobContext) {
        log(jobContext,"开始初始化映射表关系的类型......");
        Throwable t = null;
        TransactionStatus status = null;
        IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil.getBean("omsRelationService");
        try {
            status = TransactionUtils.beginNewTransaction(30);
            String sql = "SELECT relation.fd_id, ele.fd_org_type FROM oms_relation_model relation " +
                    " INNER JOIN sys_org_element ele ON relation.fd_ekp_id = ele.fd_id " +
                    " WHERE relation.fd_app_key = '" + getAppKey() + "' " +
                    "       AND relation.fd_type is null " +
                    "       AND ele.fd_org_type in (8, 2, 1) ";
            List<Object[]> result = omsRelationService.getBaseDao().getHibernateSession().createNativeQuery(sql).list();
            if(result != null && !result.isEmpty()){
                log(jobContext,"需要处理映射类型数据总量：" + result.size());
                List<String> list8 = new ArrayList<String>();
                List<String> list21 = new ArrayList<String>();
                result.stream().forEach(o->{
                    Object[] r = (Object[]) o;
                    if(Integer.valueOf(r[1].toString()) == 8){
                        list8.add(r[0].toString());
                    }
                    else{
                        list21.add(r[0].toString());
                    }
                });
                sql = "UPDATE oms_relation_model SET fd_type = '8' WHERE " + HQLUtil.buildLogicIN("fd_id", list8);
                int updatedRows = omsRelationService.getBaseDao().getHibernateSession().createNativeQuery(sql).executeUpdate();
                logger.debug("更新映射类型为8的数据总量：" + updatedRows);
                sql = "UPDATE oms_relation_model SET fd_type = '2' WHERE " + HQLUtil.buildLogicIN("fd_id", list21);
                updatedRows = omsRelationService.getBaseDao().getHibernateSession().createNativeQuery(sql).executeUpdate();
                logger.debug("更新映射类型为2的数据总量：" + updatedRows);
            }
            else{
                log(jobContext,"需要处理映射类型数据总量: 0");
            }
            TransactionUtils.commit(status);
        } catch (Exception e) {
            t = e;
            logger.error("更新映射类型失败:", e);
        }
        finally {
            if(t != null && status != null){
                log(jobContext,"更新映射类型失败");
                if(status.isRollbackOnly()){
                    TransactionUtils.rollback(status);
                }
                log(jobContext,"已回滚更新映射类型处理事务!");
            }
            else{
                log(jobContext,"成功初始化映射表关系的类型");
            }
        }
    }

    public static String getAppKey() {
        return StringUtil.isNull(DingConstant.DING_OMS_APP_KEY) ? "default"
                : DingConstant.DING_OMS_APP_KEY;
    }

    private static void log(SysQuartzJobContext jobContext, String msg) {
        logger.debug("【钉钉接入组织架构到EKP】" + msg);
        if (jobContext != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
            String time = sdf.format(Calendar.getInstance().getTime());
            jobContext.logMessage(time + "  " + msg);
        }
    }

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @param isAvailable
     * @return
     * @throws Exception
     */
    public static String getPersonByMobile(String mobile, boolean isAvailable) throws Exception {
        if (StringUtil.isNull(mobile)) {
            logger.warn("手机号码为空，不进行手机号码匹配");
            return null;
        }
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock("sysOrgPerson.fdId");
        hqlInfo.setWhereBlock("sysOrgPerson.fdMobileNo = :mobile and sysOrgPerson.fdIsAvailable = :isAvailable");
        hqlInfo.setParameter("mobile", mobile);
        hqlInfo.setParameter("isAvailable", isAvailable);
        return (String) getSysOrgPersonService().findFirstOne(hqlInfo);
    }

    /**
     * 根据登录名查询用户
     *
     * @param loginName
     * @return
     * @throws Exception
     */
    public static String getPersonByLoginName(String loginName) throws Exception {
        if (StringUtil.isNull(loginName)) {
            return null;
        }
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock("sysOrgPerson.fdId");
        hqlInfo.setWhereBlock("sysOrgPerson.fdLoginName = :loginName and sysOrgPerson.fdIsAvailable = :isAvailable");
        hqlInfo.setParameter("loginName", loginName);
        hqlInfo.setParameter("isAvailable", true);
        return (String) getSysOrgPersonService().findFirstOne(hqlInfo);
    }

    public static OmsRelationModel getOmsRelationModel(String dingId)
            throws Exception {
        IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil.getBean("omsRelationService");
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fdAppKey=:fdAppKey and fdAppPkId=:fdAppPkId");
        hqlInfo.setParameter("fdAppKey", SynchroOrgDing2EkpUtil.getAppKey());
        hqlInfo.setParameter("fdAppPkId", dingId);
        return (OmsRelationModel) omsRelationService.findFirstOne(hqlInfo);
    }

    /**
     *
     * @param userId 钉钉用户id
     * @param ddDeptId 组织名称
     * @throws Exception
     */
    public static void pushAddPersonMessage(String userId, String ddDeptId) throws Exception {
        String agentid = DingConfig.newInstance().getDingAgentid();
        if (StringUtil.isNotNull(agentid)) {
            OmsRelationModel orm = SynchroOrgDing2EkpUtil.getOmsRelationModel(ddDeptId);
            String deptName = "";
            if (orm != null) {
                ISysOrgElementService sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
                SysOrgElement dept = (SysOrgElement) sysOrgElementService.findByPrimaryKey(orm.getFdEkpId());
                String hierarchyId = dept.getFdHierarchyId();
                String topId = hierarchyId.split("x")[1];
                SysOrgElement org = (SysOrgElement) sysOrgElementService.findByPrimaryKey(topId);
                deptName = org.getFdName();
            }
            DingApiService dingApiService = DingUtils.getDingApiService();
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

    /**
     * 根据手机号生成ID，防止高并发时增加重复人员
     *
     * @return
     */
    public static String getPersonId(String mobile) {
        if (StringUtil.isNull(mobile)) {
            return IDGenerator.generateID();
        } else {
            return MD5Util.getMD5String(mobile);
        }
    }

    // 获取人员数据
    public static String getSysOrgPerson(SysQuartzJobContext jobContext,
                                         String userid, String name, String mobileNo,
                                         Map<String, String> relationMap,
                                         Map<String, String> loginNameMap,
                                         Map<String, String> mobileMap,
                                         Map<String, String> personMap,boolean isCallBack) throws Exception {
        String sysOrgPersonId = null;
        String msg = "\t\t";
        if (StringUtil.isNull(sysOrgPersonId) && relationMap.containsKey(userid + "|8")) {
            sysOrgPersonId = relationMap.get(userid + "|8");
            msg += "根据中间表映射信息${userid}完成用户${name}匹配，直接更新该用户信息"
                    .replace("${userid}", userid).replace("${name}",
                            name);
        }
        if (StringUtil.isNull(sysOrgPersonId) && StringUtil.isNotNull(userid)
                && loginNameMap.containsKey(userid)) {
            sysOrgPersonId = loginNameMap.get(userid);
            msg += "根据钉钉用户名信息${no}完成用户${name}匹配，直接更新该用户信息"
                    .replace("${no}", name).replace("${name}", name);
        }
        if (StringUtil.isNull(sysOrgPersonId) && StringUtil.isNotNull(mobileNo)
                && mobileMap.containsKey(mobileNo)) {
            sysOrgPersonId = mobileMap.get(mobileNo);
            msg += "根据钉钉电话号码信息${mobileNo}完成用户${name}匹配，直接更新该用户信息"
                    .replace("${mobileNo}", mobileNo)
                    .replace("${name}", name);
        }
        if (StringUtil.isNull(sysOrgPersonId) && StringUtil.isNotNull(userid)
                && personMap.containsKey(SynchroOrgDing2EkpUtil.importInfoPre + "_person_" + userid)) {
            sysOrgPersonId = personMap.get(SynchroOrgDing2EkpUtil.importInfoPre + "_person_"+userid);
            msg += "根据组织架构人员信息${importInfo}完成用户${name}匹配，直接更新该用户信息"
                    .replace("${importInfo}",
                            SynchroOrgDing2EkpUtil.importInfoPre + "_person_" + userid)
                    .replace("${name}", name);
        }
        if (StringUtil.isNotNull(msg) && logger.isDebugEnabled()){
            logger.debug(msg);
        }
        //回调时从数据库读取用户映射信息
        if(StringUtil.isNull(sysOrgPersonId) && isCallBack){
            sysOrgPersonId = getPersonIdFromDB(relationMap,userid,mobileNo);
        }
        return sysOrgPersonId;
    }

    /**
     * 从数据库读取用户映射关系
     * @param userid
     * @param mobileNo
     * @return
     */
    private static String getPersonIdFromDB(Map<String, String> relationMap,String userid, String mobileNo) {
        // 获取映射表数据
        String sysOrgPersonId = null;
        try {
            //先从映射表获取
            OmsRelationModel model = getOmsRelationModel(userid);
            if (model != null) {
                //加到映射Map中，不然后面更新映射时会报错
                relationMap.put(userid+"|8",model.getFdEkpId());
                sysOrgPersonId = model.getFdEkpId();
            }
            //有效人员中匹配手机
            if(StringUtil.isNull(sysOrgPersonId)){
                sysOrgPersonId = getPersonByMobile(mobileNo, true);
            }
            //无效人员中匹配手机
            if(StringUtil.isNull(sysOrgPersonId)){
                sysOrgPersonId = getPersonByMobile(mobileNo, false);
            }
            //登录名匹配
            if(StringUtil.isNull(sysOrgPersonId)){
                sysOrgPersonId = getPersonByLoginName(userid);
            }
        } catch (Exception e) {
            logger.error("获取用户信息失败："+e.getMessage(),e);
        }
        return sysOrgPersonId;
    }

    public static String getAccontType(JSONObject element){
        String dingAccountType = null;
        if(element.containsKey("exclusiveAccount")||element.containsKey("exclusive_account")){
            if(element.containsKey("exclusiveAccountType")){
                dingAccountType = element.getString("exclusiveAccountType");
            }else if(element.containsKey("exclusive_account_type")){
                dingAccountType = element.getString("exclusive_account_type");
            }
        }
        if(StringUtil.isNull(dingAccountType)){
            dingAccountType ="common";
        }
        return dingAccountType;
    }
}
