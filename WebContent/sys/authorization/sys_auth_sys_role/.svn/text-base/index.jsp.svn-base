<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-authorization:authorization.moduleName') }</template:replace>
	<template:replace name="content">
		
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
						<list:sort property="fdName" text="${lfn:message('sys-authorization:sysAuthRole.fdName') }" group="sort.list" value="down"></list:sort>
						<list:sort property="fdType" text="${lfn:message('sys-authorization:sysAuthRole.fdType') }" group="sort.list"></list:sort>
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
						<kmss:auth requestURL="/sys/authorization/sys_auth_sys_role/sysAuthSysRole.do?method=add">
							<!-- 导入系统权限 -->
							<ui:button text="${lfn:message('sys-authorization:sysAuthArea.ImportSystemPermissions') }" onclick="_import()" order="1" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/authorization/sys_auth_sys_role/sysAuthSysRole.do?method=list&modulePath=${JsParam.modulePath}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/authorization/sys_auth_sys_role/sysAuthSysRole.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdName,fdType,fdDescription,operations"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
			 	// 导入系统权限
		 		window._import = function() {
		 			window.del_load = dialog.loading();
					$.ajax({
						url : '<c:url value="/sys/authorization/sys_auth_sys_role/sysAuthSysRole.do" />?method=systemInit',
						type : 'POST',
						dataType : 'json',
						error : function(data) {
							if(window.del_load != null) {
								window.del_load.hide(); 
							}
							dialog.result(data.responseJSON);
						},
						success: function(data) {
							// 这里只显示操作成功，不显示具体的操作内容项
							data.message = undefined;
							if(window.del_load != null){
								window.del_load.hide(); 
								topic.publish("list.refresh");
							}
							dialog.result(data);
						}
				   });
		 		};
		 		// 查看引用
		 		window.reference = function(id) {
		 			Com_OpenWindow('<c:url value="/sys/authorization/sys_auth_research/sysAuthResearch.do?method=researchRole"/>&roleId=' + id);
		 		};
		 		// 用户指派
		 		window.assignment = function(id) {
		 			Com_OpenWindow('<c:url value="/sys/authorization/sys_auth_sys_role/sysAuthSysRole.do?method=edit"/>&fdId=' + id);
		 		};
		 	});
	 	</script>
	</template:replace>
</template:include>
