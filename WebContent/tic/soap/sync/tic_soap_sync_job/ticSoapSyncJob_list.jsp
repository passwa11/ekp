<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/soap/sync/tic_soap_sync_job/ticSoapSyncJob.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/soap/sync/tic_soap_sync_job/ticSoapSyncJob.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/soap/sync/tic_soap_sync_job/ticSoapSyncJob.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/soap/sync/tic_soap_sync_job/ticSoapSyncJob.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticSoapSyncJobForm, 'deleteall');">
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
				<sunbor:column property="ticSoapSyncJob.fdModelName">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdModelName"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncJob.fdModelId">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdModelId"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncJob.fdKey">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdKey"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncJob.fdSubject">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdSubject"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncJob.fdJobService">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdJobService"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncJob.fdJobMethod">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdJobMethod"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncJob.fdLink">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdLink"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncJob.fdParameter">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdParameter"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncJob.fdCronExpression">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdCronExpression"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncJob.fdEnabled">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdEnabled"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncJob.fdIsSysJob">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdIsSysJob"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncJob.fdRunType">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdRunType"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncJob.fdRunTime">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdRunTime"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncJob.fdRequired">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdRequired"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncJob.fdTriggered">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdTriggered"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncJob.fdQuartzEkp">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdQuartzEkp"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncJob.fdUseExplain">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdUseExplain"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncJob.fdParentId">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdParentId"/>
				</sunbor:column>
				<sunbor:column property="ticSoapSyncJob.docCategory.fdName">
					<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.docCategory"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticSoapSyncJob" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/soap/sync/tic_soap_sync_job/ticSoapSyncJob.do" />?method=view&fdId=${ticSoapSyncJob.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticSoapSyncJob.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticSoapSyncJob.fdModelName}" />
				</td>
				<td>
					<c:out value="${ticSoapSyncJob.fdModelId}" />
				</td>
				<td>
					<c:out value="${ticSoapSyncJob.fdKey}" />
				</td>
				<td>
					<c:out value="${ticSoapSyncJob.fdSubject}" />
				</td>
				<td>
					<c:out value="${ticSoapSyncJob.fdJobService}" />
				</td>
				<td>
					<c:out value="${ticSoapSyncJob.fdJobMethod}" />
				</td>
				<td>
					<c:out value="${ticSoapSyncJob.fdLink}" />
				</td>
				<td>
					<c:out value="${ticSoapSyncJob.fdParameter}" />
				</td>
				<td>
					<c:out value="${ticSoapSyncJob.fdCronExpression}" />
				</td>
				<td>
					<xform:radio value="${ticSoapSyncJob.fdEnabled}" property="fdEnabled" showStatus="view">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:radio>
				</td>
				<td>
					<xform:radio value="${ticSoapSyncJob.fdIsSysJob}" property="fdIsSysJob" showStatus="view">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:radio>
				</td>
				<td>
					<c:out value="${ticSoapSyncJob.fdRunType}" />
				</td>
				<td>
					<kmss:showDate value="${ticSoapSyncJob.fdRunTime}" />
				</td>
				<td>
					<xform:radio value="${ticSoapSyncJob.fdRequired}" property="fdRequired" showStatus="view">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:radio>
				</td>
				<td>
					<xform:radio value="${ticSoapSyncJob.fdTriggered}" property="fdTriggered" showStatus="view">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:radio>
				</td>
				<td>
					<c:out value="${ticSoapSyncJob.fdQuartzEkp}" />
				</td>
				<td>
					<c:out value="${ticSoapSyncJob.fdUseExplain}" />
				</td>
				<td>
					<c:out value="${ticSoapSyncJob.fdParentId}" />
				</td>
				<td>
					<c:out value="${ticSoapSyncJob.docCategory.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>