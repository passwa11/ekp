package com.landray.kmss.eop.basedata.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataItemAccountForm;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataItemAccount;
import com.landray.kmss.eop.basedata.service.IEopBasedataItemAccountService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataItemAccountServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataItemAccountService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataItemAccount) {
            EopBasedataItemAccount eopBasedataItemAccount = (EopBasedataItemAccount) model;
            eopBasedataItemAccount.setDocAlterTime(new Date());
            eopBasedataItemAccount.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IExtendForm initFormSetting(IExtendForm form, RequestContext requestContext) throws Exception {
        EopBasedataItemAccountForm mainForm = (EopBasedataItemAccountForm) super.initFormSetting(form, requestContext);
        return mainForm;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataItemAccount eopBasedataItemAccount = new EopBasedataItemAccount();
        eopBasedataItemAccount.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataItemAccount.setDocCreateTime(new Date());
        eopBasedataItemAccount.setDocAlterTime(new Date());
        eopBasedataItemAccount.setDocCreator(UserUtil.getUser());
        eopBasedataItemAccount.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataItemAccount, requestContext);
        return eopBasedataItemAccount;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataItemAccount eopBasedataItemAccount = (EopBasedataItemAccount) model;
    }

    @Override
    public List<EopBasedataItemAccount> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataItemAccount.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataItemAccount> findByFdExpenseItem(EopBasedataExpenseItem fdExpenseItem) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataItemAccount.fdExpenseItem.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdExpenseItem.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataItemAccount> findByFdAmortize(EopBasedataAccounts fdAmortize) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataItemAccount.fdAmortize.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdAmortize.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataItemAccount> findByFdAccruals(EopBasedataAccounts fdAccruals) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataItemAccount.fdAccruals.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdAccruals.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public EopBasedataAccounts getEopBasedataAccounts(String fdExpenseItemId) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock(" eopBasedataItemAccount.fdAmortize ");
        hqlInfo.setWhereBlock("eopBasedataItemAccount.fdExpenseItem.fdId=:fdExpenseItemId and eopBasedataItemAccount.fdIsAvailable = :fdIsAvailable ");
        hqlInfo.setParameter("fdExpenseItemId", fdExpenseItemId);
        hqlInfo.setParameter("fdIsAvailable", true);
        List<EopBasedataAccounts> list = this.findList(hqlInfo);
        return ArrayUtil.isEmpty(list)?null:list.get(0);
    }
    //根据费用类型获取预提科目
    @Override
    public EopBasedataAccounts getFsscAccrualsAccounts(String fdExpenseItemId) throws Exception {
    	HQLInfo hqlInfo = new HQLInfo();
    	hqlInfo.setSelectBlock(" eopBasedataItemAccount.fdAccruals ");
    	hqlInfo.setWhereBlock("eopBasedataItemAccount.fdExpenseItem.fdId=:fdExpenseItemId and eopBasedataItemAccount.fdIsAvailable = :fdIsAvailable ");
    	hqlInfo.setParameter("fdExpenseItemId", fdExpenseItemId);
    	hqlInfo.setParameter("fdIsAvailable", true);
    	List<EopBasedataAccounts> list = this.findList(hqlInfo);
    	return ArrayUtil.isEmpty(list)?null:list.get(0);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
