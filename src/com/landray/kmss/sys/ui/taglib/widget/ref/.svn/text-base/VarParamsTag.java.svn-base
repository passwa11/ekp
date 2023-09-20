/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.widget.ref;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;

import com.landray.kmss.sys.ui.taglib.widget.BaseTag;

/**
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class VarParamsTag extends BaseTag implements DynamicAttributes {

	private Map<String, Object> params = new HashMap<String, Object>();

	@Override
    public void setDynamicAttribute(String uri, String localName, Object value)
			throws JspException {
		params.put(localName, value);
	}

	@Override
	public int doEndTag() throws JspException {
		RefBaseTag base = (RefBaseTag) findAncestorWithClass(this,
				RefBaseTag.class);
		base.addAllParams(params);
		return super.doEndTag();
	}

}
