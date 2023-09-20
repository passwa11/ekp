package com.landray.kmss.third.ding.service.spring;

import com.landray.kmss.third.ding.dao.IThirdDingOmsErrorDao;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.model.ThirdDingOmsError;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.third.ding.service.IThirdDingOmsErrorService;

public class ThirdDingOmsErrorServiceImp extends ExtendDataServiceImp implements IThirdDingOmsErrorService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingOmsError) {
            ThirdDingOmsError thirdDingOmsError = (ThirdDingOmsError) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingOmsError thirdDingOmsError = new ThirdDingOmsError();
        ThirdDingUtil.initModelFromRequest(thirdDingOmsError, requestContext);
        return thirdDingOmsError;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingOmsError thirdDingOmsError = (ThirdDingOmsError) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    @Override
    public int deleteEkpRecord() throws Exception {
        return ((IThirdDingOmsErrorDao)getBaseDao()).deleteEkpRecord();
    }
}
