package com.landray.kmss.km.forum.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.km.forum.forms.KmForumConfigForm;
import com.landray.kmss.km.forum.model.KmForumConfig;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 创建日期 2006-Aug-31
 * 
 * @author 吴兵
 */
public class KmForumConfigAction extends BaseAction {

	/**
	 * 论坛设置页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回view页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			KmForumConfigForm configForm = (KmForumConfigForm) form;
			KmForumConfig forumConfig = new KmForumConfig();
			configForm.setAnonymous(forumConfig.getAnonymous());
			configForm.setCanModifyRight(forumConfig.getCanModifyRight());
			configForm.setCanModifyNickname(forumConfig.getCanModifyNickname());
			configForm.setLevel(forumConfig.getLevel().replaceAll(";", "\n"));
			configForm.setHotReplyCount(Integer.parseInt(forumConfig.getHotReplyCount()));
			configForm.setReplyTimeInterval(forumConfig.getReplyTimeInterval());
			configForm.setIsWordCheck(forumConfig.getIsWordCheck());
			configForm.setWords(forumConfig.getWords());
			configForm.setWebServiceDefForumId(forumConfig
					.getWebServiceDefForumId());
			configForm.setWebServiceDefForumName(forumConfig
					.getWebServiceDefForumName());
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("edit");
		}
	}

	/**
	 * 保存论坛系统设置且转向论坛设置页面。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则返回manage页面
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			KmForumConfigForm configForm = (KmForumConfigForm) form;
			KmForumConfig forumConfig = new KmForumConfig();
			forumConfig.setAnonymous(configForm.getAnonymous());
			forumConfig.setCanModifyRight(configForm.getCanModifyRight());
			forumConfig.setCanModifyNickname(configForm.getCanModifyNickname());
			forumConfig.setLevel(configForm.getLevel().replaceAll("\r\n", ";"));
			forumConfig.setHotReplyCoun(String.valueOf(configForm.getHotReplyCount()));
			forumConfig.setReplyTimeInterval(configForm.getReplyTimeInterval());
			forumConfig.setIsWordCheck(configForm.getIsWordCheck());
			forumConfig.setWords(configForm.getWords());
			forumConfig.setWebServiceDefForumId(configForm
					.getWebServiceDefForumId());
			forumConfig.setWebServiceDefForumName(configForm
					.getWebServiceDefForumName());

			forumConfig.save();
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return mapping.findForward("edit");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("success");
		}
	}

	/**
	 * 显示论坛等级信息<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回viewLevel页面
	 * @throws Exception
	 */
	public ActionForward viewLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("viewLevel");
	}
}
