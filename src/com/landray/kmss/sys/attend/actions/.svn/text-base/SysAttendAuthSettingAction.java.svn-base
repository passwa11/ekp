package com.landray.kmss.sys.attend.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.sys.attend.service.ISysAttendAuthSettingService;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-08-28
 */
public class SysAttendAuthSettingAction extends ExtendAction {

	private ISysAttendAuthSettingService sysAttendAuthSettingService;

	@Override
	protected ISysAttendAuthSettingService
			getServiceImp(HttpServletRequest request) {
		if (sysAttendAuthSettingService == null) {
			sysAttendAuthSettingService = (ISysAttendAuthSettingService) getBean(
					"sysAttendAuthSettingService");
		}
		return sysAttendAuthSettingService;
	}

}
