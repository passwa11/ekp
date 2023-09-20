package com.landray.kmss.eop.basedata.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataCostCenterForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCostCenterService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class EopBasedataCostCenterAction extends EopBasedataBusinessAction {

    private IEopBasedataCostCenterService eopBasedataCostCenterService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataCostCenterService == null) {
            eopBasedataCostCenterService = (IEopBasedataCostCenterService) getBean("eopBasedataCostCenterService");
        }
        return eopBasedataCostCenterService;
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
        	hqlInfo.setJoinBlock(" left join eopBasedataCostCenter.fdCompanyList company ");
        	where=StringUtil.linkString(where, " and "," company.fdName like :fdCompanyName");
        	hqlInfo.setParameter("fdCompanyName", "%"+fdCompanyName+"%");
        }
    	String fdParentId = request.getParameter("q.parentId");
    	if(StringUtil.isNotNull(fdParentId)){
    		hqlInfo.setJoinBlock(" left join eopBasedataCostCenter.hbmParent parent ");
    		where = StringUtil.linkString(where, " and ", " parent.fdId=:fdParentId");
    		hqlInfo.setParameter("fdParentId",fdParentId);
    	}
    	if(!UserUtil.getKMSSUser().isAdmin()&&!UserUtil.checkRole("ROLE_EOPBASEDATA_COMPANY_DATA")&&!UserUtil.checkRole("ROLE_EOPBASEDATA_MAINTAINER")){
         	//只有客商维护权限，只能看到自己公司的
         	List<EopBasedataCompany> companyList=getEopBasedataCompanyService().findCompanyByUserId(UserUtil.getUser().getFdId());
         	if(!ArrayUtil.isEmpty(companyList)){
         		hqlInfo.setJoinBlock(" left join eopBasedataCostCenter.fdCompanyList company ");
         		where=StringUtil.linkString(where, " and ",
         				"("+(HQLUtil.buildLogicIN("company.fdId", ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(companyList, "fdId", ";")[0].split(";")))+" or company is null)"));
         	}
         }
    	hqlInfo.setWhereBlock(where);
    	hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataCostCenter.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataCostCenterForm eopBasedataCostCenterForm = (EopBasedataCostCenterForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataCostCenterService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataCostCenterForm;
    }
    
    /**
	 * 重写该方案，用于编辑时财务系统显示
	 * 
	 * @param form
	 * @param request
	 * @return form对象
	 * @throws Exception
	 */
	@Override
    protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
		super.loadActionForm(mapping, form, request, response);
		String fdFinancialSystem = EopBasedataFsscUtil.getSwitchValue("fdFinancialSystem");
		if (StringUtil.isNotNull(fdFinancialSystem)) {
			String[] property = fdFinancialSystem.split(";");
			request.setAttribute("financialSystemList", ArrayUtil.convertArrayToList(property));
		}
	}
	
	public ActionForward getEkpOrgById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject rtn = new JSONObject();
		String fdId = request.getParameter("fdId");
		if(StringUtil.isNotNull(fdId)){
			EopBasedataCostCenter center=(EopBasedataCostCenter) getServiceImp(null).findByPrimaryKey(fdId);
			List<SysOrgElement> list=center.getFdEkpOrg();
			if(list!=null&&list.size()>0){
				rtn.put("fdOrgId", list.get(0).getFdId());
				rtn.put("fdOrgName", list.get(0).getFdName());
			}
		}
		
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(rtn.toString());
		return null;
	}
}
