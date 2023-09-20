<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdSearchName" ref="criterion.sys.docSubject" title='${lfn:message("km-calendar:sysCalendarShareGroup.seachText") }' style="width: 350px;">
			</list:cri-ref>
		</list:criteria>
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/km/calendar/km_calendar_auth/kmCalendarAuth.do?method=manageList'}
			</ui:source>
			<%-- <list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			    rowHref="/km/calendar/km_calendar_auth/kmCalendarAuth.do?method=manageView&fdId=!{fdId}"> --%>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" onRowClick="openDialog('!{fdId}','!{fdName}','!{fdParent.fdName}');">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdParent.fdName,fdName,fdLoginName"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
	 		seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'],function($,dialog,topic){
	 			
	 			window.openDialog = function(openId, name, dept){
	 				var url="/km/calendar/km_calendar_auth_list/kmCalendarAuthList_dialog.jsp?fdPersonId=" + openId;
					dialog.iframe(url, '<span style="width: 600px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;display: block;">' 
						+ '<bean:message bundle="km-calendar" key="kmCalendar.setting.authSetting"/>' 
						+ '（<bean:message bundle="km-calendar" key="kmCalendar.authSetting.personName"/>' + name 
						+ '；<bean:message bundle="km-calendar" key="kmCalendar.authSetting.deptName"/>' + dept + '）</span>', 
						function(arg){
						
					},{width:800,height:550});
				};
				
	 		});
	 	</script>
	</template:replace>
</template:include>

<ui:top id="top"></ui:top>
<kmss:ifModuleExist path="/sys/help">
	<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
</kmss:ifModuleExist>