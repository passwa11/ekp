package com.landray.kmss.sys.attachment.jg;

import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;

public interface ISysAttachmentJGAddtionFunction {
	// 执行该附加功能功能特性
	public void execute(RequestContext request, HttpServletResponse response)
			throws Exception;
}
