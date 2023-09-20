package com.landray.kmss.sys.attend.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;

/**
 * 保存发送验证码的记录
 *
 * @author cuiwj
 * @version 1.0 2018-08-23
 */
public class SysAttendOutPersonLog extends BaseModel {

	/**
	 * 用户名
	 */
	private String fdUserName;

	/**
	 * 手机号
	 */
	private String fdUserPhoneNum;

	/**
	 * 手机验证码
	 */
	private String fdMobileCode;

	/**
	 * 创建时间
	 */
	private Date fdCreateTime;

	public String getFdUserName() {
		return fdUserName;
	}

	public void setFdUserName(String fdUserName) {
		this.fdUserName = fdUserName;
	}

	public String getFdUserPhoneNum() {
		return fdUserPhoneNum;
	}

	public void setFdUserPhoneNum(String fdUserPhoneNum) {
		this.fdUserPhoneNum = fdUserPhoneNum;
	}

	public String getFdMobileCode() {
		return fdMobileCode;
	}

	public void setFdMobileCode(String fdMobileCode) {
		this.fdMobileCode = fdMobileCode;
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

}
