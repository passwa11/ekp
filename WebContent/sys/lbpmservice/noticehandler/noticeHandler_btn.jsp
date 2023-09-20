<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
	lbpm.globals.includeFile("/sys/lbpmservice/noticehandler/notice_handler_btn.js");
	//定义常量
	(function(constant){
		constant.noticeHandlerName='<bean:message bundle="sys-lbpmservice" key="lbpm.process.noticeHandler.name" />'; 
	})(lbpm.constant);	
</script>