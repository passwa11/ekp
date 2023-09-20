package com.landray.kmss.sys.organization.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.forms.SysOrganizationVisibleForm;

/**
 * 组织可见性
 * 
 * @author
 * @version 1.0 2015-06-16
 */
public class SysOrganizationVisible extends BaseModel {

	/**
	 * 创建时间
	 */
	protected Date docCreateTime;

	/**
	 * @return 创建时间
	 */
	public Date getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 最后修改时间
	 */
	protected Date docAlterTime;

	/**
	 * @return 最后修改时间
	 */
	public Date getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            最后修改时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 描述
	 */
	protected String fdDescription;

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
	 * 主组织
	 */
	protected List<SysOrgElement> visiblePrincipals;

	/**
	 * @return 主组织
	 */
	public List<SysOrgElement> getVisiblePrincipals() {
		return visiblePrincipals;
	}

	/**
	 * @param visiblePrincipals
	 *            主组织
	 */
	public void setVisiblePrincipals(List<SysOrgElement> visiblePrincipals) {
		this.visiblePrincipals = visiblePrincipals;
	}

	/**
	 * 可见组织
	 */
	protected List<SysOrgElement> visibleSubordinates;

	/**
	 * @return 可见组织
	 */
	public List<SysOrgElement> getVisibleSubordinates() {
		return visibleSubordinates;
	}

	/**
	 * @param visibleSubordinates
	 *            可见组织
	 */
	public void setVisibleSubordinates(List<SysOrgElement> visibleSubordinates) {
		this.visibleSubordinates = visibleSubordinates;
	}

	@Override
    public Class getFormClass() {
		return SysOrganizationVisibleForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("visiblePrincipals",
					new ModelConvertor_ModelListToString(
							"visiblePrincipalIds:visiblePrincipalNames",
							"fdId:fdName"));
			toFormPropertyMap.put("visibleSubordinates",
					new ModelConvertor_ModelListToString(
							"visibleSubordinateIds:visibleSubordinateNames",
							"fdId:fdName"));
		}
		return toFormPropertyMap;
	}
}
