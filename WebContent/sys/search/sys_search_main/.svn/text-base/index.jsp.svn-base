<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-search:search.mechanism') }</template:replace>
	<c:if test='<%="true".equals(SysFormDingUtil.getEnableDing())%>'>
		<template:replace name="head">
			<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/ui/extend/theme/default/style/ding_list.css?s_cache=${LUI_Cache }"/>
		</template:replace>
	</c:if>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-search:sysSearchMain.fdName') }"></list:cri-ref>
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
						<list:sort property="fdOrder" text="${lfn:message('sys-search:sysSearchMain.fdOrder') }" group="sort.list" value="up"></list:sort>
						<list:sort property="fdName" text="${lfn:message('sys-search:sysSearchMain.fdName') }" group="sort.list" ></list:sort>
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
						<kmss:auth requestURL="/sys/search/sys_search_main/sysSearchMain.do?method=add&fdModelName=${JsParam.fdModelName}">
							<!-- 增加 -->
							<ui:button text="${lfn:message('button.add')}" onclick="add()" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/search/sys_search_main/sysSearchMain.do?method=deleteall&fdModelName=${JsParam.fdModelName}">
							<!-- 删除 -->
							<!-- 快速排序 -->
							<c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.sys.search.model.SysSearchMain"></c:param>
								<c:param name="property" value="fdOrder"></c:param>
							</c:import>
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
				{url:'/sys/search/sys_search_main/sysSearchMain.do?method=list&category=${JsParam.category }&fdModelName=${JsParam.fdModelName}&fdTemplateModelName=${JsParam.fdTemplateModelName }'}
			</ui:source>
			<list:colTable var-className="lui-ding-list-table" isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/search/sys_search_main/sysSearchMain.do?method=view&fdId=!{fdId}&fdModelName=${JsParam.fdModelName}&fdTemplateModelName=${JsParam.fdTemplateModelName}&showCate=${JsParam['showCate'] }">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdOrder,fdName,fdCreator,fdCreateTime,operations"></list:col-auto>
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
		 			Com_OpenWindow('<c:url value="/sys/search/sys_search_main/sysSearchMain.do" />?method=add&category=${JsParam.category}&fdModelName=${JsParam.fdModelName}&fdTemplateModelName=${JsParam.fdTemplateModelName}&fdKey=${JsParam.fdKey}&showCate=${JsParam['showCate'] }');
		 		};
		 		// 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/search/sys_search_main/sysSearchMain.do" />?method=edit&fdId=' + id + '&fdModelName=${JsParam.fdModelName}&fdTemplateModelName=${JsParam.fdTemplateModelName}&showCate=${JsParam['showCate'] }');
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
					var url  = '<c:url value="/sys/search/sys_search_main/sysSearchMain.do?method=deleteall&fdModelName=${JsParam.fdModelName}"/>';
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
