package com.landray.kmss.sys.portal.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.portal.model.SysPortalMaterialTag;
import com.landray.kmss.sys.portal.service.ISysPortalMaterialTagService;
import com.landray.kmss.sys.portal.util.SysPortalMaterialUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class SysPortalMaterialTagServiceImp extends ExtendDataServiceImp implements ISysPortalMaterialTagService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysPortalMaterialTag) {
            SysPortalMaterialTag sysPortalMaterialTag = (SysPortalMaterialTag) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysPortalMaterialTag sysPortalMaterialTag = new SysPortalMaterialTag();
		SysPortalMaterialUtil.initModelFromRequest(sysPortalMaterialTag,
				requestContext);
        return sysPortalMaterialTag;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysPortalMaterialTag sysPortalMaterialTag = (SysPortalMaterialTag) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
