package com.landray.kmss.fssc.fee.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.fssc.fee.service.IFsscFeeMainService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

/**
 * 事前申请porlet：获取当前登录人事前列表，porlet展现
* @author xiexingxing
* @date 2020年3月18日
 */
public class FsscFeeMainPortletAction extends ExtendAction {
	protected IFsscFeeMainService fsscFeeMainService;

	@Override
    protected IFsscFeeMainService getServiceImp(HttpServletRequest request) {
		if (fsscFeeMainService == null) {
            fsscFeeMainService = (IFsscFeeMainService) getBean("fsscFeeMainService");
        }
		return fsscFeeMainService;
	}

	public ActionForward listPortlet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listPortlet", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			Page page = getServiceImp(request).listPortlet(request);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-listPortlet", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			// 视图展现方式:listtable(列表)
			return getActionForward("listPortlet", mapping, form, request,response);
		}
	}
}
