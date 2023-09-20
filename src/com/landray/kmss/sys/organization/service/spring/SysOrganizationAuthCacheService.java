package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.user.KMSSUserAuthInfoCache;
import com.landray.kmss.sys.authentication.user.UserAuthInfo;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrganizationConfig;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 预缓存用户权限信息
 *
 * @author 严明镜
 * @version 1.0 2021年04月07日
 */
public class SysOrganizationAuthCacheService {

    private final Logger log = LoggerFactory.getLogger(SysOrganizationAuthCacheService.class);

    /**
     * 预缓存用户权限信息
     */
    @SuppressWarnings({"unchecked", "unused"})
    public void cacheUserAuthInfo(SysQuartzJobContext jobContext) {
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            SysOrganizationConfig config = new SysOrganizationConfig();
            if (!config.getIsUserAuthCacheEnable() || config.getUserAuthCacheLimitIds() == null) {
                if (log.isDebugEnabled()) {
                    log.debug("未执行缓存用户权限信息定时任务，IsUserAuthCacheEnable=" + config.getIsUserAuthCacheEnable() + "，UserAuthCacheLimitIds=" + config.getUserAuthCacheLimitIds());
                }
                jobContext.logMessage("【任务忽略】未开启预缓存用户权限信息 或 未配置缓存对象");
                return;
            }
            List<SysOrgElement> elements = getSysOrgElementService().findByPrimaryKeys(config.getUserAuthCacheLimitIds().split(";"));
            if (ArrayUtil.isEmpty(elements)) {
                if (log.isDebugEnabled()) {
                    log.debug("未找到配置的人员或部门下的人员信息，未执行缓存用户权限信息定时任务。");
                }
                jobContext.logMessage("【任务忽略】未找到配置的人员或部门下的人员信息：" + config.getUserAuthCacheLimitIds());
                return;
            }
            Set<SysOrgPerson> persons = new HashSet<>();
            for (SysOrgElement element : elements) {
                if (element != null) {
                    if (element.getFdOrgType().equals(SysOrgConstant.ORG_TYPE_PERSON)) {
                        persons.add((SysOrgPerson) getSysOrgElementService().format(element));
                    } else {
                        persons.addAll(getSysOrgPersonService().findAllChildElement(element, SysOrgConstant.ORG_TYPE_PERSON));
                    }
                }
            }
            if (ArrayUtil.isEmpty(persons)) {
                if (log.isDebugEnabled()) {
                    log.debug("未找到配置中对应的人员信息，未执行缓存用户权限信息定时任务。");
                }
                jobContext.logMessage("【任务忽略】缓存配置无法解析到人员：" + config.getUserAuthCacheLimitIds());
                return;
            }
            jobContext.logMessage("正在执行预缓存用户权限信息，预处理人员数量：" + persons.size());
            long start = System.currentTimeMillis();
            int count = 0;
            for (SysOrgPerson person : persons) {
                UserAuthInfo info = KMSSUserAuthInfoCache.getInstance().load(person, config.getUserAuthCacheExpire());
                if (info != null) {
                    count++;
                }
            }
            if (log.isDebugEnabled()) {
                log.debug("预缓存用户权限信息处理完成，耗时：" + ((System.currentTimeMillis() - start) / 1000.0) + "秒");
            }
            jobContext.logMessage("预缓存用户权限信息处理完成，实际缓存人员数量：" + count + "（已失效人员忽略）。");
            TransactionUtils.commit(status);
        } catch (Exception e) {
            TransactionUtils.rollback(status);
            log.error("预缓存用户权限信息执行异常：", e);
            jobContext.logError("预缓存用户权限信息执行异常", e);
        }
    }

    private ISysOrgElementService sysOrgElementService;

    private ISysOrgElementService getSysOrgElementService() {
        if (sysOrgElementService == null) {
            sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
        }
        Assert.isTrue(sysOrgElementService != null, "未找到sysOrgElementService");
        return sysOrgElementService;
    }

    private ISysOrgPersonService sysOrgPersonService;

    private ISysOrgPersonService getSysOrgPersonService() {
        if (sysOrgPersonService == null) {
            sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
        }
        Assert.isTrue(sysOrgPersonService != null, "未找到sysOrgPersonService");
        return sysOrgPersonService;
    }

}
