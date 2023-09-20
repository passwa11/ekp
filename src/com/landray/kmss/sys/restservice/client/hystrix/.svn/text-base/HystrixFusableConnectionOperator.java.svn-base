/*
 * 文 件 名:  HystrixFusableConnectBuilder.java
 * 版    权:  
 * 描    述:  <描述>
*/

package com.landray.kmss.sys.restservice.client.hystrix;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.http.HttpHost;
import org.apache.http.config.Lookup;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.conn.DefaultHttpClientConnectionOperator;
import org.apache.http.protocol.HttpContext;

import com.landray.kmss.sys.restservice.client.hystrix.command.CommandFactory;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  宗志 2003798
 * @version  [1.0.0, 2021年9月23日]
 */
public class HystrixFusableConnectionOperator extends DefaultHttpClientConnectionOperator {

	public HystrixFusableConnectionOperator(Lookup<ConnectionSocketFactory> socketFactoryRegistry, SchemePortResolver schemePortResolver, DnsResolver dnsResolver) {
		super(socketFactoryRegistry, schemePortResolver, dnsResolver);
	}

	@Override
	public void connect(final ManagedHttpClientConnection conn, final HttpHost host, final InetSocketAddress localAddress, final int connectTimeout, final SocketConfig socketConfig, final HttpContext context) throws IOException {
		CommandAction action = new CommandAction<Object>() {
			@Override
			public Object execute() throws Exception {
				HystrixFusableConnectionOperator.super.connect(conn, host, localAddress, connectTimeout, socketConfig, context);
				return null;
			}
		};
		try {
			//breaker.execute(action);
			CommandFactory.getInstance().createCommand(action).execute();
		} catch (Exception e) {
			if (e instanceof IOException) {
				throw (IOException) e;
			} else {
				throw new IOException(e);
			}
		}
	}
}
