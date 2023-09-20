package com.landray.kmss.sys.portal.xml.parser;

import com.landray.kmss.sys.config.xml.ElementParser;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.portal.util.PortalUtil;
import com.landray.kmss.sys.portal.xml.model.SysPortalFooter;
import com.landray.kmss.sys.ui.util.ParserUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiVar;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.List;

public class FooterElementParser implements ElementParser {

	@Override
    public Serializable parse(Element paramElement) {
		Element item = paramElement;
		String xmlPath = item.getAttribute(XmlReaderContext.XML_SOURCE_PATH);
		String dirName = item.getAttribute(XmlReaderContext.COMPONENTDIRNAME);
		String id = item.getAttribute("id");
		String name = item.getAttribute("name");
		String file = item.getAttribute("file");
		String thumb = item.getAttribute("thumb");
		String help = item.getAttribute("help");
		String _for = item.getAttribute("for");
		String path = item.getAttribute("path"); // 区分是否自定义
		String thumbPath = item.getAttribute("thumb-path"); // 自定义页脚的缩略图路径

		SysPortalFooter footer = new SysPortalFooter(id, name, file, thumb,
				help, _for, path, thumbPath);
		footer.setXmlPath(xmlPath);
		Element varsTag = DomUtils.getChildElementByTagName(item, "vars");
		if (varsTag != null) {
			List<Element> vars = DomUtils.getChildElementsByTagName(varsTag,
					"var");
			for (int k = 0; k < vars.size(); k++) {
				Element var = vars.get(k);
				String vkey = var.getAttribute("key");
				String vname = var.getAttribute("name");
				String vkind = var.getAttribute("kind");
				String vrequire = var.getAttribute("require");
				String vdefault = var.getAttribute("default");
				String vbody = DomUtils.getTextValue(var);
				footer.getFdVars().add(
						new SysUiVar(vkey, vname, vkind, vbody, Boolean
								.valueOf(vrequire), vdefault));
			}
		}
		PortalUtil.getPortalFooters().put(id, footer);
		return null;
	}
}