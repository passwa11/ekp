<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting" %>

<%  pageContext.setAttribute("isRefuseSelectPeople",(new LbpmSetting()).getIsRefuseSelectPeople());%>

<style>
	.trialStaffPeople .muiSelInput{
		text-align:left;
	}
	
	.trialStaffPeople .muiSelInputRight{
		right: 1rem;
	}
</style>

<script>
//定义常量
(function(constant){
	constant.handlerOperationTypeRefuse='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse" />';
	constant.noRefuseNode='<bean:message bundle="sys-lbpmservice" key="lbpmNode.flowContent.noRefuseNode" />';
	constant.noReturnBackNode='<bean:message bundle="sys-lbpmservice" key="lbpmNode.flowContent.noReturnBackNode" />';
	constant.thisNode='<bean:message bundle="sys-lbpmservice" key="lbpmNode.flowContent.thisNode" />';
	constant.handlerOperationTypeRefuse_sequenceFlow='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.sequenceFlow.title"/>';
	constant.handlerOperationTypeRefuse_returnBackMe='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.returnBackMe.title"/>';
	constant.handlerOperationTypeRefuse_returnBack='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.returnBack.title"/>';
	constant.handlerOperationTypeRefuse_returnBackTheNode='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.returnBackTheNode.title"/>';
	constant.handlerOperationTypeRefuse_abandonSubprocess='<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.abandonSubprocess"/>';
	constant.isRefuseSelectPeople='${isRefuseSelectPeople}';
	constant.refuseSelectPeopleMessage="${lfn:message('sys-lbpmservice:lbpmservice.select.refusePeopleMess')}";
})(lbpm.workitem.constant);
lbpm.globals.includeFile("/sys/lbpmservice/mobile/node/reviewnode/operation_handler_refuse.js");
</script>
