package com.landray.kmss.eop.basedata.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.model.EopBasedataErpPerson;
import com.landray.kmss.eop.basedata.service.IEopBasedataErpPersonService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class EopBasedataErpPersonDataAction extends BaseAction {

    private IEopBasedataErpPersonService eopBasedataErpPersonService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataErpPersonService == null) {
            eopBasedataErpPersonService = (IEopBasedataErpPersonService) getBean("eopBasedataErpPersonService");
        }
        return eopBasedataErpPersonService;
    }

    public ActionForward fdERPPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("fdERPPerson", true, getClass());
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
                where += "(eopBasedataErpPerson.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += " or eopBasedataErpPerson.fdCode like :fdCode";
                hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                    "eopBasedataErpPerson.fdIsAvailable=:fdIsAvailable"));
            hqlInfo.setParameter("fdIsAvailable", true);

            String fdCompanyId = request.getParameter("fdCompanyId");
            if(StringUtil.isNotNull(fdCompanyId)){
            	hqlInfo.setJoinBlock(" left join eopBasedataErpPerson.fdCompanyList company");
                hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                        "(company.fdId=:fdCompanyId or company is null)"));
                hqlInfo.setParameter("fdCompanyId", fdCompanyId);
            }
            HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataErpPerson.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("fdERPPerson", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("fdERPPerson");
        }
    }
}
