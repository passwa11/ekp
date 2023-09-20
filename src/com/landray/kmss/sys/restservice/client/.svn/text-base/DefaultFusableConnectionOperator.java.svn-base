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
@Deprecated
public class DefaultFusableConnectionOperator extends DefaultHttpClientConnectionOperator {

	/*
	原熔断器对应的操作类构造函数
	private final CircuitBreaker breaker;

	public FusableConnectionOperator(CircuitBreaker breaker, Lookup<ConnectionSocketFactory> socketFactoryRegistry,
			SchemePortResolver schemePortResolver, DnsResolver dnsResolver) {
		super(socketFactoryRegistry, schemePortResolver, dnsResolver);
		this.breaker = breaker;

	}
	 */

	public DefaultFusableConnectionOperator(Lookup<ConnectionSocketFactory> socketFactoryRegistry,
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
		/*
		原熔断器执行的action
		ProtectedAction action = new ProtectedAction() {
			@Override
			public Object execute() throws Exception {
				FusableConnectionOperator.super.connect(conn, host, localAddress, connectTimeout, socketConfig, context);
				return null;
			}
			@Override
			public boolean isBreakException(Exception e) {
				return e instanceof ConnectTimeoutException;
			}
		};
		 */		
	}
}
