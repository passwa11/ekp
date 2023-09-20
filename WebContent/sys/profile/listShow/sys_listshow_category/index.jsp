<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
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
						<list:sort property="fdName" text="${lfn:message('sys-profile:sysListshowCategory.fdName') }" group="sort.list"  value="down"></list:sort>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<div style="float:right">
					<div style="display: inline-block;vertical-align: middle;">
						<ui:toolbar id="Btntoolbar">
							<ui:button id="del" text="${lfn:message('sys-profile:sys.profile.button.initialize')}" order="3" onclick="delDoc()"></ui:button>
						</ui:toolbar>
					</div>
				</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/profile/listShow/sys_listshow_category/sysListshowCategory.do?method=list&modelName=${param.modelName}&simple=${param.simple}'}
			</ui:source>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" onRowClick="listShowConfig('!{fdId}','!{fdModel}');">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdName,fdModel,fdPage"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
 		seajs.use([ 'sys/ui/js/dialog' ,'lui/topic'],function(dialog,topic) {
			window.listShowConfig=function(fdId,modelName){
	 		var url='/sys/profile/listShow/sys_listShow/sysListShow.do?method=showFields&modelName='+modelName+'&fdId='+fdId;
	 	
	 							dialog.iframe(url,'${lfn:message("sys-profile:sys.profile.list.display.config")}',null,{
	 								width : 720,
	 								height : 530
	 							});
	 						}
			
			//删除文档
			window.delDoc = function(){
				var values = [];
				$("input[name='List_Selected']:checked").each(function(){
					values.push($(this).val());
				});
				if(values.length==0){
					dialog.alert('${lfn:message("sys-profile:sys.profile.noSelect")}');
					return;
				}
				
				dialog.confirm('${lfn:message("sys-profile:sys.profile.comfirmInitialize")}',function(value){
					if(value==true){
						window.del_load = dialog.loading();
						$.post('${LUI_ContextPath }/sys/profile/listShow/sys_listshow_category/sysListshowCategory.do?method=deleteall',
								$.param({"List_Selected":values},true),delCallback,'json');
					}
				});
			};
			
			function delCallback(data){
				if(window.del_load!=null)
					window.del_load.hide();
				if(data!=null && data.status==true){
					topic.publish("list.refresh");
					dialog.success('${lfn:message("return.optSuccess")}');
				}else{
					dialog.failure('${lfn:message("return.optFailure")}');
				}
			};
			
		}); 
	 	</script>
	</template:replace>
</template:include>
