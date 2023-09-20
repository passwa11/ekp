<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<style>
	th.width30{
		width: 60px;
	}
</style>
		<!-- 筛选器 -->
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 全选 
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>-->
			<!-- 分割线 
			<div class="lui_list_operation_line"></div>-->
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
						<list:sort property="fdName" text="${lfn:message('sys-modeling-base:modeling.model.fdName') }" group="sort.list"  value="down"></list:sort>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/modeling/base/modelingAppModel.do?method=list&fdAppModelId=${JsParam.fdAppModelId}&forward=xformAuthList'}
			</ui:source>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable">
				<!--<list:col-checkbox></list:col-checkbox>-->
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdEmpty,fdName,docCreator.fdName,operations"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<!-- 分页 -->
		</div>
		<list:paging/>
	 	
	 	<script type="text/javascript">
	 		seajs.use([ 'sys/ui/js/dialog' ,'lui/topic'],function(dialog,topic) {
				window.doSetting = function(fdAppModelId){
		 			var url='/sys/modeling/auth/default_auth/sysModelingDefaultRightConfig.do?method=load&fdAppModelId=' + fdAppModelId;
					dialog.iframe(url,'${lfn:message("sys-modeling-auth:sysModelingAuth.DefaultPermissionSettings")}',null,{
						width : 870,
						height : 500
					});
				}
			}); 
	 	</script>
