package com.landray.kmss.third.weixin.service.spring;

import java.util.Date;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.third.weixin.service.IThirdWeixinContactCbService;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.model.ThirdWeixinContactCb;
import com.landray.kmss.common.model.IBaseModel;

public class ThirdWeixinContactCbServiceImp extends ExtendDataServiceImp implements IThirdWeixinContactCbService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinContactCb) {
            ThirdWeixinContactCb thirdWeixinContactCb = (ThirdWeixinContactCb) model;
            thirdWeixinContactCb.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinContactCb thirdWeixinContactCb = new ThirdWeixinContactCb();
        thirdWeixinContactCb.setDocCreateTime(new Date());
        thirdWeixinContactCb.setDocAlterTime(new Date());
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinContactCb, requestContext);
        return thirdWeixinContactCb;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinContactCb thirdWeixinContactCb = (ThirdWeixinContactCb) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
