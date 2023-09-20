package com.landray.kmss.sys.portal.transfer;

import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.util.ResourceUtil;
/**
 * #99640 hibernate映射字段默认值配置导致的数据库不兼容
 * 匿名字段变更需要进行迁移
 * @author 钟清学 2020-4-21
 *
 */
public class SysPortalFdAnonymousTransferChecker implements ISysAdminTransferChecker {

	@Override
	public SysAdminTransferCheckResult check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		if(checkSQLServer()) {
            return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
        } else {
            return SysAdminTransferCheckResult.TASK_STATUS_DELETED;
        }
	}
	/**
	 * 检查是否SQLServer数据库
	 * @return
	 */
	private boolean checkSQLServer() {
		return "net.sourceforge.jtds.jdbc.Driver".equals(ResourceUtil.getKmssConfigString("hibernate.connection.driverClass"));
	}

}
