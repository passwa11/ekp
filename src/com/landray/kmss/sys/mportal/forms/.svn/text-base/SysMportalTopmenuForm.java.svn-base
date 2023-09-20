package com.landray.kmss.sys.mportal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.mportal.model.SysMportalTopmenu;



/**
 * 
 * @author 
 * @version 1.0 2015-11-13
 */
public class SysMportalTopmenuForm  extends ExtendForm  {

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
	private String fdOrder;
	
	/**
	 * @return 排序号
	 */
	public String getFdOrder() {
		return this.fdOrder;
	}
	
	/**
	 * @param fdOrder 排序号
	 */
	public void setFdOrder(String fdOrder) {
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
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdOrder = null;
		fdUrl = null;
		fdIcon = null;
		super.reset(mapping, request);
	}

	@Override
    public Class<SysMportalTopmenu> getModelClass() {
		return SysMportalTopmenu.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}
}
