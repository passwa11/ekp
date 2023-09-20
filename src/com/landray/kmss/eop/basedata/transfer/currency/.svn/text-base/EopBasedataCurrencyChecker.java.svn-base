package com.landray.kmss.eop.basedata.transfer.currency;

import com.landray.kmss.eop.basedata.service.IEopBasedataCurrencyService;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.hibernate.query.Query;

import java.util.List;

/**
 * @author wangwh
 * @description:验证中文货币是否存在
 * @date 2021/5/7
 */
public class EopBasedataCurrencyChecker implements ISysAdminTransferChecker{

	protected final static Logger logger = org.slf4j.LoggerFactory.getLogger(EopBasedataCurrencyChecker.class);
	@Override
	public SysAdminTransferCheckResult
			check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		try {
			IEopBasedataCurrencyService ser = (IEopBasedataCurrencyService) SpringBeanUtil.getBean("eopBasedataCurrencyService");
			String hql = "from EopBasedataCurrency where LOWER(fdCode) like :fdCode";
			Query query = ser.getBaseDao().getHibernateSession().createQuery(hql);
			query.setParameter("fdCode", "cny");
			List list = query.list();
			if (list == null || list.isEmpty()){
				return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
			}
			return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
		} catch (Exception e) {
			logger.error("", e);
		}
		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}

}
