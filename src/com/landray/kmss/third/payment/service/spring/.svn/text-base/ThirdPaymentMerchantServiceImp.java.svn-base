package com.landray.kmss.third.payment.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.payment.model.ThirdPaymentMerchant;
import com.landray.kmss.third.payment.service.IThirdPaymentMerchantService;
import com.landray.kmss.third.payment.util.ThirdPaymentUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

public class ThirdPaymentMerchantServiceImp extends ExtendDataServiceImp implements IThirdPaymentMerchantService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdPaymentMerchant) {
            ThirdPaymentMerchant thirdPaymentMerchant = (ThirdPaymentMerchant) model;
            thirdPaymentMerchant.setDocAlterTime(new Date());
            thirdPaymentMerchant.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdPaymentMerchant thirdPaymentMerchant = new ThirdPaymentMerchant();
        thirdPaymentMerchant.setDocCreateTime(new Date());
        thirdPaymentMerchant.setDocAlterTime(new Date());
        thirdPaymentMerchant.setFdMerchType(String.valueOf("normal"));
        thirdPaymentMerchant.setFdMerchStatus(Integer.valueOf("1"));
        thirdPaymentMerchant.setDocCreator(UserUtil.getUser());
        thirdPaymentMerchant.setDocAlteror(UserUtil.getUser());
        ThirdPaymentUtil.initModelFromRequest(thirdPaymentMerchant, requestContext);
        return thirdPaymentMerchant;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdPaymentMerchant thirdPaymentMerchant = (ThirdPaymentMerchant) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
