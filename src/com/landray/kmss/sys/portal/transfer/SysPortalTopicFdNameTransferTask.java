package com.landray.kmss.sys.portal.transfer;

import com.landray.kmss.sys.admin.transfer.constant.ISysAdminTransferConstant;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.portal.service.ISysPortalTopicService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class SysPortalTopicFdNameTransferTask extends
		SysPortalTopicFdNameTransferChecker implements ISysAdminTransferTask {

	private ISysPortalTopicService sysPortalTopicService;

	public ISysPortalTopicService getSysPortalTopicService() {
		if (sysPortalTopicService == null) {
			sysPortalTopicService = (ISysPortalTopicService) SpringBeanUtil
					.getBean("sysPortalTopicService");
		}
		return sysPortalTopicService;
	}

	/**
	 * SysPortalTopic表更改fdName的数据库字段长度（多语言情况)
	 * 
	 */
	@Override
	public SysAdminTransferResult
			run(SysAdminTransferContext sysAdminTransferContext) {
		try {
			Session session = this.getSysPortalTopicService().getBaseDao()
					.getHibernateSession();
			Query qlist;
			// 判断是什么数据库
			String driverClass = ResourceUtil.getKmssConfigString("hibernate.connection.driverClass");
			// Oracle
			if ("oracle.jdbc.driver.OracleDriver".equals(driverClass)) {
				String sql = "select column_name from user_tab_columns where table_name='SYS_PORTAL_TOPIC'";
				Query q = session.createNativeQuery(sql);
				String updateLeanth = "";
				List<String> list = q.list();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).indexOf("FD_NAME_") > -1) {
						updateLeanth = "ALTER table SYS_PORTAL_TOPIC modify "+ list.get(i) + " VARCHAR2(200) ";
						qlist = session.createNativeQuery(updateLeanth).addSynchronizedQuerySpace("SYS_PORTAL_TOPIC");
						qlist.executeUpdate();
					}
				}
			}
			// SQL Server
			if ("net.sourceforge.jtds.jdbc.Driver".equals(driverClass)) {
				// 获取表的所有字段名
				String sql = "select name from syscolumns where id=object_id('sys_portal_topic') ";
				Query q = session.createNativeQuery(sql);
				String updateLeanth = "";
				List<String> list = q.list();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).indexOf("fd_name_") > -1) {
						updateLeanth = "ALTER table sys_portal_topic  alter column "+ list.get(i) + " varchar(200);";
						qlist = session.createNativeQuery(updateLeanth).addSynchronizedQuerySpace("sys_portal_topic");
						qlist.executeUpdate();
					}
				}
			}
			// MYSQL
			if ("com.mysql.jdbc.Driver".equals(driverClass)) {
				// 获取数据库名
				String url = ResourceUtil
						.getKmssConfigString("hibernate.connection.url");
				String Name = url.split("[?]")[0].split("//")[1].split("/")[1];
				// 获取表的所有字段名
				String sql = "select COLUMN_NAME from information_schema.COLUMNS where table_name = 'sys_portal_topic' and table_schema = '"
						+ Name + "'";
				Query q = session.createNativeQuery(sql);
				String updateLeanth = "";
				List<String> list = q.list();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).indexOf("fd_name_") > -1) {
						updateLeanth = "ALTER table sys_portal_topic modify "+ list.get(i) + " varchar(200)";
						qlist = session.createNativeQuery(updateLeanth).addSynchronizedQuerySpace("sys_portal_topic");
						qlist.executeUpdate();
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new SysAdminTransferResult(
					ISysAdminTransferConstant.TASK_RESULT_ERROR, e);
		}
		return SysAdminTransferResult.OK;
	}

}
