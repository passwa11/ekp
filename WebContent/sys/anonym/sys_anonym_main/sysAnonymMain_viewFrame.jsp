<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>
			<bean:message key="sysAnonymMain.fdTitle" bundle="sys-anonym"/>
		</title>
	</head>
	<frameset framespacing=1 bordercolor=#003048 frameborder=1 rows="*">
		<frame frameborder="0" noresize scrolling="yes" id="topFrame"
			src="<c:url value="/sys/anonym/sys_anonym_main/sysAnonymMain.do?method=publishToAnonym&fdModelName=${HtmlParam.fdModelName}&fdModelId=${HtmlParam.fdModelId}&fdKey=${HtmlParam.fdKey}"/>"
	</frameset>
</html>