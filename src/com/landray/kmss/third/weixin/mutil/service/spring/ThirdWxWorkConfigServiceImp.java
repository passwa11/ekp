package com.landray.kmss.third.weixin.mutil.service.spring;

import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.weixin.mutil.dao.IThirdWxWorkConfigDao;
import com.landray.kmss.third.weixin.mutil.model.ThirdWxWorkMutilConfig;
import com.landray.kmss.third.weixin.mutil.service.IThirdWxWorkConfigService;
import com.landray.kmss.third.weixin.mutil.util.ThirdWxUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class ThirdWxWorkConfigServiceImp extends ExtendDataServiceImp implements IThirdWxWorkConfigService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
		if (model instanceof ThirdWxWorkMutilConfig) {
			ThirdWxWorkMutilConfig thirdWxWorkConfig = (ThirdWxWorkMutilConfig) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		ThirdWxWorkMutilConfig thirdWxWorkConfig = new ThirdWxWorkMutilConfig();
        ThirdWxUtil.initModelFromRequest(thirdWxWorkConfig, requestContext);
        return thirdWxWorkConfig;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
		ThirdWxWorkMutilConfig thirdWxWorkConfig = (ThirdWxWorkMutilConfig) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public void save(String key, Map fieldValues) throws Exception {
		((IThirdWxWorkConfigDao) this.getBaseDao()).save(key, fieldValues);
	}
}
