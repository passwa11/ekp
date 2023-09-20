package com.landray.kmss.tic.rest.client.http;

import java.io.File;

import com.landray.kmss.tic.rest.client.http.apache.ApacheMediaDownloadRequestExecutor;

/**
 * 下载媒体文件请求执行器，请求的参数是String, 返回的结果是File 视频文件不支持下载
 *
 */
public abstract class BaseMediaDownloadRequestExecutor<H, P,R> implements RequestExecutor<File, String> {
	protected RequestHttp<H, P,R> requestHttp;
	protected File tmpDirFile;

	public BaseMediaDownloadRequestExecutor(RequestHttp<H, P,R> requestHttp, File tmpDirFile) {
		this.requestHttp = requestHttp;
		this.tmpDirFile = tmpDirFile;
	}

	public static RequestExecutor<File, String> create(RequestHttp requestHttp, File tmpDirFile) {
		return new ApacheMediaDownloadRequestExecutor(requestHttp, tmpDirFile);
	}

}
