package com.landray.kmss.sys.restservice.client;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

/**
 * <pre>
 * 适用于EKP的简版{@link RestOperations}，提供常用的调用接口，
 * 非entity方法返回的都是ResponseBody的封装，无法获取HttpHeader信息，如果需要头部信息，请使用*entity
 * <b>
 * 注意：如果对端提供的接口返回的是JSON/XML格式，并且己端没有匹配的对象与之匹配，建议把responseType设置成java.util.Map.class
 * </b>
 * </pre>
 * @author 陈进科
 * @since 1.0  2018年12月29日
 *
 */
public interface IRestClient extends RestOperationsSupport{
	public static final String DEFAULT_BEAN_ID = "defaultRestClient";
	/**
	 * 通过GET方式发送请求
	 * @param url  地址
	 * @param responseType  返回类型
	 * @param urlVariables  请求参数
	 * @throws RestClientException
	 */
	public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> urlVariables) throws RestClientException;
	
	/**
	 * 通过GET方式发送请求，请求不带参数
	 * @param url
	 * @param responseType
	 * @return
	 * @throws RestClientException
	 */
	public <T> T getForObject(String url, Class<T> responseType) throws RestClientException ;
	
	/**
	 * 通过GET方式发送请求，不带参数，忽略返回值
	 * @param url
	 * @throws RestClientException
	 */
	public void get(String url) throws RestClientException ;
	
	/**
	 * 通过GET方式发送请求，不带参数，忽略返回值
	 * @throws RestClientException
	 */
	public void get() throws RestClientException ;
	
	/**
	 * 通过GET方式发送请求，并且返回详细的HTTP响应ResponseEntity
	 * @param url
	 * @param responseType
	 * @param urlVariables
	 * @return
	 * @throws RestClientException
	 */
	public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType,  Map<String, ?> urlVariables) throws RestClientException;
	
	/**
	 * 通过GET方式发送请求，并且返回详细的HTTP响应ResponseEntity
	 * @param url
	 * @param responseType
	 * @return
	 * @throws RestClientException
	 */
	public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType) throws RestClientException ;
	
	/**
	 * 通过POST方式发送请求
	 * @param url  地址
	 * @param responseType  返回类型
	 * @param requestBody  请求参数
	 * @return
	 * @throws RestClientException
	 */
	public <T> T postForObject(String url, Class<T> responseType, Object requestBody) throws RestClientException;
	
	
	/**
	 * 通过POST方式发送请求，不带参数
	 * @param url  地址
	 * @param responseType  返回类型
	 * @return
	 * @throws RestClientException
	 */
	public <T> T postForObject(String url, Class<T> responseType) throws RestClientException ;
	
	/**
	 * 通过POST方式发送请求，
	 * @param url
	 * @throws RestClientException
	 */
	public void post(String url) throws RestClientException ;
	
	/**
	 * 通过POST方式发送请求，并且返回详细的HTTP响应ResponseEntity
	 * @param url
	 * @param responseType
	 * @param requestBody
	 * @return
	 * @throws RestClientException
	 */
	public <T> ResponseEntity<T> postForEntity(String url, Class<T> responseType, Object requestBody) throws RestClientException;
	
	/**
	 * 通过POST方式发送请求，并且返回详细的HTTP响应ResponseEntity
	 * @param url
	 * @param responseType
	 * @return
	 * @throws RestClientException
	 */
	public <T> ResponseEntity<T> postForEntity(String url, Class<T> responseType) throws RestClientException ;
	
	/**
	 * 通过POST方式发送请求，并且返回详细的HTTP响应ResponseEntity
	 * @param url
	 * @param responseType
	 * @return
	 * @throws RestClientException
	 */
	public ResponseEntity<?> postForEntity(String url) throws RestClientException ;
	
}
