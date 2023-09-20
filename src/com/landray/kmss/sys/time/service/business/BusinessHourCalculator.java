/**
 * 
 */
package com.landray.kmss.sys.time.service.business;

import java.util.Date;
import java.util.List;

import com.landray.kmss.sys.time.interfaces.HoursField;
import com.landray.kmss.util.DateUtil;

/**
 * 工时按小时计算
 * 
 * @author 龚健
 * @see
 */
public class BusinessHourCalculator extends BusinessMillisecondCalculator {

	public BusinessHourCalculator(DayRuleCalculator ruleCalc) {
		super(ruleCalc);
	}

	@Override
    public Date calculateBusinessHours(Date startDate, long numberOfHour,
                                       List<RuleVisitor> visitors) {
		if (numberOfHour <= 0) {
			return startDate;
		}
		return super.calculateBusinessHours(startDate, numberOfHour
				* DateUtil.HOUR, visitors);
	}

	@Override
    public boolean supports(HoursField field) {
		return HoursField.HOUR.equals(field);
	}
}
