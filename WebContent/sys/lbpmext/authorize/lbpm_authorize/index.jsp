<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${lfn:message('sys-lbpmext-authorize:table.lbpmAuthorize')}</template:replace>
	<template:replace name="content">
		<div style="padding: 15px">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdAuthorizer" ref="criterion.sys.person" title="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizer') }" multi="false"></list:cri-ref>
			<list:cri-ref key="fdAuthorizedPerson" ref="criterion.sys.person" title="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizedPerson') }" multi="false"></list:cri-ref>
		</list:criteria>
		
		<!-- 操作栏 -->
		<div class="lui_list_operation">
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
						<list:sort property="fdStartTime" text="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdStartTime') }" group="sort.list" value="down"></list:sort>
						<list:sort property="fdEndTime" text="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdEndTime') }" group="sort.list"></list:sort>
						<list:sort property="fdAuthorizeType" text="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizeType') }" group="sort.list"></list:sort>
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
						<kmss:auth requestURL="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=add" requestMethod="GET">
							<ui:button text="${lfn:message('button.add')}" 
								onclick="Com_OpenWindow('${LUI_ContextPath}/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=add');">
							</ui:button>
						</kmss:auth>
						
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
					{url:'/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=list&forward=listUi'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=view&fdId=!{fdId}"  name="columntable">
				<list:col-serial></list:col-serial> 
				<list:col-auto props="fdStartTime;fdEndTime;fdAuthorizer.fdName;fdAuthorizedPerson.fdName;fdAuthorizeType;operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	</div>
	 	<script type="text/javascript">
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {

				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				//删除
				window.delDoc = function(){
					var values = [];
					$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							var del_load = dialog.loading();
							$.post('<c:url value="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),function(data){
									if(del_load!=null)
										del_load.hide();
									if(data!=null && data.status==true){
										topic.publish("list.refresh");
										dialog.success('<bean:message key="return.optSuccess" />');
									}else{
										dialog.failure('<bean:message key="return.optFailure" />');
									}
								},'json').error(function(){
									dialog.failure('<bean:message key="errors.noRecord" />');
									if(del_load!=null)
										del_load.hide();
									topic.publish("list.refresh");
								});
						}
					});
				};

				// 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do" />?method=edit&fdId=' + id);
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
					$.ajaxSettings.async = false;	
					var canDel=false;
					$.post('<c:url value="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=findAuthInstanceCount"/>'+'&fdId='+id,
								{},function(data){
								if(data=='0'){
									canDel=true;
								}
								else{
									alert('<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorizeItem.notdel.msg"/>');
								}
							});	
					$.ajaxSettings.async = true;
					if(!canDel){
						return;
					}
					var url  = '<c:url value="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=delete"/>';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
						if(value == true) {
							window.del_load = dialog.loading();
							$.ajax({
								url : url,
								type : 'GET',
								data : $.param({"fdId" : id}, true),
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
				// 终止
				window.stop = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do" />?method=stopView&fdId=' + id);
		 		};
			});
		</script>
	</template:replace>
</template:include>
