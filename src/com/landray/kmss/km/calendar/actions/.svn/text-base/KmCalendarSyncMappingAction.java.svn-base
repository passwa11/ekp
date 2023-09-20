package com.landray.kmss.km.calendar.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.calendar.service.IKmCalendarSyncMappingService;


/**
 * 同步映射关联 Action
 * 
 * @author 
 * @version 1.0 2013-10-14
 */
public class KmCalendarSyncMappingAction extends ExtendAction {
	protected IKmCalendarSyncMappingService kmCalendarSyncMappingService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if(kmCalendarSyncMappingService == null) {
            kmCalendarSyncMappingService = (IKmCalendarSyncMappingService)getBean("kmCalendarSyncMappingService");
        }
		return kmCalendarSyncMappingService;
	}
}

