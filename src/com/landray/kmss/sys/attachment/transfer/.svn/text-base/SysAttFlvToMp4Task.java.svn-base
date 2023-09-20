package com.landray.kmss.sys.attachment.transfer;

import org.hibernate.query.Query;
import org.hibernate.Session;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.admin.transfer.constant.ISysAdminTransferConstant;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 附件SysAttMain表的location字段迁移到SysAttFile表中
 * @author peng
 */
public class SysAttFlvToMp4Task extends SysAttTransferChecker implements ISysAdminTransferTask {

	@SuppressWarnings({ "rawtypes"})
	@Override
	public SysAdminTransferResult run(SysAdminTransferContext sysAdminTransferContext) {
		try {
			Session session = ((IBaseDao) SpringBeanUtil.getBean("KmssBaseDao")).getHibernateSession();
			String strHql = "UPDATE SysFileConvertConfig sc SET sc.fdConverterKey = 'videoToMp4' WHERE sc.fdConverterKey = 'videoToFlv'";
			Query query = session.createQuery(strHql);
			query.executeUpdate();
			
			String strHql2 = "UPDATE SysFileConvertQueue sq SET sq.fdConverterKey = 'videoToMp4' WHERE sq.fdConverterKey = 'videoToFlv' and sq.fdConvertStatus != 4 ";
			query = session.createQuery(strHql2);
			query.executeUpdate();
			
			return SysAdminTransferResult.OK;
		} catch (Exception e) {
			logger.info("视频迁移任务出错", e);
			return new SysAdminTransferResult(ISysAdminTransferConstant.TASK_STATUS_NOT_RUNED, e.getLocalizedMessage(),
					e);

		}
	}
}
