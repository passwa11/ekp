<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/rest/connector/tic_rest_query/ticRestQuery.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/rest/connector/tic_rest_query/ticRestQuery.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticRestQueryForm, 'deleteall');">
		</kmss:auth>
	</div>
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<table id="List_ViewTable">
		<tr>
				<td width="10pt">
					<input type="checkbox" name="List_Tongle">
				</td>
				<td width="40pt">
					<bean:message key="page.serial"/>
				</td>
				<td>
					<bean:message bundle="tic-rest-connector" key="ticRestQuery.docSubject"/>
				</td>
				<td>
					<bean:message bundle="tic-rest-connector" key="ticRestQuery.docCreateTime"/>
				</td>
				<td>
					<bean:message bundle="tic-rest-connector" key="ticRestQuery.docCreator"/>
				</td>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticRestQuery" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/rest/connector/tic_rest_query/ticRestQuery.do" />?method=view&fdId=${ticRestQuery.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticRestQuery.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticRestQuery.docSubject}" />
				</td>
				<td>
					<kmss:showDate value="${ticRestQuery.docCreateTime}" />
				</td>
				<td>
					<c:out value="${ticRestQuery.docCreator.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
