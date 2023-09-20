package com.landray.kmss.third.ding.scenegroup.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingRobot;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingRobotmsgLog;
import com.landray.kmss.third.ding.scenegroup.service.IThirdDingRobotmsgLogService;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class ThirdDingRobotmsgLogServiceImp extends ExtendDataServiceImp implements IThirdDingRobotmsgLogService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingRobotmsgLog) {
            ThirdDingRobotmsgLog thirdDingRobotmsgLog = (ThirdDingRobotmsgLog) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingRobotmsgLog thirdDingRobotmsgLog = new ThirdDingRobotmsgLog();
        ThirdDingUtil.initModelFromRequest(thirdDingRobotmsgLog, requestContext);
        return thirdDingRobotmsgLog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingRobotmsgLog thirdDingRobotmsgLog = (ThirdDingRobotmsgLog) model;
    }

    @Override
    public List<ThirdDingRobotmsgLog> findByFdRobot(ThirdDingRobot fdRobot) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("thirdDingRobotmsgLog.fdRobot.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdRobot.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

}
