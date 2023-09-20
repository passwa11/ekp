package com.landray.kmss.sys.ui.xml.parser;

import com.landray.kmss.sys.config.xml.ElementParser;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.portal.cloud.CloudPortalCache;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.util.ForSystemType;
import com.landray.kmss.sys.ui.xml.model.SysUiOperation;
import com.landray.kmss.sys.ui.xml.model.SysUiPortlet;
import com.landray.kmss.sys.ui.xml.model.SysUiVar;
import com.landray.kmss.util.StringUtil;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.List;

public class PortletElementParser implements ElementParser {

	@Override
    public Serializable parse(Element item) {
		String dirName = item.getAttribute(XmlReaderContext.COMPONENTDIRNAME);
		String xmlPath = item.getAttribute(XmlReaderContext.XML_SOURCE_PATH);
		String id = item.getAttribute("id");
		String name = item.getAttribute("name");
		String module = item.getAttribute("module");
		String description = item.getAttribute("description");
		String forSystem = item.getAttribute("forSystem");
		String varExtend = item.getAttribute("varExtend");
		String anonymous = item.getAttribute("anonymous");
		String path = item.getAttribute("path");// 区分是否是自定义

		SysUiPortlet portlet = new SysUiPortlet(id, name, module, varExtend,
				forSystem, anonymous);
		portlet.setXmlPath(xmlPath);
		portlet.setFdDescription(description);
		Element varsTag = DomUtils.getChildElementByTagName(item, "vars");

		String attribute = "/sys/ui/extend/var/config.jsp";
		if (varsTag != null) {
			List<Element> vars = DomUtils.getChildElementsByTagName(varsTag,
					"var");
			if (StringUtil.isNotNull(varsTag.getAttribute("config"))) {
				attribute = varsTag.getAttribute("config");
			}
			for (int k = 0; k < vars.size(); k++) {
				Element var = vars.get(k);
				String vkey = var.getAttribute("key");
				String vname = var.getAttribute("name");
				String vkind = var.getAttribute("kind");
				String vrequire = var.getAttribute("required");
				String vdefault = var.getAttribute("default");
				String vbody = DomUtils.getTextValue(var);
				portlet.addVar(
						new SysUiVar(vkey, vname, vkind, vbody, Boolean
								.valueOf(vrequire), vdefault));
			}
		}
		Element operationsTag = DomUtils.getChildElementByTagName(item,
				"operations");
		if (operationsTag != null) {
			List<Element> operations = DomUtils.getChildElementsByTagName(
					operationsTag, "operation");
			for (int k = 0; k < operations.size(); k++) {
				Element operation = operations.get(k);
				String vname = operation.getAttribute("name");
				String vhref = operation.getAttribute("href");
				String vtarget = operation.getAttribute("target");
				String vicon = operation.getAttribute("icon");
				String vtype = operation.getAttribute("type");
				String valign = operation.getAttribute("align");
				String vmobileHref = operation.getAttribute("mobileHref");
				String vwindowFeatures = operation.getAttribute("windowFeatures");
				if (StringUtil.isNull(valign)) {
					valign = "right";
				}
				portlet.getFdOperations().add(
						new SysUiOperation(vname, vhref, vtarget, vicon, vtype,
								valign, vmobileHref, vwindowFeatures));
			}
		}

		//
		List<Element> sources = DomUtils.getChildElementsByTagName(item,
				"source");
		if (sources != null) {
			SourceElementParser p = new SourceElementParser();
			for (int i = 0; i < sources.size(); i++) {
				p.parse(sources.get(i));
			}
		}
		//
		portlet.setFdAttribute(attribute);
		// cloud
		if (ForSystemType.cloud.equals(portlet.getFdForSystem())) {
			CloudPortalCache.getPortlets().put(id, portlet);
		} else if (ForSystemType.ekp.equals(portlet.getFdForSystem())) { // ekp
			SysUiPluginUtil.getPortlets().put(id, portlet);
		} else { // all
			CloudPortalCache.getPortlets().put(id, portlet);
			SysUiPluginUtil.getPortlets().put(id, portlet);
		}
		return null;
	}
}
