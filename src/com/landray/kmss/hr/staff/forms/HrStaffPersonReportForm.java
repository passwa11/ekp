package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.staff.model.HrStaffPersonReport;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.BaseAuthForm;

/**
 * 统计报表
 * 
 * @author 潘永辉 2017-1-17
 * 
 */
public class HrStaffPersonReportForm extends BaseAuthForm {
	private static final long serialVersionUID = 1L;
	// 统计类型：人数统计，年龄结构，司龄分布，学历分布，职务等级，婚姻状况
	private String fdReportType;
	// 统计名称
	private String fdName;
	// 查询条件IDs
	private String fdQueryIds;
	// 查询条件Names
	private String fdQueryNames;
	// 入职期间
	private String fdPeriod; // 期间类型
	private String fdBeginPeriod; // 开始
	private String fdEndPeriod; // 结束

	// 年龄区间（年龄结构）
	// 司龄区间（司龄分布）
	private String fdAgeRange;

	private Integer fdMonth;
	private String fdStaffType;
	public Integer getFdMonth() {
		return fdMonth;
	}

	public void setFdMonth(Integer fdMonth) {
		this.fdMonth = fdMonth;
	}

	public String getFdStaffType() {
		return fdStaffType;
	}

	public void setFdStaffType(String fdStaffType) {
		this.fdStaffType = fdStaffType;
	}

	// 员工状态（异动情况）
	private String fdStatus;

	// 创建者
	private String docCreatorId;
	private String docCreatorName;
	// 创建时间
	private String docCreateTime;

	@Override
	public Class getModelClass() {
		return HrStaffPersonReport.class;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.fdReportType = null;
		this.fdName = null;
		this.fdQueryIds = null;
		this.fdQueryNames = null;
		this.fdPeriod = null;
		this.fdBeginPeriod = null;
		this.fdEndPeriod = null;
		this.fdAgeRange = null;
		this.fdStatus = null;
		this.docCreatorId = null;
		this.docCreatorName = null;
		this.docCreateTime = null;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

	public String getFdReportType() {
		return fdReportType;
	}

	public void setFdReportType(String fdReportType) {
		this.fdReportType = fdReportType;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdQueryIds() {
		return fdQueryIds;
	}

	public void setFdQueryIds(String fdQueryIds) {
		this.fdQueryIds = fdQueryIds;
	}

	public String getFdQueryNames() {
		return fdQueryNames;
	}

	public void setFdQueryNames(String fdQueryNames) {
		this.fdQueryNames = fdQueryNames;
	}

	public String getFdPeriod() {
		return fdPeriod;
	}

	public void setFdPeriod(String fdPeriod) {
		this.fdPeriod = fdPeriod;
	}

	public String getFdBeginPeriod() {
		return fdBeginPeriod;
	}

	public void setFdBeginPeriod(String fdBeginPeriod) {
		this.fdBeginPeriod = fdBeginPeriod;
	}

	public String getFdEndPeriod() {
		return fdEndPeriod;
	}

	public void setFdEndPeriod(String fdEndPeriod) {
		this.fdEndPeriod = fdEndPeriod;
	}

	public String getFdAgeRange() {
		return fdAgeRange;
	}

	public void setFdAgeRange(String fdAgeRange) {
		this.fdAgeRange = fdAgeRange;
	}

	public String getDocCreatorId() {
		return docCreatorId;
	}

	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

}
