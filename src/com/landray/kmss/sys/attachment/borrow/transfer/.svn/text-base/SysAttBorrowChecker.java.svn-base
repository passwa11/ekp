package com.landray.kmss.sys.attachment.borrow.transfer;

import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.attachment.borrow.service.ISysAttBorrowService;
import com.landray.kmss.util.SpringBeanUtil;
import org.hibernate.Session;
import org.slf4j.Logger;

/**
 * 借阅统计数据迁移检测
 * 
 * @author
 */
public class SysAttBorrowChecker implements ISysAdminTransferChecker {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	/**
	 * 字段为空则需要迁移
	 */
	private final String sql =
			"select count(fd_id) from sys_att_main where fd_borrow_count is null";

	@Override
	public SysAdminTransferCheckResult
			check(SysAdminTransferCheckContext context) {

		ISysAttBorrowService service = (ISysAttBorrowService) SpringBeanUtil
				.getBean("sysAttBorrowService");

		Session session;

		try {
			session = service.getBaseDao().getHibernateSession();

			long count = ((Number) session.createNativeQuery(sql).uniqueResult())
					.longValue();
			if (count == 0) {
				return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
			}
		} catch (Exception e) {
			logger.error("检测是否需要执行借阅数量迁移报错：", e);
		}

		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}

}
