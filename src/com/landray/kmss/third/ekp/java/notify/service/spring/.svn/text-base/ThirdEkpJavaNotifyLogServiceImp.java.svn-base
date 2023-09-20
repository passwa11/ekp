package com.landray.kmss.third.ekp.java.notify.service.spring;

import java.util.Date;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.ekp.java.notify.dao.IThirdEkpJavaNotifyLogDao;
import com.landray.kmss.third.ekp.java.notify.model.ThirdEkpJavaNotifyLog;
import com.landray.kmss.third.ekp.java.notify.service.IThirdEkpJavaNotifyLogService;
import com.landray.kmss.third.ekp.java.notify.service.IThirdEkpJavaNotifyMappService;
import com.landray.kmss.third.ekp.java.notify.util.ThirdEkpJavaNotifyUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class ThirdEkpJavaNotifyLogServiceImp extends ExtendDataServiceImp implements IThirdEkpJavaNotifyLogService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdEkpJavaNotifyLog) {
            ThirdEkpJavaNotifyLog thirdEkpJavaNotifyLog = (ThirdEkpJavaNotifyLog) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdEkpJavaNotifyLog thirdEkpJavaNotifyLog = new ThirdEkpJavaNotifyLog();
        thirdEkpJavaNotifyLog.setDocCreateTime(new Date());
		ThirdEkpJavaNotifyUtil.initModelFromRequest(thirdEkpJavaNotifyLog,
				requestContext);
        return thirdEkpJavaNotifyLog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdEkpJavaNotifyLog thirdEkpJavaNotifyLog = (ThirdEkpJavaNotifyLog) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public void clear(int days) throws Exception {
		((IThirdEkpJavaNotifyLogDao) getBaseDao()).clear(60);

        IThirdEkpJavaNotifyMappService thirdEkpJavaNotifyMappService = (IThirdEkpJavaNotifyMappService)SpringBeanUtil.getBean("thirdEkpJavaNotifyMappService");
        thirdEkpJavaNotifyMappService.cleanFinishedNotifyMapp();
	}
}
