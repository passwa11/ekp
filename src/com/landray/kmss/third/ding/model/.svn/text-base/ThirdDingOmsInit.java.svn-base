package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.ding.forms.ThirdDingOmsInitForm;

/**
 * 组织初始化
 * 
 * @author
 * @version 1.0 2017-06-14
 */
public class ThirdDingOmsInit extends BaseModel {

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
	 * 钉钉ID
	 */
	private String fdDingId;

	/**
	 * @return 钉钉ID
	 */
	public String getFdDingId() {
		return this.fdDingId;
	}

	/**
	 * @param fdDingId
	 *            钉钉ID
	 */
	public void setFdDingId(String fdDingId) {
		this.fdDingId = fdDingId;
	}

	/**
	 * 钉钉处理状态
	 */
	private String fdDingStatus;

	/**
	 * @return 钉钉处理状态
	 */
	public String getFdDingStatus() {
		return this.fdDingStatus;
	}

	/**
	 * @param fdDingStatus
	 *            钉钉处理状态
	 */
	public void setFdDingStatus(String fdDingStatus) {
		this.fdDingStatus = fdDingStatus;
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
	 * EKP映射组织
	 */
	private SysOrgElement fdEkp;

	/**
	 * @return EKP映射组织
	 */
	public SysOrgElement getFdEkp() {
		return this.fdEkp;
	}

	/**
	 * @param fdEkp
	 *            EKP映射组织
	 */
	public void setFdEkp(SysOrgElement fdEkp) {
		this.fdEkp = fdEkp;
	}

	/**
	 * 钉钉账号类型
	 */
	private String fdAccountType;

	public String getFdAccountType() {
		return fdAccountType;
	}

	public void setFdAccountType(String fdAccountType) {
		this.fdAccountType = fdAccountType;
	}

	// 机制开始
	// 机制结束

	@Override
    public Class<ThirdDingOmsInitForm> getFormClass() {
		return ThirdDingOmsInitForm.class;
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
