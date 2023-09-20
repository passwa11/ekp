package com.landray.kmss.sys.webservice2.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.sys.appconfig.actions.SysAppConfigAction;
import com.landray.kmss.sys.webservice2.model.SysWebserviceLogConfig;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class SysWebserviceLogConfigAction extends SysAppConfigAction {
	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String modelName = request.getParameter("modelName");
		if (StringUtil.isNull(modelName)) {
            throw new NoRecordException();
        }
		SysWebserviceLogConfig sysWebserviceLogConfig = (SysWebserviceLogConfig) ClassUtils.forName(modelName)
				.newInstance();
		sysWebserviceLogConfig.setModelName(modelName);
		request.setAttribute("sysWebserviceLogConfig", sysWebserviceLogConfig);
		return super.edit(mapping, form, request, response);
	}
}
