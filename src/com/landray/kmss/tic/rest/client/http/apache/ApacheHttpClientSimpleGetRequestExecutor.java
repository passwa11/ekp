package com.landray.kmss.tic.rest.client.http.apache;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.tic.rest.client.api.impl.LtpaTokenCookieProvider;
import com.landray.kmss.tic.rest.client.error.RestError;
import com.landray.kmss.tic.rest.client.error.RestErrorException;
import com.landray.kmss.tic.rest.client.error.RestErrorKeys;
import com.landray.kmss.tic.rest.client.http.RequestHttp;
import com.landray.kmss.tic.rest.client.http.SimpleGetRequestExecutor;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ApacheHttpClientSimpleGetRequestExecutor extends SimpleGetRequestExecutor<CloseableHttpClient, HttpHost,RestErrorKeys> {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	public ApacheHttpClientSimpleGetRequestExecutor(RequestHttp requestHttp) {
		super(requestHttp);
	}

	@Override
	public String execute(String uri, String queryParam)
			throws RestErrorException, IOException, URISyntaxException {
		if (queryParam != null) {
			if (uri.indexOf('?') == -1) {
				uri += '?';
			}
			uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
		}
		URL url = new URL(uri);
		// URI uriObj = new URI(url.getProtocol(), url.getHost(), url.getPath(),
		// url.getQuery(), null);
		URI uriObj = new URI(url.getProtocol(), null, url.getHost(),
				url.getPort(), url.getPath(),
				url.getQuery(), null);
		HttpGet httpGet = new HttpGet(uriObj);
		HttpClientContext context = HttpClientContext.create();

		if (requestHttp.getRequestHttpProxy() != null) {
			RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
			httpGet.setConfig(config);
		}
		
		if(StringUtil.isNotNull(requestHttp.getErrorKeys().getHeader())){
			JSONArray headers = JSONArray.fromObject(requestHttp.getErrorKeys().getHeader());
			for(int i=0;i<headers.size();i++) {
				String header = headers.getString(i);
				if(StringUtil.isNotNull(header)) {
					String[] kn = header.split(":");
					if (kn.length < 2) {
						log.error("header格式不正确，" + header);
					}
					httpGet.addHeader(kn[0], kn[1]);
				}
			}
		}
		
		CookieStore cookieStore = null;
		String key = null;
		if (StringUtil.isNotNull(requestHttp.getErrorKeys().getCookieStr())) {
			JSONArray cookies = JSONArray
					.fromObject(requestHttp.getErrorKeys().getCookieStr());
			cookieStore = new BasicCookieStore();
			for (int i = 0; i < cookies.size(); i++) {
				JSONObject cookie = cookies.getJSONObject(i);
				if (cookie.containsKey("key")) {
					key = cookie.getString("key");
				}
				BasicClientCookie basicClientCookie = new BasicClientCookie(
						cookie.getString("name"), cookie.getString("value"));
				basicClientCookie.setDomain(cookie.getString("domain"));
				if (cookie.containsKey("path")) {
					basicClientCookie.setPath(cookie.getString("path"));
				}
				log.debug(basicClientCookie.getName() + ","
						+ basicClientCookie.getValue() + ","
						+ basicClientCookie.getPath() + ","
						+ basicClientCookie.getDomain() + ","
						+ basicClientCookie.getExpiryDate());
				cookieStore.addCookie(basicClientCookie);
			}
			context.setCookieStore(cookieStore);
		}

		CloseableHttpClient httpClient = requestHttp.getRequestHttpClient();

		// try {
		// URL url2 = new URL(
		// "https://hsdop.dev.harsonserver.com/openapi/docs/erp-server/financial/mcode?state=1&fd_mcode=2");
		//
		// TrustManager[] tm = { new MyX509TrustManager() };
		// SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
		// sslContext.init(null, tm, new java.security.SecureRandom());
		//
		// SSLSocketFactory ssf = sslContext.getSocketFactory();
		//
		//
		// HttpsURLConnection httpsConn = (HttpsURLConnection) url2
		// .openConnection();
		//
		// httpsConn.setSSLSocketFactory(ssf);
		//
		// httpsConn.getResponseCode();
		// InputStreamReader insr = new InputStreamReader(
		// httpsConn.getInputStream());
		//
		// StringBuilder sb = new StringBuilder();
		// int respInt;
		// respInt = insr.read();
		// while (respInt != -1) {
		// sb.append((char) respInt);
		// respInt = insr.read();
		// }
		// String data = sb.toString();
		// System.out.print(data);
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// TrustManager[] tm = { new MyX509TrustManager() };
		// SSLContext sslContext = null;
		// try {
		// sslContext = SSLContext.getInstance("TLSv1.2");
		// sslContext.init(null, tm, new java.security.SecureRandom());
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		// sslContext,
		// new String[] { "TLSv1.2" },
		// null,
		// new NoopHostnameVerifier());
		//
		// Registry<ConnectionSocketFactory> registry = RegistryBuilder
		// .<ConnectionSocketFactory> create()
		// .register("https", sslsf)
		// .build();
		//
		// PoolingHttpClientConnectionManager connectionManager = new
		// PoolingHttpClientConnectionManager(
		// registry);
		// HttpClientBuilder httpClientBuilder = HttpClients.custom()
		// .setConnectionManager(connectionManager)
		// .setConnectionManagerShared(true)
		// .setDefaultRequestConfig(RequestConfig.custom()
		// .build());
		// httpClient = httpClientBuilder.build();

		try (CloseableHttpResponse response = httpClient
				.execute(httpGet, context)) {
			String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
			RestError error = RestError.fromJson(responseContent,requestHttp.getErrorKeys());
			if (error.hasError()) {
				throw new RestErrorException(error);
			}
			if (StringUtil.isNotNull(key) && responseContent != null
					&& (responseContent.trim().startsWith("{")
							|| responseContent.trim().startsWith("["))) {
				LtpaTokenCookieProvider.updateCookieCache(key,
						cookieStore);
			}
			// System.out.print("12345:" + responseContent);
			return responseContent;
		} finally {
			httpGet.releaseConnection();
		}
	}

}
