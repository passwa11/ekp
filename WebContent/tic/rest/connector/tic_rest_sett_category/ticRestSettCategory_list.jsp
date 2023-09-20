<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/rest/connector/tic_rest_sett_category/ticRestSettCategory.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/rest/connector/tic_rest_sett_category/ticRestSettCategory.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/rest/connector/tic_rest_sett_category/ticRestSettCategory.do" />?method=add&categoryId=${param.categoryId}');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/rest/connector/tic_rest_sett_category/ticRestSettCategory.do?method=deleteall">
			<input type="button" value="<bean:message key="button.delete"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticRestSettCategoryForm, 'deleteall');">
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
				<sunbor:column property="ticRestSettCategory.fdName">
					<bean:message bundle="tic-rest-connector" key="ticRestSettCategory.fdName"/>
				</sunbor:column>
				
				<sunbor:column property="ticRestSettCategory.fdParent.fdName">
					<bean:message bundle="tic-rest-connector" key="ticRestSettCategory.fdParent"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticRestSettCategory" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/rest/connector/tic_rest_sett_category/ticRestSettCategory.do" />?method=view&fdId=${ticRestSettCategory.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticRestSettCategory.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticRestSettCategory.fdName}" />
				</td>
				<!-- 
				<td>
					<c:out value="${ticRestSettCategory.fdHierarchyId}" />
				</td>
			    -->	
				<td>
					<c:out value="${ticRestSettCategory.fdParent.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>