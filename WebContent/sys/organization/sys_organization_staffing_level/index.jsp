<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.list">
	<template:replace name="title">${ lfn:message('sys-organization:table.sysOrganizationStaffingLevel') }</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-organization:sysOrganizationStaffingLevel.fdName') }"></list:cri-ref>
			<list:cri-auto modelName="com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel" property="fdIsDefault" />
		</list:criteria>
		
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
					<list:sortgroup>
						<list:sort property="fdLevel" text="${lfn:message('sys-organization:sysOrganizationStaffingLevel.fdLevel') }" group="sort.list" value="up"></list:sort>
						<list:sort property="fdName" text="${lfn:message('sys-organization:sysOrganizationStaffingLevel.fdName') }" group="sort.list"></list:sort>					
					</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar" count="5">
						<kmss:auth requestURL="/sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel.do?method=add">
							<!-- 数据导入 -->
							<ui:button text="${lfn:message('sys-organization:sysOrganizationStaffingLevel.import') }" onclick="_import();" order="2"></ui:button>
							<!-- 增加 -->
							<ui:button text="${lfn:message('button.add')}" onclick="add()" order="3" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel.do?method=deleteall">
							<!-- 删除 -->
							<ui:button text="${lfn:message('button.deleteall')}" onclick="del()" order="4" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdName,fdLevel,fdIsDefault,operations"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
		<!-- 数据导出表单 -->
		<form id="exportDataForm" action="<c:url value="/sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel.do?method=exportData" />" method="post">
		</form>
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
				
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel.do" />?method=add');
		 		};
		 		// 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel.do" />?method=edit&fdId=' + id);
		 		};
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
					var url  = '<c:url value="/sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel.do?method=deleteall"/>';
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

				// 导入
				window._import = function() {
					dialog.iframe('/sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel_upload.jsp', 
							'<bean:message key="sys-organization:sysOrganizationStaffingLevel.import.title"/>', function(data) {
							topic.publish('list.refresh');
					}, {
						width : 650,
						height : 400
					});
				};
		 	});
	 	</script>
	</template:replace>
</template:include>