package com.landray.kmss.sys.time.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.forms.SysTimeLeaveAmountFlowForm;

/**
 * 用户额度流水
 * 
 * @author linxiuxian
 *
 */
public class SysTimeLeaveAmountFlow extends BaseModel {
	private SysOrgPerson fdPerson;// 操作对象
	private SysOrgPerson docCreator;// 操作者
	private Float fdTotalTime;// 时长(分钟数)
	private String fdDesc;
	private String fdBusType;// 业务类型 请假/加班/出差/销假
	private Date docCreateTime;
	private String fdLeaveType;// 假期类型
	private Integer fdStatType;// 天/半天/小时
	private String fdMethod;// 增加或减扣(add/sub)
	private Float fdDayConvertTime;// 天与小时转换参数

	@Override
	public Class getFormClass() {
		return SysTimeLeaveAmountFlowForm.class;
	}

	public SysOrgPerson getFdPerson() {
		return fdPerson;
	}

	public void setFdPerson(SysOrgPerson fdPerson) {
		this.fdPerson = fdPerson;
	}

	public SysOrgPerson getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	public Float getFdTotalTime() {
		return fdTotalTime;
	}

	public void setFdTotalTime(Float fdTotalTime) {
		this.fdTotalTime = fdTotalTime;
	}

	public String getFdDesc() {
		return fdDesc;
	}

	public void setFdDesc(String fdDesc) {
		this.fdDesc = fdDesc;
	}

	public String getFdBusType() {
		return fdBusType;
	}

	public void setFdBusType(String fdBusType) {
		this.fdBusType = fdBusType;
	}

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getFdLeaveType() {
		return fdLeaveType;
	}

	public void setFdLeaveType(String fdLeaveType) {
		this.fdLeaveType = fdLeaveType;
	}

	public Integer getFdStatType() {
		return fdStatType;
	}

	public void setFdStatType(Integer fdStatType) {
		this.fdStatType = fdStatType;
	}

	public String getFdMethod() {
		return fdMethod;
	}

	public void setFdMethod(String fdMethod) {
		this.fdMethod = fdMethod;
	}

	public Float getFdDayConvertTime() {
		return fdDayConvertTime;
	}

	public void setFdDayConvertTime(Float fdDayConvertTime) {
		this.fdDayConvertTime = fdDayConvertTime;
	}

}
