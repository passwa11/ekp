package com.landray.kmss.sys.ui.xml.parser;

import com.landray.kmss.sys.config.xml.ElementParser;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.util.ParserUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiCode;
import com.landray.kmss.sys.ui.xml.model.SysUiRender;
import com.landray.kmss.sys.ui.xml.model.SysUiVar;
import com.landray.kmss.util.StringUtil;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.io.Serializable;
import java.util.List;

public class RenderElementParser implements ElementParser {
 
	@Override
    public Serializable parse(Element item) {
		String dirName = item.getAttribute(XmlReaderContext.COMPONENTDIRNAME);
		String xmlPath = item.getAttribute(XmlReaderContext.XML_SOURCE_PATH);
		String id = item.getAttribute("id");
		String name = item.getAttribute("name");
		String thumb = item.getAttribute("thumb");
		String format = item.getAttribute("format");
		String type = item.getAttribute("type");
		String css = item.getAttribute("css");
		String help = item.getAttribute("help");
		String _for = item.getAttribute("for");
		String path = item.getAttribute("path"); // 区分是否是自定义
		String thumbPath = item.getAttribute("thumb-path"); // 自定义呈现的缩略图路径

		Element code = DomUtils.getChildElementByTagName(item, "code");
		String codeBody = "", codeSrc = "";
		String xtype = "";
		String attribute = "";

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
		if (type.indexOf("!") > 0) {
			xtype = type;
		} else {
			xtype = "lui/view/render!" + type;
		}
		Element varsTag = DomUtils.getChildElementByTagName(item, "vars");
		SysUiRender render = null;
		if (varsTag != null) {
			attribute = varsTag.getAttribute("config");
			List<Element> vars = DomUtils.getChildElementsByTagName(varsTag,
					"var");
			if (StringUtil.isNull(attribute) && (vars.size() > 0)) {
				attribute = "/sys/ui/extend/render/config.jsp";
			}
			render = new SysUiRender(id, name, format, xtype, attribute,
					uicode, thumb, css, help, _for, path, thumbPath);
			for (int k = 0; k < vars.size(); k++) {
				Element var = vars.get(k);
				String vkey = var.getAttribute("key");
				String vname = var.getAttribute("name");
				String vkind = var.getAttribute("kind");
				String vrequire = var.getAttribute("required");
				String vdefault = var.getAttribute("default");
				String vbody = DomUtils.getTextValue(var);
				render.getFdVars().add(
						new SysUiVar(vkey, vname, vkind, vbody, Boolean
								.valueOf(vrequire), vdefault));
			}
		} else {
			render = new SysUiRender(id, name, format, xtype, attribute,
					uicode, thumb, css, help, _for, path, thumbPath);
		}
		render.setXmlPath(xmlPath);
		SysUiPluginUtil.getRenders().put(id, render);
		return null;
	}
}
