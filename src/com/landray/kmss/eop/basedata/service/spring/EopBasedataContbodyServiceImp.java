package com.landray.kmss.eop.basedata.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

public class EopBasedataContbodyServiceImp extends ExtendDataServiceImp implements com.landray.kmss.eop.basedata.service.IEopBasedataContbodyService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof com.landray.kmss.eop.basedata.model.EopBasedataContbody) {
            com.landray.kmss.eop.basedata.model.EopBasedataContbody eopBasedataContbody = (com.landray.kmss.eop.basedata.model.EopBasedataContbody) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        com.landray.kmss.eop.basedata.model.EopBasedataContbody eopBasedataContbody = new com.landray.kmss.eop.basedata.model.EopBasedataContbody();
        eopBasedataContbody.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataContbody.setDocCreateTime(new Date());
        eopBasedataContbody.setDocCreator(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataContbody, requestContext);
        return eopBasedataContbody;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        com.landray.kmss.eop.basedata.model.EopBasedataContbody eopBasedataContbody = (com.landray.kmss.eop.basedata.model.EopBasedataContbody) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
