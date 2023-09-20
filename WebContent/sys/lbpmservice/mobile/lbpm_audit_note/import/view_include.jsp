<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
	require([ "sys/lbpmservice/mobile/lbpm_audit_note/js/LbpmserviceShowFlow!?processId=${param.processId}&docStatus=${param.docStatus}" ])
</script>
