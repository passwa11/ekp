package com.landray.kmss.third.ding.scenegroup.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiChatSendRequest;
import com.dingtalk.api.request.OapiChatSendRequest.ActionCard;
import com.dingtalk.api.request.OapiChatSendRequest.BtnJson;
import com.dingtalk.api.request.OapiChatSendRequest.Link;
import com.dingtalk.api.request.OapiChatSendRequest.Markdown;
import com.dingtalk.api.request.OapiChatSendRequest.Msg;
import com.dingtalk.api.request.OapiChatSendRequest.Text;
import com.dingtalk.api.request.OapiImChatScenegroupCreateRequest;
import com.dingtalk.api.request.OapiImChatScenegroupMemberAddRequest;
import com.dingtalk.api.request.OapiImChatScenegroupMemberDeleteRequest;
import com.dingtalk.api.request.OapiImChatScenegroupMemberGetRequest;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.request.OapiRobotSendRequest.Btns;
import com.dingtalk.api.response.OapiChatSendResponse;
import com.dingtalk.api.response.OapiImChatScenegroupCreateResponse;
import com.dingtalk.api.response.OapiImChatScenegroupMemberAddResponse;
import com.dingtalk.api.response.OapiImChatScenegroupMemberDeleteResponse;
import com.dingtalk.api.response.OapiImChatScenegroupMemberGetResponse;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingGroupmsgLog;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingScenegroupMapp;
import com.landray.kmss.third.ding.scenegroup.service.IThirdDingGroupmsgLogService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.sso.client.util.StringUtil;

public class DingScenegroupApiUtil {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingScenegroupApiUtil.class);

	public static OapiImChatScenegroupCreateResponse createScenegroup(
			String templateId, String title,
			String ownerUserId, String userIds)
			throws Exception {
		String token = DingUtils.getDingApiService().getAccessToken();
		String url = DingConstant.DING_PREFIX
				+ "/topapi/im/chat/scenegroup/create";
		DingTalkClient client = new DefaultDingTalkClient(url);
		OapiImChatScenegroupCreateRequest req = new OapiImChatScenegroupCreateRequest();
		req.setOwnerUserId(ownerUserId);
		req.setUserIds(userIds);
		// req.setUuid("asdazxc");
		// req.setIcon("@asdf12zcv");
		req.setMentionAllAuthority(0L);
		req.setShowHistoryType(1L);
		req.setValidationType(0L);
		req.setSearchable(1L);
		req.setChatBannedType(0L);
		req.setManagementType(0L);
		req.setTitle(title);
		req.setTemplateId(templateId);
		OapiImChatScenegroupCreateResponse rsp = client.execute(req, token);
		logger.debug(rsp.getBody());
		return rsp;
	}

	public static boolean scenegroupAddMember(String open_conversation_id,
			String userIds)
			throws Exception {
		String token = DingUtils.getDingApiService().getAccessToken();
		String url = DingConstant.DING_PREFIX
				+ "/topapi/im/chat/scenegroup/member/add";
		DingTalkClient client = new DefaultDingTalkClient(url);
		OapiImChatScenegroupMemberAddRequest req = new OapiImChatScenegroupMemberAddRequest();
		req.setOpenConversationId(open_conversation_id);
		req.setUserIds(userIds);
		OapiImChatScenegroupMemberAddResponse rsp = client.execute(req, token);
		logger.debug(rsp.getBody());
		if (rsp.getErrcode() == 0) {
			return true;
		} else {
			logger.error("新增成员失败，" + JSON.toJSONString(rsp));
			throw new Exception(rsp.getErrorCode() + "---" + rsp.getErrmsg());
		}
	}

	public static boolean scenegroupDeleteMember(String open_conversation_id,
			String userIds)
			throws Exception {
		String token = DingUtils.getDingApiService().getAccessToken();
		String url = DingConstant.DING_PREFIX
				+ "/topapi/im/chat/scenegroup/member/delete";
		DingTalkClient client = new DefaultDingTalkClient(url);
		OapiImChatScenegroupMemberDeleteRequest req = new OapiImChatScenegroupMemberDeleteRequest();
		req.setOpenConversationId(open_conversation_id);
		req.setUserIds(userIds);
		OapiImChatScenegroupMemberDeleteResponse rsp = client.execute(req,
				token);
		logger.debug(rsp.getBody());
		if (rsp.getErrcode() == 0) {
			return true;
		} else {
			logger.error("删除成员失败，" + JSON.toJSONString(rsp));
			throw new Exception(rsp.getErrorCode() + "---" + rsp.getErrmsg());
		}
	}


	public static OapiRobotSendResponse sendRobotTextMsg(String robotApiUrl,
			String secret, String content) throws Exception {
		Long timestamp = System.currentTimeMillis();
		String sign = getRobotMsgSign(timestamp, secret);
		String url = robotApiUrl + "&timestamp="
				+ timestamp + "&sign=" + sign;
		DingTalkClient client = new DefaultDingTalkClient(url);
		OapiRobotSendRequest request = new OapiRobotSendRequest();
		request.setMsgtype("text");
		OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
		text.setContent(content);
		request.setText(text);
		OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
		// at.setAtMobiles(Arrays.asList("132xxxxxxxx"));
		// isAtAll类型如果不为Boolean，请升级至最新SDK
		at.setIsAtAll(true);
		request.setAt(at);
		OapiRobotSendResponse rsp = client.execute(request);
		return rsp;
	}

	public static OapiRobotSendResponse sendRobotLinkMsg(String robotApiUrl,
			String secret, String title, String content,
			String url) throws Exception {
		DingConfig config = DingConfig.newInstance();
		String corpId = config.getDingCorpid();
		String agentId = config.getDingAgentid();
		Long timestamp = System.currentTimeMillis();
		String sign = getRobotMsgSign(timestamp, secret);
		String url1 = robotApiUrl + "&timestamp="
				+ timestamp + "&sign=" + sign;
		DingTalkClient client = new DefaultDingTalkClient(url1);
		OapiRobotSendRequest request = new OapiRobotSendRequest();
		request.setMsgtype("link");
		OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
		// link.setMessageUrl(
		// "dingtalk://dingtalkclient/page/link?url=http%3A%2F%2Fhuangwq.qicp.vip/km/review&pc_slide=true");
		link.setMessageUrl(
				"dingtalk://dingtalkclient/action/openapp?corpid=" + corpId
						+ "&container_type=work_platform&app_id=0_" + agentId
						+ "&redirect_type=jump&redirect_url="
						+ URLEncoder.encode(url));
		link.setPicUrl("");
		link.setTitle(title);
		link.setText(content);
		request.setLink(link);
		return client.execute(request);
	}

	public static String buildOpenappUrl(String url) {
		DingConfig config = DingConfig.newInstance();
		String corpId = config.getDingCorpid();
		String agentId = config.getDingAgentid();
		return "dingtalk://dingtalkclient/action/openapp?corpid=" + corpId
				+ "&container_type=work_platform&app_id=0_" + agentId
				+ "&redirect_type=jump&redirect_url="
				+ URLEncoder.encode(url);
	}

	public static OapiRobotSendResponse sendRobotMarkdownMsg(String robotApiUrl,
			String secret, String title, String content)
			throws Exception {

		Long timestamp = System.currentTimeMillis();
		String sign = getRobotMsgSign(timestamp, secret);
		String url1 = robotApiUrl + "&timestamp="
				+ timestamp + "&sign=" + sign;
		DingTalkClient client = new DefaultDingTalkClient(url1);
		OapiRobotSendRequest request = new OapiRobotSendRequest();
		request.setMsgtype("markdown");
		OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
		markdown.setTitle(title);
		markdown.setText(content);
		request.setMarkdown(markdown);

		return client.execute(request);
	}

	public static OapiRobotSendResponse sendRobotActioncardMsg(
			String robotApiUrl, String secret, String title, String content,
			String singleTitle, String singleURL) throws Exception {
		Long timestamp = System.currentTimeMillis();
		String sign = getRobotMsgSign(timestamp, secret);
		String url1 = robotApiUrl + "&timestamp="
				+ timestamp + "&sign=" + sign;
		DingTalkClient client = new DefaultDingTalkClient(url1);
		OapiRobotSendRequest request = new OapiRobotSendRequest();
		request.setMsgtype("actionCard");
		OapiRobotSendRequest.Actioncard actioncard = new OapiRobotSendRequest.Actioncard();
		actioncard.setTitle(title);
		actioncard.setText(content);
		actioncard.setSingleTitle(singleTitle);
		actioncard.setSingleURL(buildOpenappUrl(singleURL));
		request.setActionCard(actioncard);
		OapiRobotSendResponse resp = client.execute(request);
		return resp;
	}

	public static OapiRobotSendResponse sendRobotActioncardMsg(
			String robotApiUrl, String secret, String title, String content,
			JSONArray btnsArray, String btnOrientation) throws Exception {
		Long timestamp = System.currentTimeMillis();
		String sign = getRobotMsgSign(timestamp, secret);
		String url1 = robotApiUrl + "&timestamp="
				+ timestamp + "&sign=" + sign;
		DingTalkClient client = new DefaultDingTalkClient(url1);
		OapiRobotSendRequest request = new OapiRobotSendRequest();
		request.setMsgtype("actionCard");
		OapiRobotSendRequest.Actioncard actioncard = new OapiRobotSendRequest.Actioncard();
		actioncard.setTitle(title);
		actioncard.setText(content);
		List<Btns> btns = new ArrayList<Btns>();
		if (btnsArray != null) {
			for (int i = 0; i < btnsArray.size(); i++) {
				JSONObject o = btnsArray.getJSONObject(i);
				String btnTitle = o.getString("title");
				String actionURL = o.getString("url");
				Btns btn = new Btns();
				btn.setActionURL(buildOpenappUrl(actionURL));
				btn.setTitle(btnTitle);
				btns.add(btn);
			}
		}
		actioncard.setBtns(btns);
		if (StringUtil.isNotNull(btnOrientation)) {
			actioncard.setBtnOrientation(btnOrientation);
		}
		request.setActionCard(actioncard);
		return client.execute(request);
	}



	public static String getRobotMsgSign(Long timestamp, String secret)
			throws Exception {
		String stringToSign = timestamp + "\n" + secret;
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
		byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
		String sign = URLEncoder
				.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
		return sign;
	}


	public static void sendGroupTextMsg(ThirdDingScenegroupMapp mapp,
			String content)
			throws Exception {
		Date start = new Date();
		String errMsg = null;
		String url = DingConstant.DING_PREFIX
				+ "/chat/send";
		DingTalkClient client = new DefaultDingTalkClient(url);
		OapiChatSendRequest req = new OapiChatSendRequest();
		req.setChatid(mapp.getFdChatId());
		Msg msg = new Msg();
		Text text = new Text();
		text.setContent(content);
		msg.setText(text);
		msg.setMsgtype("text");
		req.setMsg(msg);
		OapiChatSendResponse rsp = null;
		try {
			String token = DingUtils.getDingApiService().getAccessToken();
			rsp = client.execute(req, token);
		} catch (Exception e) {
			errMsg = e.getMessage();
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			addGroupmsgLog(mapp, null, url, start, errMsg, req, rsp);
		}
	}

	public static void sendGroupLinkMsg(ThirdDingScenegroupMapp mapp,
			String messageUrl,
			String title, String text)
			throws Exception {
		Date start = new Date();
		String errMsg = null;
		String url = DingConstant.DING_PREFIX
				+ "/chat/send";
		DingTalkClient client = new DefaultDingTalkClient(url);
		OapiChatSendRequest req = new OapiChatSendRequest();
		req.setChatid(mapp.getFdChatId());
		Msg msg = new Msg();
		Link link = new Link();
		link.setMessageUrl(buildOpenappUrl(messageUrl));
		link.setPicUrl("1");
		link.setTitle(title);
		link.setText(text);
		msg.setLink(link);
		msg.setMsgtype("link");
		req.setMsg(msg);
		OapiChatSendResponse rsp = null;
		try {
			String token = DingUtils.getDingApiService().getAccessToken();
			rsp = client.execute(req, token);
		} catch (Exception e) {
			errMsg = e.getMessage();
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			addGroupmsgLog(mapp, null, url, start, errMsg, req, rsp);
		}
	}

	public static void sendGroupActionCardMsg(ThirdDingScenegroupMapp mapp,
			String title,
			String content, String singleTitle, String singleURL)
			throws Exception {
		Date start = new Date();
		String errMsg = null;
		String url = DingConstant.DING_PREFIX
				+ "/chat/send";
		DingTalkClient client = new DefaultDingTalkClient(url);
		OapiChatSendRequest req = new OapiChatSendRequest();
		req.setChatid(mapp.getFdChatId());
		Msg msg = new Msg();
		ActionCard actioncard = new ActionCard();
		actioncard.setTitle(title);
		actioncard.setMarkdown(content);
		actioncard.setSingleTitle(singleTitle);
		actioncard.setSingleUrl(buildOpenappUrl(singleURL));

		msg.setActionCard(actioncard);
		msg.setMsgtype("action_card");
		req.setMsg(msg);
		OapiChatSendResponse rsp = null;
		try {
			String token = DingUtils.getDingApiService().getAccessToken();
			rsp = client.execute(req, token);
		} catch (Exception e) {
			errMsg = e.getMessage();
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			addGroupmsgLog(mapp, null, url, start, errMsg, req, rsp);
		}
	}

	public static void sendGroupActionCardMsg(ThirdDingScenegroupMapp mapp,
			String title,
			String content, JSONArray btnsArray, String btnOrientation)
			throws Exception {
		Date start = new Date();
		String errMsg = null;
		String url = DingConstant.DING_PREFIX
				+ "/chat/send";
		DingTalkClient client = new DefaultDingTalkClient(url);
		OapiChatSendRequest req = new OapiChatSendRequest();
		req.setChatid(mapp.getFdChatId());
		Msg msg = new Msg();
		ActionCard actioncard = new ActionCard();
		actioncard.setTitle(title);
		actioncard.setMarkdown(content);
		actioncard.setBtnOrientation(btnOrientation);
		List<BtnJson> btns = new ArrayList<BtnJson>();
		if (btnsArray != null) {
			for (int i = 0; i < btnsArray.size(); i++) {
				JSONObject o = btnsArray.getJSONObject(i);
				String btnTitle = o.getString("title");
				String actionURL = o.getString("url");
				BtnJson btn = new BtnJson();
				btn.setActionUrl(buildOpenappUrl(actionURL));
				btn.setTitle(btnTitle);
				btns.add(btn);
			}
		}
		actioncard.setBtnJsonList(btns);
		msg.setActionCard(actioncard);
		msg.setMsgtype("action_card");
		req.setMsg(msg);
		OapiChatSendResponse rsp = null;
		try {
			String token = DingUtils.getDingApiService().getAccessToken();
			rsp = client.execute(req, token);
		} catch (Exception e) {
			errMsg = e.getMessage();
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			addGroupmsgLog(mapp, null, url, start, errMsg, req, rsp);
		}

	}

	public static void sendGroupMarkdownMsg(ThirdDingScenegroupMapp mapp,
			String title,
			String content)
			throws Exception {
		Date start = new Date();
		String errMsg = null;
		String url = DingConstant.DING_PREFIX
				+ "/chat/send";
		DingTalkClient client = new DefaultDingTalkClient(url);
		OapiChatSendRequest req = new OapiChatSendRequest();
		req.setChatid(mapp.getFdChatId());
		req.setMsgtype("markdown");
		Markdown markdown = new Markdown();
		markdown.setTitle(title);
		markdown.setText(content);
		req.setMarkdown(markdown);
		OapiChatSendResponse rsp = null;
		try {
			String token = DingUtils.getDingApiService().getAccessToken();
			rsp = client.execute(req, token);
		} catch (Exception e) {
			errMsg = e.getMessage();
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			addGroupmsgLog(mapp, null, url, start, errMsg, req, rsp);
		}
	}

	public static void addGroupmsgLog(ThirdDingScenegroupMapp mapp,
			String title, String url, Date start, String errMsg,
			OapiChatSendRequest req, OapiChatSendResponse rsp) {
		IThirdDingGroupmsgLogService thirdDingGroupmsgLogService = (IThirdDingGroupmsgLogService) SpringBeanUtil
				.getBean("thirdDingGroupmsgLogService");
		ThirdDingGroupmsgLog log = new ThirdDingGroupmsgLog();
		log.setFdErrMsg(errMsg);
		log.setFdGroup(mapp);
		log.setFdExpireTime(new Date().getTime() - start.getTime());
		log.setFdReqData(JSON.toJSONString(req));
		log.setFdReqTime(start);
		log.setFdResTime(new Date());
		if (rsp != null) {
			log.setFdResData(JSON.toJSONString(rsp));
			if (rsp.getErrcode() == 0) {
				log.setFdResult(1);
			} else {
				log.setFdResult(2);
				log.setFdErrMsg(rsp.getErrmsg());
			}
		}
		if (StringUtil.isNotNull(errMsg)) {
			log.setFdResult(2);
			log.setFdErrMsg(errMsg);
		}
		log.setFdTitle(title);
		log.setFdUrl(url);
		try {
			thirdDingGroupmsgLogService.add(log);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static String buildUserIds(List<String> personIds) throws Exception {
		try {
			Set<String> dingIds = DingUtil.getDingIdSet(personIds);
			String userIds = "";
			for (String dingId : dingIds) {
				userIds += dingId + ",";
			}
			if (userIds.length() > 0) {
				userIds = userIds.substring(0, userIds.length() - 1);
			}
			return userIds;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	/**
	 * 查询群成员
	 * @param open_conversation_id
	 * @return
	 */
	public static OapiImChatScenegroupMemberGetResponse getScenegroupMembers(String open_conversation_id) throws Exception {
		String url = DingConstant.DING_PREFIX
				+ "/topapi/im/chat/scenegroup/member/get";
		DingTalkClient client = new DefaultDingTalkClient(url);
		OapiImChatScenegroupMemberGetRequest req = new OapiImChatScenegroupMemberGetRequest();
		req.setCursor("0");
		req.setSize(10L);
		req.setOpenConversationId(open_conversation_id);
		OapiImChatScenegroupMemberGetResponse rsp =null;
		try {
			String token = DingUtils.getDingApiService().getAccessToken();
			rsp = client.execute(req, token);
			logger.debug(rsp.getBody());
		} catch (Exception e) {
			logger.error("查询群成员失败",e);
		}
		return rsp;
	}

}
