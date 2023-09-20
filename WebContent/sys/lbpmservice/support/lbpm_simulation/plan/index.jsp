<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 仿真计划 -->
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdPlanName" ref="criterion.sys.docSubject" title="${lfn:message('sys-lbpmservice-support:lbpmSimulationPlan.fdPlanName') }">
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
					${ lfn:message('sys-lbpmservice-support:list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
					<list:sortgroup>
						<!-- 仿真计划创建时间 -->
						<list:sort property="fdCreateTime" text="${lfn:message('sys-lbpmservice-support:lbpmSimulationPlan.fdCreateTime') }" group="sort.list"></list:sort>
						<!-- 仿真计划名称 -->
						<list:sort property="fdPlanName" text="${lfn:message('sys-lbpmservice-support:lbpmSimulationPlan.fdPlanName') }" group="sort.list"></list:sort>
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
					<ui:toolbar id="Btntoolbar">
						<ui:button text="${lfn:message('button.add')}"  onclick="add();" order="1" ></ui:button>
						<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/lbpm/lbpm_simulation_plan/lbpmSimulationPlan.do?method=findList'}
			</ui:source>
			<!-- 默认点击列表请求查看页面 -->
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			    rowHref="/sys/lbpm/lbpm_simulation_plan/lbpmSimulationPlan.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdPlanName,docCreator.fdName,fdCreateTime,operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 	    
		 		// 新建申请
		 		window.addDoc = function(fdId) {
		 			Com_OpenWindow('<c:url value="/sys/lbpm/lbpm_simulation_plan/lbpmSimulationPlan.do" />?method=add&fdId=' + fdId);
		 		};
		 		// 新建申请
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/lbpm/lbpm_simulation_plan/lbpmSimulationPlan.do" />?method=add');
		 		};
		 		// 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/lbpm/lbpm_simulation_plan/lbpmSimulationPlan.do" />?method=edit&fdId=' + id);
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
					var url = '<c:url value="/sys/lbpm/lbpm_simulation_plan/lbpmSimulationPlan.do" />?method=deleteall';
					dialog.confirm('<bean:message bundle="sys-lbpmservice-support" key="page.comfirmDelete"/>',function(value){
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
