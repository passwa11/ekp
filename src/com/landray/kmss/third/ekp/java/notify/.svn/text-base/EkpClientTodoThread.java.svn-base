package com.landray.kmss.third.ekp.java.notify;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import com.landray.kmss.third.ekp.java.AddSoapHeader;
import com.landray.kmss.third.ekp.java.EkpJavaConfig;
import com.landray.kmss.third.ekp.java.notify.client.ISysNotifyTodoWebService;
import com.landray.kmss.third.ekp.java.notify.client.NotifyTodoAppResult;
import com.landray.kmss.third.ekp.java.notify.client.NotifyTodoRemoveContext;
import com.landray.kmss.third.ekp.java.notify.client.NotifyTodoSendContext;
import com.landray.kmss.third.ekp.java.notify.client.NotifyTodoUpdateContext;
import com.landray.kmss.third.ekp.java.notify.queue.service.IThirdEkpNotifyQueueErrorService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.breaker.CircuitBreaker;
import com.landray.kmss.util.breaker.CircuitBreakerConfig;
import com.landray.kmss.util.breaker.OpenCircuitException;
import com.landray.kmss.util.breaker.ProtectedAction;

import net.sf.json.JSONObject;

public class EkpClientTodoThread {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EkpClientTodoThread.class);

	// 发送待办
	public final static int SENDTODO = 1;

	// 置为已办
	public final static int SETTODODONE = 2;

	// 删除待办
	public final static int DELETETODO = 3;
	// 更新代码
	public final static int UPDATETODO = 4;

	// 待办webservice
	private static ISysNotifyTodoWebService notifyTodoWebService = null;
	private IThirdEkpNotifyQueueErrorService thirdEkpNotifyQueueErrorService;

	// 待办操作上下文
	private Object todoContext;

	// 操作类型
	private int opt;

	private String notifyId;

	private static String servicePath = "/sys/webservice/sysNotifyTodoWebService";
	
	public static void resetNotifyTodoWebService() {
		notifyTodoWebService = null;
	}

	public IThirdEkpNotifyQueueErrorService
			getThirdEkpNotifyQueueErrorService() {
		if (thirdEkpNotifyQueueErrorService == null) {
			thirdEkpNotifyQueueErrorService = (IThirdEkpNotifyQueueErrorService) SpringBeanUtil
					.getBean("thirdEkpNotifyQueueErrorService");
		}
		return thirdEkpNotifyQueueErrorService;
	}

	public EkpClientTodoThread(int opt, Object todoContext, String serviceUrl,
			String notifyId) throws Exception {
		//Thread.dumpStack();
		logger.debug(opt + "---" + todoContext.getClass().getName());
		if (notifyTodoWebService == null) {
			logger.debug("创建待办webservice对象..");
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			factory.getOutInterceptors().add(new AddSoapHeader());
			factory.setServiceClass(ISysNotifyTodoWebService.class);		
			if (serviceUrl.startsWith("/")) {
				factory.setAddress(new EkpJavaConfig().getValue("kmss.java.webservice.urlPrefix")
						+ serviceUrl);
			} else {
				factory.setAddress(serviceUrl);
			}
			notifyTodoWebService = (ISysNotifyTodoWebService) factory.create();
			Client proxy = null;
			try {
				// 超时设置
				proxy = ClientProxy.getClient(notifyTodoWebService);
				HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
				HTTPClientPolicy policy = new HTTPClientPolicy();
				policy.setConnectionTimeout(5000);
				policy.setReceiveTimeout(30000);
				conduit.setClient(policy);
			}catch (Exception e){
				throw e;
			}finally {
				if(proxy!=null){
					//proxy.close();
				}
			}
		}
		this.opt = opt;
		this.todoContext = todoContext;
		this.notifyId = notifyId;
	}

	public EkpClientTodoThread(int opt, Object todoContext, String notifyId)
			throws Exception {
		this(opt, todoContext, servicePath, notifyId);
	}
	
	
	private static CircuitBreaker breaker = null;

	public static CircuitBreaker getCircuitBreaker() {
		if (breaker == null) {
			CircuitBreakerConfig config = new CircuitBreakerConfig();
			// config.openTimeout = 600000l;
			// config.failureCount2Open = 2;
			breaker = new CircuitBreaker("EkpjBreaker", config);
		}
		return breaker;
	}

	public void sendByBreaker() throws Exception {
		// String circuitBreakerEnable = new EkpJavaConfig()
		// .getValue("ekp.java.CircuitBreaker.enable");
		// if (!"true".equals(circuitBreakerEnable)) {
		// send();
		// }
		try {
			getCircuitBreaker().execute(new ProtectedAction() {
				@Override
				public Object execute() throws Exception {
					send();
					return null;
				}

				@Override
				public boolean isBreakException(Exception e) {
					if (e instanceof ConnectException
							|| e instanceof SocketTimeoutException
							|| e instanceof UnknownHostException
							|| e instanceof NoRouteToHostException) {
						return true;
					}
					Throwable t = e.getCause();
					if (t != null
							&& (t instanceof ConnectException
									|| t instanceof SocketTimeoutException
									|| t instanceof UnknownHostException || e instanceof NoRouteToHostException)) {
						return true;
					}
					return false;
				}
			});
		} catch (OpenCircuitException e) {
			logger.error("熔断器处于打开状态", e);
			getThirdEkpNotifyQueueErrorService().add(todoContext, opt,
					e.getMessage(), notifyId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			getThirdEkpNotifyQueueErrorService().add(todoContext, opt,
					e.getMessage(), notifyId);
		}
	}

	private void send() throws Exception {
		logger.debug("EKPJ待办服务调用开始("
				+ DateUtil.convertDateToString(new Date(), null) + ")..");
		NotifyTodoAppResult rtnMsg = null;
		try {
			switch (opt) {
			case SENDTODO:
				rtnMsg = notifyTodoWebService
						.sendTodo((NotifyTodoSendContext) todoContext);
				break;
			case SETTODODONE:
				rtnMsg = notifyTodoWebService
						.setTodoDone((NotifyTodoRemoveContext) todoContext);
				break;
			case DELETETODO:
				rtnMsg = notifyTodoWebService
						.deleteTodo((NotifyTodoRemoveContext) todoContext);
				break;
			case UPDATETODO:
				rtnMsg = notifyTodoWebService
						.updateTodo((NotifyTodoUpdateContext) todoContext);
				break;
			default:
				break;
			}
			handleResonse(opt, rtnMsg);
		} catch (Exception e) {
			logger.error("EKPJ待办服务调用操作失败！", e);
			throw e;
		} finally {
			if (logger.isDebugEnabled()) {
			JSONObject o = null;
			switch (opt) {
				case SENDTODO:
					o = ((NotifyTodoSendContext) todoContext).toJson();
					break;
				case SETTODODONE:
					o = ((NotifyTodoRemoveContext) todoContext).toJson(opt);
					break;
				case DELETETODO:
					o = ((NotifyTodoRemoveContext) todoContext).toJson(opt);
					break;
				case UPDATETODO:
					o = ((NotifyTodoUpdateContext) todoContext).toJson();
					break;
				}
				logger.debug("EKPJ待办服务调用结束("
						+ opt + "," + o
						+ "," + DateUtil.convertDateToString(new Date(), null)
						+ ")..");
			}
		}
	}

	public void run() {
		
		try {
			sendByBreaker();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			logger.error("", e);
		}
		
	}

	private void handleResonse(int opt, NotifyTodoAppResult returnMessage) {
		if (returnMessage == null) {
			return;
		}
		if (returnMessage.getReturnState() != NotifyTodoAppResult.RETURN_CONSTANT_STATUS_SUCESS) {
			String method = "";
			switch (opt) {
			case SENDTODO:
				method = "sendTodo";
				break;
			case SETTODODONE:
				method = "setTodoDone";
				break;
			case DELETETODO:
				method = "deleteTodo";
				break;
			case UPDATETODO:
				method = "updateTodo";
				break;
			}
			String errorMsg = "调用EKPJ待办接口:'" + method + "'失败！错误代码："
					+ returnMessage.getReturnState() + ",错误消息："
					+ returnMessage.getMessage();
			logger.error(errorMsg);
		} else {
			logger.debug("调用EKPJ待办接口成功！返回状态：" + returnMessage.getReturnState()
					+ "," + returnMessage.getMessage());
		}
	}
}
