package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataAccountsForm;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataAccountsService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EopBasedataAccountsAction extends EopBasedataBusinessAction {

    private IEopBasedataAccountsService eopBasedataAccountsService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataAccountsService == null) {
        	eopBasedataAccountsService = (IEopBasedataAccountsService) getBean("eopBasedataAccountsService");
        }
        return eopBasedataAccountsService;
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
        	hqlInfo.setJoinBlock(" left join eopBasedataAccounts.fdCompanyList company ");
        	where=StringUtil.linkString(where, " and "," company.fdName like :fdCompanyName");
        	hqlInfo.setParameter("fdCompanyName", "%"+fdCompanyName+"%");
        }
    	if(!UserUtil.getKMSSUser().isAdmin()&&!UserUtil.checkRole("ROLE_EOPBASEDATA_COMPANY_DATA")&&!UserUtil.checkRole("ROLE_EOPBASEDATA_MAINTAINER")){
         	List<EopBasedataCompany> companyList=getEopBasedataCompanyService().findCompanyByUserId(UserUtil.getUser().getFdId());
         	if(!ArrayUtil.isEmpty(companyList)){
         		hqlInfo.setJoinBlock(" left join eopBasedataAccounts.fdCompanyList company ");
         		where=StringUtil.linkString(where, " and ","("+(HQLUtil.buildLogicIN("company.fdId", ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(companyList, "fdId", ";")[0].split(";")))+" or company is null)"));
         	}
         }
    	hqlInfo.setWhereBlock(where);
    	hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataAccounts.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataAccountsForm eopBasedataAccountsForm = (EopBasedataAccountsForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataAccountsService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataAccountsForm;
    }
    public ActionForward getFdAccountProperty(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fdAccountsId = request.getParameter("fdAccountsId");
        HQLInfo hqlInfo = new HQLInfo();
        String selectBlock = "eopBasedataAccounts.fdCostItem";
        String whereBlock = "eopBasedataAccounts.fdId = :fdAccountsId";
        hqlInfo.setSelectBlock(selectBlock);
        hqlInfo.setWhereBlock(whereBlock);
        hqlInfo.setParameter("fdAccountsId", fdAccountsId);
        List<String> values = getServiceImp(request).findValue(hqlInfo);
        if (values != null && values.size() > 0) {
            String property = values.get(0);
            JSONObject object = new JSONObject();
            object.put("property", property);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(object.toString());
        }
        return null;
    }
}
