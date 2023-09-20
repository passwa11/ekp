package com.landray.kmss.third.im.kk.service.spring;

import com.landray.kmss.third.im.kk.util.ThirdImUtil;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.im.kk.service.IKkUserService;
import com.landray.kmss.third.im.kk.service.IThirdImLoginService;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.third.im.kk.model.QRCode;
import com.landray.kmss.third.im.kk.model.ThirdImLogin;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.util.SpringBeanUtil;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;

public class ThirdImLoginServiceImp extends ExtendDataServiceImp implements IThirdImLoginService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdImLogin) {
            ThirdImLogin thirdImLogin = (ThirdImLogin) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdImLogin thirdImLogin = new ThirdImLogin();
        ThirdImUtil.initModelFromRequest(thirdImLogin, requestContext);
        return thirdImLogin;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdImLogin thirdImLogin = (ThirdImLogin) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	
}
