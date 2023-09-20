package com.landray.kmss.sys.organization.extend.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.organization.extend.OrgPluginUtil;
import com.landray.kmss.sys.organization.extend.PersonExtendProperty;
import com.landray.kmss.util.StringUtil;

/**
 * 人员扩展Action
 * 
 * @author 龚健
 * @see
 */
public abstract class PluginExtendAction extends ExtendAction {
	@Override
    public ActionForward add(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initImportForm("edit", request);
		return super.add(mapping, form, request, response);
	}

	@Override
    public ActionForward edit(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initImportForm("edit", request);
		return super.edit(mapping, form, request, response);
	}

	@Override
    public ActionForward view(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initImportForm("view", request);
		return super.view(mapping, form, request, response);
	}

	@Override
    public ActionForward saveadd(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initImportForm("edit", request);
		return super.saveadd(mapping, form, request, response);
	}

	/**
	 * 根据编辑或阅读状态，以及扩展点的配置，引入相应的页面。
	 */
	private void initImportForm(String status, HttpServletRequest request) {
		IExtension extension = OrgPluginUtil.getPersonExtendProperty();
		if (extension == null) {
			return;
		}
		String url = (String) Plugin.getParamValue(extension, status);
		String type = (String) Plugin.getParamValue(extension,
				PersonExtendProperty.PARAM_IMPORT_TYPE);
		if (StringUtil.isNull(url)) {
			return;
		}
		request.setAttribute("personImportType", type);
		request.setAttribute("personExtendFormUrl", url);
	}
}
