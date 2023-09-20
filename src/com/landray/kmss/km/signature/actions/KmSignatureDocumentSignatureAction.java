package com.landray.kmss.km.signature.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.signature.service.IKmSignatureDocumentSignatureService;

/**
 * 文档印章库 Action
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class KmSignatureDocumentSignatureAction extends ExtendAction {
	protected IKmSignatureDocumentSignatureService documentSignatureService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (documentSignatureService == null) {
            documentSignatureService = (IKmSignatureDocumentSignatureService) getBean("kmSignatureDocumentSignatureService");
        }
		return documentSignatureService;
	}
}
