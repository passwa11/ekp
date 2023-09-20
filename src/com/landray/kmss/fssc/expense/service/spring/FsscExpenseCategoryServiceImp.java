package com.landray.kmss.fssc.expense.service.spring;

import java.util.List;
import java.util.Date;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.fssc.expense.util.FsscExpenseUtil;
import com.landray.kmss.fssc.expense.service.IFsscExpenseCategoryService;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryService;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.fssc.expense.model.FsscExpenseCategory;
import com.landray.kmss.common.forms.IExtendForm;

public class FsscExpenseCategoryServiceImp extends ExtendDataServiceImp implements IFsscExpenseCategoryService, ISysSimpleCategoryService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscExpenseCategory) {
            FsscExpenseCategory fsscExpenseCategory = (FsscExpenseCategory) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscExpenseCategory fsscExpenseCategory = new FsscExpenseCategory();
        fsscExpenseCategory.setDocCreateTime(new Date());
        fsscExpenseCategory.setFdExpenseType(String.valueOf("1"));
        fsscExpenseCategory.setFdAllocType(String.valueOf("1"));
        fsscExpenseCategory.setFdSubjectType(String.valueOf("1"));
        fsscExpenseCategory.setFdIsProject(Boolean.valueOf("false"));
        fsscExpenseCategory.setDocCreator(UserUtil.getUser());
        FsscExpenseUtil.initModelFromRequest(fsscExpenseCategory, requestContext);
        return fsscExpenseCategory;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscExpenseCategory fsscExpenseCategory = (FsscExpenseCategory) model;
    }

    @Override
    public List<FsscExpenseCategory> findByFdParent(FsscExpenseCategory fdParent) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscExpenseCategory.fdParent.fdId=:fdId");
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
