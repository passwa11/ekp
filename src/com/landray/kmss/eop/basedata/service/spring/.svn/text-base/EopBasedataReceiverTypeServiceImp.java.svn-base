package com.landray.kmss.eop.basedata.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataReceiverType;
import com.landray.kmss.eop.basedata.service.IEopBasedataReceiverTypeService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

import java.util.Date;
import java.util.List;

public class EopBasedataReceiverTypeServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataReceiverTypeService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataReceiverType) {
            EopBasedataReceiverType eopBasedataReceiverType = (EopBasedataReceiverType) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataReceiverType eopBasedataReceiverType = new EopBasedataReceiverType();
        eopBasedataReceiverType.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataReceiverType.setDocCreateTime(new Date());
        eopBasedataReceiverType.setDocCreator(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataReceiverType, requestContext);
        return eopBasedataReceiverType;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataReceiverType eopBasedataReceiverType = (EopBasedataReceiverType) model;
    }

    @Override
    public List<EopBasedataReceiverType> findByFdAccounts(EopBasedataAccounts fdAccounts) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataReceiverType.fdAccounts.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdAccounts.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
