package com.landray.kmss.sys.organization.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.dao.ISysOrgElementBakDao;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.property.custom.DynamicAttributeConfig;
import com.landray.kmss.sys.property.custom.DynamicAttributeField;
import com.landray.kmss.sys.property.custom.DynamicAttributeUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class SysOrgElementBakDaoImp extends BaseDaoImp implements
		ISysOrgElementBakDao, SysOrgConstant {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgElementBakDaoImp.class);


	public static String element_fields = "fd_id,fd_org_type,fd_name,fd_name_pinyin,fd_name_simple_pinyin,fd_order,fd_no,fd_keyword,fd_is_available,fd_is_abandon,fd_is_business,fd_import_info,fd_flag_deleted,fd_ldap_dn,fd_memo,fd_hierarchy_id,fd_create_time,fd_alter_time,fd_org_email,fd_this_leaderid,fd_super_leaderid,fd_parentorgid,fd_parentid";
	public static String group_cate_fields = "fd_id,fd_name,fd_keyword,fd_create_time,fd_alter_time,fd_parentid";
	public static String person_fields = "fd_id,fd_mobile_no,fd_email,fd_login_name,fd_password,fd_init_password,fd_rtx_no,fd_wechat_no,fd_card_no,fd_attendance_card_number,fd_work_phone,"
			+ "fd_default_lang,fd_sex,fd_last_change_pwd,fd_lock_time,fd_short_no,fd_staffing_level_id,fd_double_validation";
	public static String group_element_fields = "fd_groupid,fd_elementid";
	public static String post_person_fields = "fd_personid,fd_postid";
	public static String role_fields = "fd_id,fd_plugin,fd_parameter,fd_is_multiple,fd_rtn_value,fd_role_conf_id";
			
	@Override
	public void backUp() throws Exception {
		/*
		 * Session session = getSession(); Transaction tr =
		 * session.beginTransaction(); session .createQuery(
		 * "insert into com.landray.kmss.sys.organization.model.SysOrgRoleBak select from com.landray.kmss.sys.organization.model.SysOrgRole"
		 * ) .executeUpdate(); session .createQuery(
		 * "insert into com.landray.kmss.sys.organization.model.SysOrgGroupBak select from com.landray.kmss.sys.organization.model.SysOrgGroup"
		 * ) .executeUpdate(); session .createQuery(
		 * "insert into com.landray.kmss.sys.organization.model.SysOrgPostBak select from com.landray.kmss.sys.organization.model.SysOrgPost"
		 * ) .executeUpdate(); session .createQuery(
		 * "insert into com.landray.kmss.sys.organization.model.SysOrgOrgBak select from com.landray.kmss.sys.organization.model.SysOrgOrg"
		 * ) .executeUpdate(); session .createQuery(
		 * "insert into com.landray.kmss.sys.organization.model.SysOrgDeptBak select from com.landray.kmss.sys.organization.model.SysOrgDept"
		 * ) .executeUpdate(); session .createQuery(
		 * "insert into com.landray.kmss.sys.organization.model.SysOrgPersonBak select from com.landray.kmss.sys.organization.model.SysOrgPerson"
		 * ) .executeUpdate(); tr.commit();
		 */
		String element_fields = SysOrgElementBakDaoImp.element_fields;
		if (SysLangUtil.isLangEnabled()) {
			Map<String, String> langs = SysLangUtil.getSupportedLangs();
			for (String lang : langs.keySet()) {
				element_fields += ", fd_name_" + lang;
			}
		}

		String person_fields = SysOrgElementBakDaoImp.person_fields;
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
		String sysOrgGroupCateBak_sql = "insert into sys_org_group_cate_bak("
				+ group_cate_fields + ") select " + group_cate_fields
				+ " from sys_org_group_cate";
		String sysOrgElementBak_sql = "insert into sys_org_element_bak ("
				+ element_fields
				+ ") select "
				+ element_fields
				+ " from sys_org_element";
		String sysOrgPersonBak_sql = "insert into sys_org_person_bak("
				+ person_fields + ") select " + person_fields
				+ " from sys_org_person";
		String sysOrgGroupElementBak_sql = "insert into sys_org_group_element_bak("
				+ group_element_fields + ") select " + group_element_fields
				+ " from sys_org_group_element";
		String sysOrgPostPersonBak_sql = "insert into sys_org_post_person_bak("
				+ post_person_fields + ") select " + post_person_fields
				+ " from sys_org_post_person";
		String sysOrgRoleBak_sql = "insert into sys_org_role_bak(" + role_fields
				+ ") select " + role_fields + " from sys_org_role";
		
		DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
		PreparedStatement psSysOrgGroupCateBak = null;
		PreparedStatement psSysOrgElementBak = null;
		PreparedStatement psSysOrgPersonBak = null;
		PreparedStatement psSysOrgGroupElementBak = null;
		PreparedStatement psSysOrgPostPersonBak = null;
		PreparedStatement psSysOrgRoleBak = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			
			psSysOrgGroupCateBak = conn
					.prepareStatement(sysOrgGroupCateBak_sql);
			psSysOrgGroupCateBak.executeUpdate();

			psSysOrgElementBak = conn.prepareStatement(sysOrgElementBak_sql);
			psSysOrgElementBak.executeUpdate();
			
			psSysOrgPersonBak = conn.prepareStatement(sysOrgPersonBak_sql);
			psSysOrgPersonBak.executeUpdate();

			psSysOrgGroupElementBak = conn
					.prepareStatement(sysOrgGroupElementBak_sql);
			psSysOrgGroupElementBak.executeUpdate();

			psSysOrgPostPersonBak = conn
					.prepareStatement(sysOrgPostPersonBak_sql);
			psSysOrgPostPersonBak.executeUpdate();

			psSysOrgRoleBak = conn.prepareStatement(sysOrgRoleBak_sql);
			psSysOrgRoleBak.executeUpdate();

		} catch (Exception ex) {
			logger.error("备份数据backUp发生异常："+ex);
			throw ex;
		} finally {
			// 关闭流
			JdbcUtils.closeStatement(psSysOrgGroupCateBak);
			JdbcUtils.closeStatement(psSysOrgElementBak);
			JdbcUtils.closeStatement(psSysOrgPersonBak);
			JdbcUtils.closeStatement(psSysOrgGroupElementBak );
			JdbcUtils.closeStatement(psSysOrgPostPersonBak);
			JdbcUtils.closeStatement(psSysOrgRoleBak);
			JdbcUtils.closeConnection(conn);
		}
	}

	@Override
	public void clean() throws Exception {
		// TODO 自动生成的方法存根
		
		String sysOrgRoleBak_sql = "delete from sys_org_role_bak";
		String sysOrgPostPersonBak_sql = "delete from sys_org_post_person_bak" ;
		String sysOrgGroupElementBak_sql = "delete from sys_org_group_element_bak";
		String sysOrgPersonBak_sql = "delete from sys_org_person_bak";
		String sysOrgElementBak_sql = "delete from sys_org_element_bak";
		String sysOrgGroupCateBak_sql = "delete from sys_org_group_cate_bak";
		
		DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
		PreparedStatement psSysOrgRoleBak = null;
		PreparedStatement psSysOrgPostPersonBak = null;
		PreparedStatement psSysOrgGroupElementBak = null;
		PreparedStatement psSysOrgPersonBak = null;
		PreparedStatement psSysOrgElementBak = null;
		PreparedStatement psSysOrgGroupCateBak = null;
		
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			psSysOrgRoleBak = conn.prepareStatement(sysOrgRoleBak_sql);
			psSysOrgRoleBak.executeUpdate();
			
			psSysOrgPostPersonBak = conn.prepareStatement(sysOrgPostPersonBak_sql);
			psSysOrgPostPersonBak.executeUpdate();
			
			psSysOrgGroupElementBak = conn.prepareStatement(sysOrgGroupElementBak_sql);
			psSysOrgGroupElementBak.executeUpdate();
			
			psSysOrgPersonBak = conn.prepareStatement(sysOrgPersonBak_sql);
			psSysOrgPersonBak.executeUpdate();
			
			psSysOrgElementBak = conn.prepareStatement(sysOrgElementBak_sql);
			psSysOrgElementBak.executeUpdate();
			
			psSysOrgGroupCateBak = conn.prepareStatement(sysOrgGroupCateBak_sql);
			psSysOrgGroupCateBak.executeUpdate();
			
		} catch (Exception ex) {
			logger.error("清除数据clean发生异常："+ex);
			throw ex;
		} finally {
			// 关闭流
			JdbcUtils.closeStatement(psSysOrgRoleBak);
			JdbcUtils.closeStatement(psSysOrgPostPersonBak);
			JdbcUtils.closeStatement(psSysOrgGroupElementBak);
			JdbcUtils.closeStatement(psSysOrgPersonBak);
			JdbcUtils.closeStatement(psSysOrgElementBak);
			JdbcUtils.closeStatement(psSysOrgGroupCateBak);
			
			JdbcUtils.closeConnection(conn);
		}
	}

}
