package com.landray.kmss.sys.time.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.time.forms.SysTimeHolidayPachForm;

/**
 * 节假日补班
 * 
 * @author chenl
 * @version 1.0 2017-11-15
 */
public class SysTimeHolidayPach extends BaseModel {

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
	 * 补班日期
	 */
	private Date fdPachTime;

	/**
	 * @return 补班日期
	 */
	public Date getFdPachTime() {
		return this.fdPachTime;
	}

	/**
	 * @param fdPachTime
	 *            补班日期
	 */
	public void setFdPachTime(Date fdPachTime) {
		this.fdPachTime = fdPachTime;
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

	/**
	 * 所属节假日明细
	 */
	private SysTimeHolidayDetail fdDetail;

	/**
	 * @return 所属节假日明细
	 */
	public SysTimeHolidayDetail getFdDetail() {
		return this.fdDetail;
	}

	/**
	 * @param fdDetail
	 *            所属节假日明细
	 */
	public void setFdDetail(SysTimeHolidayDetail fdDetail) {
		this.fdDetail = fdDetail;
	}

	// 机制开始
	// 机制结束

	@Override
	public Class<SysTimeHolidayPachForm> getFormClass() {
		return SysTimeHolidayPachForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdHoliday.fdId", "fdHolidayId");
			toFormPropertyMap.put("fdHoliday.fdName", "fdHolidayName");
			toFormPropertyMap.put("fdDetail.fdId", "fdDetailId");
			toFormPropertyMap.put("fdDetail.fdName", "fdDetailName");
		}
		return toFormPropertyMap;
	}

	private List<SysTimePatchworkTime> workTimes = null;
	private List<SysTimeWorkDetail> workDetailList = null;

	public List<SysTimePatchworkTime> getWorkTimes() {
		if (workTimes == null) {
			workTimes = new ArrayList<SysTimePatchworkTime>();
		}
		return workTimes;
	}

	public void setWorkTimes(List<SysTimePatchworkTime> workTimes) {
		this.workTimes = workTimes;
	}

	public List<SysTimeWorkDetail> getWorkDetailList() {
		if (workDetailList == null) {
			workDetailList = new ArrayList<SysTimeWorkDetail>();
		}
		return workDetailList;
	}

	public void setWorkDetailList(List<SysTimeWorkDetail> workDetailList) {
		this.workDetailList = workDetailList;
	}

}
