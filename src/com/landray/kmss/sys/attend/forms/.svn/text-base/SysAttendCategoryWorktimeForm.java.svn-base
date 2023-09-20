package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;



/**
 * 考勤班次 Form
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public class SysAttendCategoryWorktimeForm  extends ExtendForm  {

	/**
	 * 上班时间
	 */
	private String fdStartTime;
	
	/**
	 * @return 上班时间
	 */
	public String getFdStartTime() {
		return this.fdStartTime;
	}
	
	/**
	 * @param fdStartTime 上班时间
	 */
	public void setFdStartTime(String fdStartTime) {
		this.fdStartTime = fdStartTime;
	}
	
	/**
	 * 下班时间
	 */
	private String fdEndTime;
	
	/**
	 * @return 下班时间
	 */
	public String getFdEndTime() {
		return this.fdEndTime;
	}
	
	/**
	 * @param fdEndTime 下班时间
	 */
	public void setFdEndTime(String fdEndTime) {
		this.fdEndTime = fdEndTime;
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

	/**
	 * 是否有效
	 */
	private String fdIsAvailable;

	public String getFdIsAvailable() {
		return fdIsAvailable;
	}

	public void setFdIsAvailable(String fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}
	
	/**
	 * 跨天标识 ，1：当天，2：次日
	 */
	private String fdOverTimeType;
	

	public String getFdOverTimeType() {
		return fdOverTimeType;
	}

	public void setFdOverTimeType(String fdOverTimeType) {
		this.fdOverTimeType = fdOverTimeType;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdStartTime = null;
		fdEndTime = null;
		fdCategoryId = null;
		fdCategoryName = null;
		fdIsAvailable = null;
		fdOverTimeType = null;
		super.reset(mapping, request);
	}

	@Override
    public Class<SysAttendCategoryWorktime> getModelClass() {
		return SysAttendCategoryWorktime.class;
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
