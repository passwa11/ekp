<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="person.home">
	<c:set var="categoryType" value="attend" />
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:sysAttend.sysAttendMain.my') }"></c:out>
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/sys/attend/resource/css/attend.css" />
	</template:replace>
	<template:replace name="content">
		<ui:tabpanel id="sysAttendPanel" layout="sys.ui.tabpanel.list">
			<c:set var="navTitle" value="${lfn:message('sys-attend:sysAttend.nav.item.attendCalendar') }"></c:set>
			<c:if test="${not empty param.navTitle }">
				<c:set var="navTitle" value="${param.navTitle}"></c:set>
			</c:if>
			<ui:content title="${ navTitle }">
				<%@ include file="/sys/attend/sys_attend_main/sysAttendMain_calendar_conent.jsp"%>
			</ui:content>
		</ui:tabpanel>
	</template:replace>
</template:include>