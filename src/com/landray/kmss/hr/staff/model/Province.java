package com.landray.kmss.hr.staff.model;

import com.landray.kmss.common.model.BaseModel;

public class Province extends BaseModel {

	private static final long serialVersionUID = 1L;
	protected Integer id;

	protected String provinceId;
	protected String province;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Override
	public Class getFormClass() {
		// TODO Auto-generated method stub
		return null;
	}
}
