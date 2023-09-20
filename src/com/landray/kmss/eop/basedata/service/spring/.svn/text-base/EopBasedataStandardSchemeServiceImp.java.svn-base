package com.landray.kmss.eop.basedata.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataStandardSchemeForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataStandardScheme;
import com.landray.kmss.eop.basedata.service.IEopBasedataStandardSchemeService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataStandardSchemeServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataStandardSchemeService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataStandardScheme) {
            EopBasedataStandardScheme eopBasedataStandardScheme = (EopBasedataStandardScheme) model;
            eopBasedataStandardScheme.setDocAlterTime(new Date());
            eopBasedataStandardScheme.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }
    @Override
	public IExtendForm initFormSetting(IExtendForm form, RequestContext requestContext) throws Exception {
    	EopBasedataStandardSchemeForm mainForm = (EopBasedataStandardSchemeForm) super.initFormSetting(form, requestContext);
		return mainForm;
	}
    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataStandardScheme eopBasedataStandardScheme = new EopBasedataStandardScheme();
        eopBasedataStandardScheme.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataStandardScheme.setDocCreateTime(new Date());
        eopBasedataStandardScheme.setDocAlterTime(new Date());
        eopBasedataStandardScheme.setDocCreator(UserUtil.getUser());
        eopBasedataStandardScheme.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataStandardScheme, requestContext);
        return eopBasedataStandardScheme;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataStandardScheme eopBasedataStandardScheme = (EopBasedataStandardScheme) model;
    }

    @Override
    public List<EopBasedataStandardScheme> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataStandardScheme.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataStandardScheme> findByFdItems(EopBasedataExpenseItem fdItems) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataStandardScheme.fdItems.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdItems.getFdId());
        return this.findList(hqlInfo);
    }
}
