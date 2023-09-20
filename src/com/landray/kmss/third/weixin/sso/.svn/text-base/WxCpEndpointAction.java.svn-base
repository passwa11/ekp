package com.landray.kmss.third.weixin.sso;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.third.weixin.api.WxApiService;
import com.landray.kmss.third.weixin.api.aes.WXBizMsgCrypt;
import com.landray.kmss.third.weixin.model.WeixinConfig;
import com.landray.kmss.third.weixin.util.WxUtils;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class WxCpEndpointAction extends BaseAction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WxCpEndpointAction.class);

	protected WxApiService wxApiService;

	private void init() {
		wxApiService = WxUtils.getWxApiService();
	}

	public ActionForward service(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		init();
		String msgSignature = request.getParameter("msg_signature");
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");
		String echostr = request.getParameter("echostr");

		WeixinConfig weixinConfig = WeixinConfig.newInstance();
		String sToken = weixinConfig.getWxToken();
		String sCorpID = weixinConfig.getWxCorpid();
		String sEncodingAESKey = weixinConfig.getWxAeskey();
		WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey,
				sCorpID);

		// echostr不为空为验证请求，否则为实际业务请求
		if (StringUtil.isNotNull(echostr)) {
			String plainText; // 需要返回的明文
			try {
				plainText = wxcpt.VerifyURL(msgSignature, timestamp,
						nonce, echostr);
				logger.warn("verifyurl echostr: " + plainText);
				// 验证URL成功，将sEchoStr返回
				response.getWriter().println(plainText);
				return null;
			} catch (Exception e) {
				// 验证URL失败，错误原因请查看异常
				e.printStackTrace();
				response.getWriter().println("非法请求");
				return null;
			}
		}

		// 接收post数据
		StringBuffer sb = new StringBuffer();
		InputStream is = request.getInputStream();
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String str = sb.toString();

		String sReqData = str;
		try {
			// String sMsg = wxcpt.DecryptMsg(msgSignature, timestamp, nonce,
			// sReqData);
			// logger.debug("after decrypt msg: " + sMsg);

		} catch (Exception e) {
			logger.error("微信回调消息解密异常", e); // 解密失败，失败原因请查看异常
			e.printStackTrace();
		}

		// TODO
		// 在这里处理回调逻辑
		return null;
	}
}
