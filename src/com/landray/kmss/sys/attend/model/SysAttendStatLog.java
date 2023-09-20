package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.model.BaseModel;

import java.util.Date;

/**
 * 缺卡记录产生的日志表
 * @author 王京
 */
public class SysAttendStatLog extends BaseModel {
	/**
	 * 类型
	 */
	private String fdOperType;
	/**
	 * 缺卡所属日期
	 */
	private Date fdCreateTime;
	/**
	 * 考勤组
	 */
	private String fdCategoryId;
	/**
	 * 数据创建时间
	 */
	private Date docCreateTime;

	public String getFdOperType() {
		return fdOperType;
	}

	public void setFdOperType(String fdOperType) {
		this.fdOperType = fdOperType;
	}

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	@Override
	public Class getFormClass() {
		return null;
	}

	public String getFdCategoryId() {
		return fdCategoryId;
	}

	public void setFdCategoryId(String fdCategoryId) {
		this.fdCategoryId = fdCategoryId;
	}

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
}
