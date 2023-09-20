package com.landray.kmss.third.weixin.work.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.model.ThirdWeixinContact;
import com.landray.kmss.third.weixin.model.ThirdWeixinContactTag;
import com.landray.kmss.third.weixin.work.constant.WxworkConstant;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.model.api.*;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinWorkService;
import com.landray.kmss.third.weixin.work.util.RandomUtils;
import com.landray.kmss.third.weixin.work.util.WxworkHttpClientUtil;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 企业微信API的实现类
 *
 * @author 唐有炜
 *
 */
public class WxworkApiServiceImpl implements WxworkApiService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 全局的是否正在刷新access token的锁
	 */
	protected final Object globalAccessTokenRefreshLock = new Object();
	private static final int ACCESS_TOKEN_EXPIRESIN = 7200;
	private static Map<String, AccessToken> accessTokenMap = new ConcurrentHashMap();
	private IThirdWeixinWorkService thirdWeixinWorkService;

	/**
	 * 全局的是否正在刷新jsapi ticket
	 */
	protected final Object globalJsapiTicketRefreshLock = new Object();
	private static final Long JSAPI_TICKIT_EXPIRESIN = 7200L;
	//private long jsapiTicketExpiresTime = 0;
	//private String jsapiTicket = null;

	private static Map<String, AgentJsapiTicket> agentJsapiTickets = new ConcurrentHashMap<String, AgentJsapiTicket>();

	/**
	 * 兼容下游组织JSAPI调用
	 * key为corpid,value为ticket对象
	 */
	private static Map<String, JsapiTicket> jsapiTickets = new ConcurrentHashMap<String, JsapiTicket>();

	/**
	 * 下游企业token
	 */
	private static Map<String,AccessToken> corpTokenMap = new ConcurrentHashMap<>();

	private static Map<String,CorpGroupAppShareInfo> appShareInfoMap = null;

	//private Timer timer = new Timer();

	public WxworkApiServiceImpl() {
//		timer.schedule(new TimerTask() {
//			public void run() {
//				logger.debug("schedule");
//				for (String agentId : accessTokenMap.keySet()) {
//					String accessToken = getToken(agentId);
//					if (StringUtil.isNotNull(accessToken)) {
//						updateAccessToken(agentId, accessToken,
//							ACCESS_TOKEN_EXPIRESIN);
//					}
//				}
//			}
//		}, 1000000l, 1000000l);
	}

	public IThirdWeixinWorkService getThirdWeixinWorkService() {
		if (null == thirdWeixinWorkService) {
			thirdWeixinWorkService = (IThirdWeixinWorkService) SpringBeanUtil
					.getBean("thirdWeixinWorkService");
		}
		return thirdWeixinWorkService;
	}

	public void setThirdWeixinWorkService(
			IThirdWeixinWorkService thirdWeixinWorkService) {
		this.thirdWeixinWorkService = thirdWeixinWorkService;
	}

	@Override
	public String getAccessToken() throws Exception {
		return getAccessTokenByAgentid("orgId");
	}

	@Override
	public String getTodoAccessToken() throws Exception {
		String todoAgentId = WeixinWorkConfig.newInstance().getWxAgentid();
		return getAccessTokenByAgentid(todoAgentId);
	}

	@Override
	public String getToReadAccessToken() throws Exception {
		String toReadAgentId = WeixinWorkConfig.newInstance().getWxToReadAgentid();
		if(StringUtil.isNull(toReadAgentId)){
			toReadAgentId = WeixinWorkConfig.newInstance().getWxAgentid();
		}
		return getAccessTokenByAgentid(toReadAgentId);
	}

	@Override
	public String getAccessTokenByAgentid(String agentId) throws Exception {
		return getAccessToken(false, agentId, null);
	}

	@Override
	public String getAccessBasicsToken(String secret,String agentId) throws Exception {
		//secret不能为空，比如打卡
		if(StringUtil.isNull(secret)){
			logger.warn("agentId:"+agentId+" 的secret不能为空~~~");
			return null;
		}
		return getAccessToken(false, agentId, secret);
	}

	public String getAccessToken(boolean forceRefresh, String agentId, String secret)
			throws Exception {
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
						String accessToken = "";
						if (!StringUtil.isNull(secret)) {
							// 企业微信自带应用获取token，例：打卡
							accessToken = getBasicsToken(secret);
						} else {
							accessToken = getToken(agentId);
						}
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

	public String getBasicsToken(String secret) {

		String url = WxworkUtils.getWxworkApiUrl() + "/gettoken?&corpid="
				+ WeixinWorkConfig.newInstance().getWxCorpid() + "&corpsecret="
				+ secret;
		long start = System.currentTimeMillis();
		JSONObject json = WxworkHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		long end = System.currentTimeMillis();
		logger.debug("请求企业微信基础应用：" + url + ",耗时：" + (end - start) + ",请求结果:"
				+ json.toJSONString());
		if (json.getIntValue("errcode") == 0) {
			return json.getString("access_token");
		} else {
			logger.error("获取token失败，url:"+url+"，result:"+json.toString());
			return null;
		}
	}

	public String getToken(String agentId) {
		logger.debug(this.toString());
		String corpSecret = WeixinWorkConfig.newInstance().getWxCorpsecret();
		if("chatdata".equals(agentId)){
			corpSecret = WeixinWorkConfig.newInstance().getChatdataAppSecret();
		}
		logger.debug("获取tokend的agentId（orgId表示组织，其他表示应用）：" + agentId);
		if (!"orgId".equals(agentId) && !"chatdata".equals(agentId)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("fdSecret");
			hqlInfo.setWhereBlock("fdAgentid=:fdAgentid");
			hqlInfo.setParameter("fdAgentid", agentId);
			try {
				String fdSecret = (String) getThirdWeixinWorkService().findFirstOne(hqlInfo);
				if (StringUtils.isNotBlank(fdSecret)) {
					corpSecret = fdSecret;
				}else{
					logger.warn("无法获取应用：{} 的 secret 信息，取通讯录secret获取token",agentId);
				}
			} catch (Exception e) {
				logger.error("未找到该应用配置信息，请先在企业微信后台配置应用", e);
				return null;
			}
		}
		String url = WxworkUtils.getWxworkApiUrl() + "/gettoken?&corpid="
				+ WeixinWorkConfig.newInstance().getWxCorpid() + "&corpsecret="
				+ corpSecret;
		long start = System.currentTimeMillis();
		JSONObject json = WxworkHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		long end = System.currentTimeMillis();
		logger.debug("请求：" + url + ",耗时：" + (end - start) + ",请求结果:"
				+ json.toJSONString());
		if (json.getIntValue("errcode") == 0) {
			return json.getString("access_token");
		} else {
			return null;
		}
	}

	public synchronized void updateAccessToken(String agentId,
											   String accessToken,
											   int expiresInSeconds) {
		logger.debug("updateAccessToken");
		AccessToken accessTokenObj = (AccessToken) accessTokenMap.get(agentId);
		// 如果是第一次请求，AccessToken不存在需要新建
		if (null == accessTokenObj) {
			accessTokenObj = new AccessToken();
		}
		logger.debug(accessToken);
		accessTokenObj.setAccessToken(accessToken);
		accessTokenObj.setExpiresTime(
				System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L);
		accessTokenMap.put(agentId, accessTokenObj);
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
		url = String.valueOf(url) + "appid=" + WeixinWorkConfig.newInstance().getWxCorpid();

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
	private boolean isJsapiTicketExpired(String corpId) {
		if(StringUtil.isNull(corpId)){
			corpId = WeixinWorkConfig.newInstance().getWxCorpid();
		}
		JsapiTicket ticket = jsapiTickets.get(corpId);
		if(ticket==null){
			return true;
		}
		return ticket.isExpired();
	}

	public synchronized void updateJsapiTicket(String corpId, String jsapiTicket,
											   Long expiresInSeconds) {
		JsapiTicket ticket = new JsapiTicket(corpId,jsapiTicket,expiresInSeconds);
		jsapiTickets.put(corpId,ticket);
	}

	@Override
	public String getJsapiTicket(String corpId) throws Exception {
		return getJsapiTicket(false,corpId);
	}

	/**
	 * 兼容下游组织JSAPI签名
	 * @param forceRefresh 是否刷新ticket
	 * @param agentid 应用id
	 * @param corpId 下游组织的ID，如果为null则表示是主组织
	 * @return
	 * @throws Exception
	 */
	public String getJsapiTicket(boolean forceRefresh,String agentid, String corpId) throws Exception {

		String  accessToken = "";
		logger.debug("forceRefresh:" + forceRefresh + "  agentid:" + agentid + "  corpId:" + corpId);
		String mainCorpId = WeixinWorkConfig.newInstance().getWxCorpid();
		if(StringUtil.isNull(corpId)){
			corpId = mainCorpId;
		}
		if(!corpId.equals(mainCorpId)){
			accessToken = getCorpTokenStringByCorpId(corpId, 1);
		}else {
			if (StringUtil.isNotNull(agentid)) {
				accessToken = getAccessTokenByAgentid(agentid);
			} else {
				accessToken = getAccessToken();
			}
		}
		if(StringUtil.isNull(accessToken)){
			throw new Exception("无法获取到accessToken，请检查后台日志");
		}

		if (forceRefresh) {
			expireJsapiTicket(corpId);
		}
		if (isJsapiTicketExpired(corpId)) {
			synchronized (globalJsapiTicketRefreshLock) {
				if (isJsapiTicketExpired(corpId)) {
					logger.debug("重新获取ticket");
					String url = WxworkUtils.getWxworkApiUrl()
							+ "/get_jsapi_ticket?access_token="
							+ accessToken;
					JSONObject response = WxworkHttpClientUtil.httpGet(url,
							null,
							JSONObject.class);
					String jsapiTicket = null;
					Long expiresTime = 0L;
					if (response.containsKey("ticket")) {
						jsapiTicket = response.getString("ticket");
						expiresTime = System.currentTimeMillis() + (response.getLong("expires_in") - 200) * 1000L;
					} else {
						jsapiTicket = response.toString();
					}
					logger.debug("jsapiTicket:" + jsapiTicket);
					updateJsapiTicket(corpId, jsapiTicket, expiresTime);
				}
			}
		}
		logger.debug("this.jsapiTicket:" + jsapiTickets.get(corpId));
		return jsapiTickets.get(corpId).getTicket();
	}

	/**
	 * 兼容下游组织JSAPI签名
	 * @param forceRefresh 是否刷新ticket
	 * @param corpId 下游组织的ID，如果为null则表示是主组织
	 * @return
	 * @throws Exception
	 */
	public String getJsapiTicket(boolean forceRefresh, String corpId) throws Exception {
		return getJsapiTicket(forceRefresh, WeixinWorkConfig.newInstance().getWxAgentid(), corpId);
	}

	public String getAgentCofigJsapiTicket(String corpId, boolean forceRefresh, String agentid)
			throws Exception {
		if (StringUtil.isNull(agentid)) {
			logger.warn("******agentid不能为空*****");
			throw new Exception("agentid不能为空");
		}
		if (forceRefresh) {
			AgentJsapiTicket agentJsapiTicket = getAgentCofigJsapiTicket(corpId,
					agentid);
			agentJsapiTickets.put(corpId+"#"+agentid, agentJsapiTicket);
			return agentJsapiTicket.getTicket();
		}
		AgentJsapiTicket agentJsapiTicket = agentJsapiTickets.get(corpId+"#"+agentid);
		if (agentJsapiTicket == null || agentJsapiTicket.isExpired()) {
			agentJsapiTicket = getAgentCofigJsapiTicket(corpId, agentid);
			agentJsapiTickets.put(corpId+"#"+agentid, agentJsapiTicket);
			return agentJsapiTicket.getTicket();
		} else {
			return agentJsapiTicket.getTicket();
		}
	}

	public AgentJsapiTicket getAgentCofigJsapiTicket(String corpId, String agentid)
			throws Exception {
		String accessToken = null;
		if(StringUtil.isNotNull(corpId) && !corpId.equals(WeixinWorkConfig.newInstance().getWxCorpid())){
			Map<String, CorpGroupAppShareInfo> appShareInfoMap = getAppShareInfoMap();
			CorpGroupAppShareInfo appShareInfo = appShareInfoMap.get(corpId);
			accessToken = getCorpTokenString(corpId+"#"+appShareInfo.getAgentId(),false, 1);
			agentid = appShareInfo.getAgentId();
		}else {
			accessToken = getAccessTokenByAgentid(agentid);
		}
		logger.debug("----获取agentCofig ticket----");
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/ticket/get?access_token="
				+ accessToken + "&type=agent_config";
		JSONObject response = WxworkHttpClientUtil.httpGet(url,
				null,
				JSONObject.class);
		String agentJsapiTicket = null;
		Long expires_in = 7200L;
		if (response.containsKey("ticket")) {
			agentJsapiTicket = response.getString("ticket");
			expires_in = response.getLong("expires_in");
		} else {
			throw new Exception("获取agentTicket失败，请求地址：" + url + "，请求结果："
					+ response.toString());
		}

		long expiresTime = System.currentTimeMillis()
				+ (expires_in - 200) * 1000L;
		logger.debug("****ticket jsapiTicket:" + agentJsapiTicket);
		return new AgentJsapiTicket(corpId, agentid, agentJsapiTicket, expiresTime);
	}

	@Override
	public Map<String, Object> createJsapiSignature(String url, String corpId)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		long timestamp = System.currentTimeMillis() / 1000L;
		String noncestr = RandomUtils.getRandomStr(16);
		String ticket = getJsapiTicket(false,corpId);
		String signature = sign(ticket, noncestr, timestamp, url);

		resultMap.put("noncestr", noncestr);
		resultMap.put("timestamp", timestamp);
		resultMap.put("signature", signature);

		return resultMap;
	}

	@Override
	public Map<String, Object> createJsapiSignature(String url,String agentid,String corpId)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		long timestamp = System.currentTimeMillis() / 1000L;
		String noncestr = RandomUtils.getRandomStr(16);
		String ticket = getJsapiTicket(false,agentid,corpId);
		String signature = sign(ticket, noncestr, timestamp, url);

		resultMap.put("noncestr", noncestr);
		resultMap.put("timestamp", timestamp);
		resultMap.put("signature", signature);

		return resultMap;
	}

	@Override
	public Map<String, Object> createAgentConfigJsapiSignature(String url,
															   String agentid, String corpId) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		long timestamp = System.currentTimeMillis() / 1000L;
		String noncestr = RandomUtils.getRandomStr(16);
		String ticket = getAgentCofigJsapiTicket(corpId,false, agentid);
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
	public List<WxUser> userList(Long departId, Boolean fetchChild,
								 Integer status) throws Exception {
		return userList(departId,fetchChild,status,null,null);
	}
	// =============================================================
	// 组织架构
	// =============================================================
	@Override
	public List<WxUser> userList(Long departId, Boolean fetchChild,
								 Integer status, String corpAndAgentId, Integer business_type) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/user/list?access_token="
				+ getOrgSyncToken(corpAndAgentId,business_type) + "&department_id=" + departId;
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
				JSONObject.class, 120000);
		if (result.getIntValue("errcode") == 0) {
			JSONArray jsonArr = result.getJSONArray("userlist");
			userList = JSONObject.parseArray(jsonArr.toJSONString(),
					WxUser.class);
		}
		return userList;
	}

	@Override
	public JSONObject userDelete(String userid) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/user/delete?access_token="
				+ getAccessToken() + "&userid=" + userid;
		JSONObject result = WxworkHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		return result;
	}

	@Override
	public JSONObject userUpdate(WxUser wxuser) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/user/update?access_token="
				+ getAccessToken();
		JSONObject result = WxworkHttpClientUtil.httpPost(url, wxuser, null,
				JSONObject.class);
		return result;
	}

	@Override
	public String userGet(String userid) throws Exception {
		return userGet(userid,null,null);
	}

	@Override
	public String userGet(String userid, String corpAndAgentId, Integer business_type) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/user/get?access_token="
				+ getOrgSyncToken(corpAndAgentId,business_type) + "&userid=" + userid;
		String result = WxworkHttpClientUtil.httpGet(url, null, String.class);
		return result;
	}

	@Override
	public JSONObject userCreate(WxUser wxuser) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/user/create?access_token="
				+ getAccessToken();
		JSONObject result = WxworkHttpClientUtil.httpPost(url, wxuser, null,
				JSONObject.class);
		return result;
	}

	@Override
	public List<WxDepart> departGet() throws Exception {
		return departGet(null,null);
	}

	@Override
	public List<WxDepart> departGet(String corpAndAgentId, Integer business_type) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/department/list?access_token="
				+ getOrgSyncToken(corpAndAgentId,business_type);

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
		return departGet(departId,null,null);
	}

	@Override
	public List<WxDepart> departGet(String departId, String corpAndAgentId, Integer business_type) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/department/list?access_token="
				+ getOrgSyncToken(corpAndAgentId,business_type) + "&id=" + departId;

		List<WxDepart> departList = new ArrayList<WxDepart>();

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
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/department/update?access_token="
				+ getAccessToken();
		JSONObject result = WxworkHttpClientUtil.httpPost(url, dept, null,
				JSONObject.class);
		return result;
	}

	@Override
	public JSONObject departDelete(Long departId) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/department/delete?access_token="
				+ getAccessToken() + "&id=" + departId;
		JSONObject result = WxworkHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		return result;
	}

	@Override
	public JSONObject departCreate(WxDepart dept) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl()
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
		return messageSend(msg,null);
	}

	// ==================================================
	// 待办、待阅、机器人节点
	// ==================================================
	@Override
	public JSONObject messageSend(WxMessage msg, String corpId) throws Exception {
		logger.debug(this.toString());
		String token = null;
		String agentid = msg.getAgentId();
		if(StringUtil.isNotNull(corpId)){
			Map<String, CorpGroupAppShareInfo> appShareInfoMap = getAppShareInfoMap();
			CorpGroupAppShareInfo appShareInfo = appShareInfoMap.get(corpId);
			token = getCorpTokenString(corpId+"#"+appShareInfo.getAgentId(),false, 1);
			agentid = appShareInfo.getAgentId();
		}else {
			token = getAccessTokenByAgentid(msg.getAgentId());
			if (StringUtil.isNull(token)) {
				token = getToReadAccessToken();
			}
		}
		if(StringUtil.isNull(token)){
			throw new Exception("获取token失败");
		}

		String url = WxworkUtils.getWxworkApiUrl()
				+ "/message/send?access_token="
				+ token;

		WxMessage paramObj = null;
		List<WxArticle> ars = msg.getArticles();
		if (CollectionUtils.isNotEmpty(ars)) {
			WxMessage newsMsg = new WxMessage();
			Map<String, Object> newsMap = new HashMap<String, Object>();
			newsMap.put("articles", ars);

			newsMsg.setAgentId(agentid);
			newsMsg.setToUser(msg.getToUser());
			newsMsg.setToTag(msg.getToTag());
			newsMsg.setToParty(msg.getToParty());
			newsMsg.setMsgType(WxworkConstant.CUSTOM_MSG_NEWS);
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
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/user/getuserinfo?access_token="
				+ getAccessTokenByAgentid(agentId) + "&code=" + code
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
		String token = getAccessTokenByAgentid(agentId);
		if (StringUtil.isNull(token)) {
			token = getAccessToken();
		}
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/menu/delete?access_token="
				+ token + "&agentid=" + agentId;
		WxworkHttpClientUtil.httpGet(url, null, null);
	}

	@Override
	public void menuCreate(String agentId, WxMenu menu) throws Exception {
		String token = getAccessTokenByAgentid(agentId);
		if (StringUtil.isNull(token)) {
			token = getAccessToken();
		}
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/menu/create?access_token="
				+ token + "&agentid=" + agentId;
		WxworkHttpClientUtil.httpPost(url, menu, null, null);
	}

	// ======================================================
	@Override
	public String agentGet(String agentId) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/agent/get?access_token="
				+ getAccessTokenByAgentid(agentId) + "&agentid=" + agentId;
		return WxworkHttpClientUtil.httpGet(url, null, String.class);
	}

	@Override
	public void expireAllToken() throws Exception {
		if (accessTokenMap != null) {
			accessTokenMap.clear();
		} else {
			accessTokenMap = new ConcurrentHashMap();
		}
	}

	@Override
	public void expireJsapiTicket(String corpId) {
		jsapiTickets.remove(corpId);
	}

	@Override
	public void expireAllJsapiTicket() {
		jsapiTickets.clear();
	}

	@Override
	public JSONObject messageSend(String agentId, JSONObject paramObj,String corpId)
			throws Exception {
		String mainCorpId = WeixinWorkConfig.newInstance().getWxCorpid();
		String token = null;
		if(StringUtil.isNull(corpId) || mainCorpId.equals(corpId)) {
			token = getAccessTokenByAgentid(agentId);
			if (StringUtil.isNull(token)) {
				token = getToReadAccessToken();
			}
		}else{
			Map<String, CorpGroupAppShareInfo> appShareInfoMap = getAppShareInfoMap();
			CorpGroupAppShareInfo appShareInfo = appShareInfoMap.get(corpId);
			String corpgroupAgentId = appShareInfo.getAgentId();
			token = getCorpTokenString(corpId+"#"+corpgroupAgentId,false, 1);
			paramObj.put("agentid",corpgroupAgentId);
		}
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/message/send?access_token="
				+ token;

		JSONObject result = WxworkHttpClientUtil.httpPost(url, paramObj, null,
				JSONObject.class);
		return result;
	}

	@Override
	public JSONObject messageSendTaskcard(WxMessage msg, String notifyId)
			throws Exception {
		logger.debug(this.toString());
		String token = getAccessTokenByAgentid(msg.getAgentId());
		if (StringUtil.isNull(token)) {
			token = getToReadAccessToken();
		}
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/message/send?access_token="
				+ token;

		JSONObject msgObj = new JSONObject();
		msgObj.put("touser", msg.getToUser());
		msgObj.put("msgtype", "taskcard");
		msgObj.put("agentid", msg.getAgentId());
		JSONObject taskcard = new JSONObject();
		taskcard.put("title", msg.getArticles().get(0).getTitle());
		taskcard.put("description", msg.getArticles().get(0).getDescription());
		taskcard.put("url", msg.getArticles().get(0).getUrl());
		taskcard.put("task_id", notifyId);
		JSONArray btns = new JSONArray();
		JSONObject btn = new JSONObject();
		btn.put("key", "handle");
		btn.put("name", "待处理");
		btn.put("replace_name", "已处理");
		btns.add(btn);
		taskcard.put("btn", btns);
		msgObj.put("taskcard", taskcard);

		JSONObject result = WxworkHttpClientUtil.httpPost(url, msgObj, null,
				JSONObject.class);
		return result;
	}

	@Override
	public JSONObject updateTaskcard(String agentId, String userid,
									 String todoId, String corpId)
			throws Exception {
		logger.debug(this.toString());
		String mainCorpId = WeixinWorkConfig.newInstance().getWxCorpid();
		String token = null;
		if(StringUtil.isNull(corpId) || mainCorpId.equals(corpId)) {
			token = getAccessTokenByAgentid(agentId);
			if (StringUtil.isNull(token)) {
				token = getToReadAccessToken();
			}
		}else{
			Map<String, CorpGroupAppShareInfo> appShareInfoMap = getAppShareInfoMap();
			CorpGroupAppShareInfo appShareInfo = appShareInfoMap.get(corpId);
			agentId = appShareInfo.getAgentId();
			token = getCorpTokenString(corpId+"#"+agentId,false, 1);
		}
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/message/update_taskcard?access_token="
				+ token;

		JSONObject msgObj = new JSONObject();
		JSONArray userids = new JSONArray();
		userids.add(userid);
		msgObj.put("userids", userids);
		msgObj.put("agentid", agentId);
		msgObj.put("task_id", todoId);
		msgObj.put("clicked_key", "handle");

		JSONObject result = WxworkHttpClientUtil.httpPost(url, msgObj, null,
				JSONObject.class);
		return result;
	}

	@Override
	public JSONObject updateTaskcard(String agentId, JSONObject msgObj, String corpId)
			throws Exception {
		logger.debug(this.toString());
		String mainCorpId = WeixinWorkConfig.newInstance().getWxCorpid();
		String token = null;
		if(StringUtil.isNull(corpId) || mainCorpId.equals(corpId)) {
			token = getAccessTokenByAgentid(agentId);
			if (StringUtil.isNull(token)) {
				token = getToReadAccessToken();
			}
		}else{
			Map<String, CorpGroupAppShareInfo> appShareInfoMap = getAppShareInfoMap();
			CorpGroupAppShareInfo appShareInfo = appShareInfoMap.get(corpId);
			agentId = appShareInfo.getAgentId();
			token = getCorpTokenString(corpId+"#"+agentId,false, 1);
			msgObj.put("agentid",agentId);
		}
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/message/update_taskcard?access_token="
				+ token;

		JSONObject result = WxworkHttpClientUtil.httpPost(url, msgObj, null,
				JSONObject.class);
		return result;
	}

	@Override
	public JSONObject convert2Openid(String userId) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/user/convert_to_openid?access_token="
				+ getTodoAccessToken() ;
		JSONObject paramObj = new JSONObject();
		paramObj.put("userid",userId);
		JSONObject result = WxworkHttpClientUtil.httpPost(url,paramObj, null, JSONObject.class);
		return result;
	}

	@Override
	public String getUserOpenId(String userId) throws Exception {
		JSONObject jsonObj = convert2Openid(userId);
		if(jsonObj.getIntValue("errcode")==0){
			return jsonObj.getString("openid");
		}
		throw new Exception("获取openid失败，"+jsonObj.toString());
	}

	@Override
	public String getContactAccessToken() throws Exception {
		String secret = WeixinWorkConfig.newInstance().getSyncContactSecret();
		if(StringUtil.isNotNull(secret)) {
			return getAccessToken(false, "contact", secret);
		}
		return getAccessToken();
	}

	@Override
	public JSONObject listContact(String userid) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/externalcontact/list?access_token="
				+ getContactAccessToken() + "&userid=" + userid;
		JSONObject result = WxworkHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		return result;
	}

	@Override
	public JSONObject getContact(String external_userid) throws Exception {
		return getContact(external_userid,null);
	}

	@Override
	public JSONObject getContact(String external_userid, String cursor) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/externalcontact/get?access_token="
				+ getContactAccessToken() + "&external_userid=" + external_userid;
		if(StringUtil.isNotNull(cursor)){
			url += "&cursor="+cursor;
		}
		JSONObject result = WxworkHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		return result;
	}


	@Override
	public void listContactBatch(JSONArray userids, String cursor,JSONArray contactList_all) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/externalcontact/batch/get_by_user?access_token="
				+ getContactAccessToken();
		JSONObject params = new JSONObject();
		params.put("userid_list",userids);
		params.put("limit",100);
		if(StringUtil.isNotNull(cursor)){
			params.put("cursor",cursor);
		}
		JSONObject result = WxworkHttpClientUtil.httpPost(url, params,null,
				JSONObject.class);
		int errcode = result.getIntValue("errcode");
		if(errcode==0){
			JSONArray contactList = result.getJSONArray("external_contact_list");
			contactList_all.addAll(contactList);
			String next_cursor = result.getString("next_cursor");
			if(StringUtil.isNotNull(next_cursor)){
				listContactBatch(userids,next_cursor,contactList_all);
			}
		}else{
			throw new Exception("获取客户列表失败，返回信息："+result);
		}
	}

	@Override
	public JSONObject listCorpTag(String group_id, String tag_id) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/externalcontact/get_corp_tag_list?access_token="
				+ getContactAccessToken();
		JSONObject params = new JSONObject();
		if(StringUtil.isNotNull(group_id)){
			JSONArray array = new JSONArray();
			array.add(group_id);
			params.put("group_id",array);
		}else if(StringUtil.isNotNull(tag_id)){
			JSONArray array = new JSONArray();
			array.add(tag_id);
			params.put("tag_id",array);
		}
		JSONObject result = WxworkHttpClientUtil.httpPost(url, params,null,
				JSONObject.class);
		return result;
	}

	private Map<String,ThirdWeixinContactTag> buildContactTagMap() throws Exception {
		JSONObject o = listCorpTag(null,null);
		if(o.getInteger("errcode")!=0){
			throw new Exception("获取标签信息失败，返回信息："+o.toString());
		}
		Map<String,ThirdWeixinContactTag> tagsMap = new HashMap<>();
		JSONArray groups = o.getJSONArray("tag_group");
		for(int i=0;i<groups.size();i++){
			JSONObject group = groups.getJSONObject(i);
			String group_id = group.getString("group_id");
			String group_name = group.getString("group_name");
			JSONArray tags = group.getJSONArray("tag");
			if(tags==null){
				continue;
			}
			for(int j=0;j<tags.size();j++){
				JSONObject tagObj = tags.getJSONObject(j);
				String tagId = tagObj.getString("id");
				ThirdWeixinContactTag tag = new ThirdWeixinContactTag(group_name,tagId,tagObj.getString("name"),null);
				tagsMap.put(tagId,tag);
			}
		}
		return tagsMap;
	}

	@Override
	public Map<String,ThirdWeixinContact> listContactBatch(List<String> userIdList) throws Exception {
		Map<String,ThirdWeixinContactTag> tagsMap = buildContactTagMap();

		Map<String,ThirdWeixinContact> contactMap = new HashMap<>();
		List<List<String>> wxUserLists = Lists.partition(userIdList,100);
		for(List<String> wxUserList:wxUserLists){
			JSONArray userIdArray = JSONArray.parseArray(JSON.toJSONString(wxUserList));
			JSONArray contactArray = new JSONArray();
			listContactBatch(userIdArray,null,contactArray);
			putContactMap(contactMap,contactArray,tagsMap);
		}
		return contactMap;
	}

	private void putContactMap(Map<String,ThirdWeixinContact> contactMap, JSONArray contactArray, Map<String,ThirdWeixinContactTag> tagsMap){
		for(int i=0;i<contactArray.size();i++){
			JSONObject o = contactArray.getJSONObject(i);
			JSONObject external_contact = o.getJSONObject("external_contact");
			String external_userid = external_contact.getString("external_userid");
			ThirdWeixinContact thirdWeixinContact = null;
			if(contactMap.containsKey(external_userid)){
				thirdWeixinContact = contactMap.get(external_userid);
			}else{
				thirdWeixinContact = new ThirdWeixinContact(external_userid,
						external_contact.getString("name"),external_contact.getString("position"),
						external_contact.getString("corp_full_name"),external_contact.getString("corp_name"),
						external_contact.getInteger("gender"));
				contactMap.put(external_userid,thirdWeixinContact);
			}
			Object follow_info = o.get("follow_info");
			if(follow_info==null){
				continue;
			}
			if(follow_info instanceof JSONObject){
				JSONObject follow_info_obj = (JSONObject) follow_info;
				addContactTags(thirdWeixinContact,follow_info_obj.getJSONArray("tag_id"),tagsMap);
			}else if(follow_info instanceof JSONArray){
				JSONArray follow_info_array = (JSONArray) follow_info;
				for(int j=0;j<follow_info_array.size();j++){
					addContactTags(thirdWeixinContact,follow_info_array.getJSONObject(j).getJSONArray("tag_id"),tagsMap);
				}
			}
		}
	}

	private void addContactTags(ThirdWeixinContact thirdWeixinContact, JSONArray tagsArray, Map<String,ThirdWeixinContactTag> tagsMap){
		if(tagsArray==null){
			return;
		}
		for(int i=0;i<tagsArray.size();i++){
			String tagId = tagsArray.getString(i);
			ThirdWeixinContactTag tag = tagsMap.get(tagId);
			if(tag!=null) {
				thirdWeixinContact.addTag(tag);
			}
		}
	}

	@Override
	public JSONObject listCorpTag(JSONArray groupIds) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/externalcontact/get_corp_tag_list?access_token="
				+ getContactAccessToken();
		JSONObject params = new JSONObject();
		params.put("group_id",groupIds);
		JSONObject result = WxworkHttpClientUtil.httpPost(url, params,null,
				JSONObject.class);
		return result;
	}

	@Override
	public JSONObject convert2Userid(String openId) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/user/convert_to_userid?access_token="
				+ getTodoAccessToken();
		JSONObject paramObj = new JSONObject();
		paramObj.put("openid",openId);
		JSONObject result = WxworkHttpClientUtil.httpPost(url,paramObj, null, JSONObject.class);
		return result;
	}

	@Override
	public String getUserId(String openId) throws Exception {
		JSONObject jsonObj = convert2Userid(openId);
		if(jsonObj.getIntValue("errcode")==0){
			return jsonObj.getString("userid");
		}
		throw new Exception("获取userid失败，"+jsonObj.toString());
	}

	@Override
	public JSONObject createLiving(WxLiving living) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/living/create?access_token="
				+ getTodoAccessToken();
		JSONObject result = WxworkHttpClientUtil.httpPost(url,living, null, JSONObject.class);
		logger.debug("创建预约直播："+result);
		return result;
	}

	@Override
	public JSONObject modifyLiving(WxLiving living) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/living/modify?access_token="
				+ getTodoAccessToken();
		JSONObject result = WxworkHttpClientUtil.httpPost(url,living, null, JSONObject.class);
		logger.debug("更新预约直播："+result);
		return result;
	}

	@Override
	public JSONObject cancelLiving(String livingid) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/living/cancel?access_token="
				+ getTodoAccessToken();
		JSONObject param = new JSONObject();
		param.put("livingid",livingid);
		JSONObject result = WxworkHttpClientUtil.httpPost(url,param, null, JSONObject.class);
		logger.warn("取消预约直播："+result);
		return result;
	}

	@Override
	public JSONObject deleteReplayDataLiving(String livingid) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/living/delete_replay_data?access_token="
				+ getTodoAccessToken();
		JSONObject param = new JSONObject();
		param.put("livingid",livingid);
		JSONObject result = WxworkHttpClientUtil.httpPost(url,param, null, JSONObject.class);
		logger.warn("删除直播回放："+result);
		return result;
	}

	@Override
	public JSONObject getLivingCode(String livingid, String openid) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/living/get_living_code?access_token="
				+ getTodoAccessToken();
		JSONObject param = new JSONObject();
		param.put("livingid",livingid);
		param.put("openid",openid);
		JSONObject result = WxworkHttpClientUtil.httpPost(url,param, null, JSONObject.class);
		logger.debug("获取微信观看直播凭证："+result);
		return result;
	}

	@Override
	public JSONObject getUserAllLivingid(String userid) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/living/get_user_all_livingid?access_token="
				+ getTodoAccessToken();
		JSONObject param = new JSONObject();
		param.put("userid",userid);
		param.put("limit",100);
		JSONObject result = WxworkHttpClientUtil.httpPost(url,param, null, JSONObject.class);
		logger.debug("获取成员直播ID列表："+result);
		return result;
	}

	@Override
	public JSONObject getLivingInfo(String livingid) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/living/get_living_info?access_token="
				+ getTodoAccessToken()+"&livingid="+livingid;
		JSONObject result = WxworkHttpClientUtil.httpGet(url, null, JSONObject.class);
		logger.debug("获取直播详情："+result);
		return result;
	}

	@Override
	public JSONObject getWatchStat(String livingid) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/living/get_watch_stat?access_token="
				+ getTodoAccessToken();
		JSONObject param = new JSONObject();
		param.put("livingid",livingid);
		param.put("next_key",0);
		JSONObject result = WxworkHttpClientUtil.httpPost(url,param, null, JSONObject.class);
		logger.debug("获取直播观看明细："+result);
		return result;
	}

	@Override
	public JSONObject getRobotInfo(String robot_id) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/msgaudit/get_robot_info?access_token="
				+ getTodoAccessToken()+"&robot_id="+robot_id;
		JSONObject result = WxworkHttpClientUtil.httpGet(url, null, JSONObject.class);
		if(result.getIntValue("errcode")==0){
			return result.getJSONObject("data");
		}
		throw new Exception("获取机器人信息失败，url："+url+"，响应报文："+result.toString());
	}

	@Override
	public JSONObject getExternalContact(String external_userid) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/externalcontact/get?access_token="
				+ getContactAccessToken()+"&external_userid="+external_userid;
		JSONObject result = WxworkHttpClientUtil.httpGet(url, null, JSONObject.class);
		if(result.getIntValue("errcode")==0){
			return result.getJSONObject("external_contact");
		}
		throw new Exception("获取客户信息失败，url："+url+"，响应报文："+result.toString());
	}

	@Override
	public JSONObject getGroupChat(String roomid) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/msgaudit/groupchat/get?access_token="
				+ getAccessTokenByAgentid("chatdata");
		JSONObject req = new JSONObject();
		req.put("roomid",roomid);
		JSONObject result = WxworkHttpClientUtil.httpPost(url, req, null, JSONObject.class);
		if(result.getIntValue("errcode")==0){
			return result;
		}else if(result.getIntValue("errcode")==301059){
			result.put("roomname","");
			result.put("roomid",roomid);
			return result;
		}
		throw new Exception("获取内容存档内部群信息失败，url："+url+"，响应报文："+result.toString());
	}

	@Override
	public JSONObject getUserInfo(String userid) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/user/get?access_token="
				+ getTodoAccessToken()+"&userid="+userid;
		JSONObject result = WxworkHttpClientUtil.httpGet(url, null, JSONObject.class);
		if(result.getIntValue("errcode")==0){
			return result;
		}
		throw new Exception("获取用户信息失败，url："+url+"，响应报文："+result.toString());
	}

	@Override
	public JSONArray listAppShareInfo(Integer business_type, String agentid, String corpid) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/corpgroup/corp/list_app_share_info?access_token="
				+ getAccessTokenByAgentid(agentid);
		JSONObject param = new JSONObject();
		if(business_type!=null) {
			param.put("business_type", business_type);
		}
		param.put("agentid",agentid);
		if(StringUtil.isNotNull(corpid)) {
			param.put("corpid", corpid);
		}
		JSONObject jsonObj = WxworkHttpClientUtil.httpPost(url,param, null, JSONObject.class);
		if(jsonObj.getIntValue("errcode")==0){
			return jsonObj.getJSONArray("corp_list");
		}
		throw new Exception("获取应用共享信息失败，url:"+url+"，请求报文："+param.toString()+"，响应报文："+jsonObj.toString());
	}

	public void expireCorpToken(String corpAndAgentId) {
		if (null != corpTokenMap.get(corpAndAgentId)) {
			AccessToken accessToken = corpTokenMap.get(corpAndAgentId);
			accessToken.setExpiresTime(0);
		}
	}

	@Override
	public String getCorpTokenStringByCorpId(String corpId, Integer business_type)
			throws Exception {
		Map<String, CorpGroupAppShareInfo> appShareInfoMap = getAppShareInfoMap();
		CorpGroupAppShareInfo appShareInfo = appShareInfoMap.get(corpId);
		return getCorpTokenString(corpId+"#"+appShareInfo.getAgentId(),false, business_type);
	}

	@Override
	public String getCorpTokenString(String corpAndAgentId, Integer business_type)
			throws Exception {
		return getCorpTokenString(corpAndAgentId,false, business_type);
	}

	@Override
	public String getCorpTokenString(String corpAndAgentId, boolean forceRefresh, Integer business_type)
			throws Exception {
		if(StringUtil.isNull(corpAndAgentId)){
			return null;
		}
		String tokenKey = corpAndAgentId+"#"+business_type;
		if (forceRefresh || null == corpTokenMap.get(tokenKey)) {
			expireCorpToken(tokenKey);
		}
		if (isCorpTokenExpired(tokenKey)) {
			synchronized (globalAccessTokenRefreshLock) {
				if (isCorpTokenExpired(tokenKey)) {
					try {
						logger.debug("重新获取下游企业accessToken,corpAndAgentId=" + tokenKey);
						String accessToken = "";
						accessToken = getCorpToken(corpAndAgentId,business_type);
						updateCorpToken(tokenKey, accessToken,
								ACCESS_TOKEN_EXPIRESIN);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		AccessToken accessToken = corpTokenMap.get(tokenKey);
		return accessToken.getAccessToken();
	}

	private boolean isCorpTokenExpired(String corpAndAgentId) {
		if (null == corpTokenMap.get(corpAndAgentId)) {
			return true;
		}
		AccessToken accessToken = corpTokenMap.get(corpAndAgentId);
		return System.currentTimeMillis() > accessToken.getExpiresTime();
	}

	public String getCorpToken(String corpAndAgentId, Integer business_type) throws Exception {
		if(StringUtil.isNull(corpAndAgentId)){
			throw new Exception("corpAndAgentId不能为空");
		}
		String[] ss = corpAndAgentId.split("#");
		if(ss.length!=2){
			throw new Exception("corpAndAgentId的格式不对");
		}
		Map<String, CorpGroupAppShareInfo> appShareInfoMap = getAppShareInfoMap();
		if(appShareInfoMap==null){
			throw new Exception("获取不到应用共享信息");
		}
		String upstreamAgentId = appShareInfoMap.get(ss[0])==null?null:appShareInfoMap.get(ss[0]).getUpstreamAgentId();
		if(StringUtil.isNull(upstreamAgentId)){
			throw new Exception("获取不到 "+ ss[0]+ " 对应的上游agentId");
		}
		String url = WxworkUtils.getWxworkApiUrl() + "/corpgroup/corp/gettoken?access_token="+getAccessTokenByAgentid(upstreamAgentId);
		JSONObject reqObj = new JSONObject();
		reqObj.put("corpid",ss[0]);
		reqObj.put("agentid",ss[1]);
		if(business_type!=null){
			reqObj.put("business_type",business_type);
		}
		JSONObject json = WxworkHttpClientUtil.httpPost(url, reqObj, null,
				JSONObject.class);
		if (json.getIntValue("errcode") == 0) {
			return json.getString("access_token");
		} else {
			throw new Exception("获取下游企业token失败，url:"+url+"，reqObj:"+reqObj.toString()+"，json："+json.toString());
		}
	}

	public synchronized void updateCorpToken(String corpAndAgentId,
											 String accessToken,
											 int expiresInSeconds) {
		logger.debug("updateAccessToken");
		AccessToken accessTokenObj = corpTokenMap.get(corpAndAgentId);
		// 如果是第一次请求，AccessToken不存在需要新建
		if (null == accessTokenObj) {
			accessTokenObj = new AccessToken();
		}
		logger.debug(accessToken);
		accessTokenObj.setAccessToken(accessToken);
		accessTokenObj.setExpiresTime(
				System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L);
		corpTokenMap.put(corpAndAgentId, accessTokenObj);
	}

	/**
	 * 发消息给下游企业
	 * @param agentId
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject sendCorpMessage(String agentId, JSONObject paramObj)
			throws Exception {
		String token = getAccessTokenByAgentid(agentId);
		if (StringUtil.isNull(token)) {
			throw new Exception("获取不到企业互联的token");
		}
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/linkedcorp/message/send?access_token="
				+ getAccessTokenByAgentid(agentId);

		JSONObject result = WxworkHttpClientUtil.httpPost(url, paramObj, null,
				JSONObject.class);
		return result;
	}

	@Override
	public Map<String,CorpGroupAppShareInfo> getAppShareInfoMap() throws Exception {
		return getAppShareInfoMap(null);
	}

	public Map<String,CorpGroupAppShareInfo> getAppShareInfoMap(String agentIdStr) throws Exception {
		if(appShareInfoMap==null){
			if(agentIdStr==null) {
				WeixinWorkConfig config = WeixinWorkConfig.newInstance();
				agentIdStr = config.getCorpGroupAgentIds();
			}
			if(StringUtil.isNull(agentIdStr)){
				throw new Exception("企业互联 同步应用ID没有配置，请检查配置");
			}
			String[] agentIds = agentIdStr.split(";");
			Map<String,CorpGroupAppShareInfo> infoMap = new ConcurrentHashMap<>();
			for(String agentId:agentIds){
				JSONArray array = listAppShareInfo(1,agentId,null);
				for(int i=0;i<array.size();i++){
					JSONObject o = array.getJSONObject(i);
					CorpGroupAppShareInfo info = new CorpGroupAppShareInfo(o.getString("corpid"),o.getString("corp_name"),o.getString("agentid"),agentId);
					infoMap.put(o.getString("corpid"),info);
				}
			}
			appShareInfoMap = infoMap;
		}
		return appShareInfoMap;
	}

	@Override
	public void resetAppShareInfoMap() throws Exception {
		appShareInfoMap = null;
		getAppShareInfoMap();
	}

	@Override
	public void resetAppShareInfoMap(String agentIdStr) throws Exception {
		appShareInfoMap = null;
		getAppShareInfoMap(agentIdStr);
	}


	@Override
	public JSONArray getCorpChainList(String agentid) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/corpgroup/corp/get_chain_list?access_token="
				+ getAccessTokenByAgentid(agentid);

		JSONObject jsonObj = WxworkHttpClientUtil.httpGet(url,null, JSONObject.class);
		if(jsonObj.getIntValue("errcode")==0){
			return jsonObj.getJSONArray("chains");
		}
		throw new Exception("获取上下游列表信息失败，url:"+url+"，响应报文："+jsonObj.toString());
	}

	@Override
	public JSONArray getCorpChainList() throws Exception {
		WeixinWorkConfig config = WeixinWorkConfig.newInstance();
		String agentIdStr = config.getCorpGroupAgentIds();
		if(StringUtil.isNull(agentIdStr)){
			throw new Exception("企业互联 同步应用ID没有配置，请检查配置");
		}
		String[] agentIds = agentIdStr.split(";");
		JSONArray array = new JSONArray();
		for(String agentId:agentIds){
			array.addAll(getCorpChainList(agentId));
		}
		return array;
	}

	@Override
	public JSONArray getCorpChainGroup(String agentid, String chain_id) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/corpgroup/corp/get_chain_list?access_token="
				+ getAccessTokenByAgentid(agentid);
		JSONObject reqObj = new JSONObject();
		reqObj.put("chain_id",chain_id);
		JSONObject jsonObj = WxworkHttpClientUtil.httpPost(url, reqObj, null, JSONObject.class);
		if(jsonObj.getIntValue("errcode")==0){
			return jsonObj.getJSONArray("groups");
		}
		throw new Exception("获取上下游列表信息失败，url:"+url+"，请求报文："+reqObj.toString()+"，响应报文："+jsonObj.toString());
	}

	@Override
	public JSONArray getChainCorpinfoList(String agentid, String chain_id, String groupid, Integer fetch_child) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/corpgroup/corp/get_chain_corpinfo_list?access_token="
				+ getAccessTokenByAgentid(agentid);
		JSONObject reqObj = new JSONObject();
		reqObj.put("chain_id",chain_id);
		if(StringUtil.isNotNull(groupid)){
			reqObj.put("groupid",groupid);
		}
		if(fetch_child!=null){
			reqObj.put("fetch_child",fetch_child);
		}
		JSONObject jsonObj = WxworkHttpClientUtil.httpPost(url, reqObj, null, JSONObject.class);
		if(jsonObj.getIntValue("errcode")==0){
			return jsonObj.getJSONArray("group_corps");
		}
		throw new Exception("获取上下游列表信息失败，url:"+url+"，请求报文："+reqObj.toString()+"，响应报文："+jsonObj.toString());
	}


	private String getOrgSyncToken(String corpAndAgentId, Integer business_type) throws Exception {
		String accessToken = null;
		if(StringUtil.isNull(corpAndAgentId)){
			accessToken = getTodoAccessToken();
		}else{
			accessToken = getCorpTokenString(corpAndAgentId,business_type);
		}
		return accessToken;
	}

	@Override
	public JSONObject getAgent(String corpId, String agentId)
			throws Exception {
		String token = getCorpTokenString(corpId+"#"+agentId, 1);
		if (StringUtil.isNull(token)) {
			throw new Exception("获取不到下游应用的token");
		}
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/agent/get?access_token="
				+ token+"&agentid="+agentId;

		JSONObject result = WxworkHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		if(result.getIntValue("errcode")==0){
			return result;
		}
		throw new Exception("获取下游应用详情信息失败，url:"+url+"，响应报文："+result.toString());
	}

	@Override
	public JSONArray getAgentAllowUsers(String corpId, String agentId)
			throws Exception {
		JSONObject result = getAgent(corpId, agentId);
		JSONObject allow_userinfos = result.getJSONObject("allow_userinfos");
		if(allow_userinfos==null){
			return null;
		}
		return allow_userinfos.getJSONArray("user");
	}

	/**
	 * 兼容下游组织的场景
	 * @param openId
	 * @param corpId
	 * @param agentId
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject convert2Userid(String openId, String corpId, String agentId) throws Exception {
		String token = getCorpTokenString(corpId+"#"+agentId, 1);
		if (StringUtil.isNull(token)) {
			throw new Exception("获取不到下游应用的token");
		}
		String url = WxworkUtils.getWxworkApiUrl() + "/user/convert_to_userid?access_token="
				+ token;
		JSONObject paramObj = new JSONObject();
		paramObj.put("openid",openId);
		JSONObject result = WxworkHttpClientUtil.httpPost(url,paramObj, null, JSONObject.class);
		return result;
	}

	@Override
	public String getUserid(String mobile) throws Exception {
		String url = WxworkUtils.getWxworkApiUrl() + "/user/getuserid?access_token="
				+ getTodoAccessToken();
		JSONObject reqBody = new JSONObject();
		reqBody.put("mobile",mobile);
		JSONObject result = WxworkHttpClientUtil.httpPost(url,reqBody, null, JSONObject.class);
		if(result.getIntValue("errcode")==0){
			return result.getString("userid");
		}else {
			throw new Exception("获取userid失败，返回信息："+result.toString());
		}
	}

}
