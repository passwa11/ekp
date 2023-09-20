<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script language="JavaScript">
lbpm.globals.includeFile("/sys/lbpmservice/operation/operation_common_passtype.js");
lbpm.globals.includeFile("/sys/lbpmservice/node/reviewnode/operation_handler_pass.js");
//定义常量
(function(constant){
	constant.opt.handlerOperationTypepass='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypepass" />';
	constant.opt.calcBranch='<bean:message bundle="sys-lbpmservice" key="lbpmNode.calcBranch" />';
})(lbpm.constant);
</script>