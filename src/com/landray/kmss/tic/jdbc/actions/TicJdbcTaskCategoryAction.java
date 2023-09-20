package com.landray.kmss.tic.jdbc.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.tic.jdbc.forms.TicJdbcTaskCategoryForm;
import com.landray.kmss.tic.jdbc.model.TicJdbcTaskCategory;
import com.landray.kmss.tic.jdbc.service.ITicJdbcTaskCategoryService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;


/**
 * 任务分类 Action
 * 
 * @author 
 * @version 1.0 2013-07-24
 */
public class TicJdbcTaskCategoryAction extends ExtendAction {
	protected ITicJdbcTaskCategoryService ticJdbcTaskCategoryService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if(ticJdbcTaskCategoryService == null) {
			ticJdbcTaskCategoryService = (ITicJdbcTaskCategoryService)getBean("ticJdbcTaskCategoryService");
		}
		return ticJdbcTaskCategoryService;
	}
	
	
	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String parentId = request.getParameter("parentId");
		TicJdbcTaskCategoryForm categoryForm = (TicJdbcTaskCategoryForm) super
				.createNewForm(mapping, form, request, response);
		if (StringUtil.isNotNull(parentId)) {
			TicJdbcTaskCategory category = (TicJdbcTaskCategory) getServiceImp(request)
					.findByPrimaryKey(parentId);
			if (category != null) {
				// 设置父类
				categoryForm.setFdParentId(category.getFdId());
				categoryForm.setFdParentName(category.getFdName());
			}
		}
		return categoryForm;
	}
	
	
	@Override
    public ActionForward deleteall(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String[] ids = request.getParameterValues("List_Selected_Node");

			if (ISysAuthConstant.IS_AREA_ENABLED) {
				String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids,
						request, "method=delete&fdId=${id}");
				int noAuthIdNum = ids.length - authIds.length;
				if (noAuthIdNum > 0) {
					messages.addMsg(new KmssMessage(
							"sys-authorization:area.batch.operation.info",
							noAuthIdNum));
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

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("tree", mapping, form, request, response);
		}
	}
}

