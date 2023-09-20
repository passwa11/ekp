package com.landray.kmss.third.welink.oms;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.welink.api.WeLinkServerApi;
import com.landray.kmss.third.welink.model.ThirdWelinkDeptMapping;
import com.landray.kmss.third.welink.model.ThirdWelinkPersonMapping;
import com.landray.kmss.third.welink.service.IThirdWelinkDeptMappingService;
import com.landray.kmss.third.welink.service.IThirdWelinkPersonMappingService;
import com.landray.kmss.third.welink.util.SysOrgTypeSort;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.util.*;

public class SynchroOrg2WelinkNewImp {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SynchroOrg2WelinkNewImp.class);

    private IThirdWelinkDeptMappingService thirdWelinkDeptMappingService;

    private IThirdWelinkPersonMappingService thirdWelinkPersonMappingService;

    /**
     * 需要同步的部门
     */
    private List<SysOrgElement> targetDepartments = new ArrayList<>();

    /**
     * 需要同步的人员
     */
    private List<SysOrgPerson> targetPersons = new ArrayList<>();

    /**
     * 不需要同步的用户ids
     */
    private Set<String> excludedUserIds = new HashSet<>();

    private WeLinkServerApi weLinkServerApi;

    /**
     * 映射表数据
     */
    private Map<String, String> deptMapping = new HashMap<String, String>();

    private Map<String, String> userMapping = new HashMap<>();

    /**
     * WeLink系统已存在的所有部门信息
     */
    private Map<String, String> weLinkDeptInfoMap = new HashMap<>();

    private Map<String, String> weLinkUserInfos = new HashMap<>();

    public SynchroOrg2WelinkNewImp(){
        this.thirdWelinkDeptMappingService = (IThirdWelinkDeptMappingService) SpringBeanUtil.getBean("thirdWelinkDeptMappingService");
        this.thirdWelinkPersonMappingService = (IThirdWelinkPersonMappingService) SpringBeanUtil.getBean("thirdWelinkPersonMappingService");
        this.weLinkServerApi = new WeLinkServerApi();
    }

    public void setExcludedUserIds(Set<String> excludedUserIds) {
        this.excludedUserIds = excludedUserIds;
    }

    public void setTargetDepartments(List<SysOrgElement> targetDepartments) {
        this.targetDepartments = targetDepartments;
        Collections.sort(this.targetDepartments, new SysOrgTypeSort());
    }

    public void setTargetPersons(List<SysOrgPerson> targetPersons) {
        this.targetPersons = targetPersons;
    }

    /**
     * 同步部门
     * @param context
     */
    public void syncDepartments(SysQuartzJobContext context) throws Exception {
        for(SysOrgElement dept : this.targetDepartments){
            if (!dept.getFdIsAvailable() && !deptMapping.containsKey(dept.getFdId())) {
                if(logger.isDebugEnabled()){
                    logger.debug("无效部门，且没有同步过，ID:" + dept.getFdId() + "，名称:"
                            + dept.getFdName());
                }
                continue;
            }
            //更新部门
            String parentCode = null;
            if(dept.getFdParent() != null && weLinkDeptInfoMap.containsKey(dept.getFdParent().getFdId())){
                parentCode = weLinkDeptInfoMap.get(dept.getFdParent().getFdId());
            }
            if(deptMapping.containsKey(dept.getFdId())){
                String deptCode = deptMapping.get(dept.getFdId());
                weLinkServerApi.updateDepartment(dept, deptCode, parentCode);
                context.logMessage("成功同步更新部门："+dept.getFdId()+"->"+dept.getFdName()+"->"+deptCode);
            }
            else{
                TransactionStatus status = null;
                Throwable t = null;
                try{
                    if(weLinkDeptInfoMap.containsKey(dept.getFdId())){
                        String deptCode = weLinkDeptInfoMap.get(dept.getFdId());
                        weLinkServerApi.updateDepartment(dept, deptCode, parentCode);
                        if (logger.isDebugEnabled()) {
                            logger.debug("开启事务");
                        }
                        status = TransactionUtils.beginNewTransaction();
                        this.thirdWelinkDeptMappingService.addMapping(dept, deptCode);
                        if (logger.isDebugEnabled()) {
                            logger.debug("提交事务");
                        }
                        TransactionUtils.commit(status);
                        context.logMessage("成功同步修补部门关系："+dept.getFdId()+"->"+dept.getFdName()+"->"+deptCode);
                    }
                    else{
                        //新增部门
                        String deptCode = weLinkServerApi.addDepartment(dept, parentCode);
                        if (logger.isDebugEnabled()) {
                            logger.debug("开启事务");
                        }
                        status = TransactionUtils.beginNewTransaction();
                        this.thirdWelinkDeptMappingService.addMapping(dept, deptCode);
                        if (logger.isDebugEnabled()) {
                            logger.debug("提交事务");
                        }
                        TransactionUtils.commit(status);
                        context.logMessage("成功同步新增部门："+dept.getFdId()+"->"+dept.getFdName()+"->"+deptCode);
                    }
                }
                catch (Exception e){
                    t = e;
                    logger.error("同步部门出错："+dept.getFdId()+"->"+dept.getFdName(), e);
                    throw e;
                }
                finally {
                    if (t != null && status != null && status.isRollbackOnly()) {
                        try {
                            logger.debug("回滚事务");
                            TransactionUtils.rollback(status);
                        } catch (Exception ex) {
                            logger.error("---事务回滚出错---", ex);
                        }
                    }
                }
            }
        }
    }

    /**
     * 同步人员
     * @param context
     */
    public void syncPersons(SysQuartzJobContext context){
        for(SysOrgPerson person : this.targetPersons){
            if (excludedUserIds.contains(person.getFdId())) {
                continue;
            }
            if (person.getFdIsAvailable()) {
                if (userMapping.containsKey(person.getFdId())) {
                    //同步更新用户信息
                    try {
                        this.weLinkServerApi.updateUser(person);
                        context.logMessage("成功同步更新人员：" + person.getFdId() + "->" + person.getFdName() + "->" + userMapping.get(person.getFdId()));
                    } catch (Exception e) {
                        logger.error("同步更新用户出错", e);
                        context.logMessage("同步更新人员失败:"+ person.getFdId()+"->"+ person.getFdName());
                    }
                }
                else {
                    if(weLinkUserInfos.containsKey(person.getFdId())){
                        fixWeLinkUserMapping(context, person);
                    }
                    else{
                        //同步新增用户
                        syncNewPerson(context, person);
                    }
                }
            } else if (userMapping.containsKey(person.getFdId())) {
                removeInvalidPerson(context, person);
            }
        }
    }

    /**
     * 删除无效用户
     * @param context
     * @param person
     */
    private void removeInvalidPerson(SysQuartzJobContext context, SysOrgPerson person) {
        TransactionStatus status = null;
        Throwable t = null;
        try{
            if(weLinkUserInfos.containsKey(person.getFdId())){
                weLinkServerApi.deleteUser(person.getFdId(), weLinkUserInfos.get(person.getFdId()), person.getFdMobileNo());
            }
            if (logger.isDebugEnabled()) {
                logger.debug("开启事务");
            }
            status = TransactionUtils.beginNewTransaction();
            ThirdWelinkPersonMapping mapping = thirdWelinkPersonMappingService.findByEkpId(person.getFdId());
            thirdWelinkPersonMappingService.delete(mapping);
            context.logMessage("成功删除无效用户的映射关系："+ person.getFdId()+"->"+ person.getFdName());
            if (logger.isDebugEnabled()) {
                logger.debug("提交事务");
            }
            TransactionUtils.commit(status);
        }
        catch (Exception e){
            t = e;
            logger.error("删除无效人员出错："+ person.getFdId()+"->"+ person.getFdName(), e);
            context.logMessage("删除无效用户的映射关系失败："+ person.getFdId()+"->"+ person.getFdName());
        }
        finally {
            if (t != null && status != null && status.isRollbackOnly()) {
                try {
                    logger.debug("回滚事务");
                    TransactionUtils.rollback(status);
                } catch (Exception ex) {
                    logger.error("---事务回滚出错---", ex);
                }
            }
        }
    }

    /**
     * 新增用户
     * @param context
     * @param person
     */
    private void syncNewPerson(SysQuartzJobContext context, SysOrgPerson person) {
        TransactionStatus status = null;
        Throwable t = null;
        try {
            String userId = this.weLinkServerApi.addUser(person);
            if (logger.isDebugEnabled()) {
                logger.debug("开启事务");
            }
            status = TransactionUtils.beginNewTransaction();
            thirdWelinkPersonMappingService.addMapping(person, userId);
            if (logger.isDebugEnabled()) {
                logger.debug("提交事务");
            }
            TransactionUtils.commit(status);
            context.logMessage("成功同步用户"+ person.getFdId()+"->"+ person.getFdName()+"->"+userId);
        } catch (Exception e) {
            t = e;
            logger.error("同步新增用户出错", e);
            context.logMessage("同步用户失败："+ person.getFdId()+"->"+ person.getFdName());
        }
        finally {
            if (t != null && status != null && status.isRollbackOnly()) {
                try {
                    logger.debug("回滚事务");
                    TransactionUtils.rollback(status);
                } catch (Exception ex) {
                    logger.error("---事务回滚出错---", ex);
                }
            }
        }
    }

    /**
     * 修补用户关系
     * @param context
     * @param person
     */
    private void fixWeLinkUserMapping(SysQuartzJobContext context, SysOrgPerson person) {
        String userId = weLinkUserInfos.get(person.getFdId());
        //同步修补用户关系
        TransactionStatus status = null;
        Throwable t = null;
        try {
            this.weLinkServerApi.updateUser(person);
            if (logger.isDebugEnabled()) {
                logger.debug("开启事务");
            }
            status = TransactionUtils.beginNewTransaction();
            thirdWelinkPersonMappingService.addMapping(person, userId);
            if (logger.isDebugEnabled()) {
                logger.debug("提交事务");
            }
            TransactionUtils.commit(status);
            context.logMessage("成功同步修补映射关系用户"+ person.getFdId()+"->"+ person.getFdName()+"->"+userId);
        } catch (Exception e) {
            t = e;
            logger.error("同步修补用户关系出错", e);
            context.logMessage("同步修补映射关系用户失败："+ person.getFdId()+"->"+ person.getFdName()+"->"+userId);
        }
        finally {
            if (t != null && status != null && status.isRollbackOnly()) {
                try {
                    logger.debug("回滚事务");
                    TransactionUtils.rollback(status);
                } catch (Exception ex) {
                    logger.error("---事务回滚出错---", ex);
                }
            }
        }
    }

    /**
     * 初始化
     *
     * @throws Exception
     */
    public void init() throws Exception {
        //初始化映射数据Map
        List<ThirdWelinkDeptMapping> deptMapping = thirdWelinkDeptMappingService.findList(null, null);
        if (deptMapping != null) {
            deptMapping.stream().forEach(m -> {
                    this.deptMapping.put(m.getFdEkpDept().getFdId(), m.getFdWelinkId());
            });
        }
        List<ThirdWelinkPersonMapping> personMappings = thirdWelinkPersonMappingService.findList(null, null);
        if (personMappings != null) {
            personMappings.stream().forEach(m -> {
                    userMapping.put(m.getFdEkpPerson().getFdId(), m.getFdWelinkId());
            });
        }
        cleanInvalidDeptMappingInfos();
        cleanInvalidPersonMappingInfos();
    }

    /**
     * 清理无效的部门映射信息
     *
     * @throws Exception
     */
    private void cleanInvalidDeptMappingInfos() throws Exception {
        List<String> invalidKeys = new ArrayList<>();
        JSONArray weLinkDepts = this.weLinkServerApi.getSubDepartments(null, null);
        if (weLinkDepts != null && weLinkDepts.size() > 0) {
            weLinkDepts.stream().filter(dept -> (dept instanceof JSONObject)).forEach(dept -> {
                JSONObject deptObj = (JSONObject) dept;
                weLinkDeptInfoMap.put(deptObj.getString("corpDeptCode"), deptObj.getString("deptCode"));
            });
        }
        //清理无效部门映射关系
        deptMapping.keySet().stream().forEach(ekpOrgId -> {
            if (!weLinkDeptInfoMap.containsKey(ekpOrgId)) {
                TransactionStatus status = null;
                Throwable t = null;
                try {
                    status = TransactionUtils.beginNewTransaction();
                    ThirdWelinkDeptMapping invalidMapping = thirdWelinkDeptMappingService.findByEkpId(ekpOrgId);
                    thirdWelinkDeptMappingService.delete(invalidMapping);
                    TransactionUtils.commit(status);
                    invalidKeys.add(ekpOrgId);
                    if (logger.isDebugEnabled()) {
                        logger.debug("成功删除无效部门映射：" + ekpOrgId + "->"+invalidMapping.getFdWelinkId());
                    }
                } catch (Exception e) {
                    t = e;
                    logger.error("删除无效部门映射失败：" + ekpOrgId, e);
                } finally {
                    if (t != null && status != null && status.isRollbackOnly()) {
                        try {
                            logger.debug("回滚事务");
                            TransactionUtils.rollback(status);
                        } catch (Exception ex) {
                            logger.error("---事务回滚出错---", ex);
                        }
                    }
                }
            }
        });
        invalidKeys.stream().forEach(k -> {
            deptMapping.remove(k);
        });
    }

    /**
     * 清理无效的人员映射信息
     *
     * @throws Exception
     */
    private void cleanInvalidPersonMappingInfos() throws Exception {
        List<String> invalidKeys = new ArrayList<>();
        weLinkDeptInfoMap.keySet().stream().forEach(ekpOrgId->{
            try {
                JSONArray deptUsers = this.weLinkServerApi.getDepartmentUsers(weLinkDeptInfoMap.get(ekpOrgId), ekpOrgId);
                if (deptUsers != null && deptUsers.size() > 0) {
                    deptUsers.stream().filter(u -> (u instanceof JSONObject)).forEach(u -> {
                        JSONObject userObj = (JSONObject) u;
                        weLinkUserInfos.put(userObj.getString("corpUserId"), userObj.getString("userId"));
                    });
                }
            } catch (Exception e) {
                logger.error("清理无效部门人员关系时出错："+ ekpOrgId,e);
            }
        });
        userMapping.keySet().stream().forEach(ekpOrgId -> {
            if (!weLinkUserInfos.containsKey(ekpOrgId)) {
                TransactionStatus status = null;
                Throwable t = null;
                try {
                    status = TransactionUtils.beginNewTransaction();
                    ThirdWelinkPersonMapping invalidMapping = thirdWelinkPersonMappingService.findByEkpId(ekpOrgId);
                    thirdWelinkPersonMappingService.delete(invalidMapping);
                    TransactionUtils.commit(status);
                    invalidKeys.add(ekpOrgId);
                    if (logger.isDebugEnabled()) {
                        logger.debug("成功删除无效人员映射：" + ekpOrgId);
                    }
                } catch (Exception e) {
                    t = e;
                    logger.error("删除无效人员映射失败：" + ekpOrgId, e);
                } finally {
                    if (t != null && status != null && status.isRollbackOnly()) {
                        try {
                            logger.debug("回滚事务");
                            TransactionUtils.rollback(status);
                        } catch (Exception ex) {
                            logger.error("---事务回滚出错---", ex);
                        }
                    }
                }
            }
        });
        invalidKeys.stream().forEach(k -> {
            userMapping.remove(k);
        });
    }
}
