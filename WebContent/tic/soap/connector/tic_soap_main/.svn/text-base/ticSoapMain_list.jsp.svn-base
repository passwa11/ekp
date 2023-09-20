<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/soap/connector/tic_soap_main/ticSoapMain.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/soap/connector/tic_soap_main/ticSoapMain.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/soap/connector/tic_soap_main/ticSoapMain.do" />?method=add&categoryId=${param.categoryId}');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/soap/connector/tic_soap_main/ticSoapMain.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticSoapMainForm, 'deleteall');">
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
				<sunbor:column property="ticSoapMain.fdName">
					<bean:message bundle="tic-soap-connector" key="ticSoapMain.func.docSubject"/>
				</sunbor:column>
				<%-- <sunbor:column property="ticSoapMain.docStatus">
					<bean:message bundle="tic-soap-connector" key="ticSoapMain.docStatus"/>
				</sunbor:column>
				<sunbor:column property="ticSoapMain.docCreateTime">
					<bean:message bundle="tic-soap-connector" key="ticSoapMain.docCreateTime"/>
				</sunbor:column>
				<sunbor:column property="ticSoapMain.docAlterTime">
					<bean:message bundle="tic-soap-connector" key="ticSoapMain.docAlterTime"/>
				</sunbor:column> --%>
				<sunbor:column property="ticSoapMain.wsBindFunc">
					<bean:message bundle="tic-soap-connector" key="ticSoapMain.wsBindFunc"/>
				</sunbor:column>
				<sunbor:column property="ticSoapMain.fdIsAvailable">
					<bean:message bundle="tic-soap-connector" key="ticSoapMain.wsEnable"/>
				</sunbor:column>
				<%-- <sunbor:column property="ticSoapMain.wsSoapVersion">
					<bean:message bundle="tic-soap-connector" key="ticSoapMain.wsSoapVersion"/>
				</sunbor:column> --%>
				
				<%-- <sunbor:column property="ticSoapMain.wsMarks">
					<bean:message bundle="tic-soap-connector" key="ticSoapMain.wsMarks"/>
				</sunbor:column>
				<sunbor:column property="ticSoapMain.wsBindFuncInfo">
					<bean:message bundle="tic-soap-connector" key="ticSoapMain.wsBindFuncInfo"/>
				</sunbor:column> --%>
				<sunbor:column property="ticSoapMain.ticSoapSetting.docSubject">
					<bean:message bundle="tic-soap-connector" key="ticSoapMain.wsServerSetting"/>
				</sunbor:column>
				<sunbor:column property="ticSoapMain.fdCategory.fdName">
					<bean:message bundle="tic-soap-connector" key="ticSoapMain.docCategory"/>
				</sunbor:column>
				
				<%-- <sunbor:column property="ticSoapMain.docCreator.fdName">
					<bean:message bundle="tic-soap-connector" key="ticSoapMain.docCreator"/>
				</sunbor:column> --%>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticSoapMain" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/soap/connector/tic_soap_main/ticSoapMain.do" />?method=view&fdId=${ticSoapMain.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticSoapMain.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticSoapMain.fdName}" />
				</td>
				<%-- <td>
					<c:out value="${ticSoapMain.docStatus}" />
				</td>
				<td>
					<kmss:showDate value="${ticSoapMain.docCreateTime}" />
				</td>
				<td>
					<kmss:showDate value="${ticSoapMain.docAlterTime}" />
				</td> --%>
				<td>
					<c:out value="${ticSoapMain.wsBindFunc}" />
				</td>
				<%-- <td>
					<c:out value="${ticSoapMain.wsSoapVersion}" />
				</td> --%>
				
				<%-- <td>
					<c:out value="${ticSoapMain.wsMarks}" />
				</td>
				<td>
					<c:out value="${ticSoapMain.wsBindFuncInfo}" />
				</td>--%>
				<td>
					<c:out value="${ticSoapMain.ticSoapSetting.docSubject}" />
				</td>
				<td> 
					<c:out value="${ticSoapMain.fdCategory.fdName}" />
				</td>
				
				<%-- <td>
					<c:out value="${ticSoapMain.docCreator.fdName}" />
				</td> --%>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
