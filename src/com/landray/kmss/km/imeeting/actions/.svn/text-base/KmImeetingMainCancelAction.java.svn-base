package com.landray.kmss.km.imeeting.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainCancelForm;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
 * 会议取消 Action
 */
public class KmImeetingMainCancelAction extends ExtendAction {
	protected IKmImeetingMainService kmImeetingMainService;

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

	// 跳转到催办会议页面
	public ActionForward showCancelMeeting(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-showCancelMeeting", false,
				getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String meetingId = request.getParameter("meetingId");
			String cancelType = request.getParameter("cancelType");
			if (StringUtil.isNotNull(meetingId)) {
				KmImeetingMain kmImeetingMain = (KmImeetingMain) getKmImeetingMainServiceImp(
						request).findByPrimaryKey(meetingId);
				KmImeetingMainCancelForm kmImeetingMainCancelForm = (KmImeetingMainCancelForm) form;
				List<SysOrgElement> notifypersons = ((IKmImeetingMainService) getKmImeetingMainServiceImp(request))
						.getAllAttendPersons(kmImeetingMain);
				String cancelNotifyPersonIds = "";
				String cancelNotifyPersonNames = "";
				for (SysOrgElement notifyperson : notifypersons) {
					cancelNotifyPersonIds += notifyperson.getFdId() + ";";
					cancelNotifyPersonNames += notifyperson.getFdName() + ";";
				}
				if (StringUtil.isNotNull(cancelNotifyPersonIds)) {
					kmImeetingMainCancelForm
							.setCancelNotifyPersonIds(cancelNotifyPersonIds
									.substring(0,
											cancelNotifyPersonIds.length() - 1));
					kmImeetingMainCancelForm
							.setCancelNotifyPersonNames(cancelNotifyPersonNames.substring(
									0, cancelNotifyPersonNames.length() - 1));
				}
				if (StringUtil.isNotNull(cancelType)) {
					kmImeetingMainCancelForm.setFdCancelType(cancelType);
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-showCancelMeeting", false,
				getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
            return getActionForward("showCancelMeeting", mapping, form,
                    request, response);
        }
	}

	// 取消会议
	public ActionForward cancelMeeting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String meetingId = request.getParameter("meetingId");
			if (StringUtil.isNotNull(meetingId)) {
				KmImeetingMain kmImeetingMain = (KmImeetingMain) getKmImeetingMainServiceImp(
						request).findByPrimaryKey(meetingId, null, true);
				KmImeetingMainCancelForm kmImeetingMainCancelForm = (KmImeetingMainCancelForm) form;
				((IKmImeetingMainService) getKmImeetingMainServiceImp(request))
						.updateCancelMeeting(kmImeetingMain,
								kmImeetingMainCancelForm);
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

	// 取消会议
	public ActionForward mobileCancelMeeting(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String flag = "1";
		JSONObject json = new JSONObject();
		try {
			String meetingId = request.getParameter("meetingId");
			if (StringUtil.isNotNull(meetingId)) {
				KmImeetingMain kmImeetingMain = (KmImeetingMain) getKmImeetingMainServiceImp(
						request).findByPrimaryKey(meetingId, null, true);
				KmImeetingMainCancelForm kmImeetingMainCancelForm = (KmImeetingMainCancelForm) form;
				// 移动端取消会议处理 开始
				String fdCancelReason = request.getParameter("fdCancelReason");
				if (StringUtil.isNotNull(fdCancelReason)) {
					kmImeetingMainCancelForm.setCancelReason(fdCancelReason);
				}
				String fdCancelType = request.getParameter("fdCancelType");
				if (StringUtil.isNotNull(fdCancelType)) {
					kmImeetingMainCancelForm.setFdCancelType(fdCancelType);
				}
				kmImeetingMainCancelForm.setFdNotifyType("todo");// 设置取消会议发送通知类型为待阅
				// 移动端取消会议处理 结束
				((IKmImeetingMainService) getKmImeetingMainServiceImp(request))
						.updateCancelMeeting(kmImeetingMain,
								kmImeetingMainCancelForm);
			}
		} catch (Exception e) {
			messages.addError(e);
			flag = "0";
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-generateFile", false, getClass());
		json.put("flag", flag);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

}
