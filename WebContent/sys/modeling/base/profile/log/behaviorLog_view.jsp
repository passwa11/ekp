<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-modeling-base:module.sys.modeling') }"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
			<c:if test="${ 1 == sysModelingBehaviorLogForm.fdStatus }">
				<ui:button text="${ lfn:message('sys-modeling-base:modelingBehaviorLog.behaviorManualStopRunning') }" order="1" onclick="stopBehaviorRunning();">
				</ui:button>
			</c:if>
			<c:if test="${ 5 == sysModelingBehaviorLogForm.fdStatus }">
				<ui:button text="${ lfn:message('button.refresh') }" order="1" onclick="location.reload();">
				</ui:button>
			</c:if>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<div class='lui_form_title_frame'>
			<div class='lui_form_subject'>
					${ lfn:message('sys-modeling-base:table.modelingBehaviorLog') }
			</div>
			<div class='lui_form_baseinfo'>
			</div>
		</div>
		<div class="lui_form_content_frame">
			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" width=15%>
							${ lfn:message('sys-modeling-base:modelingBehaviorLog.fdName') }
					</td>
					<td width="85%" colspan="3">
						<c:out value="${sysModelingBehaviorLogForm.fdName }" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-modeling-base" key="modelingBehaviorLog.docCreator"/>
					</td>
					<td width="35%">
						<c:out value="${sysModelingBehaviorLogForm.docCreatorName}" />
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-modeling-base" key="modelingBehaviorLog.fdStartTime"/>
					</td>
					<td width="35%">
						<c:out value="${sysModelingBehaviorLogForm.fdStartTimeDate }" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-modeling-base" key="modelingBehaviorLog.fdConsumeTime"/>
					</td>
					<td width="35%">
						<c:if test="${not empty sysModelingBehaviorLogForm.fdConsumeTime }">
							<c:out value="${sysModelingBehaviorLogForm.fdConsumeTime}" />ms
						</c:if>
					</td>
					<td class="td_normal_title" width=15%>
							${ lfn:message('sys-modeling-base:modelingBehaviorLog.fdEndTime') }
					</td>
					<td width="85%" colspan="3">
						<c:out value="${sysModelingBehaviorLogForm.fdEndTimeDate }" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-modeling-base" key="modelingBehaviorLog.fdStatus"/>
					</td>
					<td width="35%">
						<c:if test="${sysModelingBehaviorLogForm.fdStatus == 5}">
							<div style="color: #FF943E">
						</c:if>
						<sunbor:enumsShow value="${sysModelingBehaviorLogForm.fdStatus}" enumsType="sys_modeling_behavior_exec" />
						<c:if test="">
							</div>
						</c:if>
					</td>
					<td class="td_normal_title" width=15%>
							${ lfn:message('sys-modeling-base:modelingBehaviorLog.docCreateTime') }
					</td>
					<td width="35%" >
						<c:out value="${sysModelingBehaviorLogForm.docCreateTime }" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-modeling-base" key="modelingBehaviorLog.fdSuspendStartTime"/>
					</td>
					<td width="35%">
						<c:out value="${sysModelingBehaviorLogForm.fdSuspendStartTimeDate }" />
					</td>
					<td class="td_normal_title" width=15%>
							${ lfn:message('sys-modeling-base:modelingBehaviorLog.fdSuspendEndTime') }
					</td>
					<td width="35%" >
						<c:if test="${not empty sysModelingBehaviorLogForm.fdSuspendStartTimeDate && sysModelingBehaviorLogForm.fdStatus != 4 && sysModelingBehaviorLogForm.fdStatus != 5 }">
							<div style="color: #FF943E">${ lfn:message('sys-modeling-base:modelingBehaviorLog.fdSuspendFail') }</div>
						</c:if>
						<c:out value="${sysModelingBehaviorLogForm.fdSuspendEndTimeDate }" />
					</td>
				</tr>
				<c:if test="${not empty sysModelingBehaviorLogForm.fdMessage }">
				<tr>
					<td class="td_normal_title" width=15%>
							${ lfn:message('sys-modeling-base:modelingBehaviorLog.fdMessage') }
					</td>
					<td width="85%" colspan="3">
							${sysModelingBehaviorLogForm.fdMessage }
					</td>
				</tr>
				</c:if>
			</table>
		</div>
		<script>
			function stopBehaviorRunning () {
				seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
					dialog.confirm("${ lfn:message('sys-modeling-base:modelingBehaviorLog.isSure.doSuspend') }",function (isOk) {
						if(isOk){
							var url = Com_Parameter.ContextPath + "sys/modeling/base/behaviorLog.do?method=stopBehaviorRunning&logId=${sysModelingBehaviorLogForm.fdId}";
							$.ajax({
								url: url,
								type: "get",
								dataType: 'json',
								success: function (data) {
									if(data && data.status === true){
										dialog.success("${ lfn:message('sys-modeling-base:modeling.baseinfo.OperateSuccess') }")
									}else{
										dialog.failure("${ lfn:message('sys-modeling-base:modeling.page.operation.failed') }")
									}
									setTimeout(function () {
										window.location.reload();
									}, 2000);
								}
							});
						}
					});

				});
			}
		</script>
	</template:replace>
</template:include>