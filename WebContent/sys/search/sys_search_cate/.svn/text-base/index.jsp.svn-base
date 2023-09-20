<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.list">
	<template:replace name="title">${ lfn:message('sys-search:table.sysSearchCate') }</template:replace>
	<c:if test='<%="true".equals(SysFormDingUtil.getEnableDing())%>'>
		<template:replace name="head">
			<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/ui/extend/theme/default/style/ding_list.css?s_cache=${LUI_Cache }"/>
		</template:replace>
	</c:if>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-search:sysSearchCate.fdName') }" ></list:cri-ref>
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
						<list:sort property="fdName" text="${lfn:message('sys-search:sysSearchCate.fdName') }" group="sort.list" value="down"></list:sort>
						<list:sort property="hbmParent" text="${lfn:message('sys-search:sysSearchCate.fdParent') }" group="sort.list"></list:sort>
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
					<ui:toolbar id="Btntoolbar" count="5">
						<kmss:auth requestURL="/sys/search/sys_search_cate/sysSearchCate.do?method=add&fdModelName=${JsParam.fdModelName}">
							<!-- 增加 -->
							<ui:button text="${lfn:message('button.add')}" onclick="add()" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/search/sys_search_cate/sysSearchCate.do?method=deleteAll&fdModelName=${JsParam.fdModelName}">
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
				{url:'/sys/search/sys_search_cate/sysSearchCate.do?method=list&fdModelName=${JsParam.fdModelName}'}
			</ui:source>
			<list:colTable var-className="lui-ding-list-table" isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/search/sys_search_cate/sysSearchCate.do?method=view&fdId=!{fdId}&fdModelName=${JsParam.fdModelName}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdParent,fdName,operations"></list:col-auto>
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
		 			Com_OpenWindow('<c:url value="/sys/search/sys_search_cate/sysSearchCate.do" />?method=add&fdModelName=${param.fdModelName}');
		 		};
		 		// 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/search/sys_search_cate/sysSearchCate.do" />?method=edit&fdId=' + id);
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
					var url  = '<c:url value="/sys/search/sys_search_cate/sysSearchCate.do?method=deleteall"/>';
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
									dialog.result({
										status : false,
										title : "操作失败！",
										message : [{"msg":"该分类与其它分类存在引用关系"}]
									});
								},
								success: function(data) {
									if(window.del_load != null){
										window.del_load.hide(); 
										topic.publish("list.refresh");
									}
									dialog.result({
										status : true,
										title:"操作成功"
									});
								}
						   });
						}
					});
				};
		 	});
	 	</script>
	</template:replace>
</template:include>
