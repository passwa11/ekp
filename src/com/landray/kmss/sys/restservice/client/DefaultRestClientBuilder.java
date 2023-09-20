package com.landray.kmss.sys.restservice.client;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.transform.Source;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.HttpClientConnectionOperator;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.IdleConnectionEvictor;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.client.TargetAuthenticationStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.restservice.client.hystrix.HystrixFusableConnectionOperator;
import com.landray.kmss.sys.restservice.client.hystrix.HystrixRestTemplateBuilder;
import com.landray.kmss.sys.restservice.client.model.CommonAuthInfo;
import com.landray.kmss.sys.restservice.client.model.RestRequestConfig;
import com.landray.kmss.sys.restservice.client.util.RestClientPluginUtil;
import com.landray.kmss.web.Globals;
import com.landray.kmss.web.springmvc.convert.MappingJackson2HttpMessageConverter;
import com.landray.kmss.web.springmvc.convert.MappingJackson2XmlHttpMessageConverter;


/**
 * {@link IRestClient}工厂实现类，提供链接池的统一管理，
 * 每一个客户端实例包含了各自站点的配置，通过{@code this#getRestClient(RestRequestConfig)}获取
 * 如果没有提供配置，那么使用{@code this#getRestClient()}
 *
 * 熔断器从系统的CircuitBreaker变成引入hystrix的能力 by 苏运彬 2021/7/15
 *
 * @author 陈进科
 * @since 1.0  2019年1月4日
 *
 */
public class DefaultRestClientBuilder implements IRestClientBuilder,DisposableBean{
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DefaultRestClientBuilder.class);
	private HttpClientConnectionManager connectionManager;
	//从连接池获取连接的timeout ，毫秒
	private int connectionRequestTimeout = 10*1000;
	//连接超时时间，毫秒 
	private int connectTimeout = 10*1000;
	//读写超时时间，毫秒 
	private int socketTimeout = 10*1000;
	//每个主机的并发
	private int defaultMaxPerRoute = 10;
	//整个连接池的并发
	private int maxTotal = 200;
	//链接存活的时间，毫秒 
	private int connTimeToLive = 300*1000;
	//闲置回收的时间,秒
	private int maxIdleTime = 60;
	// 数据传输超时，毫秒
	private int soTimeout=15*1000;
	
	//值:default/hystrix
	private String restTemplateBuilder = "default";
	
	private static String REST_TEMPLATE_BUILDER_HYSTRIX = "hystrix";
	
	public String getRestTemplateBuilder() {
		return restTemplateBuilder;
	}

	public void setRestTemplateBuilder(String restTemplateBuilder) {
		this.restTemplateBuilder = restTemplateBuilder;
	}

	public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    //重试
	private int retryCount = 1;
	private List<HttpMessageConverter<?>> converters = new ArrayList<>();
    
	public void setConverters(List<HttpMessageConverter<?>> converters) {
	    if(converters!=null&&!converters.isEmpty()){
	        this.converters.addAll(converters);
	    }
    }

    private static int instanceIdx = 1;
    /*
    原来用的熔断器
	private CircuitBreakerConfig circuitBreakerConfig = new CircuitBreakerConfig();

	public void setCircuitBreakerConfig(CircuitBreakerConfig circuitBreakerConfig) {
		this.circuitBreakerConfig = circuitBreakerConfig;
	}
	private CircuitBreaker breaker = null;

	private CircuitBreaker getBreaker() {
		if(breaker == null){
			if(this.circuitBreakerConfig==null){
				this.circuitBreakerConfig = new CircuitBreakerConfig();
			}
			breaker = new CircuitBreaker(
					"DefaultRestClientFactory-RestApiClientCircuitBreaker-"+instanceIdx++,this.circuitBreakerConfig);
		}
		return breaker;
	}
     */

    //目前暂不支持多个cm实例，所以理论上只有1个值
	private List<Pair<HttpClientConnectionManager,IdleConnectionEvictor>> idleGuardians = new ArrayList<>();
	private RestTemplate defaultTemplate;
	
	protected final ConcurrentHashMap<String,IRestClient> templateCache = new ConcurrentHashMap<>();
	private String defaultRestClientKey = "DefaultRestClientFactory_defaultRestClientKey";
	public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
		defaultRequestConfigBuilder.setConnectionRequestTimeout(this.connectionRequestTimeout);
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
		defaultRequestConfigBuilder.setConnectTimeout(this.connectTimeout);
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
		defaultRequestConfigBuilder.setSocketTimeout(this.socketTimeout);
	}

	public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
		this.defaultMaxPerRoute = defaultMaxPerRoute;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}
	
	public void setMaxIdleTime(int maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public void setConnTimeToLive(int connTimeToLive) {
        this.connTimeToLive = connTimeToLive;
    }

    private IBaseService restRequestConfigService;
	public void setRestRequestConfigService(IBaseService restRequestConfigService) {
		this.restRequestConfigService = restRequestConfigService;
	}


	private final RequestConfig.Builder defaultRequestConfigBuilder = 
			RequestConfig.custom()
			.setConnectionRequestTimeout(connectionRequestTimeout)
			.setConnectTimeout(connectTimeout)
			.setSocketTimeout(socketTimeout)
			.setExpectContinueEnabled(false);

//	/**
//	 * 对客户端token进行管理的类
//	 */
//	private ClientTokenServices clientTokenServices;
//	public void setClientTokenServices(ClientTokenServices clientTokenServices) {
//        this.clientTokenServices = clientTokenServices;
//    }

    /**
	 * 默认的构造函数，在不init之前是不可用的builder
	 */
	public DefaultRestClientBuilder(){
		
	}
	
	/**
	 * 静态配置：站点的链路配置
	 */
	private SocketConfig defaultSocketConfig; 
	public void setDefaultSocketConfig(final SocketConfig defaultSocketConfig) {
		this.defaultSocketConfig = defaultSocketConfig; 
    }


	/**
	 * 静态配置：默认链接配置
	 */
	private ConnectionConfig defaultConnectionConfig;
    public void setDefaultConnectionConfig(final ConnectionConfig defaultConnectionConfig) {
    	this.defaultConnectionConfig = defaultConnectionConfig;
    }
    
    /**
	 * 静态配置：各站点的链路配置
	 */
    private Map<String , SocketConfig> scMap=null;
	public void setSockectConfigs(Map<String , SocketConfig> scMap) {
		this.scMap = scMap;
		
	}
	
	/**
	 * 静态配置：各站点的链接配置
	 */
	private Map<String , ConnectionConfig> ccMap=null;
	public void setConnectionConfigs(Map<String , ConnectionConfig> ccMap) {
		this.ccMap = ccMap;
	}

	private DnsResolver dnsResolver;
	public void setDnsResolver(DnsResolver dnsResolver) {
		this.dnsResolver = dnsResolver;
	}

	/**
	 * 所有RestTemplate共用的响应异常处理类
	 */
	private ResponseErrorHandler errorHandler;
	public void setErrorHandler(ResponseErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	@Override
	public final IRestClient buildRestClient() {
		return buildRestClient((RestRequestConfig)null);
	}
	
	@Override
	public final IRestClient buildRestClient(String siteUri) {
		if(restRequestConfigService==null) {
			return buildRestClient();
		}else {
			try {
				RestRequestConfig rrc = (RestRequestConfig)restRequestConfigService.findByPrimaryKey(siteUri);
				return buildRestClient(rrc);
			} catch (Exception e) {
				if(logger.isWarnEnabled()) {
					logger.error("find "+siteUri+"rest request config error, use default restclient instead.",e);
				}
				return buildRestClient();
			}
		}
	}
	private final Lock lock = new ReentrantLock();
	@Override
	/**
	 * 多数情况是内部调用
	 */
	public final IRestClient buildRestClient(RestRequestConfig requestConfig) {
		if(!inited.get()) {
			throw new IllegalStateException("Rest client factory is not initialized.");
		}
		if(requestConfig==null) {
			if(logger.isDebugEnabled()) {
				logger.debug("The rest request config is null, use default template.");
			}
			return templateCache.get(defaultRestClientKey); 
		}else {
			String siteUri = requestConfig.getSiteUri();
			if(templateCache.containsKey(siteUri)) {
				return templateCache.get(siteUri);
			}else {
				try {
					if(lock.tryLock(5,TimeUnit.SECONDS)) {
						try {
							if(templateCache.containsKey(siteUri)) {
								return templateCache.get(siteUri);
							}else {
								RestTemplate restTemplate = createRestTemplate(requestConfig);
								IRestClient c = new RestClient(restTemplate,requestConfig.getSiteUri());
								templateCache.put(requestConfig.getSiteUri(), c);
								return c;
							}
						}finally {
							lock.unlock();
						}
					} else {
						if(logger.isWarnEnabled()) {
							logger.warn("Timeout when waiting for creating rest template for site: "+siteUri);
						}
						return null;
					}
				} catch (InterruptedException e) {
					logger.error("Concurrent create rest template for site: "+siteUri,e);
					return null;
				}
			}
		}
	}
	
	//低频使用
	private AtomicBoolean inited = new AtomicBoolean(false);
	
	public final void refresh() {
		try {
			this.destroy();
		} catch (Exception e) {
			logger.error("Error when refresh"+e);
		}
		init();
	}
	
	/**
	 * 用当前类的参数初始化连接池以及默认的RestTemplate
	 */
	public final void init() {
		if(inited.compareAndSet(false, true)) {
			try {
			    if(converters.isEmpty()){
			        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
			        converters.add(byteArrayHttpMessageConverter);
			        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName(Globals.DEFAULT_CHARSET_UTF8));
			        converters.add(stringHttpMessageConverter);
			        ResourceHttpMessageConverter resourceHttpMessageConverter = new ResourceHttpMessageConverter();
			        converters.add(resourceHttpMessageConverter);
			        converters.add(new SourceHttpMessageConverter<Source>());
			        AllEncompassingFormHttpMessageConverter allEncompassingFormHttpMessageConverter = new AllEncompassingFormHttpMessageConverter();
			        List<HttpMessageConverter<?>> partConverters = new ArrayList<>();
			        partConverters.add(byteArrayHttpMessageConverter);
			        partConverters.add(stringHttpMessageConverter);
			        partConverters.add(resourceHttpMessageConverter);
			        allEncompassingFormHttpMessageConverter.setPartConverters(partConverters);
			        converters.add(allEncompassingFormHttpMessageConverter);
			        converters.add(new MappingJackson2HttpMessageConverter());
			        converters.add(new MappingJackson2XmlHttpMessageConverter());
			    }
				this.connectionManager = createConnectionManager();
				this.defaultTemplate = createRestTemplate((RestRequestConfig)null);
				templateCache.put(defaultRestClientKey, new RestClient(this.defaultTemplate));
				if(logger.isInfoEnabled()) {
					logger.info("Factory initialized.");
				}
			}catch(Exception e) {
				//重置
				inited.compareAndSet(true, false);
				logger.error(e.toString());
				throw new IllegalStateException("Rest client factory is not initialized.",e);
			}
		}else{
			if(logger.isInfoEnabled()) {
				logger.info("Already initialized.");
			}
		}
	}
	
	/**
	 * 强制share
	 * @param httpClientBuilder
	 * @param cm
	 */
	private void shareConnectionManager(HttpClientBuilder httpClientBuilder,HttpClientConnectionManager cm) {
		httpClientBuilder.setConnectionManager(cm);
		httpClientBuilder.setConnectionManagerShared(true);
	}
	
	/**
	 * 创建createConnectionManager
	 * @return
	 */
	private HttpClientConnectionManager createConnectionManager() {
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
		        .register("http", PlainConnectionSocketFactory.getSocketFactory())
		        .register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
		
		HttpClientConnectionOperator co = null;
		
		//判断是否使用熔断器
		if (REST_TEMPLATE_BUILDER_HYSTRIX.equals(restTemplateBuilder)) {
			co = new HystrixFusableConnectionOperator(registry, null, dnsResolver);
		} else {
			co = new DefaultConnectionOperator(registry, null, dnsResolver);
		}
		
		PoolingHttpClientConnectionManager connectionManager =
				new PoolingHttpClientConnectionManager(co,null,connTimeToLive,TimeUnit.MILLISECONDS);
		connectionManager.setMaxTotal(this.maxTotal);
		connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
		if(defaultSocketConfig==null) {
			defaultSocketConfig = SocketConfig.custom()
			            .setSoTimeout(this.soTimeout)
			            .setRcvBufSize(4096)
			            .setSndBufSize(4096)
			            .setSoKeepAlive(false)
			            .build();
		}
		if(defaultConnectionConfig==null) {
			defaultConnectionConfig = ConnectionConfig.custom()
			        .setBufferSize(8192)
			        .setCharset(Charset.forName(Globals.DEFAULT_CHARSET_UTF8)).build();
		}
		
		//全局链路和链接配置
		connectionManager.setDefaultConnectionConfig(defaultConnectionConfig);
		connectionManager.setDefaultSocketConfig(defaultSocketConfig);
		
		//站点链路定制
		if(scMap!=null&&!scMap.isEmpty()) {
			Set<Entry<String, SocketConfig>> entrySet = scMap.entrySet();
			for(Entry<String, SocketConfig> entry:entrySet) {
				String host = entry.getKey();
				SocketConfig sc = entry.getValue();
				connectionManager.setSocketConfig(HttpHost.create(host), sc);
			}
		}
		//站点链接定制
		if(ccMap!=null&&!ccMap.isEmpty()) {
			Set<Entry<String, ConnectionConfig>> entrySet = ccMap.entrySet();
			for(Entry<String, ConnectionConfig> entry:entrySet) {
				String host = entry.getKey();
				ConnectionConfig cc = entry.getValue();
				connectionManager.setConnectionConfig(HttpHost.create(host), cc);
			}
		}
		IdleConnectionEvictor connectionEvictor = new IdleConnectionEvictor(connectionManager,
                maxIdleTime > 0 ? maxIdleTime : 10, TimeUnit.SECONDS);
        connectionEvictor.start();
        this.idleGuardians.add( new Pair<HttpClientConnectionManager,IdleConnectionEvictor >(connectionManager,connectionEvictor) );
		return connectionManager;
	}
	
	protected ConnectionSocketFactory createSSLConnectionSocketFactory() {
        return RestClientPluginUtil.getConnectionSockectFactoryBuilder().buildSSLConnectionSocketFactory();
    }

	protected ConnectionSocketFactory createConnectionSocketFactory() {
        return RestClientPluginUtil.getConnectionSockectFactoryBuilder().buildConnectionSocketFactory();
    }
	
    /**
	 * 创建RestTemplate，由于使用的httpclient是4.4.1版本，ClientHttpRequestFactory需要对接新版API
	 * @param closeableHttpClient
	 * @return
	 */
	protected RestTemplate createRestTemplate(HttpClient client) {
		ClientHttpRequestFactory clientHttpRequestFactory = 
				new RestClientHttpRequestFactory(client);
		
		IRestTemplateBuilder templateBuilder = null;
		
		//判断是否使用熔断器
		if (REST_TEMPLATE_BUILDER_HYSTRIX.equals(restTemplateBuilder)) {
			templateBuilder = new HystrixRestTemplateBuilder();
		} else {
			templateBuilder = new DefaultRestTemplateBuilder();
		}
		
		RestTemplate template = templateBuilder.buildRestTemplate(clientHttpRequestFactory);
		template.setMessageConverters(this.converters);
		if(errorHandler!=null) {
			template.setErrorHandler(errorHandler);
		}
		return template;
	}

//	protected final RestTemplate createOAuth2RestTemplate(CommonAuthInfo authInfo, HttpClient client) {
//		String authConfig = authInfo.getAuthConfig();
//		JSONObject authJson  = (JSONObject) JSONObject.parse(authConfig);
//		String mode = authJson.getString("grant_type");
//		OAuth2ProtectedResourceDetails resourceDetails = null;
//		AccessTokenProvider candidate = null;
//		if(OauthConstants.GrantType.Client_Credentials.name().equalsIgnoreCase(mode)) {
//		    candidate = new ClientCredentialsAccessTokenProvider();
//			resourceDetails = getClientCredentialsResource(authInfo);
//		}else if(OauthConstants.GrantType.Implicit.name().equalsIgnoreCase(mode)){
//		    resourceDetails = getImplicitResource(authInfo);
//		    candidate = new ImplicitAccessTokenProvider();
//		}else if(OauthConstants.GrantType.Resource_Owner_Password_Credentials.name().equalsIgnoreCase(mode)){
//            resourceDetails = getImplicitResource(authInfo);
//            candidate = new ResourceOwnerPasswordAccessTokenProvider();
//        }else if(OauthConstants.GrantType.Authorization_Code.name().equalsIgnoreCase(mode)){
//            resourceDetails = getAuthorizationCodeResource(authInfo);
//            candidate = new AuthorizationCodeAccessTokenProvider();
//        }else {
//			throw new IllegalArgumentException("Unknown Oauth2 grant type: "+mode);
//		}
//		ClientHttpRequestFactory clientHttpRequestFactory =
//				new RestClientHttpRequestFactory(client);
//		OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails);
//		AccessTokenProviderChain accessTokenProviderChain = new AccessTokenProviderChain(Arrays.<AccessTokenProvider> asList(candidate));
//		accessTokenProviderChain.setClientTokenServices(clientTokenServices);
//		template.setAccessTokenProvider(accessTokenProviderChain);
//		template.setRequestFactory(clientHttpRequestFactory);
//		template.setMessageConverters(this.converters);
//		if(errorHandler!=null) {
//			template.setErrorHandler(errorHandler);
//		}
//		return template;
//	}

//	/**
//	 * <pre>
//	 * Implicit:用在移动app或者web app(这些app是在用户的设备上的，如在手机上调起微信来进行认证授权)
//	 * 简化模式（implicit）
//	 * 这种模式比授权码模式少了code环节，回调url直接携带token
//	 * 这种模式的使用场景是基于浏览器的应用
//	 * 这种模式基于安全性考虑，建议把token时效设置短一些
//	 * 不支持refresh token
//	 * </pre>
//	 * @param authInfo
//	 * @return
//	 */
//	protected ImplicitResourceDetails getImplicitResource(CommonAuthInfo authInfo) {
//	    //ImplicitResourceDetails details = new ImplicitResourceDetails();
//	    throw new UnsupportedOperationException("Unsupported Oauth2 grant type: "+OauthConstants.GrantType.Implicit.name());
//	}
//	/**
//	 * <pre>
//	 * 密码模式（resource owner password credentials）
//	 * 这种模式是最不推荐的，因为client可能存了用户密码
//	 * 这种模式主要用来做遗留项目升级为oauth2的适配方案
//	 * 当然如果client是自家的应用，也是可以支持refresh token
//	 * </pre>
//	 * @param authInfo
//	 * @return
//	 */
//	protected ResourceOwnerPasswordResourceDetails getResourceOwnerPasswordResourceDetailsResource(CommonAuthInfo authInfo) {
//	    //ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
//        throw new UnsupportedOperationException("Unsupported Oauth2 grant type: "+OauthConstants.GrantType.Resource_Owner_Password_Credentials.name());
//    }
//	/**
//	 * <pre>
//	 * 授权码模式（authorization code）
//	 * 这种模式算是正宗的oauth2的授权模式
//	 * 设计了auth code，通过这个code再获取token
//	 * 支持refresh token
//	 * </pre>
//	 * @param authInfo
//	 * @return
//	 */
//	protected AuthorizationCodeResourceDetails getAuthorizationCodeResource(CommonAuthInfo authInfo) {
//	    //AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
//        throw new UnsupportedOperationException("Unsupported Oauth2 grant type: "+OauthConstants.GrantType.Authorization_Code.name());
//    }
//
//	/**
//	 * <pre>
//	 * Client Credentials:用在应用API访问。
//	 * 客户端模式（client credentials）
//	 * 这种模式直接根据client的id和密钥即可获取token，无需用户参与
//	 * 这种模式比较合适消费api的后端服务，比如拉取一组用户信息等
//	 * 不支持refresh token
//	 * </pre>
//	 * @param authInfo
//	 * @return
//	 */
//	protected ClientCredentialsResourceDetails getClientCredentialsResource(CommonAuthInfo authInfo) {
//		ClientCredentialsResourceDetails clientCredentials = new ClientCredentialsResourceDetails();
//		String authConfig = authInfo.getAuthConfig();
//		JSONObject authJson  = (JSONObject) JSONObject.parse(authConfig);
//		String cid = authJson.getString(OauthConstants.CLIENT_ID_KEY);
//		String csecret = authJson.getString(OauthConstants.CLIENT_SECRET_KEY);
//		//String tokenName = authJson.getString(OauthConstants.TOKEN_NAME_KEY);
//		String accessTokenUri = authJson.getString(OauthConstants.ACCESS_TOKEN_URI_KEY);
//		String scope = authJson.getString(OauthConstants.SCOPE_KEY);
//		if(StringUtil.isNotNull(scope)) {
//			String[] split = scope.split("\\s*;\\s*");
//			List<String> li = Arrays.asList(split);
//			clientCredentials.setScope(li);
//		}
//		clientCredentials.setClientId(cid);
//		clientCredentials.setClientSecret(csecret);
//		//clientCredentials.setTokenName(tokenName);
//		clientCredentials.setAccessTokenUri(accessTokenUri);
//		//如果对端的验证信息要求放在请求参数里，就设置query，否则设置成header
//		clientCredentials.setClientAuthenticationScheme(AuthenticationScheme.query);
//		return clientCredentials;
//	}

	/**
	 * 根据配置创建RestTemplate，在运行时使用
	 * @param requestConfig
	 * @return
	 */
	protected RestTemplate createRestTemplate(RestRequestConfig requestConfig) {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		initHttpClientBuilder(httpClientBuilder,requestConfig);
		shareConnectionManager(httpClientBuilder,this.connectionManager);
		
		RestTemplate rt;
		if(requestConfig!=null
				&&requestConfig.getAuthInfo()!=null
				&&CommonAuthInfo.AUTH_TYPE_OAUTH2.equalsIgnoreCase(requestConfig.getAuthInfo().getType())) {
//			CommonAuthInfo authInfo = requestConfig.getAuthInfo();
//			rt = createOAuth2RestTemplate(authInfo,httpClientBuilder.build());
			logger.warn("警告！OAuth2已被禁用，请查看具体实现代码。");
			rt = createRestTemplate(httpClientBuilder.build());
		}else {
			rt = createRestTemplate(httpClientBuilder.build());
		}
		return rt;
	}
	
	/**
	 * 初始化HttpClientBuilder
	 * 重写此方法，将导致{@code #otherAuthenticationConfig(HttpClientBuilder, RestRequestConfig)}
	 * 无法调用
	 * @param httpClientBuilder
	 * @param requestConfig
	 */
	protected void initHttpClientBuilder(HttpClientBuilder httpClientBuilder,RestRequestConfig requestConfig) {
		Builder configCopy = RequestConfig.copy(defaultRequestConfigBuilder.build());
		DefaultRequestExecutor exe = new DefaultRequestExecutor(
		        requestConfig==null?RestRequestConfig.WAIT_FOR_CONTINUE:requestConfig.getWaitForContinue());
		httpClientBuilder.setRequestExecutor(exe);
		//httpClientBuilder.evictExpiredConnections();
		if(requestConfig==null) {
			//精简部分功能
			httpClientBuilder.setDefaultRequestConfig(configCopy.build());
			configCopy.setAuthenticationEnabled(false);
			httpClientBuilder.disableAuthCaching();
			httpClientBuilder.disableCookieManagement();
			httpClientBuilder.disableRedirectHandling();
			httpClientBuilder.disableContentCompression();
			httpClientBuilder.evictExpiredConnections();
			httpClientBuilder.setRetryHandler(new StandardHttpRequestRetryHandler(retryCount,true));
			return;
		}
		
		if(requestConfig.getProxyAuthInfo()!=null
				|| requestConfig.getAuthInfo()!=null) {
			configCopy.setAuthenticationEnabled(true);
		}
		
		if(requestConfig.getRetry()>0) {
			httpClientBuilder.setRetryHandler(new StandardHttpRequestRetryHandler(requestConfig.getRetry(),true));
		}else {
			httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(0,false));
		}
		if (StringUtil.isNotNull(requestConfig.getProxyUri())) {
			//使用代理服务器 需要用户认证的代理服务器
			httpClientBuilder.setProxy(new HttpHost(HttpHost.create(requestConfig.getProxyUri())));
			if(requestConfig.getProxyAuthInfo()!=null) {
				httpClientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
			}
		}
		if(requestConfig.getAuthInfo()!=null) {
			httpClientBuilder.setTargetAuthenticationStrategy(new TargetAuthenticationStrategy());
			CommonAuthInfo authInfo = requestConfig.getAuthInfo();
			String type = authInfo.getType();
			if(CommonAuthInfo.AUTH_TYPE_BASIC.equalsIgnoreCase(type)) {
				CredentialsProvider provider=new BasicCredentialsProvider();
				provider.setCredentials(new AuthScope(HttpHost.create(requestConfig.getSiteUri())),
						new UsernamePasswordCredentials(authInfo.getName(), authInfo.getPassword()));
				httpClientBuilder.setDefaultCredentialsProvider(provider);
			}else if(CommonAuthInfo.AUTH_TYPE_OAUTH2.equalsIgnoreCase(type)){
				//OAUTH2不需要以下几个默认功能
				configCopy.setCookieSpec(CookieSpecs.IGNORE_COOKIES)
				.setAuthenticationEnabled(false)
				.setRedirectsEnabled(false);
			}else {
				otherAuthenticationConfig(httpClientBuilder,requestConfig);
			}
		}
		
		if (StringUtil.isNotNull(requestConfig.getUserAgent())) {
			httpClientBuilder.setUserAgent(requestConfig.getUserAgent());
		}
		Map<String, String> defaultHeader = requestConfig.getDefaultHeader();
		if(!CollectionUtils.isEmpty(requestConfig.getDefaultHeader())) {
			Set<String> keySet = defaultHeader.keySet();
			List<Header> headers = new ArrayList<>();
			for(String key:keySet) {
				headers.add(new BasicHeader(key,defaultHeader.get(key)));
			}
			httpClientBuilder.setDefaultHeaders(headers);
		}
		httpClientBuilder.setDefaultRequestConfig(configCopy.build());
	}
	
	protected void otherAuthenticationConfig(HttpClientBuilder httpClientBuilder,RestRequestConfig requestConfig) {
		throw new IllegalArgumentException("Unsupported auth type: "+requestConfig.getAuthInfo().getType());
	}
	
	@Override
	public void destroy() throws Exception {
		for(Pair<HttpClientConnectionManager, IdleConnectionEvictor> pair : idleGuardians) {
			try {
				pair.getValue().shutdown();
				pair.getKey().shutdown();
			}catch(Exception e) {
				logger.error(e.toString());
			}
		}
		//gc
		idleGuardians.clear();
		inited.compareAndSet(true, false);
	}
	
	private static class Pair<K,V>{
		private K k;
		private V v;
		Pair(K k, V v){
			Pair.this.k = k;
			Pair.this.v = v;
		}
		K getKey() {
			return k;
		}
		V getValue() {
			return v;
		}
	}
}
