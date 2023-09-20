package com.landray.kmss.sys.time.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountFlow;
import com.landray.kmss.web.action.ActionMapping;

/**
 * @author linxiuxian
 *
 */
public class SysTimeLeaveAmountFlowForm extends ExtendForm {
	private SysOrgPerson fdPersonId;// 操作对象
	private SysOrgPerson fdPersonName;
	private SysOrgPerson docCreatorId;// 操作者
	private SysOrgPerson docCreatorName;
	private Float fdTotalTime;// 时长
	private String fdDesc;
	private String fdBusType;// 业务类型
	private Date docCreateTime;
	private String fdLeaveType;// 假期编号
	private Integer fdStatType;// 按天,半天,小时(1,2,3)
	private String fdMethod;
	private Float fdDayConvertTime;// 天与小时转换参数

	@Override
	public Class getModelClass() {
		return SysTimeLeaveAmountFlow.class;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdPersonId = null;
		fdPersonName = null;
		docCreatorId = null;
		docCreatorName = null;
		fdTotalTime = null;
		fdDesc = null;
		fdBusType = null;
		docCreateTime = null;
		fdLeaveType = null;
		fdStatType = null;
		fdMethod = null;
		fdDayConvertTime = null;
		super.reset(mapping, request);
	}

	public SysOrgPerson getFdPersonId() {
		return fdPersonId;
	}

	public void setFdPersonId(SysOrgPerson fdPersonId) {
		this.fdPersonId = fdPersonId;
	}

	public SysOrgPerson getFdPersonName() {
		return fdPersonName;
	}

	public void setFdPersonName(SysOrgPerson fdPersonName) {
		this.fdPersonName = fdPersonName;
	}

	public SysOrgPerson getDocCreatorId() {
		return docCreatorId;
	}

	public void setDocCreatorId(SysOrgPerson docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	public SysOrgPerson getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(SysOrgPerson docCreatorName) {
		this.docCreatorName = docCreatorName;
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
