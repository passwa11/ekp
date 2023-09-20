package com.landray.kmss.km.signature.service.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.signature.service.IDeleteSigDomService;
import com.landray.kmss.km.signature.service.IKmSignatureDocumentSignatureService;

public class DeleteSigDomServiceImp extends BaseServiceImp implements
		IDeleteSigDomService {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	IKmSignatureDocumentSignatureService kmSignatureDocumentSignatureService;

	public void setKmSignatureDocumentSignatureService(
			IKmSignatureDocumentSignatureService kmSignatureDocumentSignatureService) {
		this.kmSignatureDocumentSignatureService = kmSignatureDocumentSignatureService;
	}

	public IKmSignatureDocumentSignatureService getKmSignatureDocumentSignatureService() {
		return kmSignatureDocumentSignatureService;
	}

	// 定时删除无用的documentSignature
	@Override
    public void add() throws Exception {
		logger.info("定时任务--------删除无用的documentSignature");
	}

}
