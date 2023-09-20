<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  ref="default.simple" spa="true">
	<template:replace name="body">
	<div style="width:100%">
	  <ui:tabpanel   layout="sys.ui.tabpanel.list">
		 <ui:content title="${lfn:message('km-imeeting:kmImeetingRes.fdPlace') }">
		 	 <ui:iframe id="meetingPlace" cfg-takeHash="false" src="${LUI_ContextPath }/km/imeeting/km_imeeting_calendar/index_content_place.jsp"></ui:iframe>
		  </ui:content>
		   <ui:content title="${lfn:message('km-imeeting:table.kmImeetingEquipment') }">
		 	 <ui:iframe id="meetingEquipment" cfg-takeHash="false" src="${LUI_ContextPath }/km/imeeting/km_imeeting_calendar/index_content_equipment.jsp"></ui:iframe>
		  </ui:content>
		</ui:tabpanel>
	  </div> 
	</template:replace>
</template:include>
