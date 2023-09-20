package com.landray.kmss.sys.mportal.actions;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.mportal.model.SysMportalLogoInfo;
import com.landray.kmss.sys.mportal.service.ISysMportalCompositeService;
import com.landray.kmss.sys.mportal.service.ISysMportalPageService;
import com.landray.kmss.sys.mportal.util.SysMportalImgUtil;
import com.landray.kmss.sys.ui.forms.SysUiLogoForm;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
public  class SysMportalLogoAction extends BaseAction {

	/**
	 * 显示所有logo
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			// 显示所有logo
			List<String> paths = SysMportalImgUtil.scanImgPath(SysMportalImgUtil.LOGO_FILE_NAME);
			request.setAttribute("paths", paths);
			request.setAttribute("logo", SysMportalImgUtil.logo());
			
			// 记录日志
			if (UserOperHelper.allowLogOper("Base_UrlParam", SysMportalLogoInfo.class.getName())) { 
				UserOperHelper.setModelNameAndModelDesc(SysMportalLogoInfo.class.getName(),  ResourceUtil.getString("module.sys.mportal", "sys-mportal"));
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-list", false, getClass());

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return mapping.findForward("failure");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("list");
		}
	}

	public ActionForward select(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-select", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			List<String> paths = SysMportalImgUtil.scanImgPath(SysMportalImgUtil.LOGO_FILE_NAME);
			request.setAttribute("paths", paths);
			request.setAttribute("logo", SysMportalImgUtil.logo());
			
			// 记录日志
			if (UserOperHelper.allowLogOper("Base_UrlParam", SysMportalLogoInfo.class.getName())) { 
				UserOperHelper.setModelNameAndModelDesc(SysMportalLogoInfo.class.getName(),  ResourceUtil.getString("module.sys.mportal", "sys-mportal"));
			}
						
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-select", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return mapping.findForward("failure");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN)
					.save(request);
			return mapping.findForward("select");
		}
	}

	/**
	 * 上传logo
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward upload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			SysUiLogoForm xform = (SysUiLogoForm) form;
			SysMportalImgUtil.saveImg(xform.getFile().getInputStream(),
					SysMportalImgUtil.LOGO_FILE_NAME,
					xform
					.getFile().getFileName());

			// 记录日志
			if (UserOperHelper.allowLogOper("Base_UrlParam", SysMportalLogoInfo.class.getName())) {
				UserOperHelper.logMessage("上传Logo");
				UserOperHelper.setModelNameAndModelDesc(SysMportalLogoInfo.class.getName(),  ResourceUtil.getString("module.sys.mportal", "sys-mportal"));
			}

		} catch (Exception e) {
			return mapping.findForward("failure");
		}
		return mapping.findForward("success");
	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String urls = request.getParameter("srcs");
			if(StringUtil.isNotNull(urls)) {
				SysMportalImgUtil.deleteImgs(urls);
			}
			
			// 记录日志
			if (UserOperHelper.allowLogOper("delete", SysMportalLogoInfo.class.getName())) {
				UserOperHelper.setModelNameAndModelDesc(SysMportalLogoInfo.class.getName(),  ResourceUtil.getString("module.sys.mportal", "sys-mportal"));
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if(messages.hasError()) {
			return mapping.findForward("failure");
		}
		return mapping.findForward("success");
	}
	
	/**
	 * 校验当前log是否被引用
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validateLogo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TimeCounter.logCurrentTime("Action-validateLogo", true, getClass());
		KmssMessages messages = new KmssMessages();
		// 默认校验通过
		boolean flag = true;

		// 要删除的logo集合
		String[] selectSrcArr = request.getParameterValues("selectSrc");
		List<String> logoList = Arrays.asList(selectSrcArr);

		// 查询简单门户是否被引用
		ISysMportalPageService sysMportalPageService = (ISysMportalPageService) SpringBeanUtil
				.getBean("sysMportalPageService");
		JSONArray simple = sysMportalPageService.getMportalInfoByLogo(logoList);
		if (simple.size() > 0) {
			flag = false;
		}

		// 查询复合门户是否被引用
		if (flag) {
			ISysMportalCompositeService sysMportalCompositeService = (ISysMportalCompositeService) SpringBeanUtil
					.getBean("sysMportalCompositeService");
			JSONArray composite = sysMportalCompositeService
					.getMportalInfoByLogo(logoList);
			if (composite.size() > 0) {
				flag = false;
			}
		}
		// 校验不通过，logo信息存入Session中
		if (!flag) {
			String sessionKey = "deleteLogo-" + UserUtil.getUser().getFdId();
			HttpSession session = request.getSession();
			session.removeAttribute(sessionKey);
			JSONArray array= JSONArray.parseArray(com.alibaba.fastjson.JSON.toJSONString(logoList));
			session.setAttribute(sessionKey, array);
		}
		// 返回校验信息
		JSONObject json = new JSONObject();
		json.put("flag", flag);
		request.setAttribute("lui-source", json);
		// 记录日志
		if (UserOperHelper.allowLogOper("validateLogo", SysMportalLogoInfo.class.getName())) {
			UserOperHelper.setEventType(ResourceUtil.getString("delete.check", "sys-mportal"));
			UserOperHelper.setModelNameAndModelDesc(SysMportalLogoInfo.class.getName(), ResourceUtil.getString("module.sys.mportal", "sys-mportal"));
		}
		TimeCounter.logCurrentTime("Action-validateLogo", false, getClass());

		if (messages.hasError()) {
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}

	}
	
	/**
	 * 获得引用Logo的门户信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getLogoQuote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TimeCounter.logCurrentTime("Action-getLogoQuote", true, getClass());
		KmssMessages messages = new KmssMessages();
		
		// session中获得要删除logo的信息
		String sessionKey = "deleteLogo-" + UserUtil.getUser().getFdId();
		HttpSession session = request.getSession();
		JSONArray listJsonArray = (JSONArray) session.getAttribute(sessionKey);
		List<String> logoList = JSONObject.parseArray(listJsonArray.toJSONString(), String.class);

		ISysMportalCompositeService sysMportalCompositeService = (ISysMportalCompositeService) SpringBeanUtil
				.getBean("sysMportalCompositeService");
		ISysMportalPageService sysMportalPageService = (ISysMportalPageService) SpringBeanUtil
				.getBean("sysMportalPageService");

		JSONArray composite = sysMportalCompositeService
				.getMportalInfoByLogo(logoList);
		JSONArray simple = sysMportalPageService.getMportalInfoByLogo(logoList);


		request.setAttribute("composite", composite);
		request.setAttribute("simple", simple);

		TimeCounter.logCurrentTime("Action-getLogoQuote", false, getClass());

		if (messages.hasError()) {
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("quote");
		}

	}

}
