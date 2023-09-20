package com.landray.kmss.km.calendar.webservice;

/**
 * WebService日程查询上下文
 */
public class KmCalendarWsQueryContext {
	private String appKey;

	/**
	 * 查询日程人员 json数据 说明: 单值格式为: {类型: 值} 如{"PersonNo":"001"}。 多值格式为: [{类型1: 值1}
	 * ,{类型2: 值2}...] ,如: [{"PersonNo":"001"} ,{"KeyWord":"2EDF6"}]。 类型说明: Id
	 * EKPj系统组织架构唯一表示 PersonNo EKPj系统组织架构个人编号 DeptNo EKPj系统组织架构部门编号 PostNo
	 * EKPj系统组织架构岗位编号 GroupNo EKPj系统组织架构常用群组编号 LoginName EKPj系统组织架构个人登录名 Keyword
	 * EKPj系统组织架构关键字 LdapDN 和LDAP集成时LDAP中DN值
	 */
	private String persons;
	/**
	 * 类型:日程？笔记？
	 */
	private String fdType;

	/**
	 * 开始时间(必填)
	 */
	private String docStartTime;

	/**
	 * 结束时间(必填)
	 */
	private String docFinishTime;

	/**
	 * 其他查询信息(暂留字段，非必填)
	 */
	private String otherInfo;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public String getPersons() {
		return persons;
	}

	public void setPersons(String persons) {
		this.persons = persons;
	}

	public String getDocStartTime() {
		return docStartTime;
	}

	public void setDocStartTime(String docStartTime) {
		this.docStartTime = docStartTime;
	}

	public String getDocFinishTime() {
		return docFinishTime;
	}

	public void setDocFinishTime(String docFinishTime) {
		this.docFinishTime = docFinishTime;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
}
