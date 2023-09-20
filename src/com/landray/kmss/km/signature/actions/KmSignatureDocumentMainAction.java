package com.landray.kmss.km.signature.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.signature.service.IKmSignatureDocumentMainService;

/**
 * 文档库表 Action
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class KmSignatureDocumentMainAction extends ExtendAction {
	protected IKmSignatureDocumentMainService documentService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (documentService == null) {
            documentService = (IKmSignatureDocumentMainService) getBean("kmSignatureDocumentMainService");
        }
		return documentService;
	}
}
