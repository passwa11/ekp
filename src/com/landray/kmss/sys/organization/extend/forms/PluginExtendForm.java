package com.landray.kmss.sys.organization.extend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.organization.extend.OrgPluginUtil;
import com.landray.kmss.sys.organization.extend.PersonExtendProperty;

/**
 * person扩展页面对象
 * 
 * @author 龚健
 * @see
 */
@SuppressWarnings("serial")
public abstract class PluginExtendForm extends ExtendForm implements
		IPluginExtendForm {
	public PluginExtendForm() {
		initPluginForm();
	}

	private void initPluginForm() {
		IExtension extension = OrgPluginUtil.getPersonExtendProperty();
		if (extension != null) {
			pluginForm = (IExtendForm) Plugin.getParamValue(extension,
					PersonExtendProperty.PARAM_FORM);
			pluginForm.setFdId(getFdId());
		}
	}

	private IExtendForm pluginForm;

	@Override
    public IExtendForm getPluginForm() {
		return pluginForm;
	}

	@Override
    public void setPluginForm(IExtendForm pluginForm) {
		this.pluginForm = pluginForm;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		initPluginForm();
		super.reset(mapping, request);
	}

}
