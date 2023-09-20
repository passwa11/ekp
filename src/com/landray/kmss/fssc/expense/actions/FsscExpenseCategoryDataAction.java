package com.landray.kmss.fssc.expense.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonPaymentService;
import com.landray.kmss.fssc.expense.model.FsscExpenseCategory;
import com.landray.kmss.fssc.expense.service.IFsscExpenseCategoryService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class FsscExpenseCategoryDataAction extends BaseAction {

    private IFsscExpenseCategoryService fsscExpenseCategoryService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscExpenseCategoryService == null) {
            fsscExpenseCategoryService = (IFsscExpenseCategoryService) getBean("fsscExpenseCategoryService");
        }
        return fsscExpenseCategoryService;
    }

    private IFsscCommonPaymentService fsscCommonPaymentService;

    public IFsscCommonPaymentService getFsscCommonPaymentService() {
        if (fsscCommonPaymentService == null) {
            fsscCommonPaymentService = (IFsscCommonPaymentService) getBean("fsscCommonPaymentService");
        }
        return fsscCommonPaymentService;
    }

    public ActionForward getCategory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("getCategory", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String fdShareType = request.getParameter("fdShareType");
            Page page = new Page();
            if("2".equals(fdShareType)){	//事后分摊选择付款分类
                page = getFsscCommonPaymentService().findPaymentCategoryPage(request);
            }else{
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
                    where += "(fsscExpenseCategory.fdName like :fdName";
                    hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                    where += ")";
                    hqlInfo.setWhereBlock(where);
                }
                String source=request.getParameter("source");
                if("mobile".equals(source)){
                    String where = hqlInfo.getWhereBlock();
                    where =StringUtil.linkString(where, " and ", " fsscExpenseCategory.fdIsMobile=:fdIsMobile");
                    hqlInfo.setParameter("fdIsMobile", true);  //移动端使用
                    hqlInfo.setWhereBlock(where);
                }
                HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscExpenseCategory.class);
                page = getServiceImp(request).findPage(hqlInfo);
            }
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("getCategory", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("getCategory");
        }
    }
}
