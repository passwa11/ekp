<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.list">
	<template:replace name="title">${ lfn:message('sys-organization:sysOrgElement.group') }</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-organization:sysOrgElement.search1') }" style="width: 400px;"></list:cri-ref>
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
					<c:if test="${param.fdFlagDeleted != null}">
					<%@ include file="/sys/organization/org_common_select.jsp"%>
					</c:if>
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
					<list:sortgroup>
						<list:sort property="fdOrder" text="${lfn:message('sys-organization:sysOrgGroup.fdOrder') }" group="sort.list" value="up"></list:sort>
						<list:sort property="fdName" text="${lfn:message('sys-organization:sysOrgGroup.fdName') }" group="sort.list"></list:sort>
						<list:sort property="fdNo" text="${lfn:message('sys-organization:sysOrgGroup.fdNo') }" group="sort.list"></list:sort>
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
						<c:if test="${param.available != '0'}">
						<kmss:auth requestURL="/sys/organization/sys_org_group/sysOrgGroup.do?method=add">
							<!-- 增加 -->
							<ui:button text="${lfn:message('button.add')}" onclick="add()" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/organization/sys_org_group/sysOrgGroup.do?method=invalidatedAll" requestMethod="POST">
							<!-- 置为无效 -->
							<ui:button text="${lfn:message('sys-organization:organization.invalidated')}" onclick="invalidated()" order="3" ></ui:button>
							<c:if test="${param.fdFlagDeleted != null}">
								<!-- 所有置为无效 -->
								<ui:button text="${lfn:message('sys-organization:organization.invalidatedAll')}" onclick="invalidatedAll()" order="3" ></ui:button>
							</c:if>
						</kmss:auth>
						</c:if>
						<kmss:auth requestURL="/sys/organization/sys_org_group/sysOrgGroup.do?method=invalidatedAll" requestMethod="POST">
							<!-- 快速排序 -->
							<c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.sys.organization.model.SysOrgGroup"></c:param>
								<c:param name="property" value="fdOrder"></c:param>
							</c:import>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview cfg-needMinHeight="false">
			<ui:source type="AjaxJson">
				{url:'/sys/organization/sys_org_group/sysOrgGroup.do?method=list&available=${JsParam.available}&parentCate=${JsParam.parentCate}&parent=${JsParam.parent}&fdFlagDeleted=${JsParam.fdFlagDeleted}&fdImportInfo=${JsParam.fdImportInfo}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/organization/sys_org_group/sysOrgGroup.do?method=view&fdId=!{fdId}">
				<kmss:auth requestURL="/sys/organization/sys_org_group/sysOrgGroup.do?method=invalidatedAll" requestMethod="POST">
				<list:col-checkbox></list:col-checkbox>
				</kmss:auth>
				<list:col-auto props="fdOrder,fdGroupCate,fdName,fdNo,fdMemo,operations"></list:col-auto>
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
		 			Com_OpenWindow('<c:url value="/sys/organization/sys_org_group/sysOrgGroup.do" />?method=add&parentCate=${JsParam.parentCate}');
		 		};
		 		// 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/organization/sys_org_group/sysOrgGroup.do" />?method=edit&fdId=' + id);
		 		};
		 		// 查看日志
		 		window.viewLog = function(id, fdName) {
		 			if(id) {
						var url = '<c:url value="/sys/organization/sys_log_organization/index.jsp" />?fdId=' + id;
						url = Com_SetUrlParameter(url, "fdName", encodeURI("${lfn:message('sys-organization:sysOrgElement.group')}-" + fdName));
		 				Com_OpenWindow(url);
			 		}
			 	};
		 		// 置为无效
		 		window.invalidated = function(id) {
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
					var url  = '<c:url value="/sys/organization/sys_org_group/sysOrgGroup.do?method=invalidatedAll"/>';
					dialog.confirm('<bean:message key="organization.invalidatedAll.comfirm" bundle="sys-organization"/>', function(value) {
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
									if(data.responseJSON.message && data.responseJSON.message.length > 0)
										dialog.alert(data.responseJSON.message[0].msg);
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
				// 所有置为无效
		 		window.invalidatedAll = function() {
					dialog.confirm('<bean:message key="organization.invalidatedAll.comfirm" bundle="sys-organization"/>', function(value) {
						if(value == true) {
							window.del_load = dialog.loading();
							$.ajax({
								url : '<c:url value="/sys/organization/sys_org_element/sysOrgElementCriteria.do?method=invalidatedAllOmsDeleted"/>',
								type : 'POST',
								data : $.param({"providerKey" : "${JsParam.fdImportInfo}"}, true),
								dataType : 'json',
								error : function(data) {
									if(window.del_load != null) {
										window.del_load.hide(); 
									}
									if(data.responseJSON.message && data.responseJSON.message.length > 0)
										dialog.alert(data.responseJSON.message[0].msg);
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
