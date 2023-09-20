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
<html:form action="/tic/core/log/tic_core_log_manage/ticCoreLogManage.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/core/log/tic_core_log_manage/ticCoreLogManage.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/core/log/tic_core_log_manage/ticCoreLogManage.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/core/log/tic_core_log_manage/ticCoreLogManage.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticCoreLogManageForm, 'deleteall');">
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
				<sunbor:column property="ticCoreLogManage.fdLogTime">
					<bean:message bundle="tic-core-log" key="ticCoreLogManage.fdLogTime"/>
				</sunbor:column>
				<sunbor:column property="ticCoreLogManage.fdLogLastTime">
					<bean:message bundle="tic-core-log" key="ticCoreLogManage.fdLogLastTime"/>
				</sunbor:column>
				<sunbor:column property="ticCoreLogManage.fdLogType">
					<bean:message bundle="tic-core-log" key="ticCoreLogManage.fdLogType"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticCoreLogManage" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/core/log/tic_core_log_manage/ticCoreLogManage.do" />?method=view&fdId=${ticCoreLogManage.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticCoreLogManage.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<kmss:showDate value="${ticCoreLogManage.fdLogTime}" />
				</td>
				<td>
					<kmss:showDate value="${ticCoreLogManage.fdLogLastTime}" />
				</td>
				<td>
					<c:out value="${ticCoreLogManage.fdLogType}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>