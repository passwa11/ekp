package com.landray.kmss.third.welink.service.spring;

import java.util.Date;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.welink.model.ThirdWelinkPersonNoMapp;
import com.landray.kmss.third.welink.service.IThirdWelinkPersonNoMappService;
import com.landray.kmss.third.welink.util.ThirdWelinkUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class ThirdWelinkPersonNoMappServiceImp extends ExtendDataServiceImp implements IThirdWelinkPersonNoMappService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWelinkPersonNoMapp) {
            ThirdWelinkPersonNoMapp thirdWelinkPersonNoMapp = (ThirdWelinkPersonNoMapp) model;
            thirdWelinkPersonNoMapp.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWelinkPersonNoMapp thirdWelinkPersonNoMapp = new ThirdWelinkPersonNoMapp();
        thirdWelinkPersonNoMapp.setDocAlterTime(new Date());
        ThirdWelinkUtil.initModelFromRequest(thirdWelinkPersonNoMapp, requestContext);
        return thirdWelinkPersonNoMapp;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWelinkPersonNoMapp thirdWelinkPersonNoMapp = (ThirdWelinkPersonNoMapp) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
    public void addNoMapping(String welinkId, String welinkName,
                             String welinkMoblieNo, String welinkEmail) throws Exception {
		ThirdWelinkPersonNoMapp thirdWelinkPersonNoMapp = new ThirdWelinkPersonNoMapp();
		thirdWelinkPersonNoMapp.setDocAlterTime(new Date());
		thirdWelinkPersonNoMapp.setFdWelinkId(welinkId);
		thirdWelinkPersonNoMapp.setFdWelinkName(welinkName);
		thirdWelinkPersonNoMapp.setFdWelinkEmail(welinkEmail);
		thirdWelinkPersonNoMapp.setFdWelinkMobileNo(welinkMoblieNo);
		this.add(thirdWelinkPersonNoMapp);
	}
}
