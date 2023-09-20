package com.landray.kmss.km.calendar.service;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.calendar.model.KmCalendarRequestAuth;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

public interface IKmCalendarRequestAuthService extends IBaseService {

	public KmCalendarRequestAuth findByCreateId(String createId)
			throws Exception;

	public void saveSendRequestNotify(
			KmCalendarRequestAuth kmCalendarRequestAuth, List notifyTarget)
			throws Exception;

	public void saveSendRequestNoNotify(
			KmCalendarRequestAuth kmCalendarRequestAuth, SysOrgPerson requestee,
			List notifyTarget)
			throws Exception;

	public void saveSendRequestYesNotify(
			KmCalendarRequestAuth kmCalendarRequestAuth, SysOrgPerson requestee,
			List notifyTarget, RequestContext requestContext) throws Exception;
}
