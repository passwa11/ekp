package com.landray.kmss.km.signature.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.signature.forms.KmSignatureDocumentSignatureForm;
import com.landray.kmss.km.signature.model.KmSignatureDocumentSignature;
import com.landray.kmss.km.signature.service.IKmSignatureDocumentSignatureService;

/**
 * 文档印章库业务接口实现
 * 
 * @author weiby
 * @version 1.0 2013-09-23
 */
public class KmSignatureDocumentSignatureServiceImp extends BaseServiceImp implements
		IKmSignatureDocumentSignatureService {

	@Override
	public IExtendForm convertModelToForm(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
		KmSignatureDocumentSignatureForm signatureForm = (KmSignatureDocumentSignatureForm) super
				.convertModelToForm(form, model, requestContext);
		return signatureForm;
	}

	@Override
	public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
		KmSignatureDocumentSignature baseModel = (KmSignatureDocumentSignature) super
				.convertFormToModel(form, model, requestContext);
		return baseModel;
	}
}
