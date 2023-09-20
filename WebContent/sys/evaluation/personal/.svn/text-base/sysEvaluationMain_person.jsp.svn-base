<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%
	String userId = request.getParameter("userId");
	if(StringUtil.isNull(userId)){
		request.setAttribute("userId", UserUtil.getUser().getFdId());
	}
%>
<template:include ref="person.home">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-evaluation:table.sysEvaluationMain') }"></c:out>
	</template:replace>
	<template:replace name="content">
		<%-- <c:import url="/sys/evaluation/personal/sysEvaluationMain_other.jsp" charEncoding="UTF-8">
			<c:param name="userId" value="${(empty param.userId) ? KMSS_Parameter_CurrentUserId : (param.userId)}"></c:param>
		</c:import> --%>
		
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${lfn:message('sys-evaluation:table.sysEvaluationMain.all') }">
				<c:import url="/sys/evaluation/personal/sysEvaluationMain_main.jsp" charEncoding="UTF-8"/>
			</ui:content>
			<ui:content title="${lfn:message('sys-evaluation:table.sysEvaluationNotes') }">
				<c:import url="/sys/evaluation/personal/sysEvaluationMain_notes.jsp" charEncoding="UTF-8"/>
			</ui:content>
		</ui:tabpanel>
	</template:replace>
</template:include>
