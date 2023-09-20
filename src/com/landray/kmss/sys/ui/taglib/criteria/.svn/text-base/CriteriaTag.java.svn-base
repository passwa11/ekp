/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class CriteriaTag extends CriteriaBaseTag implements CriterionRegister {

	private List<String> criterionKeys = new ArrayList<String>();

	@Override
	public String getType() {
		if (this.type == null) {
			return "lui/criteria!Criteria";
		}
		return this.type;
	}

	@Override
    public void regsiterKey(String key) {
		if (key == null) {
			return;
		}
		if (isRegsiterKey(key)) {
			// throw new RuntimeException("is regsitered key = " + key);
		}
		criterionKeys.add(key);
	}

	@Override
    public boolean isRegsiterKey(String key) {
		return criterionKeys.contains(key);
	}

	@Override
	public void release() {
		super.release();
		criterionKeys = new ArrayList<String>();
	}

	@Override
	public int doStartTag() throws JspException {
		Map<String, Object> criteriaAttrs = new HashMap<String, Object>();
		criteriaAttrs.put("channel", getChannel());
		criteriaAttrs.put("id", getId());
		pageContext.setAttribute("criteriaAttrs", criteriaAttrs,
				PageContext.REQUEST_SCOPE);
		return super.doStartTag();
	}
	

	@Override
	protected String acquireString(String body) throws Exception {
		return "<script>Com_IncludeFile(\"calendar.js\");</script>\r\n"
				+ super.acquireString(body);
	}

	@Override
	public int doEndTag() throws JspException {
		pageContext.removeAttribute("criteriaAttrs", PageContext.REQUEST_SCOPE);
		return super.doEndTag();
	}

}
