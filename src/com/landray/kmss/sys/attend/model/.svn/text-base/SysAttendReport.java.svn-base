package com.landray.kmss.sys.attend.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.attend.forms.SysAttendReportForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.BaseAuthModel;



/**
 * 月统计报表
 * 
 * @author 
 * @version 1.0 2017-07-27
 */
public class SysAttendReport extends BaseAuthModel {

	/**
	 * 月份
	 */
	private Date fdMonth;
	
	/**
	 * @return 月份
	 */
	public Date getFdMonth() {
		return this.fdMonth;
	}
	
	/**
	 * @param fdMonth 月份
	 */
	public void setFdMonth(Date fdMonth) {
		this.fdMonth = fdMonth;
	}
	
	/**
	 * 创建时间
	 */
	private Date docCreateTime;
	
	/**
	 * @return 创建时间
	 */
	@Override
    public Date getDocCreateTime() {
		return this.docCreateTime;
	}
	
	/**
	 * @param docCreateTime 创建时间
	 */
	@Override
    public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	
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
	 * @param fdName 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	
	/**
	 * 创建者
	 */
	private SysOrgPerson docCreator;
	
	/**
	 * @return 创建者
	 */
	@Override
    public SysOrgPerson getDocCreator() {
		return this.docCreator;
	}
	
	/**
	 * @param docCreator 创建者
	 */
	@Override
    public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}
	
	
	/**
	 * 部门
	 */
	private List<SysOrgElement> fdDepts;
	
	/**
	 * @return 部门
	 */
	public List<SysOrgElement> getFdDepts() {
		return this.fdDepts;
	}
	
	/**
	 * @param fdDepts 部门
	 */
	public void setFdDepts(List<SysOrgElement> fdDepts) {
		this.fdDepts = fdDepts;
	}
	
	private Boolean fdIsQuit;

	public Boolean getFdIsQuit() {
		return fdIsQuit;
	}

	public void setFdIsQuit(Boolean fdIsQuit) {
		this.fdIsQuit = fdIsQuit;
	}

	private String fdCategoryIds;

	public String getFdCategoryIds() {
		return fdCategoryIds;
	}

	public void setFdCategoryIds(String fdCategoryIds) {
		this.fdCategoryIds = fdCategoryIds;
	}

	private String fdCategoryNames;

	public String getFdCategoryNames() {
		return fdCategoryNames;
	}

	public void setFdCategoryNames(String fdCategoryNames) {
		this.fdCategoryNames = fdCategoryNames;
	}

	private Integer fdTargetType;

	public Integer getFdTargetType() {
		return fdTargetType;
	}

	public void setFdTargetType(Integer fdTargetType) {
		this.fdTargetType = fdTargetType;
	}

	@Override
    public Class<SysAttendReportForm> getFormClass() {
		return SysAttendReportForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("fdDepts",
					new ModelConvertor_ModelListToString(
							"fdDeptIds:fdDeptNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	@Override
	public String getDocSubject() {
		return this.fdName;
	}

}
