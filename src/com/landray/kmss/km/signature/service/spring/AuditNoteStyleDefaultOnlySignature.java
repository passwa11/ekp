package com.landray.kmss.km.signature.service.spring;

public class AuditNoteStyleDefaultOnlySignature implements
		com.landray.kmss.sys.workflow.tablib.auditnote.AuditNoteStyle {
	@Override
    public String getStyleHTML() {
		StringBuffer buff = new StringBuffer();
		buff.append("<div style='width:${width}; height:auto;'>\r\n");
		buff.append("        <p align='right'>${picPath}</p>\r\n");
		buff.append("        <p align='right'>${attachment}</p>\r\n");
		buff.append("</div>");
		return buff.toString();
	}

	@Override
	public String getDingStyleHTML() {
		StringBuffer buff = new StringBuffer();
		buff.append(
				"<div style='width:${width}; height:auto;' class='${wrapClass}'>\r\n");
		buff.append("        <p align='right'>${picPath}</p>\r\n");
		buff.append("        <p align='right'>${attachment}</p>\r\n");
		buff.append("</div>");
		return buff.toString();
	}
}