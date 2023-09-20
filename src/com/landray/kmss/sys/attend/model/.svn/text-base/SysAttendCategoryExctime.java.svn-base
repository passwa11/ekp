package com.landray.kmss.sys.attend.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryExctimeForm;



/**
 * 签到例外日期
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public class SysAttendCategoryExctime  extends BaseModel {

	/**
	 * 日期
	 */
	private Date fdExcTime;
	
	/**
	 * @return 日期
	 */
	public Date getFdExcTime() {
		return this.fdExcTime;
	}
	
	/**
	 * @param fdExcTime 日期
	 */
	public void setFdExcTime(Date fdExcTime) {
		this.fdExcTime = fdExcTime;
	}
	
	/**
	 * 签到事项
	 */
	private SysAttendCategory fdCategory;

	/**
	 * @return 签到事项
	 */
	public SysAttendCategory getFdCategory() {
		return this.fdCategory;
	}

	/**
	 * @param fdCategory
	 *            签到事项
	 */
	public void setFdCategory(SysAttendCategory fdCategory) {
		this.fdCategory = fdCategory;
	}

	//机制开始
	//机制结束

	@Override
    public Class<SysAttendCategoryExctimeForm> getFormClass() {
		return SysAttendCategoryExctimeForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdCategory.fdId", "fdCategoryId");
			toFormPropertyMap.put("fdCategory.fdId", "fdCategoryName");
		}
		return toFormPropertyMap;
	}
}
