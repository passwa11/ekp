package com.landray.kmss.third.feishu.sso;

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

import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.landray.kmss.sys.authentication.AutoLoginHelper;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.third.feishu.constant.ThirdFeishuConstant;
import com.landray.kmss.third.feishu.model.ThirdFeishuConfig;
import com.landray.kmss.third.feishu.model.ThirdFeishuPersonMapping;
import com.landray.kmss.third.feishu.service.IThirdFeishuPersonMappingService;
import com.landray.kmss.third.feishu.service.IThirdFeishuService;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 外部浏览器单点
 *
 */
public class FeishuOauth2AuthProcessingFilter implements Filter, InitializingBean {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(FeishuOauth2AuthProcessingFilter.class);

	@Override
    public void afterPropertiesSet() throws Exception {
		Assert.notNull(autoLoginHelper);
	}

	private String oauth2buildAuthorizationUrl(String redirectUri,
			String state) {
		ThirdFeishuConfig config = ThirdFeishuConfig.newInstance();
		String url = ThirdFeishuConstant.API_URL + "/authen/v1/index?";
		url += "app_id=" + config.getFeishuAppid();

		try {
			String ekpPath = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
			ekpPath = ekpPath + "/third/feishu/ssoRedirect.jsp?to="
					+ new String(Base64.encode(redirectUri.getBytes()),
							"UTF-8");
			url += "&redirect_uri="
					+ URLEncoder.encode(ekpPath,
					"utf-8");
			// url += "&redirect_uri="
			// + URLEncoder.encode(
			// "http://huangwq.qicp.vip/ekp",
			// "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		if (state != null) {
			url += "&state=" + state;
		}
		logger.warn("飞书 sso redirect url = " + url);
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
		ThirdFeishuConfig config = ThirdFeishuConfig.newInstance();
		String ssoEnable = config
				.getFeishuSsoEnabled();
		if (!"true".equals(ssoEnable)) {
			chain.doFilter(req, res);
			return;
		}
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		// MobileUtil.getClientType(request);

		if ("true".equals(ssoEnable)
				&& UserUtil.getUser().isAnonymous()
				// || MobileUtil.FEISHU_PC == MobileUtil
				// .getClientType(request)
				&& !checkIsAnonymous(request)) {
			// request.getSession().setAttribute("S_PADFlag", "1");

			if (StringUtil.isNull(code)) {
				if (MobileUtil.THIRD_FEISHU == MobileUtil
						.getClientType(request)
						|| MobileUtil.FEISHU_PC == MobileUtil
								.getClientType(request)) {
					response.sendRedirect(oauth2buildAuthorizationUrl(
						getRedirectUri(request), null));
					return;
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("飞书返回CODE=" + code);
				}
				// String userAgent = request.getHeader("User-Agent");
				// logger.error(userAgent);
				// 如果是PC的app里面打开的，先跳出到外部浏览器
				String ssoOut = config.getFeishuSsoOutEnabled();
				if ("true".equals(ssoOut) && MobileUtil.FEISHU_PC == MobileUtil
						.getClientType(request)) {
					String url = StringUtil
							.formatUrl(request.getServletPath() + "?"
									+ request.getQueryString());
					String jsStr = "<script type=\"text/javascript\" src=\"https://s0.pstatp.com/ee/lark/js_sdk/h5-js-sdk-1.4.5.js\"></script>"
							+
							"<script language='javascript'>window.open('" + url
							+ "'); window.h5sdk.ready(function() {\n" +
							"                window.h5sdk.biz.navigation.close({\n"
							+
							"                    onSuccess: function(result) {\n"
							+
							"                        console.log(result);\n" +
							"                    }\n" +
							"                });\n" +
							"                \n" +
							"            })</script>";
					response.getWriter().write(jsStr);
					logger.debug("跳到外部浏览器打开：" + jsStr);
					return;
				}
				String userId = null;
				try {
					if("ScanLogin".equals(state)){
						userId = thirdFeishuService.getPcScanUserId(
								code);
					}else {
						userId = thirdFeishuService.getSsoUserId(
								code);
					}
					logger.debug("飞书返回userId=" + userId);
				} catch (Exception e1) {
					logger.error("", e1);
				}

				if (StringUtil.isNull(userId)) {
					logger.debug("飞书集成无法获取当前的操作人员(" + userId + ")");
				} else {
					try {
						ThirdFeishuPersonMapping thirdFeishuPersonMapping = thirdFeishuPersonMappingService
								.findByEmployeeId(userId);
						if (thirdFeishuPersonMapping != null) {
							String ekpid = thirdFeishuPersonMapping
									.getFdEkp().getFdId();
							autoLoginHelper.doAutoLogin(ekpid, "id",
									session);
						} else {
							logger.error("找不到对应的映射关系，userId:" + userId);
						}
					} catch (Exception e) {
						logger.error("从飞书单点登录失败！", e);
					}
				}
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


	@Override
    public void init(FilterConfig ignored) throws ServletException {
	}

	private AutoLoginHelper autoLoginHelper;

	public void setAutoLoginHelper(AutoLoginHelper autoLoginHelper) {
		this.autoLoginHelper = autoLoginHelper;
	}

	public IThirdFeishuService getThirdFeishuService() {
		return thirdFeishuService;
	}

	public void setThirdFeishuService(IThirdFeishuService thirdFeishuService) {
		this.thirdFeishuService = thirdFeishuService;
	}

	public IThirdFeishuPersonMappingService
			getThirdFeishuPersonMappingService() {
		return thirdFeishuPersonMappingService;
	}

	public void setThirdFeishuPersonMappingService(
			IThirdFeishuPersonMappingService thirdFeishuPersonMappingService) {
		this.thirdFeishuPersonMappingService = thirdFeishuPersonMappingService;
	}

	private IThirdFeishuService thirdFeishuService;

	private IThirdFeishuPersonMappingService thirdFeishuPersonMappingService;

}
