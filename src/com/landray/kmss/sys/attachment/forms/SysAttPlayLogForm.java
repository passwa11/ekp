package com.landray.kmss.sys.attachment.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.model.SysAttPlayLog;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 附件播放日志
 */
public class SysAttPlayLogForm extends ExtendForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static FormToModelPropertyMap toModelPropertyMap;

	private String docCreateTime;

	private String docAlterTime;

	private String fdAttId;

	private String fdParam;

	private String docCreatorId;

	private String docCreatorName;

	private String fdType;

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docCreateTime = null;
		docAlterTime = null;
		fdAttId = null;
		fdParam = null;
		docCreatorId = null;
		docCreatorName = null;
		fdType = null;
		super.reset(mapping, request);
	}

	@Override
    public Class<SysAttPlayLog> getModelClass() {
		return SysAttPlayLog.class;
	}

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.addNoConvertProperty("docCreateTime");
			toModelPropertyMap.addNoConvertProperty("docAlterTime");
		}
		return toModelPropertyMap;
	}

	/**
	 * 文件类型
	 * 
	 * @return
	 */
	public String getFdType() {
		return fdType;
	}

	/**
	 * 文件类型
	 * 
	 * @param fdType
	 */
	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	/**
	 * 创建时间
	 */
	public String getDocCreateTime() {
		return this.docCreateTime;
	}

	/**
	 * 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 更新时间
	 */
	public String getDocAlterTime() {
		return this.docAlterTime;
	}

	/**
	 * 更新时间
	 */
	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 附件主键
	 */
	public String getFdAttId() {
		return this.fdAttId;
	}

	/**
	 * 附件主键
	 */
	public void setFdAttId(String fdAttId) {
		this.fdAttId = fdAttId;
	}

	/**
	 * 相关参数
	 */
	public String getFdParam() {
		return this.fdParam;
	}

	/**
	 * 相关参数
	 */
	public void setFdParam(String fdParam) {
		this.fdParam = fdParam;
	}

	/**
	 * 创建人
	 */
	public String getDocCreatorId() {
		return this.docCreatorId;
	}

	/**
	 * 创建人
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * 创建人
	 */
	public String getDocCreatorName() {
		return this.docCreatorName;
	}

	/**
	 * 创建人
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}
}
