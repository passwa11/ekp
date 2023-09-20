package com.landray.kmss.third.weixin.action;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.third.weixin.api.WxApiService;
import com.landray.kmss.third.weixin.api.aes.WXBizMsgCrypt;
import com.landray.kmss.third.weixin.model.WeixinConfig;
import com.landray.kmss.third.weixin.util.WxUtils;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WxPayEndpointAction extends BaseAction {
	private static final Logger logger = LoggerFactory.getLogger(WxPayEndpointAction.class);

	protected WxApiService wxApiService;

	private void init() {
		wxApiService = WxUtils.getWxApiService();
	}

	public ActionForward service(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		String returnTemp = "<xml>" +
				"  <return_code><![CDATA[{return_code}]]></return_code>" +
				"  <return_msg><![CDATA[{return_msg}]]></return_msg>\n" +
				"</xml>";
		returnTemp.replace("return_code","SUCCESS");
		returnTemp.replace("return_msg","OK");
		response.getWriter().write(returnTemp);

		return null;
	}

	@Override
	protected String getMethodName(ActionMapping mapping, ActionForm form,
								   HttpServletRequest request, HttpServletResponse response,
								   String parameter) throws Exception {
		return "service";
	}
}
