package com.landray.kmss.sys.time.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.time.service.ISysTimeWorkTimeService;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽
 */
public class SysTimeWorkTimeAction extends ExtendAction {
	protected ISysTimeWorkTimeService sysTimeWorkTimeService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysTimeWorkTimeService == null) {
			sysTimeWorkTimeService = (ISysTimeWorkTimeService) getBean("sysTimeWorkTimeService");
		}
		return sysTimeWorkTimeService;
	}
}
