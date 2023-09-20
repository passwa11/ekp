<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdTitle" ref="criterion.sys.docSubject" title="${lfn:message('sys-lbpmservice-support:lbpmSimulationExample.fdTitle') }">
			</list:cri-ref>
		</list:criteria>
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('sys-lbpmservice-support:list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
						<!-- 仿真实例创建时间 -->
						<list:sort property="fdCreateTime" text="${lfn:message('sys-lbpmservice-support:lbpmSimulationExample.fdCreateTime') }" group="sort.list"></list:sort>
						<!-- 仿真实例所属模板名称 -->
						<%-- <list:sort property="fdTemplateName" text="${lfn:message('sys-lbpmservice-support:lbpmSimulationExample.fdTemplateName') }" group="sort.list"></list:sort> --%>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
		</div>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/lbpmservice/support/lbpmSimulationExample.do?method=findList'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			    rowHref="/sys/lbpmservice/support/lbpmSimulationExample.do?method=editExample&fdId=!{fdId}">
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdTemplateName,fdTitle,docCreator.fdName,fdCreateTime,operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 	    // 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/lbpmservice/support/lbpmSimulationExample.do" />?method=editExample&fdId=' + id);
		 		};
		 		//删除
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
					var url = '<c:url value="/sys/lbpmservice/support/lbpmSimulationExample.do" />?method=deleteall';
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
