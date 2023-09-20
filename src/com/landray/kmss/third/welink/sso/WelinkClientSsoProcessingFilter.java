package com.landray.kmss.third.welink.sso;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.landray.kmss.sys.authentication.AutoLoginHelper;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.third.welink.model.ThirdWelinkConfig;
import com.landray.kmss.third.welink.model.ThirdWelinkPersonMapping;
import com.landray.kmss.third.welink.service.IThirdWelinkPersonMappingService;
import com.landray.kmss.third.welink.service.IThirdWelinkService;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 外部浏览器单点
 *
 */
public class WelinkClientSsoProcessingFilter implements Filter, InitializingBean {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WelinkClientSsoProcessingFilter.class);

	@Override
    public void afterPropertiesSet() throws Exception {
		Assert.notNull(autoLoginHelper);
	}

	private String getSsoLoadUrl(String redirectUri,
								 HttpServletRequest request) {
		String url = StringUtil
				.formatUrl(
						"/resource/welink_load.jsp");
		try {
			url += "?callbackUrl=" + URLEncoder.encode(redirectUri, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		logger.debug("weilink sso load url = " + url);
		return url;
	}

	private String getRedirectUri(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		StringBuffer sb = new StringBuffer();
		int n = 0;
		String val = null;
		for (String key : map.keySet()) {
			if ("code".equals(key)) {
				continue;
			}
			if (n == 0) {
				sb.append("?");
			} else {
				sb.append("&");
			}
			sb.append(key);
			sb.append("=");
			val = map.get(key)[0];
			if (val.indexOf("?") > -1) {
				try {
					val = URLEncoder.encode(val, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					val = URLEncoder.encode(val);
					logger.error(e.getMessage(), e);
				}
			}
			sb.append(val);
			n++;
		}
		String url = StringUtil
				.formatUrl(request.getServletPath() + sb.toString());

		logger.debug("重定向的地址=" + url);
		return url;
	}


	@Override
    public void destroy() {
	}

	@Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
		String ssoEnable = ThirdWelinkConfig.newInstance()
				.getWelinkSsoEnabled();
		HttpServletRequest request = (HttpServletRequest) req;
		//不需要处理单点
		if (!"true".equals(ssoEnable) || !UserUtil.getUser().isAnonymous() || checkIsAnonymous(request)) {
			chain.doFilter(req, res);
			return;
		}
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		String code = request.getParameter("welinkCode");
		if(StringUtil.isNotNull(code)){
			if (logger.isDebugEnabled()) {
				logger.debug("welink返回CODE=" + code);
			}
			String userid = null;
			try {
				userid = thirdWelinkService.getClientUserId(code);
				logger.debug("welink返回userid=" + userid);
			} catch (Exception e1) {
				logger.error(e1.getMessage(), e1);
			}
			if (StringUtil.isNull(userid)) {
				logger.warn("welink集成无法获取当前的操作人员(" + userid + ")");
			} else {
				try {
					ThirdWelinkPersonMapping thirdWelinkPersonMapping = thirdWelinkPersonMappingService
							.findByUserId(userid);
					if (thirdWelinkPersonMapping != null) {
						String ekpid = thirdWelinkPersonMapping
								.getFdEkpPerson().getFdId();
						autoLoginHelper.doAutoLogin(ekpid, "id",
								session);
					} else {
						logger.error("找不到对应的映射关系，userid:" + userid);
					}
				} catch (Exception e) {
					logger.error("从welink单点登录失败！", e);
				}
			}
		}else{
			// welink容器中访问才进行单点重定向
			if ((MobileUtil.THIRD_WELINK == MobileUtil.getClientType(request)
					|| MobileUtil.WELINK_PC == MobileUtil
					.getClientType(request))) {
				// request.getSession().setAttribute("S_PADFlag", "1");
				response.sendRedirect(getSsoLoadUrl(
						getRedirectUri(request), request));
				return;
			}
		}
		chain.doFilter(req, res);
	}



	/**
	 * 该路径是否不需要拦截
	 *
	 * @param request
	 * @return
	 */
	private final static boolean checkIsAnonymous(HttpServletRequest request) {
		String path = request.getPathInfo();
		if (StringUtil.isNull(path)) {
            path = request.getServletPath();
        }
		path = "/".equals(path) ? "" : path;
		path = StringUtil.isNull(path) ? PdaFlagUtil.CONST_LOGINURL : path;
		if ("/login.jsp".equals(path)) {
			return true;
		}
		return PdaFlagUtil.checkIsAnonymous(path);
	}

	/**
	 * Does nothing - we rely on IoC lifecycle services instead.
	 *
	 * @param ignored
	 *            not used
	 *
	 */
	@Override
    public void init(FilterConfig ignored) throws ServletException {
	}

	private AutoLoginHelper autoLoginHelper;

	public void setAutoLoginHelper(AutoLoginHelper autoLoginHelper) {
		this.autoLoginHelper = autoLoginHelper;
	}

	public IThirdWelinkService getThirdWelinkService() {
		return thirdWelinkService;
	}

	public void setThirdWelinkService(IThirdWelinkService thirdWelinkService) {
		this.thirdWelinkService = thirdWelinkService;
	}

	public IThirdWelinkPersonMappingService
	getThirdWelinkPersonMappingService() {
		return thirdWelinkPersonMappingService;
	}

	public void setThirdWelinkPersonMappingService(
			IThirdWelinkPersonMappingService thirdWelinkPersonMappingService) {
		this.thirdWelinkPersonMappingService = thirdWelinkPersonMappingService;
	}

	private IThirdWelinkService thirdWelinkService;

	private IThirdWelinkPersonMappingService thirdWelinkPersonMappingService;

}
