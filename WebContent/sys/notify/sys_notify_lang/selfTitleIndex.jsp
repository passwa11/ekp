<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<template:replace name="title">${ lfn:message('sys-notify:sysNotifySelfTitleSetting.self.notify') }</template:replace>
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdBundle" ref="criterion.sys.docSubject" title="${lfn:message('sys-notify:sysNotifySelfTitleSetting.fdBundle') }"></list:cri-ref>
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
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">

						<list:sortgroup>
							<list:sort property="docCreateTime" text="${lfn:message('sys-notify:sysNotifyTodoDoneInfo.fdFinishTime') }" group="sort.list" value="down"></list:sort>
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
					<ui:toolbar id="Btntoolbar" count="4">
						<kmss:auth requestURL="/sys/notify/sys_notify_lang/sysNotifyLang.do?method=add">
							<ui:button text="${lfn:message('button.add')}" onclick="addNotifySelfTitle();" order="1" ></ui:button>
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
				{url:'/sys/notify/sys_notify_lang/sysNotifyLang.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdBundle,fdMessage,docCreateTime,operations"></list:col-auto>
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
		 		
				window.setNotifySelfTitle = function(type,id) {
					var changeName;
					var iframeUrl = "/sys/notify/sys_notify_lang/sysNotifyLang.do?method=selfTitleLang&fdId="+id+"&type="+type+"&idType=self";
					iframeUrl = '<c:url value="'+iframeUrl+'"/>';
					var title = '<bean:message bundle="sys-notify" key="sysNotifySelfTitleSetting.set.lang.title"/>' ;
					dialog.iframe(iframeUrl, title, function(data) {
						if (null != data && undefined != data) {
							$.ajax({
								url:"${KMSS_Parameter_ContextPath}sys/notify/sys_notify_lang/sysNotifyLang.do?method=saveTitleLang&fdId="+id+"&type="+type+"&idType=self",
								type: 'POST',
								data:data,
								dataType: 'json',
								async:true, 
								success: function(data){
									alert('<bean:message bundle="sys-notify" key="sysNotifySelfTitleSetting.set.title.success"/>');
								}
						   }); 
			
						}
					}, {
						width : 700,
						height : 800
					});
				}
				
				window.addNotifySelfTitle = function() {
					var changeName;
					var iframeUrl = "/sys/notify/sys_notify_lang/sysNotifyLang.do?method=addSelfTitleLang";
					iframeUrl = '<c:url value="'+iframeUrl+'"/>';
					var title = '<bean:message bundle="sys-notify" key="sysNotifySelfTitleSetting.set.lang.title"/>' ;
					dialog.iframe(iframeUrl, title, function(data) {
						if (null != data && undefined != data) {
							$.ajax({
								url:"${KMSS_Parameter_ContextPath}sys/notify/sys_notify_lang/sysNotifyLang.do?method=addTitleLang",
								type: 'POST',
								data:data,
								dataType: 'json',
								async:true, 
								success: function(data){
									alert('<bean:message bundle="sys-notify" key="sysNotifySelfTitleSetting.add.title.success"/>');
									topic.publish("list.refresh");
								}
						   }); 
			
						}
					}, {
						width : 700,
						height : 800
					});
				}
		 		
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
				// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/notify/sys_notify_lang/sysNotifyLang.do" />?method=add');
		 		};
		 		// 编辑
		 		window.edit = function(id) {
		 			Com_OpenWindow('<c:url value="/sys/notify/sys_notify_lang/sysNotifyLang.do" />?method=edit&fdId=' + id);
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
					var url  = '<c:url value="/sys/notify/sys_notify_lang/sysNotifyLang.do?method=deleteall"/>';
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
