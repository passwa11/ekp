package com.landray.kmss.sys.webservice2.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSPasswordCallback;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.xerces.dom.ElementNSImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.log.forms.SysLogSystemForm;
import com.landray.kmss.sys.log.service.ISysLogSystemService;
import com.landray.kmss.sys.log.util.LogConstant.LogSystemType;
import com.landray.kmss.sys.webservice2.constant.SysWsConstant;
import com.landray.kmss.sys.webservice2.exception.SysWsException;
import com.landray.kmss.sys.webservice2.model.SysWebserviceLog;
import com.landray.kmss.sys.webservice2.model.SysWebserviceMain;
import com.landray.kmss.sys.webservice2.model.SysWebserviceUser;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceLogService;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceMainService;
import com.landray.kmss.sys.webservice2.thread.SysWebserviceLogThreadLocal;
import com.landray.kmss.sys.webservice2.util.SysWsUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 在调用服务前验证用户信息并记录日志
 * 
 */
public class CallBeforeInterceptor extends
		AbstractPhaseInterceptor<SoapMessage> {
	private ISysWebserviceLogService sysWebserviceLogService = null;
	private ISysLogSystemService sysLogSystemService = null;
	private ISysAppConfigService sysAppConfigService = null;

	public ISysWebserviceLogService getSysWebserviceLogService() {
		if (sysWebserviceLogService == null) {
            sysWebserviceLogService = (ISysWebserviceLogService) SpringBeanUtil.getBean("sysWebserviceLogService");
        }
		return sysWebserviceLogService;
	}

	public ISysLogSystemService getSysLogSystemService() {
		if (sysLogSystemService == null) {
            sysLogSystemService = (ISysLogSystemService) SpringBeanUtil.getBean("sysLogSystemService");
        }
		return sysLogSystemService;
	}

	public ISysAppConfigService getSysAppConfigService() {
		if (sysAppConfigService == null) {
			sysAppConfigService = (ISysAppConfigService) SpringBeanUtil
					.getBean("sysAppConfigService");
		}
		return sysAppConfigService;
	}



	private static final Logger logger = LoggerFactory.getLogger(CallBeforeInterceptor.class);

	public CallBeforeInterceptor() {
		super(Phase.PRE_INVOKE);
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		// org.apache.cxf.binding.soap.SoapMessage m =
		// (org.apache.cxf.binding.soap.SoapMessage) message;

		// SysWsUtil.getTextContent((SOAPHeader) (m.getHeaders().get(0)),
		// "tns:user");
		SOAPMessage soapMessage = message.getContent(SOAPMessage.class);
		if (soapMessage == null) {
			new SAAJInInterceptor().handleMessage(message);
			soapMessage = message.getContent(SOAPMessage.class);
		}
		try {
			SOAPHeader head = soapMessage.getSOAPHeader();
			SOAPBody body = soapMessage.getSOAPBody();
			SysWebserviceMain sysWsMain = findWebservice(message);
			String fdId = check(message, head, body, sysWsMain);
			SysWebserviceLogThreadLocal.setId(fdId);
			addSoapHeader(head, fdId);
		} catch (Exception e) {
			throw new Fault(e);
		}
	}

	@Override
	public void handleFault(SoapMessage message) {
		getAfter().clear();
	}

	/**
	 * 检查客户端的访问是否合法
	 */
	private String check(SoapMessage message, SOAPHeader head, SOAPBody body,
			SysWebserviceMain sysWsMain) throws Exception {

		logger.debug("=================检查客户端的访问是否合法==================");
		HttpServletRequest request = (HttpServletRequest) message
				.get(AbstractHTTPDestination.HTTP_REQUEST);
		String clientIp = request.getRemoteAddr();// 获取客户端IP地址
		logger.debug("请求ip:" + clientIp);
		// 获取调用的方法
		String fdServiceMethod = getServiceMethod(message);
		logger.debug("请求调用的方法:" + fdServiceMethod);
		// 判断后台配置是否为记录详细数据
		String fdRequestMsg = null;
		Map map = getSysAppConfigService().findByKey(
				"com.landray.kmss.sys.webservice2.model.SysWebserviceLogConfig");
		if ("1".equals(map.get("dataType"))) {
			// 获取请求的报文
			fdRequestMsg = getRequestMsg(message);
			logger.debug("请求报文:" + fdRequestMsg);
		}
		// 匿名策略优先
		boolean allowAccess = false;
		SysWebserviceUser usedPolicy = new SysWebserviceUser();
		boolean hasUser = false;
		if (sysWsMain.getFdAnonymous() == true) { // 兼容历史数据
			allowAccess = true;
		} else {
			if (sysWsMain.getFdUser().size() > 0) {
				if (sysWsMain.getFdUser().size() > 1) {
					// 遍历看是否有账号认证的策略
					for (SysWebserviceUser policy : sysWsMain.getFdUser()) {
						if (SysWebserviceUser.POLICY_MODEL_USER.equals(policy
								.getFdPolicy())) {
							hasUser = true;
							break;
						}
					}
				} else {
					logger.debug(sysWsMain.getFdName() + "访问策略只有一个！");
				}
				for (SysWebserviceUser policy : sysWsMain.getFdUser()) {
					if (SysWebserviceUser.POLICY_MODEL_ANONYMOUS.equals(policy
							.getFdPolicy())) {
						boolean userWay = checkClientIpByNiMing(clientIp,
								policy, sysWsMain,
								fdServiceMethod,
								fdRequestMsg, hasUser);// 检查下ip
														// ,校验不通过且还有用户方式校验则返回
														// true
						if (!userWay) {
							allowAccess = true;
						}

						break;
					}
				}

			} else {
				logger.warn(sysWsMain.getFdName() + " 没有配访问策略！！！禁止访问");
			}

		}

		// 尝试获取用户登录名
		if (!allowAccess) {
			String loginId = null;
			List<Header> headers = message.getHeaders();
			if (headers != null && headers.size() > 0) {
				String namespaceURI = headers.get(0).getName() == null ? null
						: headers.get(0).getName().getNamespaceURI();
				if (headers.size() == 1) {
					Header requestSOAPHeader = message
							.getHeader(
									new QName(namespaceURI,
											"RequestSOAPHeader"));
					if (requestSOAPHeader != null
							&& requestSOAPHeader.getObject() != null) {
						NodeList list = ((ElementNSImpl) (requestSOAPHeader
								.getObject())).getChildNodes();
						for (int i = 0; i < list.getLength(); i++) {
							String nodeName = list.item(i).getLocalName();
							if ("user".equals(nodeName)) {
								loginId = list.item(i).getTextContent();
								break;
							}
						}
					} else {
						logger.debug("requestSOAPHeader为空");
					}
				} else {
					Header userHeader = message
							.getHeader(
									new QName(namespaceURI, "user"));
					if (userHeader != null && userHeader.getObject() != null) {
						loginId = ((ElementNSImpl) (userHeader
								.getObject())).getTextContent();
					} else {
						logger.debug("userHeader为空");
					}
				}
			} else {
				logger.debug("headers为空");
			}

			// String loginId = SysWsUtil.getTextContent(head, "tns:user");
			if (StringUtil.isNotNull(loginId)) {// ekp尝试认证读取到用户名
				for (SysWebserviceUser policy : sysWsMain.getFdUser()) {
					if (SysWebserviceUser.POLICY_MODEL_USER.equals(policy
							.getFdPolicy())
							&& checkEkpUser(clientIp, head, policy, message)) {

							usedPolicy = policy;
							allowAccess = true;
							SysWebserviceLogThreadLocal
									.setUsername(usedPolicy.getFdLoginId());
							break;

					}

				}
			}
			if (!allowAccess) {// WSS认证尝试
				try {
					ServerPasswordCallback callBack = new ServerPasswordCallback(
							sysWsMain);
					Map<String, Object> properties = new HashMap<String, Object>();
					properties.put(WSHandlerConstants.ACTION,
							WSHandlerConstants.USERNAME_TOKEN);
					properties.put(WSHandlerConstants.PASSWORD_TYPE,
							WSConstants.PW_TEXT);
					properties
							.put(WSHandlerConstants.PW_CALLBACK_REF, callBack);
					new WSS4JInInterceptor(properties).handleMessage(message);
					usedPolicy = callBack.currPolicy();
					SysWebserviceLogThreadLocal
							.setUsername(usedPolicy.getFdLoginId());
				} catch (SoapFault e) {
					logger.warn("尝试WSS认证时失败", e);
				}
					}
			checkUser(clientIp, getUserName(usedPolicy), sysWsMain,
					fdServiceMethod, fdRequestMsg);
			checkAccessFrequency(clientIp, getUserName(usedPolicy), sysWsMain,
					fdServiceMethod, fdRequestMsg);

				}
		checkClientIp(clientIp, usedPolicy, sysWsMain, fdServiceMethod,
				fdRequestMsg);
		checkBodySize(clientIp, getUserName(usedPolicy), body, sysWsMain,
				fdServiceMethod, fdRequestMsg);
		String fdId = saveLog(clientIp, sysWsMain.getFdName(), sysWsMain
				.getFdServiceBean(), getUserName(usedPolicy), null,
				fdServiceMethod, fdRequestMsg);
		return fdId;
	}


	/**
	 * 返回一半密码，且另一半用*代替，如果长度为1，返回*
	 * 
	 * @param message
	 * @return
	 */
	private String printPsw(String psw) {
		if (StringUtil.isNull(psw)) {
            return null;
        }
		if (psw.length() == 1) {
			return "*";
		}
		String pswPrefix = psw.substring(0, psw.length() / 2);
		String xing = "";
		for (int i = psw.length() / 2; i < psw.length(); i++) {
			xing += "*";
		}
		return pswPrefix + xing;
	}

	/**
	 * 获取请求的报文
	 * 
	 * @param message
	 * @return
	 */
	private String getRequestMsg(SoapMessage message) {
		if (message == null) {
			return null;
		}
		String requestMsg = null;
		InputStream is = (InputStream) message.getContent(InputStream.class);
		if (is != null) {
			requestMsg = is.toString();
		}
		return requestMsg;
	}

	/**
	 * 获取调用的方法名
	 * 
	 * @param message
	 * @return
	 */
	private String getServiceMethod(SoapMessage message) {
		if (message == null) {
			return null;
		}
		String serviceMethod = null;
		Exchange exchange = message.getExchange();
		BindingOperationInfo bop = exchange.get(BindingOperationInfo.class);
		MethodDispatcher md = (MethodDispatcher) exchange.get(Service.class)
				.get(MethodDispatcher.class.getName());
		Method method = md.getMethod(bop);
		if (method != null) {
			serviceMethod = method.getName();
		}
		return serviceMethod;
	}

	/**
	 * 用户名获取兼容处理
	 * 
	 * @param policy
	 * @return
	 */
	private String getUserName(SysWebserviceUser policy) {
		if (StringUtil.isNull(policy.getFdUserName())) {
			return policy.getFdLoginId();
		}
		return policy.getFdUserName();
	}

	/**
	 * 校验用户IP和账号，ekp认证
	 */
	private boolean checkEkpUser(String clientIp, SOAPHeader head,
			SysWebserviceUser user, SoapMessage message) {
		// String loginId = SysWsUtil.getTextContent(head, "tns:user");
		// String password = SysWsUtil.getTextContent(head, "tns:password");

		if (user == null) {
			return false;
		}

		String loginId = null;
		String password = null;
		List<Header> headers = message.getHeaders();
		if (headers != null && headers.size() > 0) {
			String namespaceURI = headers.get(0).getName() == null ? null
					: headers.get(0).getName().getNamespaceURI();
			if (headers.size() == 1) {
				Header requestSOAPHeader = message
						.getHeader(
								new QName(namespaceURI,
										"RequestSOAPHeader"));
				if (requestSOAPHeader != null
						&& requestSOAPHeader.getObject() != null) {
					NodeList list = ((ElementNSImpl) (requestSOAPHeader
							.getObject())).getChildNodes();
					for (int i = 0; i < list.getLength(); i++) {
						String nodeName = list.item(i).getLocalName();
						if ("user".equals(nodeName)) {
							loginId = list.item(i).getTextContent();
						} else if ("password".equals(nodeName)) {
							password = list.item(i).getTextContent();
						}
					}
				} else {
					logger.debug("requestSOAPHeader为空");
				}
			} else {
				Header userHeader = message
						.getHeader(
								new QName(namespaceURI, "user"));
				if (userHeader != null && userHeader.getObject() != null) {
					loginId = ((ElementNSImpl) (userHeader
							.getObject())).getTextContent();
				} else {
					logger.debug("userHeader为空");
				}
				Header passHeader = message
						.getHeader(
								new QName(namespaceURI, "password"));
				if (passHeader != null && passHeader.getObject() != null) {
					password = ((ElementNSImpl) (passHeader
							.getObject())).getTextContent();
				} else {
					logger.debug("passHeader为空");
				}
			}
		} else {
			logger.debug("headers为空");
		}

		logger.debug("账号：" + loginId + " 密码：" + printPsw(password)
				+ "  user.getFdPassword():" + printPsw(user.getFdPassword()));
		if (StringUtil.isNotNull(user.getFdLoginId())
				&& user.getFdLoginId().equals(loginId)
				&& StringUtil.isNotNull(user.getFdPassword())
				&& user.getFdPassword().equals(password)
				&& (SysWebserviceUser.AUTH_TYPE_EKP
						.equals(user.getFdAuthType()) || StringUtil.isNull(user
						.getFdAuthType()))) {
			return true;
		}
		return false;
	}

	/**
	 * 检查客户端的用户帐号信息
	 */
	private void checkUser(String clientIp, String userName,
			SysWebserviceMain sysWsMain, String fdServiceMethod,
			String fdRequestMsg) throws Exception {

		if (StringUtil.isNull(userName)) {
			saveLog(clientIp, sysWsMain.getFdName(), sysWsMain
					.getFdServiceBean(), userName,
					SysWsConstant.UNAUTHORIZED_USER, fdServiceMethod,
					fdRequestMsg);
			throw new SysWsException("sysWs.errMsg.unauthorized.user");
		}
	}

	/**
	 * 验证客户端IP地址
	 */
	private void checkClientIp(String clientIp, SysWebserviceUser user,
			SysWebserviceMain sysWsMain, String fdServiceMethod,
			String fdRequestMsg) throws Exception {
		if (!SysWsUtil.isExistedIp(clientIp, user.getFdAccessIp())) {
			saveLog(clientIp, sysWsMain.getFdName(), sysWsMain
					.getFdServiceBean(), getUserName(user),
					SysWsConstant.ILLEGAL_IP, fdServiceMethod,
					fdRequestMsg);
			throw new SysWsException("sysWs.errMsg.illegal.ip");
		}
	}

	/**
	 * 匿名访问验证客户端IP地址 return true 表示ip校验不通过，采用账号验证
	 */
	private boolean checkClientIpByNiMing(String clientIp,
			SysWebserviceUser user,
			SysWebserviceMain sysWsMain, String fdServiceMethod,
			String fdRequestMsg, boolean hasUser) throws Exception {

		boolean isExistedIp = SysWsUtil.isExistedIp(clientIp,
				user.getFdAccessIp());
		if (!isExistedIp && !hasUser) {
			saveLog(clientIp, sysWsMain.getFdName(), sysWsMain
					.getFdServiceBean(), getUserName(user),
					SysWsConstant.ILLEGAL_IP, fdServiceMethod,
					fdRequestMsg);
			throw new SysWsException("sysWs.errMsg.illegal.ip");
		} else if (!isExistedIp && hasUser) {
			logger.warn(
					"匿名访问校验ip不通过!但访问策略中存在账号访问，下面将通过账号方式尝试访问！ IP:" + clientIp);
			return true;
		}
		return false;
	}

	/**
	 * 检测客户端的访问频率
	 */
	private void checkAccessFrequency(String clientIp, String userName,
			SysWebserviceMain sysWsMain, String fdServiceMethod,
			String fdRequestMsg) throws Exception {
		Integer maxConn = sysWsMain.getFdMaxConn();
		if (maxConn != null) {
			ISysWebserviceLogService sysWsLogService = (ISysWebserviceLogService) SpringBeanUtil
					.getBean("sysWebserviceLogService");
			int count = sysWsLogService.countAccessFrequency(sysWsMain
					.getFdServiceBean(), userName);

			if (count >= maxConn) {
				saveLog(clientIp, sysWsMain.getFdName(), sysWsMain
						.getFdServiceBean(), userName,
						SysWsConstant.LOCKED_USER, fdServiceMethod,
						fdRequestMsg);
				throw new SysWsException("sysWs.errMsg.locked.user");
			}
		}
	}

	/**
	 * 按服务名称查找Webservice的注册信息
	 */
	private SysWebserviceMain findWebservice(SoapMessage message)
			throws Exception {
		// 获取服务标识
		HttpServletRequest request = (HttpServletRequest) message
				.get(AbstractHTTPDestination.HTTP_REQUEST);
		String serviceBean = SysWsUtil.getServiceBeanFromUrl(request
				.getRequestURI());

		ISysWebserviceMainService sysWsMainService = (ISysWebserviceMainService) SpringBeanUtil
				.getBean("sysWebserviceMainService");
		SysWebserviceMain sysWsMain = sysWsMainService
				.findByServiceBean(serviceBean);
		if (sysWsMain == null) {
			throw new SysWsException("sysWs.errMsg.no.service", serviceBean);
		}

		return sysWsMain;
	}

	/**
	 * 记录运行日志
	 */
	private String saveLog(String clientIp, String fdServiceName,
			String fdServiceBean, String fdUserName, String fdExecResult,
			String fdServiceMethod, String fdRequestMsg)
			throws Exception {
		return saveLog(clientIp, fdServiceName,
				fdServiceBean, fdUserName, fdExecResult,
				null, fdServiceMethod, fdRequestMsg);
	}

	/**
	 * 记录运行日志
	 */
	private String saveLog(String clientIp, String fdServiceName,
			String fdServiceBean, String fdUserName, String fdExecResult,
			String fdErrorMsg, String fdServiceMethod, String fdRequestMsg)
			throws Exception {
		SysWebserviceLog sysWsLog = new SysWebserviceLog();
		sysWsLog.setFdStartTime(new Date());

		// 记录elastic日志
		if (getSysLogSystemService() != null
				&& "true".equals(ResourceUtil.getKmssConfigString("log.openLogService"))
				&& "LocalFile".equals(ResourceUtil.getKmssConfigString("kmss.oper.log.store.pattern"))) {
			if (logger.isDebugEnabled()) {
				logger.debug("记录运行日志到ES系统，以下为测试信息：");
				logger.debug("kmss.oper.log.store.pattern：" + ResourceUtil.getKmssConfigString("kmss.oper.log.store.pattern"));
				logger.debug("log.openLogService：" + ResourceUtil.getKmssConfigString("log.openLogService"));
				logger.debug("log.elastic.urls：" + ResourceUtil.getKmssConfigString("log.elastic.urls"));
			}
			SysLogSystemForm dto = new SysLogSystemForm();
			dto.setFdId(sysWsLog.getFdId());
			dto.setFdStartTime(sysWsLog.getFdStartTime());
			dto.setFdServiceBean(fdServiceBean);
			dto.setFdSubject(fdServiceName);
			dto.setFdClientIp(clientIp);
			dto.setFdUserName(fdUserName);
			if (StringUtil.isNotNull(fdExecResult)) {
				dto.setFdSuccess(StringUtil.getIntFromString(fdExecResult, -1));
			}
			if (StringUtil.isNotNull(fdErrorMsg)) {
				dto.setFdDesc(fdErrorMsg);
			}
			dto.setFdServiceMethod(fdServiceMethod);
			if (StringUtil.isNotNull(fdRequestMsg)) {
				dto.setFdRequestMsg(fdRequestMsg);
			}
			dto.setFdType(LogSystemType.WEBSERVICE.getVal());
			getSysLogSystemService().add(dto);
		}
		// 记录数据库日志
		sysWsLog.setFdServiceName(fdServiceName);
		sysWsLog.setFdServiceBean(fdServiceBean);
		sysWsLog.setFdUserName(fdUserName);
		sysWsLog.setFdClientIp(clientIp);
		if (StringUtil.isNotNull(fdExecResult)) {
			sysWsLog.setFdExecResult(fdExecResult);
		}
		if (StringUtil.isNotNull(fdErrorMsg)) {
			sysWsLog.setFdErrorMsg(fdErrorMsg);
		}
		sysWsLog.setFdServiceMethod(fdServiceMethod);
		if (StringUtil.isNotNull(fdRequestMsg)) {
			sysWsLog.setFdRequestMsg(fdRequestMsg);
		}
		return getSysWebserviceLogService().add(sysWsLog);
	}

	/**
	 * 将服务ID添加到消息头
	 */
	private void addSoapHeader(SOAPHeader head, String instanceId) {
		Document doc = head.getOwnerDocument();
		Element element = doc.createElementNS(
				"http://webservice2.sys.kmss.landray.com", "tns:instanceId");
		element.appendChild(doc.createTextNode(instanceId));
		head.appendChild(element);
	}

	private class ServerPasswordCallback implements CallbackHandler {
		SysWebserviceMain sysWsMain = null;

		public ServerPasswordCallback() {
			super();
		}

		private SysWebserviceUser usedPolicy = null;

		public ServerPasswordCallback(SysWebserviceMain sysWsMain) {
			super();
			this.sysWsMain = sysWsMain;
		}

		@Override
		public void handle(Callback[] callbacks) throws IOException,
				UnsupportedCallbackException {
			if (callbacks.length > 0) {
				WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
				String userName = pc.getIdentifier();
				if (StringUtil.isNotNull(userName) && this.sysWsMain != null) {
					for (SysWebserviceUser policy : sysWsMain.getFdUser()) {
						if (policy.getFdLoginId().equals(userName)
								&& SysWebserviceUser.AUTH_TYPE_WSS
										.equals(policy.getFdAuthType())) {
							usedPolicy = policy;
							pc.setPassword(policy.getFdPassword());
							break;
						}
					}
				}
			}
		}

		public SysWebserviceUser currPolicy() {
			return usedPolicy;
		}
	}
	
	/**
	 * 检测客户端的访问频率
	 */
	private void checkBodySize(String clientIp, String userName, SOAPBody body,
			SysWebserviceMain sysWsMain, String fdServiceMethod,
			String fdRequestMsg) throws Exception {
		Long maxBodySize = sysWsMain.getFdMaxBodySize();
		if (maxBodySize != null && body != null) {
			long length = body.toString().length();
			if (length > maxBodySize) {
				String errorMsg = "消息体长度：" + length + ", 限制长度："
						+ maxBodySize;
				saveLog(clientIp, sysWsMain.getFdName(), sysWsMain
						.getFdServiceBean(), userName,
						SysWsConstant.BODY_SIZE_EXPIRE, errorMsg,
						fdServiceMethod, fdRequestMsg);
				throw new SysWsException("sysWs.errMsg.body.expire");
			}
		}
	}


}
