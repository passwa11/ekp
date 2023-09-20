<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("dialog.js|optbar.js");
</script>
<kmss:windowTitle moduleKey="sys-lbpmservice-support:table.lbpmTemplate" subjectKey="sys-lbpmservice-support:lbpmTemplate.nodes2Excel.subject" />

<p class="txttitle"><bean:message key="lbpmTemplate.nodes2Excel.subject" bundle="sys-lbpmservice-support"/></p>
<center>
<c:if test="${not empty templateList}">

<div>
<!--
<p>
<bean:message key="lbpmTemplate.nodes2Excel.help" bundle="sys-lbpmservice-support"/>
</p>
-->
<table class="tb_normal" width=95%>
	<tr class="tr_normal_title">
		<td width="40pt">
			<bean:message key="page.serial"/>
		</td>
		<td>
			<bean:message key="lbpmTemplate.updateAuditor.template" bundle="sys-lbpmservice-support"/>
		</td>
		<td>
			<bean:message key="lbpmTemplate.updateAuditor.common" bundle="sys-lbpmservice-support"/>
		</td>
		<td width="120pt" class="td_normal_title">
			<bean:message key="lbpmTemplate.fdType" bundle="sys-lbpmservice-support"/>
		</td>
	</tr>
	<c:forEach items="${templateList}" var="template" varStatus="vstatus">
		<tr>
			<td>
				${vstatus.index+1}
			</td>
			<td>
				<c:out value="${template.modelSubject}" />
			</td>
			<td>
				<c:out value="${template.comTempName}" />
			</td>
			<td>
				<c:if test="${template.type == '1'}">
					<bean:message key="lbpmTemplate.fdType.default" bundle="sys-lbpmservice-support"/>
				</c:if>
				<c:if test="${template.type == '2' }">
					<bean:message key="lbpmTemplate.fdType.other" bundle="sys-lbpmservice-support"/>
				</c:if>
				<c:if test="${template.type == '3' }">
					<bean:message key="lbpmTemplate.fdType.define" bundle="sys-lbpmservice-support"/>
				</c:if>
			</td>
		</tr>
	</c:forEach>
</table>
</div>
</c:if>

</center>

<%@ include file="/resource/jsp/view_down.jsp"%>