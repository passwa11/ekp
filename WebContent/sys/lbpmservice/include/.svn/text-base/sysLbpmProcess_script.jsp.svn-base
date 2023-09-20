<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.lbpmservice.support.service.spring.LbpmServiceCompressExecutor" %>
<%@ page import="com.landray.kmss.sys.lbpmservice.util.SysLbpmUtil" %>
<%@ page import="com.landray.kmss.sys.ui.util.PcJsOptimizeUtil" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="net.sf.json.JSONArray"%>
<%@ page import="org.apache.commons.lang3.ArrayUtils" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.stream.Collectors" %>
<c:set var="modelClassName" value="${sysWfBusinessForm.sysWfBusinessForm.fdModelName}" />
<c:set var="modeId" value="${sysWfBusinessForm.fdId}" />
<script type="text/javascript">
	Com_IncludeFile("validator.jsp|validation.jsp", null, "js");
</script>
<script type="text/javascript">
	Com_IncludeFile("jquery.js|json2.js|docutil.js|optbar.js|validation.js|plugin.js|xform.js|data.js|dialog.js|doclist.js");
	Com_IncludeFile('fSelect_script.js','../sys/xform/designer/fSelect/');
</script>

<link rel="stylesheet" type="text/css" href="<c:url value="/sys/lbpmservice/resource/jNotify.jquery.css?s_cache=${LUI_Cache}"/>" media="screen" />
<link type="text/css" rel="stylesheet" href="<c:url value="/sys/lbpmservice/common/css/process_tab_main.css?s_cache=${LUI_Cache}"/>"/>
<script type="text/javascript" src="<c:url value="/sys/lbpmservice/resource/jNotify.jquery.js?s_cache=${LUI_Cache}"/>"></script>
<%@ include file="/sys/lbpmservice/include/sysLbpmProcessConstant.jsp"%>
<c:if test="${XformObject_Lang_Init ne 'true'}">
	<%@ include file="/sys/xform/include/sysForm_lang.jsp"%>
</c:if>
<script type="text/javascript">
lbpm.compressSwitch = "${compressSwitch}";
lbpm.combinedFiles = JSON.parse('<%=JSONArray.fromObject(Arrays.asList(LbpmServiceCompressExecutor.sysLbpmProcess_script_combined))%>');
var Lbpm_SettingInfo = lbpm.settingInfo = new KMSSData().AddBeanData("lbpmSettingInfoService").GetHashMapArray()[0]; //统一在此获取流程默认值与功能开关等配置
</script>
<%
	// 获取所有操作类型扩展点下的reviewJsp
	String[] syslbpmOperation_jspArray = SysLbpmUtil.getAllOperationJsp(false);
	// 获取所有操作类型扩展点下的reviewJs,已经截取成正确路径
	String[] syslbpmOperation_jsArray = SysLbpmUtil.getAllOperationJs();
	List<String> syslbpmOperation_jsLists = Arrays.asList(syslbpmOperation_jsArray);
	String[] assignAndCommunicateJs = {"sys/lbpmservice/operation/handler/operation_handler_assigncancel.js",
			"sys/lbpmservice/operation/handler/operation_handler_assignrefuse.js","sys/lbpmservice/operation/handler/operation_handler_assignpass.js",
			"sys/lbpmservice/operation/handler/operation_handler_cancelcommunicate.js","sys/lbpmservice/operation/handler/operation_handler_returncommunicate.js"};
	String[] allOperationJs = ArrayUtils.addAll(assignAndCommunicateJs,syslbpmOperation_jsArray);
	request.setAttribute("syslbpmOperation_jspArray", Arrays.stream(syslbpmOperation_jspArray).collect(Collectors.joining(";")));
	request.setAttribute("syslbpmOperation_jsArray",syslbpmOperation_jsArray);
%>
<!-- 是否开启合并加载js模式  -->
<c:choose>
	<c:when test="${compressSwitch eq 'true'  && lfn:jsCompressEnabled('lbpmServiceCompressExecutor', 'sysLbpmProcess_script_combined')}">
		<script src="<%= PcJsOptimizeUtil.getScriptSrcByExtension("lbpmServiceCompressExecutor","sysLbpmProcess_script_combined") %>?s_cache=${ LUI_Cache }">
		</script>
		<script src="${LUI_ContextPath}<%= PcJsOptimizeUtil.getScriptSrc("resource/dynamic_combination/sysLbpmProcessOperation_script_combined.js", Arrays.asList(allOperationJs), true) %>?s_cache=${ LUI_Cache }">
		</script>
	</c:when>
	<c:otherwise>
		<script src="<c:url value="/sys/lbpm/flowchart/js/workflow.js?s_cache=${LUI_Cache}"/>"></script>
		<script src="<c:url value="/sys/lbpmservice/include/syslbpmprocess.js?s_cache=${LUI_Cache}"/>"></script>
		<script src="<c:url value="/sys/lbpmservice/resource/address_builder.js?s_cache=${LUI_Cache}"/>"></script>
		<script src="<c:url value="/sys/lbpmservice/include/sysLbpmProcess_script.js?s_cache=${LUI_Cache}"/>"></script>
	</c:otherwise>
</c:choose>
<template:block name="preloadJs"/>

<link rel="stylesheet" type="text/css" href="<c:url value="/sys/lbpmservice/resource/review.css?s_cache=${LUI_Cache}"/>" />
<c:forEach items="${lbpmProcessForm.curNodesReviewJs}" var="reviewJs"
			varStatus="vstatus">
	<c:import url="${reviewJs}" charEncoding="UTF-8" />
</c:forEach>
<c:forEach items="${lbpmProcessForm.curTasksReviewJs}" var="reviewJs"
			varStatus="vstatus">
	<c:import url="${reviewJs}" charEncoding="UTF-8" />
</c:forEach>
<script type="text/javascript">	
	lbpm.drafterOperationsReviewJs=new Array();
	lbpm.adminOperationsReviewJs=new Array();
	lbpm.historyhandlerOperationsReviewJs=new Array();
	lbpm.branchAdminOperationsReviewJs=new Array();
	<c:forEach items="${lbpmProcessForm.curDrafterOperationsReviewJs}" var="reviewJs" varStatus="vstatus">
		<c:if test="${!(fn:contains(syslbpmOperation_jspArray, reviewJs)) || compressSwitch eq 'false'}">
			lbpm.drafterOperationsReviewJs.push("${reviewJs}");
		</c:if>
	</c:forEach>
	<c:forEach items="${lbpmProcessForm.curAdminOperationsReviewJs}" var="reviewJs" varStatus="vstatus">
		<c:if test="${!(fn:contains(syslbpmOperation_jspArray, reviewJs)) || compressSwitch eq 'false'}">
			lbpm.adminOperationsReviewJs.push("${reviewJs}");
		</c:if>
	</c:forEach>
	<c:forEach items="${lbpmProcessForm.curHistoryhandlerOperationsReviewJs}" var="reviewJs" varStatus="vstatus">
		<c:if test="${!(fn:contains(syslbpmOperation_jspArray, reviewJs)) || compressSwitch eq 'false'}">
			lbpm.historyhandlerOperationsReviewJs.push("${reviewJs}");
		</c:if>
	</c:forEach>
	<c:forEach items="${lbpmProcessForm.curBranchAdminOperationsReviewJs}" var="reviewJs" varStatus="vstatus">
		<c:if test="${!(fn:contains(syslbpmOperation_jspArray, reviewJs)) || compressSwitch eq 'false'}">
			lbpm.branchAdminOperationsReviewJs.push("${reviewJs}");
		</c:if>
	</c:forEach>
	lbpm.myAddedNodes=new Array();
</script>

<link rel="stylesheet" type="text/css" href="<c:url value="/component/locker/resource/jNotify.jquery.css"/>" media="screen" />

<script type="text/javascript">	
	lbpm.urls={};
	(function(urls) {
		urls.auditNodeUrl = '<c:url value="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote.do?method=listNote" />'
			+'&fdModelId=${sysWfBusinessForm.fdId}&fdModelName=${modelClassName}&formBeanName=${(not empty JsParam.formName) ? (JsParam.formName) : formName}'
			+'&showPersonal=true&approveType=${JsParam.approveType}';
		urls.freeChartUrl = '<c:url value="/sys/lbpm/flowchart/page/freeflowPanel.jsp">'
			+'<c:param name="edit" value="true" />'
			+'<c:param name="extend" value="oa" />'
			+'<c:param name="template" value="false" />'
			+'<c:param name="contentField" value="sysWfBusinessForm.fdFlowContent" />'
			+'<c:param name="statusField" value="sysWfBusinessForm.fdTranProcessXML" />'
			+'<c:param name="modelName" value="${modelClassName}" />'
			+'<c:param name="modelId" value="${sysWfBusinessForm.fdId}" />'
			+'<c:param name="hasParentProcess" value="${lbpmProcessForm.hasParentProcess}" />'
			+'<c:param name="hasSubProcesses" value="${lbpmProcessForm.hasSubProcesses}" />'
			+'<c:param name="showBar" value="true" />'
			+'<c:param name="isNotShowBar" value="true" />' //去掉保存按钮
			+'<c:param name="freeflowPanelImg" value="true" />' //显示自由流流程图，add 2020-3-1
			+'<c:param name="flowType" value="1" />'
			+'<c:param name="deployApproval" value="0" />'
			+'</c:url>';
		urls.freeChartUrlMessage = "${ lfn:message('sys-lbpmservice:sysProcess.freeFlow.flowChart.warning') }";
		urls.chartUrl = '<c:url value="/sys/lbpm/flowchart/page/panel.html">'
			+'<c:param name="edit" value="false" />'
			+'<c:param name="extend" value="oa" />'
			+'<c:param name="template" value="false" />'
			+'<c:param name="contentField" value="sysWfBusinessForm.fdFlowContent" />'
			+'<c:param name="statusField" value="sysWfBusinessForm.fdTranProcessXML" />'
			+'<c:param name="modelName" value="${modelClassName}" />'
			+'<c:param name="modelId" value="${sysWfBusinessForm.fdId}" />'
			+'<c:param name="hasParentProcess" value="${lbpmProcessForm.hasParentProcess}" />'
			+'<c:param name="hasSubProcesses" value="${lbpmProcessForm.hasSubProcesses}" />'
			+'<c:param name="modelingModelId" value="${modelingAppModelMainForm.fdModelId}" />'
			+'</c:url>';
		urls.flowTableUrl = '<c:url value="/sys/lbpmservice/include/sysLbpmTable_view.jsp" />'
			+'?edit=false&extend=oa&template=false&contentField=sysWfBusinessForm.fdFlowContent&statusField=sysWfBusinessForm.fdTranProcessXML'
			+'&modelName=${modelClassName}&modelId=${sysWfBusinessForm.fdId}&IdPre=${sysWfBusinessFormPrefix}&fdKey=${JsParam.fdKey}';
		urls.flowLogUrl = '<c:url value="/sys/lbpmservice/support/lbpm_audit_note_ui/lbpmAuditNote_flowLog_index.jsp" />'
			+'?fdModelId=${sysWfBusinessForm.fdId}&fdModelName=${modelClassName}&formName=${JsParam.formName}';
		urls.processStatusUrl = '<c:url value="/sys/lbpmservice/support/lbpm_process_status/index.jsp" />'
			+'?fdModelId=${sysWfBusinessForm.fdId}&fdModelName=${modelClassName}';
		urls.processStatusDinUrl = '<c:url value="/sys/lbpmservice/support/lbpm_process_status/index.jsp" />';
		urls.processRestartLogUrl = '<c:url value="/sys/lbpmservice/support/lbpm_process_restart_log/lbpmProcessRestartLog_index.jsp" />'
			+'?fdModelId=${sysWfBusinessForm.fdId}&fdModelName=${modelClassName}';
	})(lbpm.urls);
</script>
<c:if test="${lbpmProcessForm.fdTemplateType=='4'}">
<script src="<c:url value="/sys/lbpmservice/include/syslbpmprocess_freeflow.js?s_cache=${LUI_Cache}"/>"></script>
</c:if>
<c:if test="${lbpmProcessForm.fdIsError == 'true'}">
<%
com.landray.kmss.sys.lbpmservice.support.service.spring.ErrorQueueDataBean errorDataBean = 
	(com.landray.kmss.sys.lbpmservice.support.service.spring.ErrorQueueDataBean) SpringBeanUtil.getBean("lbpmErrorQueueDataBean");
Object msg = errorDataBean.getErrorJsonData((String) pageContext.getAttribute("modeId"));
Object nameInfo = errorDataBean.getAdminNameJsonData((String) pageContext.getAttribute("modeId"));
%>
<script>
lbpm.onLoadEvents.delay.push(function() {
	lbpm.errorMsg = {};
	lbpm.errorMsg.nameInfo = <%=nameInfo%>;
	lbpm.errorMsg.tmpNotify = '<kmss:message key="sys-lbpm-engine:lbpm.process.exception.notify.all" />';
	lbpm.errorMsg.tmpFull = '<kmss:message key="sys-lbpm-engine:lbpm.process.exception.notify.full" />';
	lbpm.errorMsg.tmpMsg = '<kmss:message key="sys-lbpm-engine:lbpm.process.exception.notify.msg" />';
	lbpm.errorMsg.tmpDef = '<kmss:message key="sys-lbpm-engine:lbpm.process.exception.notify.def" />';
	lbpm.errorMsg.msg = <%=msg%>;
	lbpm.errorMsg.tmp = '<kmss:message key="sys-lbpmservice-operation-admin:lbpmOperations.fdOperType.processor.retry.queueTemp" />';
	lbpm.errorMsg.lbpmRightLookmore = "<bean:message bundle='sys-lbpmservice' key='lbpmRight.lookmore' />"
	lbpm.errorMsg.lbpmRightClose = "<bean:message bundle='sys-lbpmservice' key='lbpmRight.close' />"
	lbpm.globals.checkIsError();
});
</script>
</c:if>
<c:if test="${sysWfBusinessForm.docStatus>='20' && sysWfBusinessForm.docStatus<'30'}">
<%
com.landray.kmss.sys.lbpmservice.node.checknode.CheckNodeMsgDataBean msgDataBean = 
(com.landray.kmss.sys.lbpmservice.node.checknode.CheckNodeMsgDataBean) SpringBeanUtil.getBean("lbpmcheckNodeMsgDataBean");
Object msgCheckNode=msgDataBean.getMsgJsonData((String) pageContext.getAttribute("modeId"));
%>
<script>
lbpm.onLoadEvents.delay.push(function() {
	lbpm.globals.checkNodeMsg(<%=msgCheckNode%>);
});
</script>
</c:if>
<c:if test="${(sysWfBusinessForm.docStatus>='20' && sysWfBusinessForm.docStatus<'30') || sysWfBusinessForm.docStatus == '11'}">
<script>
lbpm.onLoadEvents.delay.push(function() {
	lbpm.globals.checkQueueLock('<kmss:message key="sys-lbpm-engine:lbpm.process.running.notify.all" />')
});
</script>
<c:if test="${sysWfBusinessForm.sysWfBusinessForm.fdIsHander == 'true'}">
<script>
lbpm.onLoadEvents.once.push(function() {
	if ("true" != "${lbpmProcessForm.fdIsHander}") {
		return;
	}
	if ("true" == "${JsParam.isSimpleWorkflow}") {
		//return;
	}
	lbpm.globals.checkShortReview('<kmss:message key="sys-lbpmservice:lbpm.operation.shortcut" />');
});

lbpm.onLoadEvents.delay.push(function() {
	autoSaveDraftAction();
	lbpm.globals.updateIsLook();
});

lbpm.onLoadEvents.once.push(function() {
	lbpm.assignMsg = {};
	lbpm.assignMsg.allowMuti = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle_Scope.allow.muti" />';
	lbpm.assignMsg.assign = '<bean:message bundle="sys-lbpmservice-support" key="lbpmAssign.fdOperType.assign" />';
	lbpm.globals.initAssign();
});
</script>
</c:if>
<c:if test="${sysWfBusinessForm.sysWfBusinessForm.fdIsHander != 'true'}">
	<%
	com.landray.kmss.sys.lbpmservice.support.service.spring.LbpmAssignServiceImp assignService = 
		(com.landray.kmss.sys.lbpmservice.support.service.spring.LbpmAssignServiceImp) SpringBeanUtil.getBean("lbpmAssignService");
	Object assignData = assignService.getAssignJsonData((String) pageContext.getAttribute("modeId"));
	%>
	<script type="text/javascript">
	lbpm.onLoadEvents.once.push(function() {
		lbpm.assignMsg = {};
		lbpm.assignMsg.assignData = <%=assignData%>;
		lbpm.assignMsg.replyAssign = '<bean:message key="lbpmAssign.fdOperType.replyAssign" bundle="sys-lbpmservice-support" />';
		lbpm.assignMsg.assign = '<bean:message key="lbpmAssign.fdOperType.assign" bundle="sys-lbpmservice-support" />';
		lbpm.assignMsg.allowMuti = '<bean:message bundle="sys-lbpmservice" key="lbpmNode.processingNode.operationsTDTitle_Scope.allow.muti" />'
		lbpm.assignMsg.assignOpinion = '<bean:message key="lbpmAssign.assignOpinion" bundle="sys-lbpmservice-support" />';
		lbpm.assignMsg.submit = '<bean:message key="button.submit"/>';
		lbpm.assignMsg.assignOption = '<bean:message key="lbpmAssign.assignOption" bundle="sys-lbpmservice-support" />';
		lbpm.globals.initOtherAssign();
	});
	</script>
</c:if>
</c:if>
<script>
	lbpm.followMsg = {};
	lbpm.followMsg.allNodes = "<bean:message key='lbpmFollow.allNodes' bundle='sys-lbpmservice-support' />";
	lbpm.followMsg.specifiedNode = "<bean:message key='lbpmFollow.specifiedNode' bundle='sys-lbpmservice-support' />";
	lbpm.followMsg.cancelText = '<bean:message key="lbpmFollow.button.cancelFollow" bundle="sys-lbpmservice-support" />';
	lbpm.followMsg.followText = '<bean:message key="lbpmFollow.button.follow" bundle="sys-lbpmservice-support" />';
	lbpm.followMsg.followCancelText = '<bean:message key="lbpmFollow.button.followCancel" bundle="sys-lbpmservice-support" />';
	lbpm.followMsg.ok = "${lfn:message('button.ok')}";
	lbpm.followMsg.cancel = "${lfn:message('button.cancel')}";
	lbpm.followMsg.followConfirm = '<bean:message  bundle="sys-lbpmservice-support" key="lbpmFollow.button.follow.confirm"/>';
	lbpm.followMsg.cancelFollowConfirm = '<bean:message  bundle="sys-lbpmservice-support" key="lbpmFollow.button.cancelFollow.confirm"/>';
</script>