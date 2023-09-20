package com.landray.kmss.third.ekp.java.oms.in;

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
import com.landray.kmss.third.ekp.java.oms.in.client.ISysSynchroGetOrgWebService;
import com.landray.kmss.third.ekp.java.oms.in.client.SysSynchroGetOrgInfoContext;
import com.landray.kmss.third.ekp.java.oms.in.client.SysSynchroOrgResult;

public class EkpRoleSynchroDelegate implements IEkpRoleSynchro {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EkpRoleSynchroDelegate.class);

	private String servicePath = "/sys/webservice/sysSynchroGetOrgWebService";

	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}

	private static ISysSynchroGetOrgWebService getOrgWebService = null;

	public static void resetGetOrgWebService() {
		getOrgWebService = null;
	}

	private ISysSynchroGetOrgWebService getService() throws Exception {
		if (getOrgWebService == null) {
			logger.debug("创建webservice对象..");
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			factory.getOutInterceptors().add(new AddSoapHeader());
			factory.setServiceClass(ISysSynchroGetOrgWebService.class);
			factory.setAddress(
					new EkpJavaConfig().getValue("kmss.java.webservice.urlPrefix")
					+ servicePath);
			getOrgWebService = (ISysSynchroGetOrgWebService) factory.create();
			Client proxy = null;
			try {
				// 超时设置
				proxy = ClientProxy.getClient(getOrgWebService);
				HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
				HTTPClientPolicy policy = new HTTPClientPolicy();
				policy.setConnectionTimeout(10000);
				policy.setReceiveTimeout(600000);
				conduit.setClient(policy);
			}catch (Exception e){
				throw e;
			}finally {
				if(proxy!=null){
					//proxy.close();
				}
			}
		}
		return getOrgWebService;
	}

	@Override
	public SysSynchroOrgResult getRoleConfInfo(
			SysSynchroGetOrgInfoContext orgContext) throws Exception {
		// TODO 自动生成的方法存根
		return getService().getRoleConfInfo(orgContext);
	}

	@Override
	public SysSynchroOrgResult getRoleInfo(
			SysSynchroGetOrgInfoContext orgContext) throws Exception {
		// TODO 自动生成的方法存根
		return getService().getRoleInfo(orgContext);
	}

	@Override
	public SysSynchroOrgResult getRoleLineDefaultRoleInfo(
			SysSynchroGetOrgInfoContext orgContext) throws Exception {
		// TODO 自动生成的方法存根
		return getService().getRoleLineDefaultRoleInfo(orgContext);
	}

	@Override
	public SysSynchroOrgResult getRoleLineInfo(
			SysSynchroGetOrgInfoContext orgContext) throws Exception {
		// TODO 自动生成的方法存根
		return getService().getRoleLineInfo(orgContext);
	}

	@Override
	public SysSynchroOrgResult getRoleConfCateInfo(
			SysSynchroGetOrgInfoContext orgContext) throws Exception {
		// TODO 自动生成的方法存根
		return getService().getRoleConfCateInfo(orgContext);
	}

	@Override
	public SysSynchroOrgResult getRoleConfMemberInfo(
			SysSynchroGetOrgInfoContext orgContext) throws Exception {
		// TODO 自动生成的方法存根
		return getService().getRoleConfMemberInfo(orgContext);
	}
}
