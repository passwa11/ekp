package com.landray.kmss.tic.rest.client.api.impl;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.bouncycastle.util.encoders.Base64;
import org.json.simple.JSONArray;

import com.google.common.base.Splitter;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.authentication.ssoclient.Logger;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.tic.rest.client.api.ICookieProvider;
import com.landray.kmss.tic.rest.connector.model.TicRestMain;
import com.landray.kmss.tic.rest.connector.service.ITicRestMainService;
import com.landray.kmss.util.SpringBeanUtil;


public class LtpaTokenCookieProvider implements ICookieProvider {

	private static final byte[] HEADER = new byte[] { 0, 1, 2, 3 };

	private static final long maxAge = 24L * 3600000L;

	private ITicRestMainService ticRestMainService;

	private static Map<String, JSONArray> cookies = new ConcurrentHashMap<String, JSONArray>();

	@Override
	public String getCookies(String restMainId, JSONObject in, String url)
			throws Exception {
		// TODO Auto-generated method stub
		// [{"domain":"java.landray.com.cn","name":"route","path":"/","value":"40217fab86629be630f69fd2ef4aab3c"}.{"domain":"java.landray.com.cn","name":"JSESSIONID","path":"/","value":"15A281143604B2D847A0A28B80DF46E4"}]
		if ((in == null && StringUtil.isNull(url))
				|| StringUtil.isNull(restMainId)) {
			return null;
		}
		String loginName = null;
		if (in != null && in.containsKey("LoginName")) {
			loginName = in.getString("LoginName");
		} else {
			loginName = getParam(url, "LoginName");
		}

		TicRestMain ticRestMain = (TicRestMain) getTicRestMainService()
				.findByPrimaryKey(restMainId, null, true);
		if (ticRestMain == null) {
			return null;
		}

		// String url = ticRestMain.getFdReqURL();
		String domain = getDomain(url);

		String enviromentId = ticRestMain.getFdEnviromentId();
		if (StringUtil.isNull(enviromentId)) {
			return null;
		}

		IBaseModel enviroment = (IBaseModel) getTicRestMainService()
				.findByPrimaryKey(enviromentId,
						"com.landray.kmss.tic.scene.model.TicSceneEnvironmentInfo",
						true);
		if (enviroment == null) {
			return null;
		}
		String fdParamData = (String) PropertyUtils.getProperty(enviroment,
				"fdParamData");
		JSONObject param = JSONObject.parseObject(fdParamData);
		if (!param.containsKey("REST")) {
			return null;
		}
		JSONObject restPara = param.getJSONObject("REST");
		if (!restPara.containsKey("cookieName")) {
			return null;
		}

		String cookieName = restPara.getString("cookieName");
		String cookieSecret = restPara.getString("cookieSecret");

		String key = enviromentId + "-" + cookieName + "-" + cookieSecret + "-"
				+ loginName;
		JSONArray cookieArray = cookies.get(key);
		if (cookieArray != null) {
			if (checkExpire(cookieArray, cookieName)) {
				JSONArray array = genCookieArray(key, domain, cookieName,
						loginName,
						cookieSecret);
				cookies.put(key, array);
				return array.toJSONString();
			} else {
				return cookieArray.toJSONString();
			}
		}

		JSONArray array = genCookieArray(key, domain, cookieName, loginName,
				cookieSecret);
		cookies.put(key, array);
		return array.toJSONString();
	}

	private JSONArray genCookieArray(String key, String domain,
			String cookieName,
			String loginName, String cookieSecret) {
		String ltpaToken = generateTokenByUserName(loginName, cookieSecret);
		JSONArray array = new JSONArray();
		JSONObject o = new JSONObject();
		o.put("key", key);
		o.put("domain", domain);
		o.put("name", cookieName);
		o.put("path", "/");
		o.put("value", ltpaToken);
		o.put("expire", System.currentTimeMillis() + maxAge);
		array.add(o);
		return array;
	}

	private boolean checkExpire(JSONArray cookieArray, String cookieName) {
		for (int i = 0; i < cookieArray.size(); i++) {
			JSONObject o = (JSONObject) cookieArray.get(i);
			if (o.getString("name").equals(cookieName)) {
				long expire = o.getLong("expire");
				if ((System.currentTimeMillis() + 100000L) < expire) {
					return false;
				}
			}
		}
		return true;
	}

	public ITicRestMainService getTicRestMainService() {
		if (ticRestMainService == null) {
			ticRestMainService = (ITicRestMainService) SpringBeanUtil
					.getBean("ticRestMainService");
		}
		return ticRestMainService;
	}

	public void setTicRestMainService(ITicRestMainService ticRestMainService) {
		this.ticRestMainService = ticRestMainService;
	}

	public String generateTokenByUserName(String username, String securityKey) {
		if (StringUtil.isNull(username)) {
			return null;
		}
		try {
			// token格式：4个头字符+8个创建时间字符+8个过期时间字符+若干个用户名字符+20个验证码
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT0"));
			long createTime = cal.getTimeInMillis() / 1000;

			long expireTime = createTime + maxAge;
			byte[] create = Long.toHexString(createTime).toUpperCase()
					.getBytes();
			byte[] expire = Long.toHexString(expireTime).toUpperCase()
					.getBytes();
			// byte[] user =
			// ((this.getUserKey()==null?"":this.getUserKey())+"="+username+"/cn=users/dc=xiamenAir/dc=com").getBytes();
			byte[] user = username.getBytes("UTF-8");

			byte[] bytes = new byte[20 + user.length];
			System.arraycopy(HEADER, 0, bytes, 0, 4);
			System.arraycopy(create, 0, bytes, 4, 8);
			System.arraycopy(expire, 0, bytes, 12, 8);
			System.arraycopy(user, 0, bytes, 20, user.length);

			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(bytes);

			byte[] digest = md.digest(Base64.decode(securityKey));

			byte[] token = new byte[bytes.length + digest.length];
			System.arraycopy(bytes, 0, token, 0, bytes.length);
			System.arraycopy(digest, 0, token, bytes.length, digest.length);

			String tokenString = new String(Base64.encode(token), "UTF-8");
			return tokenString;
		} catch (Exception e) {
			// System.out.println("加密Token信息发生错误：" + e.getMessage());
			Logger.warn("加密Token信息发生错误：" + username, e);
			return null;
		}
	}

	private String getPath(String url) {
		url = url.trim().replace("http://", "").replace("https://", "");
		if (url.contains("/")) {
			url = url.substring(0, url.indexOf("/") - 1);
		}
		if (url.contains(":")) {
			url = url.substring(0, url.indexOf(":") - 1);
		}
		return url;
	}

	private String getDomain(String url) {
		url = url.trim().replace("http://", "").replace("https://", "");
		if (url.contains("/")) {
			url = url.substring(0, url.indexOf("/"));
		}
		if (url.contains(":")) {
			url = url.substring(0, url.indexOf(":"));
		}
		return url;
	}

	public static void clearCookieCache() {
		cookies.clear();
	}

	public static void updateCookieCache(String key, CookieStore cookieStore) {
		List<Cookie> cookieList = cookieStore.getCookies();
		String sessionId = null;
		Cookie JSESSIONID_cookie = null;
		for (Cookie k : cookieList) {
			if ("JSESSIONID".equals(k.getName())) {
				sessionId = k.getValue();
				JSESSIONID_cookie = k;
				break;
			}
		}
		boolean isNeedUpdateCookie = false;
		JSONArray cookieArray = cookies.get(key);
		// 判断如果cookieJo没有JSESSIONID说明是第一次获取,还没存进缓存中
		if (cookieArray.size() == 1) {
			isNeedUpdateCookie = true;
		} else {
			// 判断如果有sessionId则判断如返回的sessionId是否一样,不一样则需要重新将缓存更新
			JSONObject cookieObj = (JSONObject) cookieArray.get(1);
			String sessionIdOld = cookieObj.getString("value");
			if (sessionId != null && !sessionId.equals(sessionIdOld)) {
				isNeedUpdateCookie = true;
				cookieArray.remove(1);
			}
		}
		if (isNeedUpdateCookie) {
			JSONObject o = new JSONObject();
			o.put("key", key);
			o.put("domain", JSESSIONID_cookie.getDomain());
			o.put("name", "JSESSIONID");
			o.put("path", "/");
			o.put("value", sessionId);
			cookieArray.add(o);

		}
	}

	public static String getParam(String url, String name) {
		String params = url.substring(url.indexOf("?") + 1, url.length());
		Map<String, String> split = Splitter.on("&").withKeyValueSeparator("=")
				.split(params);
		return split.get(name);
	}

}
