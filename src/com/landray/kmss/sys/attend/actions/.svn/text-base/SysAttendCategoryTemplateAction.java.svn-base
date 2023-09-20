package com.landray.kmss.sys.attend.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryTemplateService;
import com.landray.kmss.sys.simplecategory.actions.SysSimpleCategoryAction;

public class SysAttendCategoryTemplateAction extends SysSimpleCategoryAction {

	protected ISysAttendCategoryTemplateService sysAttendCategoryTemplateService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysAttendCategoryTemplateService == null) {
			sysAttendCategoryTemplateService = (ISysAttendCategoryTemplateService) getBean(
					"sysAttendCategoryTemplateService");
		}
		return sysAttendCategoryTemplateService;
	}

}
