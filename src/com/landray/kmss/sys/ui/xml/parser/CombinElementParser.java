package com.landray.kmss.sys.ui.xml.parser;

import com.landray.kmss.sys.config.xml.ElementParser;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiCombin;
import org.w3c.dom.Element;

import java.io.Serializable;

public class CombinElementParser implements ElementParser {
 
	@Override
    public Serializable parse(Element item) {
		String xmlPath = item.getAttribute(XmlReaderContext.XML_SOURCE_PATH);
		String id = item.getAttribute("id");
		String name = item.getAttribute("name");
		String thumb = item.getAttribute("thumb");
		String help = item.getAttribute("help");
		String file = item.getAttribute("file");
		SysUiCombin combin = new SysUiCombin(id, name, thumb, help, file);
		combin.setXmlPath(xmlPath);
		SysUiPluginUtil.getCombins().put(id, combin);
		return null;
	}
}
