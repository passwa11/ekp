package com.landray.kmss.third.weixin.work.service.spring;

import java.util.Date;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkCallback;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinWorkCallbackService;
import com.landray.kmss.third.weixin.work.util.ThirdWeixinUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class ThirdWeixinWorkCallbackServiceImp extends ExtendDataServiceImp implements IThirdWeixinWorkCallbackService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinWorkCallback) {
            ThirdWeixinWorkCallback thirdWeixinWorkCallback = (ThirdWeixinWorkCallback) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinWorkCallback thirdWeixinWorkCallback = new ThirdWeixinWorkCallback();
        thirdWeixinWorkCallback.setDocCreateTime(new Date());
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinWorkCallback, requestContext);
        return thirdWeixinWorkCallback;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinWorkCallback thirdWeixinWorkCallback = (ThirdWeixinWorkCallback) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
