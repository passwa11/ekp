<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.modeling.base.util.OperLogUtil" %>
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
					${ lfn:message('sys-modeling-base:table.modelingOperLog') }
			</div>
			<div class='lui_form_baseinfo'>
			</div>
		</div>
		<div class="lui_form_content_frame">
			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" width=15%>
							${ lfn:message('sys-modeling-base:modelingOperLog.fdAppName') }
					</td>
					<td width="35%">
						<c:out value="${modelingOperLogForm.fdAppName}" />
					</td>
					<td class="td_normal_title" width=15%>
							${ lfn:message('sys-modeling-base:modelingOperLog.fdOperItemName') }
					</td>
					<td width="35%">
						<c:out value="${modelingOperLogForm.fdOperItemName}" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-modeling-base" key="modelingOperLog.fdCreator"/>
					</td>
					<td width="35%">
						<c:out value="${modelingOperLogForm.fdCreatorName}" />
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-modeling-base" key="modelingOperLog.fdCreateTime"/>
					</td>
					<td width="35%">
						<c:out value="${modelingOperLogForm.fdCreateTime }" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-modeling-base" key="modelingOperLog.fdIp"/>
					</td>
					<td width="35%">
						<c:out value="${modelingOperLogForm.fdIp}" />
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-modeling-base" key="modelingOperLog.fdMethod"/>
					</td>
					<td width="35%">
						<sunbor:enumsShow value="${modelingOperLogForm.fdMethod}" enumsType="modeling_oper_log_method" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
							${ lfn:message('sys-modeling-base:modelingOperLog.subject') }
					</td>
					<td width="85%" colspan="3">
						<c:if test="${modelingOperLogForm.fdMethod != 'systemStopApp'}">
							<c:set var="subject" value="${OperLogUtil.getModuleName(modelingOperLogForm.fdModule)}-${modelingOperLogForm.fdDataName}" />
							<span title="${modelingOperLogForm.fdDataName}">
							<sunbor:enumsShow value="${modelingOperLogForm.fdMethod}" enumsType="modeling_oper_log_method" />-<c:out value="${subject}"/>
							</span>
						</c:if>
						<c:if test="${modelingOperLogForm.fdMethod == 'systemStopApp'}">
							<span title="${modelingOperLogForm.fdDataName}">
								<sunbor:enumsShow value="${modelingOperLogForm.fdMethod}" enumsType="modeling_oper_log_method" />
							</span>
						</c:if>
					</td>
				</tr>
			</table>
		</div>
		<script>
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){

			});
		</script>
	</template:replace>
</template:include>