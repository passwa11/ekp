package com.landray.kmss.third.ding.service.spring;

import java.util.Date;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.third.ding.service.IThirdDingSendDingService;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.ding.model.ThirdDingSendDing;
import com.landray.kmss.common.model.IBaseModel;

public class ThirdDingSendDingServiceImp extends ExtendDataServiceImp implements IThirdDingSendDingService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingSendDing) {
            ThirdDingSendDing thirdDingSendDing = (ThirdDingSendDing) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingSendDing thirdDingSendDing = new ThirdDingSendDing();
        thirdDingSendDing.setDocCreateTime(new Date());
        ThirdDingUtil.initModelFromRequest(thirdDingSendDing, requestContext);
        return thirdDingSendDing;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingSendDing thirdDingSendDing = (ThirdDingSendDing) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
