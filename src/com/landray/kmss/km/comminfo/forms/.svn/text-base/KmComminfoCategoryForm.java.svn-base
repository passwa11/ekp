package com.landray.kmss.km.comminfo.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.comminfo.model.KmComminfoCategory;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 创建日期 2010-四月-29
 * 
 * @author 徐乃瑞
 */
public class KmComminfoCategoryForm extends ExtendForm {

	/**
	 * 类别名称
	 */
	private String fdName = null;

	/**
	 * 创建时间
	 */
	protected String docCreateTime = null;

	/**
	 * 创建者
	 */
	protected String docCreatorId = null;
	protected String docCreatorName = null;

	/**
	 * @return 返回 类别名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param docSubject
	 *            要设置的 类别名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/*
	 * 类别排序号
	 */
	protected java.lang.Integer fdOrder;

	/**
	 * @return 返回 类别排序号
	 */
	public java.lang.Integer getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            要设置的 类别排序号
	 */
	public void setFdOrder(java.lang.Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @seecom.landray.kmss.web.action.ActionForm#reset(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		docCreateTime = null;
		docCreatorName = null;
		authEditorIds = null;
		authEditorNames = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmComminfoCategory.class;
	}

	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getDocCreatorId() {
		return docCreatorId;
	}

	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
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

	// ================FormToModel转换开始===================
	private static FormToModelPropertyMap toModelPropertyMap = null;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			// 创建时间
			toModelPropertyMap.addNoConvertProperty("docCreateTime");
			toModelPropertyMap.addNoConvertProperty("docAlterTime");

			toModelPropertyMap.put("authEditorIds",
					new FormConvertor_IDsToModelList("authEditors",
							SysOrgElement.class));

		}
		return toModelPropertyMap;
	}
	// ================FormToModel转换结束=======================

}
