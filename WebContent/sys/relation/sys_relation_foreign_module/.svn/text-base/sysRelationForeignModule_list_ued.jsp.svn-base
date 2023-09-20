<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
<template:replace name="head">

		<link rel="stylesheet" type="text/css"
			href="${LUI_ContextPath}/sys/relation/import/resource/view.css" />

<script type="text/javascript">

Com_IncludeFile("dialog.js", null, "js");
function afterEngineSelect(rtnVal) {
	if(rtnVal!=null) {
		Com_OpenWindow(rtnVal.GetHashMapArray()[0].id, "_self");
	}
}
function List_ConfirmDel(){
	seajs.use(['lui/dialog'],function(dialog){
		dialog.confirm("<bean:message key="page.comfirmDelete"/>",function(isOk){
			if(isOk){
				Com_Submit(document.sysRelationForeignModuleForm, 'deleteall');
			}
		});
	});
}
</script>
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
	<form name="sysRelationForeignModuleForm" action="<c:url value='/sys/relation/sys_relation_foreign_module/sysRelationForeignModule.do'/>" method="post">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-auto modelName="com.landray.kmss.sys.relation.model.SysRelationForeignModule" 
			property="fdModuleName"
			/>
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
								<list:sort property="fdModuleName" text="${ lfn:message('sys-relation:sysRelationForeignModule.fdModuleName') }" group="sort.list"></list:sort>
								<list:sort property="fdOrder" text="${lfn:message('sys-relation:sysRelationForeignModule.fdOrder')  }" group="sort.list"  value="down"></list:sort>
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
						
									<!-- 导入 -->
									<ui:button text="${lfn:message('button.import')}" 
									onclick="Dialog_Tree(false, null, null, null, 'sysRelationForeignModuleImportService&fdValue=!{value}', 
									 	'${lfn:message('sys-relation:sysRelationForeignModule.fdSearchEngineBean.selectImport')}',
									 	 null, afterEngineSelect, null, null,true);">
									</ui:button>
					
									<kmss:auth requestURL="sys/relation/sys_relation_foreign_module/sysRelationForeignModule.do?method=add" requestMethod="GET">
									
										<!-- 新建 -->
									<ui:button text="${lfn:message('button.add')}" onclick="Com_OpenWindow(\"sysRelationForeignModule.do?method=add\");">
										</ui:button>
									</kmss:auth>
									
								<kmss:auth requestURL="/sys/relation/sys_relation_foreign_module/sysRelationForeignModule.do?method=deleteall" requestMethod="GET">
						
									<!-- 删除 -->
									<ui:button text="${lfn:message('button.delete')}" onclick="deleteAll();"></ui:button>
						
								</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
			<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/relation/sys_relation_foreign_module/sysRelationForeignModule.do?method=listRelative'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable"  style="text-align: center;"
				rowHref="/sys/relation/sys_relation_foreign_module/sysRelationForeignModule.do?method=edit&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdModuleName,fdOrder,fdSearchEngineBean"></list:col-auto>
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

				window.deleteAll = function(id){
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
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.post('<c:url value="/sys/relation/sys_relation_foreign_module/sysRelationForeignModule.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),delCallback,'json');
						}
					});
				};

				window.delCallback = function(data){
					if(window.del_load!=null){
						window.del_load.hide();
					}
					topic.publish("list.refresh");
					dialog.result(data);
				};
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});

			});
		</script>

	</template:replace>
</template:include>

