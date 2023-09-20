<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('kms-log:kmsLogApp.search.org') }</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-auto modelName="com.landray.kmss.kms.log.model.KmsLogApp"
			property="fdCreateTime;fdOperatorName" />
		</list:criteria>
		
 		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 排序 -->
<%-- 			<div style='color: #979797;float: left;padding-top:1px;'>
				${ lfn:message('list.orderType') }：
			</div>
			<div style="float:left">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
						<list:sort property="fdName" text="${lfn:message('sys-transport:sysTransportExportConfig.fdName') }" group="sort.list" value="down"></list:sort>
						<list:sort property="createTime" text="${lfn:message('sys-transport:sysTransportExportConfig.createTime') }" group="sort.list"></list:sort>
					</ui:toolbar>
				</div>
			</div> --%>
			<!-- mini分页 -->
			<div style="float:left;">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>  
			<!-- 操作按钮 -->
<%-- 			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar">
						<kmss:auth requestURL="/sys/transport/sys_transport_export/SysTransportExport.do?method=add&fdModelName=${param.fdModelName}">
							<!-- 增加 -->
							<ui:button text="${lfn:message('button.add')}" onclick="add()" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/transport/sys_transport_export/SysTransportExport.do?method=deleteall&fdModelName=${param.fdModelName}">
							<!-- 删除 -->
							<ui:button text="${lfn:message('button.deleteall')}" onclick="del()" order="2" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div> --%>
		</div> 
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/kms/log/kms_log_app/kmsLogApp.do?method=listMonth'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" >
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdCreateTime,fdOperatorName,operateText,moduleName,fdIp,fdSubject,fdTargetId"></list:col-auto>
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
		 			Com_OpenWindow('<c:url value="/sys/transport/sys_transport_export/SysTransportExport.do" />?method=add&fdModelName=${JsParam.fdModelName}');
		 		};
		 		// 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/transport/sys_transport_export/SysTransportExport.do" />?method=edit&fdId=' + id + '&fdModelName=${JsParam.fdModelName}');
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
					var url  = '<c:url value="/sys/transport/sys_transport_export/SysTransportExport.do"/>?method=deleteall&fdModelName=${JsParam.fdModelName}';
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
