package com.landray.kmss.third.feishu.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.feishu.service.IThirdFeishuService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 获取钉钉授权信息
 */
public class FeishuJsapiAction extends BaseAction {
	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(FeishuJsapiAction.class);

	public ActionForward jsapiSignature(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-jsapiSignature", true, getClass());
		KmssMessages messages = new KmssMessages();
		logger.debug("jsapiSignature start");
		try {
			String url = request.getParameter("url");
			String queryString = null;
			IThirdFeishuService thirdFeishuService = (IThirdFeishuService) SpringBeanUtil
					.getBean("thirdFeishuService");
			if (StringUtil.isNotNull(url) && url.indexOf("?") > -1) {
				queryString = url.substring(url.indexOf("?") + 1, url.length());
				url = url.substring(0, url.indexOf("?"));
			}
			String result = thirdFeishuService.getConfig(url, queryString);
			if (UserOperHelper.allowLogOper("jsapiSignature", "*")) {
				UserOperHelper.logMessage(result);
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		logger.debug("jsapiSignature end");
		TimeCounter.logCurrentTime("Action-jsapiSignature", true, getClass());
		return null;
	}

}
