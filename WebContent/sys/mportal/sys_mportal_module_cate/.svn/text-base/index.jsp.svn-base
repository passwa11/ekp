<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
			<list:cri-ref style="width:145px;" key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-mportal:sysMportalModuleCate.fdName') }"></list:cri-ref>
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
						<list:sort property="fdOrder" text="${lfn:message('sys-mportal:sysMportalModuleCate.fdOrder') }" group="sort.list" value="up"></list:sort>
						<list:sort property="docCreateTime" text="${lfn:message('sys-mportal:sysMportalModuleCate.docCreateTime') }" group="sort.list"></list:sort>
						<list:sort property="fdName" text="${lfn:message('sys-mportal:sysMportalModuleCate.fdName') }" group="sort.list"></list:sort>
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
						<kmss:auth requestURL="/sys/mportal/sys_mportal_module_cate/sysMportalModuleCate.do?method=add">
						    <ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/mportal/sys_mportal_module_cate/sysMportalModuleCate.do?method=deleteall">
						    <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
						    <!-- 快速排序 -->
							<c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.sys.mportal.model.SysMportalModuleCate"></c:param>
								<c:param name="property" value="fdOrder"></c:param>
							</c:import>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
	
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/mportal/sys_mportal_module_cate/sysMportalModuleCate.do?method=data'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdOrder,fdName,docCreator.fdName,docCreateTime,operations"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<!-- 用于删除，批量删除 -->
	 	<form name="sysMportalModuleCateForm" method="post" action="${LUI_ContextPath}/sys/mportal/sys_mportal_module_cate/sysMportalModuleCate.do">
	 	</form>
	
		<script type="text/javascript">
			seajs.use(['lui/jquery','lui/topic','lui/dialog'],function($,topic,dialog){
				
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				//新建
				window.add = function(){
					Com_OpenWindow('<c:url value="/sys/mportal/sys_mportal_module_cate/sysMportalModuleCate.do" />?method=add');
				};

		 	    // 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/mportal/sys_mportal_module_cate/sysMportalModuleCate.do" />?method=edit&fdId=' + id);
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
							for(var i=values.length-1;i>=0;i--){
								var input = document.createElement("INPUT");
								input.type="text";
								input.style.display="none";
								input.name="List_Selected";	
								input.value = values[i];
								document.forms["sysMportalModuleCateForm"].appendChild(input);	
							}
							Com_Submit(document.sysMportalModuleCateForm, 'deleteall');
						}
					});
				};
			});
		</script>
	
	</template:replace>
</template:include>