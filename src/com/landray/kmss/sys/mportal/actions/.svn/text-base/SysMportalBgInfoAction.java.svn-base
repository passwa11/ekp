package com.landray.kmss.sys.mportal.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.mportal.util.SysMportalImgUtil;
import com.landray.kmss.sys.ui.forms.SysUiLogoForm;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;

public class SysMportalBgInfoAction  extends BaseAction {
	/**
	 * 显示所有背景图
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			// 显示所有背景
			List<String> paths = SysMportalImgUtil.scanImgPath(SysMportalImgUtil.BG_FILE_NAME);
			request.setAttribute("paths", paths);
			request.setAttribute("bg", SysMportalImgUtil.bgImg());
			request.setAttribute("color", SysMportalImgUtil.bgColor());
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-list", false, getClass());

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return mapping.findForward("failure");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("list");
		}
	}

	/**
	 * 上传logo
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward upload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			SysUiLogoForm xform = (SysUiLogoForm) form;
			SysMportalImgUtil.saveImg(xform.getFile().getInputStream(),
					SysMportalImgUtil.BG_FILE_NAME,
					xform
					.getFile().getFileName());
		} catch (Exception e) {
			return mapping.findForward("failure");
		}
		return mapping.findForward("success");
	}
}
