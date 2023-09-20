package com.landray.kmss.sys.time.service.spring;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountItemService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountJobService;
import com.landray.kmss.sys.time.util.SysTimeUtil;

/**
 * 假期额度，若已过期，则置为无效。
 *
 * @author cuiwj
 * @version 1.0 2018-12-24
 */
public class SysTimeLeaveAmountJobServiceImp
		implements ISysTimeLeaveAmountJobService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysTimeLeaveAmountJobServiceImp.class);

	private ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService;

	public void setSysTimeLeaveAmountItemService(
			ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService) {
		this.sysTimeLeaveAmountItemService = sysTimeLeaveAmountItemService;
	}

	@Override
	public void execute(SysQuartzJobContext jobContext) throws Exception {
		try {
			logger.debug("更新假期额度状态 starting");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(" sysTimeLeaveAmountItem.fdValidDate is not null ");
			List<SysTimeLeaveAmountItem> list = sysTimeLeaveAmountItemService
					.findList(hqlInfo);
			if (!list.isEmpty()) {
				for (SysTimeLeaveAmountItem item : list) {
					if (!Boolean.TRUE.equals(item.getFdIsAccumulate())
							&& item.getFdValidDate() != null) {// 不是累加的，有过期日期的
						boolean oldFdIsAvail=item.getFdIsAvail();
						if (!isAfterToday(item.getFdValidDate())) {
							item.setFdIsAvail(false);
						} else {
							item.setFdIsAvail(true);
						}
						if(oldFdIsAvail!=item.getFdIsAvail()) {
							sysTimeLeaveAmountItemService.update(item);
						}
					}
				}
			}
			logger.debug("更新假期额度状态 end");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更新假期额度状态失败:" + e.getMessage(), e);
			throw e;
		}
	}

	private Boolean isAfterToday(Date date) {
		Date today = SysTimeUtil.getDate(new Date(), 0);
		return SysTimeUtil.getDate(date, 0).compareTo(today) >= 0;
	}

}
