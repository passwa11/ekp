package com.landray.kmss.sys.ui.xml.parser;

import com.landray.kmss.sys.config.xml.ElementParser;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.util.ParserUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiCode;
import com.landray.kmss.sys.ui.xml.model.SysUiLayout;
import com.landray.kmss.sys.ui.xml.model.SysUiVar;
import com.landray.kmss.util.StringUtil;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.io.Serializable;
import java.util.List;

public class LayoutElementParser implements ElementParser {
 
	@Override
    public Serializable parse(Element item) {
		String dirName = item.getAttribute(XmlReaderContext.COMPONENTDIRNAME);
		String xmlPath = item.getAttribute(XmlReaderContext.XML_SOURCE_PATH);
		String id = item.getAttribute("id");
		String name = item.getAttribute("name");
		String css = item.getAttribute("css");
		String type = item.getAttribute("type");
		String thumb = item.getAttribute("thumb");
		String help = item.getAttribute("help");
		String kind = item.getAttribute("kind");
		String _for = item.getAttribute("for");
		String path = item.getAttribute("path"); // 区分是否是自定义
		String thumbPath = item.getAttribute("thumb-path");// 自定义外观的缩略图路径

		String xtype = "";
		if (type.indexOf("!") > 0) {
			xtype = type;
		} else {
			xtype = "lui/view/layout!" + type;
		}
		Element code = DomUtils.getChildElementByTagName(item, "code");
		String codeBody = "", codeSrc = "";
		SysUiCode uicode = new SysUiCode(codeSrc, codeBody);
		if (code != null) {
			codeSrc = code.getAttribute("src");
			codeBody = DomUtils.getTextValue(code);
			uicode.setBody(codeBody);
			uicode.setSrc(codeSrc);
			NamedNodeMap atts = code.getAttributes();
			for (int j = 0; j < atts.getLength(); j++) {
				Node node = atts.item(j);
				String attributeName = node.getNodeName();
				String attributeValue = node.getNodeValue();
				if (attributeName != null && !"src".equals(attributeName)) {
					uicode.getParam().put(attributeName, attributeValue);
				}
			}
		}
		// System.out.println(content);
		String attribute = "/sys/ui/extend/panel/config.jsp";
		Element varsTag = DomUtils.getChildElementByTagName(item, "vars");
		SysUiLayout panel = null;
		if (varsTag != null) {
			List<Element> vars = DomUtils.getChildElementsByTagName(varsTag,
					"var");
			if (StringUtil.isNotNull(varsTag.getAttribute("config"))) {
				attribute = varsTag.getAttribute("config");
			}
			panel = new SysUiLayout(id, name, attribute, xtype, uicode, css,
					thumb, help, kind, _for, path, thumbPath);
			panel.setXmlPath(xmlPath);
			for (int k = 0; k < vars.size(); k++) {
				Element var = vars.get(k);
				String vkey = var.getAttribute("key");
				String vname = var.getAttribute("name");
				String vkind = var.getAttribute("kind");
				String vrequire = var.getAttribute("required");
				String vdefault = var.getAttribute("default");
				String vbody = DomUtils.getTextValue(var);
				panel.getFdVars().add(
						new SysUiVar(vkey, vname, vkind, vbody, Boolean
								.valueOf(vrequire), vdefault));
			}
		} else {
			panel = new SysUiLayout(id, name, attribute, xtype, uicode, css,
					thumb, help, kind, _for, path, thumbPath);
			panel.setXmlPath(xmlPath);
		}
		SysUiPluginUtil.getLayouts().put(id, panel);
		return null;
	}

}
