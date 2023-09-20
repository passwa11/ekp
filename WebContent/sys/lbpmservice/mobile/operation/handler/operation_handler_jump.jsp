<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
//定义常量
(function(constant){
	constant.handlerOperationTypeJump='<bean:message bundle="sys-lbpmservice-operation-handler" key="lbpmNode.checknode.operationsTDTitle.handlerOperationTypeJump" />';
	constant.noRefuseNode='<bean:message bundle="sys-lbpmservice-operation-handler" key="lbpmNode.flowContent.noJumpNode" />';
	constant.handlerOperationTypeRefuse_abandonSubprocess='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.abandonSubprocess" />';
})(lbpm.workitem.constant);
lbpm.globals.includeFile("/sys/lbpmservice/mobile/operation/handler/operation_handler_jump.js");
</script>
