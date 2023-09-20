<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="net.sf.json.JSONArray"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page import="com.landray.kmss.sys.profile.util.ProfileMenuUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.framework.util.PluginConfigLocationsUtil"%>
<%@ page import="java.io.File" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- 开启三员管理后，后台管理顶级菜单 --%>

<%
	/**
	 * 获取顶级菜单项，需要判断角色 <br>
	 * 普通用户（业务模块管理员）：除三员管理以外的所有菜单<br>
	 * 系统管理员：组织权限中心，流程引擎，搜索引擎，统一消息中心，应用中心（个别机制），移动办公，集成整合中心，智能应用，运维监控中心<br>
	 * 安全保密管理员：组织权限中心，安全设置，日志审计<br>
	 * 安全审计管理员：日志审计<br>
	 */
	 
	JSONArray array = new JSONArray();

	//////////////////////// 新需求：所有顶级菜单的权限可配置（兼容三员与非三员） /////////////////////////
	/////////////////// 配置入口在[/KmssConfig/sys/profile/design.xml] //////////////////////
	
	// 系统概览 
	if ( UserUtil.checkRole("SYS_PROFILE_NAVTOP_SYSTEM")) {
		array.add(getMenu("system", "lui_icon_m_profile_navTop_system", ResourceUtil.getString("sys.profile.centre.systemview", "sys-profile")));	
	}
	// 组织权限管理
	if (UserUtil.checkRole("SYSROLE_USER") || UserUtil.checkRole("SYS_PROFILE_NAVTOP_ORG")) {
		array.add(getMenu("org", "lui_icon_m_profile_navTop_org", ResourceUtil.getString("sys.profile.centre.org", "sys-profile")));
	}
	// 应用配置
	if (UserUtil.checkRole("SYSROLE_USER") || UserUtil.checkRole("SYS_PROFILE_NAVTOP_APP")) {
		array.add(getMenu("app", "lui_icon_m_profile_navTop_app", ResourceUtil.getString("sys.profile.centre.app", "sys-profile")));
	}
	// 流程引擎
	if (UserUtil.checkRole("SYSROLE_USER") || UserUtil.checkRole("SYS_PROFILE_NAVTOP_LBPM")) {
		array.add(getMenu("lbpm", "lui_icon_m_profile_navTop_lbpm", ResourceUtil.getString("sys.profile.centre.lbpm", "sys-profile")));
	}
	// 门户引擎
	if (UserUtil.checkRole("SYSROLE_USER") || UserUtil.checkRole("SYS_PROFILE_NAVTOP_PORTAL")) {
		array.add(getMenu("portal", "lui_icon_m_profile_navTop_portal", ResourceUtil.getString("sys.profile.centre.portal", "sys-profile")));
	}
	// 统一消息中心
	if (UserUtil.checkRole("SYSROLE_USER") || UserUtil.checkRole("SYS_PROFILE_NAVTOP_NOTIFY")) {
		array.add(getMenu("notify", "lui_icon_m_profile_navTop_notify", ResourceUtil.getString("sys.profile.centre.notify", "sys-profile")));
	}
	// 表单建模
	if (UserUtil.checkRole("SYSROLE_USER") ) {
		array.add(getMenu("modeling", "lui_icon_m_profile_navTop_modeling", ResourceUtil.getString("module.sys.modeling", "sys-modeling-base")));
	}
	// 移动办公
	if (UserUtil.checkRole("SYSROLE_USER") || UserUtil.checkRole("SYS_PROFILE_NAVTOP_MOBILE")) {
		array.add(getMenu("mobile", "lui_icon_m_profile_navTop_mobile", ResourceUtil.getString("sys.profile.centre.mobile", "sys-profile")));
	}
	// 运维管理，需要校验角色：SYSROLE_ADMIN
	if (UserUtil.checkRole("SYSROLE_ADMIN") || UserUtil.checkRole("SYS_PROFILE_NAVTOP_MAINTENANCE")) {
		array.add(getMenu("maintenance", "lui_icon_m_profile_navTop_maintenance", ResourceUtil.getString("sys.profile.centre.maintenance", "sys-profile")));
	}
	// 集成管理
	if (UserUtil.checkRole("SYSROLE_USER") || UserUtil.checkRole("SYS_PROFILE_NAVTOP_INTEGRATE")) {
		array.add(getMenu("integrate", "lui_icon_m_profile_navTop_integrate", ResourceUtil.getString("sys.profile.centre.integrate", "sys-profile")));
	}
	// 搜索功能配置，需要校验角色：【非三员】ROLE_SYSFTSEARCHEXPAND_MAINTAINER，【三员】SYS_PROFILE_NAVTOP_FTSEARCH ，【模块业务专员】ROLE_SYSFTSEARCHEXPAND_DEFAULT
	if (UserUtil.checkRole("ROLE_SYSFTSEARCHEXPAND_MAINTAINER") || UserUtil.checkRole("SYS_PROFILE_NAVTOP_FTSEARCH") || UserUtil.checkRole("ROLE_SYSFTSEARCHEXPAND_DEFAULT")) {
		array.add(getMenu("ftsearch", "lui_icon_m_profile_navTop_ftsearch", ResourceUtil.getString("sys.profile.centre.ftsearch", "sys-profile")));
	}
	// 智能应用(三员管理下不能使用)
	if ((UserUtil.checkRole("SYSROLE_USER") && !TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) || UserUtil.checkRole("SYS_PROFILE_NAVTOP_INTELLIGENCE")) {
		array.add(getMenu("intelligence", "lui_icon_m_profile_navTop_intelligence", ResourceUtil.getString("sys.profile.centre.intelligence", "sys-profile")));
	}
	// “安全保密管理员”和“安全审计管理员”增加“日志审计”
	if (UserUtil.checkRole("SYS_PROFILE_NAVTOP_ADMINLOG")) {
		array.add(getMenu("adminLog", "lui_icon_s_profile_navTop_log", ResourceUtil.getString("sys.profile.centre.adminLog", "sys-profile")));
	}

	boolean exist_yonghong = new File(PluginConfigLocationsUtil.getKmssConfigPath()
			+ "/third/yonghong").exists();
	// 数据分析中心
	if (exist_yonghong && (UserUtil.checkRole("SYSROLE_ADMIN") || UserUtil.checkRole("ROLE_THIRDYONGHONG_MANAGE"))) {
		array.add(getMenu("datacenter", "lui_icon_m_profile_navTop_datacenter", ResourceUtil.getString("sys.profile.centre.datacenter", "sys-profile")));
	}
%>

<%!
	private JSONObject getMenu(String key, String icon, String title) {
		JSONObject menu = new JSONObject();
		menu.put("key", key);
		if (StringUtil.isNotNull(icon))
			menu.put("icon", icon);
		if (StringUtil.isNotNull(title))
			menu.put("title", title);
		return menu;
	}
%>

<%
	// array保存的是所有有权限的顶级菜单，但是这些顶级菜单下有可能没有左则菜单，这种情况需要排除没有左则菜单的顶级菜单
	for (int i = 0; i < array.size(); i++) {
		JSONObject menu = array.getJSONObject(i);
		List list = ProfileMenuUtil.getMenus(menu.getString("key"));
		if (list.size() == 0) {
			array.remove(i);
		}
	}
	out.print(array);
%>