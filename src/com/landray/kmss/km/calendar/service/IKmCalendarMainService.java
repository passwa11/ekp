package com.landray.kmss.km.calendar.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.calendar.forms.KmCalendarMainForm;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.km.calendar.model.KmCalendarMainGroup;
import com.landray.kmss.km.calendar.model.KmCalendarPersonGroup;
import com.landray.kmss.km.calendar.util.CalendarQueryContext;
import com.landray.kmss.sys.agenda.interfaces.CommonCal;
import com.landray.kmss.sys.agenda.interfaces.IAgendaSyncroService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 日程管理主文档业务对象接口
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public interface IKmCalendarMainService extends IBaseService,
		IAgendaSyncroService {

	@SuppressWarnings("rawtypes")
	public abstract List getDataList(RequestContext requestInfo)
			throws Exception;

	/**
	 * 返回JSON数据
	 */
	public JSONArray getData(RequestContext requestContext) throws Exception;

	/**
	 * 从接出表查找指定日程
	 */
	public KmCalendarMain findCalendarByCacheOut(String appKey, String appUuid)
			throws Exception;

	/**
	 * 未知接口
	 */
	public String addSelf(IBaseModel modelObj) throws Exception;
	
	public void updateSelf(IBaseModel modelObj) throws Exception;

	public void deleteSelf(IBaseModel modelObj) throws Exception;

	/**
	 * 将来自指定APP的
	 */
	public String addByAppKey(IBaseModel modelObj, String appKey) throws Exception;
	public String addByAppKey(IBaseModel modelObj, String appKey,CommonCal cal) throws Exception;

	public String addByAppKey(IBaseModel modelObj, String appKey,CommonCal cal,boolean updateOutCache) throws Exception;


	public void updateByAppKey(IBaseModel modelObj, String appKey) throws Exception;

	public void deleteByAppKey(IBaseModel modelObj, String appKey) throws Exception;

	/**
	 * 查找指定类型(标签)的日程
	 */
	public List<KmCalendarMain> findCalendarsByLabel(String labelId)
			throws Exception;

	/**
	 * 将指定类型标签的日程置为默认日程
	 */
	public void clearCalendarLabel(String labelId) throws Exception;

	/**
	 * 批量将指定类型标签的日程置为默认日程(即将日程的标签置为默认`我的日历`)
	 */
	public void updateBatchClearCalendarLabel(List<String> labels) throws Exception;

	public List<KmCalendarMain> getCalendarByRefererId(String refererId,
			String userId)
			throws Exception;

	/**
	 * 根据查询条件获取日程总数
	 */
	public int getRangeCalendarsCount(CalendarQueryContext context);

	/**
	 * 根据查询条件获取日程
	 */
	public List<KmCalendarMain> getRangeCalendars(CalendarQueryContext context);

	public List<KmCalendarMain> getRangeCalendars(Date rangeStart,
			Date rangeEnd, String calType, boolean includeRecurrence,
			String personIds, String labelIds);

	/**
	 * 根据查询条件获取历史日程<br>
	 * 历史日程：日程的开始时间在指定日期以后
	 */
	public List<KmCalendarMain>
			getHistoryCalendars(CalendarQueryContext context);
	/**
	 * 根据查询条件获取历史日程<br>
	 * 历史日程：日程的开始时间在指定日期以后
	 */
	public List<KmCalendarMain> getHistoryCalendars(Date queryDate,
			String calType, boolean includeRecurrence, String personIds,
			String labelIds);

	/**
	 * 根据查询条件获取重复日程
	 */
	public List<KmCalendarMain> getRecurrenceCalendars(CalendarQueryContext context);

	public List<KmCalendarMain> getRecurrenceCalendars(Date rangeStart,
			Date rangeEnd, String personIds, String labelIds);

	/**
	 * 获取群组日历
	 *
	 * @param rangeStart
	 * @param rangeEnd
	 * @param personGroupId
	 * @return
	 */
	public List<KmCalendarMain> getGroupCalendars(Date rangeStart, Date rangeEnd, String personGroupId)
			throws Exception;

	/**
	 * 删除提醒定时任务
	 */
	public void deleteScheduler(KmCalendarMain kmCalendarMain) throws Exception;

	/**
	 * 发送消息待办
	 */
	public void notify(SysQuartzJobContext sysQuartzJobContext)
			throws Exception;

	public void setRecurrenceLastDate(KmCalendarMain kmCalendarMain)
			throws ParseException;

	public void addToread2Owner(KmCalendarMain kmCalendarMain)
			throws Exception;

	public KmCalendarMain findCalendar(String modelName, String docId,
			String personId) throws Exception;

	public List<KmCalendarMain> findCalendars(String modelName, String docId,
			List<String> ownerIdList) throws Exception;

	public void updateKmCalendarMain(CommonCal cal,
			KmCalendarMain kmCalendarMain) throws Exception;

	public void updateGroupEvent(KmCalendarMainForm kmCalendarMainForm,
			KmCalendarMainGroup mainGroup, RequestContext requestContext)
			throws Exception;

	public void deletePersonGroupEvent(
			KmCalendarPersonGroup kmCalendarPersonGroup,
			List<SysOrgElement> ownerList) throws Exception;

	public JSONArray getEventsByRange(RequestContext request) throws Exception;

	public JSONArray data(RequestContext request) throws Exception;

	/**
	 * 同步参数到kk
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONObject getKKConfig() throws Exception;

	/**
	 * 检查提醒时间
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONObject checkRemind(String fdId);

	public void addCalendarDetails(KmCalendarMain kmCalendarMain) throws Exception;

	public void deleteCalendarDetails(KmCalendarMain kmCalendarMain) throws Exception;

	/**
	 * 用于钉钉取消日程删除ekp日程
	 * @param modelObj
	 * @throws Exception
	 */
	void deleteCalendar(IBaseModel modelObj) throws Exception;
	public List<String> getRelatedPersonCal(String inStr);
}
