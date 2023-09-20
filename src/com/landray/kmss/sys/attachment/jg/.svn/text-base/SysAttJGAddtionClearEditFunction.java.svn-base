package com.landray.kmss.sys.attachment.jg;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;
import org.slf4j.Logger;
import org.springframework.jdbc.support.JdbcUtils;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SysAttJGAddtionClearEditFunction extends
		AbstractSysAttachmentJGAddtionFunction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttJGAddtionClearEditFunction.class);

	// 清除在线编辑信息
	@Override
	public void execute(RequestContext request, HttpServletResponse response)
			throws Exception {
		try {
			String fdId = request.getParameter("fdId");
			String modelId = request.getParameter("fdModelId");
			String modelName = request.getParameter("fdModelName");
			String key = request.getParameter("fdKey");
			SysAttMain sysAttMain = getSysAttMain(fdId, modelId, modelName, key,true);
			if (sysAttMain != null
					&& UserUtil.getUser().getFdId().equals(
							sysAttMain.getFdPersonId())) {
//				sysAttMain.setFdPersonId(null);
//				sysAttMain.setFdLastOpenTime(null);
//				this.updateSysAttMain(sysAttMain);

				DataSource dataSource = (DataSource) SpringBeanUtil
						.getBean("dataSource");
				Connection connect = null;
				PreparedStatement updateSql = null;
				try {
					connect = dataSource.getConnection();
					connect.setAutoCommit(false);
					updateSql = connect
							.prepareStatement("update sys_att_main set fd_last_open_time=?,fd_person_id=? where fd_id=?");
					updateSql.setTimestamp(1, null);
					updateSql.setString(2, null);
					updateSql.setString(3, sysAttMain.getFdId());
					updateSql.execute();
					connect.commit();
				}catch (SQLException ex) {
					logger.error("清除在线编辑信息出错，错误信息：" + ex);
					if(connect!=null) {
                        connect.rollback();
                    }
				}finally{
					JdbcUtils.closeStatement(updateSql);
					JdbcUtils.closeConnection(connect);
				}
			}
		} catch (Exception e) {
			logger.error("清除在线编辑信息出错，错误信息：" + e);
		}
	}

}
