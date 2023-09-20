package com.landray.kmss.sys.material.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.material.service.ISysMaterialMainService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysMaterialMainAction extends ExtendAction {
	private ISysMaterialMainService sysMaterialMainService;

	@Override
	protected ISysMaterialMainService getServiceImp(HttpServletRequest request) {
		if (null == sysMaterialMainService) {
			sysMaterialMainService = (ISysMaterialMainService) getBean("sysMaterialMainService");
		}
		return sysMaterialMainService;
	}

	public ActionForward upload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-upload", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			// 多个附件：每个附件对应一个主model进行存储
			getServiceImp(request).saveData((IExtendForm) form);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-upload", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("upload", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String fdModelName = request.getParameter("fdModelName");
		String fdType = request.getParameter("fdType");
		if (StringUtil.isNull(fdModelName)) {
			throw new Exception("params cannot be null!");
		}
		String whereBlock = "sysMaterialMain.fdModelName=:fdModelName";
		if (!StringUtil.isNull(fdType)) {
			whereBlock += " and sysMaterialMain.fdType=:fdType";
			hqlInfo.setParameter("fdType", fdType);
		}
		hqlInfo.setParameter("fdModelName", fdModelName);
		hqlInfo.setWhereBlock(whereBlock);
	}

}
