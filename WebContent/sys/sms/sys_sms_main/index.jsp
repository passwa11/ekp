<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdReceiverNumber" ref="criterion.sys.docSubject" title="${lfn:message('sys-sms:sysSmsMain.fdReceiverNumber') }"></list:cri-ref>
			<list:cri-ref ref="criterion.sys.person" key="receiver" multi="false" title="${ lfn:message('sys-sms:sysSmsMain.fdReceiver') }" />
			<list:cri-auto modelName="com.landray.kmss.sys.notify.model.SysNotifyShortMessageSend" property="fdCreateTime;fdFlag"/>
			<list:cri-criterion title="${lfn:message('sys-notify:sysNotifyTodo.moduleName') }" key="fdModelName" multi="true">
				<list:box-select>
					<list:item-select type="lui/criteria!CriterionSelectDatas"  id="moduleNames">
						<ui:source type="AjaxJson" >
							{url: "/sys/sms/sys_sms_main/sysSmsMain.do?method=getModules"}
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
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="5">
					<list:sortgroup>
						<list:sort property="fdCreateTime" text="${lfn:message('sys-sms:sysSmsMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
						<list:sort property="fdReceiver" text="${lfn:message('sys-sms:sysSmsMain.fdReceiver') }" group="sort.list"></list:sort>
						<list:sort property="fdReceiverNumber" text="${lfn:message('sys-sms:sysSmsMain.fdReceiverNumber') }" group="sort.list"></list:sort>
						<list:sort property="fdFlag" text="${lfn:message('sys-sms:sysSmsMain.docStatus') }" group="sort.list"></list:sort>
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
						<kmss:auth requestURL="/sys/sms/sys_sms_main/sysSmsMain.do?method=add" requestMethod="GET">
							<ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/sms/sys_sms_main/sysSmsMain.do?method=deleteall" requestMethod="GET">
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
				{url:'/sys/sms/sys_sms_main/sysSmsMain.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/sms/sys_sms_main/sysSmsMain.do?method=view&fdId=!{fdId}">
				<kmss:auth requestURL="/sys/sms/sys_sms_main/sysSmsMain.do?method=deleteall" requestMethod="GET">
				<list:col-checkbox></list:col-checkbox>
				</kmss:auth>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdReceiver,fdReceiverNumber,fdSender,fdCreateTime,fdFlag,fdModuleSource,fdScene,operations"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	<script type="text/javascript">
	 	Com_IncludeFile("docutil.js|calendar.js|dialog.js|doclist.js|optbar.js");
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
				// 增加
		 		window.add = function() {
		 			Dialog_PopupWindow(Com_Parameter.ContextPath + 'resource/jsp/frame.jsp?url='+encodeURIComponent('<c:url value="/sys/sms/sys_sms_main/sysSmsMain.do" />?method=add'),'650','600');
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
					var url  = '<c:url value="/sys/sms/sys_sms_main/sysSmsMain.do?method=deleteall"/>';
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
