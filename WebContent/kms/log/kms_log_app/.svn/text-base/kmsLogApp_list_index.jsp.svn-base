<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('kms-log:kmsLogApp.search.org') }</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-criterion title="${ lfn:message('kms-log:kmsLogApp.fdCreateTime') }" multi="false" expand="false" key="fdCreateTime">
				<list:varDef name="selectBox">
				<list:box-select>
					<list:item-select 
						type="kms/log/kms_log_app/js/criterion_calendar!CriterionDateDatas" >
					</list:item-select>
				</list:box-select>
				</list:varDef>
			</list:cri-criterion>
			<list:cri-ref key="fdOperator" ref="criterion.sys.person" multi="false"
				title="${lfn:message('kms-log:kmsLogAppHistory.fdOperator') }">
			</list:cri-ref>
			<list:cri-criterion
					title="${lfn:message('kms-log:kmsLogApp.moduleName')}"
					key="moduleKey" multi="true">
				<list:box-select>
					<list:item-select>
						<ui:source type="AjaxJson">
							{url:'/kms/log/kms_log_app/kmsLogApp.do?method=queryModuleKey4query'}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-criterion
				title="${lfn:message('kms-log:kmsLogApp.CommonOperation')}"
				key="fdOprateMethod" multi="true">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('kms-log:kmsLogType.correction') }', value:'correction'},
							{text:'${ lfn:message('kms-common:kmsShareLog.readRecord') }', value:'addShareRecord'},
							{text:'${ lfn:message('kms-log:kmsLogType.downloadAtt') }', value:'downloadAtt'},
							{text:'${ lfn:message('kms-log:kmsLog.button.bookMark') }', value:'bookmark'},
							{text:'${ lfn:message('kms-log:kmsLogType.evaluation') }', value:'evaluation'},
							{text:'${ lfn:message('kms-log:kmsLogType.introduce') }', value:'introduce'},
							{text:'${ lfn:message('kms-log:kmsLog.button.praise') }', value:'praise'},
							{text:'${ lfn:message('kms-log:kmsLogType.read') }', value:'read'},
							{text:'${ lfn:message('sys-right:right.button.doc.changeRight.view') }', value:'updateDocRight'},
							{text:'${ lfn:message('sys-right:right.button.category.changeRight.view') }', value:'updateCateRight'},
							{text:'${ lfn:message('sys-simplecategory:sysSimpleCategory.chg.button') }', value:'updateChgCate'},
							{text:'${ lfn:message('kms-log:kmsLog.button.editTag') }', value:'updateTag'}
							]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
		</list:criteria>
		
 		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 排序 -->
 
			<!-- mini分页 -->
			<div style="float:left;">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>  
			 
		</div> 
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/kms/log/kms_log_app/kmsLogApp.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" >
				
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
