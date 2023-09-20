package com.landray.kmss.third.ldap.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

import com.landray.kmss.third.ldap.ssl.MySSLContext;
import com.landray.kmss.third.ldap.ssl.MyTrustManager;

public class MySSLSocketFactory extends SSLSocketFactory {

	private SSLSocketFactory factory;
	
	private static SSLSocketFactory getSSLSocketFactory() { 
        //MyKeyManager keyManager = new MyKeyManager(KeyStoreType.PKCS12, "d:/key.p12", "123456".toCharArray());
        MyTrustManager trustManager = new MyTrustManager("d:/ssl2.jks", "landray".toCharArray());
        MySSLContext context = new MySSLContext("TLS", null, trustManager);
        return context.getSSLContext().getSocketFactory(); 
    }

	public MySSLSocketFactory() {

		try {

			factory = getSSLSocketFactory();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static SocketFactory getDefault() {

		return new MySSLSocketFactory();

	}

	@Override
    public Socket createSocket(Socket socket, String s, int i, boolean flag)
			throws IOException {

		return factory.createSocket(socket, s, i, flag);

	}

	@Override
    public Socket createSocket(InetAddress inaddr, int i, InetAddress inaddr1,
                               int j) throws IOException {

		return factory.createSocket(inaddr, i, inaddr1, j);

	}

	@Override
    public Socket createSocket(InetAddress inaddr, int i) throws IOException {

		return factory.createSocket(inaddr, i);

	}

	@Override
    public Socket createSocket(String s, int i, InetAddress inaddr, int j)
			throws IOException {

		return factory.createSocket(s, i, inaddr, j);

	}

	@Override
    public Socket createSocket(String s, int i) throws IOException {

		return factory.createSocket(s, i);

	}

	@Override
    public String[] getDefaultCipherSuites() {

		return factory.getSupportedCipherSuites();

	}

	@Override
    public String[] getSupportedCipherSuites() {

		return factory.getSupportedCipherSuites();

	}

}
