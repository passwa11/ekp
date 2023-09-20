package com.landray.kmss.third.weixin.mutil.service.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.third.weixin.mutil.api.WxmutilApiService;
import com.landray.kmss.third.weixin.mutil.constant.WxmutilConstant;
import com.landray.kmss.third.weixin.mutil.model.ThirdWxworkMutilDynamicinfo;
import com.landray.kmss.third.weixin.mutil.model.WeixinMutilConfig;
import com.landray.kmss.third.weixin.mutil.model.api.WxArticle;
import com.landray.kmss.third.weixin.mutil.model.api.WxMessage;
import com.landray.kmss.third.weixin.mutil.service.IThirdWxworkDynamicinfoService;
import com.landray.kmss.third.weixin.mutil.service.IThirdWxworkWriteDynamicInfoService;
import com.landray.kmss.third.weixin.mutil.util.WxmutilUtils;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SecureUtil;
import com.landray.kmss.util.StringUtil;

public class ThirdWxworkWriteDynamicInfoServiceImp
		implements
			IThirdWxworkWriteDynamicInfoService,
		WxmutilConstant {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(ThirdWxworkWriteDynamicInfoServiceImp.class);

	private IThirdWxworkDynamicinfoService thirdMutilWxworkDynamicinfoService;

	public void setThirdMutilWxworkDynamicinfoService(
			IThirdWxworkDynamicinfoService thirdMutilWxworkDynamicinfoService) {
		this.thirdMutilWxworkDynamicinfoService = thirdMutilWxworkDynamicinfoService;
	}

	private ThreadPoolTaskExecutor wxTaskExecutor;

	public void setWxTaskExecutor(ThreadPoolTaskExecutor wxTaskExecutor) {
		this.wxTaskExecutor = wxTaskExecutor;
	}

	private WxmutilApiService wxmutilApiService = null;

	@Override
	public void writeDynamicInfo(ThirdWxworkMutilDynamicinfo dynamicinfo, String wxkey) {
		try {
			wxmutilApiService = WxmutilUtils.getWxmutilApiServiceList()
					.get(wxkey);
			wxTaskExecutor.execute(new WxworkNotifyPostDataRunner(dynamicinfo, wxkey));
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("error", e);
		}
	}

	public class WxworkNotifyPostDataRunner implements Runnable {
		private ThirdWxworkMutilDynamicinfo dynamicinfo;
		
		private String wxkey;

		public WxworkNotifyPostDataRunner(ThirdWxworkMutilDynamicinfo dynamicinfo, String wxkey) {
			this.dynamicinfo = dynamicinfo;
			this.wxkey = wxkey;
		}

		@Override
        public void run() {
			try {
				WxMessage message = createWxNewsMessage(dynamicinfo, wxkey);
				message.setToUser("@all");
				String agentId = dynamicinfo.getAgentid();
				if(StringUtil.isNotNull(agentId)){
					message.setAgentId(agentId);
				}else{
					message.setAgentId(
							WeixinMutilConfig.newInstance(wxkey).getWxAgentid());
				}
				wxmutilApiService.messageSend(message);
				thirdMutilWxworkDynamicinfoService.delete(dynamicinfo);
			} catch (Exception e) {
				log.error("", e);
			}
		}

		private WxMessage createWxNewsMessage(
				ThirdWxworkMutilDynamicinfo dynamicinfo, String wxkey) throws Exception {
			WxMessage message = new WxMessage();
			String agentId = dynamicinfo.getAgentid();
			if(StringUtil.isNull(agentId)){
				message.setAgentId(WeixinMutilConfig.newInstance(wxkey).getWxAgentid());
			}else{
				message.setAgentId(agentId);
			}
			message.setMsgType(WxmutilConstant.CUSTOM_MSG_NEWS);
			String url = dynamicinfo.getDocUrl();
			if (StringUtil.isNotNull(url)) {
				if (url.indexOf("?") > -1) {
					url += "&oauth=" + OAUTH_EKP_FLAG;
				} else {
					url += "?oauth=" + OAUTH_EKP_FLAG;
				}
			}
			String domainName = WeixinMutilConfig.newInstance(wxkey).getWxDomain();
			if(StringUtil.isNull(domainName)) {
                domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
            }
			if(domainName.endsWith("/")) {
                domainName = domainName.substring(0, domainName.length()-1);
            }
			String purl = domainName + "/third/weixin/work/sso/pc_message.jsp?oauth="+OAUTH_EKP_FLAG;
			purl = purl + "&url=" + SecureUtil.BASE64Encoder(url);
			WxArticle article = new WxArticle();
			article.setUrl(purl);
			article.setDescription(dynamicinfo.getDocDescription());
			article.setTitle(dynamicinfo.getDocTitle());
			if (StringUtil.isNotNull(dynamicinfo.getPicUrl())) {
                article.setPicUrl(dynamicinfo.getPicUrl());
            }
			message.getArticles().add(article);
			if (log.isDebugEnabled()) {
				log.debug("wxMessage::" + JSON.toJSONString(message));
			}
			return message;
		}

	}

}
