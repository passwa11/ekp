package com.landray.kmss.eop.basedata.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataStandardForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataStandard;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataStandardService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class EopBasedataStandardAction extends EopBasedataBusinessAction {

    private IEopBasedataStandardService eopBasedataStandardService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataStandardService == null) {
            eopBasedataStandardService = (IEopBasedataStandardService) getBean("eopBasedataStandardService");
        }
        return eopBasedataStandardService;
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
    		hqlInfo.setJoinBlock(" left join eopBasedataStandard.fdCompanyList company");
    		where = StringUtil.linkString(where, " and ", "(company.fdId=:fdCompanyId or company is null)");
    		hqlInfo.setParameter("fdCompanyId",fdCompanyId);
    	}
    	if(!UserUtil.getKMSSUser().isAdmin()&&!UserUtil.checkRole("ROLE_EOPBASEDATA_COMPANY_DATA")&&!UserUtil.checkRole("ROLE_EOPBASEDATA_MAINTAINER")){
           	//非公司维护和管理员，只能看到自己公司的
           	List<EopBasedataCompany> companyList=getEopBasedataCompanyService().findCompanyByUserId(UserUtil.getUser().getFdId());
           	if(!ArrayUtil.isEmpty(companyList)){
           		hqlInfo.setJoinBlock(" left join eopBasedataStandard.fdCompanyList company");
           		where=StringUtil.linkString(where, " and ",
           				"("+(HQLUtil.buildLogicIN("company.fdId", ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(companyList, "fdId", ";")[0].split(";")))+" or company is null)"));
           	}
        }

        String fdCompanyName = request.getParameter("q.fdCompanyName");
        if(StringUtil.isNotNull(fdCompanyName)){
            hqlInfo.setJoinBlock(" left join eopBasedataStandard.fdCompanyList company");
            where = StringUtil.linkString(where, " and ", "company.fdName like :fdCompanyName ");
            hqlInfo.setParameter("fdCompanyName","%"+fdCompanyName+"%");
        }

        String fdLevelName = request.getParameter("q.fdLevelName");
        if(StringUtil.isNotNull(fdLevelName)){
            where = StringUtil.linkString(where, " and ", "eopBasedataStandard.fdLevel.fdName like :fdLevelName ");
            hqlInfo.setParameter("fdLevelName","%"+fdLevelName+"%");
        }
        String fdExpenseName = request.getParameter("q.fdItemName");
        if(StringUtil.isNotNull(fdExpenseName)){
            where = StringUtil.linkString(where, " and ", "eopBasedataStandard.fdItem.fdName like :fdExpenseItem ");
            hqlInfo.setParameter("fdExpenseItem","%"+fdExpenseName+"%");
        }
        String fdArea = request.getParameter("q.fdAreaName");
        if(StringUtil.isNotNull(fdArea)){
            where = StringUtil.linkString(where, " and ", "eopBasedataStandard.fdArea.fdArea like :fdArea ");
            hqlInfo.setParameter("fdArea","%"+fdArea+"%");
        }
        String fdVehicle = request.getParameter("q.fdVehicleName");
        if(StringUtil.isNotNull(fdVehicle)){
            where = StringUtil.linkString(where, " and ", "eopBasedataStandard.fdVehicle.fdName like :fdVehicle ");
            hqlInfo.setParameter("fdVehicle","%"+fdVehicle+"%");
        }
        String fdBerth = request.getParameter("q.fdBerthName");
        if(StringUtil.isNotNull(fdBerth)){
            where = StringUtil.linkString(where, " and ", "eopBasedataStandard.fdBerth.fdName like :fdBerth ");
            hqlInfo.setParameter("fdBerth","%"+fdBerth+"%");
        }

    	hqlInfo.setWhereBlock(where);
    	hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataStandard.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataStandardForm eopBasedataStandardForm = (EopBasedataStandardForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataStandardService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataStandardForm;
    }
}
