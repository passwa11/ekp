package com.landray.kmss.sys.organization.webservice.out;

public class SysSynchroGetOrgContext {

	/**
	 *   设置需要获取组织架构数据的条目数
	 *说明:
	 *	 不允许为空
	 *		表示此次请求需要返回的组织架构数据的条目数
	 */
	private int count;

	/**
	 *    设置需要返回的组织架构类型
	 * 说明:
	 *    可为空.
	 *        设置需要返回的组织架构类型,JSON数组
	 *    如:[{"type":"person"},{"type":"group"},{"type":"post"},{"type":"dept"},{"type":"org"}]等,    
	 *    值为空,  表示全部.
	 *        
	 */
	private String returnOrgType;

	/**
	 * 设置同步时间戳 
	 * 说明: 
	 * 允许为空 为空,则取系统所有组织架构信息 
	 * 不为空,则取系统在該时间戳之后的信息 
	 * 格式:yyyy-MM-dd HH:mm:ss
	 */
	private String beginTimeStamp;

	/**
	 * 设置同步时间戳 
	 * 说明: 
	 * 允许为空 为空,则取系统所有组织架构信息 
	 * 不为空,则取系统在該时间戳之后的信息 
	 * 格式:yyyy-MM-dd HH:mm:ss
	 */
	private String endTimeStamp;

	public String getReturnOrgType() {
		return returnOrgType;
	}

	public void setReturnOrgType(String returnOrgType) {
		this.returnOrgType = returnOrgType;
	}

	public String getBeginTimeStamp() {
		return beginTimeStamp;
	}

	public void setBeginTimeStamp(String beginTimeStamp) {
		this.beginTimeStamp = beginTimeStamp;
	}

	public String getEndTimeStamp() {
		return endTimeStamp;
	}

	public void setEndTimeStamp(String endTimeStamp) {
		this.endTimeStamp = endTimeStamp;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
