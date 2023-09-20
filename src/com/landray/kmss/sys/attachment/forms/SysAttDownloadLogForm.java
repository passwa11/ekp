package com.landray.kmss.sys.attachment.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.model.SysAttDownloadLog;

public class SysAttDownloadLogForm extends ExtendForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String docCreatorId;
	private String docCreatorName;
	private String fdAttId;
	private String fdFileName;
	private String docCreateTime;
	private String fdModelName;
	private String fdModelId;
	private String fdKey;
	private String fdDeptId; // 部门ID
	private String fdDeptName; // 部门ID
	private String fdIp; // 客户端IP
	private String fdDownloadType; // 下载类型

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

	public String getFdAttId() {
		return fdAttId;
	}

	public void setFdAttId(String fdAttId) {
		this.fdAttId = fdAttId;
	}

	public String getFdFileName() {
		return fdFileName;
	}

	public void setFdFileName(String fdFileName) {
		this.fdFileName = fdFileName;
	}

	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	public String getFdKey() {
		return fdKey;
	}

	public void setFdKey(String fdKey) {
		this.fdKey = fdKey;
	}

	public String getFdDeptId() {
		return fdDeptId;
	}

	public void setFdDeptId(String fdDeptId) {
		this.fdDeptId = fdDeptId;
	}

	public String getFdDeptName() {
		return fdDeptName;
	}

	public void setFdDeptName(String fdDeptName) {
		this.fdDeptName = fdDeptName;
	}

	public String getFdIp() {
		return fdIp;
	}

	public void setFdIp(String fdIp) {
		this.fdIp = fdIp;
	}

	public String getFdDownloadType() {
		return fdDownloadType;
	}

	public void setFdDownloadType(String fdDownloadType) {
		this.fdDownloadType = fdDownloadType;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docCreatorId = null;
		docCreatorName = null;
		fdAttId = null;
		fdFileName = null;
		docCreateTime = null;
		fdModelName = null;
		fdModelId = null;
		fdKey = null;
		fdDeptId = null; // 部门ID
		fdDeptName = null; // 部门ID
		fdIp = null;
		fdDownloadType = null;
		super.reset(mapping, request);
	}
	@Override
	public Class getModelClass() {
		return SysAttDownloadLog.class;
	}

}
