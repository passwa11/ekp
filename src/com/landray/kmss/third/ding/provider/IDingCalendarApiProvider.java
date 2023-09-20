package com.landray.kmss.third.ding.provider;

import com.landray.kmss.km.calendar.cms.interfaces.SyncroCommonCal;

import java.util.Date;
import java.util.List;

public interface IDingCalendarApiProvider {

	public String addCalendar(String token, String duserid, SyncroCommonCal cal)
			throws Exception;

	public boolean delCalendar(String token, String duserid, String uuid)
			throws Exception;

	public boolean updateCalElement(String token, String personId,
			String duserid,
			SyncroCommonCal cal) throws Exception;


	public List<SyncroCommonCal> getAddedCalElements(String personId, Date date) throws Exception;

	public List<SyncroCommonCal> getDeletedCalElements(String personId, Date date) throws Exception;

}
