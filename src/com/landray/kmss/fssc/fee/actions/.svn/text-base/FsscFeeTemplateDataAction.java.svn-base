package com.landray.kmss.fssc.fee.actions;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonExpenseService;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.fssc.fee.service.IFsscFeeTemplateService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class FsscFeeTemplateDataAction extends BaseAction {

    private IFsscFeeTemplateService fsscFeeTemplateService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscFeeTemplateService == null) {
            fsscFeeTemplateService = (IFsscFeeTemplateService) getBean("fsscFeeTemplateService");
        }
        return fsscFeeTemplateService;
    }
    
    private IFsscCommonExpenseService fsscExpenseCommonService;
    
    public IFsscCommonExpenseService getFsscExpenseCommonService() {
    	if(fsscExpenseCommonService==null){
    		fsscExpenseCommonService = (IFsscCommonExpenseService) getBean("fsscExpenseCommonService");
    	}
		return fsscExpenseCommonService;
	}

    public ActionForward getTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("getTemplate", true, getClass());
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
                where += "(fsscFeeTemplate.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            String source=request.getParameter("source");
            if("mobile".equals(source)){
           	 String where = hqlInfo.getWhereBlock();
                where =StringUtil.linkString(where, " and ", " fsscFeeTemplate.fdIsMobile=:fdIsMobile");
                hqlInfo.setParameter("fdIsMobile", true);  //移动端使用
                hqlInfo.setWhereBlock(where);
           }
            HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscFeeTemplate.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("getTemplate", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("getTemplate");
        }
    }
    
    public ActionForward getExpenseCategory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	 TimeCounter.logCurrentTime("getExpenseCategory", true, getClass());
         KmssMessages messages = new KmssMessages();
         String s_pageno = request.getParameter("pageno");
         String s_rowsize = request.getParameter("rowsize");
         String keyWord = request.getParameter("q._keyword");
         String source=request.getParameter("source");
         String docTemplateId=request.getParameter("docTemplateId");
         String connFlag=request.getParameter("connFlag");
         List<String> list=null;
         if(StringUtil.isNotNull(docTemplateId)){
             list = Arrays.asList(docTemplateId.split(","));
         }
         try {
             Page page = getFsscExpenseCommonService().getExpenseCategoryHql(request,s_pageno, s_rowsize, keyWord, source, list, connFlag);
             request.setAttribute("queryPage", page);
         } catch (Exception e) {
             messages.addError(e);
         }
         TimeCounter.logCurrentTime("getExpenseCategory", false, getClass());
         if (messages.hasError()) {
             KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
             return mapping.findForward("failure");
         } else {
             return mapping.findForward("getExpenseCategory");
         }
    }
}
