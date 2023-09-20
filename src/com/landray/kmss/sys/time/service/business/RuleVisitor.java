/**
 * 
 */
package com.landray.kmss.sys.time.service.business;

import java.util.Date;

/**
 * 规则访问器，根据指定日期，获取所属当天的工作日规则
 * 
 * @author 龚健
 * @see
 */
public interface RuleVisitor {
	/**
	 * @param dateToCheck
	 * @return 指定日期的当天工作日规则
	 */
	BusinessDayRule getDayRule(Date dateToCheck);
}
