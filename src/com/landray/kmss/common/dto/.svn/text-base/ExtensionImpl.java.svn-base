package com.landray.kmss.common.dto;

import java.lang.annotation.ElementType;

import net.sf.json.JSONObject;

public class ExtensionImpl implements Extension {
	/** 模块 如：sys-portal */
	private String module;

	/** 配置信息 扩展点注解中的字段信息 */
	private JSONObject config;

	/** 扩展作用的类名 */
	private String refName;

	/** FIELD/METHOD的名称 */
	private String elementName;

	/** 扩展作用的类型 */
	private ElementType elementType;

	private String label;

	private String messageKey;

	private Integer order;

	@Override
    public String getId() {
		if (config != null) {
			return config.getString("id");
		}
		return null;
	}

	@Override
    public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public JSONObject getConfig() {
		return config;
	}

	public void setConfig(JSONObject config) {
		this.config = config;
	}

	@Override
    public String getRefName() {
		return refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

	@Override
    public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	@Override
    public ElementType getElementType() {
		return elementType;
	}

	public void setElementType(ElementType elementType) {
		this.elementType = elementType;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public String getMessageKey() {
		return this.messageKey;
	}

	@Override
	public Integer getOrder() {
		return this.order;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
}
