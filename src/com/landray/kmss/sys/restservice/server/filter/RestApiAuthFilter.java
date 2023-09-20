package com.landray.kmss.sys.restservice.server.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.log.constant.SystemLogConstant;
import com.landray.kmss.sys.log.service.ISysLogSystemService;
import com.landray.kmss.sys.log.util.SystemLogHelper;
import com.landray.kmss.sys.restservice.client.cloud.EkpCloudConstants;
import com.landray.kmss.sys.restservice.client.cloud.IEkpCloudClient;
import com.landray.kmss.sys.restservice.server.constant.SysRsConstant;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerMain;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerPolicy;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerLogService;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerMainService;
import com.landray.kmss.sys.restservice.server.util.SysRsUtil;
import com.landray.kmss.util.DESEncrypt;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.Globals;
import com.landray.kmss.web.filter.security.CryptoUtil;
import com.landray.kmss.web.util.RequestUtils;
import com.landray.kmss.web.util.RestResponseUtils;

/**
 * 用于RestApi的验证
 * @author yanmj
 */
public class RestApiAuthFilter extends OncePerRequestFilter {
	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(RestApiAuthFilter.class);
    
	@SuppressWarnings("unused")
	private final BasicAuthenticationEntryPoint basicAuth = new BasicAuthenticationEntryPoint();
	private DESEncrypt encrypt;
	private Filter oauth2Filter;

	private ISysRestserviceServerMainService sysRestserviceServerMainService;
	private ISysRestserviceServerLogService sysRestserviceServerLogService;
	private ISysAppConfigService sysAppConfigService;
	private ISysLogSystemService sysLogSystemService;

	private IEkpCloudClient ekpCloudClient;

	public void setEkpCloudClient(IEkpCloudClient ekpCloudClient) {
		this.ekpCloudClient = ekpCloudClient;
	}

	@Override
	protected final void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		RequestUtils.markApiRequest(request); //用于跳过ErrorPageAction
		if(logger.isDebugEnabled()) {
			logger.debug("RestApiAuthFilter invoked, URI=" + request.getRequestURI());
		}
		
		SysRestserviceServerMain registedService = null;
		try {
			//解析请求的服务名与模块名
			String names[] = RequestUtils.extractModuleAndServiceNameFromUri(request);
			String serviceName = names[1];
			String moduleName = names[0];
			if (StringUtil.isNull(serviceName)) {
				filterChain.doFilter(request, response);
				return;
			}

			//找到系统中注册的服务
			registedService = findRegistedService(serviceName, moduleName);
			if (registedService == null) {
				sendError(request, response, SystemLogConstant.FDSUCCESS_EXCEPTION_REQUEST, null, HttpStatus.NOT_FOUND,
						registedService);
				return;
			}

			//校验REST服务状态
			if (!checkServiceStatus(response, registedService)) {
				sendError(request, response, SystemLogConstant.FDSUCCESS_EXCEPTION_REQUEST, null, HttpStatus.NOT_FOUND,
						registedService);
				return;
			}

			//消息体大小限制
			if (!checkRequestBodySize(request, registedService)) {
				sendError(request, response, SystemLogConstant.FDSUCCESS_LIMITED_BODY, null,
						HttpStatus.REQUEST_ENTITY_TOO_LARGE, registedService);
				return;
			}
			
			boolean ipError = false;
			Boolean haveReadBasic = false;
			String userName = null;
			String password = null;
			for (SysRestserviceServerPolicy policy : registedService.getFdPolicy()) {
				ipError = false;
				//策略是否为允许匿名
				if (allowAnonymous(moduleName, serviceName, policy)) {
					//是否为允许的IP
					if (!allowRemoteHost(request, policy)) {
						ipError = true;
						continue;
					}
					response.setStatus(HttpServletResponse.SC_OK);
					filterChain.doFilter(request, response);
					return;
				}

				if (SysRsConstant.POLICY_MODEL_USER.equals(policy.getFdPolicy())) {
					//Basic 认证
					if (doBasic(policy, userName, password, haveReadBasic, request, response, registedService)) {
						//是否为允许的IP
						if (!allowRemoteHost(request, policy)) {
							ipError = true;
							break;
						}
						response.setStatus(HttpServletResponse.SC_OK);
						filterChain.doFilter(request, response);
						return;
					}

				} else if (SysRsConstant.POLICY_MODEL_SECRETKEY.equals(policy.getFdPolicy()) && EkpCloudConstants.CLOUD_ACCESSABLE) {
					//固定密钥认证
					if (doSecretKey(request, response, policy)){
						//是否为允许的IP
						if (!allowRemoteHost(request, policy)) {
							ipError = true;
							break;
						}
						response.setStatus(HttpServletResponse.SC_OK);
						filterChain.doFilter(request, response);
						return;
					}
				}
			}
			
			if (ipError) {
				logger.warn("IP验证不通过");
				sendError(request, response, SystemLogConstant.FDSUCCESS_ILLEGAL_IP, null, HttpStatus.FORBIDDEN,
						registedService);
				
			} else {
				logger.warn("用户/密码错误");
				sendError(request, response, SystemLogConstant.FDSUCCESS_UNAUTHORIZED_USER, null, HttpStatus.UNAUTHORIZED,
						registedService);
			}
		} catch (Exception e) {
			logger.error("INTERNAL SERVER ERROR", e);
			String fdErrorMsg = SysRsUtil.getStackTrace(e);
			sendError(request, response, SystemLogConstant.FDSUCCESS_EXCEPTION_SERVER, fdErrorMsg,
					HttpStatus.INTERNAL_SERVER_ERROR, registedService);
		}
	}

	private boolean checkRequestBodySize(HttpServletRequest request, SysRestserviceServerMain registedService) {
		Long fdMaxBodySize = registedService.getFdMaxBodySize();
		if (fdMaxBodySize != null && fdMaxBodySize > 0) {
			int contentLength = request.getContentLength();
			if (logger.isDebugEnabled()) {
				logger.debug("Request content length:" + contentLength);
			}
			try {
				if (fdMaxBodySize < contentLength) {
					logger.warn("消息体过大：" + contentLength + ",限制为：" + fdMaxBodySize);
					return false;
				}
			} catch (Exception e) {
				logger.error("消息大小对比出错", e);
			}
		}
		return true;
	}
	
	/**
	 * 固定密钥认证
	 */
	private boolean doSecretKey(HttpServletRequest request, HttpServletResponse response,
			SysRestserviceServerPolicy policy){
		//Header Name
		String headername = policy.getFdHeadername();
		if(StringUtil.isNull(headername)){
			headername = EkpCloudConstants.DEFAULT_SECURITY_HEADER;
		}
		//Header Value
		String headerVal = request.getHeader(headername);
		if (StringUtil.isNull(headerVal)) {
			if (logger.isWarnEnabled()) {
				logger.warn("Request [" + request.getRequestURI() + "] from [" + request.getRemoteAddr()
						+ "]" + " without basic Authorization header, response with 401.");
			}
			return false;
		}
		//密钥
		String key = policy.getFdSecretKey();
		//服务名称
		// String appname = policy.getFdAppname();
		// if(StringUtil.isNull(appname)){
		// appname =
		// ResourceUtil.getKmssConfigString(EkpCloudConstants.CLOUD_EXPOSE_APPNAME_KEY);
		// }
		// if(StringUtil.isNull(appname)){
		// appname = EkpCloudConstants.DEFAULT_EKP_APPNAME;
		// }
		List<String> appNames = ekpCloudClient.getAppNames();
		boolean flag = false;
		try{
			flag = decryptCheck(key, appNames, headerVal);
		}catch (Exception e) {
			logger.error("固定密钥认证失败", e);
		}
		return flag;
	}

	private boolean decryptCheck(String key, List<String> appNames, String val)
			throws Exception {
		String s = key + EkpCloudConstants.CLOUD_DEFAULT_KEY_TAIL;
		String realKey = s.substring(0, 16);
		String iv = s.substring(16, 32);
		String decrypt = new String(Hex.decode(val));
		decrypt = CryptoUtil.aesDecrypt(realKey, iv, decrypt);
		for (String appName : appNames) {
			if (decrypt.equalsIgnoreCase(appName)) {
				return true;
			}
		}
		return false;
	}

	private void sendError(HttpServletRequest request, HttpServletResponse response, int fdErrorStatus, String fdErrorMsg, HttpStatus status,
			SysRestserviceServerMain registedService) throws IOException {
		String authenticate = response.getHeader(HttpHeaders.WWW_AUTHENTICATE);
		response.reset();
		response.setStatus(status.value());
		
		// 申明需补充身份验证信息
		if(StringUtil.isNotNull(authenticate)) {
			response.addHeader(HttpHeaders.WWW_AUTHENTICATE, authenticate);
		}

		// 记录日志
		SystemLogHelper.setFdDesc(buildErrorMsg(fdErrorMsg, status));
		SystemLogHelper.setFdSuccess(fdErrorStatus);
		SystemLogHelper.setStreamTextType(response.getOutputStream());
		// 记录请求内容(在写response之前)
		SystemLogHelper.setFdRequestMsg(request);
		
		// 输出报错响应内容
		RestResponseUtils.writeResponseBody(request, response, status);
	}
	
	private String buildErrorMsg(String fdErrorMsg, HttpStatus status) {
		String statusErrorMsg = status + " " + status.getReasonPhrase();
		if (StringUtil.isNotNull(fdErrorMsg)) {
			fdErrorMsg = statusErrorMsg + "\r\n" + StringUtil.getString(fdErrorMsg);
		} else {
			fdErrorMsg = statusErrorMsg;
		}
		return fdErrorMsg;
	}
	
	/**
	 * 根据serviceName和moduleName组成的URI前缀查找数据库中注册的服务
	 */
	private SysRestserviceServerMain findRegistedService(String serviceName, String moduleName) throws IOException {
		String uriPrefix = "/" + Globals.API_URL_PREFIX + "/" + moduleName + "/" + serviceName;
		try {
			SysRestserviceServerMain server = getSysRestserviceServerMainService().findByURI(uriPrefix);
			if (server == null) {
				SystemLogHelper.setFdSubject(serviceName);
				SystemLogHelper.setFdServiceBean(serviceName);
			} else {
				SystemLogHelper.setFdSubject(server.getFdName());
				SystemLogHelper.setFdServiceBean(server.getFdServiceName());
			}
			return server;
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
	}

	/**
	 * 检查REST服务当前是否可用
	 */
	private boolean checkServiceStatus(HttpServletResponse response, SysRestserviceServerMain registedService)
			throws IOException {
		//服务未启动或已禁用
		if ((registedService.getFdServiceStatus() != null
				&& SysRsConstant.SERVICE_STATUS_START != registedService.getFdServiceStatus().intValue())
				|| SysRsConstant.STARTUP_TYPE_DISABLE.equals(registedService.getFdStartupType())) {
			return false;
		}
		return true;
	}

	/**
	 * 检查是否允许匿名访问
	 */
	private boolean allowAnonymous(String module, String apiServiceName, SysRestserviceServerPolicy policy) {
		//获取token请求默认放行
		//这里的值来自于restApiTokenGeneratorController的声明
		if ("sys_restservice".equals(module) && "oauth".equals(apiServiceName)) {
			return true;
		}
		//策略允许匿名访问
		if (SysRsConstant.POLICY_MODEL_ANONYMOUS.equals(policy.getFdPolicy())) {
			return true;
		}
		return false;
	}

	/**
	 * 检查请求ID是否符合策略中允许的IP
	 */
	private boolean allowRemoteHost(HttpServletRequest request, SysRestserviceServerPolicy policy) {
		//为空允许所有IP
		if (StringUtil.isNull(policy.getFdAccessIp())) {
			return true;
		}
		String clientIp = request.getRemoteAddr();
		if (!SysRsUtil.isExistedIp(clientIp, policy.getFdAccessIp())) {
			if(logger.isDebugEnabled()) {
				logger.debug(clientIp + " FORBIDDEN");
			}
			return false;
		}
		return true;
	}

	/**
	 * OAuth2校验
	 */
	@SuppressWarnings("unused")
	private boolean doOAuth2(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (oauth2Filter == null) {
			throw new ServletException("The OAuth2 filter is not setted.");
		}
		oauth2Filter.doFilter(request, response, filterChain);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null;
	}

	/**
	 * 读取请求信息中的用户密码
	 * @param request
	 * @param response
	 * @param policy
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	private String[] readBasicInfo(HttpServletRequest request, HttpServletResponse response,
			SysRestserviceServerPolicy policy) throws IOException, ServletException {
		//请求头校验
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Basic ")) {
			commence(request, response,
					new AuthenticationCredentialsNotFoundException("AuthenticationCredentialsNotFound"));
			if (logger.isWarnEnabled()) {
				logger.warn("Request [" + request.getRequestURI() + "] from [" + request.getRemoteAddr() + "]"
						+ " without basic Authorization header, response with 401.");
			}
			return null;
		}
		String[] tokens = extractAndDecodeHeader(header, request);
		String username = tokens[0];
		String pw = tokens[1];
		return new String[] { username, pw };
	}

	/**
	 * 因tomcat容器会对没有对应返回页、且申明了错误的响应体，
	 * 增加tomcat的返回页面，故此处替代了basicAuth.commence(...)
	 * <code>
	 * (response.sendError 换成 response.setStatus)
	 * </code>
	 * header中的内容根据最终返回值修改
	 */
	@SuppressWarnings("deprecation")
	private void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
	}

	/**
	 * Basic校验
	 */
	private boolean doBasic(SysRestserviceServerPolicy policy, String userName, String password, Boolean haveReadBasic, HttpServletRequest request, HttpServletResponse response, SysRestserviceServerMain registedService) throws Exception {
		if (!haveReadBasic) {
			//只读取一次basic账号密码信息
			haveReadBasic = true;
			String[] basicInfo = readBasicInfo(request, response, policy);
			if(basicInfo == null){
				response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"null\"");
				return false;
			}
			userName = basicInfo[0];
			password = basicInfo[1];
			SystemLogHelper.setFdUserName(userName);
		}
		//校验账号密码
		if (policy.getFdLoginId().equals(userName) && policy.getFdPassword().equals(SysRsUtil.encryptPwd(password))) {
			//最大连接次数
			if (!checkAccessFrequency(request, response, policy.getFdLoginId(), registedService)) {
				logger.warn("超过最大连接次数: " + policy.getFdLoginId());
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * 解密token
	 * @param header "Basic "+*******
	 * @param request
	 * @return [username,password]
	 * @throws Exception 
	 */
	private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {

		byte[] base64Token = header.substring(6).getBytes(Globals.DEFAULT_CHARSET_UTF8);
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		} catch (IllegalArgumentException e) {
			throw new BadCredentialsException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, Globals.DEFAULT_CHARSET_UTF8);
		if (encrypt != null) {
			try {
				token = encrypt.decryptString(token);
			} catch (Exception e) {
				throw new BadCredentialsException("Failed to decode basic authentication token");
			}
		}
		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}

	/**
	 * 检测客户端的访问频率
	 * @throws IOException 
	 */
	private boolean checkAccessFrequency(HttpServletRequest request, HttpServletResponse response, String userName,
			SysRestserviceServerMain registedService) throws IOException {
		Integer maxConn = registedService.getFdMaxConn();
		if (maxConn == null || maxConn < 0) {
			return true;
		}
		int count = 0;
		try {
			count = getSysRestserviceServerLogService().countAccessFrequency(registedService.getFdServiceName(),
					userName);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		if (count >= maxConn) {
			sendError(request, response, SystemLogConstant.FDSUCCESS_LIMITED_FREQUENT, null, HttpStatus.TOO_MANY_REQUESTS, registedService);
			return false;
		}
		return true;
	}

	public void setEncrypt(DESEncrypt encrypt) {
		this.encrypt = encrypt;
	}

	public void setOauth2Filter(Filter oauth2Filter) {
		this.oauth2Filter = oauth2Filter;
	}

	public ISysRestserviceServerMainService getSysRestserviceServerMainService() {
		if (sysRestserviceServerMainService == null) {
			sysRestserviceServerMainService = (ISysRestserviceServerMainService) SpringBeanUtil
					.getBean("sysRestserviceServerMainService");
		}
		return sysRestserviceServerMainService;
	}

	public ISysRestserviceServerLogService getSysRestserviceServerLogService() {
		if (sysRestserviceServerLogService == null) {
			sysRestserviceServerLogService = (ISysRestserviceServerLogService) SpringBeanUtil
					.getBean("sysRestserviceServerLogService");
		}
		return sysRestserviceServerLogService;
	}

	public ISysAppConfigService getSysAppConfigService() {
		if (sysAppConfigService == null) {
			sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
		}
		return sysAppConfigService;
	}

	public ISysLogSystemService getSysLogSystemService() {
		if (sysLogSystemService == null) {
			sysLogSystemService = (ISysLogSystemService) SpringBeanUtil.getBean("sysLogSystemService");
		}
		return sysLogSystemService;
	}

}
