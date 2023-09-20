package com.landray.kmss.fssc.budget.actions;

import javax.servlet.http.HttpServletResponse;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.sunbor.web.tag.Page;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.fssc.budget.service.IFsscBudgetMainService;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.fssc.budget.model.FsscBudgetMain;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.BaseAction;

public class FsscBudgetMainDataAction extends BaseAction {

    private IFsscBudgetMainService fsscBudgetMainService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscBudgetMainService == null) {
            fsscBudgetMainService = (IFsscBudgetMainService) getBean("fsscBudgetMainService");
        }
        return fsscBudgetMainService;
    }

    public ActionForward fdMain(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("fdMain", true, getClass());
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
                where += "(fsscBudgetMain.fdDesc like :fdDesc";
                hqlInfo.setParameter("fdDesc", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscBudgetMain.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("fdMain", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("fdMain");
        }
    }
}
