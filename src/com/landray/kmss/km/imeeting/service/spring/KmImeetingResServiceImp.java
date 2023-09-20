package com.landray.kmss.km.imeeting.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysAuthConstant.AreaIsolation;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.forms.KmImeetingResForm;
import com.landray.kmss.km.imeeting.model.KmImeetingBook;
import com.landray.kmss.km.imeeting.model.KmImeetingInnerScreen;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingMapping;
import com.landray.kmss.km.imeeting.model.KmImeetingOuterScreen;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.km.imeeting.model.KmImeetingResCategory;
import com.landray.kmss.km.imeeting.model.KmImeetingUse;
import com.landray.kmss.km.imeeting.service.IKmImeetingBookService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMappingService;
import com.landray.kmss.km.imeeting.service.IKmImeetingResCategoryService;
import com.landray.kmss.km.imeeting.service.IKmImeetingResService;
import com.landray.kmss.km.imeeting.util.BoenUtil;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.authorization.util.SysAuthAreaHelper;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.HibernateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.RecurrenceUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会议室信息业务接口实现
 */
public class KmImeetingResServiceImp extends BaseServiceImp implements IKmImeetingResService {
	
	private IKmImeetingResCategoryService kmImeetingResCategoryService;
	
	private final SimpleDateFormat format2 = new SimpleDateFormat(
			"yyyy-MM-dd");
	
	private static final String[] weeks = { "SU", "MO", "TU", "WE", "TH", "FR",
	"SA" };
	
	public IKmImeetingResCategoryService getKmImeetingResCategoryService() {
		return kmImeetingResCategoryService;
	}

	public void setKmImeetingResCategoryService(IKmImeetingResCategoryService kmImeetingResCategoryService) {
		this.kmImeetingResCategoryService = kmImeetingResCategoryService;
	}

	private IKmImeetingMappingService kmImeetingMappingService;

	public void setKmImeetingMappingService(IKmImeetingMappingService kmImeetingMappingService) {
		this.kmImeetingMappingService = kmImeetingMappingService;
	}

	private IKmImeetingMainService kmImeetingMainService;


	public void setKmImeetingMainService(IKmImeetingMainService kmImeetingMainService) {
		this.kmImeetingMainService = kmImeetingMainService;
	}

	// 该资源是否冲突
	@Override
	public JSONObject isConflictRes(RequestContext request, String resIds) throws Exception {
		// long ____start = System.currentTimeMillis();
		//会议地点
		String[] resIdArr = resIds.split(";");
		JSONObject conflictResponse = new JSONObject();
		JSONArray conflictArr = new JSONArray();
		conflictResponse.put("result", false);
		String fdHoldDate = request.getParameter("fdHoldDate");
		String fdFinishDate = request.getParameter("fdFinishDate");
		//重复条件
		String recurrenceStr = request.getParameter("recurrenceStr");
		if(StringUtil.isNull(recurrenceStr)) {
			recurrenceStr = buildRecurrenceStr(fdHoldDate, request);
		}
		Date start = DateUtil.convertStringToDate(fdHoldDate, DateUtil.TYPE_DATETIME, request.getLocale());
		Date end = DateUtil.convertStringToDate(fdFinishDate, DateUtil.TYPE_DATETIME, request.getLocale());
		Date lastedDate = end;
		for (String resId : resIdArr) {
			// 取出所有占用资源的时间段
			ArrayList<Object[]> timeArray = new ArrayList<Object[]>();
			ArrayList<Object[]> imeetingTimeArray = new ArrayList<Object[]>();
			if (StringUtil.isNull(recurrenceStr)) {
				imeetingTimeArray.add(new Date[] { start, end });
			} else {
				conflictResponse.put("isCycle", true);
				lastedDate = RecurrenceUtil.getLastedExecuteDate(recurrenceStr, end);
				List<Date> dates = RecurrenceUtil.getExcuteDateList(recurrenceStr, start, start, lastedDate);
				for (Date date : dates) {
					imeetingTimeArray
							.add(new Date[] { date, new Date(date.getTime() + end.getTime() - start.getTime()) });

				}
			}
			//校验冲突的会议安排
			timeArray.addAll(findConflictTimeInMain(request, resId, start, lastedDate));
			//校验冲突的会议预约
			timeArray.addAll(findConflictTimeInBook(request, resId, start, lastedDate));
			Collections.sort(timeArray, new TimeComparator());
			for (Object[] times : timeArray) {
					//校验与周期性会议的首次召开时间一致 说明冲突
					if((start.getTime()<=((Date) times[0]).getTime()) && end.getTime() >= ((Date)times[1]).getTime()){
					KmImeetingRes res = (KmImeetingRes) this.findByPrimaryKey(resId);
					JSONObject obj = new JSONObject();
					obj.put("conflictId", res.getFdId());
					obj.put("conflictName", res.getFdName());
					obj.put("startDate", DateUtil.convertDateToString((Date) times[0], DateUtil.PATTERN_DATETIME));
					obj.put("endDate", DateUtil.convertDateToString((Date) times[1], DateUtil.PATTERN_DATETIME));
//					conflictArr.add(obj);
					}
				for (Object[] imeetingTimes : imeetingTimeArray) {
					// 结束时间小于等于 所创建会议的开始时间 表示没有冲突
					if (((Date) times[1]).getTime() <= ((Date) imeetingTimes[0]).getTime()) {
						continue;
					}
					// 开始时间大于等于 所创建会议的结束时间 表示没有冲突
					if (((Date) times[0]).getTime() >= ((Date) imeetingTimes[1]).getTime()) {
						continue;
					}
					KmImeetingRes res = (KmImeetingRes) this.findByPrimaryKey(resId);
					JSONObject obj = new JSONObject();
					// conflictResponse.put("result", true);
					obj.put("conflictId", res.getFdId());
					obj.put("conflictName", res.getFdName());
					obj.put("startDate", DateUtil.convertDateToString((Date) times[0], DateUtil.PATTERN_DATETIME));
					obj.put("endDate", DateUtil.convertDateToString((Date) times[1], DateUtil.PATTERN_DATETIME));
					conflictArr.add(obj);
					// return conflictResponse;
				}
					}
			if (!conflictArr.isEmpty()) {
				conflictResponse.put("result", true);
				conflictResponse.put("conflictArr", conflictArr);
					}
				}
		return conflictResponse;
	}

	private String buildRecurrenceStr(String fdHoldDate, RequestContext request) throws Exception {
		String freq = request.getParameter("RECURRENCE_FREQ");
		if (!ImeetingConstant.RECURRENCE_FREQ_NO.equals(freq)) {
			String byday = null;
			if (ImeetingConstant.RECURRENCE_FREQ_WEEKLY.equals(freq)) {
				byday = request.getParameter("RECURRENCE_WEEKS")
						.replaceAll(";", ",");
			} else if (ImeetingConstant.RECURRENCE_FREQ_MONTHLY
					.equals(freq)) {
				if (ImeetingConstant.RECURRENCE_MONTH_TYPE_WEEK
						.equals(request.getParameter("RECURRENCE_MONTH_TYPE"))) {
					Date _startDate = format2
							.parse(fdHoldDate);
					Calendar c = Calendar.getInstance();
					c.setFirstDayOfWeek(Calendar.MONDAY);
					c.setTime(_startDate);
					int weekOfMonth = c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
					int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
					String weekStr = weeks[dayOfWeek - 1];
					byday = weekOfMonth + weekStr;
				}
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("FREQ", request.getParameter("RECURRENCE_FREQ"));
			params.put("ENDTYPE", request.getParameter("RECURRENCE_END_TYPE"));
			params.put("COUNT", request.getParameter("RECURRENCE_COUNT"));
			params.put("UNTIL", request.getParameter("RECURRENCE_UNTIL"));
			params.put("INTERVAL", request.getParameter("RECURRENCE_INTERVAL"));
			params.put("BYDAY", byday);
			String recurrenceStr = RecurrenceUtil.buildRecurrenceStr(params);
			return recurrenceStr;
		}
		return null;
	}

	// 该资源是否冲突
	@Override
	public JSONObject isConflictRes(HttpServletRequest request, String resIds) throws Exception {
		return isConflictRes(new RequestContext(request), resIds);
	}

	// 在指定资源、指定时间范围内找出所有会议占用时间段
	private List<Date[]> findConflictTimeInMain(RequestContext request, String resId, Date start, Date end)
			throws Exception {
		List result = new ArrayList();
		String meetingId = request.getParameter("exceptMeetingId");
		if (StringUtil.isNull(meetingId)) {
			meetingId = request.getParameter("meetingId");
		}
		RequestContext requestContext = new RequestContext();
		requestContext.setParameter("resId", resId);
		requestContext.setParameter("exceptMeetingId", meetingId);
		requestContext.setParameter("fdStart",
				DateUtil.convertDateToString(start, DateUtil.TYPE_DATETIME, requestContext.getLocale()));
		requestContext.setParameter("fdEnd",
				DateUtil.convertDateToString(end, DateUtil.TYPE_DATETIME, requestContext.getLocale()));
		HQLInfo hql = kmImeetingMainService.buildImeetingHql(requestContext);
		hql.setSelectBlock(" kmImeetingMain.fdHoldDate,kmImeetingMain.fdFinishDate");
		result.addAll(kmImeetingMainService.findList(hql));
		HQLInfo hql2 = kmImeetingMainService.buildImeetingRangeHql(requestContext);
		List<KmImeetingMain> matchMainModels = kmImeetingMainService.findList(hql2);
		for (KmImeetingMain main : matchMainModels) {
			String recurrenceStr = main.getFdRecurrenceStr();
			Date mainStartDateTime = main.getFdHoldDate();
			Date mainEndDateTime = main.getFdFinishDate();
			long rangeTime = mainEndDateTime.getTime() - mainStartDateTime.getTime();
			Date searchStart = new Date(start.getTime() - rangeTime);
			Date searchEnd = new Date(end.getTime() + rangeTime);
			List<Date> dates = RecurrenceUtil.getExcuteDateList(recurrenceStr, mainStartDateTime, searchStart,
					searchEnd);
			for (Date date : dates) {
				Date newStartDate = date;
				Date newEndDate = new Date(date.getTime() + mainEndDateTime.getTime() - mainStartDateTime.getTime());
				if (newEndDate.getTime() > main.getFdRecurrenceLastEnd().getTime()) {
					break;
				}
				result.add(new Date[] { newStartDate, newEndDate });
			}
		}
		return result;
	}

	// 在指定资源、指定时间范围内找出所有会议占用时间段
	private List<Date[]> findConflictTimeInMain(HttpServletRequest request, String resId, Date start, Date end)
			throws Exception {
		return findConflictTimeInMain(new RequestContext(request), resId, start, end);
	}

	// 在指定资源、指定时间范围内找出所有预约占用时间段
	private List<Date[]> findConflictTimeInBook(HttpServletRequest request, String resId, Date start, Date end)
			throws Exception {
		return findConflictTimeInBook(new RequestContext(request), resId, start, end);
	}
	// 在指定资源、指定时间范围内找出所有预约占用时间段
	private List<Date[]> findConflictTimeInBook(RequestContext request, String resId, Date start, Date end)
			throws Exception {
		List result = new ArrayList();
		RequestContext requestContext = new RequestContext();
		requestContext.setParameter("hasExam", "true");
		requestContext.setParameter("fdStart",
				DateUtil.convertDateToString(start, ResourceUtil.getString("date.format.datetime")));
		requestContext.setParameter("fdEnd",
				DateUtil.convertDateToString(end, ResourceUtil.getString("date.format.datetime")));
		requestContext.setParameter("format", ResourceUtil.getString("date.format.datetime"));
		String bookId = request.getParameter("bookId");
		if (StringUtil.isNotNull(bookId)) {
			requestContext.setParameter("exceptBookId", bookId);
		}

		requestContext.setParameter("placeId", resId);

		HQLInfo hql1 = kmImeetingBookService.buildNormalBookHql(requestContext);
		hql1.setSelectBlock(" kmImeetingBook.fdHoldDate,kmImeetingBook.fdFinishDate");
		result.addAll(kmImeetingBookService.findList(hql1));
		HQLInfo hql2 = kmImeetingBookService.buildRangeBookHql(requestContext);
		List<KmImeetingBook> matchBookModels = kmImeetingBookService.findList(hql2);
		for (KmImeetingBook book : matchBookModels) {
			String recurrenceStr = book.getFdRecurrenceStr();
			Date bookStartDateTime = book.getFdHoldDate();
			Date bookEndDateTime = book.getFdFinishDate();
			long rangeTime = bookEndDateTime.getTime() - bookStartDateTime.getTime();
			Date searchStart = new Date(start.getTime() - rangeTime);
			Date searchEnd = new Date(end.getTime() + rangeTime);
			List<Date> dates = RecurrenceUtil.getExcuteDateList(recurrenceStr, bookStartDateTime, searchStart,
					searchEnd);
			for (Date date : dates) {
				Date newStartDate = date;
				Date newEndDate = new Date(date.getTime() + bookEndDateTime.getTime() - bookStartDateTime.getTime());
				if (newEndDate.getTime() > book.getFdRecurrenceLastEnd().getTime()) {
					break;
				}
				result.add(new Date[] { newStartDate, newEndDate });
			}
			//判断与召开时间及结束时间是否重叠
			if(start.getTime()<=bookStartDateTime.getTime() && end.getTime()>=bookEndDateTime.getTime()){
				result.add(new Date[] { bookStartDateTime, bookEndDateTime });
			}
		}
		return result;
	}

	// 在会议安排中找出冲突资源
	@Override
	public List<String> findConflictResInMain(HttpServletRequest request, Date start, Date end) throws Exception {
		List<String> result = new ArrayList<String>();
		result.addAll(findConflictResInNormalMain(request, start, end));
		result.addAll(findConflictResInRangeMain(request, start, end));
		return result;
	}

	private Collection<? extends String> findConflictResInRangeMain(HttpServletRequest request, Date start, Date end)
			throws Exception {
		String meetingId = request.getParameter("meetingId");
		HQLInfo hql = new HQLInfo();
		String whereBlock = "";
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" (kmImeetingMain.fdRecurrenceStr is not null or kmImeetingMain.fdRecurrenceStr!='NO') ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" kmImeetingMain.fdHoldDate<:fdFinishDate and ((kmImeetingMain.fdEarlyFinishDate is null and kmImeetingMain.fdRecurrenceLastEnd>:fdHoldDate) or (kmImeetingMain.fdEarlyFinishDate is not null and kmImeetingMain.fdEarlyFinishDate>:fdHoldDate)) and kmImeetingMain.fdPlace is not null and (kmImeetingMain.docStatus='20' or kmImeetingMain.docStatus='30')");
		if (StringUtil.isNotNull(meetingId)) {
			whereBlock += " and kmImeetingMain.fdId<>:meetingId ";
			hql.setParameter("meetingId", meetingId);
		}
		hql.setWhereBlock(whereBlock);
		hql.setParameter("fdHoldDate", start);
		hql.setParameter("fdFinishDate", end);
		List<KmImeetingMain> matchMainModels = kmImeetingMainService.findList(hql);
		List mainResult = new ArrayList();
		for (KmImeetingMain main : matchMainModels) {
			String recurrenceStr = main.getFdRecurrenceStr();
			Date mainStartDateTime = main.getFdHoldDate();
			Date mainEndDateTime = main.getFdFinishDate();
			List<Date> dates = RecurrenceUtil.getExcuteDateList(recurrenceStr, mainStartDateTime,
					start.getTime() > mainStartDateTime.getTime() ? mainStartDateTime : start,
					end.getTime() > mainEndDateTime.getTime() ? end : mainEndDateTime);
			for (Date date : dates) {
				Date newStartDate = date;
				Date newEndDate = new Date(date.getTime() + mainEndDateTime.getTime() - mainStartDateTime.getTime());
				if (newStartDate.getTime() <= end.getTime() && newEndDate.getTime() >= start.getTime()) {
					mainResult.add(main.getFdPlace().getFdId());
					break;
				}
			}
		}
		return mainResult;
	}

	private Collection<? extends String> findConflictResInNormalMain(HttpServletRequest request, Date start, Date end)
			throws Exception {
		String meetingId = request.getParameter("meetingId");
		HQLInfo hql = new HQLInfo();
		hql.setSelectBlock("kmImeetingMain.fdPlace.fdId");
		String whereBlock = "";
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" (kmImeetingMain.fdRecurrenceStr is null or kmImeetingMain.fdRecurrenceStr='NO') ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" kmImeetingMain.fdHoldDate<:fdFinishDate and ((kmImeetingMain.fdEarlyFinishDate is null and kmImeetingMain.fdFinishDate>:fdHoldDate) or (kmImeetingMain.fdEarlyFinishDate is not null and kmImeetingMain.fdEarlyFinishDate>:fdHoldDate)) and kmImeetingMain.fdPlace is not null and (kmImeetingMain.docStatus='20' or kmImeetingMain.docStatus='30')");
		if (StringUtil.isNotNull(meetingId)) {
			whereBlock += " and kmImeetingMain.fdId<>:meetingId ";
			hql.setParameter("meetingId", meetingId);
		}
		hql.setWhereBlock(whereBlock);
		hql.setParameter("fdHoldDate", start);
		hql.setParameter("fdFinishDate", end);
		List result = kmImeetingMainService.findList(hql);
		return result;
	}

	// 在会议安排的分会场找出冲突资源
	@Override
	public List<String> findConflictViceResInMain(HttpServletRequest request, Date start, Date end) throws Exception {
		List<String> result = new ArrayList<String>();
		result.addAll(findConflictViceResInNormalMain(request, start, end));
		result.addAll(findConflictViceResInRangeMain(request, start, end));
		return result;
	}

	private Collection<? extends String> findConflictViceResInRangeMain(HttpServletRequest request, Date start,
			Date end) throws Exception {
		String meetingId = request.getParameter("meetingId");
		HQLInfo hql = new HQLInfo();
		hql.setJoinBlock("left join kmImeetingMain.fdVicePlaces fdVicePlaces");
		String whereBlock = "";
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" (kmImeetingMain.fdRecurrenceStr is not null or kmImeetingMain.fdRecurrenceStr!='NO') ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"kmImeetingMain.fdHoldDate<:fdFinishDate and ((kmImeetingMain.fdEarlyFinishDate is null and kmImeetingMain.fdRecurrenceLastEnd>:fdHoldDate) or (kmImeetingMain.fdEarlyFinishDate is not null and kmImeetingMain.fdEarlyFinishDate>:fdHoldDate)) and (kmImeetingMain.docStatus='20' or kmImeetingMain.docStatus='30')");
		if (StringUtil.isNotNull(meetingId)) {
			whereBlock += " and kmImeetingMain.fdId<>:meetingId ";
			hql.setParameter("meetingId", meetingId);
		}
		hql.setWhereBlock(whereBlock);
		hql.setParameter("fdHoldDate", start);
		hql.setParameter("fdFinishDate", end);
		List<KmImeetingMain> matchMainModels = kmImeetingMainService.findList(hql);
		matchMainModels.removeAll(Collections.singleton(null));
		List mainResult = new ArrayList();
		for (KmImeetingMain main : matchMainModels) {
			String recurrenceStr = main.getFdRecurrenceStr();
			Date mainStartDateTime = main.getFdHoldDate();
			Date mainEndDateTime = main.getFdFinishDate();
			List<Date> dates = RecurrenceUtil.getExcuteDateList(recurrenceStr, mainStartDateTime, start,
					end.getTime() > mainEndDateTime.getTime() ? end : mainEndDateTime);
			for (Date date : dates) {
				Date newStartDate = date;
				Date newEndDate = new Date(date.getTime() + mainEndDateTime.getTime() - mainStartDateTime.getTime());
				if (newStartDate.getTime() <= end.getTime() && newEndDate.getTime() >= start.getTime()) {
					List<KmImeetingRes> vicePlaces = main.getFdVicePlaces();
					for (KmImeetingRes kmImeetingRes : vicePlaces) {
						mainResult.add(kmImeetingRes.getFdId());
					}
					break;
				}
			}
		}
		return mainResult;
	}

	private Collection<? extends String> findConflictViceResInNormalMain(HttpServletRequest request, Date start,
			Date end) throws Exception {
		String meetingId = request.getParameter("meetingId");
		HQLInfo hql = new HQLInfo();
		hql.setSelectBlock("fdVicePlaces.fdId");
		hql.setJoinBlock("left join kmImeetingMain.fdVicePlaces fdVicePlaces");
		String whereBlock = "";
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" (kmImeetingMain.fdRecurrenceStr is null or kmImeetingMain.fdRecurrenceStr='NO') ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"kmImeetingMain.fdHoldDate<:fdFinishDate and ((kmImeetingMain.fdEarlyFinishDate is null and kmImeetingMain.fdFinishDate>:fdHoldDate) or (kmImeetingMain.fdEarlyFinishDate is not null and kmImeetingMain.fdEarlyFinishDate>:fdHoldDate)) and (kmImeetingMain.docStatus='20' or kmImeetingMain.docStatus='30')");
		if (StringUtil.isNotNull(meetingId)) {
			whereBlock += " and kmImeetingMain.fdId<>:meetingId ";
			hql.setParameter("meetingId", meetingId);
		}
		hql.setWhereBlock(whereBlock);
		hql.setParameter("fdHoldDate", start);
		hql.setParameter("fdFinishDate", end);
		List result = kmImeetingMainService.findList(hql);
		result.removeAll(Collections.singleton(null));
		return result;
	}

	// 在会议室预约中找出冲突资源
	@Override
	public List<String> findConflictResInBook(HttpServletRequest request, Date start, Date end) throws Exception {
		List<String> result = new ArrayList<String>();
		//非重复的会议预约
		result.addAll(findConflictResInNormalBook(request, start, end));
		//重复的会议预约
		result.addAll(findConflictResInRangeBook(request, start, end));
		return result;
	}

	// 在非重复预约中找出冲突资源
	private List<String> findConflictResInNormalBook(HttpServletRequest request, Date start, Date end)
			throws Exception {
		HQLInfo hql = kmImeetingBookService.buildNormalBookHql(
				buildCommonBookRequestContext(request, start, end, ResourceUtil.getString("date.format.datetime")));
		hql.setSelectBlock("kmImeetingBook.fdPlace.fdId");
		List bookResult = kmImeetingBookService.findList(hql);
		return bookResult;
	}

	// 在重复预约中找出冲突资源
	private List<String> findConflictResInRangeBook(HttpServletRequest request, Date start, Date end) throws Exception {
		HQLInfo hql = kmImeetingBookService.buildRangeBookHql(
				buildCommonBookRequestContext(request, start, end, ResourceUtil.getString("date.format.datetime")));
		List<KmImeetingBook> matchBookModels = kmImeetingBookService.findList(hql);
		List bookResult = new ArrayList();
		for (KmImeetingBook book : matchBookModels) {
			String recurrenceStr = book.getFdRecurrenceStr();
			Date bookStartDateTime = book.getFdHoldDate();
			Date bookEndDateTime = book.getFdFinishDate();
			List<Date> dates = RecurrenceUtil.getExcuteDateList(recurrenceStr, bookStartDateTime,
					start.getTime() > bookStartDateTime.getTime() ? bookStartDateTime : start,
					end.getTime() > bookEndDateTime.getTime() ? end : bookEndDateTime);
			for (Date date : dates) {
				Date newStartDate = date;
				Date newEndDate = new Date(date.getTime() + bookEndDateTime.getTime() - bookStartDateTime.getTime());
				if (newStartDate.getTime() <= end.getTime() && newEndDate.getTime() >= start.getTime()) {
					bookResult.add(book.getFdPlace().getFdId());
					break;
				}
			}
			//判断与召开时间及结束时间是否重叠
			if(start.getTime()<=bookStartDateTime.getTime() && end.getTime()>=bookEndDateTime.getTime()){
				bookResult.add(book.getFdPlace().getFdId());
			}
		}
		return bookResult;
	}

	// 在会议安排中找出冲突资源
	@Override
	public List findOccupiedResInMain(HttpServletRequest request, String resId, Date start, Date end)
			throws Exception {
		List<String> result = new ArrayList<String>();
		result.addAll(findOccupiedResInNormalMain(request, resId, start, end));
		result.addAll(findOccupiedResInRangeMain(request, resId, start, end));
		return result;
	}

	private Collection<? extends String> findOccupiedResInRangeMain(HttpServletRequest request, String resId,
			Date start, Date end) throws Exception {
		HQLInfo hql = new HQLInfo();
		String whereBlock = " kmImeetingMain.fdHoldDate<:fdFinishDate and ((kmImeetingMain.fdEarlyFinishDate is null and kmImeetingMain.fdRecurrenceLastEnd>:fdHoldDate) or (kmImeetingMain.fdEarlyFinishDate is not null and kmImeetingMain.fdEarlyFinishDate>:fdHoldDate)) and (kmImeetingMain.fdPlace.fdId =:resId or fdVicePlaces.fdId=:vResId) and (kmImeetingMain.docStatus='20' or kmImeetingMain.docStatus='30')";
		whereBlock = StringUtil.linkString(whereBlock, " and ", " (kmImeetingMain.fdRecurrenceStr is not null) ");
		hql.setJoinBlock("left join kmImeetingMain.fdVicePlaces fdVicePlaces");
		hql.setParameter("resId", resId);
		hql.setParameter("vResId", resId);
		hql.setWhereBlock(whereBlock);
		hql.setParameter("fdHoldDate", start);
		hql.setParameter("fdFinishDate", end);
		hql.setOrderBy("kmImeetingMain.fdHoldDate asc");
		List<KmImeetingMain> matchMainModels = kmImeetingMainService.findList(hql);
		List mainResult = new ArrayList();
		for (KmImeetingMain main : matchMainModels) {
			String recurrenceStr = main.getFdRecurrenceStr();
			Date mainStartDateTime = main.getFdHoldDate();
			Date mainEndDateTime = main.getFdFinishDate();
			if (StringUtil.isNotNull(recurrenceStr)) {
				List<Date> dates = RecurrenceUtil.getExcuteDateList(recurrenceStr, mainStartDateTime, start,
						end.getTime() > mainEndDateTime.getTime() ? end : mainEndDateTime);
				for (Date date : dates) {
					Date newStartDate = date;
					Date newEndDate = new Date(
							date.getTime() + mainEndDateTime.getTime() - mainStartDateTime.getTime());
					if (newStartDate.getTime() <= end.getTime() && newEndDate.getTime() >= start.getTime()) {
						mainResult.add(main);
						break;
					}
				}
			}
		}
		return mainResult;
	}

	private Collection<? extends String> findOccupiedResInNormalMain(HttpServletRequest request, String resId,
			Date start, Date end) throws Exception {
		HQLInfo hql = new HQLInfo();
		String whereBlock = " kmImeetingMain.fdHoldDate<:fdFinishDate and ((kmImeetingMain.fdEarlyFinishDate is null and kmImeetingMain.fdFinishDate>:fdHoldDate) or (kmImeetingMain.fdEarlyFinishDate is not null and kmImeetingMain.fdEarlyFinishDate>:fdHoldDate)) and (kmImeetingMain.fdPlace.fdId =:resId or fdVicePlaces.fdId=:vResId) and (kmImeetingMain.docStatus='20' or kmImeetingMain.docStatus='30')";
		whereBlock = StringUtil.linkString(whereBlock, " and ", " (kmImeetingMain.fdRecurrenceStr is null) ");
		hql.setJoinBlock("left join kmImeetingMain.fdVicePlaces fdVicePlaces");
		hql.setParameter("resId", resId);
		hql.setParameter("vResId", resId);
		hql.setWhereBlock(whereBlock);
		hql.setParameter("fdHoldDate", start);
		hql.setParameter("fdFinishDate", end);
		hql.setOrderBy("kmImeetingMain.fdHoldDate asc");
		List result = kmImeetingMainService.findList(hql);
		return result;
	}

	// 在会议室预约中找出冲突资源
	@Override
	public List  findOccupiedResInBook(HttpServletRequest request, String resId, Date start, Date end)
			throws Exception {
		List<String> result = new ArrayList<String>();
		result.addAll(findOccupiedResInNormalBook(request, resId, start, end));
		result.addAll(findOccupiedResInRangeBook(request, resId, start, end));
		return result;
	}

	/**
	 * 非重复的会议室预定
	 * @param request
	 * @param resId
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	private List findOccupiedResInNormalBook(HttpServletRequest request, String resId, Date start, Date end)
			throws Exception {
		HQLInfo hql = kmImeetingBookService.buildNormalBookHql(
				buildCommonBookRequestContext(request, start, end, ResourceUtil.getString("date.format.datetime")));
		if (StringUtil.isNotNull(resId)) {
			hql.setWhereBlock(hql.getWhereBlock() + " and kmImeetingBook.fdPlace.fdId = :placeId ");
			hql.setParameter("placeId", resId);
		}
		List bookResult = kmImeetingBookService.findList(hql);
		return bookResult;
	}

	/**
	 * 重复的会议室预定
	 * @param request
	 * @param resId
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	private List findOccupiedResInRangeBook(HttpServletRequest request, String resId, Date start, Date end)
			throws Exception {
		HQLInfo hql = kmImeetingBookService.buildRangeBookHql(
				buildCommonBookRequestContext(request, start, end, ResourceUtil.getString("date.format.datetime")));
		if (StringUtil.isNotNull(resId)) {
			hql.setWhereBlock(hql.getWhereBlock() + " and kmImeetingBook.fdPlace.fdId = :placeId ");
			hql.setParameter("placeId", resId);
		}
		List<KmImeetingBook> matchBookModels = kmImeetingBookService.findList(hql);
		List bookResult = new ArrayList();
		if(!ArrayUtil.isEmpty(matchBookModels)){
			for (KmImeetingBook kmImeetingBook:matchBookModels){
				String fdRecurrenStr = kmImeetingBook.getFdRecurrenceStr();
				Date fdHoldDate = kmImeetingBook.getFdHoldDate();
				Date fdFinishDate = kmImeetingBook.getFdFinishDate();
				if (StringUtil.isNotNull(fdRecurrenStr)) {
					Date lastEndDate = RecurrenceUtil
							.getLastedExecuteDate(fdRecurrenStr, fdFinishDate);
					List<Date> dates = RecurrenceUtil.getExcuteDateList(
							fdRecurrenStr,
							fdHoldDate, fdHoldDate, lastEndDate);
					for (Date date : dates) {
						Date newStartDate = date;
						Date newEndDate = new Date(
								date.getTime() + fdFinishDate.getTime() - fdHoldDate.getTime());
						if (newStartDate.getTime() <= end.getTime() && newEndDate.getTime() >= start.getTime()) {
							bookResult.add(kmImeetingBook);
							break;
						}
					}
				}

			}
		}
		return bookResult;
	}

	private class TimeComparator implements Comparator<Object[]> {
		@Override
		public int compare(Object[] o1, Object[] o2) {
			return ((Date) o1[0]).getTime() <= ((Date) o2[0]).getTime() ? -1 : 1;
		}
	}

	private RequestContext buildCommonBookRequestContext(HttpServletRequest request, Date start, Date end,
			String format) throws Exception {
		RequestContext requestContext = new RequestContext(request);
		requestContext.setParameter("hasExam", "true");
		requestContext.setParameter("fdStart", DateUtil.convertDateToString(start, format));
		requestContext.setParameter("fdEnd", DateUtil.convertDateToString(end, format));
		requestContext.setParameter("format", format);
		String bookId = request.getParameter("bookId");
		if (StringUtil.isNotNull(bookId)) {
			requestContext.setParameter("exceptBookId", bookId);
		}
		return requestContext;
	}

	private JSONObject buildResJson(KmImeetingRes kmImeetingRes) throws Exception {
		JSONObject json = new JSONObject();
		json.put("thirdSystemUuid", kmImeetingRes.getFdId());
		json.put("roomName", kmImeetingRes.getFdName());
		json.put("roomLocation", StringUtil.isNotNull(kmImeetingRes.getFdAddressFloor())
				? kmImeetingRes.getFdAddressFloor() : kmImeetingRes.getFdName());
		json.put("holdNum", StringUtil.isNotNull(kmImeetingRes.getFdSeats()) ? kmImeetingRes.getFdSeats() : "100");
		json.put("dataFrom", "02");
		JSONObject topOrg = BoenUtil.getTopOrg();
		json.put("organizationId", topOrg.get("topOrgId"));
		Boolean innerScreenEnable = kmImeetingRes.getFdInnerScreenEnable();
		json.put("hasInnerScream", innerScreenEnable);
		if (innerScreenEnable) {
			List<KmImeetingInnerScreen> innerScreens = kmImeetingRes
					.getFdInnerScreens();
			JSONArray innerArr = new JSONArray();
			for (KmImeetingInnerScreen innerScreen : innerScreens) {
				JSONObject obj = new JSONObject();
				obj.put("screamName", innerScreen.getFdName());
				obj.put("screamCode", innerScreen.getFdCode());
				innerArr.add(obj);
			}
			json.put("innerScreams", innerArr);
		}
		Boolean outerScreenEnable = kmImeetingRes.getFdOuterScreenEnable();
		json.put("hasOutterScream", outerScreenEnable);
		if (outerScreenEnable) {
			List<KmImeetingOuterScreen> outerScreens = kmImeetingRes
					.getFdOuterScreens();
			JSONArray outerArr = new JSONArray();
			for (KmImeetingOuterScreen outerScreen : outerScreens) {
				JSONObject obj = new JSONObject();
				obj.put("screamName", outerScreen.getFdName());
				obj.put("screamCode", outerScreen.getFdCode());
				outerArr.add(obj);

			}
			json.put("outScreams", outerArr);
		}

		Boolean signInEnable = kmImeetingRes.getFdSignInEnable();
		json.put("hasSignInDevice", signInEnable);
		if (signInEnable) {
			JSONArray signInArr = new JSONArray();
			JSONObject obj = new JSONObject();
			obj.put("signInDeviceTypeCode",
					kmImeetingRes.getFdSignInTypeCode());
			obj.put("signInDeviceIp",
					kmImeetingRes.getFdSignInIp());
			obj.put("signInDevicePort",
					kmImeetingRes.getFdSignInPort());
			obj.put("signInDeviceUserName",
					kmImeetingRes.getFdSignInUserName());
			obj.put("signInDevicePassword",
					kmImeetingRes.getFdSignInPassword());
			signInArr.add(obj);
			json.put("signInDevices", signInArr);
		}
		return json;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		String fdId = "";
		KmImeetingRes kmImeetingRes = (KmImeetingRes) modelObj;
		fdId = super.add(modelObj);
		// 如果铂恩会议集成开启，则同步到铂恩会议系统
		if (BoenUtil.isBoenEnable()) {
			addBoen(kmImeetingRes, true);
		}
		return fdId;
	}

	public void addBoen(KmImeetingRes kmImeetingRes, Boolean isAddOpt) throws Exception {
		try {
			JSONObject resJson = buildResJson(kmImeetingRes);
			String url = BoenUtil.getBoenUrl() + "/openapi/meetRooms/";
			String result = BoenUtil.sendPost(url, resJson.toString());
			if (StringUtil.isNotNull(result)) {
				JSONObject res = JSONObject.fromObject(result);
				if (res.getInt("status") == 201) {
					String fdRoomId = res.get("data").toString();
					KmImeetingMapping kim = new KmImeetingMapping();
					kim.setFdId(kmImeetingRes.getFdId());
					kim.setFdModelId(kmImeetingRes.getFdId());
					kim.setFdModelName(KmImeetingRes.class.getName());
					kim.setFdThirdSysId(fdRoomId);
					kmImeetingMappingService.add(kim);
				} else {
					if (isAddOpt) { // 如果是新增操作，则抛异常回滚，否则是全量同步，跳过错误
						int status = res.getInt("status");
						String message = (String) res.get("message");
						throw new RuntimeException("code:" + status + ",msg:" + message);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		try {
			KmImeetingRes kmImeetingRes = (KmImeetingRes) modelObj;
			super.update(modelObj);
			// 如果铂恩会议集成开启，则同步到铂恩会议系统
			if (BoenUtil.isBoenEnable()) {
				JSONObject resJson = buildResJson(kmImeetingRes);
				KmImeetingMapping kim = (KmImeetingMapping) kmImeetingMappingService
						.findByPrimaryKey(kmImeetingRes.getFdId(), KmImeetingMapping.class.getName(), true);
				if (kim == null) {
					kim = kmImeetingMappingService.findByModelId(kmImeetingRes.getFdId(),
							KmImeetingRes.class.getName());
				}
				if (kim != null) {
					if (kmImeetingRes.getFdIsAvailable()) {
						String url = BoenUtil.getBoenUrl() + "/openapi/meetRooms/"
								+ kmImeetingRes.getFdId();
						String result = BoenUtil.sendPut(url, resJson.toString());
						if (StringUtil.isNotNull(result)) {
							JSONObject res = JSONObject.fromObject(result);
							if (res.getInt("status") == 200) {
								String fdRoomId = res.get("data").toString();
								// 如果存在且id不一致，则更新
								if (!fdRoomId.equals(kim.getFdThirdSysId())) {
									kim.setFdThirdSysId(fdRoomId);
									kmImeetingMappingService.update(kim);
								}
							} else {
								int status = res.getInt("status");
								String message = (String) res.get("message");
								throw new RuntimeException("code:" + status + ",msg:" + message);
							}
						}
					} else {
						deleteBoen(kmImeetingRes, kim);
					}
				} else {
					addBoen(kmImeetingRes, false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public void deleteBoen(KmImeetingRes kmImeetingRes, KmImeetingMapping kmImeetingMapping) throws Exception {
		try {
			String url = BoenUtil.getBoenUrl() + "/openapi/meetRooms/" + kmImeetingRes.getFdId()
					+ "?dataFrom=02";
			String result = BoenUtil.sendDelete(url);
			if (StringUtil.isNotNull(result)) {
				JSONObject res = JSONObject.fromObject(result);
				if (res.getInt("status") == 200) {
					kmImeetingMappingService.delete(kmImeetingMapping);
				} else {
					int status = res.getInt("status");
					String message = (String) res.get("message");
					throw new RuntimeException("code:" + status + ",msg:" + message);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		KmImeetingRes kmImeetingRes = (KmImeetingRes) modelObj;
		try {
			super.delete(kmImeetingRes);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		// 开启了铂恩对接
		if (BoenUtil.isBoenEnable()) {
			KmImeetingMapping kmImeetingMapping = (KmImeetingMapping) kmImeetingMappingService
					.findByPrimaryKey(kmImeetingRes.getFdId(), KmImeetingMapping.class.getName(), true);
			if (kmImeetingMapping == null) {
				kmImeetingMapping = kmImeetingMappingService.findByModelId(kmImeetingRes.getFdId(),
						KmImeetingRes.class.getName());
			}
			if (kmImeetingMapping != null) {
				deleteBoen(kmImeetingRes, kmImeetingMapping);
			}
		}
	}

	@Override
	public Page listUse(RequestContext requestContext) throws Exception {
		Page page = new Page();
		String s_pageno = requestContext.getParameter("pageno");
		String s_rowsize = requestContext.getParameter("rowsize");
		int pageno = 0;
		int rowsize = SysConfigParameters.getRowSize();
		if (s_pageno != null && s_pageno.length() > 0) {
			pageno = Integer.parseInt(s_pageno);
		}
		if (s_rowsize != null && s_rowsize.length() > 0) {
			rowsize = Integer.parseInt(s_rowsize);
		}
		int total = getListUseTotal(requestContext);// totalRows
		if (total > 0) {
			page.setRowsize(rowsize);
			page.setPageno(pageno);
			page.setTotalrows(total);
			page.excecute();
			NativeQuery sqlQuery = getUseNativeQuery(requestContext);// 分页查询sql
			sqlQuery.setFirstResult(page.getStart());
			sqlQuery.setMaxResults(page.getRowsize());
			page.setList(sqlQuery.list());
		} else {
			page = Page.getEmptyPage();
		}
		if (UserOperHelper.allowLogOper("listUse",
				KmImeetingUse.class.getName())) {
			List<KmImeetingUse> useList = (List<KmImeetingUse>) page.getList();
			for(KmImeetingUse use : useList){
				UserOperContentHelper.putFind(use.getFdPlaceId(),
						use.getFdPlace(), getModelName());
			}
		}
		return page;
	}

	/**
	 * 条件语句
	 */
	private Map<String, Object> buidCondition(RequestContext requestContext) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 筛选条件
		String fdName = requestContext.getParameter("q.fdName");
		String fdPlace = requestContext.getParameter("fdPlace");
		if (StringUtil.isNotNull(requestContext.getParameter("q.fdPlace"))) {
			fdPlace = requestContext.getParameter("q.fdPlace");
		}
		String fdStartDate = requestContext.getParameter("fdStartDate");
		String fdEndDate = requestContext.getParameter("fdEndDate");
		String[] fdDate = requestContext.getParameterValues("q.fdDate");
		if (fdDate != null) {
			if (StringUtil.isNotNull(fdDate[0])) {
				fdStartDate = fdDate[0];
			}
			if (StringUtil.isNotNull(fdDate[1])) {
				fdEndDate = fdDate[1];
			}
		}
		String dateType = requestContext.getParameter("dateType");
		if (StringUtil.isNull(dateType)) {
			dateType = requestContext.getParameter("q.dateType");
		}
		String conditionStr = "";
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtil.isNotNull(fdName)) {
			conditionStr = StringUtil.linkString(conditionStr, " and ",
					" %use%.fd_name like :fdName ");
			params.put("fdName", "%" + fdName + "%");
		}
		if (StringUtil.isNotNull(fdPlace)) {
			// 查询条件,会议室
			conditionStr = StringUtil.linkString(conditionStr, " and ",
					" %use%.fd_place_id=:fdPlaceId ");
			params.put("fdPlaceId", fdPlace);
		}
		if (StringUtil.isNotNull(fdStartDate)) {
			// 查询条件,开始时间
			conditionStr = StringUtil.linkString(conditionStr, " and ",
					" %use%.fd_hold_date>=:fdStartDate ");
			params.put("fdStartDate", DateUtil.convertStringToDate(fdStartDate,
					DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser().getLocale()));
		}
		if (StringUtil.isNotNull(fdEndDate)) {
			// 查询条件,结束时间时间
			conditionStr = StringUtil.linkString(conditionStr, " and ",
					" %use%.fd_hold_date<=:fdEndDate ");
			params.put("fdEndDate", DateUtil.convertStringToDate(fdEndDate,
					DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser().getLocale()));
		}
		if (StringUtil.isNotNull(dateType)) {
			// 查询条件,查询时间类型
			conditionStr = StringUtil.linkString(conditionStr, " and ",
					buildDateTypeSQL(dateType, params));
		}
		if (StringUtil.isNotNull(conditionStr)) {
			conditionStr = " and " + conditionStr;
		}
		result.put("conditionStr", conditionStr);// 查询语句
		result.put("params", params);// 查询参数
		return result;
	}

	/**
	 * 获取列表total
	 */
	private int getListUseTotal(RequestContext requestContext) {

		Map<String, Object> result = buidCondition(requestContext);
		String conditionStr = (String) result.get("conditionStr");
		Map<String, Object> params = (Map<String, Object>) result.get("params");

		String sql = "select count(*) from "
				+ "(select m.fd_id "
				+ "from km_imeeting_main m,sys_org_element pa,km_imeeting_res ra where m.doc_creator_id=pa.fd_id and m.fd_place_id=ra.fd_id  and m.fd_place_id is not null and m.doc_status<>'10' "
				+ conditionStr.replaceAll("%use%", "m") + getAuthAreaSql("m")
				+ "union "
				+ " select b.fd_id "
				+ "from km_imeeting_book b,sys_org_element pb,km_imeeting_res rb where b.doc_creator_id=pb.fd_id and b.fd_place_id=rb.fd_id "
				+ conditionStr.replaceAll("%use%", "b") + getAuthAreaSql("b")
				+ ") total";
		NativeQuery sqlQuery = getBaseDao().getHibernateSession().createNativeQuery(sql);
		// 参数列表
		if (!params.isEmpty()) {
			for (String key : params.keySet()) {
				sqlQuery.setParameter(key, params.get(key));
			}
		}
		int totalCount = Integer.valueOf((sqlQuery.uniqueResult())
				.toString());
		return totalCount;
	}

	/**
	 * 获取集团分级权限SQL
	 *
	 * @param alias
	 * @return
	 */
	private String getAuthAreaSql(String alias) {
		// 开启集团分级和数据隔离才需要
		if (ISysAuthConstant.IS_AREA_ENABLED && ISysAuthConstant.IS_ISOLATION_ENABLED) {
			StringBuffer sql = new StringBuffer();
			// 获取当前场所ID
			String areaId = UserUtil.getKMSSUser().getAuthAreaId();
			// 对场所用户的需要隔离的业务数据进行过滤
			if (StringUtil.isNotNull(areaId)) {
				try {
					HQLInfo hqlInfo = new HQLInfo();
					if ("m".equals(alias)) {
						hqlInfo.setModelName(KmImeetingMain.class.getName());
					} else {
						hqlInfo.setModelName(KmImeetingBook.class.getName());
					}
					String[] upperAreaIds = null;
					Object checkParam = SysAuthAreaUtils.getAreaIsolation(hqlInfo);
					if (AreaIsolation.NONE.equals(checkParam)) {
						// 不过滤任何场所
						return "";
					}
					if (AreaIsolation.SUPER.equals(checkParam) || AreaIsolation.BRANCH.equals(checkParam)) {
						upperAreaIds = SysAuthAreaHelper.getUpperAreaId(areaId, false);
					}

					if (AreaIsolation.CHILD.equals(checkParam) || AreaIsolation.BRANCH.equals(checkParam)) {
						String hierarchyId = UserUtil.getKMSSUser().getAuthAreaHierarchyId();
						sql.append(alias).append(
										".auth_area_id in (select fd_id from sys_auth_area where fd_hierarchy_id like '")
								.append(hierarchyId).append("%')");
					} else {
						sql.append(alias).append(".auth_area_id = '").append(areaId).append("'");
					}

					if (!ArrayUtils.isEmpty(upperAreaIds)
							&& (AreaIsolation.SUPER.equals(checkParam) || AreaIsolation.BRANCH.equals(checkParam))) {
						for (String upperAreaId : upperAreaIds) {
							sql.append(" or ").append(alias).append(".auth_area_id = '").append(upperAreaId)
									.append("'");
						}
					}
					if (sql.length() > 0) {
						return " and (" + sql.toString() + ")";
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} else {
				// 未登录任何场所
				sql.append(" and 1=2");
			}
			return sql.toString();
		} else {
			return "";
		}
	}

	private List<String> orderCols;
	/**
	 * 获取列表查询语句
	 */
	private NativeQuery getUseNativeQuery(RequestContext requestContext) {
		Map<String, Object> result = buidCondition(requestContext);
		String conditionStr = (String) result.get("conditionStr");
		Map<String, Object> params = (Map<String, Object>) result.get("params");

		// sql
		String sql = "select * from ";
		sql += "(select m.fd_id as fdId,ra.fd_id as fdPlaceId,ra.fd_name as fdPlace,m.fd_name as fdName ,m.fd_hold_date as fdHoldDate,m.fd_finish_date as fdFinishDate,pa.fd_name as personName,m.doc_status as docStatus,"+HibernateUtil.toBooleanValueString(true)+" as isMeeting, "+HibernateUtil.toBooleanValueString(true)+" as fdHasExam "
				+ "from km_imeeting_main m,sys_org_element pa,km_imeeting_res ra where m.doc_creator_id=pa.fd_id and m.fd_place_id=ra.fd_id and m.fd_place_id is not null  and m.doc_status<>'10' "
				+ conditionStr.replaceAll("%use%", "m") + getAuthAreaSql("m")
				+ " union "
				+ " select b.fd_id as fdId,rb.fd_id as fdPlaceId,rb.fd_name as fdPlace,b.fd_name as fdName,b.fd_hold_date as fdHoldDate,b.fd_finish_date as fdFinishDate,pb.fd_name as personName,'30' as docStatus,"+HibernateUtil.toBooleanValueString(false)+" as isMeeting ,b.fd_has_exam as fdHasExam "
				+ "from km_imeeting_book b,sys_org_element pb,km_imeeting_res rb where b.doc_creator_id=pb.fd_id and b.fd_place_id=rb.fd_id "
				+ conditionStr.replaceAll("%use%", "b") + getAuthAreaSql("b")
				+ " ) total ";
		// 排序
		String orderby = requestContext.getParameter("orderby");
		if (StringUtil.isNotNull(orderby)) {
			// 防止SQL注入，这里传入的排序字段需要做查询字段的检测
			if (orderCols == null) {
				orderCols = new ArrayList<String>();
				orderCols.add("fdId");
				orderCols.add("fdPlaceId");
				orderCols.add("fdPlace");
				orderCols.add("fdName");
				orderCols.add("fdHoldDate");
				orderCols.add("fdFinishDate");
				orderCols.add("personName");
				orderCols.add("docStatus");
				orderCols.add("isMeeting");
				orderCols.add("fdHasExam");
			}
			if (!orderCols.contains(orderby)) {
				// 如果传入的排序字段不在查询字段里，可能是SQL注入
				throw new KmssRuntimeException(new KmssMessage("errors.invalid", "orderby"));
			}
		} else {
			orderby = "fdHoldDate";
		}
		String ordertype = requestContext.getParameter("ordertype");
		if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
			orderby += " desc";
		}
		if (StringUtil.isNotNull(orderby)) {
			sql += " order by " + orderby;
		} else {
			sql += " order by fdHoldDate desc";
		}
		NativeQuery sqlQuery = getBaseDao().getHibernateSession().createNativeQuery(sql);
		// 参数列表
		if (!params.isEmpty()) {
			for (String key : params.keySet()) {
				sqlQuery.setParameter(key, params.get(key));
			}
		}
		// 实体化
		sqlQuery.addScalar("fdId", StandardBasicTypes.STRING)
				.addScalar("fdPlaceId", StandardBasicTypes.STRING)
				.addScalar("fdPlace", StandardBasicTypes.STRING)
				.addScalar("fdName", StandardBasicTypes.STRING)
				.addScalar("fdHoldDate", StandardBasicTypes.TIMESTAMP)
				.addScalar("fdFinishDate", StandardBasicTypes.TIMESTAMP)
				.addScalar("personName", StandardBasicTypes.STRING)
				.addScalar("docStatus", StandardBasicTypes.STRING)
				.addScalar("isMeeting", StandardBasicTypes.BOOLEAN)
				.addScalar("fdHasExam", StandardBasicTypes.BOOLEAN);
		sqlQuery.setResultTransformer(Transformers
				.aliasToBean(KmImeetingUse.class));
		return sqlQuery;
	}

	private String buildDateTypeSQL(String dateType, Map<String, Object> params) {
		String tmpStr = "";
		if ("thisweek".equals(dateType)) {// 本周
			Calendar monday = Calendar.getInstance();
			Calendar sunday = Calendar.getInstance();
			monday.setFirstDayOfWeek(Calendar.MONDAY);
			monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			fillTime(monday, true);
			sunday.setFirstDayOfWeek(Calendar.MONDAY);
			sunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			fillTime(monday, false);
			tmpStr = " ( %use%.fd_finish_date>=:fdHoldDate and %use%.fd_hold_date<=:fdFinishDate )";
			params.put("fdHoldDate", monday.getTime());
			params.put("fdFinishDate", sunday.getTime());
		}
		if ("nextweek".equals(dateType)) {// 下周
			Calendar monday = Calendar.getInstance();
			Calendar sunday = Calendar.getInstance();
			monday.setFirstDayOfWeek(Calendar.MONDAY);
			monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			monday.add(Calendar.DATE, 7);
			fillTime(monday, true);
			sunday.setFirstDayOfWeek(Calendar.MONDAY);
			sunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			fillTime(sunday, false);
			sunday.add(Calendar.DATE, 7);
			tmpStr = " ( %use%.fd_finish_date>=:fdHoldDate and %use%.fd_hold_date<=:fdFinishDate )";
			params.put("fdHoldDate", monday.getTime());
			params.put("fdFinishDate", sunday.getTime());
		}
		if ("thismonth".equals(dateType)) {// 半个月内
			Calendar first = Calendar.getInstance();
			Calendar middle = Calendar.getInstance();
			middle.add(Calendar.DATE, 15);
			tmpStr = " ( %use%.fd_finish_date>=:fdHoldDate and %use%.fd_hold_date<=:fdFinishDate )";
			params.put("fdHoldDate", first.getTime());
			params.put("fdFinishDate", middle.getTime());
		}
		return tmpStr;
	}

	private void fillTime(Calendar calendar, boolean isClear) {
		if (isClear) {
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
		} else {
			calendar.set(Calendar.HOUR, 59);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
		}
	}
	
	
	@Override
	public JSONArray placeList() throws Exception {
		JSONArray jsonArrLast = new JSONArray();
		HQLInfo hql = new HQLInfo();
		List<KmImeetingResCategory> categorys = (List<KmImeetingResCategory>) kmImeetingResCategoryService.findList(hql);
		
		hql = new HQLInfo();
		hql.setWhereBlock("kmImeetingRes.fdIsAvailable = :fdIsAvailable");
		hql.setParameter("fdIsAvailable", Boolean.TRUE);
		hql.setOrderBy("kmImeetingRes.fdOrder asc");
		hql.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
		for (KmImeetingResCategory category : categorys) {
				hql.setWhereBlock(StringUtil.linkString(hql.getWhereBlock(), " and ",
						"kmImeetingRes.docCategory.fdId =:docCategoryId"));
				hql.setParameter("docCategoryId", category.getFdId());
				List<KmImeetingRes> ress = new ArrayList();

				ress = this.findValue(hql);
				JSONObject obj = new JSONObject();
				obj.accumulate("cateId", category.getFdId());
				obj.accumulate("cateName", category.getFdName());
				JSONArray jsonArr = new JSONArray();
				for (KmImeetingRes c : ress) {
					JSONObject obj2 = new JSONObject();
					obj2.accumulate("placeId", c.getFdId());
					obj2.accumulate("placeName", c.getFdName());
					obj2.accumulate("fdDetail", c.getFdDetail());
					obj2.accumulate("fdAddressFloor", c.getFdAddressFloor());
					obj2.accumulate("fdSeats", c.getFdSeats());
					obj2.accumulate("fdIsAvailable", c.getFdIsAvailable());
					obj2.accumulate("fdOrder", c.getFdOrder());
					if(c.getDocKeeper()!=null){
					obj2.accumulate("docKeeperId", c.getDocKeeper().getFdId());
					obj2.accumulate("docKeeperName", c.getDocKeeper().getFdName());
					}else{
					obj2.accumulate("docKeeperId", "");
					obj2.accumulate("docKeeperName", "");
					}
					obj2.accumulate("fdUserTime", c.getFdUserTime());
					jsonArr.add(obj2);
				}
				obj.accumulate("placeList", jsonArr);
				jsonArrLast.add(obj);
		}
				return jsonArrLast;
	}
	
	@Override
	public JSONArray getCateById(String cateId) throws Exception {
		JSONArray jsonArrLast = new JSONArray();
		HQLInfo hql = new HQLInfo();
		hql.setModelName("KmImeetingResCategory");
		hql.setWhereBlock("kmImeetingResCategory.fdId = :fdId");
		hql.setParameter("fdId", cateId);
		
		List<KmImeetingResCategory> categorys = (List<KmImeetingResCategory>) this.findList(hql);
		if(categorys.size()>0){
			KmImeetingResCategory	category = categorys.get(0);
			JSONObject obj = new JSONObject();
			obj.accumulate("cateId", category.getFdId());
			obj.accumulate("cateName", category.getFdName());
			obj.accumulate("cateAlterTime", category.getDocAlterTime());
			if(category.getDocAlteror()!=null){
				obj.accumulate("cateDocAlteror", category.getDocAlteror().getFdName());
			}else{
				obj.accumulate("cateDocAlteror", "");
			}
			obj.accumulate("catefdDesc", category.getFdDesc());
			obj.accumulate("catefdOrder", category.getFdOrder());
			obj.accumulate("catefdHierarchyId", category.getFdHierarchyId());
			jsonArrLast.add(obj);
		}
			return jsonArrLast;
	}
	
	@Override
	public JSONArray getResById(String placeId, JSONArray cateJson) throws Exception {

		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock("kmImeetingRes.fdIsAvailable = :fdIsAvailable and kmImeetingRes.fdId = :fdId");
		hql.setParameter("fdIsAvailable", Boolean.TRUE);
		hql.setParameter("fdId", placeId);
		hql.setOrderBy("kmImeetingRes.fdOrder asc");
		hql.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
		List<KmImeetingRes> ress = (List<KmImeetingRes>) this.findList(hql);
		JSONArray jsonArray = new JSONArray();
		if(cateJson.size()>0){
			JSONObject obj2 = (JSONObject) cateJson.get(0);
			JSONArray jsonArrayLast = new JSONArray();
			if(ress.size()>0){
				KmImeetingRes res = ress.get(0);
				JSONObject obj = new JSONObject();
				obj.accumulate("placeId", res.getFdId());
				obj.accumulate("placeName", res.getFdName());
				obj.accumulate("fdDetail", res.getFdDetail());
				obj.accumulate("fdAddressFloor", res.getFdAddressFloor());
				obj.accumulate("fdSeats", res.getFdSeats());
				obj.accumulate("fdIsAvailable", res.getFdIsAvailable());
				obj.accumulate("fdOrder", res.getFdOrder());
				if(res.getDocKeeper()!=null){
				obj.accumulate("docKeeperId", res.getDocKeeper().getFdId());
				obj.accumulate("docKeeperName", res.getDocKeeper().getFdName());
				}else{
				obj.accumulate("docKeeperId", "");
				obj.accumulate("docKeeperName", "");
				}
				obj.accumulate("fdUserTime", res.getFdUserTime());
				jsonArray.add(obj);
			}
			obj2.accumulate("place", jsonArray);
			jsonArrayLast.add(obj2);
			return jsonArrayLast;
		}else{
			if(ress.size()>0){
				KmImeetingRes res = ress.get(0);
				JSONObject obj = new JSONObject();
				obj.accumulate("placeId", res.getFdId());
				obj.accumulate("placeName", res.getFdName());
				obj.accumulate("fdDetail", res.getFdDetail());
				obj.accumulate("fdAddressFloor", res.getFdAddressFloor());
				obj.accumulate("fdSeats", res.getFdSeats());
				obj.accumulate("fdIsAvailable", res.getFdIsAvailable());
				obj.accumulate("fdOrder", res.getFdOrder());
				if(res.getDocKeeper()!=null){
				obj.accumulate("docKeeperId", res.getDocKeeper().getFdId());
				obj.accumulate("docKeeperName", res.getDocKeeper().getFdName());
				}else{
				obj.accumulate("docKeeperId", "");
				obj.accumulate("docKeeperName", "");
				}
				obj.accumulate("fdUserTime", res.getFdUserTime());
				jsonArray.add(obj);
			}
			return jsonArray;
		}

		
	}

	private IKmImeetingBookService kmImeetingBookService;

	public void setKmImeetingBookService(
			IKmImeetingBookService kmImeetingBookService) {
		this.kmImeetingBookService = kmImeetingBookService;
	}

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	private ISysNotifyTodoService sysNotifyTodoService;

	public void setSysNotifyTodoService(
			ISysNotifyTodoService sysNotifyTodoService) {
		this.sysNotifyTodoService = sysNotifyTodoService;
	}

	@Override
	public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		KmImeetingResForm kmImeetingResForm = (KmImeetingResForm) form;
		String fdId = kmImeetingResForm.getFdId();
		KmImeetingRes kmImeetingRes = (KmImeetingRes) findByPrimaryKey(fdId);
		SysOrgElement oldDocKeeper = kmImeetingRes.getDocKeeper();
		String newDocKeeperId = kmImeetingResForm.getDocKeeperId();
		super.update(form, requestContext);
		if (oldDocKeeper != null
				&& !oldDocKeeper.getFdId().equals(newDocKeeperId) && StringUtil.isNotNull(newDocKeeperId)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setModelName(KmImeetingBook.class.getName());
			hqlInfo.setSelectBlock("kmImeetingBook.fdId");
			hqlInfo.setWhereBlock(
					"kmImeetingBook.fdHasExam is null and kmImeetingBook.fdExamer.fdId=:oldDocKeeperId");
			hqlInfo.setParameter("oldDocKeeperId", oldDocKeeper.getFdId());
			List<String> bookIdList = (List<String>) kmImeetingBookService
					.findValue(hqlInfo);
			if (!bookIdList.isEmpty()) {
				String hql = "update com.landray.kmss.km.imeeting.model.KmImeetingBook kmImeetingBook set kmImeetingBook.fdExamer.fdId=:newDocKeeperId where kmImeetingBook.fdHasExam is null and kmImeetingBook.fdExamer.fdId=:oldDocKeeperId";
				Session session = kmImeetingBookService.getBaseDao()
						.getHibernateSession();
				Query query = session.createQuery(hql);
				query.setParameter("newDocKeeperId", newDocKeeperId);
				query.setParameter("oldDocKeeperId", oldDocKeeper.getFdId());
				query.executeUpdate();
				hqlInfo = new HQLInfo();
				hqlInfo.setModelName(KmImeetingBook.class.getName());
				hqlInfo.setWhereBlock(
						HQLUtil.buildLogicIN("kmImeetingBook.fdId",
								bookIdList));
				List<KmImeetingBook> bookList = (List<KmImeetingBook>) kmImeetingBookService
						.findList(hqlInfo);
				for (KmImeetingBook book : bookList) {
					List<SysNotifyTodo> toDoList = (List<SysNotifyTodo>) sysNotifyTodoService
							.getCoreModels(book, "kmImeetingBook");
					for (SysNotifyTodo sysNotifyTodo : toDoList) {
						sysNotifyTodoService.delete(sysNotifyTodo);
					}
					send(book);
				}
			}
		}
	}

	private void send(KmImeetingBook bookModel) throws Exception {
		KmImeetingRes fdPlace = bookModel.getFdPlace();
		List<SysOrgElement> targets = new ArrayList<SysOrgElement>();
		SysOrgElement keeper = fdPlace.getDocKeeper();
		if (keeper != null) {
            targets.add(keeper);
        }
		if (!targets.isEmpty()) {
			NotifyContext notifyContext = sysNotifyMainCoreService
					.getContext("km-imeeting:kmImeetingBook.exam.notify");
			notifyContext.setKey("kmImeetingBook");
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
			notifyContext.setNotifyType("todo");
			notifyContext.setNotifyTarget(targets);
			notifyContext.setDocCreator(
					UserUtil.getUser(bookModel.getDocCreator().getFdId()));
			NotifyReplace notifyReplace = new NotifyReplace();
			notifyReplace.addReplaceText(
					"km-imeeting:kmImeetingBook.docCreator",
					bookModel.getDocCreator().getFdName());
			notifyReplace.addReplaceText("km-imeeting:kmImeetingBook.fdPlace",
					fdPlace.getFdName());
			notifyReplace.addReplaceText("km-imeeting:kmImeetingBook.fdName",
					bookModel.getFdName());
			sysNotifyMainCoreService.sendNotify(bookModel, notifyContext,
					notifyReplace);
		}
	}

	@Override
	public Page findKmImeetingRes(RequestContext request)
			throws Exception {
		int pageno = 0;
		String s_pageno = request.getParameter("pageno");
		String selectedCategories = request.getParameter("selectedCategories");
		if (s_pageno != null && s_pageno.length() > 0) {
			pageno = Integer.parseInt(s_pageno);
		}
		HQLInfo hql = new HQLInfo();
		hql.setModelName(KmImeetingRes.class.getName());
		String whereBlock = " (kmImeetingRes.fdIsAvailable = null or  kmImeetingRes.fdIsAvailable =:fdIsAvailable) ";
		hql.setParameter("fdIsAvailable",Boolean.TRUE);
		if (StringUtil.isNull(selectedCategories)) {
			hql.getParameterList().clear();
			whereBlock = "1 = 2 ";
		} else if (!"all".equals(selectedCategories)) {
			String[] categoryIds = selectedCategories.split(";");
			String linkWhereBlock = "";
			for (int i = 0; i < categoryIds.length; i++) {
				linkWhereBlock = StringUtil.linkString(linkWhereBlock, " or ",
						"kmImeetingRes.docCategory.fdHierarchyId like :fdHierarchyId"
								+ i);
				hql.setParameter("fdHierarchyId" + i, categoryIds[i] + "%");
			}
			whereBlock = StringUtil.linkString(whereBlock, " and ", "("
					+ linkWhereBlock + ")");
		} else {
			if (!UserUtil.getKMSSUser().isAdmin()) {
				// String modelName = "KmImeetingResCategory";
				// String tableName = ModelUtil.getModelTableName(modelName);
				// whereBlock = StringUtil.linkString(whereBlock, " and ",
				// HQLUtil.buildLogicIN(
				// "kmImeetingRes.docCategory.fdHierarchyId",
				// ImeetingCalendarUtil.findHierarchyReaderIds(
				// getKmImeetingResCategoryService(),
				// modelName, tableName)));
			}
		}
		hql.setWhereBlock(whereBlock);
		hql.setOrderBy("kmImeetingRes.fdOrder,kmImeetingRes.fdName");
		hql.setFromBlock("KmImeetingRes kmImeetingRes");
		// 使用权限过滤
		hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.DEFAULT);
		hql.setPageNo(pageno);
		hql.setRowSize(SysConfigParameters.getRowSize());
		Page page = findPage(hql);
		UserOperHelper.logFindAll(page.getList(), getModelName());
		return page;
	}

	@Override
	public void addSyncToBoen() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("kmImeetingRes.fdIsAvailable = :fdIsAvailable");
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		List<KmImeetingRes> l = this.findList(hqlInfo);
		for (KmImeetingRes kmImeetingRes : l) {
			KmImeetingMapping kmImeetingMapping = (KmImeetingMapping) kmImeetingMappingService
					.findByPrimaryKey(kmImeetingRes.getFdId(), KmImeetingMapping.class.getName(), true);
			if (kmImeetingMapping == null) {
				kmImeetingMapping = kmImeetingMappingService.findByModelId(kmImeetingRes.getFdId(),
						KmImeetingRes.class.getName());
			}
			// kmImeetingMapping为空，说明没有同步过
			if (kmImeetingMapping == null) {
				addBoen(kmImeetingRes, false);
			}
		}
	}
}
