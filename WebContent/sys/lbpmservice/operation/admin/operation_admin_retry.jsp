<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
lbpm.globals.includeFile("/sys/lbpmservice/operation/admin/operation_admin_retry.js");
(function(constant){
	constant.opt.retryQueueTitel='<kmss:message key="sys-lbpmservice-operation-admin:lbpmOperations.fdOperType.processor.retry.queueTitel" />';
	constant.opt.retryQueueTemp='<kmss:message key="sys-lbpmservice-operation-admin:lbpmOperations.fdOperType.processor.retry.queueTemp" />';
	constant.opt.retryShowDetail='<kmss:message key="sys-lbpmservice-operation-admin:lbpmOperations.fdOperType.processor.retry.showDetail" />';
	constant.opt.retryHideDetail='<kmss:message key="sys-lbpmservice-operation-admin:lbpmOperations.fdOperType.processor.retry.hideDetail" />';
	constant.opt.retrySelect='<kmss:message key="sys-lbpmservice-operation-admin:lbpmOperations.fdOperType.processor.retry.select" />';
})(lbpm.constant);