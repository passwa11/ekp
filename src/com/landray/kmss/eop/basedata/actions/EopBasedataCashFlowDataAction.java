package com.landray.kmss.eop.basedata.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.model.EopBasedataCashFlow;
import com.landray.kmss.eop.basedata.service.IEopBasedataCashFlowService;
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

public class EopBasedataCashFlowDataAction extends EopBasedataBusinessAction {

    private IEopBasedataCashFlowService eopBasedataCashFlowService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataCashFlowService == null) {
            eopBasedataCashFlowService = (IEopBasedataCashFlowService) getBean("eopBasedataCashFlowService");
        }
        return eopBasedataCashFlowService;
    }

	public ActionForward fdParent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("fdParent", true, getClass());
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
				where += "(eopBasedataCashFlow.fdName like :fdName";
				hqlInfo.setParameter("fdName", "%" + keyWord + "%");
				where += " or eopBasedataCashFlow.fdCode like :fdCode";
				hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
				where += ")";
				hqlInfo.setWhereBlock(where);
			}
			String fdNotId = request.getParameter("fdNotId");
			if(StringUtil.isNotNull(fdNotId)){
				hqlInfo.setWhereBlock(
						StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataCashFlow.fdId<>:fdNotId "));
				hqlInfo.setParameter("fdNotId", fdNotId);
			}
			String fdCompanyId = request.getParameter("fdCompanyId");
            if(StringUtil.isNotNull(fdCompanyId)){
            	hqlInfo.setJoinBlock(" left join eopBasedataCashFlow.fdCompanyList comp");
				if(fdCompanyId.indexOf(";")>-1) {
					hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
							"("+ HQLUtil.buildLogicIN("comp.fdId", ArrayUtil.convertArrayToList(fdCompanyId.split(";"))))+" or comp is null)");
				}else {
					hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
							"(comp.fdId=:fdCompanyId or comp is null)"));
					hqlInfo.setParameter("fdCompanyId", fdCompanyId);
				}
            }
			hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataCashFlow.fdIsAvailable=:fdIsAvailable "));
			String valid = request.getParameter("valid");
			if(StringUtil.isNotNull(valid)){
				hqlInfo.setParameter("fdIsAvailable",Boolean.valueOf(valid));
			}else{
				hqlInfo.setParameter("fdIsAvailable",true);
			}
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
			HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataCashFlow.class);
			Page page = getServiceImp(request).findPage(hqlInfo);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("fdParent", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("fdParent");
		}
    }
}
