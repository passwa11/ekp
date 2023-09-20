package com.landray.kmss.hr.organization.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.organization.model.HrOrganizationLog;
import com.landray.kmss.hr.organization.service.IHrOrganizationLogService;
import com.landray.kmss.hr.organization.util.HrOrganizationUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.SpringBeanUtil;

public class HrOrganizationLogServiceImp extends ExtendDataServiceImp implements IHrOrganizationLogService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof HrOrganizationLog) {
            HrOrganizationLog hrOrganizationLog = (HrOrganizationLog) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrOrganizationLog hrOrganizationLog = new HrOrganizationLog();
        HrOrganizationUtil.initModelFromRequest(hrOrganizationLog, requestContext);
        return hrOrganizationLog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrOrganizationLog hrOrganizationLog = (HrOrganizationLog) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public List<HrOrganizationLog> findLogByOrgId(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdTargetId =:fdId");
		hqlInfo.setParameter("fdId", fdId);
		return this.findList(hqlInfo);
	}
}
