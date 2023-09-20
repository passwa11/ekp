package com.landray.kmss.eop.basedata.service.spring;

import java.util.Date;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.eop.basedata.service.IEopBasedataFundService;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.eop.basedata.model.EopBasedataFund;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.common.forms.IExtendForm;

public class EopBasedataFundServiceImp extends ExtendDataServiceImp implements IEopBasedataFundService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataFund) {
            EopBasedataFund eopBasedataFund = (EopBasedataFund) model;
            eopBasedataFund.setDocAlterTime(new Date());
            eopBasedataFund.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataFund eopBasedataFund = new EopBasedataFund();
        eopBasedataFund.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataFund.setDocCreateTime(new Date());
        eopBasedataFund.setDocAlterTime(new Date());
        eopBasedataFund.setDocCreator(UserUtil.getUser());
        eopBasedataFund.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataFund, requestContext);
        return eopBasedataFund;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataFund eopBasedataFund = (EopBasedataFund) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
