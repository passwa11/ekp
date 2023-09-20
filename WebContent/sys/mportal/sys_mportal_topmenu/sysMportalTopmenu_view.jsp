<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.view">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
			<kmss:auth requestURL="/sys/mportal/sys_mportal_topmenu/sysMportalTopmenu.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${ lfn:message('button.edit') }" onclick="Com_OpenWindow('sysMportalTopmenu.do?method=edit&fdId=${param.fdId}','_self');">
				</ui:button>
			</kmss:auth>
			<kmss:auth requestURL="/sys/mportal/sys_mportal_topmenu/sysMportalTopmenu.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${ lfn:message('button.delete') }" onclick="if(!confirmDelete())return;Com_OpenWindow('sysMportalTopmenu.do?method=delete&fdId=${param.fdId}','_self');">
				</ui:button>
			</kmss:auth>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	
<p class="txttitle"><bean:message bundle="sys-mportal" key="table.sysMportalTopmenu"/></p>

<center>
<table class="tb_normal" id="Label_Tabel" width=95%>

	<tr LKS_LabelName='${ lfn:message('config.baseinfo') }'>
		<td>
			<table class="tb_normal" width=100%> 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-mportal" key="sysMportalTopmenu.fdName"/>
		</td><td width="35%">
			<xform:text property="fdName" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-mportal" key="sysMportalTopmenu.fdOrder"/>
		</td><td width="35%">
			<xform:text property="fdOrder" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-mportal" key="sysMportalTopmenu.fdUrl"/>
		</td><td width="35%">
			<xform:text property="fdUrl" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-mportal" key="sysMportalTopmenu.fdIcon"/>
		</td><td width="35%">
			<xform:text property="fdIcon" style="width:85%" />
		</td>
	</tr>
			</table>
		</td>
	</tr>
</table>
</center>

	</template:replace>
</template:include>