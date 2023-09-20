<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria" expand="true">
			<list:cri-ref style="width:145px;" key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-mportal:sysMportalImgSource.fdName') }"></list:cri-ref>
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
						<list:sort property="fdName" text="${lfn:message('sys-mportal:sysMportalImgSource.fdName') }" group="sort.list" ></list:sort>
						<list:sort property="docCreateTime" text="${lfn:message('sys-mportal:sysMportalImgSource.docCreateTime') }" group="sort.list" value="down"></list:sort>
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
						<kmss:auth requestURL="/sys/mportal/sys_mportal_imgsource/sysMportalImgSource.do?method=add">
							<ui:button text="${ lfn:message('button.add') }"  onclick="add();"></ui:button> 
						</kmss:auth>
						<kmss:auth requestURL="/sys/mportal/sys_mportal_imgsource/sysMportalImgSource.do?method=deleteall">
							<ui:button text="${ lfn:message('button.deleteall') }" onclick="deleteAll();"></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
	
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/mportal/sys_mportal_imgsource/sysMportalImgSource.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdName,docCreator.fdName,docCreateTime,operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<!-- 分页 -->
	 	<list:paging/>
	
		<script type="text/javascript">
			seajs.use(['lui/jquery','lui/topic','lui/dialog'],function($,topic,dialog){
				
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				//新建
				window.add = function(){
					Com_OpenWindow('${LUI_ContextPath}/sys/mportal/sys_mportal_imgsource/sysMportalImgSource.do?method=add');
				};
				//编辑
				window.edit = function(id){
					Com_OpenWindow('${LUI_ContextPath}/sys/mportal/sys_mportal_imgsource/sysMportalImgSource.do?method=edit&fdId='+id);
				};
				//删除
				window.del = function(id) {
		 			if(!id) {
		 				dialog.alert('<bean:message key="page.noSelect"/>');
						return;
		 			} else {
		 				dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(flag){
							if(flag) {
								var loading = dialog.loading();
								$.get('<c:url value="/sys/mportal/sys_mportal_imgsource/sysMportalImgSource.do?method=delete&fdId="/>' + id,
									function(data) {
										if(loading)
											loading.hide();
										if(data && data.status==true){
											topic.publish("list.refresh");
											dialog.success('<bean:message key="return.optSuccess" />');
										}else{
											dialog.failure('<bean:message key="return.optFailure" />');
										}
									},'json');
							}
						});
		 			}
		 		};
				window.deleteAll = function(id){
					var values = [];
					if(id) {
		 				values.push(id);
			 		} else{
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
							$.post('<c:url value="/sys/mportal/sys_mportal_imgsource/sysMportalImgSource.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),function(data){
								if(window.del_load!=null)
									window.del_load.hide();
								if(data!=null && data.status==true){
									topic.publish("list.refresh");
									dialog.success('<bean:message key="return.optSuccess" />');
								}else{
									dialog.failure('<bean:message key="return.optFailure" />');
								}
							},'json');
						}
					});
				};
			});
		</script>
	
	</template:replace>
</template:include>