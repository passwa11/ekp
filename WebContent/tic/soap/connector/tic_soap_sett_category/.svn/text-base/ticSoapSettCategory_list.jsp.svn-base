<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/soap/connector/tic_soap_sett_category/ticSoapSettCategory.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/soap/connector/tic_soap_sett_category/ticSoapSettCategory.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/soap/connector/tic_soap_sett_category/ticSoapSettCategory.do" />?method=add&categoryId=${param.categoryId}');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/soap/connector/tic_soap_sett_category/ticSoapSettCategory.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticSoapSettCategoryForm, 'deleteall');">
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
				<sunbor:column property="ticSoapSettCategory.fdName">
					<bean:message bundle="tic-soap-connector" key="ticSoapSettCategory.fdName"/>
				</sunbor:column>
				
				<sunbor:column property="ticSoapSettCategory.fdParent.fdName">
					<bean:message bundle="tic-soap-connector" key="ticSoapSettCategory.fdParent"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticSoapSettCategory" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/soap/connector/tic_soap_sett_category/ticSoapSettCategory.do" />?method=view&fdId=${ticSoapSettCategory.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticSoapSettCategory.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticSoapSettCategory.fdName}" />
				</td>
				<!-- 
				<td>
					<c:out value="${ticSoapSettCategory.fdHierarchyId}" />
				</td>
			    -->	
				<td>
					<c:out value="${ticSoapSettCategory.fdParent.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>