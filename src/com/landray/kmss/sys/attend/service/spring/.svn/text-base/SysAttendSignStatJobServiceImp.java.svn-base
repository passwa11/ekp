package com.landray.kmss.sys.attend.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendSignStatJobService;
import com.landray.kmss.sys.attend.util.AttendPersonUtil;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SysAttendSignStatJobServiceImp
		implements ISysAttendSignStatJobService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendSignStatJobServiceImp.class);
	private ISysAttendCategoryService sysAttendCategoryService;
	private ISysOrgCoreService sysOrgCoreService;


	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}
	public void setSysAttendCategoryService(
			ISysAttendCategoryService sysAttendCategoryService) {
		this.sysAttendCategoryService = sysAttendCategoryService;
	}

	@Override
	public void stat(Date date) throws Exception {
		logger.debug("start to sign stat job...");
		Date beginTime = AttendUtil.getDate(date, 0);
		Date endTime = AttendUtil.getDate(date, 1);

		stat(null, beginTime);
		logger.debug("end to sign stat job...");

	}

	@Override
	public void execute(SysQuartzJobContext jobContext) throws Exception {
		// 实时统计当天数据
		stat(jobContext, DateUtil.getDate(0));
	}


	private void stat(SysQuartzJobContext jobContext, Date beginTime)
			throws Exception {

		// 1.所有当天需要打卡的签到组
		List<SysAttendCategory> cateList = getCategorys();
		for (SysAttendCategory cate : cateList) {
			List fdTargets = cate.getFdTargets();
			List fdExcTargets = cate.getFdExcTargets();
			List targetIds = AttendPersonUtil.expandToPersonIds(fdTargets);

			if (!fdExcTargets.isEmpty()) {
				List excTargetIds = AttendPersonUtil.expandToPersonIds(fdExcTargets);
				targetIds.removeAll(excTargetIds);
			}
			// 防止重复初始化
			List signOrgList = this.getSignStatUsers(beginTime, cate.getFdId());
			List<String> _tmpOrgIdList = new ArrayList<String>();
			_tmpOrgIdList.addAll(targetIds);
			if (!signOrgList.isEmpty()) {
				_tmpOrgIdList.removeAll(signOrgList);
			}
			if (!_tmpOrgIdList.isEmpty()) {
				// 初始化统计表
				addBatch(beginTime, cate.getFdId(), _tmpOrgIdList);
			}

			// 更新统计数据
			Map statInfo = getSignStatInfo(beginTime, cate.getFdId(),
					targetIds);
			updateBatch(beginTime, cate.getFdId(), statInfo);
		}

	}

	// 用户签到统计信息
	private Map getSignStatInfo(Date statDate, String categoryId,
			List<String> list) {
		Map<String, Integer> userInfo = new HashMap<String, Integer>();
		if (list.isEmpty()) {
			return userInfo;
		}
		// 用户组分割
		int maxCount = 500;
		List<List> groupLists = new ArrayList<List>();
		if (list.size() <= maxCount) {
			groupLists.add(list);
		} else {
			groupLists = AttendUtil.splitList(list, maxCount);
		}
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		for (int i = 0; i < groupLists.size(); i++) {
			List orgList = groupLists.get(i);
			String sql = "select count(fd_id),doc_creator_id from sys_attend_main where doc_create_time>=:beginTime and doc_create_time<:endTime"
					+ " and fd_category_id=:categoryId and "
					+ HQLUtil.buildLogicIN("doc_creator_id", orgList)
					+ " group by doc_creator_id";
			List countList = baseDao.getHibernateSession().createNativeQuery(sql.toString()).setDate("beginTime", AttendUtil.getDate(statDate, 0)).setDate("endTime", AttendUtil.getDate(statDate, 1)).setString("categoryId", categoryId).list();
			for (int k = 0; k < countList.size(); k++) {
				Object[] record = (Object[]) countList.get(k);
				String count = record[0].toString();
				String docCreatorId = record[1].toString();
				userInfo.put(docCreatorId, Integer.valueOf(count));
			}
		}
		return userInfo;
	}

	private void addBatch(Date statDate, String categoryId, List<String> list)
			throws Exception {
		if (list.isEmpty()) {
			return;
		}
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement insert = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			int loop = 0;
			insert = conn
					.prepareStatement(
							"insert into sys_attend_sign_stat(fd_id,fd_date,doc_create_time,fd_sign_count,fd_category_id,doc_creator_id) values(?,?,?,?,?,?)");

			for (String id : list) {
				if (loop > 0 && (loop % 2000 == 0)) {
					insert.executeBatch();
					conn.commit();
				}
				insert.setString(1, IDGenerator.generateID());
				insert.setTimestamp(2, new Timestamp(
						AttendUtil.getDate(statDate, 0).getTime()));
				insert.setTimestamp(3, new Timestamp(new Date().getTime()));
				insert.setInt(4, 0);
				insert.setString(5, categoryId);
				insert.setString(6, id);
				insert.addBatch();
				loop++;
			}
			insert.executeBatch();
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage(), ex);
			conn.rollback();
			throw ex;
		} finally {
			JdbcUtils.closeStatement(insert);
			JdbcUtils.closeConnection(conn);
		}
	}

	private void updateBatch(Date statDate, String categoryId,
			Map<String, Integer> statInfo)
			throws Exception {
		if (statInfo.isEmpty()) {
			return;
		}

		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement update = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			int loop = 0;
			update = conn
					.prepareStatement(
							"update sys_attend_sign_stat set fd_sign_count=? where fd_date=? "
									+ " and fd_category_id=? and doc_creator_id=?");
			Set<String> set = statInfo.keySet();
			for (String id : set) {
				if (loop > 0 && (loop % 2000 == 0)) {
					update.executeBatch();
					conn.commit();
				}
				update.setInt(1, statInfo.get(id));
				update.setTimestamp(2, new Timestamp(
						AttendUtil.getDate(statDate, 0).getTime()));
				update.setString(3, categoryId);
				update.setString(4, id);
				update.addBatch();
				loop++;
			}
			update.executeBatch();
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage(), ex);
			conn.rollback();
			throw ex;
		} finally {
			JdbcUtils.closeStatement(update);
			JdbcUtils.closeConnection(conn);
		}
	}

	public List getSignStatUsers(Date date, String categoryId)
			throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		String orgSql = "select DISTINCT doc_creator_id from sys_attend_sign_stat where fd_date >=? and fd_date<? and fd_category_id=?";
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> orgList = new ArrayList<String>();
		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(orgSql);
			statement.setTimestamp(1,
					new Timestamp(AttendUtil.getDate(date, 0).getTime()));
			statement.setTimestamp(2,
					new Timestamp(AttendUtil.getDate(date, 1).getTime()));
			statement.setString(3, categoryId);
			rs = statement.executeQuery();
			while (rs.next()) {
				orgList.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(conn);
		}

		return orgList;
	}

	// 获取当天需要打卡的签到组
	private List<SysAttendCategory> getCategorys() throws Exception {
		List<SysAttendCategory> newList = new ArrayList<SysAttendCategory>();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setRowSize(1000);
		hqlInfo.setWhereBlock(
				"sysAttendCategory.fdStatus=1 and sysAttendCategory.fdType=2 and (sysAttendCategory.fdAppId='' or sysAttendCategory.fdAppId is null)");
		// 签到组
		List<SysAttendCategory> categories = sysAttendCategoryService
				.findList(hqlInfo);
		com.alibaba.fastjson.JSONArray list = sysAttendCategoryService
				.filterAttendCategory(categories, new Date(), false, null);
		for (int i = 0; i < categories.size(); i++) {
			SysAttendCategory cate = categories.get(i);
			for (int k = 0; k < list.size(); k++) {
				com.alibaba.fastjson.JSONObject json = (com.alibaba.fastjson.JSONObject) list.get(k);
				if (cate.getFdId().equals(json.getString("fdId"))) {
					newList.add(cate);
					break;
				}
			}
		}
		return newList;
	}


}
