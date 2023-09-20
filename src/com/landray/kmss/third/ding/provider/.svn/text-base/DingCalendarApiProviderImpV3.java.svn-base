package com.landray.kmss.third.ding.provider;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.calendar.cms.interfaces.SyncroCommonCal;
import com.landray.kmss.sys.notify.model.SysNotifyRemindMain;
import com.landray.kmss.sys.notify.service.ISysNotifyRemindMainService;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.dto.DingCalendarParam;
import com.landray.kmss.third.ding.model.ThirdDingCalendar;
import com.landray.kmss.third.ding.model.ThirdDingCalendarLog;
import com.landray.kmss.third.ding.model.api.DingCalendars;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingCalendarLogService;
import com.landray.kmss.third.ding.service.IThirdDingCalendarService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.redis.RedisTemplateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

public class DingCalendarApiProviderImpV3 implements IDingCalendarApiProvider {

	private static final Logger logger = LoggerFactory.getLogger(DingCalendarApiProviderImpV3.class);

	private ISysNotifyRemindMainService sysNotifyRemindMainService = null;

	public static final String SYN_TO_DING= "1";
	public static final String SYN_FROM_DING= "2";
	public static final String OPT_ADD= "add";
	public static final String OPT_UPDATE= "update";
	public static final String OPT_DELETE= "delete";
	public static final String PREFIX_V3_API= "$V3API$";

	public ISysNotifyRemindMainService getSysNotifyRemindMainService() {
		if (sysNotifyRemindMainService == null) {
			sysNotifyRemindMainService = (ISysNotifyRemindMainService) SpringBeanUtil
					.getBean("sysNotifyRemindMainService");
		}
		return sysNotifyRemindMainService;
	}

	private IThirdDingCalendarService thirdDingCalendarService = null;

	public IThirdDingCalendarService getThirdDingCalendarService() {
		if (thirdDingCalendarService == null) {
			thirdDingCalendarService = (IThirdDingCalendarService) SpringBeanUtil.getBean("thirdDingCalendarService");
		}
		return thirdDingCalendarService;
	}

	private IThirdDingCalendarLogService thirdDingCalendarLogService = null;

	public IThirdDingCalendarLogService getThirdDingCalendarLogService() {
		if (thirdDingCalendarLogService == null) {
			thirdDingCalendarLogService = (IThirdDingCalendarLogService) SpringBeanUtil
					.getBean("thirdDingCalendarLogService");
		}
		return thirdDingCalendarLogService;
	}

	private IOmsRelationService omsRelationService = null;

	public IOmsRelationService getOmsRelationService() {
		if (omsRelationService == null) {
			omsRelationService = (IOmsRelationService) SpringBeanUtil.getBean("omsRelationService");
		}
		return omsRelationService;
	}

	@Override
	public String addCalendar(String token, String personId, SyncroCommonCal cal)
			throws Exception {

		logger.info("-------新增日程-----------");
		//日志记录
		ThirdDingCalendarLog calendarLog = new ThirdDingCalendarLog();
		calendarLog.setFdSynWay(SYN_TO_DING);
		DingCalendars calendars = new DingCalendars();

		//标题
		calendarLog.setFdName(cal.getSubject());
		calendars.setSummary(cal.getSubject());

		//时间
		Date start = cal.getEventStartTime();
		Date end = cal.getEventFinishTime();
		//是否全天
		if (cal.isAllDayEvent()) {
			calendars.setIsAllDay(true);
			calendars.setEnd(new DingCalendars.DateAssist(DateUtil.convertDateToString(DateUtil.getNextDay(end,1), "yyyy-MM-dd"),null,null));
			calendars.setStart(new DingCalendars.DateAssist(DateUtil.convertDateToString(start, "yyyy-MM-dd"),null,null));
		} else {
			calendars.setIsAllDay(false);

			String startDateTime = DateUtil.convertDateToString(start,"yyyy-MM-dd'T'HH:mm:ssXXX");
			if(StringUtil.isNull(startDateTime)){
				return null;
			}

			String endDateTime = DateUtil.convertDateToString(end,"yyyy-MM-dd'T'HH:mm:ssXXX");
			logger.debug("----------开始时间："+startDateTime);
			logger.debug("----------结束时间："+endDateTime);
			if(startDateTime.equals(endDateTime)){
				logger.warn("-----开始和结束时间相同,默认结束时间延迟1分钟------"+cal.getSubject());
				Calendar cal_temp = Calendar.getInstance();
				cal_temp.setTime(end);
				cal_temp.add(Calendar.MINUTE, 1);
				endDateTime = DateUtil.convertDateToString(cal_temp.getTime(),"yyyy-MM-dd'T'HH:mm:ssXXX");
			}
			calendars.setEnd(new DingCalendars.DateAssist(null,endDateTime,"Asia/Shanghai"));
			calendars.setStart(new DingCalendars.DateAssist(null,startDateTime,"Asia/Shanghai"));

		}

		//unionId
		if (StringUtil.isNull(personId)) {
            personId = cal.getPersonId();
        }
		String unionId= DingUtil.getUnionIdByEkpId(personId);
        if(StringUtil.isNull(unionId)){
			calendarLog.setFdErrorMsg("创建人："+personId+"的对照表记录异常(不存在或者unionId为空)，请检查");
			getThirdDingCalendarLogService().add(calendarLog);
			return null;
		}

		// 获取日程提醒时间
		Integer reminderTime = getReminderTime(cal);
		if (reminderTime != null) {
			List <DingCalendars.RemindersAssist> rList = new ArrayList<DingCalendars.RemindersAssist>();
			rList.add(new DingCalendars.RemindersAssist("dingtalk",reminderTime));
			calendars.setReminders(rList);
		}

        //描述
		calendars.setDescription(cal.getFdDecs());

        //地点
		if (StringUtil.isNotNull(cal.getEventLocation())) {
			calendars.setLocation(new DingCalendars.LocationAssist(cal.getEventLocation()));
		}

		//参与人
		List<DingCalendars.UserAssist> userList = new ArrayList<DingCalendars.UserAssist>();
		userList.add(new DingCalendars.UserAssist(unionId));

		List<String> fdIds = cal.getRelatedPersonIds();
		if(fdIds!=null&&fdIds.size()>0){
			for(String id:fdIds){
				String uId = DingUtil.getUnionIdByEkpId(id);
				if(StringUtil.isNotNull(uId)){
					userList.add(new DingCalendars.UserAssist(uId));
				}else{
					logger.warn("参与人："+id+" 的unionId为空，请检查对照表");
				}
			}
		}

		calendars.setAttendees(userList);

		calendarLog.setFdEkpCalendarId(cal.getCalendarId());

		calendarLog.setFdReqStartTime(new Date(System.currentTimeMillis()));
		calendarLog.setFdApiUrl(DingConstant.DING_API_PREFIX+"/v1.0/calendar/users/"+unionId+"/calendars/primary/events");
		calendarLog.setFdReqParam(JSON.toJSONString(calendars));
		calendarLog.setFdOptType(OPT_ADD);
		JSONObject result = null;
		String errMsg=null;
		try {
			result = DingUtils.getDingApiService().addDingCalendars(unionId,calendars);
		} catch (Exception e) {
			errMsg=e.getMessage()+" \n";
			logger.error(e.getMessage(),e);
		}
		calendarLog.setFdResponseStartTime(new Date(System.currentTimeMillis()));
		String returnCalendarId = null;
        if(result != null && !result.isEmpty()){
        	calendarLog.setFdResult(result.toString());
        	if(result.containsKey("id")){
        		String ding_calendarId = result.getString("id");
				//设置一下调用的时间，回调时忽略
				RedisTemplateUtil.getInstance().set(ding_calendarId,System.currentTimeMillis(),20);
				calendarLog.setFdStatus(true);
				//需要对日程ID进行处理，以区分新旧接口
				returnCalendarId=PREFIX_V3_API+result.getString("id");
				calendarLog.setFdDingCalendarId(returnCalendarId);
			}else{
				calendarLog.setFdStatus(false);
				calendarLog.setFdErrorMsg(result.toString());
			}
		}else{
			calendarLog.setFdStatus(false);
			errMsg = StringUtil.isNotNull(errMsg)?errMsg:"接口返回为null,请查看后台日志信息";
			calendarLog.setFdErrorMsg(errMsg);
		}
		try {
			getThirdDingCalendarLogService().add(calendarLog);
		} catch (Exception e) {
			logger.error("日程新建日志失败："+e.getMessage(),e);
		}
        return returnCalendarId;
	}

	@Override
	public boolean delCalendar(String token, String personId, String uuid)
			throws Exception {

		logger.warn("删除日程："+uuid);
		if(uuid.startsWith(PREFIX_V3_API)){
			uuid = uuid.substring(7,uuid.length());
		}else{
			//旧接口
			logger.warn("旧接口删除日程："+uuid);
			IDingCalendarApiProvider provider = (IDingCalendarApiProvider) SpringBeanUtil
					.getBean("dingCalendarApiProviderImpV2");
			return provider.delCalendar(DingUtils.getDingApiService().getAccessToken(),DingUtil.getUseridByEkpId(personId),uuid);
		}
		boolean resultFlag = false;
		logger.info("uuid："+uuid);
		//日志记录
		ThirdDingCalendarLog calendarLog = new ThirdDingCalendarLog();
		calendarLog.setFdSynWay(SYN_TO_DING);
		calendarLog.setFdDingCalendarId(uuid);
		calendarLog.setFdOptType(OPT_DELETE);

		Object[] infoMap = getCalendarNameByUuid(uuid);
		if(infoMap!=null&&infoMap.length>0){
			calendarLog.setFdName((String) infoMap[0]);
			calendarLog.setFdEkpCalendarId((String) infoMap[1]);
		}

		//unionId
		String unionId= DingUtil.getUnionIdByEkpId(personId);
		calendarLog.setFdApiUrl(DingConstant.DING_API_PREFIX+"/v1.0/calendar/users/"+unionId+"/calendars/primary/events/"+uuid);
		if(StringUtil.isNull(unionId)){
			calendarLog.setFdErrorMsg("创建人："+personId+"的对照表记录异常(不存在或者unionId为空)，请检查");
			getThirdDingCalendarLogService().add(calendarLog);
			return false;
		}
		calendarLog.setFdReqStartTime(new Date());
		JSONObject result = DingUtils.getDingApiService().deleteDingCalendars(unionId,uuid);
		calendarLog.setFdResponseStartTime(new Date());
		if(result != null&&!result.isEmpty()){
			calendarLog.setFdResult(result.toString());
			if(result.containsKey("code")&&!"0".equals(result.getString("code"))) {
				//更新失败
				calendarLog.setFdStatus(false);
				calendarLog.setFdErrorMsg(result.toString());
			}else{
				calendarLog.setFdStatus(true);
				resultFlag=true;
			}
		}else{
			//更新失败
			calendarLog.setFdStatus(false);
			calendarLog.setFdErrorMsg("结果为空");
		}
		getThirdDingCalendarLogService().add(calendarLog);
		return false;
	}

	@Override
	public boolean updateCalElement(String token, String personId,
			String duserid,
			SyncroCommonCal cal) throws Exception {

		//dingCalId
		String uuid = cal.getUuid();
		logger.info("更新日程："+uuid);
		if(uuid.startsWith(PREFIX_V3_API)){
			uuid = uuid.substring(7,uuid.length());
		}else{
			//旧接口
			logger.warn("旧接口更新日程："+cal.getSubject());
			IDingCalendarApiProvider provider = (IDingCalendarApiProvider) SpringBeanUtil
					.getBean("dingCalendarApiProviderImpV2");
			if (StringUtil.isNull(personId)) {
                personId = cal.getPersonId();
            }
			return provider.updateCalElement(DingUtils.getDingApiService().getAccessToken(),personId,DingUtil.getUseridByEkpId(personId),cal);
		}
		logger.warn("更新日程 final："+uuid);
		boolean flag = false;

		//日志记录
		ThirdDingCalendarLog calendarLog = new ThirdDingCalendarLog();
		calendarLog.setFdSynWay(SYN_TO_DING);
		DingCalendars calendars = new DingCalendars();

		//标题
		calendarLog.setFdName(cal.getSubject());
		calendars.setSummary(cal.getSubject());
		calendars.setId(uuid);

		//时间
		Date start = cal.getEventStartTime();
		Date end = cal.getEventFinishTime();
		//是否全天
		if (cal.isAllDayEvent()) {
			calendars.setIsAllDay(true);
			calendars.setEnd(new DingCalendars.DateAssist(DateUtil.convertDateToString(DateUtil.getNextDay(end,1), "yyyy-MM-dd"),null,null));
			calendars.setStart(new DingCalendars.DateAssist(DateUtil.convertDateToString(start, "yyyy-MM-dd"),null,null));
		} else {
			calendars.setIsAllDay(false);

			String startDateTime = DateUtil.convertDateToString(start,"yyyy-MM-dd'T'HH:mm:ssXXX");
			String endDateTime = DateUtil.convertDateToString(end,"yyyy-MM-dd'T'HH:mm:ssXXX");
			logger.debug("----------开始时间："+startDateTime);
			logger.debug("----------结束时间："+endDateTime);
			if(startDateTime.equals(endDateTime)){
				logger.warn("-----开始和结束时间相同,默认结束时间延迟1分钟------"+cal.getSubject());
				Calendar cal_temp = Calendar.getInstance();
				cal_temp.setTime(end);
				cal_temp.add(Calendar.MINUTE, 1);
				endDateTime = DateUtil.convertDateToString(cal_temp.getTime(),"yyyy-MM-dd'T'HH:mm:ssXXX");
			}
			calendars.setEnd(new DingCalendars.DateAssist(null,endDateTime,"Asia/Shanghai"));
			calendars.setStart(new DingCalendars.DateAssist(null,startDateTime,"Asia/Shanghai"));

		}

		//unionId
		if (StringUtil.isNull(personId)) {
            personId = cal.getPersonId();
        }
		String unionId= DingUtil.getUnionIdByEkpId(personId);
		if(StringUtil.isNull(unionId)){
			calendarLog.setFdErrorMsg("创建人："+personId+"的对照表记录异常(不存在或者unionId为空)，请检查");
			getThirdDingCalendarLogService().add(calendarLog);
			return false;
		}

		//描述
		calendars.setDescription(cal.getFdDecs());

		// 获取日程提醒时间
		Integer reminderTime = getReminderTime(cal);
		if (reminderTime != null) {
			List <DingCalendars.RemindersAssist> rList = new ArrayList<DingCalendars.RemindersAssist>();
			rList.add(new DingCalendars.RemindersAssist("dingtalk",reminderTime));
			calendars.setReminders(rList);
		}

		//地点
		if (StringUtil.isNotNull(cal.getEventLocation())) {
			calendars.setLocation(new DingCalendars.LocationAssist(cal.getEventLocation()));
		}

		//参与人
		List<DingCalendars.UserAssist> userList = new ArrayList<DingCalendars.UserAssist>();
		userList.add(new DingCalendars.UserAssist(unionId));

		List<String> fdIds = cal.getRelatedPersonIds();
		if(fdIds!=null&&fdIds.size()>0){
			for(String id:fdIds){
				String uId = DingUtil.getUnionIdByEkpId(id);
				if(StringUtil.isNotNull(uId)){
					userList.add(new DingCalendars.UserAssist(uId));
				}else{
					logger.warn("参与人："+id+" 的unionId为空，请检查对照表");
				}
			}
		}else {
			logger.warn("---------------日程无相关人--------------");
		}
		//获取钉钉日程
		JSONObject dingCalendar = DingUtils.getDingApiService().getDingCalendars(unionId, uuid);
		if(dingCalendar!=null&&dingCalendar.containsKey("id")){
			//参与人
			JSONArray attendees = dingCalendar.getJSONArray("attendees");
			for(int i=0;i<attendees.size();i++){
				String _unionId = attendees.getJSONObject(i).getString("id");
				logger.debug("-----_unionId:"+_unionId);
				if(!DingUtil.isExistUnionid(_unionId)){
					//保留外部人员
					userList.add(new DingCalendars.UserAssist(_unionId));
					logger.debug("外部参与人员保留：{}",attendees.getJSONObject(i));
				}
			}
		}
		calendars.setAttendees(userList);

		calendarLog.setFdEkpCalendarId(cal.getCalendarId());

		calendarLog.setFdReqStartTime(new Date(System.currentTimeMillis()));
		calendarLog.setFdApiUrl(DingConstant.DING_API_PREFIX+"/v1.0/calendar/users/"+unionId+"/calendars/primary/events/"+uuid);
		calendarLog.setFdReqParam(JSON.toJSONString(calendars));
		calendarLog.setFdOptType(OPT_UPDATE);
		JSONObject result = null;
		String errMsg=null;
		try {
			result = DingUtils.getDingApiService().updateDingCalendars(unionId,uuid,calendars);
		} catch (Exception e) {
			errMsg=e.getMessage()+" \n";
			logger.error(e.getMessage(),e);
		}
		calendarLog.setFdResponseStartTime(new Date(System.currentTimeMillis()));
		String returnCalendarId = null;
		if(result != null && !result.isEmpty()){
			calendarLog.setFdResult(result.toString());
			if(result.containsKey("id")){
				String ding_calendarId = result.getString("id");
				//设置一下调用的时间，回调时忽略
				//RedisTemplateUtil.getInstance().sAdd(ding_calendarId,System.currentTimeMillis());
				RedisTemplateUtil.getInstance().set(ding_calendarId,System.currentTimeMillis(),20);

				calendarLog.setFdDingCalendarId(ding_calendarId);
				calendarLog.setFdStatus(true);
				flag = true;
			}else{
				calendarLog.setFdStatus(false);
				calendarLog.setFdErrorMsg(result.toString());
			}
		}else{
			calendarLog.setFdStatus(false);
			errMsg = StringUtil.isNotNull(errMsg)?errMsg:"接口返回为null,请查看后台日志信息";
			calendarLog.setFdErrorMsg(errMsg);
		}
		try {
			getThirdDingCalendarLogService().add(calendarLog);
		} catch (Exception e) {
			logger.error("日程新建日志失败："+e.getMessage(),e);
		}
		return flag;
	}



	@Override
	public List<SyncroCommonCal> getAddedCalElements(String personId, Date date) throws Exception {
		return CalendarList(personId, "confirmed");
	}

	@Override
	public List<SyncroCommonCal> getDeletedCalElements(String personId, Date date) throws Exception {

		return CalendarList(personId, "cancelled");
	}

	/**	日程列表
	 * @param personId  用户id
	 * @return
	 * @throws Exception
	 */
	private List<SyncroCommonCal> CalendarList(String personId, String status) throws Exception {
		List<SyncroCommonCal> dingAllCals = new ArrayList<>();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("thirdDingCalendar.fdPersonId=:fdPersonId");
		hqlInfo.setParameter("fdPersonId", personId);
		ThirdDingCalendar thirdDingCalendar = (ThirdDingCalendar) getThirdDingCalendarService().findFirstOne(hqlInfo);
		String fdSyncToken = null;
		if (thirdDingCalendar != null) {
			//更新
			fdSyncToken = thirdDingCalendar.getFdSyncToken();
		}
		DingCalendarParam dingCalendarParam = new DingCalendarParam();
		//dingCalendarParam.setMaxResults(1L);
		dingCalendarParam.setSyncToken(fdSyncToken);
		if (StringUtil.isNull(fdSyncToken)) {
			//最新时间设置成前一个月
			dingCalendarParam.setTimeMin(DateUtil.convertDateToString(DateUtil.getDate(-30), "yyyy-MM-dd'T'HH:mm:ss'Z'"));
		}
		if ("cancelled".equals(status)) {
			dingCalendarParam.setShowDeleted(true);
		}
		//获取全部日程数据
		JSONObject calendarsLists = DingUtils.getDingApiService().getDingCalendarList(getUnionId(personId), dingCalendarParam);
		logger.debug("日程列表{}", calendarsLists.toString());

		if (!calendarsLists.has("events")) {
			logger.info("fdId为:{}的用户中没有日程数据,fdSyncToken的值:{}", personId, fdSyncToken);
			return dingAllCals;
		}
		JSONArray events = calendarsLists.getJSONArray("events");
		for (Object event : events) {
			SyncroCommonCal syncroCommonCal = new SyncroCommonCal();
			//拿到每个日程对象
			JSONObject eventsObject = (JSONObject) event;

			//过滤循环日程
			if(eventsObject.containsKey("recurrence") || (eventsObject.containsKey("seriesMasterId") && StringUtil.isNotNull(eventsObject.getString("seriesMasterId")))){
				logger.debug("过滤重复日程：{} ，id:{}",eventsObject.getString("summary"),eventsObject.getString("id"));
                continue;
			}

			if (status.equals(eventsObject.getString("status"))) {
				//获取日程参与人
				JSONArray attendees = eventsObject.getJSONArray("attendees");
				List<String> dingRelatedPersonIds = new ArrayList<>();
				for (Object attendee : attendees) {
					JSONObject attendeesObject = (JSONObject) attendee;
					String id = attendeesObject.getString("id");
					//还要排除自身（因为钉钉返回的日程参与人总是包含创建者，而时间管理的参与人不需要创建者）
					if (!id.equals(eventsObject.getJSONObject("organizer").getString("id"))) {
						//获取用户id
						String ekpIdByUnionid = DingUtil.getEkpIdByUnionid(id);
						if (StringUtil.isNotNull(ekpIdByUnionid)) {
							dingRelatedPersonIds.add(ekpIdByUnionid);
						}
					}
				}
				//设置日程参与人
				syncroCommonCal.setRelatedPersonIds(dingRelatedPersonIds);
				//获取日程组织人
				JSONObject organizer = eventsObject.getJSONObject("organizer");
				syncroCommonCal.setCreatorId(DingUtil.getEkpIdByUnionid(organizer.getString("id")));
				//设置标题
				syncroCommonCal.setSubject(eventsObject.getString("summary"));
				//设置描述
				if (eventsObject.has("description")) {
					syncroCommonCal.setFdDecs(eventsObject.getString("description"));
				}
				//设置日程地点
				if (eventsObject.has("location")) {
					JSONObject location = eventsObject.getJSONObject("location");
					if(location!=null && location.containsKey("displayName")){
						syncroCommonCal.setEventLocation(location.getString("displayName"));
					}
				}
				//设置日程id
				syncroCommonCal.setUuid(DingCalendarApiProviderImpV3.PREFIX_V3_API + eventsObject.getString("id"));
				if (eventsObject.getBoolean("isAllDay")) {
					//全天类型
					//设置开始时间
					syncroCommonCal.setEventStartTime(DateUtil.convertStringToDate(eventsObject.getJSONObject("start").getString("date"), "yyyy-MM-dd"));
					Date end = DateUtil.convertStringToDate(eventsObject.getJSONObject("end").getString("date"), "yyyy-MM-dd");
					//设置结束时间
					syncroCommonCal.setEventFinishTime(DateUtil.getNextDay(end, -1));
				} else {
					//设置开始时间
					syncroCommonCal.setEventStartTime(DateUtil.convertStringToDate(eventsObject.getJSONObject("start").getString("dateTime"), "yyyy-MM-dd'T'HH:mm:ssXXX"));
					//设置结束时间
					syncroCommonCal.setEventFinishTime(DateUtil.convertStringToDate(eventsObject.getJSONObject("end").getString("dateTime"), "yyyy-MM-dd'T'HH:mm:ssXXX"));
				}
				//是否设置全天
				syncroCommonCal.setAllDayEvent(eventsObject.getBoolean("isAllDay"));
				//设置日程类型
				syncroCommonCal.setCalType("event");
				//2021-07-22T08:46:14Z
				String updateTime = eventsObject.getString("updateTime");
				if (StringUtil.isNull(updateTime)) {
					updateTime = eventsObject.getString("createTime");
				}

				Date update = new Date();
				if (StringUtil.isNotNull(updateTime)) {
					update = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(updateTime);
				}
				syncroCommonCal.setUpdateTime(update);
				//先这样写 因为新增日程的时间和删除的时间不一致
				if ("cancelled".equals(status)) {
					syncroCommonCal.setUpdateTime(new Date());
				}

				dingAllCals.add(syncroCommonCal);
			}
		}

		//记录日程信息
		if (thirdDingCalendar != null) {
			//更新
			thirdDingCalendar.setFdSyncToken(calendarsLists.getString("syncToken"));
			getThirdDingCalendarService().update(thirdDingCalendar);
		} else {
			//增加
			thirdDingCalendar = new ThirdDingCalendar();
			thirdDingCalendar.setFdPersonId(personId);
			thirdDingCalendar.setFdSyncToken(calendarsLists.getString("syncToken"));
			getThirdDingCalendarService().add(thirdDingCalendar);
		}


		return dingAllCals;
	}

	private String getUnionId(String personId){

		String unionId = DingUtil.getUnionIdByEkpId(personId);
		if (StringUtil.isNull(unionId)) {
			logger.debug("创建人:{}中unionId不存在，请检查人员对照表", personId);
			return null;
		}
		return unionId;
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
		//String sql = "SELECT cal.doc_subject,cal.fd_id FROM `km_calendar_main` cal,km_calendar_sync_mapping map where cal.fd_id = map.fd_calendar_id AND map.fd_app_uuid = '"+uuid+"'";
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
