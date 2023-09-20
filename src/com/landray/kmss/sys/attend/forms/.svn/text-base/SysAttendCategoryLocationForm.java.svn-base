package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryLocation;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;



/**
 * 签到点 Form
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public class SysAttendCategoryLocationForm  extends ExtendForm  {
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
	 * 签到地点纬度
	 */
	private String fdLng;
	
	/**
	 * @return 签到地点纬度
	 */
	public String getFdLng() {
		return this.fdLng;
	}
	
	/**
	 * @param fdLng 签到地点纬度
	 */
	public void setFdLng(String fdLng) {
		this.fdLng = fdLng;
	}
	
	/**
	 * 签到地点经度
	 */
	private String fdLat;
	
	/**
	 * @return 签到地点经度
	 */
	public String getFdLat() {
		return this.fdLat;
	}
	
	/**
	 * @param fdLat 签到地点经度
	 */
	public void setFdLat(String fdLat) {
		this.fdLat = fdLat;
	}
	
	/**
	 * 经纬度坐标
	 */
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

	public String getFdLocationCoordinate() {
		if (StringUtil.isNotNull(fdLatLng)) {
			return fdLatLng;
		}
		if (StringUtil.isNotNull(getFdLng())
				&& StringUtil.isNotNull(getFdLat())) {
			return getFdLat() + "," + getFdLng();
		}
		return null;
	}

	public void setFdLocationCoordinate(String fdLocationCoordinate) {
		if (StringUtil.isNotNull(fdLocationCoordinate)) {
			setFdLatLng(fdLocationCoordinate);

			fdLocationCoordinate = fdLocationCoordinate.replace("gcj02:", "")
					.replace("bd09:", "");
			String fdLat = fdLocationCoordinate.split("[,;]")[0];
			String fdLng = fdLocationCoordinate.split("[,;]")[1];
			if (StringUtil.isNotNull(fdLat)) {
				setFdLat(fdLat);
			}
			if (StringUtil.isNotNull(fdLng)) {
				setFdLng(fdLng);
			}
		}
	}

	/**
	 * 签到事项的ID
	 */
	private String fdCategoryId;

	/**
	 * @return 签到事项的ID
	 */
	public String getFdCategoryId() {
		return this.fdCategoryId;
	}

	/**
	 * @param fdCategoryId
	 *            签到事项的ID
	 */
	public void setFdCategoryId(String fdCategoryId) {
		this.fdCategoryId = fdCategoryId;
	}

	/**
	 * 签到事项的名称
	 */
	private String fdCategoryName;

	/**
	 * @return 签到事项的名称
	 */
	public String getFdCategoryName() {
		return this.fdCategoryName;
	}

	/**
	 * @param fdCategoryName
	 *            签到事项的名称
	 */
	public void setFdCategoryName(String fdCategoryName) {
		this.fdCategoryName = fdCategoryName;
	}

	//机制开始 
	//机制结束

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdLimit = null;
		fdRow = null;
		fdDataType = null;
		fdLimitIndex = null;
		fdLocation = null;
		fdLng = null;
		fdLat = null;
		fdCategoryId = null;
		fdCategoryName = null;
		fdLatLng = null;
 
		super.reset(mapping, request);
	}

	@Override
    public Class<SysAttendCategoryLocation> getModelClass() {
		return SysAttendCategoryLocation.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdCategoryId",
					new FormConvertor_IDToModel("fdCategory",
							SysAttendCategory.class));
		}
		return toModelPropertyMap;
	}
}
