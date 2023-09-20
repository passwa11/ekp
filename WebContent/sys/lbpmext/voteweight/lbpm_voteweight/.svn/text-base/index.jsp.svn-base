<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.config.design.SysConfigs,com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${lfn:message('sys-lbpmext-voteweight:table.lbpmVoteWeight')}</template:replace>
	<template:replace name="content">
		<div style="padding: 15px">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<%--模块--%>
			<list:cri-criterion title="${lfn:message('sys-lbpmext-voteweight:lbpmVoteWeightScope.fdModule')}" key="fdModelName" multi="false"> 
				<list:box-select>
					<list:item-select>
						<ui:source type="AjaxJson">
							{url:'/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=getModule'}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-ref key="fdVoter" ref="criterion.sys.person" title="${lfn:message('sys-lbpmext-voteweight:lbpmVoteWeight.fdVoter') }" multi="false"></list:cri-ref>
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
						<list:sort property="fdCreateTime" text="${lfn:message('sys-lbpmext-voteweight:lbpmVoteWeight.fdCreateTime') }" group="sort.list" value="down"></list:sort>
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
						<kmss:auth requestURL="/sys/lbpmext/voteweight/lbpm_voteweight/lbpmVoteWeight.do?method=add" requestMethod="GET">
							<ui:button text="${lfn:message('button.add')}" 
								onclick="Com_OpenWindow('${LUI_ContextPath}/sys/lbpmext/voteweight/lbpm_voteweight/lbpmVoteWeight.do?method=add');">
							</ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/lbpmext/voteweight/lbpm_voteweight/lbpmVoteWeight.do?method=deleteall" requestMethod="GET">
							<ui:button text="${lfn:message('button.deleteall')}" onclick="delDoc();"></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
					{url:'/sys/lbpmext/voteweight/lbpm_voteweight/lbpmVoteWeight.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/lbpmext/voteweight/lbpm_voteweight/lbpmVoteWeight.do?method=view&fdId=!{fdId}"  name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial> 
				<list:col-auto props="fdVoter.fdName;fdVoteWeight;fdCreateTime;operations"></list:col-auto>
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
							$.post('<c:url value="/sys/lbpmext/voteweight/lbpm_voteweight/lbpmVoteWeight.do?method=deleteall"/>',
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
		 				Com_OpenWindow('<c:url value="/sys/lbpmext/voteweight/lbpm_voteweight/lbpmVoteWeight.do" />?method=edit&fdId=' + id);
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
					var url  = '<c:url value="/sys/lbpmext/voteweight/lbpm_voteweight/lbpmVoteWeight.do?method=delete"/>';
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
			});
		</script>
	</template:replace>
</template:include>
