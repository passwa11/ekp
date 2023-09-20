<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<script type="text/javascript">
	Com_AddEventListener(
			window,
			'load',
			function() {
				setTimeout(
						function() {
							window.frameElement.style.height = (document.forms[0].offsetHeight + 70)+"px";
						}, 200);
			});
</script>
<html:form action="${actionUrl}">
	<%
	if (((Page) request.getAttribute("queryPage")).getTotalrows() == 0) {
	%>
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
	<%
	} else {
	%>
	
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
				<td width="40pt"><bean:message key="page.serial" /></td>

				<sunbor:column property="sysFormTemplate.fdId">
					<bean:message bundle="sys-xform" key="sysFormTemplate.fdName" />
				</sunbor:column>

				<sunbor:column property="sysFormTemplate.fdCreator">
					<bean:message bundle="sys-xform"
						key="sysFormTemplate.fdCreatorId" />
				</sunbor:column>
				<sunbor:column property="sysFormTemplate.fdCreateTime">
					<bean:message bundle="sys-xform"
						key="sysFormTemplate.fdCreateTime" />
				</sunbor:column>

			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysFormTemplate"
			varStatus="vstatus">
			<tr
				kmss_href="<c:url value="${actionUrl}" />?method=view&fdId=${sysFormTemplate.fdModelId}">
				<td>${vstatus.index+1}</td>
				<td><c:if test="${not empty subjectMap[sysFormTemplate.fdId]}">
	             <span class="com_subject">${subjectMap[sysFormTemplate.fdId]}</span>
	        </c:if></td>
				<td><c:out value="${sysFormTemplate.fdCreator.fdName}" /></td>
				<td><kmss:showDate value="${sysFormTemplate.fdCreateTime}" type="date"/></td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp"%>
	<%
	}
	%>
</html:form>

<%@ include file="/resource/jsp/list_down.jsp"%>
