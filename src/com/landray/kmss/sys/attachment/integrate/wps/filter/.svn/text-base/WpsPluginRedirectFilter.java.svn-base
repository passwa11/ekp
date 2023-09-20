package com.landray.kmss.sys.attachment.integrate.wps.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.sys.attachment.integrate.wps.authen.WpsOfficeToken;
import com.landray.kmss.sys.attachment.integrate.wps.authen.WpsOfficeTokenGenerator;
import org.slf4j.Logger;
import com.landray.kmss.sys.authentication.AutoLoginHelper;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class WpsPluginRedirectFilter implements javax.servlet.Filter {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WpsPluginRedirectFilter.class);

	private static final String WPS_OASISST_TOKEN = "wpsOasisstToken";

	private AutoLoginHelper autoLoginHelper;

	public AutoLoginHelper getAutoLoginHelper() {
		if (autoLoginHelper == null) {
			autoLoginHelper = (AutoLoginHelper) SpringBeanUtil
					.getBean("autoLoginHelper");
		}
		return autoLoginHelper;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// 判断提交的参数是否有token,如果有并且不失效就单点登陆
		String wpsOasisstToken = httpRequest.getParameter(WPS_OASISST_TOKEN);
		if (StringUtil.isNotNull(wpsOasisstToken)) {
			wpsOasisstToken = wpsOasisstToken.replaceAll(" ", "+");
			WpsOfficeToken token = WpsOfficeTokenGenerator.getInstance()
					.generateTokenByTokenString(wpsOasisstToken);
			if (token != null) {
				if (token.isExpired()) {
					logger.error("Token已过期");
				} else {
					getAutoLoginHelper().doAutoLogin(token.getUsername(),
							"username", httpRequest.getSession());
				}
			}
		}

		String path = httpRequest.getPathInfo();
		if (StringUtil.isNull(path)) {
            path = httpRequest.getServletPath();
        }
		path = "/".equals(path) ? "" : path;

		String redirectUrl = "";

		if (StringUtil.isNull(path)) {
			path = ((HttpServletRequest) request).getServletPath();
		}

		if (path.indexOf(
				"sys/attachment/sys_att_main/wps/oaassist/jsplugins.xml") > -1) {
			// 登录失败后，还是返回word插件的登录页面
			redirectUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=downloadPluginXml";
			httpResponse.sendRedirect(getFullPath(httpRequest, redirectUrl));
			return;
		}


		chain.doFilter(httpRequest, response);
	}

	private String getFullPath(HttpServletRequest httpRequest, String absPath) {
		String contentPath = httpRequest.getContextPath();
		return (StringUtil.isNull(contentPath) ? "" : contentPath) + absPath;
	}

}
