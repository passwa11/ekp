/**
 * 
 */
package com.landray.kmss.sys.time.service.business;

import java.util.Date;
import java.util.List;

import com.landray.kmss.sys.time.interfaces.HoursField;
import com.landray.kmss.util.DateUtil;

/**
 * 工时按分钟计算
 * 
 * @author miaogr
 * @see
 */
public class BusinessMinuteCalculator extends BusinessMillisecondCalculator {

	public BusinessMinuteCalculator(DayRuleCalculator ruleCalc) {
		super(ruleCalc);
	}

	@Override
    public Date calculateBusinessHours(Date startDate, long numberOfMinute,
                                       List<RuleVisitor> visitors) {
		if (numberOfMinute <= 0) {
			return startDate;
		}
		return super.calculateBusinessHours(startDate, numberOfMinute
				* DateUtil.MINUTE, visitors);
	}

	@Override
    public boolean supports(HoursField field) {
		return HoursField.MINUTE.equals(field);
	}
}
