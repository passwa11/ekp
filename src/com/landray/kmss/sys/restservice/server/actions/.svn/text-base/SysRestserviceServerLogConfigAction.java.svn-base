package com.landray.kmss.sys.restservice.server.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.sys.appconfig.actions.SysAppConfigAction;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLogConfig;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class SysRestserviceServerLogConfigAction extends SysAppConfigAction {
	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String modelName = request.getParameter("modelName");
		if (StringUtil.isNull(modelName)) {
            throw new NoRecordException();
        }
		SysRestserviceServerLogConfig sysRestserviceServerLogConfig = (SysRestserviceServerLogConfig) com.landray.kmss.util.ClassUtils.forName(modelName)
				.newInstance();
		sysRestserviceServerLogConfig.setModelName(modelName);
		request.setAttribute("sysRestserviceServerLogConfig", sysRestserviceServerLogConfig);
		return super.edit(mapping, form, request, response);
	}
}
