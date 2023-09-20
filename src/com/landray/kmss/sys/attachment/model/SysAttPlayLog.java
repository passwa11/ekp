package com.landray.kmss.sys.attachment.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.SysAttPlayLogForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
 * 附件播放日志
 */
public class SysAttPlayLog extends BaseModel {

	private static final long serialVersionUID = 1L;

	private static ModelToFormPropertyMap toFormPropertyMap;

	private Date docCreateTime;

	private Date docAlterTime = new Date();

	private String fdAttId;

	private String fdParam;

	private String fdName;

	private String fdType;

	private SysOrgPerson docCreator;

	@Override
    public Class<SysAttPlayLogForm> getFormClass() {
		return SysAttPlayLogForm.class;
	}

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreateTime",
					new ModelConvertor_Common("docCreateTime")
							.setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.put("docAlterTime",
					new ModelConvertor_Common("docAlterTime")
							.setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
		}
		return toFormPropertyMap;
	}

	@Override
    public void recalculateFields() {
		super.recalculateFields();
	}

	/**
	 * 文件类型
	 * @return
	 */
	public String getFdType() {
		return fdType;
	}

	/**
	 * 文件类型
	 * @param fdType
	 */
	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	/**
	 * 文件名
	 * 
	 * @return
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * 文件名
	 * 
	 * @param fdName
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 创建时间
	 */
	public Date getDocCreateTime() {
		return this.docCreateTime;
	}

	/**
	 * 创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 更新时间
	 */
	public Date getDocAlterTime() {
		return this.docAlterTime;
	}

	/**
	 * 更新时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
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
	public SysOrgPerson getDocCreator() {
		return this.docCreator;
	}

	/**
	 * 创建人
	 */
	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}
}
