package com.landray.kmss.sys.remind.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.sys.remind.service.ISysRemindTemplateRelationService;

/**
 * 提醒模板关系
 * 
 * @author panyh
 * @date Jun 30, 2020
 */
public class SysRemindTemplateRelationAction extends ExtendAction {

	private ISysRemindTemplateRelationService sysRemindTemplateRelationService;

	@Override
	protected ISysRemindTemplateRelationService getServiceImp(HttpServletRequest request) {
		if (sysRemindTemplateRelationService == null) {
			sysRemindTemplateRelationService = (ISysRemindTemplateRelationService) getBean(
					"sysRemindTemplateRelationService");
		}
		return sysRemindTemplateRelationService;
	}

}
