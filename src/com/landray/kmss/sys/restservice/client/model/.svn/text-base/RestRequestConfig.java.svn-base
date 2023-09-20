package com.landray.kmss.sys.restservice.client.model;

import java.util.Map;

/**
 * 客户端配置存储
 *
 */
public interface RestRequestConfig {
	
	/**
	 * 从连接池获取连接的timeout ，毫秒
	 * @param connectionRequestTimeout
	 */
	public void setConnectionRequestTimeout(int connectionRequestTimeout) ;
	public int getConnectionRequestTimeout();
	
	/**
	 * 连接超时时间，毫秒 
	 * @param connectTimeout
	 */
	public void setConnectTimeout(int connectTimeout) ;
	public int getConnectTimeout();
	
	/**
	 * 读写超时时间，毫秒 
	 * @param socketTimeout
	 */
	public void setSocketTimeout(int socketTimeout) ;
	public int getSocketTimeout();
	
	/**
	 * <pre>
	 * 等待服务器返回100-continue的时间，毫秒，
	 * 在对端存在安全认证的情况下，如果请求内容包含上传或者大字段的时候，确保对端能在此时间内返回验证结果
	 * 否则客户端将继续传递内容
	 * 1. 传递Headers到服务端
	 * 2. 等待waitForContinue
	 * 3. 如果超时或非401返回码，则继续传递Body
	 * </pre>
	 * @param waitForContinue  毫秒
	 */
    public void setWaitForContinue(int waitForContinue) ;
    public int getWaitForContinue() ;
    int WAIT_FOR_CONTINUE = 3000;
	/**
	 * 获取站点uri，返回格式为http(s)://hostname:port，比如http://exp.landray.com.cn:9000
	 * 注意不能以/结尾
	 * 不可空
	 * @return
	 */
	public String getSiteUri();
	public void setSiteUri(String siteUri);

	/**
	 * 客户端代理，可空
	 * @return
	 */
	public String getUserAgent();
	public void setUserAgent(String userAgent);

	/**
	 * 默认的访问url，可空
	 * @return
	 */
	public String getDefaultUrl() ;
	public void setDefaultUrl(String defaultUrl) ;


	/**
	 * 代理服务器, 可空，格式为http(s)://hostname:port/
	 * @return
	 */
	public String getProxyUri() ;
	public void setProxyUri(String proxyUri) ;
	
	/**
	 * 对端认证信息, 可空
	 * @return
	 */
	public CommonAuthInfo getAuthInfo() ;
	public void setAuthInfo(CommonAuthInfo authInfo);

	/**
	 * 代理认证信息，可空
	 * @return
	 */
	public CommonAuthInfo getProxyAuthInfo() ;
	public void setProxyAuthInfo(CommonAuthInfo proxyAuthInfo) ;
	
	/**
	 * 重试次数，大于0表示允许重试
	 * @return
	 */
	public int getRetry();
	public void setRetry(int retry) ;

	/**
	 * 本地网卡，当主机有多个网卡的时候，指定某个网卡为发送口
	 * IP地址的格式，可空
	 * @return
	 */
	public String getLocalAddress();
	public void setLocalAddress(String localAddress) ;
	
	/**
	 * 默认头部信息　　可空
	 * 相同的headername会被后来者替换
	 * @param defaultHeader
	 */
	public void setDefaultHeader(String headerName,String value);
	public void setDefaultHeader(Map<String,String> defaultHeader);
	public Map<String,String> getDefaultHeader() ;
}
