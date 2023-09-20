<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script src="<c:url value="/sys/lbpmservice/node/reviewnode/operation_handler_additionSign.js?s_cache=${LUI_Cache}"/>"></script>
<script language="JavaScript">
//定义常量
(function(constant){
	constant.opt.AdditionSignPeople='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationType.people" />' //补签人员;
	constant.opt.AdditionSignIsNull="<bean:message bundle='sys-lbpmservice' key='lbpmNode.validate.toOtherHandlerIds.additionSign.isNull' />";
	constant.opt.PleaseChoose="<bean:message bundle='sys-lbpmservice' key='lbpmNode.validate.toOtherHandlerIds.assign.isNull' />";
	constant.opt.Staff="<bean:message bundle='sys-lbpmservice' key='lbpmNode.processingNode.operationsTDTitle.handlerOperationType.people' />";
})(lbpm.constant);
</script>
