package com.landray.kmss.sys.organization.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;

/**
 * 创建日期 2008-十一月-27
 * 
 * @author 陈亮 角色是否默认
 */
public class SysOrgRoleLineDefaultRole extends BaseModel {

	/*
	 * 角色线配置
	 */
	protected SysOrgRoleConf sysOrgRoleConf = null;

	/*
	 * 重复的人员
	 */
	protected SysOrgElement fdPerson = null;

	/*
	 * 所选择的默认岗位
	 */
	protected SysOrgElement fdPost = null;

	public SysOrgElement getFdPerson() {
		return fdPerson;
	}

	public void setFdPerson(SysOrgElement fdPerson) {
		this.fdPerson = fdPerson;
	}

	public SysOrgElement getFdPost() {
		return fdPost;
	}

	public void setFdPost(SysOrgElement fdPost) {
		this.fdPost = fdPost;
	}

	/**
	 * @return 返回 角色线配置
	 */
	public SysOrgRoleConf getSysOrgRoleConf() {
		return sysOrgRoleConf;
	}

	/**
	 * @param sysOrgRoleConf
	 *            要设置的 角色线配置
	 */
	public void setSysOrgRoleConf(SysOrgRoleConf sysOrgRoleConf) {
		this.sysOrgRoleConf = sysOrgRoleConf;
	}

	@Override
    public Class getFormClass() {
		return null;
	}

	/*
	 * 更新时间
	 */
	private Date fdAlterTime = new Date();

	public void setFdAlterTime(Date fdAlterTime) {
		this.fdAlterTime = fdAlterTime;
	}

	public Date getFdAlterTime() {
		return fdAlterTime;
	}

	/*
	 * 创建时间
	 */
	private Date fdCreateTime = new Date();

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

}
