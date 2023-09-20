package com.landray.kmss.sys.restservice.client;

import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

/**
 * <pre>
 * 默认的链接工厂构造类，它是扩展点(com.landray.kmss.sys.restservice.client)的一个实现
 * http {@link org.apache.http.conn.socket.PlainConnectionSocketFactory#getSocketFactory()}
 * https {@link org.apache.http.conn.ssl.SSLConnectionSocketFactory#getSocketFactory()}
 * </pre>
 * @author 陈进科
 * 2020年12月14日
 */
public class DefaultConnectionSocketFactoryBuilder implements IConnectionSocketFactoryBuilder{

    @Override
    public ConnectionSocketFactory buildSSLConnectionSocketFactory() {
        return SSLConnectionSocketFactory.getSocketFactory();
    }

    @Override
    public ConnectionSocketFactory buildConnectionSocketFactory() {
        return PlainConnectionSocketFactory.getSocketFactory();
    }
}
