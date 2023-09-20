package com.landray.kmss.sys.portal.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.portal.forms.SysPortalLinkDetailForm;

/**
 * 快捷方式内容
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalLinkDetail extends BaseModel {

	/**
	 * 链接名称
	 */
	protected String fdName;

	protected String fdSysLink;

	public String getFdSysLink() {
		return fdSysLink;
	}

	public void setFdSysLink(String fdSysLink) {
		this.fdSysLink = fdSysLink;
	}

	/**
	 * 链接地址
	 */
	protected String fdUrl;

	public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", this.fdName);
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
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
	 * 自定义图片url
	 */
	protected String fdImg;

	public void setFdImg(String fdImg) {
		this.fdImg = fdImg;
	}

	public String getFdImg() {
		return fdImg;
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

	protected SysPortalLink sysPortalLink;

	public SysPortalLink getSysPortalLink() {
		return sysPortalLink;
	}

	public void setSysPortalLink(SysPortalLink sysPortalLink) {
		this.sysPortalLink = sysPortalLink;
	}

	@Override
    public Class getFormClass() {
		return SysPortalLinkDetailForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("sysPortalLink.fdId", "sysPortalLinkId");
		}
		return toFormPropertyMap;
	}
}
