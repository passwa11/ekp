package com.landray.kmss.km.imeeting.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainFeedbackForm;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingMainFeedback;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainFeedbackService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
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
 * 会议回执 Action
 */
public class KmImeetingMainFeedbackAction extends ExtendAction {

	protected IKmImeetingMainService kmImeetingMainService;
	protected IKmImeetingMainFeedbackService kmImeetingMainFeedbackService;
	protected ISysOrgCoreService sysOrgCoreService;

	protected IKmImeetingMainService getKmImeetingMainServiceImp(
			HttpServletRequest request) {
		if (kmImeetingMainService == null) {
            kmImeetingMainService = (IKmImeetingMainService) getBean("kmImeetingMainService");
        }
		return kmImeetingMainService;
	}

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmImeetingMainFeedbackService == null) {
            kmImeetingMainFeedbackService = (IKmImeetingMainFeedbackService) getBean("kmImeetingMainFeedbackService");
        }
		return kmImeetingMainFeedbackService;
	}

	public ActionForward listFeedback(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-listFeedback", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String meetingId = request.getParameter("meetingId");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					" kmImeetingMainFeedback.fdMeeting.fdId=:meetingId and (kmImeetingMainFeedback.docCreator.fdId=:userId)");
			hqlInfo.setParameter("meetingId", meetingId);
			hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
			List<KmImeetingMainFeedback> feedbackList = getServiceImp(request).findList(hqlInfo);
			request.setAttribute("queryList", feedbackList);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listFeedback", mapping, form, request, response);
		}
	}

	/**
	 * 获得指定会议的回执列表、统计数据（JSON）
	 */
	public ActionForward getFeedbackList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getFeedbackList", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String meetingId = request.getParameter("meetingId");
			String type = request.getParameter("type");//
			String sort = request.getParameter("sort");// desc asc
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");

			JSONObject result = new JSONObject();
			// 统计相关
			Long attendCount = ((IKmImeetingMainFeedbackService) getServiceImp(request))
					.getAttendCountByMeeting(meetingId);// 参加人数
			Long proxyCount = ((IKmImeetingMainFeedbackService) getServiceImp(request))
					.getCountByMeetingAndType(meetingId, "03");// 代理人数
			Long unAttendCount = ((IKmImeetingMainFeedbackService) getServiceImp(request))
					.getUnAttendCountByMeeting(meetingId);// 不参加人数
			result.put("attendCount", attendCount);
			result.put("unAttendCount", unAttendCount + proxyCount);
			result.put("feedbackCount", attendCount + unAttendCount
					+ proxyCount);// 已回执人数
			result.put("notifyCount",
					((IKmImeetingMainFeedbackService) getServiceImp(request))
							.getFeedbackCountByMeeting(meetingId));
			HQLInfo hqlInfo = new HQLInfo();// 通知人数（即回执单总数）

			if (StringUtil.isNull(type)) {
				type = "creator";
			}
			if (StringUtil.isNull(sort)) {
				sort = "asc";
			}
			setOrder(type, sort, hqlInfo);// 排序

			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);

			hqlInfo.setWhereBlock(" kmImeetingMainFeedback.fdMeeting.fdId=:meetingId");
			hqlInfo.setParameter("meetingId", meetingId);
			Page page = getServiceImp(request).findPage(hqlInfo);
			List<KmImeetingMainFeedback> feedbackList = page.getList();
			JSONArray array = new JSONArray();
			for (KmImeetingMainFeedback feedback : feedbackList) {
				JSONObject json = genFeedbackJSON(feedback);
				array.add(json);
			}
			result.put("type", StringEscapeUtils.escapeHtml(type));
			result.put("sort", StringEscapeUtils.escapeHtml(sort));
			result.put("pageno", page.getPageno());
			result.put("rowsize", page.getRowsize());
			result.put("totalrows", page.getTotalrows());
			result.put("list", array);
			result.put("meetingId", meetingId);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result.toString());
			UserOperHelper.logFindAll(feedbackList,
					getServiceImp(request).getModelName());
			UserOperHelper.setOperSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			UserOperHelper.setOperSuccess(false);
		}
		TimeCounter.logCurrentTime("Action-getFeedbackList", false, getClass());
		return null;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String meetingId = request.getParameter("meetingId");
		String criteriaType = request.getParameter("criteriaType");
		String type = request.getParameter("type");//
		String sort = request.getParameter("sort");// desc asc
		// 会议ID
		if (StringUtil.isNotNull(meetingId)) {
			hqlInfo.setWhereBlock(StringUtil.linkString(
					hqlInfo.getWhereBlock(), " and ",
					"kmImeetingMainFeedback.fdMeeting.fdId=:meetingId"));
			hqlInfo.setParameter("meetingId", meetingId);
		}
		// 回执类型
		if (StringUtil.isNotNull(criteriaType)) {
			if (ImeetingConstant.MEETING_FEEDBACK_OPT_NOT.equals(criteriaType)) {
				hqlInfo.setWhereBlock(StringUtil.linkString(
						hqlInfo.getWhereBlock(), " and ",
						"kmImeetingMainFeedback.fdOperateType is null "));
			} else {
				hqlInfo.setWhereBlock(StringUtil.linkString(
						hqlInfo.getWhereBlock(), " and ",
						"kmImeetingMainFeedback.fdOperateType=:type"));
				hqlInfo.setParameter("type", criteriaType);
			}
		}
		if (StringUtil.isNull(type)) {
			type = "creator";
		}
		if (StringUtil.isNull(sort)) {
			sort = "asc";
		}
		setOrder(type, sort, hqlInfo);// 排序
	}

	private void setOrder(String type, String sort, HQLInfo hqlInfo) {
		String orderBy = "";
		if ("creator".equals(type)) {
			orderBy = "docCreator.fdName ";
		}
		if ("dept".equals(type)) {
			orderBy = "docCreator.hbmParent ";
		}
		if ("opttype".equals(type)) {
			orderBy = " fdOperateType ";
		}
		if ("desc".equals(sort)) {
			orderBy += " desc";
		}
		hqlInfo.setOrderBy(orderBy);
	}

	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return super.view(mapping, form, request, response);
	}

	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String _todo = request.getParameter("_todo");
		
		// 移动端回执待办转向会议view页面
		if ("1".equals(_todo)
				&& (MobileUtil.getClientType(new RequestContext(request)) > -1)) {
			ActionForward forward = new ActionForward();
			forward.setPath("/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId="
					+ request.getParameter("meetingId"));
			return forward;
		} else {
			ActionForward forward = super
					.edit(mapping, form, request, response);
			return forward;
		}
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmImeetingMainFeedbackForm feedbackForm = (KmImeetingMainFeedbackForm) form;
		feedbackForm.setClientType(MobileUtil.getClientType(new RequestContext(
				request)));// 移动端标示
		return super.save(mapping, form, request, response);
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		KmImeetingMainFeedbackForm feedbackForm = (KmImeetingMainFeedbackForm) form;

		feedbackForm.setClientType(MobileUtil.getClientType(new RequestContext(
				request)));// 移动端标示
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	public ActionForward updateFeedback(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		KmImeetingMainFeedbackForm feedbackForm = (KmImeetingMainFeedbackForm) form;
		String flag = "1";
		JSONObject json = new JSONObject();
		feedbackForm.setClientType(MobileUtil.getClientType(new RequestContext(request)));// 移动端标示
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String meetingId = request.getParameter("fdMeetingId");
			if (StringUtil.isNotNull(meetingId)) {// 不存在ID但存在会议ID，用会议ID查询回执单
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(
						"kmImeetingMainFeedback.fdMeeting.fdId=:meetingId and (kmImeetingMainFeedback.docCreator.fdId=:userId)");
				hqlInfo.setParameter("meetingId", meetingId);
				hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
				List<KmImeetingMainFeedback> feedbacks = getServiceImp(request).findList(hqlInfo);
				if (feedbacks != null && !feedbacks.isEmpty()) {
					KmImeetingMainFeedback feedback = feedbacks.get(0);
					UserOperHelper.logFind(feedback);
					feedback.setFdOperateType(feedbackForm.getFdOperateType());
					feedback.setFdReason(feedbackForm.getFdReason());
					feedback.setClientType(MobileUtil.getClientType(new RequestContext(request)));
					getServiceImp(request).update(feedback);
				}
			}
		} catch (Exception e) {
			messages.addError(e);
			flag = "0";
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-generateFile", false, getClass());
		json.put("flag", flag);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		String meetingId = request.getParameter("meetingId");
		// 存在fdId,用id查询回执单
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id,
					null, true);
			if (model != null) {
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
				UserOperHelper.logFind(model);
			}
			if (StringUtil.isNull(meetingId)) {
				meetingId = ((KmImeetingMainFeedback) model).getFdMeeting()
						.getFdId();
			}
			UserOperHelper.logFind(model);
		} else if (StringUtil.isNotNull(meetingId)) {// 不存在ID但存在会议ID，用会议ID查询回执单
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo
					.setWhereBlock(
							"kmImeetingMainFeedback.fdMeeting.fdId=:meetingId and (kmImeetingMainFeedback.docCreator.fdId=:userId) and kmImeetingMainFeedback.fdType != '06' and kmImeetingMainFeedback.fdType != '07'");
			hqlInfo.setParameter("meetingId", meetingId);
			hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
			List<KmImeetingMainFeedback> feedbacks = getServiceImp(request)
					.findList(hqlInfo);
			if (feedbacks != null && !feedbacks.isEmpty()) {
				KmImeetingMainFeedback feedback = feedbacks.get(0);
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, (IBaseModel) feedback,
						new RequestContext(request));
				UserOperHelper.logFind(feedback);
				KmImeetingMain fdMeeting = feedback.getFdMeeting();
				if (fdMeeting.getFdTemplate() != null) {
                    request.setAttribute("fdNeedMultiRes",
                            fdMeeting.getFdTemplate().getFdNeedMultiRes());
                }
				KmImeetingMainFeedbackForm feedBackForm = (KmImeetingMainFeedbackForm) rtnForm;
				feedBackForm.setFdOtherPlace(fdMeeting.getFdOtherPlace());
				feedBackForm.setFdOtherPlaceCoordinate(
						fdMeeting.getFdOtherPlaceCoordinate());
				feedBackForm
						.setFdOtherVicePlace(fdMeeting.getFdOtherVicePlace());
				feedBackForm.setFdOtherVicePlaceCoord(
						fdMeeting.getFdOtherVicePlaceCoord());
			}
		}
		Boolean shouldFeedback = false;
		if (rtnForm != null) {
			shouldFeedback = true;
			request.setAttribute(getFormName(rtnForm, request), rtnForm);
		}
		request.setAttribute("shouldFeedback", shouldFeedback);
		// 获取会议信息
		if (StringUtil.isNotNull(meetingId)) {
			KmImeetingMain kmImeetingMain = (KmImeetingMain) getServiceImp(
					request).findByPrimaryKey(meetingId, KmImeetingMain.class,
					false);
			if (kmImeetingMain != null) {
				IExtendForm kmImeetingMainForm = getServiceImp(request)
						.convertModelToForm(null, kmImeetingMain,
								new RequestContext());
				request.setAttribute("kmImeetingMainForm", kmImeetingMainForm);
			}
		}
	}

	/**
	 * 生成回执JSON
	 */
	private JSONObject genFeedbackJSON(KmImeetingMainFeedback feedback) {
		JSONObject json = new JSONObject();
		String creatorStr = feedback.getDocCreator().getFdName();

		if (ImeetingConstant.MEETING_FEEDBACK_FROMTYPE_INVITE
				.equals(feedback.getFdFromType()) && feedback.getFdInvitePerson() != null) {
			String resource = ResourceUtil.getString("kmImeetingMainFeedback.fdFromType.invite", "km-imeeting");
			resource = resource.replace("%fdInvitePerson%", feedback.getFdInvitePerson().getFdName());
			creatorStr += "(" + resource + ")";
		}
		json.put("creator", creatorStr);
		if (feedback.getDocCreator().getFdParent() != null) {
			json
					.put("dept", feedback.getDocCreator().getFdParent()
							.getFdName());
		}

		json.put("type", feedback.getFdType());
		json.put("fdInvitePerson",
				feedback.getFdInvitePerson() != null ? feedback.getFdInvitePerson().getFdName() : "");
		json.put("opttype", feedback.getFdOperateType());
		if (feedback.getDocAttend() != null) {
			json.put("attend", feedback.getDocAttend().getFdName());
		}
		json.put("fdFromType",
				ImeetingConstant.MEETING_FEEDBACK_FROMTYPE_INVITE.equals(feedback.getFdFromType())
						? ImeetingConstant.MEETING_FEEDBACK_FROMTYPE_INVITE
						: ImeetingConstant.MEETING_FEEDBACK_FROMTYPE_SYSTEM);
		json.put("clientType", feedback.getClientType());
		json.put("reason", feedback.getFdReason());
		json.put("alterTime", DateUtil.convertDateToString(
				feedback.getDocAlterTime(), DateUtil.PATTERN_DATETIME));
		return json;
	}

	/**
	 * 会议回执导出
	 */
	public ActionForward getFeedbackExport(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String meetingId = request.getParameter("meetingId");
			KmImeetingMain kmImeetingMain = (KmImeetingMain) getKmImeetingMainServiceImp(
					request).findByPrimaryKey(meetingId);
			IKmImeetingMainFeedbackService service = (IKmImeetingMainFeedbackService) getServiceImp(
					request);
			service.getFeedBackExport(kmImeetingMain, request, response);
			UserOperHelper.setOperSuccess(true);
		} catch (Exception e) {
			messages.addError(e);
			UserOperHelper.setOperSuccess(false);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return this.getActionForward("failure", mapping, form, request,
					response);
		}
		return null;
	}
	
	public ActionForward checkIsSameTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String fdMeetingId = request.getParameter("fdMeetingId");
		String fdPersonIds = request.getParameter("personIds");
		String result = "";
		try {
			result  = ((IKmImeetingMainFeedbackService) getServiceImp(request))
					.checkIsSameTime(fdMeetingId, fdPersonIds);
		} catch (Exception e) {
			messages.addError(e);
			UserOperHelper.setOperSuccess(false);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return this.getActionForward("failure", mapping, form, request, response);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
}
