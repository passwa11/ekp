package com.landray.kmss.third.ding.provider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.km.calendar.service.IKmCalendarSyncMappingService;
import com.landray.kmss.third.ding.model.ThirdDingCalendarLog;
import com.landray.kmss.third.ding.model.api.DingCalendars;
import com.landray.kmss.third.ding.service.IThirdDingCalendarLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dingtalk.api.request.OapiCalendarV2EventCancelRequest;
import com.dingtalk.api.request.OapiCalendarV2EventCreateRequest;
import com.dingtalk.api.request.OapiCalendarV2EventCreateRequest.Attendee;
import com.dingtalk.api.request.OapiCalendarV2EventCreateRequest.DateTime;
import com.dingtalk.api.request.OapiCalendarV2EventCreateRequest.Event;
import com.dingtalk.api.request.OapiCalendarV2EventCreateRequest.LocationVo;
import com.dingtalk.api.request.OapiCalendarV2EventCreateRequest.OpenCalendarReminderVo;
import com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest;
import com.dingtalk.api.response.OapiCalendarV2EventCancelResponse;
import com.dingtalk.api.response.OapiCalendarV2EventCreateResponse;
import com.dingtalk.api.response.OapiCalendarV2EventUpdateResponse;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.km.calendar.cms.interfaces.SyncroCommonCal;
import com.landray.kmss.sys.notify.model.SysNotifyRemindMain;
import com.landray.kmss.sys.notify.service.ISysNotifyRemindMainService;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class DingCalendarApiProviderImpV2 implements IDingCalendarApiProvider {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingCalendarApiProviderImpV2.class);

	public static final String SYN_TO_DING= "1";
	public static final String SYN_FROM_DING= "2";
	public static final String OPT_ADD= "add";
	public static final String OPT_UPDATE= "update";
	public static final String OPT_DELETE= "delete";

	private ISysNotifyRemindMainService sysNotifyRemindMainService = null;

	public ISysNotifyRemindMainService getSysNotifyRemindMainService() {
		if (sysNotifyRemindMainService == null) {
			sysNotifyRemindMainService = (ISysNotifyRemindMainService) SpringBeanUtil
					.getBean("sysNotifyRemindMainService");
		}
		return sysNotifyRemindMainService;
	}

	private IThirdDingCalendarLogService thirdDingCalendarLogService = null;

	public IThirdDingCalendarLogService getThirdDingCalendarLogService() {
		if (thirdDingCalendarLogService == null) {
			thirdDingCalendarLogService = (IThirdDingCalendarLogService) SpringBeanUtil
					.getBean("thirdDingCalendarLogService");
		}
		return thirdDingCalendarLogService;
	}

	@Override
	public String addCalendar(String token, String duserid, SyncroCommonCal cal)
			throws Exception {

		//日志记录
		ThirdDingCalendarLog calendarLog = new ThirdDingCalendarLog();
		calendarLog.setFdSynWay(SYN_TO_DING);
		calendarLog.setFdOptType(OPT_ADD);
		calendarLog.setFdEkpCalendarId(cal.getCalendarId());
		calendarLog.setFdName(cal.getSubject());

		String url = DingConstant.DING_PREFIX
				+ "/topapi/calendar/v2/event/create"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		logger.debug("钉钉接口url:" + url);
		calendarLog.setFdApiUrl("【V2接口】"+url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);

		OapiCalendarV2EventCreateRequest req = new OapiCalendarV2EventCreateRequest();
		Event event = new Event();
		List<Attendee> attendees = new ArrayList<Attendee>();
		Attendee attendee = new Attendee();
		attendee.setUserid(duserid);
		attendees.add(attendee);
		List<String> fdIds = cal.getRelatedPersonIds();
		if(fdIds!=null&&fdIds.size()>0){
			for(String id:fdIds){
				String attendeeUserid = DingUtil.getUseridByEkpId(id);
				if(StringUtil.isNotNull(attendeeUserid)){
					Attendee tempAttendee = new Attendee();
					tempAttendee.setUserid(attendeeUserid);
					attendees.add(tempAttendee);
				}else{
					logger.warn("参与人："+id+" 的userid为空，请检查对照表");
				}
			}
		}

		event.setAttendees(attendees);
		event.setOrganizer(attendee);

		event.setCalendarId("primary");
		event.setDescription(cal.getContent());

		if (cal.isAllDayEvent()) {
			Date start = cal.getEventStartTime();
			Date end = cal.getEventFinishTime();
			Calendar c = Calendar.getInstance();
			c.setTime(end);
			c.add(Calendar.DATE, 1);
			end = c.getTime();
			DateTime endDate = new DateTime();
			endDate.setDate(DateUtil.convertDateToString(end, "yyyy-MM-dd"));
			endDate.setTimezone("Asia/Shanghai");
			event.setEnd(endDate);
			DateTime startDate = new DateTime();
			startDate
					.setDate(DateUtil.convertDateToString(start, "yyyy-MM-dd"));
			startDate.setTimezone("Asia/Shanghai");
			event.setStart(startDate);
		} else {
			Date start = cal.getEventStartTime();
			Date end = cal.getEventFinishTime();
			DateTime endDate = new DateTime();
			endDate.setTimestamp(end.getTime() / 1000L);
			endDate.setTimezone("Asia/Shanghai");
			event.setEnd(endDate);
			DateTime startDate = new DateTime();
			startDate.setTimestamp(start.getTime() / 1000L);
			startDate.setTimezone("Asia/Shanghai");
			event.setStart(startDate);
		}

		event.setSummary(cal.getSubject());
		event.setDescription(cal.getFdDecs());

		if (StringUtil.isNotNull(cal.getEventLocation())) {
			LocationVo location = new LocationVo();
			location.setPlace(cal.getEventLocation());
			event.setLocation(location);
		}
		String agentId = DingConfig.newInstance().getDingAgentid();
		event.setNotificationType("NONE");
		req.setEvent(event);
		req.setAgentid(Long.parseLong(agentId));

		// 获取日程提醒时间
		Integer reminderTime = getReminderTime(cal);
		if (reminderTime != null) {
			OpenCalendarReminderVo remainder = new OpenCalendarReminderVo();
			remainder.setMethod("app");
			remainder.setMinutes(reminderTime.longValue());
			event.setReminder(remainder);
		}

		calendarLog.setFdReqParam(JSON.toJSONString(req));
		calendarLog.setFdReqStartTime(new Date());
		OapiCalendarV2EventCreateResponse response = client.execute(req,
				token);
		calendarLog.setFdResponseStartTime(new Date());
		logger.debug("新增钉钉日程：" + response.getBody());
		calendarLog.setFdResult(response.getBody());
		String ding_calendarId = null;
		if (response.getErrcode() == 0) {
			calendarLog.setFdStatus(true);
			calendarLog.setFdDingCalendarId(response.getResult().getEventId());
			ding_calendarId = response.getResult().getEventId();
		} else {
			calendarLog.setFdStatus(false);
			calendarLog.setFdErrorMsg("新增钉钉日程出错");
			logger.error("新增钉钉日程出错：" + response.getBody());
//			throw new KmssRuntimeException(new KmssMessage(
//					response.getBody()));
		}
		try {
			getThirdDingCalendarLogService().add(calendarLog);
			if(!calendarLog.getFdStatus()){
				throw new KmssRuntimeException(new KmssMessage(
						response.getBody()));
			}
		} catch (Exception e) {
			logger.error("保存日程日志失败："+e.getMessage(),e);
		}
		return ding_calendarId;
	}

	@Override
	public boolean delCalendar(String token, String duserid, String uuid)
			throws Exception {
		ThirdDingCalendarLog calendarLog = new ThirdDingCalendarLog();
		calendarLog.setFdSynWay(SYN_TO_DING);
		calendarLog.setFdOptType(OPT_DELETE);
		Object[] infoMap = getCalendarNameByUuid(uuid);
		if(infoMap!=null&&infoMap.length>0){
			calendarLog.setFdName((String) infoMap[0]);
			calendarLog.setFdEkpCalendarId((String) infoMap[1]);
		}
		calendarLog.setFdDingCalendarId(uuid);

		String rtn = null;
		String url = DingConstant.DING_PREFIX
				+ "/topapi/calendar/v2/event/cancel"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		logger.debug("钉钉接口url:" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiCalendarV2EventCancelRequest req = new OapiCalendarV2EventCancelRequest();
		req.setCalendarId("primary");
		req.setEventId(uuid);
		calendarLog.setFdReqStartTime(new Date());
		calendarLog.setFdReqParam(JSON.toJSONString(req));
		OapiCalendarV2EventCancelResponse rsp = client.execute(req, token);
		calendarLog.setFdApiUrl("【V2接口】"+url);
		calendarLog.setFdResponseStartTime(new Date());
		rtn = rsp.getBody();
		logger.debug("删除钉钉日程：" + rtn);
		calendarLog.setFdResult(rtn);
		boolean flag= true;
		if (rsp.getErrcode() == 0) {
			calendarLog.setFdStatus(true);
			//return true;
		} else {
			calendarLog.setFdStatus(false);
			calendarLog.setFdErrorMsg("删除钉钉日程出错：" + rtn);
			logger.error("删除钉钉日程出错：" + rtn);
			flag = false;
			// throw new KmssRuntimeException(new KmssMessage(rtn));
		}
		try {
			getThirdDingCalendarLogService().add(calendarLog);
		} catch (Exception e) {
			logger.error("保存日程日志失败："+e.getMessage(),e);
		}
		return flag;
	}

	@Override
	public boolean updateCalElement(String token, String personId,
			String duserid,
			SyncroCommonCal cal) throws Exception {

		ThirdDingCalendarLog calendarLog = new ThirdDingCalendarLog();
		calendarLog.setFdSynWay(SYN_TO_DING);
		calendarLog.setFdOptType(OPT_UPDATE);
		calendarLog.setFdEkpCalendarId(cal.getCalendarId());
		calendarLog.setFdDingCalendarId(cal.getUuid());
		calendarLog.setFdName(cal.getSubject());

		String url = DingConstant.DING_PREFIX
				+ "/topapi/calendar/v2/event/update"
				+ DingUtil.getDingAppKeyByEKPUserId("?", personId);
		logger.debug("钉钉接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);

		OapiCalendarV2EventUpdateRequest req = new OapiCalendarV2EventUpdateRequest();
		com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.Event event = new com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.Event();
		List<com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.Attendee> attendees = new ArrayList<com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.Attendee>();
		com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.Attendee attendee = new com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.Attendee();
		attendees.add(attendee);
		List<String> fdIds = cal.getRelatedPersonIds();
		if(fdIds!=null&&fdIds.size()>0){
			for(String id:fdIds){
				String attendeeUserid = DingUtil.getUseridByEkpId(id);
				if(StringUtil.isNotNull(attendeeUserid)){
					com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.Attendee tempAttendee = new com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.Attendee();
					tempAttendee.setUserid(attendeeUserid);
					tempAttendee.setAttendeeStatus("add");
					attendees.add(tempAttendee);
				}else{
					logger.warn("参与人："+id+" 的userid为空，请检查对照表");
				}
			}
		}

		attendee.setUserid(duserid);
		attendee.setAttendeeStatus("add");
		// event.setAttendees(attendees);
		event.setCalendarId("primary");
		event.setDescription(StringUtil.isNull(cal.getContent())
				? cal.getSubject() : cal.getContent());
		event.setOrganizer(attendee);
		event.setEventId(cal.getUuid());

		if (cal.isAllDayEvent()) {
			Date start = cal.getEventStartTime();
			Date end = cal.getEventFinishTime();
			Calendar c = Calendar.getInstance();
			c.setTime(end);
			c.add(Calendar.DATE, 1);
			end = c.getTime();
			com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.DateTime endDate = new com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.DateTime();
			endDate.setDate(DateUtil.convertDateToString(end, "yyyy-MM-dd"));
			endDate.setTimezone("Asia/Shanghai");
			event.setEnd(endDate);
			com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.DateTime startDate = new com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.DateTime();
			startDate
					.setDate(DateUtil.convertDateToString(start, "yyyy-MM-dd"));
			startDate.setTimezone("Asia/Shanghai");
			event.setStart(startDate);
		} else {
			Date start = cal.getEventStartTime();
			Date end = cal.getEventFinishTime();
			com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.DateTime endDate = new com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.DateTime();
			endDate.setTimestamp(end.getTime() / 1000L);
			endDate.setTimezone("Asia/Shanghai");
			event.setEnd(endDate);
			com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.DateTime startDate = new com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.DateTime();
			startDate.setTimestamp(start.getTime() / 1000L);
			startDate.setTimezone("Asia/Shanghai");
			event.setStart(startDate);
		}

		event.setSummary(cal.getSubject());
		event.setDescription(cal.getFdDecs());

		if (StringUtil.isNotNull(cal.getEventLocation())) {
			com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.LocationVo location = new com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.LocationVo();
			location.setPlace(cal.getEventLocation());
			event.setLocation(location);
		}

		req.setEvent(event);

		// 获取日程提醒时间
		Integer reminderTime = getReminderTime(cal);
		if (reminderTime != null) {
			com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.OpenCalendarReminderVo remainder = new com.dingtalk.api.request.OapiCalendarV2EventUpdateRequest.OpenCalendarReminderVo();
			remainder.setMethod("app");
			remainder.setMinutes(reminderTime.longValue());
			event.setReminder(remainder);
		}

		calendarLog.setFdApiUrl("【V2接口】"+url);
		calendarLog.setFdReqStartTime(new Date());
		OapiCalendarV2EventUpdateResponse response = client.execute(req,
				token);
		calendarLog.setFdResponseStartTime(new Date());
		calendarLog.setFdResult(response.getBody());
		calendarLog.setFdReqParam(JSON.toJSONString(req));
		logger.debug("更新钉钉日程：" + response.getBody());
		boolean flag =true;
		if (response.getErrcode() == 0) {
			calendarLog.setFdStatus(true);
			//return true;
		} else {
			calendarLog.setFdStatus(false);
			logger.error("更新钉钉日程出错：" + response.getBody());
			flag=false;
		}
		try {
			getThirdDingCalendarLogService().add(calendarLog);
		} catch (Exception e) {
			logger.error("保存日程日志失败："+e.getMessage(),e);
		}
		return flag;
	}



	@Override
	public List<SyncroCommonCal> getAddedCalElements(String personId, Date date) throws Exception {
		return null;
	}

	@Override
	public List<SyncroCommonCal> getDeletedCalElements(String personId, Date date) throws Exception {
		return null;
	}

	private Integer getReminderTime(SyncroCommonCal cal) throws Exception {
		List<SysNotifyRemindMain> list = getSysNotifyRemindMainService()
				.getSysNotifyRemindMainList(cal.getCalendarId());
		List<Integer> beforeTimeList = new ArrayList<>();
		if (!list.isEmpty() && list.size() > 0) {
			for (SysNotifyRemindMain sysNotifyRemindMain : list) {
				// ekp 存入的时间单位不一致 有 分 时 天 周 统一抓换成分
				int beforeTime = 0;
				if ("minute".equals(sysNotifyRemindMain.getFdTimeUnit())) {
					beforeTime = Integer
							.valueOf(sysNotifyRemindMain.getFdBeforeTime());
				} else if ("hour".equals(sysNotifyRemindMain.getFdTimeUnit())) {
					beforeTime = Integer.valueOf(
							sysNotifyRemindMain.getFdBeforeTime()) * 60;
				} else if ("day".equals(sysNotifyRemindMain.getFdTimeUnit())) {
					beforeTime = Integer.valueOf(
							sysNotifyRemindMain.getFdBeforeTime()) * 1440;
				} else if ("week".equals(sysNotifyRemindMain.getFdTimeUnit())) {
					beforeTime = Integer.valueOf(
							sysNotifyRemindMain.getFdBeforeTime()) * 10080;
				}
				beforeTimeList.add(beforeTime);
			}
			// 可能存在多条数据 取最小的一条 所以进行排序
			Collections.sort(beforeTimeList);
			Integer newbeforeTime = beforeTimeList.get(0);
			if (newbeforeTime > 1440) { // 大于一天按一天传
				newbeforeTime = 1440;
			}
			return newbeforeTime;
		}
		return null;
	}

	private Object[] getCalendarNameByUuid(String uuid){
	//	String sql = "SELECT cal.doc_subject,cal.fd_id FROM km_calendar_main cal,km_calendar_sync_mapping map where cal.fd_id = map.fd_calendar_id AND map.fd_app_uuid = '"+uuid+"'";
		String sql = "SELECT c.fd_name,c.fd_ekp_calendar_id FROM third_ding_calendar_log c WHERE fd_ding_calendar_id='"+uuid+"' AND fd_name !='' AND fd_name IS NOT NULL ORDER BY c.fd_req_start_time DESC";
		try {
			List<Object[]> list= getThirdDingCalendarLogService().getBaseDao().getHibernateSession().createSQLQuery(sql).list();
			if(list!=null&&list.size()>0){
				return list.get(0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
}
