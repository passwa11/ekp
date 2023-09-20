package com.landray.kmss.sys.portal.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.portal.model.SysPortalPopCategory;
import com.landray.kmss.sys.portal.service.ISysPortalPopCategoryService;
import com.landray.kmss.sys.portal.util.SysPortalPopUtil;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryService;
import com.landray.kmss.util.UserUtil;

public class SysPortalPopCategoryServiceImp extends ExtendDataServiceImp implements ISysPortalPopCategoryService, ISysSimpleCategoryService  {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysPortalPopCategory) {
        	SysPortalPopCategory sysPortalPopCategory = (SysPortalPopCategory) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
    	SysPortalPopCategory sysPortalPopCategory = new SysPortalPopCategory();
        sysPortalPopCategory.setDocCreateTime(new Date());
        sysPortalPopCategory.setDocCreator(UserUtil.getUser());
        SysPortalPopUtil.initModelFromRequest(sysPortalPopCategory, requestContext);
        return sysPortalPopCategory;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
    	SysPortalPopCategory sysPortalPopCategory = (SysPortalPopCategory) model;
    }

    @Override
    public List<SysPortalPopCategory> findByFdParent(SysPortalPopCategory fdParent) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("sysPortalPopCategory.fdParent.fdId=:fdId");
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
