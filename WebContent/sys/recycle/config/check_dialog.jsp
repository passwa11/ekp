<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content">
		<center>
			<script  type="text/javascript">
				var $var = {
						checkUrl: '<c:url value="/sys/recycle/softDeleteConfig.do?method=checkTransfer" />',
						doUrl: '<c:url value="/sys/recycle/softDeleteConfig.do?method=doTransfer" />'
				};
				var $lang = {
						checkNoTransfer: '<bean:message bundle="sys-recycle" key="config.setting.transfer.checkNoTransfer"/>',
						doConfirm: '<bean:message bundle="sys-recycle" key="config.setting.confirm.info4"/>',
						dialogSuccess: '<bean:message key="return.optSuccess"/>',
						dialogFailure: '<bean:message key="return.optFailure"/>'
				};
			</script>
			<script type="text/javascript" src="${LUI_ContextPath}/sys/recycle/resource/js/check_dialog_script.js"></script>
			<style>
				.tb_normal thead tr th{
					word-break: break-word;
				    padding: 6px;
				    border-width: 1px;
				    border-style: solid;
				    border-color: rgb(210, 210, 210);
				    border-image: initial;
				}
			</style>
			<table id="_modules_table" class="tb_normal" style="margin: 10px auto;" width=95%>
				<thead>
					<tr>
						<th width=65%>
							<bean:message bundle="sys-recycle" key="config.setting.transfer.module"/>
						</th>
						<th width="35%">
							<bean:message bundle="sys-recycle" key="config.setting.transfer.count"/>
						</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
			
			<div id="__msg" style="margin: 10px auto; display: none; color: red;"></div>
			
			<ui:toolbar>
				<ui:button id="__do" text="${lfn:message('sys-recycle:config.setting.transfer.do') }" onclick="_do();"></ui:button>
				<ui:button style="padding-left:10px"  text="${lfn:message('button.cancel') }" onclick="Com_CloseWindow();"></ui:button>
			</ui:toolbar>
		</center>
	</template:replace>
</template:include>
