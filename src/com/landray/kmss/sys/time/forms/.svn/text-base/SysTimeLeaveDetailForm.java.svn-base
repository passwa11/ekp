package com.landray.kmss.sys.time.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.web.action.ActionMapping;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-24
 */
public class SysTimeLeaveDetailForm extends SysTimeImportForm
		implements ISysAuthAreaForm {

	private String fdPersonId;

	private String fdPersonName;

	private String fdLeaveName;

	private String fdStartTime;

	private String fdEndTime;

	private String fdLeaveTime;
	// 请假/加班分钟数(代替fdLeaveTime)
	private String fdTotalTime;
	private String fdLeaveType;

	private String fdOprType;

	private String fdOprStatus;

	private String fdOprDesc;

	private String fdReviewId;

	private String fdReviewName;

	private String docCreatorId;

	private String docCreatorName;

	private String docCreateTime;
	// 1：按天，2：按半天：3：按小时
	private String fdStatType;
	// 开始上下午标识
	private String fdStartNoon;
	// 结束上下午标识
	private String fdEndNoon;
	// 是否可以更新考勤
	private String fdCanUpdateAttend;
	// 是否扣减成功时更新考勤
	private String fdIsUpdateAttend;

	private String fdResumeDays;
	// 销假分钟数(代替fdResumeDays)
	private String fdResumeTime;
	// 操作类型:1:请假,2:加班(为空默认为请假)
	private String fdType;


	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
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

	public String getFdLeaveName() {
		return fdLeaveName;
	}

	public void setFdLeaveName(String fdLeaveName) {
		this.fdLeaveName = fdLeaveName;
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

	public String getFdLeaveType() {
		return fdLeaveType;
	}

	public void setFdLeaveType(String fdLeaveType) {
		this.fdLeaveType = fdLeaveType;
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

	public String getFdStatType() {
		return fdStatType;
	}

	public void setFdStatType(String fdStatType) {
		this.fdStatType = fdStatType;
	}

	public String getFdStartNoon() {
		return fdStartNoon;
	}

	public void setFdStartNoon(String fdStartNoon) {
		this.fdStartNoon = fdStartNoon;
	}

	public String getFdEndNoon() {
		return fdEndNoon;
	}

	public void setFdEndNoon(String fdEndNoon) {
		this.fdEndNoon = fdEndNoon;
	}

	public String getFdCanUpdateAttend() {
		return fdCanUpdateAttend;
	}

	public void setFdCanUpdateAttend(String fdCanUpdateAttend) {
		this.fdCanUpdateAttend = fdCanUpdateAttend;
	}

	public String getFdIsUpdateAttend() {
		return fdIsUpdateAttend;
	}

	public void setFdIsUpdateAttend(String fdIsUpdateAttend) {
		this.fdIsUpdateAttend = fdIsUpdateAttend;
	}

	public String getFdResumeDays() {
		return fdResumeDays;
	}

	public void setFdResumeDays(String fdResumeDays) {
		this.fdResumeDays = fdResumeDays;
	}

	public String getFdTotalTime() {
		return fdTotalTime;
	}

	public void setFdTotalTime(String fdTotalTime) {
		this.fdTotalTime = fdTotalTime;
	}

	public String getFdResumeTime() {
		return fdResumeTime;
	}

	public void setFdResumeTime(String fdResumeTime) {
		this.fdResumeTime = fdResumeTime;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdPersonId = null;
		fdPersonName = null;
		fdLeaveName = null;
		fdStartTime = null;
		fdEndTime = null;
		fdLeaveTime = null;
		fdLeaveType = null;
		fdOprType = null;
		fdOprStatus = null;
		fdOprDesc = null;
		fdReviewId = null;
		fdReviewName = null;
		docCreateTime = null;
		docCreatorId = null;
		docCreatorName = null;
		fdStatType = null;
		fdStartNoon = null;
		fdEndNoon = null;
		fdCanUpdateAttend = null;
		fdIsUpdateAttend = null;
		fdResumeDays = null;
		fdType = null;
		fdTotalTime = null;
		fdResumeTime = null;
		authAreaId = null;
		authAreaName = null;
		sysTimeLeaveAmountItemId = null;
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
			toModelPropertyMap.put("authAreaId", new FormConvertor_IDToModel(
					"authArea", SysAuthArea.class));
		}
		return toModelPropertyMap;
	}

	@Override
	public Class getModelClass() {
		return SysTimeLeaveDetail.class;
	}

	// 所属场所ID
	protected String authAreaId = null;

	@Override
    public String getAuthAreaId() {
		return authAreaId;
	}

	@Override
    public void setAuthAreaId(String authAreaId) {
		this.authAreaId = authAreaId;
	}
	// 所属场所名称
	protected String authAreaName = null;

	@Override
    public String getAuthAreaName() {
		return authAreaName;
	}

	@Override
    public void setAuthAreaName(String authAreaName) {
		this.authAreaName = authAreaName;
	}

	// 所用额度id
	protected String sysTimeLeaveAmountItemId;

	public String getSysTimeLeaveAmountItemId() {
		return sysTimeLeaveAmountItemId;
	}

	public void setSysTimeLeaveAmountItemId(String sysTimeLeaveAmountItemId) {
		this.sysTimeLeaveAmountItemId = sysTimeLeaveAmountItemId;
	}
}
