/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.widget;

import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import java.util.HashMap;
import java.util.Map;

/**
 * LUI组件基础标签
 * 
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public abstract class WidgetTag extends BaseTag implements DynamicAttributes {

	protected Map<String, String> vars;
	protected String type;
	protected String attr;
	protected String ref;
	protected String style;
	protected String channel;
	protected String parentId;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Map<String, String> getVars() {
		if (this.vars == null) {
			this.vars = new HashMap<String, String>();
		}
		return vars;
	}

	public void setVars(Map<String, String> vars) {
		this.vars = vars;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	@Override
	public void release() {
		this.type = null;
		this.vars = null;
		this.ref = null;
		this.attr = null;
		super.release();
	}

	protected void putConfigValue(JSONObject cfg, String key, Object value) {
		if (value != null) {
			if (value.toString().trim().length() > 0) {
				cfg.put(key, value);
			}
		}

	}

	@Override
	protected void postBuildConfigJson(JSONObject cfg) {
		Map<String, String> vars = getVars();
		if (vars != null && !vars.isEmpty()) {
			JSONObject extend = new JSONObject();
			extend.putAll(vars);
			cfg.put("vars", extend);
		}
		if (StringUtil.isNotNull(this.getStyle())) {
			cfg.put("style", this.getStyle());
		}
		if (StringUtil.isNotNull(this.getChannel())) {
			cfg.put("channel", this.getChannel());
		}
	}

	protected String buildCodeScriptHtml(String code) {
		return BuildUtils.buildCodeHtml(null, code);
	}

	protected String buildCodeUrlHtml(String url) {
		return BuildUtils.buildCodeHtml(url, null);
	}

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			String body = this.bodyContent == null ? "" : this.bodyContent
					.getString();
			pageContext.getOut().append(acquireString(body));
		} catch (Exception e) {
			logger.error("自定义标签渲染错误.",e);
			if(e instanceof NullPointerException){
				throw new JspTagException("自定义标签数据异常",e);
			}else{
				throw new JspTagException("自定义标签渲染错误",e);
			}
		}
		registerToParent();
		release();
		return EVAL_PAGE;
	}

	protected String acquireString(String body) throws Exception {
		return BuildUtils.buildLUIHtml(this.getId(), this.getType(),
				this.getAttr(), buildConfigJson(), body, getParentId());
	}

	@Override
    @SuppressWarnings("unchecked")
	public void setDynamicAttribute(String uri, String key, Object value)
			throws JspException {
		if ("vars".equals(key) && value instanceof Map) {
			this.getVars().putAll(
					(Map<? extends String, ? extends String>) value);
			return;
		}
		if (key.startsWith("var-")) {
			this.getVars().put(key.substring(4), value.toString());

		} else if (key.startsWith(CONFIG_PREFIX)
				|| key.startsWith(CONFIG_SHORT_PREFIX)) {
			addConfig(key, value.toString());
		}
	}
}
