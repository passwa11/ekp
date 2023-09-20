package com.landray.kmss.sys.attend.service.spring;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.springframework.jdbc.support.JdbcUtils;

import com.landray.kmss.sys.attend.model.SysAttendConfig;
import com.landray.kmss.sys.attend.service.ISysAttendConfigService;
import com.landray.kmss.sys.attend.service.ISysAttendMainTransferJobService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 考勤记录转移到备份表
 *
 * @author cuiwj
 * @version 1.0 2018-11-29
 */
public class SysAttendMainTransferJobServiceImp
		implements ISysAttendMainTransferJobService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendMainTransferJobServiceImp.class);

	private ISysAttendConfigService sysAttendConfigService;

	public void setSysAttendConfigService(
			ISysAttendConfigService sysAttendConfigService) {
		this.sysAttendConfigService = sysAttendConfigService;
	}

	@Override
	public void execute(SysQuartzJobContext jobContext) throws Exception {
		logger.debug("SysAttendMainTransferJob start...");
		SysAttendConfig config = sysAttendConfigService.getSysAttendConfig();
		if (config != null && Boolean.TRUE.equals(config.getFdIsRemain())
				&& config.getFdRemainMonth() != null
				&& config.getFdRemainMonth() > 0) {
			Date endTime = AttendUtil.getMonth(new Date(),
					-config.getFdRemainMonth());
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
					// 删除相应的考勤异常（原始表）
					deleteMainExc(tmpList, endTime);
					// 删除原来的考勤记录（原始表）
					deleteMain(tmpList, endTime);
				}
			}
			// 按年分割备份表
			divideMainBak();
		}
		logger.debug("SysAttendMainTransferJob end...");
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
					"select min(doc_create_time),max(doc_create_time) from sys_attend_main_bak");
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
					List<String> idList = null;
					try {
						// 按年查找数据
						select = conn.prepareStatement(
								"select fd_id from sys_attend_main_bak where doc_create_time>=? and doc_create_time<?");
						select.setTimestamp(1, new Timestamp(_start.getTime()));
						select.setTimestamp(2, new Timestamp(_end.getTime()));
						result = select.executeQuery();
						idList = new ArrayList<String>();
						while (result.next()) {
							String id = result.getString(1);
							if (StringUtil.isNotNull(id)) {
								idList.add(id);
							}
						}
					} catch (SQLException throwables) {
						throwables.printStackTrace();
					} finally {
						JdbcUtils.closeResultSet(result);
						JdbcUtils.closeStatement(select);
					}
					if (!idList.isEmpty()) {
						String logId = null;
						PreparedStatement selectLog = null;
						ResultSet resultLog = null;
						try {
							// 按年建表
							selectLog = conn.prepareStatement(
									"select fd_id from sys_attend_main_baklog where fd_year=?");
							selectLog.setString(1, year + "");
							resultLog = selectLog.executeQuery();
							if (resultLog.next()) {
								logId = resultLog.getString(1);
							}
						} catch (SQLException throwables) {
							throwables.printStackTrace();
						} finally {
							JdbcUtils.closeResultSet(resultLog);
							JdbcUtils.closeStatement(selectLog);
						}
						String tableName = "sys_attend_main_bak_" + year;
						Timestamp now = new Timestamp(new Date().getTime());
						if (StringUtil.isNull(logId)) {
							PreparedStatement addLog = null;
							try {
								// 写入日志
								addLog = conn.prepareStatement(
										"insert into sys_attend_main_baklog(fd_id,fd_year,fd_create_time,fd_opr_time,fd_table_name) values(?,?,?,?,?)");
								addLog.setString(1, IDGenerator.generateID());
								addLog.setString(2, year + "");

								addLog.setTimestamp(3, now);
								addLog.setTimestamp(4, now);
								addLog.setString(5, tableName);
								addLog.executeUpdate();
							} catch (SQLException throwables) {
								throwables.printStackTrace();
							} finally {

								JdbcUtils.closeStatement(addLog);
							}
							PreparedStatement create = null;
							try {
								String createTable = "";
								if (dialect.indexOf("sqlserver") > 0) {
									createTable = "select * into " + tableName
											+ " from sys_attend_main_bak where 1=2";
								}else if (dialect.indexOf("mysql") > 0) {
									createTable = "create table " + tableName
											+ " like sys_attend_main_bak";
								} else {
									createTable = "create table " + tableName
											+ " as select * from sys_attend_main_bak where 1=2";
								}
								create = conn.prepareStatement(createTable);
								create.execute();
							} catch (SQLException throwables) {
								throwables.printStackTrace();
							} finally {
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
							} catch (SQLException throwables) {
								throwables.printStackTrace();
							} finally {
								JdbcUtils.closeStatement(alter);
							}
							PreparedStatement index = null;
							try {
								// 索引
								String addIndex = "create index idx_signTime_"
										+ tableName + " on "
										+ tableName + " (doc_create_time)";
								index = conn.prepareStatement(addIndex);
								index.execute();
							} catch (SQLException throwables) {
								throwables.printStackTrace();
							} finally {
								JdbcUtils.closeStatement(index);
							}
						} else {
							PreparedStatement updateLog = null;
							try {
								// 更新日志
								updateLog = conn.prepareStatement(
										"update sys_attend_main_baklog set fd_opr_time=? where fd_id=?");
								updateLog.setTimestamp(1, now);
								updateLog.setString(2, logId);
								updateLog.executeUpdate();
							} catch (SQLException throwables) {
								throwables.printStackTrace();
							} finally {
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
							// 复制数据
							List tmpList = groupLists.get(i);
							try {
								String sql = "insert into " + tableName
										+ " select * from sys_attend_main_bak "
										+ "where " + HQLUtil.buildLogicIN(
										"fd_id", tmpList);
								insertInto = conn.prepareStatement(sql);
								insertInto.execute();
							} catch (Exception e) {
								throw e;
							} finally {
								JdbcUtils.closeStatement(insertInto);
							}
							PreparedStatement delete = null;
							try {
								// 删除原来数据
								delete = conn.prepareStatement(
										"delete from sys_attend_main_bak where "
												+ HQLUtil.buildLogicIN(
												"fd_id", tmpList));
								delete.executeUpdate();
							} catch (SQLException throwables) {
								throwables.printStackTrace();
							} finally {
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
			String props = "fd_id,fd_status,fd_state,doc_create_time,doc_alter_time,"
					+ "fd_alter_record,fd_desc,fd_lng,fd_lat,fd_location,fd_address,fd_work_type,fd_outside,fd_out_target,"
					+ "fd_date_type,fd_wifi_name,fd_wifi_macIp,fd_off_type,doc_creator_hid,fd_device_info,fd_client_info,"
					+ "fd_is_across,fd_lat_lng,fd_app_name,doc_status,fd_work_key,fd_category_id,fd_category_his_id,doc_creator_id,doc_alteror_id,"
					+ "fd_work_id,fd_business_id,fd_outperson_id,fd_sign_patchid";
			String sql = "insert into sys_attend_main_bak (" + props
					+ ") select " + props + " from sys_attend_main "
					+ "where doc_create_time<? and (fd_work_id is not null or fd_work_key is not null) and "
					+ HQLUtil.buildLogicIN("doc_creator_id", orgList);
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

	private void deleteMainExc(List orgList, Date endTime) throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement statement = null;

		ResultSet rs = null;
		List<String> mainExcIds = new ArrayList<String>();
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			String sql = "select e.fd_id from sys_attend_main_exc e left join sys_attend_main m on e.fd_attend_id=m.fd_id "
					+ "where m.doc_create_time<? and (m.fd_work_id is not null or m.fd_work_key is not null) and "
					+ HQLUtil.buildLogicIN("m.doc_creator_id", orgList);
			statement = conn.prepareStatement(sql);
			statement.setTimestamp(1, new Timestamp(endTime.getTime()));
			rs = statement.executeQuery();
			while (rs.next()) {
				mainExcIds.add(rs.getString(1));
			}
			if (!mainExcIds.isEmpty()) {
				PreparedStatement deleteAeditor = null;
				try {
					deleteAeditor = conn.prepareStatement(
							"delete from sys_attend_mainexc_aeditor where "
									+ HQLUtil.buildLogicIN("fd_doc_id",
									mainExcIds));
					deleteAeditor.executeUpdate();
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				} finally {
					JdbcUtils.closeStatement(deleteAeditor);
				}
				PreparedStatement deleteAreader = null;
				try {
					deleteAreader = conn.prepareStatement(
							"delete from sys_attend_mainexc_areader where "
									+ HQLUtil.buildLogicIN("fd_doc_id",
									mainExcIds));
					deleteAreader.executeUpdate();
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				} finally {
					JdbcUtils.closeStatement(deleteAreader);
				}
				PreparedStatement deleteEditors = null;
				try {
					deleteEditors = conn.prepareStatement(
							"delete from sys_attend_mainexc_editors where "
									+ HQLUtil.buildLogicIN("fd_doc_id",
									mainExcIds));
					deleteEditors.executeUpdate();
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				} finally {
					JdbcUtils.closeStatement(deleteEditors);
				}
				PreparedStatement deleteReaders = null;
				try {
					deleteReaders = conn.prepareStatement(
							"delete from sys_attend_mainexc_readers where "
									+ HQLUtil.buildLogicIN("fd_doc_id",
									mainExcIds));
					deleteReaders.executeUpdate();
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				} finally {
					JdbcUtils.closeStatement(deleteReaders);
				}
				PreparedStatement deleteOeditor = null;
				try {
					deleteOeditor = conn.prepareStatement(
							"delete from sys_attend_mainexc_oeditor where "
									+ HQLUtil.buildLogicIN("fd_doc_id",
									mainExcIds));
					deleteOeditor.executeUpdate();
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				} finally {
					JdbcUtils.closeStatement(deleteOeditor);
				}
				PreparedStatement deleteOreader = null;
				try {
					deleteOreader = conn.prepareStatement(
							"delete from sys_attend_mainexc_oreader where "
									+ HQLUtil.buildLogicIN("fd_doc_id",
									mainExcIds));
					deleteOreader.executeUpdate();
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				} finally {
					JdbcUtils.closeStatement(deleteOreader);
				}

				PreparedStatement delete = null;
				try {
					delete = conn.prepareStatement(
							"delete from sys_attend_main_exc where "
									+ HQLUtil.buildLogicIN("fd_id", mainExcIds));
					delete.executeUpdate();
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				} finally {
					JdbcUtils.closeStatement(delete);
				}
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			logger.error("删除相应的考勤异常：" + e.getMessage(), e);
			throw e;
		} finally {
			JdbcUtils.closeResultSet(rs);
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
			String sql = "delete from sys_attend_main "
					+ "where doc_create_time<? and (fd_work_id is not null or fd_work_key is not null) and "
					+ HQLUtil.buildLogicIN("doc_creator_id", orgList);
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
		String orgSql = "select DISTINCT doc_creator_id from sys_attend_main "
				+ "where doc_create_time<? and (fd_work_id is not null or fd_work_key is not null) ";
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
				orgList.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除原来的考勤记录：" + e.getMessage(), e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(conn);
		}
		return orgList;
	}

}
