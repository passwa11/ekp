package com.landray.kmss.sys.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
 * 职级配置 Form
 * 
 * @author
 * @version 1.0 2015-07-23
 */
public class SysOrganizationStaffingLevelForm extends ExtendForm {

	/**
	 * 创建时间
	 */
	protected String docCreateTime = null;

	/**
	 * @return 创建时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 最后修改时间
	 */
	protected String docAlterTime = null;

	/**
	 * @return 最后修改时间
	 */
	public String getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            最后修改时间
	 */
	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 描述
	 */
	protected String fdDescription = null;

	/**
	 * @return 描述
	 */
	public String getFdDescription() {
		return fdDescription;
	}

	/**
	 * @param fdDescription
	 *            描述
	 */
	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	/**
	 * 名称
	 */
	protected String fdName = null;

	/**
	 * @return 名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 级别
	 */
	protected String fdLevel = null;

	/**
	 * @return 级别
	 */
	public String getFdLevel() {
		return fdLevel;
	}

	/**
	 * @param fdLevel
	 *            级别
	 */
	public void setFdLevel(String fdLevel) {
		this.fdLevel = fdLevel;
	}

	/**
	 * 创建者的ID
	 */
	protected String docCreatorId = null;

	/**
	 * @return 创建者的ID
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}

	/**
	 * @param docCreatorId
	 *            创建者的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * 导入的数据的对应键值
	 */
	protected String fdImportInfo = null;

	public String getFdImportInfo() {
		return fdImportInfo;
	}

	public void setFdImportInfo(String fdImportInfo) {
		this.fdImportInfo = fdImportInfo;
	}

	/**
	 * @return 创建者的名称
	 */
	public String getDocCreatorName() {
		return docCreatorName;
	}

	/**
	 * @param docCreatorName
	 *            创建者的名称
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	/**
	 * 创建者的名称
	 */
	protected String docCreatorName = null;

	private String fdPersonIds;

	private String fdPersonNames;

	public String getFdPersonIds() {
		return fdPersonIds;
	}

	public void setFdPersonIds(String fdPersonIds) {
		this.fdPersonIds = fdPersonIds;
	}

	public String getFdPersonNames() {
		return fdPersonNames;
	}

	public void setFdPersonNames(String fdPersonNames) {
		this.fdPersonNames = fdPersonNames;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docCreateTime = null;
		docAlterTime = null;
		fdDescription = null;
		fdName = null;
		fdLevel = null;
		docCreatorId = null;
		docCreatorName = null;
		fdPersonIds = null;
		fdPersonNames = null;
		fdImportInfo = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysOrganizationStaffingLevel.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgElement.class));
			toModelPropertyMap.put("fdPersonIds",
					new FormConvertor_IDsToModelList("fdPersons",
							SysOrgPerson.class));
		}
		return toModelPropertyMap;
	}

	/**
	 * 是否为默认
	 */
	protected String fdIsDefault = null;

	/**
	 * @return 是否为默认
	 */
	public String getFdIsDefault() {
		return fdIsDefault;
	}

	/**
	 * @param fdIsDefault
	 *            是否为默认
	 */
	public void setFdIsDefault(String fdIsDefault) {
		this.fdIsDefault = fdIsDefault;
	}

	// 上传的文件
	private FormFile file;

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

}
