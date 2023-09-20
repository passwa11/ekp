package com.landray.kmss.tic.rest.client.http;

import com.landray.kmss.tic.rest.client.http.apache.ApacheSimplePostRequestExecutor;

/**
 * 用装饰模式实现
 * 简单的POST请求执行器，请求的参数是String, 返回的结果也是String
 *
 */
public abstract class SimplePostRequestExecutor<H, P,R> implements RequestExecutor<String, String> {
  protected RequestHttp<H, P,R> requestHttp;

  public SimplePostRequestExecutor(RequestHttp requestHttp) {
    this.requestHttp = requestHttp;
  }

  public static RequestExecutor<String, String> create(RequestHttp requestHttp) {
        return new ApacheSimplePostRequestExecutor(requestHttp);
  }

}
