/**
 * 
 */
package com.landray.kmss.sys.time.service.business;

import java.util.Date;
import java.util.List;

import com.landray.kmss.sys.time.interfaces.HoursField;

/**
 * 工时计算主服务
 * 
 * @author 龚健
 * @see
 */
public interface BusinessHours {
	/**
	 * 根据开始日期和日期数，返回指定工时数后的日期
	 * 
	 * @param startDate
	 * @param numberOfDate
	 * @param field
	 * @param rules
	 * @return
	 */
	Date calculateFloatingHours(Date startDate, int numberOfDate,
			HoursField field, List<RuleVisitor> visitors);

	/**
	 * 计算指定日期区间内的工时
	 * 
	 * @param startDate
	 * @param endDate
	 * @param rules
	 * @return
	 */
	long calculateBusinessHours(Date startDate, Date endDate,
			List<RuleVisitor> visitors);

	/**
	 * 计算指定日期区间内的工时
	 * 
	 * @param startDate
	 * @param endDate
	 * @param rules
	 * @return
	 */
	long calculateBusinessHours(long startDate, long endDate,
			List<RuleVisitor> visitors);

	/**
	 * 计算指定日期区间内的工作日天数
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @param rules
	 * @return
	 */
	int calculateBusinessDays(long startDate, long endDate,
			List<RuleVisitor> visitors);
}
