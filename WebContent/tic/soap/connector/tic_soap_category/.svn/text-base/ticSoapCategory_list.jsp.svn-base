<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/soap/connector/tic_soap_category/ticSoapCategory.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/soap/connector/tic_soap_category/ticSoapCategory.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/soap/connector/tic_soap_category/ticSoapCategory.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/soap/connector/tic_soap_category/ticSoapCategory.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticSoapCategoryForm, 'deleteall');">
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
				<sunbor:column property="ticSoapCategory.fdName">
					<bean:message bundle="tic-soap-connector" key="ticSoapCategory.fdName"/>
				</sunbor:column>
				<sunbor:column property="ticSoapCategory.fdOrder">
					<bean:message bundle="tic-soap-connector" key="ticSoapCategory.fdOrder"/>
				</sunbor:column>
				<sunbor:column property="ticSoapCategory.fdParent.fdName">
					<bean:message bundle="tic-soap-connector" key="ticSoapCategory.fdParent"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticSoapCategory" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/soap/connector/tic_soap_category/ticSoapCategory.do" />?method=view&fdId=${ticSoapCategory.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticSoapCategory.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticSoapCategory.fdName}" />
				</td>
				<td>
					<c:out value="${ticSoapCategory.fdOrder}" />
				</td>
				<td>
					<c:out value="${ticSoapCategory.fdParent.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
