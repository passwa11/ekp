package com.landray.kmss.sys.mportal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.mportal.model.SysMportalCard;
import com.landray.kmss.sys.mportal.model.SysMportalModuleCate;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 文档类 Form
 * 
 * @author
 * @version 1.0 2015-09-14
 */
public class SysMportalCardForm extends ExtendForm {

	/**
	 * 名称
	 */
	private String fdName;

	/**
	 * @return 名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	/**
	 * @param fdName
	 *            名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 类型，是否多页签
	 */
	private Boolean fdType = false;

	public Boolean getFdType() {
		return fdType;
	}

	public void setFdType(Boolean fdType) {
		this.fdType = fdType;
	}

	/**
	 * 创建时间
	 */
	private String docCreateTime;

	/**
	 * @return 创建时间
	 */
	public String getDocCreateTime() {
		return this.docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/*
	 * 可编辑者
	 */
	protected String authEditorIds = null;

	public String getAuthEditorIds() {
		return authEditorIds;
	}

	public void setAuthEditorIds(String authEditorIds) {
		this.authEditorIds = authEditorIds;
	}

	/*
	 * 可编辑者名称
	 */
	protected String authEditorNames = null;

	public String getAuthEditorNames() {
		return authEditorNames;
	}

	public void setAuthEditorNames(String authEditorNames) {
		this.authEditorNames = authEditorNames;
	}

	/**
	 * 最后修改时间
	 */
	private String docAlterTime;

	/**
	 * @return 最后修改时间
	 */
	public String getDocAlterTime() {
		return this.docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            最后修改时间
	 */
	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 是否启用
	 */
	private String fdEnabled;

	/**
	 * @return 是否启用
	 */
	public String getFdEnabled() {
		return this.fdEnabled;
	}

	/**
	 * @param fdEnabled
	 *            是否启用
	 */
	public void setFdEnabled(String fdEnabled) {
		this.fdEnabled = fdEnabled;
	}

	/**
	 * 排序号
	 */
	private String fdOrder;

	/**
	 * @return 排序号
	 */
	public String getFdOrder() {
		return this.fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 创建者的ID
	 */
	private String docCreatorId;

	/**
	 * @return 创建者的ID
	 */
	public String getDocCreatorId() {
		return this.docCreatorId;
	}

	/**
	 * @param docCreatorId
	 *            创建者的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * 创建者的名称
	 */
	private String docCreatorName;

	/**
	 * @return 创建者的名称
	 */
	public String getDocCreatorName() {
		return this.docCreatorName;
	}

	/**
	 * @param docCreatorName
	 *            创建者的名称
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}


	private String fdIsPushed;

	public String getFdIsPushed() {
		return fdIsPushed;
	}

	public void setFdIsPushed(String fdIsPushed) {
		this.fdIsPushed = fdIsPushed;
	}

	private String fdPortletConfig;

	public String getFdPortletConfig() {
		return fdPortletConfig;
	}

	public void setFdPortletConfig(String fdPortletConfig) {
		this.fdPortletConfig = fdPortletConfig;
	}

	/*
	 * 模块分类
	 */
	private String fdModuleCateId = null;

	public String getFdModuleCateId() {
		return fdModuleCateId;
	}

	public void setFdModuleCateId(String fdModuleCateId) {
		this.fdModuleCateId = fdModuleCateId;
	}

	public String getFdModuleCateName() {
		return fdModuleCateName;
	}

	public void setFdModuleCateName(String fdModuleCateName) {
		this.fdModuleCateName = fdModuleCateName;
	}

	private String fdModuleCateName = null;

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		docCreateTime = null;
		docAlterTime = null;
		fdEnabled = null;
		fdOrder = null;
		docCreatorId = null;
		docCreatorName = null;
		fdIsPushed = null;
		fdPortletConfig = null;
		fdType = false;
		fdModuleCateId = null;
		fdModuleCateName = null;

		super.reset(mapping, request);
	}

	@Override
    public Class<SysMportalCard> getModelClass() {
		return SysMportalCard.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgPerson.class));
			toModelPropertyMap.put("authEditorIds",
					new FormConvertor_IDsToModelList("authEditors",
							SysOrgElement.class));
			toModelPropertyMap.put("fdModuleCateId",
					new FormConvertor_IDToModel("fdModuleCate",
							SysMportalModuleCate.class));
		}
		return toModelPropertyMap;
	}
}
