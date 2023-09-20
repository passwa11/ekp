package com.landray.kmss.sys.attend.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 上下班提醒日志
 *
 * @author cuiwj
 * @version 1.0 2019-01-09
 */
public class SysAttendNotifyRemindLog extends BaseModel {

	private String fdSubject;

	private List<SysOrgElement> fdTargets;

	private String fdNotifyType;

	private Date fdTime;

	private String fdCategoryId;

	private String fdCategoryName;

	public String getFdSubject() {
		return fdSubject;
	}

	public void setFdSubject(String fdSubject) {
		this.fdSubject = fdSubject;
	}

	public List<SysOrgElement> getFdTargets() {
		return fdTargets;
	}

	public void setFdTargets(List<SysOrgElement> fdTargets) {
		this.fdTargets = fdTargets;
	}

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

	public Date getFdTime() {
		return fdTime;
	}

	public void setFdTime(Date fdTime) {
		this.fdTime = fdTime;
	}

	public String getFdCategoryId() {
		return fdCategoryId;
	}

	public void setFdCategoryId(String fdCategoryId) {
		this.fdCategoryId = fdCategoryId;
	}

	public String getFdCategoryName() {
		return fdCategoryName;
	}

	public void setFdCategoryName(String fdCategoryName) {
		this.fdCategoryName = fdCategoryName;
	}

	@Override
	public Class getFormClass() {
		return null;
	}

}
