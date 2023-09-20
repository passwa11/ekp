package com.landray.kmss.sys.attachment.borrow.transfer;

import com.landray.kmss.sys.admin.transfer.constant.ISysAdminTransferConstant;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.attachment.borrow.service.ISysAttBorrowService;
import com.landray.kmss.util.SpringBeanUtil;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;

/**
 * 借阅统计数据迁移
 * 
 * @author
 */
public class SysAttBorrowTask implements ISysAdminTransferTask {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	/**
	 * 迁移借阅数为空的字段
	 */
	private final String sql =
			"update sys_att_main set fd_borrow_count = 0 where fd_borrow_count is null";

	@Override
	public SysAdminTransferResult run(SysAdminTransferContext context) {

		ISysAttBorrowService service = (ISysAttBorrowService) SpringBeanUtil
				.getBean("sysAttBorrowService");

		Session session = null;

		try {

			session = service.getBaseDao().getHibernateSession();
			NativeQuery nativeQuery = session.createNativeQuery(sql);
			nativeQuery.addSynchronizedQuerySpace("sys_att_main").executeUpdate();

			return SysAdminTransferResult.OK;

		} catch (Exception e) {
			logger.error("附件借阅数迁移报错：", e);
			return new SysAdminTransferResult(
					ISysAdminTransferConstant.TASK_STATUS_NOT_RUNED,
					e.getMessage());
		}

	}

}
