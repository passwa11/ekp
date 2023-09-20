package com.landray.kmss.eop.basedata.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.forms.EopBasedataInputTaxForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataInputTax;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataInputTaxService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EopBasedataInputTaxAction extends EopBasedataBusinessAction {

    private IEopBasedataInputTaxService eopBasedataInputTaxService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataInputTaxService == null) {
            eopBasedataInputTaxService = (IEopBasedataInputTaxService) getBean("eopBasedataInputTaxService");
        }
        return eopBasedataInputTaxService;
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
         	hqlInfo.setJoinBlock(" left join eopBasedataInputTax.fdCompanyList company ");
         	where=StringUtil.linkString(where, " and "," company.fdName like :fdCompanyName");
         	hqlInfo.setParameter("fdCompanyName", "%"+fdCompanyName+"%");
         }
         if(!UserUtil.getKMSSUser().isAdmin()&&!UserUtil.checkRole("ROLE_EOPBASEDATA_COMPANY_DATA")&&!UserUtil.checkRole("ROLE_EOPBASEDATA_MAINTAINER")){
           	//非公司维护和管理员，只能看到自己公司的
           	List<EopBasedataCompany> companyList=getEopBasedataCompanyService().findCompanyByUserId(UserUtil.getUser().getFdId());
           	if(!ArrayUtil.isEmpty(companyList)){
           		hqlInfo.setJoinBlock(" left join eopBasedataInputTax.fdCompanyList company ");
           		where=StringUtil.linkString(where, " and ",
           				"("+(HQLUtil.buildLogicIN("company.fdId", ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(companyList, "fdId", ";")[0].split(";")))+" or company is null)"));
           	}
          }
        hqlInfo.setWhereBlock(where);
        hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataInputTax.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        EopBasedataUtil.buildHqlInfoDate(hqlInfo, request, EopBasedataInputTax.class);
        EopBasedataUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataInputTaxForm eopBasedataInputTaxForm = (EopBasedataInputTaxForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataInputTaxService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataInputTaxForm;
    }
    
    public ActionForward getInputTax(ActionMapping mapping, ActionForm form, HttpServletRequest request,
 			HttpServletResponse response) throws Exception {
 		TimeCounter.logCurrentTime("getInputTax", true, getClass());
 		KmssMessages messages = new KmssMessages();
 		try {
 			String s_pageno = request.getParameter("pageno");
 			String s_rowsize = request.getParameter("rowsize");
 			String keyWord = request.getParameter("q._keyword");
 			int pageno = 0;
 			int rowsize = SysConfigParameters.getRowSize();
 			if (s_pageno != null && s_pageno.length() > 0) {
 				pageno = Integer.parseInt(s_pageno);
 			}
 			if (s_rowsize != null && s_rowsize.length() > 0) {
 				rowsize = Integer.parseInt(s_rowsize);
 			}
 			HQLInfo hqlInfo = new HQLInfo();
 			hqlInfo.setPageNo(pageno);
 			hqlInfo.setRowSize(rowsize);
 			if (StringUtil.isNotNull(keyWord)) {
 				 String where = "";
 				where += " ( cast(eopBasedataInputTax.fdTaxRate,string) like :fdTaxRate";
 				 hqlInfo.setParameter("fdTaxRate", "%" + keyWord + "%");
                 where += ")";
                 hqlInfo.setWhereBlock(where);
 			}
 			String fdExpenseItemId = request.getParameter("fdExpenseItemId");
 			if(StringUtil.isNotNull(fdExpenseItemId)){
 				hqlInfo.setWhereBlock(
 						StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " it.fdId=:fdExpenseItemId "));
 				hqlInfo.setParameter("fdExpenseItemId", fdExpenseItemId);
 			}
 			hqlInfo.setJoinBlock("left join eopBasedataInputTax.fdCompanyList comp left join eopBasedataInputTax.fdItem it");
 			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
 					"(comp.fdId=:fdCompanyId or comp.fdId is null)"));
 			hqlInfo.setParameter("fdCompanyId", request.getParameter("fdCompanyId"));
 			hqlInfo.setWhereBlock(
 					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataInputTax.fdIsAvailable=:fdIsAvailable "));
 			String valid = request.getParameter("valid");
 			if(StringUtil.isNotNull(valid)){
 				hqlInfo.setParameter("fdIsAvailable",Boolean.valueOf(valid));
 			}else{
 				hqlInfo.setParameter("fdIsAvailable",true);
 			}
 			hqlInfo.setWhereBlock(
 					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataInputTax.fdIsInputTax=:fdIsInputTax "));
 			hqlInfo.setParameter("fdIsInputTax",true);
 			Page page = getServiceImp(request).findPage(hqlInfo);
 			request.setAttribute("queryPage", page);
 		} catch (Exception e) {
 			messages.addError(e);
 		}
 		TimeCounter.logCurrentTime("getInputTax", false, getClass());
 		if (messages.hasError()) {
 			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
 					.save(request);
 			return mapping.findForward("failure");
 		} else {
 			return mapping.findForward("getInputTax");
 			}
    }
	public ActionForward checkRateExists(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String []rate = request.getParameter("rate").split(";");
		JSONArray rtn = new JSONArray();
		for(String c:rate) {
			JSONObject obj =  new JSONObject();
			obj.put("fdRate", Double.valueOf(c));
			rtn.add(obj);
		}
		String fdCompanyId = request.getParameter("fdCompanyId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("eopBasedataInputTax.fdCompany.fdId=:fdCompanyId and eopBasedataInputTax.fdIsAvailable=:fdIsAvailable");
		hqlInfo.setParameter("fdCompanyId",fdCompanyId);
		hqlInfo.setParameter("fdIsAvailable",true);
		List<EopBasedataInputTax> list = getServiceImp(request).findList(hqlInfo);
		for(EopBasedataInputTax tax:list) {
			for(int i=0;i<rtn.size();i++) {
				JSONObject obj = rtn.getJSONObject(i);
				Double fdRate = obj.getDouble("fdRate");
				if(fdRate.equals(tax.getFdTaxRate())) {
					obj.put("fdId", tax.getFdId());
				}
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rtn.toString());
		return null;
	}
}
