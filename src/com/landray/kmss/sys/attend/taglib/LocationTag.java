package com.landray.kmss.sys.attend.taglib;

import com.landray.kmss.sys.attend.taglib.mobile.LocationMobileRender;
import com.landray.kmss.sys.attend.taglib.pc.LocationPcRender;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.taglib.xform.AbstractFieldElementTag;
import com.landray.kmss.web.taglib.xform.IFieldElementRender;

/**
 * 地图选择标签
 */
@SuppressWarnings("serial")
public class LocationTag extends AbstractFieldElementTag {

	// 名称属性
	protected String propertyName;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	// 坐标属性
	protected String propertyCoordinate;

	public String getPropertyCoordinate() {
		if (StringUtil.isNull(propertyCoordinate)) {
			this.propertyCoordinate = getPropertyName() + "Coordinate";
		}
		return propertyCoordinate;
	}

	public void setPropertyCoordinate(String propertyCoordinate) {
		this.propertyCoordinate = propertyCoordinate;
	}

	protected String nameValue;

	public String getNameValue() {
		return nameValue;
	}

	public void setNameValue(String nameValue) {
		this.nameValue = nameValue;
	}

	protected String coordinateValue;

	public String getCoordinateValue() {
		return coordinateValue;
	}

	public void setCoordinateValue(String coordinateValue) {
		this.coordinateValue = coordinateValue;
	}

	protected String propertyDetail;

	public String getPropertyDetail() {
		return propertyDetail;
	}

	public void setPropertyDetail(String propertyDetail) {
		this.propertyDetail = propertyDetail;
	}

	protected String detailValue;
	// 范围
	protected String radius;
	// 是否允许修改显示值
	protected String isModify;
	// 默认值为用户当前位置(PERSON_POSITION)
	protected String defaultValue;
	// 省份
	protected String propertyProvince;
	protected String provinceValue;
	// 城市
	protected String propertyCity;
	protected String cityValue;
	// 区
	protected String propertyDistrict;
	protected String districtValue;

	public String getDetailValue() {
		return detailValue;
	}

	public void setDetailValue(String detailValue) {
		this.detailValue = detailValue;
	}

	public String getRadius() {
		return radius;
	}

	public void setRadius(String radius) {
		this.radius = radius;
	}

	public String getPropertyProvince() {
		return propertyProvince;
	}

	public void setPropertyProvince(String propertyProvince) {
		this.propertyProvince = propertyProvince;
	}

	public String getProvinceValue() {
		return provinceValue;
	}

	public void setProvinceValue(String provinceValue) {
		this.provinceValue = provinceValue;
	}

	public String getPropertyCity() {
		return propertyCity;
	}

	public void setPropertyCity(String propertyCity) {
		this.propertyCity = propertyCity;
	}

	public String getCityValue() {
		return cityValue;
	}

	public void setCityValue(String cityValue) {
		this.cityValue = cityValue;
	}

	public String getPropertyDistrict() {
		return propertyDistrict;
	}

	public void setPropertyDistrict(String propertyDistrict) {
		this.propertyDistrict = propertyDistrict;
	}

	public String getDistrictValue() {
		return districtValue;
	}

	public void setDistrictValue(String districtValue) {
		this.districtValue = districtValue;
	}

	public String getIsModify() {
		return isModify;
	}

	public void setIsModify(String isModify) {
		this.isModify = isModify;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	protected IFieldElementRender getFieldElementRender() {
		if (mobile) {
            return new LocationMobileRender(pageContext, this);
        }
		return new LocationPcRender(pageContext, this);
	}

	@Override
	public void release() {
		super.release();
		propertyCoordinate = null;
	}

}
