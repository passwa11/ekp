<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-unit:kmImissiveUnit.fdName') }">
			</list:cri-ref>
			<list:cri-auto modelName="com.landray.kmss.sys.unit.model.KmImissiveUnit" property="fdNature" expand="true"/>
			<list:cri-criterion title="${ lfn:message('sys-unit:kmImissiveUnit.fdIsAvailable')}" key="fdIsAvailable" multi="false" >
				<list:box-select>
					<list:item-select cfg-defaultValue="1">
						<ui:source type="Static">
							[{text:'${ lfn:message('message.yes')}', value:'1'},
							{text:'${ lfn:message('message.no')}',value:'0'}]
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
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
					    <list:sortgroup>
						    <list:sort property="fdOrder" text="${lfn:message('sys-unit:kmImissiveUnit.fdOrder') }" group="sort.list" value="up"></list:sort>
							<list:sort property="docCreateTime" text="${lfn:message('sys-unit:kmImissiveUnit.docCreateTime') }" group="sort.list"></list:sort>
							<list:sort property="fdName" text="${lfn:message('sys-unit:kmImissiveUnit.fdName') }" group="sort.list"></list:sort>
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
					    <kmss:auth requestURL="/sys/unit/km_imissive_unit/kmImissiveUnit.do?method=add" requestMethod="GET">
					        <ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>
					        <ui:button text="${lfn:message('sys-unit:button.copy.unit')}" onclick="copyUnit();" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/unit/km_imissive_unit/kmImissiveUnit.do?method=deleteall" requestMethod="GET">
						    <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
							<!-- 快速排序 -->
							<c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.sys.unit.model.KmImissiveUnit"></c:param>
								<c:param name="property" value="fdOrder"></c:param>
							</c:import>
							<ui:button text="${lfn:message('sys-unit:button.invalid.unit')}" onclick="InvalidBatch();" order="2" ></ui:button>
							<ui:button text="${lfn:message('sys-unit:button.transfer.unit')}" onclick="TransferBatch();" order="2" ></ui:button>
						</kmss:auth>
						<ui:button text="${lfn:message('button.export')}" onclick="exportUnits();" order="3" ></ui:button>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/unit/km_imissive_unit/kmImissiveUnit.do?method=list&parentId=${param.parentId}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			     rowHref="/sys/unit/km_imissive_unit/kmImissiveUnit.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdOrder,fdName,fdCategory.fdName,fdShortName,fdNature,docCreator.fdName,docCreateTime,fdIsAvailable,operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/unit/km_imissive_unit/kmImissiveUnit.do" />?method=add&parentId=${param.parentId}');
		 		};
		 	// 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/unit/km_imissive_unit/kmImissiveUnit.do" />?method=edit&fdId=' + id);
		 		};
		 		
		 		window.exportUnits = function(){
		 			var values = [];
					$("input[name='List_Selected']:checked").each(function() {
						values.push($(this).val());
					});
					if (values.length == 0) {
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url = '${LUI_ContextPath}/sys/unit/km_imissive_unit/kmImissiveUnit.do?method=exportUnits&ids='+values;
					Com_OpenWindow(url);
		 		}
		 		
		 		window.deleteAll = function(id) {
					var values = [];
					if(id){
						values.push(id);
					}else{
					    $("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					
					var url = '<c:url value="/sys/unit/km_imissive_unit/kmImissiveUnit.do?method=deleteall"/>';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value) {
						if (value == true) {
							window.del_load = dialog.loading();
							$.ajax({
								url : url,
								type : 'POST',
								data : $.param({"List_Selected" : values},true),
								dataType : 'json',
								error : function(data) {
									if (window.del_load != null) {
										window.del_load.hide();
									}
									dialog.result(data.responseJSON);
								},
								success : delCallback
							});
						}
					});
				};

				window.InvalidBatch = function() {
					var values = [];

					$("input[name='List_Selected']:checked").each(function(){
						values.push($(this).val());
					});
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url = '<c:url value="/sys/unit/km_imissive_unit/kmImissiveUnit.do?method=updateInvalidBatch"/>';
					dialog.confirm('您确定要将所选的记录设置为无效吗？',function(value) {
						if (value == true) {
							window.del_load = dialog.loading();
							$.ajax({
								url : url,
								type : 'POST',
								data : $.param({"List_Selected" : values},true),
								dataType : 'json',
								error : function(data) {
									if (window.del_load != null) {
										window.del_load.hide();
									}
									dialog.result(data.responseJSON);
								},
								success : delCallback
							});
						}
					});
				};

				window.TransferBatch = function() {
					var values = [];

					$("input[name='List_Selected']:checked").each(function(){
						values.push($(this).val());
					});
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url = '<c:url value="/sys/unit/km_imissive_unit/kmImissiveUnit.do?method=updateTransferBatch"/>';

					dialog.tree('kmImissiveUnitCategoryInnerTreeService&parentId=!{value}','<bean:message  bundle="sys-category" key="dialog.window.title"/>',function(rtn){
						if(rtn){
							var idString = rtn.value;
							if(idString!=""){
								window.del_load = dialog.loading();
								$.ajax({
									url : url,
									type : 'POST',
									data : $.param({"List_Selected" : values,"categoryId":idString},true),
									dataType : 'json',
									error : function(data) {
										if (window.del_load != null) {
											window.del_load.hide();
										}
										dialog.result(data.responseJSON);
									},
									success : delCallback
								});
							}
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
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				// 复制单位
				window.copyUnit = function() {
					
					var selectItem =  $("input[name='List_Selected']:checked");
					if (selectItem.length == 0) {
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					} else if (selectItem.length > 1) {
						dialog.alert('<bean:message key="error.select.message.copy" bundle="sys-unit"/>');
						return;
					}
					
					var copyUrl = "${LUI_ContextPath}/sys/unit/km_imissive_unit/kmImissiveUnit.do?method=copy";
					var unitId = selectItem.val();
					url = Com_SetUrlParameter(copyUrl, "fdCopyId", unitId);
					Com_OpenWindow(url);
				}
		 	});
	 	</script>
	</template:replace>
</template:include>
