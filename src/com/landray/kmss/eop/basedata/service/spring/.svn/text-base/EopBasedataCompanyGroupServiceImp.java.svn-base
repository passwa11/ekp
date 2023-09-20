package com.landray.kmss.eop.basedata.service.spring;

import java.util.Date;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyGroupService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataCompanyGroupServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataCompanyGroupService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataCompanyGroup) {
            EopBasedataCompanyGroup eopBasedataCompanyGroup = (EopBasedataCompanyGroup) model;
            eopBasedataCompanyGroup.setDocAlterTime(new Date());
            eopBasedataCompanyGroup.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataCompanyGroup eopBasedataCompanyGroup = new EopBasedataCompanyGroup();
        eopBasedataCompanyGroup.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataCompanyGroup.setDocCreateTime(new Date());
        eopBasedataCompanyGroup.setDocAlterTime(new Date());
        eopBasedataCompanyGroup.setDocCreator(UserUtil.getUser());
        eopBasedataCompanyGroup.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataCompanyGroup, requestContext);
        return eopBasedataCompanyGroup;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataCompanyGroup eopBasedataCompanyGroup = (EopBasedataCompanyGroup) model;
    }
}
