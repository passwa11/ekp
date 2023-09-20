package com.landray.kmss.fssc.expense.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.fssc.expense.model.FsscExpenseCategory;
import com.landray.kmss.fssc.expense.model.FsscExpenseShareCategory;
import com.landray.kmss.fssc.expense.service.IFsscExpenseShareCategoryService;
import com.landray.kmss.fssc.expense.util.FsscExpenseUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryService;
import com.landray.kmss.util.UserUtil;

public class FsscExpenseShareCategoryServiceImp extends ExtendDataServiceImp implements IFsscExpenseShareCategoryService, ISysSimpleCategoryService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscExpenseShareCategory) {
            FsscExpenseShareCategory fsscExpenseShareCategory = (FsscExpenseShareCategory) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscExpenseShareCategory fsscExpenseShareCategory = new FsscExpenseShareCategory();
        fsscExpenseShareCategory.setDocCreateTime(new Date());
        fsscExpenseShareCategory.setDocCreator(UserUtil.getUser());
        fsscExpenseShareCategory.setFdSubjectType(String.valueOf("1"));
        FsscExpenseUtil.initModelFromRequest(fsscExpenseShareCategory, requestContext);
        return fsscExpenseShareCategory;
    }
    
    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscExpenseShareCategory fsscExpenseShareCategory = (FsscExpenseShareCategory) model;
    }

    @Override
    public List<FsscExpenseShareCategory> findByFdParent(FsscExpenseShareCategory fdParent) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscExpenseShareCategory.fdParent.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdParent.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscExpenseShareCategory> findByFdCategory(FsscExpenseCategory fdCategory) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscExpenseShareCategory.fdCategory.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCategory.getFdId());
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
