package com.landray.kmss.third.ekp.java.notify.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.ekp.java.notify.dao.IThirdEkpJavaNotifyMappDao;
import com.landray.kmss.third.ekp.java.notify.model.ThirdEkpJavaNotifyMapp;
import com.landray.kmss.third.ekp.java.notify.service.IThirdEkpJavaNotifyMappService;
import com.landray.kmss.third.ekp.java.notify.util.ThirdEkpJavaNotifyUtil;
import com.landray.kmss.util.SpringBeanUtil;

import java.util.Date;

public class ThirdEkpJavaNotifyMappServiceImp extends ExtendDataServiceImp implements IThirdEkpJavaNotifyMappService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdEkpJavaNotifyMapp) {
            ThirdEkpJavaNotifyMapp thirdEkpJavaNotifyMapp = (ThirdEkpJavaNotifyMapp) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdEkpJavaNotifyMapp thirdEkpJavaNotifyMapp = new ThirdEkpJavaNotifyMapp();
        thirdEkpJavaNotifyMapp.setDocCreateTime(new Date());
		ThirdEkpJavaNotifyUtil.initModelFromRequest(thirdEkpJavaNotifyMapp,
				requestContext);
        return thirdEkpJavaNotifyMapp;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdEkpJavaNotifyMapp thirdEkpJavaNotifyMapp = (ThirdEkpJavaNotifyMapp) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
    public ThirdEkpJavaNotifyMapp findByNotifyId(String notifyId)
			throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdNotifyId=:notifyId");
		info.setParameter("notifyId", notifyId);
		return (ThirdEkpJavaNotifyMapp) this.findFirstOne(info);
	}

    @Override
    public void cleanFinishedNotifyMapp()
            throws Exception {
        ((IThirdEkpJavaNotifyMappDao)getBaseDao()).cleanFinishedNotifyMapp(30);
    }
}
