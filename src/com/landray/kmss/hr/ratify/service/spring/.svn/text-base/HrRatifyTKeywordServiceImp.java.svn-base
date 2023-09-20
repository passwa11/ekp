package com.landray.kmss.hr.ratify.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.hr.ratify.service.IHrRatifyTKeywordService;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import java.util.List;
import com.landray.kmss.hr.ratify.model.HrRatifyTKeyword;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.common.actions.RequestContext;

public class HrRatifyTKeywordServiceImp extends ExtendDataServiceImp implements IHrRatifyTKeywordService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof HrRatifyTKeyword) {
            HrRatifyTKeyword hrRatifyTKeyword = (HrRatifyTKeyword) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrRatifyTKeyword hrRatifyTKeyword = new HrRatifyTKeyword();
        HrRatifyUtil.initModelFromRequest(hrRatifyTKeyword, requestContext);
        return hrRatifyTKeyword;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrRatifyTKeyword hrRatifyTKeyword = (HrRatifyTKeyword) model;
    }

    @Override
    public List<HrRatifyTKeyword> findByFdObject(HrRatifyTemplate fdObject) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("hrRatifyTKeyword.fdObject.fdId=:fdId");
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
