package com.landray.kmss.sys.attend.service.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.springframework.jdbc.support.JdbcUtils;

import com.landray.kmss.sys.attend.model.SysAttendConfig;
import com.landray.kmss.sys.attend.service.ISysAttendConfigService;
import com.landray.kmss.sys.attend.service.ISysAttendSynDingTransferJobService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 原始考勤记录转移到备份表
 * 
 * @author tancx
 * @version 1.0 2019-12-16
 */
public class SysAttendSynDingTransferJobServiceImp
		implements ISysAttendSynDingTransferJobService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendSynDingTransferJobServiceImp.class);

	private ISysAttendConfigService sysAttendConfigService;

	public void setSysAttendConfigService(
			ISysAttendConfigService sysAttendConfigService) {
		this.sysAttendConfigService = sysAttendConfigService;
	}

	@Override
	public void execute(SysQuartzJobContext jobContext) throws Exception {
		logger.debug("SysAttendSynDingTransferJob start...");
		SysAttendConfig config = sysAttendConfigService.getSysAttendConfig();
		if (config != null && Boolean.TRUE.equals(config.getFdIsRemain())
				&& config.getFdRemainMonth() != null
				&& config.getFdRemainMonth() > 0) {
			Date endTime = AttendUtil.getMonth(new Date(), -config.getFdRemainMonth());
			// 1.所有考勤用户
			List orgList = this.getSignUser(endTime);
			if (!orgList.isEmpty()) {
				// 用户组分割
				int maxCount = 500;
				List<List> groupLists = new ArrayList<List>();
				if (orgList.size() <= maxCount) {
					groupLists.add(orgList);
				} else {
					groupLists = AttendUtil.splitList(orgList, maxCount);
				}
				for (int i = 0; i < groupLists.size(); i++) {
					List tmpList = groupLists.get(i);
					// 考勤记录复制到备份表
					transferMain(tmpList, endTime);
					// 删除原来的考勤记录（原始表）
					deleteMain(tmpList, endTime);
				}
			}
			// 按年分割备份表
			divideMainBak();
		}
		logger.debug("SysAttendSynDingTransferJob end...");
	}

	/**
	 * 按年分割备份表
	 *
	 * @throws Exception
	 */
	private void divideMainBak() throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;


		Calendar cal = Calendar.getInstance();
		String dialect = ResourceUtil.getKmssConfigString("hibernate.dialect")
				.toLowerCase();
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			stm = conn.prepareStatement(
					"select min(fd_user_check_time),max(fd_user_check_time) from sys_attend_syn_ding_bak");
			rs = stm.executeQuery();
			Timestamp start = null;
			Timestamp end = null;
			while (rs.next()) {
				start = rs.getTimestamp(1);
				end = rs.getTimestamp(2);
			}
			if (start != null && end != null) {
				Date startTime = new Date(start.getTime());
				Date endTime = new Date(end.getTime());
				cal.setTime(startTime);
				while (cal.getTime().compareTo(endTime) <= 0) {
					Date _start = cal.getTime();
					int year = cal.get(Calendar.YEAR);
					cal.add(Calendar.YEAR, 1);
					cal.set(Calendar.DAY_OF_YEAR, 1);
					Date _end = AttendUtil.getDate(cal.getTime(), 0);

					PreparedStatement select = null;
					ResultSet result = null;

					List<String> idList = new ArrayList<String>();
					try {
						// 按年查找数据
						select = conn.prepareStatement(
								"select fd_id from sys_attend_syn_ding_bak where fd_user_check_time>=? and fd_user_check_time<?");
						select.setTimestamp(1, new Timestamp(_start.getTime()));
						select.setTimestamp(2, new Timestamp(_end.getTime()));
						result = select.executeQuery();
						while (result.next()) {
							String id = result.getString(1);
							if (StringUtil.isNotNull(id)) {
								idList.add(id);
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
					} finally {
						JdbcUtils.closeResultSet(result);
						JdbcUtils.closeStatement(select);
					}

					String logId = null;
					if (!idList.isEmpty()) {
						PreparedStatement selectLog = null;
						ResultSet resultLog = null;
						try {
							// 按年建表
							selectLog = conn
									.prepareStatement("select fd_id from sys_attend_syn_ding_baklog where fd_year=?");
							selectLog.setString(1, year + "");
							resultLog = selectLog.executeQuery();
							if (resultLog.next()) {
								logId = resultLog.getString(1);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}finally {
							JdbcUtils.closeResultSet(resultLog);
							JdbcUtils.closeStatement(selectLog);
						}
						String tableName = "sys_attend_syn_ding_bak_"
								+ year;
						Timestamp now = new Timestamp(System.currentTimeMillis());
						if (StringUtil.isNull(logId)) {
							PreparedStatement addLog = null;
							try {
								// 写入日志
								addLog = conn.prepareStatement(
										"insert into sys_attend_syn_ding_baklog(fd_id,fd_year,fd_create_time,fd_opr_time,fd_table_name) values(?,?,?,?,?)");
								addLog.setString(1, IDGenerator.generateID());
								addLog.setString(2, year + "");
								addLog.setTimestamp(3, now);
								addLog.setTimestamp(4, now);
								addLog.setString(5, tableName);
								addLog.executeUpdate();
							} catch (Exception e) {
								// TODO: handle exception
							}finally {
								JdbcUtils.closeStatement(addLog);
							}

							PreparedStatement create = null;
							try {
								String createTable = "";
								if (dialect.indexOf("sqlserver") > 0) {
									createTable = "select * into " + tableName
											+ " from sys_attend_syn_ding_bak where 1=2";
								}else if (dialect.indexOf("mysql") > 0) {
									createTable = "create table " + tableName
											+ " like sys_attend_syn_ding_bak";
								} else {
									createTable = "create table " + tableName
											+ " as select * from sys_attend_syn_ding_bak where 1=2";
								}
								create = conn.prepareStatement(createTable);
								create.execute();
							} catch (Exception e) {
								// TODO: handle exception
							} finally {
								// TODO: handle finally clause
								JdbcUtils.closeStatement(create);
							}
							PreparedStatement alter = null;
							try {
								// 主键
								String alterTable = "alter table " + tableName
										+ " add constraint pk_" + tableName
										+ " primary key (fd_id)";
								alter = conn.prepareStatement(alterTable);
								alter.execute();
							} catch (Exception e) {
								// TODO: handle exception
							} finally {
								// TODO: handle finally clause
								JdbcUtils.closeStatement(alter);
							}
							PreparedStatement index = null;
							try {
								// 索引
								String addIndex = "create index idx_signTime_" + tableName + " on " + tableName
										+ " (fd_user_check_time)";
								index = conn.prepareStatement(addIndex);
								index.execute();
							} catch (Exception e) {
								// TODO: handle exception
							} finally {
								// TODO: handle finally clause
								JdbcUtils.closeStatement(index);
							}
						} else {
							PreparedStatement updateLog = null;

							try {
								// 更新日志
								updateLog = conn.prepareStatement(
										"update sys_attend_syn_ding_baklog set fd_opr_time=? where fd_id=?");
								updateLog.setTimestamp(1, now);
								updateLog.setString(2, logId);
								updateLog.executeUpdate();
							} catch (Exception e) {
								// TODO: handle exception
							} finally {
								// TODO: handle finally clause
								JdbcUtils.closeStatement(updateLog);
							}
						}

						// 用户组分割
						int maxCount = 1000;
						List<List> groupLists = new ArrayList<List>();
						if (idList.size() <= maxCount) {
							groupLists.add(idList);
						} else {
							groupLists = AttendUtil.splitList(idList,
									maxCount);
						}
						for (int i = 0; i < groupLists.size(); i++) {
							PreparedStatement insertInto = null;
							PreparedStatement delete = null;
							try {
								// 复制数据
								List tmpList = groupLists.get(i);
								String sql = "insert into " + tableName
										+ " select * from sys_attend_syn_ding_bak "
										+ "where " + HQLUtil.buildLogicIN(
										"fd_id", tmpList);
								insertInto = conn.prepareStatement(sql);
								insertInto.execute();
								// 删除原来数据
								delete = conn.prepareStatement(
										"delete from sys_attend_syn_ding_bak where "
												+ HQLUtil.buildLogicIN(
												"fd_id", tmpList));
								delete.executeUpdate();
							} catch (Exception e) {
								throw e;
							} finally {
								JdbcUtils.closeStatement(insertInto);
								JdbcUtils.closeStatement(delete);
							}
						}
					}
				}
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			logger.error("按年分割备份表失败:" + e.getMessage(), e);
			throw e;
		} finally {
			JdbcUtils.closeStatement(stm);
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeConnection(conn);
		}
	}

	private void transferMain(List orgList, Date endTime) throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			String props = "fd_id, fd_ding_id, fd_group_id, fd_plan_id, fd_work_date, fd_user_id, fd_check_type, "
					+ "fd_source_type, fd_time_result, fd_location_result, fd_approve_id, fd_procinst_id, fd_base_check_time, "
					+ "fd_user_check_time, fd_class_id, fd_is_legal, fd_location_method, fd_device_id, fd_user_address, "
					+ "fd_user_longitude, fd_user_latitude, fd_user_accuracy, fd_user_ssid, fd_user_mac_addr, fd_plan_check_time, "
					+ "fd_base_address, fd_base_longitude, fd_base_latitude, fd_base_accuracy, fd_base_ssid, fd_base_mac_addr, "
					+ "fd_outside_remark, fd_person_id, fd_invalid_record_type,fd_group_name,fd_location_title,fd_wifi_name";
			String sql = "insert into sys_attend_syn_ding_bak (" + props
					+ ") select " + props + " from sys_attend_syn_ding "
					+ "where fd_user_check_time<? and "
					+ HQLUtil.buildLogicIN("fd_person_id", orgList);
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setTimestamp(1, new Timestamp(endTime.getTime()));
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("转存考勤记录失败：" + e.getMessage(), e);
			throw e;
		} finally {
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(conn);
		}
	}

	private void deleteMain(List orgList, Date endTime) throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			String sql = "delete from sys_attend_syn_ding "
					+ "where fd_user_check_time<? and "
					+ HQLUtil.buildLogicIN("fd_person_id", orgList);
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setTimestamp(1, new Timestamp(endTime.getTime()));
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(conn);
		}
	}

	private List getSignUser(Date endTime) {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		String orgSql = "select DISTINCT fd_person_id from sys_attend_syn_ding "
				+ "where fd_user_check_time<? ";
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> orgList = new ArrayList<String>();
		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(orgSql);
			statement.setTimestamp(1, new Timestamp(endTime.getTime()));
			rs = statement.executeQuery();
			while (rs.next()) {
				String fd_person_id = rs.getString(1);
				if(StringUtil.isNotNull(fd_person_id)) {
					orgList.add(fd_person_id);
				}
			}
		} catch (Exception e) {
			logger.error("获取原来的原始考勤用户：" + e.getMessage(), e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(conn);
		}
		return orgList;
	}

}
