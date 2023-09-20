package com.landray.kmss.km.signature.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.signature.model.KmSignatureDocumentSignature;

/**
 * 文档印章库 Form
 * 
 * @author weiby
 * @version 1.0 2013-09-23
 */
public class KmSignatureDocumentSignatureForm extends ExtendForm {

	/**
	 * 自动编号
	 */
	protected String fdDocumentSignatureId = null;

	/**
	 * @return 自动编号
	 */
	public String getFdDocumentSignatureId() {
		return fdDocumentSignatureId;
	}

	/**
	 * @param fdDocumentSignatureId
	 *            自动编号
	 */
	public void setFdDocumentSignatureId(String fdDocumentSignatureId) {
		this.fdDocumentSignatureId = fdDocumentSignatureId;
	}

	/**
	 * 此印章对应的文档编号
	 */
	protected String fdRecordId = null;

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
	protected String fdFieldName = null;

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
	protected String fdFieldValue = null;

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
	protected String fdUserName = null;

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
	protected String fdDateTime = null;

	/**
	 * @return 印章时间
	 */
	public String getFdDateTime() {
		return fdDateTime;
	}

	/**
	 * @param fdDateTime
	 *            印章时间
	 */
	public void setFdDateTime(String fdDateTime) {
		this.fdDateTime = fdDateTime;
	}

	/**
	 * 印章用户IP地址
	 */
	protected String fdHostName = null;

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
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdDocumentSignatureId = null;
		fdRecordId = null;
		fdFieldName = null;
		fdFieldValue = null;
		fdUserName = null;
		fdDateTime = null;
		fdHostName = null;

		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmSignatureDocumentSignature.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}
}
