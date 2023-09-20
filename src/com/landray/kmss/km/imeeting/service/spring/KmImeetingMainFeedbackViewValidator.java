package com.landray.kmss.km.imeeting.service.spring;

import java.util.List;

import org.apache.commons.lang3.BooleanUtils;

import com.landray.kmss.km.imeeting.model.KmImeetingAgenda;
import com.landray.kmss.km.imeeting.model.KmImeetingConfig;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingMainFeedback;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainFeedbackService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class KmImeetingMainFeedbackViewValidator
		implements IAuthenticationValidator {

	private IKmImeetingMainService kmImeetingMainService;

	public void setKmImeetingMainService(
			IKmImeetingMainService kmImeetingMainService) {
		this.kmImeetingMainService = kmImeetingMainService;
	}

	private IKmImeetingMainFeedbackService kmImeetingMainFeedbackService;

	public void setKmImeetingMainFeedbackService(
			IKmImeetingMainFeedbackService kmImeetingMainFeedbackService) {
		this.kmImeetingMainFeedbackService = kmImeetingMainFeedbackService;
	}

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		String meetingId = validatorContext.getParameter("meetingId");
		KmImeetingMain mainModel = (KmImeetingMain) kmImeetingMainService
				.findByPrimaryKey(meetingId);
		if (mainModel != null) {
			String userId = UserUtil.getUser().getFdId();
			// 可阅读者
			List<SysOrgElement> authReaders = mainModel.getAuthAllReaders();
			if (authReaders != null && !authReaders.isEmpty()) {
				for (SysOrgElement sysOrgElement : authReaders) {
					if (userId.equals(sysOrgElement.getFdId())) {
						return true;
					}
				}
			}
			KmImeetingConfig config = new KmImeetingConfig();
			String feedbackViewPerson = config.getFeedbackViewPerson();
			if (StringUtil.isNotNull(feedbackViewPerson)
					&& BooleanUtils.isTrue(mainModel.getIsNotify())) {
				String[] viewPersons = feedbackViewPerson.split(";");
				for (int i = 0; i < viewPersons.length; i++) {
					switch (viewPersons[i]) {
					case "1":// 会议发起人
						if (userId
								.equals(mainModel.getDocCreator().getFdId())) {
							return true;
						}
						break;
					case "2":// 会议组织人
						SysOrgElement fdEmcee = mainModel.getFdEmcee();
						if (fdEmcee != null) {
							if (userId.equals(fdEmcee.getFdId())) {
								return true;
							}
						}
						break;
					case "3":// 会议主持人
						SysOrgElement fdHost = mainModel.getFdHost();
						if (fdHost != null) {
							if (userId.equals(fdHost.getFdId())) {
								return true;
							}
						}
						break;
					case "4":// 参加人员
						KmImeetingMainFeedback feedback = kmImeetingMainFeedbackService
								.findFeedBackByElement(meetingId, userId);
						if (feedback != null) {
							return true;
						}
						break;
					case "5":// 会议抄送人
						List<SysOrgElement> fdCopyToPersons = mainModel
								.getFdCopyToPersons();
						if (fdCopyToPersons != null
								&& !fdCopyToPersons.isEmpty()) {
							for (SysOrgElement sysOrgElement : fdCopyToPersons) {
								if (userId.equals(sysOrgElement.getFdId())) {
									return true;
								}
							}
						}
						break;
					case "6":// 会议纪要人
						SysOrgElement fdSummaryInputPerson = mainModel
								.getFdSummaryInputPerson();
						if (fdSummaryInputPerson != null) {
							if (userId.equals(fdSummaryInputPerson.getFdId())) {
								return true;
							}
						}
						break;
					case "7":// 议题 / 议程 材料负责人
						List<KmImeetingAgenda> kmImeetingAgendas = mainModel
								.getKmImeetingAgendas();
						if (kmImeetingAgendas != null
								&& !kmImeetingAgendas.isEmpty()) {
							for (KmImeetingAgenda kmImeetingAgenda : kmImeetingAgendas) {
								SysOrgElement docRespons = kmImeetingAgenda
										.getDocRespons();
								if (docRespons != null) {
									if (userId.equals(docRespons.getFdId())) {
										return true;
									}
								}
							}
						}
						break;
					default:
						break;
					}
				}
			}
		}
		return false;
	}

}
