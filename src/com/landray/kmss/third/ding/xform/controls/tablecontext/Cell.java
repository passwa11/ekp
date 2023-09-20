package com.landray.kmss.third.ding.xform.controls.tablecontext;

import com.landray.kmss.util.StringUtil;

public class Cell {
	// 原始的HTML
	private String html;

	// 更换所有控件索引之后的模板单元格HTML
	private String templateHtml;

	// 更换所有控件索引之后的内容单元格HTML
	private String contentHtml;

	public String getTemplateHtml() {
		return templateHtml;
	}

	public void setTemplateHtml(String templateHtml) {
		this.templateHtml = templateHtml;
	}

	public String getContentHtml() {
		return contentHtml;
	}

	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public boolean isEmpty() {
		return StringUtil.isNull(html);
	}
}
