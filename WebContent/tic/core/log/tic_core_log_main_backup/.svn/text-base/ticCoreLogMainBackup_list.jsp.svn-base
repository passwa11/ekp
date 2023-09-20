<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/core/log/tic_core_log_main_backup/ticCoreLogMainBackup.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/core/log/tic_core_log_main_backup/ticCoreLogMainBackup.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/core/log/tic_core_log_main_backup/ticCoreLogMainBackup.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/core/log/tic_core_log_main_backup/ticCoreLogMainBackup.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticCoreLogMainBackupForm, 'deleteall');">
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
				<sunbor:column property="ticCoreLogMainBackup.fdType">
					<bean:message bundle="tic-core-log" key="ticCoreLogMainBackup.fdType"/>
				</sunbor:column>
				<sunbor:column property="ticCoreLogMainBackup.fdUrl">
					<bean:message bundle="tic-core-log" key="ticCoreLogMainBackup.fdUrl"/>
				</sunbor:column>
				<sunbor:column property="ticCoreLogMainBackup.fdPoolName">
					<bean:message bundle="tic-core-log" key="ticCoreLogMainBackup.fdPoolName"/>
				</sunbor:column>
				<sunbor:column property="ticCoreLogMainBackup.fdStartTime">
					<bean:message bundle="tic-core-log" key="ticCoreLogMainBackup.fdStartTime"/>
				</sunbor:column>
				<sunbor:column property="ticCoreLogMainBackup.fdEndTime">
					<bean:message bundle="tic-core-log" key="ticCoreLogMainBackup.fdEndTime"/>
				</sunbor:column>
				<sunbor:column property="ticCoreLogMainBackup.fdMessages">
					<bean:message bundle="tic-core-log" key="ticCoreLogMainBackup.fdMessages"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticCoreLogMainBackup" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/core/log/tic_core_log_main_backup/ticCoreLogMainBackup.do" />?method=view&fdId=${ticCoreLogMainBackup.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticCoreLogMainBackup.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticCoreLogMainBackup.fdType}" />
				</td>
				<td>
					<c:out value="${ticCoreLogMainBackup.fdUrl}" />
				</td>
				<td>
					<c:out value="${ticCoreLogMainBackup.fdPoolName}" />
				</td>
				<td>
					<kmss:showDate value="${ticCoreLogMainBackup.fdStartTime}" />
				</td>
				<td>
					<kmss:showDate value="${ticCoreLogMainBackup.fdEndTime}" />
				</td>
				<td>
					<c:out value="${ticCoreLogMainBackup.fdMessages}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>