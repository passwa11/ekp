package com.landray.kmss.km.review.restservice.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KmReviewInstanceRestModelDTO extends KmReviewInstanceRestBaseDTO {

	/*
	 * 模板
	 */
	protected IdNameProperty fdTemplate;

	/*
	 * 创建人部门
	 */
	private IdNameProperty fdDepartment;

	/*
	 * 可阅读者
	 */
	protected List<IdNameProperty> authReaders = new ArrayList<>();

	/*
	 * 表单数据
	 */
	private Map<String, Object> modelData  = new HashMap<>();

	public List<IdNameProperty> getAuthReaders() {
		return authReaders;
	}

	public void setAuthReaders(List<IdNameProperty> authReaders) {
		this.authReaders = authReaders;
	}

	public IdNameProperty getFdTemplate() {
		return fdTemplate;
	}

	public void setFdTemplate(IdNameProperty fdTemplate) {
		this.fdTemplate = fdTemplate;
	}

	public IdNameProperty getFdDepartment() {
		return fdDepartment;
	}

	public void setFdDepartment(IdNameProperty fdDepartment) {
		this.fdDepartment = fdDepartment;
	}

	public Map<String, Object> getModelData() {
		return modelData;
	}

	public void setModelData(Map<String, Object> modelData) {
		this.modelData = modelData;
	}
}
