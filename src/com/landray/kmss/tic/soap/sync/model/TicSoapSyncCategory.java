package com.landray.kmss.tic.soap.sync.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseTreeModel;
import com.landray.kmss.tic.soap.sync.forms.TicSoapSyncCategoryForm;

/**
 * 配置/分类信息
 * 
 * @author 
 * @version 1.0 2014-02-20
 */
public class TicSoapSyncCategory extends BaseTreeModel {

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
	
	
	@Override
    public Class getFormClass() {
		return TicSoapSyncCategoryForm.class;
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
