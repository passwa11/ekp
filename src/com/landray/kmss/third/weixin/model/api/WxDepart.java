package com.landray.kmss.third.weixin.model.api;

import com.alibaba.fastjson.annotation.JSONField;

public class WxDepart {
	private Long id;
	private String name;
	@JSONField(name = "parentid")
	private Long parentId;
	private Long order;

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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

}
