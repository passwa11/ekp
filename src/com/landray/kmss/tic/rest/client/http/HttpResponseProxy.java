package com.landray.kmss.tic.rest.client.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;

import com.landray.kmss.tic.rest.client.error.RestError;
import com.landray.kmss.tic.rest.client.error.RestErrorException;

/**
 * <pre>
 * 三种http框架的response代理类，方便提取公共方法
 * </pre>
 *
 */
public class HttpResponseProxy {
	private static final Pattern PATTERN = Pattern.compile(".*filename=\"(.*)\"");

	private CloseableHttpResponse apacheHttpResponse;

	public HttpResponseProxy(CloseableHttpResponse apacheHttpResponse) {
		this.apacheHttpResponse = apacheHttpResponse;
	}

	public String getFileName() throws RestErrorException {

		if (this.apacheHttpResponse != null) {
			return this.getFileName(this.apacheHttpResponse);
		}

		// cannot happen
		return null;
	}

	private String getFileName(CloseableHttpResponse response) throws RestErrorException {
		Header[] contentDispositionHeader = response.getHeaders("Content-disposition");
		if (contentDispositionHeader == null || contentDispositionHeader.length == 0) {
			throw new RestErrorException(RestError.fromMsg("无法获取到文件名"));
		}

		return this.extractFileNameFromContentString(contentDispositionHeader[0].getValue());
	}

	private String extractFileNameFromContentString(String content) throws RestErrorException {
		if (content == null || content.length() == 0) {
			throw new RestErrorException(RestError.fromMsg("无法获取到文件名"));
		}

		Matcher m = PATTERN.matcher(content);
		if (m.matches()) {
			return m.group(1);
		}

		throw new RestErrorException(RestError.fromMsg("无法获取到文件名"));
	}

}
