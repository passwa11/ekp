package com.landray.kmss.sys.oms.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.oms.dao.ISysOmsGroupDao;
import com.landray.kmss.sys.oms.model.SysOmsGroup;
import com.landray.kmss.sys.oms.service.ISysOmsGroupService;
import com.landray.kmss.sys.oms.util.SysOmsUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class SysOmsGroupServiceImp extends ExtendDataServiceImp implements ISysOmsGroupService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysOmsGroup) {
            SysOmsGroup sysOmsGroup = (SysOmsGroup) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysOmsGroup sysOmsGroup = new SysOmsGroup();
        sysOmsGroup.setFdIsAvailable(Boolean.valueOf("true"));
        sysOmsGroup.setFdIsBusiness(Boolean.valueOf("true"));
        SysOmsUtil.initModelFromRequest(sysOmsGroup, requestContext);
        return sysOmsGroup;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysOmsGroup sysOmsGroup = (SysOmsGroup) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public void deleteHandledOrg() throws Exception {
		((ISysOmsGroupDao) getBaseDao()).deleteHandledOrg();
	}
}
