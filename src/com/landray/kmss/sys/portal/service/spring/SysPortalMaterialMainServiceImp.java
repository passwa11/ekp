package com.landray.kmss.sys.portal.service.spring;

import java.util.Date;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.portal.model.SysPortalMaterialMain;
import com.landray.kmss.sys.portal.service.ISysPortalMaterialMainService;
import com.landray.kmss.sys.portal.util.SysPortalMaterialUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

public class SysPortalMaterialMainServiceImp extends ExtendDataServiceImp implements ISysPortalMaterialMainService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysPortalMaterialMain) {
            SysPortalMaterialMain sysPortalMaterialMain = (SysPortalMaterialMain) model;
            sysPortalMaterialMain.setDocAlterTime(new Date());
            sysPortalMaterialMain.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysPortalMaterialMain sysPortalMaterialMain = new SysPortalMaterialMain();
        sysPortalMaterialMain.setDocCreateTime(new Date());
        sysPortalMaterialMain.setDocAlterTime(new Date());
        sysPortalMaterialMain.setDocCreator(UserUtil.getUser());
        sysPortalMaterialMain.setDocAlteror(UserUtil.getUser());
		SysPortalMaterialUtil.initModelFromRequest(sysPortalMaterialMain,
				requestContext);
        return sysPortalMaterialMain;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysPortalMaterialMain sysPortalMaterialMain = (SysPortalMaterialMain) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
