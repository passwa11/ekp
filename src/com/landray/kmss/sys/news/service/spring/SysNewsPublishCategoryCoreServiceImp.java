package com.landray.kmss.sys.news.service.spring;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseCoreOuterServiceImp;
import com.landray.kmss.sys.news.interfaces.ISysNewsPublishCategoryCoreService;
import com.landray.kmss.sys.news.interfaces.ISysNewsPublishCategoryForm;
import com.landray.kmss.sys.news.interfaces.ISysNewsPublishCategoryModel;
import com.landray.kmss.sys.news.model.SysNewsPublishCategory;
import com.landray.kmss.sys.news.service.ISysNewsPublishCategoryService;
import com.landray.kmss.util.ModelUtil;

/**
 * 创建日期 2007-八月-02
 * 
 * @author 周超 发布机制类别设置
 */

public class SysNewsPublishCategoryCoreServiceImp extends
		BaseCoreOuterServiceImp implements ISysNewsPublishCategoryCoreService {

	private ISysNewsPublishCategoryService sysNewsPublishCategoryService;
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysNewsPublishCategoryCoreServiceImp.class);

	public void setSysNewsPublishCategoryService(
			ISysNewsPublishCategoryService sysNewsPublishCategoryService) {
		this.sysNewsPublishCategoryService = sysNewsPublishCategoryService;
	}

	@Override
	public void convertFormToModel(IExtendForm form, IBaseModel model,
								   RequestContext requestContext) throws Exception {
		// 判断是否有部署发布机制
		if (!(form instanceof ISysNewsPublishCategoryForm && model instanceof ISysNewsPublishCategoryModel)) {
            return;
        }
		if (logger.isDebugEnabled()) {
            logger.debug("将发布机制设置从Form转成Model");
        }
		ISysNewsPublishCategoryForm mainForm = (ISysNewsPublishCategoryForm) form;
		ISysNewsPublishCategoryModel mainModel = (ISysNewsPublishCategoryModel) model;
		mainModel.setSysNewsPublishCategorys(sysNewsPublishCategoryService
				.getCoreModels(mainModel, null));
		convertFormMapToModelList(form,model,mainForm.getSysNewsPublishCategoryForms(),
				mainModel.getSysNewsPublishCategorys(), requestContext,
				sysNewsPublishCategoryService);
	}

	@Override
	public void save(IBaseModel model) throws Exception {
		if (!(model instanceof ISysNewsPublishCategoryModel)) {
            return;
        }
		ISysNewsPublishCategoryModel settingModel = (ISysNewsPublishCategoryModel) model;
		List settingList = settingModel.getSysNewsPublishCategorys();
		if (settingList == null) {
            return;
        }
		if (logger.isDebugEnabled()) {
            logger.debug("保存相关的发布设置信息");
        }
		for (int i = 0; i < settingList.size(); i++) {
			SysNewsPublishCategory setting = (SysNewsPublishCategory) settingList
					.get(i);
			setting.setFdModelId(model.getFdId());
			setting.setFdModelName(ModelUtil.getModelClassName(model));
			sysNewsPublishCategoryService.update(setting);
		}
	}

	@Override
	public void convertModelToForm(IExtendForm form, IBaseModel model,
								   RequestContext requestContext) throws Exception {
		if (!(form instanceof ISysNewsPublishCategoryForm && model instanceof ISysNewsPublishCategoryModel)) {
            return;
        }
		if (logger.isDebugEnabled()) {
            logger.debug("将发布设置信息从Model转成Form");
        }
		ISysNewsPublishCategoryForm mainForm = (ISysNewsPublishCategoryForm) form;
		ISysNewsPublishCategoryModel mainModel = (ISysNewsPublishCategoryModel) model;
		mainModel.setSysNewsPublishCategorys(sysNewsPublishCategoryService
				.getCoreModels(mainModel, null));
		convertModelListToFormMap(mainForm.getSysNewsPublishCategoryForms(),
				mainModel.getSysNewsPublishCategorys(), requestContext,
				sysNewsPublishCategoryService);
	}

	@Override
	public void cloneModelToForm(IExtendForm form, IBaseModel model,
								 RequestContext requestContext) throws Exception {
		if (!(model instanceof ISysNewsPublishCategoryModel && form instanceof ISysNewsPublishCategoryForm)) {
			return;
		}
		ISysNewsPublishCategoryForm mainForm = (ISysNewsPublishCategoryForm) form;
		ISysNewsPublishCategoryModel mainModel = (ISysNewsPublishCategoryModel) model;
		mainModel.setSysNewsPublishCategorys(sysNewsPublishCategoryService
				.getCoreModels(mainModel, null));
		convertModelListToFormMap(mainForm.getSysNewsPublishCategoryForms(),
				mainModel.getSysNewsPublishCategorys(), requestContext,
				sysNewsPublishCategoryService); 
	}
	
	@Override
	public void delete(IBaseModel model) throws Exception {
		if (!(model instanceof ISysNewsPublishCategoryModel)) {
            return;
        }
		if (logger.isDebugEnabled()) {
            logger.debug("删除相关的发布信息记录");
        }
		sysNewsPublishCategoryService.deleteCoreModels(model);
	}
	
	@Override
	public List<?> exportData(String id, String modelName) throws Exception {
		List<?> processes = new ArrayList();
		processes.addAll(sysNewsPublishCategoryService.findValue(null, "fdModelId = '" + id + "' and fdModelName = '" + modelName + "'", null));
		return processes;
	}

}
