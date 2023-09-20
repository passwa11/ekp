package com.landray.kmss.third.ding.service.spring;

import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.ding.service.IThirdDingXformNotifyLogService;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.third.ding.model.ThirdDingXformNotifyLog;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;

public class ThirdDingXformNotifyLogServiceImp extends ExtendDataServiceImp implements IThirdDingXformNotifyLogService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingXformNotifyLog) {
            ThirdDingXformNotifyLog thirdDingXformNotifyLog = (ThirdDingXformNotifyLog) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingXformNotifyLog thirdDingXformNotifyLog = new ThirdDingXformNotifyLog();
        ThirdDingUtil.initModelFromRequest(thirdDingXformNotifyLog, requestContext);
        return thirdDingXformNotifyLog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingXformNotifyLog thirdDingXformNotifyLog = (ThirdDingXformNotifyLog) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
