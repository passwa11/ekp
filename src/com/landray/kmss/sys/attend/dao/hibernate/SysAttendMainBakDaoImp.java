package com.landray.kmss.sys.attend.dao.hibernate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.hibernate.query.NativeQuery;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attend.dao.ISysAttendMainBakDao;
import com.landray.kmss.sys.attend.dao.SQLInfo;
import com.landray.kmss.sys.attend.dao.SQLParameter;
import com.landray.kmss.sys.attend.model.SysAttendMainBak;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-01
 */
public class SysAttendMainBakDaoImp extends BaseDaoImp
		implements ISysAttendMainBakDao {

	@Override
	public Page findPage(SQLInfo sqlInfo) throws Exception {
		Page page = null;

		int total = sqlInfo.getRowSize();
		if (sqlInfo.isGetCount()) {
			TimeCounter.logCurrentTime("Dao-findPage-count", true, getClass());
			sqlInfo.setGettingCount(true);
			NativeQuery q = createSqlQuery(sqlInfo);
			total = ((Number) q.uniqueResult()).intValue();
			TimeCounter.logCurrentTime("Dao-findPage-count", false, getClass());
		}
		if (total > 0) {
			sqlInfo.setGettingCount(false);
			String order = sqlInfo.getOrderBy();
			if (StringUtil.isNotNull(order)) {
				Pattern p = Pattern.compile(",\\s*fd_id\\s*");
				if (!p.matcher("," + order).find()) {
					sqlInfo.setOrderBy(order + ",fd_id desc");
				}
			}
			page = new Page();
			page.setRowsize(sqlInfo.getRowSize());
			page.setPageno(sqlInfo.getPageNo());
			page.setTotalrows(total);
			page.excecute();
			NativeQuery q = createSqlQuery(sqlInfo);
			q.addEntity(sqlInfo.getEntityClass());
			q.setFirstResult(page.getStart());
			q.setMaxResults(page.getRowsize());
			page.setList(q.list());
		}
		if (page == null) {
			page = Page.getEmptyPage();
		}
		TimeCounter.logCurrentTime("Dao-findPage-list", false, getClass());
		return page;
	}

	private NativeQuery createSqlQuery(SQLInfo sqlInfo) {
		NativeQuery query = super.getSession().createNativeQuery(buildQuerySQL(sqlInfo));
		query = setSqlParameter(query, sqlInfo.getParameterList());
		if (sqlInfo.isCacheable()) {
			query.setCacheable(true);
		}
		return query;
	}

	private String buildQuerySQL(SQLInfo sqlInfo) {
		StringBuffer sql = new StringBuffer();
		if (sqlInfo.isGettingCount()) {
			sql.append("select count(*) ");
			if (StringUtil.isNull(sqlInfo.getFromBlock())) {
				sql.append("from " + sqlInfo.getTableName() + " ");
			} else {
				sql.append("from " + sqlInfo.getFromBlock() + " ");
			}
		} else {
			if (StringUtil.isNull(sqlInfo.getSelectBlock())) {
				sql.append("select * ");
			} else {
				sql.append(sqlInfo.getSelectBlock());
			}

			if (StringUtil.isNull(sqlInfo.getFromBlock())) {
				sql.append("from " + sqlInfo.getTableName() + " ");
			} else {
				sql.append("from " + sqlInfo.getFromBlock() + " ");
			}
		}
		if (!StringUtil.isNull(sqlInfo.getJoinBlock())) {
			sql.append(sqlInfo.getJoinBlock() + " ");
		}
		if (!StringUtil.isNull(sqlInfo.getWhereBlock())) {
			sql.append("where " + sqlInfo.getWhereBlock());
		}
		if (!sqlInfo.isGettingCount()
				&& !StringUtil.isNull(sqlInfo.getOrderBy())) {
			sql.append(" order by " + sqlInfo.getOrderBy());
		}
		return sql.toString();
	}

	private NativeQuery setSqlParameter(NativeQuery query,
			List<SQLParameter> parameterList) {
		for (SQLParameter parameter : parameterList) {
			if (parameter.getType() == null) {
				if (parameter.getValue() instanceof Collection<?>) {
					Collection<?> value = (Collection<?>) parameter.getValue();
					query.setParameterList(parameter.getName(), value);
				} else {
					query.setParameter(parameter.getName(),
							parameter.getValue());
				}
			} else {
				if (parameter.getValue() instanceof Collection<?>) {
					Collection<?> value = (Collection<?>) parameter.getValue();
					query.setParameterList(parameter.getName(), value,
							parameter.getType());
				} else {
					query.setParameter(parameter.getName(),
							parameter.getValue(), parameter.getType());
				}
			}
		}
		return query;
	}

	@Override
	public SysAttendMainBak findByPrimaryKey(String id,
											 Map<String, Object> params) throws Exception {
		TimeCounter.logCurrentTime("Dao-findByPrimaryKey", true, getClass());
		SysAttendMainBak rtnVal = null;
		if (StringUtil.isNotNull(id) && !params.isEmpty()) {
			Class entity = (Class) params.get("entity");
			String tableName = (String) params.get("tableName");
			if (entity != null && StringUtil.isNotNull(tableName)) {
				NativeQuery query = super.getSession().createNativeQuery(
						"select * from " + tableName + " where fd_id='" + id
								+ "'")
						.addEntity(entity);
				List list = query.list();
				if (!list.isEmpty()) {
					rtnVal = (SysAttendMainBak) list.get(0);
				}
			}
		}
		TimeCounter.logCurrentTime("Dao-findByPrimaryKey", false, getClass());
		return rtnVal;
	}

}
