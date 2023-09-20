package com.landray.kmss.third.pda.interfaces;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authentication.user.validate.Config;
import com.landray.kmss.sys.authorization.util.TripartiteAdminUtil;
import com.landray.kmss.sys.mobile.offline.wrapper.WrapperRequest;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.third.pda.service.IPdaLicenseSettingService;
import com.landray.kmss.third.pda.util.LicenseUtil;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.Globals;

public class ThirdPdaRedirectFilter implements javax.servlet.Filter {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdPdaRedirectFilter.class);

	/**
	 * 方法key值
	 */
	private final String METHOD_KEY = "method";

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String path = httpRequest.getPathInfo();
		if (StringUtil.isNull(path)) {
            path = httpRequest.getServletPath();
        }
		path = "/".equals(path) ? "" : path;
		KMSSUser user = UserUtil.getKMSSUser(httpRequest);
		boolean isPda = false;
		boolean isUserLimit = false;
		String redirectUrl = "";

		// TODO 拓展点解耦
		String xRequestWith = httpRequest.getHeader("X-Requested-With");
		HttpServletRequest wrapper = httpRequest;
		if ("KKProxyHttpRequest".equals(xRequestWith)) {
			wrapper = new WrapperRequest(httpRequest);
		}

		if (user.isAnonymous()) {
			// 未登录情况的拦截
			String ssoRedirectEnabled = ResourceUtil.getKmssConfigString("kmss.ssoclient.redirect.enabled");
			
			/*说明：移动端附件下载时，规定了限制时间内可下载，即含有超时Expires和签名Signature,则放行，不需要登录
			 * 使用场景：直接在移动端使用downloadFile.jsp下载附件，例如WPS移动端下载文件
			 * 
			 * 注意：此处不加入到PdaFlagUtil.CONST_PDASORUCE中
			 */
			if("/sys/attachment/sys_att_main/downloadFile.jsp".equals(path)
					&& StringUtil.isNotNull(httpRequest.getParameter("Expires"))
					&& StringUtil.isNotNull(httpRequest.getParameter("Signature"))
					&& !"true".equals(ssoRedirectEnabled))
			{
				ssoRedirectEnabled = "true";
			}
			
			if (!"true".equals(ssoRedirectEnabled)) {
				path = StringUtil.isNull(path) ? PdaFlagUtil.CONST_LOGINURL : path;
				logger.debug("拦截前的匿名路径为:" + path);
				if (PdaFlagUtil.checkIsPdaLogin(httpRequest)) {
					if (!PdaFlagUtil.checkIsAnonymous(path) && !"/sys/attachment/uploadByWpsCallback.do".equals(path)) {
						StringBuffer oldPath = httpRequest.getRequestURL();
						if (StringUtil.isNotNull(httpRequest.getQueryString())) {
                            oldPath.append("?" + httpRequest.getQueryString());
                        }
						if (oldPath.indexOf(PdaFlagUtil.CONST_LOGINURL) == -1) {
                            httpRequest.getSession().setAttribute(Globals.SPRING_SECURITY_TARGET_URL_KEY,
                                    oldPath.toString());
                        }
						redirectUrl = PdaFlagUtil.CONST_PDALOGINURL;
					}
					isPda = true;
				} else {
					if (path.indexOf(PdaFlagUtil.CONST_PDAHOMEURL) == 0) {
                        redirectUrl = PdaFlagUtil.CONST_HONEOFPDAURL;
                    }
				}
			}
		} else {
			// 已登录情况下拦截
			path = StringUtil.isNull(path) ? PdaFlagUtil.CONST_HOMEURL : path;
			logger.debug("拦截前的已登录路径为:" + path);
			Boolean isPdaAccess = PdaFlagUtil.checkClientIsPda(httpRequest);
			if (!isPdaAccess) {
				if (path.indexOf(PdaFlagUtil.CONST_PDAHOMEURL) == 0 || "/third/pda/".equalsIgnoreCase(path)
						|| "/third/pda".equalsIgnoreCase(path)) {
                    redirectUrl = PdaFlagUtil.CONST_HONEOFPDAURL;
                }
			} else {
				if (path.indexOf(PdaFlagUtil.CONST_HOMEURL) == 0 || path.indexOf(PdaFlagUtil.CONST_PDAPATHREDURL) == 0) {
                    redirectUrl = PdaFlagUtil.CONST_PDAHOMEURL;
                }
				isPda = true;
			}
		}

		// 拦截是否具备合法license
		String userInfo = (String) httpRequest.getSession().getAttribute(Config.USER_INFO_KEY);
		if (StringUtil.isNotNull(userInfo)) {
			isUserLimit = true;
		}
		if (!isUserLimit) {
			if (!LicenseUtil.chkPathIsError(path)) {
				boolean isRdtErr = false;
				if (isPda == false && LicenseUtil.isOnlyPdaAccess()) {
					redirectUrl = LicenseUtil.getErrorPath(1) + "?type=404";
					isRdtErr = true;
				}
				if (LicenseUtil.isNotAllowLogin() && !isRdtErr && MobileUtil.getClientType(httpRequest) < 2) {// 客户端放行
					if (LicenseUtil.chkPathIsLogin(StringUtil.isNotNull(redirectUrl) ? redirectUrl : path)) {
						redirectUrl = LicenseUtil.getErrorPath(1) + "?type=404";
					}
				}
				// 开启三员管理时，禁用移动端
				/*
				 * if (TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN && isPda) { redirectUrl =
				 * LicenseUtil.getErrorPath(1) + "?type=404"; }
				 */
			}

			// 假如方法加了modelUrl注解，在移动端会先走对应modelUrl页面再请求数据页面
			// 此功能是优化移动端三级页面白屏问题
			if (isPda && StringUtil.isNull(httpRequest.getParameter("_data"))) {
				String method = httpRequest.getParameter(METHOD_KEY);
				if (StringUtil.isNotNull(method)) {
					String url = ThirdPdaSeparateService.formatDataUrl(httpRequest,method);
					if(logger.isDebugEnabled()) {
						logger.debug("当前请求格式化后的链接为：" + url);
					}
					// 获取映射的移动端链接
					redirectUrl = ThirdPdaSeparateService.getMobileUrl(url, httpRequest);
					if(StringUtil.isNotNull(url)){
						//获取映射的视图，若是配置了视图，则交给视图拦截器做处理
						String viewName = ThirdPdaSeparateService.getViewName(url);
						if(StringUtil.isNotNull(viewName))//若是视图不为空，说明需要特殊处理，交给视图拦截器处理
                        {
                            redirectUrl = null;
                        }
					}
				}
			}

			if (StringUtil.isNotNull(redirectUrl)) {
				logger.debug("拦截后的路径为:" + redirectUrl);
				httpResponse.sendRedirect(getFullPath(wrapper, redirectUrl));
			} else {
				logger.debug("该路径不做拦截处理");
				chain.doFilter(wrapper, response);
			}
		} else {
			chain.doFilter(wrapper, response);
		}
	}

	private String getFullPath(HttpServletRequest httpRequest, String absPath) {
		String contentPath = httpRequest.getContextPath();
		return (StringUtil.isNull(contentPath) ? "" : contentPath) + absPath;
	}

	IPdaLicenseSettingService settingService;

	/**
	 * 初始化pda lincense信息
	 */
	public Map<String, String> initPdaLicense() {
		Map<String, String> commonMap = LicenseUtil.getPersonInfoMap();
		if (commonMap == null || commonMap.isEmpty()) {
			try {
				logger.debug(new Date() + " pda license info initalize begin..");
				settingService.getAccessorList(null);
				logger.debug(new Date() + " pda license info initalize end");
				commonMap = LicenseUtil.getPersonInfoMap();
			} catch (Exception e) {
				logger.error("初始化license信息出错:" + e);
			}
		}
		return commonMap;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		settingService = (IPdaLicenseSettingService) SpringBeanUtil.getBean("pdaLicenseSettingService");
		initPdaLicense();
	}

}
