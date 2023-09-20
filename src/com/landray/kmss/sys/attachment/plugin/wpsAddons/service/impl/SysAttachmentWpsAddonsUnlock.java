package com.landray.kmss.sys.attachment.plugin.wpsAddons.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.attachment.jg.AbstractSysAttachmentFunction;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.plugin.wpsAddons.service.ISysAttachmentWpsAddonsExt;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;

import java.util.Map;

import static com.landray.kmss.util.SpringBeanUtil.getBean;

public class SysAttachmentWpsAddonsUnlock extends AbstractSysAttachmentFunction implements ISysAttachmentWpsAddonsExt {
    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttachmentWpsAddonsUnlock.class);

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
            if (StringUtils.isEmpty(sysAttMain.getFdPersonId())) {
                success = true;
                if (logger.isDebugEnabled()) {
                    logger.debug("不处理，SysAttMain没有锁，attMainId={}, personId={}", sysAttMain.getFdId(), sysAttMain.getFdPersonId());
                }
            } else {
                String operator = UserUtil.getKMSSUser().getPerson().getFdId();
                if (StringUtils.isEmpty(operator)) {
                    operator = params.get("userId");
                }
                if (StringUtils.isEmpty(operator)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("解锁失败，当前操作人为空，attMainId={}, operator={}", sysAttMain.getFdId(), operator);
                    }
                } else if (StringUtils.equals(operator, sysAttMain.getFdPersonId())) {
                    String sql = "update sys_att_main set fd_person_id=null, fd_last_open_time=null where fd_id=:fd_id";
                    NativeQuery nativeQuery = getSysAttMainService().getBaseDao().getHibernateSession().createNativeQuery(sql);
                    nativeQuery.addSynchronizedQuerySpace("sys_att_main").setParameter("fd_id", sysAttMain.getFdId()).executeUpdate();
                    success = true;
                    if (logger.isDebugEnabled()) {
                        logger.debug("解锁成功，当前操作attMainId={}, personId={}, operator={}", sysAttMain.getFdId(), sysAttMain.getFdPersonId(), operator);
                    }
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("解锁失败，当前操作attMainId={}, personId={}, operator={}", sysAttMain.getFdId(), sysAttMain.getFdPersonId(), operator);
                    }
                }
            }
            if (success) {
                String lockQueenStr = params.get("lockQueen");
                if (StringUtils.isNotEmpty(lockQueenStr)) {
                    JSONArray lockQueen = JSONArray.parseArray(lockQueenStr);
                    if (!lockQueen.isEmpty()) {
                        String sql = "update sys_att_main set fd_person_id=null, fd_last_open_time=null where fd_id=:fd_id";
                        for (int i = 0; i < lockQueen.size(); i++) {
                            JSONObject o = lockQueen.getJSONObject(i);
                            SysAttMain attMain = getSysAttMain(o.getString("fdId"), null, null, null, true);
                            if (attMain != null && StringUtils.equals(attMain.getFdPersonId(), o.getString("userId"))) {
                                NativeQuery nativeQuery = getSysAttMainService().getBaseDao().getHibernateSession().createNativeQuery(sql);
                                nativeQuery.addSynchronizedQuerySpace("sys_att_main")
                                        .setParameter("fd_id", o.getString("fdId")).executeUpdate();
                            }
                        }
                    }
                }
            }
        }
        return success;
    }
}
