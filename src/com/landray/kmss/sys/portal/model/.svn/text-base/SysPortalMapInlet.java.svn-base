package com.landray.kmss.sys.portal.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.portal.forms.SysPortalMapInletForm;

/**
 * 快捷方式内容
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalMapInlet extends BaseModel {
	/**
	 * 所属链接
	 */
	protected SysPortalMapTpl fdMain;

	/**
	 * @return 所属链接
	 */
	public SysPortalMapTpl getFdMain() {
		return fdMain;
	}

	/**
	 * @param fdMain
	 *            所属链接
	 */
	public void setFdMain(SysPortalMapTpl fdMain) {
		this.fdMain = fdMain;
	}

	/**
	 * 链接名称
	 */
	protected String fdName;

	public String getFdName() {
		return this.fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 链接地址
	 */
	protected String fdUrl;

	public String getFdUrl() {
		return fdUrl;
	}

	public void setFdUrl(String fdUrl) {
		this.fdUrl = fdUrl;
	}

	/**
	 * 连接窗口
	 */
	protected String fdTarget;

	/**
	 * @return 连接窗口
	 */
	public String getFdTarget() {
		return fdTarget;
	}

	/**
	 * @param fdTarget
	 *            连接窗口
	 */
	public void setFdTarget(String fdTarget) {
		this.fdTarget = fdTarget;
	}

	/**
	 * 连接图标
	 */
	protected String fdIcon;

	/**
	 * @return 连接图标
	 */
	public String getFdIcon() {
		return fdIcon;
	}

	/**
	 * @param fdIcon
	 *            连接图标
	 */
	public void setFdIcon(String fdIcon) {
		this.fdIcon = fdIcon;
	}

	/**
	 * 排序
	 */
	protected String fdOrder;

	/**
	 * @return 排序
	 */
	public String getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序
	 */
	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	@Override
    public Class getFormClass() {
		return SysPortalMapInletForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdMain.fdId", "fdMainId");
		}
		return toFormPropertyMap;
	}
}
