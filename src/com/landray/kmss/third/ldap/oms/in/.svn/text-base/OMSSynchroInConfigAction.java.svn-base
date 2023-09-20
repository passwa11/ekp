package com.landray.kmss.third.ldap.oms.in;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.sys.appconfig.actions.SysAppConfigAction;
import com.landray.kmss.sys.appconfig.forms.SysAppConfigForm;
import com.landray.kmss.sys.oms.OMSPlugin;
import com.landray.kmss.third.ldap.LdapDetailConfig;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class OMSSynchroInConfigAction extends SysAppConfigAction{
	
	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysAppConfigForm appConfigForm = (SysAppConfigForm) form;
		String omsInEnable = (String)appConfigForm.getValue("kmss.oms.in.ldap.enabled");
		if("true".equals(omsInEnable)){
			String strategy = new LdapDetailConfig()
					.getValue("kmss.ldap.type.common.prop.syncStrategy");
			if ("increment".equals(strategy)) {
				strategy = "ldapIncrement";
			} else {
				strategy = "ldap";
			}
			String name = OMSPlugin.getOtherOmsInEnabledKey(strategy);
			if(name!=null){
				KmssMessages messages = new KmssMessages();
				messages.addError(new KmssMessage("sys-oms:oms.in.hasEnabled",name,name));
				KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
				return mapping.findForward("failure");
			}
		}
		
		return super.update(mapping, appConfigForm, request, response);
	}

}
