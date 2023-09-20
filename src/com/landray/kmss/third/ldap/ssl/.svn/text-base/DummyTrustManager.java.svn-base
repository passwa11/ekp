package com.landray.kmss.third.ldap.ssl;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;
public class DummyTrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] cert, String authType) {
        return;
    }
    
    @Override
    public void checkServerTrusted(X509Certificate[] cert, String authType) {
        return;
    }
    
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
