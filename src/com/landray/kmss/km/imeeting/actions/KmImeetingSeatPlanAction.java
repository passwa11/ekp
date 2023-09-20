package com.landray.kmss.km.imeeting.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.forms.KmImeetingSeatPlanForm;
import com.landray.kmss.km.imeeting.model.KmImeetingAgenda;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingMainFeedback;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainFeedbackService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.km.imeeting.service.IKmImeetingSeatPlanService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class KmImeetingSeatPlanAction extends ExtendAction {

	private IKmImeetingSeatPlanService kmImeetingSeatPlanService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmImeetingSeatPlanService == null) {
			kmImeetingSeatPlanService = (IKmImeetingSeatPlanService) getBean(
					"kmImeetingSeatPlanService");
		}
		return kmImeetingSeatPlanService;
	}

	private IKmImeetingMainService kmImeetingMainService;

	public IKmImeetingMainService getKmImeetingMainService() {
		if (kmImeetingMainService == null) {
			kmImeetingMainService = (IKmImeetingMainService) getBean(
					"kmImeetingMainService");
		}
		return kmImeetingMainService;
	}

	private IKmImeetingMainFeedbackService kmImeetingMainFeedbackService;

	public IKmImeetingMainFeedbackService getKmImeetingMainFeedbackService() {
		if (kmImeetingMainFeedbackService == null) {
			kmImeetingMainFeedbackService = (IKmImeetingMainFeedbackService) getBean(
					"kmImeetingMainFeedbackService");
		}
		return kmImeetingMainFeedbackService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.createNewForm(mapping, form, request, response);
		KmImeetingSeatPlanForm planForm = (KmImeetingSeatPlanForm) form;
		String fdImeetingMainId = request.getParameter("fdImeetingMainId");
		if (StringUtil.isNotNull(fdImeetingMainId)) {
			KmImeetingMain imeetingMain = (KmImeetingMain) getKmImeetingMainService()
					.findByPrimaryKey(fdImeetingMainId);
			if (imeetingMain != null) {
				planForm.setFdImeetingMainId(fdImeetingMainId);
				KmImeetingRes imeetingRes = imeetingMain.getFdPlace();
				if (imeetingRes != null) {
					String templateDetail = imeetingRes.getFdSeatDetail();
					if (StringUtil.isNotNull(templateDetail)
							&& templateDetail.length() > 2) {
						planForm.setFdTemplateSeatDetail(templateDetail);
						planForm.setFdSeatCount(imeetingRes.getFdSeatCount());
						planForm.setFdCols(imeetingRes.getFdCols());
						planForm.setFdRows(imeetingRes.getFdRows());
						planForm.setFdHasTemplateDetail("true");
					}
				}
				List<KmImeetingAgenda> agendas = imeetingMain
						.getKmImeetingAgendas();
				if (agendas != null && agendas.size() > 0) {
					Boolean isTopic = imeetingMain.getFdIsTopic();
					if (isTopic != null) {
						if (isTopic) {
							planForm.setFdIsShowTopic("true");
						}
					}
				}
			}
			planForm.setDocSubject(imeetingMain.getFdName() + "座席安排");
		}
		return planForm;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		KmImeetingSeatPlanForm planForm = (KmImeetingSeatPlanForm) form;
		String fdImeetingMainId = planForm.getFdImeetingMainId();
		KmImeetingMain imeetingMain = (KmImeetingMain) getKmImeetingMainService()
				.findByPrimaryKey(fdImeetingMainId);
		if (imeetingMain != null) {
			Date holdDate = imeetingMain.getFdHoldDate();
			Date now = new Date();
			if (holdDate.getTime() < now.getTime()) {
				request.setAttribute("isBegin", "true");
			}
			KmImeetingRes imeetingRes = imeetingMain.getFdPlace();
			if (imeetingRes != null) {
				planForm.setFdTemplateSeatDetail(
						imeetingRes.getFdSeatDetail());
				planForm.setFdSeatCount(imeetingRes.getFdSeatCount());
			}
			List<KmImeetingAgenda> agendas = imeetingMain
					.getKmImeetingAgendas();
			if (agendas != null && agendas.size() > 0) {
				planForm.setFdTopicSize(agendas.size());
				Boolean isTopic = imeetingMain.getFdIsTopic();
				if (isTopic != null) {
					if (isTopic) {
						planForm.setFdIsShowTopic("true");
					}
				}
			}
			planForm.setDocSubject(imeetingMain.getFdName() + "座席安排");
		}
		planForm.setFdHasTemplateDetail("true");
	}

	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-print", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-print", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("print", mapping, form, request, response);
		}
	}

	public ActionForward getPersonData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject result = new JSONObject();
		try {
			String fdImeetingMainId = request.getParameter("fdImeetingMainId");
			String fdTopicId = request.getParameter("fdTopicId");
			String isFeedback = request.getParameter("isFeedback");
			if (StringUtil.isNotNull(fdImeetingMainId)) {
				KmImeetingMain imeetingMain = (KmImeetingMain) getKmImeetingMainService()
						.findByPrimaryKey(fdImeetingMainId);
				if (imeetingMain != null) {
					// 主持人
					List<KmImeetingMainFeedback> host = getKmImeetingMainFeedbackService()
							.findFeedBackByType(fdImeetingMainId,
									ImeetingConstant.FEEDBACK_TYPE_HOST,
									null, null);
					JSONArray hostArray = addFeedbacks(host, true, isFeedback);
					result.put("host", hostArray);

					// 参加人员
					List<KmImeetingMainFeedback> attends = getKmImeetingMainFeedbackService()
							.findFeedBackByType(fdImeetingMainId,
									ImeetingConstant.FEEDBACK_TYPE_ATTEND,
									null, null);
					JSONArray attendArray = addFeedbacks(attends, true,
							isFeedback);
					result.put("attend", attendArray);

					// 纪要人员
					SysOrgElement summaryPerson = imeetingMain
							.getFdSummaryInputPerson();
					if (summaryPerson != null) {
						JSONArray summaryArray = new JSONArray();
						summaryArray.add(addPerson(summaryPerson, true));
						result.put("summary", summaryArray);
					}

					// 列席人员
					List<KmImeetingMainFeedback> participants = getKmImeetingMainFeedbackService()
							.findFeedBackByType(fdImeetingMainId,
									ImeetingConstant.FEEDBACK_TYPE_PARTICIPANT,
									null, null);
					JSONArray participantArray = addFeedbacks(participants,
							true, isFeedback);
					result.put("participant", participantArray);

					// 议题汇报人
					List<KmImeetingMainFeedback> topicReporters = new ArrayList<>();

					// 议题列席人员
					List<KmImeetingMainFeedback> attendUnitPersons = null;

					// 议题旁听人员
					List<KmImeetingMainFeedback> listenUnitPersons = null;

					// 上会材料汇报人
					List<KmImeetingMainFeedback> reporters = new ArrayList<>();

					if (!imeetingMain.getFdIsTopic()) {
						reporters = getKmImeetingMainFeedbackService()
								.findFeedBackByType(fdImeetingMainId,
										ImeetingConstant.FEEDBACK_TYPE_TOPIC_REPORTER,
										null, null);
					} else {
						if (StringUtil.isNotNull(fdTopicId)) {
							List<KmImeetingAgenda> agendas = imeetingMain
									.getKmImeetingAgendas();
							for (KmImeetingAgenda agenda : agendas) {
								if (agenda.getFdId().equals(fdTopicId)) {
									SysOrgElement element = agenda
											.getDocReporter();
									List<KmImeetingMainFeedback> feedbacks = getKmImeetingMainFeedbackService()
											.findFeedBackByType(
													fdImeetingMainId,
													ImeetingConstant.FEEDBACK_TYPE_TOPIC_REPORTER,
													null, null);
									for (KmImeetingMainFeedback feedback : feedbacks) {
										SysOrgElement creator = feedback
												.getDocCreator();
										String elementId = element.getFdId();
										if (elementId
												.equals(creator.getFdId())) {
											topicReporters.add(feedback);
										}

										SysOrgPerson invitePerson = feedback
												.getFdInvitePerson();
										if (invitePerson != null) {
											if (elementId.equals(
													invitePerson.getFdId())) {
												topicReporters.add(feedback);
											}
										}
									}

									attendUnitPersons = getKmImeetingMainFeedbackService()
											.findFeedBackByType(
													fdImeetingMainId,
													ImeetingConstant.FEEDBACK_TYPE_TOPIC_ATTENDUNITLIAISON,
													fdTopicId, null);

									listenUnitPersons = getKmImeetingMainFeedbackService()
											.findFeedBackByType(
													fdImeetingMainId,
													ImeetingConstant.FEEDBACK_TYPE_TOPIC_LISTENUNITLIAISON,
													fdTopicId, null);

								}
							}
						} else {
							topicReporters = getKmImeetingMainFeedbackService()
									.findFeedBackByType(fdImeetingMainId,
											ImeetingConstant.FEEDBACK_TYPE_TOPIC_REPORTER,
											null, null);

							attendUnitPersons = getKmImeetingMainFeedbackService()
									.findFeedBackByType(
											fdImeetingMainId,
											ImeetingConstant.FEEDBACK_TYPE_TOPIC_ATTENDUNITLIAISON,
											null, null);

							listenUnitPersons = getKmImeetingMainFeedbackService()
									.findFeedBackByType(
											fdImeetingMainId,
											ImeetingConstant.FEEDBACK_TYPE_TOPIC_LISTENUNITLIAISON,
											null, null);
						}
					}

					if (reporters != null && !reporters.isEmpty()) {
						JSONArray reportersArray = addFeedbacks(
								reporters, false, isFeedback);
						result.put("reporters", reportersArray);
					}

					if (topicReporters != null && !topicReporters.isEmpty()) {
						JSONArray reporterArray = addFeedbacks(topicReporters,
								false, isFeedback);
						result.put("topicReporter", reporterArray);
					}
					if (attendUnitPersons != null
							&& !attendUnitPersons.isEmpty()) {
						JSONArray attendUnitArray = addFeedbacks(
								attendUnitPersons, false, isFeedback);
						result.put("attendUnit", attendUnitArray);
					}

					if (listenUnitPersons != null
							&& !listenUnitPersons.isEmpty()) {
						JSONArray listenUnitArray = addFeedbacks(
								listenUnitPersons, false, isFeedback);
						result.put("listenUnit", listenUnitArray);
					}



					// 其它人员
					// List<SysOrgElement> otherPersons = imeetingMain
					// .getFdOtherPersons();
					// if (otherPersons != null && !otherPersons.isEmpty()) {
					// JSONArray otherPersonArray = addPersons(
					// otherPersons, fdImeetingMainId, isFeedback);
					// result.put("other", otherPersonArray);
					// }
				}
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		request.setAttribute("lui-source", result);
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	private JSONArray addFeedbacks(List<KmImeetingMainFeedback> feedbacks,
			boolean isPublic, String isFeedback) {
		JSONArray rtnArray = new JSONArray();
		List<SysOrgElement> elements = new ArrayList<>();
		for (KmImeetingMainFeedback feedback : feedbacks) {
			String fdOperateType = feedback.getFdOperateType();
			SysOrgElement element = feedback.getDocCreator();
			if (elements.contains(element)) {
				continue;
			}
			if (StringUtil.isNotNull(fdOperateType)
					|| "true".equals(isFeedback)) {
				if (ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND
						.equals(fdOperateType)
						|| ImeetingConstant.MEETING_FEEDBACK_OPT_ATTENDOTHER
						.equals(fdOperateType)) {
					JSONObject obj = addPerson(element, isPublic);
					rtnArray.add(obj);
					elements.add(element);
				}
			} else {
				JSONObject obj = addPerson(element, isPublic);
				rtnArray.add(obj);
				elements.add(element);
			}
		}
		return rtnArray;
	}

	private JSONObject addPerson(SysOrgElement element, boolean isPublic) {
		JSONObject obj = new JSONObject();
		obj.put("elementId", element.getFdId());
		obj.put("elementName", element.getFdName());
		obj.put("nodeType", "2");
		if (isPublic) {
			obj.put("isPublic", "true");
		} else {
			obj.put("isPublic", "false");
		}
		return obj;
	}

	public ActionForward getTopicData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONArray rtnArray = new JSONArray();
		try {
			String fdImeetingMainId = request.getParameter("fdImeetingMainId");
			if (StringUtil.isNotNull(fdImeetingMainId)) {
				KmImeetingMain imeetingMain = (KmImeetingMain) getKmImeetingMainService()
						.findByPrimaryKey(fdImeetingMainId);
				if (imeetingMain != null) {
					List<KmImeetingAgenda> agendas = imeetingMain
							.getKmImeetingAgendas();
					for (KmImeetingAgenda agenda : agendas) {
						JSONObject obj = new JSONObject();
						obj.put("topicId", agenda.getFdId());
						obj.put("topicSubject", agenda.getDocSubject());
						rtnArray.add(obj);
					}
				}
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		request.setAttribute("lui-source", rtnArray);
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

}
