package com.landray.kmss.km.signature.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.signature.forms.KmSignatureDocumentHistoryForm;

/**
 * 文档印章历史库
 * 
 * @author weiby
 * @version 1.0 2014-12-13
 */
public class KmSignatureDocumentHistory extends BaseModel {

	/**
	 * 自动编号
	 */
	protected Integer fdHistoryId = 0;

	/**
	 * @return 自动编号
	 */
	public Integer getFdHistoryId() {
		return fdHistoryId;
	}

	/**
	 * @param fdHistoryId
	 *            自动编号
	 */
	public void setFdHistoryId(Integer fdHistoryId) {
		this.fdHistoryId = fdHistoryId;
	}

	/**
	 * 文档记录号
	 */
	protected String fdRecordId;

	/**
	 * @return 文档记录号
	 */
	public String getFdRecordId() {
		return fdRecordId;
	}

	/**
	 * @param fdRecordId
	 *            文档记录号
	 */
	public void setFdRecordId(String fdRecordId) {
		this.fdRecordId = fdRecordId;
	}

	/**
	 * 字段名称
	 */
	protected String fdFieldName;

	/**
	 * @return 字段名称
	 */
	public String getFdFieldName() {
		return fdFieldName;
	}

	/**
	 * @param fdFieldName
	 *            字段名称
	 */
	public void setFdFieldName(String fdFieldName) {
		this.fdFieldName = fdFieldName;
	}

	/**
	 * 印章名称
	 */
	protected String fdMarkName;

	/**
	 * @return 印章名称
	 */
	public String getFdMarkName() {
		return fdMarkName;
	}

	/**
	 * @param fdMarkName
	 *            印章名称
	 */
	public void setFdMarkName(String fdMarkName) {
		this.fdMarkName = fdMarkName;
	}

	/**
	 * 印章用户
	 */
	protected String fdUserName;

	/**
	 * @return 印章用户
	 */
	public String getFdUserName() {
		return fdUserName;
	}

	/**
	 * @param fdUserName
	 *            印章用户
	 */
	public void setFdUserName(String fdUserName) {
		this.fdUserName = fdUserName;
	}

	/**
	 * 日期时间
	 */
	protected Date fdDateTime;

	/**
	 * @return 日期时间
	 */
	public Date getFdDateTime() {
		return fdDateTime;
	}

	/**
	 * @param fdDateTime
	 *            日期时间
	 */
	public void setFdDateTime(Date fdDateTime) {
		this.fdDateTime = fdDateTime;
	}

	/**
	 * 印章IP地址
	 */
	protected String fdHostName;

	/**
	 * @return 印章IP地址
	 */
	public String getFdHostName() {
		return fdHostName;
	}

	/**
	 * @param fdHostName
	 *            印章IP地址
	 */
	public void setFdHostName(String fdHostName) {
		this.fdHostName = fdHostName;
	}

	/**
	 * 自动生成的印章唯一编号
	 */
	protected String fdMarkGuid;

	/**
	 * @return 自动生成的印章唯一编号
	 */
	public String getFdMarkGuid() {
		return fdMarkGuid;
	}

	/**
	 * @param fdMarkGuid
	 *            自动生成的印章唯一编号
	 */
	public void setFdMarkGuid(String fdMarkGuid) {
		this.fdMarkGuid = fdMarkGuid;
	}

	@Override
    public Class getFormClass() {
		return KmSignatureDocumentHistoryForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}

	private String fdSigId;

	public String getFdSigId() {
		return fdSigId;
	}

	public void setFdSigId(String fdSigId) {
		this.fdSigId = fdSigId;
	}

}
