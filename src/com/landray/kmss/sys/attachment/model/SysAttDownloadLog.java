package com.landray.kmss.sys.attachment.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.SysAttDownloadLogForm;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
 * 附件下载记录日志
 * 
 * @author 郑超 2017-8-16
 *
 */
public class SysAttDownloadLog extends BaseModel
		implements InterceptFieldEnabled {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fdFileName; // 附件名称
	private String fdAttId; // 附件ID
	private String docCreatorId;
	private String docCreatorName;
	private Date docCreateTime;
	private String fdModelName; // 主文档
	private String fdModelId; // 主文档ID
	private String fdKey;
	private String fdDeptId; // 部门ID
	private String fdDeptName; // 部门ID
	private String fdIp; // 客户端IP
	private String fdDownloadType; // 下载类型

	public String getFdFileName() {
		return fdFileName;
	}

	public void setFdFileName(String fdFileName) {
		this.fdFileName = fdFileName;
	}

	public String getFdAttId() {
		return fdAttId;
	}

	public void setFdAttId(String fdAttId) {
		this.fdAttId = fdAttId;
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

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
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
	public Class getFormClass() {
		return SysAttDownloadLogForm.class;
	}

}
