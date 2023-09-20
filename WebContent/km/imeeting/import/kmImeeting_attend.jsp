<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  ref="default.simple">
	<template:replace name="body">
	<div style="width:100%">
		  <ui:tabpanel  id="kmImeetingAttendPanel" layout="sys.ui.tabpanel.list">
		 <ui:content title="${lfn:message('km-imeeting:kmImeeting.tree.meeting.myAttend') }">
		 	 <ui:iframe id="myAttend" cfg-takeHash="false" src="${LUI_ContextPath }/km/imeeting/km_imeeting_main/index_content_myAttend.jsp?mymeeting=myAttend&except=docStatus:00&required=${JsParam.required}&defaultValue=${JsParam.defaultValue}"></ui:iframe>
		  </ui:content>
		</ui:tabpanel>
	  </div> 
	</template:replace>
</template:include>