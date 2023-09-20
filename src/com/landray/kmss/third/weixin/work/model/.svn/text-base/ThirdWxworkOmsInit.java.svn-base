package com.landray.kmss.third.weixin.work.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.weixin.forms.ThirdWxOmsInitForm;

/**
 * 组织初始化
 * 
 * @author
 * @version 1.0 2017-06-08
 */
public class ThirdWxworkOmsInit extends BaseModel {

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
	 * @param fdName
	 *            名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 路径
	 */
	private String fdPath;

	/**
	 * @return 路径
	 */
	public String getFdPath() {
		return this.fdPath;
	}

	/**
	 * @param fdPath
	 *            路径
	 */
	public void setFdPath(String fdPath) {
		this.fdPath = fdPath;
	}

	/**
	 * 微信ID
	 */
	private String fdWeixinId;

	/**
	 * @return 微信ID
	 */
	public String getFdWeixinId() {
		return this.fdWeixinId;
	}

	/**
	 * @param fdWeixinId
	 *            微信ID
	 */
	public void setFdWeixinId(String fdWeixinId) {
		this.fdWeixinId = fdWeixinId;
	}

	/**
	 * 微信处理状态
	 */
	private String fdWxStatus;

	/**
	 * @return 微信处理状态
	 */
	public String getFdWxStatus() {
		return this.fdWxStatus;
	}

	/**
	 * @param fdWxStatus
	 *            微信处理状态
	 */
	public void setFdWxStatus(String fdWxStatus) {
		this.fdWxStatus = fdWxStatus;
	}

	/**
	 * EKP处理状态
	 */
	private String fdEkpStatus;

	/**
	 * @return EKP处理状态
	 */
	public String getFdEkpStatus() {
		return this.fdEkpStatus;
	}

	/**
	 * @param fdEkpStatus
	 *            EKP处理状态
	 */
	public void setFdEkpStatus(String fdEkpStatus) {
		this.fdEkpStatus = fdEkpStatus;
	}

	/**
	 * 是否微信企业号
	 */
	private Boolean fdIsWx;

	/**
	 * @return 是否微信企业号
	 */
	public Boolean getFdIsWx() {
		return this.fdIsWx;
	}

	/**
	 * @param fdIsWx
	 *            是否微信企业号
	 */
	public void setFdIsWx(Boolean fdIsWx) {
		this.fdIsWx = fdIsWx;
	}

	/**
	 * 是否部门
	 */
	private Boolean fdIsOrg;

	/**
	 * @return 是否部门
	 */
	public Boolean getFdIsOrg() {
		return this.fdIsOrg;
	}

	/**
	 * @param fdIsOrg
	 *            是否部门
	 */
	public void setFdIsOrg(Boolean fdIsOrg) {
		this.fdIsOrg = fdIsOrg;
	}

	/**
	 * 状态
	 */
	private String fdStatus;

	/**
	 * @return 状态
	 */
	public String getFdStatus() {
		return this.fdStatus;
	}

	/**
	 * @param fdStatus
	 *            状态
	 */
	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	/**
	 * 处理状态
	 */
	private String fdHandleStatus;

	/**
	 * @return 处理状态
	 */
	public String getFdHandleStatus() {
		return this.fdHandleStatus;
	}

	/**
	 * @param fdHandleStatus
	 *            处理状态
	 */
	public void setFdHandleStatus(String fdHandleStatus) {
		this.fdHandleStatus = fdHandleStatus;
	}

	/**
	 * 映射组织
	 */
	private SysOrgElement fdEkp;

	/**
	 * @return 映射组织
	 */
	public SysOrgElement getFdEkp() {
		return this.fdEkp;
	}

	/**
	 * @param fdEkp
	 *            映射组织
	 */
	public void setFdEkp(SysOrgElement fdEkp) {
		this.fdEkp = fdEkp;
	}

	// 机制开始
	// 机制结束

	@Override
    public Class<ThirdWxOmsInitForm> getFormClass() {
		return ThirdWxOmsInitForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdEkp.fdId", "fdEkpId");
			toFormPropertyMap.put("fdEkp.fdName", "fdEkpName");
		}
		return toFormPropertyMap;
	}
}
