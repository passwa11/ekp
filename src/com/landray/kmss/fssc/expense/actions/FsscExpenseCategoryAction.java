package com.landray.kmss.fssc.expense.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonFeeService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.expense.forms.FsscExpenseCategoryForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseCategory;
import com.landray.kmss.fssc.expense.service.IFsscExpenseCategoryService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.simplecategory.actions.SysSimpleCategoryAction;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class FsscExpenseCategoryAction extends SysSimpleCategoryAction {
	
	private IFsscCommonFeeService fsscCommonFeeService;

    public IFsscCommonFeeService getFsscCommonFeeService() {
    	if(fsscCommonFeeService==null){
    		fsscCommonFeeService = (IFsscCommonFeeService) SpringBeanUtil.getBean("fsscCommonFeeService");
    	}
		return fsscCommonFeeService;
	}
	private IFsscExpenseCategoryService fsscExpenseCategoryService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscExpenseCategoryService == null) {
            fsscExpenseCategoryService = (IFsscExpenseCategoryService) getBean("fsscExpenseCategoryService");
        }
        return fsscExpenseCategoryService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscExpenseCategory.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscExpenseCategoryForm fsscExpenseCategoryForm = (FsscExpenseCategoryForm) super.createNewForm(mapping, form, request, response);
        ((IFsscExpenseCategoryService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscExpenseCategoryForm;
    }
    /**
     * 报销分类选择事前模板
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward selectFeeTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("selectFeeTemplate", true, getClass());
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
            Page page = new Page();
            page.setPageno(pageno);
            page.setRowsize(rowsize);
            if(FsscCommonUtil.checkHasModule("/fssc/fee/")){
            	getFsscCommonFeeService().getFeeTemplatePage(page,keyWord);
            }else{
            	page.setList(new ArrayList());
            }
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("selectFeeTemplate", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("selectFeeTemplate");
        }
    }
}
