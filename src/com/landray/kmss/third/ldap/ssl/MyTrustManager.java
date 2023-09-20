package com.landray.kmss.third.ldap.ssl;

import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import com.landray.kmss.third.ldap.ssl.MyKeyStoreUtil.KeyStoreRuntimeException;
import com.landray.kmss.third.ldap.ssl.MyKeyStoreUtil.KeyStoreType;

public class MyTrustManager {

    private KeyStore ks;

    public MyTrustManager(String keyStore, char[] password) {
        this(KeyStoreType.JKS, keyStore, password);
    }

    public MyTrustManager(KeyStoreType type, String keyStore, char[] password) {
        this.ks = MyKeyStoreUtil.loadKeyStore(type, keyStore, password);
    }

    public TrustManager[] getTrustManagers() {
        return new TrustManager[]{ new ClientTrustManager() };
    }

    private class ClientTrustManager implements X509TrustManager {
        private X509TrustManager sunJSSEX509TrustManager;

        public ClientTrustManager() {
            loadTrust();
        }

        private void loadTrust() {
            try {
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init(ks);
                TrustManager[] tms = tmf.getTrustManagers();
                for (int i = 0; i < tms.length; i++) {
                    if (tms[i] instanceof X509TrustManager) {
                        sunJSSEX509TrustManager = (X509TrustManager) tms[i];
                        return;
                    }
                }
            } catch (Exception e) {
                throw new KeyStoreRuntimeException(e);
            }
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            sunJSSEX509TrustManager.checkClientTrusted(chain, authType);
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            sunJSSEX509TrustManager.checkServerTrusted(chain, authType);
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return sunJSSEX509TrustManager.getAcceptedIssuers();
        }
    }
}
