package com.landray.kmss.fssc.expense.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.fssc.expense.model.FsscExpenseBalanceCategory;
import com.landray.kmss.fssc.expense.service.IFsscExpenseBalanceCategoryService;
import com.landray.kmss.fssc.expense.util.FsscExpenseUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

public class FsscExpenseBalanceCategoryServiceImp extends ExtendDataServiceImp implements IFsscExpenseBalanceCategoryService, ISysSimpleCategoryService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscExpenseBalanceCategory) {
            FsscExpenseBalanceCategory fsscExpenseBalanceCategory = (FsscExpenseBalanceCategory) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscExpenseBalanceCategory fsscExpenseBalanceCategory = new FsscExpenseBalanceCategory();
        fsscExpenseBalanceCategory.setDocCreateTime(new Date());
        fsscExpenseBalanceCategory.setDocCreator(UserUtil.getUser());
        fsscExpenseBalanceCategory.setFdSubjectType(String.valueOf("1"));
        FsscExpenseUtil.initModelFromRequest(fsscExpenseBalanceCategory, requestContext);
        return fsscExpenseBalanceCategory;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscExpenseBalanceCategory fsscExpenseBalanceCategory = (FsscExpenseBalanceCategory) model;
    }

    @Override
    public List<FsscExpenseBalanceCategory> findByFdParent(FsscExpenseBalanceCategory fdParent) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscExpenseBalanceCategory.fdParent.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdParent.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
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
