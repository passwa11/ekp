package com.landray.kmss.km.imeeting.service.spring;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.model.KmImeetingAgenda;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingMainFeedback;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainFeedbackService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class KmImeetingAliMeetingValidator implements IAuthenticationValidator {

	private IKmImeetingMainService kmImeetingMainService;
	public IKmImeetingMainService getKmImeetingMainService() {
		if (kmImeetingMainService == null) {
			kmImeetingMainService = (IKmImeetingMainService) SpringBeanUtil
					.getBean("kmImeetingMainService");
		}
		return kmImeetingMainService;
	}
	
	private IKmImeetingMainFeedbackService kmImeetingMainFeedbackService;
	public IKmImeetingMainFeedbackService getKmImeetingMainFeedbackService() {
		if (kmImeetingMainFeedbackService == null) {
			kmImeetingMainFeedbackService = (IKmImeetingMainFeedbackService) SpringBeanUtil
					.getBean("kmImeetingMainFeedbackService");
		}
		return kmImeetingMainFeedbackService;
	}
	
	private ISysOrgCoreService sysOrgCoreService;
	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
					.getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		boolean check = false;
		try {
			String fdMeetingId = validatorContext.getParameter("fdMeetingId");
			if (StringUtil.isNotNull(fdMeetingId)) {
				Long fdCurTime = System.currentTimeMillis();
				KmImeetingMain mainModel = (KmImeetingMain) getKmImeetingMainService()
						.findByPrimaryKey(fdMeetingId);
				Long fdHoldTime = mainModel.getFdHoldDate().getTime();
				Long fdEndTime = mainModel.getFdFinishDate().getTime();
				String meetingStatus = mainModel.getDocStatus();
				if (fdCurTime < fdHoldTime || fdCurTime > fdEndTime || "41".equals(meetingStatus)) {
					return false;
				}
				String fdCurUserId = UserUtil.getKMSSUser().getUserId();
				List<String> personIds = getAliyunPersonIds(mainModel);
				if (personIds != null && !personIds.isEmpty()) {
					check = personIds.contains(fdCurUserId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return check;
	}

	@SuppressWarnings("unchecked")
	private List<String> getAliyunPersonIds(KmImeetingMain kmImeetingMain) throws Exception {
		
		List<SysOrgElement> meetingPersons = new ArrayList<SysOrgElement>();
		
		if (kmImeetingMain.getFdNeedFeedback()) {
			List<KmImeetingMainFeedback> allF = new ArrayList<>();
			// 与会人员
			List<KmImeetingMainFeedback> l1 = getKmImeetingMainFeedbackService().findFeedBackByType(kmImeetingMain.getFdId(),
					ImeetingConstant.FEEDBACK_TYPE_ATTEND, "", ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
			// 列席人员
			List<KmImeetingMainFeedback> l2 = getKmImeetingMainFeedbackService().findFeedBackByType(kmImeetingMain.getFdId(),
					ImeetingConstant.FEEDBACK_TYPE_PARTICIPANT, "", ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
			// 邀请人员
			List<KmImeetingMainFeedback> l3 = getKmImeetingMainFeedbackService().findFeedBackByType(kmImeetingMain.getFdId(),
					ImeetingConstant.FEEDBACK_TYPE_INVITE, "", ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
			if (!l1.isEmpty()) {
				ArrayUtil.concatTwoList(l1, allF);
			}
			if (!l2.isEmpty()) {
				ArrayUtil.concatTwoList(l2, allF);
			}
			if (!l3.isEmpty()) {
				ArrayUtil.concatTwoList(l3, allF);
			}
			if ("true".equals(KmImeetingConfigUtil.isTopicMng())) {
				List<KmImeetingMainFeedback> l4 = getKmImeetingMainFeedbackService().findFeedBackByType(
						kmImeetingMain.getFdId(), ImeetingConstant.FEEDBACK_TYPE_TOPIC_ATTENDUNITLIAISON, "",
						ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
				if (!l4.isEmpty()) {
					ArrayUtil.concatTwoList(l4, allF);
				}
				List<KmImeetingMainFeedback> l5 = getKmImeetingMainFeedbackService().findFeedBackByType(
						kmImeetingMain.getFdId(), ImeetingConstant.FEEDBACK_TYPE_TOPIC_LISTENUNITLIAISON, "",
						ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);

				if (!l5.isEmpty()) {
					ArrayUtil.concatTwoList(l5, allF);
				}
			}
			List<KmImeetingMainFeedback> l6 = getKmImeetingMainFeedbackService().findFeedBackByType(kmImeetingMain.getFdId(),
					ImeetingConstant.FEEDBACK_TYPE_TOPIC_REPORTER, "", ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
			if (!l6.isEmpty()) {
				ArrayUtil.concatTwoList(l6, allF);
			}
			if (!allF.isEmpty()) {
				for (KmImeetingMainFeedback f : allF) {
					if (f.getDocAlterTime().getTime() <= kmImeetingMain.getFdFeedBackDeadline().getTime()) {
						SysOrgPerson attendPerson = f.getDocAttend();
						meetingPersons.add(attendPerson);
					}
				}
			}
		} else {
			
			// 参会人
			List<SysOrgElement> fdAttendPersons = kmImeetingMain.getFdAttendPersons();
			if (fdAttendPersons != null && !fdAttendPersons.isEmpty()) {
				ArrayUtil.concatTwoList(fdAttendPersons, meetingPersons);
			}

			// 列席
			List<SysOrgElement> fdParticipantPersons = kmImeetingMain.getFdParticipantPersons();
			if (fdParticipantPersons != null && !fdParticipantPersons.isEmpty()) {
				ArrayUtil.concatTwoList(fdParticipantPersons, meetingPersons);
			}

			List<SysOrgElement> fdOtherPersons = kmImeetingMain.getFdOtherPersons();
			if (fdOtherPersons != null && !fdOtherPersons.isEmpty()) {
				ArrayUtil.concatTwoList(fdOtherPersons, meetingPersons);
			}
			// 汇报人
			List<KmImeetingAgenda> agendaList = kmImeetingMain.getKmImeetingAgendas();
			if (agendaList != null && !agendaList.isEmpty()) {
				for (int i = 0; i < agendaList.size(); i++) {
					KmImeetingAgenda kmImeetingAgenda = (KmImeetingAgenda) agendaList.get(i);
					if (kmImeetingAgenda.getDocReporter() != null 
							&& !meetingPersons.contains(kmImeetingAgenda.getDocReporter())) {
						meetingPersons.add(kmImeetingAgenda.getDocReporter());
					}
				}
			}
		}
		
		// 主持人、组织人、纪要人、汇报人、协助人、汇报人
		// 主持人
		if (kmImeetingMain.getFdHost() != null
				&& !meetingPersons.contains(kmImeetingMain.getFdHost())) {
			meetingPersons.add(kmImeetingMain.getFdHost());
		}
		// 组织人
		if (kmImeetingMain.getFdEmcee() != null
				&& !meetingPersons.contains(kmImeetingMain.getFdEmcee())) {
			meetingPersons.add(kmImeetingMain.getFdEmcee());
		}
		// 纪要人
		if (kmImeetingMain.getFdSummaryInputPerson() != null
				&& !meetingPersons.contains(kmImeetingMain.getFdSummaryInputPerson())) {
			meetingPersons.add(kmImeetingMain.getFdSummaryInputPerson());
		}
		// 会议协助人员
		List<SysOrgElement> fdAssistPersons = kmImeetingMain.getFdAssistPersons();
		if (fdAssistPersons != null && !fdAssistPersons.isEmpty()) {
			ArrayUtil.concatTwoList(fdAssistPersons, meetingPersons);
		}
		
		return getSysOrgCoreService().expandToPersonIds(meetingPersons);
	}
	
}
