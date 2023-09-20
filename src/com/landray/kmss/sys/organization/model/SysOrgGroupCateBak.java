package com.landray.kmss.sys.organization.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;

/**
 * 群组类别
 * 
 * @author 叶中奇
 */
public class SysOrgGroupCateBak extends BaseModel {
	/*
	 * 名称
	 */
	private String fdName;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/*
	 * 关键字
	 */
	private String fdKeyword;

	public String getFdKeyword() {
		return fdKeyword;
	}

	public void setFdKeyword(String fdKeyword) {
		this.fdKeyword = fdKeyword;
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

	/*
	 * 最后修改时间
	 */
	private Date fdAlterTime = new Date();

	public Date getFdAlterTime() {
		return fdAlterTime;
	}

	public void setFdAlterTime(Date fdAlterTime) {
		this.fdAlterTime = fdAlterTime;
	}

	public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}

	public String getFdParentId() {
		return fdParentId;
	}

	/*
	 * 父类别
	 */
	private String fdParentId;

	@Override
	public Class getFormClass() {
		// TODO 自动生成的方法存根
		return null;
	}

}
