package com.landray.kmss.sys.evaluation.transfer;

import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class EvaluationTransferChecker implements ISysAdminTransferChecker{
	@Override
	public SysAdminTransferCheckResult check(
			SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		try {
			IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
			Session session = baseDao.getHibernateSession();
			Query query = session.createNativeQuery("select count(*) from sys_evaluation_reply where "+
							getHierIdIsNullSQL());
			
			List<Number> result = query.list();
			if (!result.isEmpty() && result.get(0).longValue() > 0) {
                return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
	}
	
	/**
	 * 根据数据库不同使用不同的函数判断层级Id字段是否有值
	 * 
	 * @return
	 */
	public static String getHierIdIsNullSQL() {
		String sentence = "sys_evaluation_reply.fd_hierarchy_id is null or ";
		String driverClass = ResourceUtil
				.getKmssConfigString("hibernate.connection.driverClass");
		if ("com.mysql.jdbc.Driver".equals(driverClass)) {
			sentence += " sys_evaluation_reply.fd_hierarchy_id ='' ";
		}
		else if ("net.sourceforge.jtds.jdbc.Driver".equals(driverClass)) {
			sentence += " ISNULL(datalength (sys_evaluation_reply.fd_hierarchy_id),0)<=0 ";
		}
		else if ("oracle.jdbc.driver.OracleDriver".equals(driverClass)) {
			sentence += " dbms_lob.getlength(sys_evaluation_reply.fd_hierarchy_id)<=0 ";
		} else {
			sentence += " sys_evaluation_reply.fd_hierarchy_id ='' ";
		}
		return sentence;
	}
}
