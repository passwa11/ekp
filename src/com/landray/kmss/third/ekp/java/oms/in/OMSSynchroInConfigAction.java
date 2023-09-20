package com.landray.kmss.third.ekp.java.oms.in;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.sys.appconfig.actions.SysAppConfigAction;
import com.landray.kmss.sys.appconfig.forms.SysAppConfigForm;
import com.landray.kmss.sys.cluster.interfaces.MessageCenter;
import com.landray.kmss.sys.oms.OMSPlugin;
import com.landray.kmss.third.ekp.java.EkpJavaUtil;
import com.landray.kmss.third.ekp.java.cluster.EkpJavaConfigMessage;
import com.landray.kmss.third.ekp.java.notify.EkpClientTodoThread;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.MD5Util;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class OMSSynchroInConfigAction extends SysAppConfigAction{
	
	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysAppConfigForm appConfigForm = (SysAppConfigForm) form;
		String omsInEnable = (String)appConfigForm.getValue("kmss.oms.in.java.enabled");
		if("true".equals(omsInEnable)){
			String name = OMSPlugin.getOtherOmsInEnabledKey("java");
			if(name!=null){
				KmssMessages messages = new KmssMessages();
				messages.addError(new KmssMessage("sys-oms:oms.in.hasEnabled",name,name));
				KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
				return mapping.findForward("failure");
			}
		}

		if (appConfigForm.getDataMap() != null
				&& appConfigForm.getDataMap()
						.containsKey("kmss.java.webservice.password")) {
			String value = (String) appConfigForm
					.getValue("kmss.java.webservice.password");
			String password = value;
			if (EkpJavaUtil.isEncryped(value)) {
				password = EkpJavaUtil
						.desDecrypt(value);
			}
			String password_des = EkpJavaUtil.desEncrypt(password);
			String password_md5 = MD5Util.getMD5String(password);
			appConfigForm.setValue("kmss.java.webservice.tnsPassword",
					password_md5);
			appConfigForm.setValue("kmss.java.webservice.password",
					password_des);
		}

		ActionForward actionForward = super.update(mapping, appConfigForm,
				request, response);

		EkpSynchroDelegate.resetGetOrgWebService();
		EkpClientTodoThread.resetNotifyTodoWebService();
		EkpRoleSynchroDelegate.resetGetOrgWebService();
		try {
			MessageCenter
					.getInstance()
					.sendToOther(
							new EkpJavaConfigMessage(
									"updateConfig",
									appConfigForm.getDataMap()));
		} catch (Exception e) {
			logger.error("", e);
		}
		return actionForward;
		
	}

}
