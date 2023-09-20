<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

<script language="JavaScript">
//定义常量
(function(constant){
	constant.opt.AdditionSignPeople='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationType.people" />' //加签人员;
	constant.opt.AdditionSignIsNull="<bean:message bundle='sys-lbpmservice' key='lbpmNode.validate.toOtherHandlerIds.additionSign.isNull' />";
	constant.opt.PleaseChoose="<bean:message bundle='sys-lbpmservice' key='lbpmNode.validate.toOtherHandlerIds.assign.isNull' />";
})(lbpm.constant);

lbpm.globals.includeFile("/sys/lbpmservice/mobile/node/reviewnode/operation_handler_additionSign.js");
</script>
