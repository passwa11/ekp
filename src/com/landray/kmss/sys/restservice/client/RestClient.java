package com.landray.kmss.sys.restservice.client;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import com.landray.kmss.util.StringUtil;

/**
 * {@link IRestClient},{@link IRestClientDefaultValueSupport}的实现类，通过{@code RestClientBuilder#build()}获得
 * @author 陈进科
 * @since 1.0  2018年12月29日
 *
 */
public class RestClient implements IRestClient{
    protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
    protected final RestOperations restTemplate;
    protected final String siteUri;
    /**
     * 没有设置默认站点的客户端被称谓默认客户端,默认客户端只支持完整的url访问方式
     */
    private boolean isDefaultClient = false;
    protected RestClient(RestOperations restTemplate, String siteUri) {
    	Assert.isTrue(restTemplate!=null, "The RestTemplate cannot be null.");
    	this.restTemplate = restTemplate;
    	//移除多余的斜杠
    	while(StringUtil.isNotNull(siteUri) && siteUri.endsWith(slash)) {
            //一般只执行1次或不执行
    	    siteUri = siteUri.substring(0,siteUri.length()-1);
        }
    	this.siteUri = siteUri;
    	this.isDefaultClient = StringUtil.isNull(this.siteUri);
    }
    
    protected RestClient(RestOperations restTemplate) {
    	this(restTemplate,null);
    }
    
    
	public String getSiteUri() {
        return siteUri;
    }

    @Override
	public RestOperations getRestOperations() {
		return restTemplate;
	}
	protected static final String http = "http";
	protected static final String https = "https";
	protected static final String slash = "/";
	
	/**
	 * 根据配置自动补全url，如果已经是http(s)开头的，则不执行
	 * @param url
	 * @return  返回的是完整的url格式: http(s)://hostname:port/path?query=.....
	 */
	protected final String transformUrl(String rawUrl) {
	    try{
	        if(StringUtil.isNull(rawUrl)) {
	            if(isDefaultClient){
	                throw new IllegalStateException("This client's site is not setted, blank url is not allowed, retry with full url.");
	            }else{
	                return this.siteUri;
	            }
	        }
	        rawUrl = rawUrl.trim();
	        if(rawUrl.startsWith(http)||rawUrl.startsWith(https)) {
	            if(!isDefaultClient && !rawUrl.startsWith(siteUri)) {
	                if(logger.isWarnEnabled()) {
	                    logger.warn(String.format(warningForMissmatch, rawUrl,siteUri));
	                }
	            }
	            return rawUrl;
	        }
	        if(isDefaultClient) {
	            throw new IllegalStateException("This client's site is not setted, blank url is not allowed, retry with full url.");
	        }
	        rawUrl = doTransform(rawUrl);
	    }catch(Exception e){
	        throw new RestClientException(e.getMessage(),e);
	    }
		return rawUrl;
	}
	
	/**
	 * <pre>
	 * 将rawUrl转换成实际的url，该方法默认在所有GET|POST方法中被调用，所以
	 * <p>
	 * <b>
	 * 注意：在这个方法中不能再使用this.get|post，如果真的需要从外部获取，请使用原生操作，比如
	 * this.getRestOperations().get|post|xxx
	 * </b></p>
	 * </pre>
	 * @param rawUrl  非空
	 * @return
	 */
	protected String doTransform(String rawUrl){
	    String snapshot = rawUrl;
	    String site =this.siteUri; 
        //移除多余的斜杠
        while(StringUtil.isNotNull(site) && site.endsWith(slash)) {
            //一般只执行1次或不执行
            site = site.substring(0,site.length()-1);
        }
        while(StringUtil.isNotNull(rawUrl) && rawUrl.startsWith(slash)) {
            rawUrl = rawUrl.substring(1, rawUrl.length());
        }
        rawUrl = site + slash + rawUrl;
        if(logger.isInfoEnabled()) {
            logger.info("origin url: "+snapshot+", turn to: "+rawUrl);
        }
        return rawUrl;
	}
	private static final String warningForMissmatch = "Cross site access warning: target[%s], defaultsite[%s]";
	@Override
	public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> urlVariables)
			throws RestClientException {
		T t = restTemplate.getForObject(transformUrl(url), responseType, urlVariables);
		return t;
	}
	@Override
	public <T> T getForObject(String url, Class<T> responseType) throws RestClientException {
		return restTemplate.getForObject(transformUrl(url), responseType);
	}
	@Override
	public void get(String url) throws RestClientException {
		this.getForObject(transformUrl(url), null);
	}
	@Override
	public void get() throws RestClientException {
		Assert.isTrue(StringUtil.isNotNull(siteUri), "site uri is not setted, try other methods which accept url argument.");
		this.get(siteUri);
	}
	@Override
	public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> urlVariables)
			throws RestClientException {
		ResponseEntity<T> exchange = restTemplate.getForEntity(transformUrl(url),responseType,urlVariables);
		return exchange;
	}
	@Override
	public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType) throws RestClientException {
		ResponseEntity<T> exchange = restTemplate.getForEntity(transformUrl(url), responseType);
		return exchange;
	}
	
	/////////////   post /////////////////
	@Override
	public <T> T postForObject(String url, Class<T> responseType, Object requestBody) throws RestClientException {
		T t = restTemplate.postForObject(transformUrl(url), requestBody, responseType);
		return t;
	}
	@Override
	public <T> T postForObject(String url, Class<T> responseType) throws RestClientException {
		return postForObject(transformUrl(url),responseType,null);
	}
	@Override
	public void post(String url) throws RestClientException {
		postForObject(transformUrl(url),null,null);
	}
	@Override
	public <T> ResponseEntity<T> postForEntity(String url, Class<T> responseType, Object requestBody)
			throws RestClientException {
		ResponseEntity<T> postForEntity = restTemplate.postForEntity(transformUrl(url), requestBody, responseType);
		return postForEntity;
	}
	@Override
	public <T> ResponseEntity<T> postForEntity(String url, Class<T> responseType) throws RestClientException {
		return postForEntity(transformUrl(url),responseType,null);
	}
	@Override
	public ResponseEntity<?> postForEntity(String url) throws RestClientException {
		return postForEntity(transformUrl(url),Object.class,null);
	}
}
