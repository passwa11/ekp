package com.landray.kmss.sys.oms.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;

public class SysOmsElement extends BaseModel {

	private String fdName;

	private Boolean fdIsAvailable;

	private String fdShortName;

	private String fdNo;

	private String fdKeyword;

	private String fdImportinfo;

	private Boolean fdIsBusiness;

	private String fdMemo;

	private String fdParent;

	private Integer fdRecordStatus;

	private Date fdCreateTime;

	private Date fdAlterTime;

	private String fdLdapDn;

	private String fdDynamicMap;

	private String fdCustomMap;

	private String fdCreator;

	private String fdOrgEmail;

	private Integer fdOrder;

	private Integer fdHandleStatus;

	private Boolean fdIsExternal;

	@Override
	public Class getFormClass() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	/**
	 * 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 是否有效
	 */
	public Boolean getFdIsAvailable() {
		return this.fdIsAvailable;
	}

	/**
	 * 是否有效
	 */
	public void setFdIsAvailable(Boolean fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	/**
	 * 简称
	 */
	public String getFdShortName() {
		return this.fdShortName;
	}

	/**
	 * 简称
	 */
	public void setFdShortName(String fdShortName) {
		this.fdShortName = fdShortName;
	}

	/**
	 * 编号
	 */
	public String getFdNo() {
		return this.fdNo;
	}

	/**
	 * 编号
	 */
	public void setFdNo(String fdNo) {
		this.fdNo = fdNo;
	}

	/**
	 * 关键字
	 */
	public String getFdKeyword() {
		return this.fdKeyword;
	}

	/**
	 * 关键字
	 */
	public void setFdKeyword(String fdKeyword) {
		this.fdKeyword = fdKeyword;
	}

	/**
	 * 映射关系
	 */
	public String getFdImportinfo() {
		return this.fdImportinfo;
	}

	/**
	 * 映射关系
	 */
	public void setFdImportinfo(String fdImportinfo) {
		this.fdImportinfo = fdImportinfo;
	}

	/**
	 * 是否业务相关
	 */
	public Boolean getFdIsBusiness() {
		return this.fdIsBusiness;
	}

	/**
	 * 是否业务相关
	 */
	public void setFdIsBusiness(Boolean fdIsBusiness) {
		this.fdIsBusiness = fdIsBusiness;
	}

	/**
	 * 备注
	 */
	public String getFdMemo() {
		return this.fdMemo;
	}

	/**
	 * 备注
	 */
	public void setFdMemo(String fdMemo) {
		this.fdMemo = fdMemo;
	}

	/**
	 * 上级部门
	 */
	public String getFdParent() {
		return this.fdParent;
	}

	/**
	 * 上级部门
	 */
	public void setFdParent(String fdParent) {
		this.fdParent = fdParent;
	}

	/**
	 * 更新状态
	 */
	public Integer getFdRecordStatus() {
		return this.fdRecordStatus;
	}

	/**
	 * 更新状态
	 */
	public void setFdRecordStatus(Integer fdRecordStatus) {
		this.fdRecordStatus = fdRecordStatus;
	}

	/**
	 * 创建时间
	 */
	public Date getFdCreateTime() {
		return this.fdCreateTime;
	}

	/**
	 * 创建时间
	 */
	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	/**
	 * 更新时间
	 */
	public Date getFdAlterTime() {
		return this.fdAlterTime;
	}

	/**
	 * 更新时间
	 */
	public void setFdAlterTime(Date fdAlterTime) {
		this.fdAlterTime = fdAlterTime;
	}

	/**
	 * LDAP DN
	 */
	public String getFdLdapDn() {
		return this.fdLdapDn;
	}

	/**
	 * LDAP DN
	 */
	public void setFdLdapDn(String fdLdapDn) {
		this.fdLdapDn = fdLdapDn;
	}

	/**
	 * 动态参数
	 */
	public String getFdDynamicMap() {
		return (String) readLazyField("fdDynamicMap", this.fdDynamicMap);
	}

	/**
	 * 动态参数
	 */
	public void setFdDynamicMap(String fdDynamicMap) {
		this.fdDynamicMap = (String) writeLazyField("fdDynamicMap",
				this.fdDynamicMap, fdDynamicMap);
	}

	/**
	 * 自定义参数
	 */
	public String getFdCustomMap() {
		return (String) readLazyField("fdCustomMap", this.fdCustomMap);
	}

	/**
	 * 自定义参数
	 */
	public void setFdCustomMap(String fdCustomMap) {
		this.fdCustomMap = (String) writeLazyField("fdCustomMap",
				this.fdCustomMap, fdCustomMap);
	}

	/**
	 * 创建者
	 */
	public String getFdCreator() {
		return this.fdCreator;
	}

	/**
	 * 创建者
	 */
	public void setFdCreator(String fdCreator) {
		this.fdCreator = fdCreator;
	}

	/**
	 * 组织邮箱
	 */
	public String getFdOrgEmail() {
		return this.fdOrgEmail;
	}

	/**
	 * 组织邮箱
	 */
	public void setFdOrgEmail(String fdOrgEmail) {
		this.fdOrgEmail = fdOrgEmail;
	}

	/**
	 * 排序号
	 */
	public Integer getFdOrder() {
		return this.fdOrder;
	}

	/**
	 * 排序号
	 */
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 处理状态
	 */
	public Integer getFdHandleStatus() {
		return this.fdHandleStatus;
	}

	/**
	 * 处理状态
	 */
	public void setFdHandleStatus(Integer fdHandleStatus) {
		this.fdHandleStatus = fdHandleStatus;
	}

	public Integer getOrgType() {
		return null;
	}

	private String fdThisLeader;

	private String fdSuperLeader;

	/**
	 * 本级领导
	 */
	public String getFdThisLeader() {
		return this.fdThisLeader;
	}

	/**
	 * 本级领导
	 */
	public void setFdThisLeader(String fdThisLeader) {
		this.fdThisLeader = fdThisLeader;
	}

	/**
	 * 上级领导
	 */
	public String getFdSuperLeader() {
		return this.fdSuperLeader;
	}

	/**
	 * 上级领导
	 */
	public void setFdSuperLeader(String fdSuperLeader) {
		this.fdSuperLeader = fdSuperLeader;
	}

	public Boolean getFdIsExternal() {
		return fdIsExternal;
	}

	public void setFdIsExternal(Boolean fdIsExternal) {
		this.fdIsExternal = fdIsExternal;
	}
}
