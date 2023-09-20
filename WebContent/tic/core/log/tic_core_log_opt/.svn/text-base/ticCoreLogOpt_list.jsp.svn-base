<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<script>
Com_AddEventListener(window,'load',function(){
	var newForm = document.forms[0];
	if('autocomplete' in newForm)
		newForm.autocomplete = "off";
	else
		newForm.setAttribute("autocomplete","off");
});
</script>
<html:form action="/tic/core/log/tic_core_log_opt/ticCoreLogOpt.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/core/log/tic_core_log_opt/ticCoreLogOpt.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticCoreLogOptForm, 'deleteall');">
		</kmss:auth>
	</div>
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
				<td width="10pt">
					<input type="checkbox" name="List_Tongle">
				</td>
				<td width="40pt">
					<bean:message key="page.serial"/>
				</td>
				<sunbor:column property="ticCoreLogOpt.fdPerson">
					<bean:message bundle="tic-core-log" key="ticCoreLogOpt.fdPerson"/>
				</sunbor:column>
				<sunbor:column property="ticCoreLogOpt.fdAlertTime">
					<bean:message bundle="tic-core-log" key="ticCoreLogOpt.fdAlertTime"/>
				</sunbor:column>
				<sunbor:column property="ticCoreLogOpt.fdUrl">
					<bean:message bundle="tic-core-log" key="ticCoreLogOpt.fdUrl"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticCoreLogOpt" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/core/log/tic_core_log_opt/ticCoreLogOpt.do" />?method=view&fdId=${ticCoreLogOpt.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticCoreLogOpt.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticCoreLogOpt.fdPerson}" />
				</td>
				<td>
					<kmss:showDate value="${ticCoreLogOpt.fdAlertTime}" />
				</td>
				<td>
					<c:out value="${ticCoreLogOpt.fdUrl}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>