package com.landray.kmss.fssc.expense.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonDidiService;
import com.landray.kmss.fssc.expense.service.IFsscExpenseDidiDetailService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class FsscExpenseDidiDetailDataAction extends BaseAction {
	
	private IFsscCommonDidiService fsscCommonDidiService;

    public IFsscCommonDidiService getFsscCommonDidiService() {
    	if (fsscCommonDidiService == null) {
    		fsscCommonDidiService = (IFsscCommonDidiService) getBean("fsscDidiCommonService");
        }
		return fsscCommonDidiService;
	}

	private IFsscExpenseDidiDetailService fsscExpenseDidiDetailService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscExpenseDidiDetailService == null) {
            fsscExpenseDidiDetailService = (IFsscExpenseDidiDetailService) getBean("fsscExpenseDidiDetailService");
        }
        return fsscExpenseDidiDetailService;
    }
    
    public ActionForward getDidiDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("keyword", keyWord);
            params.put("ids",  request.getParameter("ids"));
            Page page = getFsscCommonDidiService().getOrderPage(hqlInfo, params);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("getDidiDetail");
        }
    }
    
}
