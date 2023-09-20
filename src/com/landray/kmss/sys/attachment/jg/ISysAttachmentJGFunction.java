package com.landray.kmss.sys.attachment.jg;

import DBstep.iMsgServer2000;

import com.landray.kmss.common.actions.RequestContext;

public interface ISysAttachmentJGFunction {
	// 执行该功能特性
	public void execute(RequestContext request, iMsgServer2000 MsgObj)
			throws Exception;
}
