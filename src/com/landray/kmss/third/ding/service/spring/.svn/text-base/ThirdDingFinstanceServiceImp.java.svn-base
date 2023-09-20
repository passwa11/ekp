package com.landray.kmss.third.ding.service.spring;

import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.third.ding.service.IThirdDingFinstanceService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.model.ThirdDingFinstance;

public class ThirdDingFinstanceServiceImp extends ExtendDataServiceImp implements IThirdDingFinstanceService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingFinstance) {
            ThirdDingFinstance thirdDingFinstance = (ThirdDingFinstance) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingFinstance thirdDingFinstance = new ThirdDingFinstance();
        ThirdDingUtil.initModelFromRequest(thirdDingFinstance, requestContext);
        return thirdDingFinstance;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingFinstance thirdDingFinstance = (ThirdDingFinstance) model;
    }
}
