package com.landray.kmss.third.ding.service.spring;

import java.util.Date;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.ding.model.ThirdDingWork;
import com.landray.kmss.third.ding.service.IThirdDingWorkService;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

public class ThirdDingWorkServiceImp extends ExtendDataServiceImp implements IThirdDingWorkService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingWork) {
            ThirdDingWork thirdDingWork = (ThirdDingWork) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingWork thirdDingWork = new ThirdDingWork();
        thirdDingWork.setDocCreateTime(new Date());
        thirdDingWork.setDocCreator(UserUtil.getUser());
        ThirdDingUtil.initModelFromRequest(thirdDingWork, requestContext);
        return thirdDingWork;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingWork thirdDingWork = (ThirdDingWork) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

}
