package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.service.IEopBasedataAccountsService;
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
import java.util.List;

public class EopBasedataAccountsDataAction extends BaseAction {

    private IEopBasedataAccountsService eopBasedataAccountsService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataAccountsService == null) {
            eopBasedataAccountsService = (IEopBasedataAccountsService) getBean("eopBasedataAccountsService");
        }
        return eopBasedataAccountsService;
    }

    public ActionForward fdAccount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("fdAccount", true, getClass());
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
                where += "(eopBasedataAccounts.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += " or eopBasedataAccounts.fdCode like :fdCode";
                hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                    "eopBasedataAccounts.fdIsAvailable=:fdIsAvailable"));
            hqlInfo.setParameter("fdIsAvailable", true);//启用
            String fdNotId=request.getParameter("fdNotId");
			if(StringUtil.isNotNull(fdNotId)){
				hqlInfo.setWhereBlock(
						StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataAccounts.fdId<>:fdNotId "));
				hqlInfo.setParameter("fdNotId", request.getParameter("fdNotId"));
			}
            String fdCompanyId=request.getParameter("fdCompanyId");
            if(StringUtil.isNotNull(fdCompanyId)){
                hqlInfo.setJoinBlock(" left join eopBasedataAccounts.fdCompanyList company");
                if(fdCompanyId.indexOf(";")>-1) {
                    hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                            "("+HQLUtil.buildLogicIN("company.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";"))))+" or company is null)");
                }else {
                    hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                            "(company.fdId=:fdCompanyId or company is null)"));
                    hqlInfo.setParameter("fdCompanyId", fdCompanyId);
                }
            }
            hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
            HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataAccounts.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("fdAccount", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("fdAccount");
        }
    }

    /**
     * 选择子级会计科目
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward fdMinLevel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("fdAccount", true, getClass());
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
                where += "(eopBasedataAccounts.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += " or eopBasedataAccounts.fdCode like :fdCode";
                hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            String fdCompanyId = request.getParameter("fdCompanyId");
            if(StringUtil.isNotNull(fdCompanyId)){
            	hqlInfo.setJoinBlock(" left join eopBasedataAccounts.fdCompanyList company");
                hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                        "(company.fdId=:fdCompanyId or company is null)"));
                hqlInfo.setParameter("fdCompanyId", fdCompanyId);
            }
            HQLInfo tempHql = new HQLInfo();
            tempHql.setSelectBlock(" eopBasedataAccounts.hbmParent.fdId ");
            tempHql.setWhereBlock(" eopBasedataAccounts.hbmParent.fdId is not null ");
            if(StringUtil.isNotNull(fdCompanyId)){
                tempHql.setJoinBlock(" left join eopBasedataAccounts.fdCompanyList comp");
                tempHql.setWhereBlock(StringUtil.linkString(tempHql.getWhereBlock(), " and ",
                        "(comp.fdId=:fdCompanyId or comp.fdId is null)"));
                tempHql.setParameter("fdCompanyId", fdCompanyId);
            }
            tempHql.setDistinctType(HQLInfo.DISTINCT_YES);
            List<String> tempList = getServiceImp(request).findValue(tempHql);
            if(!ArrayUtil.isEmpty(tempList)){
                hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                        HQLUtil.buildLogicIN(" eopBasedataAccounts.fdId not ", tempList)));//选择子级
            }
            hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                    "eopBasedataAccounts.fdIsAvailable=:fdIsAvailable"));
            hqlInfo.setParameter("fdIsAvailable", true);//有效
            HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataAccounts.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("fdAccount", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("fdAccount");
        }
    }
}
