package com.landray.kmss.sys.restservice.client;

import org.slf4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.landray.kmss.sys.restservice.client.cloud.EkpCloudConstants;
import com.landray.kmss.sys.restservice.client.cloud.IEkpCloudClient;
import com.landray.kmss.sys.restservice.client.model.CommonAuthInfo;
import com.landray.kmss.sys.restservice.client.model.RestRequestConfig;
import com.landray.kmss.sys.restservice.client.model.RestRequestConfigImpl;
import com.landray.kmss.sys.restservice.client.util.ConfigUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.filter.security.CryptoUtil;

/**
 * ekp-cloud的工厂bean，spring容器启动时加载配置，生成客户端对象
 *
 * @author 陈进科
 * 2019-02-25
 */
public class DefaultClientFactoryBean implements EkpCloudConstants, FactoryBean<IEkpCloudClient> {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DefaultClientFactoryBean.class);

	private int retryCount = 1;

	private IRestClientBuilder restClientBuilder;

	public void setRestClientBuilder(IRestClientBuilder restClientBuilder) {
		this.restClientBuilder = restClientBuilder;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	@Override
	public final IEkpCloudClient getObject() throws Exception {
		IEkpCloudClient cloudClient = null;
		try {
			Object bean = SpringBeanUtil.getBean("&mkCloudClient");
			if (null != bean) {
				@SuppressWarnings("unchecked")
				FactoryBean<IEkpCloudClient> cloudClientFactoryBean = (FactoryBean<IEkpCloudClient>) bean;
				if (null != cloudClientFactoryBean) {
					cloudClient = cloudClientFactoryBean.getObject();
					logger.info("发现云端服务客户端!");
				}
			} else {
				logger.error("没发现云端服务客户端!");
			}

		} catch (Exception e) {
			//logger.error("没发现云端服务客户端!", e);
			logger.error("没发现云端服务客户端!");
		}
		return cloudClient;
	}

	/**
	 * 获取Rest操作类，默认从{@code IRestClientBuilder}得到
	 * 目前支持Basic以及定制头信息
	 * @return
	 * @throws Exception 
	 */
	protected RestOperations getRestOperations() throws Exception {
		RestRequestConfig config = new RestRequestConfigImpl();
		config.setRetry(retryCount);
		config.setSiteUri(getSiteUri());
		String authType = ConfigUtil.getKmssConfigString(CLOUD_AUTH_TYPE_KEY, DEFAULT_AUTH_TYPE);
		if (CommonAuthInfo.AUTH_TYPE_BASIC.equalsIgnoreCase(authType)) {
			CommonAuthInfo authInfo = new CommonAuthInfo();
			authInfo.setName(ConfigUtil.getKmssConfigString(CLOUD_AUTH_USER_KEY));
			authInfo.setPassword(ConfigUtil.getKmssConfigString(CLOUD_AUTH_PW_KEY));
			authInfo.setType(CommonAuthInfo.AUTH_TYPE_BASIC);
			config.setAuthInfo(authInfo);
		} else if ("header".equalsIgnoreCase(authType)) {
			String[] headerAuthInfo = getHeaderAuthInfo();
			config.setDefaultHeader(headerAuthInfo[0], headerAuthInfo[1]);
		}

		IRestClient restClient = restClientBuilder.buildRestClient(config);
		RestTemplate restOperations = (RestTemplate) restClient.getRestOperations();
		restOperations.setErrorHandler(new DefaultResponseErrorHandler());
		return restOperations;
	}

	/**
	 * eureka支持多个注册中心地址，但是EkpCloudClient只需要其中一个，作为客户端的标识即可
	 * @return
	 */
	private String getSiteUri() {
		String host = ConfigUtil.getKmssConfigString(CLOUD_SITE_KEY);
		String hostToUse = host.split("\\s*[,;]\\s*")[0];
		if (logger.isInfoEnabled()) {
			logger.info("Found discovery-center site config: " + host + ", " + hostToUse + " is chosen.");
		}
		return hostToUse;
	}

	/** 
	 * 从 com.landray.kmss.third.mk.util.HeaderAuthUtil.getHeaderAuthInfo() 拷贝来的，因为模块依赖关系
	 */
	private static String[] getHeaderAuthInfo() throws Exception {
		String headerName = ConfigUtil.getKmssConfigString(CLOUD_SECURITY_HEADER_KEY, DEFAULT_SECURITY_HEADER);
		String key = ConfigUtil.getKmssConfigString(CLOUD_SECURITY_KEY);
		String appName = ConfigUtil.getKmssConfigString(CLOUD_EXPOSE_APPNAME_KEY, DEFAULT_EKP_APPNAME);
		String s = key + CLOUD_DEFAULT_KEY_TAIL;
		String realKey = s.substring(0, 16);
		String iv = s.substring(16, 32);
		String aesEncrypt = CryptoUtil.aesEncrypt(realKey, iv, appName);
		aesEncrypt = new String(Hex.encode(aesEncrypt.getBytes()));
		return new String[] { headerName, aesEncrypt };
	}

	@Override
	public final Class<?> getObjectType() {
		return IEkpCloudClient.class;
	}

	@Override
	public final boolean isSingleton() {
		return true;
	}
}
