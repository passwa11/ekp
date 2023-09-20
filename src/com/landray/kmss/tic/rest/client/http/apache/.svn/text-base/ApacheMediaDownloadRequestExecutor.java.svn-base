package com.landray.kmss.tic.rest.client.http.apache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;

import com.landray.kmss.tic.rest.client.error.RestError;
import com.landray.kmss.tic.rest.client.error.RestErrorException;
import com.landray.kmss.tic.rest.client.error.RestErrorKeys;
import com.landray.kmss.tic.rest.client.http.BaseMediaDownloadRequestExecutor;
import com.landray.kmss.tic.rest.client.http.HttpResponseProxy;
import com.landray.kmss.tic.rest.client.http.RequestHttp;
import com.landray.kmss.tic.rest.client.util.FileUtils;

public class ApacheMediaDownloadRequestExecutor
		extends BaseMediaDownloadRequestExecutor<CloseableHttpClient, HttpHost,RestErrorKeys> {

	public ApacheMediaDownloadRequestExecutor(RequestHttp requestHttp, File tmpDirFile) {
		super(requestHttp, tmpDirFile);
	}

	@Override
	public File execute(String uri, String queryParam) throws RestErrorException, IOException {
		if (queryParam != null) {
			if (uri.indexOf('?') == -1) {
				uri += '?';
			}
			uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
		}

		HttpGet httpGet = new HttpGet(uri);
		if (requestHttp.getRequestHttpProxy() != null) {
			RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
			httpGet.setConfig(config);
		}

		try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpGet);
				InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response)) {
			Header[] contentTypeHeader = response.getHeaders("Content-Type");
			if (contentTypeHeader != null && contentTypeHeader.length > 0) {
				if (contentTypeHeader[0].getValue().startsWith(ContentType.APPLICATION_JSON.getMimeType())) {
					// application/json; encoding=utf-8 下载媒体文件出错
					String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
					// 错误码处理
					throw new RestErrorException(RestError.fromJson(responseContent,requestHttp.getErrorKeys()));
				}
			}

			String fileName = new HttpResponseProxy(response).getFileName();
			if (StringUtils.isBlank(fileName)) {
				return null;
			}

			return FileUtils.createTmpFile(inputStream, FilenameUtils.getBaseName(fileName),
					FilenameUtils.getExtension(fileName), super.tmpDirFile);

		} finally {
			httpGet.releaseConnection();
		}
	}

}
