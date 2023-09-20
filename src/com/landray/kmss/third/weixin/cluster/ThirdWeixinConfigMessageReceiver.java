package com.landray.kmss.third.weixin.cluster;

import com.landray.kmss.sys.cluster.interfaces.message.IMessage;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageQueue;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageReceiver;
import com.landray.kmss.sys.cluster.interfaces.message.UniqueMessageQueue;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;

import java.util.Map;

public class ThirdWeixinConfigMessageReceiver implements IMessageReceiver {

	protected IMessageQueue messageQueue = new UniqueMessageQueue();

	@Override
    public IMessageQueue getMessageQueue() {
		return messageQueue;
	}

	@Override
    public void receiveMessage(IMessage message) throws Exception {
		if (!(message instanceof ThirdWeixinConfigMessage)) {
			return;
		}
		ThirdWeixinConfigMessage msg = (ThirdWeixinConfigMessage) message;
		String messageType = msg.getMessageType();
		Map<String, String> dataMap = msg.getSetting();
		if ("resetAppShareInfoMap"
				.equals(messageType)) {
			String corpGroupAgentIds = dataMap.get("corpGroupAgentIds");
			WxworkApiService wxworkApiService = WxworkUtils.getWxworkApiService();
			wxworkApiService.resetAppShareInfoMap(corpGroupAgentIds);
		}

	}

	
}
