package com.landray.kmss.third.ding.service.spring;

import java.util.Date;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.ding.dao.IThirdDingNotifylogDao;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingNotifylog;
import com.landray.kmss.third.ding.service.IThirdDingNotifylogService;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class ThirdDingNotifylogServiceImp extends ExtendDataServiceImp implements IThirdDingNotifylogService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingNotifylog) {
            ThirdDingNotifylog thirdDingNotifylog = (ThirdDingNotifylog) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingNotifylog thirdDingNotifylog = new ThirdDingNotifylog();
        thirdDingNotifylog.setFdSendTime(new Date());
        thirdDingNotifylog.setFdRtnTime(new Date());
        ThirdDingUtil.initModelFromRequest(thirdDingNotifylog, requestContext);
        return thirdDingNotifylog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingNotifylog thirdDingNotifylog = (ThirdDingNotifylog) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
    public void clean(SysQuartzJobContext context) throws Exception {
		String logDays = DingConfig.newInstance().getDingLogDays();
		((IThirdDingNotifylogDao) getBaseDao()).clean(context,logDays);
	}
}
