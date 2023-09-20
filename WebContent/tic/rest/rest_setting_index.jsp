<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" rwd="true">
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
		<div>
			<ui:tabpanel layout="sys.ui.tabpanel.list">
			  <ui:content title="${ lfn:message('tic-rest-connector:tree.ticRestSetting.register') }">
			  <ui:iframe src="${LUI_ContextPath}/tic/rest/connector/tic_rest_setting/ticRestSetting_ui_include.jsp?fdAppType=${JsParam.fdAppType}&fdEnviromentId=${JsParam.fdEnviromentId}"></ui:iframe>
			   </ui:content>
	  	 <ui:content title="${ lfn:message('tic-rest-connector:ticRestMain.funcSetting') }">
	  	   <ui:iframe src="${LUI_ContextPath}/tic/rest/connector/tic_rest_main/ticRestMain_ui_include.jsp?modelName=${JsParam.modelName}&fdAppType=${JsParam.fdAppType}&fdEnviromentId=${JsParam.fdEnviromentId}"></ui:iframe>	
			  </ui:content> 
		    </ui:tabpanel> 
	  </div>
	</template:replace> 
</template:include>