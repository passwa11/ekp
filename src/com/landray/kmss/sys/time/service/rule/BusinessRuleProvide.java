/**
 * 
 */
package com.landray.kmss.sys.time.service.rule;

import java.util.List;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.service.business.BusinessDayRule;

/**
 * 工作日规则提供器
 * 
 * @author 龚健
 * @see
 */
public interface BusinessRuleProvide {
	/**
	 * 指定区域的工作日规则集
	 * 
	 * @param area
	 * @return
	 */
	List<BusinessDayRule> getRules(SysTimeArea area,
			SysOrgElement element);

	/**
	 * 越优先的，数字越小
	 * 
	 * @return 优先级别
	 */
	int getPriority();
}
