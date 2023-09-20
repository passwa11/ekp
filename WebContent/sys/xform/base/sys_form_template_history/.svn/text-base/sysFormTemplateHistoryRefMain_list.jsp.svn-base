<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
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
				<td width="40pt"><bean:message key="page.serial" /></td>
				<td>
					<bean:message bundle="sys-xform" key="sysFormTemplate.history.subject"/>
				</td>
				<td>
					<bean:message bundle="sys-xform" key="sysFormTemplate.history.status"/>
				</td>
				<td>
					<bean:message bundle="sys-xform" key="sysFormTemplate.history.creator"/>
				</td>
				<td>
					<bean:message bundle="sys-xform" key="sysFormTemplate.history.creationTime"/>
				</td>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysFormTemplateHistoryRefMain"
			varStatus="vstatus">
			<tr
				kmss_href="<c:url value="${actionUrl}" />?method=view&fdId=${sysFormTemplateHistoryRefMain.fdId}">
				<td>${vstatus.index+1}</td>
				<td>
			        <span class="com_subject">${detailInfo[sysFormTemplateHistoryRefMain.fdId]['subject']}</span>
			    </td>
	        	<td>
					${detailInfo[sysFormTemplateHistoryRefMain.fdId]['docStatus']}
				</td>
				<td>
					${detailInfo[sysFormTemplateHistoryRefMain.fdId]['docCreator']}
				</td>
				<td>
					${detailInfo[sysFormTemplateHistoryRefMain.fdId]['docCreateTime']}
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp"%>
	<%
	}
	%>
</html:form>
<script type="text/javascript">
	 var updateWarning = "${updateWarning}";
</script>
<%@ include file="/resource/jsp/list_down.jsp"%>