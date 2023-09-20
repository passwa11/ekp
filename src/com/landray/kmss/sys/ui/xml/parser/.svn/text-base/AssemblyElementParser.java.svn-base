package com.landray.kmss.sys.ui.xml.parser;

import com.landray.kmss.sys.config.xml.ElementParser;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiAssembly;
import org.w3c.dom.Element;

import java.io.Serializable;

public class AssemblyElementParser implements ElementParser {

    @Override
    public Serializable parse(Element item) {
        String xmlPath = item.getAttribute(XmlReaderContext.XML_SOURCE_PATH);
        String code = item.getAttribute("id");
        String name = item.getAttribute("name");
        String thumb = item.getAttribute("thumb");
        String help = item.getAttribute("help");
        String category = item.getAttribute("category");
        SysUiAssembly sysUiAssembly = new SysUiAssembly(code, name, help, thumb, category);
        sysUiAssembly.setXmlPath(xmlPath);
        SysUiPluginUtil.getAssemblies().put(code, sysUiAssembly);
        return null;
    }

}
