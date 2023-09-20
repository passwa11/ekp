package com.landray.kmss.eop.basedata.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataExpenseItemForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataStandardScheme;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataExpenseItemService;
import com.landray.kmss.eop.basedata.service.IEopBasedataStandardSchemeService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class EopBasedataExpenseItemAction extends EopBasedataBusinessAction {

    private IEopBasedataExpenseItemService eopBasedataExpenseItemService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataExpenseItemService == null) {
            eopBasedataExpenseItemService = (IEopBasedataExpenseItemService) getBean("eopBasedataExpenseItemService");
        }
        return eopBasedataExpenseItemService;
    }
    
    private IEopBasedataStandardSchemeService eopBasedataStandardSchemeService;

    public IEopBasedataStandardSchemeService getEopBasedataStandardSchemeService() {
    	if (eopBasedataStandardSchemeService == null) {
    		eopBasedataStandardSchemeService = (IEopBasedataStandardSchemeService) getBean("eopBasedataStandardSchemeService");
        }
		return eopBasedataStandardSchemeService;
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
        	hqlInfo.setJoinBlock(" left join eopBasedataExpenseItem.fdCompanyList company ");
        	where=StringUtil.linkString(where, " and "," company.fdName like :fdCompanyName");
        	hqlInfo.setParameter("fdCompanyName", "%"+fdCompanyName+"%");
        }
    	if(!UserUtil.getKMSSUser().isAdmin()&&!UserUtil.checkRole("ROLE_EOPBASEDATA_COMPANY_DATA")&&!UserUtil.checkRole("ROLE_EOPBASEDATA_MAINTAINER")){
          	//非公司维护和管理员，只能看到自己公司的
          	List<EopBasedataCompany> companyList=getEopBasedataCompanyService().findCompanyByUserId(UserUtil.getUser().getFdId());
          	if(!ArrayUtil.isEmpty(companyList)){
          		hqlInfo.setJoinBlock(" left join eopBasedataExpenseItem.fdCompanyList company ");
          		where=StringUtil.linkString(where, " and ",
          				"("+(HQLUtil.buildLogicIN("company.fdId", ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(companyList, "fdId", ";")[0].split(";")))+" or company is null)"));
          	}
         }
    	hqlInfo.setWhereBlock(where);
    	hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataExpenseItem.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.loadActionForm(mapping, form, request, response);
		String fdId = request.getParameter("fdId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("eopBasedataStandardScheme.fdItems.fdId=:fdItemId");
		hqlInfo.setParameter("fdItemId",fdId);
		List<EopBasedataStandardScheme> cates = getEopBasedataStandardSchemeService().findList(hqlInfo);
		if(!ArrayUtil.isEmpty(cates)){
			request.setAttribute("fdStandardCategoryName", cates.get(0).getFdName());
		}
	}

	@Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataExpenseItemForm eopBasedataExpenseItemForm = (EopBasedataExpenseItemForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataExpenseItemService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataExpenseItemForm;
    }
}
