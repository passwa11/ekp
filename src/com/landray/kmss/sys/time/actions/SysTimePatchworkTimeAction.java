package com.landray.kmss.sys.time.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.time.service.ISysTimePatchworkTimeService;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽
 */
public class SysTimePatchworkTimeAction extends ExtendAction {
	protected ISysTimePatchworkTimeService sysTimePatchworkTimeService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysTimePatchworkTimeService == null) {
			sysTimePatchworkTimeService = (ISysTimePatchworkTimeService) getBean("sysTimePatchworkTimeService");
		}
		return sysTimePatchworkTimeService;
	}
}
