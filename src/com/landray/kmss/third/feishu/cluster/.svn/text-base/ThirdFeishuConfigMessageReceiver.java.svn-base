package com.landray.kmss.third.feishu.cluster;

import com.landray.kmss.sys.cluster.interfaces.message.IMessage;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageQueue;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageReceiver;
import com.landray.kmss.sys.cluster.interfaces.message.UniqueMessageQueue;
import com.landray.kmss.third.feishu.service.IThirdFeishuService;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;

public class ThirdFeishuConfigMessageReceiver implements IMessageReceiver {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdFeishuConfigMessageReceiver.class);

	protected IMessageQueue messageQueue = new UniqueMessageQueue();

	@Override
    public IMessageQueue getMessageQueue() {
		return messageQueue;
	}

	@Override
    public void receiveMessage(IMessage message) throws Exception {
		if (!(message instanceof ThirdFeishuConfigMessage)) {
			return;
		}
		ThirdFeishuConfigMessage msg = (ThirdFeishuConfigMessage) message;
		String messageType = msg.getMessageType();
		if ("updateConfig"
				.equals(messageType)) {
			try {
				IThirdFeishuService thirdFeishuService = (IThirdFeishuService) SpringBeanUtil
						.getBean("thirdFeishuService");
				thirdFeishuService.resetToken();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

	}

	
}
