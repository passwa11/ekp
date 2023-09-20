package com.landray.kmss.sys.oms.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.oms.dao.ISysOmsDeptDao;
import com.landray.kmss.sys.oms.model.SysOmsDept;
import com.landray.kmss.sys.oms.service.ISysOmsDeptService;
import com.landray.kmss.sys.oms.util.SysOmsUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class SysOmsDeptServiceImp extends ExtendDataServiceImp implements ISysOmsDeptService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysOmsDept) {
            SysOmsDept sysOmsDept = (SysOmsDept) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysOmsDept sysOmsDept = new SysOmsDept();
        sysOmsDept.setFdIsAvailable(Boolean.valueOf("true"));
        sysOmsDept.setFdIsBusiness(Boolean.valueOf("true"));
        SysOmsUtil.initModelFromRequest(sysOmsDept, requestContext);
        return sysOmsDept;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysOmsDept sysOmsDept = (SysOmsDept) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public void deleteHandledOrg() throws Exception {
		((ISysOmsDeptDao) getBaseDao()).deleteHandledOrg();
	}
}
