<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-lbpmservice-support:lbpmTools.nodeName.update') }</template:replace>
	<template:replace name="content">
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 操作按钮 -->
			<div style="float:right">
				<ui:button text="${ lfn:message('button.submit') }" onclick="update();" order="1" ></ui:button>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/lbpmservice/support/LbpmToolsAction.do?method=listNotifyType&cateIds=${JsParam.cateIds}&modelNames=${JsParam.modelNames}&templateIds=${JsParam.templateIds}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" rowHref="/sys/lbpmservice/support/LbpmToolsAction.do?method=viewTemp&fdModelId=!{fdModelId}&fdModelName=!{fdModelName}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="subject,notifyType"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
				$("input[name='List_Tongle']").click();
				if(parent.window.del_load != null) {
					parent.window.del_load.hide(); 
				}
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 修改
		 		window.update = function(id) {
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
					var url  = '<c:url value="/sys/lbpmservice/support/LbpmToolsAction.do?method=updateNotifyType&notifyType=${JsParam.notifyType}"/>';
					dialog.confirm('<bean:message bundle="sys-lbpmservice-support" key="lbpmTools.updateMsg" />', function(value) {
						if(value == true) {
							window.update_load = dialog.loading();
							$.ajax({
								url : url,
								type : 'POST',
								data : $.param({"List_Selected" : values}, true),
								dataType : 'json',
								error : function(data) {
									if(window.update_load != null) {
										window.update_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: function(data) {
									if(window.update_load != null){
										window.update_load.hide(); 
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