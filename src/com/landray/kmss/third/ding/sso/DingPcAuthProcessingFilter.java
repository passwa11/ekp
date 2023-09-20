package com.landray.kmss.third.ding.sso;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.authentication.AutoLoginHelper;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.third.ding.service.IDingCodeService;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class DingPcAuthProcessingFilter implements Filter, InitializingBean {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingPcAuthProcessingFilter.class);

	@Override
    public void afterPropertiesSet() throws Exception {
		Assert.notNull(autoLoginHelper);
	}

	/**
	 * Does nothing - we rely on IoC lifecycle services instead.
	 */
	@Override
    public void destroy() {
	}

	private String oauth2buildAuthorizationUrl(String redirectUri, String state) {
		String url = "https://oapi.dingtalk.com/connect/oauth2/authorize?";
		url += "appid=" + DingConfig.newInstance().getDingCorpid();
		url += "&redirect_uri=" + URLEncoder.encode(redirectUri);
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
		for (String key : map.keySet()) {
			if (!"oauth".equals(key)) {
				if (n == 0) {
					sb.append("?");
				} else {
					sb.append("&");
				}
				sb.append(key);
				sb.append("=");
				if("queryString".equals(key)) {
					try {
						sb.append(URLEncoder.encode(map.get(key)[0], "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						sb.append(map.get(key)[0]);
					}
				}else {
					sb.append(map.get(key)[0]);
				}
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
		logger.debug("url:" + url);
		return url;
	}
	
	@Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		String filterEnable = DingConfig.newInstance().getDingOauth2Enabled();
		String code = request.getParameter("code");
		String scode = request.getParameter("scode");
		String from = request.getParameter("from");
		if ("true".equals(filterEnable) && UserUtil.getUser().isAnonymous()
				&& !checkIsAnonymous(request)
				&& (MobileUtil.DING_PC == MobileUtil.getClientType(request)
						||
						MobileUtil.PC == MobileUtil.getClientType(request))
				&& (StringUtil.isNotNull(scode)
						|| StringUtil.isNotNull(code))) {
			// 内部打开
			// if(StringUtil.isNull(scode) && StringUtil.isNull(code)){
			// response.sendRedirect(oauth2buildAuthorizationUrl(getRedirectUri(request),
			// null));
			// return;
			// }

			logger.debug("scode:" + scode + "   code:" + code + "	from:" + from);

			String userid = null;
			if (StringUtil.isNotNull(code)) {
				try {
					JSONObject json = new JSONObject();
					String addAppKey = ResourceUtil
							.getKmssConfigString("kmss.ding.addAppKey");
					if (StringUtil.isNotNull(addAppKey)
							&& "true".equals(addAppKey)) {
						logger.debug(
								"----------------F4 pc单点-------------------");
						String dingAppKey = request.getParameter("dingAppKey");
						if (StringUtil.isNull(dingAppKey)) {
							logger.warn("F4 单点登录，dingAppKey为空");
						}
						if ("dingmng".equals(from)) {
							logger.debug("ISV模式外部浏览器跳转到EKP的单点登录");
							json = DingUtils.getDingApiService()
									.getSSOUserInfo(code,
											request.getParameter("dingAppKey"));
						} else {
							json = DingUtils.getDingApiService()
									.getUserInfoByDingAppKey(code, dingAppKey);
						}

					} else if("dingmng".equals(from) && !"true".equals(ResourceUtil.getKmssConfigString("kmss.ding.addAppKey"))){
						//钉钉后台管理从外部浏览器跳转到EKP的单点登录（企业自建应用模式）
						logger.warn("钉钉后台管理从外部浏览器跳转到EKP的单点登录（企业自建应用模式）");
						json = DingUtils.getDingApiService()
								.getSSOUserInfo(code,
										request.getParameter("dingAppKey"));
					} else {
						json = DingUtils.getDingApiService().getUserInfo(code);
					}
					logger.debug("钉钉返回userInfo=" + json.toString());
					if ("0".equals(json.getString("errcode"))) {
						if(json.containsKey("userid")) {
							userid = json.getString("userid");
						} else if(json.containsKey("user_info")) {
							userid = json.getJSONObject("user_info").getString("userid");
						}
					} else {
						logger.warn(
								"根据请求的code：" + code + "获取用户userid失败->" + json);
					}
					logger.debug("请求的code=" + code + ",userid=" + userid);
				} catch (Exception e) {
					logger.warn("从钉钉开放授权中获取用户名失败！", e);
				}
			}else if (StringUtil.isNotNull(scode)) {
				try {
					logger.debug("外部请求的scode=" + scode + ",userid=" + userid);
					IDingCodeService dingCodeService = (IDingCodeService) SpringBeanUtil.getBean("dingCodeService");
					userid = dingCodeService.getUseridByCode(scode);
					dingCodeService.deleteByCode(scode);
				} catch (Exception ex) {
					logger.error("", ex);
					ex.printStackTrace();
				}
			}
			// 通过钉钉Userid进行登录
			if (StringUtil.isNotNull(userid)) {
				// 如果授权失败，使用映射表查找ekpid，用ekpid登陆

				String addAppKey = ResourceUtil
						.getKmssConfigString("kmss.ding.addAppKey"); // F4参数
				IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil
						.getBean("omsRelationService");
				HQLInfo hqlInfo = new HQLInfo();
				String whereBlock = "omsRelationModel.fdAppPkId = :fdAppPkId and omsRelationModel.fdType = 8";
				if (StringUtil.isNotNull(addAppKey)
						&& "true".equals(addAppKey)) {
					logger.debug("----------------F4 pc单点-------------------");
					whereBlock = "omsRelationModel.fdAppPkId = :fdAppPkId and omsRelationModel.fdType = 8 and omsRelationModel.fdAppKey=:fdAppKey";
					String re_url = getRedirectUri(request);
					logger.debug("跳转    url:" + getRedirectUri(request));
					String dingAppKey = null;
					Map<String, String[]> map = request.getParameterMap();
					for (String key : map.keySet()) {
						if ("dingAppKey".equals(key)) {
							dingAppKey = map.get(key)[0];
							break;
						}
					}
					logger.debug("--------F4------  dingAppKey:" + dingAppKey);
					if (StringUtil.isNull(dingAppKey)) {
						logger.warn(
								"-----  F4 单点链接url没有 dingAppKey 参数，无法单点验证------");
						String redirectUtl = getRedirectUri(request);
						response.sendRedirect(redirectUtl);
						return;
					}

					hqlInfo.setParameter("fdAppPkId", userid);
					hqlInfo.setParameter("fdAppKey", dingAppKey);

				} else {
					hqlInfo.setParameter("fdAppPkId", userid);
				}
				hqlInfo.setWhereBlock(whereBlock);
				try {
					OmsRelationModel model = (OmsRelationModel) omsRelationService.findFirstOne(hqlInfo);
					if (model != null) {
						String ekpid = model.getFdEkpId();
						autoLoginHelper.doAutoLogin(ekpid, "id", session);
						logger.debug("登录成功，id=" + ekpid);
					} else {
						if (StringUtil.isNotNull(addAppKey)
								&& "true".equals(addAppKey)) {
							logger.warn(
									"------F4 开启了addAppKey参数，不直接通过userId作为登录名校验--------");

						} else {
							autoLoginHelper.doAutoLogin(userid, session);
							logger.debug("登录成功，LoginName=" + userid);
						}
					}
					// 跳转url
					String redirectUtl = getRedirectUri(request);
					response.sendRedirect(redirectUtl);
					return;
				} catch (Exception e) {
					logger.warn("从钉钉开放授权中获取用户名失败！", e);
				}
			}
		}
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
	 */
	private final static boolean checkIsAnonymous(HttpServletRequest request) {
		String path = request.getPathInfo();
		if (StringUtil.isNull(path)) {
            path = request.getServletPath();
        }
		path = "/".equals(path) ? "" : path;
		path = StringUtil.isNull(path) ? PdaFlagUtil.CONST_LOGINURL : path;
		if (path.contains("/third/ding/pc")) {
			return true;
		}
		return PdaFlagUtil.checkIsAnonymous(path);
	}	
}
