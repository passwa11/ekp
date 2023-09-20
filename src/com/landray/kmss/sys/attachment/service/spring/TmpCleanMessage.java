package com.landray.kmss.sys.attachment.service.spring;

import com.landray.kmss.sys.cluster.interfaces.message.IMessage;

public class TmpCleanMessage implements IMessage {

	private static final long serialVersionUID = 6972697994619589656L;

	private boolean immediateRun = false;

	public TmpCleanMessage() {
	}

	public TmpCleanMessage(boolean immediateRun) {
		this.immediateRun = immediateRun;
	}

	public boolean isImmediateRun() {
		return immediateRun;
	}

	public void setImmediateRun(boolean immediateRun) {
		this.immediateRun = immediateRun;
	}

}
