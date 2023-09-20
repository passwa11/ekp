package com.landray.kmss.km.signature.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.signature.model.KmSignatureDocumentHistory;

/**
 * 文档印章历史库 Form
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class KmSignatureDocumentHistoryForm extends ExtendForm {

	/**
	 * 自动编号
	 */
	protected String fdHistoryId = null;

	/**
	 * @return 自动编号
	 */
	public String getFdHistoryId() {
		return fdHistoryId;
	}

	/**
	 * @param fdHistoryId
	 *            自动编号
	 */
	public void setFdHistoryId(String fdHistoryId) {
		this.fdHistoryId = fdHistoryId;
	}

	/**
	 * 文档记录号
	 */
	protected String fdRecordId = null;

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
	protected String fdFieldName = null;

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
	protected String fdMarkName = null;

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
	protected String fdUserName = null;

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
	protected String fdDateTime = null;

	/**
	 * @return 日期时间
	 */
	public String getFdDateTime() {
		return fdDateTime;
	}

	/**
	 * @param fdDateTime
	 *            日期时间
	 */
	public void setFdDateTime(String fdDateTime) {
		this.fdDateTime = fdDateTime;
	}

	/**
	 * 印章IP地址
	 */
	protected String fdHostName = null;

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
	protected String fdMarkGuid = null;

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
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdHistoryId = null;
		fdRecordId = null;
		fdFieldName = null;
		fdMarkName = null;
		fdUserName = null;
		fdDateTime = null;
		fdHostName = null;
		fdMarkGuid = null;

		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmSignatureDocumentHistory.class;
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
