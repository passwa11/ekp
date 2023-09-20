package com.landray.kmss.third.ekp.java.oms.in;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.util.*;
import org.hibernate.HibernateException;
import org.hibernate.query.NativeQuery;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgRole;
import com.landray.kmss.sys.organization.model.SysOrgRoleConf;
import com.landray.kmss.sys.organization.model.SysOrgRoleConfCate;
import com.landray.kmss.sys.organization.model.SysOrgRoleLine;
import com.landray.kmss.sys.organization.model.SysOrgRoleLineDefaultRole;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleConfCateService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleConfService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleLineDefaultRoleService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleLineService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleService;
import com.landray.kmss.third.ekp.java.EkpJavaConfig;
import com.landray.kmss.third.ekp.java.oms.in.client.SysSynchroGetOrgInfoContext;
import com.landray.kmss.third.ekp.java.oms.in.client.SysSynchroOrgResult;

public class EkpRoleSynchroServiceImp {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EkpRoleSynchroServiceImp.class);

    private static String synchroLine = null;

    private static String synchroRoleConfCate = null;

    private IEkpRoleSynchro ekpRoleSynchro = null;

    private List<String> LocalRoleLineIds;

    private String lastUpdateTime = null;

    public IEkpRoleSynchro getEkpRoleSynchro() {
        return ekpRoleSynchro;
    }

    public void setEkpRoleSynchro(IEkpRoleSynchro ekpRoleSynchro) {
        this.ekpRoleSynchro = ekpRoleSynchro;
    }

    private ISysOrgElementService sysOrgElementService = null;

    public ISysOrgElementService getSysOrgElementService() {
        return sysOrgElementService;
    }

    public void setSysOrgElementService(
            ISysOrgElementService sysOrgElementService) {
        this.sysOrgElementService = sysOrgElementService;
    }

    private ISysAppConfigService sysAppConfigService;

    public ISysAppConfigService getSysAppConfigService() {
        if (sysAppConfigService == null) {
            sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
        }
        return sysAppConfigService;
    }

    public void syncRoleLineDatas(String omsUpdateTime) throws Exception {
        EkpJavaConfig config = new EkpJavaConfig();
        synchroLine = config.getValue("kmss.oms.in.java.synchro.roleLine");
        synchroRoleConfCate = config.getValue("kmss.oms.in.java.synchro.roleConfCate");

        if (!"true".equals(synchroLine)) {
            logger.info("没启用角色线的同步功能");
            return;
        }
        logger.info("同步角色线信息开始：" + new Date());

        Date current = new Date();

        try {
            Map<String, String> map = getSysAppConfigService().findByKey(EkpOmsConfig.class.getName());
            lastUpdateTime = map.get("roleLastUpdateTime");
            logger.debug("roleLastUpdateTime:" + lastUpdateTime);
            SysSynchroGetOrgInfoContext infoContext = new SysSynchroGetOrgInfoContext();
            infoContext.setBeginTimeStamp(lastUpdateTime);
            if ("true".equals(synchroRoleConfCate)) {
                // 同步角色线分类
                syncRoleConfCateRecords();
            }
            syncRoleConfRecords(infoContext);
            boolean deleteRoleLineSuccess = deleteRoleLineRecords(infoContext);
            syncRoleLineRecords(infoContext, deleteRoleLineSuccess);
            syncRoleLineDefaultRoleRecords(infoContext);
            syncRoleRecords(infoContext);

            TransactionStatus updateDtatus = null;
            try {
                updateDtatus = TransactionUtils
                        .beginNewTransaction();
                EkpOmsConfig ekpOmsConfig = new EkpOmsConfig();
                ekpOmsConfig.setRoleLastUpdateTime(DateUtil.convertDateToString(
                        current,
                        ResourceUtil.getString("date.format.time.msel")));
                ekpOmsConfig.setLastUpdateTime(omsUpdateTime);
                ekpOmsConfig.save();
                TransactionUtils.getTransactionManager().commit(updateDtatus);
            } catch (Exception e) {
                if (updateDtatus != null) {
                    TransactionUtils.getTransactionManager()
                            .rollback(updateDtatus);
                }
                throw e;
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("同步角色线信息出错", e);
        }
        logger.info("同步角色线信息结束：" + new Date());

    }

    private SysOrgRoleConf genRoleConf(JSONObject jsonObject,
                                       SysOrgRoleConf conf)
            throws Exception {
        if (conf == null) {
            conf = new SysOrgRoleConf();
            conf.setFdId((String) jsonObject.get("id"));
        }

        conf.setFdName((String) jsonObject.get("name"));
        if (jsonObject.containsKey("langProps")) {
            JSONObject o = (JSONObject) jsonObject.get("langProps");
            if (o != null) {
                Map map = new HashMap();
                for (String key : (Set<String>) o.keySet()) {
                    map.put(key, o.get(key));
                }
                conf.setDynamicMap(map);
            }
        }
        Object jsonObj = jsonObject.get("order");
        if (jsonObj != null) {
            conf.setFdOrder(Long.parseLong((String) jsonObj));
        }
        conf.setFdIsAvailable((Boolean) jsonObject.get("isAvailable"));
        JSONArray editors = (JSONArray) jsonObject.get("roleLineEditors");
        if (editors != null && editors.size() > 0) {
            List sysRoleLineEditors = new ArrayList();
            for (int i = 0; i < editors.size(); i++) {
                String editorId = (String) editors.get(i);
                SysOrgElement element = (SysOrgElement) sysOrgElementService
                        .findByPrimaryKey(editorId, SysOrgElement.class, true);
                sysRoleLineEditors.add(element);
            }
            conf.setSysRoleLineEditors(sysRoleLineEditors);
        }
        if ("true".equals(synchroRoleConfCate)) {
            if (jsonObject.containsKey("roleConfCateId")) {
                String roleConfCateId = (String) jsonObject
                        .get("roleConfCateId");
                if (StringUtil.isNotNull(roleConfCateId)) {
                    SysOrgRoleConfCate roleConfCate = (SysOrgRoleConfCate) sysOrgRoleConfCateService
                            .findByPrimaryKey(roleConfCateId,
                                    SysOrgRoleConfCate.class, true);
                    if (roleConfCate != null) {
                        conf.setFdRoleConfCate(roleConfCate);
                    } else {
                        conf.setFdRoleConfCate(null);
                    }
                }
            } else {
                conf.setFdRoleConfCate(null);
            }
        }

        return conf;
    }

    private List<JSONObject> getRoleConfs(String json) throws Exception {
        List<JSONObject> sysOrgRoleConfs = new ArrayList<JSONObject>();
        JSONArray array = (JSONArray) JSONValue.parse(json);
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = (JSONObject) array.get(i);
            sysOrgRoleConfs.add(jsonObject);
        }
        return sysOrgRoleConfs;
    }

    private void syncRoleConfRecords(SysSynchroGetOrgInfoContext infoContext)
            throws Exception {
        SysSynchroOrgResult orgResult = ekpRoleSynchro
                .getRoleConfInfo(infoContext);
        if (orgResult.getReturnState() == 2) {
            String resultStr = orgResult.getMessage();
            if (StringUtil.isNotNull(resultStr)) {
                List<JSONObject> sysOrgRoleConfs = getRoleConfs(resultStr);
                TransactionStatus status = TransactionUtils
                        .beginNewTransaction();
                try {
                    for (JSONObject confObject : sysOrgRoleConfs) {
                        updateRoleConf(confObject);
                    }
                    TransactionUtils.getTransactionManager().commit(status);
                } catch (Exception e) {
                    TransactionUtils.getTransactionManager().rollback(status);
                    throw e;
                }
            }
        } else {
            logger.error("获取角色线配置信息出错,返回状态值为:" + orgResult.getCount()
                    + ",错误信息为:" + orgResult.getMessage());
        }

    }

    private void updateRoleConf(JSONObject confObject) throws Exception {
        SysOrgRoleConf origin = (SysOrgRoleConf) sysOrgRoleConfService
                .findByPrimaryKey((String) confObject.get("id"),
                        SysOrgRoleConf.class,
                        true);
        if (origin != null) {
            sysOrgRoleConfService.update(genRoleConf(confObject, origin));
        } else {
            sysOrgRoleConfService.add(genRoleConf(confObject, null));
        }
    }

    private SysOrgRoleLine buildRoleLineBase(JSONObject jsonObject) {
        SysOrgRoleLine line = new SysOrgRoleLine();
        line.setFdId((String) jsonObject.get("id"));
        line.setFdHierarchyId((String) jsonObject.get("hierarchyId"));
        return line;
    }

    private SysOrgRoleLine setRoleLineDetail(SysOrgRoleLine line,
                                             JSONObject jsonObject)
            throws Exception {
        Object jsonObj = jsonObject.get("order");
        if (jsonObj != null) {
            line.setFdOrder(Long.parseLong((String) jsonObj));
        }
        jsonObj = jsonObject.get("name");
        if (jsonObj != null) {
            line.setFdName((String) jsonObj);
        } else {
            line.setFdName(null);
        }
        if (jsonObject.containsKey("langProps")) {
            JSONObject o = (JSONObject) jsonObject.get("langProps");
            if (o != null) {
                Map map = new HashMap();
                for (String key : (Set<String>) o.keySet()) {
                    map.put(key, o.get(key));
                }
                line.setDynamicMap(map);
            }
        }
        line.setFdHierarchyId((String) jsonObject.get("hierarchyId"));
        line.setFdCreateTime(DateUtil.convertStringToDate((String) jsonObject
                .get("createTime"), "yyyy-MM-dd HH:mm:ss"));
        jsonObj = jsonObject.get("parent");
        if (jsonObj != null) {
            line.setFdParent((SysOrgRoleLine) (sysOrgRoleLineService
                    .findByPrimaryKey((String) jsonObj, SysOrgRoleLine.class,
                            true)));
        } else {
            line.setFdParent(null);
        }
        jsonObj = jsonObject.get("member");
        if (jsonObj != null) {
            line.setSysOrgRoleMember((SysOrgElement) (sysOrgElementService
                    .findByPrimaryKey((String) jsonObj, SysOrgElement.class,
                            true)));
        }
        jsonObj = jsonObject.get("roleConf");
        if (jsonObj != null) {
            line.setSysOrgRoleConf((SysOrgRoleConf) (sysOrgRoleConfService
                    .findByPrimaryKey((String) jsonObj, SysOrgRoleConf.class,
                            true)));
        }

        return line;
    }

    private List<JSONObject> getRoleLines(String json)
            throws Exception {
        List<JSONObject> sysOrgRoleLines = new ArrayList<JSONObject>();
        JSONArray array = (JSONArray) JSONValue.parse(json);
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = (JSONObject) array.get(i);
            sysOrgRoleLines.add(jsonObject);
        }
        return sysOrgRoleLines;
    }

    private void syncRoleLineRecords(SysSynchroGetOrgInfoContext infoContext,
                                     boolean deleteRoleLineSuccess)
            throws Exception {
        infoContext.setBeginTimeStamp(null);
        SysSynchroOrgResult orgResult = ekpRoleSynchro
                .getRoleLineInfo(infoContext);
        if (orgResult.getReturnState() == 2) {
            String resultStr = orgResult.getMessage();
            if (StringUtil.isNotNull(resultStr)) {
                List<JSONObject> sysOrgRoleLines = getRoleLines(resultStr);
                logger.debug("角色线成员数目：" + sysOrgRoleLines.size());
                TransactionStatus status = null;
                try {
                    status = TransactionUtils
                            .beginNewTransaction();
                    for (JSONObject lineObject : sysOrgRoleLines) {
                        addRoleLine(lineObject);
                    }
                    TransactionUtils.getTransactionManager().commit(status);
                } catch (Exception e) {
                    TransactionUtils.getTransactionManager().rollback(status);
                    throw e;
                }
                try {
                    status = TransactionUtils
                            .beginNewTransaction();
                    for (JSONObject lineObject : sysOrgRoleLines) {
                        updateRoleLine(lineObject);
                    }
                    TransactionUtils.getTransactionManager().commit(status);
                } catch (Exception e) {
                    TransactionUtils.getTransactionManager().rollback(status);
                    throw e;
                }
                if (!deleteRoleLineSuccess) {
                    try {
                        status = TransactionUtils
                                .beginNewTransaction();
                        delRoleLines(sysOrgRoleLines);
                        TransactionUtils.getTransactionManager().commit(status);
                    } catch (Exception e) {
                        TransactionUtils.getTransactionManager()
                                .rollback(status);
                        throw e;
                    }
                }
            }
        } else {
            logger.error("获取角色线信息出错,返回状态值为:" + orgResult.getCount() + ",错误信息为:"
                    + orgResult.getMessage());
        }
    }

    private boolean deleteRoleLineRecords(SysSynchroGetOrgInfoContext infoContext)
            throws Exception {
        SysSynchroOrgResult orgResult = null;
        try {
            infoContext.setBeginTimeStamp(lastUpdateTime);
            orgResult = ekpRoleSynchro
                    .getRoleConfMemberInfo(infoContext);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        if (orgResult.getReturnState() == 2) {
            String resultStr = orgResult.getMessage();
            if (StringUtil.isNotNull(resultStr)) {
                JSONArray array = (JSONArray) JSONValue.parse(resultStr);
                logger.debug("角色线配置数目：" + array.size());
                TransactionStatus status = null;
                try {
                    status = TransactionUtils
                            .beginNewTransaction();
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject obj = (JSONObject) array.get(i);
                        String confId = (String) obj.get("confId");
                        JSONArray members = (JSONArray) obj.get("members");
                        List<String> memberList = new ArrayList<String>();
                        for (int j = 0; j < members.size(); j++) {
                            memberList.add((String) members.get(j));
                        }
                        try {
                            delRoleLines(confId, memberList);
                        } catch (HibernateException e) {
                            logger.error("删除角色线出错", e);
                        } catch (Exception e) {
                            logger.error("删除角色线出错", e);
                        }
                    }
                    TransactionUtils.getTransactionManager().commit(status);
                } catch (Exception e) {
                    TransactionUtils.getTransactionManager().rollback(status);
                    throw e;
                }
            }
            return true;
        } else {
            logger.error("获取角色线信息出错,返回状态值为:" + orgResult.getCount() + ",错误信息为:"
                    + orgResult.getMessage());
        }
        return false;
    }

    private void delRoleLines(List<JSONObject> sysOrgRoleLines) {
        Map<String, List<String>> roleLinesMap = new HashMap<String, List<String>>();
        for (JSONObject jsonObject : sysOrgRoleLines) {
            SysOrgRoleConf sysOrgRoleConf = null;
            if (jsonObject.containsKey("roleConf")) {
                String roleConf = (String) jsonObject.get("roleConf");
                if (StringUtil.isNotNull(roleConf)) {
                    try {
                        sysOrgRoleConf = (SysOrgRoleConf) sysOrgRoleConfService
                                .findByPrimaryKey(roleConf,
                                        SysOrgRoleConf.class, true);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            if (sysOrgRoleConf != null) {
                String roleConfId = sysOrgRoleConf.getFdId();
                List<String> roleLineIds = roleLinesMap.get(roleConfId);
                if (roleLineIds == null) {
                    roleLineIds = new ArrayList<String>();
                    roleLinesMap.put(sysOrgRoleConf.getFdId(), roleLineIds);
                }
                roleLineIds.add((String) jsonObject.get("id"));
            }
        }
        for (String roleConfId : roleLinesMap.keySet()) {
            try {
                delRoleLines(roleConfId, roleLinesMap.get(roleConfId));
            } catch (HibernateException e) {
                // TODO 自动生成 catch 块
                e.printStackTrace();
                logger.error("删除角色线出错", e);
            } catch (Exception e) {
                // TODO 自动生成 catch 块
                e.printStackTrace();
                logger.error("删除角色线出错", e);
            }
        }
    }

    private String buildInStr(List<String> ids) {
        String tmp = "";
        String arrStr = "";
        for (String roleLineId : ids) {
            tmp = "'" + roleLineId + "'";
            arrStr += "," + tmp;
        }
        if (arrStr.length() > 0) {
            arrStr = arrStr.substring(1);
        }
        return arrStr;
    }

    private void delRoleLines(String roleConfId, List<String> roleLineIds)
            throws HibernateException, Exception {
        if (roleLineIds != null && roleLineIds.size() < 1000) {
            delRoleLinesBatch(roleConfId, roleLineIds);
        } else {
            int size = roleLineIds.size();
            int i = 0;
            String inStr = "";
            for (; i < size / 500; i++) {
                List<String> ids = roleLineIds.subList(i * 500, (i + 1) * 500);
                String str = "fd_id not in (" + buildInStr(ids) + ") and ";
                inStr += str;
            }
            if (size % 500 != 0) {
                List<String> ids = roleLineIds.subList(i * 500, size);
                String str = "fd_id not in (" + buildInStr(ids) + ") and ";
                inStr += str;
            }
            if (inStr.length() > 0) {
                inStr = inStr.substring(0, inStr.length() - 4);
            }

            logger.debug(
                    "update sys_org_role_line set fd_parent_id = null where fd_role_line_conf_id='"
                            + roleConfId + "' and " + inStr);
            NativeQuery query_update = sysOrgRoleLineService.getBaseDao()
                    .getHibernateSession().createNativeQuery(
                            "update sys_org_role_line set fd_parent_id = null where fd_role_line_conf_id='"
                                    + roleConfId + "' and " + inStr);
            try {
                query_update.executeUpdate();
            } catch (Exception e) {
                logger.error(
                        "update sys_org_role_line set fd_parent_id = null where fd_role_line_conf_id='"
                                + roleConfId + "' and " + inStr);
                logger.error(e.getMessage(), e);
                throw e;
            }

            logger.debug(
                    "delete from sys_org_role_line where fd_role_line_conf_id='"
                            + roleConfId + "' and " + inStr);
            NativeQuery query_delete = sysOrgRoleLineService.getBaseDao()
                    .getHibernateSession().createNativeQuery(
                            "delete from sys_org_role_line where fd_role_line_conf_id='"
                                    + roleConfId + "' and " + inStr);
            try {
                query_delete.executeUpdate();
            } catch (Exception e) {
                logger.error(
                        "delete from sys_org_role_line where fd_role_line_conf_id='"
                                + roleConfId + "' and " + inStr);
                logger.error(e.getMessage(), e);
            }
        }

    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        int size = list.size();
        int i = 0;
        for (; i < size / 2; i++) {
            List<String> ids = list.subList(i * 2, (i + 1) * 2);
            System.out.println(ids);
        }
        if (size % 2 != 0) {
            List<String> ids = list.subList(i * 2, size);
            System.out.println(ids);
        }

    }

    private void delRoleLinesBatch(String roleConfId, List<String> roleLineIds)
            throws HibernateException, Exception {

        if (roleLineIds == null) {
            return;
        }
        String arrStr = "";
        String tmp = "";
        for (String roleLineId : roleLineIds) {
            tmp = "'" + roleLineId + "'";
            arrStr += "," + tmp;
        }
        if (arrStr.length() > 0) {
            arrStr = arrStr.substring(1);
        }

        logger.debug(
                "update sys_org_role_line set fd_parent_id = null where fd_role_line_conf_id='"
                        + roleConfId + "' and fd_id not in (" + arrStr
                        + ")");
        NativeQuery query_update = sysOrgRoleLineService.getBaseDao()
                .getHibernateSession().createNativeQuery(
                        "update sys_org_role_line set fd_parent_id = null where fd_role_line_conf_id='"
                                + roleConfId + "' and fd_id not in (" + arrStr
                                + ")");
        query_update.executeUpdate();

        logger.debug(
                "delete from sys_org_role_line where fd_role_line_conf_id='"
                        + roleConfId + "' and fd_id not in (" + arrStr
                        + ")");
        NativeQuery query_delete = sysOrgRoleLineService.getBaseDao()
                .getHibernateSession().createNativeQuery(
                        "delete from sys_org_role_line where fd_role_line_conf_id='"
                                + roleConfId + "' and fd_id not in (" + arrStr
                                + ")");
        try {
            query_delete.executeUpdate();
        } catch (Exception e) {
            logger.error(
                    "delete from sys_org_role_line where fd_role_line_conf_id='"
                            + roleConfId + "' and fd_id not in (" + arrStr
                            + ")");
            logger.error(e.getMessage(), e);
        }
    }

    private void addRoleLine(JSONObject lineObj) throws Exception {
        // getLocalRoleLineIds().remove(line.getFdId());
        IBaseModel origin = sysOrgRoleLineService
                .findByPrimaryKey((String) lineObj.get("id"),
                        SysOrgRoleLine.class, true);
        if (origin == null) {
            logger.debug("新增角色线成员：" + lineObj.get("id"));
            sysOrgRoleLineService.add(buildRoleLineBase(lineObj));
        }
    }

    private void updateRoleLine(JSONObject lineObj) throws Exception {
        SysOrgRoleLine origin = (SysOrgRoleLine) sysOrgRoleLineService
                .findByPrimaryKey((String) lineObj.get("id"),
                        SysOrgRoleLine.class, true);
        // origin.setFdCreateTime(line.getFdCreateTime());
        // origin.setFdHierarchyId(line.getFdHierarchyId());
        // origin.setFdName(line.getFdName());
        // origin.setFdOrder(line.getFdOrder());
        // origin.setFdParent(line.getFdParent());
        // origin.setSysOrgRoleConf(line.getSysOrgRoleConf());
        // origin.setSysOrgRoleMember(line.getSysOrgRoleMember());
        setRoleLineDetail(origin, lineObj);
        try {
            logger.debug("更新角色线成员：" + lineObj.get("id"));
            sysOrgRoleLineService.update(origin);
        } catch (Exception e) {
            logger.error("{}", origin.getFdId() + "---" + origin.getFdName() + "---"
                            + origin.getSysOrgRoleConf() == null ? ""
                            : origin.getSysOrgRoleConf(),
                    e);
            throw e;
        }

    }

    private Date timeConvert(String beginTime) {
        return DateUtil.convertStringToDate(beginTime, ResourceUtil
                .getString("date.format.time.msel"));
    }

    private void buildElementBase(SysOrgRole role, JSONObject jsonObject) {
        String fieldName = "id";
        Object jsonObj = jsonObject.get(fieldName);
        if (jsonObj != null) {
            role.setFdId((String) jsonObj);
            role.setFdImportInfo((String) jsonObj);
        }

        fieldName = "name";
        jsonObj = jsonObject.get(fieldName);
        if (jsonObj != null) {
            role.setFdName((String) jsonObj);
        }

        fieldName = "keyword";
        jsonObj = jsonObject.get(fieldName);
        if (jsonObj != null) {
            role.setFdKeyword((String) jsonObj);
        }

        fieldName = "no";
        jsonObj = jsonObject.get(fieldName);
        if (jsonObj != null) {
            role.setFdNo((String) jsonObj);
        }

        fieldName = "order";
        jsonObj = jsonObject.get(fieldName);
        if (jsonObj != null) {
            role.setFdOrder(Integer.valueOf((String) jsonObj));
        }

        fieldName = "isAvailable";
        jsonObj = jsonObject.get(fieldName);
        if (jsonObj != null) {
            role.setFdIsAvailable((Boolean) jsonObj);
        }

        fieldName = "memo";
        jsonObj = jsonObject.get(fieldName);
        if (jsonObj != null) {
            role.setFdMemo(StringUtil.unescape((String) jsonObj));
        }

        fieldName = "alterTime";
        jsonObj = jsonObject.get(fieldName);
        if (jsonObj != null) {
            role.setFdAlterTime(timeConvert((String) jsonObj));
        }

    }

    private SysOrgRole genRole(SysOrgRole role, JSONObject jsonObject)
            throws Exception {
        if (role == null) {
            role = new SysOrgRole();
            role.setFdId((String) jsonObject.get("id"));
        }

        role.setFdPlugin((String) jsonObject.get("plugin"));
        role.setFdIsMultiple((Boolean) jsonObject.get("isMultiple"));
        String fieldName = "parameter";
        Object jsonObj = jsonObject.get(fieldName);
        if (jsonObj != null) {
            role.setFdParameter((String) jsonObject.get(fieldName));
        }
        fieldName = "rtnValue";
        jsonObj = jsonObject.get(fieldName);
        if (jsonObj != null) {
            role.setFdRtnValue((String) jsonObject.get(fieldName));
        }
        jsonObj = jsonObject.get("roleConf");
        if (jsonObj != null) {
            role.setFdRoleConf((SysOrgRoleConf) (sysOrgRoleConfService
                    .findByPrimaryKey((String) jsonObj, SysOrgRoleConf.class,
                            true)));
        }
        buildElementBase(role, jsonObject);
        return role;
    }

    private List<JSONObject> getRoles(String json) throws Exception {
        List<JSONObject> sysOrgRoles = new ArrayList<JSONObject>();
        JSONArray array = (JSONArray) JSONValue.parse(json);
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = (JSONObject) array.get(i);
            sysOrgRoles.add(jsonObject);
        }
        return sysOrgRoles;
    }

    private void syncRoleRecords(SysSynchroGetOrgInfoContext infoContext)
            throws Exception {
        SysSynchroOrgResult orgResult = ekpRoleSynchro.getRoleInfo(infoContext);
        if (orgResult.getReturnState() == 2) {
            String resultStr = orgResult.getMessage();
            if (StringUtil.isNotNull(resultStr)) {
                List<JSONObject> sysOrgRoles = getRoles(resultStr);
                TransactionStatus updateDtatus = TransactionUtils
                        .beginNewTransaction();
                try {
                    for (JSONObject role : sysOrgRoles) {
                        updateRole(role);
                    }
                    TransactionUtils.getTransactionManager().commit(
                            updateDtatus);
                } catch (Exception e) {
                    TransactionUtils.getTransactionManager().rollback(
                            updateDtatus);
                    throw e;
                }
            }
        } else {
            logger.error("获取角色信息出错,返回状态值为:" + orgResult.getCount() + ",错误信息为:"
                    + orgResult.getMessage());
        }

    }

    private void updateRole(JSONObject jsonObject) throws Exception {
        SysOrgRole origin = (SysOrgRole) sysOrgRoleService
                .findByPrimaryKey((String) jsonObject.get("id"),
                        SysOrgRole.class, true);
        if (origin != null) {
            sysOrgRoleService.update(genRole(origin, jsonObject));
        } else {
            sysOrgRoleService.add(genRole(null, jsonObject));
        }
    }

    private SysOrgRoleLineDefaultRole genRoleLineDefaultRole(
            SysOrgRoleLineDefaultRole defaultRole,
            JSONObject jsonObject) throws Exception {
        if (defaultRole == null) {
            defaultRole = new SysOrgRoleLineDefaultRole();
            defaultRole.setFdId((String) jsonObject.get("id"));
        }

        Object jsonObj = jsonObject.get("order");

        jsonObj = jsonObject.get("roleConf");
        if (jsonObj != null) {
            defaultRole
                    .setSysOrgRoleConf((SysOrgRoleConf) (sysOrgRoleConfService
                            .findByPrimaryKey((String) jsonObj,
                                    SysOrgRoleConf.class, true)));
        }
        jsonObj = jsonObject.get("personId");
        if (jsonObj != null) {
            defaultRole.setFdPerson((SysOrgElement) (sysOrgElementService
                    .findByPrimaryKey((String) jsonObj, SysOrgRoleConf.class,
                            true)));
        }
        jsonObj = jsonObject.get("postId");
        if (jsonObj != null) {
            defaultRole.setFdPost((SysOrgElement) (sysOrgElementService
                    .findByPrimaryKey((String) jsonObj, SysOrgRoleConf.class,
                            true)));
        }

        return defaultRole;
    }

    private List<JSONObject> getRoleLineDefaultRoles(String json)
            throws Exception {
        List<JSONObject> sysOrgRoleLineDefaultRoles = new ArrayList<JSONObject>();
        JSONArray array = (JSONArray) JSONValue.parse(json);
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = (JSONObject) array.get(i);
            sysOrgRoleLineDefaultRoles
                    .add(jsonObject);
        }
        return sysOrgRoleLineDefaultRoles;
    }

    private void syncRoleLineDefaultRoleRecords(
            SysSynchroGetOrgInfoContext infoContext) throws Exception {
        infoContext.setBeginTimeStamp(null);
        SysSynchroOrgResult orgResult = ekpRoleSynchro
                .getRoleLineDefaultRoleInfo(infoContext);
        if (orgResult.getReturnState() == 2) {
            String resultStr = orgResult.getMessage();
            if (StringUtil.isNotNull(resultStr)) {
                List<JSONObject> sysOrgRoleLineDefaultRoles = getRoleLineDefaultRoles(
                        resultStr);
                TransactionStatus updateDtatus = TransactionUtils
                        .beginNewTransaction();
                try {
                    for (JSONObject defaultRole : sysOrgRoleLineDefaultRoles) {
                        updateRoleLineDefaultRole(defaultRole);
                    }
                    TransactionUtils.getTransactionManager().commit(
                            updateDtatus);
                } catch (Exception e) {
                    TransactionUtils.getTransactionManager().rollback(
                            updateDtatus);
                    throw e;
                }
            }
        } else {
            logger.error("获取角色线配置信息出错,返回状态值为:" + orgResult.getCount()
                    + ",错误信息为:" + orgResult.getMessage());
        }
    }

    private void updateRoleLineDefaultRole(JSONObject jsonObject)
            throws Exception {
        SysOrgRoleLineDefaultRole origin = (SysOrgRoleLineDefaultRole) sysOrgRoleLineDefaultRoleService
                .findByPrimaryKey((String) jsonObject.get("id"),
                        SysOrgRoleLineDefaultRole.class, true);
        if (origin != null) {
            sysOrgRoleLineDefaultRoleService
                    .update(genRoleLineDefaultRole(origin, jsonObject));
        } else {
            sysOrgRoleLineDefaultRoleService
                    .add(genRoleLineDefaultRole(null, jsonObject));
        }
    }

    public void setSysOrgRoleConfService(
            ISysOrgRoleConfService sysOrgRoleConfService) {
        this.sysOrgRoleConfService = sysOrgRoleConfService;
    }

    public ISysOrgRoleConfService getSysOrgRoleConfService() {
        return sysOrgRoleConfService;
    }

    public void setSysOrgRoleLineService(
            ISysOrgRoleLineService sysOrgRoleLineService) {
        this.sysOrgRoleLineService = sysOrgRoleLineService;
    }

    public ISysOrgRoleLineService getSysOrgRoleLineService() {
        return sysOrgRoleLineService;
    }

    public void setSysOrgRoleService(ISysOrgRoleService sysOrgRoleService) {
        this.sysOrgRoleService = sysOrgRoleService;
    }

    public ISysOrgRoleService getSysOrgRoleService() {
        return sysOrgRoleService;
    }

    public void setSysOrgRoleLineDefaultRoleService(
            ISysOrgRoleLineDefaultRoleService sysOrgRoleLineDefaultRoleService) {
        this.sysOrgRoleLineDefaultRoleService = sysOrgRoleLineDefaultRoleService;
    }

    public ISysOrgRoleLineDefaultRoleService getSysOrgRoleLineDefaultRoleService() {
        return sysOrgRoleLineDefaultRoleService;
    }

    private ISysOrgRoleConfService sysOrgRoleConfService;

    private ISysOrgRoleLineService sysOrgRoleLineService;

    private ISysOrgRoleService sysOrgRoleService;

    private ISysOrgRoleLineDefaultRoleService sysOrgRoleLineDefaultRoleService;

    private void syncRoleConfCateRecords() throws Exception {
        SysSynchroOrgResult orgResult = ekpRoleSynchro
                .getRoleConfCateInfo(new SysSynchroGetOrgInfoContext());

        if (orgResult.getReturnState() == 2) {
            String resultStr = orgResult.getMessage();
            if (StringUtil.isNotNull(resultStr)) {
                List<SysOrgRoleConfCate> sysOrgRoleConfCates = getOrgRoleConfCates(
                        resultStr, true);
                TransactionStatus updateDtatus = TransactionUtils
                        .beginNewTransaction();
                try {
                    for (SysOrgRoleConfCate roleConfCate : sysOrgRoleConfCates) {
                        addRoleConfCate(roleConfCate);
                    }
                    sysOrgRoleConfCates = getOrgRoleConfCates(resultStr, false);
                    for (SysOrgRoleConfCate roleConfCate : sysOrgRoleConfCates) {
                        updateRoleConfCate(roleConfCate);
                    }
                    TransactionUtils.getTransactionManager().commit(
                            updateDtatus);
                } catch (Exception e) {
                    TransactionUtils.getTransactionManager().rollback(
                            updateDtatus);
                    throw e;
                }
            }
        } else {
            logger.error("获取组织架构角色分类信息出错,返回状态值为:" + orgResult.getCount()
                    + ",错误信息为:" + orgResult.getMessage());
        }
    }

    private List<SysOrgRoleConfCate> getOrgRoleConfCates(String json,
                                                         boolean isBase) throws Exception {
        List<SysOrgRoleConfCate> sysOrgRoleConfCates = new ArrayList<SysOrgRoleConfCate>();
        JSONArray array = (JSONArray) JSONValue.parse(json);
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = (JSONObject) array.get(i);
            sysOrgRoleConfCates.add(buildOrgRoleConfCate(jsonObject, isBase));
        }
        return sysOrgRoleConfCates;
    }

    private void addRoleConfCate(SysOrgRoleConfCate roleConfCate)
            throws Exception {
        IBaseModel origin = sysOrgRoleConfCateService.findByPrimaryKey(
                roleConfCate.getFdId(), SysOrgRoleConfCate.class, true);
        if (origin == null) {
            Date current = new Date();
            roleConfCate.setFdCreateTime(current);
            roleConfCate.setFdAlterTime(current);
            sysOrgRoleConfCateService.add(roleConfCate);
        }
    }

    private void updateRoleConfCate(SysOrgRoleConfCate roleConfCate)
            throws Exception {
        SysOrgRoleConfCate origin = (SysOrgRoleConfCate) sysOrgRoleConfCateService
                .findByPrimaryKey(roleConfCate.getFdId(),
                        SysOrgRoleConfCate.class, true);
        origin.setFdAlterTime(new Date());
        origin.setFdKeyword(roleConfCate.getFdKeyword());
        origin.setFdName(roleConfCate.getFdName());
        origin.getDynamicMap().putAll(roleConfCate.getDynamicMap());
        origin.setFdParent(roleConfCate.getFdParent());

        sysOrgRoleConfCateService.update(origin);
    }

    private SysOrgRoleConfCate buildOrgRoleConfCate(JSONObject jsonObject,
                                                    boolean isBase) throws Exception {
        SysOrgRoleConfCate roleConfCate = new SysOrgRoleConfCate();
        roleConfCate.setFdId((String) jsonObject.get("id"));
        roleConfCate.setFdName((String) jsonObject.get("name"));
        if (!isBase) {
            if (jsonObject.containsKey("langProps")) {
                JSONObject o = (JSONObject) jsonObject.get("langProps");
                if (o != null) {
                    Map map = new HashMap();
                    for (String key : (Set<String>) o.keySet()) {
                        map.put(key, o.get(key));
                    }
                    roleConfCate.setDynamicMap(map);
                }
            }
            if (jsonObject.containsKey("keyword")) {
                roleConfCate.setFdKeyword((String) jsonObject.get("keyword"));
            }
            if (jsonObject.containsKey("parent")) {
                String parentId = (String) jsonObject.get("parent");
                if (StringUtil.isNotNull(parentId)) {
                    roleConfCate
                            .setFdParent((SysOrgRoleConfCate) sysOrgRoleConfCateService
                                    .findByPrimaryKey(parentId,
                                            SysOrgRoleConfCate.class,
                                            true));
                }
            }
        }

        return roleConfCate;
    }

    public void setSysOrgRoleConfCateService(
            ISysOrgRoleConfCateService sysOrgRoleConfCateService) {
        this.sysOrgRoleConfCateService = sysOrgRoleConfCateService;
    }

    public ISysOrgRoleConfCateService getSysOrgRoleConfCateService() {
        return sysOrgRoleConfCateService;
    }

    private ISysOrgRoleConfCateService sysOrgRoleConfCateService;

}
