/*
 * 文 件 名:  DefaultFusableConnectionOperatorBuilder.java
 * 版    权:  
 * 描    述:  <描述>
*/

package com.landray.kmss.sys.restservice.client;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.http.HttpHost;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.impl.conn.DefaultHttpClientConnectionOperator;
import org.apache.http.protocol.HttpContext;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  宗志 2003798
 * @version  [1.0.0, 2021年9月23日]
 */
public interface IFusableConnectionOperator {

	public void connect(DefaultHttpClientConnectionOperator operator, final ManagedHttpClientConnection conn,
			final HttpHost host, final InetSocketAddress localAddress, final int connectTimeout,
			final SocketConfig socketConfig, final HttpContext context) throws IOException;
}
