package com.landray.kmss.third.ding.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.ding.model.ThirdDingRoleCateMapp;
import com.landray.kmss.third.ding.service.IThirdDingRoleCateMappService;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;

import java.util.List;

public class ThirdDingRoleCateMappServiceImp extends ExtendDataServiceImp implements IThirdDingRoleCateMappService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingRoleCateMapp) {
            ThirdDingRoleCateMapp thirdDingRoleCateMapp = (ThirdDingRoleCateMapp) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingRoleCateMapp thirdDingRoleCateMapp = new ThirdDingRoleCateMapp();
        ThirdDingUtil.initModelFromRequest(thirdDingRoleCateMapp, requestContext);
        return thirdDingRoleCateMapp;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingRoleCateMapp thirdDingRoleCateMapp = (ThirdDingRoleCateMapp) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public void deleteByCateId(String cateId) throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdEkpCateId=:cateId");
		info.setParameter("cateId", cateId);
		List<ThirdDingRoleCateMapp> list = findList(info);
		if (list != null) {
			for (ThirdDingRoleCateMapp mapp : list) {
				delete(mapp);
			}
		}

	}

	@Override
	public ThirdDingRoleCateMapp findByGroupId(String groupId)
			throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdGroupId=:groupId");
		info.setParameter("groupId", groupId);
		return (ThirdDingRoleCateMapp) findFirstOne(info);
	}
}
