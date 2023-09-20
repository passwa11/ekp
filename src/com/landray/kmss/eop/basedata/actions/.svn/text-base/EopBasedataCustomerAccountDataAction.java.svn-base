package com.landray.kmss.eop.basedata.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.model.EopBasedataCustomerAccount;
import com.landray.kmss.eop.basedata.service.IEopBasedataCustomerAccountService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class EopBasedataCustomerAccountDataAction extends ExtendAction {

    private IEopBasedataCustomerAccountService eopBasedataCustomerAccountService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataCustomerAccountService == null) {
            eopBasedataCustomerAccountService = (IEopBasedataCustomerAccountService) getBean("eopBasedataCustomerAccountService");
        }
        return eopBasedataCustomerAccountService;
    }
    
    public ActionForward getCustomerAccount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("getCustomerAccount", true, getClass());
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
                where += "(eopBasedataCustomerAccount.fdAccountName like :fdAccountName";
                hqlInfo.setParameter("fdAccountName", "%" + keyWord + "%");
                where += " or eopBasedataCustomerAccount.fdBankName like :fdBankName";
                hqlInfo.setParameter("fdBankName", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }

            String fdCustomerId = request.getParameter("fdCustomerId");
            if(StringUtil.isNotNull(fdCustomerId)){
                hqlInfo.setJoinBlock(" left join eopBasedataCustomerAccount.docMain doc");
                hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                        "doc.fdId=:fdCustomerId"));
                hqlInfo.setParameter("fdCustomerId", fdCustomerId);
            }
            HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataCustomerAccount.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("getCustomerAccount", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("getCustomerAccount");
        }
    }

}
