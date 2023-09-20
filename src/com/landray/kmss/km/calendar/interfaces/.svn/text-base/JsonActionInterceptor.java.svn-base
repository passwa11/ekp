package com.landray.kmss.km.calendar.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.landray.kmss.common.actions.KmssDefaultActionInterceptor;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.config.ForwardConfig;
import com.landray.kmss.web.config.ModuleConfig;
import com.landray.kmss.web.util.ModelAndViewUtils;

/**
 * 时间管理模块forward处理拦截器
 */
public class JsonActionInterceptor extends KmssDefaultActionInterceptor{

	private ModuleConfig globalModuleConfig = null;

	public void setGlobalModuleConfig(ModuleConfig globalModuleConfig) {
		this.globalModuleConfig = globalModuleConfig;
	}

	private ModuleConfig getActualModuleConfig(Object handler) {
		if (handler instanceof ModuleConfig) {
			return (ModuleConfig) handler;
		}
		return globalModuleConfig;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		ModuleConfig actualModuleConfig = getActualModuleConfig(handler);
		if (actualModuleConfig == null || modelAndView == null) {
			return;
		}
		String s_fromApp = request.getParameter("s_fromApp"); // 只处理有s_fromApp参数的数据
		String requestURI = request.getRequestURI();
		Boolean fromCalendar = requestURI.indexOf("km/calendar") > -1; // 属于日程模块才做处理
		if (StringUtil.isNotNull(s_fromApp) && fromCalendar) {
			String viewName = modelAndView.getViewName();
			if (StringUtil.isNotNull(viewName)) {
				// 对应于struts的ActionForward.getName();
				String stripPrefix = ModelAndViewUtils.stripPrefix(viewName);
				// 跳转配置
				ForwardConfig fc = null;
				if ("success".equals(stripPrefix)) {
					fc = new ActionForward(
							"/km/calendar/common/success.jsp");
					ModelAndViewUtils.replaceViewName(modelAndView,
							fc.getName());
				}
				if ("failure".equals(stripPrefix)) {
					fc = new ActionForward(
							"/km/calendar/common/failure.jsp");
					ModelAndViewUtils.replaceViewName(modelAndView,
							fc.getName());
				}
				// rtnPage中存在错误也返回failure.jsp页面
				KmssReturnPage rtnPage = (KmssReturnPage) request
						.getAttribute("KMSS_RETURNPAGE");
				if (rtnPage != null) {
					boolean hasError = rtnPage.getMessages().hasError();
					if (hasError) {
						fc = new ActionForward(
								"/km/calendar/common/failure.jsp");
						ModelAndViewUtils.replaceViewName(modelAndView,
								fc.getName());
					}
				}
				if (StringUtil.isNotNull(stripPrefix)
						&& !stripPrefix.toLowerCase().endsWith(".4app")) {
					fc = actualModuleConfig
							.findForwardConfig(stripPrefix + ".4app");
					if (fc != null) {
						ModelAndViewUtils.replaceViewName(modelAndView,
								fc.getName());
					}
				}
			}
		}
	}

}
