<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<%-- 样式 --%>
	<template:replace name="head">
		<link rel="Stylesheet" href="resource/css/maincss.css?s_cache=${LUI_Cache }" />
	</template:replace>
	<template:replace name="title">${ lfn:message('sys-time:title.timeSetting') }</template:replace>
	<template:replace name="content">
		<div style="padding: 15px">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-time:sysTimeArea.fdName') }"></list:cri-ref>
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
						<list:sort property="docCreateTime" text="${lfn:message('sys-time:sysTimeArea.docCreateTime') }" group="sort.list" value="down"></list:sort>
						<list:sort property="fdName" text="${lfn:message('sys-time:sysTimeArea.fdName') }" group="sort.list"></list:sort>
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
						<kmss:auth requestURL="/sys/time/sys_time_area/sysTimeArea.do?method=simulatorWork">
							<!-- 增加 -->
							<ui:button text="${lfn:message('sys-time:calendar.simulator.worktime')}" onclick="simulatorWork()" order="1" ></ui:button>
						</kmss:auth>
						<%-- <kmss:auth requestURL="/sys/time/sys_time_area/sysTimeArea.do?method=simulatorPach">
							<!-- 增加 -->
							<ui:button text="${lfn:message('sys-time:calendar.simulator.workpach')}" onclick="simulatorPach()" order="2" ></ui:button>
						</kmss:auth> --%>
						<kmss:auth requestURL="/sys/time/sys_time_area/sysTimeArea.do?method=add">
							<!-- 增加 -->
							<ui:button text="${lfn:message('button.add')}" onclick="add()" order="3" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/time/sys_time_area/sysTimeArea.do?method=deleteall">
							<!-- 删除 -->
							<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteall()" order="4" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/time/sys_time_area/sysTimeArea.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/time/sys_time_area/sysTimeArea.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdName,scope,timeAdmin,docCreator,docCreateTime,operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	</div>
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
				
			 	// 工时模拟器
		 		window.simulatorWork = function() {
		 			Com_OpenWindow('<c:url value="/sys/time/sys_time_area/sysTimeArea_simulatorWork.jsp" />');
		 		};
			 	// 排班状态模拟器
		 		window.simulatorPach = function() {
		 			Com_OpenWindow('<c:url value="/sys/time/sys_time_area/sysTimeArea_simulatorPach.jsp" />');
		 		};
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/time/sys_time_area/sysTimeArea.do" />?method=add');
		 		};
		 		// 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/time/sys_time_area/sysTimeArea.do" />?method=edit&fdId=' + id);
		 		};
		 		
		 		window.onDoScheduling = function(id){
		 			var url = '<c:url value="/sys/time/sys_time_area/sysTimeArea.do" />?method=getTimeArea&fdId=' + id;
		 			$.ajax({
		 			   type: "GET",
		 			   url: url,
		 			   dataType: "json",
		 			   success: function(result){
		 				  if(result && result.status==1){
		 					 doScheduling(id,result.data.fdBatchSchedule,result.data.isScheduled);
		 				  }else{
		 					 dialog.failure("${lfn:message('return.optFailure')}");
		 				  }
		 			   },
		 			   error : function(e){
		 				  dialog.failure("${lfn:message('return.optFailure')}");
		 			   }
		 			});
		 		};
		 		//排班
		 		window.doScheduling = function(id,fdIsBatchSchedule,isScheduled){
		 			var url = "/sys/time/sys_time_area/sysTimeArea_selectCalendar.jsp?fdId=" + id;
		 			var pUrl = '<c:url value="/sys/time/sys_time_area/sysTimeArea.do" />?method=editMCalendar&fdId=' + id;
		 			var bUrl = '<c:url value="/sys/time/sys_time_area/sysTimeArea.do" />?method=editCalendar&fdId=' + id;
		 			if(fdIsBatchSchedule=='true'){
		 				url =pUrl
		 				Com_OpenWindow(url);
		 			}else if(isScheduled=='true'){
		 				url = bUrl;
		 				Com_OpenWindow(url);
		 			}else{
		 				 dialog.iframe(url,
		 			            '选择排班方式',
		 			            function(result) {
		 			                if (!result) { return; }
		 			               	if(result.value==1){
		 			               		url = bUrl;
		 			               	}else{
		 			               		url = pUrl;
		 			               	}
		 			               Com_OpenWindow(url);
		 			            },
		 			            {
		 			                width: 500,
		 			                height: 250,
		 			                params: {
		 			                    data: null,
		 			                    method: 'add'
		 			                }
		 			            }
		 			        );
		 			}
		 			
		 		};
		 		window.doViewSchedule = function(id){
		 			var url = '<c:url value="/sys/time/sys_time_area/sysTimeArea.do" />?method=view&fdId=' + id + "&forward=scheduleView";
		 			Com_OpenWindow(url);
		 		},
		 		// 删除
		 		window.del = function(id) {
					var url  = '<c:url value="/sys/time/sys_time_area/sysTimeArea.do?method=delete"/>';
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
		 		// 删除全部
		 		window.deleteall = function(id) {
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
					var url  = '<c:url value="/sys/time/sys_time_area/sysTimeArea.do?method=deleteall"/>';
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
