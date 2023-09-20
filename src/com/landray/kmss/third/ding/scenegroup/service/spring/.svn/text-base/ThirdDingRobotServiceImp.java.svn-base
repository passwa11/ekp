package com.landray.kmss.third.ding.scenegroup.service.spring;

import java.util.Date;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingRobot;
import com.landray.kmss.third.ding.scenegroup.service.IThirdDingRobotService;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

public class ThirdDingRobotServiceImp extends ExtendDataServiceImp implements IThirdDingRobotService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingRobot) {
            ThirdDingRobot thirdDingRobot = (ThirdDingRobot) model;
            thirdDingRobot.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingRobot thirdDingRobot = new ThirdDingRobot();
        thirdDingRobot.setFdIsAvailable(Boolean.valueOf("true"));
        thirdDingRobot.setDocCreateTime(new Date());
        thirdDingRobot.setDocAlterTime(new Date());
        thirdDingRobot.setDocCreator(UserUtil.getUser());
        ThirdDingUtil.initModelFromRequest(thirdDingRobot, requestContext);
        return thirdDingRobot;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingRobot thirdDingRobot = (ThirdDingRobot) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
