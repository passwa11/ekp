package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.forms.EopBasedataProvinceForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataProvince;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataProvinceService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EopBasedataProvinceAction extends EopBasedataBusinessAction {

    private IEopBasedataProvinceService eopBasedataProvinceService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataProvinceService == null) {
            eopBasedataProvinceService = (IEopBasedataProvinceService) getBean("eopBasedataProvinceService");
        }
        return eopBasedataProvinceService;
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
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataProvince.class);
        String fdCompanyName=request.getParameter("q.fdCompanyName");
        if(StringUtil.isNotNull(fdCompanyName)) {
        	hqlInfo.setJoinBlock(" left join eopBasedataProvince.fdCompanyList company ");
        	hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and "," company.fdName like :fdCompanyName"));
        	hqlInfo.setParameter("fdCompanyName", "%"+fdCompanyName+"%");
        }
        if(!UserUtil.getKMSSUser().isAdmin()&&!UserUtil.checkRole("ROLE_EOPBASEDATA_COMPANY_DATA")&&!UserUtil.checkRole("ROLE_EOPBASEDATA_MAINTAINER")){
        	//非公司维护和管理员，只能看到自己公司的
           	List<EopBasedataCompany> companyList=getEopBasedataCompanyService().findCompanyByUserId(UserUtil.getUser().getFdId());
           	if(!ArrayUtil.isEmpty(companyList)){
           		hqlInfo.setJoinBlock(" left join eopBasedataProvince.fdCompanyList company ");
           		hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
           				"("+(HQLUtil.buildLogicIN("company.fdId", ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(companyList, "fdId", ";")[0].split(";")))+" or company is null)")));;
           	}
        }
        hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.eop.basedata.util.EopBasedataUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.eop.basedata.model.EopBasedataProvince.class);
        com.landray.kmss.eop.basedata.util.EopBasedataUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataProvinceForm eopBasedataProvinceForm = (EopBasedataProvinceForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataProvinceService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataProvinceForm;
    }
    
    public ActionForward getProvince(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("getProvince", true, getClass());
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
                where += "(eopBasedataProvince.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += " or eopBasedataProvince.fdCode like :fdCode";
                hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                    "eopBasedataProvince.fdIsAvailable=:fdIsAvailable"));
            hqlInfo.setParameter("fdIsAvailable", true);//启用
            String fdCompanyId = request.getParameter("fdCompanyId");
            if(StringUtil.isNotNull(fdCompanyId)){
                hqlInfo.setJoinBlock(" left join eopBasedataProvince.fdCompanyList comp");
                if(fdCompanyId.indexOf(";")>-1) {
                    hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                            "("+ HQLUtil.buildLogicIN("comp.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";"))))+" or comp is null)");
                }else {
                    hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                            "(comp.fdId=:fdCompanyId or comp is null)"));
                    hqlInfo.setParameter("fdCompanyId", fdCompanyId);
                }
            }
            hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
            HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataProvince.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("getProvince", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("selectData");
        }
    }
}
