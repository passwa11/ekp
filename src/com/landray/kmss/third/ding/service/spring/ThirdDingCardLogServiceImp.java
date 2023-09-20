package com.landray.kmss.third.ding.service.spring;

import java.util.Date;

import com.landray.kmss.third.ding.dao.IThirdDingCallbackLogDao;
import com.landray.kmss.third.ding.dao.IThirdDingCardLogDao;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.third.ding.service.IThirdDingCardLogService;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.ding.model.ThirdDingCardLog;
import com.landray.kmss.common.model.IBaseModel;

public class ThirdDingCardLogServiceImp extends ExtendDataServiceImp implements IThirdDingCardLogService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingCardLog) {
            ThirdDingCardLog thirdDingCardLog = (ThirdDingCardLog) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingCardLog thirdDingCardLog = new ThirdDingCardLog();
        thirdDingCardLog.setDocCreateTime(new Date());
        thirdDingCardLog.setFdStatus(Boolean.valueOf("true"));
        ThirdDingUtil.initModelFromRequest(thirdDingCardLog, requestContext);
        return thirdDingCardLog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingCardLog thirdDingCardLog = (ThirdDingCardLog) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    @Override
    public void clear(int days) throws Exception {
        ((IThirdDingCardLogDao) getBaseDao()).clear(days);
    }
}
