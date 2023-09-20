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
import com.landray.kmss.sys.portal.model.SysPortalPopMain;
import com.landray.kmss.sys.portal.service.ISysPortalPopMainService;
import com.landray.kmss.sys.portal.util.SysPortalPopUtil;
import com.landray.kmss.util.UserUtil;

public class SysPortalPopMainServiceImp extends ExtendDataServiceImp implements ISysPortalPopMainService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysPortalPopMain) {
            SysPortalPopMain sysPortalPopMain = (SysPortalPopMain) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysPortalPopMain sysPortalPopMain = new SysPortalPopMain();
        sysPortalPopMain.setDocCreateTime(new Date());
        sysPortalPopMain.setFdIsAvailable(Boolean.valueOf("true"));
        sysPortalPopMain.setDocCreator(UserUtil.getUser());
        SysPortalPopUtil.initModelFromRequest(sysPortalPopMain, requestContext);
        return sysPortalPopMain;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysPortalPopMain sysPortalPopMain = (SysPortalPopMain) model;
    }

    @Override
    public List<SysPortalPopMain> findByFdCategory(SysPortalPopCategory fdCategory) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("sysPortalPopMain.fdCategory.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCategory.getFdId());
        return this.findList(hqlInfo);
    }
}

