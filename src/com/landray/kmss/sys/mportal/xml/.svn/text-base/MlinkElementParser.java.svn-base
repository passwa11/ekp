package com.landray.kmss.sys.mportal.xml;

import java.io.Serializable;

import org.w3c.dom.Element;

import com.landray.kmss.sys.config.xml.ElementParser;
import com.landray.kmss.sys.mportal.plugin.MportalLinkUtil;
import com.landray.kmss.util.StringUtil;

public class MlinkElementParser implements ElementParser  {
	
	@Override
	public Serializable parse(Element element) {
		String id = element.getAttribute("id");
		String msgKey = element.getAttribute("msgKey");
		String url = element.getAttribute("url");
		String type = element.getAttribute("type");
		
		SysMportalMlink link = new SysMportalMlink(id, msgKey, url);
		if(StringUtil.isNotNull(type)) {
			link.setType(type);
		}
		MportalLinkUtil.registerMLink(link);
		return null;
	}
	
}
