package com.landray.kmss.sys.time.service.scheduled;

import com.google.common.collect.Lists;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountItemService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountReleaseJobService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.util.SpringBeanUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;

import java.util.*;

/**
 *
 * @author cuiwj
 * @version 1.0 2019-01-25
 */
public class SysTimeLeaveAmountRehireServiceImp
		implements ISysTimeLeaveAmountReleaseJobService {

	private ISysTimeLeaveRuleService sysTimeLeaveRuleService;

	public void setSysTimeLeaveRuleService(
			ISysTimeLeaveRuleService sysTimeLeaveRuleService) {
		this.sysTimeLeaveRuleService = sysTimeLeaveRuleService;
	}

	private ISysTimeLeaveAmountService sysTimeLeaveAmountService;
	
	public void setSysTimeLeaveAmountService(
			ISysTimeLeaveAmountService sysTimeLeaveAmountService) {
		this.sysTimeLeaveAmountService = sysTimeLeaveAmountService;
	}

	private ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService;
	
	public void setSysTimeLeaveAmountItemService(
			ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService) {
		this.sysTimeLeaveAmountItemService = sysTimeLeaveAmountItemService;
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysTimeLeaveAmountRehireServiceImp.class);

	/**
	 * 获取有效人员
	 * @return
	 */
	private List<String> getPersonIds() {
		List<String> personList = new ArrayList<String>();
		try {
			IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
			personList = baseDao.getHibernateSession().createNativeQuery(
					"select fd_id from hr_staff_person_info where fd_status='rehireAfterRetirement'")
					.list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return personList;
	}

	@Override
	public void execute(SysQuartzJobContext jobContext) throws Exception {
		//假期类型
		SysTimeLeaveRule leaveRule = sysTimeLeaveRuleService.getLeaveRuleByType("1");
		if(leaveRule!=null){
			List<String> personIds = getPersonIds();
			if(CollectionUtils.isNotEmpty(personIds) ) {
				//所有类型查询一遍人员即可，并且人员去重
				Set<String> setPersonIds = new HashSet<String>();
				setPersonIds.addAll(personIds);
				jobContext.logMessage("本次任务处理人数：" + personIds.size());
				sysTimeLeaveAmountService.updateRehireAmomunt(leaveRule, Lists.newArrayList(setPersonIds));
			}else{
				jobContext.logMessage("没有需要发放的人员");
			}
		}else{
			jobContext.logMessage("没有需要处理的假期类型");
		}
	}
}
