<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  ref="default.simple" spa="true">
	<template:replace name="body">
	<div style="width:100%">
	  <ui:tabpanel   layout="sys.ui.tabpanel.list">
		 <ui:content title="${lfn:message('km-imeeting:kmImeeting.tree.data') }">
		 	 <ui:iframe id="meetingData" cfg-takeHash="false" src="${LUI_ContextPath }/km/imeeting/km_imeeting_uploadAtt/index.jsp"></ui:iframe>
		  </ui:content>
		</ui:tabpanel>
	  </div> 
	</template:replace>
</template:include>
