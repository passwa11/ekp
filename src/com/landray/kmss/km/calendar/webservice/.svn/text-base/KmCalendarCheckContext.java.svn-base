package com.landray.kmss.km.calendar.webservice;
/**
 *冲突检测参上下文
 */
public class KmCalendarCheckContext {
	/**
	 *    检测日程人员(必填)
	 *    json数据
	 * 说明:
	 * 		格式为:   {类型: 值}    如{"PersonNo":"001"}。
     *	  类型说明:
	 *		Id	EKPj系统组织架构唯一表示
	 *		PersonNo	EKPj系统组织架构个人编号
	 *		DeptNo	EKPj系统组织架构部门编号
	 *		PostNo	EKPj系统组织架构岗位编号
	 *		GroupNo	EKPj系统组织架构常用群组编号
	 *		LoginName	EKPj系统组织架构个人登录名
	 *		Keyword	EKPj系统组织架构关键字
	 *		LdapDN	和LDAP集成时LDAP中DN值
	 */
	private String person;
	
	/**
	 * 开始时间(必填)
	 */
	private  String docStartTime;
	
	/**
	 * 结束时间(必填)
	 */
	private String docFinishTime;
	
	/**
	 * 其他查询信息(暂留字段，非必填)
	 */
	private String otherInfo;

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
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
