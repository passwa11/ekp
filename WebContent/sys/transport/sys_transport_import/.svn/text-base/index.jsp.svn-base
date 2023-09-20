<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-transport:table.sysTransportImportConfig') }</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<%String arg= request.getParameter("type");
		  if(arg!=null){
	       String[] type=request.getParameter("type").split(","); 
	   	   request.setAttribute("type", type);
		}
		
	
		
		%>
		
		<list:criteria id="criteria1">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-transport:sysTransportImportConfig.fdName') }"></list:cri-ref>
			<c:if test="${fn:length(type)!=1||empty HtmlParam.type}">
			<list:cri-criterion title="${ lfn:message('sys-transport:sysTransportImportConfig.fdImportType')}" key="importType"> 
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
						[
						<c:if test="${fn:contains(HtmlParam.type, '1')||empty HtmlParam.type}">
							{text:'${ lfn:message('sys-transport:fdImportType.add')}', value:'1'},
						</c:if>
						<c:if test="${fn:contains(HtmlParam.type, '2')||empty HtmlParam.type}">
							{text:'${ lfn:message('sys-transport:fdImportType.update')}',value:'2'},
							</c:if>
						<c:if test="${fn:contains(HtmlParam.type, '3')||empty HtmlParam.type}">
							{text:'${ lfn:message('sys-transport:fdImportType.addOrUpdate')}',value:'3'}
							</c:if>]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			</c:if>
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
							<list:sort property="createTime" text="${lfn:message('sys-transport:sysTransportImportConfig.createTime') }" group="sort.list" value="down"></list:sort>
							<list:sort property="fdName" text="${lfn:message('sys-transport:sysTransportImportConfig.fdName') }" group="sort.list"></list:sort>
							<list:sort property="fdImportType" text="${lfn:message('sys-transport:sysTransportImportConfig.fdImportType') }" group="sort.list"></list:sort>
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
						<kmss:auth requestURL="/sys/transport/sys_transport_import/SysTransportImport.do?method=add&fdModelName=${param.fdModelName}">
							<!-- 增加 -->
							<ui:button text="${lfn:message('button.add')}" onclick="add()" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/transport/sys_transport_import/SysTransportImport.do?method=deleteall&fdModelName=${param.fdModelName}">
							<!-- 删除 -->
							<ui:button text="${lfn:message('button.deleteall')}" onclick="del()" order="2" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/transport/sys_transport_import/SysTransportImport.do?method=list&fdModelName=${JsParam.fdModelName}&orderby=createTime&ordertype=down'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/transport/sys_transport_import/SysTransportImport.do?method=view&fdId=!{fdId}&fdModelName=${JsParam.fdModelName}&type=${JsParam.type}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdName,fdImportType,creator,createTime,operations"></list:col-auto>
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
		 			Com_OpenWindow('<c:url value="/sys/transport/sys_transport_import/SysTransportImport.do" />?method=add&fdModelName=${JsParam.fdModelName}&type=${JsParam.type}');
		 		};
		 		// 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/transport/sys_transport_import/SysTransportImport.do" />?method=edit&fdId=' + id + '&fdModelName=${JsParam.fdModelName}&type=${JsParam.type}');
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
					var url  = '<c:url value="/sys/transport/sys_transport_import/SysTransportImport.do?method=deleteall&fdModelName=${JsParam.fdModelName}"/>';
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
