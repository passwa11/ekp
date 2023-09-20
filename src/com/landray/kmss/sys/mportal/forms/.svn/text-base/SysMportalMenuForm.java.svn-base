package com.landray.kmss.sys.mportal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.mportal.model.SysMportalMenu;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoArrayList;

/**
 * 菜单配置 Form
 * 
 * @author
 * @version 1.0 2015-10-08
 */
public class SysMportalMenuForm extends ExtendForm {

	/**
	 * 标题
	 */
	protected String docSubject = null;

	/**
	 * @return 标题
	 */
	public String getDocSubject() {
		return docSubject;
	}

	/**
	 * @param docSubject
	 *            标题
	 */
	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

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
	 * 更新时间
	 */
	protected String fdLastModifiedTime = null;

	/**
	 * @return 更新时间
	 */
	public String getFdLastModifiedTime() {
		return fdLastModifiedTime;
	}

	/**
	 * @param fdLastModifiedTime
	 *            更新时间
	 */
	public void setFdLastModifiedTime(String fdLastModifiedTime) {
		this.fdLastModifiedTime = fdLastModifiedTime;
	}

	/**
	 * 是否启用
	 */
	protected String fdEnable = null;

	/**
	 * @return 是否启用
	 */
	public String getFdEnable() {
		return fdEnable;
	}

	/**
	 * @param fdEnable
	 *            是否启用
	 */
	public void setFdEnable(String fdEnable) {
		this.fdEnable = fdEnable;
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
	
	
	private String fdEditorIds = null;
	private String fdEditorNames = null;

	public String getFdEditorIds() {
		return fdEditorIds;
	}

	public void setFdEditorIds(String fdEditorIds) {
		this.fdEditorIds = fdEditorIds;
	}

	public String getFdEditorNames() {
		return fdEditorNames;
	}

	public void setFdEditorNames(String fdEditorNames) {
		this.fdEditorNames = fdEditorNames;
	}
	
	/**
	 * 所属菜单配置的表单
	 */
	protected AutoArrayList fdSysMportalMenuItemForms = new AutoArrayList(
			SysMportalMenuItemForm.class);

	/**
	 * @return 所属菜单配置的表单
	 */
	public AutoArrayList getFdSysMportalMenuItemForms() {
		return fdSysMportalMenuItemForms;
	}

	/**
	 *            所属菜单配置的表单
	 */
	public void setFdSysMportalMenuItemForms(
			AutoArrayList fdSysMportalMenuItemForms) {
		this.fdSysMportalMenuItemForms = fdSysMportalMenuItemForms;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docSubject = null;
		docCreateTime = null;
		docAlterTime = null;
		fdLastModifiedTime = null;
		fdEnable = null;
		docCreatorId = null;
		docCreatorName = null;
		fdEditorIds = null;
		fdEditorNames = null;
		fdSysMportalMenuItemForms = new AutoArrayList(
				SysMportalMenuItemForm.class);

		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysMportalMenu.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdSysMportalMenuItemForms",
					new FormConvertor_FormListToModelList(
							"fdSysMportalMenuItems",
							"fdSysMportalMenu"));
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgPerson.class));
			toModelPropertyMap.put("fdEditorIds",
					new FormConvertor_IDsToModelList("fdEditors",
							SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
}
