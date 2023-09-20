package com.landray.kmss.sys.mportal.xml;

import com.landray.kmss.sys.config.xml.NamespaceHandlerSupport;

public class MlinkNamespaceHandler extends NamespaceHandlerSupport {
	@Override
	public void init() {
		registerBeanDefinitionParser("mlink", new MlinkElementParser());
	}
}
