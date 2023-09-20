package com.landray.kmss.sys.mportal.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.mportal.forms.SysMportalTopmenuForm;



/**
 * @author 
 * @version 1.0 2015-11-13
 */
public class SysMportalTopmenu  extends BaseModel {

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
	 * @param fdName 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	
	/**
	 * 排序号
	 */
	private Integer fdOrder;
	
	/**
	 * @return 排序号
	 */
	public Integer getFdOrder() {
		return this.fdOrder;
	}
	
	/**
	 * @param fdOrder 排序号
	 */
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}
	
	/**
	 * 链接
	 */
	private String fdUrl;
	
	/**
	 * @return 链接
	 */
	public String getFdUrl() {
		return this.fdUrl;
	}
	
	/**
	 * @param fdUrl 链接
	 */
	public void setFdUrl(String fdUrl) {
		this.fdUrl = fdUrl;
	}
	
	/**
	 * 图标
	 */
	private String fdIcon;
	
	/**
	 * @return 图标
	 */
	public String getFdIcon() {
		return this.fdIcon;
	}
	
	/**
	 * @param fdIcon 图标
	 */
	public void setFdIcon(String fdIcon) {
		this.fdIcon = fdIcon;
	}
	

	//机制开始
	//机制结束

	@Override
    public Class<SysMportalTopmenuForm> getFormClass() {
		return SysMportalTopmenuForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}
}
