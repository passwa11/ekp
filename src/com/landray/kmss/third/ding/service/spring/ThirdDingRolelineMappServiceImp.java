package com.landray.kmss.third.ding.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.ding.model.ThirdDingRolelineMapp;
import com.landray.kmss.third.ding.service.IThirdDingRolelineMappService;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class ThirdDingRolelineMappServiceImp extends ExtendDataServiceImp implements IThirdDingRolelineMappService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingRolelineMapp) {
            ThirdDingRolelineMapp thirdDingRolelineMapp = (ThirdDingRolelineMapp) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingRolelineMapp thirdDingRolelineMapp = new ThirdDingRolelineMapp();
        ThirdDingUtil.initModelFromRequest(thirdDingRolelineMapp, requestContext);
        return thirdDingRolelineMapp;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingRolelineMapp thirdDingRolelineMapp = (ThirdDingRolelineMapp) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public ThirdDingRolelineMapp findByDingRole(String dingRoleId)
			throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock(
				"fdDingRoleId=:dingRoleId");
		info.setParameter("dingRoleId", dingRoleId);
		return (ThirdDingRolelineMapp) findFirstOne(info);
	}
}
