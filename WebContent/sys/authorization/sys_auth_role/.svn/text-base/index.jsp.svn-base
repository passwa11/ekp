<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.authorization.util.SysAuthAreaHelper"%>
<%@ page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-authorization:table.sysAuthRole') }</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-authorization:sysAuthRole.fdName') }" ></list:cri-ref>
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
						<list:sort property="docCreateTime" text="${ lfn:message('model.fdCreateTime') }" group="sort.list" value="down"></list:sort>
						<list:sort property="fdName" text="${lfn:message('sys-authorization:sysAuthRole.fdName') }" group="sort.list"></list:sort>
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
						<%
						if(SysAuthAreaHelper.canCreateRole() || !ISysAuthConstant.IS_AREA_ENABLED || UserUtil.checkRole("SYSROLE_ADMIN")){
						%>
						<kmss:auth requestURL="/sys/authorization/sys_auth_role/sysAuthRole.do?method=add&type=${param.type}">
							<!-- 增加 -->
							<ui:button text="${lfn:message('button.add')}" onclick="add()" order="1" ></ui:button>	
						</kmss:auth>
						<% 
						}
						%>
						<kmss:auth requestURL="/sys/authorization/sys_auth_role/sysAuthRole.do?method=deleteall" requestMethod="POST">
							<!-- 删除 -->
							<ui:button text="${lfn:message('button.deleteall')}" onclick="del()" order="3" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/authorization/sys_auth_role/sysAuthRole.do?method=list&type=${JsParam.type}&categoryId=${JsParam.categoryId}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/authorization/sys_auth_role/sysAuthRole.do?method=view&fdId=!{fdId}&type=${JsParam.type}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdName,sysAuthCategory,fdDescription,operations"></list:col-auto>
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
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/authorization/sys_auth_role/sysAuthRole.do" />?method=add&type=${JsParam.type}&categoryId=${JsParam.categoryId}');
		 		};
		 		// 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/authorization/sys_auth_role/sysAuthRole.do" />?method=edit&fdId=' + id + '&type=${JsParam.type}');
		 		};
		 		// 查看日志
		 		window.viewLog = function(id, fdName) {
		 			if(id) {
						var url = '<c:url value="/sys/organization/sys_log_organization/index.jsp" />?fdId=' + id;
						url = Com_SetUrlParameter(url, "fdName", encodeURI("${lfn:message('sys-authorization:table.sysAuthRole')}-" + fdName));
		 				Com_OpenWindow(url);
			 		}
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
					var url  = '<c:url value="/sys/authorization/sys_auth_role/sysAuthRole.do?method=deleteall"/>';
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
