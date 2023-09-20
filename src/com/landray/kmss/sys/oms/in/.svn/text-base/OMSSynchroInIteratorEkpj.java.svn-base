package com.landray.kmss.sys.oms.in;

import com.landray.kmss.component.dbop.ds.DataSet;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.oms.SysOMSConstant;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.SpringBeanUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OMSSynchroInIteratorEkpj implements
		IOMSSynchroInIteratorEkpj, SysOrgConstant, SysOMSConstant {

	public static int BATCH_SIZE = 2000;

	private static final Log logger = LogFactory
			.getLog(OMSSynchroInIteratorEkpj.class);

	private Date lastUpdateTime = null;

	private SysQuartzJobContext jobContext = null;

	private long startTime;
	
	@Override
	public void synchro(SysQuartzJobContext jobContext) throws Exception {
		Long oms_synchro_start = new Date().getTime();
		logger.info("组织架构同步开始运行");
		jobContext.logMessage("组织架构同步开始运行");

		this.lastUpdateTime = new Date(System.currentTimeMillis() - 1000 * 60
				* 60 * 24 * 365);
		this.startTime = System.currentTimeMillis();
		this.jobContext = jobContext;
		try {
			// sys_org_element
			syncSysOrgElement();

			// sys_org_staffing_level
			syncSysOrgStaffingLevel();

			// sys_org_person
			syncSysOrgPerson();

			// 岗位
			// sys_org_post_person
			syncSysOrgPostPerson();

			// 群组
			// sys_org_group_cate
			syncSysOrgGroupCate();
			// sys_org_group_element
			syncSysOrgGroupElement();
			// sys_org_group_editor
			syncSysOrgGroupEditor();
			// sys_org_group_reader
			syncSysOrgGroupReader();

			// 角色线
			// sys_org_role_conf_cate
			syncSysOrgRoleConfCate();
			// sys_org_role_conf
			syncSysOrgRoleConf();
			// sys_org_role
			syncSysOrgRole();
			// sys_org_role_line
			syncSysOrgRoleline();
			// sys_org_role_line_editor
			syncSysOrgRoleLineEditor();
			// sys_org_role_line_reader
			syncSysOrgRoleLineReader();
			// sys_org_role_line_default_role
			syncSysOrgRolelineDefaultRole();

		} catch (Exception ex) {
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			ex.printStackTrace(printWriter);
			StringBuffer error = stringWriter.getBuffer();
			jobContext.logMessage("组织架构同步时出错:" + error.toString());
			ex.printStackTrace();
			throw ex;
		} finally {
			long oms_synchro_end = new Date().getTime();
			jobContext.logMessage("组织架构同步耗时："
					+ (oms_synchro_end - oms_synchro_start) / 1000 + "秒");

		}
	}

	private void syncSysOrgElement()
			throws Exception {
		logger.info("开始同步sys_org_element");
		jobContext.logMessage("开始同步sys_org_element");
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		Connection connV15 = null;
		PreparedStatement psselect_base_v15 = null;
		PreparedStatement psselect_all_v15 = null;
		PreparedStatement psselect_all = null;
		PreparedStatement psinsert = null;
		PreparedStatement psupdate = null;
		Set<String> ekpIds = new HashSet<String>();

		DataSet ds = new DataSet("ekpOmsIn");
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			int loop = 0;

			psselect_all = conn
					.prepareStatement(
							"select fd_id from sys_org_element");
			ResultSet rs_all = psselect_all.executeQuery();
			while (rs_all.next()) {
				String fd_id = rs_all.getString(1);
				ekpIds.add(fd_id);
			}
			rs_all.close();

			psinsert = conn
					.prepareStatement(
							"insert into sys_org_element(fd_id,fd_name,fd_org_type,fd_is_available,fd_is_business,fd_is_external) values(?,?,?,?,?,?)");

			connV15 = ds.getConnection();
			psselect_base_v15 = connV15
					.prepareStatement(
							"select fd_id,fd_name,fd_org_type,fd_is_available,fd_is_business,fd_is_external from sys_org_element");
			ResultSet rs_base_15 = psselect_base_v15.executeQuery();
			// 因为上级部门有关联，所以要先执行插入
			while (rs_base_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psinsert.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_element, 同步第" + (loop / 2000) + "批新增数据");
					jobContext.logMessage(
							"sys_org_element, 同步第" + (loop / 2000) + "批新增数据");
				}
				String fd_id = rs_base_15.getString(1);
				String fd_name = rs_base_15.getString(2);
				int fd_org_type = rs_base_15.getInt(3);
				boolean fd_is_available = rs_base_15.getBoolean(4);
				boolean fd_is_business = rs_base_15.getBoolean(5);
				boolean fd_is_external = rs_base_15.getBoolean(6);

				if (!ekpIds.contains(fd_id)) {
					psinsert.setString(1, fd_id);
					psinsert.setString(2, fd_name);
					psinsert.setInt(3, fd_org_type);
					psinsert.setBoolean(4, fd_is_available);
					psinsert.setBoolean(5, fd_is_business);
					psinsert.setBoolean(6, fd_is_external);
					psinsert.addBatch();
					loop++;
				}
			}
			psinsert.executeBatch();
			conn.commit();
			rs_base_15.close();
			loop = 0;

			String fieldStr = "fd_id,fd_org_type,fd_name,fd_name_pinyin,fd_name_simple_pinyin,fd_order,fd_no,fd_keyword,fd_is_available,fd_is_abandon,fd_is_business,fd_import_info,fd_flag_deleted,fd_ldap_dn,fd_memo,fd_hierarchy_id,fd_create_time,fd_alter_time,fd_org_email,fd_persons_number,fd_pre_dept_id,fd_pre_post_ids,fd_this_leaderid,fd_super_leaderid,fd_parentorgid,fd_parentid,auth_reader_flag,doc_creator_id,fd_is_external";
			psselect_all_v15 = connV15
					.prepareStatement(
							"select " + fieldStr + " from sys_org_element");
			psupdate = conn
					.prepareStatement(
							"update sys_org_element set fd_org_type=?,fd_name=?,fd_name_pinyin=?,fd_name_simple_pinyin=?,fd_order=?,fd_no=?,fd_keyword=?,fd_is_available=?,fd_is_abandon=?,fd_is_business=?,fd_import_info=?,fd_flag_deleted=?,fd_ldap_dn=?,fd_memo=?,fd_hierarchy_id=?,fd_create_time=?,fd_alter_time=?,fd_org_email=?,fd_persons_number=?,fd_pre_dept_id=?,fd_pre_post_ids=?,fd_this_leaderid=?,fd_super_leaderid=?,fd_parentorgid=?,fd_parentid=?,auth_reader_flag=?,doc_creator_id=?,fd_is_external=? where fd_id=?");

			ResultSet rs_all_15 = psselect_all_v15.executeQuery();
			while (rs_all_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psupdate.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_element, 同步第" + (loop / 2000) + "批更新数据");
					jobContext.logMessage(
							"sys_org_element, 同步第" + (loop / 2000) + "批更新数据");
				}

				String fd_id = rs_all_15.getString(1);
				Integer fd_org_type = rs_all_15.getInt(2);
				String fd_name = rs_all_15.getString(3);
				String fd_name_pinyin = rs_all_15.getString(4);
				String fd_name_simple_pinyin = rs_all_15.getString(5);
				Integer fd_order = rs_all_15.getInt(6);
				String fd_no = rs_all_15.getString(7);
				String fd_keyword = rs_all_15.getString(8);
				Boolean fd_is_available = rs_all_15.getBoolean(9);
				Boolean fd_is_abandon = rs_all_15.getBoolean(10);
				Boolean fd_is_business = rs_all_15.getBoolean(11);
				String fd_import_info = rs_all_15.getString(12);
				Boolean fd_flag_deleted = rs_all_15.getBoolean(13);
				String fd_ldap_dn = rs_all_15.getString(14);
				String fd_memo = rs_all_15.getString(15);
				String fd_hierarchy_id = rs_all_15.getString(16);
				Timestamp fd_create_time = rs_all_15.getTimestamp(17);
				Timestamp fd_alter_time = rs_all_15.getTimestamp(18);
				String fd_org_email = rs_all_15.getString(19);
				String fd_persons_number = rs_all_15.getString(20);
				String fd_pre_dept_id = rs_all_15.getString(21);
				String fd_pre_post_ids = rs_all_15.getString(22);
				String fd_this_leaderid = rs_all_15.getString(23);
				String fd_super_leaderid = rs_all_15.getString(24);
				String fd_parentorgid = rs_all_15.getString(25);
				String fd_parentid = rs_all_15.getString(26);
				Boolean auth_reader_flag = rs_all_15.getBoolean(27);
				String doc_creator_id = rs_all_15.getString(28);
				Boolean fd_is_external = rs_all_15.getBoolean(29);

				if (ekpIds.contains(fd_id)) {
					psupdate.setInt(1, fd_org_type);
					psupdate.setString(2, fd_name);
					psupdate.setString(3, fd_name_pinyin);
					psupdate.setString(4, fd_name_simple_pinyin);
					psupdate.setInt(5, fd_order);
					psupdate.setString(6, fd_no);
					psupdate.setString(7, fd_keyword);
					psupdate.setBoolean(8, fd_is_available);
					psupdate.setBoolean(9, fd_is_abandon);
					psupdate.setBoolean(10, fd_is_business);
					psupdate.setString(11,
							"com.landray.kmss.third.ekp.java.oms.in.EkpSynchroInIteratorProviderImp"
									+ fd_id);
					psupdate.setBoolean(12, fd_flag_deleted);
					psupdate.setString(13, fd_ldap_dn);
					psupdate.setString(14, fd_memo);
					psupdate.setString(15, fd_hierarchy_id);
					psupdate.setTimestamp(16, fd_create_time);
					psupdate.setTimestamp(17, fd_alter_time == null ? new Timestamp(System.currentTimeMillis()) : fd_alter_time);
					psupdate.setString(18, fd_org_email);
					psupdate.setString(19, fd_persons_number);
					psupdate.setString(20, fd_pre_dept_id);
					psupdate.setString(21, fd_pre_post_ids);
					psupdate.setString(22, fd_this_leaderid);
					psupdate.setString(23, fd_super_leaderid);
					psupdate.setString(24, fd_parentorgid);
					psupdate.setString(25, fd_parentid);
					psupdate.setBoolean(26, auth_reader_flag);
					psupdate.setString(27, doc_creator_id);
					psupdate.setBoolean(28, fd_is_external);
					psupdate.setString(29, fd_id);
					psupdate.addBatch();
					loop++;
				}
			}
			psupdate.executeBatch();
			conn.commit();
			rs_all_15.close();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			conn.rollback();
			throw e;
		} finally {

			JdbcUtils.closeStatement(psselect_all);

			JdbcUtils.closeStatement(psselect_base_v15);

			JdbcUtils.closeStatement(psselect_all_v15);

			JdbcUtils.closeStatement(psinsert);

			JdbcUtils.closeStatement(psupdate);
			JdbcUtils.closeConnection(conn);

			try {
				ds.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				jobContext.logError(e.getMessage(), e);
			}
		}
	}

	private void syncSysOrgPerson()
			throws Exception {
		logger.info("开始同步sys_org_person");
		jobContext.logMessage("开始同步sys_org_person");
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		Connection connV15 = null;
		PreparedStatement psselect_all_v15 = null;
		PreparedStatement psselect_all = null;
		PreparedStatement psinsert = null;
		PreparedStatement psupdate = null;
		Set<String> ekpIds = new HashSet<String>();

		DataSet ds = new DataSet("ekpOmsIn");
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			int loop = 0;

			psselect_all = conn
					.prepareStatement(
							"select fd_id from sys_org_person");
			ResultSet rs_all = psselect_all.executeQuery();
			while (rs_all.next()) {
				String fd_id = rs_all.getString(1);
				ekpIds.add(fd_id);
			}
			rs_all.close();

			String fieldStr = "fd_id,fd_mobile_no,fd_email,fd_login_name,fd_password,fd_init_password,fd_rtx_no,fd_wechat_no,fd_card_no,fd_attendance_card_number,fd_work_phone,fd_default_lang,fd_sex,fd_last_change_pwd,fd_lock_time,fd_short_no,fd_double_validation,fd_user_type,fd_nick_name,fd_staffing_level_id,fd_is_activated,fd_hiredate,fd_leave_date";
			// String[] fields = fieldStr.split(",");

			psinsert = conn
					.prepareStatement(
							"insert into sys_org_person(" + fieldStr
									+ ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			psupdate = conn
					.prepareStatement(
							"update sys_org_person set fd_mobile_no=?, fd_email=?, fd_login_name=?,fd_password=?, fd_init_password=?,fd_rtx_no=?, fd_wechat_no=?, fd_card_no=?, fd_attendance_card_number=?, fd_work_phone=?, fd_default_lang=?, fd_sex=?, fd_short_no=?,fd_double_validation=?,fd_nick_name=?,fd_staffing_level_id=?, fd_is_activated=?, fd_hiredate=?,fd_leave_date=? where fd_id=?");

			connV15 = ds.getConnection();
			psselect_all_v15 = connV15
					.prepareStatement(
							"select " + fieldStr + " from sys_org_person");
			ResultSet rs_all_15 = psselect_all_v15.executeQuery();
			while (rs_all_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psinsert.executeBatch();
					psupdate.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_person, 同步第" + (loop / 2000) + "批数据");
					jobContext.logMessage(
							"sys_org_person, 同步第" + (loop / 2000) + "批数据");
				}

				String fd_id = rs_all_15.getString(1);
				String fd_mobile_no = rs_all_15.getString(2);
				String fd_email = rs_all_15.getString(3);
				String fd_login_name = rs_all_15.getString(4);
				String fd_password = rs_all_15.getString(5);
				String fd_init_password = rs_all_15.getString(6);
				String fd_rtx_no = rs_all_15.getString(7);
				String fd_wechat_no = rs_all_15.getString(8);
				String fd_card_no = rs_all_15.getString(9);
				String fd_attendance_card_number = rs_all_15.getString(10);
				String fd_work_phone = rs_all_15.getString(11);
				String fd_default_lang = rs_all_15.getString(12);
				String fd_sex = rs_all_15.getString(13);
				Timestamp fd_last_change_pwd = rs_all_15.getTimestamp(14);
				Timestamp fd_lock_time = rs_all_15.getTimestamp(15);
				String fd_short_no = rs_all_15.getString(16);
				String fd_double_validation = rs_all_15.getString(17);
				String fd_user_type = rs_all_15.getString(18);
				String fd_nick_name = rs_all_15.getString(19);
				String fd_staffing_level_id = rs_all_15.getString(20);
				Boolean fd_is_activated = rs_all_15.getBoolean(21);
				Timestamp fd_hiredate = rs_all_15.getTimestamp(22);
				Timestamp fd_leave_date = rs_all_15.getTimestamp(23);

				if (!ekpIds.contains(fd_id)) {
					psinsert.setString(1, fd_id);
					psinsert.setString(2, fd_mobile_no);
					psinsert.setString(3, fd_email);
					psinsert.setString(4, fd_login_name);
					psinsert.setString(5, fd_password);
					psinsert.setString(6, fd_init_password);
					psinsert.setString(7, fd_rtx_no);
					psinsert.setString(8, fd_wechat_no);
					psinsert.setString(9, fd_card_no);
					psinsert.setString(10, fd_attendance_card_number);
					psinsert.setString(11, fd_work_phone);
					psinsert.setString(12, fd_default_lang);
					psinsert.setString(13, fd_sex);
					psinsert.setTimestamp(14, fd_last_change_pwd);
					psinsert.setTimestamp(15, fd_lock_time);
					psinsert.setString(16, fd_short_no);
					psinsert.setString(17, fd_double_validation);
					psinsert.setString(18, fd_user_type);
					psinsert.setString(19, fd_nick_name);
					psinsert.setString(20, fd_staffing_level_id);
					psinsert.setBoolean(21, fd_is_activated);
					psinsert.setTimestamp(22, fd_hiredate);
					psinsert.setTimestamp(23, fd_leave_date);
					psinsert.addBatch();
					loop++;
				} else {
					if ("admin".equals(fd_login_name)) {
						continue;
					}
					psupdate.setString(1, fd_mobile_no);
					psupdate.setString(2, fd_email);
					psupdate.setString(3, fd_login_name);
					psupdate.setString(4, fd_password);
					psupdate.setString(5, fd_init_password);
					psupdate.setString(6, fd_rtx_no);
					psupdate.setString(7, fd_wechat_no);
					psupdate.setString(8, fd_card_no);
					psupdate.setString(9, fd_attendance_card_number);
					psupdate.setString(10, fd_work_phone);
					psupdate.setString(11, fd_default_lang);
					psupdate.setString(12, fd_sex);
					psupdate.setString(13, fd_short_no);
					psupdate.setString(14, fd_double_validation);
					psupdate.setString(15, fd_nick_name);
					psupdate.setString(16, fd_staffing_level_id);
					psupdate.setBoolean(17, fd_is_activated);
					psupdate.setTimestamp(18, fd_hiredate);
					psupdate.setTimestamp(19, fd_leave_date);
					psupdate.setString(20, fd_id);
					psupdate.addBatch();
					loop++;
				}
			}
			psinsert.executeBatch();
			psupdate.executeBatch();
			conn.commit();
			rs_all_15.close();
			loop = 0;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jobContext.logError(e.getMessage(), e);
			conn.rollback();
			throw e;
		} finally {

			JdbcUtils.closeStatement(psselect_all);

			JdbcUtils.closeStatement(psselect_all_v15);

			JdbcUtils.closeStatement(psinsert);

			JdbcUtils.closeStatement(psupdate);
			JdbcUtils.closeConnection(conn);

			try {
				ds.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private void syncSysOrgStaffingLevel()
			throws Exception {
		logger.info("开始同步sys_org_staffing_level");
		jobContext.logMessage("开始同步sys_org_staffing_level");
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		Connection connV15 = null;
		PreparedStatement psselect_all_v15 = null;
		PreparedStatement psselect_all = null;
		PreparedStatement psinsert = null;
		PreparedStatement psupdate = null;
		Set<String> ekpIds = new HashSet<String>();

		DataSet ds = new DataSet("ekpOmsIn");
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			int loop = 0;
			psselect_all = conn
					.prepareStatement(
							"select fd_id from sys_org_staffing_level");
			ResultSet rs_all = psselect_all.executeQuery();
			while (rs_all.next()) {
				String fd_id = rs_all.getString(1);
				ekpIds.add(fd_id);
			}
			rs_all.close();

			String fieldStr = "fd_id,doc_create_time,doc_alter_time,fd_description,fd_name,fd_level,doc_creator_id,fd_is_default,fd_import_info";
			// String[] fields = fieldStr.split(",");

			psinsert = conn
					.prepareStatement(
							"insert into sys_org_staffing_level(" + fieldStr
									+ ") values(?,?,?,?,?,?,?,?,?)");
			psupdate = conn
					.prepareStatement(
							"update sys_org_staffing_level set doc_create_time=?, doc_alter_time=?, fd_description=?,fd_name=?, fd_level=?,doc_creator_id=?, fd_is_default=?, fd_import_info=? where fd_id=?");

			connV15 = ds.getConnection();
			psselect_all_v15 = connV15
					.prepareStatement(
							"select " + fieldStr
									+ " from sys_org_staffing_level");
			ResultSet rs_all_15 = psselect_all_v15.executeQuery();
			while (rs_all_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psinsert.executeBatch();
					psupdate.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_staffing_level, 同步第" + (loop / 2000)
									+ "批数据");
					jobContext.logMessage(
							"sys_org_staffing_level, 同步第" + (loop / 2000)
									+ "批数据");
				}

				String fd_id = rs_all_15.getString(1);
				Timestamp doc_create_time = rs_all_15.getTimestamp(2);
				Timestamp doc_alter_time = rs_all_15.getTimestamp(3);
				String fd_description = rs_all_15.getString(4);
				String fd_name = rs_all_15.getString(5);
				Integer fd_level = rs_all_15.getInt(6);
				String doc_creator_id = rs_all_15.getString(7);
				Boolean fd_is_default = rs_all_15.getBoolean(8);
				String fd_import_info = rs_all_15.getString(9);

				if (!ekpIds.contains(fd_id)) {
					psinsert.setString(1, fd_id);
					psinsert.setTimestamp(2, doc_create_time);
					psinsert.setTimestamp(3, doc_alter_time);
					psinsert.setString(4, fd_description);
					psinsert.setString(5, fd_name);
					psinsert.setInt(6, fd_level);
					psinsert.setString(7, doc_creator_id);
					psinsert.setBoolean(8, fd_is_default);
					psinsert.setString(9, fd_import_info);
					psinsert.addBatch();
					loop++;
				} else {
					psupdate.setTimestamp(1, doc_create_time);
					psupdate.setTimestamp(2, doc_alter_time);
					psupdate.setString(3, fd_description);
					psupdate.setString(4, fd_name);
					psupdate.setInt(5, fd_level);
					psupdate.setString(6, doc_creator_id);
					psupdate.setBoolean(7, fd_is_default);
					psupdate.setString(8, fd_import_info);
					psupdate.setString(9, fd_id);
					psupdate.addBatch();
					loop++;
				}
			}
			psinsert.executeBatch();
			psupdate.executeBatch();
			conn.commit();
			rs_all_15.close();
			loop = 0;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jobContext.logError(e.getMessage(), e);
			conn.rollback();
			throw e;
		} finally {

			JdbcUtils.closeStatement(psselect_all);

			JdbcUtils.closeStatement(psselect_all_v15);

			JdbcUtils.closeStatement(psinsert);

			JdbcUtils.closeStatement(psupdate);
			JdbcUtils.closeConnection(conn);

			try {
				ds.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 先删除关系，再插入关系。LBPM中手动建的岗位不删关系
	 * 
	 * @throws Exception
	 */
	private void syncSysOrgPostPerson()
			throws Exception {
		logger.info("开始同步sys_org_post_person");
		jobContext.logMessage("开始同步sys_org_post_person");
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		Connection connV15 = null;
		PreparedStatement psselect_all_v15 = null;
		PreparedStatement psselect_postid_v15 = null;
		PreparedStatement psinsert = null;
		PreparedStatement psdelete = null;
		Set<String> postIds = new HashSet<String>();

		DataSet ds = new DataSet("ekpOmsIn");
		try {
			connV15 = ds.getConnection();
			psselect_postid_v15 = connV15
					.prepareStatement(
							"select fd_id from sys_org_element where fd_org_type=4");
			ResultSet rs_post = psselect_postid_v15.executeQuery();
			while (rs_post.next()) {
				String fd_postid = rs_post.getString(1);
				postIds.add(fd_postid);
			}
			rs_post.close();

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			int loop = 0;
			// 删除同步过来的岗位的关系
			psdelete = conn
					.prepareStatement(
							"delete from sys_org_post_person where fd_postid = ?");
			int i = 0;
			for (String postid : postIds) {
				if (i > 0 && i % 2000 == 0) {
					psdelete.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_post_person, 删除第" + (loop / 2000)
									+ "批数据");
					jobContext.logMessage(
							"sys_org_post_person, 删除第" + (loop / 2000)
									+ "批数据");
				}
				psdelete.setString(1, postid);
				psdelete.addBatch();
				i++;
			}
			psdelete.executeBatch();
			conn.commit();
			logger.info("删除sys_org_post_person完成");
			jobContext.logMessage("删除sys_org_post_person完成");

			psinsert = conn
					.prepareStatement(
							"insert into sys_org_post_person(fd_postid,fd_personid) values(?,?)");
			psselect_all_v15 = connV15
					.prepareStatement(
							"select fd_postid,fd_personid from sys_org_post_person");
			ResultSet rs_all_15 = psselect_all_v15.executeQuery();
			while (rs_all_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psinsert.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_post_person, 同步第" + (loop / 2000)
									+ "批数据");
					jobContext.logMessage(
							"sys_org_post_person, 同步第" + (loop / 2000)
									+ "批数据");
				}
				String fd_postid = rs_all_15.getString(1);
				String fd_personid = rs_all_15.getString(2);
				psinsert.setString(1, fd_postid);
				psinsert.setString(2, fd_personid);
				psinsert.addBatch();
				loop++;

			}
			psinsert.executeBatch();
			conn.commit();
			rs_all_15.close();
			loop = 0;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jobContext.logError(e.getMessage(), e);
			conn.rollback();
			throw e;
		} finally {

			JdbcUtils.closeStatement(psselect_all_v15);

			JdbcUtils.closeStatement(psinsert);

			JdbcUtils.closeStatement(psdelete);

			JdbcUtils.closeStatement(psselect_postid_v15);
			JdbcUtils.closeConnection(conn);

			try {
				ds.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private void syncSysOrgGroupCate()
			throws Exception {
		logger.info("开始同步sys_org_group_cate");
		jobContext.logMessage("开始同步sys_org_group_cate");
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		Connection connV15 = null;
		PreparedStatement psselect_base_v15 = null;
		PreparedStatement psselect_all_v15 = null;
		PreparedStatement psselect_all = null;
		PreparedStatement psinsert = null;
		PreparedStatement psupdate = null;
		Set<String> ekpIds = new HashSet<String>();

		DataSet ds = new DataSet("ekpOmsIn");
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			int loop = 0;

			psselect_all = conn
					.prepareStatement(
							"select fd_id from sys_org_group_cate");
			ResultSet rs_all = psselect_all.executeQuery();
			while (rs_all.next()) {
				String fd_id = rs_all.getString(1);
				ekpIds.add(fd_id);
			}
			rs_all.close();

			psinsert = conn
					.prepareStatement(
							"insert into sys_org_group_cate(fd_id,fd_name) values(?,?)");

			connV15 = ds.getConnection();
			psselect_base_v15 = connV15
					.prepareStatement(
							"select fd_id,fd_name from sys_org_group_cate");
			ResultSet rs_base_15 = psselect_base_v15.executeQuery();
			// 因为上级关系有关联，所以要先执行插入
			while (rs_base_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psinsert.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_group_cate, 同步第" + (loop / 2000)
									+ "批新增数据");
					jobContext.logMessage(
							"sys_org_group_cate, 同步第" + (loop / 2000)
									+ "批新增数据");
				}
				String fd_id = rs_base_15.getString(1);
				String fd_name = rs_base_15.getString(2);

				if (!ekpIds.contains(fd_id)) {
					psinsert.setString(1, fd_id);
					psinsert.setString(2, fd_name);
					psinsert.addBatch();
					loop++;
				}
			}
			psinsert.executeBatch();
			conn.commit();
			rs_base_15.close();
			loop = 0;

			String fieldStr = "fd_id,fd_name,fd_keyword,fd_create_time,fd_alter_time,fd_parentid";
			// 需要补充完所有字段
			psselect_all_v15 = connV15
					.prepareStatement(
							"select " + fieldStr + " from sys_org_group_cate");
			psupdate = conn
					.prepareStatement(
							"update sys_org_group_cate set fd_name=?,fd_keyword=?,fd_create_time=?,fd_alter_time=?,fd_parentid=? where fd_id=?");

			ResultSet rs_all_15 = psselect_base_v15.executeQuery();
			while (rs_all_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psupdate.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_group_cate, 同步第" + (loop / 2000)
									+ "批更新数据");
					jobContext.logMessage(
							"sys_org_group_cate, 同步第" + (loop / 2000)
									+ "批更新数据");
				}
				String fd_id = rs_all_15.getString(1);
				String fd_name = rs_all_15.getString(2);
				String fd_keyword = rs_all_15.getString(3);
				Timestamp fd_create_time = rs_all_15.getTimestamp(4);
				Timestamp fd_alter_time = rs_all_15.getTimestamp(5);
				String fd_parentid = rs_all_15.getString(6);
				if (ekpIds.contains(fd_id)) {
					psupdate.setString(1, fd_name);
					psupdate.setString(2, fd_keyword);
					psupdate.setTimestamp(3, fd_create_time);
					psupdate.setTimestamp(4, fd_alter_time == null ? new Timestamp(System.currentTimeMillis()) : fd_alter_time);
					psupdate.setString(5, fd_parentid);
					psupdate.setString(6, fd_id);
					psupdate.addBatch();
					loop++;
				}
			}
			psupdate.executeBatch();
			conn.commit();
			rs_all_15.close();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jobContext.logError(e.getMessage(), e);
			conn.rollback();
			throw e;
		} finally {

			JdbcUtils.closeStatement(psselect_all);

			JdbcUtils.closeStatement(psselect_base_v15);

			JdbcUtils.closeStatement(psselect_all_v15);

			JdbcUtils.closeStatement(psinsert);

			JdbcUtils.closeStatement(psupdate);
			JdbcUtils.closeConnection(conn);

			try {
				ds.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private void syncSysOrgGroupElement()
			throws Exception {
		logger.info("开始同步sys_org_group_element");
		jobContext.logMessage("开始同步sys_org_group_element");
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		Connection connV15 = null;
		PreparedStatement psselect_all_v15 = null;
		PreparedStatement psselect_groupid_v15 = null;
		PreparedStatement psinsert = null;
		PreparedStatement psdelete = null;
		Set<String> groupIds = new HashSet<String>();

		DataSet ds = new DataSet("ekpOmsIn");
		try {
			connV15 = ds.getConnection();
			psselect_groupid_v15 = connV15
					.prepareStatement(
							"select fd_id from sys_org_element where fd_org_type=16");
			ResultSet rs_group = psselect_groupid_v15.executeQuery();
			while (rs_group.next()) {
				String fd_groupid = rs_group.getString(1);
				groupIds.add(fd_groupid);
			}
			rs_group.close();

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			int loop = 0;
			// 删除同步过来的岗位的关系
			psdelete = conn
					.prepareStatement(
							"delete from sys_org_group_element where fd_groupid = ?");
			int i = 0;
			for (String groupid : groupIds) {
				if (i > 0 && i % 2000 == 0) {
					psdelete.executeBatch();
					conn.commit();
				}
				psdelete.setString(1, groupid);
				psdelete.addBatch();
				i++;
			}
			psdelete.executeBatch();
			conn.commit();
			logger.info("删除sys_org_group_element完成");
			jobContext.logMessage("删除sys_org_group_element完成");

			psinsert = conn
					.prepareStatement(
							"insert into sys_org_group_element(fd_groupid,fd_elementid) values(?,?)");
			psselect_all_v15 = connV15
					.prepareStatement(
							"select fd_groupid,fd_elementid from sys_org_group_element");
			ResultSet rs_all_15 = psselect_all_v15.executeQuery();
			while (rs_all_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psinsert.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_group_element, 同步第" + (loop / 2000)
									+ "批数据");
					jobContext.logMessage(
							"sys_org_group_element, 同步第" + (loop / 2000)
									+ "批数据");
				}
				String fd_groupid = rs_all_15.getString(1);
				String fd_elementid = rs_all_15.getString(2);
				psinsert.setString(1, fd_groupid);
				psinsert.setString(2, fd_elementid);
				psinsert.addBatch();
				loop++;

			}
			psinsert.executeBatch();
			conn.commit();
			rs_all_15.close();
			loop = 0;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jobContext.logError(e.getMessage(), e);
			conn.rollback();
			throw e;
		} finally {

			JdbcUtils.closeStatement(psselect_all_v15);
			JdbcUtils.closeStatement(psinsert);
			JdbcUtils.closeStatement(psdelete);
			JdbcUtils.closeStatement(psselect_groupid_v15);
			JdbcUtils.closeConnection(conn);

			try {
				ds.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private void syncSysOrgGroupEditor()
			throws Exception {
		logger.info("开始同步sys_org_group_editor");
		jobContext.logMessage("开始同步sys_org_group_editor");
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		Connection connV15 = null;
		PreparedStatement psselect_all_v15 = null;
		PreparedStatement psselect_groupid_v15 = null;
		PreparedStatement psinsert = null;
		PreparedStatement psdelete = null;
		Set<String> groupIds = new HashSet<String>();

		DataSet ds = new DataSet("ekpOmsIn");
		try {
			connV15 = ds.getConnection();
			psselect_groupid_v15 = connV15
					.prepareStatement(
							"select fd_id from sys_org_element where fd_org_type=16");
			ResultSet rs_group = psselect_groupid_v15.executeQuery();
			while (rs_group.next()) {
				String fd_groupid = rs_group.getString(1);
				groupIds.add(fd_groupid);
			}
			rs_group.close();

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			int loop = 0;
			// 删除同步过来的岗位的关系
			psdelete = conn
					.prepareStatement(
							"delete from sys_org_group_editor where fd_group_id = ?");
			int i = 0;
			for (String groupid : groupIds) {
				if (i > 0 && i % 2000 == 0) {
					psdelete.executeBatch();
					conn.commit();
				}
				psdelete.setString(1, groupid);
				psdelete.addBatch();
				i++;
			}
			psdelete.executeBatch();
			conn.commit();
			logger.info("删除sys_org_group_editor完成");
			jobContext.logMessage("删除sys_org_group_editor完成");

			psinsert = conn
					.prepareStatement(
							"insert into sys_org_group_editor(fd_group_id,auth_editor_id) values(?,?)");
			psselect_all_v15 = connV15
					.prepareStatement(
							"select fd_group_id,auth_editor_id from sys_org_group_editor");
			ResultSet rs_all_15 = psselect_all_v15.executeQuery();
			while (rs_all_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psinsert.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_group_editor, 同步第" + (loop / 2000)
									+ "批数据");
					jobContext.logMessage(
							"sys_org_group_editor, 同步第" + (loop / 2000)
									+ "批数据");
				}
				String fd_groupid = rs_all_15.getString(1);
				String auth_editor_id = rs_all_15.getString(2);
				psinsert.setString(1, fd_groupid);
				psinsert.setString(2, auth_editor_id);
				psinsert.addBatch();
				loop++;

			}
			psinsert.executeBatch();
			conn.commit();
			rs_all_15.close();
			loop = 0;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jobContext.logError(e.getMessage(), e);
			conn.rollback();
			throw e;
		} finally {

			JdbcUtils.closeStatement(psselect_all_v15);
			JdbcUtils.closeStatement(psdelete);
			JdbcUtils.closeStatement(psinsert);
			JdbcUtils.closeStatement(psselect_groupid_v15);
			JdbcUtils.closeConnection(conn);

			try {
				ds.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private void syncSysOrgGroupReader()
			throws Exception {
		logger.info("开始同步sys_org_group_reader");
		jobContext.logMessage("开始同步sys_org_group_reader");
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		Connection connV15 = null;
		PreparedStatement psselect_all_v15 = null;
		PreparedStatement psselect_groupid_v15 = null;
		PreparedStatement psinsert = null;
		PreparedStatement psdelete = null;
		Set<String> groupIds = new HashSet<String>();

		DataSet ds = new DataSet("ekpOmsIn");
		try {
			connV15 = ds.getConnection();
			psselect_groupid_v15 = connV15
					.prepareStatement(
							"select fd_id from sys_org_element where fd_org_type=16");
			ResultSet rs_group = psselect_groupid_v15.executeQuery();
			while (rs_group.next()) {
				String fd_groupid = rs_group.getString(1);
				groupIds.add(fd_groupid);
			}
			rs_group.close();

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			int loop = 0;
			// 删除同步过来的岗位的关系
			psdelete = conn
					.prepareStatement(
							"delete from sys_org_group_reader where fd_group_id = ?");
			int i = 0;
			for (String groupid : groupIds) {
				if (i > 0 && i % 2000 == 0) {
					psdelete.executeBatch();
					conn.commit();
				}
				psdelete.setString(1, groupid);
				psdelete.addBatch();
				i++;
			}
			psdelete.executeBatch();
			conn.commit();
			logger.info("删除sys_org_group_reader完成");
			jobContext.logMessage("删除sys_org_group_reader完成");

			psinsert = conn
					.prepareStatement(
							"insert into sys_org_group_reader(fd_group_id,auth_reader_id) values(?,?)");
			psselect_all_v15 = connV15
					.prepareStatement(
							"select fd_group_id,auth_reader_id from sys_org_group_reader");
			ResultSet rs_all_15 = psselect_all_v15.executeQuery();
			while (rs_all_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psinsert.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_group_editor, 同步第" + (loop / 2000)
									+ "批数据");
					jobContext.logMessage(
							"sys_org_group_editor, 同步第" + (loop / 2000)
									+ "批数据");
				}
				String fd_groupid = rs_all_15.getString(1);
				String auth_reader_id = rs_all_15.getString(2);
				psinsert.setString(1, fd_groupid);
				psinsert.setString(2, auth_reader_id);
				psinsert.addBatch();
				loop++;

			}
			psinsert.executeBatch();
			conn.commit();
			rs_all_15.close();
			loop = 0;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jobContext.logError(e.getMessage(), e);
			conn.rollback();
			throw e;
		} finally {

			JdbcUtils.closeStatement(psselect_all_v15);
			JdbcUtils.closeStatement(psdelete);
			JdbcUtils.closeStatement(psinsert);
			JdbcUtils.closeStatement(psselect_groupid_v15);
			JdbcUtils.closeConnection(conn);

			try {
				ds.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private void syncSysOrgRoleConfCate()
			throws Exception {
		logger.info("开始同步sys_org_role_conf_cate");
		jobContext.logMessage("开始同步sys_org_role_conf_cate");
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		Connection connV15 = null;
		PreparedStatement psselect_base_v15 = null;
		PreparedStatement psselect_all_v15 = null;
		PreparedStatement psselect_all = null;
		PreparedStatement psinsert = null;
		PreparedStatement psupdate = null;
		Set<String> ekpIds = new HashSet<String>();

		DataSet ds = new DataSet("ekpOmsIn");
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			int loop = 0;

			psselect_all = conn
					.prepareStatement(
							"select fd_id from sys_org_role_conf_cate");
			ResultSet rs_all = psselect_all.executeQuery();
			while (rs_all.next()) {
				String fd_id = rs_all.getString(1);
				ekpIds.add(fd_id);
			}
			rs_all.close();

			psinsert = conn
					.prepareStatement(
							"insert into sys_org_role_conf_cate(fd_id,fd_name) values(?,?)");

			connV15 = ds.getConnection();
			psselect_base_v15 = connV15
					.prepareStatement(
							"select fd_id,fd_name from sys_org_role_conf_cate");
			ResultSet rs_base_15 = psselect_base_v15.executeQuery();
			// 因为上级关系有关联，所以要先执行插入
			while (rs_base_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psinsert.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_role_conf_cate, 同步第" + (loop / 2000)
									+ "批新增数据");
					jobContext.logMessage(
							"sys_org_role_conf_cate, 同步第" + (loop / 2000)
									+ "批新增数据");
				}
				String fd_id = rs_base_15.getString(1);
				String fd_name = rs_base_15.getString(2);

				if (!ekpIds.contains(fd_id)) {
					psinsert.setString(1, fd_id);
					psinsert.setString(2, fd_name);
					psinsert.addBatch();
					loop++;
				}
			}
			psinsert.executeBatch();
			conn.commit();
			rs_base_15.close();
			loop = 0;

			String fieldStr = "fd_id,fd_name,fd_keyword,fd_create_time,fd_alter_time,fd_parentid";
			// 需要补充完所有字段
			psselect_all_v15 = connV15
					.prepareStatement(
							"select " + fieldStr
									+ " from sys_org_role_conf_cate");
			psupdate = conn
					.prepareStatement(
							"update sys_org_role_conf_cate set fd_name=?,fd_keyword=?,fd_create_time=?,fd_alter_time=?,fd_parentid=? where fd_id=?");

			ResultSet rs_all_15 = psselect_all_v15.executeQuery();
			while (rs_all_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psupdate.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_group_cate, 同步第" + (loop / 2000)
									+ "批更新数据");
					jobContext.logMessage(
							"sys_org_group_cate, 同步第" + (loop / 2000)
									+ "批更新数据");
				}
				String fd_id = rs_all_15.getString(1);
				String fd_name = rs_all_15.getString(2);
				String fd_keyword = rs_all_15.getString(3);
				Timestamp fd_create_time = rs_all_15.getTimestamp(4);
				Timestamp fd_alter_time = rs_all_15.getTimestamp(5);
				String fd_parentid = rs_all_15.getString(6);
				if (ekpIds.contains(fd_id)) {
					psupdate.setString(1, fd_name);
					psupdate.setString(2, fd_keyword);
					psupdate.setTimestamp(3, fd_create_time);
					psupdate.setTimestamp(4, fd_alter_time == null ? new Timestamp(System.currentTimeMillis()) : fd_alter_time);
					psupdate.setString(5, fd_parentid);
					psupdate.setString(6, fd_id);
					psupdate.addBatch();
					loop++;
				}
			}
			psupdate.executeBatch();
			conn.commit();
			rs_all_15.close();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jobContext.logError(e.getMessage(), e);
			conn.rollback();
			throw e;
		} finally {

			JdbcUtils.closeStatement(psselect_all);

			JdbcUtils.closeStatement(psselect_base_v15);

			JdbcUtils.closeStatement(psselect_all_v15);

			JdbcUtils.closeStatement(psinsert);

			JdbcUtils.closeStatement(psupdate);
			JdbcUtils.closeConnection(conn);

			try {
				ds.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private void syncSysOrgRoleConf()
			throws Exception {
		logger.info("开始同步sys_org_role_conf");
		jobContext.logMessage("开始同步sys_org_role_conf");
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		Connection connV15 = null;
		PreparedStatement psselect_all_v15 = null;
		PreparedStatement psselect_all = null;
		PreparedStatement psinsert = null;
		PreparedStatement psupdate = null;
		Set<String> ekpIds = new HashSet<String>();

		DataSet ds = new DataSet("ekpOmsIn");
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			int loop = 0;

			psselect_all = conn
					.prepareStatement(
							"select fd_id from sys_org_role_conf");
			ResultSet rs_all = psselect_all.executeQuery();
			while (rs_all.next()) {
				String fd_id = rs_all.getString(1);
				ekpIds.add(fd_id);
			}
			rs_all.close();

			String fieldStr = "fd_id,fd_name,fd_order,fd_is_available,fd_create_time,fd_alter_time,fd_cateid";
			// String[] fields = fieldStr.split(",");

			psinsert = conn
					.prepareStatement(
							"insert into sys_org_role_conf(" + fieldStr
									+ ") values(?,?,?,?,?,?,?)");

			psupdate = conn
					.prepareStatement(
							"update sys_org_role_conf set fd_name=?, fd_order=?, fd_is_available=?,fd_create_time=?, fd_alter_time=?,fd_cateid=? where fd_id=?");

			connV15 = ds.getConnection();
			psselect_all_v15 = connV15
					.prepareStatement(
							"select " + fieldStr + " from sys_org_role_conf");
			ResultSet rs_all_15 = psselect_all_v15.executeQuery();
			while (rs_all_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psinsert.executeBatch();
					psupdate.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_role_conf, 同步第" + (loop / 2000) + "批数据");
					jobContext.logMessage(
							"sys_org_role_conf, 同步第" + (loop / 2000) + "批数据");
				}
				String fd_id = rs_all_15.getString(1);
				String fd_name = rs_all_15.getString(2);
				Integer fd_order = rs_all_15.getInt(3);
				Boolean fd_is_available = rs_all_15.getBoolean(4);
				Timestamp fd_create_time = rs_all_15.getTimestamp(5);
				Timestamp fd_alter_time = rs_all_15.getTimestamp(6);
				String fd_cateid = rs_all_15.getString(7);

				if (!ekpIds.contains(fd_id)) {
					psinsert.setString(1, fd_id);
					psinsert.setString(2, fd_name);
					psinsert.setInt(3, fd_order);
					psinsert.setBoolean(4, fd_is_available);
					psinsert.setTimestamp(5, fd_create_time);
					psinsert.setTimestamp(6, fd_alter_time == null ? new Timestamp(System.currentTimeMillis()) : fd_alter_time);
					psinsert.setString(7, fd_cateid);
					psinsert.addBatch();
					loop++;
				} else {
					psupdate.setString(1, fd_name);
					psupdate.setInt(2, fd_order);
					psupdate.setBoolean(3, fd_is_available);
					psupdate.setTimestamp(4, fd_create_time);
					psupdate.setTimestamp(5, fd_alter_time == null ? new Timestamp(System.currentTimeMillis()) : fd_alter_time);
					psupdate.setString(6, fd_cateid);
					psupdate.setString(7, fd_id);
					psupdate.addBatch();
					loop++;
				}
			}
			psinsert.executeBatch();
			psupdate.executeBatch();
			conn.commit();
			rs_all_15.close();
			loop = 0;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jobContext.logError(e.getMessage(), e);
			conn.rollback();
			throw e;
		} finally {

			JdbcUtils.closeStatement(psselect_all);

			JdbcUtils.closeStatement(psselect_all_v15);

			JdbcUtils.closeStatement(psinsert);

			JdbcUtils.closeStatement(psupdate);
			JdbcUtils.closeConnection(conn);

			try {
				ds.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private void syncSysOrgRole()
			throws Exception {
		logger.info("开始同步sys_org_role");
		jobContext.logMessage("开始同步sys_org_role");
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		Connection connV15 = null;
		PreparedStatement psselect_all = null;
		PreparedStatement psselect_all_v15 = null;
		PreparedStatement psselect_all_v15_confnull = null;
		PreparedStatement psselect_roleconfid_v15 = null;
		PreparedStatement psinsert = null;
		PreparedStatement psupdate = null;
		PreparedStatement psdelete = null;
		Set<String> roleConfIds = new HashSet<String>();

		DataSet ds = new DataSet("ekpOmsIn");
		try {
			connV15 = ds.getConnection();
			psselect_roleconfid_v15 = connV15
					.prepareStatement(
							"select fd_id from sys_org_role_conf");
			ResultSet rs_roleconf = psselect_roleconfid_v15.executeQuery();
			while (rs_roleconf.next()) {
				String fd_groupid = rs_roleconf.getString(1);
				roleConfIds.add(fd_groupid);
			}
			rs_roleconf.close();

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			int loop = 0;
			// 删除同步过来的岗位的关系
			psdelete = conn
					.prepareStatement(
							"delete from sys_org_role where fd_role_conf_id = ?");
			int i = 0;
			for (String roleConfId : roleConfIds) {
				if (i > 0 && i % 2000 == 0) {
					psdelete.executeBatch();
					conn.commit();
				}
				psdelete.setString(1, roleConfId);
				psdelete.addBatch();
				i++;
			}
			psdelete.executeBatch();
			conn.commit();
			logger.info("删除sys_org_role完成");
			jobContext.logMessage("删除sys_org_role完成");

			psinsert = conn
					.prepareStatement(
							"insert into sys_org_role(fd_id,fd_plugin,fd_parameter,fd_is_multiple,fd_rtn_value,fd_role_conf_id) values(?,?,?,?,?,?)");
			psselect_all_v15 = connV15
					.prepareStatement(
							"select fd_id,fd_plugin,fd_parameter,fd_is_multiple,fd_rtn_value,fd_role_conf_id from sys_org_role where fd_role_conf_id is not null");
			ResultSet rs_all_15 = psselect_all_v15.executeQuery();
			while (rs_all_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psinsert.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_role, 插入第" + (loop / 2000)
									+ "批数据");
					jobContext.logMessage(
							"sys_org_role, 插入第" + (loop / 2000)
									+ "批数据");
				}
				String fd_id = rs_all_15.getString(1);
				String fd_plugin = rs_all_15.getString(2);
				String fd_parameter = rs_all_15.getString(3);
				Boolean fd_is_multiple = rs_all_15.getBoolean(4);
				String fd_rtn_value = rs_all_15.getString(5);
				String fd_role_conf_id = rs_all_15.getString(6);
				psinsert.setString(1, fd_id);
				psinsert.setString(2, fd_plugin);
				psinsert.setString(3, fd_parameter);
				psinsert.setBoolean(4, fd_is_multiple);
				psinsert.setString(5, fd_rtn_value);
				psinsert.setString(6, fd_role_conf_id);
				psinsert.addBatch();
				loop++;
			}
			psinsert.executeBatch();
			conn.commit();
			rs_all_15.close();
			loop = 0;

			// 处理通用岗位
			Set<String> ekpIds = new HashSet<String>();
			psselect_all = conn
					.prepareStatement(
							"select fd_id from sys_org_role where fd_role_conf_id is null");
			ResultSet rs_all = psselect_all.executeQuery();
			while (rs_all.next()) {
				String fd_id = rs_all.getString(1);
				ekpIds.add(fd_id);
			}
			rs_all.close();

			psselect_all_v15_confnull = connV15
					.prepareStatement(
							"select fd_id,fd_plugin,fd_parameter,fd_is_multiple,fd_rtn_value,fd_role_conf_id from sys_org_role where fd_role_conf_id is null");
			psupdate = conn
					.prepareStatement(
							"update sys_org_role set fd_plugin=?,fd_parameter=?,fd_is_multiple=?,fd_rtn_value=?,fd_role_conf_id=? where fd_id=?");

			ResultSet rs_all_15_confnull = psselect_all_v15_confnull
					.executeQuery();
			while (rs_all_15_confnull.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psinsert.executeBatch();
					psupdate.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_role, 插入第" + (loop / 2000)
									+ "批数据");
					jobContext.logMessage(
							"sys_org_role, 插入第" + (loop / 2000)
									+ "批数据");
				}
				String fd_id = rs_all_15_confnull.getString(1);
				String fd_plugin = rs_all_15_confnull.getString(2);
				String fd_parameter = rs_all_15_confnull.getString(3);
				Boolean fd_is_multiple = rs_all_15_confnull.getBoolean(4);
				String fd_rtn_value = rs_all_15_confnull.getString(5);
				String fd_role_conf_id = rs_all_15_confnull.getString(6);
				if (ekpIds.contains(fd_id)) {
					psupdate.setString(6, fd_id);
					psupdate.setString(1, fd_plugin);
					psupdate.setString(2, fd_parameter);
					psupdate.setBoolean(3, fd_is_multiple);
					psupdate.setString(4, fd_rtn_value);
					psupdate.setString(5, fd_role_conf_id);
					psupdate.addBatch();
					loop++;
				} else {
					psinsert.setString(1, fd_id);
					psinsert.setString(2, fd_plugin);
					psinsert.setString(3, fd_parameter);
					psinsert.setBoolean(4, fd_is_multiple);
					psinsert.setString(5, fd_rtn_value);
					psinsert.setString(6, fd_role_conf_id);
					psinsert.addBatch();
					loop++;
				}
			}
			psinsert.executeBatch();
			psupdate.executeBatch();
			conn.commit();
			rs_all_15.close();
			loop = 0;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jobContext.logError(e.getMessage(), e);
			conn.rollback();
			throw e;
		} finally {

			JdbcUtils.closeStatement(psupdate);

			JdbcUtils.closeStatement(psselect_all_v15_confnull);

			JdbcUtils.closeStatement(psselect_all);

			JdbcUtils.closeStatement(psselect_all_v15);

			JdbcUtils.closeStatement(psselect_roleconfid_v15);

			JdbcUtils.closeStatement(psinsert);

			JdbcUtils.closeStatement(psdelete);
			JdbcUtils.closeConnection(conn);

			try {
				ds.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private void syncSysOrgRoleline()
			throws Exception {
		logger.info("开始同步sys_org_role_line");
		jobContext.logMessage("开始同步sys_org_role_line");
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		Connection connV15 = null;
		PreparedStatement psselect_base_v15 = null;
		PreparedStatement psselect_all_v15 = null;
		PreparedStatement psselect_roleconfid_v15 = null;
		PreparedStatement psinsert = null;
		PreparedStatement psdelete = null;
		PreparedStatement psupdate = null;
		Set<String> roleConfIds = new HashSet<String>();

		DataSet ds = new DataSet("ekpOmsIn");
		try {
			connV15 = ds.getConnection();
			psselect_roleconfid_v15 = connV15
					.prepareStatement(
							"select fd_id from sys_org_role_conf");
			ResultSet rs_roleconf = psselect_roleconfid_v15.executeQuery();
			while (rs_roleconf.next()) {
				String fd_groupid = rs_roleconf.getString(1);
				roleConfIds.add(fd_groupid);
			}
			rs_roleconf.close();

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			int loop = 0;
			// 删除同步过来的岗位的关系
			psdelete = conn
					.prepareStatement(
							"delete from sys_org_role_line where fd_role_line_conf_id = ?");
			int i = 0;
			for (String roleConfId : roleConfIds) {
				if (i > 0 && i % 2000 == 0) {
					psdelete.executeBatch();
					conn.commit();
				}
				psdelete.setString(1, roleConfId);
				psdelete.addBatch();
				i++;
			}
			psdelete.executeBatch();
			conn.commit();
			logger.info("删除sys_org_role_line完成");
			jobContext.logMessage("删除sys_org_role_line完成");

			psinsert = conn
					.prepareStatement(
							"insert into sys_org_role_line(fd_id,fd_name,fd_hierarchy_id) values(?,?,?)");
			psselect_base_v15 = connV15
					.prepareStatement(
							"select fd_id,fd_name,fd_hierarchy_id from sys_org_role_line");
			ResultSet rs_base_15 = psselect_base_v15.executeQuery();
			while (rs_base_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psinsert.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_role_line, 插入第" + (loop / 2000)
									+ "批数据");
					jobContext.logMessage(
							"sys_org_role_line, 插入第" + (loop / 2000)
									+ "批数据");
				}
				String fd_id = rs_base_15.getString(1);
				String fd_name = rs_base_15.getString(2);
				String fd_hierarchy_id = rs_base_15.getString(3);
				psinsert.setString(1, fd_id);
				psinsert.setString(2, fd_name);
				psinsert.setString(3, fd_hierarchy_id);
				psinsert.addBatch();
				loop++;
			}
			psinsert.executeBatch();
			conn.commit();
			rs_base_15.close();
			loop = 0;

			psupdate = conn
					.prepareStatement(
							"update sys_org_role_line set fd_name=?,fd_order=?,fd_create_time=?,fd_hierarchy_id=?,fd_has_child=?,fd_member_id=?,fd_role_line_conf_id=?,fd_alter_time=?,fd_parent_id=? where fd_id=?");
			psselect_all_v15 = connV15
					.prepareStatement(
							"select fd_id,fd_name,fd_order,fd_create_time,fd_hierarchy_id,fd_has_child,fd_member_id,fd_role_line_conf_id,fd_alter_time,fd_parent_id from sys_org_role_line");
			ResultSet rs_all_15 = psselect_all_v15.executeQuery();
			while (rs_all_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psupdate.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_role_line, 更新第" + (loop / 2000)
									+ "批数据");
					jobContext.logMessage(
							"sys_org_role_line, 更新第" + (loop / 2000)
									+ "批数据");
				}
				String fd_id = rs_all_15.getString(1);
				String fd_name = rs_all_15.getString(2);
				Long fd_order = rs_all_15.getLong(3);
				Timestamp fd_create_time = rs_all_15.getTimestamp(4);
				String fd_hierarchy_id = rs_all_15.getString(5);
				Boolean fd_has_child = rs_all_15.getBoolean(6);
				String fd_member_id = rs_all_15.getString(7);
				String fd_role_line_conf_id = rs_all_15.getString(8);
				Timestamp fd_alter_time = rs_all_15.getTimestamp(9);
				String fd_parent_id = rs_all_15.getString(10);
				psupdate.setString(1, fd_name);
				psupdate.setLong(2, fd_order);
				psupdate.setTimestamp(3, fd_create_time);
				psupdate.setString(4, fd_hierarchy_id);
				psupdate.setBoolean(5, fd_has_child);
				psupdate.setString(6, fd_member_id);
				psupdate.setString(7, fd_role_line_conf_id);
				psupdate.setTimestamp(8, fd_alter_time == null ? new Timestamp(System.currentTimeMillis()) : fd_alter_time);
				psupdate.setString(9, fd_parent_id);
				psupdate.setString(10, fd_id);
				psupdate.addBatch();
				loop++;
			}
			psupdate.executeBatch();
			conn.commit();
			rs_all_15.close();
			loop = 0;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jobContext.logError(e.getMessage(), e);
			conn.rollback();
			throw e;
		} finally {

			JdbcUtils.closeStatement(psselect_base_v15);
			JdbcUtils.closeStatement(psselect_all_v15);
			JdbcUtils.closeStatement(psselect_roleconfid_v15);
			JdbcUtils.closeStatement(psinsert);
			JdbcUtils.closeStatement(psdelete);
			JdbcUtils.closeStatement(psupdate);
			JdbcUtils.closeConnection(conn);

			try {
				ds.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private void syncSysOrgRoleLineEditor()
			throws Exception {
		logger.info("开始同步sys_org_role_line_editor");
		jobContext.logMessage("开始同步sys_org_role_line_editor");
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		Connection connV15 = null;
		PreparedStatement psselect_all_v15 = null;
		PreparedStatement psselect_roleconfid_v15 = null;
		PreparedStatement psinsert = null;
		PreparedStatement psdelete = null;
		Set<String> roleconfIds = new HashSet<String>();

		DataSet ds = new DataSet("ekpOmsIn");
		try {
			connV15 = ds.getConnection();
			psselect_roleconfid_v15 = connV15
					.prepareStatement(
							"select fd_id from sys_org_role_conf");
			ResultSet rs_roleconf = psselect_roleconfid_v15.executeQuery();
			while (rs_roleconf.next()) {
				String fd_groupid = rs_roleconf.getString(1);
				roleconfIds.add(fd_groupid);
			}
			rs_roleconf.close();

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			int loop = 0;
			// 删除同步过来的岗位的关系
			psdelete = conn
					.prepareStatement(
							"delete from sys_org_role_line_editor where fd_role_line_conf_id = ?");
			int i = 0;
			for (String roleconfid : roleconfIds) {
				if (i > 0 && i % 2000 == 0) {
					psdelete.executeBatch();
					conn.commit();
				}
				psdelete.setString(1, roleconfid);
				psdelete.addBatch();
				i++;
			}
			psdelete.executeBatch();
			conn.commit();
			logger.info("删除sys_org_role_line_editor完成");
			jobContext.logMessage("删除sys_org_role_line_editor完成");

			psinsert = conn
					.prepareStatement(
							"insert into sys_org_role_line_editor(fd_role_line_conf_id,auth_editor_id) values(?,?)");
			psselect_all_v15 = connV15
					.prepareStatement(
							"select fd_role_line_conf_id,auth_editor_id from sys_org_role_line_editor");
			ResultSet rs_all_15 = psselect_all_v15.executeQuery();
			while (rs_all_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psinsert.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_role_line_editor, 同步第" + (loop / 2000)
									+ "批数据");
					jobContext.logMessage(
							"sys_org_role_line_editor, 同步第" + (loop / 2000)
									+ "批数据");
				}
				String fd_role_line_conf_id = rs_all_15.getString(1);
				String auth_editor_id = rs_all_15.getString(2);
				psinsert.setString(1, fd_role_line_conf_id);
				psinsert.setString(2, auth_editor_id);
				psinsert.addBatch();
				loop++;

			}
			psinsert.executeBatch();
			conn.commit();
			rs_all_15.close();
			loop = 0;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jobContext.logError(e.getMessage(), e);
			conn.rollback();
			throw e;
		} finally {

			JdbcUtils.closeStatement(psselect_all_v15);
			JdbcUtils.closeStatement(psdelete);
			JdbcUtils.closeStatement(psinsert);
			JdbcUtils.closeStatement(psselect_roleconfid_v15);
			JdbcUtils.closeConnection(conn);

			try {
				ds.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private void syncSysOrgRoleLineReader()
			throws Exception {
		logger.info("开始同步sys_org_role_line_reader");
		jobContext.logMessage("开始同步sys_org_role_line_reader");
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		Connection connV15 = null;
		PreparedStatement psselect_all_v15 = null;
		PreparedStatement psselect_roleconfid_v15 = null;
		PreparedStatement psinsert = null;
		PreparedStatement psdelete = null;
		Set<String> roleconfIds = new HashSet<String>();

		DataSet ds = new DataSet("ekpOmsIn");
		try {
			connV15 = ds.getConnection();
			psselect_roleconfid_v15 = connV15
					.prepareStatement(
							"select fd_id from sys_org_role_conf");
			ResultSet rs_roleconf = psselect_roleconfid_v15.executeQuery();
			while (rs_roleconf.next()) {
				String fd_groupid = rs_roleconf.getString(1);
				roleconfIds.add(fd_groupid);
			}
			rs_roleconf.close();

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			int loop = 0;
			// 删除同步过来的岗位的关系
			psdelete = conn
					.prepareStatement(
							"delete from sys_org_role_line_reader where fd_role_line_conf_id = ?");
			int i = 0;
			for (String roleconfid : roleconfIds) {
				if (i > 0 && i % 2000 == 0) {
					psdelete.executeBatch();
					conn.commit();
				}
				psdelete.setString(1, roleconfid);
				psdelete.addBatch();
				i++;
			}
			psdelete.executeBatch();
			conn.commit();
			logger.info("删除sys_org_role_line_reader完成");
			jobContext.logMessage("删除sys_org_role_line_reader完成");

			psinsert = conn
					.prepareStatement(
							"insert into sys_org_role_line_reader(fd_role_line_conf_id,auth_reader_id) values(?,?)");
			psselect_all_v15 = connV15
					.prepareStatement(
							"select fd_role_line_conf_id,auth_reader_id from sys_org_role_line_reader");
			ResultSet rs_all_15 = psselect_all_v15.executeQuery();
			while (rs_all_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psinsert.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_role_line_reader, 同步第" + (loop / 2000)
									+ "批数据");
					jobContext.logMessage(
							"sys_org_role_line_reader, 同步第" + (loop / 2000)
									+ "批数据");
				}
				String fd_role_line_conf_id = rs_all_15.getString(1);
				String auth_reader_id = rs_all_15.getString(2);
				psinsert.setString(1, fd_role_line_conf_id);
				psinsert.setString(2, auth_reader_id);
				psinsert.addBatch();
				loop++;
			}
			psinsert.executeBatch();
			conn.commit();
			rs_all_15.close();
			loop = 0;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jobContext.logError(e.getMessage(), e);
			conn.rollback();
			throw e;
		} finally {
			JdbcUtils.closeStatement(psselect_all_v15);
			JdbcUtils.closeStatement(psdelete);
			JdbcUtils.closeStatement(psinsert);
			JdbcUtils.closeStatement(psselect_roleconfid_v15);
			JdbcUtils.closeConnection(conn);
			try {
				ds.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private void syncSysOrgRolelineDefaultRole()
			throws Exception {
		logger.info("开始同步sys_org_role_line_default_role");
		jobContext.logMessage("开始同步sys_org_role_line_default_role");
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		Connection connV15 = null;
		PreparedStatement psselect_all_v15 = null;
		PreparedStatement psselect_roleconfid_v15 = null;
		PreparedStatement psinsert = null;
		PreparedStatement psdelete = null;
		Set<String> roleConfIds = new HashSet<String>();

		DataSet ds = new DataSet("ekpOmsIn");
		try {
			connV15 = ds.getConnection();
			psselect_roleconfid_v15 = connV15
					.prepareStatement(
							"select fd_id from sys_org_role_conf");
			ResultSet rs_roleconf = psselect_roleconfid_v15.executeQuery();
			while (rs_roleconf.next()) {
				String fd_groupid = rs_roleconf.getString(1);
				roleConfIds.add(fd_groupid);
			}
			rs_roleconf.close();

			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			int loop = 0;
			// 删除同步过来的岗位的关系
			psdelete = conn
					.prepareStatement(
							"delete from sys_org_role_line_default_role where fd_role_line_conf_id = ?");
			int i = 0;
			for (String roleConfId : roleConfIds) {
				if (i > 0 && i % 2000 == 0) {
					psdelete.executeBatch();
					conn.commit();
				}
				psdelete.setString(1, roleConfId);
				psdelete.addBatch();
			}
			psdelete.executeBatch();
			conn.commit();
			logger.info("删除sys_org_role_line_default_role完成");

			String fieldStr = "fd_id,fd_create_time,fd_alter_time,fd_role_line_conf_id,fd_person_id,fd_post_id";
			psinsert = conn
					.prepareStatement(
							"insert into sys_org_role_line_default_role("
									+ fieldStr + ") values(?,?,?,?,?,?)");
			psselect_all_v15 = connV15
					.prepareStatement(
							"select " + fieldStr
									+ " from sys_org_role_line_default_role");
			ResultSet rs_all_15 = psselect_all_v15.executeQuery();
			while (rs_all_15.next()) {
				if (loop > 0 && (loop % 2000 == 0)) {
					psinsert.executeBatch();
					conn.commit();
					logger.info(
							"sys_org_role_line, 插入第" + (loop / 2000)
									+ "批数据");
					jobContext.logMessage(
							"sys_org_role_line, 插入第" + (loop / 2000)
									+ "批数据");
				}
				String fd_id = rs_all_15.getString(1);
				Timestamp fd_create_time = rs_all_15.getTimestamp(2);
				Timestamp fd_alter_time = rs_all_15.getTimestamp(3);
				String fd_role_line_conf_id = rs_all_15.getString(4);
				String fd_person_id = rs_all_15.getString(5);
				String fd_post_id = rs_all_15.getString(6);
				psinsert.setString(1, fd_id);
				psinsert.setTimestamp(2, fd_create_time);
				psinsert.setTimestamp(2, fd_alter_time == null ? new Timestamp(System.currentTimeMillis()) : fd_alter_time);
				psinsert.setString(4, fd_role_line_conf_id);
				psinsert.setString(5, fd_person_id);
				psinsert.setString(6, fd_post_id);
				psinsert.addBatch();
				loop++;
			}
			psinsert.executeBatch();
			conn.commit();
			rs_all_15.close();
			loop = 0;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jobContext.logError(e.getMessage(), e);
			conn.rollback();
			throw e;
		} finally {

			JdbcUtils.closeStatement(psselect_all_v15);

			JdbcUtils.closeStatement(psselect_roleconfid_v15);

			JdbcUtils.closeStatement(psinsert);

			JdbcUtils.closeStatement(psdelete);
			JdbcUtils.closeConnection(conn);

			try {
				ds.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

}
