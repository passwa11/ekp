<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%-- 样式 --%>
<style>
	.lui_imeeting_title{
		width: 60%;
		font-size: 16px;
		padding-left: 8px;
		line-height: 30px;
		height: 30px;
		border-bottom: 1px #d9d9d9 solid;
		margin-bottom: 10px;
	}
</style>
<%--权限机制 --%>
<ui:tabpanel>
	<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="kmImeetingMainForm" />
		<c:param name="moduleModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
	</c:import>
</ui:tabpanel>
<%-- 流程 --%>
<ui:tabpanel>
	<c:if test="${param.approveModel ne 'right'}">
		<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmImeetingMainForm" />
			<c:param name="fdKey" value="ImeetingMain" />
			<c:param name="showHistoryOpers" value="true" />
			<c:param name="isExpand" value="true" />
		</c:import>
	</c:if>
	<c:if test="${param.approveModel eq 'right'}">
	<%--流程--%>
		<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmImeetingMainForm" />
			<c:param name="fdKey" value="ImeetingMain" />
			<c:param name="showHistoryOpers" value="true" />
			<c:param name="isExpand" value="true" />
			<c:param name="approveType" value="right" />
		</c:import>
	</c:if>	
</ui:tabpanel>	
<%-- 日程同步 --%>
<c:set var="sysAgendaMainForm" value="${requestScope[param.formName]}" />
<c:if test="${sysAgendaMainForm.syncDataToCalendarTime=='sendNotify'||sysAgendaMainForm.syncDataToCalendarTime=='personAttend'}">
	<ui:tabpanel expand="false">
		<ui:content title="${ lfn:message('sys-agenda:module.sys.agenda.syn') }">
			<table class="tb_normal" width=100%>
				<tr>
				   <td width="15%"  class="tb_normal">
				   		<%--同步时机--%>
				       	<bean:message bundle="sys-agenda" key="module.sys.agenda.syn.time" />
				   </td>
				   <td width="85%" colspan="3">
				      	<xform:radio property="syncDataToCalendarTime" showStatus="edit">
		       				<xform:enumsDataSource enumsType="kmImeetingMain_syncDataToCalendarTime" />
						</xform:radio>
						<br>
						<font color="red"><bean:message bundle="km-imeeting" key="kmImeetingMain.agenda.syn.tip"/></font>
				   </td>
				</tr>
				<tr>
					<td colspan="4" style="padding: 0px;">
						<c:import url="/sys/agenda/import/sysAgendaMain_general_edit.jsp"	charEncoding="UTF-8">
							<c:param name="formName" value="${param.formName }" />
							<c:param name="fdKey" value="${param.fdKey}" />
							<c:param name="fdPrefix" value="sysAgendaMain_formula_edit" />
							<c:param name="fdModelName" value="${param.moduleModelName}" />
							<%--可选字段 1.syncTimeProperty:同步时机字段； 2.noSyncTimeValues:当syncTimeProperty为此值时，隐藏同步机制 --%>
							<c:param name="syncTimeProperty" value="syncDataToCalendarTime" />
							<c:param name="noSyncTimeValues" value="noSync" />
						</c:import>
					</td>
				</tr>
			</table>
		</ui:content>
	</ui:tabpanel>
</c:if>