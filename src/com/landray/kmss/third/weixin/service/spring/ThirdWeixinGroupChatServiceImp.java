package com.landray.kmss.third.weixin.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.weixin.model.ThirdWeixinGroupChat;
import com.landray.kmss.third.weixin.service.IThirdWeixinGroupChatService;
import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.util.SpringBeanUtil;

import java.util.Date;

public class ThirdWeixinGroupChatServiceImp extends ExtendDataServiceImp implements IThirdWeixinGroupChatService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinGroupChat) {
            ThirdWeixinGroupChat thirdWeixinGroupChat = (ThirdWeixinGroupChat) model;
            thirdWeixinGroupChat.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinGroupChat thirdWeixinGroupChat = new ThirdWeixinGroupChat();
        thirdWeixinGroupChat.setDocCreateTime(new Date());
        thirdWeixinGroupChat.setDocAlterTime(new Date());
        thirdWeixinGroupChat.setFdIsDissolve(Boolean.valueOf("false"));
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinGroupChat, requestContext);
        return thirdWeixinGroupChat;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinGroupChat thirdWeixinGroupChat = (ThirdWeixinGroupChat) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    @Override
    public ThirdWeixinGroupChat findByRoomId(String roomid) throws Exception {
        HQLInfo info = new HQLInfo();
        info.setWhereBlock("fdRoomId=:roomid");
        info.setParameter("roomid",roomid);
        return (ThirdWeixinGroupChat) findFirstOne(info);
    }
}
