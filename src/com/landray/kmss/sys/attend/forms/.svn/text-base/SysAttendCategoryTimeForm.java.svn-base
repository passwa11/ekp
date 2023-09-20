package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTime;



/**
 * 签到追加日期 Form
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public class SysAttendCategoryTimeForm  extends ExtendForm  {

	/**
	 * 日期
	 */
	private String fdTime;
	
	/**
	 * @return 日期
	 */
	public String getFdTime() {
		return this.fdTime;
	}
	
	/**
	 * @param fdTime 日期
	 */
	public void setFdTime(String fdTime) {
		this.fdTime = fdTime;
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
		fdTime = null;
		fdCategoryId = null;
		fdCategoryName = null;
 
		super.reset(mapping, request);
	}

	@Override
    public Class<SysAttendCategoryTime> getModelClass() {
		return SysAttendCategoryTime.class;
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
