package com.landray.kmss.sys.time.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.sys.time.model.SysTimeHolidayDetail;
import com.landray.kmss.sys.time.model.SysTimeHolidayPach;

/**
 * 节假日补班 Form
 * 
 * @author chenl
 * @version 1.0 2017-11-15
 */
public class SysTimeHolidayPachForm extends ExtendForm {

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
	private String fdPachTime;

	/**
	 * @return 补班日期
	 */
	public String getFdPachTime() {
		return this.fdPachTime;
	}

	/**
	 * @param fdPachTime
	 *            补班日期
	 */
	public void setFdPachTime(String fdPachTime) {
		this.fdPachTime = fdPachTime;
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

	/**
	 * 所属节假日明细的ID
	 */
	private String fdDetailId;

	/**
	 * @return 所属节假日明细的ID
	 */
	public String getFdDetailId() {
		return this.fdDetailId;
	}

	/**
	 * @param fdDetailId
	 *            所属节假日明细的ID
	 */
	public void setFdDetailId(String fdDetailId) {
		this.fdDetailId = fdDetailId;
	}

	/**
	 * 所属节假日明细的名称
	 */
	private String fdDetailName;

	/**
	 * @return 所属节假日明细的名称
	 */
	public String getFdDetailName() {
		return this.fdDetailName;
	}

	/**
	 * @param fdDetailName
	 *            所属节假日明细的名称
	 */
	public void setFdDetailName(String fdDetailName) {
		this.fdDetailName = fdDetailName;
	}

	// 机制开始
	// 机制结束

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdPachTime = null;
		fdHolidayId = null;
		fdHolidayName = null;
		fdDetailId = null;
		fdDetailName = null;

		super.reset(mapping, request);
	}

	@Override
    public Class<SysTimeHolidayPach> getModelClass() {
		return SysTimeHolidayPach.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdHolidayId", new FormConvertor_IDToModel(
					"fdHoliday", SysTimeHoliday.class));
			toModelPropertyMap.put("fdDetailId", new FormConvertor_IDToModel(
					"fdDetail", SysTimeHolidayDetail.class));
		}
		return toModelPropertyMap;
	}
}
