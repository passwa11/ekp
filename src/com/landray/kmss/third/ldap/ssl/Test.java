package com.landray.kmss.third.ldap.ssl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import com.landray.kmss.third.ldap.ssl.MyKeyStoreUtil.KeyStoreType;

public class Test {

    public static void main(String[] args) throws Exception {

        // System.setProperty("javax.net.debug", "all");

        URL url = new URL("https://www.xxxx.com");

        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        connection.setSSLSocketFactory(getSSLSocketFactory());

        InputStream in = connection.getInputStream();
        byte[] bys = new byte[8192];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int p = 0; (p = in.read(bys)) != -1;) {
            baos.write(bys, 0, p);
        }
        String str = new String(baos.toByteArray());
        System.out.println(str);
    }

    private static SSLSocketFactory getSSLSocketFactory() { 
        MyKeyManager keyManager = new MyKeyManager(KeyStoreType.PKCS12, "d:/key.p12", "123456".toCharArray());
        MyTrustManager trustManager = new MyTrustManager("d:/trust.keystore", "123456".toCharArray());
        MySSLContext context = new MySSLContext("TLS", keyManager, trustManager);
        return context.getSSLContext().getSocketFactory(); 
    }
}
