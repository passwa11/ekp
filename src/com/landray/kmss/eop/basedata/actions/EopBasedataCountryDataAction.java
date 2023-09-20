package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.model.EopBasedataCountry;
import com.landray.kmss.eop.basedata.service.IEopBasedataCountryService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EopBasedataCountryDataAction extends BaseAction {

    private IEopBasedataCountryService eopBasedataCountryService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataCountryService == null) {
            eopBasedataCountryService = (IEopBasedataCountryService) getBean("eopBasedataCountryService");
        }
        return eopBasedataCountryService;
    }

    public ActionForward getCountry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("getCountry", true, getClass());
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
                where += "(eopBasedataCountry.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += " or eopBasedataCountry.fdCode like :fdCode";
                hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                    "eopBasedataCountry.fdIsAvailable=:fdIsAvailable"));
            hqlInfo.setParameter("fdIsAvailable", true);//启用
            String fdCompanyId = request.getParameter("fdCompanyId");
            if(StringUtil.isNotNull(fdCompanyId)){
                hqlInfo.setJoinBlock(" left join eopBasedataCountry.fdCompanyList comp");
                if(fdCompanyId.indexOf(";")>-1) {
                    hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                            "("+ HQLUtil.buildLogicIN("comp.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";"))))+" or comp is null)");
                }else {
                    hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                            "(comp.fdId=:fdCompanyId or comp is null)"));
                    hqlInfo.setParameter("fdCompanyId", fdCompanyId);
                }
            }
            hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
            HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataCountry.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("getCountry", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("getCountry");
        }
    }

}
