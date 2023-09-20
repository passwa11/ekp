package com.landray.kmss.sys.time.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.forms.SysTimeLeaveResumeForm;
import com.landray.kmss.sys.time.util.SysTimeUtil;

/**
 *
 * @author cuiwj
 * @version 1.0 2019-01-15
 */
public class SysTimeLeaveResume extends BaseModel {

	private SysOrgPerson fdPerson;

	private Date fdStartTime;

	private Date fdEndTime;

	private Float fdLeaveTime;
	// 销假分钟数(替换fdLeaveTime)
	private Float fdTotalTime;

	// 1：流程录入，2：手动追加
	private Integer fdOprType;
	// 0：待处理，1：销假成功，2：销假失败
	private Integer fdOprStatus;
	// 原因和描述
	private String fdOprDesc;

	private String fdReviewId;

	private String fdReviewName;

	private SysOrgPerson docCreator;

	private Date docCreateTime;

	// 是否在扣减成功后更新考勤
	private Boolean fdIsUpdateAttend;
	// 更新考勤成功：1，更新失败：0
	private Integer fdUpdateAttendStatus;
	// 上下午标识
	private Integer fdStartNoon;
	private Integer fdEndNoon;
	// 销假类型(对应请假类型)
	private Integer fdResumeType;

	// 关联的请假明细
	private SysTimeLeaveDetail fdLeaveDetail;

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

	public Integer getFdResumeType() {
		return fdResumeType;
	}

	public void setFdResumeType(Integer fdResumeType) {
		this.fdResumeType = fdResumeType;
	}

	public SysOrgPerson getFdPerson() {
		return fdPerson;
	}

	public void setFdPerson(SysOrgPerson fdPerson) {
		this.fdPerson = fdPerson;
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

	public SysTimeLeaveDetail getFdLeaveDetail() {
		return fdLeaveDetail;
	}

	public void setFdLeaveDetail(SysTimeLeaveDetail fdLeaveDetail) {
		this.fdLeaveDetail = fdLeaveDetail;
	}

	/**
	 * 销假分钟数
	 * 
	 * @return
	 */
	public Float getFdTotalTime() {
		if (fdTotalTime == null) {
			fdLeaveTime = this.fdLeaveTime == null ? 0 : this.fdLeaveTime;
			if (Integer.valueOf(3).equals(fdLeaveDetail.getFdStatType())) {
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
			toFormPropertyMap.put("fdLeaveDetail.fdId", "fdDetailId");
			toFormPropertyMap.put("fdLeaveDetail.fdLeaveName", "fdDetailName");
			toFormPropertyMap.put("fdLeaveDetail.fdStartTime",
					"fdDetailStartTime");
			toFormPropertyMap.put("fdLeaveDetail.fdEndTime", "fdDetailEndTime");
			toFormPropertyMap.put("fdLeaveDetail.fdStatType",
					"fdDetailStatType");
			toFormPropertyMap.put("fdLeaveDetail.fdStartNoon",
					"fdDetailStartNoon");
			toFormPropertyMap.put("fdLeaveDetail.fdEndNoon",
					"fdDetailEndNoon");
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		return SysTimeLeaveResumeForm.class;
	}
}
