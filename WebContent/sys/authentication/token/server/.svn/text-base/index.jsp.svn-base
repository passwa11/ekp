<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
			<list:cri-ref key="fdValue" ref="criterion.sys.docSubject" title="${lfn:message('sys-authentication:sysTokenInfo.fdValue') }">
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
						<list:sort property="sysTokenInfo.docCreateTime" text="${lfn:message('sys-authentication:sysTokenInfo.docCreateTime') }" group="sort.list"></list:sort>
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
<%--						<kmss:auth requestURL="/sys/token/sys_token_info/sysTokenInfo.do?method=add" requestMethod="GET">--%>
<%--							<ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>--%>
<%--						</kmss:auth>--%>
						<kmss:auth requestURL="/sys/token/sys_token_info/sysTokenInfo.do?method=deleteall" requestMethod="GET">
							<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/token/sys_token_info/sysTokenInfo.do?method=updateMoreInvalidation" requestMethod="GET">
							<ui:button text="${lfn:message('sys-authentication:button.moreInvalidation')}" onclick="updateMoreInvalidation();" order="3" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/token/sys_token_info/sysTokenInfo.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable"
						   rowHref="/sys/token/sys_token_info/sysTokenInfo.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdValue,fdVisitCount,fdVisitMaxCount,fdVisitEndPeriod,fdInvalidation,docCreator.fdName,docCreateTime,operations"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded" args="vt">
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
		<list:paging/>

		<script type="text/javascript">
			seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
				// 增加
				window.add = function() {
					Com_OpenWindow('<c:url value="/sys/token/sys_token_info/sysTokenInfo.do" />?method=add');
				};
				// 编辑
				window.edit = function(id) {
					if(id)
						Com_OpenWindow('<c:url value="/sys/token/sys_token_info/sysTokenInfo.do" />?method=edit&fdId=' + id);
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
					var url = '<c:url value="/sys/token/sys_token_info/sysTokenInfo.do?method=deleteall"/>';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'POST',
								data:$.param({"List_Selected":values},true),
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

				// 编辑
				window.updateInvalidation = function(id) {
					var url = '<c:url value="/sys/token/sys_token_info/sysTokenInfo.do?method=updateInvalidation"/>'+"&fdId="+id;
					if(id){
						window.del_load = dialog.loading();
						$.ajax({
							url: url,
							type: 'GET',
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
				};

				// 编辑
				window.updateMoreInvalidation = function(id) {
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
					var url = '<c:url value="/sys/token/sys_token_info/sysTokenInfo.do?method=updateMoreInvalidation"/>';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'POST',
								data:$.param({"List_Selected":values},true),
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
		</script>
	</template:replace>
</template:include>
