package com.landray.kmss.third.ding.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.alibaba.druid.util.StringUtils;
import com.taobao.api.Constants;
import com.taobao.api.FileItem;

/**
 * 网络工具类。
 * 
 * @author carver.gu
 * @since 1.0, Sep 12, 2009
 */
public abstract class ThirdDingWebV2Utils {

	private static final String DEFAULT_CHARSET = Constants.CHARSET_UTF8;
	private static boolean ignoreSSLCheck = true; // 忽略SSL检查
	private static boolean ignoreHostCheck = true; // 忽略HOST检查

	public static class TrustAllTrustManager implements X509TrustManager {
		@Override
        public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		@Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}
	}

	private ThirdDingWebV2Utils() {
	}

	public static void setIgnoreSSLCheck(boolean ignoreSSLCheck) {
		ThirdDingWebV2Utils.ignoreSSLCheck = ignoreSSLCheck;
	}

	public static void setIgnoreHostCheck(boolean ignoreHostCheck) {
		ThirdDingWebV2Utils.ignoreHostCheck = ignoreHostCheck;
	}

	/**
	 * 执行带文件上传的HTTP POST请求。
	 *
	 * @param url
	 *            请求地址
	 * @param fileParams
	 *            文件请求参数
	 * @return 响应字符串
	 */
	public static ThirdDingHttpResponseData doPost(String url,
			Map<String, String> params, Map<String, FileItem> fileParams,
			int connectTimeout, int readTimeout, Proxy proxy)
			throws IOException {

		return doPost(url, params, fileParams, DEFAULT_CHARSET, connectTimeout,
				readTimeout, proxy);

	}

	public static ThirdDingHttpResponseData doPost(String url,
			Map<String, String> params, Map<String, FileItem> fileParams,
			String charset, int connectTimeout, int readTimeout, Proxy proxy)
			throws IOException {
		return doPost(url, params, fileParams, charset, connectTimeout,
				readTimeout, null, proxy);
	}

	/**
	 * 执行带文件上传的HTTP POST请求。
	 *
	 * @param url
	 *            请求地址
	 * @param fileParams
	 *            文件请求参数
	 * @param charset
	 *            字符集，如UTF-8, GBK, GB2312
	 * @param headerMap
	 *            需要传递的header头，可以为空
	 * @return 响应字符串
	 */
	public static ThirdDingHttpResponseData doPost(String url,
			Map<String, String> params, Map<String, FileItem> fileParams,
			String charset,
			int connectTimeout, int readTimeout, Map<String, String> headerMap,
			Proxy proxy)
			throws IOException {

		return _doPostWithFile(url, params, fileParams, charset, connectTimeout,
				readTimeout, headerMap, proxy);

	}

	private static ThirdDingHttpResponseData _doPostWithFile(String url,
			Map<String, String> params, Map<String, FileItem> fileParams,
			String charset, int connectTimeout, int readTimeout,
			Map<String, String> headerMap, Proxy proxy) throws IOException {
		String boundary = String.valueOf(System.nanoTime()); // 随机分隔线
		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		ThirdDingHttpResponseData data = new ThirdDingHttpResponseData();
		try {
			String ctype = "multipart/form-data;charset=" + charset
					+ ";boundary=" + boundary;
			conn = getConnection(new URL(url), Constants.METHOD_POST, ctype,
					headerMap, proxy);
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);
			out = conn.getOutputStream();
			byte[] entryBoundaryBytes = ("\r\n--" + boundary + "\r\n")
					.getBytes(charset);

			// 组装文本请求参数
			Set<Entry<String, String>> textEntrySet = params.entrySet();
			for (Entry<String, String> textEntry : textEntrySet) {
				byte[] textBytes = getTextEntry(textEntry.getKey(),
						textEntry.getValue(), charset);
				out.write(entryBoundaryBytes);
				out.write(textBytes);
			}

			// 组装文件请求参数
			Set<Entry<String, FileItem>> fileEntrySet = fileParams.entrySet();
			for (Entry<String, FileItem> fileEntry : fileEntrySet) {
				FileItem fileItem = fileEntry.getValue();
				if (!fileItem.isValid()) {
					throw new IOException("FileItem is invalid");
				}
				byte[] fileBytes = getFileEntry(fileEntry.getKey(),
						fileItem.getFileName(), fileItem.getMimeType(),
						charset);
				out.write(entryBoundaryBytes);
				out.write(fileBytes);
				fileItem.write(out);
			}

			// 添加请求结束标志
			byte[] endBoundaryBytes = ("\r\n--" + boundary + "--\r\n")
					.getBytes(charset);
			out.write(endBoundaryBytes);
			rsp = getResponseAsString(conn);
			data.setBody(rsp);
			data.setHeaders(conn.getHeaderFields());
		} finally {
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}

		return data;
	}

	private static byte[] getTextEntry(String fieldName, String fieldValue,
			String charset) throws IOException {
		StringBuilder entry = new StringBuilder();
		entry.append("Content-Disposition:form-data;name=\"");
		entry.append(fieldName);
		entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
		entry.append(fieldValue);
		return entry.toString().getBytes(charset);
	}

	private static byte[] getFileEntry(String fieldName, String fileName,
			String mimeType, String charset) throws IOException {
		StringBuilder entry = new StringBuilder();
		entry.append("Content-Disposition:form-data;name=\"");
		entry.append(fieldName);
		entry.append("\";filename=\"");
		entry.append(fileName);
		entry.append("\"\r\nContent-Type:");
		entry.append(mimeType);
		entry.append("\r\n\r\n");
		return entry.toString().getBytes(charset);
	}

	private static HttpURLConnection getConnection(URL url, String method,
			String ctype, Map<String, String> headerMap, Proxy proxy)
			throws IOException {
		HttpURLConnection conn = null;
		if (proxy == null) {
			conn = (HttpURLConnection) url.openConnection();
		} else {
			conn = (HttpURLConnection) url.openConnection(proxy);
		}
		if (conn instanceof HttpsURLConnection) {
			HttpsURLConnection connHttps = (HttpsURLConnection) conn;
			if (ignoreSSLCheck) {
				try {
					SSLContext ctx = SSLContext.getInstance("TLS");
					ctx.init(null,
							new TrustManager[] { new TrustAllTrustManager() },
							new SecureRandom());
					connHttps.setSSLSocketFactory(ctx.getSocketFactory());
					connHttps.setHostnameVerifier(new HostnameVerifier() {
						@Override
                        public boolean verify(String hostname,
                                              SSLSession session) {
							return true;
						}
					});
				} catch (Exception e) {
					throw new IOException(e.toString());
				}
			} else {
				if (ignoreHostCheck) {
					connHttps.setHostnameVerifier(new HostnameVerifier() {
						@Override
                        public boolean verify(String hostname,
                                              SSLSession session) {
							return true;
						}
					});
				}
			}
			conn = connHttps;
		}

		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		if (headerMap != null
				&& headerMap.get(Constants.TOP_HTTP_DNS_HOST) != null) {
			conn.setRequestProperty("Host",
					headerMap.get(Constants.TOP_HTTP_DNS_HOST));
		} else {
			conn.setRequestProperty("Host", url.getHost());
		}
		conn.setRequestProperty("Accept", "text/xml,text/javascript");
		conn.setRequestProperty("User-Agent", "top-sdk-java");
		conn.setRequestProperty("Content-Type", ctype);
		if (headerMap != null) {
			for (Entry<String, String> entry : headerMap.entrySet()) {
				if (!Constants.TOP_HTTP_DNS_HOST.equals(entry.getKey())) {
					conn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
		}
		return conn;
	}

	protected static String getResponseAsString(HttpURLConnection conn)
			throws IOException {
		String charset = getResponseCharset(conn.getContentType());
		if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
			String contentEncoding = conn.getContentEncoding();
			if (Constants.CONTENT_ENCODING_GZIP
					.equalsIgnoreCase(contentEncoding)) {
				return getStreamAsString(
						new GZIPInputStream(conn.getInputStream()), charset);
			} else {
				return getStreamAsString(conn.getInputStream(), charset);
			}
		} else {
			// OAuth bad request always return 400 status
			if (conn.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
				InputStream error = conn.getErrorStream();
				if (error != null) {
					return getStreamAsString(error, charset);
				}
			}
			// Client Error 4xx and Server Error 5xx
			throw new IOException(
					conn.getResponseCode() + " " + conn.getResponseMessage());
		}
	}

	public static String getStreamAsString(InputStream stream, String charset)
			throws IOException {
		try {
			Reader reader = new InputStreamReader(stream, charset);
			StringBuilder response = new StringBuilder();

			final char[] buff = new char[1024];
			int read = 0;
			while ((read = reader.read(buff)) > 0) {
				response.append(buff, 0, read);
			}

			return response.toString();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	public static String getResponseCharset(String ctype) {
		String charset = DEFAULT_CHARSET;

		if (!StringUtils.isEmpty(ctype)) {
			String[] params = ctype.split(";");
			for (String param : params) {
				param = param.trim();
				if (param.startsWith("charset")) {
					String[] pair = param.split("=", 2);
					if (pair.length == 2) {
						if (!StringUtils.isEmpty(pair[1])) {
							charset = pair[1].trim();
						}
					}
					break;
				}
			}
		}

		return charset;
	}

	/**
	 * 使用默认的UTF-8字符集反编码请求参数值。
	 * 
	 * @param value
	 *            参数值
	 * @return 反编码后的参数值
	 */
	public static String decode(String value) {
		return decode(value, DEFAULT_CHARSET);
	}

	/**
	 * 使用默认的UTF-8字符集编码请求参数值。
	 * 
	 * @param value
	 *            参数值
	 * @return 编码后的参数值
	 */
	public static String encode(String value) {
		return encode(value, DEFAULT_CHARSET);
	}

	/**
	 * 使用指定的字符集反编码请求参数值。
	 * 
	 * @param value
	 *            参数值
	 * @param charset
	 *            字符集
	 * @return 反编码后的参数值
	 */
	public static String decode(String value, String charset) {
		String result = null;
		if (!StringUtils.isEmpty(value)) {
			try {
				result = URLDecoder.decode(value, charset);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	/**
	 * 使用指定的字符集编码请求参数值。
	 * 
	 * @param value
	 *            参数值
	 * @param charset
	 *            字符集
	 * @return 编码后的参数值
	 */
	public static String encode(String value, String charset) {
		String result = null;
		if (!StringUtils.isEmpty(value)) {
			try {
				result = URLEncoder.encode(value, charset);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	/**
	 * 从URL中提取所有的参数。
	 * 
	 * @param query
	 *            URL地址
	 * @return 参数映射
	 */
	public static Map<String, String> splitUrlQuery(String query) {
		Map<String, String> result = new HashMap<String, String>();

		String[] pairs = query.split("&");
		if (pairs != null && pairs.length > 0) {
			for (String pair : pairs) {
				String[] param = pair.split("=", 2);
				if (param != null && param.length == 2) {
					result.put(param[0], param[1]);
				}
			}
		}

		return result;
	}

}
