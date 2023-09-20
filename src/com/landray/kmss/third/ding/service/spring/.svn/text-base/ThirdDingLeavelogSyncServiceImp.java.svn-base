package com.landray.kmss.third.ding.service.spring;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.ding.model.ThirdDingLeavelog;
import com.landray.kmss.third.ding.service.IThirdDingLeavelogService;
import com.landray.kmss.third.ding.service.IThirdDingLeavelogSyncService;

/**
 * 考勤同步到钉钉失败重试定时任务
 * 
 * @author 唐有炜
 *
 */
public class ThirdDingLeavelogSyncServiceImp
		implements IThirdDingLeavelogSyncService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingLeavelogSyncServiceImp.class);

	/**
	 * 最多重试次数
	 */
	static final int MAX_RETRY_TIMES = 5;
	/**
	 * 最大处理数目
	 */
	static final int MAX_DEAL_SIZE = 1500;
	static final String LEAVE = "请假";
	static final String BUSS = "外出";
	static final String CHECK = "补卡";
	static final String SWITCH = "换班";
	static final String OVERTIME = "加班";

	private IThirdDingLeavelogService thirdDingLeavelogService;

	public void setThirdDingLeavelogService(
			IThirdDingLeavelogService thirdDingLeavelogService) {
		this.thirdDingLeavelogService = thirdDingLeavelogService;
	}

	@Override
	public void updateLeaveSync() throws Exception {
		logger.warn("失败同步到钉钉定时任务已开启");
		// 查询失败的同步记录
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdIstrue='0'");
		hqlInfo.setPageNo(0);
		hqlInfo.setRowSize(MAX_DEAL_SIZE);
		List recordList = thirdDingLeavelogService.getBaseDao()
				.findList(hqlInfo);
		// 开始同步
		if (CollectionUtils.isNotEmpty(recordList)) {
			for (Object record : recordList) {
				ThirdDingLeavelog log = (ThirdDingLeavelog) record;
				String fdId = log.getFdId();
				String tagName = log.getFdTagName();
				// 请假
				if (tagName.equals(LEAVE)) {
					thirdDingLeavelogService.updateLeaveSync(fdId);
				} else if (tagName.equals(BUSS)) {
					thirdDingLeavelogService.updateBussSync(fdId);
				} else if (tagName.equals(CHECK)) {
					thirdDingLeavelogService.updateCheckSync(fdId);
				} else if (tagName.equals(SWITCH)) {
					thirdDingLeavelogService.updateSwitchSync(fdId);
				} else if (tagName.equals(OVERTIME)) {
					thirdDingLeavelogService.updateOvertimeSync(fdId);
				}
			}
		}
	}
}
