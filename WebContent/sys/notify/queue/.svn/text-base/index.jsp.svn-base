<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-notify:table.sysNotifyQueueError') }</template:replace>
	<template:replace name="content">
		<div style="padding: 5px 10px;">
		<!-- 筛选器 -->
		<list:criteria id="criteria1" multi="false">
			<%-- <list:cri-ref key="fdSubject" ref="criterion.sys.docSubject" title="${lfn:message('sys-notify:sysNotifyQueueError.fdSubject') }"></list:cri-ref> --%>
			
			<!-- 模块 -->
			<list:cri-criterion title="${ lfn:message('sys-notify:sysNotifyTodo.module')}" key="fdModelName">
				<list:box-select>
					<list:item-select id="fdModelNameCriterion">
						<ui:source type="AjaxJson">
							{url:'/sys/notify/queue/sysNotifyQueueError.do?method=getAppModules'}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<!-- 消息类型 -->
			<list:cri-criterion title="${ lfn:message('sys-notify:sysNotifyQueueError.fdType')}" key="fdType">
				<list:box-select>
					<list:item-select id="fdTypeCriterion">
						<ui:source type="AjaxJson">
							{url:'/sys/notify/queue/sysNotifyQueueError.do?method=getMessageTypes'}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
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
						<list:sort property="fdTime" text="${lfn:message('sys-notify:sysNotifyQueueError.fdTime') }" group="sort.list" value="down"></list:sort>
						<list:sort property="fdType" text="${lfn:message('sys-notify:sysNotifyQueueError.fdType') }" group="sort.list"></list:sort>
						<list:sort property="fdMethodType" text="${lfn:message('sys-notify:sysNotifyQueueError.fdMethodType') }" group="sort.list"></list:sort>
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
						<kmss:auth requestURL="/sys/notify/queue/sysNotifyQueueError.do?method=runAll" requestMethod="POST">
							<!-- 运行 -->
							<ui:button text="${lfn:message('sys-notify:sysNotifyQueueError.run')}" onclick="run()" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/notify/queue/sysNotifyQueueError.do?method=deleteall" requestMethod="POST">
							<!-- 删除 -->
							<ui:button text="${lfn:message('button.deleteall')}" onclick="del()" order="2" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/notify/queue/sysNotifyQueueError.do?method=list'}
			</ui:source>
			<!-- 开启三员后，列表不可点击查看详细页面-->
			<% if(TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" >
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="title,modelText,fdType,fdMethodType,fdTime,fdUser,doc,operations"></list:col-auto>
			</list:colTable>
			<% } else { %>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/notify/queue/sysNotifyQueueError.do?method=viewPage&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="title,modelText,fdType,fdMethodType,fdTime,fdUser,doc,operations"></list:col-auto>
			</list:colTable>
			<% } %>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	</div>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});

				// 运行
		 		window.run = function(id) {
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
					var url  = '<c:url value="/sys/notify/queue/sysNotifyQueueError.do?method=runAll"/>';
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
					var url  = '<c:url value="/sys/notify/queue/sysNotifyQueueError.do?method=deleteall"/>';
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
