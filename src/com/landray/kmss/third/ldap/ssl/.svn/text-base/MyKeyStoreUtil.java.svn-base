package com.landray.kmss.third.ldap.ssl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;

public class MyKeyStoreUtil {

    private MyKeyStoreUtil() {
    }

    public static KeyStore loadKeyStore(KeyStoreType type, String keyStore, char[] password) {
        if (type == null) {
            type = KeyStoreType.JKS;
        }
        InputStream in = null;
        try {
            try {
                KeyStore ks = type.getKeyStore();
                in = new FileInputStream(keyStore);
                ks.load(in, password);
                return ks;
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (Exception e) {
            throw new KeyStoreRuntimeException("type: " + type +
                    ", keyStore: " + keyStore, e);
        }
    }

    public static enum KeyStoreType {
        JKS {
            @Override
            public KeyStore getKeyStore() throws KeyStoreException {
                return getKeyStore("JKS");
            }
        },

        PKCS12 {
            @Override
            public KeyStore getKeyStore() throws KeyStoreException {
                return  getKeyStore("PKCS12");
            }
        };

        public abstract KeyStore getKeyStore() throws KeyStoreException ;

        public static KeyStore getKeyStore(String type) throws KeyStoreException {
            return KeyStore.getInstance(type);
        }
    }

    public static class KeyStoreRuntimeException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public KeyStoreRuntimeException(String message, Throwable cause) {
            super(message, cause);
        }

        public KeyStoreRuntimeException(Throwable cause) {
            super(cause);
        }
    }
}
