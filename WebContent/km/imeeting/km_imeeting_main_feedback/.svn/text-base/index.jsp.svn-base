<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="body">  
		<list:listview id="listview">
			<ui:source type="AjaxJson">
					{url:'/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=listFeedback&meetingId=${JsParam.meetingId}'}
			</ui:source>
			<list:colTable  isDefault="false" layout="sys.ui.listview.columntable"  name="columntable">
				<list:col-serial></list:col-serial>
				<list:col-auto props=""></list:col-auto>
			</list:colTable>
		</list:listview> 
			<script type="text/javascript">
			seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/toolbar'], function($, dialog , topic,toolbar) {
				window.editFeedBack= function(fdId){
					Com_OpenWindow('${LUI_ContextPath}/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=edit&type=attend&fdId='+fdId,'_blakk');
					$dialog.hide();
				};
				
				window.addFeedBack= function(fdId){
					Com_OpenWindow('${LUI_ContextPath}/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=edit&type=attend&fdId='+fdId,'_blakk');
					$dialog.hide();
				};
			});
		</script >
	</template:replace>
</template:include>