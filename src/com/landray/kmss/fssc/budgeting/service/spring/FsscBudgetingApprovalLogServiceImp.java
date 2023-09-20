package com.landray.kmss.fssc.budgeting.service.spring;

import com.landray.kmss.fssc.budgeting.util.FsscBudgetingUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingApprovalLog;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingApprovalLogService;

public class FsscBudgetingApprovalLogServiceImp extends ExtendDataServiceImp implements IFsscBudgetingApprovalLogService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscBudgetingApprovalLog) {
            FsscBudgetingApprovalLog fsscBudgetingApprovalLog = (FsscBudgetingApprovalLog) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscBudgetingApprovalLog fsscBudgetingApprovalLog = new FsscBudgetingApprovalLog();
        FsscBudgetingUtil.initModelFromRequest(fsscBudgetingApprovalLog, requestContext);
        return fsscBudgetingApprovalLog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscBudgetingApprovalLog fsscBudgetingApprovalLog = (FsscBudgetingApprovalLog) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
