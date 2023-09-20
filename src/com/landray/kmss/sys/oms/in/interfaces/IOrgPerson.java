package com.landray.kmss.sys.oms.in.interfaces;

/**
 * 接入第三方平台组织机构元素接口
 *
 * @author 吴兵
 * @version 1.0 2006-11-29
 */

public interface IOrgPerson extends IOrgElement {
	/**
	 * @return 手机号
	 */
	public abstract String getMobileNo();

	/**
	 * @return Email地址
	 */
	public abstract String getEmail();

	/**
	 * @return 登录名
	 */
	public abstract String getLoginName();

	/**
	 * @return 登录密码
	 */
	public abstract String getPassword();

	/*
	 * 考勤卡号
	 */
	public abstract String getAttendanceCardNumber();

	/*
	 * 办公电话
	 */
	public abstract String getWorkPhone();

	/**
	 * @return 所有岗位对应键关键字数组
	 */
	public abstract String[] getPosts();

	/*
	 * 默认语言
	 */
	public abstract String getLang();

	/*
	 * RTX号
	 */
	public abstract String getRtx();

	/*
	 * 微信号
	 */
	public abstract String getWechat();

	/*
	 * 动态密码
	 */
	public abstract String getScard();

	/**
	 * @return 性别
	 */
	public String getSex();

	/**
	 * 短号
	 *
	 * @return
	 */
	public String getShortNo();

	public String getNickName();

	// 是否激活
	public Boolean getActivated();

	// 是否登录系统
	public Boolean getCanLogin();

	// 登录名纯小写
	public String getLoginNameLower();
}
