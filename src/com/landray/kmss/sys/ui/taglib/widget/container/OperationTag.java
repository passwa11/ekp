package com.landray.kmss.sys.ui.taglib.widget.container;

import javax.servlet.jsp.JspException;

import com.landray.kmss.sys.ui.taglib.interfaces.IOperations;
import com.landray.kmss.sys.ui.taglib.widget.BaseTag;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class OperationTag extends BaseTag {
	private String name;
	private String href;
	private String icon;
	private String target;
	private String type;
	private String align;
	// 垂直方向
	private String vertical;
	
	// 点击事件
	private String onclick;
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getVertical() {
		if (this.vertical == null) {
            return "bottom";
        }
		return vertical;
	}

	public void setVertical(String vertical) {
		this.vertical = vertical;
	}

	public String getAlign() {
		if (this.align == null) {
            return "right";
        }
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	private IOperations getOperationsTag() {
		return ((IOperations) findAncestorWithClass(this, IOperations.class));
	}

	@Override
	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		IOperations portlet = getOperationsTag();
		if (portlet != null) {
			JSONObject operation = new JSONObject();
			if(StringUtil.isNotNull(this.getOnclick())){
				operation.put("onclick", this.getOnclick());
			}
			if (StringUtil.isNotNull(this.getTarget())) {
				operation.put("target", this.getTarget());
			}
			if (StringUtil.isNotNull(this.getHref())) {
				String href = this.getHref();
				operation.put("href", href);
			}
			if (StringUtil.isNotNull(this.getIcon())) {
				operation.put("icon", this.getIcon());
			}
			if (StringUtil.isNotNull(this.getName())) {
				operation.put("name", ResourceUtil.getMessage(this.getName()));
			}
			if (StringUtil.isNotNull(this.getType())) {
				operation.put("type", this.getType());
			}
			if (StringUtil.isNotNull(this.getAlign())) {
				operation.put("align", this.getAlign());
			}
			if (StringUtil.isNotNull(this.getVertical())) {
				operation.put("vertical", this.getVertical());
			}
			
			portlet.getOperations().add(operation);
		}
		release();
		return EVAL_PAGE;
	}

	@Override
	public void release() {
		this.name = null;
		this.icon = null;
		this.href = null;
		this.target = null;
		this.align = null;
		this.vertical = null;
		super.release();
	}
}
