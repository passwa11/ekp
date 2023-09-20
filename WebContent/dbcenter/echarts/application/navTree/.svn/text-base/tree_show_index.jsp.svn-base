<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<c:if test='<%="true".equals(SysFormDingUtil.getEnableDing())%>'>
		<template:replace name="head">
			<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/ui/extend/theme/default/style/ding_list.css?s_cache=${LUI_Cache }"/>
		</template:replace>
	</c:if>
	<template:replace name="content">
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
					   <list:sort property="docCreateTime" text="${lfn:message('dbcenter-echarts-application:dbEchartsNavTree.docCreateTime') }" group="sort.list"></list:sort>
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
							<ui:button text="${ lfn:message('dbcenter-echarts-application:treeShow.add') }"  onclick="add();"> </ui:button>
							<ui:button text="${ lfn:message('button.deleteall') }" onclick="deleteAll();"> </ui:button>
					</ui:toolbar>	
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/dbcenter/echarts/application/dbEchartsNavTreeShow.do?method=list&serviceModelName=${JsParam.mainModelName }&fdKey=${JsParam.fdKey}'}
			</ui:source>
			<list:colTable var-className="lui-ding-list-table" isDefault="true" layout="sys.ui.listview.columntable" 
			     rowHref="/dbcenter/echarts/application/dbEchartsNavTreeShow.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdName,docCreateTime,docCreator.fdName,operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		window.add = function(){
		 			Com_OpenWindow("<c:url value='/dbcenter/echarts/application/dbEchartsNavTreeShow.do?method=add&serviceModelName=${JsParam.mainModelName }&fdKey=${JsParam.fdKey }'/>");	
		 		};
		 		window.edit = function(id){
					if(id){
						Com_OpenWindow("<c:url value='/dbcenter/echarts/application/dbEchartsNavTreeShow.do?method=edit&fdId=' />" + id);
					}
			 	};
		 		
		 		window.deleteAll = function(id){
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
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url:'<c:url value="/dbcenter/echarts/application/dbEchartsNavTreeShow.do?method=deleteall"/>',
										type:"POST",
										data:$.param({"List_Selected":values},true),
										dataType:"json",
										success:delCallback,
										error:delErrorCallback
							});
						}
					});
				};
				window.delCallback = function(data){
					if(window.del_load!=null){
						window.del_load.hide();
					}
					topic.publish("list.refresh");
					dialog.result(data);
				};
				
				window.delErrorCallback = function(data){
					var messages=data.responseJSON.message;
					var message=messages[0];
					if(window.del_load!=null){
						window.del_load.hide();
					}
					dialog.alert(message.msg);
					topic.publish("list.refresh");
				}
		 	});
	 	</script>
	</template:replace>
</template:include>
