package com.landray.kmss.third.ding.oms;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.landray.kmss.third.ding.dto.DingToDoList;
import com.landray.kmss.third.ding.dto.DingCalendarParam;
import com.landray.kmss.third.ding.model.api.DingCalendars;
import com.landray.kmss.third.ding.model.api.TodoCard;
import com.landray.kmss.third.ding.model.api.TodoTask;
import com.landray.kmss.third.ding.util.DingApiHttpClientUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiAttendanceVacationQuotaUpdateRequest.LeaveQuotas;
import com.dingtalk.api.request.OapiDingSendRequest.OpenDingSendVo;
import com.dingtalk.api.request.OapiProcessDeleteRequest.DeleteProcessRequest;
import com.dingtalk.api.response.OapiMicroappListResponse.Applist;
import com.dingtalk.api.response.OapiUserListbypageResponse.Userlist;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingWork;
import com.landray.kmss.third.ding.notify.interfaces.MessageSendException;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingWorkService;
import com.landray.kmss.third.ding.util.DingHttpClientUtil;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.taobao.api.ApiException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DingApiServiceImpl extends DingApiV2ServiceImpl implements DingApiService, DingConstant {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(DingApiServiceImpl.class);

	public static String admin_userId;

	private IOmsRelationService omsRelationService;

	public IOmsRelationService getOmsRelationService() {
		if (omsRelationService == null) {
			return (IOmsRelationService) SpringBeanUtil
					.getBean("omsRelationService");
		}
		return omsRelationService;
	}

	@Override
    public void messageSendOld(JSONObject message) throws Exception {

		String url = DingConstant.DING_PREFIX + "/message/send?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		log.debug("钉钉接口： url：" + url);
		DingHttpClientUtil.httpPost(url, message, "errcode", String.class);
	}
	
	@Override
    public String messageSend(Map<String, String> content, String userid,
                              String deptid, boolean toall, Long agentId, String ekpUserId)
			throws Exception {
		if (!(content.containsKey("title") && content.containsKey("content") && content.containsKey("message_url")
				&& content.containsKey("pc_message_url"))) {
			log.debug("发送的内容（标题/消息内容/移动端地址/pc端地址）有存在为空的情况导致无法发送钉钉消息");
			return "发送的内容（标题/消息内容/移动端地址/pc端地址）有存在为空的情况导致无法发送钉钉消息";
		}
		if (agentId == null) {
			log.debug("发送消息应用的Id为空，导致无法发送钉钉消息");
			return "发送消息应用的Id为空，导致无法发送钉钉消息";
		}
		if (!toall && StringUtil.isNull(userid) && StringUtil.isNull(deptid)) {
			log.debug("用户,部门, 发送全员（一天只能发送3次）必须有一个不能为空");
			return "用户,部门, 发送全员（一天只能发送3次）必须有一个不能为空";
		}
		String dingUrl = DingConstant.DING_PREFIX
				+ "/topapi/message/corpconversation/asyncsend_v2"
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);
		log.debug("【钉钉接口】发送工作通知: " + dingUrl);
		ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
		OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
		request.setUseridList(userid);
		request.setDeptIdList(deptid);
		request.setAgentId(agentId);
		request.setToAllUser(toall);
		OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
		msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
		msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
		msg.getOa().getHead().setBgcolor(content.get("color"));
		msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
		msg.getOa().getBody().setTitle(content.get("title"));
		msg.getOa().getBody().setContent(content.get("content"));
		String message_url = content.get("message_url");
		if (StringUtil.isNotNull(message_url)
				&& !message_url.contains("dingAppKey")) {
			message_url = content.get("message_url")
					+ DingUtil.getDingAppKeyByEKPUserId("&", ekpUserId);
		}
		log.debug("message_url:" + message_url);
		msg.getOa().setMessageUrl(message_url);
		String pc_message_url = content.get("pc_message_url");
		if (StringUtil.isNotNull(pc_message_url)
				&& !pc_message_url.contains("dingAppKey")) {
			pc_message_url = content.get("pc_message_url")
					+ DingUtil.getDingAppKeyByEKPUserId("&", ekpUserId);
		}
		log.debug("pc_message_url:" + pc_message_url);
		msg.getOa().setPcMessageUrl(pc_message_url);
		msg.setMsgtype("oa");
		request.setMsg(msg);
		log.error(JSONObject.fromObject(request).toString());
		OapiMessageCorpconversationAsyncsendV2Response response = client
				.execute(request, getAccessToken(String.valueOf(agentId)));
		//全员推送标识+1
		if(toall && response.isSuccess()){
			DingOmsConfig dingOmsConfig = new DingOmsConfig();
			dingOmsConfig.addHadSendAllNotifyNums();
			dingOmsConfig.save();
		}
		return response.getBody();
	}
	
	@Override
    public String messageSend(String content, String userid, String deptid,
                              boolean toall, Long agentId, String ekpUserId) throws Exception {
		if (StringUtil.isNull(content)) {
			log.debug("发送的内容（标题）有存在为空的情况导致无法发送钉钉消息");
			return "发送的内容（标题）有存在为空的情况导致无法发送钉钉消息";
		}
		if (agentId == null) {
			log.debug("发送消息应用的Id为空，导致无法发送钉钉消息");
			return "发送消息应用的Id为空，导致无法发送钉钉消息";
		}
		if (!toall && StringUtil.isNull(userid) && StringUtil.isNull(deptid)) {
			log.debug("用户,部门, 发送全员（一天只能发送3次）必须有一个不能为空");
			return "用户,部门, 发送全员（一天只能发送3次）必须有一个不能为空";
		}
		String dingUrl = DingConstant.DING_PREFIX
				+ "/topapi/message/corpconversation/asyncsend_v2"
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);
		log.debug("【钉钉接口】发送工作通知 ekpUserId： " + ekpUserId + "    dingUrl: "
				+ dingUrl);
		ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
		OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
		request.setUseridList(userid);
		request.setDeptIdList(deptid);
		request.setAgentId(agentId);
		request.setToAllUser(toall);
		OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
		msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
		msg.getText().setContent(content);
		msg.setMsgtype("text");
		request.setMsg(msg);
		OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, getAccessToken());
		//全员推送标识+1
		if(toall && response.isSuccess()){
			DingOmsConfig dingOmsConfig = new DingOmsConfig();
			dingOmsConfig.addHadSendAllNotifyNums();
			dingOmsConfig.save();
		}
		return response.getBody();
	}

	@Override
    public JSONObject departCreate(JSONObject depart) throws Exception {
		String url = DingConstant.DING_PREFIX + "/department/create?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		return DingHttpClientUtil.httpPost(url, depart, null, JSONObject.class);
	}

	@Override
    public String departUpdate(JSONObject group) throws Exception {
		String url = DingConstant.DING_PREFIX + "/department/update?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		return DingHttpClientUtil.httpPost(url, group, "errmsg", String.class);
	}

	@Override
    public String departDelete(String departId) throws Exception {
		if (StringUtil.isNotNull(departId)) {
			departId = departId.trim();
		}
		String url = DingConstant.DING_PREFIX + "/department/delete?access_token="
				+ getAccessToken() + "&id=" + departId
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		return DingHttpClientUtil.httpGet(url, "errmsg", String.class);
	}

	@Override
	public JSONObject departGet(Long departId) throws Exception {
		String url = DingConstant.DING_PREFIX + "/department/get?access_token="
				+ getAccessToken() + "&id=" + departId
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		return DingHttpClientUtil.httpGet(url, null, JSONObject.class);
	}

	@Override
    public JSONObject departGet() throws Exception {
		String url = DingConstant.DING_PREFIX + "/department/list?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		return DingHttpClientUtil.httpGet(url, null,
				JSONObject.class);
	}
	
	@Override
    public JSONObject departsGet(String departId) throws Exception {
		String url = DingConstant.DING_PREFIX + "/department/list?access_token="
				+ getAccessToken() + "&id=" + departId
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		return DingHttpClientUtil.httpGet(url, null, JSONObject.class);
	}
	
	// 钉钉现接口 fetch_child参数为boolean,用到这个api需要验证该功能是否正常
	@Override
    public JSONObject departsSubGet(String departId) throws Exception {
		String url = DingConstant.DING_PREFIX + "/department/list?access_token="
				+ getAccessToken() + "&id=" + departId + "&fetch_child=0"
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		return DingHttpClientUtil.httpGet(url, null, JSONObject.class);
	}

	@Override
	public JSONObject userCreate(JSONObject user) throws Exception {
		String url = DingConstant.DING_PREFIX + "/topapi/v2/user/create?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		return DingHttpClientUtil.httpPost(url, user, null, JSONObject.class);
	}

	@Override
	public String userUpdate(JSONObject user) throws Exception {
		String url = DingConstant.DING_PREFIX + "/topapi/v2/user/update?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		return DingHttpClientUtil.httpPost(url, user, "errmsg", String.class);
	}

	@Override
	public String userDelete(String userid) throws Exception {
		if (StringUtil.isNotNull(userid)) {
			userid = userid.trim();
		}
		String url = DingConstant.DING_PREFIX + "/user/delete?access_token="
				+ getAccessToken() + "&userid=" + userid
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		return DingHttpClientUtil.httpGet(url, "errmsg", String.class);
	}

	@Override
	public JSONObject userGet(String userid, String ekpUserId)
			throws Exception {
		if (StringUtil.isNotNull(userid)) {
			userid = userid.trim();
		}
		String url = DingConstant.DING_PREFIX + "/user/get?access_token="
				+ getAccessToken() + "&userid=" + userid
				+ DingUtil.getDingAppKeyByEKPUserId("&", ekpUserId);
		return DingHttpClientUtil.httpGet(url, null, JSONObject.class);
	}

	@Override
	public JSONObject userGet_v2(String userid, String ekpUserId)
			throws Exception {
		if (StringUtil.isNotNull(userid)) {
			userid = userid.trim();
		}
		String url = DingConstant.DING_PREFIX + "/topapi/v2/user/get?access_token="
				+ getAccessToken() + "&userid=" + userid
				+ DingUtil.getDingAppKeyByEKPUserId("&", ekpUserId);
		JSONObject req = new JSONObject();
		req.put("userid",userid);
		return DingHttpClientUtil.httpPost(url, req, null,JSONObject.class);
	}

	// 钉钉现接口 fetch_child参数为boolean,用到这个api需要验证该功能是否正常
	@Override
    public JSONObject userListOld(Integer departId, Boolean fetchChild)
			throws Exception {
		String url = DingConstant.DING_PREFIX + "/user/list?access_token="
				+ getAccessToken() + "&department_id=" + departId
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		if (fetchChild) {
			url += "&fetch_child=1";
		} else {
			url += "&fetch_child=0";
		}
		return DingHttpClientUtil.httpGet(url, null, JSONObject.class);
	}
	
	// 钉钉现接口 fetch_child参数为boolean,用到这个api需要验证该功能是否正常
	@Override
    public JSONObject departGetUsers(Integer departId, Boolean fetchChild)
			throws Exception {
		String url = DingConstant.DING_PREFIX + "/user/simplelist?access_token="
				+ getAccessToken() + "&department_id=" + departId
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		if (fetchChild) {
			url += "&fetch_child=1";
		} else {
			url += "&fetch_child=0";
		}
		return DingHttpClientUtil.httpGet(url, null, JSONObject.class);
	}

	@Override
    public String sign(String ticket, String nonceStr, long timeStamp,
                       String url) throws Exception {
		String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr
				+ "&timestamp=" + String.valueOf(timeStamp) + "&url=" + url;
		try {
			log.debug("plain:"+plain);
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
    public String getConfig(String urlString, String queryString) {
		String url = urlString;
		if(StringUtil.isNotNull(queryString)){
			 url = urlString + "?" + queryString;
		}
		String nonceStr = "abcdefg";
		long timeStamp = System.currentTimeMillis() / 1000;
		log.debug("鉴权url:" + url);
		String signedUrl = decodeUrl(url);
		log.debug("signedUrl:" + signedUrl);
		String ticket = null;
		String signature = null;
		try {
			log.debug("getJsapiTicket start");
			ticket = getJsapiTicket();
			log.debug("getJsapiTicket end");
			log.debug("sign start");
			signature = sign(ticket, nonceStr, timeStamp, signedUrl);
			log.debug("sign end");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject result = new JSONObject();

		log.debug("DingConfig.newInstance() start");
		DingConfig config = DingConfig.newInstance();
		log.debug("DingConfig.newInstance() end");
		String dev = config.getDevModel();
		result.put("corpId", DingUtil.getCorpId());
		log.debug("DingConfig.newInstance().getValue end");
		if("1".equals(dev)){
			result.put("appId", "");//老secret的方式
		}else if("2".equals(dev)){
			result.put("appId", DingUtil.getAgentIdByCorpId(null));// appkey和appsecret
		}else{
			result.put("appId", DingUtil.getAgentIdByCorpId(null));// customkey和customsecret
		}
		result.put("nonceStr", nonceStr);
		result.put("signature", signature);
		result.put("timeStamp", timeStamp);
		result.put("url", signedUrl);
		log.info(result.toString());
		return result.toString();
	}

	/**
	 * 因为ios端上传递的url是encode过的，android是原始的url。开发者使用的也是原始url,
	 * 所以需要把参数进行一般urlDecode
	 *
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private static String decodeUrl(String url){
		try {
			URL urler = new URL(url);
			StringBuilder urlBuffer = new StringBuilder();
			urlBuffer.append(urler.getProtocol());
			urlBuffer.append(":");
			if (urler.getAuthority() != null && urler.getAuthority().length() > 0) {
				urlBuffer.append("//");
				urlBuffer.append(urler.getAuthority());
			}
			if (urler.getPath() != null) {
				urlBuffer.append(urler.getPath());
			}
			if (urler.getQuery() != null) {
				urlBuffer.append('?');
				urlBuffer.append(URLDecoder.decode(urler.getQuery(), "utf-8"));
			}
			return urlBuffer.toString();
		} catch (Exception e) {
			log.warn(e.getMessage(),e);
		}
		return url;
	}

	/**
	 * 获取钉钉后台管理免登的accessToken
	 */
	private String getSSOToken() throws Exception {
		DingTalkClient client = new DefaultDingTalkClient(DingConstant.DING_PREFIX + "/sso/gettoken");
		OapiSsoGettokenRequest request = new OapiSsoGettokenRequest();
		request.setCorpid(DingUtil.getCorpId());
		String dingmngSSOSecret = DingConfig.newInstance().getDingmngSSOSecret();
		if (StringUtil.isNull(dingmngSSOSecret)) {
			throw new IllegalArgumentException("钉钉后台管理单点登录请配置SSOSecret（EKP管理后台>移动办公>钉钉入口>登录配置>SSOSecret）");
		}
		request.setCorpsecret(dingmngSSOSecret);
		request.setHttpMethod("GET");
		OapiSsoGettokenResponse response = client.execute(request);
		return response.getAccessToken();
	}

	/**
	 * 获取钉钉后台管理免登的用户信息
	 */
	@Override
    public JSONObject getSSOUserInfo(String code, String dingAppKey)
			throws Exception {
		String url = DingConstant.DING_PREFIX + "/sso/getuserinfo?"
				+ "access_token=" + getSSOToken_ISVOrCommom() + "&code=" + code
				+ "&dingAppKey=" + dingAppKey;
		return DingHttpClientUtil.httpGet(url, null, JSONObject.class);
	}

	public String getSSOToken_ISVOrCommom() throws Exception {

		String addAppKey = ResourceUtil
				.getKmssConfigString("kmss.ding.addAppKey");
		if (StringUtil.isNotNull(addAppKey)
				&& "true".equalsIgnoreCase(addAppKey)) {
			// ISV demo环境
			log.debug("ISV demo环境获取getSSOToken");
			return getAccessToken();
		}
		return getSSOToken();
	}

	@Override
    public JSONObject getUserInfo(String code) throws Exception {
		String url = DingConstant.DING_PREFIX + "/user/getuserinfo?"
				+ "access_token=" + getAccessToken() + "&code=" + code
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		JSONObject response = DingHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		return response;
	}

	@Override
	public JSONObject getUserInfoByDingAppKey(String code, String dingAppKey)
			throws Exception {
		String url = DingConstant.DING_PREFIX + "/user/getuserinfo?"
				+ "access_token=" + getAccessToken() + "&code=" + code
				+ "&dingAppKey=" + dingAppKey;
		JSONObject response = DingHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		return response;
	}

	@Override
    public JSONObject upload(String type, File file) throws Exception {
		String url = DingConstant.DING_PREFIX + "/media/upload?" + "access_token="
				+ getAccessToken() + "&type=" + type
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		log.debug("钉钉接口：" + url);
		String ret = DingHttpClientUtil.uploadMedia(url, file);
		if (StringUtil.isNull(ret)) {
			return null;
		}
		JSONObject response = JSONObject.fromObject(ret);
		return response;
	}

	@Override
    public JSONObject appCreate(JSONObject app) throws Exception {
		String url = DingConstant.DING_PREFIX + "/microapp/create?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		return DingHttpClientUtil.httpPost(url, app, null, JSONObject.class);
	}

	@Override
	public JSONObject getAdmin() throws Exception {
		String url = DingConstant.DING_PREFIX + "/user/get_admin?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		return DingHttpClientUtil.httpGet(url, null, JSONObject.class);
	}

	@Override
	public JSONObject getApps() throws Exception {
		String url = DingConstant.DING_PREFIX + "/microapp/list?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		return DingHttpClientUtil.httpPost(url, null, null, JSONObject.class);
	}

	@Override
	public JSONObject appUpdate(JSONObject app) throws Exception {
		String url = DingConstant.DING_PREFIX + "/microapp/update?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		return DingHttpClientUtil.httpPost(url, app, null, JSONObject.class);
	}

	@Override
	public JSONObject appDelete(String agentId) throws Exception {
		String url = DingConstant.DING_PREFIX + "/microapp/delete?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		JSONObject json = new JSONObject();
		json.put("agentId", agentId);
		return DingHttpClientUtil.httpPost(url, json, null, JSONObject.class);
	}

	@Override
	public JSONObject appVisible(String agentId) throws Exception {
		String url = DingConstant.DING_PREFIX + "/microapp/visible_scopes?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		JSONObject json = new JSONObject();
		json.put("agentId", agentId);
		return DingHttpClientUtil.httpPost(url, json, null, JSONObject.class);
	}

	private String[] cbTag = { "user_add_org", "user_leave_org",
			"user_modify_org", "user_active_org",
			 "org_admin_add", "org_admin_remove", "org_dept_create",
			"org_dept_modify", "org_dept_remove" };

	@Override
    public OapiCallBackGetCallBackResponse queryCallBackEvent(String token) throws Exception {
		String url = DingConstant.DING_PREFIX + "/call_back/get_call_back"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		log.debug("钉钉接口 url:" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiCallBackGetCallBackRequest request = new OapiCallBackGetCallBackRequest();
		request.setTopHttpMethod("GET");
		OapiCallBackGetCallBackResponse response = client.execute(request, token);
		log.debug("查询事件回调接口：" + response.getBody() );
		return response;
	}


	@Override
	public boolean delCallBackEvent(String token) throws Exception {
		String url = DingConstant.DING_PREFIX + "/call_back/delete_call_back"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		log.debug("钉钉接口 url:" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiCallBackDeleteCallBackRequest request = new OapiCallBackDeleteCallBackRequest();
		request.setTopHttpMethod("GET");
		OapiCallBackDeleteCallBackResponse response = client.execute(request, token);
		log.debug("删除事件回调接口：" + response.getBody());
		if (response.getErrcode() == 0) {
			// 删除成功
			DingConfig dingConfig = new DingConfig();
			dingConfig.setDingCallback("-11");
			dingConfig.setDingCallbackErro("");
			dingConfig.setOldDingCallbackurl("");
			dingConfig.save();
			return true;
		} else {
			// 删除失败
			DingConfig dingConfig = new DingConfig();
			dingConfig.setDingCallback("-1");
			dingConfig.setDingCallbackErro(response.getBody());
			dingConfig.save();
			log.debug("回调接口注册失败！回调url:" + dingConfig.getDingCallbackurl());
			return false;
		}

	}

	private void registerCallBackEvent(String token, DingConfig config, List<String> tags) throws Exception {
		String url = DingConstant.DING_PREFIX + "/call_back/register_call_back"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		log.debug("钉钉接口 url:" + url);

		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiCallBackRegisterCallBackRequest request = new OapiCallBackRegisterCallBackRequest();
		String callbackUrl = config.getDingCallbackurl();
		if (StringUtil.isNotNull(callbackUrl)
				&& callbackUrl.trim().startsWith("/")) {
			// 配置页面没有配域名，取admin.do的域名
			String domainName = ResourceUtil
					.getKmssConfigString("kmss.urlPrefix");
			if (StringUtil.isNotNull(domainName)) {
				if (domainName.trim().endsWith("/")) {
					domainName = domainName.substring(0,
							domainName.length() - 1);
				}
				callbackUrl = domainName + callbackUrl;
			}
		}
		log.debug("将注册的回调：" + callbackUrl);
		request.setUrl(callbackUrl);
		request.setAesKey(config.getDingAeskey());
		request.setToken(config.getDingToken());
		request.setCallBackTag(tags);
		OapiCallBackRegisterCallBackResponse response = client.execute(request, token);
	
		if (response.getErrcode() == 0) {

			DingConfig dingConfig = new DingConfig();
			dingConfig.setDingCallback("1");
			dingConfig.setDingCallbackErro("");
			dingConfig.setOldDingCallbackurl("");
			dingConfig.save();
			log.debug("注册事件回调接口：" + response.getBody() + ",注册的事件标识有：" + tags);

			// 回调成功
		} else {
			DingConfig dingConfig = new DingConfig();
			dingConfig.setDingCallback("2");
			dingConfig.setDingCallbackErro(response.getBody());
			dingConfig.save();
			log.debug("回调接口注册失败！原因:" + response.getBody());
			log.debug("回调接口注册失败！url:" + config.getDingCallbackurl());
			// 回调失败

		}
	}

	private OapiCallBackUpdateCallBackResponse updateCallBackEvent(String token, DingConfig config, List<String> tags) throws Exception {

		String url = DingConstant.DING_PREFIX + "/call_back/update_call_back"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		log.debug("钉钉接口 url:" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiCallBackUpdateCallBackRequest request = new OapiCallBackUpdateCallBackRequest();
		// String url = config.getDingCallbackurl();
		String callbackUrl = config.getDingCallbackurl();
		if (StringUtil.isNotNull(callbackUrl)
				&& callbackUrl.trim().startsWith("/")) {
			// 配置页面没有配域名，取admin.do的域名
			String domainName = ResourceUtil
					.getKmssConfigString("kmss.urlPrefix");
			if (StringUtil.isNotNull(domainName)) {
				if (domainName.trim().endsWith("/")) {
					domainName = domainName.substring(0,
							domainName.length() - 1);
				}
				callbackUrl = domainName + callbackUrl;
			}
		}
		log.debug("将注册的回调：" + callbackUrl);
		request.setUrl(callbackUrl);
		request.setAesKey(config.getDingAeskey());
		request.setToken(config.getDingToken());
		request.setCallBackTag(tags);
		OapiCallBackUpdateCallBackResponse response = client.execute(request, token);
		log.debug("更新注册事件接口：" + response.getBody() + ",更新的事件标识有：" + tags);
		if (response.getErrcode() != 0) {
			// 更新失败
			DingConfig dingConfig = new DingConfig();
			dingConfig.setDingCallback("2");
			dingConfig.setDingCallbackErro(response.getBody());
			dingConfig.save();
			log.debug("回调接口更新失败！url:" + config.getDingCallbackurl());
		} else {
			DingConfig dingConfig = new DingConfig();
			dingConfig.setDingCallback("1");
			dingConfig.setDingCallbackErro("");
			dingConfig.setOldDingCallbackurl("");
			dingConfig.save();
			log.debug("更新注册事件成功！url:" + config.getDingCallbackurl());
		}
		return response;
	}

	private void managerCallBackEvent(String token, DingConfig config, List<String> tags) throws Exception {
		OapiCallBackGetCallBackResponse response = queryCallBackEvent(token);
		if (response.getErrcode() == 0) {
			//88674 提交配置注册回调时若钉钉端已注册了其他域名的回调事件则停止注册，改为配置页手动注册
			//获取域名判断域名
			String callbackUrl = config.getDingCallbackurl();
			if (StringUtil.isNotNull(callbackUrl)
					&& callbackUrl.trim().startsWith("/")) {
				// 配置页面没有配域名，取admin.do的域名
				String domainName = ResourceUtil
						.getKmssConfigString("kmss.urlPrefix");
				if (StringUtil.isNotNull(domainName)) {
					if (domainName.trim().endsWith("/")) {
						domainName = domainName.substring(0,
								domainName.length() - 1);
					}
					callbackUrl = domainName + callbackUrl;
				}
			}
			if (!response.getUrl().equals(callbackUrl)) {
				DingConfig dingConfig = new DingConfig();
				dingConfig.setDingCallback("-2");
				dingConfig.setOldDingCallbackurl(response.getUrl());
				dingConfig.save();
			}else {
				OapiCallBackUpdateCallBackResponse uresponse = updateCallBackEvent(token, config, tags);
				if (uresponse.getErrcode() != 0) {
					delCallBackEvent(token);
					registerCallBackEvent(token, config, tags);
				} else {
					DingConfig dingConfig = new DingConfig();
					dingConfig.setDingCallback("1");
					dingConfig.save();
				}
			}
		} else if (response.getErrcode() == 71007) {
			// 不存在重新注册
			registerCallBackEvent(token, config, tags);
		} else {
			DingConfig dingConfig = new DingConfig();
			dingConfig.setDingCallbackErro(response.getBody());
			dingConfig.save();
			//88674 提交配置注册回调时若钉钉端已注册了其他域名的回调事件则停止注册，改为配置页手动注册
			//delCallBackEvent(token);
			//registerCallBackEvent(token, config, tags);
		}
	}
	
	@Override
	public void eventManager() throws Exception {
		try {
			DingConfig config = DingConfig.newInstance();
			if (!"true".equals(config.getDingEnabled())) {
				log.debug("未开启钉钉集成功能,不注册钉钉回调事件");
				return;
			}
			// 钉钉事件类型
			List<String> rtnTag = new ArrayList<String>();
			// 判断是否是f4
			String[] newCbTag;
			if (StringUtil.isNotNull(ResourceUtil.getKmssConfigString("kmss.ding.proxy"))) {
				// f4
				newCbTag = new String[cbTag.length + 2];
				for (int i = 0; i < cbTag.length; i++) {
					newCbTag[i] = cbTag[i];
				}
				newCbTag[cbTag.length] = "user_role_change";
				newCbTag[cbTag.length + 1] = "user_dept_change";
			} else {
				newCbTag = cbTag.clone();
			}

			// 钉钉到EKP //兼容历史数据 如果config.getSyncSelection()是空说明升级后没有保存过使用之前的参数
			if (StringUtil.isNotNull(config.getSyncSelection()) && "2".equals(config.getSyncSelection()) || "true".equals(config.getDingOmsInEnabled())) {
				for (String tag : newCbTag) {
					if (!rtnTag.contains(tag)) {
						rtnTag.add(tag);
					}
				}
				// 角色回调
				rtnTag.add("label_user_change"); // 员工角色信息发生变更
				rtnTag.add("label_conf_add"); // 增加角色或者角色组
				rtnTag.add("label_conf_del"); // 删除角色或者角色组
				rtnTag.add("label_conf_modify"); // 修改角色或者角色组
				rtnTag.add("label_user_scope_change"); // 修改角色控制范围
			} else {
				// EKP到钉钉，移除所有的
				for (String tag : newCbTag) {
					rtnTag.remove(tag);
				}
				// 开启生态组织实时同步
				if (SysOrgEcoUtil.IS_ENABLED_ECO
						&& "true".equals(config.getDingOmsExternal())) {
					rtnTag.add("user_add_org");
				}
			}
			// 手机号回调开关
			if ((StringUtil.isNotNull(config.getSyncSelection())
					&& "1".equals(config.getSyncSelection()))
					|| ("true".equals(config.getDingOmsOutEnabled())&& "true".equals(config.getDingMobileEnabled()))) {
				if (!rtnTag.contains("user_modify_org")) {
					rtnTag.add("user_modify_org");
				}
				if (!rtnTag.contains("user_active_org")) {
					rtnTag.add("user_active_org");
				}
			}

			//人员退出企业监听
			if ((StringUtil.isNotNull(config.getSyncSelection())&& "1".equals(config.getSyncSelection()))) {
				if("true".equalsIgnoreCase(config.getUserLeaveListenerEnable())) {
                    if (!rtnTag.contains("user_leave_org")) {
                        rtnTag.add("user_leave_org");
                    }
                }
			}

			// 群事件
			rtnTag.add("chat_update_title");
			rtnTag.add("chat_disband");

			if (rtnTag != null && rtnTag.size() > 0) {
				// 钉钉到EKP
				if (StringUtil.isNull(config.getDingToken()) || StringUtil.isNull(config.getDingAeskey())) {
					log.debug("钉钉集成中token或aeskey为空,无法注册钉钉回调事件");
				} else {
					log.debug("钉钉的所有注册事件类型：" + rtnTag);
					managerCallBackEvent(getAccessToken(), config, rtnTag);
				}
			} else {
				OapiCallBackGetCallBackResponse response = queryCallBackEvent(getAccessToken());
				if (response.getErrcode() == 0) {
					if(response.getUrl().equals(config.getDingCallbackurl()) ){
						log.debug("注册地址相同则删除回调：" + rtnTag);
						delCallBackEvent(getAccessToken());
					} else {
						log.debug("注册地址不相同不做处理，钉钉已经注册的地址=" + response.getUrl());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("钉钉集成事件回调管理异常");
		}
	}

	@Override
	public Map<String, String> attendInfo() throws Exception {
		DingConfig config = DingConfig.newInstance();
		Map<String,String> map = new HashMap<String, String>();
		map.put("corpid", "");
		map.put("agentid", "");
		if("true".equals(config.getDingEnabled())){

			String url = DingConstant.DING_PREFIX + "/microapp/list"
					+ DingUtil.getDingAppKeyByEKPUserId("?", null);
			log.debug("钉钉考勤打卡接口：" + url);
			ThirdDingTalkClient client = new ThirdDingTalkClient(url);
			OapiMicroappListRequest req = new OapiMicroappListRequest();
			OapiMicroappListResponse res = client.execute(req,getAccessToken());
			map.put("corpid", DingUtil.getCorpId());
			List<Applist> apps = res.getAppList();
			for(Applist app:apps){
				if(!app.getIsSelf()&&app.getAppStatus()==1&&"考勤打卡".equals(app.getName())){
					map.put("agentid", app.getAgentId().toString());
					break;
				}
			}
		}else{
			log.debug("钉钉集成模块未开启，无法获取corpid和agentid");
		}
		return map;
	}

	@Override
	public void userList_v2(List<OapiV2UserListResponse.ListUserResponse> users, Long departId, Long cursor) throws Exception {
		if (users == null) {
            users = new ArrayList<OapiV2UserListResponse.ListUserResponse>();
        }
		String url = DingConstant.DING_PREFIX + "/topapi/v2/user/list"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		log.debug("钉钉接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiV2UserListRequest req = new OapiV2UserListRequest();
		req.setDeptId(departId);
		req.setCursor(cursor);
		req.setSize(100L);
		req.setOrderField("modify_desc");
		req.setContainAccessLimit(false);
		req.setLanguage("zh_CN");
		OapiV2UserListResponse res = client.execute(req,getAccessToken());

		if(res.isSuccess()){
			users.addAll(res.getResult().getList());
			if(res.getResult().getHasMore()){
				userList_v2(users, departId, res.getResult().getNextCursor());
			}
		}else{
			log.debug("获取钉钉部门下面的人员异常："+res.getBody());
			throw new Exception(
					"调用/user/listbypage失败，部门ID为：" + departId + "，响应信息："
							+ com.alibaba.fastjson.JSONObject
							.toJSONString(res));
		}
	}

	//1.0接口-获取部门用户详情用户备份
	@Override
	public void userList(List<Userlist> users, Long departId, Long page) throws Exception {
		if (users == null) {
            users = new ArrayList<Userlist>();
        }
		String url = DingConstant.DING_PREFIX + "/user/listbypage"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		log.debug("钉钉接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiUserListbypageRequest request = new OapiUserListbypageRequest();
		request.setDepartmentId(departId);
		request.setOffset(page*80);
		request.setSize(80L);
		request.setOrder("entry_desc");
		request.setHttpMethod("GET");
		OapiUserListbypageResponse res = client.execute(request,getAccessToken());
		if(res.isSuccess()){
			users.addAll(res.getUserlist());
			if(res.getHasMore()){
				userList(users, departId, ++page);
			}
		}else{
			log.debug("获取钉钉部门下面的人员异常："+res.getBody());
			throw new Exception(
					"调用/user/listbypage失败，部门ID为：" + departId + "，响应信息："
							+ com.alibaba.fastjson.JSONObject
							.toJSONString(res));
		}
	}


	@Override
	public JSONObject preCalcuateDate(JSONObject param, String ekpUserId)
			throws Exception {
		String url = DingConstant.DING_PREFIX + "/topapi/attendance/approve/duration/calculate?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", ekpUserId);
		log.warn("调用钉钉预计算时长接口：url=" + url);
		return DingHttpClientUtil.httpPost(url, param, null, JSONObject.class);
	}

	@Override
	public JSONObject approveFinish(JSONObject param, String ekpUserId)
			throws Exception {
		String url = DingConstant.DING_PREFIX + "/topapi/attendance/approve/finish?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", ekpUserId);
		log.warn("调用钉钉审批通过接口：url=" + url);
		return DingHttpClientUtil.httpPost(url, param, null, JSONObject.class);
	}

	@Override
	public JSONObject approveCanel(JSONObject param, String ekpUserId)
			throws Exception {
		String url = DingConstant.DING_PREFIX + "/topapi/attendance/approve/cancel?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", ekpUserId);
		log.warn("调用钉钉销假接口：url=" + url);
		return DingHttpClientUtil.httpPost(url, param, null, JSONObject.class);
	}

	@Override
	public JSONObject scheduleByDay(JSONObject param, String ekpUserId)
			throws Exception {
		String url = DingConstant.DING_PREFIX + "/topapi/attendance/schedule/listbyday?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", ekpUserId);
		log.warn("调用钉钉查询排班接口：url=" + url);
		return DingHttpClientUtil.httpPost(url, param, null, JSONObject.class);
	}

	@Override
	public JSONObject approveCheck(JSONObject param, String ekpUserId)
			throws Exception {
		String url = DingConstant.DING_PREFIX + "/topapi/attendance/approve/check?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", ekpUserId);
		log.warn("调用钉钉补卡接口：url=" + url);
		return DingHttpClientUtil.httpPost(url, param, null, JSONObject.class);
	}

	@Override
	public JSONObject approveSwitch(JSONObject param, String ekpUserId)
			throws Exception {
		String url = DingConstant.DING_PREFIX + "/topapi/attendance/approve/schedule/switch?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", ekpUserId);
		log.warn("调用钉钉换班接口：url=" + url);
		return DingHttpClientUtil.httpPost(url, param, null, JSONObject.class);
	}
	
	public String getDomain(String url){
		int index=url.indexOf("/");
        //根据第一个点的位置 获得第二个点的位置
        index=url.indexOf("/", index+1);
        index=url.indexOf("/", index+1);
        index=url.indexOf("/", index+1);
        String result=url.substring(0,index);
        return result;
	}

	@Override
	public OapiCallBackGetCallBackFailedResultResponse getCallBackFailedResult(String token) throws Exception {
		String url = DingConstant.DING_PREFIX
				+ "/call_back/get_call_back_failed_result"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiCallBackGetCallBackFailedResultRequest request = new OapiCallBackGetCallBackFailedResultRequest();
		request.setTopHttpMethod("GET");
		OapiCallBackGetCallBackFailedResultResponse response = client.execute(request, token);
		log.debug("获取回调失败的结果接口：" + response.getBody());
		if (response.getErrcode() != 0) {
			log.debug("获取回调失败的结果接口失败！"+response.getErrmsg());
		}
		return response;
		
		
	} 
		
	@Override
	public JSONObject getDeptParents(String deptId) throws Exception {
		String url = DingConstant.DING_PREFIX + "/department/list_parent_depts_by_dept?access_token="
				+ getAccessToken() + "&id=" + deptId
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		return DingHttpClientUtil.httpGet(url, null, JSONObject.class);
	}

	@Override
	public JSONObject getPersonParents(String userId) throws Exception {
		String url = DingConstant.DING_PREFIX + "/department/list_parent_depts?access_token="
				+ getAccessToken() + "&userId=" + userId
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		return DingHttpClientUtil.httpGet(url, null, JSONObject.class);
	}

	@Override
	public String sendDING(String text_content,
			List<String> receiver, String appName) throws Exception {

		// String url = DingConstant.DING_PREFIX
		// + "/topapi/ding/send?access_token="
		// + getAccessToken();
		String ekpUserId = ""; // 用于F4获取dingAppKey
		if (StringUtil.isNull(text_content)) {
			log.error("DING标题不能为空！");
			return null;
		}
		if (receiver == null || receiver.size() == 0) {
			log.error("DING接收人不能为空！");
			return null;
		}

		String agentId = getAgentIdByAppName(appName);
		log.debug("发送DING的agentId：" + agentId);
		OapiDingSendRequest req = new OapiDingSendRequest();
		OpenDingSendVo obj1 = new OpenDingSendVo();
		obj1.setTextContent(text_content);

		IOmsRelationService omsRelationService = getOmsRelationService();

		List<String> ding_id = new ArrayList<String>();
		for (String ekpId : receiver) {
			String id = omsRelationService.getDingUserIdByEkpUserId(ekpId);
			if (StringUtil.isNotNull(id)) {
				ekpUserId = ekpId;
				ding_id.add(id);
			} else {
				log.warn("根据EKP人员id:" + ekpId + " 在钉钉对照表无法找到对应的关系！");
			}
		}
		log.debug("ding_id：" + ding_id + "  ekpUserId(F4):" + ekpUserId);
		String url = DingConstant.DING_PREFIX
				+ "/topapi/ding/send"
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);
		log.debug("钉钉接口 url:" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		obj1.setReceiverUids(ding_id);
		obj1.setRemindType(1L);
		req.setOpenDingSendVo(obj1);
		OapiDingSendResponse rsp = client.execute(req, getAccessToken(agentId));

		try {
			JSONObject rs = JSONObject.fromObject(rsp.getBody());
			Long errcode = rsp.getErrcode();
			log.debug("---errcode:" + errcode);
			if (errcode == null || errcode == 0) {
				rs.put("errcode", 0);
			} else {
				rs.put("errcode", rsp.getErrcode());
				rs.put("errmsg", rsp.getErrmsg());
			}

			log.debug("---rs:" + rs.toString());
			return rs.toString();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		rsp.getErrcode();
		log.debug(rsp.getBody());
		return rsp.getBody();
	}

	@Override
	public String getDingCustomInfo() throws Exception {
		// 先获取管理员
		JSONObject admins = getAdmin();
		if (admins != null && admins.getInt("errcode") == 0) {
			log.debug("Ding管理员信息获取成功！");
			JSONArray ja = admins.getJSONArray("adminList");
			if (ja != null && ja.size() > 0) {
				String admin1 = ja.getJSONObject(0).getString("userid");
				log.debug("其中管理员的id:" + admin1);
				if (StringUtil.isNotNull(admin1)) {
					String url = DingConstant.DING_PREFIX
							+ "/topapi/smartwork/hrm/employee/list"
							+ DingUtil.getDingAppKeyByEKPUserId("?", null);
					log.debug("钉钉接口 url" + url);
					ThirdDingTalkClient client = new ThirdDingTalkClient(url);
					OapiSmartworkHrmEmployeeListRequest req = new OapiSmartworkHrmEmployeeListRequest();
					req.setUseridList(admin1);
					req.setFieldFilterList("customField");
					OapiSmartworkHrmEmployeeListResponse rsp = client
							.execute(req, getAccessToken());
					if (rsp.getErrcode() == 0) {
						log.debug("自定义数据：" + rsp.getBody());
					} else {
						log.error("自定义数据失败：" + rsp.getBody());
					}
					return rsp.getBody();
				}
			}

		}
		return null;
	}

	@Override
	public String sendWorkNotify(
			OapiMessageCorpconversationAsyncsendV2Request.Msg msg,
			List userid_list,
			List dept_id_list, boolean to_all_user, String agentId)
			throws Exception {
		// String
		if (msg == null) {
			log.error("工作通知内容不能为空");
			return "error:工作通知内容msg不能为空";
		}


		IOmsRelationService omsRelationService = getOmsRelationService();
		String userList = "";
		String deptList = "";

		if (userid_list != null && userid_list.size() > 0) {
			if (userid_list.size() > 100) {
				log.warn("error:发送钉钉工作通知的人员列表userid_list不能超过100人");
				return "error:发送钉钉工作通知的人员列表userid_list不能超过100人";
			} else {
				userList = findDingIdsByEkpList(userid_list);
			}
		}

		if (dept_id_list != null && dept_id_list.size() > 0) {
			if (dept_id_list.size() > 20) {
				log.warn("error:发送钉钉工作通知的部门列表dept_id_list不能超过20");
				return "error:发送钉钉工作通知的部门列表dept_id_list不能超过20";
			} else {
				deptList = findDingIdsByEkpList(dept_id_list);
			}
		}
		log.debug("userList:" + userList);
		log.debug("deptList:" + deptList);
		String ekpUserId = findEkpUserId(userid_list, dept_id_list);

		String url = DingConstant.DING_PREFIX
				+ "/topapi/message/corpconversation/asyncsend_v2"
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);
		log.debug("钉钉接口  url:" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
		request.setMsg(msg);
		if (to_all_user) {
			log.debug("全员发送工作通知");
			request.setToAllUser(to_all_user);
		} else {
			if (StringUtil.isNotNull(userList)) {
				request.setUseridList(userList);
			}
			if (StringUtil.isNotNull(deptList)) {
				request.setDeptIdList(deptList);
			}
		}
		if (StringUtil.isNotNull(agentId)) {
			// {"errcode":41020,"errmsg":"accessToken中包含的appKey与agentId不一致，
			// 无法使用当前agentId","request_id":"w7qqh64sqdsj"}
			request.setAgentId(Long.valueOf(agentId)); // 其他应用暂时用不了
			// String agent_id = DingConfig.newInstance().getDingAgentid();
			// log.debug("agent_id:" + agent_id);
			// if (StringUtil.isNotNull(agent_id)) {
			// request.setAgentId(Long.valueOf(agent_id));
			// }
		} else {
			log.debug("agentId为空，默认取待办agentId");
			String agent_id = DingConfig.newInstance().getDingAgentid();
			log.debug("agent_id:" + agent_id);
			if (StringUtil.isNotNull(agent_id)) {
				request.setAgentId(Long.valueOf(agent_id));
			}
		}

		OapiMessageCorpconversationAsyncsendV2Response response = client
				.execute(request, getAccessToken(agentId));
		log.debug("response.getBody():" + response.getBody());
		return response.getBody();

	}

	private String findEkpUserId(List userid_list, List dept_id_list) {
		String ekpUserId = null;
		if (userid_list != null && userid_list.size() > 0) {

			String user_id = null;
			for (int i = 0; i < userid_list.size(); i++) {
				user_id = (String) userid_list.get(i);
				try {
					String did = getOmsRelationService()
							.getDingUserIdByEkpUserId(user_id);
					if (StringUtil.isNotNull(did)) {
						return did;
					}
				} catch (Exception e) {
					log.debug("", e);
				}
			}
			for (int i = 0; i < dept_id_list.size(); i++) {
				user_id = (String) dept_id_list.get(i);
				try {
					String did = getOmsRelationService()
							.getDingUserIdByEkpUserId(user_id);
					if (StringUtil.isNotNull(did)) {
						return did;
					}
				} catch (Exception e) {
					log.debug("", e);
				}
			}

		}

		return ekpUserId;
	}

	public String findDingIdsByEkpList(List list) {
		String ids = "";
		if (list == null || list.isEmpty()) {
			return null;
		}
		String ekpId;
		for (int i = 0; i < list.size(); i++) {
			ekpId = (String) list.get(i);
			try {
				String did = getOmsRelationService()
						.getDingUserIdByEkpUserId(ekpId);

				if (StringUtil.isNull(did)) {
					log.error("根据ekpId无法找到钉钉对照关系,ekpId:" + ekpId);
				} else {
					if (StringUtil.isNull(ids)) {
						ids = did;
					} else {
						ids = ids + "," + did;
					}
				}
			} catch (Exception e) {
				log.debug("", e);
			}
		}
		return ids;
	}

	@Override
    public JSONObject getOrgUserCount(boolean onlyActive) throws Exception {
		String url = DingConstant.DING_PREFIX + "/user/get_org_user_count?"
				+ "access_token=" + getAccessToken() + "&onlyActive="
				+ (onlyActive ? "1" : "0")
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		log.debug("钉钉接口 url:" + url);
		JSONObject response = DingHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		return response;
	}

	private void updateAdmin() {
		try {
			JSONObject admins = getAdmin();
			if (admins != null && admins.getInt("errcode") == 0) {
				log.debug("Ding管理员信息获取成功！");
				JSONArray ja = admins.getJSONArray("adminList");
				if (ja != null && ja.size() > 0) {
					String admin1 = ja.getJSONObject(0).getString("userid");
					admin_userId = admin1;
					log.debug("其中管理员的id:" + admin1);
				}
			}
		} catch (Exception e) {
			log.error("更新管理员账号失败");
		}

	}

	@Override
	public String getAttendanceHolidayTypes(JSONObject param, String ekpId)
			throws Exception {

		String url = DingConstant.DING_PREFIX
				+ "/topapi/attendance/vacation/type/list"
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpId);
		log.debug("钉钉接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiAttendanceVacationTypeListRequest req = new OapiAttendanceVacationTypeListRequest();
		// req.setOpUserid(param.getString("opUserid")); 需要钉钉管理员去取数据
		if (StringUtil.isNull(admin_userId)) {
			updateAdmin();
		}
		req.setOpUserid(admin_userId); // 需要钉钉管理员去取数据
		req.setVacationSource("all");
		OapiAttendanceVacationTypeListResponse rsp = client.execute(req,
				getAccessToken());
		log.debug(rsp.getBody());
		if (rsp.getErrcode() == 0) {
			return rsp.getBody();
		} else if (rsp.getErrcode() == 60068 || rsp.getErrcode() == 400023) {
			updateAdmin();
			req.setOpUserid(admin_userId); // 需要钉钉管理员去取数据
			req.setVacationSource("all");
			rsp = client.execute(req, getAccessToken());
			log.debug(rsp.getBody());
			return rsp.getBody();
		}
		return rsp.getBody();

	}

	@Override
	public String getAttendanceQuota(JSONObject param, String ekpUserId)
			throws Exception {
		String url = DingConstant.DING_PREFIX
				+ "/topapi/attendance/vacation/quota/list"
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);
		log.debug("钉钉接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiAttendanceVacationQuotaListRequest req = new OapiAttendanceVacationQuotaListRequest();
		req.setLeaveCode(param.getString("leaveCode"));
		if (StringUtil.isNull(admin_userId)) {
			updateAdmin();
		}
		req.setOpUserid(admin_userId); // 需要钉钉管理员去取数据
		req.setUserids(param.getString("userids"));
		req.setOffset(0L);
		req.setSize(30L);
		OapiAttendanceVacationQuotaListResponse rsp = client.execute(req,
				getAccessToken());
		log.debug(rsp.getBody());
		if (rsp.getErrcode() == 0) {
			return rsp.getBody();
		} else if (rsp.getErrcode() == 880011 || rsp.getErrcode() == 400023) {
			updateAdmin();
			req.setOpUserid(admin_userId); // 需要钉钉管理员去取数据
			rsp = client.execute(req, getAccessToken());
			log.debug(rsp.getBody());
			return rsp.getBody();
		}
		return rsp.getBody();
	}

	@Override
	public String getUserGroup(JSONObject param, String ekpUserId)
			throws Exception {
		String url = DingConstant.DING_PREFIX
				+ "/topapi/attendance/getusergroup"
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);
		log.debug("钉钉接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiAttendanceGetusergroupRequest request = new OapiAttendanceGetusergroupRequest();
		request.setUserid(param.getString("userid"));
		OapiAttendanceGetusergroupResponse rsp = client.execute(request,
				getAccessToken());
		log.debug(rsp.getBody());

		return rsp.getBody();
	}

	@Override
	public String updateQuota(JSONObject param, String ekpUserId)
			throws Exception {
		String url = DingConstant.DING_PREFIX
				+ "/topapi/attendance/vacation/quota/update"
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);
		log.debug("钉钉接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiAttendanceVacationQuotaUpdateRequest req = new OapiAttendanceVacationQuotaUpdateRequest();
		List<LeaveQuotas> list2 = new ArrayList<LeaveQuotas>();
		LeaveQuotas obj3 = new LeaveQuotas();
		list2.add(obj3);
		obj3.setUserid(param.getString("userid"));
		obj3.setLeaveCode(param.getString("leave_code"));
		obj3.setReason(param.getString("reason"));

		if (param.containsKey("quota_num_per_day")) {
			obj3.setQuotaNumPerDay(
					Long.valueOf(param.getString("quota_num_per_day")));
		}
		if (param.containsKey("quota_num_per_hour")) {
			obj3.setQuotaNumPerHour(
					Long.valueOf(param.getString("quota_num_per_hour")));
		}

		req.setLeaveQuotas(list2);
		if (StringUtil.isNull(admin_userId)) {
			updateAdmin();
		}
		req.setOpUserid(admin_userId);
		OapiAttendanceVacationQuotaUpdateResponse rsp = client.execute(req,
				getAccessToken());
		log.debug(rsp.getBody());

		return rsp.getBody();
	}

	@Override
	public JSONObject getDirFrom(JSONObject param)
			throws Exception {
		String apiPre = "";
		if ("true".equals(DingConfig.newInstance().getAttendanceDebug())) {
			apiPre = "https://pre-oapi.dingtalk.com";
		} else {
			apiPre = DingConstant.DING_PREFIX;
		}
		String url = apiPre
				+ "/topapi/process/dirlist/get?access_token="
				+ getAccessToken() + "&dingAppKey="
				+ param.getString("corpId");
		log.warn("查询审批分组接口：url=" + url);

		JSONObject req = new JSONObject();
		req.put("request", param);
		return DingHttpClientUtil.httpPost(url, req, null, JSONObject.class);
		// return test;
	}

	@Override
	public String getDingTemplateList(String userId, Long offSet, Long size)
			throws Exception {

		String url = DingConstant.DING_PREFIX
				+ "/topapi/process/template/list"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		log.debug("查询钉钉模板接口：url=" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);

		OapiProcessTemplateListRequest req = new OapiProcessTemplateListRequest();
		req.setUserid(userId);
		req.setOffset(offSet);
		req.setSize(size);
		OapiProcessTemplateListResponse rsp = client.execute(req,
				getAccessToken());
		return rsp.getBody();
	}

	@Override
	public String delTemplate(String processCode) throws Exception {

		String url = DingConstant.DING_PREFIX + "/topapi/process/delete"
				+ DingUtil.getDingAppKey("?", null);
		log.debug("钉钉删除模板接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiProcessDeleteRequest req = new OapiProcessDeleteRequest();
		DeleteProcessRequest obj1 = new DeleteProcessRequest();
		obj1.setAgentid(
				Long.valueOf(DingConfig.newInstance().getDingAgentid()));
		obj1.setProcessCode(processCode);
		obj1.setCleanRunningTask(false);
		req.setRequest(obj1);
		return client.execute(req, getAccessToken()).getBody();

	}

	// ISV根据corpId获取agentId
	@Override
	public JSONObject getAgentIdByCorpId(String corpId)
			throws Exception {
		String url = "http://d.dingoa.com/ops/f4-assist/f4AssistEntry/findAgentId";
		log.debug("ISV根据corpId获取agentId：url=" + url);
		JSONObject req = new JSONObject();
		req.put("corpId", corpId);
		// return DingHttpClientUtil.httpPost(url, req, null, JSONObject.class);
		String rs = sendPost(url, req);
		if (rs == null) {
			return null;
		} else {
			return JSONObject.fromObject(rs);
		}
	}

	public static String sendPost(String url, JSONObject requestJsonObject)
			throws Exception {
		String result = "";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json");
		log.debug("url:" + url + ",postData="
				+ JSON.toJSONString(requestJsonObject));
		StringEntity requestEntity = new StringEntity(
				JSON.toJSONString(requestJsonObject), "utf-8");
		httpPost.setEntity(requestEntity);

		httpPost.setEntity(requestEntity);
		CloseableHttpResponse response = null;
		HttpEntity httpEntity = null;
		try {
			response = client.execute(httpPost);
			try {
				httpEntity = response.getEntity();
				result = EntityUtils.toString(httpEntity);
				log.warn("接口调用：" + url + "\n result:" + result);
			} catch (Exception e) {
				log.error("接口调用出错：" + url + "\n result:" + result, e);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			log.error("", e);
			log.error("接口调用出错：" + url + "\n result:" + result);
		} finally {
			client.close();
		}
		return result;
	}

	@Override
	public JSONObject getBizsuite(JSONObject param)
			throws Exception {
		String apiPre = "";
		if ("true".equals(DingConfig.newInstance().getAttendanceDebug())) {
			apiPre = "https://pre-oapi.dingtalk.com";
		} else {
			apiPre = DingConstant.DING_PREFIX;
		}
		String url = apiPre
				+ "/topapi/process/bizsuite/get?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		JSONObject req = new JSONObject();
		req.put("request", param);
		log.debug("套件接口：url=" + url + " req:" + req);
		return DingHttpClientUtil.httpPost(url, req, null, JSONObject.class);
	}
	/**
	 * 获取钉钉角色组
	 */
	@Override
	public String getRoleByGroupId(Long groupId) throws Exception {
		String url = DingConstant.DING_PREFIX
				+ "/topapi/role/getrolegroup"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		log.debug("钉钉获取钉钉角色组接口 url" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiRoleGetrolegroupRequest request = new OapiRoleGetrolegroupRequest();
		request.setGroupId(groupId);
		OapiRoleGetrolegroupResponse rsp = client
				.execute(request, getAccessToken());
		if (rsp == null) {
			log.error("获取钉钉角色组失败!");
			return null;
		}
		return rsp.getBody();
	}

	/**
	 * 获取角色下的人员列表数据
	 */
	@Override
	public String getSimplelistByRoleId(Long roleId, Long count)
			throws Exception {

		String url = DingConstant.DING_PREFIX
				+ "/topapi/role/simplelist"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		log.debug("获取角色下的人员列表数据接口 url" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiRoleSimplelistRequest request = new OapiRoleSimplelistRequest();
		request.setRoleId(roleId);
		request.setOffset(count * 100);
		request.setSize(100L);

		OapiRoleSimplelistResponse rsp = client.execute(request,
				getAccessToken());
		if (rsp == null) {
			log.error("获取角色下的人员列表数据失败!");
			return null;
		}
		return rsp.getBody();
	}

	/**
	 * 获取角色列表
	 */
	@Override
	public String getRoleList() throws Exception {
		String url = DingConstant.DING_PREFIX
				+ "/topapi/role/list"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		log.debug("获取角色列表接口 url" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiRoleListRequest request = new OapiRoleListRequest();
		request.setOffset(0L);
		request.setSize(100L);
		OapiRoleListResponse response = client.execute(request,
				getAccessToken());
		return response.getBody();
	}

	@Override
	public String queryShift(String ding_optId, Long classId)
			throws Exception {
		String url = DingConstant.DING_PREFIX
				+ "/topapi/attendance/shift/query"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		log.debug("获取班次信息 ：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiAttendanceShiftQueryRequest req = new OapiAttendanceShiftQueryRequest();
		req.setOpUserId(ding_optId);
		req.setShiftId(classId);
		OapiAttendanceShiftQueryResponse rsp = client.execute(req,
				getAccessToken());
		return rsp.getBody();
	}

	/**
	 * 获取角色信息
	 */
	@Override
	public String getRole(Long role_id) throws Exception {
		String url = DingConstant.DING_PREFIX
				+ "/topapi/role/getrole"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		log.debug("获取角色信息 url" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiRoleGetroleRequest request = new OapiRoleGetroleRequest();
		request.setRoleId(role_id);
		OapiRoleGetroleResponse response = client.execute(request,
				getAccessToken());
		return response.getBody();
	}

	@Override
    public OapiMessageCorpconversationAsyncsendV2Request
			buildMessageSendRequest(Map<String, String> content, String userid,
					String deptid, boolean toall, Long agentId,
					String ekpUserId)
					throws Exception {
		if (!(content.containsKey("title") && content.containsKey("content")
				&& content.containsKey("message_url")
				&& content.containsKey("pc_message_url"))) {
			log.debug("发送的内容（标题/消息内容/移动端地址/pc端地址）有存在为空的情况导致无法发送钉钉消息");
			throw new MessageSendException(
					"发送的内容（标题/消息内容/移动端地址/pc端地址）有存在为空的情况导致无法发送钉钉消息");
		}
		if (agentId == null) {
			log.debug("发送消息应用的Id为空，导致无法发送钉钉消息");
			throw new MessageSendException("发送消息应用的Id为空，导致无法发送钉钉消息");
		}
		if (!toall && StringUtil.isNull(userid) && StringUtil.isNull(deptid)) {
			log.debug("用户,部门, 发送全员（一天只能发送3次）必须有一个不能为空");
			throw new MessageSendException("用户,部门, 发送全员（一天只能发送3次）必须有一个不能为空");
		}
		OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
		request.setUseridList(userid);
		request.setDeptIdList(deptid);
		request.setAgentId(agentId);
		request.setToAllUser(toall);
		OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
		msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
		msg.getOa().setHead(
				new OapiMessageCorpconversationAsyncsendV2Request.Head());
		msg.getOa().getHead().setBgcolor(content.get("color"));
		msg.getOa().setBody(
				new OapiMessageCorpconversationAsyncsendV2Request.Body());
		msg.getOa().getBody().setTitle(content.get("title"));
		msg.getOa().getBody().setContent(content.get("content"));
		if(content.containsKey("status_bar")) {
			OapiMessageCorpconversationAsyncsendV2Request.StatusBar statusBar = new OapiMessageCorpconversationAsyncsendV2Request.StatusBar();
			statusBar.setStatusValue((String)content.get("status_bar"));
			statusBar.setStatusBg("0xFF78C06E");
			msg.getOa().setStatusBar(statusBar);
		}
		String message_url = content.get("message_url");
		if (StringUtil.isNotNull(message_url)
				&& !message_url.contains("dingAppKey")) {
			message_url = content.get("message_url")
					+ DingUtil.getDingAppKeyByEKPUserId("&", ekpUserId);
		}
		log.debug("message_url:" + message_url);
		msg.getOa().setMessageUrl(message_url);
		String pc_message_url = content.get("pc_message_url");
		if (StringUtil.isNotNull(pc_message_url)
				&& !pc_message_url.contains("dingAppKey")) {
			pc_message_url = content.get("pc_message_url")
					+ DingUtil.getDingAppKeyByEKPUserId("&", ekpUserId);
		}
		log.debug("pc_message_url:" + pc_message_url);
		msg.getOa().setPcMessageUrl(pc_message_url);
		msg.setMsgtype("oa");
		request.setMsg(msg);
		log.debug(JSONObject.fromObject(request).toString());
		return request;
		// OapiMessageCorpconversationAsyncsendV2Response response = client
		// .execute(request, getAccessToken(String.valueOf(agentId)));
		// return response.getBody();
	}

	@Override
    public String messageSend(Long agentId, String ekpUserId,
                              OapiMessageCorpconversationAsyncsendV2Request request)
			throws Exception {
		String dingUrl = DingConstant.DING_PREFIX
				+ "/topapi/message/corpconversation/asyncsend_v2"
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);
		log.debug("【钉钉接口】发送工作通知: " + dingUrl);
		ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
		OapiMessageCorpconversationAsyncsendV2Response response = client
				.execute(request, getAccessToken(String.valueOf(agentId)));
		return response.getBody();
	}
	
	/**
	 * 根据名称查钉钉的模板id
	 */
	@Override
	public String getProcessCodeByName(String name) throws Exception {
		String url = DingConstant.DING_PREFIX
				+ "/topapi/process/get_by_name"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		log.debug("根据名称查钉钉的模板id url" + url);

		ThirdDingTalkClient client = new ThirdDingTalkClient(url);

		OapiProcessGetByNameRequest req = new OapiProcessGetByNameRequest();
		req.setName(name);
		OapiProcessGetByNameResponse rsp = client.execute(req,
				getAccessToken());
		return rsp.getBody();
	}

	@Override
	public ByteArrayOutputStream download(JSONObject param) throws Exception {
		ByteArrayOutputStream out = null;

		String agentId = DingConfig.newInstance().getDingAgentid();
		if(param.containsKey("agentId")){
			agentId = param.getString("agentId");
		}
		String accessToken = getAccessToken(agentId);
		String spaceId = param.getString("spaceId");
		String fileId = param.getString("fileId");
		String fileName = param.getString("fileName");
		StringBuilder params = new StringBuilder();
		params.append("code=")
				.append(URLEncoder.encode(param.getString("code"), "utf-8"))
				.append("&");
		params.append("agent_id=").append(URLEncoder.encode(agentId,
				"utf-8"))
				.append("&");
		params.append("dentry_id=").append(URLEncoder.encode(fileId,
				"utf-8"))
				.append("&");
		params.append("space_id=")
				.append(URLEncoder.encode(spaceId.toString(), "utf-8"))
				.append("&");
		params.append("access_token=")
				.append(URLEncoder.encode(accessToken, "utf-8"));
		String url = DingConstant.DING_PREFIX + "/cspace/get_download_key?"
				+ params.toString();

		log.warn("url:" + url);
		JSONObject obj = DingHttpClientUtil.httpGet(url, null,
				JSONObject.class);
		System.out.println("obj:" + obj);
		if (obj != null && obj.getInt("errcode") == 0) {
			String downloadKey = obj.getString("download_key");
			log.warn("...准备下载..." + downloadKey + "  文件：" + fileName);
			params = new StringBuilder();
			params.append("download_key=")
					.append(URLEncoder.encode(downloadKey, "utf-8"))
					.append("&");
			params.append("agent_id=")
					.append(URLEncoder.encode(agentId, "utf-8")).append("&");
			params.append("access_token=")
					.append(URLEncoder.encode(accessToken, "utf-8"));
			url = DingConstant.DING_PREFIX + "/file/download_by_key?"
					+ params.toString();
			out = DingHttpClientUtil.httpGet(url, null,
					ByteArrayOutputStream.class);

		} else {
			log.error("获取download_key异常：" + obj);
			throw new RuntimeException("获取download_key异常：" + obj);
		}
		return out;
	}
	//-------------------钉钉新接口  start----------------
	@Override
	public JSONObject addTask(String unionId, TodoTask task)  throws Exception{
		if(StringUtil.isNull(unionId)){
			if(StringUtil.isNull(unionId)){
				unionId = (String) task.getExecutorIds().get(0);
			}
			task.setOnlyShowExecutor(true);
		}
		String url =DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+unionId+"/tasks";
		return DingApiHttpClientUtil.httpPost("/v1.0/todo/users/{unionId}/tasks",url,getAccessToken(),task,null,JSONObject.class);
	}

	@Override
	public JSONObject updateTask(String unionId, String taskId, JSONObject req)  throws Exception{
		String url =DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+unionId+"/tasks/"+taskId;
		return DingApiHttpClientUtil.httpPut("/v1.0/todo/users/{unionId}/tasks",url,getAccessToken(),req,null,JSONObject.class);
	}

	@Override
	public JSONObject updateExecutorStatus(String unionId, String taskId, JSONObject req)  throws Exception{
		String url =DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+unionId+"/tasks/"+taskId+"/executorStatus";
		return DingApiHttpClientUtil.httpPut("/v1.0/todo/users/{unionId}/tasks",url,getAccessToken(),req,null,JSONObject.class);
	}

	@Override
	public JSONObject getTask(String unionId, String taskId)  throws Exception{
		String url =DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+unionId+"/tasks/"+taskId;
		return DingApiHttpClientUtil.httpGet("/v1.0/todo/users/{unionId}/tasks",url,getAccessToken(),null,JSONObject.class);
	}

	@Override
	public JSONObject getTaskList(String unionId)  throws Exception{
		String url =DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+unionId+"/tasks/list";
		JSONObject req = new JSONObject();
		req.put("isDone",false);
		req.put("orderBy","created");
		req.put("orderDirection","desc");
		JSONArray ja= new JSONArray();
		JSONArray ja2= new JSONArray();
		ja2.add("executor");
		ja.add(ja2);
		req.put("roleTypes",ja);
		log.warn("请求参数:"+req.toString());
		JSONObject result = DingApiHttpClientUtil.httpPost("/v1.0/todo/users/{unionId}/tasks/list",url,getAccessToken(),req,null,JSONObject.class);
		if(result!=null&&result.containsKey("todoCards")){
			boolean flag = true;
			String nextToken = result.getString("nextToken");
			JSONObject temp_result = new JSONObject();
			while (flag){
				req.put("nextToken",nextToken);
				temp_result = DingApiHttpClientUtil.httpPost("/v1.0/todo/users/{unionId}/tasks/list",url,getAccessToken(),req,null,JSONObject.class);
				if (temp_result!=null&&temp_result.containsKey("todoCards")){
					result.accumulate("todoCards",temp_result.getJSONArray("todoCards"));
				}
				if(!temp_result.containsKey("nextToken")){
					flag=false;
				}else {
					nextToken =temp_result.getString("nextToken");
				}
			}
		}else {
			throw new RuntimeException(result==null?"返回结果为空！！！":result.toString());
		}
		return result;
	}

	@Override
	public JSONObject deleteTask(String unionId, String taskId)  throws Exception{
		String url =DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+unionId+"/tasks/"+taskId;
		return DingApiHttpClientUtil.httpDelete("/v1.0/todo/users/{unionId}/tasks/{taskId}",url,getAccessToken(),null,JSONObject.class);
	}

	@Override
	public JSONObject addCard(String unionId, TodoCard card) throws Exception {
		String url =DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+unionId+"/configs/types";
		return DingApiHttpClientUtil.httpPost("/v1.0/todo/users/{unionId}/configs/types",url,getAccessToken(),card,null,JSONObject.class);
	}

	@Override
	public JSONObject updateCard(String unionId, String cardId, TodoCard card) throws Exception {
		String url =DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+unionId+"/configs/types/"+cardId;
		return DingApiHttpClientUtil.httpPut("/v1.0/todo/users/{unionId}/configs/types/{cardId}",url,getAccessToken(),card,null,JSONObject.class);
	}

	@Override
	public JSONObject getCard(String unionId, String cardId) throws Exception {
		String url =DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+unionId+"/configs/types/"+cardId;
		return DingApiHttpClientUtil.httpGet("/v1.0/todo/users/{unionId}/configs/types/{cardId}",url,getAccessToken(),null,JSONObject.class);
	}

	@Override
	public JSONObject addDingCalendars(String unionId, DingCalendars calendars) throws Exception {
		String url =DingConstant.DING_API_PREFIX+"/v1.0/calendar/users/"+unionId+"/calendars/primary/events";
		return DingApiHttpClientUtil.httpPost("/v1.0/calendar/users/{unionId}/calendars/primary/events",url,getAccessToken(),calendars,null,JSONObject.class);
	}

	@Override
	public JSONObject updateDingCalendars(String unionId, String calendarId, DingCalendars calendars) throws Exception {
		String url =DingConstant.DING_API_PREFIX+"/v1.0/calendar/users/"+unionId+"/calendars/primary/events/"+calendarId;
		return DingApiHttpClientUtil.httpPut("/v1.0/calendar/users/{unionId}/calendars/primary/events/{calendarId}",url,getAccessToken(),calendars,null,JSONObject.class);
	}

	@Override
	public JSONObject getDingCalendars(String unionId, String calendarId) throws Exception {
		String url =DingConstant.DING_API_PREFIX+"/v1.0/calendar/users/"+unionId+"/calendars/primary/events/"+calendarId;
		return DingApiHttpClientUtil.httpGet("/v1.0/calendar/users/{unionId}/calendars/primary/events/{calendarId}",url,getAccessToken(),null,JSONObject.class);
	}

	@Override
	public JSONObject deleteDingCalendars(String unionId, String calendarId) throws Exception {
		String url =DingConstant.DING_API_PREFIX+"/v1.0/calendar/users/"+unionId+"/calendars/primary/events/"+calendarId;
		return DingApiHttpClientUtil.httpDelete("/v1.0/calendar/users/{unionId}/calendars/primary/events/{calendarId}",url,getAccessToken(),null,JSONObject.class);
	}

	@Override
	public JSONObject getToDoList(String unionId, DingToDoList dingToDoList) throws Exception {

		String url =DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+unionId+"/tasks/list";
		dingToDoList.setOrderBy("created");
		dingToDoList.setDone(false);
		dingToDoList.setOrderDirection("desc");
		dingToDoList.setCategory("other");
		/**
		 * executor：执行人
		 * creator：创建人
		 * participant：参与人
		 */
		List<String> executor = Collections.singletonList("executor");
		List<List<String>> objects = new ArrayList<>();
		objects.add(executor);
		dingToDoList.setRoleTypes(objects);
		log.warn("请求参数:"+dingToDoList.toString());
		log.debug("请求url地址:{}", url);
		JSONObject jsonObject = DingApiHttpClientUtil.httpPost("/v1.0/todo/users/{unionId}/tasks/list",url, getAccessToken(), dingToDoList,null, JSONObject.class);

		String nextToken = null;

		if (jsonObject != null && jsonObject.size() > 0) {
			boolean flag = jsonObject.has("nextToken");
			if (flag) {
				//分页token
				nextToken = jsonObject.getString("nextToken");
			} else {
				//已经查询出全部数据
				return jsonObject;
			}
			while (true) {
				dingToDoList.setNextToken(nextToken);
				JSONObject object = DingApiHttpClientUtil.httpPost("/v1.0/todo/users/{unionId}/tasks/list",url, getAccessToken(), dingToDoList,null, JSONObject.class);
				JSONArray temp = jsonObject.getJSONArray("todoCards");
				if (object != null && object.has("todoCards")) {
					//添加所有分页查询出来的数据到原始jsonObject 对象
					temp.addAll(object.getJSONArray("todoCards"));
					jsonObject.put("todoCards", temp);
				} else {
					return jsonObject;
				}
				if (object.has("nextToken")) {
					//得到最新的分页token
					nextToken = object.getString("nextToken");
				} else {
					//如果不存在nextToken 证明所有分页数据已查询完毕
					return jsonObject;
				}
			}

		}
		return new JSONObject();
}

	@Override
	public JSONObject getUserIdByMobile(String mobile) throws Exception {
		String url = DingConstant.DING_PREFIX + "/topapi/v2/user/getbymobile?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		JSONObject req = new JSONObject();
		req.put("mobile",mobile);
		return DingHttpClientUtil.httpPost(url, req, null, JSONObject.class);
	}


	@Override
	public JSONObject calendarIdConvert(String unionId, LinkedHashMap<String, List<String>> oldIdsMap) throws Exception {
		String url =DingConstant.DING_API_PREFIX+"/v1.0/calendar/users/"+unionId+"/legacyEventIds/convert";
		return DingApiHttpClientUtil.httpPost("/v1.0/calendar/users/{unionId}/legacyEventIds/convert",url,getAccessToken(),oldIdsMap,null,JSONObject.class);
	}

	@Override
	public JSONObject getDingCalendarList(String unionId, DingCalendarParam dingCalendarParam) throws Exception {

		StringBuffer url = new StringBuffer(DingConstant.DING_API_PREFIX + "/v1.0/calendar/users/" + unionId + "/calendars/primary/events/");

		if (dingCalendarParam != null) {
			url.append("?");
			if (dingCalendarParam.getShowDeleted() != null) {
				url.append("showDeleted=").append(dingCalendarParam.getShowDeleted());
			}
			if (StringUtil.isNotNull(dingCalendarParam.getTimeMin())) {
				url.append("&timeMin=").append(dingCalendarParam.getTimeMin());
			}
			if (StringUtil.isNotNull(dingCalendarParam.getTimeMax())) {
				url.append("&timeMax=").append(dingCalendarParam.getTimeMax());
			}
			if (dingCalendarParam.getMaxResults() != null) {
				url.append("&maxResults=").append(dingCalendarParam.getMaxResults());
			}
			if (StringUtil.isNotNull(dingCalendarParam.getNextToken())) {
				url.append("&nextToken=").append(dingCalendarParam.getNextToken());
			}
			if (StringUtil.isNotNull(dingCalendarParam.getSyncToken())) {
				url.append("&syncToken=").append(dingCalendarParam.getSyncToken());
			}
		}
		log.debug("请求地址:{}", url);

		JSONObject jsonObject = DingApiHttpClientUtil.httpGet("/v1.0/calendar/users/{unionId}/calendars/primary/events/",url.toString(), getAccessToken(), null, JSONObject.class);
		String temp_url = url.toString();
		String nextToken = null;

		if (jsonObject != null && jsonObject.size() > 0) {
			boolean flag = jsonObject.has("nextToken");
			if (flag) {
				//分页token
				nextToken = jsonObject.getString("nextToken");
			} else {
				//已经查询出全部数据
				return jsonObject;
			}
			while (true) {
				String url_req = temp_url + "&nextToken=" + nextToken;
				JSONObject object = DingApiHttpClientUtil.httpGet("/v1.0/calendar/users/{unionId}/calendars/primary/events/",url_req, getAccessToken(), null, JSONObject.class);
				JSONArray temp = jsonObject.getJSONArray("events");
				if (object != null && object.has("events")) {
					//添加所有分页查询出来的数据到原始jsonObject 对象
					temp.addAll(object.getJSONArray("events"));
					jsonObject.put("events", temp);
				} else {
					return jsonObject;
				}
				if (object.has("nextToken")) {
					//得到最新的分页token
					nextToken = object.getString("nextToken");
				} else {
					//如果存在syncToken 证明所有分页数据已查询完毕
					if (object.has("syncToken")) {
						jsonObject.put("syncToken", object.getString("syncToken"));
					}
					return jsonObject;
				}
			}

		}
		return new JSONObject();

	}

	@Override
	public JSONObject newTodo(String unionId, Map<String, Object> map) throws Exception {
		String url =DingConstant.DING_API_PREFIX+"/v1.0/todo/users/"+unionId+"/tasks/list";
		return DingApiHttpClientUtil.httpPost("/v1.0/todo/users/{unionId}/tasks/list", url,getAccessToken(),map,null,JSONObject.class);
	}
	//-------------------钉钉新接口  end----------------

	@Override
	public JSONObject sendInteractiveCard(JSONObject request) throws Exception{
		String url =DingConstant.DING_API_PREFIX+"/v1.0/im/interactiveCards/send";
		return DingApiHttpClientUtil.httpPost("/v1.0/im/interactiveCards/send", url,getRobotAccessToken(),request,null,JSONObject.class);
	}

	@Override
	public JSONObject updateInteractiveCard(JSONObject request) throws Exception {
		String url =DingConstant.DING_API_PREFIX+"/v1.0/im/interactiveCards";
		return DingApiHttpClientUtil.httpPut("/v1.0/im/interactiveCards", url,getRobotAccessToken(),request,null,JSONObject.class);
	}

	@Override
	public JSONObject registerCardCallback(String callbackUrl, String routeKey,String apiSecret) throws Exception {
		String url = DingConstant.DING_PREFIX + "/topapi/im/chat/scencegroup/interactivecard/callback/register?access_token="
				+ getAccessToken()
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		log.debug("钉钉接口： url：" + url);
		JSONObject request = new JSONObject();
		request.put("callback_url",callbackUrl);
		request.put("callbackRouteKey",routeKey);
		if(StringUtil.isNotNull(apiSecret)){
			request.put("api_secret",apiSecret);
		}
		request.put("forceUpdate",true);
		JSONObject rs= DingHttpClientUtil.httpPost(url, request, null, JSONObject.class);
		log.info("回调注册结果："+rs);
		return rs;
	}

}