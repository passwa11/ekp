<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" rwd="true">
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
		<div>
			<ui:tabpanel layout="sys.ui.tabpanel.list">
			  <ui:content title="${lfn:message('tic-jdbc:ticJdbcDataSet.dataSourcesSet')}">
			  <ui:iframe src="${LUI_ContextPath}/tic/jdbc/tic_jdbc_dbcp/index.jsp?fdAppType=${JsParam.fdAppType}"></ui:iframe>
			   </ui:content>
	  	 <ui:content title="${lfn:message('tic-jdbc:ticJdbcDataSet.dataSetSetting')}">
	  	   <ui:iframe src="${LUI_ContextPath}/tic/jdbc/tic_jdbc_data_set/ticJdbcDataSet_ui_include.jsp?fdAppType=${JsParam.fdAppType}&fdEnviromentId=${JsParam.fdEnviromentId}&modelName=${JsParam.modelName}"></ui:iframe>	
			  </ui:content> 
		    </ui:tabpanel> 
	  </div>
	</template:replace> 
</template:include>