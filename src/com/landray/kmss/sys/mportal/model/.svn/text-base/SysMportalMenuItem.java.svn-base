package com.landray.kmss.sys.mportal.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.mportal.forms.SysMportalMenuItemForm;

/**
 * 菜单配置项
 * 
 * @author
 * @version 1.0 2015-10-08
 */
public class SysMportalMenuItem extends BaseModel {
	/**
	 * 图标
	 */
	protected String fdIconType;
	/**
	 * @return 图标类型
	 */
	public String getFdIconType() {
		return fdIconType;
	}

	/**
	 * @param fdIconType 图标类型
	 *            
	 */
	public void setFdIconType(String fdIconType) {
		this.fdIconType = fdIconType;
	}	

	/**
	 * 图标
	 */
	protected String fdIcon;

	/**
	 * @return 图标
	 */
	public String getFdIcon() {
		return fdIcon;
	}

	/**
	 * @param fdIcon
	 *            图标
	 */
	public void setFdIcon(String fdIcon) {
		this.fdIcon = fdIcon;
	}

	/**
	 * 链接
	 */
	protected String fdUrl;

	/**
	 * @return 链接
	 */
	public String getFdUrl() {
		return fdUrl;
	}

	/**
	 * @param fdUrl
	 *            链接
	 */
	public void setFdUrl(String fdUrl) {
		this.fdUrl = fdUrl;
	}

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
	 * @param fdName
	 *            名称
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
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 所属菜单配置
	 */
	protected SysMportalMenu fdSysMportalMenu;

	/**
	 * @return 所属菜单配置
	 */
	public SysMportalMenu getFdSysMportalMenu() {
		return fdSysMportalMenu;
	}

	/**
	 *            所属菜单配置
	 */
	public void setFdSysMportalMenu(
			SysMportalMenu fdSysMportalMenu) {
		this.fdSysMportalMenu = fdSysMportalMenu;
	}

	@Override
    public Class getFormClass() {
		return SysMportalMenuItemForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdSysMportalMenu.fdId",
					"fdSysMportalMenuId");
			toFormPropertyMap.put("fdSysMportalMenu.docSubject",
					"fdSysMportalMenuName");
		}
		return toFormPropertyMap;
	}
}
