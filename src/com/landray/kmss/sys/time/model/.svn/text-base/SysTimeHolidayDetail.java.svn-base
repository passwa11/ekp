package com.landray.kmss.sys.time.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.time.forms.SysTimeHolidayDetailForm;

/**
 * 节假日明细设置
 * 
 * @author
 * @version 1.0 2017-09-26
 */
public class SysTimeHolidayDetail extends BaseModel {

	/**
	 * 名称
	 */
	private String fdName;

	/**
	 * @return 名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	/**
	 * @param fdName
	 *            名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 休假开始时间
	 */
	private Date fdStartDay;

	/**
	 * @return 休假开始时间
	 */
	public Date getFdStartDay() {
		return this.fdStartDay;
	}

	/**
	 * @param fdStartDay
	 *            休假开始时间
	 */
	public void setFdStartDay(Date fdStartDay) {
		this.fdStartDay = fdStartDay;
	}

	/**
	 * 休假结束时间
	 */
	private Date fdEndDay;

	/**
	 * @return 休假结束时间
	 */
	public Date getFdEndDay() {
		return this.fdEndDay;
	}

	/**
	 * @param fdEndDay
	 *            休假结束时间
	 */
	public void setFdEndDay(Date fdEndDay) {
		this.fdEndDay = fdEndDay;
	}
	
	/**
	 *  补假时间
	 */
	private String fdPatchHolidayDay;

	/**
	 * @return  补假时间
	 */
	public String getFdPatchHolidayDay() {
		return this.fdPatchHolidayDay;
	}

	/**
	 * @param fdPatchHolidayDay
	 *            补假时间
	 */
	public void setFdPatchHolidayDay(String fdPatchHolidayDay) {
		this.fdPatchHolidayDay = fdPatchHolidayDay;
	}

	/**
	 * 补班时间
	 */
	private String fdPatchDay;

	/**
	 * @return 补班时间
	 */
	public String getFdPatchDay() {
		return this.fdPatchDay;
	}

	/**
	 * @param fdPatchDay
	 *            补班时间
	 */
	public void setFdPatchDay(String fdPatchDay) {
		this.fdPatchDay = fdPatchDay;
	}

	/**
	 * 所属节假日
	 */
	private SysTimeHoliday fdHoliday;

	/**
	 * @return 所属节假日
	 */
	public SysTimeHoliday getFdHoliday() {
		return this.fdHoliday;
	}

	/**
	 * @param fdHoliday
	 *            所属节假日
	 */
	public void setFdHoliday(SysTimeHoliday fdHoliday) {
		this.fdHoliday = fdHoliday;
	}

	// 机制开始
	// 机制结束

	@Override
    public Class<SysTimeHolidayDetailForm> getFormClass() {
		return SysTimeHolidayDetailForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdHoliday.fdId", "fdHolidayId");
			toFormPropertyMap.put("fdHoliday.fdName", "fdHolidayName");
		}
		return toFormPropertyMap;
	}
	
	private String fdYear = null;

	public String getFdYear() {
		if(getFdStartDay()!=null){
			fdYear = (getFdStartDay().getYear()+2018)+"";
		}
		return fdYear;
	}

	public void setFdYear(String fdYear) {
		this.fdYear = fdYear;
	}
}
