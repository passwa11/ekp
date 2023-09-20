<%@page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-quartz:table.sysQuartzJob') }</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<%
			if(UserUtil.getKMSSUser().getLocale().toString().equals("zh_CN")){
			%>
			<list:cri-ref key="fdTitle" ref="criterion.sys.docSubject" title="${lfn:message('sys-quartz:sysQuartzJob.fdSubject') }"></list:cri-ref>
			<%
			}
			%>
			<c:if test="${JsParam.sysJob == '1'}">
			<list:cri-criterion title="${ lfn:message('sys-quartz:sysQuartzJob.fdEnabled')}" key="fdEnabled"> 
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('message.yes')}', value:'true'},
							{text:'${ lfn:message('message.no')}',value:'false'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			</c:if>
			<list:cri-criterion title="${ lfn:message('sys-quartz:sysQuartzJob.fdRunType')}" key="fdRunType"> 
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-quartz:sysQuartzJob.fdRunType.ASSIGNNODE')}', value:'2'},
							{text:'${ lfn:message('sys-quartz:sysQuartzJob.fdRunType.SINGLENODE')}',value:'4'}]
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
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="5">
					<list:sortgroup>
						<list:sort property="fdRunTime" text="${lfn:message('sys-quartz:sysQuartzJob.nextTime') }" group="sort.list" value="up"></list:sort>
						<list:sort property="fdTitlePinyin" text="${lfn:message('sys-quartz:sysQuartzJob.fdSubject') }" group="sort.list"></list:sort>
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
						<kmss:auth requestURL="/sys/quartz/sys_quartz_job/sysQuartzJob.do?method=chgenabled" requestMethod="POST">
							<!-- 启用 -->
							<ui:button text="${lfn:message('sys-quartz:sysQuartzJob.button.enable')}" onclick="changeJob(true)" order="1" ></ui:button>
							<!-- 禁用 -->
							<ui:button text="${lfn:message('sys-quartz:sysQuartzJob.button.disable')}" onclick="changeJob(false)" order="2" ></ui:button>
						</kmss:auth>
						<c:if test="${'1' eq param.sysJob}">
							<!-- 导入系统任务 -->
							<ui:button text="${lfn:message('sys-quartz:sysQuartzJob.init.name')}" onclick="importJob()" order="3" ></ui:button>
							<ui:button text="${lfn:message('sys-quartz:sysQuartzJob.button.export')}" onclick="exportJob()" order="4" ></ui:button>
						</c:if>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/quartz/sys_quartz_job/sysQuartzJob.do?method=list&sysJob=${JsParam.sysJob}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/quartz/sys_quartz_job/sysQuartzJob.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<% if(TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN && "0".equals(request.getParameter("sysJob"))) { %>
				<list:col-auto props="fdSubject,fdCronExpression,nextTime,fdRunType,fdEnabled,operations"></list:col-auto>
				<% } else { %>
				<list:col-auto props="fdSubject,fdCronExpression,nextTime,fdLink,fdRunType,fdEnabled,operations"></list:col-auto>
				<% } %>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<%-- 为兼容低版本IE浏览器，导出时需要使用form --%>
	 	<form id="exportJobForm" action="<c:url value="/sys/quartz/sys_quartz_job/sysQuartzJob.do"/>?method=exportJob" method="post"></form>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
				// 导入系统任务
		 		window.exportJob = function() {
		 			$("#exportJobForm").submit();
		 		};
		 		// 导入系统任务
		 		window.importJob = function() {
		 			window.open('<c:url value="/sys/quartz/sys_quartz_job/sysQuartzJob.do" />?method=systemInit', '_self');
		 		};
				// 修改
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/quartz/sys_quartz_job/sysQuartzJob.do" />?method=edit&fdId=' + id);
		 		};
			 	// 修改状态
		 		window.changeJob = function(enabled, id) {
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
					var url  = '<c:url value="/sys/quartz/sys_quartz_job/sysQuartzJob.do?method=chgenabled"/>';
					var msg = '<bean:message bundle="sys-quartz" key="sysQuartzJob.dialog.comfirmEnable"/>';
					if(!enabled) {
						msg = '<bean:message bundle="sys-quartz" key="sysQuartzJob.dialog.comfirmDisable"/>';
					}
					dialog.confirm(msg, function(value) {
						if(value == true) {
							window.del_load = dialog.loading();
							$.ajax({
								url : url,
								type : 'POST',
								data : $.param({"List_Selected" : values, "fdEnabled" : enabled}, true),
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
