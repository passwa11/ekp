package com.landray.kmss.km.review.restservice.dto;

public class IdNameProperty extends IdProperty {
	public IdNameProperty() {
	}

	public IdNameProperty(String fdId, String fdName) {
		super(fdId);
		this.fdName = fdName;
	}

	public static IdNameProperty of(String fdId, String fdName) {
		return new IdNameProperty(fdId, fdName);
	}


	private String fdName;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
}
