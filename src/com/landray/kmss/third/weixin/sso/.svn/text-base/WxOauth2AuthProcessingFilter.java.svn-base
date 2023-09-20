package com.landray.kmss.third.weixin.sso;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.authentication.AutoLoginHelper;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.third.weixin.api.WxApiService;
import com.landray.kmss.third.weixin.constant.WxConstant;
import com.landray.kmss.third.weixin.model.WeixinConfig;
import com.landray.kmss.third.weixin.spi.service.IWxOmsRelationService;
import com.landray.kmss.third.weixin.util.WxUtils;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.lang.StringUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 与EKP与微信进行Oauth2进行单点登录时使用的过滤器
 * 
 * @author wubing 2015-06-12
 */
public class WxOauth2AuthProcessingFilter
		implements
			Filter,
			InitializingBean,
			WxConstant {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WxOauth2AuthProcessingFilter.class);

	private WxApiService wxApiService = null;

	@Override
    public void afterPropertiesSet() throws Exception {
		Assert.notNull(autoLoginHelper);
	}

	private String getRedirectUri(HttpServletRequest request,String code) {
		Map<String, String[]> map = request.getParameterMap();
		StringBuffer sb = new StringBuffer();
		int n = 0;
		String val = null;
		for (String key : map.keySet()) {
			if("code".equals(key)){
				continue;
			}
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
		if(StringUtil.isNotNull(code)){
			if(sb.toString().indexOf("&")==-1&&sb.toString().indexOf("?")==-1){
				sb.append("?fwxcd="+code);
			}else{
				sb.append("&fwxcd="+code);
			}
		}
		String url = request.getServletPath() + sb.toString();
		String domainName = WeixinConfig.newInstance().getWxDomain();
		if(StringUtil.isNull(domainName)){
			url = StringUtil.formatUrl(request.getServletPath() + sb.toString());
		}else{
			if(domainName.trim().endsWith("/")) {
                domainName = domainName.trim().substring(0, domainName.length()-1);
            }
			url = domainName+request.getServletPath() + sb.toString();
		}
		logger.debug("单点登录认证的重定向地址："+url);
		return url;
	}

	/**
	 * Does nothing - we rely on IoC lifecycle services instead.
	 */
	@Override
    public void destroy() {
	}

	private static Map<String,String> nameMap = new ConcurrentHashMap<String,String>(5000);
	private static Map<String,Integer> fwxcdMap = new ConcurrentHashMap<String,Integer>(2000);

	@Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		String code = request.getParameter("code");
		String filterEnable = WeixinConfig.newInstance().getWxOauth2Enabled();
		if ("true".equals(filterEnable) && !autoLoginHelper.hasLogin()
				&& MobileUtil.getClientType(request) == MobileUtil.THIRD_WEIXIN
				&& !checkIsAnonymous(request)) {
			if (StringUtil.isNull(code)) {
				response.sendRedirect(
						wxApiService.oauth2buildAuthorizationUrl(
								getRedirectUri(request,null), null));
				return;
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("微信返回CODE=" + code);
				}
				String username = null;
				if(nameMap.containsValue(code)&&!autoLoginHelper.hasLogin()){
					List<String> list = new ArrayList<String>(nameMap.keySet());
					for(String key:list){
						if(code.equals(nameMap.get(key))){
							logger.info("重复回调，直接登录...");
							login(key, session);
						}
					}
				}else{
					try {
						JSONObject result = wxApiService
								.oauth2getUserInfo(code);
						if (result.getIntValue("errcode") == 0) {
							username = result.getString("UserId");

							logger.debug(
									"EKP与微信开放授权  OK username ." + username);
							if (nameMap.size() > 10000) {
								nameMap.clear();
							}
							if (fwxcdMap.size() > 2000) {
								fwxcdMap.clear();
							}
							nameMap.put(username, code);
						} else {
							Integer errcode = result.getIntValue("errcode");

							if (errcode == 40029) {
								String fwxcd = request.getParameter("fwxcd");
								if (StringUtil.isNotNull(fwxcd)) {
									if (fwxcdMap.get(fwxcd) == null
											|| fwxcdMap.get(fwxcd) == 0) {
										fwxcdMap.put(fwxcd, 1);
									} else {
										fwxcdMap.put(fwxcd,
												fwxcdMap.get(fwxcd) + 1);
									}
								} else {
									fwxcd = code;
								}
								if (fwxcdMap.get(fwxcd) <= 3) {
									logger.info("重复回调，尝试重新登录("
											+ fwxcdMap.get(fwxcd) + ")...");
									response.sendRedirect(
											wxApiService
													.oauth2buildAuthorizationUrl(
															getRedirectUri(
																	request,
																	fwxcd),
															null));
								}
								return;
							} else {
								logger.warn("从微信开放授权中获取用户名失败！"
										+ result.getString("errmsg"));
							}
						}
					} catch (Exception e) {
						logger.warn("从微信开放授权中获取用户名失败！", e);
					}
					
					if (StringUtil.isNull(username) || "null".equalsIgnoreCase(username)) {
						logger.debug("Wx Code couldn't get keyword ." + username);
					} else {
						login(username, session);
					}
				}
			}
		}
		chain.doFilter(request, response);
	}

	private void login(String username, HttpSession session) {
		IWxOmsRelationService relationService = (IWxOmsRelationService) SpringBeanUtil
				.getBean("wxOmsRelationService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdEkpId");
		hqlInfo.setWhereBlock("fdAppPkId = :fdAppPkId");
		hqlInfo.setParameter("fdAppPkId", username);
		try {
			String fdEkpId = (String) relationService.findFirstOne(hqlInfo);
			if (StringUtils.isNotBlank(fdEkpId)) {
				autoLoginHelper.doAutoLogin(fdEkpId, "id", session);
			} else {
				autoLoginHelper.doAutoLogin(username, session);
			}
		} catch (Exception e) {
			logger.warn("从微信开放授权中获取用户名失败！", e);
		}
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
		wxApiService = WxUtils.getWxApiService();
	}

	private volatile AutoLoginHelper autoLoginHelper;

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
