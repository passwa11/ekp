package com.landray.kmss.sys.ui.xml.parser;

import com.landray.kmss.sys.config.xml.ElementParser;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiVarKind;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.io.Serializable;

public class VarKindElementParser implements ElementParser {

    @Override
    public Serializable parse(Element item) {
        String xmlPath = item.getAttribute(XmlReaderContext.XML_SOURCE_PATH);
        String id = item.getAttribute("id");
        String name = item.getAttribute("name");
        String thumb = item.getAttribute("thumb");
        String file = item.getAttribute("file");
        String help = item.getAttribute("help");
        String config = DomUtils.getTextValue(item);
        SysUiVarKind sysUiVarKind = new SysUiVarKind(id, name, thumb, help, file, config);
        sysUiVarKind.setXmlPath(xmlPath);
        SysUiPluginUtil.getVarKinds().put(id, sysUiVarKind);
        return null;
    }

}
