package com.landray.kmss.third.ekp.java.cluster;

import java.util.Map;

import com.landray.kmss.sys.cluster.interfaces.message.IMessage;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageQueue;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageReceiver;
import com.landray.kmss.sys.cluster.interfaces.message.UniqueMessageQueue;
import com.landray.kmss.third.ekp.java.EkpJavaConfig;
import com.landray.kmss.third.ekp.java.notify.EkpClientTodoThread;
import com.landray.kmss.third.ekp.java.oms.in.EkpRoleSynchroDelegate;
import com.landray.kmss.third.ekp.java.oms.in.EkpSynchroDelegate;
import com.landray.kmss.util.StringUtil;

public class EkpJavaConfigMessageReceiver implements IMessageReceiver {

	protected IMessageQueue messageQueue = new UniqueMessageQueue();

	@Override
    public IMessageQueue getMessageQueue() {
		return messageQueue;
	}

	@Override
    public void receiveMessage(IMessage message) throws Exception {
		if (!(message instanceof EkpJavaConfigMessage)) {
			return;
		}
		EkpJavaConfigMessage msg = (EkpJavaConfigMessage) message;
		String messageType = msg.getMessageType();
		Map<String, String> dataMap = msg.getSetting();
		if ("updateConfig"
				.equals(messageType)) {
			String password_des = dataMap.get("kmss.java.webservice.password");
			String password_md5 = dataMap
					.get("kmss.java.webservice.tnsPassword");
			String userName = dataMap
					.get("kmss.java.webservice.userName");
			EkpJavaConfig config = new EkpJavaConfig();
			if (StringUtil.isNotNull(userName)) {
				config.setValue("kmss.java.webservice.userName",
						userName);
			}
			if (StringUtil.isNotNull(password_md5)) {
				config.setValue("kmss.java.webservice.tnsPassword",
						password_md5);
			}
			if (StringUtil.isNotNull(password_des)) {
				config.setValue("kmss.java.webservice.password",
						password_des);
			}
			config.save();
			EkpClientTodoThread.resetNotifyTodoWebService();
			EkpSynchroDelegate.resetGetOrgWebService();
			EkpRoleSynchroDelegate.resetGetOrgWebService();
		}

	}

	
}
