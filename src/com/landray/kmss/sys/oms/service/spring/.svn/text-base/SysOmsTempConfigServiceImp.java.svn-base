package com.landray.kmss.sys.oms.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.oms.service.ISysOmsTempConfigService;
import com.landray.kmss.sys.oms.util.SysOmsUtil;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.sys.oms.model.SysOmsTempConfig;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;

public class SysOmsTempConfigServiceImp extends ExtendDataServiceImp implements ISysOmsTempConfigService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysOmsTempConfig) {
            SysOmsTempConfig sysOmsTempConfig = (SysOmsTempConfig) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysOmsTempConfig sysOmsTempConfig = new SysOmsTempConfig();
        sysOmsTempConfig.setFdSynStatus(Integer.valueOf("0"));
        SysOmsUtil.initModelFromRequest(sysOmsTempConfig, requestContext);
        return sysOmsTempConfig;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysOmsTempConfig sysOmsTempConfig = (SysOmsTempConfig) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
