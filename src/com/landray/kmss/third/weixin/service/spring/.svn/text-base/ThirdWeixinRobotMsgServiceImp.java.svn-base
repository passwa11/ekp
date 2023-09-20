package com.landray.kmss.third.weixin.service.spring;

import java.util.Date;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.third.weixin.service.IThirdWeixinRobotMsgService;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.model.ThirdWeixinRobotMsg;
import com.landray.kmss.common.model.IBaseModel;

public class ThirdWeixinRobotMsgServiceImp extends ExtendDataServiceImp implements IThirdWeixinRobotMsgService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinRobotMsg) {
            ThirdWeixinRobotMsg thirdWeixinRobotMsg = (ThirdWeixinRobotMsg) model;
            thirdWeixinRobotMsg.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinRobotMsg thirdWeixinRobotMsg = new ThirdWeixinRobotMsg();
        thirdWeixinRobotMsg.setDocCreateTime(new Date());
        thirdWeixinRobotMsg.setDocAlterTime(new Date());
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinRobotMsg, requestContext);
        return thirdWeixinRobotMsg;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinRobotMsg thirdWeixinRobotMsg = (ThirdWeixinRobotMsg) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
