package com.landray.kmss.sys.organization.service.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.dao.ISysOrgElementBakDao;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementBakService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.property.custom.DynamicAttributeConfig;
import com.landray.kmss.sys.property.custom.DynamicAttributeField;
import com.landray.kmss.sys.property.custom.DynamicAttributeUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;

public class SysOrgElementBakServiceImp extends BaseServiceImp implements
		ISysOrgElementBakService {

	private ISysOrgElementService orgElementService = null;

	public static String element_fields = "fd_org_type,fd_name,fd_name_pinyin,fd_name_simple_pinyin,fd_order,fd_no,fd_keyword,fd_is_available,fd_is_abandon,fd_is_business,fd_import_info,fd_flag_deleted,fd_ldap_dn,fd_memo,fd_hierarchy_id,fd_create_time,fd_alter_time,fd_org_email,fd_this_leaderid,fd_super_leaderid,fd_parentorgid,fd_parentid";
	public static String group_cate_fields = "fd_name,fd_keyword,fd_create_time,fd_alter_time,fd_parentid";
	public static String person_fields = "fd_mobile_no,fd_email,fd_login_name,fd_password,fd_init_password,fd_rtx_no,fd_wechat_no,fd_card_no,fd_attendance_card_number,fd_work_phone,"
			+ "fd_default_lang,fd_sex,fd_last_change_pwd,fd_lock_time,fd_short_no,fd_staffing_level_id,fd_double_validation";
	public static String group_element_fields = "fd_groupid,fd_elementid";
	public static String post_person_fields = "fd_personid,fd_postid";
	public static String role_fields = "fd_plugin,fd_parameter,fd_is_multiple,fd_rtn_value,fd_role_conf_id";

	public ISysOrgElementService getOrgElementService() {
		return orgElementService;
	}

	public void setOrgElementService(ISysOrgElementService orgElementService) {
		this.orgElementService = orgElementService;
	}

	private ISysOrgCoreService orgCoreService = null;

	public ISysOrgCoreService getOrgCoreService() {
		return orgCoreService;
	}

	public void setOrgCoreService(ISysOrgCoreService orgCoreService) {
		this.orgCoreService = orgCoreService;
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgElementBakServiceImp.class);

	@Override
	public void clean() throws Exception {
		((ISysOrgElementBakDao) getBaseDao()).clean();
	}

	@Override
	public void backUp() throws Exception {
		((ISysOrgElementBakDao) getBaseDao()).backUp();
	}

	@Override
	public void restore() throws Exception {
		// TODO 自动生成的方法存根
		try {
			updateGroupCateBase();
			updateGroupCateDetail();
			flagDeleted(true);
			updateBaseAttributes();
			delete();
			updateDetailAttributes();
			flagDeleted(false);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	private void delete() throws Exception {
		String[] deleteKeywords = getKeywordsForDelete();
		TransactionStatus deleteStatus = TransactionUtils.beginNewTransaction();
		try {
			deleteElements(deleteKeywords);
			TransactionUtils.getTransactionManager().commit(deleteStatus);
		} catch (Exception ex) {
			TransactionUtils.getTransactionManager().rollback(deleteStatus);
			throw ex;
		}
	}

	public static void main(String[] args) {
//		String s = "fd_id,fd_org_type,fd_name,fd_name_pinyin,fd_order,fd_no,fd_keyword,fd_is_available,fd_is_abandon,fd_is_business,fd_import_info,fd_flag_deleted,fd_ldap_dn,fd_memo,fd_hierarchy_id,fd_create_time,fd_alter_time,fd_this_leaderid,fd_super_leaderid,fd_parentorgid,fd_parentid,fd_cateid";
//		s = "fd_id,fd_mobile_no,fd_email,fd_login_name,fd_password,fd_init_password,fd_rtx_no,fd_card_no,fd_attendance_card_number,fd_work_phone,fd_default_lang,fd_sex,fd_wechat_no";
//		String[] ss = s.split(",");
//		String result = "";
//		for (String a : ss) {
//			result += "b." + a + ",";
//		}
//		logger.info(result);
	}

	private void updateDetailAttributes() throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		String driverClassName = (String) PropertyUtils.getProperty(dataSource,
				"targetDataSource.driverClassName");
		Connection conn = null;
		PreparedStatement psupdate_element = null;
		PreparedStatement psupdate_person = null;
		PreparedStatement psdelete_post_person = null;
		PreparedStatement psdelete_group_element = null;
		PreparedStatement psinsert_post_person = null;
		PreparedStatement psinsert_group_element = null;
		// PreparedStatement psupdate_role = null;
		String element_fields = SysOrgElementBakServiceImp.element_fields;
		Map<String, String> langs = SysLangUtil.getSupportedLangs();
		for (String lang : langs.keySet()) {
				element_fields += ", fd_name_" + lang;
		}
		String[] ele_fields = element_fields.split(",");
		String element_update = "";
		for (String field : ele_fields) {
			element_update += "sys_org_element." + field
					+ "=sys_org_element_bak." + field + ",";
		}
		element_update = element_update.substring(0,
				element_update.length() - 1);

		String person_fields = SysOrgElementBakServiceImp.person_fields;
		DynamicAttributeConfig config = DynamicAttributeUtil
				.getDynamicAttributeConfig(SysOrgPerson.class.getName());
		if (config != null) {
			for (DynamicAttributeField field : config.getFields()) {
				if (!"true".equals(field.getStatus())) {
                    continue; // 不加载失效的属性
                }
				person_fields += "," + field.getColumnName();
			}
		}
		String[] per_fields = person_fields.split(",");
		String person_update = "";
		for (String field : per_fields) {
			person_update += "sys_org_person." + field + "=sys_org_person_bak."
					+ field + ",";
		}
		person_update = person_update.substring(0, person_update.length() - 1);
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			String sql_update_element = "update sys_org_element set "
					+ element_update
					+ " from sys_org_element_bak where sys_org_element.fd_id=sys_org_element_bak.fd_id";
			String sql_update_person = "update sys_org_person set "
					+ person_update
					+ " from sys_org_person_bak where sys_org_person.fd_id=sys_org_person_bak.fd_id";
			logger.info("driverClassName:" + driverClassName);
			if (driverClassName.toLowerCase().contains("mysql")) {
				sql_update_element = "update sys_org_element inner join sys_org_element_bak on sys_org_element.fd_id=sys_org_element_bak.fd_id set "
						+ element_update;
				sql_update_person = "update sys_org_person inner join sys_org_person_bak on sys_org_person.fd_id=sys_org_person_bak.fd_id set "
						+ person_update;
			} else if (driverClassName.toLowerCase().contains("oracle")) {
				ele_fields = element_fields.split(",");
				String element_a = "";
				String element_b = "";
				for (String field : ele_fields) {
					element_a += "a." + field
							+ ",";
					element_b += "b." + field
							+ ",";
				}
				element_a = element_a.substring(0,
						element_a.length() - 1);
				element_b = element_b.substring(0,
						element_b.length() - 1);

				per_fields = person_fields.split(",");
				String person_a = "";
				String person_b = "";
				for (String field : per_fields) {
					person_a += "a." + field + ",";
					person_b += "b." + field + ",";
				}
				person_a = person_a.substring(0,
						person_a.length() - 1);
				person_b = person_b.substring(0,
						person_b.length() - 1);

				sql_update_element = "update sys_org_element a set ("
						+ element_a + ")=(select " + element_b
						+ " from sys_org_element_bak b where a.fd_id=b.fd_id) WHERE EXISTS (SELECT b.fd_id FROM sys_org_element_bak b WHERE a.fd_id=b.fd_id)";
				sql_update_person = "update sys_org_person a set (" + person_a
						+ ")=(select " + person_b
						+ " from sys_org_person_bak b where a.fd_id=b.fd_id) WHERE EXISTS (SELECT b.fd_id FROM sys_org_person_bak b WHERE a.fd_id=b.fd_id)";
			}
			psupdate_element = conn.prepareStatement(sql_update_element);
			psupdate_person = conn.prepareStatement(sql_update_person);
			// psupdate_role = conn.prepareStatement("");
			psdelete_post_person = conn
					.prepareStatement("delete from sys_org_post_person");
			psdelete_group_element = conn
					.prepareStatement("delete from sys_org_group_element");
			psinsert_post_person = conn
					.prepareStatement(
							"insert into sys_org_post_person (fd_personid,fd_postid) select fd_personid,fd_postid from sys_org_post_person_bak");
			psinsert_group_element = conn
					.prepareStatement(
							"insert into sys_org_group_element (fd_elementid,fd_groupid) select fd_elementid,fd_groupid from sys_org_group_element_bak");
			logger.info("sql_update_element:" + sql_update_element);
			logger.info("sql_update_person:" + sql_update_person);
			logger.info("psdelete_post_person:"
					+ "delete from sys_org_post_person");
			logger.info("psdelete_group_element:"
					+ "delete from sys_org_group_element");
			logger.info("psinsert_post_person:"
					+ "insert into sys_org_post_person (fd_personid,fd_postid) select fd_personid,fd_postid from sys_org_post_person_bak");
			logger.info("psinsert_group_element:"
					+ "insert into sys_org_group_element (fd_elementid,fd_groupid) select fd_elementid,fd_groupid from sys_org_group_element_bak");
			psupdate_element.executeUpdate();
			psupdate_person.executeUpdate();
			psdelete_post_person.executeUpdate();
			psdelete_group_element.executeUpdate();
			psinsert_post_person.executeUpdate();
			psinsert_group_element.executeUpdate();

			conn.commit();
		} catch (Exception ex) {
			logger.error("更新详细属性方法(updateDetailAttributes)发生异常："+ex);
			if(conn!=null) {
                conn.rollback();
            }
			throw ex;
		} finally {
			// 关闭流
			JdbcUtils.closeStatement(psupdate_element);
			JdbcUtils.closeStatement(psupdate_person);
			JdbcUtils.closeStatement(psdelete_post_person);
			JdbcUtils.closeStatement(psdelete_group_element);
			JdbcUtils.closeStatement(psinsert_post_person);
			JdbcUtils.closeStatement(psinsert_group_element);
			JdbcUtils.closeConnection(conn);
		}

	}

	private void updateGroupCateBase() throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement psselect_cate_bak = null;
		PreparedStatement psselect_cate = null;
		PreparedStatement psinsert_cate = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			psselect_cate_bak = conn
					.prepareStatement("select fd_id,fd_name from sys_org_group_cate_bak");
			psselect_cate = conn
					.prepareStatement("select fd_id from sys_org_group_cate where fd_id = ?");
			psinsert_cate = conn
					.prepareStatement("insert into sys_org_group_cate(fd_id,fd_name) values(?,?)");
			rs = psselect_cate_bak.executeQuery();
			while (rs.next()) {
				String fdId = rs.getString(1);
				psselect_cate.setString(1, fdId);
				rs2 = psselect_cate.executeQuery();
				if (!rs2.next()) {
					String fd_name = rs.getString(2);
					psinsert_cate.setString(1, fdId);
					psinsert_cate.setString(2, fd_name);
					psinsert_cate.addBatch();
				}
			}
			psinsert_cate.executeBatch();
			conn.commit();
		} catch (Exception ex) {
			logger.error("更新组方法(updateGroupCateBase)发生异常："+ex);
			if(conn!=null) {
                conn.rollback();
            }
			throw ex;
		} finally {
			// 关闭流
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeResultSet(rs2);
			JdbcUtils.closeStatement(psselect_cate_bak);
			JdbcUtils.closeStatement(psselect_cate);			
			JdbcUtils.closeStatement(psinsert_cate);
			
			JdbcUtils.closeConnection(conn);
		}
	}

	private void updateGroupCateDetail() throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		String driverClassName = (String) PropertyUtils.getProperty(dataSource,
				"targetDataSource.driverClassName");
		Connection conn = null;
		PreparedStatement psupdate_cate = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			String sql_update_cate = "update sys_org_group_cate set sys_org_group_cate.fd_name=sys_org_group_cate_bak.fd_name,sys_org_group_cate.fd_keyword=sys_org_group_cate_bak.fd_keyword,sys_org_group_cate.fd_create_time=sys_org_group_cate_bak.fd_create_time,sys_org_group_cate.fd_alter_time=sys_org_group_cate_bak.fd_alter_time,sys_org_group_cate.fd_parentid=sys_org_group_cate_bak.fd_parentid from sys_org_group_cate_bak where sys_org_group_cate.fd_id= sys_org_group_cate_bak.fd_id";
			if (driverClassName.toLowerCase().contains("mysql")) {
				sql_update_cate = "update sys_org_group_cate inner join sys_org_group_cate_bak on sys_org_group_cate.fd_id= sys_org_group_cate_bak.fd_id set sys_org_group_cate.fd_name=sys_org_group_cate_bak.fd_name,sys_org_group_cate.fd_keyword=sys_org_group_cate_bak.fd_keyword,sys_org_group_cate.fd_create_time=sys_org_group_cate_bak.fd_create_time,sys_org_group_cate.fd_alter_time=sys_org_group_cate_bak.fd_alter_time,sys_org_group_cate.fd_parentid=sys_org_group_cate_bak.fd_parentid";
			} else if (driverClassName.toLowerCase().contains("oracle")) {
				sql_update_cate = "update sys_org_group_cate a set (a.fd_name,a.fd_keyword,a.fd_create_time,a.fd_alter_time,a.fd_parentid)=(select b.fd_name,b.fd_keyword,b.fd_create_time,b.fd_alter_time,b.fd_parentid from sys_org_group_cate_bak b where a.fd_id= b.fd_id) where EXISTS (SELECT b.fd_id FROM sys_org_group_cate_bak b WHERE a.fd_id=b.fd_id)";
			}
			psupdate_cate = conn.prepareStatement(sql_update_cate);
			psupdate_cate.executeBatch();
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if(conn!=null) {
                conn.rollback();
            }
			throw ex;
		} finally {
			// 关闭流
			JdbcUtils.closeStatement(psupdate_cate);
			JdbcUtils.closeConnection(conn);
		}
	}

	private void updateBaseAttributes() throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement psselect_element_bak = null;
		PreparedStatement psselect = null;
		PreparedStatement psupdate_delete_flag = null;
		PreparedStatement psinsert_element = null;
		PreparedStatement psinsert_person = null;
		PreparedStatement psinsert_role = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			psselect_element_bak = conn
					.prepareStatement("select fd_id,fd_name,fd_org_type,fd_is_available,fd_is_business from sys_org_element_bak");
			psselect = conn
					.prepareStatement("select fd_id from sys_org_element where fd_id = ?");
			psupdate_delete_flag = conn
					.prepareStatement("update sys_org_element set fd_flag_deleted=? where fd_id=?");
			psinsert_element = conn
					.prepareStatement("insert into sys_org_element(fd_id,fd_name,fd_org_type,fd_is_available,fd_is_business,fd_flag_deleted) values(?,?,?,?,?,?,?,?,?,?)");
			psinsert_person = conn
					.prepareStatement("insert into sys_org_person(fd_id) values(?)");
			psinsert_role = conn
					.prepareStatement(
							"insert into sys_org_role(fd_id,fd_plugin,fd_parameter,fd_is_multiple,fd_rtn_value,fd_role_conf_id) select fd_id,fd_plugin,fd_parameter,fd_is_multiple,fd_rtn_value,fd_role_conf_id from sys_org_role_bak where fd_id = ?");

			ResultSet rs2 = null;

			ResultSet rs = psselect_element_bak.executeQuery();
			while (rs.next()) {
				String fdId = rs.getString(1);
				psselect.setString(1, fdId);
				rs2 = psselect.executeQuery();
				if (rs2.next()) {
					psupdate_delete_flag.setBoolean(1, false);
					psupdate_delete_flag.setString(2, fdId);
					psupdate_delete_flag.addBatch();
				} else {
					String fd_name = rs.getString(2);
					int fd_org_type = rs.getInt(3);
					boolean fd_is_available = rs.getBoolean(4);
					boolean fd_is_business = rs.getBoolean(5);
					psinsert_element.setString(1, fdId);
					psinsert_element.setString(2, fd_name);
					psinsert_element.setInt(3, fd_org_type);
					psinsert_element.setBoolean(4, fd_is_available);
					psinsert_element.setBoolean(5, fd_is_business);
					psinsert_element.setBoolean(6, false);
					psinsert_element.addBatch();
					if (fd_org_type == 8) {
						psinsert_person.setString(1, fdId);
						psinsert_person.addBatch();
					} else if (fd_org_type == 32) {
						psinsert_role.setString(1, fdId);
						psinsert_role.addBatch();
					}
				}
			}
			psupdate_delete_flag.executeBatch();
			psinsert_element.executeBatch();
			psinsert_person.executeBatch();
			psinsert_role.executeBatch();
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if(conn!=null) {
                conn.rollback();
            }
			throw ex;
		} finally {
			// 关闭流
			JdbcUtils.closeStatement(psselect);
			JdbcUtils.closeStatement(psselect_element_bak);
			JdbcUtils.closeStatement(psupdate_delete_flag);
			JdbcUtils.closeStatement(psinsert_element);
			JdbcUtils.closeStatement(psinsert_person);
			JdbcUtils.closeStatement(psinsert_role);
			JdbcUtils.closeConnection(conn);
		}
	}

	private void flagDeleted(boolean fd_flag_deleted) throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			ps = conn
					.prepareStatement("update sys_org_element set fd_flag_deleted = ?");
			ps.setBoolean(1, fd_flag_deleted);
			ps.executeUpdate();
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if(conn!=null) {
                conn.rollback();
            }
			throw ex;
		} finally {
			// 关闭流
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(conn);
		}
	}

	private void deleteElements(String[] keywords) throws Exception {
		for (int i = 0; i < keywords.length; i++) {
			SysOrgElement sysOrgElement = orgCoreService
					.findByPrimaryKey(keywords[i]);
			if (sysOrgElement != null) {
				sysOrgElement.setFdIsAvailable(new Boolean(false));
				sysOrgElement.getHbmChildren().clear();
				sysOrgElement.setFdFlagDeleted(new Boolean(false));
				orgElementService.update(sysOrgElement);
			}
		}

	}

	public String[] getKeywordsForDelete() throws Exception {
		String sql = " select fd_id from sys_org_element where fd_is_available = ? and fd_flag_deleted = ?";
		List ids = new ArrayList();
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setBoolean(1, true);
			ps.setBoolean(2, true);
			rs = ps.executeQuery();
			while (rs.next()) {
				ids.add(rs.getString(1));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			// 关闭流
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(conn);
		}
		return (String[]) ids.toArray(new String[0]);
	}

}
