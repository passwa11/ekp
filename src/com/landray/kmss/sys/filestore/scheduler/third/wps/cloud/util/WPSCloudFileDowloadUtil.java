package com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.util;

import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class WPSCloudFileDowloadUtil {

    private static final Logger logger = LoggerFactory.getLogger(WPSCloudFileDowloadUtil.class);
    public static InputStream getFileInputStream(String sUrl) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug(
                    "WPSCloudFileDowloadUtil.getFileInputStream下载地址：" + sUrl);
        }
        HttpsURLConnection connssl = null;
        URLConnection conn = null;
        try {
            URL url = new URL(sUrl);
            if (StringUtil.isNotNull(sUrl) && sUrl.startsWith("https")) {
                SSLContext ctx = SSLContext.getInstance("SSL");
                X509TrustManager tm = new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] xcs,
                                                   String string)
                            throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] xcs,
                                                   String string)
                            throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                };
                ctx.init(null, new TrustManager[] { tm }, null);
                SSLSocketFactory ssf = ctx.getSocketFactory();

                connssl = (HttpsURLConnection) url.openConnection();
                connssl.setConnectTimeout(5000);
                connssl.setReadTimeout(1000 * 60 * 60);
                connssl.setSSLSocketFactory(ssf);
                return connssl.getInputStream();
            } else {
                conn = url.openConnection();
                conn.connect();
                return conn.getInputStream();
            }
        } catch (Exception ex) {
            logger.error("下载出错", ex);
        }

        if (logger.isDebugEnabled()) {
            logger.debug(
                    "WPSCloudFileDowloadUtil.getFileInputStream下载转流成功");
        }
        return null;
    }
}
