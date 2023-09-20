<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<!doctype html>
<html>
	<head>
		<meta http-equiv="x-ua-compatible" content="IE=edge"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><bean:message key="lbpmAuditNote.modify.windowTitle" bundle="sys-lbpmservice-support"/></title>
	</head>
	<body style="margin: 0px;height:600px;overflow:hidden;">
		<iframe width="100%" height="100%" frameborder="0" scrolling="yes" id="topFrame" src="<c:url value="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote.do" />?
		method=viewHistory&fdId=${lfn:escapeHtml(param.fdId)}&formBeanName=${lfn:escapeHtml(param.formBeanName)}">
		</iframe>
	</body>
</html>