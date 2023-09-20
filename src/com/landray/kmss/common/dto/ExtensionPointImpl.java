package com.landray.kmss.common.dto;

/**
 * 扩展点
 * 
 * @author chao
 *
 */
public class ExtensionPointImpl implements ExtensionPoint {
	/** ID，取注解类名 */
	private String id;

	/** 中文名 */
	private String label;

	/** 模块 */
	private String module;

	/** 是否全局扩展点 */
	private boolean global;

	/** 是否可配置 */
	private boolean configurable;

	/** 是否有序 */
	private boolean ordered;

	/** 是否单例 */
	private boolean singleton;

	/** 配置类名 */
	private String config;

	/** 注解所在类接口名 */
	private String baseOn;

	@Override
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
    public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
    public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	@Override
    public boolean isConfigurable() {
		return configurable;
	}

	public void setConfigurable(boolean configurable) {
		this.configurable = configurable;
	}

	@Override
    public boolean isOrdered() {
		return ordered;
	}

	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
	}

	@Override
    public boolean isSingleton() {
		return singleton;
	}

	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}

	@Override
    public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	@Override
    public String getBaseOn() {
		return baseOn;
	}

	public void setBaseOn(String baseOn) {
		this.baseOn = baseOn;
	}
}
