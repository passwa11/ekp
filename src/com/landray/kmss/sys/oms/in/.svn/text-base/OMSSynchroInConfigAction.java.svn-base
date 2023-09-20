package com.landray.kmss.sys.oms.in;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.sys.appconfig.actions.SysAppConfigAction;
import com.landray.kmss.sys.appconfig.forms.SysAppConfigForm;
import com.landray.kmss.sys.oms.OMSPlugin;
import com.landray.kmss.sys.oms.in.interfaces.OMSSynchroInProvider;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;

public class OMSSynchroInConfigAction extends SysAppConfigAction{
	
	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		for (Iterator<OMSSynchroInProvider> iter = OMSPlugin
				.getInExtensionMap().values().iterator(); iter.hasNext();) {
			try {
				OMSSynchroInProvider provider = iter.next();
				if(!provider.isSynchroInEnable()){
					continue;
				}
			}catch(Exception e){
				
			}
		}
		
		KmssMessages messages = new KmssMessages();
		try {
			String modelName = request.getParameter("modelName");
			String autoclose = request.getParameter("autoclose");
			if (StringUtil.isNull(modelName)) {
                throw new NoRecordException();
            }
			if (StringUtil.isNotNull(autoclose) && "false".equals(autoclose)) {
				request.setAttribute("SUCCESS_PAGE_AUTO_CLOSE", "false");
			}
			SysAppConfigForm appConfigForm = (SysAppConfigForm) form;
			getSysAppConfigService().add(modelName, appConfigForm.getMap());
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
