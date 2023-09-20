package com.landray.kmss.sys.webservice2.forms;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlMimeType;

/**
 * 附件Form
 * 
 * @author Jeff
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AttachmentForm {

	/*
	 * KEY，默认值为fdAttachment
	 */
	private String fdKey;

	/*
	 * 文件名
	 */
	private String fdFileName;

	// 附件内容
	@XmlMimeType("application/octet-stream")
	private DataHandler fdAttachment;

	public DataHandler getFdAttachment() {
		return fdAttachment;
	}

	public void setFdAttachment(DataHandler fdAttachment) {
		this.fdAttachment = fdAttachment;
	}

	/**
	 * @return 返回 KEY
	 */
	public String getFdKey() {
		return fdKey;
	}

	/**
	 * @param fdKey
	 *            要设置的 KEY
	 */
	public void setFdKey(String fdKey) {
		this.fdKey = fdKey;
	}

	/**
	 * @return 返回 文件名
	 */
	public String getFdFileName() {
		return fdFileName;
	}

	/**
	 * @param fdFileName
	 *            要设置的 文件名
	 */
	public void setFdFileName(String fdFileName) {
		this.fdFileName = fdFileName;
	}

}
