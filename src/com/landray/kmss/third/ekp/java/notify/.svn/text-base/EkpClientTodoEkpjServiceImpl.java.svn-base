package com.landray.kmss.third.ekp.java.notify;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

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
import com.landray.kmss.third.ekp.java.notify.client.v2.ISysNotifyTodoWebServiceEkpj;
import com.landray.kmss.third.ekp.java.notify.client.v2.NotifyTodoAppResult;
import com.landray.kmss.third.ekp.java.notify.client.v2.NotifyTodoClearContext;
import com.landray.kmss.third.ekp.java.notify.client.v2.NotifyTodoGetAllContext;
import com.landray.kmss.third.ekp.java.notify.client.v2.NotifyTodoSendContext;
import com.landray.kmss.third.ekp.java.notify.client.v2.NotifyTodoUpdateContext;
import com.landray.kmss.third.ekp.java.notify.interfaces.EkpNotifyJavaTodoConstant;
import com.landray.kmss.util.breaker.CircuitBreaker;
import com.landray.kmss.util.breaker.CircuitBreakerConfig;
import com.landray.kmss.util.breaker.OpenCircuitException;
import com.landray.kmss.util.breaker.ProtectedAction;


public class EkpClientTodoEkpjServiceImpl
		implements IEkpClientTodoEkpjService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EkpClientTodoEkpjServiceImpl.class);

	private String servicePath = "/sys/webservice/sysNotifyTodoWebServiceEkpj";

	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}

	private ISysNotifyTodoWebServiceEkpj notifyTodoWebService = null;



	private ISysNotifyTodoWebServiceEkpj getService() throws Exception {
		if (notifyTodoWebService == null) {
			logger.debug("创建待办webservice对象..");
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			factory.getOutInterceptors().add(new AddSoapHeader());
			factory.setServiceClass(ISysNotifyTodoWebServiceEkpj.class);
			factory.setAddress(new EkpJavaConfig().getValue("kmss.java.webservice.urlPrefix")
					+ servicePath);
			notifyTodoWebService = (ISysNotifyTodoWebServiceEkpj) factory
					.create();
			Client proxy = null;
			try {
				// 超时设置
				proxy = ClientProxy.getClient(notifyTodoWebService);
				HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
				HTTPClientPolicy policy = new HTTPClientPolicy();
				policy.setConnectionTimeout(10000);
				policy.setReceiveTimeout(600000);
			}catch (Exception e){
				throw e;
			}finally {
				if(proxy!=null){
					//proxy.close();
				}
			}
		}
		return notifyTodoWebService;
	}



	private NotifyTodoAppResult add(NotifyTodoSendContext todoContext)
			throws Exception {
		return getService().add(todoContext);
	}

	private NotifyTodoAppResult remove(NotifyTodoClearContext todoContext)
			throws Exception {
		return getService().remove(todoContext);
	}

	private NotifyTodoAppResult clearTodoPersons(
			NotifyTodoClearContext todoContext) throws Exception {
		return getService().clearTodoPersons(todoContext);
	}

	private NotifyTodoAppResult setPersonsDone(
			NotifyTodoClearContext todoContext) throws Exception {
		return getService().setPersonsDone(todoContext);
	}

	private NotifyTodoAppResult setTodoDone(NotifyTodoClearContext todoContext)
			throws Exception {
		return getService().setTodoDone(todoContext);
	}

	private NotifyTodoAppResult removeDonePerson(
			NotifyTodoClearContext todoContext) throws Exception {
		return getService().removeDonePerson(todoContext);
	}

	private NotifyTodoAppResult updateTodo(NotifyTodoUpdateContext todoContext)
			throws Exception {
		return getService().updateTodo(todoContext);
	}

	private NotifyTodoAppResult getAllTodo(NotifyTodoGetAllContext todoContext)
			throws Exception {
		return getService().getAllTodo(todoContext);
	}

	private NotifyTodoAppResult
			getAllTodoId(NotifyTodoGetAllContext todoContext)
					throws Exception {
		return getService().getAllTodoId(todoContext);
	}

	private static CircuitBreaker breaker = null;

	public static CircuitBreaker getCircuitBreaker() {
		if (breaker == null) {
			CircuitBreakerConfig config = new CircuitBreakerConfig();
			// config.openTimeout = 600000l;
			// config.failureCount2Open = 2;
			breaker = new CircuitBreaker("EkpjBreakerV2", config);
		}
		return breaker;
	}

	public Object send(Integer method, Object context) throws Exception {
		switch (method) {
		case EkpNotifyJavaTodoConstant.METHOD_ADD:
			return add((NotifyTodoSendContext) context);
		case EkpNotifyJavaTodoConstant.METHOD_REMOVE:
			return remove((NotifyTodoClearContext) context);
		case EkpNotifyJavaTodoConstant.METHOD_CLEARTODOPERSONS:
			return clearTodoPersons((NotifyTodoClearContext) context);
		case EkpNotifyJavaTodoConstant.METHOD_SETPERSONSDONE:
			return setPersonsDone((NotifyTodoClearContext) context);
		case EkpNotifyJavaTodoConstant.METHOD_SETTODODONE:
			return setTodoDone((NotifyTodoClearContext) context);
		case EkpNotifyJavaTodoConstant.METHOD_REMOVEDONEPERSON:
			return removeDonePerson((NotifyTodoClearContext) context);
		case EkpNotifyJavaTodoConstant.METHOD_UPDATETODO:
			return updateTodo((NotifyTodoUpdateContext) context);
		case EkpNotifyJavaTodoConstant.METHOD_GETALLTODO:
			return getAllTodo((NotifyTodoGetAllContext) context);
		case EkpNotifyJavaTodoConstant.METHOD_GETALLTODOID:
			return getAllTodoId((NotifyTodoGetAllContext) context);
		default:
			throw new Exception("找不到对应的方法，" + method);
		}
	}

	@Override
    public Object sendByBreaker(final Integer method, final Object context)
			throws Exception {
		try {
			Object result = getCircuitBreaker().execute(new ProtectedAction() {
				@Override
				public Object execute() throws Exception {
					return send(method, context);
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
									|| t instanceof UnknownHostException
									|| e instanceof NoRouteToHostException)) {
						return true;
					}
					return false;
				}
			});
			return result;
		} catch (OpenCircuitException e) {
			logger.error("熔断器处于打开状态", e);
			throw e;
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
	}

}
