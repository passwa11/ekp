<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ page import="com.landray.kmss.tic.core.sync.forms.TicCoreSyncJobForm,
	com.landray.kmss.sys.quartz.scheduler.CronExpression,
	java.util.Date,
	com.landray.kmss.util.DateUtil" %>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%
	TicCoreSyncJobForm ticCoreSyncJobForm = (TicCoreSyncJobForm)request.getAttribute("ticCoreSyncJobForm");
%>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
<div id="optBarDiv">
	<input type="button"
			value="<bean:message key="ticCoreSyncJob.dataList" bundle="tic-core-sync"/>"
			onclick="Com_OpenWindow('ticCoreSyncJob.do?method=getXMLTable&quartzId=${ticCoreSyncJobForm.fdId}');">
	<c:if test="${not empty ticCoreSyncJobForm.fdQuartzEkp}">
		 <input type="button" value="<bean:message bundle="sys-quartz" key="sysQuartzJob.button.run"/>"
		onClick="Com_OpenWindow('<c:url value="/tic/core/sync/tic_core_sync_job/ticCoreSyncJob.do"/>?method=run&fdId=${ticCoreSyncJobForm.fdQuartzEkp}','_self');">
	</c:if>

	<kmss:auth requestURL="/tic/core/sync/tic_core_sync_job/ticCoreSyncJob.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('ticCoreSyncJob.do?method=edit&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/tic/core/sync/tic_core_sync_job/ticCoreSyncJob.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('ticCoreSyncJob.do?method=delete&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-core-sync" key="table.ticCoreSyncJob"/></p>

<center>

<table
		id="Label_Tabel"
		width=95%>
		<tr LKS_LabelName="${lfn:message('tic-core-common:ticCoreCommon.syncJobInfo')}">
			<td>
			
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.fdSubject"/>
		</td><td>
			<c:if test="${ticCoreSyncJobForm.fdIsSysJob=='true'}">
				<kmss:message key="${ticCoreSyncJobForm.fdSubject}"/>
			</c:if>
			<c:if test="${ticCoreSyncJobForm.fdIsSysJob!='true'}">
				${ticCoreSyncJobForm.fdSubject} 
			</c:if>
		</td>
		
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.fdCategory"/>
		</td><td width="35%">
			<c:out value="${ticCoreSyncJobForm.fdCategoryName}" />
		</td>
		
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.fdCronExpression"/>
		</td><td width=35%>
			<c:import url="/sys/quartz/sys_quartz_job/sysQuartzJob_showCronExpression.jsp" charEncoding="UTF-8">
				<c:param name="value" value="${ticCoreSyncJobForm.fdCronExpression}" />
			</c:import>
		</td>
		<td width=15% class="td_normal_title">
			<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.nextTime"/>
		</td><td width=35%>
			<c:if test="${ticCoreSyncJobForm.fdEnabled=='true'}">
			<%
				CronExpression expression = new CronExpression(ticCoreSyncJobForm.getFdCronExpression());
				Date nxtTime = expression.getNextValidTimeAfter(new Date());
				if(nxtTime!=null)
					out.write(DateUtil.convertDateToString(nxtTime,DateUtil.TYPE_DATETIME, request.getLocale()));
			%>
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.fdJobType"/>
		</td><td>
			<sunbor:enumsShow value="${(empty ticCoreSyncJobForm.fdRunTime)?'false':'true'}" enumsType="common_yesno" />
		</td>
		<td class="td_normal_title">
			<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.fdRunType"/>
		</td><td>
			<sunbor:enumsShow value="${ticCoreSyncJobForm.fdRunType}" enumsType="sysQuartzJob_fdRunType" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.fdEnabled"/>
		</td><td>
			<sunbor:enumsShow value="${ticCoreSyncJobForm.fdEnabled}" enumsType="common_yesno" />
		</td>
		<td class="td_normal_title">
			<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.fdIsSysJob"/>
		</td><td>
			<sunbor:enumsShow value="${ticCoreSyncJobForm.fdIsSysJob}" enumsType="common_yesno" />
		</td>
	</tr>
	<c:if test="${not empty ticCoreSyncJobForm.fdRunTime}">
		<tr>
			<td class="td_normal_title">
				<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.fdRequired"/>
			</td><td>
				<sunbor:enumsShow value="${ticCoreSyncJobForm.fdRequired}" enumsType="common_yesno" />
			</td>
			<td class="td_normal_title">
				<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.fdTriggered"/>
			</td><td>
				<sunbor:enumsShow value="${ticCoreSyncJobForm.fdTriggered}" enumsType="common_yesno" />
			</td>
		</tr>
	</c:if>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.fdJob"/>
		</td><td colspan="3">
			${ticCoreSyncJobForm.fdJobService}.${ticCoreSyncJobForm.fdJobMethod}(${ticCoreSyncJobForm.fdParameter})
		</td>
	</tr>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.fdLink"/>
		</td><td colspan="3">
			<c:if test="${ticCoreSyncJobForm.fdLink!=null && ticCoreSyncJobForm.fdLink!=''}">
				<a href="<c:url value="${ticCoreSyncJobForm.fdLink}" />" target="_blank">
					<c:out value="${ticCoreSyncJobForm.fdUseExplain}"/>
				</a>
			</c:if>
		</td>
	</tr>
		<tr>
		<td class="td_normal_title">
			<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.fdUseExplain"/>
		</td><td colspan="3">
		${ticCoreSyncJobForm.fdUseExplain}
		</td>
	</tr>
</table>
</td>
</tr>
 
 <%@ include file="/tic/core/sync/tic_core_sync_job/ticCoreSync_tempView.jsp"%>

</table>

</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
