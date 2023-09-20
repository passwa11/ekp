package com.landray.kmss.fssc.expense.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.fssc.expense.model.FsscExpenseTranData;
import com.landray.kmss.fssc.expense.service.IFsscExpenseTranDataService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FsscExpenseTranDataAction extends ExtendAction {

    private IFsscExpenseTranDataService fsscExpenseTranDataService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscExpenseTranDataService == null) {
            fsscExpenseTranDataService = (IFsscExpenseTranDataService) getBean("fsscExpenseTranDataService");
        }
        return fsscExpenseTranDataService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscExpenseTranData.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForward data(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String iden = request.getParameter("iden");
        if (StringUtil.isNull(iden)) {
            iden = "data";
        }
        TimeCounter.logCurrentTime("Action-list", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String s_pageno = request.getParameter("pageno");
            String s_rowsize = request.getParameter("rowsize");
            String orderby = request.getParameter("orderby");
            String ordertype = request.getParameter("ordertype");
            boolean isReserve = false;
            if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
                isReserve = true;
            }
            int pageno = 0;
            int rowsize = com.landray.kmss.sys.config.model.SysConfigParameters.getRowSize();
            if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
                pageno = Integer.parseInt(s_pageno);
            }
            if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
                rowsize = Integer.parseInt(s_rowsize);
            }
            if (isReserve) {
                orderby += " desc";
            }
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setOrderBy(orderby);
            hqlInfo.setPageNo(pageno);
            hqlInfo.setRowSize(rowsize);
            changeFindPageHQLInfo(request, hqlInfo);
            com.sunbor.web.tag.Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("Action-list", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward(iden, mapping, form, request, response);
        }
    }
}
