<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-lbpmservice-support:table.lbpmEmbeddedSubFlow') }</template:replace>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/profile/resource/css/operations.css?s_cache=${LUI_Cache}"/>
	</template:replace>
	
	<%-- 右边框内容 --%>
	<template:replace name="content">
		<%-- 筛选器 --%>
		<list:criteria>
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuth.fdName') }">
			</list:cri-ref>
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
						<list:sort property="fdName" text="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuth.fdName') }" group="sort.list" value="up"></list:sort>
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
					<ui:toolbar>
						<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuth.do?method=add&category=${JsParam.category}">
							<ui:button text="${lfn:message('button.add')}" onclick="add();" order="2"  ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuth.do?method=deleteall">
							<ui:button text="${lfn:message('button.deleteall')}" order="4" onclick="delDoc()"></ui:button>
						</kmss:auth> 
					</ui:toolbar>
				</div>
			</div>
		</div>
		
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/sys/lbpmext/businessauth/lbpmBusinessAuth.do?method=list&category=${JsParam.category}'}
			</ui:source>
			<%--列表形式--%>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="/sys/lbpmext/businessauth/lbpmBusinessAuth.do?method=view&fdId=!{fdId}" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props=""></list:col-auto>
			</list:colTable>   
		</list:listview> 
		<script type="text/javascript">
		seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic'], function($, strutil, dialog , topic) {
			window.add = function(){
				var fdMainModelName = '';
				Com_OpenWindow('<c:url value="/sys/lbpmext/businessauth/lbpmBusinessAuth.do" />?method=add&category=${JsParam.category}');
			};
			// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				topic.publish("list.refresh");
			});
			// 编辑
	 		window.edit = function(id) {
		 		if(id){
		 			var fdMainModelName = '';
		 			Com_OpenWindow('<c:url value="/sys/lbpmext/businessauth/lbpmBusinessAuth.do" />?method=edit&fdId=' + id);
		 		}
	 				
	 		};
		 	//删除
	 		window.delDoc = function(id){
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
				var url  = '<c:url value="/sys/lbpmext/businessauth/lbpmBusinessAuth.do?method="/>';
				if(id){
					url+='delete';
				}else{
					url+='deleteall';
				}
				var data = id?$.param({'fdId' : values}, true):$.param({'List_Selected' : values}, true);
				var type = id?'GET':'POST';
				dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
					if(value == true) {
						window.del_load = dialog.loading();
						$.ajax({
							url : url,
							type : type,
							data : data,
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
		<br>
	 	<list:paging></list:paging>
	</template:replace>
</template:include>