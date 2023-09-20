package com.landray.kmss.tic.soap.sync.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.tic.soap.sync.service.ITicSoapSyncCategoryService;


/**
 * 配置/分类信息 Action
 * 
 * @author 
 * @version 1.0 2014-02-20
 */
public class TicSoapSyncCategoryAction extends ExtendAction {
	protected ITicSoapSyncCategoryService ticSoapSyncCategoryService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if(ticSoapSyncCategoryService == null) {
			ticSoapSyncCategoryService = (ITicSoapSyncCategoryService)getBean("ticSoapSyncCategoryService");
		}
		return ticSoapSyncCategoryService;
	}

	@Override
	public ActionForward deleteall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = super.deleteall(mapping, form, request, response);
		if ("failure".equals(forward.getName())) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("tree", mapping, form, request, response);
		}
		
	}

}

