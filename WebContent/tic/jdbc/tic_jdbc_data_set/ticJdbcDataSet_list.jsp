<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/jdbc/tic_jdbc_data_set/ticJdbcDataSet.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/jdbc/tic_jdbc_data_set/ticJdbcDataSet.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/jdbc/tic_jdbc_data_set/ticJdbcDataSet.do" />?method=add&fdAppType=${JsParam.fdAppType}');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/jdbc/tic_jdbc_data_set/ticJdbcDataSet.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticJdbcDataSetForm, 'deleteall');">
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
				<sunbor:column property="ticJdbcDataSet.docSubject">
					<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.docSubject"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcDataSet.fdDataSource">
					<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.fdDataSource"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcDataSet.fdSqlExpression">
					<bean:message bundle="tic-jdbc" key="ticJdbcDataSet.fdSqlExpression"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticJdbcDataSet" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/jdbc/tic_jdbc_data_set/ticJdbcDataSet.do" />?method=view&fdId=${ticJdbcDataSet.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticJdbcDataSet.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticJdbcDataSet.fdName}" />
				</td>
				<td>
					<c:out value="${ticJdbcDataSet.fdDataSource}" />
				</td>
				<td>
					<c:out value="${ticJdbcDataSet.fdSqlExpression}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>