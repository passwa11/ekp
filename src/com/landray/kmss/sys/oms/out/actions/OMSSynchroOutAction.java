package com.landray.kmss.sys.oms.out.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.sys.oms.out.IOMSSynchroOutService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;

/**
 * OMS接出同步操作
 * 
 * @author 刘声斌
 * 
 */
public class OMSSynchroOutAction extends BaseAction {
	/**
	 * OMS接出第一次同步所有组织架构操作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward run(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String typeKey = request.getParameter("type");
			if (StringUtil.isNull(typeKey)) {
                throw new RuntimeException("type 参数不能为空！");
            }
			((IOMSSynchroOutService) getBean("synchroOutService"))
					.allSynchro(typeKey);

		} catch (Exception e) {
			messages.addError(new KmssMessage("common.io.exception", e
					.toString()), e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
            return mapping.findForward("failure");
        } else {
			return mapping.findForward("success");
		}
	}
}
