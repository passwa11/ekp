<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.list">
	<template:replace name="title">${ lfn:message('sys-organization:table.sysOrgMatrix') }</template:replace>
	<template:replace name="head">
		<link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/resource/css/matrixData.css">
	</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-organization:sysOrgMatrix.fdName') }" style="width: 280px;"></list:cri-ref>
			<list:cri-criterion title="${ lfn:message('sys-organization:sysOrgMatrix.fdIsAvailable')}" key="fdIsAvailable"> 
				<list:box-select>
					<list:item-select>
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
						<list:sort property="sysOrgMatrix.fdOrder" text="${lfn:message('sys-organization:sysOrgMatrix.fdOrder') }" group="sort.list" value="up"></list:sort>
						<list:sort property="sysOrgMatrix.fdName" text="${lfn:message('sys-organization:sysOrgMatrix.fdName') }" group="sort.list"></list:sort>
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
						<ui:togglegroup order="0">
						    <ui:toggle icon="lui_icon_s_zaiyao" title="${lfn:message('sys-organization:sysOrgMatrix.card')}" 
								group="tg_1" text="${lfn:message('sys-organization:sysOrgMatrix.card')}" 
								value="gridtable" onclick="LUI('listview').switchType(this.value);">
							</ui:toggle>
							<ui:toggle icon="lui_icon_s_liebiao" title="${ lfn:message('list.columnTable') }" 
								value="columntable" group="tg_1" text="${ lfn:message('list.columnTable') }" 
								onclick="LUI('listview').switchType(this.value);">
							</ui:toggle>
						</ui:togglegroup>
						<c:if test="${param.available != '0'}">
						<kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=add">
							<!-- 增加 -->
							<ui:button text="${lfn:message('button.add')}" onclick="add()" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=invalidatedAll">
							<!-- 所有置为无效 -->
							<ui:button text="${lfn:message('sys-organization:sys.org.available.false')}" onclick="invalidated()" order="3" ></ui:button>
						</kmss:auth>
						</c:if>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<div class="lui_matrix_card_wrap">
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=list&parentCate=${JsParam.parentCate}'}
			</ui:source>
			<!-- 格子视图 -->
			<list:gridTable name="gridtable" columnNum="4">
				<list:row-template>
					<c:import url="/sys/organization/sys_org_matrix/sysOrgMatrix_box_content_tmpl.jsp" charEncoding="UTF-8"></c:import>
				</list:row-template>
			</list:gridTable>
			<!-- 列表视图 -->
			<list:colTable layout="sys.ui.listview.columntable" name="columntable"
				rowHref="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=view&fdId=!{fdId}">
				<kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=invalidatedAll">
				<list:col-checkbox></list:col-checkbox>
				</kmss:auth>
				<list:col-auto></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		</div>
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
		 			Com_OpenWindow('<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix.do" />?method=add&parentCate=${JsParam.parentCate}');
		 		};
		 		// 编辑
		 		window.edit = function(id, step) {
					event.stopPropagation();
			 		if(id) {
			 			if(step){
			 				step = "&step=" + step;
			 			} else {
			 				step = "";
			 			}
		 				Com_OpenWindow('<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix.do" />?method=edit&fdId=' + id + step);
			 		}
		 		};
				window.divideEdit = function(id) {
					event.stopPropagation();
			 		if(id) {
		 				Com_OpenWindow('<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix.do" />?method=editMatrixData&fdId=' + id);
			 		}
		 		};
		 		// 矩阵数据维护
		 		window.dataCate = function(id) {
					event.stopPropagation();
		 			Com_OpenWindow('<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix.do" />?method=editMatrixData&fdId=' + id);
		 		}
		 		// 置为无效
		 		window.invalidated = function(id, available) {
					event.stopPropagation();
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
					var msg = '<bean:message key="organization.invalidatedAll.comfirm" bundle="sys-organization"/>';
					if(available) {
						msg = '<bean:message key="organization.enable.comfirm" bundle="sys-organization"/>';
					}
					var url  = '<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=invalidatedAll"/>';
					if(id) {
						url  = '<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=invalidated"/>&fdId=' + id;
					}
					dialog.confirm(msg, function(value) {
						if(value == true) {
							window.del_load = dialog.loading();
							$.ajax({
								url : url,
								type : 'POST',
								data : $.param({"List_Selected" : values, "available": available}, true),
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
