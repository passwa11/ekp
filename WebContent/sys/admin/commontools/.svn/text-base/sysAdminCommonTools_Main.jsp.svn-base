<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple">
	<template:replace name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/admin/resource/css/commontools.css?s_cache=${LUI_Cache}">
	</template:replace>
	<template:replace name="body">
		<div class="profile_tool_container">
			<h2 class="profile_tool_title"><span><bean:message bundle="sys-admin" key="sys.sysAdminCommonTools"/></span></h2>
			<div class="profile_tool_list">
				<ul>
					<c:forEach items="${tools}" var="tools" varStatus="vstatus">
					<li>
						<div class="profile_tool_icon"><i class="${tools['icon']}"></i></div>
						<div class="profile_tool_info">
							<h3 class="profile_tool_name">${tools['name']}</h3>
							<p class="profile_tool_brief">${tools['description']}</p>
						</div>
						<div class="profile_tool_opt">
							<c:choose>
								<c:when test="${fn:contains(tools['path'],'method=design')}">
									<%-- 解决IE8下载时，文件名乱码 --%>
									<form id="_download" action="<c:url value="${tools['path']}"/>" method="post"></form>
									<div class="profile_tool_btn" onclick="$('#_download').submit();"><bean:message bundle="sys-admin" key="sys.admin.commontools.download"/></div>
								</c:when>
								<c:when test="${fn:contains(tools['path'],'sysFormTemplateReUpdate.jsp')}">
									<%-- 重新生成JSP，不开新页面 --%>
									<%-- <%@ include file="/sys/xform/base/sysFormTemplateReUpdate.jsp"%> --%>
									<div class="profile_tool_btn" onclick="Com_OpenWindow('<c:url value="/sys/xform/base/sysFormTemplateToReUpdate.jsp"/>','_blank');"><bean:message bundle="sys-admin" key="sys.admin.commontools.enter"/></div>
								</c:when>
								<c:when test="${fn:contains(tools['path'],'sysOrgData_update.jsp')}">
									<%-- 转换全拼储存 --%>
									<%@ include file="/sys/organization/sysOrgData_update.jsp"%>
									<div class="profile_tool_btn" onclick="window.DoUpdatePinYinField();"><bean:message bundle="sys-admin" key="sys.admin.commontools.reUpdate"/></div>
								</c:when>
								<c:when test="${fn:contains(tools['path'],'change_back_logo.jsp')}">
									<!-- 后台管理Logo设置 -->
									<%@ include file="/sys/admin/commontools/change_back_logo.jsp"%>
									<div class="profile_tool_btn" onclick="window.BackstageLogoSetting();"><bean:message bundle="sys-admin" key="sys.admin.commontools.backenLogo.setting"/></div>
								</c:when>
								<c:otherwise>
									<div class="profile_tool_btn" onclick="Com_OpenWindow('<c:url value="${tools['path']}"/>','_blank');"><bean:message bundle="sys-admin" key="sys.admin.commontools.enter"/></div>
								</c:otherwise>
							</c:choose>
						</div>
					</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</template:replace>
</template:include>
