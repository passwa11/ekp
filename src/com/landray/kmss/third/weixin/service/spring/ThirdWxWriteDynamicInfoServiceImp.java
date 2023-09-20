package com.landray.kmss.third.weixin.service.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.third.weixin.api.WxApiService;
import com.landray.kmss.third.weixin.constant.WxConstant;
import com.landray.kmss.third.weixin.model.ThirdWxDynamicinfo;
import com.landray.kmss.third.weixin.model.WeixinConfig;
import com.landray.kmss.third.weixin.model.api.WxArticle;
import com.landray.kmss.third.weixin.model.api.WxMessage;
import com.landray.kmss.third.weixin.service.IThirdWxDynamicinfoService;
import com.landray.kmss.third.weixin.service.IThirdWxWriteDynamicInfoService;
import com.landray.kmss.third.weixin.util.WxUtils;
import com.landray.kmss.util.StringUtil;

public class ThirdWxWriteDynamicInfoServiceImp
		implements
			IThirdWxWriteDynamicInfoService,
			WxConstant {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(ThirdWxWriteDynamicInfoServiceImp.class);

	private IThirdWxDynamicinfoService thirdWxDynamicinfoService;

	public void setThirdWxDynamicinfoService(
			IThirdWxDynamicinfoService thirdWxDynamicinfoService) {
		this.thirdWxDynamicinfoService = thirdWxDynamicinfoService;
	}

	private ThreadPoolTaskExecutor wxTaskExecutor;
	
	public void setWxTaskExecutor(ThreadPoolTaskExecutor wxTaskExecutor) {
		this.wxTaskExecutor = wxTaskExecutor;
	}

	@Override
    public void writeDynamicInfo(ThirdWxDynamicinfo dynamicinfo) {
		try {
			wxTaskExecutor.execute(new WxNotifyPostDataRunner(dynamicinfo));
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("error", e);
		}
	}

	public class WxNotifyPostDataRunner implements Runnable{
		private WxApiService wxApiService = null;
		private ThirdWxDynamicinfo dynamicinfo;

		public WxNotifyPostDataRunner(ThirdWxDynamicinfo dynamicinfo) {
			this.dynamicinfo = dynamicinfo;
		}

		@Override
        public void run() {
			try {
				wxApiService = WxUtils.getWxApiService();
				WxMessage message = createWxNewsMessage(dynamicinfo);
				message.setToUser("@all");
				wxApiService.messageSend(message);
			} catch (Exception e) {
				log.error("", e);
			}
		}

		private WxMessage createWxNewsMessage(ThirdWxDynamicinfo dynamicinfo)
				throws Exception {
			WxMessage message = new WxMessage();
			String agentId = dynamicinfo.getAgentid();
			if(StringUtil.isNull(agentId)){
				message.setAgentId(WeixinConfig.newInstance().getWxAgentid());
			}else{
				message.setAgentId(agentId);
			}
			message.setMsgType(WxConstant.CUSTOM_MSG_NEWS);
			String url = dynamicinfo.getDocUrl();
			if (StringUtil.isNotNull(url)) {
				if (url.indexOf("?") > -1) {
					url += "&oauth=" + OAUTH_EKP_FLAG;
				} else {
					url += "?oauth=" + OAUTH_EKP_FLAG;
				}

			}
			WxArticle article = new WxArticle();
			article.setUrl(url);
			article.setDescription(dynamicinfo.getDocDescription());
			article.setTitle(dynamicinfo.getDocTitle());
			if(StringUtil.isNotNull(dynamicinfo.getPicUrl())) {
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
