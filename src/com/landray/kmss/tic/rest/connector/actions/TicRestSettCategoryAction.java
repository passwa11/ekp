package com.landray.kmss.tic.rest.connector.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.tic.rest.connector.forms.TicRestSettCategoryForm;
import com.landray.kmss.tic.rest.connector.service.ITicRestSettCategoryService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 分类信息 Action
 */
public class TicRestSettCategoryAction extends ExtendAction {
	protected ITicRestSettCategoryService ticRestSettCategoryService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if (ticRestSettCategoryService == null) {
			ticRestSettCategoryService = (ITicRestSettCategoryService) getBean("ticRestSettCategoryService");
		}
		return ticRestSettCategoryService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String parentId = request.getParameter("parentId");
		TicRestSettCategoryForm categoryForm = (TicRestSettCategoryForm) super.createNewForm(mapping, form, request,
				response);
		if (StringUtil.isNotNull(parentId)) {
			TicRestSettCategoryForm category = (TicRestSettCategoryForm) getServiceImp(request).findByPrimaryKey(parentId);
			if (category != null) {
				// 设置父类
				categoryForm.setFdParentId(category.getFdId());
				categoryForm.setFdParentName(category.getFdName());
			}
		}
		return categoryForm;
	}

	@Override
    public ActionForward deleteall(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String[] ids = request.getParameterValues("List_Selected_Node");

			if (ISysAuthConstant.IS_AREA_ENABLED) {
				String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids, request, "method=delete&fdId=${id}");
				int noAuthIdNum = ids.length - authIds.length;
				if (noAuthIdNum > 0) {
					messages.addMsg(new KmssMessage("sys-authorization:area.batch.operation.info", noAuthIdNum));
				}

				if (!ArrayUtils.isEmpty(authIds)) {
					getServiceImp(request).delete(authIds);
				}
			} else if (ids != null) {
				getServiceImp(request).delete(ids);
			}

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN)
				.save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("tree", mapping, form, request, response);
		}
	}

}
