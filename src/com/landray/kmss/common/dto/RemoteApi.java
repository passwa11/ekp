package com.landray.kmss.common.dto;

import java.util.Map;

/**
 * 远程API
 * 
 * @author 叶中奇
 */
public class RemoteApi {
	/** 所属模块 */
	private String module;

	/** controller的全路径（含节点名） */
	private String path;

	/** 类名 */
	private String className;

	/** 接口类名 -> 泛型参数名 -> 泛型实际类名 */
	private Map<String, Map<String, String>> interfaces;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Map<String, Map<String, String>> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(Map<String, Map<String, String>> interfaces) {
		this.interfaces = interfaces;
	}
}
