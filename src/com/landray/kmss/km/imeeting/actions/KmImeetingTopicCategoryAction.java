package com.landray.kmss.km.imeeting.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.imeeting.service.IKmImeetingTopicCategoryService;
import com.landray.kmss.sys.simplecategory.actions.SysSimpleCategoryAction;


public class KmImeetingTopicCategoryAction extends SysSimpleCategoryAction {
	protected IKmImeetingTopicCategoryService kmImeetingTopicCategoryService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmImeetingTopicCategoryService == null) {
            kmImeetingTopicCategoryService = (IKmImeetingTopicCategoryService) getBean(
                    "kmImeetingTopicCategoryService");
        }
		return kmImeetingTopicCategoryService;
	}

	protected String getParentProperty() {
		return "hbmParent";
	}

}
