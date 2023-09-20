package com.landray.kmss.eop.basedata.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.service.IEopBasedataBudgetSchemeService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataBudgetSchemeServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataBudgetSchemeService,IXMLDataBean {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataBudgetScheme) {
            EopBasedataBudgetScheme eopBasedataBudgetScheme = (EopBasedataBudgetScheme) model;
            eopBasedataBudgetScheme.setDocAlterTime(new Date());
            eopBasedataBudgetScheme.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataBudgetScheme eopBasedataBudgetScheme = new EopBasedataBudgetScheme();
        eopBasedataBudgetScheme.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataBudgetScheme.setFdTarget(String.valueOf("2"));
        eopBasedataBudgetScheme.setDocCreateTime(new Date());
        eopBasedataBudgetScheme.setDocAlterTime(new Date());
        eopBasedataBudgetScheme.setDocCreator(UserUtil.getUser());
        eopBasedataBudgetScheme.setDocAlteror(UserUtil.getUser());
        requestContext.setAttribute("fdCompanyGroup", EopBasedataFsscUtil.getSwitchValue("fdCompanyGroup"));
        EopBasedataUtil.initModelFromRequest(eopBasedataBudgetScheme, requestContext);
        return eopBasedataBudgetScheme;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataBudgetScheme eopBasedataBudgetScheme = (EopBasedataBudgetScheme) model;
    }

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String fdType=requestInfo.getParameter("fdType");
		if(StringUtil.isNotNull(fdType)&&"findOfCenter".equals(fdType)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock(" new Map(eopBasedataBudgetScheme.id as value, eopBasedataBudgetScheme.fdName as text) ");
			hqlInfo.setWhereBlock("eopBasedataBudgetScheme.fdIsAvailable = :fdIsAvailable and eopBasedataBudgetScheme.fdDimension like '%4%'");
			hqlInfo.setParameter("fdIsAvailable", true);
			return this.findList(hqlInfo);
		}else {
			
			String fdName = requestInfo.getParameter("fdName");
			fdName = java.net.URLDecoder.decode(fdName, "UTF-8").trim();// 转码后去前后空格
			List<?> list = new ArrayList();
			HQLInfo hql = new HQLInfo();
			hql.setSelectBlock("count(fdId)");
			hql.setWhereBlock("eopBasedataBudgetScheme.fdName=:fdName");
			hql.setParameter("fdName", fdName);
			list = this.findList(hql);
			if ((Long) list.get(0) > 0) {
				return list;
			} else {
				return null;
			}
		}
		
	}

	@Override
	public EopBasedataBudgetScheme findBudgetSchemeByCode(String fdSchemeCodeCode) throws Exception {
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setWhereBlock("eopBasedataBudgetScheme.fdCode=:fdCode and eopBasedataBudgetScheme.fdIsAvailable=:fdIsAvailable");
		hqlInfo.setParameter("fdCode", fdSchemeCodeCode);
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		List<EopBasedataBudgetScheme> schemeList=this.findList(hqlInfo);
		if(ArrayUtil.isEmpty(schemeList)){
			return null;
		}else{
			return schemeList.get(0);
		}
	}
	
	
}
