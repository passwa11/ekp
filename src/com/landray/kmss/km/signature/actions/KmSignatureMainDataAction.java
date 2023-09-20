package com.landray.kmss.km.signature.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.signature.model.KmSignatureMain;
import com.landray.kmss.km.signature.service.IKmSignatureMainService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class KmSignatureMainDataAction extends ExtendAction {


	protected IKmSignatureMainService signatureService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (signatureService == null) {
            signatureService = (IKmSignatureMainService) getBean("kmSignatureMainService");
        }
		return signatureService;
	}
	
	public ActionForward relativelist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("relativelist", true, getClass());
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
			String where = " (kmSignatureMain.fdIsAvailable=:fdIsAvailable or kmSignatureMain.fdIsAvailable is null)";
			hqlInfo.setParameter("fdIsAvailable",Boolean.TRUE);
			hqlInfo.setJoinBlock(" left join kmSignatureMain.fdUsers fdUsers");
			where += "and " + HQLUtil.buildLogicIN("fdUsers.fdId",
					UserUtil.getKMSSUser()
							.getUserAuthInfo().getAuthOrgIds());
			// hqlInfo.setParameter("creatorId", UserUtil.getUser().getFdId());
			if (StringUtil.isNotNull(keyWord)) {
				where += "and kmSignatureMain.fdMarkName like :fdMarkName";
				hqlInfo.setParameter("fdMarkName", "%" + keyWord + "%");
			}
			hqlInfo.setWhereBlock(where);
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
			HQLHelper.by(request).buildHQLInfo(hqlInfo, KmSignatureMain.class);
			Page page = getServiceImp(request).findPage(hqlInfo);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("relativelist", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("relativelist");
		}
	}

}
