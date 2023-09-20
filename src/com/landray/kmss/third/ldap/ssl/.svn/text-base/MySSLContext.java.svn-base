package com.landray.kmss.third.ldap.ssl;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class MySSLContext {

    private String protocol;
    private MyKeyManager keyManager;
    private MyTrustManager trustManager;

    public MySSLContext(String protocol, MyKeyManager keyManager, MyTrustManager trustManager) {
        this.protocol = protocol;
        this.keyManager = keyManager;
        this.trustManager = trustManager; 
    }

    public MySSLContext(String protocol, MyTrustManager trustManager) {
        this(protocol, null, trustManager);
    }

    public MySSLContext(String protocol, MyKeyManager keyManager) {
        this(protocol, keyManager, null);
    }

    public SSLContext getSSLContext() {
        try {
            SSLContext context = SSLContext.getInstance(protocol);
            context.init(getKeyManagers(), getTrustManagers(), null);
            return context;
        } catch (Exception e) {
            throw new IllegalStateException("error, protocol: " + protocol, e);
        }
    }

    private KeyManager[] getKeyManagers() {
        if (keyManager == null) {
            return null;
        }
        return keyManager.getKeyManagers();
    }

    private TrustManager[] getTrustManagers() {
        if (trustManager == null) {
            return null;
        }
        return trustManager.getTrustManagers();
    }
}
