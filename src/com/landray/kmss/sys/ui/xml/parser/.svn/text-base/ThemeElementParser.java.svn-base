package com.landray.kmss.sys.ui.xml.parser;

import com.landray.kmss.sys.config.xml.ElementParser;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiTheme;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.List;

public class ThemeElementParser implements ElementParser {

	@Override
    public Serializable parse(Element paramElement) {
		Element item = paramElement;
		String xmlPath = item.getAttribute(XmlReaderContext.XML_SOURCE_PATH);
		String id = item.getAttribute("id");
		String name = item.getAttribute("name");
		String path = item.getAttribute("path");
		String thumb = item.getAttribute("thumb");
		String help = item.getAttribute("help");

		SysUiTheme theme = new SysUiTheme(id, name, path, thumb, help);
		theme.setXmlPath(xmlPath);
		List<Element> file = DomUtils.getChildElementsByTagName(
				DomUtils.getChildElementByTagName(item, "style"), "css");
		for (int l = 0; l < file.size(); l++) {
			Element cssfile = file.get(l);
			String type = cssfile.getAttribute("type");
			String fpath = cssfile.getAttribute("path");
			theme.getFiles().put(type, fpath);
		}
		SysUiPluginUtil.getThemes().put(id, theme);
		return null;
	}

}
