package com.landray.kmss.sys.oms.service.spring;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.oms.model.SysOmsTempTrx;
import com.landray.kmss.sys.oms.service.ISysOmsTempTrxNewService;
import com.landray.kmss.sys.oms.temp.ISysOmsThreadSynchService;
import com.landray.kmss.sys.oms.temp.SysOmsTempConstants;
import com.landray.kmss.sys.oms.util.SysOmsUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class SysOmsTempTrxNewServiceImp extends SysOmsTempTrxBaseServiceImp implements ISysOmsTempTrxNewService,
ISysOmsThreadSynchService,SysOrgConstant ,SysOmsTempConstants{
	private Logger logger = org.slf4j.LoggerFactory.getLogger(SysOmsTempTrxNewServiceImp.class);
    private ISysNotifyMainCoreService sysNotifyMainCoreService;
    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysOmsTempTrx) {
            SysOmsTempTrx sysOmsTempTrx = (SysOmsTempTrx) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysOmsTempTrx sysOmsTempTrx = new SysOmsTempTrx();
        sysOmsTempTrx.setBeginTime(new Date());
        SysOmsUtil.initModelFromRequest(sysOmsTempTrx, requestContext);
        return sysOmsTempTrx;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysOmsTempTrx sysOmsTempTrx = (SysOmsTempTrx) model;
    }

    @Override
    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	

	
}
