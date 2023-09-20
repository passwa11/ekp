package com.landray.kmss.tic.rest.connector.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.simplecategory.actions.SysSimpleCategoryAction;
import com.landray.kmss.tic.rest.connector.forms.TicRestCategoryForm;
import com.landray.kmss.tic.rest.connector.model.TicRestCategory;
import com.landray.kmss.tic.rest.connector.service.ITicRestCategoryService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

/**
 * REST服务分类 Action
 */
public class TicRestCategoryAction extends SysSimpleCategoryAction {
	private ITicRestCategoryService ticRestCategoryService;

	@Override
    public IBaseService getServiceImp(HttpServletRequest request) {
		if (ticRestCategoryService == null) {
			ticRestCategoryService = (ITicRestCategoryService) getBean(
					"ticRestCategoryService");
		}
		return ticRestCategoryService;
	}

	@Override
    public void changeFindPageHQLInfo(HttpServletRequest request,
                                      HQLInfo hqlInfo) throws Exception {
		HQLHelper.by(request).buildHQLInfo(hqlInfo, TicRestCategory.class);
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
	}

	@Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TicRestCategoryForm ticRestCategoryForm = (TicRestCategoryForm) super.createNewForm(
				mapping, form, request, response);
		((ITicRestCategoryService) getServiceImp(request)).initFormSetting(
				(IExtendForm) form, new RequestContext(request));
		return ticRestCategoryForm;
	}
}
