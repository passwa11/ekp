<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/soap/sync/tic_soap_sync_temp_func/ticSoapSyncTempFunc.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/soap/sync/tic_soap_sync_temp_func/ticSoapSyncTempFunc.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/soap/sync/tic_soap_sync_temp_func/ticSoapSyncTempFunc.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/soap/sync/tic_soap_sync_temp_func/ticSoapSyncTempFunc.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticSoapSyncTempFuncForm, 'deleteall');">
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
				<sunbor:column property="ticSoapSyncTempFunc.fdInvokeType">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncTempFunc.fdInvokeType"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncTempFunc.fdFuncMark">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncTempFunc.fdFuncMark"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncTempFunc.fdUse">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncTempFunc.fdUse"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncTempFunc.fdQuartzTime">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncTempFunc.fdQuartzTime"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncTempFunc.fdQuartz.fdId">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncTempFunc.fdQuartz"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncTempFunc.fdSoapMain.docSubject">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncTempFunc.fdSoapMain"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticSoapSyncTempFunc" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/soap/sync/tic_soap_sync_temp_func/ticSoapSyncTempFunc.do" />?method=view&fdId=${ticSoapSyncTempFunc.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticSoapSyncTempFunc.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticSoapSyncTempFunc.fdInvokeType}" />
				</td>
				<td>
					<c:out value="${ticSoapSyncTempFunc.fdFuncMark}" />
				</td>
				<td>
					<xform:radio value="${ticSoapSyncTempFunc.fdUse}" property="fdUse" showStatus="view">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:radio>
				</td>
				<td>
					<kmss:showDate value="${ticSoapSyncTempFunc.fdQuartzTime}" />
				</td>
				<td>
					<c:out value="${ticSoapSyncTempFunc.fdQuartz.fdId}" />
				</td>
				<td>
					<c:out value="${ticSoapSyncTempFunc.fdSoapMain.docSubject}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>