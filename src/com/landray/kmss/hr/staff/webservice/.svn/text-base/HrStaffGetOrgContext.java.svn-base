package com.landray.kmss.hr.staff.webservice;

import java.util.Date;

import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;

public class HrStaffGetOrgContext {
	/**
	 *   设置需要获取组织架构数据的条目数
	 *说明:
	 *	 不允许为空
	 *		表示此次请求需要返回的组织架构数据的条目数
	 */
	private int count;
	
	/**
	 * 设置同步时间戳 
	 * 说明: 
	 * 允许为空 为空,则取系统所有组织架构信息 
	 * 不为空,则取系统在該时间戳之后的信息 
	 * 格式:yyyy-MM-dd HH:mm:ss
	 */
	private String beginTimeStamp;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setBeginTimeStamp(String beginTimeStamp) {
		this.beginTimeStamp = beginTimeStamp;
	}

	/**
	 * 获取开始时间，同时兼容：yyyy-MM-dd HH:mm:ss 和 时间戳
	 * 
	 * @return
	 */
	public Date getBeginTimeStamp() {
		if (StringUtil.isNotNull(beginTimeStamp)) {
			if (beginTimeStamp.matches("\\d+")) {
				return new Date(Long.parseLong(beginTimeStamp));
			} else {
				return DateUtil.convertStringToDate(beginTimeStamp, "yyyy-MM-dd HH:mm:ss");
			}
		}
		return null;
	}

}
