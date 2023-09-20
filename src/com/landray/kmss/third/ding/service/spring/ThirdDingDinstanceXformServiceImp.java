package com.landray.kmss.third.ding.service.spring;

import java.util.List;
import java.util.Date;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.ding.model.ThirdDingDinstanceXform;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.model.ThirdDingDtemplateXform;
import com.landray.kmss.third.ding.service.IThirdDingDinstanceXformService;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.common.convertor.ConvertorContext;

public class ThirdDingDinstanceXformServiceImp extends ExtendDataServiceImp implements IThirdDingDinstanceXformService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingDinstanceXform) {
            ThirdDingDinstanceXform thirdDingDinstanceXform = (ThirdDingDinstanceXform) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingDinstanceXform thirdDingDinstanceXform = new ThirdDingDinstanceXform();
        thirdDingDinstanceXform.setDocCreateTime(new Date());
        ThirdDingUtil.initModelFromRequest(thirdDingDinstanceXform, requestContext);
        return thirdDingDinstanceXform;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingDinstanceXform thirdDingDinstanceXform = (ThirdDingDinstanceXform) model;
    }

    @Override
    public List<ThirdDingDinstanceXform> findByFdTemplate(ThirdDingDtemplateXform fdTemplate) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("thirdDingDinstanceXform.fdTemplate.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdTemplate.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
