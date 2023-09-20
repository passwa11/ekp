/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.Tag;

import com.landray.kmss.sys.ui.taglib.widget.ref.AttributeUtil;
import com.landray.kmss.sys.ui.taglib.widget.ref.RefBaseTag;
import com.landray.sso.client.util.StringUtil;

/**
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class RefCriterionTag extends RefBaseTag implements DynamicAttributes {

	public void setKey(String key) {
		this.attributes.put("key", key);
	}

	public void setTitle(String title) {
		this.attributes.put("title", title);
	}

	public void setStyle(String style) {
		this.attributes.put("style", style);
	}

	public void setExpand(Boolean expand) {
		this.attributes.put("expand", expand);
	}

	public void setMulti(Boolean multi) {
		this.attributes.put("canMulti", multi);
	}

	protected Map<String, Object> attributes = new HashMap<String, Object>();

	@Override
	public void release() {
		super.release();
		attributes = new HashMap<String, Object>();
	}

	@Override
	public void setDynamicAttribute(String uri, String localName, Object value)
			throws JspException {
		if (value != null) {
			if (localName.startsWith("cfg-")) {
				value = value.toString().trim();
			}
			attributes.put(localName, value.toString());
		}
	}

	@Override
	public int doStartTag() throws JspException {
		Tag tag = this.getParent();
		if (tag instanceof CriteriaTag) {
			CriteriaTag criTag = (CriteriaTag) tag;
			String channel = criTag.getChannel();
			if(StringUtil.isNotNull(channel)) {
                this.attributes.put("channel", channel);
            }
		}
		return super.doStartTag();
	}

	@Override
	protected void beforeEndTag() {
		super.beforeEndTag();
		AttributeUtil.set(pageContext, "criterionAttrs", attributes);
	}

	@Override
	protected void afterEndTag() {
		super.afterEndTag();
		AttributeUtil.release(pageContext, "criterionAttrs");
	}

}
