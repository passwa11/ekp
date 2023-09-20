<%@page import="com.landray.kmss.kms.lservice.util.UrlsUtil"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<link rel="stylesheet" type="text/css"
	href="${ LUI_ContextPath}/kms/lservice/index/common/style/switch.css" />

<%
	String modelName = request.getParameter("modelName");

	String teacher = "teacher";
	String admin = "admin";
	String student = "student";

	List<String> buttonss = new ArrayList<String>();

	String teacherUrl = request.getParameter(teacher);
	String adminUrl = request.getParameter(admin);
	String studentUrl = request.getParameter(student);

	if (StringUtil.isNull(teacherUrl)) {

		teacherUrl = UrlsUtil.getTeacherUrlByModelNameAndType(modelName);

		if (StringUtil.isNull(teacherUrl))
			teacherUrl = "/kms/lservice/index/teacher/";

	} else
		buttonss.add(teacher);

	request.setAttribute("teacherUrl", teacherUrl);

	if (StringUtil.isNull(adminUrl)) {

		adminUrl = UrlsUtil.getAdminUrlByModelNameAndType(modelName);

		if (StringUtil.isNull(adminUrl))
			adminUrl = "/kms/lservice/index/admin/";

	} else
		buttonss.add(admin);

	request.setAttribute("adminUrl", adminUrl);

	if (StringUtil.isNull(studentUrl)) {

		studentUrl = UrlsUtil.getStudentUrlByModelNameAndType(modelName);

		if (StringUtil.isNull(studentUrl))
			studentUrl = "/kms/lservice/index/student/";

	} else
		buttonss.add(student);

	request.setAttribute("studentUrl", studentUrl);

	if (buttonss.isEmpty()) {

		if (UserUtil.checkRole("ROLE_KMSLSERVICE_MAINTAINER"))
			buttonss.add(admin);

		buttonss.add(teacher);
		buttonss.add(student);

	}

%>


<c:choose>
	<c:when test="${param.type=='admin'}">
		<c:set var="adminView" value="on"></c:set>
		<c:set var="studentView" value="off"></c:set>
		<c:set var="teacherView" value="off"></c:set>
	</c:when>
	<c:when test="${param.type=='teacher'}">
		<c:set var="adminView" value="off"></c:set>
		<c:set var="studentView" value="off"></c:set>
		<c:set var="teacherView" value="on"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="adminView" value="off"></c:set>
		<c:set var="studentView" value="on"></c:set>
		<c:set var="teacherView" value="off"></c:set>
	</c:otherwise>
</c:choose>


<%
	if (buttonss.contains(admin)) {
%>

<ui:button parentId="top" order="1"
	onclick="lservice.switchRole('${adminUrl }','${adminView }')"
	styleClass="lui_lservice_admin_${adminView}"
	title="${lfn:message('kms-lservice:lservice.admin')}">
</ui:button>


<%
	}
%>


<%
	if (buttonss.contains(teacher)) {
%>
<ui:button parentId="top" order="2"
	onclick="lservice.switchRole('${teacherUrl }','${teacherView}')"
	styleClass="lui_lservice_teach_${teacherView}"
	title="${lfn:message('kms-lservice:lservice.teacher')}">
</ui:button>

<%
	}
%>

<%
	if (buttonss.contains(student)) {
%>
<ui:button parentId="top" order="3"
	onclick="lservice.switchRole('${studentUrl }','${studentView}')"
	styleClass="lui_lservice_stu_${studentView}"
	title="${lfn:message('kms-lservice:lservice.student')}">
</ui:button>

<%
	}
%>
 <script>seajs.use('kms/lservice/index/js/index.js');</script>
