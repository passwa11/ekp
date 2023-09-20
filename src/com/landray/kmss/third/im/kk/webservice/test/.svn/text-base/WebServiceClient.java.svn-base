package com.landray.kmss.third.im.kk.webservice.test;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.landray.kmss.third.im.kk.webservice.IThirdImSyncForKKWebService;

import net.sf.json.JSONObject;

/**
 * WebService客户端
 * 
 */
public class WebServiceClient {

	/**
	 * 主方法
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		WebServiceConfig cfg = WebServiceConfig.getInstance();

		Object service = callService(cfg.getAddress(), cfg.getServiceClass());
		// 请在此处添加业务代码
		IThirdImSyncForKKWebService webservice = (IThirdImSyncForKKWebService) service;

		/*		NotifyTodoGetContext context = new NotifyTodoGetContext();
				JSONObject targets = new JSONObject();
				targets.put("LoginName", "ceshi");
				context.setTargets(targets.toString());*/
		JSONObject json = JSONObject.fromObject(webservice.getExtendApp());
		System.out.println(json);
		System.out.println(json.get("message"));
		System.out.println(
				(JSONObject.fromObject(json.get("message"))).get("com.landray.kmss.sys.attend.model.SysAttendConfig"));
	}
	
	/**
	 * 调用服务，生成客户端的服务代理
	 * 
	 * @param address WebService的URL
	 * @param serviceClass 服务接口全名
	 * @return 服务代理对象
	 * @throws Exception
	 */
	public static Object callService(String address, Class serviceClass)
			throws Exception {

		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		
		// 记录入站消息
		factory.getInInterceptors().add(new LoggingInInterceptor());
		
		// 记录出站消息
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		
		// 添加消息头验证信息。如果服务端要求验证用户密码，请加入此段代码
		factory.getOutInterceptors().add(new AddSoapHeader());

		factory.setServiceClass(serviceClass);
		factory.setAddress(address);
		
		// 使用MTOM编码处理消息。如果需要在消息中传输文档附件等二进制内容，请加入此段代码
		// Map<String, Object> props = new HashMap<String, Object>();
		// props.put("mtom-enabled", Boolean.TRUE);
		// factory.setProperties(props);		
        
        // 创建服务代理并返回
		return factory.create();
	}

}
