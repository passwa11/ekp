package com.landray.kmss.third.weixin.service.spring;

import java.util.Date;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.third.weixin.service.IThirdWeixinAccountService;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.model.ThirdWeixinAccount;
import com.landray.kmss.common.model.IBaseModel;

public class ThirdWeixinAccountServiceImp extends ExtendDataServiceImp implements IThirdWeixinAccountService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinAccount) {
            ThirdWeixinAccount thirdWeixinAccount = (ThirdWeixinAccount) model;
            thirdWeixinAccount.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinAccount thirdWeixinAccount = new ThirdWeixinAccount();
        thirdWeixinAccount.setFdAccountType(Integer.valueOf("1"));
        thirdWeixinAccount.setDocCreateTime(new Date());
        thirdWeixinAccount.setDocAlterTime(new Date());
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinAccount, requestContext);
        return thirdWeixinAccount;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinAccount thirdWeixinAccount = (ThirdWeixinAccount) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
