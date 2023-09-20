package com.landray.kmss.sys.oms.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.oms.model.SysOmsTempDp;
import com.landray.kmss.sys.oms.model.SysOmsTempPp;
import com.landray.kmss.sys.oms.service.ISysOmsTempPpService;
import com.landray.kmss.sys.oms.temp.SysOmsTempConstants;
import com.landray.kmss.sys.oms.util.SysOmsUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class SysOmsTempPpServiceImp extends ExtendDataServiceImp implements ISysOmsTempPpService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysOmsTempPp) {
            SysOmsTempPp sysOmsTempPp = (SysOmsTempPp) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysOmsTempPp sysOmsTempPp = new SysOmsTempPp();
        SysOmsUtil.initModelFromRequest(sysOmsTempPp, requestContext);
        return sysOmsTempPp;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysOmsTempPp sysOmsTempPp = (SysOmsTempPp) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public List<SysOmsTempPp> findListByTrxId(String fdTrxId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdTrxId=:fdTrxId");
		hqlInfo.setParameter("fdTrxId",fdTrxId);
		return findList(hqlInfo);
	}

	@Override
	public List<SysOmsTempDp> findFailListByTrxId(String fdTrxId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdTrxId=:fdTrxId and fdStatus=:fdStatus");
		hqlInfo.setParameter("fdTrxId",fdTrxId);
		hqlInfo.setParameter("fdStatus", SysOmsTempConstants.SYS_OMS_TEMP_DATA_SYN_STATUS_FAIL);
		return findList(hqlInfo);
	}
}
