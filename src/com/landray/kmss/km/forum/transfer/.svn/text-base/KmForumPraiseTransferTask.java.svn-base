package com.landray.kmss.km.forum.transfer;

import com.landray.kmss.util.DbUtils;
import org.slf4j.Logger;

import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.praise.service.ISysPraiseMainService;
import com.landray.kmss.util.SpringBeanUtil;

public class KmForumPraiseTransferTask implements ISysAdminTransferTask {
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	private static final String TABLE_SUPPORT = "km_forum_support";

	@Override
    public SysAdminTransferResult run(SysAdminTransferContext sysAdminTransferContext) {
		ISysPraiseMainService sysPraiseMainService = (ISysPraiseMainService) SpringBeanUtil.getBean("sysPraiseMainService");
		try {
			//判断km_forum_support表是否存在，不存在不处理 #171214
			if(DbUtils.isExitTable(TABLE_SUPPORT)) {
				String insertSql = "insert into sys_praise_main (fd_id,fd_praise_time,fd_model_name,fd_model_id,fd_praise_person_id)"
						+ " select fd_id,doc_support_time,fd_model_name,fd_model_id,doc_supporter_id from km_forum_support";
				sysPraiseMainService.getBaseDao().getHibernateSession().createNativeQuery(insertSql).executeUpdate();
			}
			String updateSql = "update km_forum_post set doc_approve_count = fd_support_count";
			sysPraiseMainService.getBaseDao().getHibernateSession().createNativeQuery(updateSql).executeUpdate();
		} catch (Exception e) {
			logger.error("执行旧数据迁移为空异常", e);
		}
		return SysAdminTransferResult.OK;
	}

}
