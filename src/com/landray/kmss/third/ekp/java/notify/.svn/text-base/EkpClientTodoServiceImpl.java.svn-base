package com.landray.kmss.third.ekp.java.notify;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.landray.kmss.third.ekp.java.notify.client.NotifyTodoGetContext;
import com.landray.kmss.third.ekp.java.notify.client.NotifyTodoRemoveContext;
import com.landray.kmss.third.ekp.java.notify.client.NotifyTodoSendContext;
import com.landray.kmss.third.ekp.java.oms.in.client.ISysSynchroGetOrgWebService;

public class EkpClientTodoServiceImpl
		implements IEkpClientTodoService {

	private static final Log logger = LogFactory
			.getLog(EkpClientTodoServiceImpl.class);

	private String servicePath = "/sys/webservice/sysNotifyTodoWebService";

	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}

	private ISysNotifyTodoWebService notifyTodoWebService = null;

	@Override
    public NotifyTodoAppResult deleteTodo(NotifyTodoRemoveContext removeContext)
			throws Exception {
		return getService().deleteTodo(removeContext);
	}

	@Override
    public NotifyTodoAppResult getTodo(NotifyTodoGetContext getContext)
			throws Exception {
		return getService().getTodo(getContext);
	}

	@Override
    public NotifyTodoAppResult sendTodo(NotifyTodoSendContext sendContext)
			throws Exception {
		return getService().sendTodo(sendContext);
	}

	@Override
    public NotifyTodoAppResult setTodoDone(NotifyTodoRemoveContext removeContext)
			throws Exception {
		return getService().setTodoDone(removeContext);
	}

	private ISysNotifyTodoWebService getService() throws Exception {
		if (notifyTodoWebService == null) {
			logger.debug("创建待办webservice对象..");
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			factory.getOutInterceptors().add(new AddSoapHeader());
			factory.setServiceClass(ISysSynchroGetOrgWebService.class);
			factory.setAddress(new EkpJavaConfig().getValue("kmss.java.webservice.urlPrefix")
					+ servicePath);
			notifyTodoWebService = (ISysNotifyTodoWebService) factory.create();
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



}
