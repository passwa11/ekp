<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
lbpm.globals.includeFile("/sys/lbpmservice/operation/branchadmin/operation_branchadmin_endbranch.js");
(function(constant){
	constant.opt.endbranch='<bean:message bundle="sys-lbpmservice-operation-branchadmin" key="lbpmOperations.fdOperType.processor.endbranch" />';
	constant.opt.endbranchMsg='<bean:message bundle="sys-lbpmservice-operation-branchadmin" key="lbpmOperations.endbranch.msg" />';
})(lbpm.constant);