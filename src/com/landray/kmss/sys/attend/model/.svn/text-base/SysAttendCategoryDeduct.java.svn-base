package com.landray.kmss.sys.attend.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryDeductForm;

/**
 * @version:
 * @Description: 加班扣除
 * @author: zby
 * @date: 2019年12月5日 上午11:35:04
 */
public class SysAttendCategoryDeduct extends BaseModel {

	/**
	 * 休息开始时间
	 */
	private Date fdStartTime;

	/**
	 * 休息结束时间
	 */
	private Date fdEndTime;

	/**
	 * 扣除阈值 例如： 每满fdThreshold(小时)扣除fdDeductHours(小时)
	 */
	private Integer fdThreshold;

	/**
	 * 扣除小时数
	 */
	private Integer fdDeductHours;

	public Date getFdStartTime() {
		return fdStartTime;
	}

	public void setFdStartTime(Date fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	public Date getFdEndTime() {
		return fdEndTime;
	}

	public void setFdEndTime(Date fdEndTime) {
		this.fdEndTime = fdEndTime;
	}

	public Integer getFdThreshold() {
		return fdThreshold;
	}

	public void setFdThreshold(Integer fdThreshold) {
		this.fdThreshold = fdThreshold;
	}

	public Integer getFdDeductHours() {
		return fdDeductHours;
	}

	public void setFdDeductHours(Integer fdDeductHours) {
		this.fdDeductHours = fdDeductHours;
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

	@Override
	public Class getFormClass() {
		return SysAttendCategoryDeductForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdCategory.fdId", "fdCategoryId");
		}
		return toFormPropertyMap;
	}

}
