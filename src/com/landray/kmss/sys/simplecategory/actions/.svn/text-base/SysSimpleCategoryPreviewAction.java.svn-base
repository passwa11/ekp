package com.landray.kmss.sys.simplecategory.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryPreviewManageService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 分类概览action
 * 
 * @author Administrator
 * 
 */
public class SysSimpleCategoryPreviewAction extends BaseAction {

	/**
	 * 获取概览的内容
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getContent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-preview", true, getClass());
		KmssMessages messages = new KmssMessages();
		boolean allowLogOper = UserOperHelper.allowLogOper("getContent", null);
		if(allowLogOper){
		    UserOperHelper.appendEventType(ResourceUtil.getString("menu.sysSimpleCategory.overview","sys-simplecategory"));
		}
		String previewManagerServiceName = request.getParameter("service");
		String categoryId = request.getParameter("currid");
		if (StringUtil.isNotNull(previewManagerServiceName)) {
			try {
				ISysSimpleCategoryPreviewManageService previewManagerService = (ISysSimpleCategoryPreviewManageService) getBean(
						previewManagerServiceName);
				if (previewManagerService != null) {
					JSONArray jsonArray = previewManagerService.generateJSONCategoryPreviewContent(categoryId);
					if(allowLogOper){
					    UserOperHelper.logMessage(jsonArray.toString());
					}
					request.setAttribute("lui-source", jsonArray);
					TimeCounter.logCurrentTime("Action-preview", true, getClass());
				}
			} catch (Exception e) {
				e.printStackTrace();
				messages.addError(e);
			}
		}
		if (messages.hasError()) {
			return mapping.findForward("lui-failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	/**
	 * 获取思维导图概览的内容
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getXMLContent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String previewManagerServiceName = request.getParameter("service");
		String categoryId = request.getParameter("currid");
		boolean allowLogOper = UserOperHelper.allowLogOper("getXMLContent", null);
        if(allowLogOper){
            UserOperHelper.appendEventType(ResourceUtil.getString("menu.sysSimpleCategory.getXMLContent","sys-simplecategory"));
        }
		if (StringUtil.isNotNull(previewManagerServiceName)) {
			try {
				ISysSimpleCategoryPreviewManageService previewManagerService = (ISysSimpleCategoryPreviewManageService) getBean(
						previewManagerServiceName);
				if (previewManagerService != null) {
					JSONArray preContent = previewManagerService.generateCategoryPreviewContent(categoryId);
					String content = null;
					if ("h5".equals(request.getParameter("fdType"))) {
						content = previewManagerService.generateJsonContent(preContent, categoryId);
					} else {
						content = previewManagerService.generateXmlContent(preContent.toString(), categoryId);
					}
					if(allowLogOper){
					    UserOperHelper.logMessage(content);
					}
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(content);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public ActionForward forward(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("service", request.getParameter("service"));
		request.setAttribute("currid", request.getParameter("currid"));
		return mapping.findForward("previewPage");
	}
}
