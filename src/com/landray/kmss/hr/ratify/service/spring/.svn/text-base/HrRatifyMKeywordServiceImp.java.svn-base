package com.landray.kmss.hr.ratify.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.hr.ratify.service.IHrRatifyMKeywordService;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import java.util.List;
import com.landray.kmss.hr.ratify.model.HrRatifyMKeyword;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.common.actions.RequestContext;

public class HrRatifyMKeywordServiceImp extends ExtendDataServiceImp implements IHrRatifyMKeywordService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof HrRatifyMKeyword) {
            HrRatifyMKeyword hrRatifyMKeyword = (HrRatifyMKeyword) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrRatifyMKeyword hrRatifyMKeyword = new HrRatifyMKeyword();
        HrRatifyUtil.initModelFromRequest(hrRatifyMKeyword, requestContext);
        return hrRatifyMKeyword;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrRatifyMKeyword hrRatifyMKeyword = (HrRatifyMKeyword) model;
    }

    @Override
    public List<HrRatifyMKeyword> findByFdObject(HrRatifyMain fdObject) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("hrRatifyMKeyword.fdObject.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdObject.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
