<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/jdbc/tic_jdbc_data_set_category/ticJdbcDataSetCategory.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/jdbc/tic_jdbc_data_set_category/ticJdbcDataSetCategory.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/jdbc/tic_jdbc_data_set_category/ticJdbcDataSetCategory.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/jdbc/tic_jdbc_data_set_category/ticJdbcDataSetCategory.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticJdbcDataSetCategoryForm, 'deleteall');">
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
				<sunbor:column property="ticJdbcDataSetCategory.fdName">
					<bean:message bundle="tic-jdbc" key="ticJdbcDataSetCategory.fdName"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcDataSetCategory.fdHierarchyId">
					<bean:message bundle="tic-jdbc" key="ticJdbcDataSetCategory.fdHierarchyId"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcDataSetCategory.fdOrder">
					<bean:message bundle="tic-jdbc" key="ticJdbcDataSetCategory.fdOrder"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcDataSetCategory.fdParent.fdName">
					<bean:message bundle="tic-jdbc" key="ticJdbcDataSetCategory.fdParent"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticJdbcDataSetCategory" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/jdbc/tic_jdbc_data_set_category/ticJdbcDataSetCategory.do" />?method=view&fdId=${ticJdbcDataSetCategory.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticJdbcDataSetCategory.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticJdbcDataSetCategory.fdName}" />
				</td>
				<td>
					<c:out value="${ticJdbcDataSetCategory.fdHierarchyId}" />
				</td>
				<td>
					<c:out value="${ticJdbcDataSetCategory.fdOrder}" />
				</td>
				<td>
					<c:out value="${ticJdbcDataSetCategory.fdParent.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>