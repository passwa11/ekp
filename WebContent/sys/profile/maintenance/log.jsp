<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@page import="com.landray.kmss.sys.log.util.LogConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- 访问此文件有2种情况：1、未开启三员管理，并且是超级管理员；2、开启三员管理，并且是系统管理员 --%>
<template:include file="/sys/profile/resource/template/tree.jsp">
	<template:replace name="content">

/**
* 根据顶层window地址栏?号后面的参数名称获取参数值
* @param name 参数名称
* @return
*/
function getTopWindowQueryString(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.top.location.search.substr(1).match(reg);
    if(r!=null)return unescape(r[2]); return null;
}

function generateTree() {
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="sys.profile.maintenance.log" bundle="sys-profile" />",
		document.getElementById("treeDiv")
	);
	var n1 = null; 
	var defaultMenuUrl = getTopWindowQueryString("defaultMenuUrl");
	if(defaultMenuUrl){
	  if(defaultMenuUrl.substring(0,1)=="/"){
	    defaultMenuUrl = Com_Parameter.ContextPath + defaultMenuUrl.substring(1);
	  }else{
	    defaultMenuUrl = Com_Parameter.ContextPath + defaultMenuUrl;
	  }
	}
	var defaultNode = null;
	n1 = LKSTree.treeRoot;
	
	<% if(TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
	<%-- 开启三员管理，强制使用新日志系统 --%>
	<kmss:auth requestURL="/sys/log/sys_log_system/sysLogSystem.do?method=list" requestMethod="GET">
		//========== 后台日志 ==========
		var backstage_log_url = "<c:url value="/sys/log/sys_log_system/index.jsp"/>";
		var backstageLogNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="table.sysLogJob"/>", backstage_log_url );
		if(defaultMenuUrl==backstage_log_url){
		   defaultNode = backstageLogNode;
		}
    </kmss:auth>
    <kmss:auth requestURL="/sys/log/sys_log_bak/sysLogBak.do?method=list" requestMethod="GET">
	    //========== 日志备份(elastic search) ==========
	    var log_backup_url = "<c:url value="/sys/log/sys_log_bak/index.jsp"/>";
	    var logBackupNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="table.sysLogBak"/>", log_backup_url );
	    if(defaultMenuUrl==log_backup_url){
		   defaultNode = logBackupNode;
		}
    </kmss:auth>
	<% } else { %>
	<%-- 未开启三员管理 --%>
		<%-- 需要判断是否开启新日志系统 --%>
		<% 
		String pattern = ResourceUtil.getKmssConfigString("kmss.oper.log.store.pattern");
		if (StringUtil.isNull(pattern) || LogConstant.LogStorePattern.DB.getVal().equals(pattern)) { %>
		<kmss:auth requestURL="/sys/log/sys_log_app/sysLogApp.do?method=list" requestMethod="GET">
			//========== 操作日志 ==========
			var opt_log_url = "<c:url value="/sys/log/sys_log_app/index.jsp"/>";
			var optLogNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="table.sysLogApp"/>", opt_log_url );
			if(defaultMenuUrl==opt_log_url){
			   defaultNode = optLogNode;
			}
		
			//========== 错误日志 ==========
			var error_log_url = "<c:url value="/sys/log/sys_log_error/index.jsp"/>";
			var errorLogNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="table.sysLogError"/>", error_log_url );	
			if(defaultMenuUrl==error_log_url){
			   defaultNode = errorLogNode;
			}
		
			//========== 后台日志 ==========
			var backstage_log_url = "<c:url value="/sys/log/sys_log_job/index.jsp"/>";
			var backstageLogNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="table.sysLogJob"/>", backstage_log_url );
			if(defaultMenuUrl==backstage_log_url){
			   defaultNode = backstageLogNode;
			}
			
			//========== 登录日志 ==========
			var login_log_url = "<c:url value="/sys/log/sys_log_login/index.jsp"/>";
			var loginLogNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="table.sysLogLogin"/>", login_log_url );
			if(defaultMenuUrl==login_log_url){
			   defaultNode = loginLogNode;
			}	
			
			//========== 登出日志 ==========
			var logout_log_url = "<c:url value="/sys/log/sys_log_logout/index.jsp"/>";
			var logoutLogNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="table.sysLogLogout"/>", logout_log_url );
			if(defaultMenuUrl==logout_log_url){
			   defaultNode = logoutLogNode;
			}	
			
			//========== 组织架构操作日志 ==========
			var org_opt_log_url = "<c:url value="/sys/log/sys_log_organization/index.jsp"/>";
			var orgOptLogNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="table.sysLogOrganization"/>", org_opt_log_url );
			if(defaultMenuUrl==org_opt_log_url){
			   defaultNode = orgOptLogNode;
			}		
			
			//========== 执行失败的任务 ==========
			var failed_task_log_url = "<c:url value="/sys/log/sys_log_job/index_faile.jsp"/>";
			var failedTaskLogNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="table.sysLogFaileJob"/>", failed_task_log_url );
			if(defaultMenuUrl==failed_task_log_url){
			   defaultNode = failedTaskLogNode;
			}
				
			//========== 任务通知日志 ==========
			var task_notice_log_url = "<c:url value="/sys/log/sys_log_job/index.jsp"/>";
			var taskNoticeLogNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="table.sysLogFaileJobSend"/>", task_notice_log_url );
			if(defaultMenuUrl==task_notice_log_url){
			   defaultNode = taskNoticeLogNode;
			}
		</kmss:auth>
		<% } else { %>
		<kmss:auth requestURL="/sys/log/sys_log_user_oper/sysLogUserOper.do?method=listNonTripartite" requestMethod="GET">
			//========== 操作日志 ==========
			var opt_log_url = "<c:url value="/sys/log/sys_log_user_oper/index.jsp?method=listNonTripartite&eventType=oper"/>";
			var optLogNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="table.sysLogUserOper"/>", opt_log_url );
			if(defaultMenuUrl==opt_log_url){
			   defaultNode = optLogNode;
			}
			
			//========== 登录日志 ==========
			var login_log_url = "<c:url value="/sys/log/sys_log_user_oper/index.jsp?method=listNonTripartite&eventType=login"/>";
			var loginLogNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="table.sysLogLogin"/>", login_log_url );
			if(defaultMenuUrl==login_log_url){
			   defaultNode = loginLogNode;
			}	

			//========== 登出日志 ==========
			var logout_log_url = "<c:url value="/sys/log/sys_log_user_oper/index.jsp?method=listNonTripartite&eventType=logout"/>";
			var logoutLogNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="table.sysLogLogout"/>", logout_log_url );
			if(defaultMenuUrl==logout_log_url){
			   defaultNode = logoutLogNode;
			}
    	</kmss:auth>
    	<kmss:auth requestURL="/sys/log/sys_log_user_oper/sysLogUserOper.do?method=listOrg" requestMethod="GET">
			//========== 组织架构操作日志 ==========
			var org_opt_log_url = "<c:url value="/sys/log/sys_log_user_oper/index.jsp?method=listOrg"/>";
			var orgOptLogNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="sysLogOper.method.organization"/>", org_opt_log_url );
			if(defaultMenuUrl==org_opt_log_url){
			   defaultNode = orgOptLogNode;
			}
	    </kmss:auth>
	    <kmss:auth requestURL="/sys/log/sys_log_user_oper/sysLogUserOper.do?method=listAuth" requestMethod="GET">
		    //========== 权限变更日志 ==========
		    var permission_change_log_url = "<c:url value="/sys/log/sys_log_user_oper/index.jsp?method=listAuth"/>"
		    var permissionChangeLogNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="sysLogOper.method.sysAuthRole"/>", permission_change_log_url );
			if(defaultMenuUrl==permission_change_log_url){
			   defaultNode = permissionChangeLogNode;
			}	    
	    </kmss:auth>
	    <kmss:auth requestURL="/sys/log/sys_log_system/sysLogSystem.do?method=list" requestMethod="GET">
			//========== 后台日志 ==========
			var backstage_log_url = "<c:url value="/sys/log/sys_log_system/index.jsp"/>";
			var backstageLogNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="table.sysLogJob"/>", backstage_log_url );
			if(defaultMenuUrl==backstage_log_url){
			   defaultNode = backstageLogNode;
			}
	    </kmss:auth>
        <kmss:auth requestURL="/sys/log/sys_log_notify/sysLogNotify.do?method=list" requestMethod="GET">
        	//========== 通知日志 ==========
			var notice_log_url = "<c:url value="/sys/log/sys_log_notify/index.jsp"/>";
			var noticeLogNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="table.sysLogNotify"/>", notice_log_url );
			if(defaultMenuUrl==notice_log_url){
			   defaultNode = noticeLogNode;
			}
	    </kmss:auth>
	    <kmss:auth requestURL="/sys/log/sys_log_bak/sysLogBak.do?method=list" requestMethod="GET">
		    //========== 日志备份(elastic search) ==========
		    var log_backup_url = "<c:url value="/sys/log/sys_log_bak/index.jsp"/>";
		    var logBackupNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="table.sysLogBak"/>", log_backup_url );
		    if(defaultMenuUrl==log_backup_url){
			   defaultNode = logBackupNode;
			}
	    </kmss:auth>
	    <kmss:auth requestURL="/sys/log/sys_log_faile_job/sysLogBak.do?method=list" requestMethod="GET">
			//========== 任务通知日志 ==========
			var task_notice_log_url = "<c:url value="/sys/log/sys_log_faile_job/index.jsp"/>";
			var taskNoticeLogNode = n1.AppendURLChild( "<bean:message bundle="sys-log" key="table.sysLogFaileJobSend"/>", task_notice_log_url );
			if(defaultMenuUrl==task_notice_log_url){
			   defaultNode = taskNoticeLogNode;
			}
		</kmss:auth>
		<% } %>
		<kmss:auth requestURL="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.log.model.SysLogOnlineNotifyConfig" requestMethod="GET">
			//========== 系统通知设置 ==========
			var sys_notice_log_url = "<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.log.model.SysLogOnlineNotifyConfig"/>";
			var sysNoticeLogNode = n1.AppendURLChild("<bean:message bundle="sys-log" key="sysLogOnline.NotifyConfig.set"/>",sys_notice_log_url);
			if(defaultMenuUrl==sys_notice_log_url || defaultNode==null){
			   defaultNode = sysNoticeLogNode;
			}
		</kmss:auth>
		
		<kmss:auth requestURL="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.log.model.SysLogPortConfig" requestMethod="GET">
			//========== 系统通知设置 ==========
			var sys_port_log_url = "<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.sys.log.model.SysLogPortConfig"/>";
			var sysLogPortNode = n1.AppendURLChild("<bean:message bundle="sys-log" key="sysLogOnline.Port.Switch"/>",sys_port_log_url);
			if(defaultMenuUrl==sys_port_log_url || defaultNode==null){
			   defaultNode = sysLogPortNode;
			}
		</kmss:auth>
	<% } %>
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
	</template:replace>
</template:include>