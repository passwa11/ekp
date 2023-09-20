package com.landray.kmss.eop.basedata.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataWbsForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.eop.basedata.service.IEopBasedataWbsService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataWbsServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataWbsService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataWbs) {
            EopBasedataWbs eopBasedataWbs = (EopBasedataWbs) model;
            eopBasedataWbs.setDocAlterTime(new Date());
            eopBasedataWbs.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }
    @Override
	public IExtendForm initFormSetting(IExtendForm form, RequestContext requestContext) throws Exception {
    	EopBasedataWbsForm mainForm = (EopBasedataWbsForm) super.initFormSetting(form, requestContext);
		return mainForm;
	}
    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataWbs eopBasedataWbs = new EopBasedataWbs();
		String parentId = requestContext.getParameter("parentId");
		if (StringUtil.isNotNull(parentId)) {
			EopBasedataWbs parent = (EopBasedataWbs) this.findByPrimaryKey(parentId, null, true);
			if (parent != null) {
				eopBasedataWbs.setFdParent(parent);
			}
		}
        eopBasedataWbs.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataWbs.setDocCreateTime(new Date());
        eopBasedataWbs.setDocAlterTime(new Date());
        eopBasedataWbs.setDocCreator(UserUtil.getUser());
        eopBasedataWbs.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataWbs, requestContext);
        return eopBasedataWbs;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataWbs eopBasedataWbs = (EopBasedataWbs) model;
    }

    @Override
    public List<EopBasedataWbs> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataWbs.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }
}
