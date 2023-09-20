package com.landray.kmss.eop.basedata.xml;

import java.io.Serializable;

import org.w3c.dom.Element;

import com.landray.kmss.sys.config.xml.ElementParser;

public class EopBasedataFsscxmlElementParser implements ElementParser {

	@Override
	public Serializable parse(Element item) {
		String name = item.getAttribute("name");
		String keycol = item.getAttribute("key-col");
		EopBasedataFsscxmlModel fsscxml = new EopBasedataFsscxmlModel(name,keycol);
		EopBasedataXmlUtil.registerModel(fsscxml);
		return null;
	}
}
