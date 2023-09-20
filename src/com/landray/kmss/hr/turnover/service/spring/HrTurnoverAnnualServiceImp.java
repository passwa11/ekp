package com.landray.kmss.hr.turnover.service.spring;

import java.util.Date;
import com.landray.kmss.hr.turnover.util.HrTurnoverUtil;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.turnover.service.IHrTurnoverAnnualService;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.hr.turnover.model.HrTurnoverAnnual;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.common.forms.IExtendForm;

/**
  * 年度离职率目标值 服务实现
  */
public class HrTurnoverAnnualServiceImp extends ExtendDataServiceImp implements IHrTurnoverAnnualService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof HrTurnoverAnnual) {
            HrTurnoverAnnual hrTurnoverAnnual = (HrTurnoverAnnual) model;
        }
        return model;
    }

    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrTurnoverAnnual hrTurnoverAnnual = new HrTurnoverAnnual();
        hrTurnoverAnnual.setDocCreateTime(new Date());
        hrTurnoverAnnual.setDocCreator(UserUtil.getUser());
        HrTurnoverUtil.initModelFromRequest(hrTurnoverAnnual, requestContext);
        return hrTurnoverAnnual;
    }

    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrTurnoverAnnual hrTurnoverAnnual = (HrTurnoverAnnual) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
