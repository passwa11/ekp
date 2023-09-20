package com.landray.kmss.sys.handover.service.spring;

import java.util.Date;

import com.landray.kmss.sys.handover.constant.SysHandoverConstant;
import com.landray.kmss.sys.handover.interfaces.config.HandoverExecuteContext;
import com.landray.kmss.sys.handover.interfaces.config.HandoverExecuteResult;
import com.landray.kmss.sys.handover.interfaces.config.HandoverItem;
import com.landray.kmss.sys.handover.model.SysHandoverConfigLog;
import com.landray.kmss.sys.handover.model.SysHandoverConfigMain;
import com.landray.kmss.sys.handover.service.ISysHandoverConfigLogService;
import com.landray.kmss.sys.handover.service.ISysHandoverConfigMainService;
import com.landray.kmss.sys.handover.service.ISysHandoverLogService;
import com.landray.kmss.sys.handover.service.spring.HandoverPluginUtils.HandoverConfig;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.UserUtil;

public class SysHandoverLogServiceImp implements ISysHandoverLogService {

	static int STATUS_WAITTING = 0;

	static int STATUS_RUNNING = 1;

	static int STATUS_SUSPENDED = 2;

	static int STATUS_ENDED = 3;

	private ISysHandoverConfigMainService sysHandoverConfigMainService;

	private ISysHandoverConfigLogService sysHandoverConfigLogService;

	@Override
	public String beforeConfigHandover(HandoverConfig config,
									   HandoverItem item, SysOrgElement from, SysOrgElement to,
									   String mainId) {
		return beforeConfigHandover(config, item, from, to, mainId, null);
	}

	@Override
	public String beforeConfigHandover(HandoverConfig config,
									   HandoverItem item, SysOrgElement from, SysOrgElement to,
									   String mainId, Integer handoverType) {
		try {
			SysHandoverConfigMain sysHandoverConfigMain = (SysHandoverConfigMain) sysHandoverConfigMainService
					.findByPrimaryKey(mainId, null, true);
			if (sysHandoverConfigMain == null) {
				sysHandoverConfigMain = new SysHandoverConfigMain();
				sysHandoverConfigMain.setFdId(mainId);

				sysHandoverConfigMain.setFdFromId(from.getFdId());
				sysHandoverConfigMain.setFdFromName(from.getFdName());
				if (to != null) {
					sysHandoverConfigMain.setFdToId(to.getFdId());
					sysHandoverConfigMain.setFdToName(to.getFdName());
				}
				sysHandoverConfigMain.setDocCreateTime(new Date());
				sysHandoverConfigMain.setDocCreator(UserUtil.getUser());
				sysHandoverConfigMain.setFdState(SysHandoverConstant.HANDOVER_STATE_WAIT); // 等待执行
				if (handoverType != null) {
					sysHandoverConfigMain.setHandoverType(handoverType);
				}
				sysHandoverConfigMainService.add(sysHandoverConfigMain);
			}

			SysHandoverConfigLog sysHandoverConfigLog = new SysHandoverConfigLog();
			sysHandoverConfigLog.setFdMain(sysHandoverConfigMain);
			sysHandoverConfigLog.setFdIsSucc(false);
			sysHandoverConfigLog.setFdStatus(STATUS_RUNNING);
			sysHandoverConfigLog.setFdCount(0L);
			sysHandoverConfigLog.setFdIgnoreCount(0L);
			sysHandoverConfigLog.setFdModule(config.getModule());
			sysHandoverConfigLog.setFdModuleName(config.getMessageKey());
			if (item != null) {
				sysHandoverConfigLog.setFdItem(item.getItem());
				sysHandoverConfigLog.setFdItemName(item.getItemMessageKey());
			}
			sysHandoverConfigLog.setFdStartTime(new Date());

			String fdId = sysHandoverConfigLogService.add(sysHandoverConfigLog);
			sysHandoverConfigLogService.flushHibernateSession();
			sysHandoverConfigLogService.clearHibernateSession();
			return fdId;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void afterConfigHandover(
			HandoverExecuteContext handoverExecuteContext) {
		try {
			HandoverExecuteResult handoverExecuteResult = handoverExecuteContext
					.getHandoverExecuteResult();
			SysHandoverConfigLog sysHandoverConfigLog = (SysHandoverConfigLog) sysHandoverConfigLogService
					.findByPrimaryKey(handoverExecuteResult.getLogId());
			if (handoverExecuteResult.getException() != null) {
				sysHandoverConfigLog.setFdIsSucc(false);
			} else {
				sysHandoverConfigLog.setFdIsSucc(true);
			}
			sysHandoverConfigLog.setFdStatus(STATUS_ENDED);
			sysHandoverConfigLog.setFdEndedTime(new Date());
			sysHandoverConfigLog.setFdCount(handoverExecuteResult.getSuccTotal());
			sysHandoverConfigLog.setFdIgnoreCount(handoverExecuteResult.getIgnoreTotal());

			if (sysHandoverConfigLog.getFdCount() > 0 || sysHandoverConfigLog.getFdIgnoreCount() > 0) {
                sysHandoverConfigLogService.update(sysHandoverConfigLog);
            } else {
                sysHandoverConfigLogService.delete(sysHandoverConfigLog);
            }
			sysHandoverConfigLogService.flushHibernateSession();
			sysHandoverConfigLogService.clearHibernateSession();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setSysHandoverConfigMainService(
			ISysHandoverConfigMainService sysHandoverConfigMainService) {
		this.sysHandoverConfigMainService = sysHandoverConfigMainService;
	}

	public void setSysHandoverConfigLogService(
			ISysHandoverConfigLogService sysHandoverConfigLogService) {
		this.sysHandoverConfigLogService = sysHandoverConfigLogService;
	}

}
