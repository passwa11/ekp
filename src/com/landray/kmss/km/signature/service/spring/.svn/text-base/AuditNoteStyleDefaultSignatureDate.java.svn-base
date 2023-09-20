package com.landray.kmss.km.signature.service.spring;

import com.landray.kmss.sys.lbpmservice.taglib.auditnote.AuditNoteStyleUtil;

public class AuditNoteStyleDefaultSignatureDate implements
		com.landray.kmss.sys.workflow.tablib.auditnote.AuditNoteStyle {
	@Override
    public String getStyleHTML() {
		StringBuffer buff = new StringBuffer();
		buff.append("<div style='width:${width}; height:auto;'>\r\n");
		buff.append("        <p align='right'>${picPath}</p>\r\n");
		buff.append("        <p align='right'>${attachment}</p>\r\n");
		buff.append("        <p align='right'>${time}</p>\r\n");
		buff.append("</div>");
		return buff.toString();
	}

	@Override
	public String getDingStyleHTML() {
		StringBuffer buff = new StringBuffer();
		buff.append(
				"<div style='width:${width}; height:auto;' class='${wrapClass}'>\r\n");
		buff.append("		 <div class='lui-ding-auditnote-head'>\r\n");
		if (AuditNoteStyleUtil.enableDingHandlerIcon()) {
			buff.append("	 <p class='lui-ding-audit-handler-icon'>${handlerIcon}</p>\r\n");
		} else {
			buff.append("	 <p class='lui-ding-audit-handler-name' align='right'>${person}</p>\r\n");
		}
		buff.append("		 <p class='lui-ding-audit-time' align='right'>${time}</p>\r\n");
		buff.append("		 <p class='lui-ding-audit-folder' align='right'></p>\r\n");
		buff.append("		 <p class='lui-ding-audit-expand' align='right'></p>\r\n");
		buff.append("		 </div>\r\n");
		buff.append("        <p>${msg}</p>\r\n");
		buff.append("        <p align='right'>${picPath}</p>\r\n");
		buff.append("        <p align='right'>${attachment}</p>\r\n");
		buff.append("</div>");
		return buff.toString();
	}
}