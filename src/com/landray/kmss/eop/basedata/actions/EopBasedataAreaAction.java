package com.landray.kmss.eop.basedata.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataAreaForm;
import com.landray.kmss.eop.basedata.model.EopBasedataArea;
import com.landray.kmss.eop.basedata.model.EopBasedataCity;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataAreaService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCityService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EopBasedataAreaAction extends EopBasedataBusinessAction {

    private IEopBasedataAreaService eopBasedataAreaService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataAreaService == null) {
            eopBasedataAreaService = (IEopBasedataAreaService) getBean("eopBasedataAreaService");
        }
        return eopBasedataAreaService;
    }
    
    private IEopBasedataCompanyService eopBasedataCompanyService;
    
    public IEopBasedataCompanyService getEopBasedataCompanyService() {
    	 if (eopBasedataCompanyService == null) {
    		 eopBasedataCompanyService = (IEopBasedataCompanyService) getBean("eopBasedataCompanyService");
         }
		return eopBasedataCompanyService;
	}
    
    private IEopBasedataCityService eopBasedataCityService ;

    public IEopBasedataCityService getEopBasedataCityService() {
    	if (eopBasedataCityService == null) {
    		eopBasedataCityService = (IEopBasedataCityService) getBean("eopBasedataCityService");
        }
		return eopBasedataCityService;
	}

	@Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
    	String where = hqlInfo.getWhereBlock();
    	String fdCompanyName=request.getParameter("q.fdCompanyName");
        if(StringUtil.isNotNull(fdCompanyName)) {
        	hqlInfo.setJoinBlock(" left join eopBasedataArea.fdCompanyList company ");
        	where=StringUtil.linkString(where, " and "," company.fdName like :fdCompanyName");
        	hqlInfo.setParameter("fdCompanyName", "%"+fdCompanyName+"%");
        }
    	if(!UserUtil.getKMSSUser().isAdmin()&&!UserUtil.checkRole("ROLE_EOPBASEDATA_COMPANY_DATA")&&!UserUtil.checkRole("ROLE_EOPBASEDATA_MAINTAINER")){
           	//非公司维护和管理员，只能看到自己公司的
           	List<EopBasedataCompany> companyList=getEopBasedataCompanyService().findCompanyByUserId(UserUtil.getUser().getFdId());
           	if(!ArrayUtil.isEmpty(companyList)){
           		hqlInfo.setJoinBlock(" left join eopBasedataArea.fdCompanyList company ");
           		where=StringUtil.linkString(where, " and ",
           				"("+(HQLUtil.buildLogicIN("company.fdId", ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(companyList, "fdId", ";")[0].split(";")))+" or company is null)"));
           	}
          }
    	hqlInfo.setWhereBlock(where);
    	hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataArea.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataAreaForm eopBasedataAreaForm = (EopBasedataAreaForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataAreaService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataAreaForm;
    }

	public ActionForward checkCityExists(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String []city = request.getParameter("city").split(";");
		JSONArray rtn = new JSONArray();
		String fdCompanyId = request.getParameter("fdCompanyId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("(comp.fdId=:fdCompanyId or comp is null) and eopBasedataCity.fdIsAvailable=:fdIsAvailable and eopBasedataCity.fdName in(:fdName)");
		hqlInfo.setParameter("fdCompanyId",fdCompanyId);
		hqlInfo.setParameter("fdIsAvailable",true);
		hqlInfo.setParameter("fdName",Arrays.asList(city));
		hqlInfo.setJoinBlock("left join eopBasedataCity.fdCompanyList comp");
		hqlInfo.setFromBlock(EopBasedataCity.class.getName()+" eopBasedataCity ");
		List<EopBasedataCity> list = getEopBasedataCityService().findList(hqlInfo);
		for(EopBasedataCity c:list) {
			JSONObject obj =  new JSONObject();
			obj.put("fdCity", c.getFdName());
			obj.put("fdId", c.getFdId());
			rtn.add(obj);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
	}
}
