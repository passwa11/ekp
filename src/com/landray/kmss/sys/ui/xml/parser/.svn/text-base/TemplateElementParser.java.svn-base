package com.landray.kmss.sys.ui.xml.parser;

import com.landray.kmss.sys.config.xml.ElementParser;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.util.ParserUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiTemplate;
import org.w3c.dom.Element;

import java.io.Serializable;

public class TemplateElementParser implements ElementParser {

	@Override
    public Serializable parse(Element item) {
		String dirName = item.getAttribute(XmlReaderContext.COMPONENTDIRNAME);
		String xmlPath = item.getAttribute(XmlReaderContext.XML_SOURCE_PATH);
		String code = item.getAttribute("id");
		String name = item.getAttribute("name");
		String file = item.getAttribute("file");
		String thumb = item.getAttribute("thumb");
		String help = item.getAttribute("help");
		String _for = item.getAttribute("for");
		String designer = item.getAttribute("designer");
		String path = item.getAttribute("path"); // 区分是否是自定义
		String thumbPath = item.getAttribute("thumb-path"); // 自定义页面模板的缩略图路径

		SysUiTemplate master = new SysUiTemplate(code, name, file, thumb, help,
				_for, designer, path, thumbPath);
		master.setXmlPath(xmlPath);
		SysUiPluginUtil.getTemplates().put(code, master);
		return null;
	}

}
