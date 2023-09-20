package com.landray.kmss.km.calendar.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.calendar.service.IKmCalendarOutCacheService;

/**
 * 日程接出缓存 Action
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public class KmCalendarOutCacheAction extends ExtendAction {
	protected IKmCalendarOutCacheService kmCalendarOutCacheService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmCalendarOutCacheService == null) {
            kmCalendarOutCacheService = (IKmCalendarOutCacheService) getBean("kmCalendarOutCacheService");
        }
		return kmCalendarOutCacheService;
	}

	public ActionForward get(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// String modelName =request.getParameter("modelName");
		((IKmCalendarOutCacheService) getServiceImp(request))
				.findByCalendarIdAndAppKey("", "");

		return null;
	}
}
