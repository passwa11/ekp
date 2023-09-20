package com.landray.kmss.third.ding.scenegroup;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.request.OapiRobotSendRequest.Btns;
import com.dingtalk.api.response.OapiChatSendResponse;
import com.dingtalk.api.response.OapiImChatScenegroupCreateResponse;
import com.dingtalk.api.response.OapiImChatScenegroupMemberAddResponse;
import com.dingtalk.api.response.OapiImChatScenegroupMemberDeleteResponse;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.sso.client.util.StringUtil;

public class DingScenegroupApiService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingScenegroupApiService.class);

	public static String createScenegroup(String title,
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
		req.setTemplateId("2a7dd017-d068-4145-8ddd-55aee3ad771e");
		OapiImChatScenegroupCreateResponse rsp = client.execute(req, token);
		System.out.println(rsp.getBody());
		return rsp.getResult().getOpenConversationId();
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
		System.out.println(rsp.getBody());
		if (rsp.getErrcode() == 0) {
			return true;
		} else {
			logger.error(rsp.getBody());
			return false;
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
		System.out.println(rsp.getBody());
		if (rsp.getErrcode() == 0) {
			return true;
		} else {
			logger.error(rsp.getBody());
			return false;
		}
	}

	public static void main(String[] args) throws Exception {
		sendRobotMsg();
	}

	public static void sendRobotTextMsg(String content) throws Exception {
		Long timestamp = System.currentTimeMillis();
		String sign = getRobotMsgSign(timestamp);
		// https://oapi.dingtalk.com/robot/send?access_token=1135999450edb5e5779475ce69915b88780924e290ff3d9e9804a3698dbbeacd
		String url = "https://oapi.dingtalk.com"
				+ "/robot/send?access_token=1135999450edb5e5779475ce69915b88780924e290ff3d9e9804a3698dbbeacd&timestamp="
				+ timestamp + "&sign=" + sign
		// + DingUtil.getDingAppKeyByEKPUserId("&", null)
		;
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
		client.execute(request);
	}

	public static void sendRobotLinkMsg(String title, String content,
			String url) throws Exception {
		DingConfig config = DingConfig.newInstance();
		String corpId = config.getDingCorpid();
		String agentId = config.getDingAgentid();
		Long timestamp = System.currentTimeMillis();
		String sign = getRobotMsgSign(timestamp);
		// https://oapi.dingtalk.com/robot/send?access_token=1135999450edb5e5779475ce69915b88780924e290ff3d9e9804a3698dbbeacd
		String url1 = "https://oapi.dingtalk.com"
				+ "/robot/send?access_token=1135999450edb5e5779475ce69915b88780924e290ff3d9e9804a3698dbbeacd&timestamp="
				+ timestamp + "&sign=" + sign
		// + DingUtil.getDingAppKeyByEKPUserId("&", null)
		;
		DingTalkClient client = new DefaultDingTalkClient(url1);
		OapiRobotSendRequest request = new OapiRobotSendRequest();
		request.setMsgtype("link");
		OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
		// link.setMessageUrl("https://www.dingtalk.com?ddtab=true");
		link.setMessageUrl(
				"dingtalk://dingtalkclient/page/link?url=http%3A%2F%2Fhuangwq.qicp.vip/km/review&pc_slide=true");
		// link.setMessageUrl(
		// "dingtalk://dingtalkclient/page/link?url=http%3A%2F%2Fjava.landray.com.cn&ddtab=true");
		link.setMessageUrl(
				"dingtalk://dingtalkclient/action/openapp?corpid=" + corpId
						+ "&container_type=work_platform&app_id=0_" + agentId
						+ "&redirect_type=jump&redirect_url="
						+ URLEncoder.encode(url));

		link.setPicUrl("");
		link.setTitle(title);
		link.setText(
				content);

		request.setLink(link);
		client.execute(request);
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

	public static void sendRobotMarkdownMsg(String title, String content)
			throws Exception {

		Long timestamp = System.currentTimeMillis();
		String sign = getRobotMsgSign(timestamp);
		// https://oapi.dingtalk.com/robot/send?access_token=1135999450edb5e5779475ce69915b88780924e290ff3d9e9804a3698dbbeacd
		String url1 = "https://oapi.dingtalk.com"
				+ "/robot/send?access_token=1135999450edb5e5779475ce69915b88780924e290ff3d9e9804a3698dbbeacd&timestamp="
				+ timestamp + "&sign=" + sign;
		DingTalkClient client = new DefaultDingTalkClient(url1);
		OapiRobotSendRequest request = new OapiRobotSendRequest();
		request.setMsgtype("markdown");
		OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
		markdown.setTitle(title);
		// markdown.setText("#### 杭州天气 @13560309442\n" +
		// "> 9度，西北风1级，空气良89，相对温度73%\n\n" +
		// ">![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png)\n"
		// +
		// "> ######
		// 10点20分发布[天气](dingtalk://dingtalkclient/page/link?url=http%3A%2F%2Fhuangwq.qicp.vip/ekp/km/review&pc_slide=true)"
		// +"\n");
		markdown.setText(content);
		request.setMarkdown(markdown);

		OapiRobotSendResponse response = client.execute(request);
	}

	public static void sendRobotActioncardMsg(String title, String content,
			String singleTitle, String singleURL) throws Exception {
		Long timestamp = System.currentTimeMillis();
		String sign = getRobotMsgSign(timestamp);
		// https://oapi.dingtalk.com/robot/send?access_token=1135999450edb5e5779475ce69915b88780924e290ff3d9e9804a3698dbbeacd
		String url1 = "https://oapi.dingtalk.com"
				+ "/robot/send?access_token=2c30f8317cae88530746cd583a919f188b2a3ef1c8f0ed0b0db3b508e0c94668&timestamp="
				+ timestamp + "&sign=" + sign
		// + DingUtil.getDingAppKeyByEKPUserId("&", null)
		;
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
		System.out.println(resp.getBody());
	}

	public static void sendRobotActioncardMsg(String title, String content,
			JSONArray btnsArray, String btnOrientation) throws Exception {
		Long timestamp = System.currentTimeMillis();
		String sign = getRobotMsgSign(timestamp);
		// https://oapi.dingtalk.com/robot/send?access_token=1135999450edb5e5779475ce69915b88780924e290ff3d9e9804a3698dbbeacd
		String url1 = "https://oapi.dingtalk.com"
				+ "/robot/send?access_token=2c30f8317cae88530746cd583a919f188b2a3ef1c8f0ed0b0db3b508e0c94668&timestamp="
				+ timestamp + "&sign=" + sign
		// + DingUtil.getDingAppKeyByEKPUserId("&", null)
		;
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
		client.execute(request);
	}

	public static void sendRobotMsg() throws Exception {
		String token = "1135999450edb5e5779475ce69915b88780924e290ff3d9e9804a3698dbbeacd";
		token = "2c30f8317cae88530746cd583a919f188b2a3ef1c8f0ed0b0db3b508e0c94668";
		Long timestamp = System.currentTimeMillis();
		String sign = getRobotMsgSign(timestamp);
		// https://oapi.dingtalk.com/robot/send?access_token=1135999450edb5e5779475ce69915b88780924e290ff3d9e9804a3698dbbeacd
		String url = "https://oapi.dingtalk.com"
				+ "/robot/send?access_token=" + token + "&timestamp="
				+ timestamp + "&sign=" + sign
		// + DingUtil.getDingAppKeyByEKPUserId("&", null)
		;
		DingTalkClient client = new DefaultDingTalkClient(url);
		OapiRobotSendRequest request = new OapiRobotSendRequest();
		request.setMsgtype("text");
		OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
		text.setContent("测试文本消息");
		request.setText(text);
		OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
		// at.setAtMobiles(Arrays.asList("132xxxxxxxx"));
		// isAtAll类型如果不为Boolean，请升级至最新SDK
		at.setIsAtAll(true);
		request.setAt(at);
		client.execute(request);

		request.setMsgtype("link");
		OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
		// link.setMessageUrl("https://www.dingtalk.com?ddtab=true");
		link.setMessageUrl(
				"dingtalk://dingtalkclient/page/link?url=http%3A%2F%2Fjava.landray.com.cn&pc_slide=true");
		link.setMessageUrl(
				"dingtalk://dingtalkclient/page/link?url=http%3A%2F%2Fjava.landray.com.cn&ddtab=true");
		link.setMessageUrl(
				"dingtalk://dingtalkclient/page/link?url=http%3A%2F%2Fhuangwq.qicp.vip/ekp/km/review&pc_slide=true");
		link.setMessageUrl(
				"dingtalk://dingtalkclient/action/openapp?corpid=ding35a7fd308d38a9ee35c2f4657eb6378f&container_type=work_platform&app_id=0_696159955&redirect_type=jump&redirect_url=http://huangwq.qicp.vip/ekp/sys/notify");

		link.setPicUrl("");
		link.setTitle("流程管理");
		link.setText(
				"这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林");
		request.setLink(link);
		client.execute(request);

		request.setMsgtype("markdown");
		OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
		markdown.setTitle("杭州天气");
		markdown.setText("#### 杭州天气 @13560309442\n" +
				"> 9度，西北风1级，空气良89，相对温度73%\n\n" +
				"> ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png)\n"
				+
				"> ###### 10点20分发布 [天气](dingtalk://dingtalkclient/page/link?url=http%3A%2F%2Fhuangwq.qicp.vip/ekp/km/review&pc_slide=true) \n");
		request.setMarkdown(markdown);
		OapiRobotSendResponse response = client.execute(request);
	}

	public static String getRobotMsgSign(Long timestamp) throws Exception {
		String secret = "SEC2dfdf11dd3d42e55abfe45ead17ed08496efc78dd76cd1680e7c0ca6c6436074";
		secret = "SEC670870e0bab22f93ab2d53dfa10542717a3249440c1797aa5ec6a4c87b8ca2b2";
		String stringToSign = timestamp + "\n" + secret;
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
		byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
		String sign = URLEncoder
				.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
		System.out.println(sign);
		return sign;
	}


	public static void sendGroupTextMsg(String chatId, String content)
			throws Exception {
		String token = DingUtils.getDingApiService().getAccessToken();
		String url = DingConstant.DING_PREFIX
				+ "/chat/send";
		DingTalkClient client = new DefaultDingTalkClient(url);
		OapiChatSendRequest req = new OapiChatSendRequest();
		req.setChatid(chatId);
		Msg msg = new Msg();
		Text text = new Text();
		text.setContent(content);
		msg.setText(text);
		msg.setMsgtype("text");
		req.setMsg(msg);
		OapiChatSendResponse rsp = client.execute(req, token);
		System.out.println(rsp.getBody());
	}

	public static void sendGroupLinkMsg(String chatId, String messageUrl,
			String title, String text)
			throws Exception {
		String token = DingUtils.getDingApiService().getAccessToken();
		String url = DingConstant.DING_PREFIX
				+ "/chat/send";
		DingTalkClient client = new DefaultDingTalkClient(url);
		OapiChatSendRequest req = new OapiChatSendRequest();
		req.setChatid(chatId);
		Msg msg = new Msg();
		Link link = new Link();
		link.setMessageUrl(buildOpenappUrl(messageUrl));
		link.setPicUrl("1");
		link.setTitle(title);
		link.setText(text);
		msg.setLink(link);
		msg.setMsgtype("link");
		req.setMsg(msg);
		OapiChatSendResponse rsp = client.execute(req, token);
		System.out.println(rsp.getBody());
	}

	public static void sendGroupActionCardMsg(String chatId, String title,
			String content, String singleTitle, String singleURL)
			throws Exception {
		String token = DingUtils.getDingApiService().getAccessToken();
		String url = DingConstant.DING_PREFIX
				+ "/chat/send";
		DingTalkClient client = new DefaultDingTalkClient(url);
		OapiChatSendRequest req = new OapiChatSendRequest();
		req.setChatid(chatId);
		Msg msg = new Msg();
		ActionCard actioncard = new ActionCard();
		actioncard.setTitle(title);
		actioncard.setMarkdown(content);
		actioncard.setSingleTitle(singleTitle);
		actioncard.setSingleUrl(buildOpenappUrl(singleURL));

		msg.setActionCard(actioncard);
		msg.setMsgtype("action_card");
		req.setMsg(msg);
		OapiChatSendResponse rsp = client.execute(req, token);
		System.out.println(rsp.getBody());
	}

	public static void sendGroupActionCardMsg(String chatId, String title,
			String content, JSONArray btnsArray, String btnOrientation)
			throws Exception {
		String token = DingUtils.getDingApiService().getAccessToken();
		String url = DingConstant.DING_PREFIX
				+ "/chat/send";
		DingTalkClient client = new DefaultDingTalkClient(url);
		OapiChatSendRequest req = new OapiChatSendRequest();
		req.setChatid(chatId);
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
		OapiChatSendResponse rsp = client.execute(req, token);
		System.out.println(rsp.getBody());

	}

	public static void sendGroupMarkdownMsg(String chatId, String title,
			String content)
			throws Exception {
		String token = DingUtils.getDingApiService().getAccessToken();
		String url = DingConstant.DING_PREFIX
				+ "/chat/send";
		DingTalkClient client = new DefaultDingTalkClient(url);
		OapiChatSendRequest req = new OapiChatSendRequest();
		req.setChatid(chatId);
		req.setMsgtype("markdown");
		Markdown markdown = new Markdown();
		markdown.setTitle(title);
		markdown.setText(content);
		req.setMarkdown(markdown);
		OapiChatSendResponse rsp = client.execute(req, token);
		System.out.println(rsp.getBody());

	}
}
