package com.landray.kmss.eop.basedata.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.model.EopBasedataCustomer;
import com.landray.kmss.eop.basedata.service.IEopBasedataCustomerService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class EopBasedataCustomerDataAction extends BaseAction {

    private IEopBasedataCustomerService eopBasedataCustomerService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataCustomerService == null) {
            eopBasedataCustomerService = (IEopBasedataCustomerService) getBean("eopBasedataCustomerService");
        }
        return eopBasedataCustomerService;
    }

    public ActionForward getCustomer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("getCustomer", true, getClass());
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
            String where = "";
            if (StringUtil.isNotNull(keyWord)) {
                
                where += "(eopBasedataCustomer.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += " or eopBasedataCustomer.fdCode like :fdCode";
                hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
                where += " or eopBasedataCustomer.fdTaxNo like :fdTaxNo";
                hqlInfo.setParameter("fdTaxNo", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }

            String fdCompanyId = request.getParameter("fdCompanyId");
            if(StringUtil.isNotNull(fdCompanyId)){
            	hqlInfo.setJoinBlock(" left join eopBasedataCustomer.fdCompanyList company");
                hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                        "(company.fdId=:fdCompanyId or company is null)"));
                hqlInfo.setParameter("fdCompanyId", fdCompanyId);
            }
            hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
        			"eopBasedataCustomer.fdIsAvailable=:fdIsAvailable"));
            String fdIsAvailable = request.getParameter("fdIsAvailable");
            if(StringUtil.isNotNull(fdIsAvailable)){
            	hqlInfo.setParameter("fdIsAvailable", fdIsAvailable);
            }else{
            	hqlInfo.setParameter("fdIsAvailable", true);
            }
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("getCustomer", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("getCustomer");
        }
    }
}
