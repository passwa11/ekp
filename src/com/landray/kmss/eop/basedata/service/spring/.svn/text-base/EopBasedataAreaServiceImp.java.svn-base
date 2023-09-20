package com.landray.kmss.eop.basedata.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.forms.EopBasedataAreaForm;
import com.landray.kmss.eop.basedata.model.EopBasedataArea;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataAreaService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataAreaServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataAreaService,IXMLDataBean {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataArea) {
            EopBasedataArea eopBasedataArea = (EopBasedataArea) model;
            eopBasedataArea.setDocAlterTime(new Date());
            eopBasedataArea.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }
    @Override
	public IExtendForm initFormSetting(IExtendForm form, RequestContext requestContext) throws Exception {
    	EopBasedataAreaForm mainForm = (EopBasedataAreaForm) super.initFormSetting(form, requestContext);
		return mainForm;
	}
    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataArea eopBasedataArea = new EopBasedataArea();
        eopBasedataArea.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataArea.setDocCreateTime(new Date());
        eopBasedataArea.setDocAlterTime(new Date());
        eopBasedataArea.setDocCreator(UserUtil.getUser());
        eopBasedataArea.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataArea, requestContext);
        return eopBasedataArea;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataArea eopBasedataArea = (EopBasedataArea) model;
    }

    @Override
    public List<EopBasedataArea> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataArea.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String fdCompanyId = requestInfo.getParameter("fdCompanyId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("new map(eopBasedataArea.fdId as value,eopBasedataArea.fdArea as text)");
		hqlInfo.setWhereBlock("eopBasedataArea.fdCompany.fdId=:fdCompanyId and eopBasedataArea.fdIsAvailable=:fdIsAvailable");
		hqlInfo.setParameter("fdCompanyId",fdCompanyId);
		hqlInfo.setParameter("fdIsAvailable",true);
		hqlInfo.setOrderBy("eopBasedataArea.fdOrder asc");
		return findList(hqlInfo);
	}
}
