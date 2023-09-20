package com.landray.kmss.tic.rest.client.http;

import java.io.File;

import com.landray.kmss.tic.rest.client.http.apache.ApacheMediaUploadRequestExecutor;

/**
 * 上传媒体文件请求执行器，请求的参数是File, 返回的结果是String
 *
 */
public abstract class MediaUploadRequestExecutor<H, P,R> implements RequestExecutor<String, File> {
	protected RequestHttp<H, P,R> requestHttp;

	public MediaUploadRequestExecutor(RequestHttp requestHttp) {
		this.requestHttp = requestHttp;
	}

	public static RequestExecutor<String, File> create(RequestHttp requestHttp) {
		return new ApacheMediaUploadRequestExecutor(requestHttp);
	}

}
