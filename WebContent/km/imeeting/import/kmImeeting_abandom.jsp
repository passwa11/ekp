<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  ref="default.simple" spa="true">
	<template:replace name="body">
	<div style="width:100%">
	  <ui:tabpanel   layout="sys.ui.tabpanel.list">
		 <ui:content title="${lfn:message('km-imeeting:kmImeeting.panel.status.abandom') }">
		 	 <ui:iframe id="kmImeetingAbandom" cfg-takeHash="false" src="${LUI_ContextPath }/km/imeeting/km_imeeting_main/index_content_meeting.jsp?status=00&docStatus=abandom"></ui:iframe>
		  </ui:content>
		   <ui:content title="${lfn:message('km-imeeting:kmImeetingSummary.panel.status.abandom') }">
		 	 <ui:iframe id="kmImeetingSummaryAbandom" cfg-takeHash="false" src="${LUI_ContextPath }/km/imeeting/km_imeeting_summary/index_content_summary.jsp?status=00&docStatus=discard"></ui:iframe>
		  </ui:content>
		</ui:tabpanel>
	  </div> 
	</template:replace>
</template:include>
