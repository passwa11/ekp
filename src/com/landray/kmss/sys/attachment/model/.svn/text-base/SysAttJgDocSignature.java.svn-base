package com.landray.kmss.sys.attachment.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;

/**
 * iWebRevision手写签批实体类
 *
 */
public class SysAttJgDocSignature extends BaseModel {

	private static final long serialVersionUID = -6832396957939575932L;
	
	private String fdRecordID;	//文档ID
	
	private String fdFieldName;
	
	// 大字段数据
	private String fdFieldValue;	//签批数据
	
	private String fdUserID;	//签批用户ID
	
	private Date fdDateTime;	//签章日期时间
	
	private String fdHostName;	//客户端IP

	public String getFdRecordID() {
		return fdRecordID;
	}

	public void setFdRecordID(String fdRecordID) {
		this.fdRecordID = fdRecordID;
	}

	public String getFdFieldName() {
		return fdFieldName;
	}

	public void setFdFieldName(String fdFieldName) {
		this.fdFieldName = fdFieldName;
	}

	public String getFdFieldValue() {
		return (String) readLazyField("fdFieldValue", fdFieldValue);
	}

	public void setFdFieldValue(String fdFieldValue) {
		this.fdFieldValue = (String) writeLazyField("fdFieldValue",
				this.fdFieldValue, fdFieldValue);
	}

	public String getFdUserID() {
		return fdUserID;
	}

	public void setFdUserID(String fdUserID) {
		this.fdUserID = fdUserID;
	}

	public Date getFdDateTime() {
		return fdDateTime;
	}

	public void setFdDateTime(Date fdDateTime) {
		this.fdDateTime = fdDateTime;
	}

	public String getFdHostName() {
		return fdHostName;
	}

	public void setFdHostName(String fdHostName) {
		this.fdHostName = fdHostName;
	}

	@Override
	public Class getFormClass() {
		// TODO Auto-generated method stub
		return null;
	}
}
