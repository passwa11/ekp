package com.landray.kmss.third.payment.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.payment.model.ThirdPaymentCallLog;
import com.landray.kmss.third.payment.service.IThirdPaymentCallLogService;
import com.landray.kmss.third.payment.util.ThirdPaymentUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

public class ThirdPaymentCallLogServiceImp extends ExtendDataServiceImp implements IThirdPaymentCallLogService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdPaymentCallLog) {
            ThirdPaymentCallLog thirdPaymentCallLog = (ThirdPaymentCallLog) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdPaymentCallLog thirdPaymentCallLog = new ThirdPaymentCallLog();
        thirdPaymentCallLog.setDocCreateTime(new Date());
        thirdPaymentCallLog.setDocCreator(UserUtil.getUser());
        ThirdPaymentUtil.initModelFromRequest(thirdPaymentCallLog, requestContext);
        return thirdPaymentCallLog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdPaymentCallLog thirdPaymentCallLog = (ThirdPaymentCallLog) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
