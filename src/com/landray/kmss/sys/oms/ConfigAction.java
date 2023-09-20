package com.landray.kmss.sys.oms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.sys.config.action.SysConfigAdminAction;
import com.landray.kmss.sys.config.form.SysConfigAdminForm;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;

public class ConfigAction extends SysConfigAdminAction{
	
	@Override
    public ActionForward config(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			SysConfigAdminForm configForm = new SysConfigAdminForm();
			Map<String,String> map = new HashMap<String,String>();
			map.putAll(ResourceUtil.getKmssConfig("kmss.oms.in"));

			configForm.setMap(map);
				request.setAttribute("configForm", configForm);
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("configView");
		}
	}

}
