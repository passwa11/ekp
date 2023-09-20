<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/jdbc/tic_jdbc_query/ticJdbcQuery.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/jdbc/tic_jdbc_query/ticJdbcQuery.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticJdbcQueryForm, 'deleteall');">
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
				<sunbor:column property="ticJdbcQuery.docSubject">
					<bean:message bundle="tic-jdbc" key="ticJdbcQuery.docSubject"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcQuery.docCreateTime">
					<bean:message bundle="tic-jdbc" key="ticJdbcQuery.docCreateTime"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcQuery.docCreator.fdName">
					<bean:message bundle="tic-jdbc" key="ticJdbcQuery.docCreator"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticJdbcQuery" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/jdbc/tic_jdbc_query/ticJdbcQuery.do" />?method=view&fdId=${ticJdbcQuery.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticJdbcQuery.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticJdbcQuery.docSubject}" />
				</td>
				<td>
					<kmss:showDate value="${ticJdbcQuery.docCreateTime}" />
				</td>
				<td>
					<c:out value="${ticJdbcQuery.docCreator.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
