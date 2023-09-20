package com.landray.kmss.sys.time.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.sys.time.model.SysTimeHolidayDetail;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 节假日明细设置 Form
 * 
 * @author
 * @version 1.0 2017-09-26
 */
public class SysTimeHolidayDetailForm extends ExtendForm {

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
	private String fdStartDay;

	/**
	 * @return 休假开始时间
	 */
	public String getFdStartDay() {
		return this.fdStartDay;
	}

	/**
	 * @param fdStartDay
	 *            休假开始时间
	 */
	public void setFdStartDay(String fdStartDay) {
		this.fdStartDay = fdStartDay;
	}

	/**
	 * 休假结束时间
	 */
	private String fdEndDay;

	/**
	 * @return 休假结束时间
	 */
	public String getFdEndDay() {
		return this.fdEndDay;
	}

	/**
	 * @param fdEndDay
	 *            休假结束时间
	 */
	public void setFdEndDay(String fdEndDay) {
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
	 * 所属节假日的ID
	 */
	private String fdHolidayId;

	/**
	 * @return 所属节假日的ID
	 */
	public String getFdHolidayId() {
		return this.fdHolidayId;
	}

	/**
	 * @param fdHolidayId
	 *            所属节假日的ID
	 */
	public void setFdHolidayId(String fdHolidayId) {
		this.fdHolidayId = fdHolidayId;
	}

	/**
	 * 所属节假日的名称
	 */
	private String fdHolidayName;

	/**
	 * @return 所属节假日的名称
	 */
	public String getFdHolidayName() {
		return this.fdHolidayName;
	}

	/**
	 * @param fdHolidayName
	 *            所属节假日的名称
	 */
	public void setFdHolidayName(String fdHolidayName) {
		this.fdHolidayName = fdHolidayName;
	}

	// 机制开始
	// 机制结束

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdStartDay = null;
		fdEndDay = null;
		fdPatchDay = null;
		fdPatchHolidayDay=null;
		fdHolidayId = null;
		fdHolidayName = null;
		fdYear = null;

		super.reset(mapping, request);
	}

	@Override
	public Class<SysTimeHolidayDetail> getModelClass() {
		return SysTimeHolidayDetail.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdHolidayId", new FormConvertor_IDToModel(
					"fdHoliday", SysTimeHoliday.class));
		}
		return toModelPropertyMap;
	}
	
	private String fdYear = null;

	public String getFdYear() {
		fdYear = getFdStartDay();
		if(StringUtil.isNotNull(fdYear)&&fdYear.length()>4){
			String year = fdYear.split(" ")[0];
			if (year.indexOf("-") > -1) {
                fdYear = year.substring(0, 4);
            } else {
                fdYear = year.substring(6, year.length());
            }
		}
		return fdYear;
	}

	public void setFdYear(String fdYear) {
		this.fdYear = fdYear;
	}
	
}
