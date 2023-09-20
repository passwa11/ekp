<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:if test="${XformObject_Lang_Init ne 'true'}">
	<%@ include file="/sys/xform/include/sysForm_lang.jsp"%>
</c:if>
<script language="JavaScript">
	Com_IncludeFile('fSelect_script.js','../sys/xform/designer/fSelect/');
</script>
<script language="JavaScript">
lbpm.globals.includeFile("/sys/lbpmservice/node/reviewnode/operation_handler_refuse.js");
//定义常量
(function(constant){
	constant.opt.handlerOperationTypeRefuse = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse" />';
	constant.opt.abandonSubprocess = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.abandonSubprocess" />';
	constant.opt.noRefuseNode = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.flowContent.noRefuseNode" />';
	constant.opt.sequenceFlow = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.sequenceFlow" />';
	constant.opt.sequenceFlowTitle = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.sequenceFlow.title" />';
	constant.opt.returnBackMe = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.returnBackMe" />';
	constant.opt.returnBackMeTitle = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.returnBackMe.title" />';
	constant.opt.returnBackTitle = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.returnBack.title" />'; 
	constant.opt.returnBackTheNode = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.returnBackTheNode" />';
	constant.opt.returnBackTheNodeTitle = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.returnBackTheNode.title" />';
	constant.opt.thisNode = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.flowContent.thisNode" />';
	constant.opt.refusePeople = "${lfn:message('sys-lbpmservice:lbpmservice.select.refusePeople')}";
	constant.opt.refusePeopleSearch = "${lfn:message('sys-lbpmservice:lbpmservice.select.refusePeople.search')}";
})(lbpm.constant);
</script>
