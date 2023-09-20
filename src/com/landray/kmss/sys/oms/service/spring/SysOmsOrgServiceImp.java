package com.landray.kmss.sys.oms.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.oms.dao.ISysOmsOrgDao;
import com.landray.kmss.sys.oms.model.SysOmsOrg;
import com.landray.kmss.sys.oms.service.ISysOmsOrgService;
import com.landray.kmss.sys.oms.util.SysOmsUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class SysOmsOrgServiceImp extends ExtendDataServiceImp implements ISysOmsOrgService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysOmsOrg) {
            SysOmsOrg sysOmsOrg = (SysOmsOrg) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysOmsOrg sysOmsOrg = new SysOmsOrg();
        sysOmsOrg.setFdIsAvailable(Boolean.valueOf("true"));
        sysOmsOrg.setFdIsBusiness(Boolean.valueOf("true"));
        SysOmsUtil.initModelFromRequest(sysOmsOrg, requestContext);
        return sysOmsOrg;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysOmsOrg sysOmsOrg = (SysOmsOrg) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public void deleteHandledOrg() throws Exception {
		((ISysOmsOrgDao) getBaseDao()).deleteHandledOrg();
	}
}
