package com.landray.kmss.eop.basedata.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataInnerOrderForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataInnerOrderService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class EopBasedataInnerOrderAction extends EopBasedataBusinessAction {

    private IEopBasedataInnerOrderService eopBasedataInnerOrderService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataInnerOrderService == null) {
            eopBasedataInnerOrderService = (IEopBasedataInnerOrderService) getBean("eopBasedataInnerOrderService");
        }
        return eopBasedataInnerOrderService;
    }
    
    private IEopBasedataCompanyService eopBasedataCompanyService;
    
    public IEopBasedataCompanyService getEopBasedataCompanyService() {
    	 if (eopBasedataCompanyService == null) {
    		 eopBasedataCompanyService = (IEopBasedataCompanyService) getBean("eopBasedataCompanyService");
         }
		return eopBasedataCompanyService;
	}

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
    	String where = hqlInfo.getWhereBlock();
    	String fdCompanyName=request.getParameter("q.fdCompanyName");
    	if(StringUtil.isNotNull(fdCompanyName)) {
        	hqlInfo.setJoinBlock(" left join eopBasedataInnerOrder.fdCompanyList company ");
        	where=StringUtil.linkString(where, " and "," company.fdName like :fdCompanyName");
        	hqlInfo.setParameter("fdCompanyName", "%"+fdCompanyName+"%");
        }
    	if(!UserUtil.getKMSSUser().isAdmin()&&!UserUtil.checkRole("ROLE_EOPBASEDATA_COMPANY_DATA")&&!UserUtil.checkRole("ROLE_EOPBASEDATA_MAINTAINER")){
          	//非公司维护和管理员，只能看到自己公司的
          	List<EopBasedataCompany> companyList=getEopBasedataCompanyService().findCompanyByUserId(UserUtil.getUser().getFdId());
          	if(!ArrayUtil.isEmpty(companyList)){
          		hqlInfo.setJoinBlock(" left join eopBasedataInnerOrder.fdCompanyList company ");
          		where=StringUtil.linkString(where, " and ",
          				"("+(HQLUtil.buildLogicIN("company.fdId", ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(companyList, "fdId", ";")[0].split(";")))+" or company is null)"));
          	}
         }
    	hqlInfo.setWhereBlock(where);
    	 hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataInnerOrder.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataInnerOrderForm eopBasedataInnerOrderForm = (EopBasedataInnerOrderForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataInnerOrderService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataInnerOrderForm;
    }
}
