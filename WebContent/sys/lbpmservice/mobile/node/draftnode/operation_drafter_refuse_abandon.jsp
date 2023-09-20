<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script language="JavaScript">
	(function(constant){
		constant.draft_opr_abandon='<bean:message key="lbpmOperations.fdOperType.draft.abandon" bundle="sys-lbpmservice-operation-drafter" />';
		constant.draft_opr_abandon_confirm='<bean:message key="lbpmNode.validate.abandon.confirm" bundle="sys-lbpmservice" />';
	})(lbpm.workitem.constant);
	lbpm.globals.includeFile("/sys/lbpmservice/mobile/node/draftnode/operation_drafter_refuse_abandon.js");
</script>