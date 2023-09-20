package com.landray.kmss.sys.zone.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.zone.forms.SysZonePersonAttenFanForm;

/**
 * 关注/粉丝信息
 * 
 * @author XuJieYang
 * @version 1.0 2014-08-28
 */
public class SysZonePersonAttenFan extends BaseModel {

	/**
	 * 关注/粉时间
	 */
	protected Date fdCreateTime;

	/**
	 * @return 关注/粉时间
	 */
	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	/**
	 * @param fdCreateTime
	 *            关注/粉时间
	 */
	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	/**
	 * 关注者组织ID
	 */
	protected SysOrgElement fdFromElement;

	/**
	 * @return 关注者组织ID
	 */
	public SysOrgElement getFdFromElement() {
		return fdFromElement;
	}

	/**
	 * @param fdFromElement
	 *            关注者组织ID
	 */
	public void setFdFromElement(SysOrgElement fdFromElement) {
		this.fdFromElement = fdFromElement;
	}

	/**
	 * 被关注者组织ID
	 */
	protected SysOrgElement fdToElement;

	/**
	 * @return 被关注者组织ID
	 */
	public SysOrgElement getFdToElement() {
		return fdToElement;
	}

	/**
	 * @param fdToElement
	 *            被关注者组织ID
	 */
	public void setFdToElement(SysOrgElement fdToElement) {
		this.fdToElement = fdToElement;
	}

	// 机制开始

	// 机制结束

	@Override
    public Class getFormClass() {
		return SysZonePersonAttenFanForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdFromElement.fdId", "fdFromElementId");
			toFormPropertyMap.put("fdFromElement.fdName", "fdFromElementName");
			toFormPropertyMap.put("fdToElement.fdId", "fdToElementId");
			toFormPropertyMap.put("fdToElement.fdName", "fdToElementName");
		}
		return toFormPropertyMap;
	}
}
