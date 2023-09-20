package com.landray.kmss.fssc.budgeting.service.spring;

import java.util.Date;
import com.landray.kmss.fssc.budgeting.util.FsscBudgetingUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingEffectAuthService;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingEffectAuth;
import com.landray.kmss.common.model.IBaseModel;

public class FsscBudgetingEffectAuthServiceImp extends ExtendDataServiceImp implements IFsscBudgetingEffectAuthService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscBudgetingEffectAuth) {
            FsscBudgetingEffectAuth fsscBudgetingEffectAuth = (FsscBudgetingEffectAuth) model;
            fsscBudgetingEffectAuth.setDocAlterTime(new Date());
            fsscBudgetingEffectAuth.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscBudgetingEffectAuth fsscBudgetingEffectAuth = new FsscBudgetingEffectAuth();
        fsscBudgetingEffectAuth.setDocCreateTime(new Date());
        fsscBudgetingEffectAuth.setDocAlterTime(new Date());
        fsscBudgetingEffectAuth.setFdIsAvailable(Boolean.valueOf("true"));
        fsscBudgetingEffectAuth.setDocCreator(UserUtil.getUser());
        fsscBudgetingEffectAuth.setDocAlteror(UserUtil.getUser());
        FsscBudgetingUtil.initModelFromRequest(fsscBudgetingEffectAuth, requestContext);
        return fsscBudgetingEffectAuth;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscBudgetingEffectAuth fsscBudgetingEffectAuth = (FsscBudgetingEffectAuth) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
