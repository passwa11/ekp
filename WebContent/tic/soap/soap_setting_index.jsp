<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" rwd="true">
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
		<div>
			<ui:tabpanel layout="sys.ui.tabpanel.list">
				<ui:content title="${lfn:message('tic-core-common:ticCoreCommon.serverSetting')}">
					<ui:iframe
						src="${LUI_ContextPath }/tic/soap/connector/tic_soap_setting/ticSoapSetting_ui_include.jsp?fdAppType=${JsParam.fdAppType}&fdEnviromentId=${JsParam.fdEnviromentId}"></ui:iframe>
				</ui:content>
				<ui:content title="${lfn:message('tic-core-common:ticCoreCommon.funcSetting')}">
					<ui:iframe
						src="${LUI_ContextPath }/tic/soap/connector/tic_soap_main/ticSoapMain_ui_include.jsp?modelName=${JsParam.modelName}&fdAppType=${JsParam.fdAppType}&modelName=${JsParam.modelName}&fdEnviromentId=${JsParam.fdEnviromentId}"></ui:iframe>
				</ui:content>
			</ui:tabpanel>
		</div>
	</template:replace>
</template:include>