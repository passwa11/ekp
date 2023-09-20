package com.landray.kmss.sys.portal.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.portal.model.SysPortalPopTpl;
import com.landray.kmss.sys.portal.model.SysPortalPopTplCategory;
import com.landray.kmss.sys.portal.service.ISysPortalPopTplService;
import com.landray.kmss.sys.portal.util.SysPortalPopUtil;
import com.landray.kmss.util.UserUtil;

public class SysPortalPopTplServiceImp extends ExtendDataServiceImp implements ISysPortalPopTplService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysPortalPopTpl) {
            SysPortalPopTpl sysPortalPopTpl = (SysPortalPopTpl) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysPortalPopTpl sysPortalPopTpl = new SysPortalPopTpl();
        sysPortalPopTpl.setDocCreateTime(new Date());
        sysPortalPopTpl.setFdIsAvailable(Boolean.valueOf("true"));
        sysPortalPopTpl.setDocCreator(UserUtil.getUser());
        SysPortalPopUtil.initModelFromRequest(sysPortalPopTpl, requestContext);
        return sysPortalPopTpl;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysPortalPopTpl sysPortalPopTpl = (SysPortalPopTpl) model;
    }

    @Override
    public List<SysPortalPopTpl> findByFdCategory(SysPortalPopTplCategory fdCategory) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("sysPortalPopTpl.fdCategory.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCategory.getFdId());
        return this.findList(hqlInfo);
    }
}
