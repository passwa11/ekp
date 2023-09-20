package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.sys.cluster.interfaces.message.IMessage;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageQueue;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageReceiver;
import com.landray.kmss.sys.cluster.interfaces.message.UniqueMessageQueue;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.service.ISysOrganizationVisibleService;

public class SysOrgMessageReceiver implements IMessageReceiver {

	private ISysOrganizationVisibleService sysOrganizationVisibleService;
	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;
	protected IMessageQueue messageQueue = new UniqueMessageQueue();

	@Override
    public IMessageQueue getMessageQueue() {
		return messageQueue;
	}

	@Override
    public void receiveMessage(IMessage message) throws Exception {
		if(!(message instanceof SysOrgMessage)){
			return;
		}
		SysOrgMessage msg = (SysOrgMessage) message;
		String messageType = msg.getMessageType();
		if (SysOrgMessageType.ORG_MESSAGE_VISIBLE_UPDATE.equals(messageType)) {
			sysOrganizationVisibleService.updateCacheLocal(null);
		} else if (SysOrgMessageType.ORG_MESSAGE_STAFFING_LEVEL_UPDATE
				.equals(messageType)) {
			sysOrganizationStaffingLevelService.updateCacheLocal(null);
		}

	}

	public void setSysOrganizationVisibleService(
			ISysOrganizationVisibleService sysOrganizationVisibleService) {
		this.sysOrganizationVisibleService = sysOrganizationVisibleService;
	}

	public ISysOrganizationVisibleService getSysOrganizationVisibleService() {
		return sysOrganizationVisibleService;
	}

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
		return sysOrganizationStaffingLevelService;
	}

}
