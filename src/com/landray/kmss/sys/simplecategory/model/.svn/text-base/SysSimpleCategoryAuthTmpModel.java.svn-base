package com.landray.kmss.sys.simplecategory.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.IBaseTreeModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpModel;
import com.landray.kmss.sys.simplecategory.forms.SysSimpleCategoryAuthTmpForm;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ObjectUtil;

/**
 * 简单分类默认实现类
 * 
 * @author wubin
 * 
 */
public class SysSimpleCategoryAuthTmpModel extends ExtendAuthTmpModel implements
		ISysSimpleCategoryModel {

	@Override
    public Class getFormClass() {
		return SysSimpleCategoryAuthTmpForm.class;
	}

	/*
	 * 修改时间
	 */
	protected Date docAlterTime = new Date();

	/**
	 * @return 返回 修改时间
	 */
	@Override
    public Date getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            要设置的 修改时间
	 */
	@Override
    public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/*
	 * 最后修改人
	 */
	protected SysOrgPerson docAlteror = null;

	/**
	 * @return 返回 最后修改人
	 */
	@Override
    public SysOrgPerson getDocAlteror() {
		return docAlteror;
	}

	/**
	 * @param alteror
	 *            要设置的 最后修改人
	 */
	@Override
    public void setDocAlteror(SysOrgPerson alteror) {
		this.docAlteror = alteror;
	}

	/**
	 * 是否继承父类别可维护者。该设置项已经废除，强制返回true
	 */
	@Override
    @Deprecated
	public Boolean getFdIsinheritMaintainer() {
		return Boolean.TRUE;
	}

	@Override
    @Deprecated
	public void setFdIsinheritMaintainer(Boolean fdIsinheritMaintainer) {
	}

	/**
	 * 是否继承父类别可使用者，该设置项已经废除，强制返回false
	 */
	@Override
    @Deprecated
	public Boolean getFdIsinheritUser() {
		return Boolean.FALSE;
	}

	@Override
    @Deprecated
	public void setFdIsinheritUser(Boolean fdIsinheritUser) {
	}

	/*
	 * 类别名称
	 */
	protected String fdName;

	/**
	 * @return 返回 类别名称
	 */
	@Override
    public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", this.fdName);
	}

	/**
	 * @param fdName
	 *            要设置的 类别名称
	 */
	@Override
    public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}
	
	public String getFdNameOri() {
		return fdName;
	}

	public void setFdNameOri(String fdName) {
		this.fdName = fdName;
	}

	/*
	 * 类别描述
	 */
	protected String fdDesc;
	
	/**
	 * @return 返回 类别描述
	 */
	@Override
    public String getFdDesc() {
		return fdDesc;
	}

	/**
	 * @param fdMark
	 *            要设置的 类别描述
	 */
	@Override
    public void setFdDesc(String fdDesc) {
		this.fdDesc = fdDesc;
	}

	/*
	 * 类别排序号
	 */
	protected java.lang.Integer fdOrder;

	/**
	 * @return 返回 类别排序号
	 */
	@Override
    public java.lang.Integer getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            要设置的 类别排序号
	 */
	@Override
    public void setFdOrder(java.lang.Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	private IBaseTreeModel fdParent;

	@Override
    public IBaseTreeModel getFdParent() {
		return getHbmParent();
	}

	public void setFdParent(IBaseTreeModel parent) {
		if (!ObjectUtil.equals(getHbmParent(), parent)) {
			ModelUtil.checkTreeCycle(this, parent, "fdParent");
			setHbmParent(parent);
		}
	}

	@Override
    public IBaseTreeModel getHbmParent() {
		return fdParent;
	}

	@Override
    public void setHbmParent(IBaseTreeModel parent) {
		this.fdParent = parent;
	}

	/*
	 * 层级ID
	 */
	protected java.lang.String fdHierarchyId;

	/**
	 * @return 返回 层级ID
	 */
	@Override
    public java.lang.String getFdHierarchyId() {
		return fdHierarchyId;
	}

	/**
	 * @param fdHierarchyId
	 *            要设置的 层级ID
	 */
	@Override
    public void setFdHierarchyId(java.lang.String fdHierarchyId) {
		this.fdHierarchyId = fdHierarchyId;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdParent.fdId", "fdParentId");
			toFormPropertyMap.put("fdParent.fdName", "fdParentName");
			toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
		}
		return toFormPropertyMap;
	}
	
	protected Integer docAmount = 0;
	
	public Integer getDocAmount() {
		return docAmount;
	}
	
	public void setDocAmount(Integer docAmount) {
		this.docAmount = docAmount;
	}
	
	protected List<SysSimpleCategoryAuthTmpModel> tempList = new ArrayList<SysSimpleCategoryAuthTmpModel>();
	public List<SysSimpleCategoryAuthTmpModel> getTempList() {
		return tempList;
	}

	public void setTempList(List<SysSimpleCategoryAuthTmpModel> tempList) {
		this.tempList = tempList;
	}
}
