package com.landray.kmss.sys.restservice.client;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.Configurable;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Assert;

/**
 * <pre>
 * 重写setConnectTimeout和setReadTimeout来支持高版本的httpclient
 * {@link HttpClientBuilder#setDefaultRequestConfig(org.apache.http.client.config.RequestConfig)}
 * 对于不同的第三方，需要不同的ClientHttpRequestFactory实现，主要是设置RequestConfig
 * </pre>
 * @author 陈进科
 * @since 1.0  2018年12月27日
 *
 */
public class RestClientHttpRequestFactory
		extends HttpComponentsClientHttpRequestFactory{

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(RestClientHttpRequestFactory.class);

	/**
	 * 当前工厂使用的RequestConfig，
	 * 只关注3个关键参数,其它的参数在org.apache.http.impl.client.HttpClientBuilder初始化的时候指定
	 * @see setConnectTimeout
	 * @see setConnectionRequestTimeout
	 * @see setReadTimeout
	 * 
	 */
	private RequestConfig requestConfig;

	public RestClientHttpRequestFactory(HttpClient httpClient) {
		super(httpClient);
	}

	@Override
	public void setConnectTimeout(int timeout) {
		Assert.isTrue(timeout >= 0, "Timeout must be a non-negative value");
		this.requestConfig = requestConfigBuilder().setConnectTimeout(timeout).build();
	}

	@Override
    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.requestConfig = requestConfigBuilder().setConnectionRequestTimeout(connectionRequestTimeout).build();
	}

	@Override
	public void setReadTimeout(int timeout) {
		Assert.isTrue(timeout >= 0, "Timeout must be a non-negative value");
		this.requestConfig = requestConfigBuilder().setSocketTimeout(timeout).build();
	}

	
	private RequestConfig.Builder requestConfigBuilder() {
		return (this.requestConfig != null ? RequestConfig.copy(this.requestConfig) : RequestConfig.custom());
	}

	@Override
    protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
		switch (httpMethod) {
			case GET:
				return new HttpGet(uri);
			case HEAD:
				return new HttpHead(uri);
			case POST:
				return new HttpPost(uri);
			case PUT:
				return new HttpPut(uri);
			case PATCH:
				return new HttpPatch(uri);
			case DELETE:
				return new HttpDelete(uri);
			case OPTIONS:
				return new HttpOptions(uri);
			case TRACE:
				return new HttpTrace(uri);
			default:
				throw new IllegalArgumentException("Invalid HTTP method: " + httpMethod);
		}
	}

	@Override
	protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
		HttpContext context = super.createHttpContext(httpMethod, uri);
		if(context==null) {
			context = HttpClientContext.create();
		}
		//HttpEntity he ;
		if (context.getAttribute(HttpClientContext.REQUEST_CONFIG) == null) {
			RequestConfig config = createRequestConfig(getHttpClient());
			if (config != null) {
				context.setAttribute(HttpClientContext.REQUEST_CONFIG, config);
			}
		}
		return context;
	}
	
	/**
	 * Template method that allows for manipulating the {@link HttpUriRequest} before it is
	 * returned as part of a {@link HttpComponentsClientHttpRequest}.
	 * <p>The default implementation is empty.
	 * @param request the request to process
	 */
	@Override
    protected void postProcessHttpRequest(HttpUriRequest request) {
		//HttpRequestBase b;
		
	}

	@Override
	public void destroy() {
		HttpClient client = getHttpClient();
		if (client instanceof Closeable) {
			try {
				((Closeable) client).close();
			} catch (IOException e) {
				if(logger.isWarnEnabled()) {
					logger.warn("destroy {}",e);
					throw new IllegalStateException(e);
				}
			}
		}
	}


	private static class HttpDelete extends HttpEntityEnclosingRequestBase {
		public HttpDelete(URI uri) {
			super();
			setURI(uri);
		}
		@Override
		public String getMethod() {
			return "DELETE";
		}
	}
}
