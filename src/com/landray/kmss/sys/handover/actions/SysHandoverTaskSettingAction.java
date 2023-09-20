package com.landray.kmss.sys.handover.actions;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.sys.appconfig.actions.SysAppConfigAction;
import com.landray.kmss.sys.handover.model.SysHandoverTaskSetting;
import com.landray.kmss.sys.quartz.model.SysQuartzJob;
import com.landray.kmss.sys.quartz.service.ISysQuartzJobService;

/**
 * 工作交接任务设置
 * 
 * @author 潘永辉 2017年7月10日
 *
 */
public class SysHandoverTaskSettingAction extends SysAppConfigAction {

	public ISysQuartzJobService getSysQuartzJobService() {
		return (ISysQuartzJobService) getBean("sysQuartzJobService");
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward af = super.update(mapping, form, request, response);

		List<SysQuartzJob> list = getSysQuartzJobService().findList(
				"fdJobService = 'sysHandoverConfigMainService' and fdJobMethod = 'executeAuth'", null);
		if (!list.isEmpty()) {
			// 更新成功，更新定时任务执行时间
			StringBuffer cronExpression = new StringBuffer();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new SysHandoverTaskSetting().getRunTimeForJob());
			cronExpression.append("0 ")
					.append(calendar.get(Calendar.MINUTE) + " ")
					.append(calendar.get(Calendar.HOUR_OF_DAY) + " ")
					.append("* * ?");
			SysQuartzJob job = list.get(0);
			job.setFdCronExpression(cronExpression.toString());
			getSysQuartzJobService().update(job);
		}

		return af;
	}
}
