package com.landray.kmss.third.welink.service.spring;

import java.util.Date;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.welink.model.ThirdWelinkDeptNoMapping;
import com.landray.kmss.third.welink.service.IThirdWelinkDeptNoMappingService;
import com.landray.kmss.third.welink.util.ThirdWelinkUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class ThirdWelinkDeptNoMappingServiceImp extends ExtendDataServiceImp implements IThirdWelinkDeptNoMappingService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWelinkDeptNoMapping) {
            ThirdWelinkDeptNoMapping thirdWelinkDeptNoMapping = (ThirdWelinkDeptNoMapping) model;
            thirdWelinkDeptNoMapping.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWelinkDeptNoMapping thirdWelinkDeptNoMapping = new ThirdWelinkDeptNoMapping();
        thirdWelinkDeptNoMapping.setDocAlterTime(new Date());
        ThirdWelinkUtil.initModelFromRequest(thirdWelinkDeptNoMapping, requestContext);
        return thirdWelinkDeptNoMapping;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWelinkDeptNoMapping thirdWelinkDeptNoMapping = (ThirdWelinkDeptNoMapping) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
    public void addNoMapping(String welinkId, String welinkName,
                             String welinkPath) throws Exception {
		ThirdWelinkDeptNoMapping thirdWelinkDeptNoMapping = new ThirdWelinkDeptNoMapping();
		thirdWelinkDeptNoMapping.setDocAlterTime(new Date());
		thirdWelinkDeptNoMapping.setFdWelinkId(welinkId);
		thirdWelinkDeptNoMapping.setFdWelinkName(welinkName);
		thirdWelinkDeptNoMapping.setFdWelinkPath(welinkPath);
		this.add(thirdWelinkDeptNoMapping);
	}
}
