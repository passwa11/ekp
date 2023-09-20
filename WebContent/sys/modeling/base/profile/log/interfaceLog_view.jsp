<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-modeling-base:module.sys.modeling') }"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<div class='lui_form_title_frame'>
			<div class='lui_form_subject'>
					${ lfn:message('sys-modeling-base:table.modelingInterfaceLog') }
			</div>
			<div class='lui_form_baseinfo'>
			</div>
		</div>
		<div class="lui_form_content_frame">
			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" width=15%>
							${ lfn:message('sys-modeling-base:modelingInterfaceLog.fdName') }
					</td>
					<td width="85%" colspan="3">
						<c:out value="${sysModelingInterfaceLogForm.fdName }" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-modeling-base" key="modelingInterfaceLog.docCreator"/>
					</td>
					<td width="35%">
						<c:out value="${sysModelingInterfaceLogForm.docCreatorName}" />
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-modeling-base" key="modelingInterfaceLog.docCreateTime"/>
					</td>
					<td width="35%">
						<c:out value="${sysModelingInterfaceLogForm.docCreateTime }" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-modeling-base" key="modelingInterfaceLog.fdConsumeTime"/>
					</td>
					<td width="35%">
						<c:out value="${sysModelingInterfaceLogForm.fdConsumeTime}" />ms
					</td>
					<td class="td_normal_title" width=15%>
							${ lfn:message('sys-modeling-base:modelingInterfaceLog.fdEndTime') }
					</td>
					<td width="85%" colspan="3">
						<c:out value="${sysModelingInterfaceLogForm.fdEndTime }" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-modeling-base" key="modelingInterfaceLog.fdStatus"/>
					</td>
					<td width="35%">
						<sunbor:enumsShow value="${sysModelingInterfaceLogForm.fdStatus}" enumsType="sys_modeling_interface_exec" />
					</td>
				</tr>
				<c:if test="${sysModelingInterfaceLogForm.fdStatus != 1}">
					<tr>
						<td class="td_normal_title" width=15%>
								${ lfn:message('sys-modeling-base:modelingInterfaceLog.fdMessage') }
						</td>
						<td width="85%" colspan="3">
								${sysModelingInterfaceLogForm.fdMessage }
						</td>
					</tr>
				</c:if>
			</table>
		</div>
		<script>
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){

			});
		</script>
	</template:replace>
</template:include>