package com.landray.kmss.km.signature.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.signature.forms.KmSignatureDocumentSignatureForm;

/**
 * 文档印章库
 * 
 * @author weiby
 * @version 1.0 2014-12-13
 */
public class KmSignatureDocumentSignature extends BaseModel {

	/**
	 * 自动编号
	 */
	protected Integer fdDocumentSignatureId = 0;

	/**
	 * @return 自动编号
	 */
	public Integer getFdDocumentSignatureId() {
		return fdDocumentSignatureId;
	}

	/**
	 * @param fdDocumentSignatureId
	 *            自动编号
	 */
	public void setFdDocumentSignatureId(Integer fdDocumentSignatureId) {
		this.fdDocumentSignatureId = fdDocumentSignatureId;
	}

	/**
	 * 此印章对应的文档编号
	 */
	protected String fdRecordId;

	/**
	 * @return 此印章对应的文档编号
	 */
	public String getFdRecordId() {
		return fdRecordId;
	}

	/**
	 * @param fdRecordId
	 *            此印章对应的文档编号
	 */
	public void setFdRecordId(String fdRecordId) {
		this.fdRecordId = fdRecordId;
	}

	/**
	 * 此印章对应的字段(或区域名称)
	 */
	protected String fdFieldName;

	/**
	 * @return 此印章对应的字段(或区域名称)
	 */
	public String getFdFieldName() {
		return fdFieldName;
	}

	/**
	 * @param fdFieldName
	 *            此印章对应的字段(或区域名称)
	 */
	public void setFdFieldName(String fdFieldName) {
		this.fdFieldName = fdFieldName;
	}

	/**
	 * 此印章对应的印章信息(即当前印章的加密图片信息)
	 */
	protected String fdFieldValue;

	/**
	 * @return 此印章对应的印章信息(即当前印章的加密图片信息)
	 */
	public String getFdFieldValue() {
		return fdFieldValue;
	}

	/**
	 * @param fdFieldValue
	 *            此印章对应的印章信息(即当前印章的加密图片信息)
	 */
	public void setFdFieldValue(String fdFieldValue) {
		this.fdFieldValue = fdFieldValue;
	}

	/**
	 * 用户名称
	 */
	protected String fdUserName;

	/**
	 * @return 用户名称
	 */
	public String getFdUserName() {
		return fdUserName;
	}

	/**
	 * @param fdUserName
	 *            用户名称
	 */
	public void setFdUserName(String fdUserName) {
		this.fdUserName = fdUserName;
	}

	/**
	 * 印章时间
	 */
	protected Date fdDateTime;

	/**
	 * @return 印章时间
	 */
	public Date getFdDateTime() {
		return fdDateTime;
	}

	/**
	 * @param fdDateTime
	 *            印章时间
	 */
	public void setFdDateTime(Date fdDateTime) {
		this.fdDateTime = fdDateTime;
	}

	/**
	 * 印章用户IP地址
	 */
	protected String fdHostName;

	/**
	 * @return 印章用户IP地址
	 */
	public String getFdHostName() {
		return fdHostName;
	}

	/**
	 * @param fdHostName
	 *            印章用户IP地址
	 */
	public void setFdHostName(String fdHostName) {
		this.fdHostName = fdHostName;
	}

	@Override
    public Class getFormClass() {
		return KmSignatureDocumentSignatureForm.class;
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
}
