package com.landray.kmss.third.weixin.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.weixin.api.WxApiService;
import com.landray.kmss.third.weixin.model.WeixinConfig;
import com.landray.kmss.third.weixin.util.WxUtils;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class WxJsapiAction extends BaseAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WxJsapiAction.class);

	public ActionForward jsapiSignature(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-jsapiSignature", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String url = request.getParameter("url");
			String appId = WeixinConfig.newInstance().getWxCorpid();
			JSONObject result = new JSONObject();
			if (StringUtil.isNotNull(appId)) {
				WxApiService wxApiService = WxUtils.getWxApiService();
				Map<String, Object> resultMap = wxApiService
						.createJsapiSignature(url);
				result.put("appId", appId);
				result.put("noncestr", resultMap.get("noncestr"));
				result.put("signature", resultMap.get("signature"));
				result.put("timestamp", resultMap.get("timestamp"));
			} else {
				// logger.debug("微信企业ID为空,不生成jsapi签名");
			}
			response.setCharacterEncoding("UTF-8");
			if (UserOperHelper.allowLogOper("jsapiSignature", "*")) {
				UserOperHelper.logMessage(result.toString());
			}
			response.getWriter().write(result.toString());
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-jsapiSignature", true, getClass());
		return null;
	}

}
