<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="no">
	<template:replace name="title">
		<c:out value="${ lfn:message('kms-medal:module.kms.medal') }"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<script>
		function deleteDoc(delUrl){
			seajs.use(['lui/dialog'],function(dialog){
				dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(isOk){
					if(isOk){
						Com_OpenWindow(delUrl,'_self');
					}	
				});
			});
		}
		</script>
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<kmss:auth requestURL="/kms/medal/kms_medal_main/kmsMedalMain.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${lfn:message('button.delete')}" order="4"
							onclick="deleteDoc('kmsMedalLog.do?method=delete&fdId=${param.fdId}');">
				</ui:button> 
			</kmss:auth>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<div class='lui_form_title_frame'>
			<div class='lui_form_subject'>
				<c:out value="${ lfn:message('kms-medal:table.kmsMedalLog') }"></c:out>
			</div>
			<div class='lui_form_baseinfo'>
		<div class="lui_form_content_frame">
			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-medal" key="kmsMedalLog.docHonoursTime"/>
					</td>
					<td width="35%">
						<xform:datetime property="docHonoursTime" />
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-medal" key="kmsMedalLog.docOperator"/>
					</td>
					<td width="35%">
						<c:out value="${kmsMedalLogForm.docOperatorName}" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-medal" key="kmsMedalLog.fdHonours"/>
					</td>
					<td width="35%" colspan="3">
						<c:out value="${kmsMedalLogForm.fdHonourNames}" />
					</td>
				</tr>
			</table> 
		</div>

	</template:replace>
</template:include>
