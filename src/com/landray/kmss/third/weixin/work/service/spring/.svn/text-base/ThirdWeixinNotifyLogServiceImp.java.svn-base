package com.landray.kmss.third.weixin.work.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.weixin.work.dao.IThirdWeixinNotifyLogDao;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyLog;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinNotifyLogService;
import com.landray.kmss.third.weixin.work.util.ThirdWeixinUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class ThirdWeixinNotifyLogServiceImp extends ExtendDataServiceImp implements IThirdWeixinNotifyLogService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinNotifyLog) {
            ThirdWeixinNotifyLog thirdWeixinNotifyLog = (ThirdWeixinNotifyLog) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinNotifyLog thirdWeixinNotifyLog = new ThirdWeixinNotifyLog();
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinNotifyLog, requestContext);
        return thirdWeixinNotifyLog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinNotifyLog thirdWeixinNotifyLog = (ThirdWeixinNotifyLog) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public void clear(int days) throws Exception {
		((IThirdWeixinNotifyLogDao) getBaseDao()).clear(60);
	}
}
