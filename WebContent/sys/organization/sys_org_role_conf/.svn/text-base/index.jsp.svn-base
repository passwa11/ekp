<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.list">
	<template:replace name="title">${ lfn:message('sys-organization:table.sysOrgRoleConf') }</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-organization:sysOrgRoleConf.fdName') }"></list:cri-ref>
			<list:cri-criterion title="${ lfn:message('sys-organization:sysOrgRoleConf.fdIsAvailable')}" key="fdIsAvailable"> 
				<list:box-select>
					<list:item-select cfg-defaultValue="true">
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-organization:sys.org.available.result.true')}', value:'true'},
							{text:'${ lfn:message('sys-organization:sys.org.available.result.false')}',value:'false'}]
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
						<list:sort property="fdOrder" text="${lfn:message('sys-organization:sysOrgRoleConf.fdOrder') }" group="sort.list" value="up"></list:sort>
						<list:sort property="fdCreateTime" text="${lfn:message('model.fdCreateTime') }" group="sort.list"></list:sort>
						<list:sort property="fdName" text="${lfn:message('sys-organization:sysOrgRoleConf.fdName') }" group="sort.list"></list:sort>
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
						<kmss:auth requestURL="/sys/organization/sys_org_role_conf/sysOrgRoleConf.do?method=add">
							<!-- 增加 -->
							<ui:button text="${lfn:message('button.add')}" onclick="add()" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/organization/sys_org_role_conf/sysOrgRoleConf.do?method=invalidatedAll" requestMethod="POST">
							<!-- 置为无效 -->
							<ui:button text="${lfn:message('sys-organization:organization.invalidated')}" onclick="chgenabled(false)" order="3" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/organization/sys_org_role_conf/sysOrgRoleConf.do?method=invalidatedAll" requestMethod="POST">
							<!-- 快速排序 -->
							<jsp:include page="/sys/profile/common/change_order_num.jsp">
								<jsp:param name="modelName" value="com.landray.kmss.sys.organization.model.SysOrgRoleConf" />
								<jsp:param name="property" value="fdOrder" />
							</jsp:include>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/organization/sys_org_role_conf/sysOrgRoleConf.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/organization/sys_org_role_conf/sysOrgRoleConf.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdOrder,fdRoleConfCate,fdName,fdIsAvailable,fdCreateTime,operations"></list:col-auto>
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
		 			Com_OpenWindow('<c:url value="/sys/organization/sys_org_role_conf/sysOrgRoleConf.do" />?method=add');
		 		};
		 		// 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/organization/sys_org_role_conf/sysOrgRoleConf.do" />?method=edit&fdId=' + id);
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
					var url  = '<c:url value="/sys/organization/sys_org_role_conf/sysOrgRoleConf.do?method=deleteall"/>';
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
				// 修改状态
				window.chgenabled = function(enabled, id){
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
					var url  = '<c:url value="/sys/organization/sys_org_role_conf/sysOrgRoleConf.do?method=invalidatedAll"/>';
					var msg = '<bean:message bundle="sys-organization" key="sys.org.available.comfirmEnable"/>';
					if(!enabled) {
						msg = '<bean:message bundle="sys-organization" key="sys.org.available.comfirmDisable"/>';
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
