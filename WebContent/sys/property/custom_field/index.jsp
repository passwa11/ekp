<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.list">
	<template:replace name="content">
		<p style="margin: 5px 0;"><font color="red">${ lfn:message('sys-property:custom.field.updateMapping.tips') }</font></p>
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdSearchName" ref="criterion.sys.docSubject" title="${ lfn:message('sys-property:custom.field.filter.fdSearchName') }" style="width: 460px;"></list:cri-ref>
			<list:cri-criterion title="${ lfn:message('sys-property:custom.field.status') }" key="status" multi="false">
				<list:box-select>
					<list:item-select cfg-defaultValue="true">
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-property:custom.field.status.false') }',value:'false'},
							{text:'${ lfn:message('sys-property:custom.field.status.true') }',value:'true'}]
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
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="3">
						<list:sortgroup>
							<list:sort property="order" text="${ lfn:message('sys-property:custom.field.order') }" group="sort.list" value="up"></list:sort>
							<list:sort property="fieldName" text="${lfn:message('sys-property:custom.field.fieldName') }" group="sort.list"></list:sort>
							<list:sort property="columnName" text="${ lfn:message('sys-property:custom.field.columnName') }" group="sort.list"></list:sort>
							<list:sort property="fieldText" text="${ lfn:message('sys-property:custom.field.fieldTexts') }" group="sort.list"></list:sort>
							<list:sort property="fieldType" text="${ lfn:message('sys-property:custom.field.fieldType') }" group="sort.list"></list:sort>
							<list:sort property="status" text="${ lfn:message('sys-property:custom.field.status') }" group="sort.list"></list:sort>
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
					<ui:toolbar id="Btntoolbar" count="5" cfg-dataInit="false">
						<ui:button text="${lfn:message('button.add')}" onclick="add()" order="1" ></ui:button>
						<ui:button text="${ lfn:message('sys-property:custom.field.status.bath.true') }" onclick="changeStatus('true')" order="2" ></ui:button>
						<ui:button text="${ lfn:message('sys-property:custom.field.status.bath.false') }" onclick="changeStatus('false')" order="3" ></ui:button>
						<ui:button text="${ lfn:message('sys-property:custom.field.updateMapping') }" onclick="updateMapping()" order="4" ></ui:button>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/property/custom_field/customField.do?method=list&modelName=${JsParam.modelName}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" >
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props=""></list:col-auto>
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
		 			Com_OpenWindow('<c:url value="/sys/property/custom_field/customField.do" />?method=add&modelName=${JsParam.modelName}&s_path=' + encodeURIComponent('${JsParam.s_path}'));
		 		};
		 		// 编辑
		 		window.edit = function(fieldName) {
		 			Com_OpenWindow('<c:url value="/sys/property/custom_field/customField.do" />?method=edit&fieldName='+fieldName+'&modelName=${JsParam.modelName}&s_path=' + encodeURIComponent('${JsParam.s_path}'));
		 		};
		 		// 修改状态
		 		window.changeStatus = function(status, fieldName) {
		 			var fieldNames = [];
		 			if(fieldName) {
		 				fieldNames.push(fieldName);
		 			} else {
						$("input[name='List_Selected']:checked").each(function(){
							fieldNames.push($(this).val());
						});
		 			}
		 			if(fieldNames.length==0) {
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
		 			var msg;
		 			if("true" == status) {
		 				msg = '<bean:message bundle="sys-property" key="custom.field.changeStatus.confirm.true"/>';
		 			} else {
		 				msg = '<bean:message bundle="sys-property" key="custom.field.changeStatus.confirm.false"/>';
		 			}
		 			dialog.confirm(msg,function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: '<c:url value="/sys/property/custom_field/customField.do" />?method=changeStatus&modelName=${JsParam.modelName}',
								type: 'POST',
								data:$.param({"fieldNames":fieldNames.join(","), status:status},true),
								dataType: 'json',
								success: function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
										topic.publish("list.refresh");
									}
								}
						   });
						}
					});
		 		}
		 		// 更新hibernate缓存
		 		window.updateMapping = function() {
		 			dialog.confirm('<bean:message bundle="sys-property" key="custom.field.updateMapping.info"/>', function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: '<c:url value="/sys/property/custom_field/customField.do" />?method=updateMapping',
								type: 'GET',
								dataType: 'json',
								success: function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
									}
									dialog.alert('<bean:message bundle="sys-property" key="custom.field.updateMapping.success"/>');
								}
						   });
						}
					});
		 		}
		 	});
	 	</script>
	</template:replace>
</template:include>
