package com.landray.kmss.sys.time.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.forms.SysTimeLeaveLastAmountForm;

/**
 * 记录用户请假使用上周期额度时相关信息(该model信息用于销假恢复额度)
 * 
 * @author linxiuxian
 *
 */
public class SysTimeLeaveLastAmount extends BaseModel {
	private SysOrgPerson fdPerson;// 操作对象
	private SysOrgPerson docCreator;// 操作者
	private Float fdTotalDay;// 时长(天数)
	private Float fdTotalTime;// 时长(分钟数)

	private String fdDesc;
	private Date docCreateTime;
	private String fdLeaveId;// 请假明细id
	private Date fdStartDate;// 开始日期
	private Date fdEndDate;// 结束日期

	@Override
	public Class getFormClass() {
		return SysTimeLeaveLastAmountForm.class;
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

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getFdLeaveId() {
		return fdLeaveId;
	}

	public void setFdLeaveId(String fdLeaveId) {
		this.fdLeaveId = fdLeaveId;
	}

	public Date getFdStartDate() {
		return fdStartDate;
	}

	public void setFdStartDate(Date fdStartDate) {
		this.fdStartDate = fdStartDate;
	}

	public Date getFdEndDate() {
		return fdEndDate;
	}

	public void setFdEndDate(Date fdEndDate) {
		this.fdEndDate = fdEndDate;
	}

	public Float getFdTotalDay() {
		return fdTotalDay;
	}

	public void setFdTotalDay(Float fdTotalDay) {
		this.fdTotalDay = fdTotalDay;
	}


}
