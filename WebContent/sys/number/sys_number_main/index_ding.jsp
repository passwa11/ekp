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
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-number:sysNumberMain.fdName') }">
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
			<div class="lui_list_operation_order_text"> 
				${ lfn:message('list.orderType') }：
			</div>
			<%--排序按钮  --%>
			<div class="lui_list_operation_sort_toolbar">
				<ui:toolbar layout="sys.ui.toolbar.sort">
					<list:sortgroup>
					    <list:sort property="sysNumberMain.fdOrder" text="${lfn:message('sys-number:sysNumberMain.fdOrder') }" group="sort.list" value="up"></list:sort>
						<list:sort property="sysNumberMain.docCreateTime" text="${lfn:message('sys-number:sysNumberMain.docCreateTime') }" group="sort.list"></list:sort>
						<list:sort property="sysNumberMain.fdName" text="${lfn:message('sys-number:sysNumberMain.fdName') }" group="sort.list"></list:sort>
					</list:sortgroup>
				</ui:toolbar>
			</div>
			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<%--操作按钮  --%>
			<div class="lui_list_operation_toolbar">
				<div class="lui_table_toolbar_inner">
					<ui:toolbar id="Btntoolbar">
						<kmss:auth requestURL="/sys/number/sys_number_main/sysNumberMain.do?method=add&modelName=${param.modelName}" requestMethod="GET">
							<ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/number/sys_number_main/sysNumberMain.do?method=deleteall&modelName=${param.modelName}" requestMethod="GET">
							<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/number/sys_number_main/sysNumberMain.do?method=list&modelName=${param.modelName}&newUi=true'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			     rowHref="/sys/number/sys_number_main/sysNumberMain.do?method=view&fdId=!{fdId}&modelName=${param.modelName}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdOrder,fdName,fdDefaultFlag,docCreator.fdName,docCreateTime,operations"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded" args="vt">	
				LUI.$("[name='List_Tongle']").css("display","none");
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">	 		
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/number/sys_number_main/sysNumberMain.do" />?method=add&isSysnumberAdmin=${param.isSysnumberAdmin}&modelName=${param.modelName}&isCustom=2');
		 		};
		 	   // 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/number/sys_number_main/sysNumberMain.do" />?method=edit&modelName=${param.modelName}&fdId=' + id);
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
					var url = '<c:url value="/sys/number/sys_number_main/sysNumberMain.do?method=deleteall"/>';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'POST',
								data:$.param({"List_Selected":values,"modelName":'${param.modelName}'},true),
								dataType: 'json',
								error: function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: delCallback
						   });
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
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
		 	});
		 	$(function(){
		 		
		 		//$(".commit-action").style.display = "inline-block";
		 		
		 	});
	 	</script>
	</template:replace>
</template:include>
