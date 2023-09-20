<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.kms.lservice.util.UrlsUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content">
		<link rel="stylesheet" type="text/css"
			href="${ LUI_ContextPath}/kms/lservice/index/common/style/switch.css" />
		<%
			String type = request.getParameter("type");
			String imgUrl = "", roleTxt  = "";
			String modelName = request.getParameter("modelName");
			
			String studentUrl = UrlsUtil.getStudentUrlByModelNameAndType(modelName);
			if (StringUtil.isNull(studentUrl))
				studentUrl = "";
			
			String teacherUrl = UrlsUtil.getTeacherUrlByModelNameAndType(modelName);
			if(StringUtil.isNull(teacherUrl))
				teacherUrl = "";
			
			String adminUrl = UrlsUtil.getAdminUrlByModelNameAndType(modelName);
			if(StringUtil.isNull(adminUrl))
				adminUrl = "";
		%>
		<div class="lui_studyCenter_roleSwitch_wrapper">
			<ul class="lui_studyCenter_roleSwitch_list">
				<%
					if(StringUtil.isNotNull(studentUrl) && UserUtil.checkAuthentication(studentUrl, "GET")) {
				%>
				<li class="<%="student".equals(type) ? "lui_item_on" : ""%>" data-url="<%=studentUrl%>">
					<div class="lui_studyCenter_portrait lui_role_student">
						<img src="${LUI_ContextPath}/kms/lservice/index/common/style/img/portrait-student.png">
					</div>
					<p class="lui_studyCenter_name">${lfn:message('kms-lservice:lservice.role.student')}</p>
				</li>
				<%
					}
				%>
				<%
					if(StringUtil.isNotNull(teacherUrl) && UserUtil.checkAuthentication(teacherUrl, "GET")) {
				%>
				<li class="<%="teacher".equals(type) ? "lui_item_on" : ""%>" data-url="<%=teacherUrl%>">
					<div class="lui_studyCenter_portrait lui_role_teacher">
						<img src="${LUI_ContextPath}/kms/lservice/index/common/style/img/portrait-teacher.png">
					</div>
					<p class="lui_studyCenter_name">${lfn:message('kms-lservice:lservice.role.teacher')}</p>
				</li>
				<%
					}
				%>
				<%
					if(StringUtil.isNotNull(adminUrl) && UserUtil.checkAuthentication(adminUrl, "GET")) {
				%>
				<li class="<%="admin".equals(type) ? "lui_item_on" : ""%>" data-url="<%=adminUrl%>">
					<div class="lui_studyCenter_portrait lui_role_admin">
						<img src="${LUI_ContextPath}/kms/lservice/index/common/style/img/portrait-admin.png">
					</div>
					<p class="lui_studyCenter_name">${lfn:message('kms-lservice:lservice.role.admin')}</p>
				</li>
				<%
					}
				%>
			</ul>
		</div>
		
		<script>
			seajs.use(["lui/jquery", "lui/util/env"], function($, env) {
				$("[data-url]").on("click" , function(evt) {
					var $t = $(evt.currentTarget), url =  $t.attr("data-url");
					if( $t.hasClass("lui_item_on")) {
						return;
					}
					if(url) {
						window.$dialog.hide();
						LUI.pageOpen(env.fn.formatUrl(url), '_iframe');
					}
				});
			});
			
		</script>
	</template:replace>
</template:include>
