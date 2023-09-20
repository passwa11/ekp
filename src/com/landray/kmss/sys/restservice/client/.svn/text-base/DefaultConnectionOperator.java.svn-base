package com.landray.kmss.sys.restservice.client;

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

/**
 * 可熔断的连接操作类
 *
 * 熔断器从系统的CircuitBreaker变成引入hystrix的能力 by 苏运彬 2021/7/15
 *
 * @author 陈进科
 * @since 1.0  2019年1月15日
 *
 */
public class DefaultConnectionOperator extends DefaultHttpClientConnectionOperator {

	public DefaultConnectionOperator(Lookup<ConnectionSocketFactory> socketFactoryRegistry,
									 SchemePortResolver schemePortResolver, DnsResolver dnsResolver){
		super(socketFactoryRegistry, schemePortResolver, dnsResolver);
	}
	
	@Override
	public void connect(
            final ManagedHttpClientConnection conn,
            final HttpHost host,
            final InetSocketAddress localAddress,
            final int connectTimeout,
            final SocketConfig socketConfig,
            final HttpContext context) throws IOException {
		//默认不使用熔断器执行的action
		super.connect(conn, host, localAddress, connectTimeout, socketConfig, context);

	}
}
