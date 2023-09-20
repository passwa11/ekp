<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.profile.list">
	<template:replace name="content">
		<div style="">
			<!-- 筛选器 -->
			<list:criteria id="criteria">
			     <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${ lfn:message('model.fdName') }">
				</list:cri-ref>
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
								<list:sort property="docCreateTime" text="${lfn:message('model.fdCreateTime') }" group="sort.list"></list:sort>
								<list:sort property="fdName" text="${lfn:message('model.fdName') }" group="sort.list"></list:sort>
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
						<ui:toolbar id="Btntoolbar" count="2">
						<kmss:auth requestURL="/sys/modeling/base/modelingAppCategory.do?method=add" requestMethod="GET">
							<ui:button text="${lfn:message('button.add')}"  onclick="add();" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/modeling/base/modelingAppCategory.do?method=deleteall" requestMethod="GET">
							<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
						</kmss:auth>
						</ui:toolbar>
					</div>
				</div>
			</div>
			<ui:fixed elem=".lui_list_operation"></ui:fixed>
				<!-- 内容列表 -->
				<list:listview>
					<ui:source type="AjaxJson">
						{url:'/sys/modeling/base/modelingAppCategory.do?method=list'}
					</ui:source>
					<list:colTable isDefault="true" layout="sys.ui.listview.columntable" onRowClick="javascript:edit('!{fdId}');">
						<list:col-checkbox></list:col-checkbox>
						<list:col-auto></list:col-auto>
					</list:colTable>
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
		 		
			 	// 增加
		 		window.add = function() {
			 		var url = "/sys/modeling/base/modelingAppCategory.do?method=add";
			 		var height = document.documentElement.clientHeight * 0.78;
					var width = document.documentElement.clientWidth * 0.7;
			 		dialog.iframe(url, "${lfn:message('sys-modeling-base:modeling.category.create')}" , function(){
			 			topic.publish('list.refresh');
			 		} ,{width:width,height : height});
		 			//Com_OpenWindow('<c:url value="/sys/modeling/base/modelingAppCategory.do" />?method=add');
		 		};
		 	    // 编辑
		 		window.edit = function(id) {
		 			var url = "/sys/modeling/base/modelingAppCategory.do?method=edit&fdId=" + id;
		 			var height = document.documentElement.clientHeight * 0.78;
					var width = document.documentElement.clientWidth * 0.7;
			 		dialog.iframe(url, "${lfn:message('sys-modeling-base:modeling.category.edit')}" , function(){
			 			topic.publish('list.refresh');
			 		} ,{width:width,height : height});
		 		};

		 		window.deleteDoc = function(id){
		 			var url = '<c:url value="/sys/modeling/base/modelingAppCategory.do?method=delete"/>';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'GET',
								data:{fdId:id},
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
					var url = '<c:url value="/sys/modeling/base/modelingAppCategory.do?method=deleteall"/>';
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
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
		 	});
	 	</script>
	</template:replace>
</template:include>