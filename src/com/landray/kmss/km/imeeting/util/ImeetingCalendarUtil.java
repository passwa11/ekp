package com.landray.kmss.km.imeeting.util;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.imeeting.model.KmImeetingAgenda;
import com.landray.kmss.km.imeeting.model.KmImeetingBook;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingMainFeedback;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.km.imeeting.model.KmImeetingResCategory;
import com.landray.kmss.km.imeeting.service.IKmImeetingResCategoryService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.unit.model.KmImissiveUnit;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HibernateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.CacheMode;
import org.hibernate.query.Query;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ImeetingCalendarUtil {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(ImeetingCalendarUtil.class);


	public static JSONArray mycalendar(List<KmImeetingMain> mains,
			List<KmImeetingBook> meetingBooks, HttpServletRequest request)
			throws Exception {
		JSONArray array = new JSONArray();
		Map map = new HashMap();
		int index = mains.size() + meetingBooks.size();
		String maxhub = request.getParameter("maxhub");
		for (KmImeetingMain main : mains) {
			JSONObject meeting = genMeetingJSON(main, request);
			meeting.put("priority", index--);
			if ("true".equals(maxhub)) {
				boolean permission = UserUtil.checkAuthentication(
						"/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId="
								+ main.getFdId(),
						"post");
				meeting.put("permission", permission);
			}
			// 我组织的
			if(equalUser(main.getFdEmcee())) {
				meeting.put("color", "#FFC130");
				meeting.put("textColor", "#FFF");
			} else if(equalUser(main.getFdHost())) { // 我主持的
				meeting.put("color", "#4C7BFD");
				meeting.put("textColor", "#FFF");
			} else {
				boolean result = false;
				// 我汇报的
				if (!CollectionUtils.isEmpty(main.getKmImeetingAgendas())) {
					for (KmImeetingAgenda agenda : main
							.getKmImeetingAgendas()) {
						if (equalUser(agenda.getDocReporter())) {
							result = true;
							break;
						}
					}
				}
				if(result) {
					meeting.put("color", "#19A4FF");
					meeting.put("textColor", "#FFF");
				} else {
					// 我参与的
					result = equalUser(main.getFdSummaryInputPerson());
					if (!CollectionUtils
							.isEmpty(main.getKmImeetingMainFeedbacks())
							&& !result) {
						for (KmImeetingMainFeedback feedback : main
								.getKmImeetingMainFeedbacks()) {
							if (equalUser(feedback.getDocCreator())) {
								result = true;
								break;
							}
						}
					} else {
						// 不需要回执的视频会议
						if (!result && !main.getFdNeedFeedback()) {
							for (SysOrgElement orgEle : main.getFdAttendPersons()) {
								if (equalUser(orgEle)) {
									result = true;
									break;
								}
							}
						}
					}
					if(result) {
						meeting.put("color", "#8280FF");
						meeting.put("textColor", "#FFF");
					}
				}
			}
			map.put(main.getFdHoldDate() + main.getFdId(), meeting);
		}
		for (KmImeetingBook meetingBook : meetingBooks) {
			if (meetingBook.getFdHasExam() != null
					&& meetingBook.getFdHasExam()) {
				JSONObject book = genMeetingBookJSON(meetingBook, request);
				book.put("priority", index--);
				map.put(meetingBook.getFdHoldDate() + meetingBook.getFdId(),
						book);
			}
		}
		List<String> ll = new ArrayList<>(map.keySet());

		Collections.sort(ll);

		for (int i = 0; i < ll.size(); i++) {
			array.add(map.get(ll.get(i)));
		}
		return array;
	}

	public static JSONArray myMobileCalendar(List<KmImeetingMain> mains,
			List<KmImeetingBook> meetingBooks, HttpServletRequest request)
			throws Exception {
		String showNotExam = request.getParameter("showNotExam");
		JSONArray array = new JSONArray();
		Map map = new HashMap();
		int index = mains.size() + meetingBooks.size();
		logger.debug("index:" + index);
		logger.debug("myMobileCalendar-mains:" + mains.size());
		for (KmImeetingMain main : mains) {
			JSONObject meeting = genMobileMeetingJSON(main, request);
			meeting.put("priority", index--);
			map.put(main.getFdHoldDate() + main.getFdId(), meeting);
		}
		logger.debug("myMobileCalendar-endMains:");
		for (KmImeetingBook meetingBook : meetingBooks) {
			// 移动端需要显示待审批的预约数据
			if ("true".equals(showNotExam)) {
				JSONObject book = genMobileMeetingBookJSON(meetingBook,
						request);
				book.put("priority", index--);
				map.put(meetingBook.getFdHoldDate() + meetingBook.getFdId(),
						book);
			} else {
				if (meetingBook.getFdHasExam() != null
						&& meetingBook.getFdHasExam()) {
					JSONObject book = genMobileMeetingBookJSON(meetingBook,
							request);
					book.put("priority", index--);
					map.put(meetingBook.getFdHoldDate() + meetingBook.getFdId(),
							book);
				}
			}
		}
		List<String> ll = new ArrayList<>(map.keySet());
		Collections.sort(ll);
		for (int i = 0; i < ll.size(); i++) {
			array.add(map.get(ll.get(i)));
		}
		logger.debug("myMobileCalendar-ll:" + ll.size());
		logger.debug("myMobileCalendar-end");
		return array;
	}

	public static JSONArray myListCalendar(List<KmImeetingMain> mains,
			List<KmImeetingBook> meetingBooks, HttpServletRequest request)
			throws Exception {
		String showNotExam = request.getParameter("showNotExam");
		JSONArray array = new JSONArray();
		try {
			Map map = new HashMap();
			int index = mains.size() + meetingBooks.size();
			String isDesc = request.getParameter("isDesc");
			logger.debug("myListCalendar:" + mains.size());
			for (KmImeetingMain main : mains) {
				JSONObject meeting = genListMeetingJSON(main, request);
				meeting.put("priority", index--);
				map.put(main.getFdHoldDate() + main.getFdId(), meeting);
			}
			logger.debug("myListCalendarEnd:" + map.size());
			for (KmImeetingBook meetingBook : meetingBooks) {
				// 移动端需要显示待审批的预约数据
				if ("true".equals(showNotExam)) {
					JSONObject book = genListMeetingBookJSON(meetingBook,
							request);
					book.put("priority", index--);
					map.put(meetingBook.getFdHoldDate() + meetingBook.getFdId(),
							book);
				} else {
					if (meetingBook.getFdHasExam() != null
							&& meetingBook.getFdHasExam()) {
						JSONObject book = genListMeetingBookJSON(meetingBook,
								request);
						book.put("priority", index--);
						map.put(meetingBook.getFdHoldDate()
								+ meetingBook.getFdId(),
								book);
					}
				}
			}
			List<String> ll = new ArrayList<>(map.keySet());
			if ("true".equals(isDesc)) {
				Collections.sort(ll, new Comparator<String>() {

					@Override
					public int compare(String paramT1, String paramT2) {
						return paramT2.compareTo(paramT1);
					}

				});
			} else {
				Collections.sort(ll);
			}
			for (int i = 0; i < ll.size(); i++) {
				array.add(map.get(ll.get(i)));
			}
			logger.debug("arrayJsonSize:" + ll.size());
		} catch (Exception e) {
			logger.debug("myListCalendar-Exception:" + e.getMessage());
		}
		return array;
	}

	private static boolean equalUser(SysOrgElement element) {
		return element != null
				&& element.getFdId().equals(UserUtil.getUser().getFdId());
	}

	// 会议安排JSON
	public static JSONObject genMeetingJSON(KmImeetingMain kmImeetingMain,
			HttpServletRequest request) throws Exception {
		JSONObject meeting = new JSONObject();
		Date date = new Date();
		String maxhub = request.getParameter("maxhub");
		if ("true".equals(maxhub)) {
			meeting.put("isCloud", kmImeetingMain.getIsCloud());
			meeting.put("isFace", kmImeetingMain.getIsFace());
		}
		meeting.put("fdId", kmImeetingMain.getFdId());
		meeting.put("title", kmImeetingMain.getFdName());
		Date start = kmImeetingMain.getFdHoldDate();
		meeting.put("start", DateUtil.convertDateToString(start,
				DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser().getLocale()));

		Date end = kmImeetingMain.getFdEarlyFinishDate() != null
				? kmImeetingMain.getFdEarlyFinishDate()
				: kmImeetingMain.getFdFinishDate();
		meeting.put("end", DateUtil.convertDateToString(end,
				DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser().getLocale()));

		meeting.put("allDay", false);
		String status = "holding", statusText = ResourceUtil.getString(
				"kmImeeting.status.publish.holding",
				"km-imeeting");
		if (date.getTime() < start.getTime()) {
			status = "unHold";
			statusText = ResourceUtil.getString(
					"kmImeeting.status.publish.unHold", "km-imeeting");
		}
		if (date.getTime() > end.getTime()) {
			status = "hold";
			statusText = ResourceUtil.getString(
					"kmImeeting.status.publish.hold", "km-imeeting");
		}
		meeting.put("status", status);
		meeting.put("statusText", statusText);
		String fdHost = "";
		String src = "";
		if (kmImeetingMain.getFdHost() != null) {
			fdHost += kmImeetingMain.getFdHost().getFdName();
			src = PersonInfoServiceGetter.getPersonHeadimageUrl(kmImeetingMain
					.getFdHost().getFdId());
		} else {
			src = PersonInfoServiceGetter.DEFAULT_IMG;
		}
		if (!PersonInfoServiceGetter.isFullPath(src)) {
			src = request.getContextPath() + src;
		}
		if (StringUtil.isNotNull(kmImeetingMain.getFdOtherHostPerson())) {
			fdHost += " " + kmImeetingMain.getFdOtherHostPerson();// 其他主持人
		}
		meeting.put("fdHost", fdHost);// 主持人
		meeting.put("hostsrc", src);// 主持人头像
		meeting.put("creatorId", kmImeetingMain.getDocCreator().getFdId());// 发起人ID
		meeting.put("creator", kmImeetingMain.getDocCreator().getFdName());// 发起人
		if (kmImeetingMain.getDocCreator().getFdParent() != null) {
			meeting.put("dept", kmImeetingMain.getDocCreator().getFdParent()
					.getFdName());// 预约部门
		}
		String fdPlaceName = "";
		if (kmImeetingMain.getFdPlace() != null) {
			meeting.put("fdPlaceId", kmImeetingMain.getFdPlace().getFdId());// 会议地点ID
			fdPlaceName += kmImeetingMain.getFdPlace().getFdName();
			meeting.put("fdPlaceDetail",
					kmImeetingMain.getFdPlace().getFdDetail());// 会议详情
		}
		if (StringUtil.isNotNull(kmImeetingMain.getFdOtherPlace())) {
			fdPlaceName += " " + kmImeetingMain.getFdOtherPlace();
		}
		meeting.put("fdPlaceName", fdPlaceName);// 会议地点

		// 分会场
		List<KmImeetingRes> fdVicePlaces = kmImeetingMain.getFdVicePlaces();
		if (fdVicePlaces != null) {
			List<String> vicePlacesIds = new ArrayList();
			List<String> vicePlacesNames = new ArrayList();
			for (KmImeetingRes res : fdVicePlaces) {
				vicePlacesIds.add(res.getFdId());
				vicePlacesNames.add(res.getFdName());
			}
			meeting.put("fdVicePlacesIds", StringUtil.join(vicePlacesIds, ";"));
			meeting.put("fdVicePlacesNames",
					StringUtil.join(vicePlacesNames, ";"));
		}

		if (StringUtil.isNotNull(kmImeetingMain.getFdOtherVicePlace())) {
			meeting.put("fdOtherVicePlace",
					kmImeetingMain.getFdOtherVicePlace());
			meeting.put("fdOtherVicePlaceCoord",
					kmImeetingMain.getFdOtherVicePlaceCoord());
		}

		meeting.put("type", "meeting");
		meeting.put("href",
				"/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId="
						+ kmImeetingMain.getFdId());
		meeting.put("docStatus", kmImeetingMain.getDocStatus());
		String recurrenceStr = kmImeetingMain.getFdRecurrenceStr();
		if (StringUtil.isNotNull(recurrenceStr)) {
			meeting.put("isCycle", "true");
			Map<String, String> infos = RecurrenceUtil.getRepeatInfo(
					recurrenceStr, kmImeetingMain.getFdHoldDate());
			StringBuilder sb = new StringBuilder();
			if (StringUtil.isNotNull(infos.get("INTERVAL"))) {
				sb.append(infos.get("INTERVAL")).append(" ");
			}
			if (StringUtil.isNotNull(infos.get("BYDAY"))) {
				sb.append(infos.get("BYDAY")).append(" ");
			}
			if (StringUtil.isNotNull(infos.get("COUNT"))) {
				sb.append(infos.get("COUNT")).append(" ");
			} else if (StringUtil.isNotNull(infos.get("UNTIL"))) {
				sb.append(infos.get("UNTIL")).append(" ");
			} else {
				sb.append(ResourceUtil.getString("calendar.never",
						"km-imeeting", ResourceUtil.getLocaleByUser()));
			}
			meeting.put("fdRepeatInfo", sb.toString());
		}
		meeting.put("fdRemark", kmImeetingMain.getFdRemark());
		return meeting;
	}

	// 移动端日历界面 会议安排JSON
	public static JSONObject genMobileMeetingJSON(KmImeetingMain kmImeetingMain,
			HttpServletRequest request) throws Exception {
		logger.debug("ImeetingCalendarUtil-genMobileMeetingJSON-run");
		JSONObject meeting = new JSONObject();
		try {
			Date date = new Date();
			logger.debug("fdId:" + kmImeetingMain.getFdId());
			meeting.put("fdId", kmImeetingMain.getFdId());
			logger.debug(
					"title:" + kmImeetingMain.getFdName() + "titleScapeEnd:"
							+ StringUtil.XMLEscape(kmImeetingMain.getFdName()));
			meeting.put("title",
					StringUtil.XMLEscape(kmImeetingMain.getFdName()));
			Date start = kmImeetingMain.getFdHoldDate();
			logger.debug(
					"start:" + DateUtil.convertDateToString(start,
							DateUtil.TYPE_DATETIME,
							UserUtil.getKMSSUser().getLocale()));
			meeting.put("start", DateUtil.convertDateToString(start,
					DateUtil.TYPE_DATETIME,
					UserUtil.getKMSSUser().getLocale()));

			Date end = kmImeetingMain.getFdEarlyFinishDate() != null
					? kmImeetingMain.getFdEarlyFinishDate()
					: kmImeetingMain.getFdFinishDate();
			meeting.put("end", DateUtil.convertDateToString(end,
					DateUtil.TYPE_DATETIME,
					UserUtil.getKMSSUser().getLocale()));

			meeting.put("allDay", false);
			String status = "holding", statusText = ResourceUtil.getString(
					"kmImeeting.status.publish.holding",
					"km-imeeting");
			if (date.getTime() < start.getTime()) {
				status = "unHold";
				statusText = ResourceUtil.getString(
						"kmImeeting.status.publish.unHold", "km-imeeting");
			}
			if (date.getTime() > end.getTime()
					|| "41".equals(kmImeetingMain.getDocStatus())) {
				status = "hold";
				statusText = ResourceUtil.getString(
						"kmImeeting.status.publish.hold", "km-imeeting");
			}
			meeting.put("status", status);
			meeting.put("statusText", statusText);
			String fdHost = "";
			if (kmImeetingMain.getFdHost() != null) {
				fdHost += kmImeetingMain.getFdHost().getFdName();
			}
			if (StringUtil.isNotNull(kmImeetingMain.getFdOtherHostPerson())) {
				fdHost += " " + kmImeetingMain.getFdOtherHostPerson();// 其他主持人
			}

			logger.debug(
					"fdHost:" + fdHost + "fdHostScapeEnd:"
							+ StringUtil.XMLEscape(fdHost));
			meeting.put("fdHost", StringUtil.XMLEscape(fdHost));// 主持人

			logger.debug(
					"creator:" + kmImeetingMain.getDocCreator().getFdName()
							+ "creatorScapeEnd:"
							+ StringUtil
									.XMLEscape(kmImeetingMain.getDocCreator()
											.getFdName()));
			meeting.put("creator", StringUtil
					.XMLEscape(kmImeetingMain.getDocCreator().getFdName()));// 发起人
			String fdPlaceName = "";
			if (kmImeetingMain.getFdPlace() != null) {
				fdPlaceName += kmImeetingMain.getFdPlace().getFdName();
				logger.debug(
						"fdPlaceDetail:"
								+ kmImeetingMain.getFdPlace().getFdDetail()
								+ "fdPlaceDetailScapeEnd:"
								+ StringUtil.XMLEscape(
										kmImeetingMain.getFdPlace()
												.getFdDetail()));
				meeting.put("fdPlaceDetail",
						StringUtil.XMLEscape(
								kmImeetingMain.getFdPlace().getFdDetail()));// 会议详情
			}
			if (StringUtil.isNotNull(kmImeetingMain.getFdOtherPlace())) {
				fdPlaceName += " " + kmImeetingMain.getFdOtherPlace();
			}
			meeting.put("fdPlaceName", fdPlaceName);// 会议地点

			// 分会场
			List<KmImeetingRes> fdVicePlaces = kmImeetingMain.getFdVicePlaces();
			if (fdVicePlaces != null) {
				List<String> vicePlacesIds = new ArrayList();
				List<String> vicePlacesNames = new ArrayList();
				for (KmImeetingRes res : fdVicePlaces) {
					vicePlacesIds.add(res.getFdId());
					vicePlacesNames.add(res.getFdName());
				}
				meeting.put("fdVicePlacesIds",
						StringUtil.join(vicePlacesIds, ";"));
				meeting.put("fdVicePlacesNames",
						StringUtil.join(vicePlacesNames, ";"));
			}

			if (StringUtil.isNotNull(kmImeetingMain.getFdOtherVicePlace())) {
				logger.debug(
						"fdOtherVicePlace:"
								+ kmImeetingMain.getFdOtherVicePlace()
								+ "fdOtherVicePlaceScapeEnd:"
								+ StringUtil.XMLEscape(
										kmImeetingMain.getFdOtherVicePlace()));
				meeting.put("fdOtherVicePlace",
						StringUtil.XMLEscape(
								kmImeetingMain.getFdOtherVicePlace()));
				
				logger.debug(
						"fdOtherVicePlaceCoord:" + kmImeetingMain.getFdOtherVicePlaceCoord() + "fdOtherVicePlaceCoordScapeEnd:"
								+ StringUtil.XMLEscape(
										kmImeetingMain.getFdOtherVicePlaceCoord()));
				meeting.put("fdOtherVicePlaceCoord",
						StringUtil.XMLEscape(
								kmImeetingMain.getFdOtherVicePlaceCoord()));
			}

			meeting.put("type", "meeting");
			meeting.put("href",
					"/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId="
							+ kmImeetingMain.getFdId());
			meeting.put("docStatus", kmImeetingMain.getDocStatus());
			meeting.put("fdRemark", kmImeetingMain.getFdRemark());// 备注
			logger.debug("genMobileMeetingJSON:" + meeting);
		} catch (Exception e) {
			logger.debug("genMobileMeetingJSON-Exception:" + e.getMessage());
		}
		return meeting;
	}

	// 移动端列表页面 会议安排JSON
	public static JSONObject genListMeetingJSON(KmImeetingMain kmImeetingMain,
			HttpServletRequest request) throws Exception {
		logger.debug("genListMeetingJSONStart");
		JSONObject meeting = genMobileMeetingJSON(kmImeetingMain, request);

		Date start = kmImeetingMain.getFdHoldDate();
		// 那一年、那一月
		meeting.put("yearMonth", DateUtil.convertDateToString(start,
				"yearMonth", UserUtil.getKMSSUser().getLocale()));

		// 历时
		Double fdHoldDuration = kmImeetingMain.getFdHoldDuration();
		if (fdHoldDuration != null) {
			Double time = new Double(fdHoldDuration);
			int division = 60 * 1000;
			Double min = time / division;
			meeting.put("minDura", min.intValue());
		}
		// 参加人员数量
		meeting.put("attendPersonSum", getAttendSum(kmImeetingMain));
		Date end = kmImeetingMain.getFdEarlyFinishDate() != null
				? kmImeetingMain.getFdEarlyFinishDate()
				: kmImeetingMain.getFdFinishDate();

		// 哪一天
		if (DateUtil.getDateNumber(start) == DateUtil.getDateNumber(end)) {
			meeting.put("day", DateUtil.convertDateToString(start, "dd"));
			// 星期
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(start);
			int weekday = calendar.get(Calendar.DAY_OF_WEEK);
			meeting.put("weekday", weekday);
		} else {
			meeting.put("day", DateUtil.convertDateToString(start, "dd") + "~"
					+ DateUtil.convertDateToString(end, "dd"));
		}
		logger.debug("genListMeetingJSONEnd:" + meeting);
		return meeting;
	}

	private static int getAttendSum(KmImeetingMain kmImeetingMain)
			throws Exception {
		int count = 0;
		List<SysOrgElement> diffUnionList = new ArrayList<SysOrgElement>();
		// 主持人
		List<SysOrgElement> persons = new ArrayList<>();
		SysOrgPerson fdHost = kmImeetingMain.getFdHost();
		if (fdHost != null) {
			persons.add(fdHost);
		}
		// 参加人员
		List<SysOrgElement> fdAttendPersons = kmImeetingMain
				.getFdAttendPersons();
		ArrayUtil.concatTwoList(fdAttendPersons, persons);
		// 列席人员
		List<SysOrgElement> fdParticipantPersons = kmImeetingMain
				.getFdParticipantPersons();
		ArrayUtil.concatTwoList(fdParticipantPersons, persons);
		// 纪要人员
		SysOrgElement fdSummaryInputPerson = kmImeetingMain
				.getFdSummaryInputPerson();
		if (fdSummaryInputPerson != null) {
			if (!persons.contains(fdSummaryInputPerson)) {
				persons.add(fdSummaryInputPerson);
			}
		}
		ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
				.getBean("sysOrgCoreService");
		List<SysOrgElement> attend = sysOrgCoreService
				.expandToPerson(persons);
		if (attend != null && !attend.isEmpty()) {
			ArrayUtil.concatTwoList(attend, diffUnionList);
		}

		List<SysOrgElement> tmpList = new ArrayList<SysOrgElement>();
		for (KmImeetingAgenda kmImeetingAgenda : kmImeetingMain
				.getKmImeetingAgendas()) {
			// 汇报人
			if (kmImeetingAgenda.getDocReporter() != null) {
				tmpList.add(kmImeetingAgenda.getDocReporter());
				ArrayUtil.concatTwoList(tmpList, diffUnionList);
			}
			// 建议列席单位的会议联络员
			if (kmImeetingAgenda.getFdAttendUnit() != null
					&& !kmImeetingAgenda.getFdAttendUnit().isEmpty()) {
				List fdAttendUnit = kmImeetingAgenda.getFdAttendUnit();
				for (int i = 0; i < fdAttendUnit.size(); i++) {
					KmImissiveUnit kmImissiveUnit = (KmImissiveUnit) fdAttendUnit
							.get(i);
					if (kmImissiveUnit.getFdMeetingLiaison() != null) {
						ArrayUtil.concatTwoList(
								kmImissiveUnit.getFdMeetingLiaison(),
								diffUnionList);
					}
				}
			}
			// 建议旁听单位的会议联络员
			if (kmImeetingAgenda.getFdListenUnit() != null
					&& !kmImeetingAgenda.getFdListenUnit().isEmpty()) {
				List fdListenUnit = kmImeetingAgenda.getFdListenUnit();
				for (int i = 0; i < fdListenUnit.size(); i++) {
					KmImissiveUnit kmImissiveUnit = (KmImissiveUnit) fdListenUnit
							.get(i);
					if (kmImissiveUnit.getFdMeetingLiaison() != null) {
						ArrayUtil.concatTwoList(
								kmImissiveUnit.getFdMeetingLiaison(),
								diffUnionList);
					}
				}
			}
		}
		if (!diffUnionList.isEmpty() && diffUnionList.size() > 0) {
			List attendIds = sysOrgCoreService.expandToPersonIds(diffUnionList);
			if (attendIds != null && !attendIds.isEmpty()) {
				count += attendIds.size();
			}
		}
		return count;
	}

	// 会议预约JSON
	public static JSONObject genMeetingBookJSON(KmImeetingBook kmImeetingBook,
			HttpServletRequest request) {
		JSONObject book = new JSONObject();
		Date date = new Date();
		book.put("fdId", kmImeetingBook.getFdId());
		book.put("title", kmImeetingBook.getFdName());
		Date start = kmImeetingBook.getFdHoldDate();
		book.put("start",
				DateUtil.convertDateToString(start, DateUtil.TYPE_DATETIME,
						UserUtil.getKMSSUser().getLocale()));

		Date end = kmImeetingBook.getFdFinishDate();
		book.put("end", DateUtil.convertDateToString(end,
				DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser().getLocale()));
		book.put("allDay", false);
		String status = "holding",
				statusText = ResourceUtil.getString(
						"kmImeeting.status.publish.holding", "km-imeeting");
		if (date.getTime() < start.getTime()) {
			status = "unHold";
			statusText = ResourceUtil.getString(
					"kmImeeting.status.publish.unHold", "km-imeeting");
		}
		if (date.getTime() > end.getTime()) {
			status = "hold";
			statusText = ResourceUtil
					.getString("kmImeeting.status.publish.hold", "km-imeeting");
		}
		book.put("status", status);
		book.put("statusText", statusText);
		String fdHost = "";
		String src = "";
		if (kmImeetingBook.getDocCreator() != null) {
			fdHost += kmImeetingBook.getDocCreator().getFdName();
			src = PersonInfoServiceGetter.getPersonHeadimageUrl(
					kmImeetingBook.getDocCreator().getFdId());
		} else {
			src = PersonInfoServiceGetter.DEFAULT_IMG;
		}
		if (!PersonInfoServiceGetter.isFullPath(src)) {
			src = request.getContextPath() + src;
		}
		book.put("fdHost", fdHost);// 主持人
		book.put("hostsrc", src);// 主持人头像
		book.put("creatorId", kmImeetingBook.getDocCreator().getFdId());// 发起人ID
		book.put("creator", kmImeetingBook.getDocCreator().getFdName());// 发起人
		if (kmImeetingBook.getDocCreator().getFdParent() != null) {
			book.put("dept",
					kmImeetingBook.getDocCreator().getFdParent().getFdName());// 预约部门
		}
		String fdPlaceName = "";
		if (kmImeetingBook.getFdPlace() != null) {
			book.put("fdPlaceId", kmImeetingBook.getFdPlace().getFdId());// 会议地点ID
			fdPlaceName += kmImeetingBook.getFdPlace().getFdName();
			book.put("fdPlaceDetail",
					kmImeetingBook.getFdPlace().getFdDetail());// 会议详情
		}
		book.put("fdPlaceName", fdPlaceName);// 会议地点
		book.put("type", "book");
		book.put("href",
				"/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=view&fdId="
						+ kmImeetingBook.getFdId());
		book.put("recurrenceStr", kmImeetingBook.getFdRecurrenceStr());

		if (kmImeetingBook.getFdHasExam() != null) {
			if (kmImeetingBook.getFdHasExam() == true) {
				book.put("fdHasExam", "true");
			} else {
				book.put("fdHasExam", "false");
			}
		} else {
			book.put("fdHasExam", "wait");
		}
		String recurrenceStr = kmImeetingBook.getFdRecurrenceStr();
		if (StringUtil.isNotNull(recurrenceStr)) {
			book.put("isCycle", "true");
			Map<String, String> infos = RecurrenceUtil.getRepeatInfo(
					recurrenceStr, kmImeetingBook.getFdHoldDate());
			StringBuilder sb = new StringBuilder();
			if (StringUtil.isNotNull(infos.get("INTERVAL"))) {
				sb.append(infos.get("INTERVAL")).append(" ");
			}
			if (StringUtil.isNotNull(infos.get("BYDAY"))) {
				sb.append(infos.get("BYDAY")).append(" ");
			}
			if (StringUtil.isNotNull(infos.get("COUNT"))) {
				sb.append(infos.get("COUNT")).append(" ");
			} else if (StringUtil.isNotNull(infos.get("UNTIL"))) {
				sb.append(infos.get("UNTIL")).append(" ");
			} else {
				sb.append(ResourceUtil.getString("calendar.never",
						"km-imeeting", ResourceUtil.getLocaleByUser()));
			}
			book.put("fdRepeatInfo", sb.toString());
		}
		book.put("fdRemark", kmImeetingBook.getFdRemark());
		return book;
	}

	// 移动端日历页面 会议预约JSON
	public static JSONObject genMobileMeetingBookJSON(
			KmImeetingBook kmImeetingBook, HttpServletRequest request) {
		JSONObject book = new JSONObject();
		Date date = new Date();
		book.put("fdId", kmImeetingBook.getFdId());
		book.put("title", StringUtil.XMLEscape(kmImeetingBook.getFdName()));
		Date start = kmImeetingBook.getFdHoldDate();
		String startStr = DateUtil.convertDateToString(start,
				DateUtil.TYPE_DATETIME,
				UserUtil.getKMSSUser().getLocale());
		String fdHoldDate = DateUtil.convertDateToString(start,
				DateUtil.TYPE_DATE,
				UserUtil.getKMSSUser().getLocale());
		String fdHoldTime = DateUtil.convertDateToString(start,
				DateUtil.TYPE_TIME,
				UserUtil.getKMSSUser().getLocale());
		book.put("start", startStr);
		Date end = kmImeetingBook.getFdFinishDate();
		String endStr = DateUtil.convertDateToString(end,
				DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser().getLocale());
		String fdFinishDate = DateUtil.convertDateToString(end,
				DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
		String fdFinishTime = DateUtil.convertDateToString(end,
				DateUtil.TYPE_TIME, UserUtil.getKMSSUser().getLocale());
		book.put("end", endStr);
		book.put("allDay", false);
		String status = "holding",
				statusText = ResourceUtil.getString(
						"kmImeeting.status.publish.holding", "km-imeeting");
		if (date.getTime() < start.getTime()) {
			status = "unHold";
			statusText = ResourceUtil.getString(
					"kmImeeting.status.publish.unHold", "km-imeeting");
		}
		if (date.getTime() > end.getTime()) {
			status = "hold";
			statusText = ResourceUtil
					.getString("kmImeeting.status.publish.hold", "km-imeeting");
		}
		book.put("status", status);
		book.put("statusText", statusText);
		String fdHost = "";
		if (kmImeetingBook.getDocCreator() != null) {
			fdHost += kmImeetingBook.getDocCreator().getFdName();
		}
		book.put("fdHost", fdHost);// 主持人
		book.put("creator", kmImeetingBook.getDocCreator().getFdName());// 发起人
		String fdPlaceName = "";
		if (kmImeetingBook.getFdPlace() != null) {
			fdPlaceName += kmImeetingBook.getFdPlace().getFdName();
		}
		book.put("fdPlaceName", fdPlaceName);// 会议地点
		book.put("type", "book");
		book.put("href",
				"/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=view&fdId="
						+ kmImeetingBook.getFdId() + "&fdHoldDate=" + fdHoldDate
						+ "&fdHoldTime=" + fdHoldTime
						+ "&fdFinishDate=" + fdFinishDate + "&fdFinishTime="
						+ fdFinishTime);
		if (kmImeetingBook.getFdHasExam() != null) {
			if (kmImeetingBook.getFdHasExam() == true) {
				book.put("fdHasExam", "true");
			} else {
				book.put("fdHasExam", "false");
			}
		} else {
			book.put("fdHasExam", "wait");
		}
		book.put("fdRemark", kmImeetingBook.getFdRemark());
		return book;
	}

	// 移动端列表页面 会议预约JSON
	public static JSONObject genListMeetingBookJSON(
			KmImeetingBook kmImeetingBook,
			HttpServletRequest request) {
		JSONObject book = genMobileMeetingBookJSON(kmImeetingBook, request);
		Date start = kmImeetingBook.getFdHoldDate();
		// 那一年、那一月
		book.put("yearMonth", DateUtil.convertDateToString(start,
				"yearMonth", UserUtil.getKMSSUser().getLocale()));
		// 历时
		Double fdHoldDuration = kmImeetingBook.getFdHoldDuration();
		if (fdHoldDuration != null) {
			Double min = fdHoldDuration * 60;
			book.put("minDura", min.intValue());
		}
		// 参加人员数量
		book.put("attendPersonSum", 0);
		Date end = kmImeetingBook.getFdFinishDate();
		// 哪一天
		if (DateUtil.getDateNumber(start) == DateUtil.getDateNumber(end)) {
			book.put("day", DateUtil.convertDateToString(start, "dd"));
			// 星期
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(start);
			int weekday = calendar.get(Calendar.DAY_OF_WEEK);
			book.put("weekday", weekday);
		} else {
			book.put("day", DateUtil.convertDateToString(start, "dd") + "~"
					+ DateUtil.convertDateToString(end, "dd"));
		}
		return book;
	}

	public static JSONObject rescalendar(Page resourcePage,
			List<KmImeetingMain> mains, List<KmImeetingBook> books,
			HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		// 会议室分类信息
		result.put("category", genCategoryJSON(request));
		// 会议室信息
		JSONObject json = new JSONObject();
		json.put("total", resourcePage.getTotalrows());
		json.put("rowsize", resourcePage.getRowsize());
		result.put("resource", json);
		// 会议信息
		List<KmImeetingRes> resources = resourcePage.getList();
		result.put("main",
				genResCalendarJSON(resources, mains, books, request));
		return result;
	}

	// 生成资源日历所需要的JSON
	public static JSONObject genResCalendarJSON(List<KmImeetingRes> resources,
			List<KmImeetingMain> mains, List<KmImeetingBook> books,
			HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		for (KmImeetingRes resource : resources) {
			JSONObject json = new JSONObject();
			json.put("fdId", resource.getFdId());
			json.put("name", StringUtil.XMLEscape(resource.getFdName()));// 会议室名称
			json.put("detail", resource.getFdDetail());// 会议室详情
			json.put("floor", resource.getFdAddressFloor());// 地点楼层
			json.put("seats", resource.getFdSeats());// 容纳人数
			json.put("hierarchyId", resource.getDocCategory()
					.getFdHierarchyId());// hierarchyId
			json.put("list", new JSONArray());// 使用该资源的会议列表
			Boolean fdNeedExam = resource.getFdNeedExamFlag();
			json.put("fdNeedExam",
					fdNeedExam != null ? fdNeedExam.booleanValue() : "");
			result.put(resource.getFdId(), json);
		}
		Queue queueMeeting = convertToQueue(mains);
		Queue queueBook = convertToQueue(books);
		KmImeetingMain main = (KmImeetingMain) queueMeeting.poll();
		KmImeetingBook book = (KmImeetingBook) queueBook.poll();
		// 按召开时间排序输出
		while (main != null || book != null) {
			if (book == null || main != null && main.getFdHoldDate().getTime() < book.getFdHoldDate().getTime()) {
				if (main.getFdPlace() != null && result.containsKey(main.getFdPlace().getFdId())) {
					JSONObject meeting = genMeetingJSON(main, request);
					JSONObject json = (JSONObject) result.get(main.getFdPlace().getFdId());
					JSONArray list = (JSONArray) json.get("list");
					list.add(meeting);
					sortMeetList(list);
				}
				List<KmImeetingRes> vicePlaces = main.getFdVicePlaces();
				if (!vicePlaces.isEmpty()) {
					JSONObject meeting = genMeetingJSON(main, request);
					for (KmImeetingRes viceRes : vicePlaces) {
						if (viceRes != null && result.containsKey(viceRes.getFdId())) {
							JSONObject json = (JSONObject) result.get(viceRes.getFdId());
							JSONArray list = (JSONArray) json.get("list");
							list.add(meeting);
							sortMeetList(list);
						}
					}
				}
				main = (KmImeetingMain) queueMeeting.poll();
			} else  if(book !=null) {
				Boolean fdHasExam = book.getIsNotify() == null ? true : (book.getFdHasExam() == null ? true : book.getFdHasExam());
				if (book.getFdPlace() != null && fdHasExam && result.containsKey(book.getFdPlace().getFdId())) {
					// #148774 解决拒绝的会议室预约仍然显示
					JSONObject b = genBookJSON(book);
					JSONObject json = (JSONObject) result.get(book.getFdPlace().getFdId());
					JSONArray list = (JSONArray) json.get("list");
					list.add(b);
					sortMeetList(list);
				}
				book = (KmImeetingBook) queueBook.poll();
			}
		}
		//根据召开时间排序
		return result;
	}

	/**
	 * 会议排序
	 * @param list
	 * @return: void
	 * @author: wangjf
	 * @time: 2022/7/18 5:36 下午
	 */
	private static void sortMeetList(JSONArray list){
		Collections.sort(list, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject o1, JSONObject o2) {
				Date start1 = DateUtil.convertStringToDate(o1.getString("start"), DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser().getLocale());
				Date start2 = DateUtil.convertStringToDate(o2.getString("start"), DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser().getLocale());
				return (int)(start1.getTime()-start2.getTime());
			}
		});

	}

	// 会议室分类JSON
	public static JSONObject genCategoryJSON(HttpServletRequest request)
			throws Exception {
		JSONObject json = new JSONObject();
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock(" kmImeetingResCategory.hbmParent is null ");
		hql.setOrderBy(" kmImeetingResCategory.fdOrder ");
		if (hql.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
		}
		IKmImeetingResCategoryService kmImeetingResCategoryService = (IKmImeetingResCategoryService) SpringBeanUtil
				.getBean("kmImeetingResCategoryService");
		List<KmImeetingResCategory> list = kmImeetingResCategoryService
				.findValue(hql);

		String modelName = "KmImeetingResCategory";
		String tableName = ModelUtil.getModelTableName(modelName);
		List<String> hierarchyReaderIds = findHierarchyReaderIds(
				kmImeetingResCategoryService, modelName, tableName);
		for (KmImeetingResCategory category : list) {
			boolean flag = UserUtil.getKMSSUser().isAdmin() ? true
					: hierarchyReaderIds.contains(category.getFdHierarchyId());
			if (!flag) {
				continue;
			}
			JSONObject j = new JSONObject();
			j.put("name", StringUtil.XMLEscape(category.getFdName()));
			json.put(category.getFdHierarchyId(), j);
		}
		return json;
	}

	public static Queue<Object> convertToQueue(List<?> list) {
		Queue<Object> queue = new LinkedList();
		for (Object item : list) {
			queue.offer(item);
		}
		return queue;
	}

	// 会议室预约JSON
	public static JSONObject genBookJSON(KmImeetingBook kmImeetingBook) {
		JSONObject book = new JSONObject();
		book.put("fdId", kmImeetingBook.getFdId());
		book.put("title", "("
				+ ResourceUtil.getString("kmImeetingBook.book", "km-imeeting")
				+ ")" + kmImeetingBook.getFdName());
		Date start = kmImeetingBook.getFdHoldDate();
		book.put("start", DateUtil.convertDateToString(start,
				DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser().getLocale()));
		Date end = kmImeetingBook.getFdFinishDate();
		book.put("end", DateUtil.convertDateToString(end,
				DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser().getLocale()));
		if (kmImeetingBook.getFdPlace() != null) {
			book.put("fdPlaceId", kmImeetingBook.getFdPlace().getFdId());
			book.put("fdPlaceName", kmImeetingBook.getFdPlace().getFdName());
			book.put("fdPlaceDetail",
					kmImeetingBook.getFdPlace().getFdDetail());// 会议详情
		}
		book.put("creatorId", kmImeetingBook.getDocCreator().getFdId());// 发起人ID
		book.put("creator", kmImeetingBook.getDocCreator().getFdName());// 预约人
		if (kmImeetingBook.getDocCreator().getFdParent() != null) {
			book.put("dept", kmImeetingBook.getDocCreator().getFdParent()
					.getFdName());// 预约部门
		}
		book.put("fdRemark", kmImeetingBook.getFdRemark());// 备注
		book.put("type", "book");
		if (kmImeetingBook.getIsNotify() != null) {
			Boolean fdHasExam = kmImeetingBook.getFdHasExam();
			book.put("fdHasExam",
					fdHasExam != null ? String.valueOf(fdHasExam.booleanValue())
							: "wait");
		} else {
			book.put("fdHasExam", "true");
		}
		String recurrenceStr = kmImeetingBook.getFdRecurrenceStr();
		book.put("recurrenceStr", recurrenceStr);
		if (StringUtil.isNotNull(recurrenceStr)) {
			book.put("isCycle", "true");
			Map<String, String> infos = RecurrenceUtil.getRepeatInfo(
					recurrenceStr, kmImeetingBook.getFdHoldDate());
			StringBuilder sb = new StringBuilder();
			if (StringUtil.isNotNull(infos.get("INTERVAL"))) {
				sb.append(infos.get("INTERVAL")).append(" ");
			}
			if (StringUtil.isNotNull(infos.get("BYDAY"))) {
				sb.append(infos.get("BYDAY")).append(" ");
			}
			if (StringUtil.isNotNull(infos.get("COUNT"))) {
				sb.append(infos.get("COUNT")).append(" ");
			} else if (StringUtil.isNotNull(infos.get("UNTIL"))) {
				sb.append(infos.get("UNTIL")).append(" ");
			} else {
				sb.append(ResourceUtil.getString("calendar.never",
						"km-imeeting", ResourceUtil.getLocaleByUser()));
			}
			book.put("fdRepeatInfo", sb.toString());
		}
		return book;
	}

	public static List findHierarchyReaderIds(IBaseService service,
			String modelName, String tableName) throws Exception {
		// 通过HQL查询有权限的层级ID
		String hql = "select distinct " + tableName + ".fdHierarchyId from "
				+ modelName + " " + tableName
				+ " left join " + tableName + ".authAllEditors editors";
		hql += " left join " + tableName + ".authAllReaders readers";
		hql += " where (editors.fdId in (:orgIds)";

		List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			// 如果是外部组织，只能查看有权限的模板
			if (SysOrgEcoUtil.isExternal()) {
				hql += " or readers.fdId in (:orgIds))";
			} else {
				orgIds.add(UserUtil.getEveryoneUser().getFdId());
				hql += " or readers.fdId is null or readers.fdId in (:orgIds))";
			}
		} else {
			hql += " or " + tableName + ".authReaderFlag= " + HibernateUtil.toBooleanValueString(true)
					+ " or readers.fdId in (:orgIds))";
		}
		Query query = service.getBaseDao().getHibernateSession()
				.createQuery(hql);
		query.setCacheable(true);
		query.setCacheMode(CacheMode.NORMAL);
		query.setCacheRegion("km-imeeting");
		query.setParameterList("orgIds", orgIds);
		return query.list();
	}
}
