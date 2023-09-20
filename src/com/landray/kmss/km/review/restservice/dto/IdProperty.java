package com.landray.kmss.km.review.restservice.dto;

public class IdProperty {
	public IdProperty() {
	}

	public IdProperty(String fdId) {
		this.fdId = fdId;
	}

	private String fdId;

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}
}
