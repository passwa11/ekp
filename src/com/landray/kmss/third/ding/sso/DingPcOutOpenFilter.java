package com.landray.kmss.third.ding.sso;

import com.landray.kmss.sys.authentication.AutoLoginHelper;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * PC版钉钉在外部浏览器打开时实现单点登录的过滤器
 * 
 * @author 唐有炜
 *
 */
public class DingPcOutOpenFilter implements Filter, InitializingBean {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingPcOutOpenFilter.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(autoLoginHelper);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		// 存在严重安全漏洞
//		HttpSession session = request.getSession();
//
//		String outToken = request.getParameter("outToken");
//		if (!autoLoginHelper.hasLogin() && !checkIsAnonymous(request)
//				&& StringUtil.isNotNull(outToken)
//				&& MobileUtil.PC == MobileUtil.getClientType(request)) {
//			String ekpid = null;
//			try {
//				ekpid = SecureUtil.BASE64Decoder(outToken);
//				autoLoginHelper.doAutoLogin(ekpid, "id", session);
//				logger.debug("登录成功，id=" + ekpid);
//			} catch (Exception e) {
//				logger.error("token解密失败", e);
//			}
//		}
		chain.doFilter(request, response);
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

	/**
	 * 该路径是否不需要拦截
	 * 
	 * @param path
	 * @return
	 */
	private final static boolean checkIsAnonymous(HttpServletRequest request) {
		String path = request.getPathInfo();
		if (StringUtil.isNull(path)) {
            path = request.getServletPath();
        }
		path = "/".equals(path) ? "" : path;
		path = StringUtil.isNull(path) ? PdaFlagUtil.CONST_LOGINURL : path;
		return PdaFlagUtil.checkIsAnonymous(path);
	}
}
