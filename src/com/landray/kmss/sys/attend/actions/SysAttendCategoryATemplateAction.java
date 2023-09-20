package com.landray.kmss.sys.attend.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryATemplateService;
import com.landray.kmss.sys.simplecategory.actions.SysSimpleCategoryAction;

public class SysAttendCategoryATemplateAction extends SysSimpleCategoryAction {

	protected ISysAttendCategoryATemplateService sysAttendCategoryATemplateService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysAttendCategoryATemplateService == null) {
			sysAttendCategoryATemplateService = (ISysAttendCategoryATemplateService) getBean(
					"sysAttendCategoryATemplateService");
		}
		return sysAttendCategoryATemplateService;
	}

}
