package com.landray.kmss.eop.basedata.xml;

import com.landray.kmss.sys.config.xml.NamespaceHandlerSupport;

public class EopBasedataFsscxmlNamespaceHandler extends NamespaceHandlerSupport{

	@Override
	public void init() {
		registerBeanDefinitionParser("model", new EopBasedataFsscxmlElementParser());
		registerBeanDefinitionParser("validator", new EopBasedataFsscxmlElementParser());
		registerBeanDefinitionParser("element", new EopBasedataFsscxmlElementParser());
	}
	
}
