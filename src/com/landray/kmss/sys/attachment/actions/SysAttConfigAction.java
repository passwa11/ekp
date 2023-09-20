package com.landray.kmss.sys.attachment.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attachment.integrate.wps.interfaces.ISysAttachmentWpsAddinProvider;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class SysAttConfigAction extends ExtendAction{
	
	private ISysAttachmentWpsAddinProvider sysAttachmentWpsAddinProvider;
	
    private ISysAttachmentWpsAddinProvider getSysAttachmentWpsAddinProvider() {
    	if(sysAttachmentWpsAddinProvider == null) {
    		sysAttachmentWpsAddinProvider = (ISysAttachmentWpsAddinProvider) getBean("sysAttachmentWpsAddinProviderImp");
    	}
    	
    	return sysAttachmentWpsAddinProvider;
    }
    
	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return null;
	}
	
	
	 /**
	    * 配置加载项Token
	    * @param mapping
	    * @param form
	    * @param request
	    * @param response
	    * @return
	    * @throws Exception
	    */
	public ActionForward handleWpsOfficeToken(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String cookieAdmin = request.getParameter("cookieAdmain");
		String thirdWpsWebOfficeEnabled = request.getParameter("thirdWpsWebOfficeEnabled");
		try {
			if(StringUtil.isNotNull(thirdWpsWebOfficeEnabled) && 
					"true".equalsIgnoreCase(thirdWpsWebOfficeEnabled)) {
				getSysAttachmentWpsAddinProvider().addinAuthentication(cookieAdmin);
			} else {
				getSysAttachmentWpsAddinProvider().deleteToken();
			}
			
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("success");
		}
	}

}
