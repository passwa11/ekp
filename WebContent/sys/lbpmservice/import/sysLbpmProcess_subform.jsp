<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%
	//是否钉钉高级审批模式
	pageContext.setAttribute("ddMode", SysFormDingUtil.getEnableDing());
%>
<c:if test="${compressSwitch eq 'false'}">
	<script src="<c:url value="/sys/lbpmservice/import/sysLbpmProcess_subform.js?s_cache=${LUI_Cache}"/>"></script>
</c:if>
<c:set var="sysWfBusinessForm" value="${requestScope[param.formName]}" />
<c:set var="lbpmProcessForm" value="${sysWfBusinessForm.sysWfBusinessForm.internalForm}" />
<!-- 多表单模式下，显示切换表单按钮 -->
<c:if test="${sysWfBusinessForm.docStatus!='10' && sysWfBusinessForm.docStatus!='11' && sysWfBusinessForm.method_GET=='view' && lbpmProcessForm.showSubBut  && ddMode == 'false'}">
	 <ui:button id="switchForm" parentId="toolbar" text="${ lfn:message('sys-lbpmservice:lbpmNode.subForm.switchForm') }" order="1" onclick="Subform_switchForm();"></ui:button>
</c:if>

<script type="text/javascript">
//lbpm.globals.includeFile("/sys/lbpmservice/import/sysLbpmProcess_subform.js");
lbpm.subformMsg = {};
(function(subformMsg){
	subformMsg.curSubForm = '${ lfn:message("sys-lbpmservice:lbpmNode.subform.curSubForm") }';
	subformMsg.taskToDo = '${ lfn:message("sys-lbpmservice:lbpmNode.subform.taskToDo") }';
	subformMsg.defautForm = '${ lfn:message("sys-lbpmservice:lbpmNode.subform.defaut_form") }';
	subformMsg.switchForm = "${ lfn:message('sys-lbpmservice:lbpmNode.subForm.switchForm') }";
	subformMsg.ok = "${lfn:message('button.ok')}";
	subformMsg.cancel = "${lfn:message('button.cancel')}";
	subformMsg.defaultPrintForm = '${ lfn:message("sys-lbpmservice:lbpmNode.subform.default_print_form") }';
	subformMsg.switchPrint = "${ lfn:message('sys-lbpmservice:lbpmNode.subForm.switchPrint') }";
})(lbpm.subformMsg);
</script>