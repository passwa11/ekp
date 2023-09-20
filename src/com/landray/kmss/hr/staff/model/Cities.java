package com.landray.kmss.hr.staff.model;

import com.landray.kmss.common.model.BaseModel;

public class Cities extends BaseModel {

	private static final long serialVersionUID = 1L;

	protected Integer id;
	protected String cityId;
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	protected String city;
	protected String provinceId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public Class getFormClass() {
		// TODO Auto-generated method stub
		return null;
	}


}
