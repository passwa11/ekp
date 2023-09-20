package com.landray.kmss.tic.jdbc.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseTreeModel;
import com.landray.kmss.tic.jdbc.forms.TicJdbcMappCategoryForm;

/**
 * 映射分类
 * 
 * @author 
 * @version 1.0 2013-07-24
 */
public class TicJdbcMappCategory extends BaseTreeModel {

	/**
	 * 名称
	 */
	protected String fdName;
	
	/**
	 * @return 名称
	 */
	public String getFdName() {
		return fdName;
	}
	
	/**
	 * @param fdName 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	
/*	*//**
	 * 层级ID
	 *//*
	protected String fdHierarchyId;
	
	*//**
	 * @return 层级ID
	 *//*
	public String getFdHierarchyId() {
		return fdHierarchyId;
	}
	
	*//**
	 * @param fdHierarchyId 层级ID
	 *//*
	public void setFdHierarchyId(String fdHierarchyId) {
		this.fdHierarchyId = fdHierarchyId;
	}*/
	
	/**
	 * 排序号
	 */
	protected Integer fdOrder;
	
	/**
	 * @return 排序号
	 */
	public Integer getFdOrder() {
		return fdOrder;
	}
	
	/**
	 * @param fdOrder 排序号
	 */
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}
/*	
	*//**
	 * 上级分类
	 *//*
	protected TicJdbcMappCategory fdParent;
	
	*//**
	 * @return 上级分类
	 *//*
	public TicJdbcMappCategory getFdParent() {
		return fdParent;
	}
	
	*//**
	 * @param fdParent 上级分类
	 *//*
	public void setFdParent(TicJdbcMappCategory fdParent) {
		this.fdParent = fdParent;
	}*/
	
	@Override
    public Class getFormClass() {
		return TicJdbcMappCategoryForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdParent.fdId", "fdParentId");
			toFormPropertyMap.put("fdParent.fdName", "fdParentName");
		}
		return toFormPropertyMap;
	}
}
