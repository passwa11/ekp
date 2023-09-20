<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="title">
		<c:out value="${ lfn:message('hr-staff:module.hr.staff') }"></c:out>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:module.hr.staff') }" href="/hr/staff/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:table.hrStaffEntry')}"></ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript" src="${LUI_ContextPath}/hr/staff/resource/js/hrImageUtil.js?s_cache=${LUI_Cache}"></script>
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/hr/staff/resource/css/hr_box.css?s_cache=${LUI_Cache}">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
			<list:cri-auto modelName="com.landray.kmss.hr.staff.model.HrStaffEntry" property="fdName" />
			<list:cri-auto modelName="com.landray.kmss.hr.staff.model.HrStaffEntry" property="fdMobileNo" />
			<list:cri-auto modelName="com.landray.kmss.hr.staff.model.HrStaffEntry" property="fdPlanEntryTime" />
			<list:cri-auto modelName="com.landray.kmss.hr.staff.model.HrStaffEntry" property="fdPlanEntryDept" />
			<list:cri-criterion title="${ lfn:message('hr-staff:hrStaffEntry.fdDataFrom') }" key="fdDataFrom">
				<list:box-select>
					<list:item-select cfg-enable="true">
						<ui:source type="Static">
							[{text:'${ lfn:message('hr-staff:hrStaffEntry.fdDataFrom.1') }', value:'1'},
							{text:'${ lfn:message('hr-staff:hrStaffEntry.fdDataFrom.2') }',value:'2'}]
						</ui:source>
					</list:item-select>
				</list:box-select> 
			</list:cri-criterion>
			<list:cri-auto modelName="com.landray.kmss.hr.staff.model.HrStaffEntry" property="fdAlteror" />
			<list:cri-auto modelName="com.landray.kmss.hr.staff.model.HrStaffEntry" property="fdLastModifiedTime" />
		</list:criteria>
		<!-- 排序 -->
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
					<list:sortgroup>
					    <list:sort property="fdLastModifiedTime" text="${lfn:message('hr-staff:hrStaffEntry.fdLastModifiedTime') }" group="sort.list" value="up"></list:sort>
					</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top" >
				</list:paging>
			</div>
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar count="4">
						<kmss:authShow roles="ROLE_HRSTAFF_DELETE">
							<ui:button text="${lfn:message('button.deleteall') }" onclick="del();" order="1"></ui:button>
						</kmss:authShow>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<!-- 列表 -->
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/hr/staff/hr_staff_entry/hrStaffEntry.do?method=list&q.fdStatus=2'}
			</ui:source>
			<!-- 列表视图 -->	
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="/hr/staff/hr_staff_entry/hrStaffEntry.do?method=view&fdId=!{fdId}" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial> 
				<list:col-auto props="fdName;fdDataFrom;fdLastModifiedTime;fdAlteror;fdStatus;fdCheckDate;fdChecker"></list:col-auto>
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
					var url  = '<c:url value="/hr/staff/hr_staff_entry/hrStaffEntry.do?method=deleteall"/>';
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
			});
		</script>
	</template:replace>
</template:include>