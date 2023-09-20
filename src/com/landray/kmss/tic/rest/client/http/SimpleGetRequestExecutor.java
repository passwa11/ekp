package com.landray.kmss.tic.rest.client.http;

import com.landray.kmss.tic.rest.client.http.apache.ApacheHttpClientSimpleGetRequestExecutor;

/**
 * 简单的GET请求执行器，请求的参数是String, 返回的结果也是String
 *
 */
public abstract class SimpleGetRequestExecutor<H, P,E> implements RequestExecutor<String, String> {
  protected RequestHttp<H, P,E> requestHttp;

  public SimpleGetRequestExecutor(RequestHttp<H, P,E> requestHttp) {
    this.requestHttp = requestHttp;
  }

  public static RequestExecutor<String, String> create(RequestHttp requestHttp) {
        return new ApacheHttpClientSimpleGetRequestExecutor(requestHttp);
  }

}
