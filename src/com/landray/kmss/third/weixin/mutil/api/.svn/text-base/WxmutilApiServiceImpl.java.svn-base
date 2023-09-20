package com.landray.kmss.third.weixin.mutil.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.mutil.constant.WxmutilConstant;
import com.landray.kmss.third.weixin.mutil.model.WeixinMutilConfig;
import com.landray.kmss.third.weixin.mutil.model.api.*;
import com.landray.kmss.third.weixin.mutil.service.IThirdWeixinWorkService;
import com.landray.kmss.third.weixin.mutil.util.WxmutilUtils;
import com.landray.kmss.third.weixin.work.util.RandomUtils;
import com.landray.kmss.third.weixin.work.util.WxworkHttpClientUtil;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 多企业微信API的实现类
 * 
 * @author 唐有炜
 *
 */
public class WxmutilApiServiceImpl implements WxmutilApiService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 全局的是否正在刷新access token的锁
	 */
	protected final Object globalAccessTokenRefreshLock = new Object();
	private static final int ACCESS_TOKEN_EXPIRESIN = 7200;
	Map<String, Object> accessTokenMap = new ConcurrentHashMap();
	private IThirdWeixinWorkService thirdWeixinWorkService;
	private WeixinMutilConfig weixinMutilConfig;

	/**
	 * 全局的是否正在刷新jsapi ticket
	 */
	protected final Object globalJsapiTicketRefreshLock = new Object();
	private static final int JSAPI_TICKIT_EXPIRESIN = 7200;
	private long jsapiTicketExpiresTime = 0;
	private String jsapiTicket = null;

	/**
	 * 多企业模式，指定配置
	 */
	public WxmutilApiServiceImpl(WeixinMutilConfig weixinMutilConfig) {
		this.weixinMutilConfig = weixinMutilConfig;
	}

	public IThirdWeixinWorkService getThirdWeixinWorkService() {
		if (null == thirdWeixinWorkService) {
			thirdWeixinWorkService = (IThirdWeixinWorkService) SpringBeanUtil
					.getBean("thirdMutilWeixinWorkService");
		}
		return thirdWeixinWorkService;
	}

	public void setThirdWeixinWorkService(
			IThirdWeixinWorkService thirdWeixinWorkService) {
		this.thirdWeixinWorkService = thirdWeixinWorkService;
	}

	@Override
	public String getAccessToken() throws Exception {
		return getAccessTokenByAgentid("orgId_" + weixinMutilConfig.getWxKey());
	}

	@Override
	public String getTodoAccessToken() throws Exception {
		String todoAgentId = weixinMutilConfig.getWxAgentid();
		return getAccessTokenByAgentid(todoAgentId);
	}

	@Override
	public String getToReadAccessToken() throws Exception {
		String toReadAgentId = weixinMutilConfig.getWxToReadAgentid();
		return getAccessTokenByAgentid(
				toReadAgentId + "_" + weixinMutilConfig.getWxKey());
	}

	@Override
	public String getAccessTokenByAgentid(String agentId) throws Exception {
		return getAccessToken(false, agentId);
	}

	public String getAccessToken(boolean forceRefresh, String agentId)
			throws Exception {

		// logger.debug("获取token应用：" + agentId);
		if (StringUtil.isNull(agentId)) {
			return null;
		}

		if (forceRefresh || null == accessTokenMap.get(agentId)) {
			expireAccessToken(agentId);
		}

		if (isAccessTokenExpired(agentId)) {
			synchronized (globalAccessTokenRefreshLock) {
				if (isAccessTokenExpired(agentId)) {
					try {
						logger.debug("重新获取accessToken,agentId=" + agentId);
						String accessToken = getToken(agentId);
						updateAccessToken(agentId, accessToken,
								ACCESS_TOKEN_EXPIRESIN);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		}

		AccessToken accessToken = (AccessToken) accessTokenMap.get(agentId);
		return accessToken.getAccessToken();
	}

	private boolean isAccessTokenExpired(String agentId) {
		if (null == accessTokenMap.get(agentId)) {
			return true;
		}
		AccessToken accessToken = (AccessToken) accessTokenMap.get(agentId);
		return System.currentTimeMillis() > accessToken.getExpiresTime();
	}

	public String getToken(String agentId) {
		String corpSecret = weixinMutilConfig.getWxCorpsecret();
		String key = weixinMutilConfig.getWxKey();
		logger.debug("key:" + key);
        logger.debug("获取token的agentId:"+agentId);
		if (!agentId.startsWith("orgId_")) { // 组织架构的token是以orgId开头 如：
												// orgId_wx_1
			// 应用后面有标识 1000002_wx_1
			if (agentId.contains("_")) {
				agentId = agentId.substring(0, agentId.indexOf("_"));
				//// System.out.println("截取的应用：" + agentId);
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("fdSecret");
			hqlInfo.setWhereBlock("fdAgentid=:fdAgentid and fdWxKey=:fdWxKey");
			hqlInfo.setParameter("fdAgentid", agentId);
			hqlInfo.setParameter("fdWxKey", weixinMutilConfig.getWxKey());
			try {
				String fdSecret = (String) getThirdWeixinWorkService()
						.findFirstOne(hqlInfo);
				if (StringUtils.isNoneBlank(fdSecret)) {
					corpSecret = fdSecret;
				}
			} catch (Exception e) {
				logger.error("未找到该应用配置信息，请先在企业微信后台配置应用", e);
				return null;
			}
		} else {
			// 组织架构token获取
			key = agentId.substring(agentId.indexOf("_") + 1);
			// System.out.println("截取处理的key:" + key);
			corpSecret = WeixinMutilConfig.newInstance(key).getWxCorpsecret();
		}

		String url = WxmutilUtils.getWxworkApiUrl(weixinMutilConfig.getWxKey())
				+ "/gettoken?corpid="
				+ weixinMutilConfig.getWxCorpid() + "&corpsecret="
				+ corpSecret;
		logger.debug("获取token的url:" + url);
		JSONObject json = WxworkHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		if (json.getIntValue("errcode") == 0) {
			return json.getString("access_token");
		} else {
			return null;
		}
	}

	public synchronized void updateAccessToken(String agentId,
			String accessToken,
			int expiresInSeconds) {
		AccessToken accessTokenObj = (AccessToken) accessTokenMap.get(agentId);
		// 如果是第一次请求，AccessToken不存在需要新建
		if (null == accessTokenObj) {
			accessTokenObj = new AccessToken();
		}
		accessTokenObj.setAccessToken(accessToken);
		accessTokenObj.setExpiresTime(
				System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L);
		// System.out.println("新建了一个token哦！！！agentId:" + agentId);
		this.accessTokenMap.put(agentId, accessTokenObj);
	}

	public void expireAccessToken(String agentId) {
		if (null != accessTokenMap.get(agentId)) {
			AccessToken accessToken = (AccessToken) accessTokenMap.get(agentId);
			accessToken.setExpiresTime(0);
		}
	}

	@Override
	public String oauth2buildAuthorizationUrl(String redirectUri,
			String state) {
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?";
		url = String.valueOf(url) + "appid=" + weixinMutilConfig.getWxCorpid();

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
					String url = WxmutilUtils
							.getWxworkApiUrl(weixinMutilConfig.getWxKey())
							+ "/get_jsapi_ticket?access_token="
							+ getAccessToken();
					JSONObject response = WxworkHttpClientUtil.httpGet(url,
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

	// =============================================================
	// 组织架构
	// =============================================================
	@Override
	public List<WxUser> userList(Long departId, Boolean fetchChild,
			Integer status) throws Exception {
		String url = WxmutilUtils.getWxworkApiUrl(weixinMutilConfig.getWxKey())
				+ "/user/list?access_token="
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

		JSONObject result = WxworkHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		if (result.getIntValue("errcode") == 0) {
			JSONArray jsonArr = result.getJSONArray("userlist");
			userList = JSONObject.parseArray(jsonArr.toJSONString(),
					WxUser.class);
		}
		return userList;
	}

	@Override
	public JSONObject userDelete(String userid) throws Exception {
		String url = WxmutilUtils.getWxworkApiUrl(weixinMutilConfig.getWxKey())
				+ "/user/delete?access_token="
				+ getAccessToken() + "&userid=" + userid;
		JSONObject result = WxworkHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		return result;
	}

	@Override
	public JSONObject userUpdate(WxUser wxuser) throws Exception {
		String url = WxmutilUtils.getWxworkApiUrl(weixinMutilConfig.getWxKey())
				+ "/user/update?access_token="
				+ getAccessToken();
		JSONObject result = WxworkHttpClientUtil.httpPost(url, wxuser, null,
				JSONObject.class);
		return result;
	}

	@Override
	public String userGet(String userid) throws Exception {
		String url = WxmutilUtils.getWxworkApiUrl(weixinMutilConfig.getWxKey())
				+ "/user/get?access_token="
				+ getAccessToken() + "&userid=" + userid;
		String result = WxworkHttpClientUtil.httpGet(url, null, String.class);
		return result;
	}

	@Override
	public JSONObject userCreate(WxUser wxuser) throws Exception {
		String url = WxmutilUtils.getWxworkApiUrl(weixinMutilConfig.getWxKey())
				+ "/user/create?access_token="
				+ getAccessToken();
		JSONObject result = WxworkHttpClientUtil.httpPost(url, wxuser, null,
				JSONObject.class);
		return result;
	}

	@Override
	public List<WxDepart> departGet() throws Exception {
		String url = WxmutilUtils.getWxworkApiUrl(weixinMutilConfig.getWxKey())
				+ "/department/list?access_token="
				+ getAccessToken();

		List<WxDepart> departList = new ArrayList<WxDepart>();

		JSONObject result = WxworkHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		if (result.getIntValue("errcode") == 0) {
			JSONArray jsonArr = result.getJSONArray("department");
			departList = JSONObject.parseArray(jsonArr.toJSONString(),
					WxDepart.class);

			// 下面的方式也可以，等价
			// departList = result.getObject("department",new
			// TypeReference<List<WxDepart>>() {});
		}
		return departList;
	}

	@Override
	public List<WxDepart> departGet(String departId) throws Exception {
		String url = WxmutilUtils.getWxworkApiUrl(weixinMutilConfig.getWxKey())
				+ "/department/list?access_token="
				+ getAccessToken() + "&id=" + departId;

		List<WxDepart> departList = new ArrayList<WxDepart>();
		logger.debug("根据部门id获取列表  url：" + url);
		JSONObject result = WxworkHttpClientUtil.httpGet(url, null,
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
		String url = WxmutilUtils.getWxworkApiUrl(weixinMutilConfig.getWxKey())
				+ "/department/update?access_token="
				+ getAccessToken();
		JSONObject result = WxworkHttpClientUtil.httpPost(url, dept, null,
				JSONObject.class);
		return result;
	}

	@Override
	public JSONObject departDelete(Long departId) throws Exception {
		String url = WxmutilUtils.getWxworkApiUrl(weixinMutilConfig.getWxKey())
				+ "/department/delete?access_token="
				+ getAccessToken() + "&id=" + departId;
		JSONObject result = WxworkHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		return result;
	}

	@Override
	public JSONObject departCreate(WxDepart dept) throws Exception {
		String url = WxmutilUtils.getWxworkApiUrl(weixinMutilConfig.getWxKey())
				+ "/department/create?access_token="
				+ getAccessToken();
		JSONObject result = WxworkHttpClientUtil.httpPost(url, dept, null,
				JSONObject.class);
		return result;
	}

	// ==================================================
	// 待办、待阅、机器人节点
	// ==================================================
	@Override
	public JSONObject messageSend(WxMessage msg) throws Exception {
		String agentIdKey = msg.getAgentId() + "_"
				+ weixinMutilConfig.getWxKey();
		// System.out.println("==========待办、待阅、机器人节点agentIdKey:" + agentIdKey);
		String token = getAccessTokenByAgentid(agentIdKey);
		if (StringUtil.isNull(token)) {
			token = getToReadAccessToken();
		}
		String url = WxmutilUtils.getWxworkApiUrl(weixinMutilConfig.getWxKey())
				+ "/message/send?access_token="
				+ token;

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
			newsMsg.setMsgType(WxmutilConstant.CUSTOM_MSG_NEWS);
			newsMsg.setNews(newsMap);

			paramObj = newsMsg;
		}

		JSONObject result = WxworkHttpClientUtil.httpPost(url, paramObj, null,
				JSONObject.class);
		return result;
	}

	// =============================================
	// SSO
	// =============================================
	@Override
	public JSONObject oauth2getUserInfo(String agentId, String code)
			throws Exception {

		String agentIdKey = agentId + "_" + weixinMutilConfig.getWxKey();
		// System.out.println("SSO agentId:" + agentIdKey);
		logger.warn("--------------2222-------------");
		String url = WxmutilUtils.getWxworkApiUrl(weixinMutilConfig.getWxKey())
				+ "/user/getuserinfo?access_token="
				+ getAccessTokenByAgentid(agentIdKey) + "&code=" + code
				+ "&agendid=" + agentId;
		JSONObject result = WxworkHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		return result;
	}

	// =================================================
	// 应用菜单
	// ==================================================
	@Override
	public void menuDelete(String agentId) throws Exception {
		String token = getAccessTokenByAgentid(
				agentId + "_" + weixinMutilConfig.getWxKey());
		if (StringUtil.isNull(token)) {
			token = getAccessToken();
		}
		String url = WxmutilUtils.getWxworkApiUrl(weixinMutilConfig.getWxKey())
				+ "/menu/delete?access_token="
				+ token + "&agentid=" + agentId;
		WxworkHttpClientUtil.httpGet(url, null, null);
	}

	@Override
	public void menuCreate(String agentId, WxMenu menu) throws Exception {
		String token = getAccessTokenByAgentid(
				agentId + "_" + weixinMutilConfig.getWxKey());
		if (StringUtil.isNull(token)) {
			token = getAccessToken();
		}
		String url = WxmutilUtils.getWxworkApiUrl(weixinMutilConfig.getWxKey())
				+ "/menu/create?access_token="
				+ token + "&agentid=" + agentId;
		WxworkHttpClientUtil.httpPost(url, menu, null, null);
	}

	// ======================================================
	@Override
	public String agentGet(String agentId) throws Exception {

		String agentIdKey = agentId + "_" + weixinMutilConfig.getWxKey();
		String url = WxmutilUtils.getWxworkApiUrl(weixinMutilConfig.getWxKey())
				+ "/agent/get?access_token="
				+ getAccessTokenByAgentid(agentIdKey) + "&agentid=" + agentId;
		return WxworkHttpClientUtil.httpGet(url, null, String.class);
	}

	@Override
	public JSONObject convert2Openid(String userId) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/user/convert_to_openid?access_token="
				+ getAccessToken() ;
		JSONObject paramObj = new JSONObject();
		paramObj.put("userid",userId);
		JSONObject result = WxworkHttpClientUtil.httpPost(url,paramObj, null, JSONObject.class);
		return result;
	}
}
