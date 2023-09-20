package com.landray.kmss.sys.mportal.xml;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.landray.kmss.sys.config.xml.ElementParser;
import com.landray.kmss.sys.mportal.plugin.MportalMportletUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiVar;

public class MportletElementParser implements ElementParser {

	@Override
	public Serializable parse(Element item) {
		String id = item.getAttribute("id");
		String name = item.getAttribute("name");
		String module = item.getAttribute("module");
		String jspUrl = item.getAttribute("jspUrl");
		String jsUrl = item.getAttribute("jsUrl");
		String cssUrl = item.getAttribute("cssUrl");
		SysMportalMportlet mportlet = new SysMportalMportlet(id, name, module, jspUrl, jsUrl,
				cssUrl);
		String description = item.getAttribute("description");
		String moduleUrl = item.getAttribute("moduleUrl");
		mportlet.setDescription(description);
		mportlet.setFdModuleUrl(moduleUrl);
		Element varsTag = DomUtils.getChildElementByTagName(item, "vars");
		if (varsTag != null) {
			List<Element> vars = DomUtils.getChildElementsByTagName(varsTag, "var");
			for (int k = 0; k < vars.size(); k++) {
				Element var = vars.get(k);
				String vkey = var.getAttribute("key");
				String vname = var.getAttribute("name");
				String vkind = var.getAttribute("kind");
				String vrequire = var.getAttribute("required");
				String vdefault = var.getAttribute("default");
				String vbody = DomUtils.getTextValue(var);
				mportlet.getFdVars().add(new SysUiVar(vkey, vname, vkind, vbody, Boolean.valueOf(vrequire), vdefault));
			}
		}
		Element operaionsTag = DomUtils.getChildElementByTagName(item, "operations");
		if (operaionsTag != null) {
			List<Element> operations = DomUtils.getChildElementsByTagName(operaionsTag, "operation");
			for (int k = 0; k < operations.size(); k++) {
				Element operation = operations.get(k);
				String oname = operation.getAttribute("name");
				String otype = operation.getAttribute("type");
				String ohref = operation.getAttribute("href");
				String obody = DomUtils.getTextValue(operation);
				if (obody != null) {
					obody = obody.trim();
				}
				mportlet.getFdOperations().add(new MportletOperation(oname, otype, ohref, obody));
			}
		}

		MportalMportletUtil.registerMportlet(mportlet);
		return null;
	}
}
