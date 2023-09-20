package com.landray.kmss.km.archives.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.module.core.register.loader.ModuleDictUtil;
import com.landray.kmss.common.module.util.ModuleCenter;
import com.landray.kmss.common.service.BaseCoreOuterServiceImp;
import com.landray.kmss.km.archives.forms.KmArchivesFileTemplateForm;
import com.landray.kmss.km.archives.interfaces.IKmArchivesFileTemplateCoreService;
import com.landray.kmss.km.archives.interfaces.IKmArchivesFileTemplateForm;
import com.landray.kmss.km.archives.interfaces.IKmArchivesFileTemplateModel;
import com.landray.kmss.km.archives.model.KmArchivesFileTemplate;
import com.landray.kmss.km.archives.service.IKmArchivesFileTemplateService;
import com.landray.kmss.util.ModelUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class KmArchivesFileTemplateCoreServiceImp extends
		BaseCoreOuterServiceImp implements IKmArchivesFileTemplateCoreService {

	private IKmArchivesFileTemplateService kmArchivesFileTemplateService;

	public void setKmArchivesFileTemplateService(
			IKmArchivesFileTemplateService kmArchivesFileTemplateService) {
		this.kmArchivesFileTemplateService = kmArchivesFileTemplateService;
	}

	@Override
	public void add(IBaseModel model) throws Exception {
		if (model instanceof IKmArchivesFileTemplateModel) {
			KmArchivesFileTemplate template = this.getTemplate(model);
			if (template != null) {
                kmArchivesFileTemplateService.add(template);
            }

		} else if (ModuleDictUtil.isRequired(model, IKmArchivesFileTemplateModel.class)) {
			KmArchivesFileTemplate template = this.getTemplateCarefully(model);
			if (template != null) {
                kmArchivesFileTemplateService.add(template);
            }
		}
	}

	@Override
	public void update(IBaseModel model) throws Exception {
		if (model instanceof IKmArchivesFileTemplateModel) {
			KmArchivesFileTemplate template = this.getTemplate(model);
			if (template != null) {
                kmArchivesFileTemplateService.update(template);
            }

		} else if (ModuleDictUtil.isRequired(model, IKmArchivesFileTemplateModel.class)) {
			KmArchivesFileTemplate template = this.getTemplateCarefully(model);
			if (template != null) {
                kmArchivesFileTemplateService.update(template);
            }
		}
	}

	@Override
	public void delete(IBaseModel model) throws Exception {
		if (model instanceof IKmArchivesFileTemplateModel || ModuleDictUtil.isRequired(model, IKmArchivesFileTemplateModel.class)) {
			kmArchivesFileTemplateService.deleteCoreModels(model);
		}
	}

	private KmArchivesFileTemplate getTemplate(IBaseModel model) {
		IKmArchivesFileTemplateModel mainModel = (IKmArchivesFileTemplateModel) model;
		KmArchivesFileTemplate template = mainModel.getKmArchivesFileTemplate();
		if (template == null || template.getFdId() == null) {
			return null;
		}
		return initTemplate(model, template);
	}

	private KmArchivesFileTemplate getTemplateCarefully(IBaseModel model) {
		KmArchivesFileTemplate template = ModuleCenter.enhanceBean(model).getMechanismModel(KmArchivesFileTemplate.class);
		if (template != null && template.getFdId() != null) {
			String fdModelId = model.getFdId();
			String fdModelName = ModelUtil.getModelClassName(model);
			String fdKey = StringUtils.isNotEmpty(template.getFdKey())
					? template
					.getFdKey()
					: template.getFdId();
			template.setFdModelId(fdModelId);
			template.setFdModelName(fdModelName);
			template.setFdKey(fdKey);
			return template;
		}
		return null;
	}

	private KmArchivesFileTemplate initTemplate(IBaseModel model, KmArchivesFileTemplate template) {
		String fdModelId = model.getFdId();
		String fdModelName = ModelUtil.getModelClassName(model);
		String fdKey = StringUtils.isNotEmpty(template.getFdKey())
				? template
				.getFdKey()
				: template.getFdId();
		template.setFdModelId(fdModelId);
		template.setFdModelName(fdModelName);
		template.setFdKey(fdKey);
		return template;
	}

	@Override
	public void convertFormToModel(IExtendForm form, IBaseModel model,
								   RequestContext requestContext) throws Exception {
		//原机制分发逻辑
		if (model instanceof IKmArchivesFileTemplateModel
				&& form instanceof IKmArchivesFileTemplateForm) {
			IKmArchivesFileTemplateForm mainForm = (IKmArchivesFileTemplateForm) form;
			IKmArchivesFileTemplateModel mainModel = (IKmArchivesFileTemplateModel) model;
			KmArchivesFileTemplateForm templateForm = mainForm
					.getKmArchivesFileTemplateForm();
			KmArchivesFileTemplate template = mainModel
					.getKmArchivesFileTemplate();
			associateMech2Main(model, templateForm, template);
			template = (KmArchivesFileTemplate) kmArchivesFileTemplateService
					.convertFormToModel(templateForm, template, requestContext);
			mainModel.setKmArchivesFileTemplate(template);

		//模块解耦中心机制分发逻辑
		} else if (ModuleDictUtil.isRequired(form, IKmArchivesFileTemplateModel.class)) {
			KmArchivesFileTemplateForm templateForm = ModuleCenter.enhanceBean(form).getMechanismForm(KmArchivesFileTemplateForm.class) ;
			if (templateForm==null){
				templateForm = new KmArchivesFileTemplateForm();
			}
			KmArchivesFileTemplate template = (KmArchivesFileTemplate) kmArchivesFileTemplateService.convertFormToModel(templateForm,null, requestContext);
			associateMech2Main(model,templateForm,template);
			ModuleCenter.enhanceBean(model).setMechanism(template);
		}
	}

	@Override
	public void convertModelToForm(IExtendForm form, IBaseModel model,
								   RequestContext requestContext) throws Exception {
		if (model == null) {
			return;
		}
		if (model instanceof IKmArchivesFileTemplateModel
				&& form instanceof IKmArchivesFileTemplateForm) {
			IKmArchivesFileTemplateForm mainForm = (IKmArchivesFileTemplateForm) form;
			IKmArchivesFileTemplateModel mainModel = (IKmArchivesFileTemplateModel) model;
			KmArchivesFileTemplateForm templateForm = mainForm
					.getKmArchivesFileTemplateForm();
			List list = kmArchivesFileTemplateService.getCoreModels(mainModel, null);
			if (list != null && list.size() > 0) {
				kmArchivesFileTemplateService.convertModelToForm(templateForm, (IBaseModel) list.get(0), requestContext);
			}
		} else if (ModuleDictUtil.isRequired(model, IKmArchivesFileTemplateModel.class)) {
			KmArchivesFileTemplateForm templateForm = ModuleCenter.enhanceBean(form).getMechanismForm(KmArchivesFileTemplateForm.class);
			if (templateForm==null){
				templateForm = new KmArchivesFileTemplateForm();
			}
			List list = kmArchivesFileTemplateService.getCoreModels(model, null);
			if (list != null && list.size() > 0) {
				kmArchivesFileTemplateService.convertModelToForm(templateForm, (IBaseModel) list.get(0), requestContext);
			}
			ModuleCenter.enhanceBean(form).setMechanism(templateForm);
		}
	}
}
