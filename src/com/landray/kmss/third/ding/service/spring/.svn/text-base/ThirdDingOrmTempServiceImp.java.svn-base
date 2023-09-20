package com.landray.kmss.third.ding.service.spring;

import java.util.Date;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.third.ding.service.IThirdDingOrmTempService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.model.ThirdDingOrmTemp;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;

public class ThirdDingOrmTempServiceImp extends ExtendDataServiceImp implements IThirdDingOrmTempService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingOrmTemp) {
            ThirdDingOrmTemp thirdDingOrmTemp = (ThirdDingOrmTemp) model;
            thirdDingOrmTemp.setDocAlterTime(new Date());
            thirdDingOrmTemp.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingOrmTemp thirdDingOrmTemp = new ThirdDingOrmTemp();
        thirdDingOrmTemp.setFdIsAvailable(Boolean.valueOf("true"));
        thirdDingOrmTemp.setDocCreateTime(new Date());
        thirdDingOrmTemp.setDocAlterTime(new Date());
        thirdDingOrmTemp.setDocCreator(UserUtil.getUser());
        thirdDingOrmTemp.setDocAlteror(UserUtil.getUser());
        ThirdDingUtil.initModelFromRequest(thirdDingOrmTemp, requestContext);
        return thirdDingOrmTemp;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingOrmTemp thirdDingOrmTemp = (ThirdDingOrmTemp) model;
    }
}
