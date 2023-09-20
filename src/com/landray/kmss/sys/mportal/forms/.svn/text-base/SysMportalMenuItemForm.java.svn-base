package com.landray.kmss.sys.mportal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.mportal.model.SysMportalMenu;
import com.landray.kmss.sys.mportal.model.SysMportalMenuItem;

/**
 * 菜单配置项 Form
 * 
 * @author
 * @version 1.0 2015-10-08
 */
public class SysMportalMenuItemForm extends ExtendForm {
	
	/**
	 * 图标类型
	 */
	protected String fdIconType = null;
	
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
	protected String fdIcon = null;

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
	protected String fdUrl = null;

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
	protected String fdName = null;

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
	 * 所属菜单配置的ID
	 */
	protected String fdSysMportalMenuId = null;

	/**
	 * @return 所属菜单配置的ID
	 */
	public String getFdSysMportalMenuId() {
		return fdSysMportalMenuId;
	}

	/**
	 *            所属菜单配置的ID
	 */
	public void setFdSysMportalMenuId(String fdSysMportalMenuId) {
		this.fdSysMportalMenuId = fdSysMportalMenuId;
	}

	/**
	 * 所属菜单配置的名称
	 */
	protected String fdSysMportalMenuName = null;

	/**
	 * @return 所属菜单配置的名称
	 */
	public String getFdSysMportalMenuName() {
		return fdSysMportalMenuName;
	}

	/**
	 *            所属菜单配置的名称
	 */
	public void setFdSysMportalMenuName(
			String fdSysMportalMenuName) {
		this.fdSysMportalMenuName = fdSysMportalMenuName;
	}

	/**
	 * 排序号
	 */
	protected Integer fdOrder;

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdIconType = null;
		fdIcon = null;
		fdUrl = null;
		fdName = null;
		fdSysMportalMenuId = null;
		fdSysMportalMenuName = null;
		fdOrder = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysMportalMenuItem.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdSysMportalMenuId",
					new FormConvertor_IDToModel("fdSysMportalMenu",
							SysMportalMenu.class));
		}
		return toModelPropertyMap;
	}
}
