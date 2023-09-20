package com.landray.kmss.km.imeeting.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainHastenForm;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainFeedbackService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 会议催办 Action
 */
public class KmImeetingMainHastenAction extends ExtendAction {
	protected IKmImeetingMainService kmImeetingMainService;
	protected IKmImeetingMainFeedbackService kmImeetingMainFeedbackService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return null;
	}

	protected IKmImeetingMainService getKmImeetingMainServiceImp(
			HttpServletRequest request) {
		if (kmImeetingMainService == null) {
			kmImeetingMainService = (IKmImeetingMainService) getBean("kmImeetingMainService");
		}
		return kmImeetingMainService;
	}

	public IKmImeetingMainFeedbackService getKmImeetingMainFeedbackService() {
		if (kmImeetingMainFeedbackService == null) {
			kmImeetingMainFeedbackService = (IKmImeetingMainFeedbackService) getBean("kmImeetingMainFeedbackService");
		}
		return kmImeetingMainFeedbackService;
	}

	// 跳转到催办会议页面
	public ActionForward showHastenMeeting(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-showHastenMeeting", false,
				getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String meetingId = request.getParameter("meetingId");
			if (StringUtil.isNotNull(meetingId)) {
				KmImeetingMain kmImeetingMain = (KmImeetingMain) getKmImeetingMainServiceImp(
						request).findByPrimaryKey(meetingId);
				KmImeetingMainHastenForm kmImeetingMainHastenForm = (KmImeetingMainHastenForm) form;
				List<SysOrgElement> notifypersons = ((IKmImeetingMainFeedbackService) getKmImeetingMainFeedbackService())
						.getPersonsByOptType(kmImeetingMain.getFdId(), "01;04");// 01:参加的人
																				// 04:未回执的人
				String hastenNotifyPersonIds = "";
				String hastenNotifyPersonNames = "";
				for (SysOrgElement notifyperson : notifypersons) {
					if (hastenNotifyPersonIds.indexOf(notifyperson.getFdId()) == -1) {
						hastenNotifyPersonIds += notifyperson.getFdId() + ";";
						hastenNotifyPersonNames += notifyperson.getFdName() + ";";
					}
				}
				if (StringUtil.isNotNull(hastenNotifyPersonIds)) {
					kmImeetingMainHastenForm
							.setHastenNotifyPersonIds(hastenNotifyPersonIds
									.substring(0,
											hastenNotifyPersonIds.length() - 1));
					kmImeetingMainHastenForm
							.setHastenNotifyPersonNames(hastenNotifyPersonNames.substring(
									0, hastenNotifyPersonNames.length() - 1));
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-showHastenMeeting", false,
				getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
            return getActionForward("showHastenMeeting", mapping, form,
                    request, response);
        }
	}

	// 催办会议
	public ActionForward hastenMeeting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String meetingId = request.getParameter("meetingId");
			if (StringUtil.isNotNull(meetingId)) {
				KmImeetingMain kmImeetingMain = (KmImeetingMain) getKmImeetingMainServiceImp(
						request).findByPrimaryKey(meetingId);
				KmImeetingMainHastenForm kmImeetingMainHastenForm = (KmImeetingMainHastenForm) form;
				((IKmImeetingMainService) getKmImeetingMainServiceImp(request))
						.saveHastenMeeting(kmImeetingMain,
								kmImeetingMainHastenForm);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		return getActionForward("success", mapping, form, request, response);
	}

}
