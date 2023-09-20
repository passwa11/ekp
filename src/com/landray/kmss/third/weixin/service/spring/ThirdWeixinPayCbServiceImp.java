package com.landray.kmss.third.weixin.service.spring;

import java.util.Date;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.third.weixin.service.IThirdWeixinPayCbService;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayCb;
import com.landray.kmss.common.model.IBaseModel;

public class ThirdWeixinPayCbServiceImp extends ExtendDataServiceImp implements IThirdWeixinPayCbService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinPayCb) {
            ThirdWeixinPayCb thirdWeixinPayCb = (ThirdWeixinPayCb) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinPayCb thirdWeixinPayCb = new ThirdWeixinPayCb();
        thirdWeixinPayCb.setDocCreateTime(new Date());
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinPayCb, requestContext);
        return thirdWeixinPayCb;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinPayCb thirdWeixinPayCb = (ThirdWeixinPayCb) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
