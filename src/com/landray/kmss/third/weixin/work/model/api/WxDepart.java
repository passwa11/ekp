package com.landray.kmss.third.weixin.work.model.api;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class WxDepart implements Serializable {

	private static final long serialVersionUID = 6278555762069217156L;
	private Long id;
	private String name;
	private Long parentid;
	private Long order;
	@JSONField(name = "name_en")
	private String nameEn;

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public String[] getDeptLeaders() {
		return deptLeaders;
	}

	public void setDeptLeaders(String[] deptLeaders) {
		this.deptLeaders = deptLeaders;
	}

	@JSONField(name = "department_leader")
	private String[] deptLeaders;

}
