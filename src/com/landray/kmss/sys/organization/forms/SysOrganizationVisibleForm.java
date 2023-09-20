package com.landray.kmss.sys.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrganizationVisible;

/**
 * 组织可见性 Form
 * 
 * @author
 * @version 1.0 2015-06-16
 */
public class SysOrganizationVisibleForm extends ExtendForm {

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
	 * 主组织的ID列表
	 */
	protected String visiblePrincipalIds = null;

	/**
	 * @return 主组织的ID列表
	 */
	public String getVisiblePrincipalIds() {
		return visiblePrincipalIds;
	}

	/**
	 * @param visiblePrincipalIds
	 *            主组织的ID列表
	 */
	public void setVisiblePrincipalIds(String visiblePrincipalIds) {
		this.visiblePrincipalIds = visiblePrincipalIds;
	}

	/**
	 * 主组织的名称列表
	 */
	protected String visiblePrincipalNames = null;

	/**
	 * @return 主组织的名称列表
	 */
	public String getVisiblePrincipalNames() {
		return visiblePrincipalNames;
	}

	/**
	 * @param visiblePrincipalNames
	 *            主组织的名称列表
	 */
	public void setVisiblePrincipalNames(String visiblePrincipalNames) {
		this.visiblePrincipalNames = visiblePrincipalNames;
	}

	/**
	 * 可见组织的ID列表
	 */
	protected String visibleSubordinateIds = null;

	/**
	 * @return 可见组织的ID列表
	 */
	public String getVisibleSubordinateIds() {
		return visibleSubordinateIds;
	}

	/**
	 * @param visibleSubordinateIds
	 *            可见组织的ID列表
	 */
	public void setVisibleSubordinateIds(String visibleSubordinateIds) {
		this.visibleSubordinateIds = visibleSubordinateIds;
	}

	/**
	 * 可见组织的名称列表
	 */
	protected String visibleSubordinateNames = null;

	/**
	 * @return 可见组织的名称列表
	 */
	public String getVisibleSubordinateNames() {
		return visibleSubordinateNames;
	}

	/**
	 * @param visibleSubordinateNames
	 *            可见组织的名称列表
	 */
	public void setVisibleSubordinateNames(String visibleSubordinateNames) {
		this.visibleSubordinateNames = visibleSubordinateNames;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docCreateTime = null;
		docAlterTime = null;
		fdDescription = null;
		visiblePrincipalIds = null;
		visiblePrincipalNames = null;
		visibleSubordinateIds = null;
		visibleSubordinateNames = null;

		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysOrganizationVisible.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("visiblePrincipalIds",
					new FormConvertor_IDsToModelList("visiblePrincipals",
							SysOrgElement.class));
			toModelPropertyMap.put("visibleSubordinateIds",
					new FormConvertor_IDsToModelList("visibleSubordinates",
							SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
}
