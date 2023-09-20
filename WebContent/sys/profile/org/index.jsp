<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.authorization.model.SysAuthRole,com.landray.kmss.sys.authorization.service.ISysAuthRoleService"%>

<%
	String org_attr = "onclick=\"viewFun('org_{% organizationalInfo[i].icon %}');\" ";
	String auth_attr = "onclick=\"viewFun('auth_{% organizationalInfo[i].icon %}');\" ";
	//开启三员管理后，此页面的所有链接都不能点击
	if (TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) {
		org_attr = "onclic=\"javascript:void(0);\" style=\"cursor: default;\" ";
		auth_attr = org_attr;
		// 开启三员管理后，此页面只有“安全保密管理员”可访问
		if(!TripartiteAdminUtil.isSecurity()) {
			response.sendRedirect(request.getContextPath() + "/sys/profile/index.jsp");
		}
	}
	
	ISysAuthRoleService s = (ISysAuthRoleService)SpringBeanUtil.getBean("sysAuthRoleService");
	List<SysAuthRole> roles = s.findList(" sysAuthRole.fdAlias = 'SYSROLE_ADMIN'", null);
	String roleId = "";
	if(roles.size()>0)
		roleId = roles.get(0).getFdId();
	request.setAttribute("roleId", roleId);	
%>

<template:include ref="default.simple">
	<template:replace name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/profile/resource/css/orgIndex.css?s_cache=${LUI_Cache}">
	</template:replace>
	<template:replace name="body">
		<ui:dataview>
			<ui:source type="AjaxJson">
				{url:'/sys/profile/org/orgAuthCenter.do?method=overview&roleId=${roleId}'}
			</ui:source>
			<ui:render type="Template">
				{$<div class="lui_profile_org_box">
					<div class="lui_profile_org_main_point main_point_one">
						<div class="lui_profile_icon lui_profile_icon_lg lui_profile_icon_lg_1"></div>
     	 				<div class="lui_profile_org_title"><bean:message bundle="sys-profile" key="sys.profile.org.overview.statistics"/></div>
    				</div>
    				<div class="lui_profile_org_main_point main_point_two">
				      <div class="lui_profile_icon lui_profile_icon_lg lui_profile_icon_lg_2"></div>
				      <div class="lui_profile_org_title"><bean:message bundle="sys-profile" key="sys.profile.org.overview.org"/></div>
				    </div>
				    <div class="lui_profile_org_main_point main_point_three">
				      <div class="lui_profile_icon lui_profile_icon_lg lui_profile_icon_lg_3"></div>
				      <div class="lui_profile_org_title"><bean:message bundle="sys-profile" key="sys.profile.org.overview.auth"/></div>
				    </div>
				$}
				var organizationalInfo = data['organizational'];
				for(var i = 0; i < organizationalInfo.length;i++){
					{$
						<a <%=org_attr %> class="lui_profile_org_point lui_profile_organizational point_{% i+1 %}">
							<div class="lui_profile_icon {% organizationalInfo[i].icon %}"></div>
							<div class="lui_profile_org_title">{% organizationalInfo[i].name %}</div>
							<div class="lui_profile_org_count">({% organizationalInfo[i].number %})</div>
						</a>
					$}
				}
				
				var authInfo = data['auth'];
				for(var i = 0; i < authInfo.length;i++){
					{$
						<a <%=auth_attr %> class="lui_profile_org_point lui_profile_auth point_{% i+1 %}">
							<div class="lui_profile_icon {% authInfo[i].icon %}"></div>
							<div class="lui_profile_org_title">{% authInfo[i].name %}</div>
							<div class="lui_profile_org_count">({% authInfo[i].number %})</div>
						</a>
					$}
				}
				
				{$</div>$}
			</ui:render>
		</ui:dataview>
		
		<%
			// 开启三员管理后，此页面的所有链接都不能点击
			if (!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) {
		%>
		<script type="text/javascript">
			function viewFun(type) {
				var url = '';
				switch(type){
				case 'org_lui_icon_m_profile_04' : {
					url = '#org/organizational/org';
					break;
				}
				case 'org_lui_icon_m_profile_02' : {
					url = '#org/organizational/dept';
					break;
				}
				case 'auth_lui_icon_m_profile_04' :{
					url = "${LUI_ContextPath}/sys/authorization/sys_auth_research/sysAuthResearch.do?method=researchRole&roleId=${roleId}"
					break;
				}
				case 'org_lui_icon_m_profile_05' : {
					url = '#org/organizational/person';
					break;
				}
				case 'org_lui_icon_m_profile_03' : {
					url = '#org/organizational/group';
					break;
				}
				case 'org_lui_icon_m_profile_07' : {
					url = '#org/organizational/roleLine';
					break;
				}
				case 'org_lui_icon_m_profile_06' : {
					url = '#org/organizational/post';
					break;
				}
				case 'auth_lui_icon_m_profile_02' : {
					url = '#org/authority/role';
					break;
				}
				case 'auth_lui_icon_m_profile_05' : {
					url = '#org/authority/auth';
					break;
				}
				}
				
				if(type=='auth_lui_icon_m_profile_04')
					window.location.href = url;
				else{
					window.top.location.href = "${LUI_ContextPath}/sys/profile/index.jsp" + url;
					window.top.location.reload();
				}
			}
		</script>
		<%	
			}
		%>
	</template:replace>
</template:include>	
	