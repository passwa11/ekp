<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/soap/connector/tic_soap_setting/ticSoapSetting.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/soap/connector/tic_soap_setting/ticSoapSetting.do?method=add&fdAppType=${param.fdAppType}">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/soap/connector/tic_soap_setting/ticSoapSetting.do" />?method=add&categoryId=${param.categoryId}&fdAppType=${param.fdAppType}');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/soap/connector/tic_soap_setting/ticSoapSetting.do?method=deleteall&fdAppType=${param.fdAppType}">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticSoapSettingForm, 'deleteall');">
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
				<sunbor:column property="ticSoapSetting.docSubject">
					<bean:message bundle="tic-soap-connector" key="ticSoapSetting.docSubject"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSetting.fdWsdlUrl">
					<bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdWsdlUrl"/>
				</sunbor:column>
				<%-- <sunbor:column property="ticSoapSetting.fdSoapVerson">
					<bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdSoapVerson"/>
				</sunbor:column> --%>
				<sunbor:column property="ticSoapSetting.fdProtectWsdl">
					<bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdProtectWsdl"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSetting.fdCheck">
					<bean:message bundle="tic-soap-connector" key="ticSoapSetting.fdCheck"/>
				</sunbor:column>
				
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticSoapSetting" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/soap/connector/tic_soap_setting/ticSoapSetting.do" />?method=view&fdId=${ticSoapSetting.fdId}&fdAppType=${param.fdAppType}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticSoapSetting.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticSoapSetting.docSubject}" />
				</td>
				<td>
					<c:out value="${ticSoapSetting.fdWsdlUrl}" />
				</td>
				<%-- <td>
					<c:out value="${ticSoapSetting.fdSoapVerson}" />
				</td> --%>
				<td>
					<xform:select value="${ticSoapSetting.fdProtectWsdl}" property="fdProtectWsdl" showStatus="view">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:select>
				</td>
				<td>
					<xform:select value="${ticSoapSetting.fdCheck}" property="fdCheck" showStatus="view">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:select>
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
