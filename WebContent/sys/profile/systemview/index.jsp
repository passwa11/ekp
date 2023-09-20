<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.profile.util.ProfileMenuUtil" %>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page import="com.landray.kmss.sys.config.util.LicenseUtil"%>
<%@ page import="com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService"%>
<%@ page import="com.landray.kmss.sys.cluster.model.SysClusterParameter"%>
<%@ page import="com.landray.kmss.sys.log.service.ISysLogOnlineService"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="com.landray.kmss.util.version.VersionXMLUtil"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="net.sf.json.JSONArray"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.config.design.SysCfgProfileConfig"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>


<%
	/**
	 * 参考顶级菜单的代码
	 */
	 
	JSONArray array = new JSONArray();

	//////////////////////// 新需求：所有顶级菜单的权限可配置（兼容三员与非三员） /////////////////////////
	/////////////////// 配置入口在[/KmssConfig/sys/profile/design.xml] //////////////////////
	
	// 系统概览
	if (UserUtil.checkRole("SYS_PROFILE_NAVTOP_SYSTEM")) {
		array.add(getMenu("system", "lui_iconfont_profile_t_overview", ResourceUtil.getString("sys.profile.centre.systemview", "sys-profile")));	
	}
	// 组织权限中心
	if (UserUtil.checkRole("SYSROLE_USER") || UserUtil.checkRole("SYS_PROFILE_NAVTOP_ORG")) {
		array.add(getMenu("org", "lui_iconfont_profile_t_org", ResourceUtil.getString("sys.profile.centre.org", "sys-profile")));
	}
	// 应用配置
	if (UserUtil.checkRole("SYSROLE_USER") || UserUtil.checkRole("SYS_PROFILE_NAVTOP_APP")) {
		array.add(getMenu("app", "lui_iconfont_profile_t_app", ResourceUtil.getString("sys.profile.centre.app", "sys-profile")));
	}
	// 流程引擎
	if (UserUtil.checkRole("SYSROLE_USER") || UserUtil.checkRole("SYS_PROFILE_NAVTOP_LBPM")) {
		array.add(getMenu("lbpm", "lui_iconfont_profile_t_lbpm", ResourceUtil.getString("sys.profile.centre.lbpm", "sys-profile")));
	}
	// 门户引擎
	if (UserUtil.checkRole("SYSROLE_USER") || UserUtil.checkRole("SYS_PROFILE_NAVTOP_PORTAL")) {
		array.add(getMenu("portal", "lui_iconfont_profile_t_portal", ResourceUtil.getString("sys.profile.centre.portal", "sys-profile")));
	}
	// 统一消息中心
	if (UserUtil.checkRole("SYSROLE_USER") || UserUtil.checkRole("SYS_PROFILE_NAVTOP_NOTIFY")) {
		array.add(getMenu("notify", "lui_iconfont_profile_t_notify", ResourceUtil.getString("sys.profile.centre.notify", "sys-profile")));
	}
	// 移动办公
	if (UserUtil.checkRole("SYSROLE_USER") || UserUtil.checkRole("SYS_PROFILE_NAVTOP_MOBILE")) {
		array.add(getMenu("mobile", "lui_iconfont_profile_t_mobile", ResourceUtil.getString("sys.profile.centre.mobile", "sys-profile")));
	}
	// 运维管理，需要校验角色：SYSROLE_ADMIN
	if (UserUtil.checkRole("SYSROLE_ADMIN") || UserUtil.checkRole("SYS_PROFILE_NAVTOP_MAINTENANCE")) {
		array.add(getMenu("maintenance", "lui_iconfont_profile_t_maintenance", ResourceUtil.getString("sys.profile.centre.maintenance", "sys-profile")));
	}
	// 集成管理
	if (UserUtil.checkRole("SYSROLE_USER") || UserUtil.checkRole("SYS_PROFILE_NAVTOP_INTEGRATE")) {
		array.add(getMenu("integrate", "lui_iconfont_profile_t_integrate", ResourceUtil.getString("sys.profile.centre.integrate", "sys-profile")));
	}
	// 搜索功能配置，需要校验角色：【非三员】ROLE_SYSFTSEARCHEXPAND_MAINTAINER，【三员】SYS_PROFILE_NAVTOP_FTSEARCH ，【模块业务专员】ROLE_SYSFTSEARCHEXPAND_DEFAULT
	if (UserUtil.checkRole("ROLE_SYSFTSEARCHEXPAND_MAINTAINER") || UserUtil.checkRole("SYS_PROFILE_NAVTOP_FTSEARCH") || UserUtil.checkRole("ROLE_SYSFTSEARCHEXPAND_DEFAULT")) {
		array.add(getMenu("ftsearch", "lui_iconfont_profile_t_ftsearch", ResourceUtil.getString("sys.profile.centre.ftsearch", "sys-profile")));
	}
	// 智能应用(三员管理下不能使用)
	if ((UserUtil.checkRole("SYSROLE_USER") && !TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) || UserUtil.checkRole("SYS_PROFILE_NAVTOP_INTELLIGENCE")) {
		array.add(getMenu("intelligence", "lui_iconfont_profile_t_intelligence", ResourceUtil.getString("sys.profile.centre.intelligence", "sys-profile")));
	}
	// “安全保密管理员”和“安全审计管理员”增加“日志审计”
	if (UserUtil.checkRole("SYS_PROFILE_NAVTOP_ADMINLOG")) {
		array.add(getMenu("adminLog", "lui_iconfont_profile_t_log", ResourceUtil.getString("sys.profile.centre.adminLog", "sys-profile")));
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

%>


<template:include ref="default.simple">

	<template:replace name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/profile/systemview/css/systemview.css?s_cache=${LUI_Cache}">
		<link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/profile/systemview/font/iconfont.css?s_cache=${LUI_Cache}">
	</template:replace>
	
	<template:replace name="body">

    <!-- 概览页区域 Starts -->
    <div class="lui_profile_overview_container">
        <!-- 概览头部信息 Starts-->
        <div class="lui_profile_overview_header">
            <div class="lui_profile_main_content">
                <ul class="lui_profile_overview_baseInfo_list">
                    <!-- 用户数 -->
                    <%
						String unlimit = ResourceUtil.getString("sysLicense.licenseType.unlimit", "sys-config");
						// 生态注册数
						int outRegCount  = ((ISysOrgCoreService)SpringBeanUtil.getBean("sysOrgCoreService")).getCountByRegistered(true, false);
						// 内部注册数
						int inRegCount  = ((ISysOrgCoreService)SpringBeanUtil.getBean("sysOrgCoreService")).getCountByRegistered(false, false);
						// 总注册人数
						int totalRegCount  = outRegCount + inRegCount;
						// 生态在线数
						long outOnlineCount = ((ISysLogOnlineService)SpringBeanUtil.getBean("sysLogOnlineService")).getOnlineUserNumByPc(true);
						// 内部在线数
						long inOnlineCount = ((ISysLogOnlineService)SpringBeanUtil.getBean("sysLogOnlineService")).getOnlineUserNumByPc(false);
						// 总在线人数
						long totalOnlineCount = outOnlineCount + inOnlineCount;

						// ====== 注册用户数量提醒 =====
						int unlimitCount = -1;
						// 许可注册数（内部）
						int licenseInRegCount = StringUtil.getIntFromString(LicenseUtil.get("license-org-person"), unlimitCount);
						// 许可注册数（生态）
						int licenseOutRegCount = StringUtil.getIntFromString(LicenseUtil.get("license-org-person-external"), unlimitCount);
						boolean orgpersonoverflow = false;
						// 获取注册数量（排除特权）
						inRegCount  = ((ISysOrgCoreService)SpringBeanUtil.getBean("sysOrgCoreService")).getCountByRegistered(false, true);
						if(licenseInRegCount != unlimitCount) {
							if(inRegCount > licenseInRegCount) {
								orgpersonoverflow = true;
							}
						}
						// 获取注册数量（排除特权）
						outRegCount  = ((ISysOrgCoreService)SpringBeanUtil.getBean("sysOrgCoreService")).getCountByRegistered(true, true);
						if(licenseOutRegCount != unlimitCount) {
							if(outRegCount > licenseOutRegCount) {
								orgpersonoverflow = true;
							}
						}
						pageContext.setAttribute("orgpersonoverflow", orgpersonoverflow);

						// ====== 异常信息提醒 =====
						// 获取提示信息
						boolean systemTips = false;
						// 许可文件自身定义的有效期到期
						String licenseExpire = LicenseUtil.getExpireSubject(LicenseUtil.NOTIFY_SYS_JOB);
						if(StringUtil.isNotNull(licenseExpire)) {
							systemTips = true;
						}
						// 注册用户限制
						if((licenseInRegCount != unlimitCount && licenseInRegCount > 0 && inRegCount >= licenseInRegCount) || (licenseOutRegCount != unlimitCount && licenseOutRegCount > 0 && outRegCount >= licenseOutRegCount)) {
							systemTips = true;
						}
						pageContext.setAttribute("systemTips", systemTips);
			        %>
			        	
                    <li class="lui_profile_overview_baseInfo_item lui_profile_overview_baseInfo_person">
						<a class="lui_profile_overview_baseInfo_link" href="<c:url value="/sys/profile/index.jsp#maintenance/overview/"/>" target="_blank"><bean:message bundle="sys-profile" key="sys.profile.systemview.moreUser"/><i class="lui_profile_arrow"></i></a>
                        <dl>
                            <dd><em class="lui_profile_overview_num"><%=totalRegCount%></em>
                                <p><bean:message bundle="sys-profile" key="sys.profile.current.registeredUser"/></p>
                                <c:if test="${orgpersonoverflow}">
                                <p><span style="color: red"><bean:message bundle="sys-profile" key="sys.profile.maintenance.overview.orgpersonoverflow.msg"/></span></p>
                                </c:if>
                            </dd>
                            <dd><em class="lui_profile_overview_num"><%=totalOnlineCount%></em>
                                <p><bean:message bundle="sys-profile" key="sys.profile.current.onlineUser"/></p>
                            </dd>
                        </dl>
						<c:if test="${systemTips}">
							<a class="lui_profile_overview_baseInfo_link" style="color: red;font-size: 12px;text-align: center;top: auto;" href="<c:url value="/sys/profile/index.jsp#maintenance/overview/"/>" target="_blank"><bean:message bundle="sys-profile" key="sys.profile.maintenance.overview.systemTips.msg"/></a>
						</c:if>
                    </li>
                    <!-- 集群信息 -->
                    <%
				    		int licenseCluster = StringUtil.getIntFromString(LicenseUtil.get("license-cluster"), 1);
				    		String licenseClusterString = licenseCluster < 0 ? unlimit : String.valueOf(licenseCluster);
				    		List sysClusterServerList = SysClusterParameter.getInstance().getAllServers();
				    		pageContext.setAttribute("sysClusterServerSize", sysClusterServerList != null ? sysClusterServerList.size() : 0);
				    		pageContext.setAttribute("licenseClusterString", licenseClusterString);
			        	%>
			        	
                    <li class="lui_profile_overview_baseInfo_item lui_profile_overview_baseInfo_cluster">
                        <a class="lui_profile_overview_baseInfo_link" href="<c:url value="/sys/profile/index.jsp#maintenance/server/"/>" target="_blank"><bean:message bundle="sys-profile" key="sys.profile.systemview.moreCluster"/><i class="lui_profile_arrow"></i></a>
                        <dl>
                            <dd><em class="lui_profile_overview_num">${sysClusterServerSize}</em>
                                <p><bean:message bundle="sys-profile" key="sys.profile.systemview.licenseCluster"/></p>
                            </dd>
                            <dd><em class="lui_profile_overview_num">${licenseClusterString}</em>
                                <p><bean:message bundle="sys-profile" key="sys.profile.systemview.cluster"/></p>
                            </dd>
                        </dl>
                    </li>
                    <!-- 版本信息 -->
                    
                    <% 
							String path = request.getSession().getServletContext().getRealPath("/");
							path = path.replaceAll("\\\\", "/");
							if (!path.endsWith("/")) {
								path += "/";
							}
							String file = path + "WEB-INF/KmssConfig/version/description.xml";
							String version = VersionXMLUtil.getInstance(file).getDescriprion().getModule().getBaseline();
							StringBuffer buf = new StringBuffer();
							if(version != null) {
								int count = 0;
								for(int i=0; i<version.length(); i++) {
									if(version.charAt(i) == '.') {
										count++;
										if(count > 1) break;
									}
									buf.append(version.charAt(i));
								}
							}
						%>
						
						<% 
							String __path = "/all.version";
							if(!TripartiteAdminUtil.isGeneralUser()) { // 非三员管理 才显示的内容
								__path = "/sys/profile/tripartiteAdminAction.do?method=showVersion&path=/all.version";
							}
							__path = request.getContextPath() + __path;
						%>
                    <li class="lui_profile_overview_baseInfo_item lui_profile_overview_baseInfo_version">
                        <a class="lui_profile_overview_baseInfo_link" href="<%=__path%>" target="_blank"><bean:message bundle="sys-profile" key="sys.profile.systemview.moreVersion"/><i class="lui_profile_arrow"></i></a>
                        <dl>
                            <dd><em class="lui_profile_overview_num"><%=buf.toString()%></em>
                                <p><bean:message bundle="sys-profile" key="sys.profile.systemview.serverVersion"/></p>
                            </dd>
                        </dl>
                    </li>
                    <!-- ip地址-登录信息 -->
                    <li class="lui_profile_overview_baseInfo_item lui_profile_overview_baseInfo_login">
                        <p class="lui_ip_address">
                        </p>
                        <p class="lui_date"></p>
                        <p class="lui_other_info"><bean:message bundle="sys-profile" key="sys.profile.systemview.lastLoginInfo"/></p>
                    </li>
                    <!-- 系统通知 -->
                    <li class="lui_profile_overview_baseInfo_item lui_profile_overview_baseInfo_notify">
                        <a class="lui_profile_overview_baseInfo_link" href="<c:url value="/sys/profile/index.jsp#maintenance/appConfig/"/>" target="_blank">前往设置<i class="lui_profile_arrow"></i></a>
                        <p class="lui_notify_title"><bean:message bundle="sys-log" key="sysLogOnline.NotifyConfig.set"/></p>
                        <p class="lui_notify_error">
                        </p>
                        <p class="lui_notify_msg"><bean:message bundle="sys-log" key="sysLogOnline.NotifyConfig.NotifyDescribe"/></p>
                    </li>
                </ul>
            </div>
        </div>
        <!-- 概览头部信息 Ends-->
        <!-- 概览列表内容 Starts -->
        <div class="lui_profile_overview_content">
            <div class="lui_profile_main_content">
                <!-- 列表 -->
                <ul class="lui_profile_overview_module_list">
                		
                		
             <%
    	        //外循环
    	    	for(int i=0;i < array.size(); i++){
    	    		JSONObject menu = array.getJSONObject(i);
    	    		if(menu.getString("key").equals("system")){//系统概览不显示
    	    			continue;
    	    		}
    	    %>
    	          <li class="lui_profile_overview_module_item">
							<div class="lui_profile_overview_module_icon ">
								<i class="lui_text_primary iconfont_profile <%=menu.getString("icon") %>">
									<i></i>
								</i>
							</div>
    	            		<div class="lui_profile_overview_module_content">
									<div class="lui_profile_overview_module_title"><%=menu.getString("title") %></div>
    	    							<div class="lui_profile_overview_module_subitem">
    	    		
    	    		<%
	    	    		List list = ProfileMenuUtil.getMenus(menu.getString("key"));
    	    		    if(list!=null&&list.size()>0){
	    	    			for(int j=0;j<list.size();j++){//内循环
	    	    					SysCfgProfileConfig sysCfgProfileConfig=(SysCfgProfileConfig)list.get(j);
	    	    					
    	    		%>
    	    					
									<a class="itemTitle" onclick="itemClick('<%=sysCfgProfileConfig.getType() %>','<%=sysCfgProfileConfig.getKey() %>','<%=sysCfgProfileConfig.getUrl()%>');" href="javascript:;"  target="_top"><%=sysCfgProfileConfig.getMessageKey() %></a>
    	    		<%
	    	    			}
	    	    		}%>	
    	    							</div>
							</div>
					</li>
    	    		
    	    <%
    	    	}//外循环
    	    %>
    	    
                		
							
	
	
                </ul>
            </div>
        </div>
        <!-- 概览列表内容 Ends -->
    </div>
    <!-- 概览页区域 Ends -->
    
 

    <script>
    
    function isExitsFunction(funcName) {
    	  try {
    	    if (typeof(eval(funcName)) == "function") {
    	      return true;
    	    }
    	  } catch(e) {}
    	  return false;
    }
    
    function itemClick(topKey,subKey,subUrl){
    	if(isExitsFunction(parent.itemTitleClick)){
    		parent.itemTitleClick(topKey,subKey,subUrl);
    	}else{
    		window.location.href ='<%=request.getContextPath()%>'+subUrl;
    	}
	}
    
    
	    seajs.use(['lui/jquery','lui/topic'], function($ , topic) {
	    	   var url = '<c:url value="/sys/log/sys_log_online/sysLogOnline.do" />';
	    	    
	    	    var param = {
	    			method : "getLastLoginInfo",
	    		};
	    	    
	    	    var notifyParam={
	    	    		method : "getNotifyConfigStatus",	
	    	    };
	    	    
	    	    $.post(url, param, function(data,status){
	    		 	if(data.data){
	    		 		$(".lui_date").html(data.data.fdLastLoginTime);
		    		 	
		    		 	if(data.data.fdLastLoginIp!=null&&data.data.fdLastLoginIp!=""){
		    		 		$(".lui_ip_address").html("IP:"+data.data.fdLastLoginIp);
		    		 	}
	    		 	}
	    		 	
	    		 },"json");
	    	    
	    	    $.post(url, notifyParam, function(data,status){
	    		 	if(data.data){
	    		 		var code = data.data.errorCode
		    		 	if(!code||code==0){
		    		 		$(".lui_notify_error").html("");
		    		 	}else if(code==1){
		    		 		$(".lui_notify_error").html("<bean:message bundle='sys-log' key='sysLogOnline.NotifyConfig.NotifyInvalid'/>");
		    		 	}else if(code==2){
		    		 		$(".lui_notify_error").html("<bean:message bundle='sys-log' key='sysLogOnline.NotifyConfig.NotifyUnset'/>");
		    		 	}
	    		 	}else{
	    		 		$(".lui_notify_error").html("系统错误");
	    		 	}
	    		 	
	    		 },"json");

	    	   
	    });
	    
	    
    
    
    </script>
	</template:replace>
</template:include>	
	