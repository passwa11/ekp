package com.landray.kmss.sys.attachment.integrate.wps.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.util.StringUtil;

public class FileDowloadUtil {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(FileDowloadUtil.class);

	public static InputStream getFileInputStream(String sUrl) throws Exception {
		if (logger.isDebugEnabled()) {
            logger.debug(
                    "ThirdWpsWebhookServiceImp.getFileInputStream下载地址：" + sUrl);
        }
		InputStream is = null;
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
				is = connssl.getInputStream();
			} else {
				conn = url.openConnection();
				conn.connect();
				is = conn.getInputStream();
			}
		} catch (Exception ex) {
			logger.error("下载出错", ex);
			if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    throw  e;
                }
            }
			throw  ex;
		}

		if (logger.isDebugEnabled()) {
            logger.debug(
                    "ThirdWpsWebhookServiceImp.getFileInputStream下载转流成功");
        }
		return is;
	}
}
