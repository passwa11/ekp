<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.quartz.model.SysQuartzJob"%>
<%@page import="com.landray.kmss.sys.quartz.service.ISysQuartzJobService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.config.design.SysConfigs,com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-xform-base:table.sysXformAuditshow') }</template:replace>
	<template:replace name="content">
		<list:criteria channel="channel_common" id="criteria1" expand="false" multi="false">
			<list:cri-ref key="docSubject" ref="criterion.sys.docSubject"/>
			<%--当前处理人--%>	
			<list:cri-ref ref="criterion.sys.postperson.availableAll"
				key="fdCurrentHandler" multi="false"
				title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.currentHandler') }" />
			<%--创建者--%>
			<list:cri-ref ref="criterion.sys.person" key="fdCreator"
				multi="false"
				title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.docAuthor') }" />
			<%--创建时间--%>
			<list:cri-ref ref="criterion.sys.calendar" key="fdCreateTime"
				title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.docAuthorTime') }" />
			<%--所属模块--%>
			<list:cri-criterion title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.order.module')}" key="fdModelName" multi="false"> 
				<list:box-select>
					<list:item-select>
						<ui:source type="AjaxJson">
							{url:'/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=getModule'}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
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
						<list:sort property="exeTime" text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.queue.exeTime')}" group="sort.list"></list:sort>
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
						<!-- 中断线程 -->
						<ui:button text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.queue.interruptThread')}" onclick="interruptThread()" order="2" ></ui:button>
						<ui:button text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.queue.endOfThread')}" onclick="endOfThread()" order="2" ></ui:button>
						<%
							ISysQuartzJobService quartzJobService = (ISysQuartzJobService) SpringBeanUtil
									.getBean("sysQuartzJobService");
							List<SysQuartzJob> job = quartzJobService.findList("fdJobService='sysLbpmMonitorQueueTaskService'", "");
							if (job != null && job.size() > 0) {
								SysQuartzJob quartzJob = job.get(0);
								request.setAttribute("quartzJobId", quartzJob.getFdId());
						%>
							<ui:button text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.queue.quartz.button')}" onclick="openSynDataTask('${quartzJobId }')" order="2" ></ui:button>
						<%
							}
						%>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/lbpmmonitor/actions/SysLbpmMonitorQueueTaskAction.do?method=listChildren'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=view&fdId=!{fdId}&fdModelName=!{fdModelName}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="subject,exeTime,fdCreator.fdName,fdCreateTime,nodeName,handlerName"></list:col-auto>
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
			 	// 中断线程
		 		window.interruptThread = function(id) {
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
					var url  = '<c:url value="/sys/lbpmmonitor/actions/SysLbpmMonitorQueueTaskAction.do?method=interruptThread"/>';
					dialog.confirm('<bean:message bundle="sys-lbpmmonitor" key="sysLbpmMonitor.queue.interruptThreadMsg" />', function(value) {
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
		 		// 结束线程
		 		window.endOfThread = function(id) {
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
					var url  = '<c:url value="/sys/lbpmmonitor/actions/SysLbpmMonitorQueueTaskAction.do?method=endOfThread"/>';
					dialog.confirm('<bean:message bundle="sys-lbpmmonitor" key="sysLbpmMonitor.queue.endOfThreadMsg" />', function(value) {
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
		 		
		 		window.openSynDataTask = function(quartzJobId) {
		 			window.open("${LUI_ContextPath}/sys/quartz/sys_quartz_job/sysQuartzJob.do?method=view&fdId="+quartzJobId,"_blank");
		 		}
		 		
				//筛选器变化		
				topic.channel("channel_common").subscribe("criteria.changed", function(evt) {
				    if(evt.criterions){
					    var keyArray=new Array();
					    for(var i=0; i<evt.criterions.length;i++){
						    keyArray[i] = evt.criterions[i].key;
						}
					    /* if(keyArray.contains("docSubject")){
							if(!keyArray.contains("fdModelName")){
								alert('<bean:message bundle="sys-lbpmmonitor" key="sysLbpmMonitor.flow.selectModuleFirst"/>');												
								return;
							}
						} */
					}
				    topic.publish("criteria.changed", evt);
				});
		 	});
		 	Array.prototype.contains = function (arr){
				for(var i=0;i<this.length;i++){
					if(this[i] == arr){
						return true;
					}
				}
				return false;
			};
	 	</script>
	</template:replace>
</template:include>