package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryDeduct;
import com.landray.kmss.web.action.ActionMapping;

public class SysAttendCategoryDeductForm extends ExtendForm {

	/**
	 * 休息开始时间
	 */
	private String fdStartTime;

	/**
	 * 休息结束时间
	 */
	private String fdEndTime;

	/**
	 * 扣除阈值 例如： 每满threshold(小时)扣除deductHours(小时)
	 */
	private String fdThreshold;

	/**
	 * 扣除小时数
	 */
	private String fdDeductHours;

	public String getFdStartTime() {
		return fdStartTime;
	}

	public void setFdStartTime(String fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	public String getFdEndTime() {
		return fdEndTime;
	}

	public void setFdEndTime(String fdEndTime) {
		this.fdEndTime = fdEndTime;
	}

	public String getFdThreshold() {
		return fdThreshold;
	}

	public void setFdThreshold(String fdThreshold) {
		this.fdThreshold = fdThreshold;
	}

	public String getFdDeductHours() {
		return fdDeductHours;
	}

	public void setFdDeductHours(String fdDeductHours) {
		this.fdDeductHours = fdDeductHours;
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

	@Override
	public Class getModelClass() {
		return SysAttendCategoryDeduct.class;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdStartTime = null;
		fdEndTime = null;
		fdCategoryId = null;
		fdThreshold = null;
		fdDeductHours = null;

		super.reset(mapping, request);
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
