package com.landray.kmss.third.weixin.mutil.sso;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.authentication.AutoLoginHelper;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.weixin.model.WeixinConfig;
import com.landray.kmss.third.weixin.mutil.api.WxmutilApiService;
import com.landray.kmss.third.weixin.mutil.constant.WxmutilConstant;
import com.landray.kmss.third.weixin.mutil.model.ThirdWxWorkMutilConfig;
import com.landray.kmss.third.weixin.mutil.model.WeixinMutilConfig;
import com.landray.kmss.third.weixin.mutil.service.IThirdWxWorkConfigService;
import com.landray.kmss.third.weixin.mutil.spi.model.WxworkOmsRelationMutilModel;
import com.landray.kmss.third.weixin.mutil.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.mutil.util.WxmutilUtils;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 与EKP与微信进行Oauth2进行单点登录时使用的过滤器
 * 
 * @author wubing 2015-06-12
 */
public class WxOauth2AuthProcessingFilter implements Filter, InitializingBean,
        WxmutilConstant {

	private static final Logger logger = LoggerFactory.getLogger(WxOauth2AuthProcessingFilter.class);

	private WxmutilApiService wxmutilApiService = null;

	@Override
    public void afterPropertiesSet() throws Exception {
		Assert.notNull(autoLoginHelper);
	}

	private String getRedirectUri(HttpServletRequest request, String fdkey) {
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
		String url = request.getServletPath() + sb.toString();
		String domainName = WeixinMutilConfig.newInstance(fdkey).getWxDomain();
		if(StringUtil.isNull(domainName)){
			url = StringUtil.formatUrl(request.getServletPath() + sb.toString());
		}else{
			if(domainName.trim().endsWith("/")) {
                domainName = domainName.trim().substring(0, domainName.length()-1);
            }
			url = domainName+request.getServletPath() + sb.toString();
		}
		return url;
	}

	/**
	 * Does nothing - we rely on IoC lifecycle services instead.
	 */
	@Override
    public void destroy() {
	}

	private volatile static Map<String,String> nameMap = new HashMap<String,String>(2000);
	private static Map<String,Integer> fwxcdMap = new ConcurrentHashMap<String,Integer>(2000);

	@Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		String code = request.getParameter("code");
		//兼容多企业微信，通过url中标识的fdkey来获取对应的配置
		String fdWxKey = request.getParameter("wxkey");
		if (StringUtil.isNull(fdWxKey)) {
//			logger.debug("请配置企业微信wxkey!");
			chain.doFilter(request, response);
			return;
		}
		wxmutilApiService = WxmutilUtils.getWxmutilApiServiceList()
				.get(fdWxKey);

		String filterEnable = WeixinMutilConfig.newInstance(fdWxKey).getWxOauth2Enabled();
		String wxfilterEnable = WeixinConfig.newInstance().getWxOauth2Enabled();
		String agentid = WeixinMutilConfig.newInstance(fdWxKey).getWxAgentid();
		String url="http://" + request.getServerName()+ ":"   + request.getServerPort() + request.getRequestURI(); 
		String queryurl=request.getQueryString();
		String pcUrl = StringUtil.getParameter(queryurl, "pc");
		String ou = StringUtil.getParameter(queryurl, "oauth");
		String cd = StringUtil.getParameter(queryurl, "code");		
		String pcWXSso = StringUtil.getParameter(queryurl, "pcWXSso");
		if(StringUtil.isNull(queryurl)){  
			url += "?"+queryurl;  
		}
		if ("true".equals(filterEnable) && session.getAttribute("KMSS_SECURITY_USER_INFO_LOGIN")==null
				&& ((MobileUtil
					.getClientType(request) == MobileUtil.THIRD_WXWORK ||
					MobileUtil
						.getClientType(request) == MobileUtil.WXWORK_PC)
				|| ("false".equals(wxfilterEnable) && MobileUtil
						.getClientType(request) == MobileUtil.THIRD_WEIXIN))) {
			if (StringUtil.isNull(code)) {
				String oauth = request.getParameter("oauth");
				if (OAUTH_EKP_FLAG.equals(oauth)) {// 对于传过来的标识才进行oauth，避免多次重复跳转
					response.sendRedirect(oauth2buildAuthorizationUrl(
							getRedirectUri(request, fdWxKey), null, fdWxKey));
					return;
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("微信返回CODE=" + code);
				}
				String username = null;
				if(nameMap.containsValue(code)&&session.getAttribute("KMSS_SECURITY_USER_INFO_LOGIN")==null){
					List<String> list = new ArrayList<String>(nameMap.keySet());
					for(String key:list){
						if(code.equals(nameMap.get(key))){
							logger.info("重复回调，直接登录...");
							login(key, session);
						}
					}
				}else{
					try {
						JSONObject result = wxmutilApiService.oauth2getUserInfo(agentid,code);
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
											wxmutilApiService
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
						logger.error("从微信开放授权中获取用户名失败！",e);
					}

					if (StringUtil.isNull(username)) {
						logger.debug("Wx Code couldn't get keyword ." + username);
					} else if(session.getAttribute("KMSS_SECURITY_USER_INFO_LOGIN")==null){
						login(username, session);
					}
				}
			}
		} else if (StringUtil.isNotNull(pcUrl)
				&& (StringUtil.isNotNull(cd) || OAUTH_EKP_FLAG.equals(ou))
				&& MobileUtil.getClientType(request) == MobileUtil.PC) {
			String domainName = WeixinMutilConfig.newInstance(fdWxKey).getWxDomain();
			if(StringUtil.isNull(domainName)){
				url = StringUtil.formatUrl(pcUrl);
			}else{
				if(pcUrl.startsWith("http://")){
					url = pcUrl;
				}else{
					if(domainName.trim().endsWith("/")) {
                        domainName = domainName.trim().substring(0, domainName.length()-1);
                    }
					if (pcUrl.startsWith("/")) {
						url = domainName+pcUrl;
					}else{
						url = domainName + "/" + pcUrl;
					}
				}
			}
			response.sendRedirect(url);
			return;
		} else if (StringUtil.isNotNull(pcWXSso)
				&& "windowswechat".equalsIgnoreCase(pcWXSso)
				&& MobileUtil.getClientType(request) == MobileUtil.PC
				&& StringUtil.isNotNull(cd)) {
			String xul = getRedirectUri(request, fdWxKey);
			if(session.getAttribute("KMSS_SECURITY_USER_INFO_LOGIN")!=null){
				response.sendRedirect(xul);
				return;
			}
			String username = null;
			try {
				JSONObject result = wxmutilApiService
						.oauth2getUserInfo(agentid, code);
				username = result.getString("UserId");
				logger.debug("EKP与微信开放授权  OK username ." + username);
			} catch (Exception e) {
				logger.warn("从微信开放授权中获取用户名失败！", e);
			}

			if (StringUtil.isNull(username)) {
				logger.debug("Wx Code couldn't get keyword ." + username);
			} else if(session.getAttribute("KMSS_SECURITY_USER_INFO_LOGIN")==null){
				login(username, session);
				response.sendRedirect(xul);
				return;
			}
		}
		chain.doFilter(request, response);
	}
	
	private void login(String username, HttpSession session) {
		IWxworkOmsRelationService relationService = (IWxworkOmsRelationService) SpringBeanUtil
				.getBean("mutilWxworkOmsRelationService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdAppPkId = :fdAppPkId");
		hqlInfo.setParameter("fdAppPkId", username);
		try {
			List<WxworkOmsRelationMutilModel> relations = relationService
					.findList(hqlInfo);
			if (relations.size() == 1) {
				String ekpid = relations.get(0).getFdEkpId();
				autoLoginHelper.doAutoLogin(ekpid, "id", session);
			} else if(relations.size()>1){
				ISysOrgElementService elementService = (ISysOrgElementService) SpringBeanUtil
						.getBean("sysOrgElementService");
				SysOrgElement person = null;
				for (WxworkOmsRelationMutilModel model : relations) {
					if (model != null && StringUtil
							.isNotNull(model.getFdEkpId())) {
						person = (SysOrgElement) elementService
								.findByPrimaryKey(
										model.getFdEkpId(), null,
										true);
						if (person.getFdOrgType().intValue() == 8) {
							autoLoginHelper.doAutoLogin(
									model.getFdEkpId(), "id",
									session);
							break;
						}
					}
				}
			} else {
				autoLoginHelper.doAutoLogin(username, session);
			}
		} catch (Exception e) {
			logger.warn("从企业微信开放授权中获取用户名失败！", e);
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
	}

	private volatile AutoLoginHelper autoLoginHelper;

	public void setAutoLoginHelper(AutoLoginHelper autoLoginHelper) {
		this.autoLoginHelper = autoLoginHelper;
	}


	public String oauth2buildAuthorizationUrl(String redirectUri,
			String state, String wxkey) {
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?";
		String corpId = null;
		try {
			IThirdWxWorkConfigService thirdMutilWxWorkConfigService = (IThirdWxWorkConfigService) SpringBeanUtil
					.getBean("thirdMutilWxWorkConfigService");

			HQLInfo info = new HQLInfo();
			info.setSelectBlock("fdValue");
			info.setWhereBlock("fdKey=:fdKey and fdField='wxCorpid'");
			info.setParameter("fdKey", wxkey);
			String fdValue = (String) thirdMutilWxWorkConfigService
					.findFirstOne(info);
			if (StringUtils.isNoneBlank(fdValue)) {
				corpId = fdValue;
			}

		} catch (Exception e1) {
			logger.error("", e1);
		}
		url = url + "appid=" + corpId;
		try {
			url = url + "&redirect_uri="
					+ URLEncoder.encode(redirectUri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
			url = url + "&redirect_uri=" + URLEncoder.encode(redirectUri);
		}
		url = url + "&response_type=code";
		url = url + "&scope=snsapi_base";
		if (state != null) {
			url = url + "&state=" + state;
		}
		url = url + "#wechat_redirect";
		return url;
	}

}
