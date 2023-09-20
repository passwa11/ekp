<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ page import="com.landray.kmss.tic.soap.sync.forms.TicSoapSyncJobForm,
	com.landray.kmss.sys.quartz.scheduler.CronExpression,
	java.util.Date,
	com.landray.kmss.util.DateUtil" %>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%
	TicSoapSyncJobForm ticSoapSyncJobForm = (TicSoapSyncJobForm)request.getAttribute("ticSoapSyncJobForm");
%>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
<div id="optBarDiv">
	<input type="button"
			value="<bean:message key="ticSoapSyncJob.dataList" bundle="tic-soap-sync"/>"
			onclick="Com_OpenWindow('ticSoapSyncJob.do?method=getXMLTable&quartzId=${ticSoapSyncJobForm.fdId}');">
	<c:if test="${not empty ticSoapSyncJobForm.fdQuartzEkp}">
		 <input type="button" value="<bean:message bundle="sys-quartz" key="sysQuartzJob.button.run"/>"
		onClick="Com_OpenWindow('<c:url value="/tic/soap/sync/tic_soap_sync_job/ticSoapSyncJob.do"/>?method=run&fdId=${ticSoapSyncJobForm.fdQuartzEkp}','_self');">
	</c:if>

	<kmss:auth requestURL="/tic/soap/sync/tic_soap_sync_job/ticSoapSyncJob.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('ticSoapSyncJob.do?method=edit&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/tic/soap/sync/tic_soap_sync_job/ticSoapSyncJob.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('ticSoapSyncJob.do?method=delete&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-soap-sync" key="table.ticSoapSyncJob"/></p>

<center>

<table
		id="Label_Tabel"
		width=95%>
		<tr LKS_LabelName="${lfn:message('tic-soap-sync:ticSoapSyncJob.fdSoapMsg')}">
			<td>
			
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdSubject"/>
		</td><td>
			<c:if test="${ticSoapSyncJobForm.fdIsSysJob=='true'}">
				<kmss:message key="${ticSoapSyncJobForm.fdSubject}"/>
			</c:if>
			<c:if test="${ticSoapSyncJobForm.fdIsSysJob!='true'}">
				${ticSoapSyncJobForm.fdSubject} 
			</c:if>
		</td>
		
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.docCategory"/>
		</td><td width="35%">
			<c:out value="${ticSoapSyncJobForm.docCategoryName}" />
		</td>
		
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdCronExpression"/>
		</td><td width=35%>
			<c:import url="/sys/quartz/sys_quartz_job/sysQuartzJob_showCronExpression.jsp" charEncoding="UTF-8">
				<c:param name="value" value="${ticSoapSyncJobForm.fdCronExpression}" />
			</c:import>
		</td>
		<td width=15% class="td_normal_title">
			<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.nextTime"/>
		</td><td width=35%>
			<c:if test="${ticSoapSyncJobForm.fdEnabled=='true'}">
			<%
				CronExpression expression = new CronExpression(ticSoapSyncJobForm.getFdCronExpression());
				Date nxtTime = expression.getNextValidTimeAfter(new Date());
				if(nxtTime!=null)
					out.write(DateUtil.convertDateToString(nxtTime,DateUtil.TYPE_DATETIME, request.getLocale()));
			%>
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdJobType"/>
		</td><td>
			<sunbor:enumsShow value="${(empty ticSoapSyncJobForm.fdRunTime)?'false':'true'}" enumsType="common_yesno" />
		</td>
		<td class="td_normal_title">
			<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdRunType"/>
		</td><td>
			<sunbor:enumsShow value="${ticSoapSyncJobForm.fdRunType}" enumsType="sysQuartzJob_fdRunType" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdEnabled"/>
		</td><td>
			<sunbor:enumsShow value="${ticSoapSyncJobForm.fdEnabled}" enumsType="common_yesno" />
		</td>
		<td class="td_normal_title">
			<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdIsSysJob"/>
		</td><td>
			<sunbor:enumsShow value="${ticSoapSyncJobForm.fdIsSysJob}" enumsType="common_yesno" />
		</td>
	</tr>
	<c:if test="${not empty ticSoapSyncJobForm.fdRunTime}">
		<tr>
			<td class="td_normal_title">
				<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdRequired"/>
			</td><td>
				<sunbor:enumsShow value="${ticSoapSyncJobForm.fdRequired}" enumsType="common_yesno" />
			</td>
			<td class="td_normal_title">
				<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdTriggered"/>
			</td><td>
				<sunbor:enumsShow value="${ticSoapSyncJobForm.fdTriggered}" enumsType="common_yesno" />
			</td>
		</tr>
	</c:if>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdJob"/>
		</td><td colspan="3">
			${ticSoapSyncJobForm.fdJobService}.${ticSoapSyncJobForm.fdJobMethod}(${ticSoapSyncJobForm.fdParameter})
		</td>
	</tr>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdLink"/>
		</td><td colspan="3">
			<c:if test="${ticSoapSyncJobForm.fdLink!=null && ticSoapSyncJobForm.fdLink!=''}">
				<a href="<c:url value="${ticSoapSyncJobForm.fdLink}" />" target="_blank">
					<c:out value="${ticSoapSyncJobForm.fdUseExplain}"/>
				</a>
			</c:if>
		</td>
	</tr>
		<tr>
		<td class="td_normal_title">
			<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdUseExplain"/>
		</td><td colspan="3">
		${ticSoapSyncJobForm.fdUseExplain}
		</td>
	</tr>
</table>
</td>
</tr>
 
<%@ include file="/tic/soap/sync/tic_soap_sync_job/ticSoapSync_tempView.jsp"%>

</table>

</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
