package com.landray.kmss.third.weixin.work.action;

import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.third.weixin.work.model.ThirdWeixinCgUserMapp;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinCgUserMappService;
import com.landray.kmss.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.constant.WxworkConstant;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class WxJsapiAction extends BaseAction implements WxworkConstant{

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WxJsapiAction.class);

	/**
	 * 获取下游组织的用户企业ID
	 * @return
	 */
	private String getUserCorpId(){
		String corpGroupIntegrateEnable = WeixinWorkConfig.newInstance().getCorpGroupIntegrateEnable();
		if(!"true".equals(corpGroupIntegrateEnable)) {
			return WeixinWorkConfig.newInstance().getWxCorpid();
		}
		Boolean isExternal = UserUtil.getUser().getFdIsExternal();
		if(isExternal==true){
			try {
				IThirdWeixinCgUserMappService thirdWeixinCgUserMappService = (IThirdWeixinCgUserMappService) SpringBeanUtil.getBean("thirdWeixinCgUserMappService");
				ThirdWeixinCgUserMapp thirdWeixinCgUserMapp = thirdWeixinCgUserMappService.findByEkpId(UserUtil.getUser().getFdId());
				if (thirdWeixinCgUserMapp != null) {
					return thirdWeixinCgUserMapp.getFdCorpId();
				}
			}catch (Exception e){
				logger.error(e.getMessage(),e);
			}
		}
		return WeixinWorkConfig.newInstance().getWxCorpid();
	}

	public ActionForward jsapiSignature(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-jsapiSignature", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String url = request.getParameter("url");
			String type = request.getParameter("type");
			logger.debug("鉴权type:" + type);

			String appId = WeixinWorkConfig.newInstance().getWxCorpid();
			JSONObject result = new JSONObject();
			if (StringUtil.isNotNull(appId)) {
				String corpGroupCorpId = getUserCorpId();
				WxworkApiService wxworkApiService = WxworkUtils
						.getWxworkApiService();
				Map<String, Object> resultMap = null;
				if (StringUtil.isNotNull(type) && "agent_config".equals(type)) {
					resultMap = wxworkApiService
							.createAgentConfigJsapiSignature(url,
									WeixinWorkConfig
									.newInstance().getWxAgentid(),
									corpGroupCorpId);
					if(StringUtil.isNotNull(corpGroupCorpId) && !appId.equals(corpGroupCorpId)) {
						result.put("agentid",
								wxworkApiService.getAppShareInfoMap().get(corpGroupCorpId).getAgentId());
						result.put("corpid",
								corpGroupCorpId);
						appId = corpGroupCorpId;
					}else{
						result.put("agentid",
								WeixinWorkConfig.newInstance().getWxAgentid());
						result.put("corpid",
								WeixinWorkConfig.newInstance().getWxCorpid());
					}
				} else {
					resultMap = wxworkApiService
							.createJsapiSignature(url,corpGroupCorpId);
					if(StringUtil.isNotNull(corpGroupCorpId)) {
						appId = corpGroupCorpId;
					}
				}
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
			logger.error(e.getMessage(), e);
		}
		TimeCounter.logCurrentTime("Action-jsapiSignature", true, getClass());
		return null;
	}
	
	public ActionForward pcJsapiSignature(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		TimeCounter.logCurrentTime("Action-jsapiSignature", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String url = request.getParameter("url");
//			if(StringUtil.isNotNull(url)){
//				url = URLDecoder.decode(url,"UTF-8");
//			}
			String isBase64 = request.getParameter("base64");
			String pUrl = StringUtil.getParameter(url, "pUrl");
			if ("true".equals(isBase64)) {
				pUrl = SecureUtil.BASE64Decoder(pUrl);
			}
			if (StringUtil.isNull(pUrl)) {
				pUrl = request.getParameter("pUrl");
			} else {
				pUrl = URLDecoder.decode(pUrl, "UTF-8");
			}
			if(StringUtil.isNotNull(pUrl)){
				if(!pUrl.startsWith("http")||!pUrl.startsWith("https")){
					String domainName = WeixinWorkConfig.newInstance().getWxDomain();
					if(StringUtil.isNull(domainName)) {
                        domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
                    }
					if(domainName.endsWith("/")) {
                        domainName = domainName.substring(0, domainName.length()-1);
                    }
					pUrl = StringUtil.formatUrl(pUrl, domainName);		
					if(pUrl.indexOf("?")==-1){
						pUrl = pUrl + "?oauth=" + OAUTH_EKP_FLAG;
					}else{
						pUrl = pUrl + "&oauth=" + OAUTH_EKP_FLAG;
					}
				}
			}
			String appId = WeixinWorkConfig.newInstance().getWxCorpid();
			JSONObject result = new JSONObject();
			if (StringUtil.isNotNull(appId)) {
				String corpGroupCorpId = getUserCorpId();
				WxworkApiService wxworkApiService = WxworkUtils
						.getWxworkApiService();
				Map<String, Object> resultMap = wxworkApiService
						.createJsapiSignature(url,corpGroupCorpId);
				String jsapiTicket = wxworkApiService.getJsapiTicket(corpGroupCorpId);
				if(StringUtil.isNotNull(corpGroupCorpId)){
					appId = corpGroupCorpId;
				}
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
					result.put("url", wxworkApiService
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

	public ActionForward checkLogin(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		JSONObject result = new JSONObject();
		result.put("hadLogin",!(UserUtil.getUser().isAnonymous()));
		if(!(UserUtil.getUser().isAnonymous())){
			//加载鉴权信息
			String url = request.getParameter("url");
			String appId = WeixinWorkConfig.newInstance().getWxCorpid();
			if (StringUtil.isNotNull(appId)) {
				long l = System.currentTimeMillis();
				Map<String, Object> resultMap =  WxworkUtils.getWxworkApiService().createJsapiSignature(url, appId);
				System.out.println("resultMap耗时："+(System.currentTimeMillis()-l));
				result.put("appId", appId);
				result.put("noncestr", resultMap.get("noncestr"));
				result.put("signature", resultMap.get("signature"));
				result.put("timestamp", resultMap.get("timestamp"));
			}
		}

		response.getWriter().write(result.toString());
		return null;
	}

}
