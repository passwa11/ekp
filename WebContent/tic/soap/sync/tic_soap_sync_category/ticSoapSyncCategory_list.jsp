<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/soap/sync/tic_soap_sync_category/ticSoapSyncCategory.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/soap/sync/tic_soap_sync_category/ticSoapSyncCategory.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/soap/sync/tic_soap_sync_category/ticSoapSyncCategory.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/soap/sync/tic_soap_sync_category/ticSoapSyncCategory.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticSoapSyncCategoryForm, 'deleteall');">
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
				<sunbor:column property="ticSoapSyncCategory.fdName">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncCategory.fdName"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncCategory.fdOrder">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncCategory.fdOrder"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncCategory.fdHierarchyId">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncCategory.fdHierarchyId"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncCategory.fdParent.fdName">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncCategory.fdParent"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticSoapSyncCategory" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/soap/sync/tic_soap_sync_category/ticSoapSyncCategory.do" />?method=view&fdId=${ticSoapSyncCategory.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticSoapSyncCategory.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticSoapSyncCategory.fdName}" />
				</td>
				<td>
					<c:out value="${ticSoapSyncCategory.fdOrder}" />
				</td>
				<td>
					<c:out value="${ticSoapSyncCategory.fdHierarchyId}" />
				</td>
				<td>
					<c:out value="${ticSoapSyncCategory.fdParent.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>