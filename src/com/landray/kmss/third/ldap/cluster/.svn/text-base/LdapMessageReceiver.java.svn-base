package com.landray.kmss.third.ldap.cluster;

import java.util.Map;

import com.landray.kmss.sys.cluster.interfaces.message.IMessage;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageQueue;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageReceiver;
import com.landray.kmss.sys.cluster.interfaces.message.UniqueMessageQueue;
import com.landray.kmss.third.ldap.authentication.LdapAuthenticationProcessingFilter;
import com.landray.kmss.util.SpringBeanUtil;

public class LdapMessageReceiver implements IMessageReceiver {

	protected IMessageQueue messageQueue = new UniqueMessageQueue();

	@Override
    public IMessageQueue getMessageQueue() {
		return messageQueue;
	}

	@Override
    public void receiveMessage(IMessage message) throws Exception {
		if(!(message instanceof LdapMessage)){
			return;
		}
		LdapMessage msg = (LdapMessage) message;
		String messageType = msg.getMessageType();
		Map<String, String> dataMap = msg.getDataMap();
		if (LdapMessageType.LDAP_MESSAGE_DETAIL_CONFIG_UPDATE.equals(messageType)) {
			LdapAuthenticationProcessingFilter ldapProcessingFilter = (LdapAuthenticationProcessingFilter)SpringBeanUtil.getBean("ldapProcessingFilter");
			if (dataMap != null && dataMap.size() > 0) {
				ldapProcessingFilter.update(dataMap);
			} else {
				ldapProcessingFilter.update(null);
			}
		}

	}

	
}
