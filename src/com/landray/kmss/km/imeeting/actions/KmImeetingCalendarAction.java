package com.landray.kmss.km.imeeting.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.imeeting.model.KmImeetingBook;
import com.landray.kmss.km.imeeting.model.KmImeetingEquipment;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.service.IKmImeetingBookService;
import com.landray.kmss.km.imeeting.service.IKmImeetingEquipmentService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainFeedbackService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.km.imeeting.service.IKmImeetingResCategoryService;
import com.landray.kmss.km.imeeting.service.IKmImeetingResService;
import com.landray.kmss.km.imeeting.util.ImeetingCalendarUtil;
import com.landray.kmss.km.imeeting.util.ImeetingCateUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 会议室日历 Action
 */
public class KmImeetingCalendarAction extends ExtendAction {

	private static final Log logger = LogFactory
			.getLog(KmImeetingCalendarAction.class);

	private IKmImeetingResCategoryService kmImeetingResCategoryService;
	private IKmImeetingResService kmImeetingResService;
	private IKmImeetingMainService kmImeetingMainService;
	private IKmImeetingBookService kmImeetingBookService;
	private IKmImeetingMainFeedbackService kmImeetingMainFeedbackService;
	private IKmImeetingEquipmentService kmImeetingEquipmentService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return null;
	}

	protected IKmImeetingResCategoryService getKmImeetingResCategoryService(
			HttpServletRequest request) {
		if (kmImeetingResCategoryService == null) {
			kmImeetingResCategoryService = (IKmImeetingResCategoryService) getBean("kmImeetingResCategoryService");
		}
		return kmImeetingResCategoryService;
	}

	protected IKmImeetingResService getKmImeetingResService() {
		if (kmImeetingResService == null) {
			kmImeetingResService = (IKmImeetingResService) getBean("kmImeetingResService");
		}
		return kmImeetingResService;
	}

	protected IKmImeetingMainService getKmImeetingMainService(
			HttpServletRequest request) {
		if (kmImeetingMainService == null) {
			kmImeetingMainService = (IKmImeetingMainService) getBean("kmImeetingMainService");
		}
		return kmImeetingMainService;
	}

	protected IKmImeetingBookService getKmImeetingBookService(
			HttpServletRequest request) {
		if (kmImeetingBookService == null) {
			kmImeetingBookService = (IKmImeetingBookService) getBean("kmImeetingBookService");
		}
		return kmImeetingBookService;
	}

	protected IKmImeetingMainFeedbackService getKmImeetingMainFeedbackService(
			HttpServletRequest request) {
		if (kmImeetingMainFeedbackService == null) {
			kmImeetingMainFeedbackService = (IKmImeetingMainFeedbackService) getBean("kmImeetingMainFeedbackService");
		}
		return kmImeetingMainFeedbackService;
	}

	protected IKmImeetingEquipmentService
			getKmImeetingEquipmentService(HttpServletRequest request) {
		if (kmImeetingEquipmentService == null) {
			kmImeetingEquipmentService = (IKmImeetingEquipmentService) getBean(
					"kmImeetingEquipmentService");
		}
		return kmImeetingEquipmentService;
	}
	// 资源会议日历数据，以JSON返回
	public ActionForward rescalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 1.包括会议、预约数据
		// 2.返回的资源信息需要包括设备详情，地点楼层，容纳人数(前端需要即时显示)
		// 3.会议、预约需要出计算会议状态(前端会显示成不同颜色)
		// 4.会议数据需要做权限控制
		TimeCounter.logCurrentTime("Action-rescalendar", true, getClass());
		KmssMessages messages = new KmssMessages();
		RequestContext requestCtx = new RequestContext(request);
		try {
			// 性能优化相关1：会议室资源一般变化不大，后期考虑做缓存
			// 性能优化相关2：会议室分类一般变化不大，后期考虑做缓存

			// 资源（会议室）
			Page resourcePage = getKmImeetingResService()
					.findKmImeetingRes(requestCtx);
			// 会议安排
			List<KmImeetingMain> mains = getKmImeetingMainService(request)
					.findKmIMeetingMain(requestCtx, false);
			// 会议预约
			List<KmImeetingBook> books = getKmImeetingBookService(request)
					.findKmImeetingBook(requestCtx, false);
			// 会议预约排序
			meetingSort(books);
			UserOperHelper.setOperSuccess(true);
			// 组装成会议资源需要的JSON
			response.setCharacterEncoding("UTF-8");
			response.getWriter()
					.write(ImeetingCalendarUtil
							.rescalendar(resourcePage, mains, books, request)
							.toString());
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-rescalendar", true, getClass());
		return null;
	}

	// 我的会议日历数据，以JSON返回
	public ActionForward mycalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 1.只包括会议数据
		// 2.列出的会议包括20、30的
		// 3.会议数据需要做权限控制
		TimeCounter.logCurrentTime("Action-mycalendar", true, getClass());
		KmssMessages messages = new KmssMessages();
		RequestContext requestCtx = new RequestContext(request);
		try {
			// 会议安排
			List<KmImeetingMain> mains = getKmImeetingMainService(request)
					.findKmIMeetingMain(requestCtx, true);
			// 会议预约
			List<KmImeetingBook> meetingBooks = getKmImeetingBookService(
					request).findKmImeetingBook(requestCtx, true);
			// 会议预约排序
			meetingSort(meetingBooks);
			UserOperHelper.setOperSuccess(true);
			// 组装成会议资源需要的JSON
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(ImeetingCalendarUtil
					.mycalendar(mains, meetingBooks, request).toString());
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-rescalendar", true, getClass());
		return null;
	}

	// 我要参加的会议
	private List<String> findMyAttendMeeting(HttpServletRequest request)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmImeetingMainFeedback.fdMeeting.fdId");
		hqlInfo.setWhereBlock("kmImeetingMainFeedback.docAttend.fdId=:userId and kmImeetingMainFeedback.fdOperateType='01' ");
		hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
		List<String> ids = new ArrayList<String>();
		ids = getKmImeetingMainFeedbackService(request)
				.findList(hqlInfo);
		return ids;
	}

	public ActionForward equcalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 1.显示被预定和使用中的辅助设备
		// 2.返回的信息包括被哪个会议占用、占用时间段
		// 3.正使用、被预定需要出计算使用状态(前端会显示成不同颜色)
		TimeCounter.logCurrentTime("Action-equcalendar", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			// 辅助设备
			Page equipmentPage = getKmImeetingEqu(request);
			// 组装成设备辅助日历需要的JSON
			JSONObject result = new JSONObject();
			result.put("category", null);
			JSONObject json = new JSONObject();
			json.put("total", equipmentPage.getTotalrows());
			json.put("rowsize", equipmentPage.getRowsize());
			result.put("resource", json);
			List<KmImeetingEquipment> equipments = equipmentPage.getList();
			UserOperHelper.logFindAll(equipments,
					KmImeetingEquipment.class.getName());
			// 获取指定时间段内未召开或进行中的的会议安排
			List<KmImeetingMain> mains = getKmImeetingMainsEqu(request);
			UserOperHelper.logFindAll(mains, KmImeetingMain.class.getName());
			UserOperHelper.setOperSuccess(true);
			result.put("main", genEquCalendarJSON(equipments, mains, request));
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result.toString());
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-equcalendar", true, getClass());
		return null;
	}

	// 获取辅助设备列表
	private Page getKmImeetingEqu(HttpServletRequest request) throws Exception {
		int pageno = 0;
		String s_pageno = request.getParameter("pageno");
		if (s_pageno != null && s_pageno.length() > 0) {
			pageno = Integer.parseInt(s_pageno);
		}
		HQLInfo hql = new HQLInfo();
		hql.setModelName(KmImeetingEquipment.class.getName());
		String whereBlock = " kmImeetingEquipment.fdIsAvailable =:fdIsAvailable";
		hql.setWhereBlock(whereBlock);
		hql.setParameter("fdIsAvailable", Boolean.TRUE);
		hql.setOrderBy(
				"kmImeetingEquipment.fdOrder,kmImeetingEquipment.fdName");
		hql.setFromBlock("KmImeetingEquipment kmImeetingEquipment");
		// 使用权限过滤
		hql.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
		hql.setPageNo(pageno);
		hql.setRowSize(SysConfigParameters.getRowSize());
		Page page = getKmImeetingEquipmentService(request).findPage(hql);
		return page;
	}

	// 获取指定时间段内未召开或进行中的的会议安排
	private List<KmImeetingMain>
			getKmImeetingMainsEqu(HttpServletRequest request) throws Exception {
		HQLInfo hql = new HQLInfo();
		String fdStart = request.getParameter("fdStart");// 开始时间
		Date startDateTime = DateUtil.convertStringToDate(fdStart, ResourceUtil
				.getString("date.format.date"));
		String fdEnd = request.getParameter("fdEnd");// 结束时间
		Date endDateTime = DateUtil.convertStringToDate(fdEnd, ResourceUtil
				.getString("date.format.date"));
		if ((endDateTime.getTime() - startDateTime.getTime())
				/ (24 * 60 * 60 * 1000) > 1) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDateTime);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			endDateTime = cal.getTime();
		}
		hql.setWhereBlock(
				"kmImeetingMain.fdHoldDate<:fdEnd and kmImeetingMain.fdFinishDate>:fdStart and ((kmImeetingMain.fdHoldDate<=:fdDate and kmImeetingMain.fdFinishDate>=:fdDate) or (kmImeetingMain.fdHoldDate>=:beforeFdHoldDate))");
		hql.setParameter("fdStart", startDateTime);
		hql.setParameter("fdEnd", endDateTime);
		Date now = new Date();
		hql.setParameter("fdDate", now);
		hql.setParameter("beforeFdHoldDate", now);
		hql.setOrderBy("kmImeetingMain.fdHoldDate");
		List<KmImeetingMain> list = getKmImeetingMainService(request)
				.findValue(hql);
		return list;
	}

	private JSONObject genEquCalendarJSON(List<KmImeetingEquipment> equipments,
			List<KmImeetingMain> mains, HttpServletRequest request)
			throws Exception {
		JSONObject result = new JSONObject();
		for (KmImeetingEquipment equipment : equipments) {
			JSONObject json = new JSONObject();
			json.put("fdId", equipment.getFdId());
			json.put("name", StringUtil.XMLEscape(equipment.getFdName()));
			json.put("list", new JSONArray());// 使用该设备的会议列表
			result.put(equipment.getFdId(), json);
		}
		Queue queueMeeting = ImeetingCalendarUtil.convertToQueue(mains);
		KmImeetingMain main = (KmImeetingMain) queueMeeting.poll();
		while (main != null) {
			JSONObject meeting = ImeetingCalendarUtil.genMeetingJSON(main,
					request);
			List<KmImeetingEquipment> equList = main.getKmImeetingEquipments();
			if (equList != null && !equList.isEmpty()) {
				for (KmImeetingEquipment equ : equList) {
					String equFdId = equ.getFdId();
					if (result.containsKey(equFdId)) {
						JSONObject json = (JSONObject) result.get(equFdId);
						JSONArray list = (JSONArray) json.get("list");
						list.add(meeting);
					}
				}
			}
			main = (KmImeetingMain) queueMeeting.poll();
		}
		return result;
	}

	public ActionForward mocalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 1、具有【会议管理_阅读所有会议安排】角色时，展现所有的会议安排和会议预约,反之只展现自己可见的
		boolean check = UserUtil.checkRole("ROLE_KMIMEETING_READER");
		if (check) {
			TimeCounter.logCurrentTime("Action-mocalendar", true, getClass());
			KmssMessages messages = new KmssMessages();
			RequestContext requestCtx = new RequestContext(request);
			try {
				// 会议安排
				List<KmImeetingMain> mains = getKmImeetingMainService(request)
						.findKmIMeetingMain(requestCtx, false);
				// 会议预约
				List<KmImeetingBook> meetingBooks = getKmImeetingBookService(
						request).findKmImeetingBook(requestCtx, false);
				// 组装成会议资源需要的JSON
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(ImeetingCalendarUtil
						.mycalendar(mains, meetingBooks, request).toString());
			} catch (Exception e) {
				messages.addError(e);
				e.printStackTrace();
			}
			TimeCounter.logCurrentTime("Action-mocalendar", true, getClass());
			return null;
		} else {
			return mycalendar(mapping, form, request, response);
		}
	}
	
	public ActionForward mobileCalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 1、具有【会议管理_阅读所有会议安排】角色时，展现所有的会议安排和会议预约,反之只展现自己可见的
		boolean check = UserUtil.checkRole("ROLE_KMIMEETING_READER");
		if (check) {
			TimeCounter.logCurrentTime("Action-mocalendar", true, getClass());
			KmssMessages messages = new KmssMessages();
			RequestContext requestCtx = new RequestContext(request);
			try {
				// 会议安排
				List<KmImeetingMain> mains = getKmImeetingMainService(request)
						.findKmIMeetingMain(requestCtx, false);
				// 会议预约
				List<KmImeetingBook> meetingBooks = getKmImeetingBookService(
						request).findKmImeetingBook(requestCtx, false);
				// 组装成会议资源需要的JSON
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(ImeetingCalendarUtil
						.myMobileCalendar(mains, meetingBooks, request)
						.toString());
			} catch (Exception e) {
				messages.addError(e);
				e.printStackTrace();
			}
			TimeCounter.logCurrentTime("Action-mocalendar", true, getClass());
			return null;
		} else {
			return myMocalendar(mapping, form, request, response);
		}
	}

	// 我的会议日历数据，以JSON返回
	public ActionForward myMocalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 1.只包括会议数据
		// 2.列出的会议包括20、30的
		// 3.会议数据需要做权限控制
		TimeCounter.logCurrentTime("Action-mycalendar", true, getClass());
		KmssMessages messages = new KmssMessages();
		RequestContext requestCtx = new RequestContext(request);
		try {
			// 会议安排
			List<KmImeetingMain> mains = getKmImeetingMainService(request)
					.findKmIMeetingMain(requestCtx, true);
			// 会议预约
			List<KmImeetingBook> meetingBooks = getKmImeetingBookService(
					request).findKmImeetingBook(requestCtx, true);
			// 会议预约排序
			meetingSort(meetingBooks);
			UserOperHelper.setOperSuccess(true);
			// 组装成会议资源需要的JSON
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(ImeetingCalendarUtil
					.myMobileCalendar(mains, meetingBooks, request).toString());
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-rescalendar", true, getClass());
		return null;
	}

	public ActionForward listcalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 1.只包括会议数据
		// 2.列出的会议包括20、30的
		// 3.会议数据需要做权限控制
		TimeCounter.logCurrentTime("Action-mycalendar", true, getClass());
		KmssMessages messages = new KmssMessages();
		RequestContext requestCtx = new RequestContext(request);
		try {
			// 会议安排
			List<KmImeetingMain> mains = getKmImeetingMainService(request)
					.findKmIMeetingListMain(requestCtx);
			logger.debug("mainsSize:" + mains.size());
			// 会议预约
			List<KmImeetingBook> meetingBooks = getKmImeetingBookService(
					request).findKmImeetingListBook(requestCtx);
			logger.debug("meetingBooksSize:" + meetingBooks.size());
			// 会议预约排序
			// meetingSort(meetingBooks);
			UserOperHelper.setOperSuccess(true);
			// 组装成会议资源需要的JSON
			response.setCharacterEncoding("UTF-8");
			logger.debug("ImeetingJson:" + ImeetingCalendarUtil
					.myListCalendar(mains, meetingBooks, request).toString());
			response.getWriter().write(ImeetingCalendarUtil
					.myListCalendar(mains, meetingBooks, request).toString());
		} catch (Exception e) {
			messages.addError(e);
			logger.debug("listcalendarMethd-Exception:" + e.getMessage());
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-rescalendar", true, getClass());
		return null;

	}


	/**
	 * 根据召开时间对会议排序
	 * @param list
	 */
	private static void meetingSort(List<KmImeetingBook> list) {
		Collections.sort(list, new Comparator<KmImeetingBook>() {
			@Override
			public int compare(KmImeetingBook book1, KmImeetingBook book2) {
				return (int)(book1.getFdHoldDate().getTime() - book2.getFdHoldDate().getTime());
			}
		});
	}

	public ActionForward getDefCate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getDefCate", true, getClass());
		JSONObject json = new JSONObject();
		json.put("defCateId", ImeetingCateUtil.getDefCates());
		response.getWriter().write(json.toString());// 参与人数
		response.setCharacterEncoding("UTF-8");
		TimeCounter.logCurrentTime("Action-getDefCate", false, getClass());
		return null;
	}

}
