<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" rwd="true">
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use([ 'theme!list' ]);
		</script>
		<div>
			<ui:tabpanel layout="sys.ui.tabpanel.list">
				<ui:content title="${lfn:message('tic-core-common:ticCoreCommon.normalLog')}">
					<ui:iframe
						src="${LUI_ContextPath }/tic/core/log/tic_core_log_main/ticCoreLogMain_ui_include.jsp?isError=0&displayName=&subDisplayName=&logResultType=Normal&fdAppType=${JsParam.fdAppType}&fdEnviromentId=${JsParam.fdEnviromentId}"></ui:iframe>

				</ui:content>
				<ui:content title="${lfn:message('tic-core-common:ticCoreCommon.exceptionLog')}">
					<ui:iframe
						src="${LUI_ContextPath }/tic/core/log/tic_core_log_main/ticCoreLogMain_ui_include.jsp?isError=1&displayName=&subDisplayName=&logResultType=Normal&fdAppType=${JsParam.fdAppType}&fdEnviromentId=${JsParam.fdEnviromentId}"></ui:iframe>
				</ui:content>
				<ui:content title="${lfn:message('tic-core-common:ticCoreCommon.businessExceptionLog')}">
					<ui:iframe
						src="${LUI_ContextPath }/tic/core/log/tic_core_log_main/ticCoreLogMain_ui_include.jsp?isError=2&displayName=&subDisplayName=&logResultType=Normal&fdAppType=${JsParam.fdAppType}&fdEnviromentId=${JsParam.fdEnviromentId}"></ui:iframe>
				</ui:content>
			</ui:tabpanel>
		</div>
	</template:replace>
</template:include>