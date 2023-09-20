package com.landray.kmss.sys.time.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaModel;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.forms.SysTimeLeaveDetailForm;
import com.landray.kmss.sys.time.util.SysTimeUtil;

import java.util.Date;

/**
 * 请假明细
 *
 * @author cuiwj
 * @version 1.0 2018-12-24
 */
public class SysTimeLeaveDetail extends BaseModel
		implements ISysAuthAreaModel, ISysNotifyModel {

	private SysOrgPerson fdPerson;

	private String fdLeaveName;

	private Date fdStartTime;

	private Date fdEndTime;
	// 请假/加班天数
	private Float fdLeaveTime;
	// 请假/加班分钟数(代替fdLeaveTime)
	private Float fdTotalTime;
	// 请假编号
	private String fdLeaveType;

	// 1：流程写入，2：手动录入，3：批量导入
	private Integer fdOprType;
	// 0：未扣减，1：扣减成功，2：扣减失败 ，5：预扣减（流程起草节点进行），在流程废弃或者结束执行操作
	private Integer fdOprStatus;
	// 原因和描述
	private String fdOprDesc;

	private String fdReviewId;

	private String fdReviewName;

	private SysOrgPerson docCreator;

	private Date docCreateTime;
	// 1：按天，2：按半天：3：按小时
	private Integer fdStatType;
	// 开始上下午标识
	private Integer fdStartNoon;
	// 结束上下午标识
	private Integer fdEndNoon;
	// 是否可以更新考勤
	private Boolean fdCanUpdateAttend;
	// 是否在扣减成功后更新考勤
	private Boolean fdIsUpdateAttend;
	// 更新考勤成功：1，更新失败：0
	private Integer fdUpdateAttendStatus;

	// 销假天数
	private Float fdResumeDays;
	// 销假分钟数(代替fdResumeDays)
	private Float fdResumeTime;

	// 操作类型:1:请假,2:加班(为空默认为请假)
	private Integer fdType = 1;
	//上一年度扣除假期天数
	private Float fdPreviousYearAmount;
	//本年度扣除假期天数
	private Float fdCurrentYearAmount;

	public Integer getFdType() {
		return fdType;
	}

	public void setFdType(Integer fdType) {
		this.fdType = fdType;
	}

	public SysOrgPerson getFdPerson() {
		return fdPerson;
	}

	public void setFdPerson(SysOrgPerson fdPerson) {
		this.fdPerson = fdPerson;
	}

	public String getFdLeaveName() {
		return fdLeaveName;
	}

	public void setFdLeaveName(String fdLeaveName) {
		this.fdLeaveName = fdLeaveName;
	}

	public Date getFdStartTime() {
		return fdStartTime;
	}

	public void setFdStartTime(Date fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	public Date getFdEndTime() {
		return fdEndTime;
	}

	public void setFdEndTime(Date fdEndTime) {
		this.fdEndTime = fdEndTime;
	}

	public Float getFdLeaveTime() {
		return fdLeaveTime;
	}

	public void setFdLeaveTime(Float fdLeaveTime) {
		this.fdLeaveTime = fdLeaveTime;
	}

	public String getFdLeaveType() {
		return fdLeaveType;
	}

	public void setFdLeaveType(String fdLeaveType) {
		this.fdLeaveType = fdLeaveType;
	}

	public Integer getFdOprType() {
		return fdOprType;
	}

	public void setFdOprType(Integer fdOprType) {
		this.fdOprType = fdOprType;
	}

	public Integer getFdOprStatus() {
		return fdOprStatus;
	}

	public void setFdOprStatus(Integer fdOprStatus) {
		this.fdOprStatus = fdOprStatus;
	}

	public String getFdOprDesc() {
		return fdOprDesc;
	}

	public void setFdOprDesc(String fdOprDesc) {
		this.fdOprDesc = fdOprDesc;
	}

	public String getFdReviewId() {
		return fdReviewId;
	}

	public void setFdReviewId(String fdReviewId) {
		this.fdReviewId = fdReviewId;
	}

	public String getFdReviewName() {
		return fdReviewName;
	}

	public void setFdReviewName(String fdReviewName) {
		this.fdReviewName = fdReviewName;
	}

	public SysOrgPerson getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public Integer getFdStatType() {
		return fdStatType;
	}

	public void setFdStatType(Integer fdStatType) {
		this.fdStatType = fdStatType;
	}

	public Integer getFdStartNoon() {
		return fdStartNoon;
	}

	public void setFdStartNoon(Integer fdStartNoon) {
		this.fdStartNoon = fdStartNoon;
	}

	public Integer getFdEndNoon() {
		return fdEndNoon;
	}

	public void setFdEndNoon(Integer fdEndNoon) {
		this.fdEndNoon = fdEndNoon;
	}

	public Boolean getFdCanUpdateAttend() {
		return fdCanUpdateAttend;
	}

	public void setFdCanUpdateAttend(Boolean fdCanUpdateAttend) {
		this.fdCanUpdateAttend = fdCanUpdateAttend;
	}

	public Boolean getFdIsUpdateAttend() {
		return fdIsUpdateAttend;
	}

	public void setFdIsUpdateAttend(Boolean fdIsUpdateAttend) {
		this.fdIsUpdateAttend = fdIsUpdateAttend;
	}

	public Integer getFdUpdateAttendStatus() {
		return fdUpdateAttendStatus;
	}

	public void setFdUpdateAttendStatus(Integer fdUpdateAttendStatus) {
		this.fdUpdateAttendStatus = fdUpdateAttendStatus;
	}

	public Float getFdResumeDays() {
		return fdResumeDays;
	}

	public void setFdResumeDays(Float fdResumeDays) {
		this.fdResumeDays = fdResumeDays;
	}

	/**
	 * 请假分钟数
	 * 
	 * @return
	 */
	public Float getFdTotalTime() {
		if (fdTotalTime == null) {
			fdLeaveTime = fdLeaveTime == null ? 0f : fdLeaveTime;
			if (Integer.valueOf(3).equals(this.fdStatType)) {
				return this.fdLeaveTime * SysTimeUtil.getConvertTime() * 60;
			} else {
				return this.fdLeaveTime * 24 * 60;
			}
		}
		return fdTotalTime;
	}

	public void setFdTotalTime(Float fdTotalTime) {
		this.fdTotalTime = fdTotalTime;
	}

	/**
	 * 销假分钟数
	 * 
	 * @return
	 */
	public Float getFdResumeTime() {
		if (fdResumeTime == null) {
			fdResumeDays = fdResumeDays == null ? 0f : fdResumeDays;
			if (Integer.valueOf(3).equals(this.fdStatType)) {
				return this.fdResumeDays * SysTimeUtil.getConvertTime() * 60;
			} else {
				return this.fdResumeDays * 24 * 60;
			}
		}
		return fdResumeTime;
	}

	public void setFdResumeTime(Float fdResumeTime) {
		this.fdResumeTime = fdResumeTime;
	}


	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdPerson.fdId", "fdPersonId");
			toFormPropertyMap.put("fdPerson.fdName", "fdPersonName");
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("authArea.fdId", "authAreaId");
			toFormPropertyMap.put("authArea.fdName", "authAreaName");
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		return SysTimeLeaveDetailForm.class;
	}

	/*
	 * 所属场所
	 */
	protected SysAuthArea authArea;

	@Override
	public SysAuthArea getAuthArea() {
		return authArea;
	}

	@Override
	public void setAuthArea(SysAuthArea authArea) {
		this.authArea = authArea;
	}

	private String sysTimeLeaveAmountItemId;

	public String getSysTimeLeaveAmountItemId() {
		return sysTimeLeaveAmountItemId;
	}

	public void setSysTimeLeaveAmountItemId(
			String sysTimeLeaveAmountItemId) {
		this.sysTimeLeaveAmountItemId = sysTimeLeaveAmountItemId;
	}

	public Float getFdPreviousYearAmount() {
		if(fdPreviousYearAmount==null){
			fdPreviousYearAmount =0F;
		}
		return fdPreviousYearAmount;
	}

	public void setFdPreviousYearAmount(Float fdPreviousYearAmount) {
		this.fdPreviousYearAmount = fdPreviousYearAmount;
	}

	public Float getFdCurrentYearAmount() {
		if(fdCurrentYearAmount==null){
			fdCurrentYearAmount =0F;
		}
		return fdCurrentYearAmount;
	}

	public void setFdCurrentYearAmount(Float fdCurrentYearAmount) {
		this.fdCurrentYearAmount = fdCurrentYearAmount;
	}
}
