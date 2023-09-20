package com.landray.kmss.tic.rest.client.http.apache;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import com.landray.kmss.tic.rest.client.error.RestError;
import com.landray.kmss.tic.rest.client.error.RestErrorException;
import com.landray.kmss.tic.rest.client.error.RestErrorKeys;
import com.landray.kmss.tic.rest.client.http.MediaUploadRequestExecutor;
import com.landray.kmss.tic.rest.client.http.RequestHttp;

public class ApacheMediaUploadRequestExecutor extends MediaUploadRequestExecutor<CloseableHttpClient, HttpHost,RestErrorKeys> {
	public ApacheMediaUploadRequestExecutor(RequestHttp requestHttp) {
		super(requestHttp);
	}

	@Override
	public String execute(String uri, File file) throws RestErrorException, IOException {
		HttpPost httpPost = new HttpPost(uri);
		if (requestHttp.getRequestHttpProxy() != null) {
			RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
			httpPost.setConfig(config);
		}
		if (file != null) {
			HttpEntity entity = MultipartEntityBuilder.create().addBinaryBody("media", file)
					.setMode(HttpMultipartMode.RFC6532).build();
			httpPost.setEntity(entity);
		}
		try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost)) {
			String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
			RestError error = RestError.fromJson(responseContent,requestHttp.getErrorKeys());
			if (error.hasError()) {
				throw new RestErrorException(error);
			}
			return responseContent;
		} finally {
			httpPost.releaseConnection();
		}
	}
}
