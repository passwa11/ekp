<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.view">
	<template:replace name="title">
		<c:out value="${sysHelpConfigForm.fdName}"></c:out>
	</template:replace>
	<template:replace name="head">
		<script>
			function toEdit(){
				window.location.href="${LUI_ContextPath }/sys/help/sys_help_config/sysHelpConfig.do?method=edit&fdId=${sysHelpConfigForm.fdId}"
			}
		</script>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<ui:button text="${lfn:message('button.edit')}" onclick="toEdit()" order="2"></ui:button>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav">
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
			<ui:menu-item text="${ lfn:message('sys-help:table.sysHelpConfig') }" />
		</ui:menu>
	</template:replace>
	<template:replace name="content">
		<div style="margin: 30px auto;margin-bottom: 30px;text-align: center;">
			<font style="font-size: 24px;font-weight: bold;">${fn:escapeXml(sysHelpConfigForm.fdName)}</font>
		</div>
		<table class="tb_normal" width="100%" style="margin-bottom: 30px;">
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="sys-help" key="sysHelpConfig.fdModuleName" />
				</td>
				<td width="85%">
						${fn:escapeXml(sysHelpConfigForm.fdModuleName)}
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="sys-help" key="sysHelpConfig.fdModulePath" />
				</td>
				<td width="85%">
						${fn:escapeXml(sysHelpConfigForm.fdModulePath)}
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="sys-help" key="sysHelpConfig.fdUrl" />
				</td>
				<td width="85%">
						${fn:escapeXml(sysHelpConfigForm.fdUrl)}
				</td>
			</tr>
		</table>
	</template:replace>
</template:include>
