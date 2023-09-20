package com.landray.kmss.sys.ui.taglib.widget.component;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.taglib.xform.TagUtils;

import net.sf.json.JSONObject;

/**
 * 重复信息组件标签
 */
public class RecurrenceTag extends WidgetTag {

	private String property;

	private String value;

	private String buildInType;

	private String typeContainer;

	private String customContainer;

	private String isOn;

	private static final long serialVersionUID = 1L;

	@Override
    public String getType() {
		if (StringUtil.isNull(this.type)) {
			this.type = "lui/recurrence/recurrence!Recurrence";
		}
		return this.type;
	}

	@Override
    protected void postBuildConfigJson(JSONObject cfg) {
		putConfigValue(cfg, "property", this.getProperty());
		putConfigValue(cfg, "value", this.getValue());
		putConfigValue(cfg, "buildInType", this.getBuildInType());
		putConfigValue(cfg, "typeContainer", this.getTypeContainer());
		putConfigValue(cfg, "customContainer", this.getCustomContainer());
		putConfigValue(cfg, "isOn", this.getIsOn());
		super.postBuildConfigJson(cfg);
	}

	@Override
    public void release() {
		this.property = null;
		this.value = null;
		this.buildInType = null;
		this.typeContainer = null;
		this.customContainer = null;
		this.isOn = null;
		super.release();
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getValue() {
		if (StringUtil.isNull(value)) {
			Object fValue = TagUtils.getFieldValue(
					(HttpServletRequest) pageContext.getRequest(),
					getProperty());
			if (fValue != null) {
				value = fValue.toString();
			}
		}
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getBuildInType() {
		return buildInType;
	}

	public void setBuildInType(String buildInType) {
		this.buildInType = buildInType;
	}

	public String getTypeContainer() {
		return typeContainer;
	}

	public void setTypeContainer(String typeContainer) {
		this.typeContainer = typeContainer;
	}

	public String getCustomContainer() {
		return customContainer;
	}

	public void setCustomContainer(String customContainer) {
		this.customContainer = customContainer;
	}

	public String getIsOn() {
		return isOn;
	}

	public void setIsOn(String isOn) {
		this.isOn = isOn;
	}

}
