<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-cluster:sysClusterGroup.fdName') }">
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
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar" cfg-dataInit="false">
					    <ui:button text="${lfn:message('sys-cluster:sysClusterDispatcher.setting')}"  onclick="setting();" order="1" ></ui:button>
					    <ui:button text="${lfn:message('sys-cluster:sysClusterGroup.sync')}"  onclick="syncGroup();" order="1" ></ui:button>
						<ui:button text="${lfn:message('button.add')}"  onclick="add();" order="1" ></ui:button>
						<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/cluster/sys_cluster_group/sysClusterGroup.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			    rowHref="/sys/cluster/sys_cluster_group/sysClusterGroup.do?method=view&fdId=!{fdId}">
			    <list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdOrder,fdName,fdKey,fdUrl,fdMaster,fdLocal"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	<br>
		说明：更新子系统信息以后，或者重新设置子系统功能以后，在各个子系统已经启动的情况下，可以通过“同步”功能，将子系统的配置信息同步到其它子系统<br>
		当中心服务发生变更（包括修改URL等），请务必点击“同步”功能，将配置信息刷新到各个子系统中，否则将会出现系统功能异常
	 	
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/cluster/sys_cluster_group/sysClusterGroup.do" />?method=add&parentId=${param.parentId}');
		 		};
		 	    // 设置调度服务运行地址
		 		window.setting = function(id) {
		 			Com_OpenWindow('<c:url value="/sys/cluster/sys_cluster_group_func/sysClusterGroupFunc.do" />?method=list');
		 		};
		 		window.deleteAll = function(id){
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
					var url = '<c:url value="/sys/cluster/sys_cluster_group/sysClusterGroup.do?method=deleteall"/>';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'POST',
								data:$.param({"List_Selected":values},true),
								dataType: 'json',
								error: function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: delCallback
						   });
						}
					});
				};
				window.delCallback = function(data){
					if(window.del_load!=null){
						window.del_load.hide();
						topic.publish("list.refresh");
					}
					dialog.result(data);
				};
				
				window.syncGroup = function(id){
					var values = [];
					$("input[name='List_Selected']:checked").each(function() {
						values.push($(this).val());
					});
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url = '<c:url value="/sys/cluster/sys_cluster_group/sysClusterGroup.do?method=syncGroup"/>';
					dialog.confirm('<bean:message bundle="sys-cluster" key="sysClusterGroup.sync.confirm"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'POST',
								data:$.param({"List_Selected":values},true),
								dataType: 'json',
								error: function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: syncCallback
						   });
						}
					});
				};
				window.syncCallback = function(data){
					if(window.del_load!=null){
						window.del_load.hide();
						topic.publish("list.refresh");
					}
					dialog.result(data);
				};
		 	});
	 	</script>
	</template:replace>
</template:include>
