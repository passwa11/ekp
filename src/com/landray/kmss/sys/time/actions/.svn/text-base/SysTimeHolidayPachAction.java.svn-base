package com.landray.kmss.sys.time.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.time.service.ISysTimeHolidayPachService;

/**
 * 节假日补班 Action
 * 
 * @author chenl
 * @version 1.0 2017-11-15
 */
public class SysTimeHolidayPachAction extends ExtendAction {
	protected ISysTimeHolidayPachService sysTimeHolidayPachService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysTimeHolidayPachService == null) {
			sysTimeHolidayPachService = (ISysTimeHolidayPachService) getBean(
					"sysTimeHolidayPachService");
		}
		return sysTimeHolidayPachService;
	}
}
