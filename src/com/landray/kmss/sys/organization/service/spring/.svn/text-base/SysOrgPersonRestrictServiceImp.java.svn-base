package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.component.locker.interfaces.ConcurrencyException;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.sys.authentication.user.validate.Config;
import com.landray.kmss.sys.config.util.LicenseUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPersonRestrictLock;
import com.landray.kmss.sys.organization.service.ISysOrgPersonRestrictService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.landray.kmss.util.ArrayUtil.averageAssign;

/**
 * License人员数量受限，业务服务实现
 *
 * @author 潘永辉 2020年1月14日
 */
public class SysOrgPersonRestrictServiceImp extends BaseServiceImp
        implements ISysOrgPersonRestrictService, ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgPersonRestrictServiceImp.class);

    private IComponentLockService getGlobalLockService() {
        return (IComponentLockService) SpringBeanUtil.getBean("componentLockService");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            ISysOrgPersonRestrictService service = (ISysOrgPersonRestrictService) SpringBeanUtil.getBean("sysOrgPersonRestrictService");
            service.refreshPersonRestrict();
        }
    }

    /**
     * 检验人员限制数量
     */
    @Override
    public void refreshPersonRestrict() {
        SysOrgPersonRestrictLock lockObj = new SysOrgPersonRestrictLock();
        try {
            getGlobalLockService().tryLock(lockObj, "refresh", 10 * 1000);
            //先锁定再执行
            // 获取人员上限
            deleteAll();
            // 内部用户
            int licenseCount = StringUtil.getIntFromString(LicenseUtil.get("license-org-person"), -1);
            if (licenseCount > -1) {
                // 获取已存在激活人员数量
                int activate = getActivateCount(false);
                // 检验
                if (licenseCount < activate) {
                    // 设置人员为『受限』
                    setRestrict(activate - licenseCount, false);
                }
            }
            // 外部用户
            licenseCount = StringUtil.getIntFromString(LicenseUtil.get("license-org-person-external"), -1);
            if (licenseCount > -1) {
                // 获取已存在激活人员数量
                int activate = getActivateCount(true);
                // 检验
                if (licenseCount < activate) {
                    // 设置人员为『受限』
                    setRestrict(activate - licenseCount, true);
                }
            }
        } catch (ConcurrencyException e) {
            logger.warn("获取SysOrgPersonRestrictLock:" + e.getMessage(), e);
        } finally {
            getGlobalLockService().unLock(lockObj);
        }
    }

    /**
     * 获取激活人员数量
     *
     * @return
     */
    private int getActivateCount(boolean isExternal) {
        try {
            // 获取可用的特权人员
            List<String> personIds = getPrivilegeIds();
            String sql = "SELECT COUNT(*) FROM sys_org_element WHERE fd_org_type = :orgType AND fd_is_available = :isAvailable AND fd_is_business = :isBusiness" +
                    " AND (fd_id IN (SELECT fd_id FROM sys_org_person WHERE fd_can_login = :canLogin))" +
                    " AND (fd_id NOT IN (SELECT fd_id FROM sys_org_person_restrict))"; // 已经受限
            Map<String, List<String>> params = new HashMap<>();
            if (CollectionUtils.isNotEmpty(personIds)) {
                int maxSize = 1000;
                if (personIds.size() <= maxSize) {
                    // 特权用户
                    sql += " AND fd_id NOT IN (:personIds)";
                    params.put("personIds", personIds);
                } else {
                    List<List<String>> lists = averageAssign(personIds, maxSize);
                    for (int i = 0; i < lists.size(); i++) {
                        List<String> list = lists.get(i);
                        sql += " AND fd_id NOT IN (:personIds_" + i + ")";
                        params.put("personIds_" + i, list);
                    }
                }
            }
            if (isExternal) {
                sql += " AND fd_is_external = :isExternal";
            } else {
                sql += " AND (fd_is_external IS NULL OR fd_is_external = :isExternal)";
            }
            NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(sql)
                    .setParameter("orgType", 8)
                    .setParameter("isAvailable", Boolean.TRUE)
                    .setParameter("isBusiness", Boolean.TRUE)
                    .setParameter("canLogin", Boolean.TRUE)
                    .setParameter("isExternal", isExternal);
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
            List<Object> list = query.list();
            if (CollectionUtils.isNotEmpty(list)) {
                return NumberUtils.toInt(list.get(0).toString(), 0);
            }
        } catch (Exception e) {
            logger.error("操作失败：", e);
        }
        return 0;
    }

    /**
     * 可用数量不够时，设置某些人员不可使用
     *
     * @param count
     */
    private void setRestrict(int count, boolean isExternal) {
        if (count > 0) {
            List<String> personIds = getPrivilegeIds();
            // 按ID倒序获取人员数据，把获取到的数据放到受限的表中
            String hql = "SELECT sysOrgPerson.fdId FROM " + SysOrgPerson.class.getName() + " sysOrgPerson "
                    + " WHERE sysOrgPerson.fdIsAvailable = :fdIsAvailable AND sysOrgPerson.fdIsBusiness = :fdIsBusiness AND (sysOrgPerson.fdCanLogin IS NULL OR sysOrgPerson.fdCanLogin = :fdCanLogin)";
            Map<String, List<String>> params = new HashMap<>();
            int maxSize = 1000;
            if (CollectionUtils.isNotEmpty(personIds)) {
                if (personIds.size() <= maxSize) {
                    // 特权用户
                    hql += " AND sysOrgPerson.fdId NOT IN (:personIds)";
                    params.put("personIds", personIds);
                } else {
                    List<List<String>> lists = averageAssign(personIds, maxSize);
                    for (int i = 0; i < lists.size(); i++) {
                        List<String> list = lists.get(i);
                        hql += " AND sysOrgPerson.fdId NOT IN (:personIds_" + i + ")";
                        params.put("personIds_" + i, list);
                    }
                }
            }
            if (isExternal) {
                hql += " AND sysOrgPerson.fdIsExternal = :isExternal";
            } else {
                hql += " AND (sysOrgPerson.fdIsExternal IS NULL OR sysOrgPerson.fdIsExternal = :isExternal)";
            }
            hql += " ORDER BY sysOrgPerson.fdId DESC";
            try {
                Query query = getBaseDao().getHibernateSession().createQuery(hql);
                query.setCacheable(true);
                query.setCacheMode(CacheMode.NORMAL);
                query.setCacheRegion("sys-organization");
                query.setParameter("fdIsAvailable", true);
                query.setParameter("fdIsBusiness", true);
                query.setParameter("fdCanLogin", true);
                query.setParameter("isExternal", isExternal);
                for (String key : params.keySet()) {
                    query.setParameter(key, params.get(key));
                }
                query.setMaxResults(count);
                List<String> list = query.list();
                if (CollectionUtils.isNotEmpty(list)) {
                    for (String id : list) {
                        addRestrict(id);
                    }
                }
            } catch (Exception e) {
                logger.error("标记受限用户操作失败：", e);
            }
        }
    }

    /**
     * 获取可用的特权人员
     *
     * @return
     */
    private List<String> getPrivilegeIds() {
        List<String> personIds = getBaseDao().getHibernateSession().createNativeQuery("select fd_person_id from sys_org_person_privilege where 1 = 1 order by fd_id").setMaxResults(Config.getLicPrivCount()).list();
        return personIds;
    }

    /**
     * 设置为受限制
     *
     * @param personId
     */
    @Override
    public void addRestrict(String personId) {
        try {
            String sql = "INSERT INTO sys_org_person_restrict(fd_id) VALUES (?)";
            int executeUpdate = getBaseDao().getHibernateSession().createNativeQuery(sql)
                    .setString(0, personId).executeUpdate();
            if (logger.isDebugEnabled()) {
                logger.debug("addRestrict effective count : " + executeUpdate);
            }
        } catch (Exception e) {
            logger.error("操作失败：", e);
        }
    }

    private void deleteAll() {
        try {
            String sql = "DELETE FROM sys_org_person_restrict";
            NativeQuery nativeQuery = getBaseDao().getHibernateSession().createNativeQuery(sql);
            nativeQuery.addSynchronizedQuerySpace("sys_org_person_restrict");
            nativeQuery.executeUpdate();
        } catch (Exception e) {
            logger.error("清空表数据操作失败：", e);
        }
    }

    /**
     * 判断人员是否被受限
     *
     * @param personId
     * @return
     */
    @Override
    public boolean isRestrict(String personId) {
        try {
            Query query = getBaseDao().getHibernateSession().createQuery(
                    "select count(1) from SysOrgPersonRestrict sysOrgPersonRestrict " +
                            "where sysOrgPersonRestrict.fdId=:fdId");
            query.setParameter("fdId", personId);
            List list = query.list();
            Number number = (Number) list.get(0);
            try {
                Integer integer = number.intValue();
                if (integer > 0) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("受限用户:" + personId);
                    }
                    return true;
                }
            } catch (Exception e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("解析受限用户数失败：" + e.getMessage(), e);
                }
            }
            return false;
        } catch (Exception e) {
            logger.error("操作失败：", e);
            // 出异常时，限制用户登录
            return true;
        }
    }

    /**
     * 获取所有受限制的账号信息
     *
     * @return
     */
    @Override
    public List<Map<String, String>> getAllData() {
        List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
        try {
            String sql = "SELECT per.fd_id, per.fd_login_name, elem.fd_name, "
                    + "(select fd_name from sys_org_element where fd_id = elem.fd_parentid) parent_name "
                    + "FROM sys_org_person per, sys_org_person_restrict rest, sys_org_element elem "
                    + "WHERE rest.fd_id = per.fd_id AND elem.fd_id = per.fd_id";
            List<Object[]> list = getBaseDao().getHibernateSession().createNativeQuery(sql).list();
            if (CollectionUtils.isNotEmpty(list)) {
                for (Object[] objs : list) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("fdId", objs[0].toString());
                    map.put("fdLoginName", objs[1] != null ? objs[1].toString() : "");
                    map.put("fdName", objs[2] != null ? objs[2].toString() : "");
                    map.put("fdParentName", objs[3] != null ? objs[3].toString() : "");
                    datas.add(map);
                }
            }
        } catch (Exception e) {
            logger.error("操作失败：", e);
        }
        return datas;
    }

}
