package com.landray.kmss.sys.portal.taglib;

import com.landray.kmss.sys.ui.taglib.widget.container.ContentTag;

public class PortletTag extends ContentTag {

	@Override
    public void doCatch(Throwable paramThrowable) throws Throwable {
		pageContext.getOut().append(acquireString(paramThrowable.getMessage()));
		logger.error(
				getClass().getName() + ":error:" + paramThrowable.getMessage(),
				paramThrowable);
	}
}
