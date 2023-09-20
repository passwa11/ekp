<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/jdbc/tic_jdbc_task_category/ticJdbcTaskCategory.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/jdbc/tic_jdbc_task_category/ticJdbcTaskCategory.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/jdbc/tic_jdbc_task_category/ticJdbcTaskCategory.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/jdbc/tic_jdbc_task_category/ticJdbcTaskCategory.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticJdbcTaskCategoryForm, 'deleteall');">
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
				<sunbor:column property="ticJdbcTaskCategory.fdName">
					<bean:message bundle="tic-jdbc" key="ticJdbcTaskCategory.fdName"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcTaskCategory.fdHierarchyId">
					<bean:message bundle="tic-jdbc" key="ticJdbcTaskCategory.fdHierarchyId"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcTaskCategory.fdOrder">
					<bean:message bundle="tic-jdbc" key="ticJdbcTaskCategory.fdOrder"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcTaskCategory.fdParent.fdName">
					<bean:message bundle="tic-jdbc" key="ticJdbcTaskCategory.fdParent"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticJdbcTaskCategory" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/jdbc/tic_jdbc_task_category/ticJdbcTaskCategory.do" />?method=view&fdId=${ticJdbcTaskCategory.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticJdbcTaskCategory.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticJdbcTaskCategory.fdName}" />
				</td>
				<td>
					<c:out value="${ticJdbcTaskCategory.fdHierarchyId}" />
				</td>
				<td>
					<c:out value="${ticJdbcTaskCategory.fdOrder}" />
				</td>
				<td>
					<c:out value="${ticJdbcTaskCategory.fdParent.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>