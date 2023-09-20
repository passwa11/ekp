package com.landray.kmss.third.ding.scenegroup.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingGroupmsgLog;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingScenegroupMapp;
import com.landray.kmss.third.ding.scenegroup.service.IThirdDingGroupmsgLogService;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class ThirdDingGroupmsgLogServiceImp extends ExtendDataServiceImp implements IThirdDingGroupmsgLogService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingGroupmsgLog) {
            ThirdDingGroupmsgLog thirdDingGroupmsgLog = (ThirdDingGroupmsgLog) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingGroupmsgLog thirdDingGroupmsgLog = new ThirdDingGroupmsgLog();
        ThirdDingUtil.initModelFromRequest(thirdDingGroupmsgLog, requestContext);
        return thirdDingGroupmsgLog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingGroupmsgLog thirdDingGroupmsgLog = (ThirdDingGroupmsgLog) model;
    }

    @Override
    public List<ThirdDingGroupmsgLog> findByFdGroup(ThirdDingScenegroupMapp fdGroup) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("thirdDingGroupmsgLog.fdGroup.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdGroup.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
