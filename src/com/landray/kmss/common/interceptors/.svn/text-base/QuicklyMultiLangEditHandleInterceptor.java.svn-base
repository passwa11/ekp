package com.landray.kmss.common.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.landray.kmss.common.actions.KmssDefaultActionInterceptor;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 多语言快编功能
 * @author 黄海
 *
 */
public class QuicklyMultiLangEditHandleInterceptor extends KmssDefaultActionInterceptor {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(modelAndView == null)
		{
			return;
		}
		String acceptHeader = request.getHeader("accept");
		if (StringUtil.isNotNull(acceptHeader) && request.getHeader("accept").indexOf("application/json") >= 0) {
			return;
		}
		String viewName = modelAndView.getViewName();
		if (ResourceUtil.isQuicklyEdit() && StringUtil.isNotNull(viewName) && !viewName.startsWith("redirect:")
				&& request.getAttribute("redirectto") == null) {
			request.setAttribute("realViewName", viewName.replace("forward:", ""));
			modelAndView.setViewName("/sys/profile/i18n/quicklyMultiLangEditForward.jsp");
			boolean isAjaxRequest = false;
			if (!StringUtil.isNull(request.getHeader("x-requested-with"))
					&& "XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
				isAjaxRequest = true;
			}
			request.setAttribute("isAjaxRequest", isAjaxRequest);
		}
	}
}
