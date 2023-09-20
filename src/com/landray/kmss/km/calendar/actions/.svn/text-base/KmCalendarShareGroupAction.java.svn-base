package com.landray.kmss.km.calendar.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.calendar.constant.KmCalendarConstant;
import com.landray.kmss.km.calendar.model.KmCalendarAuth;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.km.calendar.model.KmCalendarShareGroup;
import com.landray.kmss.km.calendar.service.IKmCalendarAuthService;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.km.calendar.service.IKmCalendarShareGroupService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.notify.model.SysNotifyRemindMain;
import com.landray.kmss.sys.notify.service.ISysNotifyRemindMainService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
 * 日程共享组设置 Action
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public class KmCalendarShareGroupAction extends ExtendAction {

	protected IKmCalendarShareGroupService kmCalendarShareGroupService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
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

	protected IKmCalendarMainService kmCalendarMainService;

	protected IKmCalendarMainService getKmCalendarMainService() {
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

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = "1=1 ";
		}
		whereBlock += " and kmCalendarShareGroup.docCreator.fdId= :docCreatorId";
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy("kmCalendarShareGroup.fdOrder");
		hqlInfo.setParameter("docCreatorId", UserUtil.getUser().getFdId());

	}

	public ActionForward listUserGroupJson(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		KmCalendarShareGroup kmCalendarShareGroup = new KmCalendarShareGroup();
		hqlInfo
				.setWhereBlock("kmCalendarShareGroup.docCreator.fdId = :personId");
		hqlInfo.setParameter("personId", UserUtil.getUser().getFdId());
		List<KmCalendarShareGroup> list = getServiceImp(request).findList(
				hqlInfo);
		UserOperHelper.logFindAll(list, getServiceImp(request).getModelName());
		UserOperHelper.setOperSuccess(true);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("id", "defaultGroup");
		jsonObject.accumulate("name", ResourceUtil.getString(
				"module.km.calendar.tree.share.all", "km-calendar"));
		jsonArray.add(jsonObject);
		for (KmCalendarShareGroup group : list) {
			jsonObject = new JSONObject();
			jsonObject.accumulate("id", group.getFdId());
			jsonObject.accumulate("name",StringUtil.XMLEscape(group.getFdName()) );
			jsonArray.add(jsonObject);
		}
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		// System.out.println(jsonArray.toString());
		response.getWriter().write(jsonArray.toString());

		return null;
	}

	public ActionForward listGroupSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		String groupId = request.getParameter("groupId");
		String keyword = request.getParameter("keyword");
		String checkType = request.getParameter("checkType");
		SysOrgPerson curUser = UserUtil.getUser();
		List<SysOrgElement> groupMembers = new ArrayList<SysOrgElement>();
		if (StringUtil.isNull(groupId) || "defaultGroup".equals(groupId)) {// 全部共享
			RequestContext requestContext = new RequestContext(request);
			requestContext.setParameter("userId", curUser.getFdId());
			Map<String, List> maps = getKmCalendarAuthService()
					.getDefaultGroupMembers(requestContext);
			groupMembers = maps.get("persons");
		} else {// 指定共享组
			Map<String, List> maps = ((IKmCalendarShareGroupService) getServiceImp(
					request)).getShareGroupMembers(new RequestContext(request));
			groupMembers = maps.get("persons");
		}
		JSONArray arr = new JSONArray();
		for (SysOrgElement ele : groupMembers) {
			String eleId = ele.getFdId();
			String eleName = ele.getFdName();
			SysOrgElement parent = ele.getFdParent();
			String eleDept = parent != null ? parent.getDeptLevelNames()
					: ele.getDeptLevelNames();
			boolean canRead = false;
			boolean canEditor = false;
			boolean canModifier = false;
			if (ele.equals(curUser)) {
				canRead = true;
				canEditor = true;
				canModifier = true;
			} else {
				KmCalendarAuth auth = getKmCalendarAuthService()
						.findByPerson(eleId);
				if (auth != null) {
					List<SysOrgElement> authReaders = getSysOrgCoreService()
							.expandToPerson(auth.getAuthReaders());
					canRead = authReaders.contains(curUser);
					if (!"read".equals(checkType)) {
						List<SysOrgElement> authEditors = getSysOrgCoreService()
								.expandToPerson(auth.getAuthEditors());
						canEditor = authEditors.contains(curUser);
						List<SysOrgElement> authModifiers = getSysOrgCoreService()
								.expandToPerson(auth.getAuthModifiers());
						canModifier = authModifiers.contains(curUser);
					}
				}
			}
			JSONObject obj = new JSONObject();
			obj.accumulate("id", eleId);
			obj.accumulate("name", eleName);
			obj.accumulate("dept", eleDept);
			obj.accumulate("canRead", canRead);
			obj.accumulate("canEditor", canEditor);
			obj.accumulate("canModifier", canModifier);
			arr.add(obj);
		}
		PrintWriter out = response.getWriter();
		if (StringUtil.isNotNull(keyword)) {
			JSONArray arr2 = new JSONArray();
			Iterator it = arr.iterator();
			while (it.hasNext()) {
				JSONObject obj2 = (JSONObject) it.next();
				String name = (String) obj2.get("name");
				if (name.contains(keyword)) {
					arr2.add(obj2);
				}
			}
			out.println(arr2.toString());
		} else {
			out.write(arr.toString());
		}
		return null;
	}

	public ActionForward listGroupCalendar(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TimeCounter
				.logCurrentTime("Action-listGroupCalendar", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String includeSelf = request.getParameter("include_self");
			String groupId = request.getParameter("groupId");
			List<SysOrgElement> groupMembers = new ArrayList<SysOrgElement>();
			if (StringUtil.isNull(groupId) || "defaultGroup".equals(groupId)) {// 全部共享
				Map<String, List> maps = getKmCalendarAuthService()
						.getDefaultGroupMembers(new RequestContext(request),
								true);
				groupMembers = maps.get("persons");
			} else {// 指定共享组
				Map<String, List> maps = ((IKmCalendarShareGroupService) getServiceImp(request))
						.getShareGroupMembers(new RequestContext(request), true);
				groupMembers = maps.get("persons");
			}
			String memberIds = "";
			for (SysOrgElement member : groupMembers) {
				if ("false".equals(includeSelf)
						&& member.getFdId()
								.equals(UserUtil.getUser().getFdId())) {
					continue;
				}
				memberIds += member.getFdId() + ";";
			}
			String startStr = request.getParameter("fdStart");
			String endStr = request.getParameter("fdEnd");
			Date rangeStart = null;
			Date rangeEnd = null;
			if (StringUtil.isNotNull(startStr)) {
				rangeStart = DateUtil.convertStringToDate(startStr,
						DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
				rangeEnd = DateUtil.convertStringToDate(endStr,
						DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
			} else {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -3);
				rangeStart = calendar.getTime();
				calendar.add(Calendar.DATE, 7);
				rangeEnd = calendar.getTime();
			}

			List<KmCalendarMain> kmCalendarMains = new ArrayList<KmCalendarMain>();

			List<KmCalendarMain> kmNormalCalendarMains = getKmCalendarMainService()
					.getRangeCalendars(rangeStart, rangeEnd,
							KmCalendarConstant.CALENDAR_TYPE_EVENT, false,
							memberIds, null);
			if (kmNormalCalendarMains != null) {
				kmCalendarMains.addAll(kmNormalCalendarMains);
			}

			List<KmCalendarMain> recurrenceCalendars = getKmCalendarMainService()
					.getRecurrenceCalendars(rangeStart, rangeEnd, memberIds,
							null);
			if (recurrenceCalendars != null) {
				kmCalendarMains.addAll(recurrenceCalendars);
			}
			Collections.sort(kmCalendarMains, new CalendarComparator());
			loadCalendarReminds(kmCalendarMains);
			request.setAttribute("calendars", kmCalendarMains);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-listGroupCalendar", false,
				getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listGroupCalendar", mapping, form,
					request, response);
		}
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

	private class CalendarComparator implements Comparator<KmCalendarMain> {
		@Override
		public int compare(KmCalendarMain o1, KmCalendarMain o2) {
			int result = 0;
			if (o1.getDocStartTime().getTime() > o2.getDocStartTime().getTime()) {
				result = 1;
			} else if (o1.getDocStartTime().getTime() < o2.getDocStartTime()
					.getTime()) {
				result = -1;
			} else {
				if (o1.getDocCreateTime().getTime() > o2.getDocCreateTime()
						.getTime()) {
					result = 1;
				} else if (o1.getDocCreateTime().getTime() < o2
						.getDocCreateTime().getTime()) {
					result = -1;
				}
			}
			return result;
		}
	}

}
