package com.landray.kmss.third.weixin.work.notify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.model.api.WxMessage;

public class WxNotifySendThread implements Runnable {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WxNotifySendThread.class);

	private WxworkApiService wxworkApiService = null;

	private WxMessage message = null;

	public WxNotifySendThread(WxworkApiService wxworkApiService,
			WxMessage message) {
		this.wxworkApiService = wxworkApiService;
		this.message = message;
	}

	@Override
	public void run() {
		try {
			wxworkApiService.messageSend(message);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
