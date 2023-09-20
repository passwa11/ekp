package com.landray.kmss.sys.organization.extend.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.organization.extend.OrgPluginUtil;
import com.landray.kmss.sys.organization.extend.PersonExtendProperty;
import com.landray.kmss.sys.organization.extend.forms.IPluginExtendForm;

public class PluginExtendBaseServiceImp extends BaseServiceImp implements
		IBaseService {

	@Override
    public IExtendForm convertModelToForm(IExtendForm form, IBaseModel model,
                                          RequestContext requestContext) throws Exception {
		form = super.convertModelToForm(form, model, requestContext);

		if (form instanceof IPluginExtendForm) {
			IExtension extension = OrgPluginUtil.getPersonExtendProperty();
			if (extension != null) {
				IBaseService piService = (IBaseService) Plugin.getParamValue(
						extension, PersonExtendProperty.PARAM_BEAN);
				IBaseModel piModel = piService.findByPrimaryKey(form.getFdId());
				piService.convertModelToForm(((IPluginExtendForm) form)
						.getPluginForm(), piModel, requestContext);
			}
		}
		return form;
	}

	@Override
    public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		String fdId = super.add(form, requestContext);

		if (form instanceof IPluginExtendForm) {
			IExtension extension = OrgPluginUtil.getPersonExtendProperty();
			if (extension != null) {
				IPluginExtendForm piForm = (IPluginExtendForm) form;
				piForm.getPluginForm().setFdId(fdId);
				IBaseService piService = (IBaseService) Plugin.getParamValue(
						extension, PersonExtendProperty.PARAM_BEAN);
				piService.add(piForm.getPluginForm(), requestContext);
			}
		}
		return fdId;
	}

	@Override
    public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		super.update(form, requestContext);

		if (form instanceof IPluginExtendForm) {
			IExtension extension = OrgPluginUtil.getPersonExtendProperty();
			if (extension != null) {
				IPluginExtendForm piForm = (IPluginExtendForm) form;
				piForm.getPluginForm().setFdId(form.getFdId());
				IBaseService piService = (IBaseService) Plugin.getParamValue(
						extension, PersonExtendProperty.PARAM_BEAN);
				piService.update(piForm.getPluginForm(), requestContext);
			}
		}
	}

	@Override
    public void delete(IBaseModel modelObj) throws Exception {
		super.delete(modelObj);
		IExtension extension = OrgPluginUtil.getPersonExtendProperty();
		if (extension != null) {
			IBaseService piService = (IBaseService) Plugin.getParamValue(
					extension, PersonExtendProperty.PARAM_BEAN);
			piService.delete(modelObj.getFdId());
		}
	}
}
