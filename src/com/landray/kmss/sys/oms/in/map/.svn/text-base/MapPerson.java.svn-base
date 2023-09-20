package com.landray.kmss.sys.oms.in.map;

import java.util.Map;

import com.landray.kmss.sys.oms.in.interfaces.IOrgPerson;

/**
 * 组织元素Map实现
 *
 * @author 吴兵
 * @version 3.0 2009-10-26
 */

public class MapPerson extends MapElement implements IOrgPerson {
	public static String MOBILE_NO = "mobileNo";
	public static String EMAIL = "email";
	public static String LOGIN_NAME = "loginName";
	public static String PASSWORD = "password";
	public static String ATTENDANCE_CARD_NUMBER = "attendanceCardNumber";
	public static String WORK_PHONE = "workPhone";
	public static String POSTS = "posts";
	public static String SEX = "sex";
	public static String SHORT_NO = "shortNo";
	public static String NICK_NAME = "nickName";
	public final static String IS_ACTIVATED = "isActivated";
	public final static String CAN_LOGIN = "canLogin";
	public final static String LOGIN_NAME_LOWER = "loginNameLower";

	private Map mapOrgPerson;

	public MapPerson(Map mapOrgPerson) {
		super(mapOrgPerson);
		this.mapOrgPerson = mapOrgPerson;
	}

	/**
	 * @return 手机号
	 */
	@Override
    public String getMobileNo() {
		return (String) mapOrgPerson.get(MOBILE_NO);
	}

	/**
	 * @return Email地址
	 */
	@Override
    public String getEmail() {
		return (String) mapOrgPerson.get(EMAIL);
	}

	/**
	 * @return 登录名
	 */
	@Override
    public String getLoginName() {
		return (String) mapOrgPerson.get(LOGIN_NAME);
	}

	/**
	 * @return 登录密码
	 */
	@Override
    public String getPassword() {
		return (String) mapOrgPerson.get(PASSWORD);
	}

	/*
	 * 考勤卡号
	 */
	@Override
    public String getAttendanceCardNumber() {
		return (String) mapOrgPerson.get(ATTENDANCE_CARD_NUMBER);
	}

	/*
	 * 办公电话
	 */
	@Override
    public String getWorkPhone() {
		return (String) mapOrgPerson.get(WORK_PHONE);
	}

	/**
	 * @return 所有岗位对应键关键字数组
	 */
	@Override
    public String[] getPosts() {
		return (String[]) mapOrgPerson.get(POSTS);
	}

	@Override
    public String getScard() {
		return null;
	}

	@Override
	public String getSex() {
		return (String) mapOrgPerson.get(SEX);
	}

	@Override
    public String getShortNo() {
		return (String) mapOrgPerson.get(SHORT_NO);
	}

	@Override
	public String getNickName() {
		return (String) mapOrgPerson.get(NICK_NAME);
	}

	@Override
	public Boolean getActivated() {
		return (Boolean) mapOrgPerson.get(IS_ACTIVATED);
	}

	@Override
	public Boolean getCanLogin() {
		return (Boolean) mapOrgPerson.get(CAN_LOGIN);
	}

	@Override
	public String getLoginNameLower() {
		return (String) mapOrgPerson.get(LOGIN_NAME_LOWER);
	}
}
