package com.landray.kmss.fssc.expense.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainService;
import com.landray.kmss.fssc.expense.service.IFsscExpensePortalService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

/**
 * 报销申请porlet：获取当前登录人报销列表，porlet展现
* @author xiexingxing
* @date 2020年3月18日
 */
public class FsscExpenseMainPortletAction extends ExtendAction {
	protected IFsscExpenseMainService fsscExpenseMainService;

	@Override
    protected IFsscExpenseMainService getServiceImp(HttpServletRequest request) {
		if (fsscExpenseMainService == null) {
			fsscExpenseMainService = (IFsscExpenseMainService) getBean("fsscExpenseMainService");
		}
		return fsscExpenseMainService;
	}
	
	
	protected IFsscExpensePortalService fsscExpensePortalService;

	protected IFsscExpensePortalService getExpensePortalService(HttpServletRequest request) {
		if (fsscExpensePortalService == null) {
			fsscExpensePortalService = (IFsscExpensePortalService) getBean("fsscExpensePortalService");
		}
		return fsscExpensePortalService;
	}

	public ActionForward listPortlet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listPortlet", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			Page page = getServiceImp(request).listPortlet(request);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-listPortlet", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			// 视图展现方式:listtable(列表)
			return getActionForward("listPortlet", mapping, form, request,response);
		}
	}
	
	
	public ActionForward alreadyExpense(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-alreadyExpense", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json =  getExpensePortalService(request).alreadyExpense(UserUtil.getUser().getFdId());
			if(!json.isEmpty()) {
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json.toString());
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		return null;
	}
	
	
	public ActionForward expsenseIng(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-expsenseIng", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json =  getExpensePortalService(request).expsenseIng(UserUtil.getUser().getFdId());
			if(!json.isEmpty()) {
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json.toString());
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 费用总览
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward costContent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-costContent", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = getExpensePortalService(request).costContent();
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json.toString());
		} catch (Exception e) {
			messages.addError(e);
		}
		return null;
	}
	
	/**
	 * 加载费用总览头部统计
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadCostHead(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-loadCostHead", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject json = getExpensePortalService(request).loadCostHead();
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json.toString());
		} catch (Exception e) {
			messages.addError(e);
		}
		return null;
	}
}
