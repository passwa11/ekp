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
import com.landray.kmss.eop.basedata.service.IEopBasedataVoucherTypeService;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.eop.basedata.model.EopBasedataVoucherType;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.BaseAction;

public class EopBasedataVoucherTypeDataAction extends BaseAction {

    private IEopBasedataVoucherTypeService eopBasedataVoucherTypeService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataVoucherTypeService == null) {
            eopBasedataVoucherTypeService = (IEopBasedataVoucherTypeService) getBean("eopBasedataVoucherTypeService");
        }
        return eopBasedataVoucherTypeService;
    }

    public ActionForward getVoucherType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("getVoucherType", true, getClass());
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
                where += "(eopBasedataVoucherType.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += " or eopBasedataVoucherType.fdCode like :fdCode";
                hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            String fdCompanyId = request.getParameter("fdCompanyId");
            if(StringUtil.isNotNull(fdCompanyId)){
            	hqlInfo.setJoinBlock(" left join eopBasedataVoucherType.fdCompanyList comp ");
                hqlInfo.setWhereBlock(
                        StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " (comp.fdId = :fdCompanyId or comp.fdId is null) "));
                hqlInfo.setParameter("fdCompanyId", fdCompanyId);
            }
            HQLHelper.by(request).where("fdIsAvailable").eq(Boolean.valueOf("true")).buildHQLInfo(hqlInfo, EopBasedataVoucherType.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("getVoucherType", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("selectVoucherType");
            
        }
    }
}
