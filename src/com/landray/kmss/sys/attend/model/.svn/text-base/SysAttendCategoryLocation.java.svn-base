package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryLocationForm;
import com.landray.kmss.util.StringUtil;



/**
 * 签到点
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public class SysAttendCategoryLocation  extends BaseModel {
	/**
	 * 新旧数据类型
	 */
	private String fdDataType;

	/**
	 * @return 新旧数据类型
	 */
	public String getFdDataType() {
		return this.fdDataType;
	}

	/**
	 * @param fdLimit
	 *            新旧数据类型
	 */
	public void setFdDataType(String fdDataType) {
		this.fdDataType = fdDataType;
	}

	/**
	 * 签到范围排序
	 */
	private Integer fdRow;

	/**
	 * @return 签到范围排序
	 */
	public Integer getFdRow() {
		return this.fdRow;
	}

	/**
	 * @param fdLimit
	 *            签到范围排序
	 */
	public void setFdRow(Integer fdRow) {
		this.fdRow = fdRow;
	}

	/**
	 * 签到范围标识
	 */
	private Integer fdLimitIndex;

	/**
	 * @return 签到范围标识
	 */
	public Integer getFdLimitIndex() {
		return this.fdLimitIndex;
	}

	/**
	 * @param fdLimit
	 *            签到范围标识
	 */
	public void setFdLimitIndex(Integer fdLimitIndex) {
		this.fdLimitIndex = fdLimitIndex;
	}

	/**
	 * 签到范围
	 */
	private Integer fdLimit;

	/**
	 * @return 签到范围
	 */
	public Integer getFdLimit() {
		return this.fdLimit;
	}

	/**
	 * @param fdLimit
	 *            签到范围
	 */
	public void setFdLimit(Integer fdLimit) {
		this.fdLimit = fdLimit;
	}
	/**
	 * 签到地点名称
	 */
	private String fdLocation;
	
	/**
	 * @return 签到地点名称
	 */
	public String getFdLocation() {
		return this.fdLocation;
	}
	
	/**
	 * @param fdLocation 签到地点名称
	 */
	public void setFdLocation(String fdLocation) {
		this.fdLocation = fdLocation;
	}
	
	/**
	 * 签到地点经度
	 */
	private String fdLng;
	
	/**
	 * @return 签到地点经度
	 */
	public String getFdLng() {
		return this.fdLng;
	}
	
	/**
	 * @param fdLng
	 *            签到地点经度
	 */
	public void setFdLng(String fdLng) {
		this.fdLng = fdLng;
	}
	
	/**
	 * 签到地点纬度
	 */
	private String fdLat;
	
	/**
	 * @return 签到地点纬度
	 */
	public String getFdLat() {
		return this.fdLat;
	}
	
	/**
	 * @param fdLat
	 *            签到地点纬度
	 */
	public void setFdLat(String fdLat) {
		this.fdLat = fdLat;
	}
	
	private String fdLatLng;

	public String getFdLatLng() {
		if (StringUtil.isNull(fdLatLng)) {
			if (StringUtil.isNotNull(getFdLng())
					&& StringUtil.isNotNull(getFdLat())) {
				return getFdLat() + "," + getFdLng();
			}
		}
		return fdLatLng;
	}

	public void setFdLatLng(String fdLatLng) {
		this.fdLatLng = fdLatLng;
	}

	@Override
    public Class<SysAttendCategoryLocationForm> getFormClass() {
		return SysAttendCategoryLocationForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}
}
