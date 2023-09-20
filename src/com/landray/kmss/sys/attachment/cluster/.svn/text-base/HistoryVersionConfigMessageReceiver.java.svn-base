package com.landray.kmss.sys.attachment.cluster;

import com.landray.kmss.sys.attachment.plugin.HistoryVersionPlugin;
import com.landray.kmss.sys.cluster.interfaces.message.IMessage;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageQueue;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageReceiver;
import com.landray.kmss.sys.cluster.interfaces.message.UniqueMessageQueue;

public class HistoryVersionConfigMessageReceiver implements IMessageReceiver {

	protected IMessageQueue messageQueue = new UniqueMessageQueue();

	@Override
    public IMessageQueue getMessageQueue() {
		return messageQueue;
	}

	@Override
    public void receiveMessage(IMessage message) throws Exception {
		if (!(message instanceof HistoryVersionConfigMessage)) {
			return;
		}
		HistoryVersionConfigMessage msg = (HistoryVersionConfigMessage) message;
		String attHistoryConfigEnableModules = msg
				.getAttHistoryConfigEnableModules();
		HistoryVersionPlugin.resetEnabledModelsMap(attHistoryConfigEnableModules);

	}

	
}
