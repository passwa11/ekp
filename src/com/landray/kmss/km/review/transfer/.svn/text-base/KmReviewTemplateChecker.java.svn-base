package com.landray.kmss.km.review.transfer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.km.review.service.IKmReviewTemplateService;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.util.SpringBeanUtil;

public class KmReviewTemplateChecker implements ISysAdminTransferChecker {
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public SysAdminTransferCheckResult check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		try {
			IKmReviewTemplateService kmReviewTemplateService = (IKmReviewTemplateService) SpringBeanUtil.getBean("kmReviewTemplateService");
			List<Long> list = kmReviewTemplateService.getBaseDao().findValue("count(*)", "kmReviewTemplate.fdIsAvailable is null", null);
			if (list.get(0) > 0) {
				return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
			}
		} catch (Exception e) {
			logger.error("检查是否执行过旧数据迁移为空异常", e);
		}
		return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
	}

}
