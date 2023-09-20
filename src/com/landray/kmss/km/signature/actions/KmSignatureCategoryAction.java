package com.landray.kmss.km.signature.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.signature.forms.KmSignatureCategoryForm;
import com.landray.kmss.km.signature.service.IKmSignatureCategoryService;
import com.landray.kmss.sys.simplecategory.actions.SysSimpleCategoryAction;

/**
 * 签章分类 Action
 * 
 * @author
 * @version 1.0 2014-08-10
 */
public class KmSignatureCategoryAction extends SysSimpleCategoryAction {
	
	protected IKmSignatureCategoryService kmSignatureCategoryService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmSignatureCategoryService == null) {
            kmSignatureCategoryService = (IKmSignatureCategoryService) getBean("kmSignatureCategoryService");
        }
		return kmSignatureCategoryService;
	}
	
	protected String getParentProperty() {
		return "hbmParent";
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmSignatureCategoryForm kmSignatureMainForm = (KmSignatureCategoryForm) super
				.createNewForm(mapping, form, request, response);
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
		//java.util.Date date = new java.util.Date();  
		//String docCreateTime = sdf.format(date);  
		//kmSignatureMainForm.setDocCreateTime(docCreateTime);
		//kmSignatureMainForm.setDocCreatorName(UserUtil.getUser().getFdId());

		return kmSignatureMainForm;
	}
}
