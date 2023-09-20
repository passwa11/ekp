package com.landray.kmss.third.weixin.work.service.spring;

import java.util.Date;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinAuthLog;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinAuthLogService;
import com.landray.kmss.util.SpringBeanUtil;

public class ThirdWeixinAuthLogServiceImp extends ExtendDataServiceImp implements IThirdWeixinAuthLogService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinAuthLog) {
            ThirdWeixinAuthLog thirdWeixinAuthLog = (ThirdWeixinAuthLog) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinAuthLog thirdWeixinAuthLog = new ThirdWeixinAuthLog();
        thirdWeixinAuthLog.setDocCreateTime(new Date());
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinAuthLog, requestContext);
        return thirdWeixinAuthLog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinAuthLog thirdWeixinAuthLog = (ThirdWeixinAuthLog) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
