package com.landray.kmss.sys.ui.xml.model;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SysUiVar {
	public SysUiVar(){
		
	}
	public SysUiVar(String key, String name, String kind, String body,
			Boolean require, String _default) {
		this.key = key;
		this.name = name;
		this.kind = kind;
		this.body = body;
		this.require = require;
		this.Default = _default;
	}

	private String key;
	private String kind;
	private String name;
	private String body;
	private Boolean require;
	private String Default;

	public Boolean getRequire() {
		return require;
	}

	public void setRequire(Boolean require) {
		this.require = require;
	}

	public String getDefault() {
		return Default;
	}

	public void setDefault(String _default) {
		this.Default = _default;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getClass().getName())
				.append(getKey()).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (!(other instanceof SysUiVar)) {
			return false;
		}
		SysUiVar otherO = (SysUiVar) other;
		return getKey().equals(otherO.getKey());
	}
}
