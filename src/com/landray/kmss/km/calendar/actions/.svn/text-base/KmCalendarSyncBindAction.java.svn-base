package com.landray.kmss.km.calendar.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.km.calendar.service.IKmCalendarSyncBindService;

/**
 * 同步绑定信息 Action
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public class KmCalendarSyncBindAction extends ExtendAction {

	protected IKmCalendarSyncBindService kmCalendarSyncBindService;

	@Override
    protected IKmCalendarSyncBindService getServiceImp(
			HttpServletRequest request) {
		if (kmCalendarSyncBindService == null) {
			kmCalendarSyncBindService = (IKmCalendarSyncBindService) getBean("kmCalendarSyncBindService");
		}
		return kmCalendarSyncBindService;
	}

	public ActionForm getTimeStamp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// String appKey = request.getParameter("appKey");
		// SysOrgPerson user = UserUtil.getUser();
		Date date = getServiceImp(request).getSyncroDate(
				"1335d29c080e5ddf15f4d3a4d74add20", "evernote");
		// System.out.println(date);
		return null;
	}
}
