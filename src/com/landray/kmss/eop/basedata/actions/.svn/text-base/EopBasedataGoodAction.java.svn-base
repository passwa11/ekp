package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.forms.EopBasedataGoodForm;
import com.landray.kmss.eop.basedata.model.EopBasedataGood;
import com.landray.kmss.eop.basedata.service.IEopBasedataGoodService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EopBasedataGoodAction extends ExtendAction {

    private IEopBasedataGoodService eopBasedataGoodService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataGoodService == null) {
            eopBasedataGoodService = (IEopBasedataGoodService) getBean("eopBasedataGoodService");
        }
        return eopBasedataGoodService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataGood.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataGoodForm eopBasedataGoodForm = (EopBasedataGoodForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataGoodService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        String fdId = request.getParameter("parentId");
        String copyId = request.getParameter("copyId");
        if (!StringUtil.isNull(fdId)) {
            EopBasedataGood eopBasedataGood = (EopBasedataGood) getServiceImp(request).findByPrimaryKey(fdId);
            eopBasedataGoodForm.setFdParentId(eopBasedataGood.getFdId());
            eopBasedataGoodForm.setFdParentName(eopBasedataGood.getFdName());
        }
        if (!StringUtil.isNull(copyId)) {
            EopBasedataGood eopBasedataGood = (EopBasedataGood) getServiceImp(request).findByPrimaryKey(copyId);
            String id = eopBasedataGoodForm.getFdId();
            eopBasedataGoodForm = (EopBasedataGoodForm) getServiceImp(request).cloneModelToForm(null, eopBasedataGood, new RequestContext());
            eopBasedataGoodForm.setFdId(id);
            eopBasedataGoodForm.setFdName(null);
            ((ExtendForm) eopBasedataGoodForm).setMethod("add");
            ((ExtendForm) eopBasedataGoodForm).setMethod_GET("add");
            eopBasedataGoodForm.setFdOrder(null);
        }
        return eopBasedataGoodForm;
    }

    public ActionForward getGood(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("getInvoiceGood", true, getClass());
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
                where += "(eopBasedataGood.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += " or eopBasedataGood.fdCode like :fdCode";
                hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("getInvoiceGood", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("getInvoiceGood");
        }
    }
}
