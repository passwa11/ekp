package com.landray.kmss.eop.basedata.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostType;
import com.landray.kmss.eop.basedata.service.IEopBasedataCostTypeService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataCostTypeServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataCostTypeService,IXMLDataBean {

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context)
			throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataCostType) {
            EopBasedataCostType eopBasedataCostType = (EopBasedataCostType) model;
            eopBasedataCostType.setDocAlterTime(new Date());
            eopBasedataCostType.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataCostType eopBasedataCostType = new EopBasedataCostType();
        eopBasedataCostType.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataCostType.setDocCreateTime(new Date());
        eopBasedataCostType.setDocAlterTime(new Date());
        eopBasedataCostType.setDocCreator(UserUtil.getUser());
        eopBasedataCostType.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataCostType, requestContext);
        return eopBasedataCostType;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataCostType eopBasedataCostType = (EopBasedataCostType) model;
    }

    @Override
    public List<EopBasedataCostType> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataCostType.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String fdCompanyId = requestInfo.getParameter("fdCompanyId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("eopBasedataCostType.fdIsAvailable=:fdIsAvailable");
		hqlInfo.setParameter("fdIsAvailable",true);
		if(StringUtil.isNotNull(fdCompanyId)){
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "eopBasedataCostType.fdCompany.fdId=:fdCompanyId"));
			hqlInfo.setParameter("fdCompanyId",fdCompanyId);
		}
		hqlInfo.setSelectBlock("new map(eopBasedataCostType.fdName as text,eopBasedataCostType.fdId as value)");
		return findList(hqlInfo);
	}
}
