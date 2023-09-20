package com.landray.kmss.fssc.budget.service.spring;

import java.util.List;
import java.util.Date;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.fssc.budget.util.FsscBudgetUtil;
import com.landray.kmss.fssc.budget.service.IFsscBudgetAdjustCategoryService;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryService;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustCategory;
import com.landray.kmss.common.forms.IExtendForm;

public class FsscBudgetAdjustCategoryServiceImp extends ExtendDataServiceImp implements IFsscBudgetAdjustCategoryService, ISysSimpleCategoryService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscBudgetAdjustCategory) {
            FsscBudgetAdjustCategory fsscBudgetAdjustCategory = (FsscBudgetAdjustCategory) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscBudgetAdjustCategory fsscBudgetAdjustCategory = new FsscBudgetAdjustCategory();
        fsscBudgetAdjustCategory.setDocCreateTime(new Date());
        fsscBudgetAdjustCategory.setFdAdjustType(String.valueOf("1"));
        fsscBudgetAdjustCategory.setDocCreator(UserUtil.getUser());
        FsscBudgetUtil.initModelFromRequest(fsscBudgetAdjustCategory, requestContext);
        return fsscBudgetAdjustCategory;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscBudgetAdjustCategory fsscBudgetAdjustCategory = (FsscBudgetAdjustCategory) model;
    }

    @Override
    public List<FsscBudgetAdjustCategory> findByFdParent(FsscBudgetAdjustCategory fdParent) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetAdjustCategory.fdParent.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdParent.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List getAllChildCategory(ISysSimpleCategoryModel category) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fdHierarchyId like :fdHierarchyId and fdId!=:fdId");
        hqlInfo.setParameter("fdHierarchyId", category.getFdHierarchyId() + "%");
        hqlInfo.setParameter("fdId", category.getFdId());
        return this.findList(hqlInfo);
    }
}
