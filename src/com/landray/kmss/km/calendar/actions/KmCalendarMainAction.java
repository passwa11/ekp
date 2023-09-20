package com.landray.kmss.km.calendar.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.calendar.cms.CMSPlugin;
import com.landray.kmss.km.calendar.cms.CMSPluginData;
import com.landray.kmss.km.calendar.cms.CMSSynchroService;
import com.landray.kmss.km.calendar.cms.interfaces.ICMSProvider;
import com.landray.kmss.km.calendar.cms.interfaces.IOAuthProvider;
import com.landray.kmss.km.calendar.constant.KmCalendarConstant;
import com.landray.kmss.km.calendar.forms.KmCalendarMainForm;
import com.landray.kmss.km.calendar.forms.KmCalendarMainGroupForm;
import com.landray.kmss.km.calendar.model.*;
import com.landray.kmss.km.calendar.service.*;
import com.landray.kmss.km.calendar.util.CalendarQueryContext;
import com.landray.kmss.km.calendar.util.CalendarSysOrgUtil;
import com.landray.kmss.km.calendar.util.Lunar;
import com.landray.kmss.km.calendar.util.Rfc2445Util;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.config.design.SysCfgModuleInfo;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.notify.forms.SysNotifyRemindMainForm;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyRemindMainCoreService;
import com.landray.kmss.sys.notify.interfaces.SysNotifyRemindMainContextForm;
import com.landray.kmss.sys.notify.model.SysNotifyRemindMain;
import com.landray.kmss.sys.notify.service.ISysNotifyRemindMainService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.poi.hssf.usermodel.*;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.tritonus.share.ArraySet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 日程管理主文档 Action
 *
 * @author
 * @version 1.0 2013-10-14
 */
public class KmCalendarMainAction extends ExtendAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmCalendarMainAction.class);

	//private static final SimpleDateFormat format = new SimpleDateFormat(
	//		"yyyyMMdd");
	// "Calendars"和"DateFormats"不应被声明为static
	private final SimpleDateFormat format2 = new SimpleDateFormat(
			"yyyy-MM-dd");
	//private static final SimpleDateFormat format3 = new SimpleDateFormat(
			//"yyyy-MM-dd HH:mm:ss.SSS");

	private static final String[] weeks = { "SU", "MO", "TU", "WE", "TH", "FR",
			"SA" };

	protected IKmCalendarMainService kmCalendarMainService;

	@Override
	protected IKmCalendarMainService getServiceImp(HttpServletRequest request) {
		if (kmCalendarMainService == null) {
			kmCalendarMainService = (IKmCalendarMainService) getBean("kmCalendarMainService");
		}
		return kmCalendarMainService;
	}

	protected ISysOrgCoreService sysOrgCoreService;

	protected ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	protected IKmCalendarPersonGroupService kmCalendarPersonGroupService;

	public IKmCalendarPersonGroupService getKmCalendarPersonGroupService() {
		if (kmCalendarPersonGroupService == null) {
			kmCalendarPersonGroupService = (IKmCalendarPersonGroupService) getBean(
					"kmCalendarPersonGroupService");
		}
		return kmCalendarPersonGroupService;
	}

	protected IKmCalendarMainGroupService kmCalendarMainGroupService;

	public IKmCalendarMainGroupService getKmCalendarMainGroupService() {
		if (kmCalendarMainGroupService == null) {
			kmCalendarMainGroupService = (IKmCalendarMainGroupService) getBean(
					"kmCalendarMainGroupService");
		}
		return kmCalendarMainGroupService;
	}

	protected IKmCalendarShareGroupService kmCalendarShareGroupService;

	public IKmCalendarShareGroupService getKmCalendarShareGroupService() {
		if (kmCalendarShareGroupService == null) {
			kmCalendarShareGroupService = (IKmCalendarShareGroupService) getBean("kmCalendarShareGroupService");
		}
		return kmCalendarShareGroupService;
	}

	protected IKmCalendarAuthService kmCalendarAuthService;

	public IKmCalendarAuthService getKmCalendarAuthService() {
		if (kmCalendarAuthService == null) {
			kmCalendarAuthService = (IKmCalendarAuthService) getBean("kmCalendarAuthService");
		}
		return kmCalendarAuthService;
	}

	protected IKmCalendarLabelService kmCalendarLabelService;

	public IKmCalendarLabelService getKmCalendarLabelService() {
		if (kmCalendarLabelService == null) {
			kmCalendarLabelService = (IKmCalendarLabelService) getBean("kmCalendarLabelService");
		}
		return kmCalendarLabelService;
	}

	protected ISysNotifyRemindMainService sysNotifyRemindMainService;

	public ISysNotifyRemindMainService getSysNotifyRemindMainService() {
		if (sysNotifyRemindMainService == null) {
			sysNotifyRemindMainService = (ISysNotifyRemindMainService) getBean("sysNotifyRemindMainService");
		}
		return sysNotifyRemindMainService;
	}

	private ISysNotifyRemindMainCoreService sysNotifyRemindMainCoreService = null;

	public ISysNotifyRemindMainCoreService getSysNotifyRemindMainCoreService() {
		if (sysNotifyRemindMainCoreService == null) {
			sysNotifyRemindMainCoreService = (ISysNotifyRemindMainCoreService) getBean("sysNotifyRemindMainCoreService");
		}
		return sysNotifyRemindMainCoreService;
	}

	/**
	 * 日程对象转为JSON
	 */
	private JSONObject genCalendarData(KmCalendarMain kmCalendarMain)
			throws Exception {
		JSONObject data = new JSONObject();
		data.put("id", kmCalendarMain.getFdId());
		data.put("title", kmCalendarMain.getDocSubject());
		String type = DateUtil.TYPE_DATETIME;
		Boolean isAlldayevent = kmCalendarMain.getFdIsAlldayevent();
		if (isAlldayevent == null || isAlldayevent) {
			type = DateUtil.TYPE_DATE;
		}
		Date docStartTime = kmCalendarMain.getDocStartTime();
		Date docEndTime = kmCalendarMain.getDocFinishTime();
		long startTimeNum = docStartTime.getTime();
		long endTimeNum = docEndTime.getTime();
		if (startTimeNum == endTimeNum) {
			endTimeNum = endTimeNum + (60 * 1000);
			data.put("isChangeEndTime", "true");
		} else {
			data.put("isChangeEndTime", "false");
		}
		docStartTime = DateUtil.getCalendar(startTimeNum).getTime();
		docEndTime = DateUtil.getCalendar(endTimeNum).getTime();
		String statDate = DateUtil.convertDateToString(
				docStartTime, type, null);
		data.put("start", statDate);
		if (kmCalendarMain.getDocFinishTime() != null) {
			String endDate = DateUtil.convertDateToString(
					docEndTime, type, null);
			data.put("end", endDate);
		}
		data.put("allDay", kmCalendarMain.getFdIsAlldayevent());
		KmCalendarLabel kmCalendarLabel = kmCalendarMain.getDocLabel();
		if (kmCalendarLabel != null) {
			data.put("labelId", kmCalendarMain.getDocLabel().getFdId());

			// 获取模块名称
		    SysDictModel sysDict = SysDataDict.getInstance().getModel(kmCalendarMain.getDocLabel().getFdModelName());
		    String labelName = (sysDict == null ? kmCalendarMain.getDocLabel().getFdName() : ResourceUtil.getString(sysDict.getMessageKey()));
			data.put("labelName", labelName);
			data.put("color", kmCalendarMain.getDocLabel().getFdColor());
		}else{
			data.put("labelId", "myEvent");
			data.put("labelName", ResourceUtil.getString("module.km.calendar.tree.my.calendar", "km-calendar"));
			data.put("color", "rgb(193, 156, 83)");
		}
		if (kmCalendarMain.getDocContent() != null) {
			data.put("content", kmCalendarMain.getDocContent());
		}
		data.put("type", kmCalendarMain.getFdType());
		// 头像
		if (kmCalendarMain.getDocOwner() != null) {
			String img = PersonInfoServiceGetter
					.getPersonHeadimageUrl(kmCalendarMain.getDocOwner()
							.getFdId());
			if (!PersonInfoServiceGetter.isFullPath(img)) {
				img = getServletContext().getContextPath() + img;
			}
			data.put("img", img);
			data.put("owner", kmCalendarMain.getDocOwner().getFdName());
			if (kmCalendarMain.getDocCreator() != kmCalendarMain.getDocOwner()) {
				data.put("ownerId", kmCalendarMain.getDocOwner().getFdId());
			} else {
				data.put("ownerId", kmCalendarMain.getDocOwner().getFdId());
			}
		}
		Map<String, Boolean> map = getAuth(kmCalendarMain.getDocOwner(),
				UserUtil.getUser());
		data.put("canRead", map.get("canRead"));
		data.put("canEditor", map.get("canEditor"));
		data.put("canModifier", map.get("canModifier"));
		// 判断是否为群组日程
		Boolean fdIsGroup = kmCalendarMain.getFdIsGroup();
		if (fdIsGroup != null) {
			if (fdIsGroup.booleanValue()) {
				data.put("labelId", "myGroupEvent");
				data.put("isGroup", kmCalendarMain.getFdIsGroup());
				data.put("color", "#00ccff");
				KmCalendarMainGroup mainGroupId = (KmCalendarMainGroup) getKmCalendarMainGroupService()
						.findMainGroupByMainId(kmCalendarMain.getFdId());
				StringBuffer sb = new StringBuffer("");
				List<KmCalendarMain> calendars = new ArrayList<KmCalendarMain>();
				String groupId = "";
				if (mainGroupId != null) {
					groupId = mainGroupId.getFdId();
					calendars = mainGroupId.getFdMainList();
				}

				data.put("mainGroupId", groupId);
				for (KmCalendarMain cal : calendars) {
					sb.append(cal.getDocOwner().getFdName() + ";");
				}
				String personNames = "";
				if (sb.length() > 0) {
					personNames = sb.substring(0, sb.length() - 1);
				}
				data.put("personNames", personNames);
			}
		}
		// 判断日程是否设置了提醒
		try {
			List sysNotifyRemindMainList = getSysNotifyRemindMainService()
					.getCoreModels(kmCalendarMain, null);
			if (sysNotifyRemindMainList != null
					&& !sysNotifyRemindMainList.isEmpty()) {
				data.put("hasSettedRemind", "true");
			} else {
				data.put("hasSettedRemind", "false");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String relationUrl = kmCalendarMain.getFdRelationUrl();
		if (StringUtil.isNotNull(relationUrl)) {
			data.put("relationUrl", relationUrl);
		}
		String fdLocation = kmCalendarMain.getFdLocation();
		if (StringUtil.isNotNull(fdLocation)) {
			data.put("fdLocation", fdLocation);
		}
		String fdAuthorityType = kmCalendarMain.getFdAuthorityType();
		if (StringUtil.isNotNull(fdAuthorityType)) {
			data.put("isPrivate", fdAuthorityType
					.equals(KmCalendarConstant.AUTHORITY_TYPE_PRIVATE));
		}
		data.put("href",
				"/km/calendar/km_calendar_main/kmCalendarMain.do?method=view&fdId=" + kmCalendarMain.getFdId());
		return data;
	}

	private Calendar clearTime(Calendar calendar) {
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar;
	}

	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String startTime = request.getParameter("start");
			String endTime = request.getParameter("end");
			Date docStartTime = new Date();
			Date docFinishTime = new Date();
			if (StringUtil.isNotNull(startTime)) {// 存在开始时间参数
				docStartTime = DateUtil.convertStringToDate(startTime,
						DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
				if (StringUtil.isNull(endTime)) {// 不存在结束时间参数,取开始时间后的一个月为结束时间
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(docStartTime);
					calendar.add(Calendar.MONTH, 1);
					calendar = clearTime(calendar);
					docFinishTime = calendar.getTime();
				}
			} else {// 不存在开始时间参数,取当天
				Calendar calendar = Calendar.getInstance();
				calendar = clearTime(calendar);
				docStartTime = calendar.getTime();
				if (StringUtil.isNull(endTime)) {// 不存在结束时间参数,取开始时间后的一个周为结束时间
					calendar.add(Calendar.WEEK_OF_YEAR, 1);
					docFinishTime = calendar.getTime();
				}
			}
			if (StringUtil.isNotNull(endTime)) {
				docFinishTime = DateUtil.convertStringToDate(endTime,
						DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
			}
			// 非重复日程
			List<KmCalendarMain> kmCalendars = getServiceImp(request)
					.getRangeCalendars(docStartTime, docFinishTime, null,
							false, UserUtil.getUser().getFdId(), null);
			// 重复日程
			List<KmCalendarMain> recurrenceCalendars = getServiceImp(request)
					.getRecurrenceCalendars(docStartTime, docFinishTime,
							UserUtil.getUser().getFdId(), null);
			kmCalendars.addAll(recurrenceCalendars);
			// 记录日志
			UserOperHelper.logFindAll(kmCalendars,
					getServiceImp(request).getModelName());
			Collections.sort(kmCalendars, new CalendarComparator());
			loadCalendarReminds(kmCalendars);
			request.setAttribute("calendars", kmCalendars);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("list", mapping, form, request,
					response);
		}
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String whereblock = "";
		String inStr = "'" + UserUtil.getUser().getFdId() + "'";
		List<String> calendarId = getServiceImp(request).getRelatedPersonCal(inStr);
		if (!ArrayUtil.isEmpty(calendarId)) {
			whereblock += " (kmCalendarMain.docOwner.fdId =:owner or " + HQLUtil.buildLogicIN("kmCalendarMain.fdId", calendarId) + " ) ";
		}else{
			whereblock += " (kmCalendarMain.docOwner.fdId =:owner) ";
		}
		hqlInfo.setParameter("owner", UserUtil.getUser().getFdId());
		String subject = request.getParameter("subject");
		if (StringUtil.isNotNull(subject)) {
			whereblock = StringUtil.linkString(whereblock, " and ", "kmCalendarMain.docSubject like :subject");
			hqlInfo.setParameter("subject", "%"+subject+"%");
		}
		hqlInfo.setWhereBlock(whereblock);
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		List list = getServiceImp(request).findList(hqlInfo);
	}

	public ActionForward page(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward actionForward = super.list(mapping, form, request, response);
		return getActionForward("page", mapping, form, request, response);
	}

	private void loadCalendarReminds(List<KmCalendarMain> calendars)
			throws Exception {
		ISysNotifyRemindMainService sysNotifyRemindMainService = (ISysNotifyRemindMainService) SpringBeanUtil
				.getBean("sysNotifyRemindMainService");
		for (KmCalendarMain kmCalendarMain : calendars) {
			List<SysNotifyRemindMain> reminds = sysNotifyRemindMainService
					.getSysNotifyRemindMainList(kmCalendarMain.getFdId());
			kmCalendarMain.getSysNotifyRemindMainContextModel()
					.setSysNotifyRemindMainList(reminds);
		}
	}

	/**
	 * 以JSON形式返回日程列表
	 */
	@Override
	public ActionForward data(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-data", true, getClass());
		KmssMessages messages = new KmssMessages();
		RequestContext requestCtx = new RequestContext(request);
		try {
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-type", "text/json;charset=UTF-8");
			response.getWriter()
					.write(getServiceImp(request).data(requestCtx).toString());
			// 初始化当前用户的阅读者
			SysOrgPerson person = UserUtil.getUser();
			KmCalendarAuth auth = null;
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"kmCalendarAuth.docCreator.fdId=:docCreatorFdId");
			hqlInfo.setParameter("docCreatorFdId", person.getFdId());
			List<KmCalendarAuth> auths = getKmCalendarAuthService()
					.findList(hqlInfo);
			KmCalendarBaseConfig config = new KmCalendarBaseConfig();
			String deptCanRead = config.getDeptCanRead();
			if (person.getFdParent() != null) {
				if (auths == null || auths.size() == 0) {
					auth = new KmCalendarAuth();
					auth.setDocCreator(person);
					List<SysOrgElement> readList = new ArrayList<SysOrgElement>();
					if (!"false".equals(deptCanRead)
							&& person.getFdParent() != null) {
						readList.add(person.getFdParent());
						auth.setAuthReaders(readList);
						// 同时加入KmCalendarAuthList
						KmCalendarAuthList authList = new KmCalendarAuthList();
						authList.setFdIsRead(true);
						authList.setFdIsShare(false);
						authList.setFdIsPartShare(false);
						authList.setFdPerson(readList);
						List<KmCalendarAuthList> authLists = new ArrayList<>();
						authLists.add(authList);
						auth.setKmCalendarAuthList(authLists);
					}
					getKmCalendarAuthService().add(auth);
				} else {
					auth = auths.get(0);
					if (!"false".equals(deptCanRead)
							&& person.getFdParent() != null) {
						boolean update = false;
						// 加入authReaders
						List<SysOrgElement> readList = auth.getAuthReaders();
						if (!readList.contains(person.getFdParent())) {
							readList.add(person.getFdParent());
							update = true;
						}
						// 同时加入KmCalendarAuthList
						List<KmCalendarAuthList> authLists = auth
								.getKmCalendarAuthList();
						if (authLists == null) {
							authLists = new ArrayList<>();
						}
						boolean addList = true;
						for (KmCalendarAuthList kmCalendarAuthList : authLists) {
							List<SysOrgElement> elements = kmCalendarAuthList
									.getFdPerson();
							boolean isRead = BooleanUtils
									.isTrue(kmCalendarAuthList.getFdIsRead());
							if (isRead && elements
									.contains(person.getFdParent())) {
								addList = false;
								break;
							}
						}
						if (addList) {
							KmCalendarAuthList authList = new KmCalendarAuthList();
							authList.setFdIsRead(true);
							authList.setFdIsShare(false);
							authList.setFdIsPartShare(false);
							List<SysOrgElement> reads = new ArrayList<SysOrgElement>();
							reads.add(person.getFdParent());
							authList.setFdPerson(reads);
							authLists.add(authList);
							auth.setKmCalendarAuthList(authLists);
							update = true;
						}
						// 更新
						if (update) {
							getKmCalendarAuthService().update(auth);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			UserOperHelper.setOperSuccess(false);
		}
		TimeCounter.logCurrentTime("Action-data", false, getClass());
		return null;
	}

	/**
	 * 构建选中标签ids
	 */
	private String buildLabelIds(String exceptLabelIds) throws Exception {
		String labelIds = null;
		List<KmCalendarLabel> labels = getKmCalendarLabelService()
				.getLabelsByPerson(UserUtil.getUser().getFdId());
		List<String> labelIdlist = new ArrayList<String>();
		labelIdlist.add(KmCalendarConstant.CALENDAR_MY_EVENT);
		labelIdlist.add(KmCalendarConstant.CALENDAR_MY_NOTE);
		labelIdlist.add(KmCalendarConstant.CALENDAR_MY_GROUP_EVENT);
		if (labels != null && labels.size() > 0) {
			for (KmCalendarLabel label : labels) {
				labelIdlist.add(label.getFdId());
			}
		}
		String[] exceptLabelIdArray = exceptLabelIds.split(",");
		for (String id : exceptLabelIdArray) {
			labelIdlist.remove(id);
		}
		if (labelIdlist.size() > 0) {
			labelIds = "";
			for (String labelId : labelIdlist) {
				labelIds += labelId + ",";
			}
		} else {
			labelIds = KmCalendarConstant.CALENDAR_NO_EVENT;
		}
		return labelIds;
	}

	/**
	 * 返回指定日期的日程、笔记
	 */
	public ActionForward getDayEvents(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String dateStr = request.getParameter("date");
		String calType = null;
		if (StringUtil.isNull(dateStr)) {
			logger.error("日程portlet数据请求出错，date的值都不能为空");
		} else {
			Date rangeStart = DateUtil.convertStringToDate(dateStr,
					DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
			Calendar c = Calendar.getInstance();
			c.setTime(rangeStart);
			c.add(Calendar.DAY_OF_MONTH, 1);
			Date rangeEnd = c.getTime();
			JSONArray jsonArray = new JSONArray();
			try {
				// 非重复日历
				List<KmCalendarMain> matchedKmCalendars = getServiceImp(request)
						.getRangeCalendars(rangeStart, rangeEnd,calType, false,
								UserUtil.getUser().getFdId(), null);
				// 重复日历
				List<KmCalendarMain> calendars = getServiceImp(request)
						.getRecurrenceCalendars(rangeStart, rangeEnd,
								UserUtil.getUser().getFdId(), null);

				ArrayUtil.concatTwoList(calendars, matchedKmCalendars);
				Collections.sort(matchedKmCalendars, new CalendarComparator());

				for (KmCalendarMain kmCalendarMain : matchedKmCalendars) {
					JSONObject data = new JSONObject();
					data.put("type", kmCalendarMain.getFdType());
					data.put("docSubject", kmCalendarMain.getDocSubject());
					data.put("isAllday", kmCalendarMain.getFdIsAlldayevent());

					String type = DateUtil.PATTERN_DATETIME;
					Boolean isAlldayevent = kmCalendarMain.getFdIsAlldayevent();
					if (isAlldayevent == null || isAlldayevent) {
						type = DateUtil.PATTERN_DATE;
					}
					data.put("startTime", DateUtil.convertDateToString(
							kmCalendarMain.getDocStartTime(), type));
					if (kmCalendarMain.getDocFinishTime() != null) {
						data.put("endTime", DateUtil.convertDateToString(
								kmCalendarMain.getDocFinishTime(), type));
					}
					if (StringUtil.isNotNull(kmCalendarMain.getFdRelationUrl())) {
						data.put("url", kmCalendarMain.getFdRelationUrl());
						data.put("pdaUrl",
								"/km/calendar/km_calendar_main/kmCalendarMain.do?method=view&fdId="
										+ kmCalendarMain.getFdId());
						data.put("hasRelation", true);
					} else {
						data.put("url",
								"/km/calendar/km_calendar_main/kmCalendarMain.do?method=edit&fdId="
										+ kmCalendarMain.getFdId());
						data.put("pdaUrl",
								"/km/calendar/km_calendar_main/kmCalendarMain.do?method=view&fdId="
										+ kmCalendarMain.getFdId());
						data.put("hasRelation", false);
					}
					jsonArray.add(data);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-type", "text/json;charset=UTF-8");
			response.getWriter().write(jsonArray.toString());
		}
		return null;
	}

	/**
	 * 返回指定时间段每天是否有日程
	 */
	public ActionForward getEventsByRange(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getEventsByRange", true, getClass());
		KmssMessages messages = new KmssMessages();
		RequestContext requestCtx = new RequestContext(request);
		try {
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-type", "text/json;charset=UTF-8");
			response.getWriter().write(getServiceImp(request)
					.getEventsByRange(requestCtx).toString());
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getEventsByRange", true, getClass());
		return null;
	}

	/**
	 * 返回一个月每天是否有日程
	 */
	public ActionForward getEventsByMonth(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		JSONArray datas = new JSONArray();

		if (StringUtil.isNull(year) || StringUtil.isNull(month)) {
			logger.error("日程portlet数据请求出错，year和month的值都不能为空");
		} else {
			String[] eventsOfEverDay = getEventsByMonth(Integer.parseInt(year),
					Integer.parseInt(month), request);
			for (int i = 0; i < eventsOfEverDay.length; i++) {
				if ("1".equals(eventsOfEverDay[i])) {
					datas.add(true);
				} else {
					datas.add(false);
				}
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(datas.toString());
		return null;
	}

	/**
	 * 获取某个月的每天是否有日程
	 *
	 * @param calendars
	 */
	private void updateEventsOfEverDay(List<KmCalendarMain> calendars,
			Date rangeStart, Date rangeEnd, String[] eventsOfEverDay) {
		Calendar c = Calendar.getInstance();
		int startDay;
		int endDay;
		Date start = null;
		Date end = null;
		for (KmCalendarMain kmCalendarMain : calendars) {
			Date docStartTime = kmCalendarMain.getDocStartTime();
			Date docFinishTime = kmCalendarMain.getDocFinishTime();
			if (docStartTime == null || docFinishTime == null) {
				logger.error("日程记录数据错误，开始时间和结束时间都不能为空，日程ID："
						+ kmCalendarMain.getFdId());
				continue;
			}
			if (docStartTime.before(rangeStart)) {
				start = rangeStart;
			} else {
				start = docStartTime;
			}
			if (docFinishTime.before(rangeEnd)) {
				end = docFinishTime;
			} else {
				end = rangeEnd;
			}

			c.setTime(start);
			startDay = c.get(Calendar.DAY_OF_MONTH);
			c.setTime(end);
			if (end.after(start)
					&& kmCalendarMain.getFdIsAlldayevent() == false) {
				c.add(Calendar.SECOND, -1);
			}
			endDay = c.get(Calendar.DAY_OF_MONTH);

			for (int i = startDay; i <= endDay; i++) {
				eventsOfEverDay[i - 1] = "1";
			}
		}
	}

	private String[] getEventsByMonth(int year, int month,
			HttpServletRequest request) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date rangeStart = c.getTime();
		int num = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		String[] eventsOfEverDay = new String[num];

		c.add(Calendar.MONTH, 1);
		Date rangeEnd = c.getTime();
		try {
			List<KmCalendarMain> matchedKmCalendars = getServiceImp(request)
					.getRangeCalendars(rangeStart, rangeEnd,
							KmCalendarConstant.CALENDAR_TYPE_EVENT, false,
							UserUtil.getUser().getFdId(), null);
			updateEventsOfEverDay(matchedKmCalendars, rangeStart, rangeEnd,
					eventsOfEverDay);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<KmCalendarMain> calendars = getServiceImp(request)
				.getRecurrenceCalendars(rangeStart, rangeEnd,
						UserUtil.getUser().getFdId(), null);

		updateEventsOfEverDay(calendars, rangeStart, rangeEnd, eventsOfEverDay);
		return eventsOfEverDay;
	}

	/**
	 * 修改日程事件(拖动日程事件、拉伸日程事件)
	 */
	public ActionForward updateCalendarTime(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-updateCalendarTime", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		boolean status = true;
		boolean isRecurrence = false;// 是否重复日程
		try {
			String fdId = request.getParameter("fdId");
			KmCalendarMain kmCalendarMain = (KmCalendarMain) getServiceImp(
					request).findByPrimaryKey(fdId);

			// 如果是重复日程，isRecurrence=true
			if (StringUtil.isNotNull(kmCalendarMain.getFdRecurrenceStr())) {
				isRecurrence = true;
			}

			String resize = request.getParameter("resize");
			String dayDelta = request.getParameter("dayDelta");
			String minuteDelta = request.getParameter("minuteDelta");

			if ("true".equals(resize)) {
				// 拉伸日程
				Date endDate = kmCalendarMain.getDocFinishTime();
				Calendar c = Calendar.getInstance();
				c.setTime(endDate);
				Date oldEndDate = c.getTime();
				if (StringUtil.isNotNull(dayDelta)) {
					c.add(Calendar.DATE, Integer.parseInt(dayDelta));
				}
				if (StringUtil.isNotNull(minuteDelta)) {
					c.add(Calendar.MINUTE, Integer.parseInt(minuteDelta));
				}
				kmCalendarMain.setDocFinishTime(c.getTime());
				if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_UPDATE,
						getServiceImp(request).getModelName())) {
					UserOperContentHelper.putUpdate(kmCalendarMain)
							.putSimple("docFinishTime", oldEndDate,
									kmCalendarMain.getDocFinishTime());
				}
			} else {
				// 拖动日程
				String startTime = request.getParameter("start");
				String endTime = request.getParameter("end");
				String isAllDayEvent = request.getParameter("isAllDayEvent");
				Boolean isAllDayEventOld = kmCalendarMain.getFdIsAlldayevent();

				String pattern = DateUtil.PATTERN_DATE;
				if ("false".equals(isAllDayEvent)) {
					pattern = DateUtil.PATTERN_DATETIME;
					kmCalendarMain.setFdIsAlldayevent(false);
				} else {
					kmCalendarMain.setFdIsAlldayevent(true);
				}

				// #1966
				// 为解决重复日程拖动异常问题，将原来使用参数startTime、endTime修改时间的方式改为使用dayDelta、minuteDelta修改时间
				Calendar startCalendar = Calendar.getInstance();
				startCalendar.setTime(kmCalendarMain.getDocStartTime());
				Date oldStartTime = startCalendar.getTime();
				if (kmCalendarMain.getDocFinishTime() == null) {
					kmCalendarMain.setDocFinishTime(kmCalendarMain
							.getDocStartTime());
				}
				Calendar endCalendar = Calendar.getInstance();
				endCalendar.setTime(kmCalendarMain.getDocFinishTime());
				Date oldFinishTime = endCalendar.getTime();
				if (StringUtil.isNotNull(dayDelta)) {
					startCalendar
							.add(Calendar.DATE, Integer.parseInt(dayDelta));
					endCalendar.add(Calendar.DATE, Integer.parseInt(dayDelta));
				}
				if (StringUtil.isNotNull(minuteDelta)) {
					startCalendar.add(Calendar.MINUTE, Integer
							.parseInt(minuteDelta));
					endCalendar.add(Calendar.MINUTE, Integer
							.parseInt(minuteDelta));
					if (Boolean.TRUE.equals(isAllDayEventOld)
							&& "false".equals(isAllDayEvent)) {
						endCalendar.setTime(startCalendar.getTime());
						endCalendar.add(Calendar.MINUTE, 60);
					}
				}
				// 全天事件
				if (Boolean.TRUE.equals(kmCalendarMain.getFdIsAlldayevent())) {
					Calendar c = Calendar.getInstance();
					c.setTime(startCalendar.getTime());
					c.set(Calendar.HOUR_OF_DAY, 0);
					c.set(Calendar.MINUTE, 0);
					c.set(Calendar.SECOND, 0);
					startCalendar.setTime(c.getTime());
				}
				kmCalendarMain.setDocStartTime(startCalendar.getTime());
				kmCalendarMain.setDocFinishTime(endCalendar.getTime());
				// #1966

				// 已废弃参数，后期删除
				if (StringUtil.isNotNull(startTime)) {
					kmCalendarMain.setDocStartTime(DateUtil
							.convertStringToDate(startTime, pattern));
				}
				if (StringUtil.isNotNull(endTime)) {
					kmCalendarMain.setDocFinishTime(DateUtil
							.convertStringToDate(endTime, pattern));
				}
				if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_UPDATE,
						getServiceImp(request).getModelName())) {
					UserOperContentHelper.putUpdate(kmCalendarMain)
							.putSimple("docStartTime", oldStartTime,
									kmCalendarMain.getDocStartTime())
							.putSimple("docFinishTime", oldFinishTime,
									kmCalendarMain.getDocFinishTime());
				}
			}
			getServiceImp(request).update(kmCalendarMain);
			UserOperHelper.setOperSuccess(true);
		} catch (Exception e) {
			UserOperHelper.setOperSuccess(false);
			e.printStackTrace();
			messages.addError(e);
		}
		JSONObject json = new JSONObject();
		if (messages.hasError()) {
			status = false;
		}
		json.put("status", status);// 执行结果
		json.put("isRecurrence", isRecurrence);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 同步
	 */
	public ActionForward synchro(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// String modelName =request.getParameter("modelName");
		CMSSynchroService cmsSynchroService = (CMSSynchroService) SpringBeanUtil
				.getBean("CMSSynchroService");
		String appKey = request.getParameter("appKey");

		Map<String, Object> result = cmsSynchroService.syncro(UserUtil
				.getUser().getFdId(), appKey);
		KmssMessages messages = new KmssMessages();
		for (String messageKey : result.keySet()) {
			if ("exceptions".equals(messageKey)) {
				continue;
			}
			messages.addMsg(new KmssMessage("km-calendar:" + messageKey,
					(String) result.get(messageKey)));
		}
		List<Exception> exceptions = (List<Exception>) result.get("exceptions");
		if (exceptions != null && exceptions.size() > 0) {
			for (Exception e : exceptions) {
				messages.addError(e);
			}
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);

		return getActionForward("synchroResult", mapping, form, request,
				response);
	}

	public ActionForward synchroReset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CMSSynchroService cmsSynchroService = (CMSSynchroService) SpringBeanUtil
				.getBean("CMSSynchroService");
		cmsSynchroService.reset();
		return null;
	}

	private static JSONObject getSynchroDataObject(CMSPluginData data) {
		JSONObject object = new JSONObject();
		object.put("appKey", data.getAppKey());
		object.put(
				"text",
				ResourceUtil
						.getString("kmCalendarSyncBind.sync", "km-calendar")
						+ " "
				+ data.getName());// 同步
		object.put("url",
				"km/calendar/km_calendar_main/kmCalendarMain.do?method=synchro&appKey="
						+ data.getAppKey());
		return object;
	}

	/**
	 * 获取同步菜单
	 */
	public ActionForward getSyncroMenuDataJson(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String personId = UserUtil.getUser().getFdId();
		List<CMSPluginData> CMSPluginDatas = CMSPlugin.getExtensionList();
		Map<String, List<JSONObject>> result = new HashMap<String, List<JSONObject>>();
		for (CMSPluginData data : CMSPluginDatas) {
			ICMSProvider cmsProvider = data.getCmsProvider();
			if(!cmsProvider.isSynchroEnable()){
				continue;
			}
			IOAuthProvider OAuthprovider = data.getOAuthProvider();
			List<JSONObject> objects = new ArrayList<JSONObject>();
			String originName = data.getOriginName();
			String name = ResourceUtil.getString(originName.substring(1,originName.length()-1));
			if (OAuthprovider != null) {

				boolean tokenAvailable = OAuthprovider
						.isTokenAvailable(personId);
				if (tokenAvailable) {
					JSONObject object = new JSONObject();
					object.put("appKey", data.getAppKey());
					object.put(
							"text",
							ResourceUtil.getString("kmCalendarSyncBind.unbind",
									"km-calendar") + " " + name);// 解除绑定
					object.put("url", OAuthprovider.getDisBindUrl());
					objects.add(object);

					objects.add(getSynchroDataObject(data));

				} else {
					JSONObject object = new JSONObject();
					object.put("appKey", data.getAppKey());
					object.put(
							"text",
							ResourceUtil.getString("kmCalendarSyncBind.bind",
									"km-calendar") + " "
							+ name);// 绑定
					object.put("url", OAuthprovider.getBindUrl());
					objects.add(object);
				}

			} else {
				JSONObject object = new JSONObject();
				String bindPageUrl = data.getBindPageUrl();
				if (StringUtil.isNotNull(bindPageUrl)) {
					object.put("appKey", data.getAppKey());
					object.put(
							"text",
							ResourceUtil.getString("kmCalendarSyncBind.bind",
									"km-calendar") + " "
							+ data.getName());
					object.put("url", bindPageUrl);
					objects.add(object);
				}

				if (cmsProvider.isNeedSyncro(personId) && !data.getSyncNow()) {
					objects.add(getSynchroDataObject(data));
				}

			}
			result.put(data.getAppKey(), objects);
		}
		JSONObject jsonObject = new JSONObject();
		String json = JSONObject.fromObject(result).toString();

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(json);
		return null;
	}

	public ActionForward listPersonGroupCalendar(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SysOrgPerson curUser = UserUtil.getUser();
		String personGroupId = request.getParameter("personGroupId");
		List<SysOrgElement> totalPersons = new ArrayList<SysOrgElement>();// 全部日程人员
		List<SysOrgElement> persons = new ArrayList<SysOrgElement>();// 当前页日程人员
		try {
			if (StringUtil.isNull(personGroupId)) {
				logger.error("personGroupId不能为空！");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-type", "text/json;charset=UTF-8");
				response.getWriter().write(new JSONObject().toString());
				return null;
			} else {// 指定共享组
				request.setAttribute("performanceApprove", "y");
				Map<String, List<SysOrgElement>> maps = getKmCalendarPersonGroupService()
						.getFdPersonGroup(new RequestContext(request));
				totalPersons = maps.get("totalPersons");
				persons = maps.get("persons");
			}

			JSONObject object = new JSONObject();

			// 总人数
			if (totalPersons != null && !totalPersons.isEmpty()) {
				JSONArray array = new JSONArray();
				for (SysOrgElement person : totalPersons) {
					JSONObject personJson = new JSONObject();
					personJson.put("fdId", person.getFdId());
					personJson.put("fdName", person.getFdName());
					array.add(personJson);
				}
				object.put("totalPerson", array);
			}

			//
			if (persons != null && !persons.isEmpty()) {
				List<String> eleIds = persons.stream().map(ele->ele.getFdId()).collect(Collectors.toList());
				ExecutorService threadPool = CalendarSysOrgUtil.getThreadPool();
				Future<Map<String, List<String>>> readerFuture = threadPool.submit(new Callable<Map<String, List<String>>>() {
					@Override
					public Map<String, List<String>> call() throws Exception {
						return getKmCalendarAuthService().getHierarchyIdsFromReaderAuth(eleIds);
					}
				});
				Future<Map<String, List<String>>> editorFuture = threadPool.submit(new Callable<Map<String, List<String>>>() {
					@Override
					public Map<String, List<String>> call() throws Exception {
						return getKmCalendarAuthService().getHierarchyIdsFromEditorAuth(eleIds);
					}
				});
				Future<Map<String, List<String>>> modifierFuture = threadPool.submit(new Callable<Map<String, List<String>>>() {
					@Override
					public Map<String, List<String>> call() throws Exception {
						return getKmCalendarAuthService().getHierarchyIdsFromModifierAuth(eleIds);
					}
				});
				Map<String, List<String>> readerMap = readerFuture.get();
				Map<String, List<String>> editorMap = editorFuture.get();
				Map<String, List<String>> modifierMap = modifierFuture.get();
				JSONArray array = new JSONArray();
				String memberIds = "";
				for (SysOrgElement person : persons) {
					JSONObject personJson = new JSONObject();
					personJson.put("fdId", person.getFdId());
					personJson.put("fdName", person.getFdName());
					Map<String, Boolean> map = getAuth(person, curUser, readerMap, editorMap, modifierMap);
					personJson.put("canRead", map.get("canRead"));
					personJson.put("canEditor", map.get("canEditor"));
					personJson.put("canModifier", map.get("canModifier"));
					array.add(personJson);
					memberIds += person.getFdId() + ";";
				}
				object.put("persons", array);

				String startStr = request.getParameter("fdStart");
				String endStr = request.getParameter("fdEnd");
				Date rangeStart = null;
				Date rangeEnd = null;
				if (StringUtil.isNotNull(startStr)) {
					rangeStart = DateUtil.convertStringToDate(startStr,
							DateUtil.TYPE_DATE, UserUtil.getKMSSUser()
									.getLocale());
					rangeEnd = DateUtil.convertStringToDate(endStr,
							DateUtil.TYPE_DATE, UserUtil.getKMSSUser()
									.getLocale());
				} else {
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.DATE, -3);
					rangeStart = calendar.getTime();
					calendar.add(Calendar.DATE, 7);
					rangeEnd = calendar.getTime();
				}

				List<KmCalendarMain> kmCalendarMains = new ArrayList<KmCalendarMain>();

				List<KmCalendarMain> kmNormalCalendarMains = getServiceImp(
						request).getRangeCalendars(rangeStart, rangeEnd,
								KmCalendarConstant.CALENDAR_TYPE_EVENT, false,
								memberIds, null);
				if (CollectionUtils.isNotEmpty(kmNormalCalendarMains)) {
					ArrayUtil.concatTwoList(kmNormalCalendarMains, kmCalendarMains);
				}

				List<KmCalendarMain> recurrenceCalendars = getServiceImp(
						request).getRecurrenceCalendars(rangeStart, rangeEnd,
						memberIds, null);
				if (CollectionUtils.isNotEmpty(recurrenceCalendars)) {
					ArrayUtil.concatTwoList(recurrenceCalendars, kmCalendarMains);
				}

				// 如果是群组阅读者和维护者，可以查看该群组日历
				List<KmCalendarMain> groupCalendars = getServiceImp(request).getGroupCalendars(rangeStart, rangeEnd,
						personGroupId);
				if (CollectionUtils.isNotEmpty(groupCalendars)) {
					ArrayUtil.concatTwoList(groupCalendars, kmCalendarMains);
				}

				Collections.sort(kmCalendarMains, new CalendarComparator());

				Map<String, List<JSONObject>> result = new HashMap<String, List<JSONObject>>();
				String[] ids = memberIds.split(";");
				for (String id : ids) {
					List<JSONObject> userCalendars = new ArrayList<JSONObject>();
					result.put(id, userCalendars);
				}
				int index = kmCalendarMains.size();
				for (KmCalendarMain kmCalendarMain : kmCalendarMains) {
					if (KmCalendarConstant.AUTHORITY_TYPE_PRIVATE
							.equals(kmCalendarMain.getFdAuthorityType())
							&& kmCalendarMain.getDocOwner() != null
							&& !kmCalendarMain.getDocOwner().getFdId()
									.equals(UserUtil.getUser().getFdId())) {
						continue;
					}
					JSONObject json = genCalendarData(kmCalendarMain);
					json.put("priority", index--);
					List<JSONObject> calendars = result.get(kmCalendarMain.getDocOwner().getFdId());
					if (calendars != null) {
						calendars.add(json);
					}
				}

				object.put("calendars", result);
				// 月视图数据
				String operType = request.getParameter("operType");
				JSONArray monthResult = new JSONArray();
				for (String key : result.keySet()) {
					List<JSONObject> userData = result.get(key);
					for (JSONObject json : userData) {
						monthResult.add(json);
					}
				}
				String newResult = monthResult.toString();
				if ("week".equals(operType)) {
					newResult = object.toString();
				}
				if (MobileUtil.getClientType(request) > -1) {
					newResult = object.toString();
				}
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-type", "text/json;charset=UTF-8");
				response.getWriter().write(newResult);
			} else {
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-type", "text/json;charset=UTF-8");
				response.getWriter().write(object.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Map<String, Boolean> getAuth(SysOrgElement person,
										 SysOrgPerson curUser,
										 Map<String, List<String>> readerMap,
										 Map<String, List<String>> editorMap,
										 Map<String, List<String>> modifierMap) throws Exception {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		if (person.equals(curUser)) {
			map.put("canRead", true);
			map.put("canEditor", true);
			map.put("canModifier", true);
		} else {
			List<SysOrgElement> list;
			List<String> hierarchyIds = readerMap.get(person.getFdId());
			if(hierarchyIds != null && hierarchyIds.size() > 0){
				list = getKmCalendarPersonGroupService().getSysOrgElements(new HashSet<String>(hierarchyIds), false);
				if(list != null){
					map.put("canRead", list.contains(curUser));
				}
			}
			hierarchyIds = editorMap.get(person.getFdId());
			if(hierarchyIds != null && hierarchyIds.size() > 0){
				list = getKmCalendarPersonGroupService().getSysOrgElements(new HashSet<String>(hierarchyIds), false);
				if(list != null){
					map.put("canEditor", list.contains(curUser));
				}
			}

			hierarchyIds = modifierMap.get(person.getFdId());
			if(hierarchyIds != null && hierarchyIds.size() > 0){
				list = getKmCalendarPersonGroupService().getSysOrgElements(new HashSet<String>(hierarchyIds), false);
				if(list != null){
					map.put("canModifier", list.contains(curUser));
				}
			}
		}
		return map;
	}

	private Map<String, Boolean> getAuth(SysOrgElement person,
			SysOrgPerson curUser) throws Exception {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		if (person.equals(curUser)) {
			map.put("canRead", true);
			map.put("canEditor", true);
			map.put("canModifier", true);
		} else {
			KmCalendarAuth auth = getKmCalendarAuthService()
					.findByPerson(person.getFdId());
			if (auth != null) {
				List<String> authReaders = getSysOrgCoreService()
						.expandToPersonIds(auth.getAuthReaders());
				map.put("canRead", authReaders.contains(curUser.getFdId()));
				List<String> authEditors = getSysOrgCoreService()
						.expandToPersonIds(auth.getAuthEditors());
				map.put("canEditor", authEditors.contains(curUser.getFdId()));
				List<String> authModifiers = getSysOrgCoreService()
						.expandToPersonIds(auth.getAuthModifiers());
				map.put("canModifier",
						authModifiers.contains(curUser.getFdId()));
			}
		}
		return map;
	}

	private Map<String, Boolean> getAuth(SysOrgElement person,
			SysOrgPerson curUser, List authList) throws Exception {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		if (person.equals(curUser)) {
			map.put("canRead", true);
			map.put("canEditor", true);
			map.put("canModifier", true);
		} else {
			KmCalendarAuth auth = getUserCalendarAuth(authList, person);
			if (auth != null) {
				List<String> authReaders = getSysOrgCoreService()
						.expandToPersonIds(auth.getAuthReaders());
				map.put("canRead", authReaders.contains(curUser.getFdId()));
				List<String> authEditors = getSysOrgCoreService()
						.expandToPersonIds(auth.getAuthEditors());
				map.put("canEditor", authEditors.contains(curUser.getFdId()));
				List<String> authModifiers = getSysOrgCoreService()
						.expandToPersonIds(auth.getAuthModifiers());
				map.put("canModifier", authModifiers.contains(curUser.getFdId()));
			}
		}
		return map;
	}

	private KmCalendarAuth getUserCalendarAuth(List<KmCalendarAuth> authList,
			SysOrgElement person) {
		for(KmCalendarAuth auth : authList){
			if (auth.getDocCreator().equals(person)) {
				return auth;
			}
		}
		return null;
	}

	/**
	 * 获取群组日程
	 */
	public ActionForward listGroupCalendar(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SysOrgPerson curUser = UserUtil.getUser();
		String groupId = request.getParameter("groupId");
		String isShare = request.getParameter("isShare");
		List<SysOrgElement> totalPersons = new ArrayList<SysOrgElement>();// 全部日程人员
		List<SysOrgElement> persons = new ArrayList<SysOrgElement>();// 当前页日程人员
		long start = System.currentTimeMillis();
		try {
			if (StringUtil.isNull(groupId)) {
				logger.error("groupId不能为空！");
				return null;
			} else if ("defaultGroup".equals(groupId)) {// 全部共享
				Map<String, List> maps = getKmCalendarAuthService()
						.getDefaultGroupMembers(new RequestContext(request));
				totalPersons = maps.get("totalPersons");
				persons = maps.get("persons");
			} else {// 指定共享组
				Map<String, List> maps = getKmCalendarShareGroupService()
						.getShareGroupMembers(new RequestContext(request));
				totalPersons = maps.get("totalPersons");
				persons = maps.get("persons");
			}

			//根据指定人员查询
			String personIds = request.getParameter("personIds");
			if (StringUtil.isNotNull(personIds)) {
				List<SysOrgElement> newPersons = new ArrayList<SysOrgElement>();
				for(SysOrgElement ele :persons){
					if(personIds.indexOf(ele.getFdId())>-1){
						newPersons.add(ele);
					}
				}
				persons = newPersons;
			}

			JSONObject object = new JSONObject();
			// 总人数
			if (totalPersons != null && !totalPersons.isEmpty()) {
				JSONArray array = new JSONArray();
				for (SysOrgElement person : totalPersons) {
					JSONObject personJson = new JSONObject();
					personJson.put("fdId", person.getFdId());
					personJson.put("fdName", person.getFdName());
					array.add(personJson);
				}
				object.put("totalPerson", array);
			}
			if (persons != null && !persons.isEmpty()) {
				JSONArray array = new JSONArray();
				String memberIds = "";
				List<String> orgIds = getOrgIdList(persons);
				List<KmCalendarAuth> authList = this.getKmCalendarAuthService()
						.findUserCalendarAuth(orgIds);
				for (SysOrgElement person : persons) {
					JSONObject personJson = new JSONObject();
					personJson.put("fdId", person.getFdId());
					personJson.put("fdName", person.getFdName());
					Map<String, Boolean> map = getAuth(person, curUser,
							authList);
					personJson.put("canRead", map.get("canRead"));
					personJson.put("canEditor", map.get("canEditor"));
					personJson.put("canModifier", map.get("canModifier"));
					array.add(personJson);
					memberIds += person.getFdId() + ";";
				}
				object.put("persons", array);

				String startStr = request.getParameter("fdStart");
				String endStr = request.getParameter("fdEnd");
				Date rangeStart = null;
				Date rangeEnd = null;
				if (StringUtil.isNotNull(startStr)) {
					rangeStart = DateUtil.convertStringToDate(startStr,
							DateUtil.TYPE_DATE, UserUtil.getKMSSUser()
									.getLocale());
					rangeEnd = DateUtil.convertStringToDate(endStr,
							DateUtil.TYPE_DATE, UserUtil.getKMSSUser()
									.getLocale());
				} else {
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.DATE, -3);
					rangeStart = calendar.getTime();
					calendar.add(Calendar.DATE, 7);
					rangeEnd = calendar.getTime();
				}

				List<KmCalendarMain> kmCalendarMains = new ArrayList<KmCalendarMain>();
				List<KmCalendarMain> kmNormalCalendarMains = new ArrayList<KmCalendarMain>();
				if("true".equals(isShare)){
					CalendarQueryContext context = new CalendarQueryContext();
					context.setRangeStart(rangeStart); //开始时间
					context.setRangeEnd(rangeEnd); //结束时间
					context.setCalType(KmCalendarConstant.CALENDAR_TYPE_EVENT); ////类型:笔记？日程？
					context.setIncludeRecurrence(false); //是否包含重复日程
					context.setPersonsIds(memberIds); //查询人员
					context.setLabelIds(null); ////查询标签
					context.setGroupId(groupId); //群组
					context.setIsShare(isShare); //共享入口进入的
					kmNormalCalendarMains = getServiceImp(
							request).getRangeCalendars(context);
				}else {
					kmNormalCalendarMains = getServiceImp(
							request).getRangeCalendars(rangeStart, rangeEnd,
							KmCalendarConstant.CALENDAR_TYPE_EVENT, false,
							memberIds, null);
				}
				if (kmNormalCalendarMains != null && !kmNormalCalendarMains.isEmpty()) {
					kmCalendarMains.addAll(kmNormalCalendarMains);
				}

				List<KmCalendarMain> recurrenceCalendars = getServiceImp(
						request).getRecurrenceCalendars(rangeStart, rangeEnd,
								memberIds, null);
				if (recurrenceCalendars != null && !recurrenceCalendars.isEmpty()) {
					kmCalendarMains.addAll(recurrenceCalendars);
				}
				if (UserOperHelper.allowLogOper("listGroup",
						getServiceImp(request).getModelName())) {
					UserOperContentHelper.putFinds(kmCalendarMains);
					UserOperHelper.setOperSuccess(true);
				}
				Collections.sort(kmCalendarMains, new CalendarComparator());

				//list->set 去重
				Map<String, Set<JSONObject>> result = new HashMap<>();

				String[] ids = memberIds.split(";");
				for (String id : ids) {
					Set<JSONObject> userCalendars = new ArraySet();
					result.put(id, userCalendars);
				}
				int index = kmCalendarMains.size();
				for (KmCalendarMain kmCalendarMain : kmCalendarMains) {
					if (KmCalendarConstant.AUTHORITY_TYPE_PRIVATE
							.equals(kmCalendarMain.getFdAuthorityType())
							&& kmCalendarMain.getDocOwner() != null
							&& !kmCalendarMain.getDocOwner().getFdId()
									.equals(UserUtil.getUser().getFdId())) {
						continue;
					}
					JSONObject json = genCalendarData(kmCalendarMain);
					json.put("priority", index--);
					Set<JSONObject> calendars = result.get(kmCalendarMain.getDocOwner().getFdId());
					if (calendars != null) {
						calendars.add(json);
					}
					//共享日程-日程相关人员-也添加到自己的日程展示中 #170298
					List<SysOrgPerson> relations = kmCalendarMain.getFdRelatedPersons();
					if(relations != null && !relations.isEmpty()){
						for(SysOrgPerson person : relations){
							String reId = person.getFdId();
							if(memberIds.contains(reId) && !kmCalendarMain.getDocOwner().getFdId().equals(reId)){
								Set<JSONObject> reCalendars = result.get(reId);
								if (reCalendars != null) {
									reCalendars.add(json);
								}
							}
						}
					}
				}

				object.put("calendars", result);
				// 月视图数据
				String operType = request.getParameter("operType");
				JSONArray monthResult = new JSONArray();
				for (String key : result.keySet()) {
					Set<JSONObject> userData = result.get(key);
					for (JSONObject json : userData) {
						monthResult.add(json);
					}
				}
				String newResult = monthResult.toString();
				if ("week".equals(operType)) {
					newResult = object.toString();
				}
				if(MobileUtil.getClientType(request)>-1){
					newResult = object.toString();
				}
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-type", "text/json;charset=UTF-8");
				response.getWriter().write(newResult.toString());
			} else {
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-type", "text/json;charset=UTF-8");
				response.getWriter().write(object.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (UserOperHelper.allowLogOper("listGroup",
					getServiceImp(request).getModelName())) {
				UserOperHelper.setOperSuccess(false);
			}
		}
		return null;
	}

	private List getOrgIdList(List<SysOrgElement> list) {
		List<String> eleList = new ArrayList<String>();
		for (SysOrgElement org : list) {
			eleList.add(org.getFdId());
		}
		return eleList;
	}

	/**
	 * 保存、更新前设置日程重复信息等
	 */
	private void initEvent(KmCalendarMainForm kmCalendarMainForm)
			throws ParseException {

		String fdIsAlldayevent = kmCalendarMainForm.getFdIsAlldayevent();

		String fdIsLunar = kmCalendarMainForm.getFdIsLunar();
		if (StringUtil.isNull(fdIsLunar)) {
			kmCalendarMainForm.setFdIsLunar("false");
		}
		if (StringUtil.isNull(kmCalendarMainForm.getFdType())) {
			kmCalendarMainForm
					.setFdType(KmCalendarConstant.CALENDAR_TYPE_EVENT);
		}
		String fdAuthorityType = kmCalendarMainForm.getFdAuthorityType();
		if (StringUtil.isNull(fdAuthorityType)) {
			kmCalendarMainForm
					.setFdAuthorityType(KmCalendarConstant.AUTHORITY_TYPE_DEFAULT);
		}
		String freq = kmCalendarMainForm.getRECURRENCE_FREQ();
		String startDate = kmCalendarMainForm.getDocStartTime();
		String isLunar = kmCalendarMainForm.getFdIsLunar();
		if ("true".equals(isLunar)) {
			String RECURRENCE_FREQ_LUNAR = kmCalendarMainForm
					.getRECURRENCE_FREQ_LUNAR();
			if (!KmCalendarConstant.RECURRENCE_FREQ_NO
					.equals(RECURRENCE_FREQ_LUNAR)) {
				String recurrenceStr = Rfc2445Util.buildRecurrenceStr(
						RECURRENCE_FREQ_LUNAR, kmCalendarMainForm
								.getRECURRENCE_INTERVAL_LUNAR(),
						kmCalendarMainForm.getRECURRENCE_END_TYPE_LUNAR(),
						kmCalendarMainForm.getRECURRENCE_COUNT_LUNAR(),
						kmCalendarMainForm.getRECURRENCE_UNTIL_LUNAR(), null);
				kmCalendarMainForm.setFdRecurrenceStr(recurrenceStr);
			}
		} else {
			if (!KmCalendarConstant.RECURRENCE_FREQ_NO.equals(freq)) {
				String byday = null;
				if (KmCalendarConstant.RECURRENCE_FREQ_WEEKLY.equals(freq)) {
					byday = kmCalendarMainForm.getRECURRENCE_WEEKS()
							.replaceAll(";", ",");
				} else if (KmCalendarConstant.RECURRENCE_FREQ_MONTHLY
						.equals(freq)) {
					if (KmCalendarConstant.RECURRENCE_MONTH_TYPE_WEEK
							.equals(kmCalendarMainForm
									.getRECURRENCE_MONTH_TYPE())) {
						Date _startDate = format2.parse(startDate);
						Calendar c = Calendar.getInstance();
						c.setFirstDayOfWeek(Calendar.MONDAY);
						c.setTime(_startDate);
						int weekOfMonth = c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
						int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
						String weekStr = weeks[dayOfWeek - 1];
						byday = weekOfMonth + weekStr;
					}
				}
				String recurrenceStr = Rfc2445Util.buildRecurrenceStr(freq,
						kmCalendarMainForm.getRECURRENCE_INTERVAL(),
						kmCalendarMainForm.getRECURRENCE_END_TYPE(),
						kmCalendarMainForm.getRECURRENCE_COUNT(),
						kmCalendarMainForm.getRECURRENCE_UNTIL(), byday);
				kmCalendarMainForm.setFdRecurrenceStr(recurrenceStr);
			}
		}
		if (StringUtil.isNull(kmCalendarMainForm.getDocFinishTime())) {
			String startTime = kmCalendarMainForm.getDocStartTime();
			boolean isallDay = StringUtil.isNotNull(fdIsAlldayevent) && "true".equals(fdIsAlldayevent);
			String pattern = isallDay ? DateUtil.TYPE_DATE : DateUtil.TYPE_DATETIME;
			Date docStartTime = DateUtil.convertStringToDate(startTime, pattern,
					UserUtil.getKMSSUser().getLocale());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(docStartTime);
			if (!isallDay) {
				calendar.add(Calendar.HOUR, 1);
			}
			kmCalendarMainForm
					.setDocFinishTime(DateUtil.convertDateToString(calendar.getTime(), pattern, null));
		}
		// 构建开始时间和结束时间
		if (StringUtil.isNull(fdIsAlldayevent)
				|| "false".equals(fdIsAlldayevent)) {
			kmCalendarMainForm.setFdIsAlldayevent("false");
			String startDayStr = kmCalendarMainForm.getDocStartTime();
			String endDayStr = kmCalendarMainForm.getDocFinishTime();
			if ("true".equals(isLunar)) {
				String startHour = kmCalendarMainForm.getLunarStartHour();
				String startMinute = kmCalendarMainForm.getLunarStartMinute();
				startDayStr += " " + startHour + ":" + startMinute + ":00";
				// System.out.println(startDayStr);
				kmCalendarMainForm.setDocStartTime(startDayStr);

				String endHour = kmCalendarMainForm.getLunarEndHour();
				String endMinute = kmCalendarMainForm.getLunarEndMinute();
				endDayStr += " " + endHour + ":" + endMinute + ":00";
				kmCalendarMainForm.setDocFinishTime(endDayStr);
			} else {
				String startHour = kmCalendarMainForm.getStartHour();
				String startMinute = kmCalendarMainForm.getStartMinute();
				if (StringUtil.isNotNull(startHour)
						&& StringUtil.isNotNull(startMinute)) {
					startDayStr += " " + startHour + ":" + startMinute + ":00";
					kmCalendarMainForm.setDocStartTime(startDayStr);
				}
				String endHour = kmCalendarMainForm.getEndHour();
				String endMinute = kmCalendarMainForm.getEndMinute();
				if (StringUtil.isNotNull(endHour)
						&& StringUtil.isNotNull(endMinute)) {
					endDayStr += " " + endHour + ":" + endMinute + ":00";
					kmCalendarMainForm.setDocFinishTime(endDayStr);
				}
			}
		}
	}

	/**
	 * 更新日程
	 */
	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		KmCalendarMainForm kmCalendarMainForm = (KmCalendarMainForm) form;
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			initEvent(kmCalendarMainForm);
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			JSONObject data = new JSONObject();
			data.accumulate("viewurl", StringUtil
					.formatUrl("/km/calendar/km_calendar_main/kmCalendarMain.do?method=view&fdId="
							+ kmCalendarMainForm.getFdId()));
			request.setAttribute("data", data);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 更新群组日程
	 */
	public ActionForward updateGroupEvent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-updateGroupEvent", true, getClass());
		KmssMessages messages = new KmssMessages();
		KmCalendarMainForm kmCalendarMainForm = (KmCalendarMainForm) form;
		JSONObject obj = new JSONObject();
		boolean status = true;
		boolean isRecurrence = false;// 是否重复日程
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			initEvent(kmCalendarMainForm);
			String mainGroupId = request.getParameter("mainGroupId");
			KmCalendarMainGroup mainGroup = (KmCalendarMainGroup) getKmCalendarMainGroupService()
					.findByPrimaryKey(mainGroupId);
			getServiceImp(request).updateGroupEvent(kmCalendarMainForm,
					mainGroup, new RequestContext(request));
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-updateGroupEvent", false,
				getClass());
		if (messages.hasError()) {
			status = false;
		}
		JSONObject json = new JSONObject();
		json.put("status", status);// 执行结果
		json.put("schedule", obj);// 日程对象
		json.put("isRecurrence", isRecurrence);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 更新日程
	 */
	public ActionForward updateEvent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-updateEvent", true, getClass());
		KmssMessages messages = new KmssMessages();
		KmCalendarMainForm kmCalendarMainForm = (KmCalendarMainForm) form;
		String labelId = kmCalendarMainForm.getLabelId();
		if (KmCalendarConstant.CALENDAR_MY_EVENT.equals(labelId)
				|| KmCalendarConstant.CALENDAR_MY_GROUP_EVENT.equals(labelId)) {
			kmCalendarMainForm.setLabelId("");
		}
		JSONObject obj = new JSONObject();
		boolean status = true;
		boolean isRecurrence = false;// 是否重复日程
		boolean isGroup = false;//保存修改前的日程是否属于组群日程
		boolean changeGroup = false;//是否涉及改动群组标签
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			KmCalendarMain oldCalendar = (KmCalendarMain) getServiceImp(request)
					.findByPrimaryKey(kmCalendarMainForm.getFdId());
			if (oldCalendar !=null) {
				isGroup =  oldCalendar.getFdIsGroup() !=null ? oldCalendar.getFdIsGroup() : false;
			}

			// 防止用户被篡改
			String docCreatorId = oldCalendar.getDocCreator() == null ? null
					: oldCalendar.getDocCreator().getFdId();
			kmCalendarMainForm
					.setDocCreatorId(docCreatorId);
			if (!checkCanModify(kmCalendarMainForm.getDocOwnerId())
					&& !checkIsEditor(oldCalendar,
							UserUtil.getUser().getFdId())) {
				String warnTip = "当前日程(" + kmCalendarMainForm.getDocSubject()
						+ ")拥有者不在合法权限内,更新失败!docOwnerId:"
						+ kmCalendarMainForm.getDocOwnerId();
				logger.warn(warnTip);
				throw new Exception(warnTip);
			}
			initEvent(kmCalendarMainForm);
			if(StringUtil.isNotNull(docCreatorId)&&docCreatorId.equals(UserUtil.getUser().getFdId())&&isGroup) {
				KmCalendarMainGroup mainGroup=(KmCalendarMainGroup) getKmCalendarMainGroupService().findMainGroupByMainId(kmCalendarMainForm.getFdId());
				kmCalendarMainForm.setFdIsGroup("true");
				getServiceImp(request).updateGroupEvent(kmCalendarMainForm,
						mainGroup, new RequestContext(request));
			}else {
				getServiceImp(request).update((IExtendForm) form,
						new RequestContext(request));
			}
			String fdId = kmCalendarMainForm.getFdId();
			KmCalendarMain calendar = (KmCalendarMain) getServiceImp(request)
					.findByPrimaryKey(fdId);
			// 生成JSON对象
			obj = genCalendarData(calendar);
			// 如果是重复日程，isRecurrence=true
			if (StringUtil.isNotNull(calendar.getFdRecurrenceStr())
					|| StringUtil.isNotNull(kmCalendarMainForm
							.getFdRecurrenceStr())) {
				isRecurrence = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-updateEvent", false, getClass());
		if (messages.hasError()) {
			status = false;
		}
		JSONObject json = new JSONObject();
		json.put("status", status);// 执行结果
		json.put("schedule", obj);// 日程对象
		json.put("isRecurrence", isRecurrence);
		json.put("changeGroup", changeGroup);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 更新笔记
	 */
	public ActionForward updateNote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-updateNote", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject obj = new JSONObject();
		boolean status = true;
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
			String fdId = ((IExtendForm) form).getFdId();
			obj = genCalendarData((KmCalendarMain) getServiceImp(request)
					.findByPrimaryKey(fdId));
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-updateNote", false, getClass());
		if (messages.hasError()) {
			status = false;
		}
		JSONObject json = new JSONObject();
		json.put("status", status);// 执行结果
		json.put("schedule", obj);// 日程对象
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response) {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		KmCalendarMainForm kmCalendarMainForm = (KmCalendarMainForm) form;
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String fdRefererId = request.getParameter("fdRefererId");
			if (StringUtil.isNotNull(fdRefererId)) {
				List<KmCalendarMain> calendars = getServiceImp(request)
						.getCalendarByRefererId(fdRefererId,
								UserUtil.getUser().getFdId());
				if (!calendars.isEmpty()) {
					messages.addMsg(new KmssMessage("relation calendar exist")
									.setMessageType(2)).setHasError();
					KmssReturnPage.getInstance(request).addMessages(messages)
							.save(request);
					return getActionForward("failure", mapping, form, request,
							response);
				}
			}
			initEvent(kmCalendarMainForm);
			initAuthData(UserUtil.getUser().getFdId(), kmCalendarMainForm);
			if (StringUtil.isNull(kmCalendarMainForm.getDocOwnerId())) {
				kmCalendarMainForm.setDocOwnerId(UserUtil.getUser().getFdId());
			}
			if (StringUtil.isNull(kmCalendarMainForm.getFdType())) {
				kmCalendarMainForm
						.setFdType(KmCalendarConstant.CALENDAR_TYPE_EVENT);
			}
			if (StringUtil.isNull(kmCalendarMainForm.getFdIsLunar())) {
				kmCalendarMainForm.setFdIsLunar("false");
			}
			String fdId = getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
			KmCalendarMain calendar = (KmCalendarMain) getServiceImp(
					request).findByPrimaryKey(fdId);
			savePartShareAuthList(calendar, request);
			TimeCounter.logCurrentTime("Action-save", false, getClass());
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			JSONObject data = new JSONObject();
			data.accumulate("viewurl",
					StringUtil.formatUrl("/km/calendar/km_calendar_main/kmCalendarMain.do?method=view&fdId="
							+ kmCalendarMainForm.getFdId()));
			request.setAttribute("data", data);
			return getActionForward("success", mapping, form, request, response);
		}
	}


	/**
	 * 保存日程
	 */
	public ActionForward saveEvent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-saveEvent", true, getClass());
		KmssMessages messages = new KmssMessages();
		KmCalendarMainForm kmCalendarMainForm = (KmCalendarMainForm) form;
		String labelId = kmCalendarMainForm.getLabelId();
		if (KmCalendarConstant.CALENDAR_MY_EVENT.equals(labelId)
				|| KmCalendarConstant.CALENDAR_MY_GROUP_EVENT.equals(labelId)) {
			kmCalendarMainForm.setLabelId("");
		}
		String simple = request.getParameter("simple");// 是否为简单页面新增日程
		JSONObject obj = new JSONObject();
		boolean status = true;// 执行结果
		boolean isRecurrence = false;// 是否重复日程
		boolean isSelf = true;
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			if (StringUtil.isNotNull(kmCalendarMainForm.getDocOwnerId())
					&& !checkIsOwner(kmCalendarMainForm.getDocOwnerId())) {
				JSONObject json = new JSONObject();
				json.put("status", false);
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-type", "text/json;charset=UTF-8");
				response.getWriter().write(json.toString());
				return null;
			}
			initEvent(kmCalendarMainForm);
			String docOwnerId = kmCalendarMainForm.getDocOwnerId();
			if ("true".equals(simple)) {
				String personId = "multiCreate".equals(docOwnerId)
						? UserUtil.getUser().getFdId() : docOwnerId;
				initAuthData(personId, kmCalendarMainForm);
			}
			if ("multiCreate".equals(docOwnerId)) {// 发起多人日程
				KmCalendarMainGroupForm kmCalendarMainGroupForm = new KmCalendarMainGroupForm();
				StringBuffer sb = new StringBuffer();
				String docOwnerIds = request.getParameter("docOwnerIds");
				ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
						.getBean("sysOrgCoreService");
				List<String> docOwnerIdsList = sysOrgCoreService
						.expandToPersonIds(Arrays
								.asList(docOwnerIds.split(";")));
				int size = docOwnerIdsList.size();
				if (size > 100) {
					kmCalendarMainForm
							.setDocCreatorId(UserUtil.getUser().getFdId());
					ExecutorService excutor = Executors.newFixedThreadPool(10);
					CountDownLatch latch = new CountDownLatch(size);
					for (int i = 0; i < size; i++) {
						MulitiCreate create = new MulitiCreate(
								docOwnerIdsList.get(i), kmCalendarMainForm,
								request, sb, latch);
						excutor.execute(create);
					}
					latch.await();
					excutor.shutdown();
				} else {
					for (String ownerId : docOwnerIdsList) {
						String fdMainId = multiCreate(ownerId,
								kmCalendarMainForm, request);
						sb.append(fdMainId + ";");
					}
				}
				kmCalendarMainGroupForm
						.setFdGroupId(request.getParameter("personGroupId"));
				kmCalendarMainGroupForm
						.setFdMainIds(sb.substring(0, sb.length() - 1));
				getKmCalendarMainGroupService().add(kmCalendarMainGroupForm,
						new RequestContext(request));
				String fdId = (String) request.getAttribute("value");
				if (StringUtil.isNotNull(fdId)) {
					KmCalendarMain calendar = (KmCalendarMain) getServiceImp(
							request).findByPrimaryKey(fdId);
					// 获取JSON对象
					obj = genCalendarData(calendar);
					// 如果是重复日程，isRecurrence=true
					if (StringUtil.isNotNull(calendar.getFdRecurrenceStr())) {
						isRecurrence = true;
					}
				}
				// 多人日程不包含自己,isSelf=false
				if (!docOwnerIdsList.contains(UserUtil.getUser().getFdId())) {
					isSelf = false;
				}
			} else {// 发起单人日程
				if (StringUtil.isNull(kmCalendarMainForm.getDocOwnerId())) {
					kmCalendarMainForm.setDocOwnerId(UserUtil.getUser()
							.getFdId());
				}
				String fdId = getServiceImp(request).add((IExtendForm) form,
						new RequestContext(request));
				KmCalendarMain calendar = (KmCalendarMain) getServiceImp(
						request).findByPrimaryKey(fdId);
				savePartShareAuthList(calendar, request);

				if (calendar.getDocCreator() != null
						&& calendar.getDocOwner() != null
						&& !calendar.getDocCreator().getFdId()
						.equals(calendar.getDocOwner().getFdId())) {
					try {
						getServiceImp(request).addToread2Owner(calendar);
					} catch (Exception e) {
						logger.error("", e);
					}
				}
				// 获取JSON对象
				obj = genCalendarData(calendar);
				// 如果是重复日程,isRecurrence=true
				if (StringUtil.isNotNull(calendar.getFdRecurrenceStr())) {
					isRecurrence = true;
				}
				// 如果是替他人创建的日程,isSelf=false
				if (!calendar.getDocOwner().equals(UserUtil.getUser())) {
					isSelf = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-saveEvent", false, getClass());
		if (messages.hasError()) {
			status = false;
		}
		JSONObject json = new JSONObject();
		json.put("status", status);// 执行结果
		json.put("schedule", obj);// 日程对象
		json.put("isRecurrence", isRecurrence);// 是否重复日程
		json.put("isSelf", isSelf);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 创建单独共享日程权限<br>
	 * 即用户A新建日程后，将用户B添加到日程的可阅读者域，但B不在A的共享权限里面
	 *
	 * @param kmCalendarMain
	 * @param request
	 * @throws Exception
	 */
	private void savePartShareAuthList(KmCalendarMain kmCalendarMain,
			HttpServletRequest request) throws Exception {
		// 创建群组日程和笔记不进行该操作
		String personGroupId = request.getParameter("personGroupId");
		if (StringUtil.isNull(personGroupId)
				&& KmCalendarConstant.CALENDAR_TYPE_EVENT
						.equals(kmCalendarMain.getFdType())) {
			String ownerId = kmCalendarMain.getDocOwner().getFdId();
			List<SysOrgElement> readers = kmCalendarMain.getAuthReaders();
			if (readers != null && !readers.isEmpty()) {
				KmCalendarAuth kmCalendarAuth = getKmCalendarAuthService()
						.findByPerson(ownerId);
				if (kmCalendarAuth != null) {
					List<SysOrgElement> person = new ArrayList<>();
					List<SysOrgElement> totalAuthPerson = new ArrayList<>();
					List<SysOrgElement> authReaders = kmCalendarAuth
							.getAuthReaders();
					List<SysOrgElement> authModifiers = kmCalendarAuth
							.getAuthModifiers();
					List<SysOrgElement> authEditors = kmCalendarAuth
							.getAuthEditors();
					ArrayUtil.concatTwoList(authReaders, totalAuthPerson);
					ArrayUtil.concatTwoList(authModifiers, totalAuthPerson);
					ArrayUtil.concatTwoList(authEditors, totalAuthPerson);
					for (SysOrgElement sysOrgElement : readers) {
						if (!ownerId.equals(sysOrgElement.getFdId())
								&& !totalAuthPerson.contains(sysOrgElement)) {
							person.add(sysOrgElement);
						}
					}
					if (!person.isEmpty()) {
						List<KmCalendarAuthList> authLists = kmCalendarAuth
								.getKmCalendarAuthList();
						if (authLists == null) {
							authLists = new ArrayList<>();
						}
						KmCalendarAuthList authList = new KmCalendarAuthList();
						authList.setFdIsPartShare(true);
						authList.setFdPerson(person);
						authLists.add(authList);
						kmCalendarAuth.setKmCalendarAuthList(authLists);
						getKmCalendarAuthService().update(kmCalendarAuth);
					}
				}
			}
		}
	}

	private String multiCreate(String ownerId,
			KmCalendarMainForm kmCalendarMainForm, HttpServletRequest request)
			throws Exception {
		KmCalendarMainForm clone = (KmCalendarMainForm) ModelUtil
				.clone(kmCalendarMainForm);
		clone.setFdId(IDGenerator.generateID());
		clone.setDocOwnerId(ownerId);
		initAuthData(ownerId, clone);
		String fdId = getServiceImp(request).add(
				(IExtendForm) clone, new RequestContext(request));
		KmCalendarMain calendar = (KmCalendarMain) getServiceImp(
				request).findByPrimaryKey(fdId);
		savePartShareAuthList(calendar, request);
		if (UserUtil.getUser().getFdId().equals(ownerId)) {
			request.setAttribute("value", fdId);
		}
		return fdId;
	}

	private class MulitiCreate extends Thread {
		String ownerId;
		KmCalendarMainForm kmCalendarMainForm;
		HttpServletRequest request;
		StringBuffer sb;
		CountDownLatch latch;

		public MulitiCreate(String ownerId,
				KmCalendarMainForm kmCalendarMainForm,
				HttpServletRequest request, StringBuffer sb,
				CountDownLatch latch) {
			this.ownerId = ownerId;
			this.kmCalendarMainForm = kmCalendarMainForm;
			this.request = request;
			this.sb = sb;
			this.latch = latch;
		}

		@Override
		public void run() {
			try {
				String fdId = multiCreate(ownerId, kmCalendarMainForm, request);
				sb.append(fdId + ";");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				latch.countDown();
			}
		}
	}

	/**
	 * 保存笔记
	 */
	public ActionForward saveNote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-saveNote", true, getClass());
		KmssMessages messages = new KmssMessages();
		boolean status = true;
		JSONObject obj = new JSONObject();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String fdId = getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
			obj = genCalendarData((KmCalendarMain) getServiceImp(request)
					.findByPrimaryKey(fdId));
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-saveNote", false, getClass());
		if (messages.hasError()) {
			status = false;
		}
		JSONObject json = new JSONObject();
		json.put("status", status);// 执行结果
		json.put("schedule", obj);// 日程对象
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 查询所有者
	 */
	public ActionForward searchOwners(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();
		json.put("owners", getOwnersArray());// 执行结果
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 获取当前用户的标签
	 * @param hasSysLable 是否包含系统标签
	 * @return
	 * @throws Exception
	 */
	private List<String[]> getLabelsArray(boolean hasSysLable) throws Exception {
		List<String[]> labelsArray = new ArrayList<String[]>();
		List<String[]> sysLabelsArray = new ArrayList<String[]>();
		List<String[]> otherLabelsArray = new ArrayList<String[]>();
		List<KmCalendarLabel> labels = getKmCalendarLabelService()
				.getLabelsByPerson(UserUtil.getUser().getFdId());
		// 是否要增加默认标签-我的日志
		String defName = ResourceUtil.getString("module.km.calendar.tree.my.calendar", "km-calendar");
		boolean addDef = true;
		if (labels != null && labels.size() > 0) {
			for (KmCalendarLabel label : labels) {
				String[] labelArray = new String[2];
				labelArray[0] = label.getFdId();
				//定义标签
				String fdName = StringUtil.XMLEscape(label.getFdName());
				//event
				String fdCommonFlag = label.getFdCommonFlag();
				//模块标签
				if (StringUtil.isNotNull(label.getFdModelName())) {
					SysDictModel sysDictModel = SysDataDict.getInstance()
							.getModel(label.getFdModelName());
					if (sysDictModel != null) {
						String messageKey = sysDictModel.getMessageKey();
						String fdName_lang = ResourceUtil.getString(messageKey,
								ResourceUtil.getLocaleByUser());
						fdName = fdName_lang != null ? fdName_lang : fdName;
					}
				}
				//系统标签
				else {
					boolean isSysLabel= false;
					if(fdCommonFlag != null){
						if (fdCommonFlag.contains(KmCalendarConstant.CALENDAR_MY_EVENT)) {
							fdName = ResourceUtil.getString("km-calendar:kmCalendar.nav.title");
							isSysLabel = true;
						} else if (fdCommonFlag.contains(KmCalendarConstant.CALENDAR_MY_GROUP_EVENT)) {
							fdName = ResourceUtil.getString("km-calendar:kmCalendarMain.group.header.title");
							isSysLabel = true;
						} else if (fdCommonFlag.contains(KmCalendarConstant.CALENDAR_MY_NOTE)) {
							fdName = ResourceUtil.getString("km-calendar:module.km.calendar.tree.my.note");
							isSysLabel = true;
						}
					}
                    //是系统标签
					if(isSysLabel){
						if (defName.equals(fdName)) {
							addDef = false;
						} else {
							//[跳过系统标签(除了我的日志)，仅记录是否选中，不展示]
							if(!hasSysLable){
								continue;
							}
						}
						labelArray[1] = fdName;
						sysLabelsArray.add(labelArray);
						continue;
					}
				}

				labelArray[1] = fdName;
				otherLabelsArray.add(labelArray);
			}
		}
		if (addDef) {
			String[] myEvent = new String[2];
			myEvent[0] = null;
			myEvent[1] = defName;
			sysLabelsArray.add(myEvent);
		}
		//#169334 我的日志，需要排序放到第一位[与pc端一致]
		int k = 0;
        for(String[] s1 : sysLabelsArray){
           if(!s1[1].equals(defName)){
           	  k++;
		   } else {
           	  break;
		   }
		}
        if(k != 0){
            //swap
			String[] myEvent = sysLabelsArray.get(k);
			String[] elseEvent = sysLabelsArray.get(0);
			sysLabelsArray.set(0,myEvent);
			sysLabelsArray.set(k,elseEvent);
		}
		labelsArray.addAll(sysLabelsArray);
		labelsArray.addAll(otherLabelsArray);
		return labelsArray;
	}

	/**
	 * 获取可为其创建日程的人员
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listOwner(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listOwner", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
			hqlInfo.setJoinBlock("left join kmCalendarAuth.authEditors auths");
			String whereBlock = HQLUtil.buildLogicIN("auths.fdId", UserUtil
					.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
			hqlInfo.setWhereBlock(whereBlock);
			List<KmCalendarAuth> kmCalendarAuths = getKmCalendarAuthService()
					.findList(
					hqlInfo);
			// 记录操作日志
			UserOperHelper.logFindAll(kmCalendarAuths,
					getKmCalendarAuthService().getModelName());
			UserOperHelper.setOperSuccess(true);
			request.setAttribute("kmCalendarAuths", kmCalendarAuths);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-listOwner", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			UserOperHelper.setOperSuccess(false);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listOwner", mapping, form, request,
					response);
		}
	}


	/**
	 * 获取当前用户的所有者
	 */
	private List<String[]> getOwnersArray() throws Exception {
		List<String[]> owners = getKmCalendarAuthService()
				.getCreateAuthPersonList();
		String modelName = KmCalendarAuth.class.getName();
		if (UserOperHelper.allowLogOper("searchOwners", modelName)) {
			for (String[] owner : owners) {
				UserOperContentHelper.putFind(owner[0], owner[1], modelName);
			}
			UserOperHelper.setOperSuccess(true);
		}
		String[] self = new String[2];
		self[0] = UserUtil.getUser().getFdId();
		self[1] = ResourceUtil.getString("kmCalendarMain.docOwner.self",
				"km-calendar");
		owners.add(0, self);
		return owners;
	}

	public ActionForward getOwners(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getOwners", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONArray rtnArray = new JSONArray();
		try {
			List<String[]> owners = getKmCalendarAuthService()
					.getCreateAuthPersonList();
			for (String[] owner : owners) {
				JSONObject json = new JSONObject();
				json.put("id", owner[0]);
				json.put("name", owner[1]);
				rtnArray.add(json);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-getOwners", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(rtnArray.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	private boolean checkIsOwner(String ownerId) throws Exception {
		if ("multiCreate".equals(ownerId)) {
			return true;
		}
		List<String[]> owners = getOwnersArray();
		for (String[] owner : owners) {
			if (owner[0].equals(ownerId)) {
				return true;
			}
		}
		return false;
	}

	private boolean checkCanModify(String ownerId) throws Exception{
	    List<String[]> owners = getKmCalendarAuthService().getModifyAuthPersonList();
	    String[] self = new String[2];
	    self[0] = UserUtil.getUser().getFdId();
	    self[1] = ResourceUtil.getString("kmCalendarMain.docOwner.self",
	        "km-calendar");
	    owners.add(0, self);
	    for (String[] owner : owners) {
	      if (owner[0].equals(ownerId)) {
	        return true;
	      }
	    }
	    return false;
	  }

	private boolean checkIsEditor(KmCalendarMain kmCalendarMain,
			String personId) throws Exception {
		List<String> editorIds = getSysOrgCoreService()
				.expandToPersonIds(kmCalendarMain.getAuthEditors());
		if (StringUtil.isNotNull(personId) && editorIds.contains(personId)) {
			return true;
		}
		return false;
	}

	/**
	 * 删除群组日程
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteGroupEvent(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteGroupEvent", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameter("fdMainIds").split(";");

			if (ISysAuthConstant.IS_AREA_ENABLED) {
				String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids,
						request, "method=delete&fdId=${id}");
				int noAuthIdNum = ids.length - authIds.length;
				if (noAuthIdNum > 0) {
					messages.addMsg(new KmssMessage(
							"sys-authorization:area.batch.operation.info",
							noAuthIdNum));
				}

				if (!ArrayUtils.isEmpty(authIds)) {
                    getServiceImp(request).delete(authIds);
                }
			} else if (ids != null) {
				getServiceImp(request).delete(ids);
			}

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteGroupEvent", false,
				getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
	}

	/**
	 * 编辑群组日程
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editGroupEvent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-editGroupEvent", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-editGroupEvent", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			KmCalendarMainForm kmCalenarMainForm = (KmCalendarMainForm) form;
			String fdId = kmCalenarMainForm.getFdId();
			KmCalendarMainGroup mainGroup = getKmCalendarMainGroupService()
					.findMainGroupByMainId(fdId);
			String forward = "editEvent";
			boolean isGroupCalendar = false;
			if (mainGroup != null) {
				List<KmCalendarMain> mainList = mainGroup.getFdMainList();
				if (!mainList.isEmpty()) {
					StringBuffer docOwnerIds = new StringBuffer();
					StringBuffer docOwnerNames = new StringBuffer();
					StringBuffer fdMainIds = new StringBuffer();
					isGroupCalendar = true;
					for (KmCalendarMain main : mainList) {
						SysOrgPerson owner = main.getDocOwner();
						docOwnerIds.append(owner.getFdId() + ";");
						docOwnerNames.append(owner.getFdName() + ";");
						fdMainIds.append(main.getFdId() + ";");
					}
					kmCalenarMainForm.setDocOwnerId("multiCreate");
					request.setAttribute("docOwnerIds",
							docOwnerIds.substring(0, docOwnerIds.length() - 1));
					request.setAttribute("docOwnerNames",
							docOwnerNames.substring(0,
									docOwnerNames.length() - 1));
					request.setAttribute("fdMainIds",
							fdMainIds.substring(0, fdMainIds.length() - 1));
					KmCalendarPersonGroup fdGroup = mainGroup.getFdGroup();
					List<SysOrgElement> authEditors = fdGroup.getAuthEditors();
					//设置编辑权限
					List<SysOrgElement> authEditorPersonList = getSysOrgCoreService().expandToPerson(authEditors);
					SysOrgPerson curUser = UserUtil.getUser();
					boolean isGroupAdmin = false;
					if (authEditorPersonList.contains(curUser)){
						isGroupAdmin = true;
					}
					request.setAttribute("isGroupAdmin", isGroupAdmin);
					request.setAttribute("mainGroupId", mainGroup.getFdId());
					forward = "editGroupEvent";
				}
			}
			request.setAttribute("isGroupCalendar", isGroupCalendar);
			return getActionForward(forward, mapping, form, request,
					response);
		}
	}

	public ActionForward addGroupEvent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-addGroupEvent", true, getClass());
		KmssMessages messages = new KmssMessages();
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String isAllDayEvent = request.getParameter("isAllDayEvent");
		String startHour = request.getParameter("startHour");
		String startMinute = request.getParameter("startMinute");
		String endHour = request.getParameter("endHour");
		String endMinute = request.getParameter("endMinute");
		String subject = request.getParameter("subject");
		String personGroupId = request.getParameter("personGroupId");
		if (StringUtil.isNull(startMinute)) {
            startMinute = "0";
        }
		if (StringUtil.isNull(endMinute)) {
            endMinute = "0";
        }
		try {
			request.setAttribute("owners", getOwnersArray());
			request.setAttribute("labels", getLabelsArray(false));
			KmCalendarMainForm kmCalendarMainForm = (KmCalendarMainForm) createNewForm(
					mapping, form, request, response);
			kmCalendarMainForm
					.setFdType(KmCalendarConstant.CALENDAR_TYPE_EVENT);
			kmCalendarMainForm.setDocSubject(subject);
			KmCalendarPersonGroup personGroup = (KmCalendarPersonGroup) getKmCalendarPersonGroupService()
					.findByPrimaryKey(personGroupId);
			// 初始化可阅读者和可编辑者
			List<SysOrgElement> authReaders = personGroup.getAuthReaders();
			StringBuffer readerIds = new StringBuffer();
			for (SysOrgElement reader : authReaders) {
				readerIds.append(reader.getFdId() + ";");
			}
			kmCalendarMainForm.setAuthReaderIds(
					readerIds.substring(0, readerIds.length() - 1));
			List<SysOrgElement> authEditors = personGroup.getAuthEditors();
			StringBuffer editorIds = new StringBuffer();
			for (SysOrgElement editor : authEditors) {
				editorIds.append(editor.getFdId() + ";");
			}
			kmCalendarMainForm.setAuthEditorIds(
					editorIds.substring(0, editorIds.length() - 1));
			List<SysOrgElement> persons = personGroup.getFdPersonGroup();
			StringBuffer docOwnerIds = new StringBuffer();
			StringBuffer docOwnerNames = new StringBuffer();
			for (SysOrgElement person : persons) {
				docOwnerIds.append(person.getFdId() + ";");
				docOwnerNames.append(person.getFdName() + ";");
			}
			request.setAttribute("docOwnerIds",
					docOwnerIds.substring(0, docOwnerIds.length() - 1));
			request.setAttribute("docOwnerNames",
					docOwnerNames.substring(0, docOwnerNames.length() - 1));
			request.setAttribute("personGroupId", personGroupId);
			//设置添加权限
			List<SysOrgElement> authEditorPersonList = getSysOrgCoreService().expandToPerson(authEditors);
			SysOrgPerson curUser = UserUtil.getUser();
			boolean isGroupAdmin = false;
			if (authEditorPersonList.contains(curUser)){
				isGroupAdmin = true;
			}
			request.setAttribute("isGroupAdmin", isGroupAdmin);

			// 如果开始结束时间为空,则为当前时间
			if (StringUtil.isNull(startTime)) {
				startTime = DateUtil.convertDateToString(new Date(),
						DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
			}
			kmCalendarMainForm.setDocStartTime(startTime);
			if (StringUtil.isNull(endTime)) {
				endTime = DateUtil.convertDateToString(new Date(),
						DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
			}
			kmCalendarMainForm.setDocFinishTime(endTime);
			Date now = new Date();
			if (StringUtil.isNull(startHour)) {
				startHour = String.valueOf(now.getHours() + 1);
			}
			if (StringUtil.isNull(endHour)) {
				endHour = String.valueOf(now.getHours() + 2);
			}
			// 默认非全天
			if (StringUtil.isNull(isAllDayEvent)) {
				kmCalendarMainForm.setFdIsAlldayevent("false");
			} else {
				kmCalendarMainForm.setFdIsAlldayevent(isAllDayEvent);
			}
			kmCalendarMainForm.setFdIsLunar("false");
			if ("false".equals(kmCalendarMainForm.getFdIsAlldayevent())) {
				kmCalendarMainForm.setStartHour(startHour);
				kmCalendarMainForm.setStartMinute(startMinute);
				kmCalendarMainForm.setEndHour(endHour);
				kmCalendarMainForm.setEndMinute(endMinute);
				kmCalendarMainForm.setLunarStartHour(startHour);
				kmCalendarMainForm.setLunarStartMinute(startMinute);
				kmCalendarMainForm.setLunarEndHour(endHour);
				kmCalendarMainForm.setLunarEndMinute(endMinute);
			}
			if (kmCalendarMainForm != form) {
                request.setAttribute(getFormName(kmCalendarMainForm, request),
                        kmCalendarMainForm);
            }
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-addGroupEvent", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("editGroupEvent", mapping, form, request,
					response);
		}
	}

	/**
	 * 新增日程
	 */
	public ActionForward addEvent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-addEvent", true, getClass());
		KmssMessages messages = new KmssMessages();
		String labelId = request.getParameter("labelId");
		String ownerId = request.getParameter("ownerId");
		String ownerName = request.getParameter("ownerName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String isAllDayEvent = request.getParameter("isAllDayEvent");
		String startHour = request.getParameter("startHour");
		String startMinute = request.getParameter("startMinute");
		String endHour = request.getParameter("endHour");
		String endMinute = request.getParameter("endMinute");
		String subject = request.getParameter("subject");
		try {
			request.setAttribute("owners", getOwnersArray());
			request.setAttribute("labels", getLabelsArray(false));
			KmCalendarMainForm kmCalendarMainForm = (KmCalendarMainForm) createNewForm(
					mapping, form, request, response);
			kmCalendarMainForm
					.setFdType(KmCalendarConstant.CALENDAR_TYPE_EVENT);
			kmCalendarMainForm.setDocSubject(subject);
			labelId = StringUtil.isNull(labelId) ? "myEvent" : labelId;
			kmCalendarMainForm.setLabelId(labelId);
			kmCalendarMainForm.setDocOwnerId(ownerId);
			kmCalendarMainForm.setDocOwnerName(ownerName);
			Calendar nowCalendar = Calendar.getInstance();
			Date now = new Date();
			nowCalendar.setTime(now);
			// 如果开始结束时间为空,则为当前时间
			if (StringUtil.isNull(startTime)) {
				//#170547 英文下date数据mmddyyyy通过-convertStringToDate-转化异常->mmddhhhh,故修改为date4y
				startTime = DateUtil.convertDateToString(now,
						"date4y", UserUtil.getKMSSUser().getLocale());
			}

			if (StringUtil.isNull(endTime)) {
				endTime = DateUtil.convertDateToString(now,
						"date4y", UserUtil.getKMSSUser().getLocale());
			}
			Date startData =DateUtil.convertStringToDate(startTime);
			//开始日期加上当前时间
			Calendar cal = Calendar.getInstance();
			cal.setTime(startData);
			cal.set(Calendar.HOUR, nowCalendar.get(Calendar.HOUR_OF_DAY));
			cal.set(Calendar.MINUTE, nowCalendar.get(Calendar.MINUTE));
			if (StringUtil.isNull(startHour)) {
				cal.add(Calendar.HOUR, 1);
				startHour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
				startTime =DateUtil.convertDateToString(cal.getTime(),DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
			}else {
				cal.set(Calendar.HOUR, Integer.valueOf(startHour));
			}
			if (StringUtil.isNull(endHour)) {
				//结束时间再开始时间上加1
				cal.add(Calendar.HOUR, 1);
				endHour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
				endTime =DateUtil.convertDateToString(cal.getTime(),DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
			}
			kmCalendarMainForm.setDocStartTime(startTime);
			kmCalendarMainForm.setDocFinishTime(endTime);
			// 默认非全天
			if (StringUtil.isNull(isAllDayEvent)) {
				kmCalendarMainForm.setFdIsAlldayevent("false");
			} else {
				kmCalendarMainForm.setFdIsAlldayevent(isAllDayEvent);
			}
			kmCalendarMainForm.setFdIsLunar("false");
			if ("false".equals(kmCalendarMainForm.getFdIsAlldayevent())) {
				kmCalendarMainForm.setStartHour(startHour);
				kmCalendarMainForm.setStartMinute(startMinute);
				kmCalendarMainForm.setEndHour(endHour);
				kmCalendarMainForm.setEndMinute(endMinute);
				kmCalendarMainForm.setLunarStartHour(startHour);
				kmCalendarMainForm.setLunarStartMinute(startMinute);
				kmCalendarMainForm.setLunarEndHour(endHour);
				kmCalendarMainForm.setLunarEndMinute(endMinute);
			}
			String personId = "multiCreate".equals(ownerId) || StringUtil.isNull(ownerId) ? UserUtil.getUser().getFdId() : ownerId;
			initAuthData(personId, kmCalendarMainForm);
			kmCalendarMainForm.setDocOwnerId(personId);
			// kkim消息转日程
			initKKIMCalendar(kmCalendarMainForm, request);

			if (kmCalendarMainForm != form) {
                request.setAttribute(getFormName(kmCalendarMainForm, request),
                        kmCalendarMainForm);
            }
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-addEvent", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("editEvent", mapping, form, request,
					response);
		}
	}

	private void initKKIMCalendar(KmCalendarMainForm kmCalendarMainForm,
			HttpServletRequest request) throws Exception {
		String data = request.getParameter("data");
		if (StringUtil.isNotNull(data)
				&& MobileUtil.isFromKK(new RequestContext(request))) {
			logger.debug("kk消息转任务请求参数data:" + data);
			JSONObject json = JSONObject.fromObject(data);
			String content = (String) json.get("content");
			Object sessionId = json.get("sessionId");
			String sessionType = (String) json.get("sessionType");
			Object typeId = json.get("typeId");
			String sessionName = (String) json.get("sessionName");
			Object messageIndex = json.get("messageIndex");
			Object messageSenderId = json.get("messageSenderId");
			Object messageReceiverId = json.get("messageReceiverId");

			String ownerLoginName = (String) json.get("owner");
			if (StringUtil.isNotNull(ownerLoginName)) {
				SysOrgPerson sysOrgPerson = getSysOrgCoreService()
						.findByLoginName(ownerLoginName);
				if (sysOrgPerson != null) {
					kmCalendarMainForm.setDocOwnerId(sysOrgPerson.getFdId());
					kmCalendarMainForm.setDocOwnerName(ownerLoginName);
				}
			}
			// 可阅读者
			String availableReaders = (String) json.get("availableReaders");
			if (StringUtil.isNotNull(availableReaders)) {
				String[] loginNames = availableReaders.split(",");
				int length = loginNames.length;
				String fdPerformId = "";
				String fdPerformName = "";
				for (int i = 0; i < length; i++) {
					SysOrgPerson sysOrgPerson = getSysOrgCoreService()
							.findByLoginName(loginNames[i]);
					if (sysOrgPerson != null) {
						if (StringUtil.isNotNull(fdPerformId)) {
							fdPerformId += ";" + sysOrgPerson.getFdId();
							fdPerformName += ";" + sysOrgPerson.getFdName();
						} else {
							fdPerformId += sysOrgPerson.getFdId();
							fdPerformName += sysOrgPerson.getFdName();
						}
					}
				}
				kmCalendarMainForm.setAuthReaderIds(fdPerformId);
				kmCalendarMainForm.setAuthReaderNames(fdPerformName);
			}
			kmCalendarMainForm.setCreatedFrom("KK_IM");
			kmCalendarMainForm.setMethod_GET(kmCalendarMainForm.getMethod());
			kmCalendarMainForm.setDocSubject(content);
			kmCalendarMainForm.setFdSourceSubject(sessionName);
			kmCalendarMainForm.setFdSessionId(
					sessionId != null ? sessionId.toString() : null);
			kmCalendarMainForm.setFdSessionType(sessionType);
			kmCalendarMainForm
					.setFdTypeId(typeId != null ? typeId.toString() : null);
			kmCalendarMainForm.setFdMessageIndex(
					messageIndex != null ? messageIndex.toString() : null);
			kmCalendarMainForm.setFdMessageSenderId(messageSenderId != null
					? messageSenderId.toString() : null);
			kmCalendarMainForm.setFdMessageReceiverId(messageReceiverId != null
					? messageReceiverId.toString() : null);
			request.setAttribute("fdKkInfoJson", json);
		}
	}

	/**
	 * 新增笔记
	 */
	public ActionForward addNote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-addNote", true, getClass());
		KmssMessages messages = new KmssMessages();
		String startTime = request.getParameter("startTime");
		String subject = request.getParameter("subject");
		String docContent = request.getParameter("docContent");
		try {

			KmCalendarMainForm kmCalendarMainForm = (KmCalendarMainForm) createNewForm(
					mapping, form, request, response);
			kmCalendarMainForm.setFdType(KmCalendarConstant.CALENDAR_TYPE_NOTE);
			kmCalendarMainForm.setDocSubject(subject);
			kmCalendarMainForm.setDocContent(docContent);
			kmCalendarMainForm.setDocStartTime(startTime);
			kmCalendarMainForm.setFdIsLunar(null);
			if (kmCalendarMainForm != form) {
                request.setAttribute(getFormName(kmCalendarMainForm, request),
                        kmCalendarMainForm);
            }
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-addNote", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("editNote", mapping, form, request,
					response);
		}
	}

	/**
	 * 初始化权限
	 */
	private void initAuthData(String personId,
			KmCalendarMainForm kmCalendarMainForm) {
		try {
			Map<String, String> result = getAuthData(personId);
			if(StringUtil.isNotNull(kmCalendarMainForm.getAuthReaderIds())){
				kmCalendarMainForm.setAuthReaderIds(kmCalendarMainForm.getAuthReaderIds() + ";" + result.get("readersIdStr"));
			}else{
				kmCalendarMainForm.setAuthReaderIds(result.get("readersIdStr"));
			}
			if(StringUtil.isNotNull(kmCalendarMainForm.getAuthEditorIds())){
				kmCalendarMainForm.setAuthEditorIds(kmCalendarMainForm.getAuthEditorIds() + ";" + result.get("modifiersIdStr"));
			}else {
				kmCalendarMainForm.setAuthEditorIds(result.get("modifiersIdStr"));
			}
			kmCalendarMainForm.setAuthReaderNames(result.get("readersNameStr"));
			kmCalendarMainForm.setAuthEditorNames(result
					.get("modifiersNameStr"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取权限,返回json
	 */
	public ActionForward loadAuthData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String personId = request.getParameter("personId");
			Map<String, String> result = getAuthData(personId);
			request.setAttribute("lui-source", JSONObject.fromObject(result));
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			getActionForward("failure", mapping, form, request, response);
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		return getActionForward("lui-source", mapping, form, request, response);
	}

	/**
	 * 根据personID获取权限信息
	 */
	private Map<String, String> getAuthData(String personId)
			throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		KmCalendarAuth kmCalendarAuth = getKmCalendarAuthService()
				.findByPerson(personId);
		String[] modifierArrays = new String[] { "", "" };
		String[] readersArrays = new String[] { "", "" };
		if (kmCalendarAuth != null) {
			List<SysOrgElement> modifiersList = new ArrayList<>();
			List<SysOrgElement> modifiers = kmCalendarAuth.getAuthModifiers();
			modifiersList.addAll(modifiers);
			if (!modifiersList.contains(UserUtil.getUser())) {
				modifiersList.add(UserUtil.getUser());
			}
			modifierArrays = ArrayUtil.joinProperty(modifiersList,
					"fdId:fdName", ";");
			List<SysOrgElement> readersList = new ArrayList<>();
			List<SysOrgElement> readers = kmCalendarAuth.getAuthReaders();
			readersList.addAll(readers);
			if (!readers.contains(UserUtil.getUser())) {
				readersList.add(UserUtil.getUser());
			}
			readersArrays = ArrayUtil.joinProperty(readersList, "fdId:fdName",
					";");
		}
		result.put("readersIdStr", readersArrays[0]);
		result.put("readersNameStr", readersArrays[1]);
		result.put("modifiersIdStr", modifierArrays[0]);
		result.put("modifiersNameStr", modifierArrays[1]);
		return result;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		KmCalendarMainForm kmCalenarMainForm = (KmCalendarMainForm) form;
		String ownerId = kmCalenarMainForm.getDocOwnerId();

		if (ownerId.equals(UserUtil.getUser().getFdId())) {
			request.setAttribute("owners", getOwnersArray());
			request.setAttribute("labels", getLabelsArray(false));
		} else {
			List<String[]> labels = new ArrayList<String[]>();
			List<String[]> owners = new ArrayList<String[]>();
			String labelId = kmCalenarMainForm.getLabelId();
			String[] label = new String[2];
			if (StringUtil.isNotNull(labelId)) {
				label[0] = labelId;
				label[1] = kmCalenarMainForm.getLabelName();
			} else {
				label[0] = null;
				label[1] = ResourceUtil.getString(
						"module.km.calendar.tree.my.calendar", "km-calendar");
			}
			labels.add(label);
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"kmCalendarLabel.fdCreator.fdId=:fdId and kmCalendarLabel.fdModelName is not null and kmCalendarLabel.fdModelName <> ' '");
			hqlInfo.setParameter("fdId", ownerId);
			hqlInfo.setOrderBy(" kmCalendarLabel.fdOrder asc");
			List<KmCalendarLabel> calendarLabelList = getKmCalendarLabelService()
					.findList(hqlInfo);
			for (KmCalendarLabel calendarLabel : calendarLabelList) {
				String[] _label = new String[2];
				_label[0] = calendarLabel.getFdId();
				_label[1] = calendarLabel.getFdName();
				labels.add(_label);
			}
			String[] owner = new String[2];
			owner[0] = ownerId;
			owner[1] = kmCalenarMainForm.getDocOwnerName();
			owners.add(owner);
			request.setAttribute("owners", owners);
			request.setAttribute("labels", labels);
		}
		String fdType = kmCalenarMainForm.getFdType();
		String isAllDayEvent = kmCalenarMainForm.getFdIsAlldayevent();
		String startTime = kmCalenarMainForm.getDocStartTime();
		String endTime = kmCalenarMainForm.getDocFinishTime();
		if (StringUtil.isNull(endTime)) {
			endTime = startTime;
		}
		Map<String, String> dateTimeStrs_start = parseDateTime(startTime);
		Map<String, String> dateTimeStrs_end = parseDateTime(endTime);
		if (KmCalendarConstant.CALENDAR_TYPE_EVENT.equals(fdType)) {
			if ("true".equals(kmCalenarMainForm.getFdIsAlldayevent())) {
				SysNotifyRemindMainContextForm sysNotifyRemindMainContextForm = kmCalenarMainForm
						.getSysNotifyRemindMainContextForm();
				List<SysNotifyRemindMainForm> sysNotifyRemindMainFormList = sysNotifyRemindMainContextForm
						.getSysNotifyRemindMainFormList();
				for (SysNotifyRemindMainForm sysNotifyRemindMainForm : sysNotifyRemindMainFormList) {
					String fdTimeUnit = sysNotifyRemindMainForm
							.getFdTimeUnit();
					Integer fdBeforeTime = Integer.valueOf(
							sysNotifyRemindMainForm.getFdBeforeTime());
					switch (fdTimeUnit) {
					case "hour":
						sysNotifyRemindMainForm.setFdBeforeTime(
								String.valueOf(fdBeforeTime * 60));
						break;
					case "day":
						sysNotifyRemindMainForm.setFdBeforeTime(
								String.valueOf(fdBeforeTime * 24 * 60));
						break;
					case "week":
						sysNotifyRemindMainForm.setFdBeforeTime(
								String.valueOf(fdBeforeTime * 7 * 24 * 60));
						break;
					}
					sysNotifyRemindMainForm.setFdTimeUnit("minute");
				}
			}
			String recurrenctStr = kmCalenarMainForm.getFdRecurrenceStr();
			String isLunar = kmCalenarMainForm.getFdIsLunar();
			if ("true".equals(isLunar)) {
				// 公历转农历
				Lunar lunar = new Lunar(DateUtil.convertStringToDate(startTime,
						DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser()
								.getLocale()));
				String lunarStartYear = lunar.getLunarYear() + "";
				String lunarStartMonth = lunar.getLunarMonth() + "";
				String lunarStartDay = lunar.getLunarDay() + "";
				if (lunar.isLeap()) {
					lunarStartMonth = "r" + lunarStartMonth;
				}
				kmCalenarMainForm.setLunarStartYear(lunarStartYear);
				kmCalenarMainForm.setLunarStartMonth(lunarStartMonth);
				kmCalenarMainForm.setLunarStartDay(lunarStartDay);

				lunar = new Lunar(DateUtil.convertStringToDate(startTime,
						DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser()
								.getLocale()));
				String lunarEndYear = lunar.getLunarYear() + "";
				String lunarEndMonth = lunar.getLunarMonth() + "";
				String lunarEndDay = lunar.getLunarDay() + "";
				if (lunar.isLeap()) {
					lunarEndMonth = "r" + lunarEndMonth;
				}
				kmCalenarMainForm.setLunarEndYear(lunarEndYear);
				kmCalenarMainForm.setLunarEndMonth(lunarEndMonth);
				kmCalenarMainForm.setLunarEndDay(lunarEndDay);

				if (!"true".equals(isAllDayEvent)) {
					kmCalenarMainForm.setLunarStartHour(dateTimeStrs_start
							.get("hour"));
					kmCalenarMainForm.setLunarStartMinute(dateTimeStrs_start
							.get("minute"));
					kmCalenarMainForm.setLunarEndHour(dateTimeStrs_end
							.get("hour"));
					kmCalenarMainForm.setLunarEndMinute(dateTimeStrs_end
							.get("minute"));
				}
				if (StringUtil.isNotNull(recurrenctStr)) {
					Map<String, String> result = Rfc2445Util
							.parseRecurrenceStr(recurrenctStr);
					String freq = result
							.get(KmCalendarConstant.RECURRENCE_FREQ);
					String interval = result
							.get(KmCalendarConstant.RECURRENCE_INTERVAL);
					String endType = result
							.get(KmCalendarConstant.RECURRENCE_END_TYPE);

					kmCalenarMainForm.setRECURRENCE_FREQ_LUNAR(freq);
					kmCalenarMainForm.setRECURRENCE_INTERVAL_LUNAR(interval);
					kmCalenarMainForm.setRECURRENCE_END_TYPE_LUNAR(endType);
					if (KmCalendarConstant.RECURRENCE_END_TYPE_COUNT
							.equals(endType)) {
						kmCalenarMainForm.setRECURRENCE_COUNT_LUNAR(result
								.get(KmCalendarConstant.RECURRENCE_COUNT));
					} else if (KmCalendarConstant.RECURRENCE_END_TYPE_UNTIL
							.equals(endType)) {
						String until = result
								.get(KmCalendarConstant.RECURRENCE_UNTIL);
						until = until.substring(0, 4) + "-"
								+ until.substring(4, 6) + "-"
								+ until.substring(6);
						kmCalenarMainForm.setRECURRENCE_UNTIL_LUNAR(until);
					}
				}
			} else {
				if (!"true".equals(isAllDayEvent)) {
					kmCalenarMainForm.setStartHour(dateTimeStrs_start
							.get("hour"));
					kmCalenarMainForm.setStartMinute(dateTimeStrs_start
							.get("minute"));
					kmCalenarMainForm.setEndHour(dateTimeStrs_end.get("hour"));
					kmCalenarMainForm.setEndMinute(dateTimeStrs_end
							.get("minute"));
				}
				if (StringUtil.isNotNull(recurrenctStr)) {
					Map<String, String> result = Rfc2445Util
							.parseRecurrenceStr(recurrenctStr);
					String freq = result
							.get(KmCalendarConstant.RECURRENCE_FREQ);
					String interval = result
							.get(KmCalendarConstant.RECURRENCE_INTERVAL);
					String endType = result
							.get(KmCalendarConstant.RECURRENCE_END_TYPE);
					kmCalenarMainForm.setRECURRENCE_FREQ(freq);
					if (KmCalendarConstant.RECURRENCE_FREQ_WEEKLY.equals(freq)) {
						kmCalenarMainForm.setRECURRENCE_WEEKS(result
								.get(KmCalendarConstant.RECURRENCE_WEEKS)
								.replaceAll(",", ";"));
					} else if (KmCalendarConstant.RECURRENCE_FREQ_MONTHLY
							.equals(freq)) {
						String monthType = result
								.get(KmCalendarConstant.RECURRENCE_MONTH_TYPE);
						kmCalenarMainForm.setRECURRENCE_MONTH_TYPE(monthType);

					}
					kmCalenarMainForm.setRECURRENCE_INTERVAL(interval);

					kmCalenarMainForm.setRECURRENCE_END_TYPE(endType);
					if (KmCalendarConstant.RECURRENCE_END_TYPE_COUNT
							.equals(endType)) {
						kmCalenarMainForm.setRECURRENCE_COUNT(result
								.get(KmCalendarConstant.RECURRENCE_COUNT));
					} else if (KmCalendarConstant.RECURRENCE_END_TYPE_UNTIL
							.equals(endType)) {
						String until = result
								.get(KmCalendarConstant.RECURRENCE_UNTIL);
						until = until.substring(0, 4) + "-"
								+ until.substring(4, 6) + "-"
								+ until.substring(6);
						kmCalenarMainForm.setRECURRENCE_UNTIL(until);
					}
				}
			}
			kmCalenarMainForm.setDocStartTime(dateTimeStrs_start.get("date"));
			kmCalenarMainForm.setDocFinishTime(dateTimeStrs_end.get("date"));
		} else {
			if (!"true".equals(isAllDayEvent)) {
				kmCalenarMainForm.setStartHour(dateTimeStrs_start.get("hour"));
				kmCalenarMainForm.setStartMinute(dateTimeStrs_start
						.get("minute"));
				kmCalenarMainForm.setEndHour(dateTimeStrs_end.get("hour"));
				kmCalenarMainForm.setEndMinute(dateTimeStrs_end.get("minute"));
			} else {
				kmCalenarMainForm.setDocStartTime(dateTimeStrs_start.get("date"));
				kmCalenarMainForm.setDocFinishTime(dateTimeStrs_end.get("date"));
			}
		}
		String creatorId = kmCalenarMainForm.getDocCreatorId();
		if (StringUtil.isNotNull(creatorId)
				&& !UserUtil.getUser().getFdId().equals(creatorId)) {
			request.setAttribute("creatorName", kmCalenarMainForm
					.getDocCreatorName());
		}
		if (StringUtil.isNotNull(kmCalenarMainForm.getLabelId())) {
			KmCalendarLabel kmCalendarLabel = (KmCalendarLabel) getKmCalendarLabelService()
					.findByPrimaryKey(kmCalenarMainForm.getLabelId());
			if (kmCalendarLabel != null) {
				// 获取模块名称
				SysDictModel sysDict = SysDataDict.getInstance()
						.getModel(kmCalendarLabel.getFdModelName());
				String labelName = (sysDict == null
						? kmCalendarLabel.getFdName()
						: ResourceUtil.getString(sysDict.getMessageKey()));
				if (KmCalendarConstant.CALENDAR_MY_EVENT.equals(labelName)) {
					labelName = ResourceUtil.getString("km-calendar:kmCalendar.nav.title");
				} else if (KmCalendarConstant.CALENDAR_MY_GROUP_EVENT.equals(labelName)) {
					labelName = ResourceUtil.getString("km-calendar:kmCalendarMain.group.header.title");
				} else if (KmCalendarConstant.CALENDAR_MY_NOTE.equals(labelName)) {
					labelName = ResourceUtil.getString("km-calendar:module.km.calendar.tree.my.note");
				}
				kmCalenarMainForm.setLabelName(labelName);
			}
		}
		if (StringUtil.isNull(kmCalenarMainForm.getLabelId())) {
			if ("true".equals(kmCalenarMainForm.getFdIsGroup())) {
				kmCalenarMainForm
						.setLabelId(KmCalendarConstant.CALENDAR_MY_GROUP_EVENT);
			} else {
				kmCalenarMainForm
						.setLabelId(KmCalendarConstant.CALENDAR_MY_EVENT);
				kmCalenarMainForm.setLabelName(ResourceUtil.getString(
						"module.km.calendar.tree.my.calendar", "km-calendar"));
			}

		}
	}

	// yyyy-MM-dd HH:mm:ss.SSS
	private static Map<String, String> parseDateTime(String dateTime) {
		Map<String, String> result = new HashMap<String, String>();
		String date = dateTime.substring(0, 10);
		String hour = dateTime.substring(11, 13);
		String minute = dateTime.substring(14, 16);
		result.put("date", date);
		result.put("hour", hour);
		result.put("minute", minute);
		return result;
	}

	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		KmCalendarMainForm kmCalenarMainForm = (KmCalendarMainForm) form;
		String fdType = kmCalenarMainForm.getFdType();
		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			if (KmCalendarConstant.CALENDAR_TYPE_EVENT.equals(fdType)) {
				return getActionForward("editEvent", mapping, form, request,
						response);
			} else {
				return getActionForward("editNote", mapping, form, request,
						response);
			}
		}
	}

	public ActionForward notifyView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-notifyView", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-notifyView", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("notifyView", mapping, form, request,
					response);
		}
	}

	/**
	 * 检查日程是否存在
	 */
	public ActionForward checkCalendarIsExists(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		TimeCounter.logCurrentTime("Action-checkCalendarIsExists", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		boolean status = false;
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
				KmCalendarMain kmCalendarMain = (KmCalendarMain) getServiceImp(
						request).findByPrimaryKey(id);
				if (kmCalendarMain != null) {
					status = true;
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-checkCalendarIsExists", false,
				getClass());
		if (messages.hasError()) {
            status = false;
        }
		JSONObject json = new JSONObject();
		json.put("status", status);// 执行结果
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 修改日程标签
	 */
	public ActionForward updateLabelAndRemind(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-updateLabelAndRemind", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		boolean status = true;
		boolean isRecurrence = false;
		boolean isGroup = false;//保存修改前的日程是否属于组群日程
		boolean changeGroup = false;//是否涉及改动群组标签
		JSONObject obj = new JSONObject();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String id = request.getParameter("fdId");
			String labelId = request.getParameter("labelId");
			String clearRemind = request.getParameter("clearRemind");
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
				KmCalendarMain kmCalendarMain = (KmCalendarMain) getServiceImp(
						request).findByPrimaryKey(id);
				if (kmCalendarMain !=null) {
					isGroup =  kmCalendarMain.getFdIsGroup() !=null ? kmCalendarMain.getFdIsGroup() : false;
				}
				if(isGroup){
					//组群不可修改
					obj = genCalendarData(kmCalendarMain);
					isRecurrence = true;
					status = true;
					changeGroup = true;
					//可以组群改组群
					if(KmCalendarConstant.CALENDAR_MY_GROUP_EVENT.equals(labelId)){
						changeGroup = false;
					}
				}else if (KmCalendarConstant.CALENDAR_MY_GROUP_EVENT.equals(labelId)){
					obj = genCalendarData(kmCalendarMain);
					isRecurrence = false;
					status = true;
					changeGroup = true;
				}else{
					if (StringUtil.isNull(labelId)) {
						kmCalendarMain.setDocLabel(null);
					} else {
						if(KmCalendarConstant.CALENDAR_MY_EVENT.equals(labelId)){
							kmCalendarMain.setDocLabel(null);
						}else{
							kmCalendarMain
								.setDocLabel((KmCalendarLabel) getKmCalendarLabelService()
										.findByPrimaryKey(labelId));
						}
					}
					if ("true".equals(clearRemind)) {
						// 将提醒主文档的modelName置为(删除提醒)
						for (SysNotifyRemindMain sysNotifyRemindMain : (List<SysNotifyRemindMain>) sysNotifyRemindMainService
								.getCoreModels(kmCalendarMain, null)) {
							sysNotifyRemindMain.setFdModelName(null);
						}
						// kmCalendarMain.getSysNotifyRemindMainContextModel().getSysNotifyRemindMainList().clear();
						// 删除提醒定时任务
						getServiceImp(request).deleteScheduler(kmCalendarMain);
					}
					getServiceImp(request).update(kmCalendarMain);
					// 生成JSON对象
					obj = genCalendarData(kmCalendarMain);
					// 如果是重复日程，isRecurrence=true
					if (StringUtil.isNotNull(kmCalendarMain.getFdRecurrenceStr())) {
						isRecurrence = true;
					}
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-updateLabelAndRemind", false,
				getClass());
		if (messages.hasError()) {
            status = false;
        }
		JSONObject json = new JSONObject();
		json.put("status", status);// 执行结果
		json.put("schedule", obj);// 日程对象
		json.put("isRecurrence", isRecurrence);// 是否重复日程
		json.put("changeGroup", changeGroup);// 是否重复日程
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 权限判断
	 */
	public ActionForward checkEditAuth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		TimeCounter.logCurrentTime("Action-checkEditAuth", true, getClass());
		String calendarId = request.getParameter("calendarId");
		JSONObject object = new JSONObject();
		if (StringUtil.isNotNull(calendarId)) {
			object.put("canEdit", false);
		}
		if (UserUtil.getKMSSUser().isAdmin()) {
			object.put("canEdit", true);
		} else {
			HQLInfo info = new HQLInfo();
			// info.setSelectBlock("kmCalendarMain.fdId");
			info.setDistinctType(HQLInfo.DISTINCT_YES);
			info.setJoinBlock("left join kmCalendarMain.authAllEditors auths");
			String whereBlock = HQLUtil.buildLogicIN("auths.fdId", UserUtil
					.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
			info.setWhereBlock(whereBlock
					+ " and kmCalendarMain.fdId = :calendarId");
			info.setParameter("calendarId", calendarId);
			try {
				List list = getServiceImp(request).findList(info);
				if (list != null && list.size() > 0) {
					KmCalendarMain kmCalendarMain = (KmCalendarMain) list
							.get(0);
					Boolean fdIsGroup = kmCalendarMain.getFdIsGroup();
					if (fdIsGroup != null && fdIsGroup.booleanValue()) {
						KmCalendarMainGroup mainGroup = (KmCalendarMainGroup) getKmCalendarMainGroupService()
								.findMainGroupByMainId(
										kmCalendarMain.getFdId());
						KmCalendarPersonGroup personGroup = mainGroup
								.getFdGroup();
						List editors = getSysOrgCoreService()
								.expandToPerson(personGroup.getAuthEditors());
						List<KmCalendarMain> calendars = mainGroup
								.getFdMainList();
						SysOrgPerson curUser = UserUtil.getUser();
						for (KmCalendarMain calendar : calendars) {
							if (curUser.equals(calendar.getDocOwner())
									|| editors.contains(curUser)) {
								object.put("canEdit", true);
								break;
							}
						}
					} else {
						object.put("canEdit", true);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				object.put("canEdit", false);
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(object.toString());
		return null;
	}

	/**
	 * 导出日程
	 */
	public ActionForward exportCalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TimeCounter.logCurrentTime("Action-exportCalendar", true, getClass());
		KmssMessages messages = new KmssMessages();

		String type = request.getParameter("type");// 导出类型:个人日历？群组日历？
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		Date docStartTime = new Date();
		Date docFinishTime = new Date();
		List<KmCalendarMain> calendars = new ArrayList<KmCalendarMain>();// 日程集合
		String title = "data";// Excel标题
		String personIds = "";// 查询人员
		String labelIds = null;// 要查询的标签
		String evenType = "event";
		if (StringUtil.isNotNull(startTime)) {
			docStartTime = DateUtil.convertStringToDate(startTime,
					DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
		}
		if (StringUtil.isNotNull(endTime)) {
			docFinishTime = DateUtil.convertStringToDate(endTime,
					DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(docFinishTime);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			// 结束日期设置为yyyy-MM-dd 23:59:59
			docFinishTime = calendar.getTime();
		}
		if ("personGroupCalendar".equals(type)) {// 导出人员群组日程
			String personGroupId = request.getParameter("personGroupId");
			KmCalendarPersonGroup kmCalendarPersonGroup = (KmCalendarPersonGroup) getKmCalendarPersonGroupService()
					.findByPrimaryKey(personGroupId);
			title = ResourceUtil
					.getString(
							"km-calendar:kmCalendarMain.export.groupCalendar")
					.replace("%group%", kmCalendarPersonGroup.getDocSubject());
			List<SysOrgElement> list = kmCalendarPersonGroup.getFdPersonGroup();
			//展开所有人员
			List<SysOrgPerson> allPersons = getSysOrgCoreService().expandToPerson(list);

			for (SysOrgElement person : allPersons) {
				if (person.getFdIsAvailable() == true) {
					personIds += person.getFdId() + ";";
				}
			}
		} else if ("groupCalendar".equals(type)) {// 导出共享分组日程
			String groupId = request.getParameter("groupId");
			if (StringUtil.isNull(groupId)) {
				groupId = "defaultGroup";
			}
			if ("defaultGroup".equals(groupId)) {
				title = ResourceUtil.getString(
						"km-calendar:kmCalendarMain.export.shareGroupCalendar")
						.replace(
								"%group%",
								ResourceUtil
										.getString("km-calendar:module.km.calendar.tree.share.all"));
				List<SysOrgElement> list = getKmCalendarAuthService()
						.getDefaultGroupMembers(new RequestContext(request))
						.get("totalPersons");
				for (SysOrgElement person : list) {
					personIds += person.getFdId() + ";";
				}
			} else {
				KmCalendarShareGroup kmCalendarShareGroup = (KmCalendarShareGroup) getKmCalendarShareGroupService()
						.findByPrimaryKey(groupId);
				title = ResourceUtil.getString(
						"km-calendar:kmCalendarMain.export.shareGroupCalendar")
						.replace("%group%", kmCalendarShareGroup.getFdName());// XX共享分组的日程
				List<SysOrgElement> list = kmCalendarShareGroup
						.getShareGroupMembers();
				for (SysOrgElement person : list) {
					if (person.getFdIsAvailable() == true) {
						personIds += person.getFdId() + ";";
					}
				}
			}
		} else {// 导出个人日程
			evenType = "";// 笔记导出
			String exceptLabelIds = request.getParameter("exceptLabelIds");
			if (StringUtil.isNull(labelIds)
					&& StringUtil.isNotNull(exceptLabelIds)) {
				labelIds = buildLabelIds(exceptLabelIds);
			}
			title = ResourceUtil
					.getString("km-calendar:kmCalendarMain.export.myCalendar");// 我的日程
			personIds = UserUtil.getUser().getFdId();
		}
		List<KmCalendarMain> rangeCalendars = getServiceImp(request)
				.getRangeCalendars(docStartTime, docFinishTime, evenType, false,
						personIds, labelIds);// 非重复日程
		List<KmCalendarMain> recurrenceCalendars = getServiceImp(request)
				.getRecurrenceCalendars(docStartTime, docFinishTime, personIds,
						labelIds);// 重复日程
		if (!ArrayUtil.isEmpty(rangeCalendars)) {
			calendars.addAll(rangeCalendars);
		}
		if (!ArrayUtil.isEmpty(recurrenceCalendars)) {
			calendars.addAll(recurrenceCalendars);
		}
		// 记录操作日志
		UserOperHelper.logFindAll(calendars,
				getServiceImp(request).getModelName());
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=\""
				+ ExcelUtil.getFileName(title, request) + ".xls\"");
		ServletOutputStream out = response.getOutputStream();
		try {
			HSSFWorkbook workbook = buildWorkBook(calendars, title);
			workbook.write(out);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
		TimeCounter.logCurrentTime("Action-exportCalendar", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return null;
		}
	}

	/**
	 * 构建Excel文件对象
	 */
	private HSSFWorkbook buildWorkBook(List<KmCalendarMain> list, String title) {
		/* 创建一个excel工作簿对象 */
		HSSFWorkbook workbook = new HSSFWorkbook();
		/* 创建一个工作表对象 */
		HSSFSheet sheet = workbook.createSheet();

		sheet.setColumnWidth(0, 10000);// 标题
		sheet.setColumnWidth(1, 3000);// 所有者
		sheet.setColumnWidth(2, 3000);// 标签
		sheet.setColumnWidth(3, 4000);// 地点
		sheet.setColumnWidth(4, 5000);// 开始时间
		sheet.setColumnWidth(5, 5000);// 结束时间
		sheet.setColumnWidth(6, 10000);// 详情

		workbook.setSheetName(0, title);
		int rowIndex = 0;
		/* 标题行 */
		HSSFRow titlerow = sheet.createRow(rowIndex++);
		HSSFCellStyle titleCellStyle = workbook.createCellStyle();
		titleCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);// 居中对齐
		HSSFFont font = workbook.createFont();
		font.setBold(true);// 加粗
		titleCellStyle.setFont(font);
		// titlerow.setRowStyle(titleCellStyle);
		HSSFCell[] cells = new HSSFCell[7];
		for (int i = 0; i < cells.length; i++) {
			cells[i] = titlerow.createCell(i);
			cells[i].setCellStyle(titleCellStyle);
		}
		cells[0].setCellValue(ResourceUtil
				.getString("km-calendar:kmCalendarMain.docSubject"));
		cells[1].setCellValue(ResourceUtil
				.getString("km-calendar:kmCalendarMain.docOwner"));
		cells[2].setCellValue(ResourceUtil
				.getString("km-calendar:kmCalendarMain.docLabel"));
		cells[3].setCellValue(ResourceUtil
				.getString("km-calendar:kmCalendarMain.fdLocation"));
		cells[4].setCellValue(ResourceUtil
				.getString("km-calendar:kmCalendarMain.docStartTime"));
		cells[5].setCellValue(ResourceUtil
				.getString("km-calendar:kmCalendarMain.docFinishTime"));
		cells[6].setCellValue(ResourceUtil
				.getString("km-calendar:kmCalendarMain.detailDocContent"));
		// 样式
		HSSFCellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);// 居中对齐
		/* 内容行 */
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				KmCalendarMain calendar = list.get(i);
				HSSFRow contentrow = sheet.createRow(rowIndex++);
				HSSFCell[] contentcells = new HSSFCell[7];
				for (int j = 0; j < contentcells.length; j++) {
					contentcells[j] = contentrow.createCell(j);
					if (j != 0) {
                        contentcells[j].setCellStyle(contentCellStyle);// 除了标题其他内容居中
                    }
				}
				contentcells[0].setCellValue(calendar.getDocSubject());
				contentcells[1]
						.setCellValue(calendar.getDocOwner().getFdName());
				if (calendar.getDocLabel() != null) {
					contentcells[2].setCellValue(calendar.getDocLabel()
							.getFdName());
				} else if (BooleanUtils.isTrue(calendar.getFdIsGroup())) {
					contentcells[2].setCellValue(ResourceUtil
							.getString("km-calendar:kmCalendarMain.calendar.group"));
				} else {
					contentcells[2].setCellValue(ResourceUtil
							.getString("km-calendar:kmCalendar.nav.title"));
				}
				contentcells[3].setCellValue(calendar.getFdLocation());
				contentcells[4].setCellValue(DateUtil.convertDateToString(
						calendar.getDocStartTime(), DateUtil.PATTERN_DATETIME));
				contentcells[5]
						.setCellValue(DateUtil.convertDateToString(calendar
								.getDocFinishTime(), DateUtil.PATTERN_DATETIME));
				String docContent = calendar.getDocContent() == null ? ""
						: calendar.getDocContent().replaceAll("<(//)*[^>]*>",
								"");
				contentcells[6].setCellValue(docContent);
			}
		}

		return workbook;
	}

	private class CalendarComparator implements Comparator<KmCalendarMain> {
		@Override
		public int compare(KmCalendarMain o1, KmCalendarMain o2) {
			int result = 0;
			if (o1.getDocStartTime().getTime() > o2.getDocStartTime().getTime()) {
				result = 1;
			}else if(o1.getDocStartTime().getTime() < o2.getDocStartTime().getTime()){
				result = -1;
			}else{
				if(o1.getDocCreateTime().getTime() > o2.getDocCreateTime().getTime()){
					result  = 1;
				} else if (o1.getDocCreateTime().getTime() < o2
						.getDocCreateTime().getTime()) {
					result = -1;
				}
			}
			return result;
		}
	}

	/**
	 * 检查提醒是否能够正常执行
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward checkRemind(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		TimeCounter.logCurrentTime("Action-checkRemind", true, getClass());
		String fdId = request.getParameter("fdId");
		JSONObject object = getServiceImp(request).checkRemind(fdId);
		TimeCounter.logCurrentTime("Action-checkRemind", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(object.toString());
		return null;
	}

	/**
	 * 检查模块是否存在
	 * @description:
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return: com.landray.kmss.web.action.ActionForward
	 * @author: wangjf
	 * @time: 2021/12/22 4:29 下午
	 */
	public ActionForward checkModule(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									 HttpServletResponse response) throws IOException {
		TimeCounter.logCurrentTime("Action-checkRemind", true, getClass());
		String innerUrl = request.getParameter("innerUrl");
		JSONObject object = new JSONObject();
		object.put("result", false);
		if(StringUtil.isNotNull(innerUrl)){
			try {
				List moduleInfoList = SysConfigs.getInstance().getModuleInfoList();
				for (Object module : moduleInfoList) {
					SysCfgModuleInfo temp = (SysCfgModuleInfo) module;
					if(innerUrl.indexOf(temp.getUrlPrefix())>-1){
						object.put("result", true);
						break;
					}
				}
			}catch (RuntimeException e){
				logger.error("时间管理验证模块出错",e);
			}
		}
		TimeCounter.logCurrentTime("Action-checkRemind", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/json;charset=UTF-8");
		response.getWriter().write(object.toString());
		return null;
	}

}
