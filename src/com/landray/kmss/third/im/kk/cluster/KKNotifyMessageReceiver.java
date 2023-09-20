package com.landray.kmss.third.im.kk.cluster;

import java.util.Map;

import com.landray.kmss.sys.cluster.interfaces.message.IMessage;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageQueue;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageReceiver;
import com.landray.kmss.sys.cluster.interfaces.message.UniqueMessageQueue;
import com.landray.kmss.third.im.kk.KkConfig;

public class KKNotifyMessageReceiver implements IMessageReceiver {

	protected IMessageQueue messageQueue = new UniqueMessageQueue();

	@Override
    public IMessageQueue getMessageQueue() {
		return messageQueue;
	}

	@Override
    public void receiveMessage(IMessage message) throws Exception {
		if (!(message instanceof KKNotifyMessage)) {
			return;
		}
		KKNotifyMessage msg = (KKNotifyMessage) message;
		String messageType = msg.getMessageType();
		Map<String, String> setting = msg.getSetting();
		if (KKNotifyMessageType.NOTIFY_MESSAGE_KK_CONFIG_UPDATE.equals(messageType)) {
			KkConfig kkConfig = new KkConfig();
			//更新数据到缓存
			kkConfig.getDataMap().putAll(setting);
		}
	}

	
}
