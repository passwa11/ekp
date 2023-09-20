package com.landray.kmss.hr.staff.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.staff.forms.HrStaffPersonReportForm;
import com.landray.kmss.sys.right.interfaces.BaseAuthModel;
import com.landray.kmss.util.DateUtil;

/**
 * 统计报表
 * 
 * @author 潘永辉 2017-1-17
 * 
 */
public class HrStaffPersonReport extends BaseAuthModel {
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
	private Date fdBeginPeriod; // 开始
	private Date fdEndPeriod; // 结束
	private String fdStaffType;
	public String getFdStaffType() {
		return fdStaffType;
	}

	public void setFdStaffType(String fdStaffType) {
		this.fdStaffType = fdStaffType;
	}

	// 年龄区间（年龄结构）
	// 司龄区间（司龄分布）
	private Integer fdAgeRange;
	private Integer fdMonth;
	public Integer getFdMonth() {
		return fdMonth;
	}

	public void setFdMonth(Integer fdMonth) {
		this.fdMonth = fdMonth;
	}

	// 员工状态（异动情况）
	private String fdStatus;

	@Override
	public Class getFormClass() {
		return HrStaffPersonReportForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());

			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");

			toFormPropertyMap.put("fdBeginPeriod", new ModelConvertor_Common(
					"fdBeginPeriod").setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdEndPeriod", new ModelConvertor_Common(
					"fdEndPeriod").setDateTimeType(DateUtil.TYPE_DATE));
		}
		return toFormPropertyMap;
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

	public Date getFdBeginPeriod() {
		return fdBeginPeriod;
	}

	public void setFdBeginPeriod(Date fdBeginPeriod) {
		this.fdBeginPeriod = fdBeginPeriod;
	}

	public Date getFdEndPeriod() {
		return fdEndPeriod;
	}

	public void setFdEndPeriod(Date fdEndPeriod) {
		this.fdEndPeriod = fdEndPeriod;
	}

	public Integer getFdAgeRange() {
		return fdAgeRange;
	}

	public void setFdAgeRange(Integer fdAgeRange) {
		this.fdAgeRange = fdAgeRange;
	}

	@Override
	public String getDocSubject() {
		return fdName;
	}

	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

}
