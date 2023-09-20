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
public class SysTimeLeaveLastAmountForm extends ExtendForm {
	private String fdPersonId;
	private String fdPersonName;
	private SysOrgPerson docCreatorId;
	private SysOrgPerson docCreatorName;
	private Float fdTotalDay;// 使用的额度(天数)
	private Float fdTotalTime;// 使用的额度(分钟数)
	private String fdDesc;
	private Date docCreateTime;
	private String fdLeaveId;// 请假明细id
	private Date fdStartDate;// 开始日期
	private Date fdEndDate;// 结束日期

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
		docCreateTime = null;
		fdLeaveId = null;
		fdStartDate = null;
		fdEndDate = null;
		fdTotalDay = null;
		super.reset(mapping, request);
	}

	public String getFdPersonId() {
		return fdPersonId;
	}

	public void setFdPersonId(String fdPersonId) {
		this.fdPersonId = fdPersonId;
	}

	public String getFdPersonName() {
		return fdPersonName;
	}

	public void setFdPersonName(String fdPersonName) {
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
