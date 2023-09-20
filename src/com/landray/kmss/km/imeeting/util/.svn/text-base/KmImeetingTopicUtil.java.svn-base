package com.landray.kmss.km.imeeting.util;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.forms.KmImeetingAgendaForm;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainFeedbackForm;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainForm;
import com.landray.kmss.km.imeeting.model.KmImeetingMainFeedback;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainFeedbackService;
import com.landray.kmss.sys.lbpmservice.support.service.spring.InternalLbpmProcessForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.unit.model.KmImissiveUnit;
import com.landray.kmss.sys.unit.service.IKmImissiveUnitService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class KmImeetingTopicUtil {
	
	private static IKmImeetingMainFeedbackService feedbackService;
	public static IKmImeetingMainFeedbackService getFeedbackService() {
		if (feedbackService == null) {
			feedbackService = (IKmImeetingMainFeedbackService) SpringBeanUtil
					.getBean("kmImeetingMainFeedbackService");
		}
		return feedbackService;
	}

	/**
	 * 是否可以查看当前议题
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean viewTopicEnable(KmImeetingMainForm kmImeetingMainForm,
			KmImeetingAgendaForm kmImeetingAgendaForm,
			KmImeetingMainFeedbackForm kmImeetingMainFeedbackForm, Boolean isMeetingPage) throws Exception {
		
		String userId = UserUtil.getUser().getFdId();
		
		//被邀请人员或者代理人可以查看对应的议题（邀请人员对应回执的下议题）
		if (kmImeetingMainFeedbackForm != null) {
			
			/*
			 *  最开始的邀请人员的回执ID
			 *  A 邀请 B , B 邀请 C ，fdInviteFeedbackId 为A的回执ID
			 * */
			String fdAttendAgendaId = kmImeetingMainFeedbackForm.getFdAttendAgendaId();
			if (StringUtil.isNotNull(fdAttendAgendaId)
					&&
					fdAttendAgendaId.indexOf(kmImeetingAgendaForm.getFdId()) > -1 ) {
				return true;
			}
		}
		
		if (isMeetingPage) {
			// 议题ID
			List<String> agendaIds = new ArrayList<String>();
			
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("kmImeetingMainFeedback.fdAttendAgendaId");
			// 查询所有邀请我参加会议的邀请人ID
			hqlInfo.setWhereBlock("kmImeetingMainFeedback.fdMeeting.fdId = :fdMeetingId"
					+ " and kmImeetingMainFeedback.docCreator.fdId = :fdCurUserId");
			hqlInfo.setParameter("fdMeetingId", kmImeetingMainForm.getFdId());
			hqlInfo.setParameter("fdCurUserId", userId);
			agendaIds = getFeedbackService().findList(hqlInfo);
			if (!agendaIds.isEmpty()) {
				for (String agendaId :agendaIds) {
					if (agendaId != null
							&&
							agendaId.indexOf(kmImeetingAgendaForm.getFdId()) > -1) {
						return true;
					}
				}
			}
		}
		
		String fdIsHander = kmImeetingMainForm.getSysWfBusinessForm()
				.getFdIsHander();// 是否当前审批人

		// 是否是历史审批人
		InternalLbpmProcessForm processForm = (InternalLbpmProcessForm) kmImeetingMainForm
				.getSysWfBusinessForm()
				.getInternalForm();
		Boolean isHistoryHandler = false;
		if (processForm != null) {
			isHistoryHandler = processForm.getProcessInstanceInfo()
					.isHistoryhandler();
		}

		if (UserUtil.getKMSSUser().isAdmin()
				|| UserUtil.checkRole("ROLE_KMIMEETING_READER")
				|| userId.equals(kmImeetingMainForm.getFdEmceeId())
				|| userId.equals(kmImeetingMainForm.getDocCreatorId())
				|| "true".equals(fdIsHander) || isHistoryHandler) {
			// admin:管理员、会议组织人、会议创建人、流程审批人
			return true;
		} else {
			List<String> attendIds = ArrayUtil
					.convertArrayToList(kmImeetingMainForm
							.getFdAttendPersonIds().split(";"));
			List<String> participantIds = ArrayUtil
					.convertArrayToList(kmImeetingMainForm
							.getFdParticipantPersonIds().split(";"));
			if (!participantIds.isEmpty()) {
				attendIds.addAll(participantIds);
			}
			if (StringUtil.isNotNull(kmImeetingMainForm.getFdHostId())) {
				attendIds.add(kmImeetingMainForm.getFdHostId());
			}
			if (StringUtil.isNotNull(kmImeetingMainForm
					.getFdSummaryInputPersonId())) {
				attendIds.add(kmImeetingMainForm.getFdSummaryInputPersonId());
			}
			// attend:主持人、会议参与人、会议列席人、纪要录入人
			if (!attendIds.isEmpty() && UserUtil.checkUserIds(attendIds)) {
				return true;
			} else {

				List<String> ccIds = ArrayUtil
						.convertArrayToList(kmImeetingMainForm
								.getFdCopyToPersonIds().split(";"));
				List<String> readerIds = ArrayUtil
						.convertArrayToList(kmImeetingMainForm
								.getAuthReaderIds().split(";"));
				if (!readerIds.isEmpty()) {
					ccIds.addAll(readerIds);
				}
				// cc:抄送人、可阅读者
				if (!ccIds.isEmpty() && UserUtil.checkUserIds(ccIds)) {
					return true;
				} else {
					// 议题汇报人
					String docReporterId = kmImeetingAgendaForm
							.getDocReporterId();
					if (StringUtil.isNotNull(docReporterId)) {
						if (userId.equals(docReporterId)) {
							return true;
						}
					}

					// 议题材料负责人
					String docResponsId = kmImeetingAgendaForm
							.getDocResponsId();
					if (StringUtil.isNotNull(docResponsId)) {
						if (userId.equals(docResponsId)) {
							return true;
						}
					}

					// 议题列席人员
					String fdAttendUnitIds = kmImeetingAgendaForm
							.getFdAttendUnitIds();
					IKmImissiveUnitService kmImissiveUnitService = (IKmImissiveUnitService) SpringBeanUtil
							.getBean("kmImissiveUnitService");
					IKmImeetingMainFeedbackService kmImeetingMainFeedbackService = (IKmImeetingMainFeedbackService) SpringBeanUtil
							.getBean("kmImeetingMainFeedbackService");
					if (StringUtil.isNotNull(fdAttendUnitIds)) {
						String[] attendUnitIds = fdAttendUnitIds.split(";");
						List<KmImissiveUnit> list = kmImissiveUnitService
								.findByPrimaryKeys(attendUnitIds);
						if (list != null && !list.isEmpty()) {
							for (KmImissiveUnit kmImissiveUnit : list) {
								List<SysOrgElement> fdMeetingLiaison = kmImissiveUnit
										.getFdMeetingLiaison();
								if(fdMeetingLiaison != null && !fdMeetingLiaison.isEmpty()){
									for (SysOrgElement sysOrgElement : fdMeetingLiaison) {
										if (sysOrgElement.getFdId()
												.equals(userId)) {
											return true;
										}
									}
								}
							}
						}

						List<KmImeetingMainFeedback> feedbackList = kmImeetingMainFeedbackService
								.findFeedBackByType(
								kmImeetingMainForm.getFdId(),
								ImeetingConstant.FEEDBACK_TYPE_TOPIC_ATTENDUNITLIAISON,
										kmImeetingAgendaForm.getFdId(), null);
						if (feedbackList != null && !feedbackList.isEmpty()) {
							for (KmImeetingMainFeedback feedback : feedbackList) {
								if (feedback.getDocCreator().getFdId()
										.equals(userId)) {
									return true;
								}
							}
						}
					}

					// 议题旁听人员
					String fdListenUnitIds = kmImeetingAgendaForm
							.getFdListenUnitIds();
					if (StringUtil.isNotNull(fdListenUnitIds)) {
						String[] listenUnitIds = fdListenUnitIds.split(";");
						List<KmImissiveUnit> list = kmImissiveUnitService
								.findByPrimaryKeys(listenUnitIds);
						if (list != null && !list.isEmpty()) {
							for (KmImissiveUnit kmImissiveUnit : list) {
								List<SysOrgElement> fdMeetingLiaison = kmImissiveUnit
										.getFdMeetingLiaison();
								if (fdMeetingLiaison != null
										&& !fdMeetingLiaison.isEmpty()) {
									for (SysOrgElement sysOrgElement : fdMeetingLiaison) {
										if (sysOrgElement.getFdId()
												.equals(userId)) {
											return true;
										}
									}
								}
							}
						}

						List<KmImeetingMainFeedback> feedbackList = kmImeetingMainFeedbackService
								.findFeedBackByType(
										kmImeetingMainForm.getFdId(),
										ImeetingConstant.FEEDBACK_TYPE_TOPIC_LISTENUNITLIAISON,
										kmImeetingAgendaForm.getFdId(), null);
						if (feedbackList != null && !feedbackList.isEmpty()) {
							for (KmImeetingMainFeedback feedback : feedbackList) {
								if (feedback.getDocCreator().getFdId()
										.equals(userId)) {
									return true;
								}
							}
						}
					}
				}
			}

		}
		return false;
	}
}
