/**
 * 
 */
package com.landray.kmss.sys.time.service.business;

import java.util.Date;
import java.util.List;

/**
 * 一个工作日内规则计算服务，主要就是根据工作日规则设置，合并工作日和剔除假期后的时间区间的计算。
 * 
 * @author 龚健
 * @see
 */
public interface DayRuleCalculator {
	/**
	 * 合并指定日期所在当天，直到指定结束时间的工作日区间，同时剔除在此区间的假期。
	 * 
	 * @param start
	 * @param endTime
	 * @param visitors
	 * @return
	 */
	List<TimeRange> intersectRanges(Date start, long endTime,
			List<RuleVisitor> visitors);

	/**
	 * 合并指定日期所在当天，直到当天结束的工作日区间，同时剔除在此区间的假期。
	 * 
	 * @param start
	 * @param visitors
	 * @return
	 */
	List<TimeRange> intersectRanges(Date start, List<RuleVisitor> visitors);
}
