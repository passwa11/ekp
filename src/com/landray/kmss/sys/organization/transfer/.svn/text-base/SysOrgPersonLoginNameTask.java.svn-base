package com.landray.kmss.sys.organization.transfer;

import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * 登录名转小写
 *
 * @author 潘永辉 2021-07-29
 */
public class SysOrgPersonLoginNameTask implements ISysAdminTransferTask {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public SysAdminTransferResult run(SysAdminTransferContext sysAdminTransferContext) {
        String uuid = sysAdminTransferContext.getUUID();
        ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil.getBean("sysAdminTransferTaskService");

        try {
            List<SysAdminTransferTask> list = sysAdminTransferTaskService.getBaseDao().findValue(null, "sysAdminTransferTask.fdUuid='" + uuid + "'", null);
            if (list != null && list.size() > 0) {
                SysAdminTransferTask sysAdminTransferTask = list.get(0);
                if (sysAdminTransferTask.getFdStatus() != 1) {
                    DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
                    Connection conn = null;
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    try {
                        conn = dataSource.getConnection();
                        ps = conn.prepareStatement("SELECT fd_id, fd_login_name FROM sys_org_person WHERE fd_login_name_lower IS NULL");
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            String id = rs.getString(1);
                            String loginName = rs.getString(2);

                            PreparedStatement update_ps = null;
                            try {
                                update_ps = conn.prepareStatement("UPDATE sys_org_person SET fd_login_name = ?, fd_login_name_lower = ? WHERE fd_id = ?");
                                update_ps.setString(1, loginName.trim());
                                update_ps.setString(2, loginName.trim().toLowerCase());
                                update_ps.setString(3, id);
                                update_ps.executeUpdate();
                            } catch (Exception e) {
                                logger.error("登录名转换小写失败：", e);
                            } finally {
                                JdbcUtils.closeStatement(update_ps);
                            }
                        }
                    } catch (Exception e) {
                        logger.error("检查是否执行过旧数据迁移为空异常", e);
                    } finally {
                        JdbcUtils.closeResultSet(rs);
                        JdbcUtils.closeStatement(ps);
                        JdbcUtils.closeConnection(conn);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("执行旧数据迁移为空异常", e);
        }

        return SysAdminTransferResult.OK;
    }

}
