<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="title">
		<c:out value="${ lfn:message('hr-staff:module.hr.staff') }"></c:out>
	</template:replace>
	<template:replace name="nav">
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('hr-staff:table.HrStaffPersonInfoLog') }" />
			<ui:varParam name="button">
				[
					{
						"text": "${ lfn:message('hr-staff:table.HrStaffPersonInfoLog') }",
						"href": "javascript:void(0);",
						"icon": "lui_icon_l_icon_26"
					}
				]
			</ui:varParam>			
		</ui:combin>
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				<c:import url="/hr/staff/import/nav.jsp" charEncoding="UTF-8" />
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
		<!-- 排序 -->
		<div class="lui_list_operation">
			<div style='color: #979797;float: left;padding-top:1px;'>
				${ lfn:message('list.orderType') }：
			</div>
			<div style="float:left">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
					    <list:sortgroup>
					    <list:sort property="fdCreateTime" text="${lfn:message('hr-staff:hrStaffPersonInfoLog.fdCreateTime') }" group="sort.list" value="down"></list:sort>
						</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<div style="float:left;">
				<list:paging layout="sys.ui.paging.top" >
				</list:paging>
			</div>
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar count="5">
						<kmss:authShow roles="ROLE_HRSTAFF_LOG_DELETE">
						<ui:button text="${lfn:message('button.deleteall') }" onclick="del();" order="4"></ui:button>
						</kmss:authShow>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<!-- 列表 -->
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/hr/staff/hr_staff_person_info_log/hrStaffPersonInfoLog.do?method=list'}
			</ui:source>
			<!-- 列表视图 -->	
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="/hr/staff/hr_staff_person_info_log/hrStaffPersonInfoLog.do?method=view&fdId=!{fdId}" name="columntable">
				<kmss:authShow roles="ROLE_HRSTAFF_LOG_DELETE">
				<list:col-checkbox></list:col-checkbox>
				</kmss:authShow>
				<list:col-serial></list:col-serial> 
				<list:col-auto props="fdCreateTime;fdIp;fdBrowser;fdEquipment;fdCreator;fdParaMethod;fdDetails"></list:col-auto>
				<kmss:authShow roles="ROLE_HRSTAFF_LOG_DELETE">
				<list:col-auto props="operations"></list:col-auto>
				</kmss:authShow>
			</list:colTable>
		</list:listview> 
		<list:paging></list:paging>
		
		<script type="text/javascript">
		
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					setTimeout(function() {
						seajs.use(['lui/topic'], function(topic) {
							topic.publish('list.refresh');
						});
					}, 100);
				});
				<kmss:authShow roles="ROLE_HRSTAFF_LOG_DELETE">
				// 删除
				window.del = function(id) {
					var values = [];
		 			if(id) {
		 				values.push(id);
			 		} else {
						$("input[name='List_Selected']:checked").each(function() {
							values.push($(this).val());
						});
			 		}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url  = '<c:url value="/hr/staff/hr_staff_person_info_log/hrStaffPersonInfoLog.do?method=deleteall"/>';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
						if(value == true) {
							window.del_load = dialog.loading();
							$.ajax({
								url : url,
								type : 'POST',
								data : $.param({"List_Selected" : values}, true),
								dataType : 'json',
								error : function(data) {
									if(window.del_load != null) {
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: function(data) {
									if(window.del_load != null){
										window.del_load.hide(); 
										topic.publish("list.refresh");
									}
									dialog.result(data);
								}
						   });
						}
					});
				};
				</kmss:authShow>
			});
		</script>
	</template:replace>
</template:include>
