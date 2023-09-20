<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<td width="100px">
	<kmss:message key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.selectTypes" bundle="sys-lbpmservice" />
</td>
<td colspan="2">
	<c:if test="${JsParam.nodeType ne 'robtodoNode'}">
		<input name="ext_refuse_types" value="refusePassedToSequenceFlowNodeLabel" type="checkbox" checked='true'><kmss:message key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.sequenceFlow.text" bundle="sys-lbpmservice" /></br>
	</c:if>
	<input name="ext_refuse_types" value="refusePassedToThisNodeLabel" type="checkbox" checked='true'><kmss:message key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.returnBackMe.text" bundle="sys-lbpmservice" /></br>
	<c:if test="${JsParam.nodeType ne 'robtodoNode'}">
		<input name="ext_refuse_types" value="refusePassedToThisNodeOnNodeLabel" type="checkbox" checked='true'><kmss:message key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.returnBack.text" bundle="sys-lbpmservice" /></br>
		<input name="ext_refuse_types" value="refusePassedToTheNodeLabel" type="checkbox" checked='true'><kmss:message key="lbpmNode.processingNode.operationsTDTitle.handlerOperationTypeRefuse.returnBackTheNode.text" bundle="sys-lbpmservice" />
	</c:if>
</td>

