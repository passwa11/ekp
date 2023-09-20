package com.landray.kmss.common.module.core.register.loader.model;

import com.landray.kmss.util.ResourceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 严明镜
 * @version 1.0 2021年02月23日
 */
public class Declare {

	private String id;

	private String messageKey;

	private Map<String, MechanismModelConfig> mechanismModels = new HashMap<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public Map<String, MechanismModelConfig> getMechanismModels() {
		return mechanismModels;
	}

	public void setMechanismModels(Map<String, MechanismModelConfig> mechanismModels) {
		this.mechanismModels = mechanismModels;
	}

	@Override
	public String toString() {
		return ResourceUtil.getString(getMessageKey()) + "(" + getId() + ")";
	}
}
