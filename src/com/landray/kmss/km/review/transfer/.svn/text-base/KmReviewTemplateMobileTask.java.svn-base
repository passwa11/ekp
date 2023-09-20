package com.landray.kmss.km.review.transfer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.km.review.model.KmReviewTemplate;
import com.landray.kmss.km.review.service.IKmReviewTemplateService;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 流程模板数据迁移
 * 
 * @author 潘永辉
 * 
 */
public class KmReviewTemplateMobileTask implements ISysAdminTransferTask {
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public SysAdminTransferResult run(SysAdminTransferContext sysAdminTransferContext) {
		String uuid = sysAdminTransferContext.getUUID();
		ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil.getBean("sysAdminTransferTaskService");
		IKmReviewTemplateService kmReviewTemplateService = (IKmReviewTemplateService) SpringBeanUtil.getBean("kmReviewTemplateService");

		try {
			List<SysAdminTransferTask> list = sysAdminTransferTaskService.getBaseDao().findValue(null, "sysAdminTransferTask.fdUuid='" + uuid + "'", null);
			if (list != null && list.size() > 0) {
				SysAdminTransferTask sysAdminTransferTask = list.get(0);
				if (sysAdminTransferTask.getFdStatus() != 1) {
					// 移动端的新建和审批分开控制数据迁移
					List<KmReviewTemplate> kmReviewTemplates = kmReviewTemplateService.findList("kmReviewTemplate.fdIsMobileCreate is null or kmReviewTemplate.fdIsMobileCreate is null", null);
					for (KmReviewTemplate kmReviewTemplate : kmReviewTemplates) {
						kmReviewTemplate.setFdIsMobileCreate(!kmReviewTemplate.getFdDisableMobileForm());
						kmReviewTemplate.setFdIsMobileApprove(!kmReviewTemplate.getFdDisableMobileForm());
						kmReviewTemplateService.update(kmReviewTemplate);
					}
				}
			}
		} catch (Exception e) {
			logger.error("执行旧数据迁移为空异常", e);
		}

		return SysAdminTransferResult.OK;
	}

}
