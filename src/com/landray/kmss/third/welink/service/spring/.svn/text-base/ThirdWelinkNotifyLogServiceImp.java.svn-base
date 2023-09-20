package com.landray.kmss.third.welink.service.spring;

import java.util.Date;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.welink.dao.IThirdWelinkNotifyLogDao;
import com.landray.kmss.third.welink.model.ThirdWelinkNotifyLog;
import com.landray.kmss.third.welink.service.IThirdWelinkNotifyLogService;
import com.landray.kmss.third.welink.util.ThirdWelinkUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class ThirdWelinkNotifyLogServiceImp extends ExtendDataServiceImp implements IThirdWelinkNotifyLogService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWelinkNotifyLog) {
            ThirdWelinkNotifyLog thirdWelinkNotifyLog = (ThirdWelinkNotifyLog) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWelinkNotifyLog thirdWelinkNotifyLog = new ThirdWelinkNotifyLog();
        thirdWelinkNotifyLog.setDocCreateTime(new Date());
        ThirdWelinkUtil.initModelFromRequest(thirdWelinkNotifyLog, requestContext);
        return thirdWelinkNotifyLog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWelinkNotifyLog thirdWelinkNotifyLog = (ThirdWelinkNotifyLog) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public void clear(int days) throws Exception {
		((IThirdWelinkNotifyLogDao) getBaseDao()).clear(30);
	}

}
