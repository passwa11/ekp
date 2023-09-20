package com.landray.kmss.sys.ui.xml.parser;

import com.landray.kmss.sys.config.xml.ElementParser;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.portal.cloud.CloudPortalCache;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.util.ForSystemType;
import com.landray.kmss.sys.ui.xml.model.SysUiCode;
import com.landray.kmss.sys.ui.xml.model.SysUiSource;
import com.landray.kmss.sys.ui.xml.model.SysUiVar;
import com.landray.kmss.util.StringUtil;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.List;

public class SourceElementParser implements ElementParser {

	@Override
    public Serializable parse(Element item) {
		String xmlPath = item.getAttribute(XmlReaderContext.XML_SOURCE_PATH);
		String id = item.getAttribute("id");
		String name = item.getAttribute("name");
		String format = item.getAttribute("format");
		String thumb = item.getAttribute("thumb");
		String help = item.getAttribute("help");
		String _for = item.getAttribute("for");
		String forSystem = item.getAttribute("forSystem");
		String varExtend = item.getAttribute("varExtend");
		String portletId = null;
		String anonymous = null;
		boolean isPortlet = "portlet"
				.equals(item.getParentNode().getNodeName());
		if (isPortlet) {
			String portletForSystem = ((Element) item.getParentNode())
					.getAttribute("forSystem");
			anonymous = ((Element) item.getParentNode())
					.getAttribute("anonymous");
			// 使用系统优先取portlet的
			if (StringUtil.isNotNull(portletForSystem)) {
				forSystem = portletForSystem;
			}
			portletId = ((Element) item.getParentNode()).getAttribute("id");
			// 没有缩略图则取portlet的缩略图
			if (StringUtil.isNull(thumb)) {
				thumb = ((Element) item.getParentNode())
						.getAttribute("thumb");
			}
		}
		if (StringUtil.isNull(name) && isPortlet) {
			name = ((Element) item.getParentNode()).getAttribute("name");
		}
		if (StringUtil.isNull(id) && isPortlet) {
			id = ((Element) item.getParentNode()).getAttribute("id")
					+ ".source";
			name = ((Element) item.getParentNode()).getAttribute("name");
		}

		if (StringUtil.isNull(id)) {
			throw new RuntimeException("");
		}
		String xtype = "lui/data/source!";
		String type = item.getAttribute("type");
		if (type.indexOf("!") > 0) {
			xtype = type;
		} else {
			xtype = xtype + type;
		}
		String exampleData = null;
		Element example = DomUtils.getChildElementByTagName(item, "example");
		if (example != null) {
			Element exampleCode = DomUtils.getChildElementByTagName(example,
					"code");
			if (exampleCode != null) {
				exampleData = DomUtils.getTextValue(exampleCode);
			}
		}
		String dataFieldData = null;
		Element dataFields = DomUtils.getChildElementByTagName(item, "dataFields");
		if (dataFields != null) {
			Element dataFieldCode = DomUtils.getChildElementByTagName(dataFields,
					"code");
			if (dataFieldCode != null) {
				dataFieldData = DomUtils.getTextValue(dataFieldCode);
			}
		}
		String attribute = "";
		Element code = DomUtils.getChildElementByTagName(item, "code");
		String codeSrc = "", codeBody = "";
		if (code != null) {
			codeSrc = code.getAttribute("src");
			codeBody = DomUtils.getTextValue(code);
		}
		Element varsTag = DomUtils.getChildElementByTagName(item, "vars");
		if (varsTag == null) {
			varsTag = DomUtils.getChildElementByTagName(
					(Element) item.getParentNode(), "vars");
		}
		SysUiSource source = null;
		if (varsTag != null) {
			attribute = varsTag.getAttribute("config");
			List<Element> vars = DomUtils.getChildElementsByTagName(varsTag,
					"var");
			if (StringUtil.isNull(attribute) && (vars.size() > 0)) {
				attribute = "/sys/ui/extend/source/config.jsp";
			}
			String[] formats = format.split(";");
			source = new SysUiSource(id, name, formats[0], new SysUiCode(
					codeSrc, codeBody), xtype, attribute, thumb, help, formats,
					_for, forSystem, varExtend, portletId, exampleData,
					anonymous, dataFieldData);
			for (int k = 0; k < vars.size(); k++) {
				Element var = vars.get(k);
				String vkey = var.getAttribute("key");
				String vname = var.getAttribute("name");
				String vkind = var.getAttribute("kind");
				String vrequire = var.getAttribute("required");
				String vdefault = var.getAttribute("default");
				String vbody = DomUtils.getTextValue(var);
				source.addVar(
						new SysUiVar(vkey, vname, vkind, vbody, Boolean
								.valueOf(vrequire), vdefault));
			}
		} else {
			String[] formats = format.split(";");
			source = new SysUiSource(id, name, formats[0], new SysUiCode(
					codeSrc, codeBody), xtype, attribute, thumb, help, formats,
					_for, forSystem, varExtend, portletId, exampleData,
					anonymous, dataFieldData);
		}
		source.setXmlPath(xmlPath);
		// cloud
		if (ForSystemType.cloud.equals(source.getFdForSystem())) {
			CloudPortalCache.getSources().put(id, source);
		} else if (ForSystemType.ekp.equals(source.getFdForSystem())) { // ekp
			SysUiPluginUtil.getSources().put(id, source);
		} else { // all
			CloudPortalCache.getSources().put(id, source);
			SysUiPluginUtil.getSources().put(id, source);
		}
		return null;
	}

}
