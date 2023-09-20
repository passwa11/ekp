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
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;

public class SysAttJGAddtionIsFirstFunction extends
		AbstractSysAttachmentJGAddtionFunction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttJGAddtionIsFirstFunction.class);

	private ISysOrgCoreService sysOrgCoreService = null;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	// 判断是否只有当前用户在线编辑
	@Override
	public void execute(RequestContext request, HttpServletResponse response)
			throws Exception {
		String isEdit = "0";
		String editOrgName = "";
		try {
			String fdId = request.getParameter("fdId");
			String modelId = request.getParameter("fdModelId");
			String modelName = request.getParameter("fdModelName");
			String key = request.getParameter("fdKey");
			SysAttMain sysAttMain = getSysAttMain(fdId, modelId, modelName, key,true);
			if (sysAttMain != null) {
				if (StringUtil.isNotNull(sysAttMain.getFdPersonId())
						&& !sysAttMain.getFdPersonId().equals(
								UserUtil.getUser().getFdId())) {
					Date lastOpenTime = sysAttMain.getFdLastOpenTime();
					if (lastOpenTime != null) {
						Date d = new Date();
						long timegap = d.getTime() - lastOpenTime.getTime();
						if (timegap > 60 * 1000) {
                            isEdit = "1";
                        } else {
							SysOrgElement editOrg = sysOrgCoreService
									.findByPrimaryKey(sysAttMain
											.getFdPersonId());
							editOrgName = editOrg.getFdName();
						}
					}
				} else {
					isEdit = "1";
					//sysAttMain.setFdPersonId(UserUtil.getUser().getFdId());
					//sysAttMain.setFdLastOpenTime(new Date());
					
					DataSource dataSource = (DataSource) SpringBeanUtil
							.getBean("dataSource");
					Connection connect = null;
					PreparedStatement updateSql = null;
					try {
						connect = dataSource.getConnection();
						connect.setAutoCommit(false);
						updateSql = connect
								.prepareStatement("update sys_att_main set fd_person_id=?, fd_last_open_time=? where fd_id=?");
						updateSql.setString(1, UserUtil.getUser().getFdId());
						updateSql.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
						updateSql.setString(3, sysAttMain.getFdId());
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
					
					//this.updateSysAttMain(sysAttMain);
				}
			} else {
				isEdit = "1";
			}
			JSONObject json = new JSONObject();
			json.put("isEdit", isEdit);
			json.put("editOrgName", editOrgName);
			response.setCharacterEncoding("utf-8");
			response.getOutputStream().write(json.toString().getBytes("UTF-8"));
		} catch (Exception e) {
			logger.error("判断是否只有当前用户在线编辑,出错，错误信息：" + e);
		}
	}

}
