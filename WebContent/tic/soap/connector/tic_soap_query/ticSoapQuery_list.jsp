<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/soap/connector/tic_soap_query/ticSoapQuery.do">
	<div id="optBarDiv">
		<%-- <kmss:auth requestURL="/tic/soap/connector/tic_soap_query/ticSoapQuery.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/soap/connector/tic_soap_query/ticSoapQuery.do" />?method=add');">
		</kmss:auth> --%>
		<kmss:auth requestURL="/tic/soap/connector/tic_soap_query/ticSoapQuery.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticSoapQueryForm, 'deleteall');">
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
				<sunbor:column property="ticSoapQuery.docSubject">
					<bean:message bundle="tic-soap-connector" key="ticSoapQuery.docSubject"/>
				</sunbor:column>
				<sunbor:column property="ticSoapQuery.docCreateTime">
					<bean:message bundle="tic-soap-connector" key="ticSoapQuery.docCreateTime"/>
				</sunbor:column>
				<sunbor:column property="ticSoapQuery.docCreator.fdName">
					<bean:message bundle="tic-soap-connector" key="ticSoapQuery.docCreator"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticSoapQuery" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/soap/connector/tic_soap_query/ticSoapQuery.do" />?method=view&fdId=${ticSoapQuery.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticSoapQuery.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticSoapQuery.docSubject}" />
				</td>
				<td>
					<kmss:showDate value="${ticSoapQuery.docCreateTime}" />
				</td>
				<td>
					<c:out value="${ticSoapQuery.docCreator.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
