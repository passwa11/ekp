package com.landray.kmss.eop.basedata.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataItemFieldForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataItemField;
import com.landray.kmss.eop.basedata.service.IEopBasedataItemFieldService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataItemFieldServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataItemFieldService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataItemField) {
            EopBasedataItemField eopBasedataItemField = (EopBasedataItemField) model;
            eopBasedataItemField.setDocAlterTime(new Date());
            eopBasedataItemField.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
	public IExtendForm initFormSetting(IExtendForm form, RequestContext requestContext) throws Exception {
    	EopBasedataItemFieldForm mainForm = (EopBasedataItemFieldForm) super.initFormSetting(form, requestContext);
		return mainForm;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataItemField eopBasedataItemField = new EopBasedataItemField();
        eopBasedataItemField.setDocCreateTime(new Date());
        eopBasedataItemField.setDocAlterTime(new Date());
        eopBasedataItemField.setDocCreator(UserUtil.getUser());
        eopBasedataItemField.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataItemField, requestContext);
        return eopBasedataItemField;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataItemField eopBasedataItemField = (EopBasedataItemField) model;
    }

    @Override
    public List<EopBasedataItemField> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataItemField.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataItemField> findByFdItems(EopBasedataExpenseItem fdItems) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataItemField.fdItems.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdItems.getFdId());
        return this.findList(hqlInfo);
    }
}
