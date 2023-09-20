<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/rest/connector/tic_rest_main/ticRestMain.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/rest/connector/tic_rest_main/ticRestMain.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/rest/connector/tic_rest_main/ticRestMain.do" />?method=add&categoryId=${param.categoryId}');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/rest/connector/tic_rest_main/ticRestMain.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticRestMainForm, 'deleteall');">
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
				<sunbor:column property="ticRestMain.fdName">
					<bean:message bundle="tic-rest-connector" key="ticRestMain.func.docSubject"/>
				</sunbor:column>
				<sunbor:column property="ticRestMain.ticRestSetting.fdName">
					<bean:message bundle="tic-rest-connector" key="ticRestMain.fdServerSetting"/>
				</sunbor:column>
				<sunbor:column property="ticRestMain.fdCategory.fdName">
					<bean:message bundle="tic-rest-connector" key="ticRestMain.docCategory"/>
				</sunbor:column>
				<sunbor:column property="ticRestMain.fdIsAvailable">
					<bean:message bundle="tic-rest-connector" key="ticRestMain.fdIsAvailable"/>
				</sunbor:column>
				
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticRestMain" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/rest/connector/tic_rest_main/ticRestMain.do" />?method=view&fdId=${ticRestMain.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticRestMain.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticRestMain.fdName}" />
				</td>
				<td>
					<c:out value="${ticRestMain.ticRestSetting.fdName}" />
				</td>
				<td> 
					<c:out value="${ticRestMain.fdCategory.fdName}" />
				</td>
				<td>
					<c:out value="${ticRestMain.fdIsAvailable}" />
				</td>
				
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
