package com.landray.kmss.km.signature.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.signature.service.IKmSignatureDocumentHistoryService;
import com.landray.kmss.util.StringUtil;

/**
 * 文档印章历史库 Action
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class KmSignatureDocumentHistoryAction extends ExtendAction {
	protected IKmSignatureDocumentHistoryService kmSignatureDocumentHistoryService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmSignatureDocumentHistoryService == null) {
            kmSignatureDocumentHistoryService = (IKmSignatureDocumentHistoryService) getBean("kmSignatureDocumentHistoryService");
        }
		return kmSignatureDocumentHistoryService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String fdMarkName = request.getParameter("fdMarkName");
		if (fdMarkName != null) {
			hqlInfo.setWhereBlock(StringUtil.linkString(
					hqlInfo.getWhereBlock(), " and ",
					"kmSignatureDocumentHistory.fdMarkName='" + fdMarkName
							+ "'"));
		}

	}

}
