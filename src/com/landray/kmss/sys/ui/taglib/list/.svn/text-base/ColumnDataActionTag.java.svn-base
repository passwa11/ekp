package com.landray.kmss.sys.ui.taglib.list;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import com.landray.kmss.sys.ui.taglib.widget.BaseTag;

import net.sf.json.JSONObject;

/**
 * 
 * 输出每行数据对应的一个可执行操作，每行数据包含多个Action
 *
 */
@SuppressWarnings("serial")
public class ColumnDataActionTag extends BaseTag {

	private String name;
	private String type;
	private String uri;

	@Override
	public void release() {
		name = null;
		type = null;
		uri = null;
		super.release();
	}

	public JSONObject buildActionData() {
		JSONObject action = new JSONObject();
		action.element("name", name);
		if (type != null) {
			action.element("type", type);
		}
		if (uri != null) {
			action.element("uri", uri);
		}
		return action;
	}

	@Override
    public int doEndTag() throws JspException {
		try {
			Tag parent = findAncestorWithClass(this, ColumnDatasTag.class);
			if (parent instanceof ColumnDatasTag) {
				if (name != null) {
					((ColumnDatasTag) parent)
							.addCurrentRowAction(buildActionData());
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new JspTagException(e);
		}
		registerToParent();
		release();
		return EVAL_PAGE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}
