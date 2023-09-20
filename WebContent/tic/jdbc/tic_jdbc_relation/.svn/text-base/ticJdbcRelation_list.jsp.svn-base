<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/jdbc/tic_jdbc_relation/ticJdbcRelation.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/jdbc/tic_jdbc_relation/ticJdbcRelation.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/jdbc/tic_jdbc_relation/ticJdbcRelation.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/jdbc/tic_jdbc_relation/ticJdbcRelation.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticJdbcRelationForm, 'deleteall');">
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
				<sunbor:column property="ticJdbcRelation.fdUseExplain">
					<bean:message bundle="tic-jdbc" key="ticJdbcRelation.fdUseExplain"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcRelation.fdSyncType">
					<bean:message bundle="tic-jdbc" key="ticJdbcRelation.fdSyncType"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcRelation.ticJdbcMappCategory.docSubject">
					<bean:message bundle="tic-jdbc" key="ticJdbcRelation.ticJdbcMappCategory"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcRelation.ticJdbcTaskManage.fdId">
					<bean:message bundle="tic-jdbc" key="ticJdbcRelation.ticJdbcTaskManage"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticJdbcRelation" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/jdbc/tic_jdbc_relation/ticJdbcRelation.do" />?method=view&fdId=${ticJdbcRelation.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticJdbcRelation.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticJdbcRelation.fdUseExplain}" />
				</td>
				<td>
					<c:out value="${ticJdbcRelation.fdSyncType}" />
				</td>
				<td>
					<c:out value="${ticJdbcRelation.ticJdbcMappCategory.docSubject}" />
				</td>
				<td>
					<c:out value="${ticJdbcRelation.ticJdbcTaskManage.fdId}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>