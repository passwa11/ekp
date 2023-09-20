package com.landray.kmss.sys.attachment.jg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

public class SysAttJGAddtionUpdateTimeFunction extends
		AbstractSysAttachmentJGAddtionFunction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttJGAddtionUpdateTimeFunction.class);

	// 更新编辑时间
	@Override
	public void execute(RequestContext request, HttpServletResponse response)
			throws Exception {
		try {
			String fdId = request.getParameter("fdId");
			String modelId = request.getParameter("fdModelId");
			String modelName = request.getParameter("fdModelName");
			String key = request.getParameter("fdKey");
			SysAttMain sysAttMain = getSysAttMain(fdId, modelId, modelName, key,true);
			if (sysAttMain != null && UserUtil.getUser().getFdId().equals(
					sysAttMain.getFdPersonId())) {
				//sysAttMain.setFdLastOpenTime(new Date());
				//this.updateSysAttMain(sysAttMain);
				
				DataSource dataSource = (DataSource) SpringBeanUtil
						.getBean("dataSource");
				Connection connect = null;
				PreparedStatement updateSql = null;
				try {
					connect = dataSource.getConnection();
					connect.setAutoCommit(false);
					updateSql = connect
							.prepareStatement("update sys_att_main set fd_last_open_time=? where fd_id=?");
					updateSql.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
					updateSql.setString(2, sysAttMain.getFdId());
					updateSql.execute();
					connect.commit();
				}catch (SQLException ex) {
					logger.error("更新在线编辑时间出错，错误信息：" + ex);
					if(connect!=null) {
                        connect.rollback();
                    }
				}finally{
					JdbcUtils.closeStatement(updateSql);
					JdbcUtils.closeConnection(connect);						
				}
			}
		} catch (Exception e) {
			logger.error("更新在线编辑时间出错，错误信息：" + e);
		}
	}

}
