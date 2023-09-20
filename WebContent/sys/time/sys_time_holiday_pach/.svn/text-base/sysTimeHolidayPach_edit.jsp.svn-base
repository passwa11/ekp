<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<c:choose>
				<c:when test="${ sysTimeHolidayPachForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.sysTimeHolidayPachForm, 'update');"></ui:button>
				</c:when>
				<c:when test="${ sysTimeHolidayPachForm.method_GET == 'add' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.sysTimeHolidayPachForm, 'save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.sysTimeHolidayPachForm, 'saveadd');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	
<html:form action="/sys/time/sys_time_holiday_pach/sysTimeHolidayPach.do">
 
<p class="txttitle"><bean:message bundle="sys-time" key="table.sysTimeHolidayPach"/></p>

<center>
<table class="tb_normal" id="Label_Tabel" width=95%>
	<tr LKS_LabelName='${ lfn:message('config.baseinfo') }'>
		<td>
			<table class="tb_normal" width=100%> 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-time" key="sysTimeHolidayPach.fdName"/>
		</td><td width="35%">
			<xform:text property="fdName" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-time" key="sysTimeHolidayPach.fdPachTime"/>
		</td><td width="35%">
			<xform:datetime property="fdPachTime" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-time" key="sysTimeHolidayPach.fdHoliday"/>
		</td><td width="35%">
			<xform:select property="fdHolidayId">
				<xform:beanDataSource serviceBean="sysTimeHolidayService" selectBlock="fdId,fdName" orderBy="" />
			</xform:select>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-time" key="sysTimeHolidayPach.fdDetail"/>
		</td><td width="35%">
			<xform:select property="fdDetailId">
				<xform:beanDataSource serviceBean="sysTimeHolidayDetailService" selectBlock="fdId,fdName" orderBy="" />
			</xform:select>
		</td>
	</tr>
			</table>
		</td>
	</tr>
</table> 
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	Com_IncludeFile("calendar.js");
	$KMSSValidation();
</script>
</html:form>

	</template:replace>
</template:include>