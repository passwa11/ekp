package com.landray.kmss.common.module.core.register.loader.model;

import com.landray.kmss.util.ResourceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 严明镜
 * @version 1.0 2021年03月19日
 */
public class Depend {

	private String id;

	private String MessageKey;

	/**
	 * 自身模块所依赖的所有模块
	 */
	private List<String> dependModules = new ArrayList<>();

	/**
	 * 自身模块Model所依赖的机制Model
	 * key=自身模块Model
	 * value=所有依赖的机制Model
	 */
	private Map<String, List<String>> dependModels = new HashMap<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessageKey() {
		return MessageKey;
	}

	public void setMessageKey(String messageKey) {
		MessageKey = messageKey;
	}

	public List<String> getDependModules() {
		return dependModules;
	}

	public void setDependModules(List<String> dependModules) {
		this.dependModules = dependModules;
	}

	public Map<String, List<String>> getDependModels() {
		return dependModels;
	}

	public void setDependModels(Map<String, List<String>> dependModels) {
		this.dependModels = dependModels;
	}

	@Override
	public String toString() {
		return ResourceUtil.getString(getMessageKey()) + "(" + getId() + ")";
	}
}
