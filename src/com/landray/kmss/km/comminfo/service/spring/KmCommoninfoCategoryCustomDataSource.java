package com.landray.kmss.km.comminfo.service.spring;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.taglib.xform.ICustomizeDataSourceWithRequest;

/**
 * 
 * @author lixp 2015-10-26
 * 
 */
public class KmCommoninfoCategoryCustomDataSource implements
		ICustomizeDataSourceWithRequest {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(KmCommoninfoCategoryCustomDataSource.class);

	private HttpServletRequest httpServletRequest = null;

	@Override
	public void setRequest(ServletRequest request) {
		this.httpServletRequest = (HttpServletRequest) request;
	}

	@Override
	public String getDefaultValue() {
		return "";
	}

	@Override
	public Map<String, String> getOptions() {
		Map<String, String> resultMap = new HashMap<String, String>();
		String userId = String.valueOf(httpServletRequest
				.getAttribute("userId"));
		String sqlQuery = getNativeQuery(userId);
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = ((DataSource) SpringBeanUtil.getBean("dataSource"))
					.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);
			while (resultSet.next()) {
				resultMap.put(resultSet.getObject(1).toString(), resultSet
						.getObject(2).toString());
			}
		} catch (SQLException e) {
			logger.error("error in sql:" + sqlQuery);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
					resultSet = null;
				} catch (Exception e) {
				}
			}
			if (statement != null) {
				try {
					statement.close();
					statement = null;
				} catch (Exception e) {
				}
			}
			if (connection != null) {
				try {
					connection.close();
					connection = null;
				} catch (Exception e) {
				}
			}
		}
		return resultMap;
	}

	private String getNativeQuery(String userId) {
		String resultSQL = "SELECT fd_id,fdname FROM km_comminfo_category WHERE fd_id IN (SELECT fd_category_id FROM km_comminfo_category_editor WHERE auth_editor_id IN ("
				+ getAuthEditIds(userId) + "))";
		return resultSQL;
	}

	@SuppressWarnings("unchecked")
	private String getAuthEditIds(String userId) {
		StringBuffer sb = new StringBuffer("");
		ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
				.getBean("sysOrgPersonService");
		SysOrgPerson person;
		try {
			person = (SysOrgPerson) sysOrgPersonService
					.findByPrimaryKey(userId);
			if (person != null) {
				List<SysOrgPost> postsList = person.getFdPosts();
				for (SysOrgPost post : postsList) {
					sb.append("'").append(post.getFdId()).append("',");
				}
				String[] parentIds = person.getFdHierarchyId().split("x");
				for (String parentId : parentIds) {
					if (StringUtil.isNotNull(parentId)) {
						sb.append("'").append(parentId).append("',");
					}
				}
				sb.append("'").append(userId).append("'");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
