package com.landray.kmss.eop.basedata.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.model.EopBasedataAccount;
import com.landray.kmss.eop.basedata.service.IEopBasedataAccountService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class EopBasedataAccountDataAction extends BaseAction {
    private IEopBasedataAccountService eopBasedataAccountService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataAccountService == null) {
        	eopBasedataAccountService = (IEopBasedataAccountService) getBean("eopBasedataAccountService");
        }
        return eopBasedataAccountService;
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
                where += "(eopBasedataAccount.fdBankAccount like :keyWord";
                hqlInfo.setParameter("keyWord", "%" + keyWord + "%");
                where += " or eopBasedataAccount.fdBankName like :keyWord";
                where += " or eopBasedataAccount.fdName like :keyWord";
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            String fdIsAuthorize=EopBasedataFsscUtil.getSwitchValue("fdIsAuthorize");
            if(StringUtil.isNull(fdIsAuthorize)||"true".equals(fdIsAuthorize)) {//启用提单转授权或者未配置，都过滤
            	hqlInfo.setWhereBlock(
    					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataAccount.fdPerson.fdId in (select t.fdId from SysOrgPerson t where ( t.fdId in (select auth.fdAuthorizedBy.fdId from EopBasedataAuthorize auth left join auth.fdToOrg org where org.fdId=:userId) or t.fdId=:userId)) "));
            	 if(StringUtil.isNotNull(request.getParameter("fdPersonId"))){
              	   hqlInfo.setParameter("userId",request.getParameter("fdPersonId"));
                 }else {
              	   hqlInfo.setParameter("userId",UserUtil.getUser().getFdId());
                 }
            }
            hqlInfo.setWhereBlock(
            		StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataAccount.fdIsAvailable=:fdIsAvailable "));
			String valid = request.getParameter("valid");
			if(StringUtil.isNotNull(valid)){
				hqlInfo.setParameter("fdIsAvailable",Boolean.valueOf(valid));
			}else{
				hqlInfo.setParameter("fdIsAvailable",true);
			}
            HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataAccount.class);
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
