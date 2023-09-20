package com.landray.kmss.hrext.staff.service.spring;

import java.util.Date;
import com.landray.kmss.hrext.staff.util.HrextStaffUtil;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hrext.staff.service.IHrextStaffExpandService;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.hrext.staff.model.HrextStaffExpand;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.common.forms.IExtendForm;

/**
  * 流程人事同步 服务实现
  */
public class HrextStaffExpandServiceImp extends ExtendDataServiceImp implements IHrextStaffExpandService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof HrextStaffExpand) {
            HrextStaffExpand hrextStaffExpand = (HrextStaffExpand) model;
        }
        return model;
    }

    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrextStaffExpand hrextStaffExpand = new HrextStaffExpand();
        hrextStaffExpand.setFdIsAvailable(Boolean.valueOf("true"));
        hrextStaffExpand.setDocCreateTime(new Date());
        hrextStaffExpand.setDocCreator(UserUtil.getUser());
        HrextStaffUtil.initModelFromRequest(hrextStaffExpand, requestContext);
        return hrextStaffExpand;
    }

    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrextStaffExpand hrextStaffExpand = (HrextStaffExpand) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
