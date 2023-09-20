package com.landray.kmss.tic.rest.connector.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.tic.rest.connector.model.TicRestCategory;
import com.landray.kmss.tic.rest.connector.service.ITicRestCategoryService;
import com.landray.kmss.util.UserUtil;

/**
 * REST服务分类业务接口实现
 */
public class TicRestCategoryServiceImp extends ExtendDataServiceImp implements ITicRestCategoryService {
	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context)
			throws Exception {
		model = super.convertBizFormToModel(form, model, context);
		if (model instanceof TicRestCategory) {
			TicRestCategory ticRestCategory = (TicRestCategory) model;
		}
		return model;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		TicRestCategory ticRestCategory = new TicRestCategory();
		ticRestCategory.setDocCreateTime(new Date());
		ticRestCategory.setDocCreator(UserUtil.getUser());
		// TicCoreUtil.initModelFromRequest(TicRestCategory, requestContext);
		return ticRestCategory;
	}

	@Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {
		TicRestCategory TicRestCategory = (TicRestCategory) model;
	}

	@Override
    public List<TicRestCategory> findByFdParent(TicRestCategory fdParent) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("ticRestCategory.fdParent.fdId=:fdId");
		hqlInfo.setParameter("fdId", fdParent.getFdId());
		return this.findList(hqlInfo);
	}

	public List getAllChildCategory(ISysSimpleCategoryModel category) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdHierarchyId like :fdHierarchyId and fdId!=:fdId");
		hqlInfo.setParameter("fdHierarchyId", category.getFdHierarchyId() + "%");
		hqlInfo.setParameter("fdId", category.getFdId());
		return this.findList(hqlInfo);
	}
}
