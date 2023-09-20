package com.landray.kmss.hr.organization.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.organization.model.HrOrganizationDept;
import com.landray.kmss.hr.organization.service.IHrOrganizationDeptService;
import com.landray.kmss.hr.organization.util.HrOrganizationUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.SpringBeanUtil;

public class HrOrganizationDeptServiceImp extends HrOrganizationElementServiceImp
		implements IHrOrganizationDeptService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof HrOrganizationDept) {
            HrOrganizationDept hrOrganizationDept = (HrOrganizationDept) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrOrganizationDept hrOrganizationDept = new HrOrganizationDept();
        HrOrganizationUtil.initModelFromRequest(hrOrganizationDept, requestContext);
        return hrOrganizationDept;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrOrganizationDept hrOrganizationDept = (HrOrganizationDept) model;
    }

    @Override
    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
