package com.landray.kmss.sys.ui.taglib.list;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.BaseTag;

@SuppressWarnings("serial")
public class ColumnDataTag extends BaseTag {

	protected String property;
	protected String style;
	protected String styleClass;
	protected String title;
	protected String headerStyle;
	protected String headerClass;

	public String getHeaderClass() {
		return headerClass;
	}

	public void setHeaderClass(String headerClass) {
		this.headerClass = headerClass;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getHeaderStyle() {
		return headerStyle;
	}

	public void setHeaderStyle(String headerStyle) {
		this.headerStyle = headerStyle;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	protected String col;

	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	protected String escape;

	public String getEscape() {
		if (this.escape == null) {
			return "true";
		}
		return escape;
	}

	public void setEscape(String escape) {
		this.escape = escape;
	}

	@Override
	public void release() {
		col = null;
		title = null;
		property = null;
		style = null;
		escape = null;
		headerStyle = null;
		headerClass = null;
		styleClass = null;

		super.release();
	}

	public JSONObject buildDataConfig(String value) {
		JSONObject config = new JSONObject();
		if (property != null) {
			config.element("property", property);
		} else {
			config.element("value", value);
		}
		if (col != null) {
			config.element("col", col);
		}
		config.element("style", style);
		config.element("styleClass", styleClass);
		config.element("escape", this.getEscape());
		return config;
	}

	public JSONObject buildColumnConfig() {
		JSONObject config = new JSONObject();
		config.element("title", title);
		if (property != null) {
			config.element("property", property);
		}
		if (col != null) {
			config.element("property", col);
		}
		config.element("headerStyle", headerStyle);
		config.element("headerClass", headerClass);
		return config;
	}

	@Override
    public int doEndTag() throws JspException {
		try {
			String body = this.bodyContent == null ? "" : this.bodyContent
					.getString().trim();
			Tag parent = findAncestorWithClass(this, ColumnDatasTag.class);
			if (parent instanceof ColumnDatasTag) {
				((ColumnDatasTag) parent).addCDatas(buildDataConfig(body));
				((ColumnDatasTag) parent).addCColumns(buildColumnConfig());
			}

		} catch (Exception e) {
			logger.error(e.toString());
			throw new JspTagException(e);
		}
		registerToParent();
		release();
		return EVAL_PAGE;
	}
}
