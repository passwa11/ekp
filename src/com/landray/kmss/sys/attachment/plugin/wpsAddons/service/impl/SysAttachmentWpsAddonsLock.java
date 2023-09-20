package com.landray.kmss.sys.attachment.plugin.wpsAddons.service.impl;

import com.landray.kmss.sys.attachment.jg.AbstractSysAttachmentFunction;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.plugin.wpsAddons.service.ISysAttachmentWpsAddonsExt;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;

import java.util.Date;
import java.util.Map;

import static com.landray.kmss.util.SpringBeanUtil.getBean;

public class SysAttachmentWpsAddonsLock extends AbstractSysAttachmentFunction implements ISysAttachmentWpsAddonsExt {
    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttachmentWpsAddonsLock.class);

    private ISysAttMainCoreInnerService sysAttMainService = null;

    @Override
    public ISysAttMainCoreInnerService getSysAttMainService() {
        if (sysAttMainService == null) {
            sysAttMainService = (ISysAttMainCoreInnerService) getBean("sysAttMainService");
        }
        return sysAttMainService;
    }

    @Override
    public boolean execute(Map<String,String> params) throws Exception {
        boolean success = false;
        String fdId = params.get("fdId");
        String modelId = params.get("fdModelId");
        String modelName = params.get("fdModelName");
        String key = params.get("fdKey");
        SysAttMain sysAttMain = getSysAttMain(fdId, modelId, modelName, key,true);
        if (sysAttMain == null) {
            success = true;
            if (logger.isDebugEnabled()) {
                logger.debug("AttMain不存在，入参attMainId={}, modelId={}, modelName={}, key={}", fdId, modelId, modelName, key);
            }
        } else {
            String operator = UserUtil.getKMSSUser().getPerson().getFdId();
            if (StringUtils.isEmpty(operator)) {
                operator = params.get("userId");
            }
            if (StringUtils.isEmpty(sysAttMain.getFdPersonId())) {
                Date now = new Date();
                String sql = "update sys_att_main set fd_person_id=:fd_person_id, fd_last_open_time=:fd_last_open_time where fd_id=:fd_id";
                NativeQuery nativeQuery = getSysAttMainService().getBaseDao().getHibernateSession().createNativeQuery(sql);
                nativeQuery.addSynchronizedQuerySpace("sys_att_main")
                        .setParameter("fd_person_id", operator).setParameter("fd_last_open_time", now)
                        .setParameter("fd_id", sysAttMain.getFdId()).executeUpdate();
                success = true;
                if (logger.isDebugEnabled()) {
                    logger.debug("上锁成功，attMainId={}, personId={}", sysAttMain.getFdId(), operator);
                }
            } else if (!StringUtils.equals(sysAttMain.getFdPersonId(), operator)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("上锁失败，当前attMainId={}, personId={}, operator={}", sysAttMain.getFdId(), sysAttMain.getFdPersonId(), operator);
                }
            } else {
                success = true;
                if (logger.isDebugEnabled()) {
                    logger.debug("重入锁，当前attMainId={}, personId={}, operator={}", sysAttMain.getFdId(), sysAttMain.getFdPersonId(), operator);
                }
            }
        }
        return success;
    }
}
