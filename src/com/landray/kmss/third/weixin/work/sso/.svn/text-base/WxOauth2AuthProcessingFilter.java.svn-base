package com.landray.kmss.third.weixin.work.sso;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.authentication.AutoLoginHelper;
import com.landray.kmss.sys.authentication.integration.KmssAuthenticationProcessingFilter;
import com.landray.kmss.sys.authentication.intercept.RoleValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.filter.AuthOrgVisibleFilter;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;
import com.landray.kmss.third.weixin.model.ThirdWeixinContactMapp;
import com.landray.kmss.third.weixin.model.ThirdWeixinDomainCheck;
import com.landray.kmss.third.weixin.model.WeixinConfig;
import com.landray.kmss.third.weixin.service.IThirdWeixinChatDataMainService;
import com.landray.kmss.third.weixin.service.IThirdWeixinContactMappService;
import com.landray.kmss.third.weixin.service.IThirdWeixinDomainCheckService;
import com.landray.kmss.third.weixin.work.api.CorpGroupAppShareInfo;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.constant.WxworkConstant;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinCgUserMapp;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinCgUserMappService;
import com.landray.kmss.third.weixin.work.spi.model.WxworkOmsRelationModel;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 与EKP与微信进行Oauth2进行单点登录时使用的过滤器
 *
 * @author wubing 2015-06-12
 */
public class WxOauth2AuthProcessingFilter implements Filter, InitializingBean,
		WxworkConstant {

	private static final Logger logger = LoggerFactory.getLogger(WxOauth2AuthProcessingFilter.class);

	private final String WXWORKSSOKEY = "WXWORKSSOKEY";

	private WxworkApiService wxworkApiService = null;

	@Override
    public void afterPropertiesSet() throws Exception {
		Assert.notNull(autoLoginHelper);
	}

	public String oauth2buildAuthorizationUrl(String redirectUri,String state) {
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?";
		String corpId = null;
		try {
			BaseAppConfig appConfig = (BaseAppConfig) com.landray.kmss.util.ClassUtils.forName(
					"com.landray.kmss.third.weixin.work.model.WeixinWorkConfig")
					.newInstance();
			corpId = (String) appConfig.getDataMap().get("wxCorpid");

		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}
		url = url + "appid=" + corpId;
		logger.warn("redirectUri:"+redirectUri);
		try {
			url = url + "&redirect_uri="
					+ URLEncoder.encode(redirectUri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			url = url + "&redirect_uri=" + URLEncoder.encode(redirectUri);
		}
		url = url.replace("%26q.", "%2526q.");
		url = url + "&response_type=code";
		url = url + "&scope=snsapi_base";
		if (state != null) {
			url = url + "&state=" + state;
		}
		url = url + "#wechat_redirect";
		logger.info("重定向的url:" + url);
		return url;
	}

	private String getRedirectUri(HttpServletRequest request,String code,String ssoKey) {
		Map<String, String[]> map = request.getParameterMap();
		StringBuffer sb = new StringBuffer();
		int n = 0;
		String val = null;
		for (String key : map.keySet()) {
			if("code".equals(key) || "fwxcd".equals(key)){
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
		String url = WxworkUtils.getWxworkDomain()+request.getServletPath() + sb.toString();
		if(StringUtil.isNotNull(ssoKey)) {
            url+=ssoKey;
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

	private static Map<String,Integer> fwxcdMap = new ConcurrentHashMap<String,Integer>(2000);

	@Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		// 判断是否是域名校验的请求
		String fileName = getCheckDomainFileName(request);
		if (StringUtil.isNotNull(fileName)) {
			repsonseFile(fileName, response);
			return;
		}
//		String voiceMainId = getVoiceChatDataId(request);
//		if (StringUtil.isNotNull(voiceMainId)) {
//			if(!authValidate()){
//				throw new ServletException("没有权限");
//			}
//			forward2DownloadVoice(request,response,voiceMainId);
//			return;
//		}

		HttpSession session = request.getSession();
		String code = request.getParameter("code");
		String filterEnable = WeixinWorkConfig.newInstance().getWxOauth2Enabled();
		String wxfilterEnable = WeixinConfig.newInstance().getWxOauth2Enabled();
		String agentid = WeixinWorkConfig.newInstance().getWxAgentid();
		String url="http://" + request.getServerName()+ ":"   + request.getServerPort() + request.getRequestURI();
		String queryurl=request.getQueryString();
		String pcUrl = StringUtil.getParameter(queryurl, "pc");
		String ou = StringUtil.getParameter(queryurl, "oauth");
		String cd = StringUtil.getParameter(queryurl, "code");
		String pcWXSso = StringUtil.getParameter(queryurl, "pcWXSso");
		if(StringUtil.isNotNull(queryurl)){
			url += "?"+queryurl;
		}
//		logger.debug(
//				"filterEnable:" + filterEnable + " !autoLoginHelper.hasLogin():"
//						+ !autoLoginHelper.hasLogin()
//						+ "MobileUtil.getClientType(request) == MobileUtil.THIRD_WXWORK:"
//						+ (MobileUtil.getClientType(
//								request) == MobileUtil.THIRD_WXWORK)
//						+ "!checkIsAnonymous(request):"
//						+ !checkIsAnonymous(request) +
//						"-------------UserUtil.getUser().isAnonymous():"
//						+ UserUtil.getUser().isAnonymous()
//						+ "-------------UserUtil.getUser().getFdName():"
//						+ UserUtil.getUser().getFdName());

		if ("true".equals(filterEnable) && !autoLoginHelper.hasLogin()
				&& ((MobileUtil
				.getClientType(request) == MobileUtil.THIRD_WXWORK
				|| MobileUtil
				.getClientType(request) == MobileUtil.WXWORK_PC)
				|| ("false".equals(wxfilterEnable) && MobileUtil
				.getClientType(request) == MobileUtil.THIRD_WEIXIN))
				&& !checkIsAnonymous(request)
				&& UserUtil.getUser().isAnonymous()) {
			logger.debug("code:" + code);
			if (StringUtil.isNull(code)) {
				String wxSSOHashHandle = WeixinWorkConfig.newInstance().getWxSSOHashHandle();
				logger.debug("MobileUtil.getClientType(request):"+MobileUtil.getClientType(request));
				if(StringUtil.isNotNull(wxSSOHashHandle) &&
						((wxSSOHashHandle.contains("pc") && MobileUtil.getClientType(request) == MobileUtil.WXWORK_PC)
								|| (wxSSOHashHandle.contains("mobile") && MobileUtil.getClientType(request) != MobileUtil.WXWORK_PC))){
					request.getSession().setAttribute("wxWorkSsoUrl",oauth2buildAuthorizationUrl(
							getRedirectUri(request, null,WXWORKSSOKEY), null));
					response.sendRedirect(WxworkUtils.getWxworkDomain()+"/third/weixin/work/sso/sso.jsp");
					return;
				}
				logger.debug("正常oauth2方式，不走中间页面");
				response.sendRedirect(oauth2buildAuthorizationUrl(
						getRedirectUri(request, null,null), null));
				return;
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("微信返回CODE=" + code);
				}
				String username = null;
				String external_userid = null;
				String openid = null;
				try {
					String fwxcd = request.getParameter("fwxcd");
					JSONObject result = wxworkApiService
							.oauth2getUserInfo(agentid, code);

					if (result.getIntValue("errcode") == 0) {
						external_userid = result.getString("external_userid");
						username = result.getString("UserId");
						openid = result.getString("OpenId");
						logger.debug(
								"EKP与微信开放授权  OK。username:" + username+", external_userid："+external_userid+", openid:"+openid);
						if (StringUtil.isNotNull(fwxcd)) {
							logger.debug("--------原来code:{},获取ueserid成功({})，新code:{}", fwxcd, username, code);
							if (fwxcdMap.containsKey(fwxcd)) {
								fwxcdMap.remove(fwxcd);
							}
							if (fwxcdMap.size() > 2000) {
								fwxcdMap.clear();
							}
						}
					}else {
						Integer errcode = result.getIntValue("errcode");

						if (errcode == 40029) {
							if (fwxcdMap.size() > 2000) {
								fwxcdMap.clear();
							}
							//无效code,重试获取code
							if (StringUtil.isNull(fwxcd)) {
								fwxcd = code;
							}
							//次数记录，大于3次则退出尝试
							if(!fwxcdMap.containsKey(fwxcd)){
								fwxcdMap.put(fwxcd, 1);
							}else{
								fwxcdMap.put(fwxcd,fwxcdMap.get(fwxcd) + 1);
							}
							if (fwxcdMap.get(fwxcd) != null && fwxcdMap.get(fwxcd) <= 3) {
								logger.info("code无效，尝试重新获取("+ fwxcdMap.get(fwxcd) + ")...");
								String redirectUrl = wxworkApiService
										.oauth2buildAuthorizationUrl(
												getRedirectUri(
														request,
														fwxcd,
														null),
												null);
								logger.debug("---redirectUrl:"+redirectUrl);
								response.sendRedirect(redirectUrl);
								return;
							}else{
								logger.warn("---尝试单点企业微信重试次数超过3次,将不再继续---次数[{}]",fwxcdMap.get(fwxcd));
							}
						} else {
							logger.warn("从微信开放授权中获取用户名失败！"+ result.getString("errmsg"));
						}
					}
				} catch (Exception e) {
					logger.warn("从微信开放授权中获取用户名失败！", e);
				}

				if (StringUtil.isNull(StringUtil.getString(username)) && StringUtil.isNull(StringUtil.getString(external_userid)) && StringUtil.isNull(StringUtil.getString(openid))) {
					logger.debug("Wx Code couldn't get keyword ." + username);
				} else {
					String loginUsername = login(username, external_userid,openid, session);
					if (StringUtil.isNotNull(loginUsername)) {
						// 如果登录成功，继续走过滤器链
						chain.doFilter(request, response);
						return;
					}
					// 如果登录不成功，且访问的是中间跳转页面，则不走过滤器链，直接进入页面
					String path = request.getRequestURI();
					if (StringUtil.isNotNull(path)
							&& path.endsWith(
							"/third/weixin/work/sso/pc.jsp")) {
						request.getRequestDispatcher(
								path)
								.forward(request, response);
						return;
					}
				}
			}
		} else if (StringUtil.isNotNull(pcUrl)
				&& (StringUtil.isNotNull(cd) || OAUTH_EKP_FLAG.equals(ou))
				&& MobileUtil.getClientType(request) == MobileUtil.PC
				&& !checkIsAnonymous(request)) {
			String domainName = WeixinWorkConfig.newInstance().getWxDomain();
			if(StringUtil.isNull(domainName)){
				url = StringUtil.formatUrl(pcUrl);
			}else{
				if(pcUrl.startsWith("http")){
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
				&& StringUtil.isNotNull(cd)
				&& !checkIsAnonymous(request)) {
			String xul = getRedirectUri(request,null,null);
			if(autoLoginHelper.hasLogin()){
				response.sendRedirect(xul);
				return;
			}
			String username = null;
			String external_userid = null;
			String openid = null;
			try {
				JSONObject result = wxworkApiService
						.oauth2getUserInfo(agentid, cd);
				username = result.getString("UserId");
				external_userid = result.getString("external_userid");
				openid = result.getString("OpenId");
				logger.debug("EKP与微信开放授权  OK username:" + username+", external_userid:"+external_userid+", openid:"+openid);
			} catch (Exception e) {
				logger.warn("从微信开放授权中获取用户名失败！", e);
			}

			if (StringUtil.isNull(StringUtil.getString(username)) && StringUtil.isNull(StringUtil.getString(external_userid)) && StringUtil.isNull(StringUtil.getString(openid))) {
				logger.debug("Wx Code couldn't get keyword ." + username);
			} else {
				login(username,external_userid,openid, session);
				response.sendRedirect(xul);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * 微信客户单点
	 * @return
	 */
	private String loginByContact(String external_userid, HttpSession session) throws Exception {
		IThirdWeixinContactMappService thirdWeixinContactMappService = (IThirdWeixinContactMappService) SpringBeanUtil
				.getBean("thirdWeixinContactMappService");
		List<ThirdWeixinContactMapp> contactMapps = thirdWeixinContactMappService.findByContactId(external_userid);
		if(contactMapps==null || contactMapps.isEmpty()){
			logger.info("找不到客户映射关系，客户ID："+external_userid);
			return null;
		}
		String ekpid = contactMapps.get(0).getFdExternalId();
		if(StringUtil.isNull(ekpid)){
			logger.info("客户映射关系中fdExternalId为空，客户ID："+external_userid);
			return null;
		}
		return autoLoginHelper.doAutoLogin(ekpid, "id", session);
	}

	/**
	 * 下游用户单点
	 * @param username 格式为corpid/userid
	 * @param session
	 * @return
	 */
	private String loginByCorpgroup(String username, HttpSession session) throws Exception {
		String[] ss = username.split("/");
		String corpid = ss[0];
		String userid = ss[1];
		IThirdWeixinCgUserMappService thirdWeixinCgUserMappService = (IThirdWeixinCgUserMappService)SpringBeanUtil.getBean("thirdWeixinCgUserMappService");
		ThirdWeixinCgUserMapp mapp = thirdWeixinCgUserMappService.findByUserId(corpid,userid);
		if(mapp!=null){
			return autoLoginHelper.doAutoLogin(mapp.getFdEkpId(), "id", session);
		}
		return null;
	}

	/**
	 * 内部用户单点
	 * @param username 微信userid
	 * @param session
	 * @return
	 * @throws Exception
	 */
	private String loginInnerUser(String username, HttpSession session) throws Exception {
		IWxworkOmsRelationService relationService = (IWxworkOmsRelationService) SpringBeanUtil
				.getBean("wxworkOmsRelationService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdAppPkId = :fdAppPkId");
		hqlInfo.setParameter("fdAppPkId", username);
		List<WxworkOmsRelationModel> relations = relationService
				.findList(hqlInfo);
		if (relations.size() == 1) {
			String ekpid = relations.get(0).getFdEkpId();
			return autoLoginHelper.doAutoLogin(ekpid, "id", session);
		} else if (relations.size() > 1) {
			ISysOrgElementService elementService = (ISysOrgElementService) SpringBeanUtil
					.getBean("sysOrgElementService");
			SysOrgElement person = null;
			for (WxworkOmsRelationModel model : relations) {
				if (model != null && StringUtil
						.isNotNull(model.getFdEkpId())) {
					person = (SysOrgElement) elementService
							.findByPrimaryKey(
									model.getFdEkpId(), null,
									true);
					if (person.getFdOrgType().intValue() == 8) {
						return autoLoginHelper.doAutoLogin(
								model.getFdEkpId(), "id",
								session);
					}
				}
			}
		} else {
			return autoLoginHelper.doAutoLogin(username, session);
		}
		return null;
	}

	private String login(String username, String external_userid,String openid, HttpSession session) {
		try {
			if(StringUtil.isNotNull(external_userid)){
				return loginByContact(external_userid,session);
			}else if(StringUtil.isNotNull(username)) {
				if(username.contains("/")){
					return loginByCorpgroup(username,session);
				}else {
					return loginInnerUser(username,session);
				}
			}else if(StringUtil.isNotNull(openid)) {
				Map<String, CorpGroupAppShareInfo> shareInfoMap = wxworkApiService.getAppShareInfoMap();
				if(shareInfoMap==null || shareInfoMap.isEmpty()){
					return null;
				}
				for(String corpid:shareInfoMap.keySet()){
					try {
						JSONObject resultJson = wxworkApiService.convert2Userid(openid, corpid, shareInfoMap.get(corpid).getAgentId());
						String userid = resultJson.getString("userid");
						if(StringUtil.isNotNull(userid)){
							return loginByCorpgroup(corpid+"/"+userid,session);
						}
					}catch (Exception e){
						logger.info(e.getMessage(),e);
					}
				}
			}
		} catch (Exception e) {
			logger.warn("从企业微信开放授权中获取用户名失败！", e);
		}
		return null;
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
		wxworkApiService = WxworkUtils.getWxworkApiService();
	}

	private volatile AutoLoginHelper autoLoginHelper;

	public void setAutoLoginHelper(AutoLoginHelper autoLoginHelper) {
		this.autoLoginHelper = autoLoginHelper;
	}

	/**
	 * 该路径是否不需要拦截
	 *
	 * @param request
	 * @return
	 */
	private final boolean checkIsAnonymous(HttpServletRequest request) {
		String path = request.getPathInfo();
		if (StringUtil.isNull(path)) {
            path = request.getServletPath();
        }
		path = "/".equals(path) ? "" : path;
		path = StringUtil.isNull(path) ? PdaFlagUtil.CONST_LOGINURL : path;
		if ("/third/wxwork/jsapi/wxJsapi.do".equals(path)) {
			return true;
		}
		return PdaFlagUtil.checkIsAnonymous(path)
				|| getAuthenticationProcessingFilter()
				.isIgnorePathRequest(request);

	}


	private KmssAuthenticationProcessingFilter authenticationProcessingFilter;

	public KmssAuthenticationProcessingFilter
			getAuthenticationProcessingFilter() {
		if (authenticationProcessingFilter == null) {
			authenticationProcessingFilter = (KmssAuthenticationProcessingFilter) SpringBeanUtil
					.getBean("authenticationProcessingFilter");
		}
		return authenticationProcessingFilter;
	}

	private final String getCheckDomainFileName(HttpServletRequest request) {
		String path = request.getPathInfo();
		if (StringUtil.isNull(path)) {
            path = request.getServletPath();
        }
		if (StringUtil.isNull(path)) {
			return null;
		}
		if (path.startsWith("/WW_verify_") && path.endsWith(".txt")) {
			return path.substring(1);
		}
		return null;
	}

	private IThirdWeixinDomainCheckService thirdWeixinDomainCheckService;

	public IThirdWeixinDomainCheckService getThirdWeixinDomainCheckService() {
		if (thirdWeixinDomainCheckService == null) {
			thirdWeixinDomainCheckService = (IThirdWeixinDomainCheckService) SpringBeanUtil
					.getBean("thirdWeixinDomainCheckService");
		}
		return thirdWeixinDomainCheckService;
	}

	public void repsonseFile(String fileName, HttpServletResponse response) {
		try {
			ThirdWeixinDomainCheck check = getThirdWeixinDomainCheckService()
					.findByFileName(fileName);
			if (check == null) {
				logger.error("找不到文件对应的记录，文件名：" + fileName);
				return;
			}
			response.reset();// 清除首部的空白行
			response.setContentType("txt");// 是设置文件类型的
			response.addHeader("Content-Disposition",
					"attachment; filename=\"" + fileName + "\"");
			response.getOutputStream()
					.write(check.getFdFileContent().getBytes("UTF-8"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private String getVoiceChatDataId(HttpServletRequest request) {
		String path = request.getPathInfo();
		if (StringUtil.isNull(path)) {
            path = request.getServletPath();
        }
		if (StringUtil.isNull(path)) {
			return null;
		}
		if (path.startsWith("/third/weixin/chatdata/voice") && path.endsWith(".ogg")) {
			return request.getParameter("fdId");
		}
		return null;
	}

	private boolean authValidate(){
		KMSSUser user = UserUtil.getKMSSUser();
		if(user.isAdmin()){
			return true;
		}
		String validatorParas = "role=ROLE_THIRDWXWORK_ADMIN";
		ValidatorRequestContext context = new ValidatorRequestContext();
		context.setUser(user);
		context.addValidatorParas(validatorParas);
		return getRoleValidator().validate(context);
	}


	private IThirdWeixinChatDataMainService thirdWeixinChatDataMainService;

	public IThirdWeixinChatDataMainService getThirdWeixinChatDataMainService() {
		if (thirdWeixinChatDataMainService == null) {
			thirdWeixinChatDataMainService = (IThirdWeixinChatDataMainService) SpringBeanUtil
					.getBean("thirdWeixinChatDataMainService");
		}
		return thirdWeixinChatDataMainService;
	}

	private RoleValidator roleValidator;

	public RoleValidator getRoleValidator() {
		if (roleValidator == null) {
			roleValidator = (RoleValidator) SpringBeanUtil.getBean("roleValidator");
		}
		return roleValidator;
	}

	private ISysAttMainCoreInnerService sysAttMainService;

	public ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}

	private void forward2DownloadVoice(HttpServletRequest request,HttpServletResponse response, String voiceMainId) throws ServletException, IOException {
		ThirdWeixinChatDataMain thirdWeixinChatDataMain = null;
		try {
			thirdWeixinChatDataMain = (ThirdWeixinChatDataMain)getThirdWeixinChatDataMainService().findByPrimaryKey(voiceMainId);
			if(thirdWeixinChatDataMain==null){
				throw new Exception("");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ServletException("没有找到对应的会话记录");
		}
		String fdKey = thirdWeixinChatDataMain.getFdFileId();
		if(fdKey.length()>450){
			fdKey = fdKey.substring(0,450);
		}
		SysAttMain attMain = null;
		try {
			List list = getSysAttMainService().findByModelKey(ThirdWeixinChatDataMain.class.getName(),voiceMainId,fdKey);
			if(list==null || list.isEmpty()){
				throw new Exception();
			}
			attMain = (SysAttMain)list.get(0);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ServletException("找不到对应的附件");
		}
		request.getRequestDispatcher("/sys/attachment/sys_att_main/sysAttMain.do?method=readDownload&fdId="+attMain.getFdId()).forward(request,response);
	}
}
