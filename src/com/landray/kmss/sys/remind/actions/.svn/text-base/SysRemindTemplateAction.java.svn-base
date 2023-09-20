package com.landray.kmss.sys.remind.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.sys.remind.service.ISysRemindTemplateService;

/**
 * 提醒中心模板
 * 
 * @author panyh
 * @date Jun 23, 2020
 */
public class SysRemindTemplateAction extends ExtendAction {

	private ISysRemindTemplateService sysRemindTemplateService;

	@Override
	protected ISysRemindTemplateService getServiceImp(HttpServletRequest request) {
		if (sysRemindTemplateService == null) {
			sysRemindTemplateService = (ISysRemindTemplateService) getBean("sysRemindTemplateService");
		}
		return sysRemindTemplateService;
	}

}
