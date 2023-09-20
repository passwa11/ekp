package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendSignStat;
import com.landray.kmss.sys.organization.model.SysOrgElement;



/**
 * 签到统计
 * 
 * @author
 * @version 1.0 2017-07-27
 */
public class SysAttendSignStatForm  extends ExtendForm  {

	/**
	 * 日期
	 */
	private String fdDate;
	
	/**
	 * @return 日期
	 */
	public String getFdDate() {
		return this.fdDate;
	}
	
	/**
	 * @param fdDate 日期
	 */
	public void setFdDate(String fdDate) {
		this.fdDate = fdDate;
	}
	
	/**
	 * 考勤组ID
	 */
	private String fdCategoryId;
	
	/**
	 * @return 考勤组ID
	 */
	public String getFdCategoryId() {
		return this.fdCategoryId;
	}
	
	/**
	 * @param fdCategoryId 考勤组ID
	 */
	public void setFdCategoryId(String fdCategoryId) {
		this.fdCategoryId = fdCategoryId;
	}
	
	

	/**
	 * 创建时间
	 */
	private String docCreateTime;
	
	/**
	 * @return 创建时间
	 */
	public String getDocCreateTime() {
		return this.docCreateTime;
	}
	
	/**
	 * @param docCreateTime 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	
	
	// 签到次数
	private Integer fdSignCount;

	
	/**
	 * 考勤组名称
	 */
	private String fdCategoryName;
	
	/**
	 * @return 考勤组名称
	 */
	public String getFdCategoryName() {
		return this.fdCategoryName;
	}
	
	/**
	 * @param fdCategoryName 考勤组名称
	 */
	public void setFdCategoryName(String fdCategoryName) {
		this.fdCategoryName = fdCategoryName;
	}
	
	/**
	 * 创建者的ID
	 */
	private String docCreatorId;
	
	/**
	 * @return 创建者的ID
	 */
	public String getDocCreatorId() {
		return this.docCreatorId;
	}
	
	/**
	 * @param docCreatorId 创建者的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}
	
	/**
	 * 创建者的名称
	 */
	private String docCreatorName;
	
	/**
	 * @return 创建者的名称
	 */
	public String getDocCreatorName() {
		return this.docCreatorName;
	}
	
	/**
	 * @param docCreatorName 创建者的名称
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdDate = null;
		fdCategoryId = null;
		docCreateTime = null;
		fdCategoryName = null;
		docCreatorId = null;
		docCreatorName = null;

		super.reset(mapping, request);
	}

	@Override
    public Class<SysAttendSignStat> getModelClass() {
		return SysAttendSignStat.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("docCreator",
						SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
}
