package com.landray.kmss.eop.basedata.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataTaxRateForm;
import com.landray.kmss.eop.basedata.model.EopBasedataArea;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataTaxRate;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataTaxRateService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EopBasedataTaxRateAction extends EopBasedataBusinessAction {

    private IEopBasedataTaxRateService eopBasedataTaxRateService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataTaxRateService == null) {
            eopBasedataTaxRateService = (IEopBasedataTaxRateService) getBean("eopBasedataTaxRateService");
        }
        return eopBasedataTaxRateService;
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
    	String fdCompanyId = request.getParameter("fdCompanyId");
    	if(StringUtil.isNotNull(fdCompanyId)){
    		where = StringUtil.linkString(where, " and ", "eopBasedataTaxRate.fdCompany.fdId=:fdCompanyId");
    		hqlInfo.setParameter("fdCompanyId",fdCompanyId);
    	}
    	if(!UserUtil.getKMSSUser().isAdmin()&&!UserUtil.checkRole("ROLE_EOPBASEDATA_COMPANY_DATA")&&!UserUtil.checkRole("ROLE_EOPBASEDATA_MAINTAINER")){
          	//非公司维护和管理员，只能看到自己公司的
          	List<EopBasedataCompany> companyList=getEopBasedataCompanyService().findCompanyByUserId(UserUtil.getUser().getFdId());
          	if(!ArrayUtil.isEmpty(companyList)){
          		where=StringUtil.linkString(where, " and ",
                          HQLUtil.buildLogicIN("eopBasedataTaxRate.fdCompany.fdId", ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(companyList, "fdId", ";")[0].split(";"))));
          	}
         }
    	hqlInfo.setWhereBlock(where);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataTaxRate.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataTaxRateForm eopBasedataTaxRateForm = (EopBasedataTaxRateForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataTaxRateService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataTaxRateForm;
    }
}
