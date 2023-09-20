package com.landray.kmss.sys.attend.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryTSheetService;

/**
 *
 * @author
 * @version 1.0 2018-06-12
 */
public class SysAttendCategoryTSheetAction extends ExtendAction {

	private ISysAttendCategoryTSheetService sysAttendCategoryTSheetService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysAttendCategoryTSheetService == null) {
			sysAttendCategoryTSheetService = (ISysAttendCategoryTSheetService) getBean(
					"sysAttendCategoryTSheetService");
		}
		return sysAttendCategoryTSheetService;
	}

}
