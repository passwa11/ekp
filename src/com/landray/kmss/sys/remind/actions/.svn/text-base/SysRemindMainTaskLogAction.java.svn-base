package com.landray.kmss.sys.remind.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.remind.service.ISysRemindMainTaskLogService;
import com.landray.kmss.util.StringUtil;

/**
 * 任务日志
 * 
 * @author panyh
 * @date Jun 28, 2020
 */
public class SysRemindMainTaskLogAction extends ExtendAction {

	private ISysRemindMainTaskLogService sysRemindMainTaskLogService;

	@Override
	protected ISysRemindMainTaskLogService getServiceImp(HttpServletRequest request) {
		if (sysRemindMainTaskLogService == null) {
			sysRemindMainTaskLogService = (ISysRemindMainTaskLogService) getBean("sysRemindMainTaskLogService");
		}
		return sysRemindMainTaskLogService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String taskId = request.getParameter("taskId");
		if (StringUtil.isNotNull(taskId)) {
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "sysRemindMainTaskLog.fdTask.fdId = :taskId"));
			hqlInfo.setParameter("taskId", taskId);
		}
		String remindId = request.getParameter("remindId");
		if (StringUtil.isNotNull(remindId)) {
			hqlInfo.setJoinBlock(" inner join sysRemindMainTaskLog.fdTask task");
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "task.fdRemindId = :remindId"));
			hqlInfo.setParameter("remindId", remindId);
		}
	}

}
