package com.landray.kmss.third.ding.sso;

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

import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.landray.kmss.sys.authentication.AutoLoginHelper;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 与EKP与钉钉进行Oauth2进行单点登录时使用的过滤器
 * 
 * @author wubing 2015-06-12
 */
public class Oauth2AuthProcessingFilter implements Filter, InitializingBean,
		DingConstant {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Oauth2AuthProcessingFilter.class);

	@Override
    public void afterPropertiesSet() throws Exception {
		Assert.notNull(autoLoginHelper);
	}

	private String oauth2buildAuthorizationUrl(String redirectUri, String state) {

		String addAppKey = ResourceUtil
				.getKmssConfigString("kmss.ding.addAppKey");
		String appid = null;
		if (StringUtil.isNotNull(addAppKey)
				&& "true".equalsIgnoreCase(addAppKey)) {
			if (StringUtil.isNotNull(redirectUri)
					&& redirectUri.contains("dingAppKey")) {
				appid = StringUtil.getParameter(redirectUri.replace("?", "&"),
						"dingAppKey");
			}
		} else {
			appid = DingConfig.newInstance().getDingCorpid();
		}
		String url = "https://oapi.dingtalk.com/connect/oauth2/authorize?";
		url += "appid=" + appid;
		try {
			url += "&redirect_uri=" + URLEncoder.encode(redirectUri,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url += "&response_type=code";
		url += "&scope=snsapi_base";
		if (state != null) {
			url += "&state=" + state;
		}
		logger.debug("url=" + url);
		return url;
	}

	private String getRedirectUri(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		StringBuffer sb = new StringBuffer();
		int n = 0;
		String val = null;
		for (String key : map.keySet()) {
			if (!"oauth".equals(key)) {
				if (n == 0) {
					sb.append("?");
				} else {
					sb.append("&");
				}
				sb.append(key);
				sb.append("=");
				val = map.get(key)[0];
				if(val.indexOf("?")>-1){
					try {
						val = URLEncoder.encode(val, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						val = URLEncoder.encode(val);
						e.printStackTrace();
					}
				}
				sb.append(val);
				n++;
			}
		}
		// 多语言信息
		String lang = request.getHeader("Accept-Language");
		logger.debug("==钉钉客户端语言信息======:" + lang);
		if (StringUtil.isNotNull(lang) && MobileUtil.DING_PC == MobileUtil
				.getClientType(request) && lang.indexOf(",") > -1
				&& sb.toString().indexOf("j_lang") == -1) {
			lang = lang.trim();
			String def_lang = lang.substring(0, lang.indexOf(","));
			if (sb.indexOf("?") > -1) {
				sb.append("&");
			} else {
				sb.append("?");
			}
			sb.append("j_lang=");
			sb.append(def_lang);
		}

		String url = request.getServletPath() + sb.toString();
		String domainName = DingConfig.newInstance().getDingDomain();
		if(StringUtil.isNull(domainName)){
			url = StringUtil.formatUrl(request.getServletPath() + sb.toString());
		}else{
			if(domainName.trim().endsWith("/")) {
                domainName = domainName.trim().substring(0, domainName.length()-1);
            }
			url = domainName+request.getServletPath() + sb.toString();
		}
		if (url.indexOf("?") > -1 || (url.indexOf("?") == -1 && url.indexOf("&") > -1)) {
			url = StringUtil.setQueryParameter(url, "mdingclose", "1");
		} else {
			url = url + "?mdingclose=1";
		}
		// 钉钉转屏参数
		// if (!url.contains("dd_orientation")) {
		// url += "&dd_orientation=auto";
		// }
		logger.debug("重定向的地址=" + url);
		return url;
	}

	/**
	 * Does nothing - we rely on IoC lifecycle services instead.
	 */
	@Override
    public void destroy() {
	}

	@Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		// HttpSession session = request.getSession();
		String code = request.getParameter("code");
		String filterEnable = DingConfig.newInstance().getDingOauth2Enabled();
		if ("true".equals(filterEnable) && !autoLoginHelper.hasLogin()
				&& UserUtil.getUser().isAnonymous()
				&& !checkIsAnonymous(request) && StringUtil.isNull(code)) {
			if (MobileUtil.DING_ANDRIOD == MobileUtil.getClientType(request)) {
				request.getSession().setAttribute("S_PADFlag", "1");
				response.sendRedirect(request.getContextPath()
						+ "/resource/ding_load.jsp?callbackUrl="
						+ URLEncoder.encode(getRedirectUri(request), "UTF-8")
						+ "&title="
						+ URLEncoder.encode(getTitle(request), "UTF-8"));
				return;
			}
			if (MobileUtil.DING_PC == MobileUtil
					.getClientType(request)) {
				response.sendRedirect(
						oauth2buildAuthorizationUrl(getRedirectUri(request),
								null));
				return;
			}
		}

		// 钉钉转屏参数添加
		// Map<String, String[]> map = request.getParameterMap();
		// if ("true".equals(filterEnable)
		// && MobileUtil.DING_ANDRIOD == MobileUtil.getClientType(request)
		// && !checkIsAnonymous(request)
		// && !map.containsKey("dd_orientation")) {
		// response.sendRedirect(getRedirectUri(request));
		// return;
		// }

		chain.doFilter(request, response);
		// else if ("true".equals(filterEnable) && !autoLoginHelper.hasLogin()
		// && MobileUtil.DING_ANDRIOD == MobileUtil.getClientType(request) &&
		// !checkIsAnonymous(request)) {
		// request.getSession().setAttribute("S_PADFlag", "1");
		// String token = null;
		// try {
		// token = DingUtils.getDingApiService().getAccessToken();
		// } catch (Exception e1) {
		// e1.printStackTrace();
		// }
		// if(StringUtil.isNotNull(token)&&!"null".equals(token)){
		// if (StringUtil.isNull(code)) {
		// response.sendRedirect(oauth2buildAuthorizationUrl(getRedirectUri(request),
		// null));
		// return;
		// } else {
		// if (logger.isDebugEnabled()) {
		// logger.debug("钉钉返回CODE=" + code);
		// }
		// String userid = null;
		// try {
		// JSONObject json = DingUtils.getDingApiService().getUserInfo(code);
		// logger.debug("钉钉返回userInfo=" + json.toString());
		// if ("0".equals(json.getString("errcode"))) {
		// userid = json.getString("userid");
		// }
		// logger.debug("EKP与钉钉开放授权 OK username=" + userid);
		// } catch (Exception e) {
		// logger.warn("从钉钉开放授权中获取用户名失败！", e);
		// }
		//
		// if (StringUtil.isNull(userid)) {
		// logger.debug("钉钉集成无法获取当前的操作人员(" + userid + ")");
		// } else {
		// IOmsRelationService omsRelationService =
		// (IOmsRelationService)SpringBeanUtil.getBean("omsRelationService");
		// HQLInfo hqlInfo = new HQLInfo();
		// hqlInfo.setWhereBlock("omsRelationModel.fdAppPkId = :fdAppPkId");
		// hqlInfo.setParameter("fdAppPkId", userid);
		// try {
		// List<OmsRelationModel> relations =
		// omsRelationService.findList(hqlInfo);
		// if(relations.size()>0){
		// String ekpid = relations.get(0).getFdEkpId();
		// autoLoginHelper.doAutoLogin(ekpid,"id" ,session);
		// logger.debug("从钉钉开放授权中获取用户名登录EKP成功ID="+ekpid);
		// }else{
		// if ("true".equals(DingConfig.newInstance().getLdingEnabled())) {
		// //开启蓝钉对接（钉钉的Userid默认为EKP的fdId）
		// autoLoginHelper.doAutoLogin(userid,"id" ,session);
		// }else{
		// autoLoginHelper.doAutoLogin(userid ,session);
		// }
		// logger.debug("从钉钉开放授权中获取用户名登录EKP成功LoginName=" + userid);
		// }
		// } catch (Exception e) {
		// logger.debug("从钉钉集成映射表中获取EKP用户或者单点登录失败！", e);
		// }
		// }
		// }
		// }
		// }

	}

	private String getTitle(HttpServletRequest request) {

		try {
			// 这里的uri是@Separater里描述的uri
			String uri = request.getServletPath();
			// 解析出来会是一个数组，第一个元素是空串，[, km, review, mobile, view.jsp]，数组长度>=5
			String[] split = uri.split("[/]");
			if (split == null || split.length < 3) {
				return "loading";
			}
			// 取1，2元素用于拼接模块url前缀
			String _path = "/" + split[1] + "/" + split[2] + '/';
			com.landray.kmss.sys.config.design.SysCfgModule module = com.landray.kmss.sys.config.design.SysConfigs
					.getInstance().getModule(_path);
			String msg2 = null;
			if (module != null) {
				String msg = module.getMessageKey();
				msg2 = ResourceUtil.getString(msg);
			} else {
				// 兼容3级的模块情况
				_path = "/" + split[1] + "/" + split[2] + '/' + split[3] + '/';
				module = com.landray.kmss.sys.config.design.SysConfigs
						.getInstance()
						.getModule(_path);
				if (module != null) {
					String msg = module.getMessageKey();
					msg2 = ResourceUtil.getString(msg);
				}
			}
			String defaultTitle = msg2 == null ? "loading" : msg2;
			return defaultTitle;
		} catch (Exception e) {
			return "loading";
		}

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
		if ("/login.jsp".equals(path)) {
			return true;
		}
		if ("/third/ding/pc/pcpg.jsp".equals(path)) {
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

}
