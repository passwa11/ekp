package com.landray.kmss.sys.portal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.portal.model.SysPortalMapInlet;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 快捷方式内容 Form
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalMapInletForm extends ExtendForm {
	/**
	 * 链接名称
	 */
	protected String fdName = null;

	/**
	 * 链接地址
	 */
	protected String fdUrl = null;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdUrl() {
		return fdUrl;
	}

	public void setFdUrl(String fdUrl) {
		this.fdUrl = fdUrl;
	}

	/**
	 * 连接窗口
	 */
	protected String fdTarget = null;

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
	protected String fdIcon = null;

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
	protected String fdOrder = null;

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
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdUrl = null;
		fdTarget = null;
		fdIcon = null;
		fdOrder = null;
		
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysPortalMapInlet.class;
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
