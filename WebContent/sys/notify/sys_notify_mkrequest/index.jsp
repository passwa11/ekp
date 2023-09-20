<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<template:replace name="title">${ lfn:message('sys-notify:table.sysNotifyMKRequest') }</template:replace>
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdModelId" ref="criterion.sys.docSubject" 
			title="ModelId"></list:cri-ref>
			<list:cri-ref key="fdModelName" ref="criterion.sys.string" 
			title="ModelName"></list:cri-ref>
			<list:cri-ref key="fdTraceId" ref="criterion.sys.string" 
			title="${lfn:message('sys-notify:sysNotifyMKRequest.fdTraceId') }"></list:cri-ref>
			<list:cri-ref key="fdCreateTime" ref="criterion.sys.calendar" 
			title="${lfn:message('sys-notify:sysNotifyMKRequest.fdCreateTime') }"></list:cri-ref>
			<list:cri-ref key="fdUrl" ref="criterion.sys.string" 
			title="${lfn:message('sys-notify:sysNotifyMKRequest.fdUrl') }"></list:cri-ref>
			<list:cri-criterion title="请求状态" key="fdSuccess" expand="false" multi="false">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'成功', value:'true'},{text:'失败',value:'false'}]
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
			
			<!-- 分页 -->	
			<div class="lui_list_operation_page_top">		
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar" count="4">
						<kmss:auth requestURL="/sys/notify/sys_notify_mkrequest/sysNotifyMKRequest.do?method=add">
							<ui:button text="批量重发" onclick="retry();" order="1" ></ui:button>
							<ui:button text="${lfn:message('button.deleteall')}" onclick="del();" order="2" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/notify/sys_notify_mkrequest/sysNotifyMKRequest.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable"
				rowHref="/sys/notify/sys_notify_mkrequest/sysNotifyMKRequest.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdTraceId,fdSuccess,fdModelId,fdModelName,fdCreateTime,fdUrl,operations"></list:col-auto>
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
				// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/notify/sys_notify_mkrequest/sysNotifyMKRequest.do" />?method=add');
		 		};
		 		// 查看
		 		window.view = function(id) {
		 			Com_OpenWindow('<c:url value="/sys/notify/sys_notify_mkrequest/sysNotifyMKRequest.do" />?method=view&fdId=' + id);
		 		};
		 		
		 	    var existSuc = 0;
		 	    
		 		// 重发
		 		window.retry = function(id) {
		 			var values = [];
		 			if(id) {
		 				values.push(id);
			 		} else {
						$("input[name='List_Selected']:checked").each(function() {
							if($(this).parent().parent().find('.btn_txt').length == 1){
								existSuc++;
							}else{
								values.push($(this).val());
							}
						});
			 		}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					
					if(existSuc > 0){
						dialog.alert('<bean:message key="sys-notify:sysNotifyMKRequest.retry.msg"/>!');
						return;
					}
					
					var url  = '<c:url value="/sys/notify/sys_notify_mkrequest/sysNotifyMKRequest.do?method=retryall"/>';
					dialog.confirm('所选记录的相关数据是否存在重复！您确认要执行此重发操作吗？', function(value) {
						if(value == true) {
							window.retry_load = dialog.loading();
							$.ajax({
								url : url,
								type : 'POST',
								data : $.param({"List_Selected" : values}, true),
								dataType : 'json',
								error : function(data) {
									if(window.retry_load != null) {
										window.retry_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: function(data) {
									if(window.retry_load != null){
										window.retry_load.hide(); 
										topic.publish("list.refresh");
									}
									dialog.result(data);
								}
						   });
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
					var url  = '<c:url value="/sys/notify/sys_notify_mkrequest/sysNotifyMKRequest.do?method=deleteall"/>';
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
