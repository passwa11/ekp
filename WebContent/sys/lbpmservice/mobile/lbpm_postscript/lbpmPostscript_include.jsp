<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:forEach items="${lbpmPostscriptMap[param.type]}" var="lbpmPostscript" varStatus="vStatus">
	<table class="tb_noborder" width="100%">
		<c:if test="${lbpmPostscript.fdIsHide=='2'}">
			<tr>
				<td style="word-wrap: break-word;word-break: break-all;">
					<kmss:showText value="${lbpmPostscript.fdPostscript}" />
				</td>
			</tr>
			<c:if test="${not empty mainForm.attachmentForms[lbpmPostscript.fdId] and not empty mainForm.attachmentForms[lbpmPostscript.fdId].attachments}">
				<tr>
					<td colspan="2">
						<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
				        <c:param name="formBeanName" value="${formBeanName}"/>
				        <c:param name="fdKey" value="${lbpmPostscript.fdId}"/>
				        <c:param name="fdModelName" value="${lbpmPostscript.fdProcess.fdModelName}" />
						<c:param name="fdModelId" value="${lbpmPostscript.fdProcess.fdModelId}" />
				        <c:param name="fdViewType" value="simple"/>
			        </c:import>
					</td>
				</tr>
			</c:if>
			<tr>
				<td style="word-wrap: break-word;word-break: break-all;text-align:right;">
					<kmss:showDate type="datetime" value="${lbpmPostscript.fdCreateTime}"/>
				</td>
			</tr>
		</c:if>
		<c:if test="${lbpmPostscript.fdIsHide=='1'}" >
			<tr><td colspan="2"><font style="font-style:italic"><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdIsHide_1" /></font></td></tr>
		</c:if>
		<c:if test="${lbpmPostscript.fdIsHide=='3'}" >
			<tr><td colspan="2"><font style="font-style:italic"><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdIsHide_3" /></font></td></tr>
		</c:if>
		<c:if test="${not empty lbpmPostscript.fdPostscriptFrom}">
			<tr>
				<td colspan="2" align="right" style="color:#999;">
					<kmss:showText value="${lbpmPostscript.fdPostscriptFrom}" />
				</td>
			</tr>
		</c:if>
	</table>
</c:forEach>