package com.landray.kmss.sys.restservice.server.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerInitService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;

/**
 * RestService初始化加载 Action
 * 
 * @author  
 */
public class SysRestserviceServerInitAction extends ExtendAction {
	protected ISysRestserviceServerInitService sysRestserviceServerInitService;

	@Override
	protected ISysRestserviceServerInitService getServiceImp(HttpServletRequest request) {
		if (sysRestserviceServerInitService == null) {
			sysRestserviceServerInitService = (ISysRestserviceServerInitService) getBean("sysRestserviceServerInitService");
		}
		return sysRestserviceServerInitService;
	}

	/**
	 * 系统初始化导入
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = getServiceImp(request).initializeData();
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("success");
		}
	}
}
