package com.landray.kmss.third.weixin.service.spring;

import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.service.IThirdWeixinPayLogService;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayLog;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;

public class ThirdWeixinPayLogServiceImp extends ExtendDataServiceImp implements IThirdWeixinPayLogService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinPayLog) {
            ThirdWeixinPayLog thirdWeixinPayLog = (ThirdWeixinPayLog) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinPayLog thirdWeixinPayLog = new ThirdWeixinPayLog();
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinPayLog, requestContext);
        return thirdWeixinPayLog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinPayLog thirdWeixinPayLog = (ThirdWeixinPayLog) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
