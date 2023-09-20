package com.landray.kmss.third.weixin.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.constant.WxConstant;
import com.landray.kmss.third.weixin.model.WeixinConfig;
import com.landray.kmss.third.weixin.model.api.WxAgent;
import com.landray.kmss.third.weixin.model.api.WxArticle;
import com.landray.kmss.third.weixin.model.api.WxDepart;
import com.landray.kmss.third.weixin.model.api.WxMenu;
import com.landray.kmss.third.weixin.model.api.WxMessage;
import com.landray.kmss.third.weixin.model.api.WxUser;
import com.landray.kmss.third.weixin.util.RandomUtils;
import com.landray.kmss.third.weixin.util.WxHttpClientUtil;

/**
 * 微信企业号API的实现类
 * 
 * @author 唐有炜
 *
 */
public class WxApiServiceImpl implements WxApiService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 全局的是否正在刷新access token的锁
	 */
	protected final Object globalAccessTokenRefreshLock = new Object();
	private static final int ACCESS_TOKEN_EXPIRESIN = 7200;
	private String accessToken = null;
	private long expiresTime = 0;
	private WeixinConfig weixinConfig;

	/**
	 * 全局的是否正在刷新jsapi ticket
	 */
	protected final Object globalJsapiTicketRefreshLock = new Object();
	private static final int JSAPI_TICKIT_EXPIRESIN = 7200;
	private long jsapiTicketExpiresTime = 0;
	private String jsapiTicket = null;

	public WxApiServiceImpl() {
		weixinConfig = WeixinConfig.newInstance();
	}

	@Override
	public String getAccessToken() throws Exception {
		return getAccessToken(false);
	}

	public String getAccessToken(boolean forceRefresh) throws Exception {
		if (forceRefresh || null == accessToken) {
			expireAccessToken();
		}
		if (isAccessTokenExpired()) {
			synchronized (globalAccessTokenRefreshLock) {
				if (isAccessTokenExpired()) {
					try {
						logger.debug("重新获取accessToken");
						String accessToken = getToken();
						updateAccessToken(accessToken, ACCESS_TOKEN_EXPIRESIN);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return this.accessToken;
	}

	private boolean isAccessTokenExpired() {
		return System.currentTimeMillis() > this.expiresTime;
	}

	public void expireAccessToken() {
		this.expiresTime = 0;
	}

	public String getToken() {
		String url = WxConstant.WX_PREFIX + "/gettoken?&corpid="
				+ weixinConfig.getWxCorpid() + "&corpsecret="
				+ weixinConfig.getWxCorpsecret();
		JSONObject json = WxHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		if (json.getIntValue("errcode") == 0) {
			return json.getString("access_token");
		} else {
			return null;
		}
	}

	public synchronized void updateAccessToken(String accessToken,
			int expiresInSeconds) {
		this.accessToken = accessToken;
		this.expiresTime = System.currentTimeMillis()
				+ (expiresInSeconds - 200) * 1000L;
	}

	@Override
	public String oauth2buildAuthorizationUrl(String redirectUri,
			String state) {
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?";
		url = String.valueOf(url) + "appid=" + weixinConfig.getWxCorpsecret();

		url = String.valueOf(url) + "&redirect_uri="
				+ URLEncoder.encode(redirectUri);
		url = String.valueOf(url) + "&response_type=code";
		url = String.valueOf(url) + "&scope=snsapi_base";
		if (state != null) {
			url = String.valueOf(url) + "&state=" + state;
		}
		return String.valueOf(url) + "#wechat_redirect";
	}

	// ====================================================
	// JsapiSignature
	// ====================================================
	private boolean isJsapiTicketExpired() {
		return System.currentTimeMillis() > this.jsapiTicketExpiresTime;
	}

	private void expireJsapiTicket() {
		this.jsapiTicketExpiresTime = 0L;
	}

	public synchronized void updateJsapiTicket(String jsapiTicket,
			int expiresInSeconds) {
		this.jsapiTicket = jsapiTicket;
		this.jsapiTicketExpiresTime = System.currentTimeMillis()
				+ (expiresInSeconds - 200) * 1000L;
	}

	@Override
	public String getJsapiTicket() throws Exception {
		return getJsapiTicket(false);
	}

	public String getJsapiTicket(boolean forceRefresh) throws Exception {
		if (forceRefresh) {
			expireJsapiTicket();
		}
		if (isJsapiTicketExpired()) {
			synchronized (globalJsapiTicketRefreshLock) {
				if (isJsapiTicketExpired()) {
					String url = WxConstant.WX_PREFIX
							+ "/get_jsapi_ticket?access_token="
							+ getAccessToken();
					JSONObject response = WxHttpClientUtil.httpGet(url,
							null,
							JSONObject.class);
					if (response.containsKey("ticket")) {
						this.jsapiTicket = response.getString("ticket");
					} else {
						this.jsapiTicket = response.toString();
					}
					updateJsapiTicket(this.jsapiTicket, JSAPI_TICKIT_EXPIRESIN);
				}
			}
		}
		return this.jsapiTicket;
	}

	@Override
	public Map<String, Object> createJsapiSignature(String url)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		long timestamp = System.currentTimeMillis() / 1000L;
		String noncestr = RandomUtils.getRandomStr(16);
		String ticket = getJsapiTicket(false);
		String signature = sign(ticket, noncestr, timestamp, url);

		resultMap.put("noncestr", noncestr);
		resultMap.put("timestamp", timestamp);
		resultMap.put("signature", signature);

		return resultMap;
	}

	public String sign(String ticket, String nonceStr, long timeStamp,
			String url) throws Exception {
		String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr
				+ "&timestamp=" + String.valueOf(timeStamp) + "&url=" + url;
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			sha1.reset();
			sha1.update(plain.getBytes("UTF-8"));
			return bytesToHex(sha1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new Exception(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new Exception(e.getMessage());
		}
	}

	private String bytesToHex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	@Override
	public String agentGet(String agentId) throws Exception {
		String url = WxConstant.WX_PREFIX + "/agent/list?access_token="
				+ getAccessToken() + "&agentid=" + agentId;
		return WxHttpClientUtil.httpGet(url, null, String.class);
	}

	@Override
	public List<WxAgent> agentList() throws Exception {
		String url = WxConstant.WX_PREFIX + "/agent/list?access_token="
				+ getAccessToken();

		List<WxAgent> agentList = new ArrayList<WxAgent>();

		JSONObject result = WxHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		if (result.getIntValue("errcode") == 0) {
			JSONArray jsonArr = result.getJSONArray("agentlist");
			agentList = JSONObject.parseArray(jsonArr.toJSONString(),
					WxAgent.class);
		}
		return agentList;
	}

	// =============================
	// SSO
	// =============================
	@Override
	public JSONObject getLoginInfo(String authCode) throws Exception {
		String url = WxConstant.WX_PREFIX
				+ "service/get_login_info?access_token="
				+ getAccessToken();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("authCode", "authCode");
		JSONObject result = WxHttpClientUtil.httpPost(url, paramMap, null,
				JSONObject.class);
		return result;
	}

	@Override
	public JSONObject oauth2getUserInfo(String code)
			throws Exception {
		String url = WxConstant.WX_PREFIX
				+ "/user/getuserinfo?access_token="
				+ getAccessToken() + "&code=" + code;
		JSONObject result = WxHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		return result;
	}

	// =================================================
	// 应用菜单
	// ==================================================
	@Override
	public void menuDelete(String agentId) throws Exception {
		String url = WxConstant.WX_PREFIX + "/menu/delete?access_token="
				+ getAccessToken() + "&agentid=" + agentId;
		WxHttpClientUtil.httpGet(url, null, null);
	}

	@Override
	public void menuCreate(String agentId, WxMenu menu) throws Exception {
		String url = WxConstant.WX_PREFIX + "/menu/create?access_token="
				+ getAccessToken() + "&agentid=" + agentId;
		WxHttpClientUtil.httpPost(url, menu, null, null);
	}

	// =================================
	// 组织架构同步
	// =================================
	@Override
	public List<WxUser> userList(Long departId, Boolean fetchChild,
			Integer status) throws Exception {
		String url = WxConstant.WX_PREFIX + "/user/list?access_token="
				+ getAccessToken() + "&department_id=" + departId;
		if (fetchChild != null) {
			url = String.valueOf(url) + "&fetch_child="
					+ (fetchChild.booleanValue() ? "1" : "0");
		}
		if (status != null) {
			url = String.valueOf(url) + "&status=" + status;
		} else {
			url = String.valueOf(url) + "&status=0";
		}

		List<WxUser> userList = new ArrayList<WxUser>();

		JSONObject result = WxHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		if (result.getIntValue("errcode") == 0) {
			JSONArray jsonArr = result.getJSONArray("userlist");
			userList = JSONObject.parseArray(jsonArr.toJSONString(),
					WxUser.class);
		}
		return userList;
	}

	@Override
	public String userGet(String userid) throws Exception {
		String url = WxConstant.WX_PREFIX + "/user/get?access_token="
				+ getAccessToken() + "&userid=" + userid;
		String result = WxHttpClientUtil.httpGet(url, null, String.class);
		return result;
	}

	@Override
	public JSONObject userCreate(WxUser wxuser) throws Exception {
		String url = WxConstant.WX_PREFIX + "/user/create?access_token="
				+ getAccessToken();
		JSONObject result = WxHttpClientUtil.httpPost(url, wxuser, null,
				JSONObject.class);
		return result;
	}

	@Override
	public JSONObject userDelete(String userid) throws Exception {
		String url = WxConstant.WX_PREFIX + "/user/delete?access_token="
				+ getAccessToken() + "&userid=" + userid;
		JSONObject result = WxHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		return result;
	}

	@Override
	public JSONObject userUpdate(WxUser wxuser) throws Exception {
		String url = WxConstant.WX_PREFIX + "/user/update?access_token="
				+ getAccessToken();
		JSONObject result = WxHttpClientUtil.httpPost(url, wxuser, null,
				JSONObject.class);
		return result;
	}

	@Override
	public List<WxDepart> departGet() throws Exception {
		String url = WxConstant.WX_PREFIX
				+ "/department/list?access_token="
				+ getAccessToken();

		List<WxDepart> departList = new ArrayList<WxDepart>();

		JSONObject result = WxHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		if (result.getIntValue("errcode") == 0) {
			JSONArray jsonArr = result.getJSONArray("department");
			departList = JSONObject.parseArray(jsonArr.toJSONString(),
					WxDepart.class);
		}
		return departList;
	}

	@Override
	public List<WxDepart> departGet(String departId) throws Exception {
		String url = WxConstant.WX_PREFIX
				+ "/department/list?access_token="
				+ getAccessToken() + "&id=" + departId;

		List<WxDepart> departList = new ArrayList<WxDepart>();

		JSONObject result = WxHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		if (result.getIntValue("errcode") == 0) {
			JSONArray jsonArr = result.getJSONArray("department");
			departList = JSONObject.parseArray(jsonArr.toJSONString(),
					WxDepart.class);
		}
		return departList;
	}

	@Override
	public JSONObject departUpdate(WxDepart dept) throws Exception {
		String url = WxConstant.WX_PREFIX
				+ "/department/update?access_token="
				+ getAccessToken();
		JSONObject result = WxHttpClientUtil.httpPost(url, dept, null,
				JSONObject.class);
		return result;
	}

	@Override
	public JSONObject departDelete(Long departId) throws Exception {
		String url = WxConstant.WX_PREFIX
				+ "/department/delete?access_token="
				+ getAccessToken() + "&id=" + departId;
		JSONObject result = WxHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		return result;
	}

	@Override
	public JSONObject departCreate(WxDepart dept) throws Exception {
		String url = WxConstant.WX_PREFIX
				+ "/department/create?access_token="
				+ getAccessToken();
		JSONObject result = WxHttpClientUtil.httpPost(url, dept, null,
				JSONObject.class);
		return result;
	}

	// ==================================================
	// 待办、待阅、机器人节点
	// ==================================================
	@Override
	public JSONObject messageSend(WxMessage msg) throws Exception {
		String url = WxConstant.WX_PREFIX
				+ "/message/send?access_token="
				+ getAccessToken();

		WxMessage paramObj = null;
		List<WxArticle> ars = msg.getArticles();
		if (CollectionUtils.isNotEmpty(ars)) {
			WxMessage newsMsg = new WxMessage();
			Map<String, Object> newsMap = new HashMap<String, Object>();
			newsMap.put("articles", ars);

			newsMsg.setAgentId(msg.getAgentId());
			newsMsg.setToUser(msg.getToUser());
			newsMsg.setToTag(msg.getToTag());
			newsMsg.setToParty(msg.getToParty());
			newsMsg.setMsgType(WxConstant.CUSTOM_MSG_NEWS);
			newsMsg.setNews(newsMap);

			paramObj = newsMsg;
		}

		JSONObject result = WxHttpClientUtil.httpPost(url, paramObj, null,
				JSONObject.class);
		return result;
	}
}
