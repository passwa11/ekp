package com.landray.kmss.eop.basedata.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataOutTax;
import com.landray.kmss.eop.basedata.service.IEopBasedataOutTaxService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

import java.util.Date;
import java.util.List;

public class EopBasedataOutTaxServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataOutTaxService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataOutTax) {
            EopBasedataOutTax eopBasedataOutTax = (EopBasedataOutTax) model;
            eopBasedataOutTax.setDocAlterTime(new Date());
            eopBasedataOutTax.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataOutTax eopBasedataOutTax = new EopBasedataOutTax();
        eopBasedataOutTax.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataOutTax.setDocCreateTime(new Date());
        eopBasedataOutTax.setDocAlterTime(new Date());
        eopBasedataOutTax.setDocCreator(UserUtil.getUser());
        eopBasedataOutTax.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataOutTax, requestContext);
        return eopBasedataOutTax;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataOutTax eopBasedataOutTax = (EopBasedataOutTax) model;
    }

    @Override
    public List<EopBasedataOutTax> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataOutTax.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataOutTax> findByFdAccount(EopBasedataAccounts fdAccount) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataOutTax.fdAccount.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdAccount.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
