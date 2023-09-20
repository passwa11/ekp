/**
 * 
 */
package com.landray.kmss.sys.time.service.business;

import java.util.Date;
import java.util.List;

import com.landray.kmss.sys.time.interfaces.HoursField;

/**
 * 工时计算服务
 * 
 * @author 龚健
 * @see
 */
public interface BusinessHoursCalculator {
	/**
	 * @param field
	 * @return 是否支持指定计算模式
	 */
	boolean supports(HoursField field);

	/**
	 * 根据指定的日期数，返回相应的截止日期
	 * 
	 * @param startDate
	 * @param numberOfDate
	 * @param rules
	 * @return
	 */
	Date calculateBusinessHours(Date startDate, long number,
			List<RuleVisitor> visitors);
}
