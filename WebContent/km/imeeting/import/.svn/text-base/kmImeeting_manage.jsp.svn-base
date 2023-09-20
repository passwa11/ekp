<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  ref="default.simple">
	<template:replace name="body">
	<div style="width:100%">
	  <ui:tabpanel   layout="sys.ui.tabpanel.list">
		 <ui:content title="${lfn:message('km-imeeting:kmImeeting.committee') }">
		 	 <ui:iframe id="myHost" cfg-takeHash="false" src="${LUI_ContextPath }/km/imeeting/km_imeeting_main/index_content_myHost.jsp?myHost=true&except=docStatus:00&required=${JsParam.required}&defaultValue=${JsParam.defaultValue}"></ui:iframe>
		  </ui:content>
		</ui:tabpanel>
	  </div> 
	</template:replace>
</template:include>