package com.landray.kmss.sys.attachment.forms;

import java.io.Serializable;

import com.landray.kmss.util.AutoArrayList;

/**
 * 创建日期 2006-九月-04
 * 
 * @author 孙真
 */
public  class AttachmentDetailsForm implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8244782393549872277L;
	/*
	 * 模型ID
	 */
    private String fdModelId = null;
	/*
	 * 模型
	 */
    private String fdModelName = null;
	/*
	 * KEY
	 */
    private String fdKey = null;
    
    private String fdAttType = null;
    
    private String fdMulti = null;
    
    private String attachmentIds = null;
    
    private String deletedAttachmentIds = null;
    
    private String extParam = null;

	//附件保存转换控制，默认为true，设置false表示附件仅保存，不进入转换队列
	private Boolean addQueue = true;
    
    public String getExtParam() {
		return extParam;
	}

	public void setExtParam(String extParam) {
		this.extParam = extParam;
	}

	/*
     * 附件信息列表
     */
    private AutoArrayList attachments = new AutoArrayList(SysAttMainForm.class);

	public String getAttachmentIds() {
		return attachmentIds;
	}

	public void setAttachmentIds(String attachmentIds) {
		this.attachmentIds = attachmentIds;
	}

	public AutoArrayList getAttachments() {
		return attachments;
	}

	public void setAttachments(AutoArrayList attachments) {
		this.attachments = attachments;
	}

	public String getDeletedAttachmentIds() {
		return deletedAttachmentIds;
	}

	public void setDeletedAttachmentIds(String deletedAttachmentIds) {
		this.deletedAttachmentIds = deletedAttachmentIds;
	}

	public String getFdKey() {
		return fdKey;
	}

	public void setFdKey(String fdKey) {
		this.fdKey = fdKey;
	}

	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	public String getFdAttType() {
		return fdAttType;
	}

	public void setFdAttType(String fdAttType) {
		this.fdAttType = fdAttType;
	}

	public String getFdMulti() {
		return fdMulti;
	}

	public void setFdMulti(String fdMulti) {
		this.fdMulti = fdMulti;
	}

	public Boolean getAddQueue() {
		return addQueue;
	}

	public void setAddQueue(Boolean addQueue) {
		this.addQueue = addQueue;
	}
}
