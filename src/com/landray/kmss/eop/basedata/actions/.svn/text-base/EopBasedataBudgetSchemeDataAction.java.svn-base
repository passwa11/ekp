package com.landray.kmss.eop.basedata.actions;

import javax.servlet.http.HttpServletResponse;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.sunbor.web.tag.Page;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.eop.basedata.service.IEopBasedataBudgetSchemeService;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.BaseAction;

public class EopBasedataBudgetSchemeDataAction extends BaseAction {

    private IEopBasedataBudgetSchemeService eopBasedataBudgetSchemeService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataBudgetSchemeService == null) {
            eopBasedataBudgetSchemeService = (IEopBasedataBudgetSchemeService) getBean("eopBasedataBudgetSchemeService");
        }
        return eopBasedataBudgetSchemeService;
    }

    public ActionForward fdCategory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("fdCategory", true, getClass());
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
                where += "(eopBasedataBudgetScheme.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += " or eopBasedataBudgetScheme.fdType like :fdType";
                hqlInfo.setParameter("fdType", "%" + keyWord + "%");
                where += " or eopBasedataBudgetScheme.fdTarget like :fdTarget";
                hqlInfo.setParameter("fdTarget", "%" + keyWord + "%");
                where += " or eopBasedataBudgetScheme.fdPeriod like :fdPeriod";
                hqlInfo.setParameter("fdPeriod", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataBudgetScheme.fdIsAvailable=:fdIsAvailable "));
			String valid = request.getParameter("valid");
			if(StringUtil.isNotNull(valid)){
				hqlInfo.setParameter("fdIsAvailable",Boolean.valueOf(valid));
			}else{
				hqlInfo.setParameter("fdIsAvailable",true);
			}
            HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataBudgetScheme.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("fdCategory", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("fdCategory");
        }
    }
}
