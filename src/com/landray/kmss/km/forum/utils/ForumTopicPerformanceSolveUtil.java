package com.landray.kmss.km.forum.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.landray.kmss.util.HibernateUtil;
import org.springframework.jdbc.support.JdbcUtils;

import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.forum.model.KmForumTopic;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 解决论坛性能问题
 *
 */
public class ForumTopicPerformanceSolveUtil {
	/**
	 * 用于构建关联中间表sql查询
	 * @param table
	 * 	中间表名，它的值仅为：km_forum_visit, km_forum_poster, km_forum_manager
	 * @param alias
	 * @return
	 */
	private static String buildForumRelatedSql(String table, String alias, String column) {
		List<String> orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		StringBuilder sqlBuilder = new StringBuilder(" SELECT  kmforumtop0_.fd_id ids	");
		sqlBuilder.append("FROM km_forum_topic kmforumtop0_	");
		sqlBuilder.append("	LEFT OUTER JOIN km_forum_category kmforumcat2_ ON kmforumtop0_.fd_forum_id = kmforumcat2_.fd_id	");
		sqlBuilder.append("	LEFT OUTER JOIN " + table + " " + alias + " ON " + alias + ".fd_forum_id=kmforumcat2_.fd_id	");
		sqlBuilder.append("WHERE kmforumtop0_.fd_status != '" + SysDocConstant.DOC_STATUS_DRAFT + "' ");
		sqlBuilder.append("	AND ( " + HQLUtil.buildLogicIN("kmforumtop0_.fd_poster_id" , orgIds) + "	");
		sqlBuilder.append("			OR " + HQLUtil.buildLogicIN(alias+"."+ column, orgIds) + "	");
		//兼容pgsql #159177
		String visitFlag = HibernateUtil.toBooleanValueString(Boolean.TRUE);
		sqlBuilder.append("			OR kmforumcat2_.auth_visit_flag = "+visitFlag+" ) ");
		return sqlBuilder.toString();
	}
	
	/**
	 * 获取当前用户权限范围内的所有话题pk
	 * @return
	 */
	private static String buildGetAllTopicIdsSql() {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(buildForumRelatedSql("km_forum_visit", "visit", "fd_visit_id"));
		sqlBuilder.append(" UNION ");
		sqlBuilder.append(buildForumRelatedSql("km_forum_poster", "poster", "fd_poster_id"));
		sqlBuilder.append(" UNION ");
		sqlBuilder.append(buildForumRelatedSql("km_forum_manager", "manager", "fd_manager_id"));
		return sqlBuilder.toString();
	}
	
	private static List<Map<String, Object>> getQueryResult(String sql, int rowSize, String... columnNames) throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(sql);
			rs = statement.executeQuery();
			int index = 0;
			while (rs.next()) {
				if(index < rowSize) {
					Map<String, Object> row = new HashMap<String, Object>();
					for(String column : columnNames) {
						Object columnVal = rs.getObject(column);
						row.put(column, columnVal == null ? "" : columnVal);
					}
					result.add(row);
					++index;
				}
				else {
					break;
				}
			}
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(conn);
		}
		return result;
	}
	
	public static int getTopicTotal4NonAdmin() throws Exception {
		StringBuilder sqlBuilder = new StringBuilder("SELECT count(1) topicCount ");
		sqlBuilder.append("FROM km_forum_topic topic INNER JOIN ( ");
		sqlBuilder.append(buildGetAllTopicIdsSql());
		sqlBuilder.append(" ) derived on topic.fd_id = derived.ids");
		List<Map<String, Object>> result = getQueryResult(sqlBuilder.toString(), 1, "topicCount");
		if(result.isEmpty()) {
			return 0;
		}
		Object topicCount = result.get(0).get("topicCount");
		return topicCount == null ? 0 : Integer.valueOf(topicCount.toString());
	}
	
	public static List<KmForumTopic> getNewTopics() throws Exception{
		StringBuilder sqlBuilder = new StringBuilder("SELECT topic.fd_id, topic.doc_subject, topic.fd_reply_count, topic.fd_sticked, topic.fd_last_post_time ");
		sqlBuilder.append("FROM km_forum_topic topic INNER JOIN ( ");
		sqlBuilder.append(buildGetAllTopicIdsSql());
		sqlBuilder.append(" ) derived on topic.fd_id = derived.ids ");
		sqlBuilder.append("ORDER BY topic.fd_sticked DESC, topic.fd_last_post_time DESC ");
		List<Map<String, Object>> result = getQueryResult(sqlBuilder.toString(), 7, "fd_id", "doc_subject", "fd_reply_count");
		List<KmForumTopic> newTopics = new ArrayList<KmForumTopic>();
		for(Map<String, Object> row : result) {
			KmForumTopic topic = new KmForumTopic();
			topic.setFdId(row.get("fd_id").toString());
			topic.setDocSubject(row.get("doc_subject").toString());
			topic.setFdReplyCount(Integer.valueOf(row.get("fd_reply_count").toString()));
			newTopics.add(topic);
		}
		return newTopics;
	}
}