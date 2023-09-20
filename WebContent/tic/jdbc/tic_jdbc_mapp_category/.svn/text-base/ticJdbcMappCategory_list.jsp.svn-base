<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/jdbc/tic_jdbc_mapp_category/ticJdbcMappCategory.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/jdbc/tic_jdbc_mapp_category/ticJdbcMappCategory.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/jdbc/tic_jdbc_mapp_category/ticJdbcMappCategory.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/jdbc/tic_jdbc_mapp_category/ticJdbcMappCategory.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticJdbcMappCategoryForm, 'deleteall');">
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
				<sunbor:column property="ticJdbcMappCategory.fdName">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappCategory.fdName"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcMappCategory.fdHierarchyId">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappCategory.fdHierarchyId"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcMappCategory.fdOrder">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappCategory.fdOrder"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcMappCategory.fdParent.fdName">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappCategory.fdParent"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticJdbcMappCategory" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/jdbc/tic_jdbc_mapp_category/ticJdbcMappCategory.do" />?method=view&fdId=${ticJdbcMappCategory.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticJdbcMappCategory.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticJdbcMappCategory.fdName}" />
				</td>
				<td>
					<c:out value="${ticJdbcMappCategory.fdHierarchyId}" />
				</td>
				<td>
					<c:out value="${ticJdbcMappCategory.fdOrder}" />
				</td>
				<td>
					<c:out value="${ticJdbcMappCategory.fdParent.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>