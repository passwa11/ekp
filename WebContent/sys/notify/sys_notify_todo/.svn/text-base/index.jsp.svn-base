<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">
		<c:choose>
			<%-- 待办 --%>
			<c:when test="${'doing' eq param.oprType}">
				${ lfn:message('sys-notify:sysNotifyTodo.doing.item') }
			</c:when>
			<%-- 已办 --%>
			<c:otherwise>
				${ lfn:message('sys-notify:sysNotifyTodo.done.item') }
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="head">
		<style type="text/css">
		#notify_content_1{
			display:inline-block;
			width:13px;
			height:11px;
			background: url(<c:url value='/resource/style/default/portal/icon_red.gif'/>) 50% 30% no-repeat;
		}
		#notify_content_2{
			display:inline-block;
			width:13px;
			height:11px;
			background: url(<c:url value='/resource/style/default/portal/icon_green.gif'/>) 50% 30% no-repeat;
		}
		#notify_content_3{
			display:inline-block;
			width:13px;
			height:11px;
			background: url(<c:url value='/resource/style/default/portal/icon_blue.gif'/>) 50% 30% no-repeat;
		}
		</style>
	</template:replace>
	<template:replace name="content">
		<ui:tabpanel>
			<ui:content id="todo" title="${ lfn:message('sys-notify:sysNotifyTodo.cate.audit') }">
				<!-- 筛选器 -->
				<list:criteria id="criteria1" multi="false" channel="sysNotifyTodo">
					<list:cri-ref key="fdSubject" ref="criterion.sys.docSubject" title="${lfn:message('sys-notify:sysNotifyTodo.fdSubject') }"></list:cri-ref>
					<!-- 类型 -->
					<list:cri-criterion title="${ lfn:message('sys-notify:sysNotifyTodo.cate')}" key="fdType" channel="sysNotifyTodo">
						<list:box-select>
							<list:item-select>
								<ui:source type="Static">
									[{text:'${ lfn:message('sys-notify:sysNotifyTodo.cate.audit')}', value:'1'},
									{text:'${ lfn:message('sys-notify:sysNotifyTodo.cate.suspend')}', value:'3'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					<% if(com.landray.kmss.sys.notify.util.SysNotifyConfigUtil.getFdDisplayAppName() == 1) { %>
					<!-- 应用 -->
					<list:cri-criterion title="${ lfn:message('sys-notify:sysNotifyTodo.app')}" key="fdAppName" channel="sysNotifyTodo">
						<list:box-select>
							<list:item-select>
								<ui:source type="AjaxJson">
									{url:'/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=getAppModules&oprType=doing&isAdmin=true'}
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					<% } %>
					<!-- 模块 -->
					<list:cri-criterion title="${ lfn:message('sys-notify:sysNotifyTodo.module')}" key="fdModelName" channel="sysNotifyTodo">
						<list:box-select>
							<list:item-select id="fdModelNameCriterion">
								<ui:source type="AjaxJson">
									{url:'/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=getAppModules&oprType=doing&isAdmin=true&fdAppName=no'}
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					<!-- 人员 -->
					<list:cri-ref ref="criterion.sys.postperson.availableAll"
						  key="userId" multi="false" title="${lfn:message('sys-notify:sysNotifyTodo.org') }" />
				</list:criteria>
				
				<!-- 操作栏 -->
				<div class="lui_list_operation">
					<!-- 全选 -->
				 	<div class="lui_list_operation_order_btn">
						<list:selectall channel="sysNotifyTodo"></list:selectall>
					</div>
					<!-- 分割线 -->
					<div class="lui_list_operation_line"></div>
					<!-- 排序 -->
					<div class="lui_list_operation_sort_btn">
						<div class="lui_list_operation_order_text">
							${ lfn:message('list.orderType') }：
						</div>
						<div class="lui_list_operation_sort_toolbar">
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" channel="sysNotifyTodo">
								<c:choose>
									<%-- 待办 --%>
									<c:when test="${'doing' eq param.oprType}">
										<list:sortgroup>
											<list:sort property="fdCreateTime" text="${lfn:message('sys-notify:sysNotifyTodo.fdCreateDate') }" group="sort.list" value="down"></list:sort>
											<list:sort property="fdSubject" text="${lfn:message('sys-notify:sysNotifyTodo.fdSubject') }" group="sort.list"></list:sort>
											<list:sort property="fdType" text="${lfn:message('sys-notify:sysNotifyTodo.fdType') }" group="sort.list"></list:sort>
										</list:sortgroup>
									</c:when>
									<%-- 已办 --%>
									<c:otherwise>
										<list:sortgroup>
											<list:sort property="fdFinishTime" text="${lfn:message('sys-notify:sysNotifyTodoDoneInfo.fdFinishTime') }" group="sort.list" value="down"></list:sort>
											<list:sort property="todo.fdSubject" text="${lfn:message('sys-notify:sysNotifyTodo.fdSubject') }" group="sort.list"></list:sort>
											<list:sort property="todo.fdType" text="${lfn:message('sys-notify:sysNotifyTodo.fdType') }" group="sort.list"></list:sort>
										</list:sortgroup>
									</c:otherwise>
								</c:choose>
							</ui:toolbar>
						</div>
					</div>
					<!-- 分页 -->	
					<div class="lui_list_operation_page_top">	
						<list:paging layout="sys.ui.paging.top" channel="sysNotifyTodo"> 		
						</list:paging>
					</div>
					<!-- 操作按钮 -->
					<div style="float:right">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar id="Btntoolbar1" channel="sysNotifyTodo">
								<c:choose>
									<%-- 待办 --%>
									<c:when test="${'doing' eq param.oprType}">
										<c:if test="${empty param.owner || param.owner == 'true'}">
										<kmss:auth requestURL="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=deleteall" requestMethod="POST">
											<!-- 置为已办事项 -->
											<ui:button text="${lfn:message('sys-notify:sysNotifyTodo.button.todo.finish')}" onclick="__del('deleteall','1')" channel="sysNotifyTodo"></ui:button>
										</kmss:auth>
										</c:if>
										<c:if test="${not empty param.oprType && param.owner == 'false'}">
										<kmss:auth requestURL="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngDelete" requestMethod="POST">
											<!-- 置为已办事项 -->
											<ui:button text="${lfn:message('sys-notify:sysNotifyTodo.button.todo.finish')}" onclick="__del('mngDelete','1')" channel="sysNotifyTodo"></ui:button>
										</kmss:auth>
										</c:if>
									</c:when>
									<%-- 已办 --%>
									<c:otherwise>
										<c:if test="${empty param.owner || param.owner == 'true'}">
										<kmss:auth requestURL="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=deleteall" requestMethod="POST">
											<!-- 删除 -->
											<ui:button text="${lfn:message('button.deleteall')}" onclick="__del('deleteall','1')" channel="sysNotifyTodo"></ui:button>
										</kmss:auth>
										</c:if>
										<c:if test="${not empty param.oprType && param.owner == 'false'}">
										<kmss:auth requestURL="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngDelete" requestMethod="POST">
											<!-- 删除 -->
											<ui:button text="${lfn:message('button.deleteall')}" onclick="__del('mngDelete','1')" channel="sysNotifyTodo"></ui:button>
										</kmss:auth>
										</c:if>
									</c:otherwise>
								</c:choose>
								
							</ui:toolbar>
						</div>
					</div>
				</div>
				<!-- 内容列表 -->
				<list:listview channel="sysNotifyTodo">
					<ui:source type="AjaxJson">
						{url:'/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&viewType=todo&oprType=${JsParam.oprType}&owner=${JsParam.owner}'}
					</ui:source>
					<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
						rowHref="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId=!{fdId}">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial>
						<list:col-auto props="fdSubject,fdAppName,fdType,fdCreateTime,finishDate,operations"></list:col-auto>
					</list:colTable>
					<ui:event topic="list.loaded">
						Dropdown.init();
					</ui:event>
				</list:listview>
				<br>
				<!-- 分页 -->
			 	<list:paging channel="sysNotifyTodo"></list:paging>
	 		</ui:content>
	 		
			<ui:content id="todo2" title="${ lfn:message('sys-notify:sysNotifyTodo.cate.copyto') }">
				<!-- 筛选器 -->
				<list:criteria id="criteria2" multi="false" channel="sysNotifyTodo2">
					<list:cri-ref key="fdSubject" ref="criterion.sys.docSubject" title="${lfn:message('sys-notify:sysNotifyTodo.fdSubject') }"></list:cri-ref>

					<% if(com.landray.kmss.sys.notify.util.SysNotifyConfigUtil.getFdDisplayAppName() == 1) { %>
					<!-- 应用 -->
					<list:cri-criterion title="${ lfn:message('sys-notify:sysNotifyTodo.app')}" key="fdAppName" channel="sysNotifyTodo2">
						<list:box-select>
							<list:item-select>
								<ui:source type="AjaxJson">
									{url:'/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=getAppModules&oprType=doing&isAdmin=true'}
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					<% } %>
					<!-- 模块 -->
					<list:cri-criterion title="${ lfn:message('sys-notify:sysNotifyTodo.module')}" key="fdModelName" channel="sysNotifyTodo2">
						<list:box-select>
							<list:item-select id="fdModelNameCriterion2">
								<ui:source type="AjaxJson">
									{url:'/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=getAppModules&oprType=doing&isAdmin=true&fdAppName=no'}
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					<!-- 人员 -->
					<list:cri-ref ref="criterion.sys.postperson.availableAll"
						  key="userId" multi="false" title="${lfn:message('sys-notify:sysNotifyTodo.org') }" />
				</list:criteria>
				
				<!-- 操作栏 -->
				<div class="lui_list_operation">
					<!-- 全选 -->
				 	<div class="lui_list_operation_order_btn">
						<list:selectall channel="sysNotifyTodo2"></list:selectall>
					</div>
					<!-- 分割线 -->
					<div class="lui_list_operation_line"></div>
					<!-- 排序 -->
					<div class="lui_list_operation_sort_btn">
						<div class="lui_list_operation_order_text">
							${ lfn:message('list.orderType') }：
						</div>
						<div class="lui_list_operation_sort_toolbar">
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" channel="sysNotifyTodo2">
								<c:choose>
									<%-- 待办 --%>
									<c:when test="${'doing' eq param.oprType}">
										<list:sortgroup>
											<list:sort property="fdCreateTime" text="${lfn:message('sys-notify:sysNotifyTodo.fdCreateDate') }" group="sort.list" value="down"></list:sort>
											<list:sort property="fdSubject" text="${lfn:message('sys-notify:sysNotifyTodo.fdSubject') }" group="sort.list"></list:sort>
											<list:sort property="fdType" text="${lfn:message('sys-notify:sysNotifyTodo.fdType') }" group="sort.list"></list:sort>
										</list:sortgroup>
									</c:when>
									<%-- 已办 --%>
									<c:otherwise>
										<list:sortgroup>
											<list:sort property="fdFinishTime" text="${lfn:message('sys-notify:sysNotifyTodoDoneInfo.fdFinishTime') }" group="sort.list" value="down"></list:sort>
											<list:sort property="todo.fdSubject" text="${lfn:message('sys-notify:sysNotifyTodo.fdSubject') }" group="sort.list"></list:sort>
											<list:sort property="todo.fdType" text="${lfn:message('sys-notify:sysNotifyTodo.fdType') }" group="sort.list"></list:sort>
										</list:sortgroup>
									</c:otherwise>
								</c:choose>
							</ui:toolbar>
						</div>
					</div>
					<!-- 分页 -->	
					<div class="lui_list_operation_page_top">	
						<list:paging layout="sys.ui.paging.top" channel="sysNotifyTodo2"> 		
						</list:paging>
					</div>
					<!-- 操作按钮 -->
					<div style="float:right">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar id="Btntoolbar2" channel="sysNotifyTodo2">
								<c:choose>
									<%-- 待办 --%>
									<c:when test="${'doing' eq param.oprType}">
										<c:if test="${empty param.owner || param.owner == 'true'}">
										<kmss:auth requestURL="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=deleteall" requestMethod="POST">
											<!-- 置为已办事项 -->
											<ui:button text="${lfn:message('sys-notify:sysNotifyTodo.button.todo.finish')}" onclick="__del('deleteall','1')" channel="sysNotifyTodo2"></ui:button>
										</kmss:auth>
										</c:if>
										<c:if test="${not empty param.oprType && param.owner == 'false'}">
										<kmss:auth requestURL="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngDelete" requestMethod="POST">
											<!-- 置为已办事项 -->
											<ui:button text="${lfn:message('sys-notify:sysNotifyTodo.button.todo.finish')}" onclick="__del('mngDelete','1')" channel="sysNotifyTodo2"></ui:button>
										</kmss:auth>
										</c:if>
									</c:when>
									<%-- 已办 --%>
									<c:otherwise>
										<c:if test="${empty param.owner || param.owner == 'true'}">
										<kmss:auth requestURL="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=deleteall" requestMethod="POST">
											<!-- 删除 -->
											<ui:button text="${lfn:message('button.deleteall')}" onclick="__del('deleteall','1')" channel="sysNotifyTodo2"></ui:button>
										</kmss:auth>
										</c:if>
										<c:if test="${not empty param.oprType && param.owner == 'false'}">
										<kmss:auth requestURL="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngDelete" requestMethod="POST">
											<!-- 删除 -->
											<ui:button text="${lfn:message('button.deleteall')}" onclick="__del('mngDelete','1')" channel="sysNotifyTodo2"></ui:button>
										</kmss:auth>
										</c:if>
									</c:otherwise>
								</c:choose>
								
							</ui:toolbar>
						</div>
					</div>
				</div>
				<!-- 内容列表 -->
				<list:listview channel="sysNotifyTodo2">
					<ui:source type="AjaxJson">
						{url:'/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&viewType=read&oprType=${JsParam.oprType}&owner=${JsParam.owner}'}
					</ui:source>
					<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
						rowHref="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId=!{fdId}">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial>
						<list:col-auto props="fdSubject,fdAppName,fdType,fdCreateTime,finishDate,operations"></list:col-auto>
					</list:colTable>
					<ui:event topic="list.loaded">
						Dropdown.init();
					</ui:event>
				</list:listview>
				<br>
				<!-- 分页 -->
			 	<list:paging channel="sysNotifyTodo2"></list:paging>
	 		</ui:content>	 		
	 		
			<ui:content id="sysTodo" title="${ lfn:message('sys-notify:sysNotifyTodo.cate.system') }">
				<!-- 筛选器 -->
 				<list:criteria id="criteria3" multi="false" channel="sysNotifySystemTodo">
					<list:cri-ref key="fdSubject" ref="criterion.sys.docSubject" title="${lfn:message('sys-notify:sysNotifyTodo.fdSubject') }"></list:cri-ref>

					<% if(com.landray.kmss.sys.notify.util.SysNotifyConfigUtil.getFdDisplayAppName() == 1) { %>
					<!-- 应用 -->
					<list:cri-criterion title="${ lfn:message('sys-notify:sysNotifyTodo.app')}" key="fdAppName" channel="sysNotifySystemTodo">
						<list:box-select>
							<list:item-select>
								<ui:source type="AjaxJson">
									{url:'/sys/notify/sys_notify_todo/sysNotifySystemTodo.do?method=getAppModules&oprType=doing&isAdmin=true'}
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					<% } %>
					<!-- 模块 -->
					<list:cri-criterion title="${ lfn:message('sys-notify:sysNotifyTodo.module')}" key="fdModelName" channel="sysNotifySystemTodo">
						<list:box-select>
							<list:item-select id="fdModelNameCriterion3">
								<ui:source type="AjaxJson">
									{url:'/sys/notify/sys_notify_todo/sysNotifySystemTodo.do?method=getAppModules&oprType=doing&isAdmin=true&fdAppName=no'}
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					<!-- 人员 -->
					<list:cri-ref ref="criterion.sys.postperson.availableAll"
						  key="userId" multi="false" title="${lfn:message('sys-notify:sysNotifyTodo.org') }" />
				</list:criteria>
				
				<!-- 操作栏 -->
				<div class="lui_list_operation">
					<!-- 全选 -->
				 	<div class="lui_list_operation_order_btn">
						<list:selectall channel="sysNotifySystemTodo"></list:selectall>
					</div>
					<!-- 分割线 -->
					<div class="lui_list_operation_line"></div>
				 	<!-- 排序 -->
					<div class="lui_list_operation_sort_btn">
						<div class="lui_list_operation_order_text">
							${ lfn:message('list.orderType') }：
						</div>
						<div class="lui_list_operation_sort_toolbar">
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" channel="sysNotifySystemTodo">
								<c:choose>
									<%-- 待办 --%>
									<c:when test="${'doing' eq param.oprType}">
										<list:sortgroup>
											<list:sort property="fdCreateTime" text="${lfn:message('sys-notify:sysNotifyTodo.fdCreateDate') }" group="sort.list" value="down"></list:sort>
											<list:sort property="fdSubject" text="${lfn:message('sys-notify:sysNotifyTodo.fdSubject') }" group="sort.list"></list:sort>
											<list:sort property="fdType" text="${lfn:message('sys-notify:sysNotifyTodo.fdType') }" group="sort.list"></list:sort>
										</list:sortgroup>
									</c:when>
									<%-- 已办 --%>
									<c:otherwise>
										<list:sortgroup>
											<list:sort property="fdFinishTime" text="${lfn:message('sys-notify:sysNotifyTodoDoneInfo.fdFinishTime') }" group="sort.list" value="down"></list:sort>
											<list:sort property="todo.fdSubject" text="${lfn:message('sys-notify:sysNotifyTodo.fdSubject') }" group="sort.list"></list:sort>
											<list:sort property="todo.fdType" text="${lfn:message('sys-notify:sysNotifyTodo.fdType') }" group="sort.list"></list:sort>
										</list:sortgroup>
									</c:otherwise>
								</c:choose>
							</ui:toolbar>
						</div>
					</div>
					<!-- 分页 -->	
					<div class="lui_list_operation_page_top">
						<list:paging layout="sys.ui.paging.top" channel="sysNotifySystemTodo"> 		
						</list:paging>
					</div>
					<!-- 操作按钮 -->
					<div style="float:right">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar id="Btntoolbar3" channel="sysNotifySystemTodo">
								<c:choose>
									<%-- 待办 --%>
									<c:when test="${'doing' eq param.oprType}">
										<c:if test="${empty param.owner || param.owner == 'true'}">
										<kmss:auth requestURL="/sys/notify/sys_notify_todo/sysNotifySystemTodo.do?method=deleteall" requestMethod="POST">
											<!-- 置为已办事项 -->
											<ui:button text="${lfn:message('sys-notify:sysNotifyTodo.button.todo.finish')}" onclick="__del('deleteall','2')" channel="sysNotifySystemTodo"></ui:button>
										</kmss:auth>
										</c:if>
										<c:if test="${not empty param.oprType && param.owner == 'false'}">
										<kmss:auth requestURL="/sys/notify/sys_notify_todo/sysNotifySystemTodo.do?method=mngDelete" requestMethod="POST">
											<!-- 置为已办事项 -->
											<ui:button text="${lfn:message('sys-notify:sysNotifyTodo.button.todo.finish')}" onclick="__del('mngDelete','2')" channel="sysNotifySystemTodo"></ui:button>
										</kmss:auth>
										</c:if>
									</c:when>
									<%-- 已办 --%>
									<c:otherwise>
										<c:if test="${empty param.owner || param.owner == 'true'}">
										<kmss:auth requestURL="/sys/notify/sys_notify_todo/sysNotifySystemTodo.do?method=deleteall" requestMethod="POST">
											<!-- 删除 -->
											<ui:button text="${lfn:message('button.deleteall')}" onclick="__del('deleteall','2')" channel="sysNotifySystemTodo"></ui:button>
										</kmss:auth>
										</c:if>
										<c:if test="${not empty param.oprType && param.owner == 'false'}">
										<kmss:auth requestURL="/sys/notify/sys_notify_todo/sysNotifySystemTodo.do?method=mngDelete" requestMethod="POST">
											<!-- 删除 -->
											<ui:button text="${lfn:message('button.deleteall')}" onclick="__del('mngDelete','2')" channel="sysNotifySystemTodo"></ui:button>
										</kmss:auth>
										</c:if>
									</c:otherwise>
								</c:choose>
								
							</ui:toolbar>
						</div>
					</div>
				</div>
				<!-- 内容列表 -->
				<list:listview channel="sysNotifySystemTodo">
					<ui:source type="AjaxJson">
						{url:'/sys/notify/sys_notify_todo/sysNotifySystemTodo.do?method=mngList&oprType=${JsParam.oprType}&owner=${JsParam.owner}'}
					</ui:source>
					<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
						rowHref="/sys/notify/sys_notify_todo/sysNotifySystemTodo.do?method=view&fdId=!{fdId}">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial>
						<list:col-auto props="fdSubject,fdAppName,fdType,fdCreateTime,finishDate,operations"></list:col-auto>
					</list:colTable>
					<ui:event topic="list.loaded">
						Dropdown.init();
					</ui:event>
				</list:listview>
				<br>
				<!-- 分页 -->
			 	<list:paging channel="sysNotifySystemTodo"></list:paging>
	 		</ui:content>	 		
		</ui:tabpanel>
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
				
		 		// 置为已办事项
		 		window.__del = function(method,type,id) {
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
					if("1"==type){
						<c:choose>
							<%-- 待办 --%>
							<c:when test="${'doing' eq param.oprType}">
							var url  = '<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=' + method + '"/>';
							var msg = '<bean:message bundle="sys-notify" key="sysNotifyTodo.confirm.finish"/>';
							</c:when>
							<%-- 已办 --%>
							<c:otherwise>
							var url  = '<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=' + method + '&finish=1"/>';
							var msg = '<bean:message key="page.comfirmDelete"/>';
							</c:otherwise>
						</c:choose>
		 			}else if("2"==type){
						<c:choose>
							<%-- 待办 --%>
							<c:when test="${'doing' eq param.oprType}">
							var url  = '<c:url value="/sys/notify/sys_notify_todo/sysNotifySystemTodo.do?method=' + method + '"/>';
							var msg = '<bean:message bundle="sys-notify" key="sysNotifyTodo.confirm.finish"/>';
							</c:when>
							<%-- 已办 --%>
							<c:otherwise>
							var url  = '<c:url value="/sys/notify/sys_notify_todo/sysNotifySystemTodo.do?method=' + method + '&finish=1"/>';
							var msg = '<bean:message key="page.comfirmDelete"/>';
							</c:otherwise>
						</c:choose>
		 			}
					
					var criteriaValues = LUI("criteria1").criteriaValues
					var userIds = [],userId = "";
					for(var i = 0;i<criteriaValues.length;i++){
						if(criteriaValues[i].key=='userId'){
							userIds = criteriaValues[i].values;
							break;
						}
					}
					if(userIds.length>0){
						userId = userIds[0].value;
					}
					url +="&q.userId="+userId;
					dialog.confirm(msg, function(value) {
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
				
				window.setNotifySelfTitle = function(type,id) {
					var changeName;
					var iframeUrl = "/sys/notify/sys_notify_lang/sysNotifyLang.do?method=selfTitleLang&fdId="+id+"&type="+type+"&idType=todo";
					iframeUrl = '<c:url value="'+iframeUrl+'"/>';
					var title = '<bean:message bundle="sys-notify" key="sysNotifySelfTitleSetting.set.lang.title"/>' ;
					dialog.iframe(iframeUrl, title, function(data) {
						if (null != data && undefined != data) {
							$.ajax({
								url:"${KMSS_Parameter_ContextPath}sys/notify/sys_notify_lang/sysNotifyLang.do?method=saveTitleLang&fdId="+id+"&type="+type+"&idType=todo",
								type: 'POST',
								data:data,
								dataType: 'json',
								async:false, 
								success: function(data){
									alert('<bean:message bundle="sys-notify" key="sysNotifySelfTitleSetting.set.title.success"/>');
								}
						   }); 
			
						}
					}, {
						width : 700,
						height : 800
					});
				}

				// 应用切换时，修改相应的模块
				topic.channel('sysNotifyTodo').subscribe('criteria.criterion.changed',function(evt) {
					if(evt.key == "fdAppName") {
						var appName = "no";
						if(evt.values.length > 0)
							appName = evt.values[0].value;

						// 修改“按模块”选项的数据源，并重新加载数据
						LUI('fdModelNameCriterion').source.setUrl("/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=getAppModules&oprType=doing&isAdmin=true&fdAppName=" + appName);
						LUI('fdModelNameCriterion').source.get();

						// 应用切换后，模块需要重置，重置的方法就是自动点击“不限”
						$("#fdModelNameCriterion > .criterion-all > a").click();
					}
				});
				
				// 应用切换时，修改相应的模块
				topic.channel('sysNotifyTodo2').subscribe('criteria.criterion.changed',function(evt) {
					if(evt.key == "fdAppName") {
						var appName = "no";
						if(evt.values.length > 0)
							appName = evt.values[0].value;

						// 修改“按模块”选项的数据源，并重新加载数据
						LUI('fdModelNameCriterion2').source.setUrl("/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=getAppModules&oprType=doing&isAdmin=true&fdAppName=" + appName);
						LUI('fdModelNameCriterion2').source.get();

						// 应用切换后，模块需要重置，重置的方法就是自动点击“不限”
						$("#fdModelNameCriterion2 > .criterion-all > a").click();
					}
				});
				
				// 应用切换时，修改相应的模块
				topic.channel('sysNotifySystemTodo').subscribe('criteria.criterion.changed',function(evt) {
					if(evt.key == "fdAppName") {
						var appName = "no";
						if(evt.values.length > 0)
							appName = evt.values[0].value;

						// 修改“按模块”选项的数据源，并重新加载数据
						LUI('fdModelNameCriterion3').source.setUrl("/sys/notify/sys_notify_todo/sysNotifySystemTodo.do?method=getAppModules&oprType=doing&isAdmin=true&fdAppName=" + appName);
						LUI('fdModelNameCriterion3').source.get();

						// 应用切换后，模块需要重置，重置的方法就是自动点击“不限”
						$("#fdModelNameCriterion3 > .criterion-all > a").click();
					}
				});				

		 	});
	 	</script>
	</template:replace>
</template:include>
