package com.landray.kmss.sys.ui.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.portal.service.ISysPortalMainService;
import com.landray.kmss.sys.ui.forms.SysUiLogoForm;
import com.landray.kmss.sys.ui.plugin.SysUiTools;
import com.landray.kmss.sys.ui.util.SysUiConfigUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class SysUiLogoAction extends BaseAction {
	public ActionForward upload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			SysUiLogoForm xform = (SysUiLogoForm) form;
			SysUiTools.saveLogo(xform.getFile().getInputStream(), xform
					.getFile().getFileName());
			if (UserOperHelper.allowLogOper("Base_UrlParam", null)) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil.getString("sys-admin:home.nav.sysAdmin")
								+ "(" + ResourceUtil
										.getString("sys-ui:ui.logo.upload")
								+ ")");
			}
		} catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
		}
		return mapping.findForward("upload");
	}

	public ActionForward deleteLogo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteLogo", true, getClass());
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("code", 1);
		try {
			String fileName = request.getParameter("fileName");
			if (StringUtil.isNotNull(fileName)) {
				// 若logo已被使用则不允许删除
				ISysPortalMainService sysPortalMainService = (ISysPortalMainService) SpringBeanUtil
						.getBean("sysPortalMainService");
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setRowSize(1);
				hqlInfo.setGetCount(false);
				hqlInfo.setWhereBlock(
						"sysPortalMain.fdEnabled = true and sysPortalMain.fdLogo=:fdLogo");
				hqlInfo.setParameter("fdLogo", fileName);

				List list = sysPortalMainService.findList(hqlInfo);
				if (list.size() > 0 || fileName
						.equals(SysUiConfigUtil.getProfileLogoTitle())) {
					jsonObj.put("code", 2);
				} else {
					SysUiTools.deleteLogo(fileName);
				}
			}
		} catch (Exception e) {
			jsonObj.put("code", 0);
		}
		if (UserOperHelper.allowLogOper("Base_UrlParam", null)) {
			UserOperHelper.setModelNameAndModelDesc(null,
					ResourceUtil
							.getString("sys-admin:home.nav.sysAdmin")
							+ "(" + ResourceUtil.getString(
									"sys-ui:ui.logo.deleteLogo")
							+ ")");
			UserOperHelper.logMessage(jsonObj.toString());
		}
		request.setAttribute("lui-source", jsonObj);
		return mapping.findForward("lui-source");
	}

	/**
	 * 保存后台配置Logo
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveProfileLogo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject jsonObj = new JSONObject();
		String logoTitle = request.getParameter("logoTitle");
		String modelName = "com.landray.kmss.sys.ui.model.SysUiConfig";
		if (!logoTitle.equals(SysUiConfigUtil.getProfileLogoTitle())) {
			BaseAppConfig appConfig = (BaseAppConfig) ClassUtils.forName(modelName)
					.newInstance();
			appConfig.getDataMap().put("logoTitle", logoTitle);
			appConfig.save();
			jsonObj.put("result", "success");
		}

		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(jsonObj.toString());
		response.getWriter().flush();
		response.getWriter().close();

		return null;
	}

	/**
	 * 管理员工具箱处上传Logo
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadProfileLogo(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			SysUiLogoForm xform = (SysUiLogoForm) form;
			SysUiTools.saveLogo(xform.getFile().getInputStream(), xform
					.getFile().getFileName());
			if (UserOperHelper.allowLogOper("Base_UrlParam", null)) {
				UserOperHelper.setModelNameAndModelDesc(null,
						ResourceUtil.getString("sys-admin:home.nav.sysAdmin")
								+ "(" + ResourceUtil
										.getString("sys-ui:ui.logo.upload")
								+ ")");
			}
		} catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
		}
		return mapping.findForward("profile_logo");
	}
}
