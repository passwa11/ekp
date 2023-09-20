/**
 * 
 */
package com.landray.kmss.sys.time.service.rule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.service.business.BusinessDayRule;
import com.landray.kmss.sys.time.service.business.RuleVisitor;
import com.landray.kmss.sys.time.service.business.TimeRange;

/**
 * 时间规则访问器，提供给工时服务使用。
 * 
 * @author 龚健
 * @see
 */
public class BusinessRuleVisitor implements RuleVisitor {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(BusinessRuleVisitor.class);

	private List<BusinessDayRule> rules = null;
	private BusinessRuleProvide provide;
	private SysTimeArea area;
	private SysOrgElement element;

	public BusinessRuleVisitor(BusinessRuleProvide provide, SysTimeArea area,
			SysOrgElement element) {
		this.provide = provide;
		this.area = area;
		this.element = element;
	}

	@Override
	public BusinessDayRule getDayRule(Date dateToCheck) {
		for (BusinessDayRule rule : getRules()) {
			if (rule.contains(dateToCheck)) {
				return rule;
			}
		}

		if (provide instanceof WorkTimeProvide) {
			logger.debug("工作时间没有设置或者已经过期");
			// 没有配置工作时间，则认为都为工作时间
			return new BusinessDayRule() {
				@Override
				public boolean contains(Date dateToCheck) {
					return true;
				}

				@Override
				public boolean isBusinessDay(Date dateToCheck) {
					return true;
				}

				@Override
				public List<TimeRange> getTimeRangesOnAsc(TimeRange target) {
					List<TimeRange> ranges = new ArrayList<TimeRange>();
					ranges.add(new TimeRange(0, 0));
					return ranges;
				}

				@Override
				public boolean isHolidayDay(Date dateToCheck) {
					return false;
				}
			};
		}

		return null;
	}

	private List<BusinessDayRule> getRules() {
		if (rules == null) {
			List<BusinessDayRule> rulesToUse = provide.getRules(area,
					element);
			if (rulesToUse == null || rulesToUse.isEmpty()) {
				rules = new ArrayList<BusinessDayRule>();
			} else {
				rules = rulesToUse;
			}
		}
		return rules;
	}
}
