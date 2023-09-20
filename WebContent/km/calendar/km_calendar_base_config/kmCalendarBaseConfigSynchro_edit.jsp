<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="content">
<script language="JavaScript">
	Com_IncludeFile("calendar.js");
</script>

<html:form action="/km/calendar/km_calendar_base_config/kmCalendarBaseConfig.do">
<div style="margin-top:25px">
<p class="configtitle">
	<bean:message key="kmCalendarBaseConfig.synchro.setting" bundle="km-calendar"/>
</p>
<center>
<table class="tb_normal" width=90%>
	<input type="hidden" value="${type }" name="type">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message key="kmCalendarBaseConfig.defaultScheduleAuthority" bundle="km-calendar"/>
			 </td>
			<td colspan="3">
				<xform:radio property="defaultAuthorityType"  showStatus="edit">
					<xform:simpleDataSource value="DEFAULT" bundle="km-calendar" textKey="authority.type.default"></xform:simpleDataSource>
					<%-- <xform:simpleDataSource value="PUBLIC" bundle="km-calendar" textKey="authority.type.public"></xform:simpleDataSource> --%>
					<xform:simpleDataSource value="PRIVATE" bundle="km-calendar" textKey="authority.type.private"></xform:simpleDataSource>
				</xform:radio>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message key="kmCalendarBaseConfig.threadPoolSize.setting" bundle="km-calendar"/>
			 </td>
			 <td colspan=3>
			 	<xform:text property="threadPoolSize" validators="required digits min(0)" showStatus="edit"></xform:text><span class="txtstrong">*</span>
				<span id='dateDescription'>
				<br><font color="red" ><bean:message bundle="km-calendar" key="kmCalendarBaseConfig.synchro.tips"/></font></span>
			</td>
		</tr>
		
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-calendar" key="kmCalendarBaseConfig.synchro.setting.name"/>
			 </td>
			 <td colspan=3>
				<xform:radio property="synchroDirect" showStatus="edit">
					<xform:enumsDataSource enumsType="km_calendar_synchro_direct" />
				</xform:radio>
				<span>
				<br><font color="red" >
					<bean:message bundle="km-calendar" key="kmCalendarBaseConfig.synchro.setting.desc"/>
					</font>
				</span>
			</td>
		</tr>
		
</table>
<div style="margin-bottom: 10px;margin-top:25px">
	   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.kmCalendarBaseConfigForm, 'updateSynchroThreadSize');" order="1" ></ui:button>
</div>
</center>
</div>
<html:hidden property="method_GET"/>
</html:form>

	<script type="text/javascript">
	 		$KMSSValidation();
	</script>
</template:replace>
</template:include>

<ui:top id="top"></ui:top>
<kmss:ifModuleExist path="/sys/help">
	<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
</kmss:ifModuleExist>