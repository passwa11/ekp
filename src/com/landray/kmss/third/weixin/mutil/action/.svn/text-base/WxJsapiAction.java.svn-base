package com.landray.kmss.third.weixin.mutil.action;

import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.weixin.mutil.api.WxmutilApiService;
import com.landray.kmss.third.weixin.mutil.constant.WxmutilConstant;
import com.landray.kmss.third.weixin.mutil.model.WeixinMutilConfig;
import com.landray.kmss.third.weixin.mutil.util.WxmutilUtils;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class WxJsapiAction extends BaseAction implements WxmutilConstant {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WxJsapiAction.class);

	public ActionForward jsapiSignature(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-jsapiSignature", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String url = request.getParameter("url");
			WeixinMutilConfig config = WeixinMutilConfig.newInstance(null);
			String appId = config.getWxCorpid();
			String fdWxKey = config.getKey();
			JSONObject result = new JSONObject();
			if (StringUtil.isNotNull(appId)) {
				WxmutilApiService wxmutilApiService = WxmutilUtils
						.getWxmutilApiServiceList().get(fdWxKey);

				Map<String, Object> resultMap = wxmutilApiService
						.createJsapiSignature(url);
				result.put("appId", appId);
				result.put("noncestr", resultMap.get("noncestr"));
				result.put("signature", resultMap.get("signature"));
				result.put("timestamp", resultMap.get("timestamp"));
			} else {
				// logger.debug("企业微信ID为空,不生成jsapi签名");
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result.toString());
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-jsapiSignature", true, getClass());
		return null;
	}
	
	public ActionForward pcJsapiSignature(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		TimeCounter.logCurrentTime("Action-jsapiSignature", true, getClass());
		logger.debug("=========pcJsapiSignature============");
		KmssMessages messages = new KmssMessages();
		try {
			String url = request.getParameter("url");
			if(StringUtil.isNotNull(url)){
				url = URLDecoder.decode(url,"UTF-8");
			}
			String pUrl = StringUtil.getParameter(url, "pUrl");
			if(StringUtil.isNull(pUrl)) {
                pUrl = request.getParameter("pUrl");
            }
			String key = request.getParameter("wxkey");
			logger.debug("=========key============" + key);
			WeixinMutilConfig workConfig = WeixinMutilConfig.newInstance(key);
			String fdWxKey = workConfig.getKey();
			logger.debug("=========fdWxKey============" + fdWxKey);
			if(StringUtil.isNotNull(pUrl)){
				if(!pUrl.startsWith("http")||!pUrl.startsWith("https")){
					String domainName = workConfig.getWxDomain();
					if(StringUtil.isNull(domainName)) {
                        domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
                    }
					if(domainName.endsWith("/")) {
                        domainName = domainName.substring(0, domainName.length()-1);
                    }
					pUrl = StringUtil.formatUrl(pUrl, domainName);		
					if(pUrl.indexOf("?")==-1){
						pUrl = pUrl + "?oauth=" + OAUTH_EKP_FLAG + "&wxkey="
								+ fdWxKey;
					}else{
						pUrl = pUrl + "&oauth=" + OAUTH_EKP_FLAG + "&wxkey="
								+ fdWxKey;
					}
				}
			}
			String appId = workConfig.getWxCorpid();
			JSONObject result = new JSONObject();
			if (StringUtil.isNotNull(appId)) {
				WxmutilApiService wxmutilApiService = WxmutilUtils
						.getWxmutilApiServiceList().get(fdWxKey);

				Map<String, Object> resultMap = wxmutilApiService
						.createJsapiSignature(url);
				String jsapiTicket = wxmutilApiService.getJsapiTicket();
				result.put("appId", appId);
				result.put("noncestr", resultMap.get("noncestr"));
				result.put("signature", resultMap.get("signature"));
				result.put("timestamp", resultMap.get("timestamp"));
				result.put("jsapiTicket", jsapiTicket);
				if(StringUtil.isNotNull(pUrl)){
					if(pUrl.indexOf("?")==-1){
						pUrl = pUrl + "?pcWXSso=windowswechat";
					}else{
						pUrl = pUrl + "&pcWXSso=windowswechat";
					}
					result.put("url", wxmutilApiService
							.oauth2buildAuthorizationUrl(pUrl, null));
				}
			} else {
				logger.debug("企业微信ID为空,不生成jsapi签名");
			}
			if (UserOperHelper.allowLogOper("jsapiSignature", "*")) {
				UserOperHelper.logMessage(result.toString());
			}
			logger.debug(result.toString());
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result.toString());
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-jsapiSignature", true, getClass());
		return null;
	}

}
