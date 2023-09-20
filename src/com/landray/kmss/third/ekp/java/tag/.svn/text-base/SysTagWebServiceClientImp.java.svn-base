package com.landray.kmss.third.ekp.java.tag;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.landray.kmss.third.ekp.java.AddSoapHeader;
import com.landray.kmss.third.ekp.java.EkpJavaConfig;
import com.landray.kmss.third.ekp.java.tag.client.ISysTagWebService;
import com.landray.kmss.third.ekp.java.tag.client.TagAddContext;
import com.landray.kmss.third.ekp.java.tag.client.TagGetResult;
import com.landray.kmss.third.ekp.java.tag.client.TagGetTagsContext;
import com.landray.kmss.third.ekp.java.tag.client.TagResult;

public class SysTagWebServiceClientImp implements ISysTagWebServiceClient {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysTagWebServiceClientImp.class);

	private String servicePath = "/sys/webservice/sysTagWebService";

	private ISysTagWebService sysTagWebService;

	private ISysTagWebService getService() throws Exception {

		if (sysTagWebService == null) {

			logger.debug("创建标签webservice对象..");

			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			// factory.getInInterceptors().add(new ArtifactInInterceptor());
			// factory.getOutInterceptors().add(new ArtifactOutInterceptor());
			factory.getOutInterceptors().add(new AddSoapHeader());
			factory.setServiceClass(ISysTagWebService.class);
			factory.setAddress(new EkpJavaConfig()
					.getValue("kmss.java.webservice.urlPrefix") + servicePath);

			sysTagWebService = (ISysTagWebService) factory.create();

		}

		return sysTagWebService;
	}

	@Override
    public TagGetResult getCategories(String type) throws Exception {
		return getService().getCategories(type);
	}

	@Override
    public TagGetResult getTags(TagGetTagsContext context) throws Exception {
		return getService().getTags(context);
	}

	@Override
    public TagResult addTags(TagAddContext context) throws Exception {
		return getService().addTags(context);
	}

	@Override
	public TagResult getGroups(String modelName) throws Exception {
		return getService().getGroups(modelName);
	}

	@Override
	public TagResult getIsSpecialByTags(List<String> tags) throws Exception {
		return getService().getIsSpecialByTags(tags);
	}
}
