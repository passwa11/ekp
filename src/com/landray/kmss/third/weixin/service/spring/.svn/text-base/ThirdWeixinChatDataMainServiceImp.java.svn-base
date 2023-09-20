package com.landray.kmss.third.weixin.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.weixin.dao.IThirdWeixinChatDataMainDao;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;
import com.landray.kmss.third.weixin.service.IThirdWeixinChatDataMainService;
import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.util.SpringBeanUtil;

import java.util.Date;

public class ThirdWeixinChatDataMainServiceImp extends ExtendDataServiceImp implements IThirdWeixinChatDataMainService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinChatDataMain) {
            ThirdWeixinChatDataMain thirdWeixinChatDataMain = (ThirdWeixinChatDataMain) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinChatDataMain thirdWeixinChatDataMain = new ThirdWeixinChatDataMain();
        thirdWeixinChatDataMain.setFdEncryType(Integer.valueOf("1"));
        thirdWeixinChatDataMain.setDocCreateTime(new Date());
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinChatDataMain, requestContext);
        return thirdWeixinChatDataMain;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinChatDataMain thirdWeixinChatDataMain = (ThirdWeixinChatDataMain) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    @Override
    public ThirdWeixinChatDataMain findByMsgid(String msgid) throws Exception {
        HQLInfo info = new HQLInfo();
        info.setWhereBlock("fdMsgId=:msgid");
        info.setParameter("msgid",msgid);
        return (ThirdWeixinChatDataMain) findFirstOne(info);
    }

    @Override
    public void backUp() throws Exception {
        ((IThirdWeixinChatDataMainDao)getBaseDao()).backUp();
    }
}
