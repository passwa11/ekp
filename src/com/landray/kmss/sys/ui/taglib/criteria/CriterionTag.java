/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria;

import javax.servlet.jsp.JspException;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.taglib.widget.ref.AttributeUtil;

/**
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class CriterionTag extends CriteriaBaseTag {

	protected String title;

	protected String key;

	@Override
	public void release() {
		super.release();
		title = null;
		key = null;
	}

	@Override
	public String getType() {
		if (this.type == null) {
			return "lui/criteria!Criterion";
		}
		return this.type;
	}

	@Override
	protected void postBuildConfigJson(JSONObject cfg) {
		cfg.put("title", title);
		cfg.put("key", key);
		super.postBuildConfigJson(cfg);
		AttributeUtil.putAttrsToConfig(pageContext, "criterionAttrs", cfg);
	}

	@Override
	public int doStartTag() throws JspException {
		CriterionRegister register = (CriterionRegister) findAncestorWithClass(
				this, CriterionRegister.class);
		if (register != null) {
            register.regsiterKey(key);
        }
		return super.doStartTag();
	}

	@Override
	protected String acquireString(String body) throws Exception {
		String newBody = super.acquireString(body);
		return newBody;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
