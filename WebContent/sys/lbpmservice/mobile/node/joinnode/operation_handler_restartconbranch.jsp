<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script language="JavaScript">
//定义常量
(function(constant){
	constant.opt.RestartconbranchTDTitle='<bean:message bundle="sys-lbpmservice-node-joinnode" key="lbpmNode.processingNode.operationsTDTitle.restartconbranch" />';
	constant.opt.RestartconbranchIsNull='<bean:message bundle="sys-lbpmservice-node-joinnode" key="lbpmProcess.joinnode.conbranch.restartbranch.check" />';
})(lbpm.constant);

lbpm.globals.includeFile("/sys/lbpmservice/mobile/node/joinnode/operation_handler_restartconbranch.js");
</script>
