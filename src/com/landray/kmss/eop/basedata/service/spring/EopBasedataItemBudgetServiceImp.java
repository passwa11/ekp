package com.landray.kmss.eop.basedata.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataItemBudgetForm;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataItemBudget;
import com.landray.kmss.eop.basedata.service.IEopBasedataItemBudgetService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataItemBudgetServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataItemBudgetService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataItemBudget) {
            EopBasedataItemBudget eopBasedataItemBudget = (EopBasedataItemBudget) model;
            eopBasedataItemBudget.setDocAlterTime(new Date());
            eopBasedataItemBudget.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
	public IExtendForm initFormSetting(IExtendForm form, RequestContext requestContext) throws Exception {
    	EopBasedataItemBudgetForm mainForm = (EopBasedataItemBudgetForm) super.initFormSetting(form, requestContext);
		return mainForm;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataItemBudget eopBasedataItemBudget = new EopBasedataItemBudget();
        eopBasedataItemBudget.setFdIsAvailable(true);
        eopBasedataItemBudget.setDocCreateTime(new Date());
        eopBasedataItemBudget.setDocAlterTime(new Date());
        eopBasedataItemBudget.setDocCreator(UserUtil.getUser());
        eopBasedataItemBudget.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataItemBudget, requestContext);
        return eopBasedataItemBudget;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataItemBudget eopBasedataItemBudget = (EopBasedataItemBudget) model;
    }

    @Override
    public List<EopBasedataItemBudget> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataItemBudget.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataItemBudget> findByFdCategory(EopBasedataBudgetScheme fdCategory) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataItemBudget.fdCategory.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCategory.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataItemBudget> findByFdItems(EopBasedataExpenseItem fdItems) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataItemBudget.fdItems.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdItems.getFdId());
        return this.findList(hqlInfo);
    }
}
