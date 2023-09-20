package com.landray.kmss.tic.soap.connector.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseTreeModel;
import com.landray.kmss.tic.soap.connector.forms.TicSoapCategoryForm;

/**
 * WS服务分类
 * 
 * @author 
 * @version 1.0 2012-08-06
 */
@SuppressWarnings("serial")
public class TicSoapCategory extends BaseTreeModel {

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
//	
//	/**
//	 * 上级分类
//	 */
//	protected TicSoapCategory fdParent;
//	
//	/**
//	 * @return 上级分类
//	 */
//	public TicSoapCategory getFdParent() {
//		return fdParent;
//	}
//	
//	/**
//	 * @param fdParent 上级分类
//	 */
//	public void setFdParent(TicSoapCategory fdParent) {
//		this.fdParent = fdParent;
//	}
	
	@Override
    public Class getFormClass() {
		return TicSoapCategoryForm.class;
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
