package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.forms.EopBasedataOutTaxForm;
import com.landray.kmss.eop.basedata.model.EopBasedataOutTax;
import com.landray.kmss.eop.basedata.service.IEopBasedataOutTaxService;
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

public class EopBasedataOutTaxAction extends EopBasedataBusinessAction {

    private IEopBasedataOutTaxService eopBasedataOutTaxService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataOutTaxService == null) {
            eopBasedataOutTaxService = (IEopBasedataOutTaxService) getBean("eopBasedataOutTaxService");
        }
        return eopBasedataOutTaxService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataOutTax.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.eop.basedata.util.EopBasedataUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.eop.basedata.model.EopBasedataOutTax.class);
        com.landray.kmss.eop.basedata.util.EopBasedataUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataOutTaxForm eopBasedataOutTaxForm = (EopBasedataOutTaxForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataOutTaxService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataOutTaxForm;
    }

    public ActionForward getOutTax(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("getOutTax", true, getClass());
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
                where += " ( cast(eopBasedataOutTax.fdRate,string) like :fdRate";
                hqlInfo.setParameter("fdRate", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            String fdCompanyId = request.getParameter("fdCompanyId");
            if(StringUtil.isNotNull(fdCompanyId)) {
                hqlInfo.setJoinBlock(" left join eopBasedataOutTax.fdCompanyList comp");
                hqlInfo.setWhereBlock(
                        StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " (comp.fdId=:fdCompanyId or comp.fdId is null)"));
                hqlInfo.setParameter("fdCompanyId",fdCompanyId);
            }
            hqlInfo.setWhereBlock(
                    StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataOutTax.fdIsAvailable=:fdIsAvailable "));
            String valid = request.getParameter("valid");
            if(StringUtil.isNotNull(valid)){
                hqlInfo.setParameter("fdIsAvailable",Boolean.valueOf(valid));
            }else{
                hqlInfo.setParameter("fdIsAvailable",true);
            }
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("getOutTax", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
                    .save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("getOutTax");
        }
    }
}
