package com.landray.kmss.eop.basedata.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.forms.EopBasedataVehicleForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataVehicle;
import com.landray.kmss.eop.basedata.service.IEopBasedataVehicleService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataVehicleServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataVehicleService,IXMLDataBean {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataVehicle) {
            EopBasedataVehicle eopBasedataVehicle = (EopBasedataVehicle) model;
            eopBasedataVehicle.setDocAlterTime(new Date());
            eopBasedataVehicle.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }
    @Override
	public IExtendForm initFormSetting(IExtendForm form, RequestContext requestContext) throws Exception {
    	EopBasedataVehicleForm mainForm = (EopBasedataVehicleForm) super.initFormSetting(form, requestContext);
		return mainForm;
	}
    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataVehicle eopBasedataVehicle = new EopBasedataVehicle();
        eopBasedataVehicle.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataVehicle.setDocCreateTime(new Date());
        eopBasedataVehicle.setDocAlterTime(new Date());
        eopBasedataVehicle.setDocCreator(UserUtil.getUser());
        eopBasedataVehicle.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataVehicle, requestContext);
        return eopBasedataVehicle;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataVehicle eopBasedataVehicle = (EopBasedataVehicle) model;
    }

    @Override
    public List<EopBasedataVehicle> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataVehicle.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String fdCompanyId = requestInfo.getParameter("fdCompanyId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock("left join eopBasedataVehicle.fdCompanyList comp");
		hqlInfo.setSelectBlock("new map(eopBasedataVehicle.fdId as value,eopBasedataVehicle.fdName as text)");
		hqlInfo.setWhereBlock("(comp.fdId=:fdCompanyId or comp.fdId is null) and eopBasedataVehicle.fdIsAvailable=:fdIsAvailable");
		hqlInfo.setParameter("fdCompanyId",fdCompanyId);
		hqlInfo.setParameter("fdIsAvailable",true);
		hqlInfo.setOrderBy("eopBasedataVehicle.docCreateTime desc");
		return findList(hqlInfo);
	}
}
