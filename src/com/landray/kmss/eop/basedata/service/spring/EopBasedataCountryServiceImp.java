package com.landray.kmss.eop.basedata.service.spring;

import java.util.Date;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.eop.basedata.service.IEopBasedataCountryService;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.eop.basedata.model.EopBasedataCountry;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.common.forms.IExtendForm;

public class EopBasedataCountryServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataCountryService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataCountry) {
            EopBasedataCountry eopBasedataCountry = (EopBasedataCountry) model;
            eopBasedataCountry.setDocAlterTime(new Date());
            eopBasedataCountry.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataCountry eopBasedataCountry = new EopBasedataCountry();
        eopBasedataCountry.setDocCreateTime(new Date());
        eopBasedataCountry.setDocAlterTime(new Date());
        eopBasedataCountry.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataCountry.setDocCreator(UserUtil.getUser());
        eopBasedataCountry.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataCountry, requestContext);
        return eopBasedataCountry;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataCountry eopBasedataCountry = (EopBasedataCountry) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
