<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" rwd="true">
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use([ 'theme!list' ]);
		</script>
		<div>
			<ui:tabpanel layout="sys.ui.tabpanel.list">
				<ui:content title="${lfn:message('tic-core-common:ticCoreCommon.dataImport')}">
					<ui:iframe
						src="${LUI_ContextPath }/tic/core/inoutdata/ticCoreInoutdata_upload.jsp?fdAppType=${JsParam.fdAppType}"></ui:iframe>
				</ui:content>
				<ui:content title="${lfn:message('tic-core-common:ticCoreCommon.dataExport')}">
					<ui:iframe
						src="${LUI_ContextPath }/tic/core/inoutdata/ticCoreInoutdata.do?method=init&fdAppType=${JsParam.fdAppType}"></ui:iframe>
				</ui:content>
			</ui:tabpanel>
		</div>
	</template:replace>
</template:include>