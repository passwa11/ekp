package com.landray.kmss.third.weixin.service.spring;

import java.util.Date;
import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.service.IThirdWeixinPayBlService;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayBl;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.common.forms.IExtendForm;

public class ThirdWeixinPayBlServiceImp extends ExtendDataServiceImp implements IThirdWeixinPayBlService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinPayBl) {
            ThirdWeixinPayBl thirdWeixinPayBl = (ThirdWeixinPayBl) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinPayBl thirdWeixinPayBl = new ThirdWeixinPayBl();
        thirdWeixinPayBl.setDocCreateTime(new Date());
        thirdWeixinPayBl.setDocCreator(UserUtil.getUser());
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinPayBl, requestContext);
        return thirdWeixinPayBl;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinPayBl thirdWeixinPayBl = (ThirdWeixinPayBl) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
