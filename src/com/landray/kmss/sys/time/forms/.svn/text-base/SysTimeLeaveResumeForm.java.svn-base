package com.landray.kmss.sys.time.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveResume;
import com.landray.kmss.web.action.ActionMapping;

/**
 *
 * @author cuiwj
 * @version 1.0 2019-01-15
 */
public class SysTimeLeaveResumeForm extends ExtendForm {

	private String fdPersonId;

	private String fdPersonName;

	private String fdStartTime;

	private String fdEndTime;
	// 销假天数
	private String fdLeaveTime;
	// 销假分钟数(替换fdLeaveTime)
	private String fdTotalTime;
	// 1：流程写入，2：手动追加
	private String fdOprType;
	// 0：未追加，1：销假成功，2：销假失败
	private String fdOprStatus;
	// 原因和描述
	private String fdOprDesc;

	private String fdReviewId;

	private String fdReviewName;

	private String docCreatorId;

	private String docCreatorName;

	private String docCreateTime;

	private String fdDetailId;

	private String fdDetailName;

	private String fdDetailStartTime;

	private String fdDetailEndTime;

	private String fdDetailStatType;

	private String fdDetailStartNoon;

	private String fdDetailEndNoon;

	// 是否在扣减成功后更新考勤
	private String fdIsUpdateAttend;
	// 更新考勤成功：1，更新失败：0
	private String fdUpdateAttendStatus;

	// 上下午标识
	private Integer fdStartNoon;
	private Integer fdEndNoon;
	// 销假类型(对应请假类型)
	private Integer fdResumeType;

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

	public String getFdStartTime() {
		return fdStartTime;
	}

	public void setFdStartTime(String fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	public String getFdEndTime() {
		return fdEndTime;
	}

	public void setFdEndTime(String fdEndTime) {
		this.fdEndTime = fdEndTime;
	}

	public String getFdLeaveTime() {
		return fdLeaveTime;
	}

	public void setFdLeaveTime(String fdLeaveTime) {
		this.fdLeaveTime = fdLeaveTime;
	}

	public String getFdOprType() {
		return fdOprType;
	}

	public void setFdOprType(String fdOprType) {
		this.fdOprType = fdOprType;
	}

	public String getFdOprStatus() {
		return fdOprStatus;
	}

	public void setFdOprStatus(String fdOprStatus) {
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

	public String getFdDetailId() {
		return fdDetailId;
	}

	public void setFdDetailId(String fdDetailId) {
		this.fdDetailId = fdDetailId;
	}

	public String getFdDetailName() {
		return fdDetailName;
	}

	public void setFdDetailName(String fdDetailName) {
		this.fdDetailName = fdDetailName;
	}

	public String getFdDetailStartTime() {
		return fdDetailStartTime;
	}

	public void setFdDetailStartTime(String fdDetailStartTime) {
		this.fdDetailStartTime = fdDetailStartTime;
	}

	public String getFdDetailEndTime() {
		return fdDetailEndTime;
	}

	public void setFdDetailEndTime(String fdDetailEndTime) {
		this.fdDetailEndTime = fdDetailEndTime;
	}

	public String getFdIsUpdateAttend() {
		return fdIsUpdateAttend;
	}

	public void setFdIsUpdateAttend(String fdIsUpdateAttend) {
		this.fdIsUpdateAttend = fdIsUpdateAttend;
	}

	public String getFdUpdateAttendStatus() {
		return fdUpdateAttendStatus;
	}

	public void setFdUpdateAttendStatus(String fdUpdateAttendStatus) {
		this.fdUpdateAttendStatus = fdUpdateAttendStatus;
	}

	public String getFdDetailStatType() {
		return fdDetailStatType;
	}

	public void setFdDetailStatType(String fdDetailStatType) {
		this.fdDetailStatType = fdDetailStatType;
	}

	public String getFdDetailStartNoon() {
		return fdDetailStartNoon;
	}

	public void setFdDetailStartNoon(String fdDetailStartNoon) {
		this.fdDetailStartNoon = fdDetailStartNoon;
	}

	public String getFdDetailEndNoon() {
		return fdDetailEndNoon;
	}

	public void setFdDetailEndNoon(String fdDetailEndNoon) {
		this.fdDetailEndNoon = fdDetailEndNoon;
	}

	public String getFdTotalTime() {
		return fdTotalTime;
	}

	public void setFdTotalTime(String fdTotalTime) {
		this.fdTotalTime = fdTotalTime;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdPersonId = null;
		fdPersonName = null;
		fdStartTime = null;
		fdEndTime = null;
		fdLeaveTime = null;
		fdOprType = null;
		fdOprStatus = null;
		fdOprDesc = null;
		fdReviewId = null;
		fdReviewName = null;
		docCreateTime = null;
		docCreatorId = null;
		docCreatorName = null;
		fdDetailId = null;
		fdDetailName = null;
		fdDetailStartTime = null;
		fdDetailEndTime = null;
		fdDetailStatType = null;
		fdDetailStartNoon = null;
		fdDetailEndNoon = null;
		fdIsUpdateAttend = null;
		fdUpdateAttendStatus = null;
		fdTotalTime = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdPersonId", new FormConvertor_IDToModel(
					"fdPerson", SysOrgPerson.class));
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgPerson.class));
			toModelPropertyMap.put("fdDetailId", new FormConvertor_IDToModel(
					"fdLeaveDetail", SysTimeLeaveDetail.class));
		}
		return toModelPropertyMap;
	}

	@Override
	public Class getModelClass() {
		return SysTimeLeaveResume.class;
	}

}
