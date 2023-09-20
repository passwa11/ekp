package com.landray.kmss.sys.ui.xml.parser;

import com.landray.kmss.sys.config.xml.ElementParser;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiFormat;
import org.w3c.dom.Element;

import java.io.Serializable;

public class FormatElementParser implements ElementParser {

    @Override
    public Serializable parse(Element item) {
        String xmlPath = item.getAttribute(XmlReaderContext.XML_SOURCE_PATH);
        String code = item.getAttribute("id");
        String name = item.getAttribute("name");
        String description = item.getAttribute("description");
        String render = item.getAttribute("defaultRender");
        String example = item.getAttribute("example");
        String help = item.getAttribute("help");
        SysUiFormat sysUiFormat = new SysUiFormat(code, name, description, render, example, help);
        sysUiFormat.setXmlPath(xmlPath);
        SysUiPluginUtil.getFormats().put(code, sysUiFormat);
        return null;
    }

}
