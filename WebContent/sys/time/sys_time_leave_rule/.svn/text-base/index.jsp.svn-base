<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="head">
	</template:replace>
	<template:replace name="content">
		<!-- 查询条件  -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${ lfn:message('sys-time:sysTimeLeaveRule.fdName') }"></list:cri-ref>
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
						<list:sort property="docCreateTime" text="${lfn:message('sys-time:sysTimeArea.docCreateTime') }" group="sort.list"></list:sort>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
		
			<div style="float: right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar>
						<kmss:auth requestURL="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do?method=add">
						<ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>
						</kmss:auth>
						<%--  <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button> --%>
					</ui:toolbar>
				</div>
			</div>
		</div>
		
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable"
				rowHref="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do?method=view&fdId=!{fdId}" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial headerStyle="min-width: 50px"></list:col-serial>
				<list:col-auto props="fdName;fdSerialNo;fdStatType;fdIsAmount;fdIsAvailable;operation;"></list:col-auto>
			</list:colTable>
		</list:listview>
		<!-- 分页 -->
	 	<list:paging></list:paging>
	 	
	 	<script type="text/javascript">
			seajs.use(['lui/jquery','lui/topic','lui/dialog'],function($,topic,dialog){
				
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				//新建
				window.add = function(){
					Com_OpenWindow('<c:url value="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do" />?method=add');
				};
				
				//编辑
				window.edit = function(id){
					Com_OpenWindow('<c:url value="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do" />?method=edit&fdId=' + id,'_blank');
				};
				
				//删除
				window.deleteAll = function(id){
		 			var values = [];
					if(id) {
		 				values.push(id);
			 		} else {
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
							$.post('<c:url value="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),function(data){
								if(window.del_load!=null)
									window.del_load.hide();
								if(data!=null && data.status==true){
									topic.publish("list.refresh");
									dialog.success('<bean:message key="return.optSuccess" />');
								}else{
									dialog.failure('<bean:message key="return.optFailure" />');
								}
							},'json');
						}
					});
				};
				
				// 启用
				window.enable = function(id) {
					if(!id) {
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					window.del_load = dialog.loading();
					$.post('<c:url value="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do?method=enable"/>',
							$.param({"fdId":id},true),function(data){
						if(window.del_load!=null)
							window.del_load.hide();
						if(data!=null && data.status==true){
							topic.publish("list.refresh");
							dialog.success('<bean:message key="return.optSuccess" />');
						}else{
							var errMsg = data && data.errMsg || '<bean:message key="return.optFailure" />';
							dialog.failure(errMsg);
						}
					},'json');
				};
				
				// 禁用
				window.disable = function(id) {
					if(!id) {
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					window.del_load = dialog.loading();
					$.post('<c:url value="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do?method=disable"/>',
							$.param({"fdId":id},true),function(data){
						if(window.del_load!=null)
							window.del_load.hide();
						if(data!=null && data.status==true){
							topic.publish("list.refresh");
							dialog.success('<bean:message key="return.optSuccess" />');
						}else{
							dialog.failure('<bean:message key="return.optFailure" />');
						}
					},'json');
				};
				
				window.disableAndDeleteAmount = function(id) {
					if(!id) {
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm("${ lfn:message('sys-time:sysTimeLeaveRule.warn.disable.deleteAmount') }",function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.post('<c:url value="/sys/time/sys_time_leave_rule/sysTimeLeaveRule.do?method=disable"/>',
									$.param({"fdId":id},true),function(data){
								if(window.del_load!=null)
									window.del_load.hide();
								if(data!=null && data.status==true){
									topic.publish("list.refresh");
									dialog.success('<bean:message key="return.optSuccess" />');
								}else{
									dialog.failure('<bean:message key="return.optFailure" />');
								}
							},'json');
						}
					});
				};

			});
		</script>
	</template:replace>
</template:include>